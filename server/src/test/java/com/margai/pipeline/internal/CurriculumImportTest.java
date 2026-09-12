package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.AttemptType;
import com.margai.common.api.Category;
import com.margai.curriculum.api.ArchetypeStepRow;
import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.SeatType;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import com.margai.curriculum.api.TrackPhase;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * The D13 loads end to end — the committed inputs through the readers into
 * {@link CurriculumImport} against a database of their own in the shared container (the
 * {@code importtest} profile has no seed): parents before children, idempotent re-runs, updates
 * counted, refusals that write nothing, the cycle check that PLAN D13's ✅ asks for, tracks whose
 * stale steps go, and cut-offs by natural key.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class CurriculumImportTest {

    private static final Path TAXONOMY = InputReadersTest.INPUTS.resolve(TaxonomyLoadCommand.FILE);
    private static final Path PREREQUISITES = InputReadersTest.INPUTS.resolve(TaxonomyPrerequisitesCommand.FILE);
    private static final Path ARCHETYPES = InputReadersTest.INPUTS.resolve(BackboneLoadCommand.FILE);
    private static final Path CUTOFFS = InputReadersTest.INPUTS.resolve(CutoffsLoadCommand.FILE);

    @Autowired
    private CurriculumImport imports;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void emptyTheCurriculumTables() {
        jdbc.update("DELETE FROM syllabus_prerequisites");
        jdbc.update("DELETE FROM archetype_track_steps");
        jdbc.update("DELETE FROM archetype_tracks");
        jdbc.update("DELETE FROM chapter_status");
        jdbc.update("DELETE FROM syllabus_nodes");
        jdbc.update("DELETE FROM cutoffs");
    }

    @Test
    void loadsTheCommittedTaxonomyParentsFirst() {
        TaxonomyLoadReport report = imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));

        assertThat(report.inserted()).isEqualTo(516);
        assertThat(report.updated()).isZero();
        assertThat(report.unchanged()).isZero();
        assertThat(report.orphans()).isEmpty();
        assertThat(report.counts().get(Subject.physics))
                .containsEntry(NodeKind.unit, 20).containsEntry(NodeKind.chapter, 29).containsEntry(NodeKind.topic, 134);
        assertThat(report.counts().get(Subject.zoology)).containsEntry(NodeKind.unit, 6).containsEntry(NodeKind.chapter, 12);

        assertThat(count("kind = 'subject'")).isEqualTo(4);
        assertThat(count("kind = 'unit'")).isEqualTo(55);
        assertThat(count("kind = 'chapter'")).isEqualTo(83);
        assertThat(count("kind = 'topic'")).isEqualTo(374);
        assertThat(count("parent_id IS NULL")).isEqualTo(4);
        assertThat(parentCodeOf("PHY.11.GRAV.KEPLER")).isEqualTo("PHY.11.GRAV");
        assertThat(parentCodeOf("PHY.11.GRAV")).isEqualTo("PHY.U06");
        assertThat(parentCodeOf("PHY.U06")).isEqualTo("PHY");
        assertThat(jdbc.queryForObject("SELECT default_learn_minutes FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", Integer.class))
                .isEqualTo(225);
        assertThat(jdbc.queryForObject("SELECT class_level FROM syllabus_nodes WHERE code = 'PHY.00.EXPSKILL'", Integer.class))
                .isNull();
    }

    @Test
    void aSecondRunChangesNothing() {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(TAXONOMY);
        imports.loadTaxonomy(rows);

        TaxonomyLoadReport again = imports.loadTaxonomy(rows);

        assertThat(again.inserted()).isZero();
        assertThat(again.updated()).isZero();
        assertThat(again.unchanged()).isEqualTo(516);
        assertThat(count("TRUE")).isEqualTo(516);
    }

    @Test
    void aChangedRowIsAnUpdateAndKeepsItsId() {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(TAXONOMY);
        imports.loadTaxonomy(rows);
        String idBefore = jdbc.queryForObject("SELECT id::text FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", String.class);

        TaxonomyLoadReport report = imports.loadTaxonomy(rows.stream()
                .map(row -> row.code().equals("PHY.11.GRAV") ? renamed(row, "Gravitation (renamed)") : row)
                .toList());

        assertThat(report.updated()).isEqualTo(1);
        assertThat(report.unchanged()).isEqualTo(515);
        assertThat(jdbc.queryForObject("SELECT name_en FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", String.class))
                .isEqualTo("Gravitation (renamed)");
        assertThat(jdbc.queryForObject("SELECT id::text FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", String.class))
                .isEqualTo(idBefore);
    }

    @Test
    void aParentMissingFromTheFileOrOfTheWrongKindWritesNothing() {
        SyllabusNodeRow physics = new SyllabusNodeRow("PHY", Subject.physics, null, null, NodeKind.subject, "Physics", null, 1, null, true);
        SyllabusNodeRow orphanChapter = new SyllabusNodeRow("PHY.11.UNITS", Subject.physics, (short) 11, "PHY.U01",
                NodeKind.chapter, "Units", null, 1, null, true);
        SyllabusNodeRow chapterUnderSubject = new SyllabusNodeRow("PHY.11.UNITS", Subject.physics, (short) 11, "PHY",
                NodeKind.chapter, "Units", null, 1, null, true);

        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, orphanChapter)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("parent 'PHY.U01' of PHY.11.UNITS is not in the file");
        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, chapterUnderSubject)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("the parent of a chapter must be a unit; PHY.11.UNITS hangs from the subject PHY");
        assertThat(count("TRUE")).isZero();
    }

    @Test
    void loadsThePrerequisitesAndFindsNoCycle() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        List<PrerequisiteRow> edges = PrerequisitesCsvReader.read(PREREQUISITES);

        PrerequisiteLoadReport first = imports.loadPrerequisites(edges);
        PrerequisiteLoadReport again = imports.loadPrerequisites(edges);

        assertThat(first.inserted()).isEqualTo(104);
        assertThat(first.unchanged()).isZero();
        assertThat(first.edgesInDatabase()).isEqualTo(104);
        assertThat(first.nodesWithEdges()).isEqualTo(83);
        assertThat(first.orphanEdges()).isEmpty();
        assertThat(again.inserted()).isZero();
        assertThat(again.unchanged()).isEqualTo(104);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM syllabus_prerequisites", Long.class)).isEqualTo(104);
    }

    @Test
    void aCycleRollsTheRunBack() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        imports.loadPrerequisites(PrerequisitesCsvReader.read(PREREQUISITES));

        assertThatThrownBy(() -> imports.loadPrerequisites(List.of(new PrerequisiteRow("ZOO.11.ANIMALK", "BOT.11.CLASSIF"))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageStartingWith("the prerequisite graph has a cycle among ")
                .hasMessageContaining("BOT.11.CLASSIF")
                .hasMessageContaining("ZOO.11.ANIMALK");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM syllabus_prerequisites", Long.class)).isEqualTo(104);
    }

    @Test
    void unknownAndNonChapterEndpointsAreRefused() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));

        assertThatThrownBy(() -> imports.loadPrerequisites(List.of(new PrerequisiteRow("PHY.11.UNITS", "PHY.11.NOSUCH"))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("prerequisite endpoints not in the taxonomy: [PHY.11.NOSUCH]");
        assertThatThrownBy(() -> imports.loadPrerequisites(List.of(new PrerequisiteRow("PHY.U01", "PHY.11.KIN1D"))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("prerequisites are chapter-level (TECH_PLAN §2.3); not chapters: [PHY.U01]");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM syllabus_prerequisites", Long.class)).isZero();
    }

    @Test
    void loadsTheBackboneAndEveryChapterIsLearnedByATrack() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(ARCHETYPES);

        BackboneLoadReport first = imports.loadBackbone(tracks);
        BackboneLoadReport again = imports.loadBackbone(tracks);

        assertThat(first.tracksInserted()).isEqualTo(4);
        assertThat(first.stepsInserted()).isEqualTo(744);
        assertThat(first.stepsRemoved()).isZero();
        assertThat(first.stepsPerTrack())
                .containsEntry(AttemptType.fresher_2yr, 210).containsEntry(AttemptType.fresher_1yr, 166)
                .containsEntry(AttemptType.dropper, 178).containsEntry(AttemptType.repeater, 190);
        assertThat(first.nodesInNoTrack()).isEmpty();
        assertThat(first.orphanTracks()).isEmpty();
        assertThat(again.tracksUnchanged()).isEqualTo(4);
        assertThat(again.stepsUnchanged()).isEqualTo(744);
        assertThat(again.stepsInserted()).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_track_steps", Long.class)).isEqualTo(744);
        assertThat(jdbc.queryForObject(
                "SELECT n.code FROM archetype_track_steps s JOIN archetype_tracks t ON t.id = s.track_id"
                        + " JOIN syllabus_nodes n ON n.id = s.node_id WHERE t.code = 'dropper' AND s.sequence = 1",
                String.class)).isEqualTo("PHY.11.UNITS");
        assertThat(jdbc.queryForObject("SELECT weeks FROM archetype_tracks WHERE code = 'fresher_2yr'", Integer.class))
                .isEqualTo(96);
    }

    @Test
    void aShortenedTrackLosesItsStaleStepsAndAChangedStepIsAnUpdate() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(ARCHETYPES);
        imports.loadBackbone(tracks);
        // The dropper's first 50 steps learn about half the chapters; the file's last learn step is sequence 95.
        ArchetypeTrackRow dropper = tracks.get(2);
        List<ArchetypeStepRow> kept = dropper.steps().subList(0, 50).stream()
                .map(step -> step.sequence() == 1 ? new ArchetypeStepRow(1, step.nodeCode(), step.phase(), (short) 2) : step)
                .toList();
        ArchetypeTrackRow shortened = new ArchetypeTrackRow(dropper.code(), dropper.nameEn(), dropper.nameHi(),
                dropper.weeks(), dropper.descriptionMd(), kept);

        BackboneLoadReport report = imports.loadBackbone(List.of(shortened));

        assertThat(report.tracksUnchanged()).isEqualTo(1);
        assertThat(report.stepsUpdated()).isEqualTo(1);
        assertThat(report.stepsUnchanged()).isEqualTo(49);
        assertThat(report.stepsRemoved()).isEqualTo(128);
        // Chapters the first 50 steps never learn, and every unit: revision steps start at sequence 100.
        assertThat(report.nodesInNoTrack()).contains("CHE.00.PRACTICAL", "BOT.12.MICROBES", "PHY.U01", "ZOO.U08")
                .doesNotContain("PHY", "CHE", "BOT", "ZOO", "PHY.11.UNITS");
        assertThat(report.orphanTracks()).containsExactly("fresher_1yr", "fresher_2yr", "repeater");
        assertThat(jdbc.queryForObject(
                "SELECT count(*) FROM archetype_track_steps s JOIN archetype_tracks t ON t.id = s.track_id WHERE t.code = 'dropper'",
                Long.class)).isEqualTo(50);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_track_steps", Long.class)).isEqualTo(744 - 128);
    }

    @Test
    void aStepNamingTheWrongKindOfNodeOrAnUnknownNodeIsRefused() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        ArchetypeTrackRow learnsAUnit = track(new ArchetypeStepRow(1, "PHY.U01", TrackPhase.learn, (short) 1));
        ArchetypeTrackRow mocksAChapter = track(new ArchetypeStepRow(1, "PHY.11.UNITS", TrackPhase.mock, (short) 1));
        ArchetypeTrackRow unknown = track(new ArchetypeStepRow(1, "PHY.11.NOSUCH", TrackPhase.learn, (short) 1));

        assertThatThrownBy(() -> imports.loadBackbone(List.of(learnsAUnit)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper step 1: a learn step names a chapter, not the unit PHY.U01");
        assertThatThrownBy(() -> imports.loadBackbone(List.of(mocksAChapter)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper step 1: a mock step names a subject, not the chapter PHY.11.UNITS");
        assertThatThrownBy(() -> imports.loadBackbone(List.of(unknown)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track steps name nodes not in the taxonomy: [PHY.11.NOSUCH]");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_tracks", Long.class)).isZero();
    }

    @Test
    void aDuplicateSiblingSortOrderAndATopicClassMismatchAreRefused() {
        SyllabusNodeRow physics = new SyllabusNodeRow("PHY", Subject.physics, null, null, NodeKind.subject, "Physics", null, 1, null, true);
        SyllabusNodeRow unit1 = new SyllabusNodeRow("PHY.U01", Subject.physics, null, "PHY", NodeKind.unit, "Measurement", null, 1, null, true);
        SyllabusNodeRow unit2SameOrder = new SyllabusNodeRow("PHY.U02", Subject.physics, null, "PHY", NodeKind.unit, "Kinematics", null, 1, null, true);
        SyllabusNodeRow chapter = new SyllabusNodeRow("PHY.11.UNITS", Subject.physics, (short) 11, "PHY.U01", NodeKind.chapter, "Units", null, 1, null, true);
        SyllabusNodeRow topicOfClass12 = new SyllabusNodeRow("PHY.11.UNITS.SI", Subject.physics, (short) 12, "PHY.11.UNITS", NodeKind.topic, "SI units", null, 1, 45, true);

        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, unit1, unit2SameOrder)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("sort_order 1 appears twice under PHY (PHY.U01 and PHY.U02)");
        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, unit1, chapter, topicOfClass12)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("topic PHY.11.UNITS.SI has class_level 12 but its chapter PHY.11.UNITS has 11");
        assertThat(count("TRUE")).isZero();
    }

    @Test
    void aChapterLearnedTwiceOrBeforeItsPrerequisiteIsRefused() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        imports.loadPrerequisites(PrerequisitesCsvReader.read(PREREQUISITES));
        ArchetypeTrackRow twice = track(
                new ArchetypeStepRow(1, "PHY.11.UNITS", TrackPhase.learn, (short) 1),
                new ArchetypeStepRow(2, "PHY.11.UNITS", TrackPhase.learn, (short) 2));
        ArchetypeTrackRow outOfOrder = track(
                new ArchetypeStepRow(1, "PHY.11.KIN1D", TrackPhase.learn, (short) 1),
                new ArchetypeStepRow(2, "PHY.11.UNITS", TrackPhase.learn, (short) 1));

        assertThatThrownBy(() -> imports.loadBackbone(List.of(twice)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper: chapter PHY.11.UNITS is learned twice (sequences 1 and 2)");
        assertThatThrownBy(() -> imports.loadBackbone(List.of(outOfOrder)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper: PHY.11.KIN1D (sequence 1) is learned before its prerequisite PHY.11.UNITS (sequence 2)");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_tracks", Long.class)).isZero();
    }

    @Test
    void loadsTheCutoffsByNaturalKey() {
        List<CutoffRow> rows = CutoffsCsvReader.read(CUTOFFS);

        CutoffLoadReport first = imports.loadCutoffs(rows);
        CutoffLoadReport again = imports.loadCutoffs(rows);
        CutoffLoadReport changed = imports.loadCutoffs(rows.stream()
                .map(row -> row.year() == 2026 && row.category() == Category.general
                        ? new CutoffRow(row.year(), row.category(), row.quotaScope(), row.seatType(), (short) 214, row.source())
                        : row)
                .toList());

        assertThat(first.inserted()).isEqualTo(40);
        assertThat(first.rowsPerYear()).containsEntry((short) 2019, 5).containsEntry((short) 2026, 5).hasSize(8);
        assertThat(first.orphans()).isEmpty();
        assertThat(again.unchanged()).isEqualTo(40);
        assertThat(changed.updated()).isEqualTo(1);
        assertThat(changed.unchanged()).isEqualTo(39);
        assertThat(jdbc.queryForObject(
                "SELECT qualifying_marks FROM cutoffs WHERE year = 2026 AND category = 'general' AND seat_type = 'qualifying'",
                Integer.class)).isEqualTo(214);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM cutoffs", Long.class)).isEqualTo(40);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM cutoffs WHERE seat_type = 'qualifying'", Long.class)).isEqualTo(40);
    }

    private long count(String where) {
        return jdbc.queryForObject("SELECT count(*) FROM syllabus_nodes WHERE " + where, Long.class);
    }

    private String parentCodeOf(String code) {
        return jdbc.queryForObject(
                "SELECT p.code FROM syllabus_nodes n JOIN syllabus_nodes p ON p.id = n.parent_id WHERE n.code = ?",
                String.class, code);
    }

    private static SyllabusNodeRow renamed(SyllabusNodeRow row, String nameEn) {
        return new SyllabusNodeRow(row.code(), row.subject(), row.classLevel(), row.parentCode(), row.kind(), nameEn,
                row.nameHi(), row.sortOrder(), row.defaultLearnMinutes(), row.neetRelevant());
    }

    private static ArchetypeTrackRow track(ArchetypeStepRow... steps) {
        return new ArchetypeTrackRow(AttemptType.dropper, "Dropper (1st repeat)", null, (short) 40, null, List.of(steps));
    }
}

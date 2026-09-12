package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
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
 * counted, refusals that write nothing, and the cycle check that PLAN D13's ✅ asks for.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class CurriculumImportTest {

    private static final Path TAXONOMY = InputReadersTest.INPUTS.resolve(TaxonomyLoadCommand.FILE);
    private static final Path PREREQUISITES = InputReadersTest.INPUTS.resolve(TaxonomyPrerequisitesCommand.FILE);

    @Autowired
    private CurriculumImport imports;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void emptyTheCurriculumTables() {
        jdbc.update("DELETE FROM syllabus_prerequisites");
        jdbc.update("DELETE FROM archetype_track_steps");
        jdbc.update("DELETE FROM chapter_status");
        jdbc.update("DELETE FROM syllabus_nodes");
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
}

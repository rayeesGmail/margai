package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.common.api.AttemptType;
import com.margai.common.api.Category;
import com.margai.curriculum.api.ArchetypeStepRow;
import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.CutoffRow;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.SeatType;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TrackPhase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The four readers over the committed founder inputs (TECH_PLAN §6.2; the counts of the D13
 * README) and over malformed files: every refusal names the file and, for CSV, the line.
 */
class InputReadersTest {

    /** Surefire runs from {@code server/}; the inputs live beside it (InputsMixin's default). */
    static final Path INPUTS = Path.of(InputsMixin.DEFAULT_INPUTS);

    @TempDir
    Path dir;

    @Test
    void taxonomyCsvReadsTheCommittedTree() {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(INPUTS.resolve(TaxonomyLoadCommand.FILE));
        Map<String, SyllabusNodeRow> byCode = rows.stream().collect(Collectors.toMap(SyllabusNodeRow::code, Function.identity()));

        assertThat(rows).hasSize(516);
        assertThat(rows.stream().filter(row -> row.kind() == NodeKind.subject)).hasSize(4);
        assertThat(rows.stream().filter(row -> row.kind() == NodeKind.unit)).hasSize(55);
        assertThat(rows.stream().filter(row -> row.kind() == NodeKind.chapter)).hasSize(83);
        assertThat(rows.stream().filter(row -> row.kind() == NodeKind.topic)).hasSize(374);

        SyllabusNodeRow physics = byCode.get("PHY");
        assertThat(physics.subject()).isEqualTo(Subject.physics);
        assertThat(physics.parentCode()).isNull();
        assertThat(physics.classLevel()).isNull();
        assertThat(physics.nameHi()).isEqualTo("भौतिकी");

        SyllabusNodeRow kepler = byCode.get("PHY.11.GRAV.KEPLER");
        assertThat(kepler.kind()).isEqualTo(NodeKind.topic);
        assertThat(kepler.parentCode()).isEqualTo("PHY.11.GRAV");
        assertThat(kepler.classLevel()).isEqualTo((short) 11);
        assertThat(kepler.defaultLearnMinutes()).isEqualTo(45);
        assertThat(kepler.nameHi()).isNull();
        assertThat(kepler.neetRelevant()).isTrue();

        // A quoted field with commas, and a syllabus-only chapter without a class level.
        assertThat(byCode.get("PHY.11.WEP").nameEn()).isEqualTo("Work, Energy and Power");
        assertThat(byCode.get("PHY.00.EXPSKILL").classLevel()).isNull();
        assertThat(byCode.get("PHY.00.EXPSKILL").defaultLearnMinutes()).isEqualTo(450);
    }

    @Test
    void prerequisitesCsvReadsTheCommittedEdges() {
        List<PrerequisiteRow> rows = PrerequisitesCsvReader.read(INPUTS.resolve(TaxonomyPrerequisitesCommand.FILE));

        assertThat(rows).hasSize(104);
        assertThat(rows).contains(new PrerequisiteRow("BOT.11.CLASSIF", "ZOO.11.ANIMALK"));
        assertThat(rows.getFirst()).isEqualTo(new PrerequisiteRow("PHY.11.UNITS", "PHY.11.KIN1D"));
    }

    @Test
    void archetypesYamlReadsTheCommittedTracks() {
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(INPUTS.resolve(BackboneLoadCommand.FILE));

        assertThat(tracks).extracting(ArchetypeTrackRow::code)
                .containsExactly(AttemptType.fresher_2yr, AttemptType.fresher_1yr, AttemptType.dropper, AttemptType.repeater);
        assertThat(tracks).extracting(track -> track.steps().size()).containsExactly(210, 166, 178, 190);
        assertThat(tracks).extracting(ArchetypeTrackRow::weeks).containsExactly((short) 96, (short) 44, (short) 40, (short) 40);

        ArchetypeTrackRow dropper = tracks.get(2);
        assertThat(dropper.nameEn()).isEqualTo("Dropper (1st repeat)");
        assertThat(dropper.descriptionMd()).startsWith("Starts from evidence");
        assertThat(dropper.steps().getFirst()).isEqualTo(new ArchetypeStepRow(1, "PHY.11.UNITS", TrackPhase.learn, (short) 1));
        assertThat(dropper.steps().getLast()).isEqualTo(new ArchetypeStepRow(178, "ZOO", TrackPhase.mock, (short) 40));
    }

    @Test
    void cutoffsCsvReadsTheCommittedRows() {
        List<CutoffRow> rows = CutoffsCsvReader.read(INPUTS.resolve(CutoffsLoadCommand.FILE));

        assertThat(rows).hasSize(40);
        assertThat(rows.getFirst()).isEqualTo(new CutoffRow((short) 2019, Category.general, "AIQ", SeatType.qualifying,
                (short) 134, "NTA NEET (UG) 2019 result notice: UR/EWS qualifying range 701-134"));
        CutoffRow reNeet = rows.getLast();
        assertThat(reNeet.year()).isEqualTo((short) 2026);
        assertThat(reNeet.category()).isEqualTo(Category.st);
        assertThat(reNeet.qualifyingMarks()).isEqualTo((short) 177);
        assertThat(reNeet.source()).endsWith("(Re-NEET)");
    }

    @Test
    void aWrongHeaderIsRefusedOnLineOne() throws IOException {
        Path file = write("taxonomy.csv", "code,subject,kind\nPHY,physics,subject\n");

        assertThatThrownBy(() -> TaxonomyCsvReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageStartingWith("taxonomy.csv:1: header must be code,subject,class_level");
    }

    @Test
    void aBadValueIsRefusedWithItsLine() throws IOException {
        Path file = write("taxonomy.csv", String.join("\n", String.join(",", TaxonomyCsvReader.HEADER),
                "PHY,physics,,,subject,Physics,,1,,true",
                "PHY.U01,physics,,PHY,unit,Physics and Measurement,,1,,true",
                "PHY.11.UNITS,physics,13,PHY.U01,chapter,Units and Measurement,,1,135,true", ""));

        assertThatThrownBy(() -> TaxonomyCsvReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessage("taxonomy.csv:4: class_level must be at most 12");
    }

    @Test
    void aSubjectWithAParentAndAUnitWithoutOneAreRefused() throws IOException {
        Path subjectWithParent = write("a.csv", String.join("\n", String.join(",", TaxonomyCsvReader.HEADER),
                "PHY,physics,,CHE,subject,Physics,,1,,true", ""));
        Path unitWithoutParent = write("b.csv", String.join("\n", String.join(",", TaxonomyCsvReader.HEADER),
                "PHY.U01,physics,,,unit,Physics and Measurement,,1,,true", ""));

        assertThatThrownBy(() -> TaxonomyCsvReader.read(subjectWithParent))
                .hasMessage("a.csv:2: a subject has no parent_code and no class_level");
        assertThatThrownBy(() -> TaxonomyCsvReader.read(unitWithoutParent))
                .hasMessage("b.csv:2: a unit needs a parent_code");
    }

    @Test
    void aSelfLoopAndARepeatedEdgeAreRefused() throws IOException {
        Path selfLoop = write("p1.csv", "from_code,to_code\nPHY.11.NLM,PHY.11.NLM\n");
        Path repeated = write("p2.csv", "from_code,to_code\nPHY.11.UNITS,PHY.11.NLM\nPHY.11.UNITS,PHY.11.NLM\n");

        assertThatThrownBy(() -> PrerequisitesCsvReader.read(selfLoop))
                .hasMessage("p1.csv:2: a node cannot be its own prerequisite: PHY.11.NLM");
        assertThatThrownBy(() -> PrerequisitesCsvReader.read(repeated))
                .hasMessage("p2.csv:3: edge PHY.11.UNITS -> PHY.11.NLM appears twice");
    }

    @Test
    void anUnknownEnumValueListsTheAllowedOnes() throws IOException {
        Path file = write("cutoffs.csv", String.join("\n", String.join(",", CutoffsCsvReader.HEADER),
                "2025,obc,AIQ,qualifying,113,notice",
                "2025,pwd,AIQ,qualifying,127,notice", ""));

        assertThatThrownBy(() -> CutoffsCsvReader.read(file))
                .hasMessage("cutoffs.csv:3: category must be one of [general, obc, sc, st, ews], found 'pwd'");
    }

    @Test
    void yamlRefusesUnknownKeysDuplicateSequencesAndWeeksOutsideTheTrack() throws IOException {
        Path unknownKey = write("a.yaml", """
                tracks:
                  - code: dropper
                    name_en: Dropper
                    weeks: 40
                    steps:
                      - {sequence: 1, node_code: PHY.11.UNITS, phase: learn, target_weeks: 1}
                """);
        Path duplicate = write("b.yaml", """
                tracks:
                  - code: dropper
                    name_en: Dropper
                    weeks: 40
                    steps:
                      - {sequence: 1, node_code: PHY.11.UNITS, phase: learn, target_week: 1}
                      - {sequence: 1, node_code: PHY.11.KIN1D, phase: learn, target_week: 2}
                """);
        Path outside = write("c.yaml", """
                tracks:
                  - code: dropper
                    name_en: Dropper
                    weeks: 40
                    steps:
                      - {sequence: 1, node_code: PHY.11.UNITS, phase: learn, target_week: 41}
                """);

        assertThatThrownBy(() -> ArchetypesYamlReader.read(unknownKey))
                .hasMessage("a.yaml: track dropper: unknown step key 'target_weeks'");
        assertThatThrownBy(() -> ArchetypesYamlReader.read(duplicate))
                .hasMessage("b.yaml: track dropper: sequence 1 appears twice");
        assertThatThrownBy(() -> ArchetypesYamlReader.read(outside))
                .hasMessage("c.yaml: track dropper step 1: 'target_week' must be between 1 and 40");
    }

    @Test
    void yamlThatIsNotATrackListIsRefused() throws IOException {
        Path notYaml = write("d.yaml", "tracks: [\n");
        Path noTracks = write("e.yaml", "steps: []\n");

        assertThatThrownBy(() -> ArchetypesYamlReader.read(notYaml)).hasMessageStartingWith("d.yaml: is not valid YAML");
        assertThatThrownBy(() -> ArchetypesYamlReader.read(noTracks)).hasMessage("e.yaml: unknown top-level key 'steps'");
    }

    private Path write(String name, String content) throws IOException {
        Path file = dir.resolve(name);
        Files.writeString(file, content);
        return file;
    }
}

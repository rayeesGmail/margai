package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.TaxonomyLoadReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * The four loads over a database that carries the D4 test seed ({@code test} profile, its own
 * database through the extra {@code importseed} profile): the seed's shapes that the real inputs
 * do not name — the class-scoped units {@code PHY.11.MECH} and {@code CHE.11.PHYS}, the chapters
 * {@code PHY.11.KIN} and {@code CHE.11.MOLE}, three of its four edges, its two synthetic
 * {@code govt_mbbs} cut-offs — survive as reported orphans, the shared codes are updated in place
 * (the seed's {@code dropper} track becomes the real one, its seven steps overwritten), and the
 * union of edges is still acyclic. This is the shape a developer's local database is in until D14
 * replaces the seed (DECISIONS 2026-09-12 D13).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles({"test", "importseed"})
@Import(TestcontainersConfiguration.class)
class CurriculumImportSeedTest {

    @Autowired
    private CurriculumImport imports;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void theSeedsStaleShapesBecomeOrphansAndSharedCodesAreUpdatedInPlace() {
        TaxonomyLoadReport taxonomy = imports.loadTaxonomy(
                TaxonomyCsvReader.read(InputReadersTest.INPUTS.resolve(TaxonomyLoadCommand.FILE)));
        PrerequisiteLoadReport prerequisites = imports.loadPrerequisites(
                PrerequisitesCsvReader.read(InputReadersTest.INPUTS.resolve(TaxonomyPrerequisitesCommand.FILE)));
        BackboneLoadReport backbone = imports.loadBackbone(
                ArchetypesYamlReader.read(InputReadersTest.INPUTS.resolve(BackboneLoadCommand.FILE)));
        CutoffLoadReport cutoffs = imports.loadCutoffs(
                CutoffsCsvReader.read(InputReadersTest.INPUTS.resolve(CutoffsLoadCommand.FILE)));

        assertThat(taxonomy.total()).isEqualTo(516);
        assertThat(taxonomy.inserted()).isEqualTo(508);
        assertThat(taxonomy.orphans()).containsExactly("CHE.11.MOLE", "CHE.11.PHYS", "PHY.11.KIN", "PHY.11.MECH");
        assertThat(jdbc.queryForObject(
                "SELECT p.code FROM syllabus_nodes n JOIN syllabus_nodes p ON p.id = n.parent_id WHERE n.code = 'PHY.11.ROT'",
                String.class)).isEqualTo("PHY.U05");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM syllabus_nodes", Long.class)).isEqualTo(520);

        assertThat(prerequisites.inserted()).isEqualTo(103);
        assertThat(prerequisites.unchanged()).isEqualTo(1);
        assertThat(prerequisites.edgesInDatabase()).isEqualTo(107);
        assertThat(prerequisites.orphanEdges())
                .containsExactly("CHE.11.ATOM -> CHE.11.THERMO", "CHE.11.MOLE -> CHE.11.ATOM", "PHY.11.KIN -> PHY.11.NLM");

        assertThat(backbone.tracksInserted()).isEqualTo(3);
        assertThat(backbone.tracksUpdated()).isEqualTo(1);
        assertThat(backbone.stepsUpdated()).isEqualTo(7);
        assertThat(backbone.stepsInserted()).isEqualTo(744 - 7);
        assertThat(backbone.stepsRemoved()).isZero();
        // The seed's stale chapters PHY.11.KIN and CHE.11.MOLE are chapters no real track learns.
        assertThat(backbone.chaptersInNoTrack()).containsExactly("CHE.11.MOLE", "PHY.11.KIN");
        assertThat(jdbc.queryForObject("SELECT name_en FROM archetype_tracks WHERE code = 'dropper'", String.class))
                .isEqualTo("Dropper (1st repeat)");

        assertThat(cutoffs.inserted()).isEqualTo(39);
        assertThat(cutoffs.updated()).isEqualTo(1);
        assertThat(cutoffs.orphans()).containsExactly("2025 general AIQ govt_mbbs", "2025 obc AIQ govt_mbbs");
        assertThat(jdbc.queryForObject(
                "SELECT qualifying_marks FROM cutoffs WHERE year = 2025 AND category = 'general' AND seat_type = 'qualifying'",
                Integer.class)).isEqualTo(144);
    }
}

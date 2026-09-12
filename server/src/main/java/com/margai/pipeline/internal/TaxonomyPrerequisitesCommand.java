package com.margai.pipeline.internal;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import java.nio.file.Path;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/** {@code taxonomy prerequisites}: {@code prerequisites.csv} into {@code syllabus_prerequisites} (TECH_PLAN §6.3). */
@Component
@Profile("pipeline")
@Command(name = "prerequisites", mixinStandardHelpOptions = true,
        description = "Upsert prerequisites.csv into syllabus_prerequisites by (from, to); fails the run on a cycle.")
class TaxonomyPrerequisitesCommand extends InputFileCommand {

    static final String FILE = "prerequisites.csv";

    private final CurriculumImport imports;

    TaxonomyPrerequisitesCommand(CurriculumImport imports, Reports reports) {
        super(reports);
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    void run(Path inputFile, Report report) {
        List<PrerequisiteRow> rows = PrerequisitesCsvReader.read(inputFile);
        report.read(rows.size() + " edges");
        PrerequisiteLoadReport result = imports.loadPrerequisites(rows);
        report.section("syllabus_prerequisites")
                .table(List.of("inserted", "already present", "edges in the database", "nodes with edges", "cycle"),
                        List.of(List.of(String.valueOf(result.inserted()), String.valueOf(result.unchanged()),
                                String.valueOf(result.edgesInDatabase()), String.valueOf(result.nodesWithEdges()), "none")));
        report.section("orphan edges (in the database, not in the file)").list(result.orphanEdges());
    }
}

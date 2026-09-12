package com.margai.pipeline.internal;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/** {@code taxonomy load}: {@code taxonomy.csv} into {@code syllabus_nodes} (TECH_PLAN §6.3). */
@Component
@Profile("pipeline")
@Command(name = "load", mixinStandardHelpOptions = true,
        description = "Upsert taxonomy.csv into syllabus_nodes by code; reports nodes per subject and kind, and orphans.")
class TaxonomyLoadCommand extends InputFileCommand {

    static final String FILE = "taxonomy.csv";

    private final CurriculumImport imports;

    TaxonomyLoadCommand(CurriculumImport imports) {
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    int run(Path inputFile) {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(inputFile);
        print(FILE + ": " + rows.size() + " nodes read");
        TaxonomyLoadReport report = imports.loadTaxonomy(rows);
        print("syllabus_nodes: " + report.inserted() + " inserted, " + report.updated() + " updated, "
                + report.unchanged() + " unchanged");
        report.counts().forEach((subject, kinds) -> print("  " + subject + ": " + kinds.entrySet().stream()
                .map(entry -> entry.getValue() + " " + entry.getKey())
                .collect(Collectors.joining(", "))));
        print("orphans (in the database, not in the file): " + listOrNone(report.orphans()));
        return EXIT_OK;
    }
}

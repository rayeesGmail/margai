package com.margai.pipeline.internal;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/** {@code cutoffs load}: {@code cutoffs.csv} into {@code cutoffs} (TECH_PLAN §6.3). */
@Component
@Profile("pipeline")
@Command(name = "load", mixinStandardHelpOptions = true,
        description = "Upsert cutoffs.csv into cutoffs by (year, category, quota_scope, seat_type).")
class CutoffsLoadCommand extends InputFileCommand {

    static final String FILE = "cutoffs.csv";

    private final CurriculumImport imports;

    CutoffsLoadCommand(CurriculumImport imports) {
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    int run(Path inputFile) {
        List<CutoffRow> rows = CutoffsCsvReader.read(inputFile);
        print(FILE + ": " + rows.size() + " rows read");
        CutoffLoadReport report = imports.loadCutoffs(rows);
        print("cutoffs: " + report.inserted() + " inserted, " + report.updated() + " updated, " + report.unchanged() + " unchanged");
        print("  rows per year: " + report.rowsPerYear().entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .collect(Collectors.joining(", ")));
        print("orphans (in the database, not in the file): " + listOrNone(report.orphans()));
        return EXIT_OK;
    }
}

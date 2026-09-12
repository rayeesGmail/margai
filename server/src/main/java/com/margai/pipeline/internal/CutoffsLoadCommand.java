package com.margai.pipeline.internal;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import java.nio.file.Path;
import java.util.List;
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

    CutoffsLoadCommand(CurriculumImport imports, Reports reports) {
        super(reports);
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    void run(Path inputFile, Report report) {
        List<CutoffRow> rows = CutoffsCsvReader.read(inputFile);
        report.read(rows.size() + " rows");
        CutoffLoadReport result = imports.loadCutoffs(rows);
        report.section("cutoffs")
                .table(List.of("inserted", "updated", "unchanged"), List.of(List.of(
                        String.valueOf(result.inserted()), String.valueOf(result.updated()), String.valueOf(result.unchanged()))));
        report.section("rows per year")
                .table(List.of("year", "rows"), result.rowsPerYear().entrySet().stream()
                        .map(entry -> List.of(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())))
                        .toList());
        report.section("orphans (in the database, not in the file)").list(result.orphans());
    }
}

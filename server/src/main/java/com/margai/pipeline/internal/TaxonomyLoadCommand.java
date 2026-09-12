package com.margai.pipeline.internal;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    TaxonomyLoadCommand(CurriculumImport imports, Reports reports) {
        super(reports);
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    void run(Path inputFile, Report report) {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(inputFile);
        report.read(rows.size() + " nodes");
        TaxonomyLoadReport result = imports.loadTaxonomy(rows);
        report.section("syllabus_nodes")
                .table(List.of("inserted", "updated", "unchanged"), List.of(List.of(
                        String.valueOf(result.inserted()), String.valueOf(result.updated()), String.valueOf(result.unchanged()))));
        List<String> header = new ArrayList<>(List.of("subject"));
        Arrays.stream(NodeKind.values()).map(NodeKind::name).forEach(header::add);
        List<List<String>> perSubject = new ArrayList<>();
        result.counts().forEach((subject, kinds) -> {
            List<String> cells = new ArrayList<>(List.of(subject.name()));
            for (NodeKind kind : NodeKind.values()) {
                cells.add(String.valueOf(kinds.getOrDefault(kind, 0)));
            }
            perSubject.add(cells);
        });
        report.section("nodes per subject and kind").table(header, perSubject);
        report.section("orphans (in the database, not in the file)").list(result.orphans());
    }
}

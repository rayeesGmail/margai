package com.margai.pipeline.internal;

import com.margai.curriculum.api.SyllabusNodeRow;
import java.nio.file.Path;
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

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    int run(Path inputFile) {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(inputFile);
        print(FILE + ": " + rows.size() + " nodes read");
        return EXIT_OK;
    }
}

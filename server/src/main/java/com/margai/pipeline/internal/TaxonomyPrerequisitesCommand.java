package com.margai.pipeline.internal;

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

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    int run(Path inputFile) {
        List<PrerequisiteRow> rows = PrerequisitesCsvReader.read(inputFile);
        print(FILE + ": " + rows.size() + " edges read");
        return EXIT_OK;
    }
}

package com.margai.pipeline.internal;

import java.nio.file.Path;
import java.util.List;
import picocli.CommandLine.Option;

/**
 * A {@code pyq} command over one paper of the founder's register, {@code pyq-papers.yaml} (D19):
 * the register is read whole — so a malformed entry anywhere fails every command, not only the
 * one that names it — and the paper {@code --paper} names is handed on.
 */
abstract class PyqPaperCommand extends InputFileCommand {

    static final String FILE = "pyq-papers.yaml";

    @Option(names = "--paper", paramLabel = "CODE", required = true,
            description = "The register's id of the paper, e.g. neet-2025-45.")
    String paperCode;

    PyqPaperCommand(Reports reports) {
        super(reports);
    }

    @Override
    final String inputFileName() {
        return FILE;
    }

    @Override
    final void run(Path inputFile, Report report) {
        List<PaperDefinition> papers = PyqPapersYamlReader.read(inputFile);
        PaperDefinition paper = papers.stream().filter(candidate -> candidate.code().equals(paperCode)).findFirst()
                .orElseThrow(() -> new InputFormatException(inputFile, 0, "has no paper '" + paperCode + "' — it registers "
                        + papers.stream().map(PaperDefinition::code).toList()));
        report.line("paper: " + paper.code() + " (" + paper.row().exam() + " " + paper.row().year() + ", booklet code "
                + paper.row().paperCode() + ", " + paper.row().examDate() + ")");
        run(paper, report);
    }

    abstract void run(PaperDefinition paper, Report report);

    /** The approved file of a paper: {@code papers/<code>.json} under {@code --inputs} (TECH_PLAN §6.2). */
    Path approvedFile(PyqProperties pyq, PaperDefinition paper) {
        return io.inputs.resolve(pyq.papersDirectory()).resolve(paper.code() + ".json");
    }
}

package com.margai.pipeline.internal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

    /** The register's paper codes, which the tag rulings file may name and no other. */
    private Set<String> registered = Set.of();

    @Override
    final void run(Path inputFile, Report report) {
        List<PaperDefinition> papers = PyqPapersYamlReader.read(inputFile);
        registered = papers.stream().map(PaperDefinition::code).collect(Collectors.toUnmodifiableSet());
        report.read(papers.size() + " paper(s) in the register");
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

    /**
     * The founder's tag rulings for the paper, by question ({@code pyq-tag-rulings.yaml} under {@code --inputs},
     * DECISIONS 2026-10-21), named in the report with the file's sha256; none when the file is absent. Refused when
     * the file names a paper the register does not, or when the approved file is not the one they were made against.
     */
    Map<Short, PyqTagRuling> rulings(PyqProperties pyq, PaperDefinition paper, Report report) {
        Path file = io.input(PyqTagRulingsYamlReader.FILE);
        if (!Files.isRegularFile(file)) {
            report.line("no " + PyqTagRulingsYamlReader.FILE + " under the inputs: no tag rulings");
            return Map.of();
        }
        Map<Short, PyqTagRuling> rulings = PyqTagRulingsYamlReader.forPaper(file, paper, registered,
                approvedFile(pyq, paper));
        report.line("tag rulings: " + file.normalize() + " sha256 " + Report.sha256(file) + " (" + rulings.size()
                + " for this paper)");
        return rulings;
    }
}

package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;
import java.nio.file.Path;
import java.util.List;
import picocli.CommandLine.Option;

/**
 * A leaf command scoped to one book and one edition ({@code ncert render|extract|load}): it reads
 * {@code books.yaml} like every other command reads its input file, then works on the book
 * {@code --book} names. A book the file does not carry, or an edition it has no source prefix
 * for, fails the run rather than doing nothing quietly.
 */
abstract class NcertBookCommand extends InputFileCommand {

    @Option(names = "--book", paramLabel = "CODE", required = true,
            description = "The book code from books.yaml (bio11, phy11-part1, …).")
    String book;

    @Option(names = "--lang", paramLabel = "LANG", defaultValue = "en",
            description = "Which edition: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE}).")
    BookLanguage language;

    @Option(names = "--chapters", paramLabel = "N[,N…]", split = ",",
            description = "Only these chapter numbers (default: every chapter of the book).")
    List<Short> chapters;

    NcertBookCommand(Reports reports) {
        super(reports);
    }

    @Override
    final String inputFileName() {
        return NcertRegisterCommand.FILE;
    }

    @Override
    final void run(Path inputFile, Report report) {
        BookDefinition definition = BooksYamlReader.read(inputFile).stream()
                .filter(candidate -> candidate.code().equals(book))
                .findFirst()
                .orElseThrow(() -> new InputFormatException(inputFile, 0,
                        "does not carry a book with code '" + book + "'"));
        if (!definition.has(language)) {
            throw new InputFormatException(inputFile, 0,
                    "book '" + book + "' has no " + language + " edition");
        }
        List<BookDefinition.Chapter> selected = select(inputFile, definition);
        report.read(selected.size() + " chapters of " + book + " (" + language + ")");
        run(definition, selected, report);
    }

    /** The command's work over the selected chapters of one edition. */
    abstract void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report);

    private List<BookDefinition.Chapter> select(Path inputFile, BookDefinition definition) {
        if (chapters == null || chapters.isEmpty()) {
            return definition.chapters();
        }
        List<BookDefinition.Chapter> selected = definition.chapters().stream()
                .filter(chapter -> chapters.contains(chapter.no()))
                .toList();
        if (selected.size() != chapters.stream().distinct().count()) {
            throw new InputFormatException(inputFile, 0,
                    "book '" + book + "' does not have every chapter of " + chapters);
        }
        return selected;
    }
}

package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.NcertBookRow;
import com.margai.curriculum.api.NcertRegisterReport;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/** {@code ncert register}: {@code books.yaml} into {@code ncert_books} (TECH_PLAN §6.3). */
@Component
@Profile("pipeline")
@Command(name = "register", mixinStandardHelpOptions = true,
        description = "Upsert books.yaml into ncert_books by code; reports the books and their chapter counts.")
class NcertRegisterCommand extends InputFileCommand {

    static final String FILE = "books.yaml";

    private final CurriculumImport imports;

    NcertRegisterCommand(CurriculumImport imports, Reports reports) {
        super(reports);
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    void run(Path inputFile, Report report) {
        List<BookDefinition> books = BooksYamlReader.read(inputFile);
        report.read(books.size() + " books");
        NcertRegisterReport result = imports.registerBooks(books.stream().map(BookDefinition::row).toList());
        report.section("ncert_books")
                .table(List.of("inserted", "updated", "unchanged"), List.of(List.of(
                        String.valueOf(result.inserted()), String.valueOf(result.updated()),
                        String.valueOf(result.unchanged()))));

        List<List<String>> rows = new ArrayList<>();
        for (BookDefinition book : books) {
            NcertBookRow row = book.row();
            rows.add(List.of(row.code(), row.subject().name(), String.valueOf(row.classLevel()),
                    row.part() == null ? "—" : String.valueOf(row.part()),
                    book.chapters().getFirst().no() + "–" + book.chapters().getLast().no(),
                    String.valueOf(book.chapters().size()),
                    editions(book)));
        }
        report.section("books")
                .table(List.of("code", "subject", "class", "part", "chapters", "files", "editions"), rows);
        report.section("books in the database that books.yaml no longer names").list(result.orphans());
    }

    private static String editions(BookDefinition book) {
        List<String> editions = new ArrayList<>();
        for (BookLanguage language : BookLanguage.values()) {
            if (book.has(language)) {
                editions.add(language.name());
            }
        }
        return String.join(" + ", editions);
    }
}

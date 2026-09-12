package com.margai.curriculum.internal;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NcertBookRow;
import com.margai.curriculum.api.NcertRegisterReport;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * {@code ncert register} (TECH_PLAN §6.3): upsert on {@code ncert_books.code}. Runs inside
 * {@link CurriculumImportService}'s transaction. Two books may not claim the same code, and no
 * two may claim the same (subject, class level, part) — that pair is what a student's "Class 11
 * Physics Part-I" means, and two books answering to it would make an anchor ambiguous.
 */
@Component
class NcertBookImporter {

    private final NcertBookRepository books;

    NcertBookImporter(NcertBookRepository books) {
        this.books = books;
    }

    void recordRenderedPages(String bookCode, BookLanguage language, int pages) {
        NcertBook book = books.findByCode(bookCode)
                .orElseThrow(() -> new CurriculumImportException(
                        "book '" + bookCode + "' is not registered — run `ncert register` first"));
        book.applyRenderedPages(language, pages);
        books.flush();
    }

    Integer renderedPages(String bookCode, BookLanguage language) {
        return books.findByCode(bookCode)
                .orElseThrow(() -> new CurriculumImportException(
                        "book '" + bookCode + "' is not registered — run `ncert register` first"))
                .getPages(language);
    }

    NcertRegisterReport register(List<NcertBookRow> rows) {
        Map<String, NcertBook> existing = new HashMap<>();
        books.findAll().forEach(book -> existing.put(book.getCode(), book));

        int inserted = 0;
        int updated = 0;
        int unchanged = 0;
        Set<String> codesInFile = new HashSet<>();
        Set<String> editionsInFile = new HashSet<>();
        for (NcertBookRow row : rows) {
            if (!codesInFile.add(row.code())) {
                throw new CurriculumImportException("the book code '" + row.code() + "' appears twice in the file");
            }
            String edition = row.subject() + " class " + row.classLevel() + " part " + row.part();
            if (!editionsInFile.add(edition)) {
                throw new CurriculumImportException("two books claim to be " + edition);
            }
            NcertBook book = existing.get(row.code());
            if (book == null) {
                books.save(new NcertBook(row));
                inserted++;
            } else if (book.apply(row)) {
                updated++;
            } else {
                unchanged++;
            }
        }
        books.flush();

        List<String> orphans = existing.keySet().stream().filter(code -> !codesInFile.contains(code)).sorted().toList();
        return new NcertRegisterReport(inserted, updated, unchanged, orphans);
    }
}

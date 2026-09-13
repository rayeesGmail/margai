package com.margai.curriculum.internal;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 * {@code ncert load} (TECH_PLAN §6.3): upsert on the paragraph address
 * {@code (book_id, chapter_no, section, para_no)}. Runs inside {@link CurriculumImportService}'s
 * transaction, so a file that contradicts itself leaves the book exactly as it was.
 *
 * <p>The text lands in the column of the edition being loaded, so D16's Hindi pass writes
 * {@code text_hi} beside an English paragraph already at that address rather than a second row.
 */
@Component
class NcertParagraphImporter {

    private final NcertBookRepository books;
    private final NcertParagraphRepository paragraphs;

    NcertParagraphImporter(NcertBookRepository books, NcertParagraphRepository paragraphs) {
        this.books = books;
        this.paragraphs = paragraphs;
    }

    NcertLoadReport load(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
        NcertBook book = books.findByCode(bookCode)
                .orElseThrow(() -> new CurriculumImportException(
                        "book '" + bookCode + "' is not registered — run `ncert register` first"));

        Map<String, NcertParagraph> existing = new HashMap<>();
        paragraphs.findByBookId(book.getId()).forEach(paragraph -> existing.put(paragraph.address(), paragraph));

        int inserted = 0;
        int updated = 0;
        int unchanged = 0;
        Set<String> inFile = new HashSet<>();
        Map<Short, Integer> perChapter = new TreeMap<>();
        for (NcertParagraphRow row : rows) {
            String address = row.address();
            if (!inFile.add(address)) {
                throw new CurriculumImportException(
                        "the address " + address + " appears twice in the extraction of " + bookCode);
            }
            perChapter.merge(row.chapterNo(), 1, Integer::sum);
            NcertParagraph paragraph = existing.get(address);
            if (paragraph == null) {
                paragraphs.save(new NcertParagraph(book.getId(), row, language));
                inserted++;
            } else if (paragraph.apply(row, language)) {
                updated++;
            } else {
                unchanged++;
            }
        }
        paragraphs.flush();

        // Only within the chapters this load carried. A `--chapters 7` load says nothing about
        // chapter 3's rows, and reporting them as "no longer carried" listed the whole book —
        // 600 lines, every one wrong — the first time a subset was loaded into a full one (D14).
        List<String> orphans = existing.values().stream()
                .filter(paragraph -> perChapter.containsKey(paragraph.getChapterNo()))
                .map(NcertParagraph::address)
                .filter(address -> !inFile.contains(address))
                .sorted().toList();
        return new NcertLoadReport(inserted, updated, unchanged, perChapter, orphans);
    }
}

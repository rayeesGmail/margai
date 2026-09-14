package com.margai.curriculum.internal;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import java.util.Comparator;
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
        // …and only rows that are this edition's alone: the Hindi extraction of a section may cut
        // fewer paragraphs than the English one (D16 aligns them by section and order), and a row
        // that still carries the other edition's text is that edition's row, not an orphan.
        BookLanguage other = language == BookLanguage.en ? BookLanguage.hi : BookLanguage.en;
        List<NcertParagraph> orphans = existing.values().stream()
                .filter(paragraph -> perChapter.containsKey(paragraph.getChapterNo()))
                .filter(paragraph -> !inFile.contains(paragraph.address()))
                .filter(paragraph -> paragraph.text(other) == null)
                .sorted(Comparator.comparing(NcertParagraph::address))
                .toList();
        // Deleted, not kept (DECISIONS 2026-09-14): a re-extraction cuts paragraphs differently and
        // since v3 the loader numbers them, so the rows a run no longer carries are rows no run
        // produced — 85 of them sat beside phy11-part1's canonical 1,017 on 2026-09-14, sampleable
        // by the ✅ and embeddable by D17. The one thing a load may not delete is a row something
        // already points at: an anchored paragraph (D23's node_id; D17's embedding joins this check
        // when the entity gains the column) makes the re-extraction a migration, not a load.
        List<String> anchored = orphans.stream()
                .filter(paragraph -> paragraph.getNodeId() != null)
                .map(NcertParagraph::address).toList();
        if (!anchored.isEmpty()) {
            throw new CurriculumImportException(anchored.size() + " paragraph(s) of " + bookCode
                    + " that this extraction no longer carries are anchored and cannot be deleted by a load: "
                    + String.join(", ", anchored) + " — re-extracting an anchored chapter is a corpus event that "
                    + "re-anchors what it moved (D17 guard), not a load; nothing was written");
        }
        paragraphs.deleteAll(orphans);
        return new NcertLoadReport(inserted, updated, unchanged, perChapter,
                orphans.stream().map(NcertParagraph::address).toList());
    }
}

package com.margai.curriculum.internal;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.NcertVerificationRow;
import com.margai.curriculum.api.ParagraphVerification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
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

    /** Chapter, then the printed section compared number by number (7.2 before 7.10), then ¶. */
    private static final Comparator<NcertParagraph> READING_ORDER = Comparator
            .comparing(NcertParagraph::getChapterNo)
            .thenComparing(NcertParagraph::getSection, NcertParagraphImporter::compareSections)
            .thenComparing(NcertParagraph::getParaNo);

    private final NcertBookRepository books;
    private final NcertParagraphRepository paragraphs;
    private final NcertEmbeddings embeddings;

    NcertParagraphImporter(NcertBookRepository books, NcertParagraphRepository paragraphs,
            NcertEmbeddings embeddings) {
        this.books = books;
        this.paragraphs = paragraphs;
        this.embeddings = embeddings;
    }

    NcertLoadReport load(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
        NcertBook book = book(bookCode);

        Map<String, NcertParagraph> existing = new HashMap<>();
        paragraphs.findByBookId(book.getId()).forEach(paragraph -> existing.put(paragraph.address(), paragraph));

        int inserted = 0;
        int updated = 0;
        int unchanged = 0;
        Set<String> inFile = new HashSet<>();
        Map<Short, Integer> perChapter = new TreeMap<>();
        // Rows whose English text this load actually rewrote. Their vectors describe words the row
        // no longer carries, so they are dropped below and the next `ncert embed` re-reads them
        // (§4.9). Deliberately not "rows that changed": a load that only moves a figure reference
        // or re-stamps provenance leaves the words alone, and re-embedding for that would be paid
        // work for an identical vector.
        List<UUID> rewritten = new ArrayList<>();
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
            } else {
                boolean textRewritten = !Objects.equals(paragraph.text(language), row.text());
                if (paragraph.apply(row, language)) {
                    updated++;
                    if (textRewritten && language == BookLanguage.en) {
                        rewritten.add(paragraph.getId());
                    }
                } else {
                    unchanged++;
                }
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
        // already points at: an anchored paragraph (D23's node_id) makes the re-extraction a
        // migration, not a load.
        //
        // An embedding is NOT such a thing, and this is the correction of a note left here at D14
        // ("D17's embedding joins this check"). A vector is a property of the row, not a pointer to
        // it: refusing to delete an embedded orphan would refuse every re-load of an embedded book,
        // which is every load after D15. Deleting one loses a vector that describes words nobody
        // carries any more — the right outcome. It is counted and named instead, because a load
        // quietly throwing away paid work should be visible in the report.
        List<String> anchored = orphans.stream()
                .filter(paragraph -> paragraph.getNodeId() != null)
                .map(NcertParagraph::address).toList();
        if (!anchored.isEmpty()) {
            throw new CurriculumImportException(anchored.size() + " paragraph(s) of " + bookCode
                    + " that this extraction no longer carries are anchored and cannot be deleted by a load: "
                    + String.join(", ", anchored) + " — re-extracting an anchored chapter is a corpus event that "
                    + "re-anchors what it moved (D17 guard), not a load; nothing was written");
        }
        long embeddedOrphans = embeddings.embeddedAmong(orphans.stream().map(NcertParagraph::getId).toList());
        paragraphs.deleteAll(orphans);
        int cleared = embeddings.clearFor(rewritten);
        return new NcertLoadReport(inserted, updated, unchanged, perChapter,
                orphans.stream().map(NcertParagraph::address).toList(), cleared, (int) embeddedOrphans);
    }

    List<NcertParagraphRow> paragraphs(String bookCode, BookLanguage language, Collection<Short> chapters) {
        NcertBook book = book(bookCode);
        return paragraphs.findByBookId(book.getId()).stream()
                .filter(paragraph -> chapters.contains(paragraph.getChapterNo()))
                .filter(paragraph -> paragraph.text(language) != null)
                .sorted(READING_ORDER)
                .map(paragraph -> paragraph.row(language))
                .toList();
    }

    /**
     * Every verdict is checked before any is written, so a refusal names all of them at once and the
     * transaction has nothing to roll back but the reading.
     */
    int recordVerifications(String bookCode, BookLanguage language, List<NcertVerificationRow> verdicts) {
        NcertBook book = book(bookCode);
        Map<String, NcertParagraph> existing = new HashMap<>();
        paragraphs.findByBookId(book.getId()).forEach(paragraph -> existing.put(paragraph.address(), paragraph));

        List<String> refusals = new ArrayList<>();
        for (NcertVerificationRow verdict : verdicts) {
            NcertParagraph paragraph = existing.get(verdict.address());
            String text = paragraph == null ? null : paragraph.text(language);
            if (text == null) {
                refusals.add(verdict.address() + " is not in " + bookCode + " (" + language + ")");
            } else if (!ParagraphVerification.sha256(text).equals(verdict.verification().textSha256())) {
                refusals.add(verdict.address() + ": the text has changed since it was verified — "
                        + "run `ncert verify --read-pages` again for its page");
            }
        }
        if (!refusals.isEmpty()) {
            throw new CurriculumImportException(refusals.size() + " verdict(s) cannot be recorded, nothing was written:\n  "
                    + String.join("\n  ", refusals));
        }
        verdicts.forEach(verdict -> existing.get(verdict.address()).recordVerification(language, verdict.verification()));
        paragraphs.flush();
        return verdicts.size();
    }

    private NcertBook book(String bookCode) {
        return books.findByCode(bookCode)
                .orElseThrow(() -> new CurriculumImportException(
                        "book '" + bookCode + "' is not registered — run `ncert register` first"));
    }

    private static int compareSections(String left, String right) {
        String[] a = left.split("\\.");
        String[] b = right.split("\\.");
        for (int index = 0; index < Math.min(a.length, b.length); index++) {
            int compared = Integer.compare(Integer.parseInt(a[index]), Integer.parseInt(b[index]));
            if (compared != 0) {
                return compared;
            }
        }
        return Integer.compare(a.length, b.length);
    }
}

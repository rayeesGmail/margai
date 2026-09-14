package com.margai.pipeline.internal;

import com.margai.curriculum.api.NcertParagraphRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * The free half of {@code ncert verify} (D15, DECISIONS 2026-09-14 "the pair"): a chapter's loaded rows
 * held against its print's typography ({@link PdfLayout}). On a frozen prompt the character layer is
 * stable and the segmentation is not, so these are the checks aimed at segmentation — and at the one
 * field of a row that is a list of labels:
 *
 * <ul>
 * <li><b>starts</b> — per page, the rows that begin there against the paragraphs the print begins
 * there, matched by opening words, naming whatever does not meet on either side;</li>
 * <li><b>join</b> — per page boundary, a row running across it where the next page opens a new
 * paragraph, or a row starting fresh where the next page's first line continues the previous one;</li>
 * <li><b>figure</b> — per row, a figure_refs label the paragraph never mentions, a mention figure_refs
 * does not carry, and a label no caption in the chapter prints.</li>
 * </ul>
 *
 * <p>The typography rules are measured guesses, so a flag routes the founder's eye and never refuses
 * anything. A page top that is a display or a figure cannot say whether it continues; it is counted
 * as undecided, never flagged.
 */
final class LayoutChecks {

    /** How many words of a row's opening a flag quotes. */
    private static final int QUOTED_WORDS = 7;

    private LayoutChecks() {
    }

    enum Kind {
        starts,
        join,
        figure
    }

    /**
     * @param address the row the flag is about, or null for a page-level flag
     */
    record Flag(Kind kind, int page, String address, String message) {
    }

    record Result(List<Flag> flags, int pagesCompared, int boundariesJudged, int boundariesUndecided) {

        Result {
            flags = List.copyOf(flags);
        }
    }

    /**
     * @param rows   one chapter's rows in reading order, each divisible by page ({@link ParagraphParts})
     * @param shapes that chapter's PDF, page by page
     */
    static Result check(List<NcertParagraphRow> rows, List<PdfLayout.PageShape> shapes) {
        if (rows.isEmpty()) {
            return new Result(List.of(), 0, 0, 0);
        }
        short chapter = rows.getFirst().chapterNo();
        List<Flag> flags = new ArrayList<>();
        TreeSet<Integer> textPages = new TreeSet<>();
        rows.forEach(row -> textPages.addAll(row.extraction().pages()));

        int compared = 0;
        for (int page : textPages) {
            if (page > shapes.size()) {
                flags.add(new Flag(Kind.starts, page, null, "ch " + chapter + " p" + page + ": the chapter's PDF has "
                        + shapes.size() + " pages — nothing to compare"));
                continue;
            }
            compared++;
            starts(chapter, page, rows, shapes.get(page - 1)).ifPresent(flags::add);
        }

        int judged = 0;
        int undecided = 0;
        Integer previous = null;
        for (int page : textPages) {
            if (previous != null && page <= shapes.size()) {
                PdfLayout.PageShape shape = shapes.get(page - 1);
                if (shape.top() == PdfLayout.Top.unknown) {
                    undecided++;
                } else {
                    judged++;
                    join(chapter, previous, page, rows, shape).ifPresent(flags::add);
                }
            }
            previous = page;
        }

        Set<String> captions = new LinkedHashSet<>();
        shapes.forEach(shape -> captions.addAll(shape.captions()));
        for (NcertParagraphRow row : rows) {
            flags.addAll(figures(chapter, row, captions));
        }
        return new Result(flags, compared, judged, undecided);
    }

    private static Optional<Flag> starts(short chapter, int page, List<NcertParagraphRow> rows, PdfLayout.PageShape shape) {
        List<NcertParagraphRow> starting = new ArrayList<>();
        List<String> openings = new ArrayList<>();
        for (NcertParagraphRow row : rows) {
            ParagraphParts.Part first = ParagraphParts.of(row).getFirst();
            if (first.page() == page) {
                starting.add(row);
                openings.add(first.text());
            }
        }
        List<String> printed = new ArrayList<>(shape.starts());
        List<String> unmatchedRows = new ArrayList<>();
        for (int index = 0; index < starting.size(); index++) {
            String opening = openings.get(index);
            Optional<String> match = printed.stream().filter(start -> ParagraphParts.sameOpening(start, opening)).findFirst();
            if (match.isPresent()) {
                printed.remove(match.get());
            } else {
                NcertParagraphRow row = starting.get(index);
                unmatchedRows.add("§" + row.section() + " ¶" + row.paraNo() + " \"" + quote(opening) + "\"");
            }
        }
        if (unmatchedRows.isEmpty() && printed.isEmpty()) {
            return Optional.empty();
        }
        StringBuilder message = new StringBuilder("ch " + chapter + " p" + page + ": " + starting.size()
                + " rows start here, the print starts " + shape.starts().size() + " paragraphs");
        if (!unmatchedRows.isEmpty()) {
            message.append(" — rows the print does not start: ").append(String.join(" · ", unmatchedRows));
        }
        if (!printed.isEmpty()) {
            message.append(unmatchedRows.isEmpty() ? " — " : " · ").append("printed starts no row begins with: ")
                    .append(String.join(" · ", printed.stream().map(start -> "\"" + start + "\"").toList()));
        }
        return Optional.of(new Flag(Kind.starts, page, null, message.toString()));
    }

    private static Optional<Flag> join(short chapter, int before, int page, List<NcertParagraphRow> rows,
            PdfLayout.PageShape shape) {
        NcertParagraphRow joined = null;
        NcertParagraphRow freshStart = null;
        for (NcertParagraphRow row : rows) {
            List<Integer> pages = row.extraction().pages();
            int at = pages.indexOf(page);
            if (at > 0 && pages.get(at - 1) == before) {
                joined = row;
            }
            if (at == 0 && freshStart == null) {
                freshStart = row;
            }
        }
        if (shape.top() == PdfLayout.Top.starts && joined != null) {
            return Optional.of(new Flag(Kind.join, page, joined.address(), joined.address() + " runs from p" + before
                    + " onto p" + page + ", but p" + page + " opens a new paragraph: \"" + shape.topLine() + "\""));
        }
        if (shape.top() == PdfLayout.Top.continues && joined == null && freshStart != null) {
            return Optional.of(new Flag(Kind.join, page, freshStart.address(), freshStart.address()
                    + " starts a paragraph at the top of p" + page + ", but the print continues p" + before + "'s: \""
                    + shape.topLine() + "\""));
        }
        return Optional.empty();
    }

    private static List<Flag> figures(short chapter, NcertParagraphRow row, Set<String> captions) {
        int page = row.extraction().pages().getFirst();
        List<FigureLabels.Label> mentions = FigureLabels.in(row.text());
        List<Flag> flags = new ArrayList<>();
        List<FigureLabels.Label> refs = new ArrayList<>();
        for (String ref : row.figureRefs()) {
            Optional<FigureLabels.Label> label = FigureLabels.of(ref);
            if (label.isEmpty()) {
                continue;
            }
            refs.add(label.get());
            boolean mentioned = mentions.stream().anyMatch(mention -> mention.base().equals(label.get().base())
                    && (mention.part() == null || label.get().part() == null || mention.part().equals(label.get().part())));
            if (!mentioned) {
                flags.add(new Flag(Kind.figure, page, row.address(), row.address() + ": figure_refs carries \"" + ref
                        + "\", which the paragraph never mentions"));
            }
            if (!captions.isEmpty() && !captions.contains(label.get().base())) {
                flags.add(new Flag(Kind.figure, page, row.address(), row.address() + ": figure_refs carries \"" + ref
                        + "\", which no caption in chapter " + chapter + " prints"));
            }
        }
        Set<String> named = new LinkedHashSet<>();
        for (FigureLabels.Label mention : mentions) {
            if (refs.stream().noneMatch(ref -> ref.base().equals(mention.base())) && named.add(mention.base())) {
                flags.add(new Flag(Kind.figure, page, row.address(), row.address() + ": the paragraph mentions "
                        + mention.base() + ", which figure_refs does not carry"));
            }
        }
        return flags;
    }

    private static String quote(String text) {
        String[] words = text.strip().split("\\s+");
        return String.join(" ", Arrays.copyOf(words, Math.min(words.length, QUOTED_WORDS)));
    }
}

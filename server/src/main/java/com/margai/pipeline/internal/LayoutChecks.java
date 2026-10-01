package com.margai.pipeline.internal;

import com.margai.curriculum.api.NcertParagraphRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /** An equation's printed number, as the print and the transcription both write it. */
    private static final Pattern EQUATION_NUMBER = Pattern.compile("\\(\\d{1,2}\\.\\d{1,3}\\)");

    private LayoutChecks() {
    }

    enum Kind {
        starts,
        join,
        figure,
        equation
    }

    /**
     * @param address the row the flag is about, or null for a page-level flag
     */
    record Flag(Kind kind, int page, String address, String message) {
    }

    /**
     * @param paired the printed starts matched to a row only after allowing for math the text layer
     *               dropped — not flags, but the one place a page can be quiet because of a guess, so
     *               they are named for the founder to spot-check
     */
    record Result(List<Flag> flags, List<String> paired, int pagesCompared, int boundariesJudged,
            int boundariesUndecided) {

        Result {
            flags = List.copyOf(flags);
            paired = List.copyOf(paired);
        }
    }

    /**
     * @param rows   one chapter's rows in reading order, each divisible by page ({@link ParagraphParts})
     * @param shapes that chapter's PDF, page by page
     */
    static Result check(List<NcertParagraphRow> rows, List<PdfLayout.PageShape> shapes) {
        if (rows.isEmpty()) {
            return new Result(List.of(), List.of(), 0, 0, 0);
        }
        short chapter = rows.getFirst().chapterNo();
        List<Flag> flags = new ArrayList<>();
        List<String> paired = new ArrayList<>();
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
            Starts starts = starts(chapter, page, rows, shapes.get(page - 1));
            starts.flag().ifPresent(flags::add);
            paired.addAll(starts.paired());
            flags.addAll(equations(chapter, page, rows, shapes.get(page - 1)));
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
                    PdfLayout.PageShape before = previous <= shapes.size() ? shapes.get(previous - 1) : null;
                    join(chapter, previous, page, rows, shape, before).ifPresent(flags::add);
                }
            }
            previous = page;
        }

        Set<String> captions = new LinkedHashSet<>();
        shapes.forEach(shape -> captions.addAll(shape.captions()));
        for (NcertParagraphRow row : rows) {
            flags.addAll(figures(chapter, row, captions));
        }
        return new Result(flags, paired, compared, judged, undecided);
    }

    /** A page's start check: its flag, if any, and every pairing that needed the dropped-math allowance. */
    private record Starts(Optional<Flag> flag, List<String> paired) {
    }

    private static Starts starts(short chapter, int page, List<NcertParagraphRow> rows, PdfLayout.PageShape shape) {
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
        List<Integer> unmatched = new ArrayList<>();
        for (int index = 0; index < starting.size(); index++) {
            String opening = openings.get(index);
            Optional<String> match = printed.stream().filter(start -> ParagraphParts.sameOpening(start, opening)).findFirst();
            if (match.isPresent()) {
                printed.remove(match.get());
            } else {
                unmatched.add(index);
            }
        }
        // What is left over on both sides, once more: a line whose math the layer dropped is the row's
        // opening with a run cut out of it, and only the leftovers can still be paired (ruling, 2026-09-16).
        // Each pairing is named: it is a guess, and a page it quiets would otherwise leave no trace.
        List<String> paired = new ArrayList<>();
        for (Iterator<Integer> left = unmatched.iterator(); left.hasNext();) {
            int index = left.next();
            String opening = openings.get(index);
            Optional<String> match = printed.stream()
                    .filter(start -> ParagraphParts.openingWithMathDropped(start, opening)).findFirst();
            if (match.isPresent()) {
                NcertParagraphRow row = starting.get(index);
                paired.add("ch " + chapter + " p" + page + ": the print starts \"" + match.get() + "\" where §"
                        + row.section() + " ¶" + row.paraNo() + " starts \"" + quote(opening)
                        + "\" — the layer dropped the line's math");
                printed.remove(match.get());
                left.remove();
            }
        }
        List<String> unmatchedRows = new ArrayList<>();
        for (int index : unmatched) {
            NcertParagraphRow row = starting.get(index);
            unmatchedRows.add("§" + row.section() + " ¶" + row.paraNo() + " \"" + quote(openings.get(index)) + "\"");
        }
        if (unmatchedRows.isEmpty() && printed.isEmpty()) {
            return new Starts(Optional.empty(), paired);
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
        return new Starts(Optional.of(new Flag(Kind.starts, page, null, message.toString())), paired);
    }

    /**
     * The page's own numbered equations against the rows printed on it. A displayed equation the
     * transcription drops takes its number with it, and the number is the part of it the text layer keeps
     * — the blind spot the seeded run measured twice (TRACKER 2026-09-15, ruling 2026-09-16). Counted, not
     * merely listed, because a page prints the same number both beside the equation and in the sentence
     * that refers to it; and only where the print carries more than the rows, since a number the layer
     * itself loses is nothing against a row.
     */
    private static List<Flag> equations(short chapter, int page, List<NcertParagraphRow> rows, PdfLayout.PageShape shape) {
        String mine = chapter + ".";
        Map<String, Integer> printed = new LinkedHashMap<>();
        shape.equationNumbers().stream().filter(number -> number.startsWith("(" + mine))
                .forEach(number -> printed.merge(number, 1, Integer::sum));
        if (printed.isEmpty()) {
            return List.of();
        }
        Map<String, Integer> carried = new HashMap<>();
        for (NcertParagraphRow row : rows) {
            for (ParagraphParts.Part part : ParagraphParts.of(row)) {
                if (part.page() != page) {
                    continue;
                }
                Matcher number = EQUATION_NUMBER.matcher(part.text());
                while (number.find()) {
                    carried.merge(number.group(), 1, Integer::sum);
                }
            }
        }
        List<Flag> flags = new ArrayList<>();
        printed.forEach((number, times) -> {
            int rowsCarry = carried.getOrDefault(number, 0);
            if (times > rowsCarry) {
                flags.add(new Flag(Kind.equation, page, null, "ch " + chapter + " p" + page + ": the print numbers "
                        + number + " " + times(times) + ", the rows carry it " + times(rowsCarry)
                        + " — a displayed equation dropped, its number altered, or a reference to it lost"));
            }
        });
        return flags;
    }

    private static String times(int count) {
        return switch (count) {
            case 0 -> "not at all";
            case 1 -> "once";
            case 2 -> "twice";
            default -> count + " times";
        };
    }

    private static Optional<Flag> join(short chapter, int before, int page, List<NcertParagraphRow> rows,
            PdfLayout.PageShape shape, PdfLayout.PageShape beforeShape) {
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
        // Only this direction: here the previous page has the casting vote, because a flush first line
        // is no evidence at all in a book that sets every page's first line flush. A last line short of
        // its column's measure is a paragraph's last line, so nothing ran across the break and there is
        // nothing to flag — on bio11, twelve of fifteen flags, each 37-228 pt short (D15, 2026-09-23).
        // The branch above is the opposite case, where a page ending its paragraph is the corroboration
        // of a row that ran across anyway, so it is never silenced.
        boolean printEndedThere = beforeShape != null && beforeShape.bottom() == PdfLayout.Bottom.ends;
        if (shape.top() == PdfLayout.Top.continues && !printEndedThere && joined == null && freshStart != null) {
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

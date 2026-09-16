package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

/**
 * Where a page's printed paragraphs start, read from where its text layer puts each glyph (D15).
 *
 * <p>{@link PdfTextLayer} and the checks built on it use the layer's <em>characters</em>; this uses
 * their <em>positions</em>, which survive the symbol fonts and the private-use encoding that garble
 * the characters. It exists because on a frozen prompt the character layer of an extraction is stable
 * and its paragraph boundaries are not (DECISIONS 2026-09-14, dry runs 10 and 11): the typography
 * decides where a paragraph starts, the book sets it plainly, and code can read it.
 *
 * <p>The rules were measured on {@code keph107.pdf} (2026-09-14) and each is a guess about print that
 * the chapter-7 calibration measures, so what this produces routes attention and never refuses a run:
 *
 * <ul>
 * <li>Body text is the page's commonest glyph size; smaller lines (running head, captions, scripts)
 * are not body text, and a line is prose when most of its tokens are words.</li>
 * <li>A paragraph's first line is set 12–40 pt in <em>from the line directly below it</em> — 18 pt on
 * this corpus. Measuring against the next line rather than a page-wide margin is what keeps a boxed
 * statement, set 24 pt in as a whole, from reading as twenty paragraphs; a column's own commonest
 * margin decides only for a line with no prose line close beneath it (a sentence before a display).</li>
 * <li>The first body line after a bold section heading starts a paragraph, flush as it is; so does a
 * bold "Example 7.4", "Answer" or "Solution" label.</li>
 * <li>Two columns are told apart by where their lines start, not by the page's centre: a verso page's
 * right column begins at 319.7 pt of 657, left of the middle.</li>
 * </ul>
 */
final class PdfLayout {

    /** How far in a first line is set from the line below it, in points. */
    static final double MIN_INDENT = 12;
    static final double MAX_INDENT = 40;

    /** Glyphs whose baselines differ by at most this much are one line. */
    private static final double SAME_LINE = 1.5;
    /** Wider than a word space: where a line may cross the column gutter. */
    private static final double SEGMENT_GAP = 12;
    /** Wider than any justified word space: a display's spacing, which splits a line anywhere. */
    private static final double DISPLAY_GAP = 40;
    /** How many body lines below the right column's first line the left column's first may begin before something unseen is above it. */
    private static final double FAR_BELOW_LINES = 3;
    /** How many prose lines must share a start for it to be a column's margin. */
    private static final int MARGIN_LINES = 3;
    /** A line body text sits within this many points of the body size. */
    private static final double BODY_SIZE_TOLERANCE = 0.9;
    /** How many words of a starting line a report quotes. */
    private static final int QUOTED_WORDS = 6;

    private static final Pattern HEADING = Pattern.compile("^\\d{1,2}(\\.\\d{1,2})+\\s+[A-Z][A-Z ,'’()-]{3,}");
    /** A worked example's label, behind at most a box ornament glyph or two ("tExample 7.3"), in any font. */
    private static final Pattern EXAMPLE = Pattern.compile("^.{0,2}?Example\\s+\\d{1,2}\\.\\d{1,2}\\b");
    /** Set in bold by the book; in running prose the word would not open a line in bold. */
    private static final Pattern ANSWER = Pattern.compile("^(Answer|Solution)\\b");
    /** A numbered law ("3. Law of periods") or an item marker ("(a)", "(ii)") opens its paragraph (prompt v3). */
    private static final Pattern ITEM = Pattern.compile("^(\\d{1,2}\\.\\s+\\p{Lu}|\\((?:[a-h]|i{1,3}|iv|vi{0,3}|ix|x)\\)\\s)");
    private static final Pattern WORD = Pattern.compile("(?=[A-Za-z’']{2,})[A-Za-z’']*[AEIOUYaeiouy][A-Za-z’']*");

    private PdfLayout() {
    }

    /** One glyph of the text layer: its left edge and baseline from the top-left, its width and size. */
    record Glyph(double x, double y, double width, double size, String font, String text) {
    }

    /** Whether the page's first line of running text continues the previous page's paragraph. */
    enum Top {
        /** Flush, and neither a heading's first line nor a label: the previous page's paragraph goes on. */
        continues,
        /** Indented, after a heading, or a label: a new paragraph. */
        starts,
        /** The page opens with something that is not a prose line — a display, a figure — or has no text. */
        unknown
    }

    /** An equation's printed number, "(7.35)" — the layer keeps it where it loses the equation itself. */
    private static final Pattern EQUATION_NUMBER = Pattern.compile("\\(\\d{1,2}\\.\\d{1,3}\\)");

    /**
     * @param starts          the opening words of each line that starts a paragraph, in reading order
     * @param top             how the page's first line of running text begins
     * @param topLine         that line's opening words, or null when there is none
     * @param captions        the figure and table labels the page's bold captions carry ("fig 7.3")
     * @param equationNumbers every printed equation number of the page, in reading order, as often as
     *                        the page prints it — a displayed equation's label and every reference to it
     */
    record PageShape(List<String> starts, Top top, String topLine, List<String> captions,
            List<String> equationNumbers) {

        PageShape {
            starts = List.copyOf(starts);
            captions = List.copyOf(captions);
            equationNumbers = List.copyOf(equationNumbers);
        }

        PageShape(List<String> starts, Top top, String topLine, List<String> captions) {
            this(starts, top, topLine, captions, List.of());
        }
    }

    /** Every page of a chapter PDF, in order. */
    static List<PageShape> pages(byte[] pdf) {
        try (PDDocument document = Loader.loadPDF(pdf)) {
            List<PageShape> shapes = new ArrayList<>();
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                GlyphCollector collector = new GlyphCollector();
                collector.setSortByPosition(true);
                collector.setStartPage(page);
                collector.setEndPage(page);
                collector.getText(document);
                shapes.add(shape(collector.glyphs, document.getPage(page - 1).getMediaBox().getWidth()));
            }
            return shapes;
        } catch (IOException e) {
            throw new UncheckedIOException("cannot read the PDF's text layer", e);
        }
    }

    static PageShape shape(List<Glyph> glyphs, double pageWidth) {
        // Two passes. A first, cautious cut at every gap wider than a word space finds the prose and
        // so the columns; the second cuts only at the gutter or at a display's spacing, because a
        // justified line in a narrow column can space two words wider than the first cut allows —
        // "Since Cavendish's ⟶ experiment, the" on page 6 of chapter 7.
        List<Line> firstCut = lines(glyphs, (previous, next) -> gap(previous, next) > SEGMENT_GAP);
        if (firstCut.isEmpty()) {
            return new PageShape(List.of(), Top.unknown, null, List.of());
        }
        double rightFrom = columnBoundary(firstCut, pageWidth);
        Double rightMargin = commonest(prose(firstCut, bodySize(firstCut)).stream()
                .filter(line -> line.x >= rightFrom).map(line -> line.x).toList());
        List<Line> lines = lines(glyphs, (previous, next) -> gap(previous, next) > DISPLAY_GAP
                || (rightMargin != null && gap(previous, next) > SEGMENT_GAP
                        && next.x >= rightMargin - 2 && previous.x + previous.width < rightMargin - 5));
        double body = bodySize(lines);
        List<Line> readable = lines.stream().filter(line -> line.size >= body - BODY_SIZE_TOLERANCE).toList();
        Set<Line> prose = prose(readable, body);
        Map<Boolean, List<Line>> columns = new HashMap<>();
        for (Line line : readable) {
            columns.computeIfAbsent(line.x >= rightFrom, right -> new ArrayList<>()).add(line);
        }

        List<String> starts = new ArrayList<>();
        Top top = Top.unknown;
        String topLine = null;
        boolean topJudged = false;
        // Where the right column's text begins, for the one check below on the left column's first line.
        double rightTop = columns.getOrDefault(true, List.of()).stream()
                .filter(line -> Math.abs(line.size - body) <= BODY_SIZE_TOLERANCE)
                .mapToDouble(line -> line.y).min().orElse(Double.MAX_VALUE);
        for (boolean right : new boolean[] {false, true}) {
            List<Line> column = new ArrayList<>(columns.getOrDefault(right, List.of()));
            column.sort(Comparator.comparingDouble((Line line) -> line.y).thenComparingDouble(line -> line.x));
            Double margin = commonest(column.stream().filter(prose::contains).map(line -> line.x).toList());
            boolean afterHeading = false;
            for (Line line : column) {
                // A left column whose first line in the layer sits far below where the right column's
                // text begins has something above it the layer cannot see — on chapter 7 page 11, the
                // displayed equations that finish page 10's paragraph — so it cannot say what it continues.
                if (!topJudged && !right && line.y - rightTop > FAR_BELOW_LINES * body) {
                    topJudged = true;
                }
                // A heading may run to a second line of capitals, which is still the heading.
                boolean heading = line.bold && (HEADING.matcher(line.text).find() || (afterHeading && line.isCapitals()));
                boolean isProse = prose.contains(line);
                boolean start = false;
                if (isProse && !heading) {
                    start = afterHeading
                            || EXAMPLE.matcher(line.text).find()
                            || (line.bold && ANSWER.matcher(line.text).find())
                            || ITEM.matcher(line.text).find()
                            || indented(line, column, prose, margin, body);
                }
                if (!topJudged && !column.isEmpty()) {
                    topJudged = true;
                    if (isProse && !heading) {
                        top = start ? Top.starts : Top.continues;
                        topLine = line.quote();
                    } else if (heading) {
                        top = Top.starts;
                        topLine = line.quote();
                    }
                }
                if (start) {
                    starts.add(line.quote());
                }
                if (heading) {
                    afterHeading = true;
                } else if (isProse) {
                    afterHeading = false;
                }
            }
        }

        Set<String> captions = new LinkedHashSet<>();
        List<String> equationNumbers = new ArrayList<>();
        for (Line line : lines) {
            if (line.bold) {
                FigureLabels.of(line.text).ifPresent(label -> captions.add(label.base()));
            }
            Matcher number = EQUATION_NUMBER.matcher(line.text);
            while (number.find()) {
                equationNumbers.add(number.group());
            }
        }
        return new PageShape(starts, top, topLine, List.copyOf(captions), equationNumbers);
    }

    /**
     * Set in from the prose line directly beneath it — or, when what follows it is not prose (a
     * display, a figure, the end of the column), from the column's commonest margin. The fallback
     * is that narrow on purpose: a box's last line followed by prose at the column's margin is the
     * box's own margin, not an indent.
     */
    private static boolean indented(Line line, List<Line> column, Set<Line> prose, Double margin, double body) {
        // "Below" and "above" mean a different visual line: an italic symbol's baseline sits a point
        // or two off its line's and is not the line beneath it.
        Line next = null;
        Line previous = null;
        for (Line candidate : column) {
            if (candidate.y > line.y + 0.5 * body && next == null) {
                next = candidate;
            }
            if (candidate.y < line.y - 0.5 * body) {
                previous = candidate;
            }
        }
        double reference;
        if (next != null && prose.contains(next) && next.y - line.y <= 2 * body) {
            reference = next.x;
        } else if (previous != null && prose.contains(previous) && line.y - previous.y <= 2 * body) {
            // Nothing to compare with close beneath — a display or a figure follows — so the line above:
            // the previous paragraph's last line sits at the margin this one is set in from, and a
            // box's own lines share the box's margin.
            reference = previous.x;
        } else if (margin != null) {
            reference = margin;
        } else {
            return false;
        }
        double inset = line.x - reference;
        return inset >= MIN_INDENT && inset <= MAX_INDENT;
    }

    /**
     * Where the right column begins: the left column's margin is the leftmost start that several prose
     * lines in the left half share — not the commonest, because a verso page's right column can start
     * left of the middle and outnumber the left column's flush lines (chapter 7 page 7) — and a line
     * starting most of the way from it to the middle is the right column's. With no such margin the
     * page is one column.
     */
    private static double columnBoundary(List<Line> lines, double pageWidth) {
        if (lines.isEmpty()) {
            return Double.MAX_VALUE;
        }
        double body = bodySize(lines);
        Map<Long, List<Double>> byPoint = new TreeMap<>();
        prose(lines, body).stream().filter(line -> line.x < pageWidth / 2)
                .forEach(line -> byPoint.computeIfAbsent(Math.round(line.x), point -> new ArrayList<>()).add(line.x));
        Double leftMargin = byPoint.values().stream().filter(group -> group.size() >= MARGIN_LINES)
                .map(List::getFirst).findFirst().orElse(null);
        return leftMargin == null ? Double.MAX_VALUE : leftMargin + 0.8 * (pageWidth / 2 - leftMargin);
    }

    /** Body-size lines that are mostly words, by identity: two lines may read the same. */
    private static Set<Line> prose(List<Line> lines, double body) {
        Set<Line> prose = Collections.newSetFromMap(new IdentityHashMap<>());
        lines.stream()
                .filter(line -> Math.abs(line.size - body) <= BODY_SIZE_TOLERANCE && line.isProse())
                .forEach(prose::add);
        return prose;
    }

    private static double gap(Glyph previous, Glyph next) {
        return next.x - (previous.x + previous.width);
    }

    private static List<Line> lines(List<Glyph> glyphs, java.util.function.BiPredicate<Glyph, Glyph> splits) {
        List<Glyph> sorted = glyphs.stream()
                .filter(glyph -> glyph.text != null && !glyph.text.isBlank())
                .sorted(Comparator.comparingDouble(Glyph::y).thenComparingDouble(Glyph::x))
                .toList();
        List<List<Glyph>> rows = new ArrayList<>();
        for (Glyph glyph : sorted) {
            if (rows.isEmpty() || glyph.y - rows.getLast().getFirst().y > SAME_LINE) {
                rows.add(new ArrayList<>());
            }
            rows.getLast().add(glyph);
        }
        List<Line> lines = new ArrayList<>();
        for (List<Glyph> row : rows) {
            row.sort(Comparator.comparingDouble(Glyph::x));
            List<Glyph> segment = new ArrayList<>();
            for (Glyph glyph : row) {
                if (!segment.isEmpty()) {
                    if (splits.test(segment.getLast(), glyph)) {
                        lines.add(Line.of(segment));
                        segment = new ArrayList<>();
                    }
                }
                segment.add(glyph);
            }
            lines.add(Line.of(segment));
        }
        return lines;
    }

    /** The size most glyphs are set in, to the half point. */
    private static double bodySize(List<Line> lines) {
        Map<Double, Integer> counts = new HashMap<>();
        lines.forEach(line -> counts.merge(Math.round(line.size * 2) / 2.0, line.glyphs, Integer::sum));
        return counts.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey();
    }

    /** The most frequent value to the nearest point, or null for too few to call one a margin. */
    private static Double commonest(List<Double> values) {
        Map<Long, List<Double>> byPoint = new HashMap<>();
        values.forEach(value -> byPoint.computeIfAbsent(Math.round(value), point -> new ArrayList<>()).add(value));
        return byPoint.values().stream()
                .max(Comparator.comparingInt((List<Double> group) -> group.size())
                        .thenComparingDouble(group -> -group.getFirst()))
                .filter(group -> group.size() >= 2)
                .map(List::getFirst)
                .orElse(null);
    }

    /** One run of glyphs on one baseline with no wide gap in it. */
    private record Line(double x, double y, double size, boolean bold, String text, int glyphs) {

        static Line of(List<Glyph> glyphs) {
            StringBuilder text = new StringBuilder();
            Glyph previous = null;
            Map<Double, Integer> sizes = new HashMap<>();
            for (Glyph glyph : glyphs) {
                if (previous != null && glyph.x - (previous.x + previous.width) > 0.15 * glyph.size) {
                    text.append(' ');
                }
                text.append(glyph.text);
                sizes.merge(Math.round(glyph.size * 2) / 2.0, 1, Integer::sum);
                previous = glyph;
            }
            double size = sizes.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey();
            Glyph first = glyphs.getFirst();
            String font = first.font == null ? "" : first.font.toLowerCase(Locale.ROOT);
            boolean bold = font.contains("bold") || font.contains("demi") || font.contains("black")
                    || font.contains("heavy");
            return new Line(first.x, first.y, size, bold, text.toString().replaceAll("\\s+", " ").strip(),
                    glyphs.size());
        }

        /**
         * Mostly words: three or more, and at least half of the line's tokens. A word has a vowel, so
         * a display's symbol runs — "mV GmM mV" — are not words however many letters they carry.
         */
        boolean isProse() {
            String[] tokens = text.split(" ");
            long words = Arrays.stream(tokens)
                    .map(token -> token.replaceAll("^[^A-Za-z]+|[^A-Za-z]+$", ""))
                    .filter(token -> WORD.matcher(token).matches())
                    .count();
            return words >= 3 && words * 2 >= tokens.length;
        }

        /** Every letter a capital, and enough of them to be words. */
        boolean isCapitals() {
            String letters = text.replaceAll("[^\\p{L}]", "");
            return letters.length() >= 3 && letters.equals(letters.toUpperCase(Locale.ROOT));
        }

        String quote() {
            String[] tokens = text.split(" ");
            return String.join(" ", Arrays.copyOf(tokens, Math.min(tokens.length, QUOTED_WORDS)));
        }
    }

    /** The glyphs of one page as PDFBox positions them, private-use codepoints decoded. */
    private static final class GlyphCollector extends PDFTextStripper {

        private final List<Glyph> glyphs = new ArrayList<>();

        @Override
        protected void writeString(String text, List<TextPosition> positions) {
            for (TextPosition position : positions) {
                String font = position.getFont() == null ? "" : position.getFont().getName();
                glyphs.add(new Glyph(position.getXDirAdj(), position.getYDirAdj(), position.getWidthDirAdj(),
                        position.getFontSizeInPt(), font, PdfTextLayer.decodePrivateUse(position.getUnicode())));
            }
        }
    }
}

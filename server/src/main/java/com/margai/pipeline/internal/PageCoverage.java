package com.margai.pipeline.internal;

import java.util.regex.Pattern;

/**
 * How much of a page's text came back, measured as characters against the page's own text layer.
 *
 * <p>This is the free check that answers the question that actually matters — <em>was any teaching
 * dropped</em> — and it is the one check the text layer can answer honestly for an equation-dense
 * page. It counts letters and digits and ignores everything else: order, punctuation, spacing. That
 * matters, because PDFBox emits a displayed equation by typographic row rather than in reading
 * order ({@code 22 / fi E / mVmV GmM} for a page of Chapter 7), so every check that depends on the
 * layer's <em>sequence</em> is worthless there while a character count is unharmed (D14).
 *
 * <p>It replaced a check that counted blocks of prose and compared them to the paragraph count.
 * That one fired on two pages of the chapter-7 dry run and was wrong both times: an equation-dense
 * page has few long prose blocks by construction, so its denominator collapsed exactly where the
 * risk was highest.
 *
 * <h2>The recalibration of 2026-09-23</h2>
 *
 * <p>{@link #TOO_LITTLE} was 0.60, measured across the twelve taught pages of Chapter 7 alone,
 * which ran 0.76–1.14. Re-measured across <b>every</b> page of both books that exist — 301 pages
 * sent to the model that returned paragraphs — the two subjects turn out to have the same
 * distribution: {@code phy11-part1} 0.31–1.16 with a median of 0.95, {@code bio11} 0.32–0.99 with
 * a median of 0.90. So a figure-dense subject is not what makes a ratio low; a table or a tint box
 * is, and Physics has those too. All eight pages below 0.60 across both books were adjudicated
 * correct skips — Table 1.1, two tint boxes, Table 6.1, Table 4.2's cells, two figure interiors —
 * and <b>nothing in either book falls below 0.31</b>. The floor therefore sits below the lowest
 * page either book has: it can still catch a page that lost most of itself, without firing on the
 * table interiors the prompt tells the model not to transcribe and the layer counts anyway.
 *
 * <p>Two things the old check treated as ratios are not ratios, and both were noise:
 *
 * <ul>
 *   <li><b>A page that returned nothing.</b> It scored 0 and read "text is missing", which is
 *       eleven of {@code bio11}'s fourteen flags — five unit openers, five biographies and the
 *       plates, every one of them a page the prompt tells the model to return nothing for. It gets
 *       its own verdict and its own section of the report, a checklist rather than a defect list,
 *       carrying what separates the two kinds: a biography's layer reads as sentences and a
 *       plate's reads as labels. Their character counts do not separate them — Corti's page holds
 *       376 and the cell-diagram plate 466.
 *   <li><b>A layer too thin to divide by.</b> {@link #MIN_CHARACTERS} stays, because a ratio
 *       against 200 characters means nothing and a plate whose caption was transcribed would read
 *       as "transcribed twice" — but it stops being <em>silent</em>. It is a verdict now, so the
 *       report can name the page. It hid three pages of {@code bio11}, one of them the fifth
 *       biography, which was found by hand and might not have been.
 * </ul>
 */
final class PageCoverage {

    /** Below this share of the page's characters, text is missing rather than merely skipped. */
    static final double TOO_LITTLE = 0.30;

    /** Above this, the page has probably been transcribed twice — the tiling overlap. */
    static final double TOO_MUCH = 1.50;

    /** Too little text on the page to judge a ratio: a plate, a full-page figure. */
    private static final int MIN_CHARACTERS = 400;

    /** Running text as the layer carries it: sixty characters with no full stop, and then one. */
    private static final Pattern SENTENCE_RUN = Pattern.compile("[A-Za-z][^.!?]{59,}[.!?]");

    private static final Pattern NOT_ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]+");

    private PageCoverage() {
    }

    /** What a page's character count says about its transcription. */
    enum Verdict {

        /** The transcription carries a believable share of the page. */
        MATCHED,

        /** Far less came back than the page holds: text is missing. */
        TOO_LITTLE_CAME_BACK,

        /** More came back than the page holds: the tiling overlap, read twice. */
        TOO_MUCH_CAME_BACK,

        /** The page returned no paragraphs at all — a plate, a biography, a table, or a loss. */
        NOTHING_CAME_BACK,

        /** No layer, or too little of one for a ratio to mean anything. */
        NOT_JUDGED
    }

    /**
     * @param verdict        what the count says
     * @param ratio          the share of the page's characters the transcription carries, or -1
     *                       where there was no ratio to take
     * @param pageCharacters the letters and digits the page's own layer holds
     * @param sentenceRuns   how many of them the layer sets as running text, which is what tells a
     *                       biography from a plate when nothing came back
     */
    record Assessment(Verdict verdict, double ratio, int pageCharacters, int sentenceRuns) {

        /** This page in one line, for the report that carries its verdict. */
        String reason() {
            return switch (verdict) {
                case TOO_LITTLE_CAME_BACK -> "only %.0f%% of the page's characters came back — text is missing"
                        .formatted(ratio * 100);
                case TOO_MUCH_CAME_BACK -> "%.0f%% of the page's characters came back — transcribed twice?"
                        .formatted(ratio * 100);
                case NOTHING_CAME_BACK -> "nothing came back; the layer holds %d characters %s"
                        .formatted(pageCharacters, sentenceRuns == 0 ? "and no sentence-length run"
                                : "in %d sentence-length runs".formatted(sentenceRuns));
                case NOT_JUDGED -> "the layer holds %d characters, too few to measure a ratio against"
                        .formatted(pageCharacters);
                case MATCHED -> "%.0f%% of the page's characters came back".formatted(ratio * 100);
            };
        }
    }

    /**
     * What this page's coverage looks like.
     *
     * <p>A page that returned nothing is judged before a thin layer is: the page said something
     * about itself, and where it said nothing at all the reader wants to see it whether or not
     * there were 400 characters to divide by.
     *
     * @param pageText    the page's text layer
     * @param transcribed every paragraph the model returned for that page, joined
     */
    static Assessment of(String pageText, String transcribed) {
        if (pageText == null || transcribed == null) {
            return new Assessment(Verdict.NOT_JUDGED, -1, 0, 0);
        }
        int onPage = squashedLength(pageText);
        int runs = (int) SENTENCE_RUN.matcher(collapsed(pageText)).results().count();
        if (squashedLength(transcribed) == 0) {
            return new Assessment(Verdict.NOTHING_CAME_BACK, -1, onPage, runs);
        }
        if (onPage < MIN_CHARACTERS) {
            return new Assessment(Verdict.NOT_JUDGED, -1, onPage, runs);
        }
        double ratio = (double) squashedLength(transcribed) / onPage;
        Verdict verdict = ratio < TOO_LITTLE ? Verdict.TOO_LITTLE_CAME_BACK
                : ratio > TOO_MUCH ? Verdict.TOO_MUCH_CAME_BACK : Verdict.MATCHED;
        return new Assessment(verdict, ratio, onPage, runs);
    }

    private static int squashedLength(String text) {
        return NOT_ALPHANUMERIC.matcher(text).replaceAll("").length();
    }

    /** The layer's own line breaks fall mid-sentence, so a run is only visible once they are gone. */
    private static String collapsed(String text) {
        return text.replaceAll("\\s+", " ");
    }
}

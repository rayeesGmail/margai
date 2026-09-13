package com.margai.pipeline.internal;

import java.util.Optional;
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
 * <p>The thresholds are measured, not chosen. Across the twelve taught pages of Chapter 7 the ratio
 * ran <b>0.76 to 1.14</b>, mean 0.88 — the shortfall being running heads, figure captions, table
 * interiors and the reprint footer, all skipped on purpose. Page 7 exceeds 1.0 because our notation
 * is longer than the glyphs it replaces: {@code sqrt} for √, {@code approx=} for ≅, {@code i_hat}
 * for î. So {@link #TOO_LITTLE} sits well below the observed floor, and {@link #TOO_MUCH} catches
 * the opposite failure — a page transcribed twice, which is a real risk when a page is sent to the
 * model as two overlapping bands.
 */
final class PageCoverage {

    /** Below this share of the page's characters, text is missing rather than merely skipped. */
    static final double TOO_LITTLE = 0.60;

    /** Above this, the page has probably been transcribed twice — the tiling overlap. */
    static final double TOO_MUCH = 1.50;

    /** Too little text on the page to judge a ratio: a plate, a full-page figure. */
    private static final int MIN_CHARACTERS = 400;

    private static final Pattern NOT_ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]+");

    private PageCoverage() {
    }

    /** The share of the page's characters this transcription carries, or -1 when it cannot be judged. */
    static double ratio(String pageText, String transcribed) {
        if (pageText == null || transcribed == null) {
            return -1;
        }
        int onPage = squashedLength(pageText);
        return onPage < MIN_CHARACTERS ? -1 : (double) squashedLength(transcribed) / onPage;
    }

    /**
     * Why this page's coverage looks wrong, or empty when it looks reasonable.
     *
     * @param pageText    the page's text layer
     * @param transcribed every paragraph the model returned for that page, joined
     */
    static Optional<String> check(String pageText, String transcribed) {
        double ratio = ratio(pageText, transcribed);
        if (ratio < 0) {
            return Optional.empty();
        }
        if (ratio < TOO_LITTLE) {
            return Optional.of("only %.0f%% of the page's characters came back — text is missing"
                    .formatted(ratio * 100));
        }
        if (ratio > TOO_MUCH) {
            return Optional.of("%.0f%% of the page's characters came back — transcribed twice?"
                    .formatted(ratio * 100));
        }
        return Optional.empty();
    }

    private static int squashedLength(String text) {
        return NOT_ALPHANUMERIC.matcher(text).replaceAll("").length();
    }
}

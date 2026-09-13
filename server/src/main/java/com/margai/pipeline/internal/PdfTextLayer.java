package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * A PDF's own text layer, page by page — what the file claims its words are, as distinct from what
 * the VISION tier reads off the rendered image (TECH_PLAN §6.1). The pipeline extracts from images
 * because NCERT's layout defeats text extractors; the text layer is still useful as a cheap,
 * model-free signal about a page, and this is where that signal comes from.
 *
 * <p>It is not uniformly trustworthy, and the difference is measurable. Over the 79 English chapter
 * files the share of common English words per page is 0.26–0.47 (median 0.33), except
 * {@code chem11-part2/kech202.pdf} at 0.02, whose text is set in a custom-encoded font that
 * extracts as a Caesar-shifted alphabet. {@link #MIN_LEGIBILITY} sits in that gap, four times above
 * the garbled file and well below the worst genuine one, so "can this page's text be trusted" is a
 * measured question rather than an assumption (surveyed at D14).
 */
final class PdfTextLayer {

    /** Below this share of common English words, the text layer is not the page's real words. */
    static final double MIN_LEGIBILITY = 0.10;

    /** Enough text to judge; a figure-only page says nothing either way. */
    private static final int MIN_WORDS = 20;

    private static final Set<String> COMMON_WORDS = Set.of("the", "of", "and", "is", "to", "in", "a",
            "that", "we", "are", "this", "for", "as", "it", "with", "be", "on", "by", "an", "which");

    private PdfTextLayer() {
    }

    /** Every page's text in order, private-use codepoints decoded. */
    static List<String> pages(byte[] pdf) {
        try (PDDocument document = Loader.loadPDF(pdf)) {
            List<String> pages = new ArrayList<>();
            PDFTextStripper stripper = new PDFTextStripper();
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);
                pages.add(decodePrivateUse(stripper.getText(document)));
            }
            return pages;
        } catch (IOException e) {
            throw new UncheckedIOException("cannot read the PDF's text layer", e);
        }
    }

    /**
     * Some NCERT files encode text as cp1252 bytes offset into the private use area — Chapter 7 of
     * Physics Part-I is the English example, seven of its seventeen pages (TRACKER, D9). Subtracting
     * the offset turns it back into the text it was, which is why that chapter's boundary is
     * detectable at all.
     */
    static String decodePrivateUse(String text) {
        StringBuilder decoded = new StringBuilder(text.length());
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            decoded.append(character >= 0xF000 && character <= 0xF0FF ? (char) (character - 0xF000) : character);
        }
        return decoded.toString();
    }

    /**
     * The share of this text's words that are common English ones, or -1 when there is too little
     * text to judge. A page of real prose scores around a third; a page of mis-encoded glyphs
     * scores near zero, because the shift destroys exactly the short function words.
     */
    static double legibility(String text) {
        String[] tokens = text.toLowerCase(Locale.ROOT).split("[^a-z]+");
        long words = Arrays.stream(tokens).filter(token -> token.length() > 1).count();
        if (words < MIN_WORDS) {
            return -1;
        }
        long common = Arrays.stream(tokens).filter(COMMON_WORDS::contains).count();
        return (double) common / words;
    }

    /** Whether a chapter's text layer can be trusted, judged over its pages that carry enough text. */
    static boolean isLegible(List<String> pages) {
        return meanLegibility(pages) >= MIN_LEGIBILITY;
    }

    /** The chapter's mean legibility, or 0 when no page carries enough text to judge. */
    static double meanLegibility(List<String> pages) {
        double[] scores = pages.stream().mapToDouble(PdfTextLayer::legibility).filter(score -> score >= 0).toArray();
        return scores.length == 0 ? 0 : Arrays.stream(scores).average().orElse(0);
    }
}

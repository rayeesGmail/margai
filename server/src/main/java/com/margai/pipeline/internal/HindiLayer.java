package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * A Hindi chapter's text layer as a check on its transcription (SPEC §12.2, DECISIONS 2026-10-02): each line's
 * Chanakya runs decoded by {@link ChanakyaDecoder}, its Bookman, Times and Symbol runs — digits, Latin, symbols,
 * bullets, already Unicode in these books — kept as they are. Never sent to the model and never stored: Hindi
 * extraction stays image-only, and this exists so its rows can be held to the page the way an English row is
 * held to its layer.
 *
 * <p>Order within a page is by baseline, then by position along it; a two-column page interleaves its
 * columns line by line, which a check of words and tokens against the page does not mind.
 */
final class HindiLayer {

    /**
     * One page.
     *
     * @param devanagari  the page's text in Unicode: decoded runs and Unicode runs, line by line
     * @param unicode     only the runs the layer already carries as Unicode — the digits, Latin, symbols and
     *                    bullets a Devanagari check cannot see
     * @param decodedOnly only the decoded Chanakya runs, for proving every glyph was mapped
     */
    record Page(String devanagari, String unicode, String decodedOnly) {
    }

    private HindiLayer() {
    }

    /** Every page of a Hindi chapter PDF, in order. */
    static List<Page> pages(byte[] pdf) {
        try (PDDocument document = Loader.loadPDF(pdf)) {
            List<Page> pages = new ArrayList<>();
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                pages.add(page(PdfLayout.glyphs(document, page, false)));
            }
            return pages;
        } catch (IOException e) {
            throw new UncheckedIOException("cannot read the Hindi PDF's text layer", e);
        }
    }

    private static Page page(List<PdfLayout.Glyph> glyphs) {
        StringBuilder all = new StringBuilder();
        StringBuilder unicode = new StringBuilder();
        StringBuilder decoded = new StringBuilder();
        for (List<PdfLayout.Glyph> line : lines(glyphs)) {
            StringBuilder lineText = new StringBuilder();
            for (List<PdfLayout.Glyph> run : runs(line)) {
                String text = text(run);
                String font = run.getFirst().font();
                if (ChanakyaDecoder.isDevanagariFont(font)) {
                    String devanagari = ChanakyaDecoder.decode(text, font);
                    lineText.append(devanagari).append(' ');
                    decoded.append(devanagari).append(' ');
                } else {
                    lineText.append(text).append(' ');
                    unicode.append(text).append(' ');
                }
            }
            all.append(lineText.toString().replaceAll("\\s+", " ").strip()).append('\n');
            unicode.append('\n');
            decoded.append('\n');
        }
        return new Page(all.toString().strip(), unicode.toString().strip(), decoded.toString().strip());
    }

    /**
     * Glyphs in the order the page draws them, a new line wherever the baseline moves. Never re-sorted along
     * the line: the Chanakya fonts draw a vowel sign over or before the letter it follows in typed order, and
     * sorting by position turned और into आरै (D16).
     */
    private static List<List<PdfLayout.Glyph>> lines(List<PdfLayout.Glyph> glyphs) {
        List<List<PdfLayout.Glyph>> lines = new ArrayList<>();
        for (PdfLayout.Glyph glyph : glyphs) {
            if (glyph.text() == null || glyph.text().isBlank()) {
                continue;
            }
            if (lines.isEmpty() || Math.abs(glyph.y() - lines.getLast().getFirst().y()) > PdfLayout.SAME_LINE) {
                lines.add(new ArrayList<>());
            }
            lines.getLast().add(glyph);
        }
        return lines;
    }

    /** Consecutive glyphs of one family — Walkman-Chanakya, KrutiDev, or Unicode — decode together. */
    private static List<List<PdfLayout.Glyph>> runs(List<PdfLayout.Glyph> line) {
        List<List<PdfLayout.Glyph>> runs = new ArrayList<>();
        String family = null;
        for (PdfLayout.Glyph glyph : line) {
            String own = family(glyph.font());
            if (runs.isEmpty() || !own.equals(family)) {
                runs.add(new ArrayList<>());
                family = own;
            }
            runs.getLast().add(glyph);
        }
        return runs;
    }

    private static String family(String font) {
        String name = font == null ? "" : font.toLowerCase(Locale.ROOT);
        return name.contains("krutidev") ? "krutidev" : name.contains("chanakya") ? "chanakya" : "unicode";
    }

    /** A run's text, a space wherever the gap between two glyphs is wider than a letter's spacing. */
    private static String text(List<PdfLayout.Glyph> run) {
        StringBuilder text = new StringBuilder();
        PdfLayout.Glyph previous = null;
        for (PdfLayout.Glyph glyph : run) {
            if (previous != null && glyph.x() - (previous.x() + previous.width()) > 0.15 * glyph.size()) {
                text.append(' ');
            }
            text.append(glyph.text());
            previous = glyph;
        }
        return text.toString();
    }
}

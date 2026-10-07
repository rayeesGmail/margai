package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.encoding.GlyphList;
import org.apache.pdfbox.pdmodel.font.encoding.SymbolEncoding;

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
     * @param latin       the page's Latin letters again, gathered by baseline and read left to right — a stacked
     *                    fraction's dv over dt is drawn d, d, v, t and the lines above never hold dv or dt; for
     *                    finding a token the rows carry, never for counting one
     */
    record Page(String devanagari, String unicode, String decodedOnly, String latin) {
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
        for (List<Placed> line : lines(glyphs)) {
            StringBuilder lineText = new StringBuilder();
            Placed previous = null;
            for (List<Placed> run : runs(line)) {
                String font = run.getFirst().glyph().font();
                boolean devanagariRun = ChanakyaDecoder.isDevanagariFont(font);
                List<Placed> ordered = devanagariRun ? run : leftToRight(run);
                if (previous != null && spaced(previous, ordered.getFirst())) {
                    lineText.append(' ');
                }
                String text = text(ordered);
                if (devanagariRun) {
                    String devanagari = ChanakyaDecoder.decode(text, font);
                    lineText.append(devanagari);
                    decoded.append(devanagari).append(' ');
                } else {
                    lineText.append(text);
                    unicode.append(text).append(' ');
                }
                previous = run.getLast();
            }
            all.append(lineText.toString().replaceAll("\\s+", " ").strip()).append('\n');
            unicode.append('\n');
            decoded.append('\n');
        }
        return new Page(all.toString().strip(), unicode.toString().strip(), decoded.toString().strip(),
                latinByBaseline(glyphs));
    }

    /**
     * The Latin letters of the Unicode faces, every glyph on a baseline together whenever the page drew it,
     * left to right, a space only where a gap is wider than half an em: dv and dt out of a stacked dv/dt, mgh
     * out of the italic m g h.
     */
    private static String latinByBaseline(List<PdfLayout.Glyph> glyphs) {
        List<PdfLayout.Glyph> latin = glyphs.stream()
                .filter(glyph -> glyph.text() != null && family(glyph.font()).equals("unicode") && !isMarker(glyph.font()))
                .filter(glyph -> glyphText(glyph).matches("[A-Za-z]"))
                .sorted(Comparator.comparingDouble(PdfLayout.Glyph::y)).toList();
        StringBuilder text = new StringBuilder();
        List<PdfLayout.Glyph> baseline = new ArrayList<>();
        for (PdfLayout.Glyph glyph : latin) {
            if (!baseline.isEmpty() && glyph.y() - baseline.getFirst().y() > PdfLayout.SAME_LINE) {
                appendLeftToRight(baseline, text);
                baseline.clear();
            }
            baseline.add(glyph);
        }
        appendLeftToRight(baseline, text);
        return text.toString().strip();
    }

    private static void appendLeftToRight(List<PdfLayout.Glyph> baseline, StringBuilder text) {
        PdfLayout.Glyph previous = null;
        for (PdfLayout.Glyph glyph : baseline.stream().sorted(Comparator.comparingDouble(PdfLayout.Glyph::x)).toList()) {
            if (previous != null && glyph.x() - (previous.x() + previous.width()) > 0.5 * glyph.size()) {
                text.append(' ');
            }
            text.append(glyphText(glyph));
            previous = glyph;
        }
        text.append('\n');
    }

    /** A drawn glyph, and whether the page drew a space glyph (or a dropped marker) since the one before it. */
    private record Placed(PdfLayout.Glyph glyph, boolean afterSpace) {
    }

    /**
     * Glyphs in the order the page draws them, a new line wherever the baseline moves. Never re-sorted along
     * the line: the Chanakya fonts draw a vowel sign over or before the letter it follows in typed order, and
     * sorting by position turned और into आरै (D16). A blank glyph is not kept but remembered as a space: a
     * condensed line's word spaces are narrower than the gap rule allows, and ch 5 p11 of the first Hindi book
     * read as one word without them. The example markers are dropped — MathB's ▶, which PDFBox gives as a
     * bullet, and Wingdings3's end mark — so 32 of that book's pages stopped counting a bullet nobody prints.
     */
    private static List<List<Placed>> lines(List<PdfLayout.Glyph> glyphs) {
        List<List<Placed>> lines = new ArrayList<>();
        boolean space = false;
        for (PdfLayout.Glyph glyph : glyphs) {
            if (glyph.text() == null || glyph.text().isBlank() || isMarker(glyph.font())) {
                space = true;
                continue;
            }
            if (lines.isEmpty() || Math.abs(glyph.y() - lines.getLast().getFirst().glyph().y()) > PdfLayout.SAME_LINE) {
                lines.add(new ArrayList<>());
                space = false;
            }
            lines.getLast().add(new Placed(glyph, space));
            space = false;
        }
        return lines;
    }

    /** Consecutive glyphs of one family — Walkman-Chanakya, KrutiDev, or Unicode — decode together. */
    private static List<List<Placed>> runs(List<Placed> line) {
        List<List<Placed>> runs = new ArrayList<>();
        String family = null;
        for (Placed placed : line) {
            String own = family(placed.glyph().font());
            if (runs.isEmpty() || !own.equals(family)) {
                runs.add(new ArrayList<>());
                family = own;
            }
            runs.getLast().add(placed);
        }
        return runs;
    }

    private static String family(String font) {
        String name = font == null ? "" : font.toLowerCase(Locale.ROOT);
        return name.contains("krutidev") ? "krutidev" : name.contains("chanakya") ? "chanakya" : "unicode";
    }

    private static boolean isMarker(String font) {
        String name = font == null ? "" : font.toLowerCase(Locale.ROOT);
        return name.contains("mathb") || name.contains("wingdings3");
    }

    /**
     * A Unicode run as the eye reads it: a display equation is drawn out of order — 0.05 kg as kg05.0 — which
     * no Chanakya glyph is, so only these runs are put back left to right, and only when they need it.
     */
    private static List<Placed> leftToRight(List<Placed> run) {
        for (int index = 1; index < run.size(); index++) {
            if (run.get(index).glyph().x() < run.get(index - 1).glyph().x() - 0.5) {
                return run.stream().sorted(Comparator.comparingDouble(placed -> placed.glyph().x()))
                        .map(placed -> new Placed(placed.glyph(), false)).toList();
            }
        }
        return run;
    }

    /**
     * Whether a space separates two glyphs: a space glyph between them, a gap wider than a letter's spacing,
     * or a jump back of more than an em (a caption drawn after its line, to its left). A change of font is not
     * one: these books set a number's decimal point in the Chanakya face, and 35.6 read as 35 . 6 (D16).
     */
    private static boolean spaced(Placed previous, Placed next) {
        PdfLayout.Glyph before = previous.glyph();
        PdfLayout.Glyph glyph = next.glyph();
        return next.afterSpace() || glyph.x() - (before.x() + before.width()) > 0.15 * glyph.size()
                || glyph.x() < before.x() - glyph.size();
    }

    /** A run's text, its glyphs' own text read in their fonts, spaces where {@link #spaced} puts them. */
    private static String text(List<Placed> run) {
        StringBuilder text = new StringBuilder();
        Placed previous = null;
        for (Placed placed : run) {
            if (previous != null && spaced(previous, placed)) {
                text.append(' ');
            }
            text.append(glyphText(placed.glyph()));
            previous = placed;
        }
        return text.toString();
    }

    /**
     * One glyph's text: a plain Chanakya face's conjunct as Walkman keys it, Wingdings' l as the list bullet it
     * draws, and a Symbol face's private-use code — which the layer's reader turns into Latin-1, ∞ into ¥ — as
     * the symbol the font's encoding gives it.
     */
    private static String glyphText(PdfLayout.Glyph glyph) {
        String name = glyph.font() == null ? "" : glyph.font().replaceFirst("^[A-Z]{6}\\+", "").toLowerCase(Locale.ROOT);
        if (ChanakyaDecoder.isDevanagariFont(name)) {
            return ChanakyaDecoder.asWalkman(glyph.text(), glyph.font());
        }
        if (name.startsWith("wingdings") && !name.startsWith("wingdings2") && glyph.text().equals("l")) {
            return "•";
        }
        if (name.startsWith("symbol") && glyph.text().length() == 1) {
            return SymbolFace.read(glyph.text().charAt(0));
        }
        return glyph.text();
    }

    /**
     * The Symbol face's private-use codes read through its encoding. PDFBox gives a Symbol glyph it can map as
     * Unicode — θ, ×, ° — and one it cannot as its code shifted out of the private-use area into Latin-1. A
     * Latin-1 character the encoding's own glyphs never produce is such a code (¥ is the infinity glyph,
     * £ the ≤); every other character is left alone, μ included — these books' μ arrives as the micro sign.
     */
    private static final class SymbolFace {

        private static final Map<Character, String> SHIFTED = shifted();

        static String read(char code) {
            return SHIFTED.getOrDefault(code, String.valueOf(code));
        }

        private static Map<Character, String> shifted() {
            Map<Integer, String> names = SymbolEncoding.INSTANCE.getCodeToNameMap();
            GlyphList glyphList = GlyphList.getAdobeGlyphList();
            Set<String> produced = new HashSet<>(Set.of("µ"));
            names.values().forEach(glyphName -> {
                String unicode = glyphList.toUnicode(glyphName);
                if (unicode != null) {
                    produced.add(unicode);
                }
            });
            Map<Character, String> shifted = new HashMap<>();
            names.forEach((code, glyphName) -> {
                String unicode = glyphList.toUnicode(glyphName);
                char latin = (char) code.intValue();
                if (code >= 0x21 && code <= 0xFF && unicode != null && !produced.contains(String.valueOf(latin))) {
                    shifted.put(latin, unicode);
                }
            });
            return Map.copyOf(shifted);
        }
    }
}

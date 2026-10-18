package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A visarga the transcriber wrote as a colon, put back (D16).
 *
 * <p>The Hindi books are set in Walkman-Chanakya, which draws the visarga and the colon with one glyph,
 * so the page cannot tell अतः from अत: and the transcriber, reading the image, wrote both: of the first
 * Hindi book's visarga words about a quarter came back with an ASCII colon — 97 in phy11-part1, अत: and
 * अतः on the same page. The two look alike on a screen and are different words to a search, to speech
 * and to the embedding. Code can see this one, so code decides it rather than the model
 * (.claude/rules/ai-layer.md) and the frozen prompt is untouched, as with {@link DimensionalBrackets}.
 *
 * <p>The rule is deliberately narrow, because a colon is usually a colon. Only a Devanagari word glued
 * to the colon is looked at — the print sets most real colons off with a space ("होते हैं :") — and of
 * those only a word this list knows is rewritten. The words ending in a visarga are an open class (any
 * adverb in -तः) and a noun ending in त can take a real colon ("ज्ञात: m = 5 kg"), so a glued word the
 * list does not know that ends in a bare consonant is left as written and named for a ruling. A colon
 * after a vowel sign ("है:", "चाहिए:") is a colon. Inside a word a visarga follows one of a few prefixes
 * (निःसंदेह, दुःख); any other colon glued on both sides — a ratio, a prefix the list lacks, a lost space —
 * is left and named too.
 *
 * <p>The verifier reads the image, where the two are one glyph, so {@link VerdictSpans} treats a visarga
 * and a colon after a Devanagari letter as the same: this step's rewrite must not come back as a
 * difference at the next paid read.
 */
final class VisargaColons {

    /** A Devanagari word — letters and signs, not the danda or the digits — and the colon glued to it. */
    private static final Pattern GLUED = Pattern.compile("([\\u0900-\\u0963\\u0970-\\u097F]+):");

    /** The words, without their visarga, that the books print with one. */
    private static final Set<String> WORDS = Set.of(
            "अत", "परित", "क्रमश", "प्राय", "पुन", "छ", "अंतत", "अन्तत", "अंतिमत", "अन्तिमत", "स्पष्टत", "पूर्णत",
            "स्वत", "वस्तुत", "सन्निकटत", "मुख्यत", "परिणामत", "सामान्यत", "साधारणत", "अधिकांशत", "फलत", "तत्वत",
            "तत्त्वत", "यथार्थत", "ऊर्ध्वाधरत", "क्षैतिजत", "एकघातत", "संभवत", "सम्भवत", "विशेषत", "मूलत", "अंशत");

    /** The prefixes a visarga follows inside a word: निःसंदेह, दुःख, अंतःकरण, पुनःस्थापन. */
    private static final Set<String> PREFIXES = Set.of("नि", "दु", "अंत", "अन्त", "पुन", "अध", "प्रात");

    /**
     * The pages as the load will correct and number them, one line per paragraph this rewrote, and one
     * per paragraph holding a glued colon it could not place.
     */
    record Applied(List<ExtractedPage> pages, List<String> notes, List<String> undecided) {
    }

    private VisargaColons() {
    }

    static Applied apply(List<ExtractedPage> pages) {
        List<ExtractedPage> out = new ArrayList<>(pages.size());
        List<String> notes = new ArrayList<>();
        List<String> undecided = new ArrayList<>();
        for (ExtractedPage page : pages) {
            List<NcertPage.Paragraph> rewritten = new ArrayList<>(page.paragraphs().size());
            boolean changed = false;
            for (NcertPage.Paragraph paragraph : page.paragraphs()) {
                List<String> written = new ArrayList<>();
                List<String> left = new ArrayList<>();
                String text = rewrite(paragraph.text(), written, left);
                String where = "ch " + page.chapterNo() + " p" + page.page() + " §" + paragraph.section() + ": ";
                if (!left.isEmpty()) {
                    undecided.add(where + String.join(", ", left));
                }
                if (written.isEmpty()) {
                    rewritten.add(paragraph);
                    continue;
                }
                changed = true;
                rewritten.add(new NcertPage.Paragraph(paragraph.section(), text,
                        paragraph.continuesPreviousPage(), paragraph.figureRefs()));
                notes.add(where + "a visarga written as a colon — " + String.join(", ", written));
            }
            out.add(changed
                    ? new ExtractedPage(page.chapterNo(), page.page(), page.confidence(), page.aiCallId(),
                            rewritten, page.skipped())
                    : page);
        }
        return new Applied(List.copyOf(out), List.copyOf(notes), List.copyOf(undecided));
    }

    /** The text with each known word's colon made a visarga; what was rewritten and what was left go to the lists. */
    private static String rewrite(String text, List<String> written, List<String> left) {
        StringBuilder out = new StringBuilder(text);
        Matcher matcher = GLUED.matcher(text);
        while (matcher.find()) {
            String word = matcher.group(1);
            int colon = matcher.end() - 1;
            boolean inside = matcher.end() < text.length() && letter(text.charAt(matcher.end()));
            if (inside ? PREFIXES.contains(word) : WORDS.contains(word)) {
                out.setCharAt(colon, 'ः');
                written.add(word + "ः");
            } else if (inside) {
                // glued on both sides and not a known prefix: a ratio, another prefix, or a lost space
                int end = matcher.end();
                while (end < text.length() && wordCharacter(text.charAt(end))) {
                    end++;
                }
                left.add(text.substring(matcher.start(), end));
            } else if (consonant(word.charAt(word.length() - 1))) {
                left.add(word + ":");
            }
        }
        return out.toString();
    }

    /** A letter or a sign of a Devanagari word, as {@link #GLUED} reads one. */
    private static boolean wordCharacter(char c) {
        return (c >= 'ऀ' && c <= 'ॣ') || (c >= '॰' && c <= 'ॿ');
    }

    /** A Devanagari letter (U+0904–0939, U+0958–0961), as opposed to a sign, a digit or a danda. */
    private static boolean letter(char c) {
        return (c >= 'ऄ' && c <= 'ह') || (c >= 'क़' && c <= 'ॡ');
    }

    /**
     * A consonant with its inherent vowel — क to ह, the precomposed nukta letters, or a consonant's
     * nukta sign: what a visarga can follow.
     */
    private static boolean consonant(char c) {
        return (c >= 'क' && c <= 'ह') || (c >= 'क़' && c <= 'य़') || c == '़';
    }
}

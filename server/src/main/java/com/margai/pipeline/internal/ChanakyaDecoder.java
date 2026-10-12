package com.margai.pipeline.internal;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Hindi books' text layer into Devanagari: every Hindi NCERT book is set in Walkman-Chanakya-901/902/905
 * (and Chemistry 12's headings in KrutiDev501), 8-bit fonts with no Unicode map, so the layer carries the
 * fonts' glyph codes — "HkkSfrdh" for भौतिकी. This reads them back.
 *
 * <p><b>A check, never a source</b> (SPEC §12.2, DECISIONS 2026-10-02): the decoded text is compared with the
 * Hindi transcription and nothing else. It is not sent to the model — Hindi extraction stays image-only — and
 * never stored in a row. Proved first as a scratchpad witness on phy11-part1 Hindi ch 1, where it found every
 * real defect in four transcriptions while neither paid second read found one (D16).
 *
 * <p>The glyph map was read off charts rendered from the fonts embedded in the PDFs and confirmed against
 * word contexts across all 79 Hindi chapter files, which it decodes with no unmapped glyph. One code was
 * misread there: ß, taken for श्व, prints ह्र (D16, 2026-10-12). With that and the second book's typing rules,
 * 339 of the 625,583 words the layer gives (0.05%) cannot be right as decoded — a sign with no letter, a halant
 * twice — most of them headings the books overprint for a bold. Typed order is not reading
 * order in these fonts, so beyond the map: the short-i glyph comes before its cluster, the reph after it, a
 * "hook" glyph turns the letter before it into another (व → क, प → फ, उ → ऊ), a half form takes the aa-stroke
 * to become the full letter, and a mark typed before a vowel sign belongs after it.
 */
final class ChanakyaDecoder {

    private static final char PRE_I = '';
    private static final char PRE_I_REPH = '';
    private static final char PRE_I_ANUS = '';
    private static final char REPH = '';
    private static final char REPH_ANUS = '';
    private static final char HOOK = '';

    /**
     * The ð glyph is a ट set under the letter before it, with no width of its own: after ट (V) or its half
     * form (ê) it prints ट्ट — संघट्ट, भट्टी, typed both ways in the same book (D16).
     */
    private static final String BELOW_TA = "्ट";

    private static final Map<Character, String> GLYPH = Map.ofEntries(
            Map.entry(' ', " "), Map.entry('!', "!"), Map.entry('\"', "ष्"), Map.entry('#', "रु"),
            Map.entry('$', "+"), Map.entry('%', "ः"), Map.entry('&', "-"), Map.entry('\'', "श्"),
            Map.entry('(', "("), Map.entry(')', ")"), Map.entry('*', "’"), Map.entry('+', "़"),
            Map.entry(',', "ए"), Map.entry('-', "."), Map.entry('.', "ण्"), Map.entry('/', "ध"),
            Map.entry('0', "0"), Map.entry('1', "1"), Map.entry('2', "2"), Map.entry('3', "3"), Map.entry('4', "4"),
            Map.entry('5', "5"), Map.entry('6', "6"), Map.entry('7', "7"), Map.entry('8', "8"), Map.entry('9', "9"),
            Map.entry(':', "रू"), Map.entry(';', "य"), Map.entry('<', "ढ"),
            Map.entry('=', "त्र्"), Map.entry('>', "झ"), Map.entry('?', "घ्"),
            Map.entry('@', "/"), Map.entry('A', "।"), Map.entry('B', "ठ"), Map.entry('C', "ब्"),
            Map.entry('D', "क्"), Map.entry('E', "म्"), Map.entry('F', "थ्"),
            Map.entry('G', "ळ"), Map.entry('H', "भ्"), Map.entry('I', "प्"),
            Map.entry('J', "श्र"), Map.entry('K', "ज्ञ"), Map.entry('L', "स्"),
            Map.entry('M', "ड"), Map.entry('N', "छ"), Map.entry('O', "व्"),
            Map.entry('P', "च्"), Map.entry('Q', String.valueOf(HOOK)), Map.entry('R', "त्"),
            Map.entry('S', "ै"), Map.entry('T', "ज्"), Map.entry('U', "न्"),
            Map.entry('V', "ट"), Map.entry('W', "ॅ"), Map.entry('X', "ग्"),
            Map.entry('Y', "ल्"), Map.entry('Z', String.valueOf(REPH)), Map.entry('[', "ख्"),
            Map.entry('\\', "?"), Map.entry(']', ","), Map.entry('^', "‘"), Map.entry('_', ";"),
            Map.entry('`', "ृ"), Map.entry('a', "ं"), Map.entry('b', "इ"), Map.entry('c', "ब"),
            Map.entry('d', "क"), Map.entry('e', "म"), Map.entry('f', String.valueOf(PRE_I)),
            Map.entry('g', "ह"), Map.entry('h', "ी"), Map.entry('i', "प"), Map.entry('j', "र"),
            Map.entry('k', "ा"), Map.entry('l', "स"), Map.entry('m', "उ"), Map.entry('n', "द"),
            Map.entry('o', "व"), Map.entry('p', "च"), Map.entry('q', "ु"), Map.entry('r', "त"),
            Map.entry('s', "े"), Map.entry('t', "ज"), Map.entry('u', "न"), Map.entry('v', "अ"),
            Map.entry('w', "ू"), Map.entry('x', "ग"), Map.entry('y', "ल"), Map.entry('z', "्र"),
            Map.entry('{', "क्ष्"), Map.entry('|', "द्य"),
            Map.entry('}', "द्व"), Map.entry('~', "्"), Map.entry(' ', " "),
            Map.entry('¡', "ँ"), Map.entry('£', String.valueOf(PRE_I_REPH)), Map.entry('ª', "्र"),
            Map.entry('­', "."), Map.entry('¯', String.valueOf(PRE_I_ANUS)),
            Map.entry('±', String.valueOf(REPH_ANUS)), Map.entry('µ', "—"), Map.entry('¶', "“"),
            Map.entry('·', "*"), Map.entry('¸', "”"), Map.entry('¹', "["), Map.entry('º', "]"),
            Map.entry('»', "%"), Map.entry('¼', "द्ध"), Map.entry('½', "ऋ"),
            Map.entry('¾', "="), Map.entry('Á', "द्म"), Map.entry('Â', "न्न"),
            Map.entry('Ä', "ङ"), Map.entry('Å', "ऊ"), Map.entry('É', "ह्न"),
            Map.entry('Í', "ऋ"), Map.entry('Ï', "स्त्र"),
            Map.entry('Ñ', "कृ"), Map.entry('×', "ञ्"), Map.entry('Ø', "क्र"),
            Map.entry('Ù', "त्त्"), Map.entry('Ú', "फ्र"),
            Map.entry('Ü', "श्"), Map.entry('Ý', "फ्"), Map.entry('Þ', "ह्"),
            Map.entry('ß', "ह्र"), Map.entry('à', "ह्व"),
            Map.entry('á', "ह्य"), Map.entry('â', "हृ"),
            Map.entry('ã', "ह्म"), Map.entry('æ', "द्र"),
            Map.entry('ç', "प्र"), Map.entry('è', "ध्"), Map.entry('ê', "ट्"),
            Map.entry('í', "द्द"), Map.entry('ï', "ज़"), Map.entry('ð', BELOW_TA),
            Map.entry('ñ', "॰"), Map.entry('ò', "ठ"), Map.entry('˜', "ु"),
            Map.entry('’', "ष्ठ"), Map.entry('”', "ज़्"),
            Map.entry('•', "ख"), Map.entry('™', "ू"));

    /** KrutiDev501 shares the map but for three glyphs: / is a half ध, ) is द्ध, and Q a plain फ with no hook. */
    private static final Map<Character, String> KRUTI;

    static {
        Map<Character, String> kruti = new HashMap<>(GLYPH);
        kruti.put('/', "ध्");
        kruti.put(')', "द्ध");
        kruti.put('Q', "फ");
        KRUTI = Map.copyOf(kruti);
    }

    /**
     * The combining mark PDFBox makes of a glyph code Unicode calls an accent, back to the glyph (D16). Its
     * text stripper folds such a glyph into the letter before it when their boxes overlap — the ृ keyed on
     * the grave accent, the short i with its anusvara keyed on the macron, on any tightened line — and the
     * decoder then met U+0300 where the glyph had been: प्रकृति came out as प्रव̀फति and the check named 27
     * words of the first Hindi book as not on their pages. The grave and the circumflex have PDFBox's own
     * table entries; every other accent it folds through its compatibility decomposition, as here.
     */
    private static final Map<Character, Character> FOLDED;

    static {
        Map<Character, Character> folded = new HashMap<>();
        for (char glyph : GLYPH.keySet()) {
            if (Character.getType(glyph) != Character.MODIFIER_SYMBOL) {
                continue;
            }
            String mark = glyph == '`' ? "̀" : glyph == '^' ? "̂"
                    : Normalizer.normalize(String.valueOf(glyph), Normalizer.Form.NFKC).strip();
            if (mark.length() == 1 && mark.charAt(0) >= '̀' && mark.charAt(0) <= 'ͯ') {
                folded.put(mark.charAt(0), glyph);
            }
        }
        FOLDED = Map.copyOf(folded);
    }

    /**
     * The letter the hook glyph makes of the one before it: व → क, प → फ, their half forms, उ → ऊ — and र → रु,
     * which phy11-part2 types once (विरुद्ध, ch 8 p8) where every other book uses the रु glyph (D16).
     */
    private static final Map<Character, String> HOOK_BASE = Map.of(
            'o', "क", 'i', "फ", 'O', "क्", 'I', "फ्", 'm', "ऊ", 'j', "रु");

    /** The glyphs that may sit between a letter and its hook — marks and signs typed in between. */
    private static final String HOOK_SKIP = "sSqwa`¡Zz-˜™W±~+k";

    private static final String CONSONANT = "क-हक़-य़";
    private static final String SIGNS = "ा-ौॅॉॢॣ";
    private static final String MARKS = "ँं";
    private static final Pattern CLUSTER =
            Pattern.compile("[" + CONSONANT + "]़?(?:्[" + CONSONANT + "]़?)*");
    private static final Pattern MARK_BEFORE_SIGN = Pattern.compile("([" + MARKS + "])([" + SIGNS + "]+)");
    /** A sign typed twice, which the raster draws once on itself: esas = में, o``f¼ = वृद्धि (D16). */
    private static final Pattern DOUBLED_SIGN = Pattern.compile("([ेैुूृ])\\1");

    /**
     * An anusvara keyed straight after a half form, inside its cluster, belongs after the cluster: fLaizx =
     * स्प्रिंग, Laith = स्पंजी — the four places in the 79 chapter files, each read off the print (D16).
     */
    private static final Pattern ANUSVARA_IN_CLUSTER =
            Pattern.compile("्ं([" + CONSONANT + "]़?(?:्[" + CONSONANT + "]़?)*)");

    /**
     * A nukta or a rakar typed after the vowel sign or mark it comes before in Unicode: i<s+axs = पढ़ेंगे,
     * xszV = ग्रेट, isazQp = फ्रेंच (D16: the e-sign and the anusvara both before the rakar).
     */
    private static final Pattern SIGN_BEFORE_NUKTA_OR_RAKAR = Pattern.compile("([" + SIGNS + MARKS + "]+)(़|्र)");

    /** The below-base ट typed after a vowel sign of its cluster: [kêsð = खट्टे. */
    private static final Pattern SIGN_BEFORE_BELOW_TA = Pattern.compile("([" + SIGNS + MARKS + "]+)(" + BELOW_TA + ")");

    /**
     * Blanks after a short-i glyph, which is typed before its cluster and never ends a word: PDFBox folds the ¯
     * of किंतु into the space glyph before it, and the gap after that glyph reads as a second space (D16).
     */
    private static final Pattern BLANK_AFTER_PRE_I = Pattern.compile("([f£¯])\\s+");

    /**
     * A rakar typed after a short-i glyph belongs to the letter before it, as no cluster has begun: ifzrcy =
     * प्रतिबल, isfz{kr = प्रेक्षित — the three places in the 79 chapter files (D16).
     */
    private static final Pattern RAKAR_AFTER_PRE_I = Pattern.compile("([f£¯])([zª])");

    /**
     * The plain Chanakya faces — Chanakya, ChanakyaThin, ChanakyaBold, which set a single conjunct inside a
     * Walkman-Chanakya word — key four glyphs Walkman does not: ^ is ट्ट (पट्टियों, चट्टान, संघट्ट), b is ड्ढ
     * (गड्ढे), _ is ट्ठ (भट्ठी) and ï the half ज़ the aa-stroke completes (क्वार्ट्ज़), each read off the print in
     * every place the 79 chapter files use them (D16). Given here as the Walkman codes that build them, so a
     * run mixing the faces decodes as one.
     */
    private static final Map<Character, String> PLAIN_FACE = Map.of('^', "êV", 'b', "M~<", '_', "êB", 'ï', "”");

    private ChanakyaDecoder() {
    }

    /** Whether a run in this font is Chanakya or KrutiDev glyph text rather than Unicode. */
    static boolean isDevanagariFont(String font) {
        String name = font == null ? "" : font.toLowerCase(Locale.ROOT);
        return name.contains("chanakya") || name.contains("krutidev");
    }

    /** One run of glyph text in its font as the Devanagari it prints; a Unicode font's run comes back as it is. */
    static String decode(String text, String font) {
        if (text == null || !isDevanagariFont(font)) {
            return text;
        }
        boolean kruti = font.toLowerCase(Locale.ROOT).contains("krutidev");
        String typed = anusvaraAfterPreI(RAKAR_AFTER_PRE_I.matcher(
                BLANK_AFTER_PRE_I.matcher(unfolded(text)).replaceAll("$1")).replaceAll("$2$1"));
        String s = String.join("", glyphs(typed, kruti ? KRUTI : GLYPH, !kruti));
        // the below-base ट joins its cluster: after the half ट its halant is already there
        s = SIGN_BEFORE_BELOW_TA.matcher(s).replaceAll("$2$1").replace("्" + BELOW_TA, BELOW_TA);
        s = s.replace("इ" + REPH, "ई").replace("इ" + REPH_ANUS, "ईं");
        s = MARK_BEFORE_SIGN.matcher(s).replaceAll("$2$1");
        // before any vowel letter takes a sign into itself, so ,ss = ऐसा keeps none over
        s = DOUBLED_SIGN.matcher(s).replaceAll("$1");
        // a reph typed between a half form and its aa-stroke: iznf'Zkr = प्रदर्शित
        s = s.replace("्" + REPH + "ा", String.valueOf(REPH));
        // a half form completed by the aa-stroke is the full letter, the nukta first: T+k = ज़
        s = s.replace("़्", "़्").replace("्ा", "");
        s = s.replace("ेा", "ो").replace("ैा", "ौ");
        s = s.replace("अॅा", "ऑ").replace("ॅा", "ॉ");
        s = s.replace("अा", "आ").replace("आे", "ओ").replace("आै", "औ")
                .replace("आॅ", "ऑ");
        s = s.replace("ाे", "ो").replace("ाै", "ौ").replace("ाॅ", "ॉ")
                .replace("एे", "ऐ");
        s = DOUBLED_SIGN.matcher(s).replaceAll("$1");
        s = ANUSVARA_IN_CLUSTER.matcher(s).replaceAll("्$1ं");
        s = movePreI(s);
        s = placeReph(s);
        s = MARK_BEFORE_SIGN.matcher(s).replaceAll("$2$1");
        s = s.replace("ाे", "ो").replace("ाै", "ौ");
        // signs typed in an order Unicode does not allow, which the raster draws as the word (D16): a nukta or
        // rakar after its vowel sign, a sign typed again round a mark (ksas = ओं, aa = ं) or under the ी or ै
        // flag (gSsa = हैं)
        s = SIGN_BEFORE_NUKTA_OR_RAKAR.matcher(s).replaceAll("$2$1");
        s = s.replace("ोे", "ो").replace("ओे", "ओ").replace("ंं", "ं").replace("ीे", "ी").replace("ैे", "ै");
        return Normalizer.normalize(s, Normalizer.Form.NFC);
    }

    /**
     * One glyph's code as the Walkman faces key it: a plain Chanakya face's own conjuncts rewritten, any
     * other glyph as it is. The layer reads a line's Chanakya glyphs as one run in its first glyph's font.
     */
    static String asWalkman(String glyph, String font) {
        String name = font == null ? "" : font.toLowerCase(Locale.ROOT);
        if (glyph == null || glyph.length() != 1 || !name.contains("chanakya") || name.contains("walkman")) {
            return glyph;
        }
        return PLAIN_FACE.getOrDefault(glyph.charAt(0), glyph);
    }

    /**
     * An anusvara typed after a short-i glyph: after a letter it is that letter's, the short i still waiting for
     * its cluster (vkjfaHkd = आरंभिक, vfare = अंतिम); opening a word the two are the short i with its anusvara
     * (fatad = जिंक) — every one of the 14 places in the 79 chapter files reads so (D16).
     */
    private static String anusvaraAfterPreI(String typed) {
        StringBuilder out = new StringBuilder(typed);
        for (int at = out.indexOf("fa"); at >= 0; at = out.indexOf("fa", at + 1)) {
            if (at > 0 && isLetterCode(out.charAt(at - 1))) {
                out.setCharAt(at, 'a');
                out.setCharAt(at + 1, 'f');
            } else {
                out.replace(at, at + 2, "¯");
            }
        }
        return out.toString();
    }

    /** Whether a glyph code prints a letter or sign, rather than a blank, a digit or punctuation. */
    private static boolean isLetterCode(char code) {
        String printed = code == 'Q' ? "फ" : GLYPH.getOrDefault(code, "");
        return printed.chars().anyMatch(c -> c >= 'ऀ' && c <= 'ॿ' && c != '।' && c != '॥' && (c < '०' || c > '९'));
    }

    /** The glyph text with every folded accent read back as the glyph code it was. */
    private static String unfolded(String text) {
        StringBuilder out = null;
        for (int at = 0; at < text.length(); at++) {
            Character glyph = FOLDED.get(text.charAt(at));
            if (glyph != null) {
                out = out == null ? new StringBuilder(text) : out;
                out.setCharAt(at, glyph);
            }
        }
        return out == null ? text : out.toString();
    }

    /** Glyph codes into Unicode pieces, each hook resolved onto the letter it changes. */
    private static String[] glyphs(String text, Map<Character, String> table, boolean hooks) {
        char[] chars = text.toCharArray();
        String[] out = new String[chars.length];
        for (int j = 0; hooks && j < chars.length; j++) {
            if (chars[j] != 'Q') {
                continue;
            }
            int k = j - 1;
            boolean nukta = false;
            while (k >= 0 && HOOK_SKIP.indexOf(chars[k]) >= 0) {
                nukta |= chars[k] == '-';
                k--;
            }
            if (k >= 0 && HOOK_BASE.containsKey(chars[k]) && out[k] == null) {
                char base = chars[k];
                String letter = HOOK_BASE.get(base);
                int aa = -1;
                for (int m = k + 1; m < j && aa < 0; m++) {
                    aa = chars[m] == 'k' ? m : -1;
                }
                if ((base == 'O' || base == 'I') && aa >= 0) {
                    // the half form and the aa-stroke are the full letter: OksQ = के
                    letter = letter.substring(0, letter.length() - 1);
                    out[aa] = "";
                }
                out[k] = letter + (nukta ? "़" : "");
                for (int m = k + 1; nukta && m < j; m++) {
                    if (chars[m] == '-') {
                        out[m] = "";
                    }
                }
                out[j] = "";
            } else {
                out[j] = "फ";
            }
        }
        for (int j = 0; j < chars.length; j++) {
            if (out[j] == null) {
                out[j] = table.getOrDefault(chars[j], String.valueOf(chars[j]));
            }
        }
        return out;
    }

    /** The short-i glyph, typed before its cluster, after it — carrying a reph or anusvara where it did. */
    private static String movePreI(String s) {
        while (true) {
            int at = indexOfAny(s, PRE_I, PRE_I_REPH, PRE_I_ANUS);
            if (at < 0) {
                return s;
            }
            char mark = s.charAt(at);
            String tail = "ि" + (mark == PRE_I_ANUS ? "ं" : "");
            Matcher cluster = CLUSTER.matcher(s).region(at + 1, s.length());
            if (!cluster.lookingAt()) {
                s = s.substring(0, at) + tail + s.substring(at + 1);
                continue;
            }
            String head = mark == PRE_I_REPH ? "र्" : "";
            s = s.substring(0, at) + head + cluster.group() + tail + s.substring(cluster.end());
        }
    }

    /** The reph, typed after its cluster's signs, before the cluster as र्; with no consonant, इ + reph is ई. */
    private static String placeReph(String s) {
        while (true) {
            int at = indexOfAny(s, REPH, REPH_ANUS);
            if (at < 0) {
                return s;
            }
            String anusvara = s.charAt(at) == REPH_ANUS ? "ं" : "";
            int start = at;
            while (start > 0 && isSignMarkOrNukta(s.charAt(start - 1))) {
                start--;
            }
            if (start > 0 && s.charAt(start - 1) == '़') {
                start--;
            }
            if (start > 1 && s.charAt(start - 1) == '्' && isConsonant(s.charAt(start - 2))) {
                start--;
            }
            if (start > 0 && isConsonant(s.charAt(start - 1))) {
                start--;
                while (start >= 2 && s.charAt(start - 1) == '्' && isConsonant(s.charAt(start - 2))) {
                    start -= 2;
                }
                s = s.substring(0, start) + "र्" + s.substring(start, at) + anusvara + s.substring(at + 1);
            } else if (at > 0 && s.charAt(at - 1) == 'इ') {
                s = s.substring(0, at - 1) + "ई" + anusvara + s.substring(at + 1);
            } else {
                s = s.substring(0, at) + anusvara + s.substring(at + 1);
            }
        }
    }

    private static boolean isConsonant(char c) {
        return (c >= 'क' && c <= 'ह') || (c >= 'क़' && c <= 'य़');
    }

    private static boolean isSignMarkOrNukta(char c) {
        return (c >= 'ा' && c <= 'ौ') || c == 'ॢ' || c == 'ॣ'
                || c == 'ँ' || c == 'ं' || c == '़';
    }

    private static int indexOfAny(String s, char... marks) {
        for (int index = 0; index < s.length(); index++) {
            for (char mark : marks) {
                if (s.charAt(index) == mark) {
                    return index;
                }
            }
        }
        return -1;
    }
}

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
 * word contexts across all 79 Hindi chapter files, which it decodes with no unmapped glyph (≈0.03% of words
 * suspect, each a layer artefact — a word cut across lines, a typo in the layer). Typed order is not reading
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
            Map.entry('ß', "श्व"), Map.entry('à', "ह्व"),
            Map.entry('á', "ह्य"), Map.entry('â', "हृ"),
            Map.entry('ã', "ह्म"), Map.entry('æ', "द्र"),
            Map.entry('ç', "प्र"), Map.entry('è', "ध्"), Map.entry('ê', "ट्"),
            Map.entry('í', "द्द"), Map.entry('ï', "ज़"), Map.entry('ð', "ट"),
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

    /** The letter the hook glyph makes of the one before it: व → क, प → फ, their half forms, and उ → ऊ. */
    private static final Map<Character, String> HOOK_BASE = Map.of(
            'o', "क", 'i', "फ", 'O', "क्", 'I', "फ्", 'm', "ऊ");

    /** The glyphs that may sit between a letter and its hook — marks and signs typed in between. */
    private static final String HOOK_SKIP = "sSqwa`¡Zz-˜™W±~+k";

    private static final String CONSONANT = "क-हक़-य़";
    private static final String SIGNS = "ा-ौॅॉॢॣ";
    private static final String MARKS = "ँं";
    private static final Pattern CLUSTER =
            Pattern.compile("[" + CONSONANT + "]़?(?:्[" + CONSONANT + "]़?)*");
    private static final Pattern MARK_BEFORE_SIGN = Pattern.compile("([" + MARKS + "])([" + SIGNS + "]+)");
    private static final Pattern DOUBLED_SIGN = Pattern.compile("([ेैुू])\\1");

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
        String s = String.join("", glyphs(text, kruti ? KRUTI : GLYPH, !kruti));
        s = s.replace("इ" + REPH, "ई").replace("इ" + REPH_ANUS, "ईं");
        s = MARK_BEFORE_SIGN.matcher(s).replaceAll("$2$1");
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
        // a sign typed twice prints once: esas = में
        s = DOUBLED_SIGN.matcher(s).replaceAll("$1");
        s = movePreI(s);
        s = placeReph(s);
        s = MARK_BEFORE_SIGN.matcher(s).replaceAll("$2$1");
        s = s.replace("ाे", "ो").replace("ाै", "ौ");
        return Normalizer.normalize(s, Normalizer.Form.NFC);
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

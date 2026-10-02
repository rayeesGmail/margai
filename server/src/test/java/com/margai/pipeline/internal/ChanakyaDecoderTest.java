package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The Hindi books' Walkman-Chanakya (and Chemistry 12's KrutiDev501) glyph text into Devanagari — a check on
 * the Hindi transcription, never a source of its text (SPEC §12.2, DECISIONS 2026-10-02). The cases are the
 * scratchpad decoder's own, each read off a crop of the print when the map was built (D16).
 */
class ChanakyaDecoderTest {

    private static final String WALKMAN = "Walkman-Chanakya905Normal";
    private static final String KRUTI = "KrutiDev501,Bold";

    /** Glyph text, then the Devanagari the page prints. */
    private static final String[] WALKMAN_CASES = {
        "HkkSfrdh", "भौतिकी", "lkjka'k", "सारांश", "lkjak'k", "सारांश", "Lkkjka'k", "सारांश", "vH;kl", "अभ्यास",
        "fopkj", "विचार", "ifjf'k\"V", "परिशिष्ट", "mÙkj", "उत्तर", "osQ", "के", "iQyLo:i", "फलस्वरूप", "oqQN", "कुछ",
        "mQtkZ", "ऊर्जा", "izn£'kr", "प्रदर्शित", "ifjo£rr", "परिवर्तित", "fu£n\"V", "निर्दिष्ट", "¯cnq", "बिंदु",
        "inkFkks±", "पदार्थों", "b±èku", "ईंधन", "fLFkfr", "स्थिति", "vkWDlhtu", "ऑक्सीजन", "lacaf/r", "संबंधित",
        "dk;Z", "कार्य", "fo|qr~", "विद्युत्", "bysDVªkWu", "इलेक्ट्रॉन", "ukbVªkstu", "नाइट्रोजन", "ÝyDl", "फ्लक्स",
        "feêðh", "मिट्टी", "Ük`a[kyk", "शृंखला", "ckÞ;", "बाह्य", "fpÉ", "चिह्न", "ßkl", "श्वास", "ân;", "हृदय",
        "i`’", "पृष्ठ", "lkb”k", "साइज़", "ns•k", "देखा", "fliZQ", "सिर्फ", "fiQj", "फिर", "isQiQM+s", "फेफड़े",
        "14-4-2", "14.4.2", "Hkkstu", "भोजन", "vkSj", "और", ",slk", "ऐसा", "bZ'oj", "ईश्वर", "tgk¡", "जहाँ",
        "fopkj.kh; fo\"k;", "विचारणीय विषय", "vè;;u", "अध्ययन", "O;kolkf;d", "व्यावसायिक", "Øe", "क्रम",
        "izfrfØ;k", "प्रतिक्रिया", "èkkjk", "धारा", "i+Qksdl", "फ़ोकस", "I+kQhukWy", "फ़ीनॉल", "OksQ", "के",
        "vfHkfOkzQ;k", "अभिक्रिया", "i`Fko~Q", "पृथक्", "dkI+kQh", "काफ़ी", "lsoaQM", "सेकंड", "iznf'Zkr", "प्रदर्शित",
        "esas", "में", "gSaSA", "हैं।", "bysDVªWku", "इलेक्ट्रॉन", "vf/d", "अधिक", "/kjk", "धारा",
    };

    /** KrutiDev501: its / is a half ध, its ) is द्ध, and its Q a plain फ with no hook. */
    private static final String[] KRUTI_CASES = {
        "fof/k;k¡", "विधियाँ", "ukei)fr", "नामपद्धति", "fl)kar", "सिद्धांत", "b±/ku", "ईंधन", "usu~ZLV", "नेर्न्स्ट",
        "ikB~;fufgr", "पाठ्यनिहित", "iz'u", "प्रश्न",
    };

    @Test
    void everyWordTheMapWasBuiltOnDecodesToWhatThePagePrints() {
        assertThat(mismatches(WALKMAN_CASES, WALKMAN)).isEmpty();
        assertThat(WALKMAN_CASES.length / 2).isEqualTo(63);
    }

    @Test
    void krutiDevHasItsOwnThreeGlyphs() {
        assertThat(mismatches(KRUTI_CASES, KRUTI)).isEmpty();
    }

    /** Bookman, Times and Symbol spans are already Unicode: only the Devanagari fonts are decoded. */
    @Test
    void aLatinFontPassesThroughUntouched() {
        assertThat(ChanakyaDecoder.decode("3.00 × 10", "Bookman-Light")).isEqualTo("3.00 × 10");
        assertThat(ChanakyaDecoder.isDevanagariFont("Walkman-Chanakya905Bold")).isTrue();
        assertThat(ChanakyaDecoder.isDevanagariFont("ABCDEF+KrutiDev501,Bold")).isTrue();
        assertThat(ChanakyaDecoder.isDevanagariFont("Symbol")).isFalse();
    }

    private static List<String> mismatches(String[] cases, String font) {
        List<String> wrong = new ArrayList<>();
        for (int index = 0; index < cases.length; index += 2) {
            String decoded = ChanakyaDecoder.decode(cases[index], font);
            if (!decoded.equals(cases[index + 1])) {
                wrong.add(cases[index] + " → " + decoded + " (the page prints " + cases[index + 1] + ")");
            }
        }
        return wrong;
    }
}

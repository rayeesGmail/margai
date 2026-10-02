package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.tasks.NcertPage;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The Hindi print sets the visarga and the colon with one glyph, and the transcriber wrote a quarter of
 * the first Hindi book's visarga words with a colon — 97 of them, अत: beside अतः on the same page
 * (phy11-part1, D16). A list of the words settles the ones it knows; a colon it cannot place is named.
 */
class VisargaColonsTest {

    @Test
    void aListedWordGluedToAColonTakesItsVisarga() {
        VisargaColons.Applied applied = VisargaColons.apply(List.of(page(4,
                p("2.3", "अत: v = v_0 + at तथा क्रमश: x और y; परित: घूर्णन, प्राय: ऐसा होता है।"))));

        assertThat(applied.pages().getFirst().paragraphs().getFirst().text())
                .isEqualTo("अतः v = v_0 + at तथा क्रमशः x और y; परितः घूर्णन, प्रायः ऐसा होता है।");
        assertThat(applied.notes()).containsExactly(
                "ch 2 p4 §2.3: a visarga written as a colon — अतः, क्रमशः, परितः, प्रायः");
        assertThat(applied.undecided()).isEmpty();
    }

    /** A colon that ends a clause stays one, glued to its word or not: the print spaces it either way. */
    @Test
    void aColonThatIsAColonStays() {
        String text = "निम्नलिखित नियम प्राप्त होते हैं : पहला। आवश्यकता है: दूसरा। हल : अनुपात 1:2 तथा T : समय।";

        VisargaColons.Applied applied = VisargaColons.apply(List.of(page(4, p("2.3", text))));

        assertThat(applied.pages().getFirst().paragraphs().getFirst().text()).isEqualTo(text);
        assertThat(applied.notes()).isEmpty();
        assertThat(applied.undecided()).isEmpty();
    }

    /** A visarga inside a word follows one of a few prefixes; nothing else glued on both sides is touched. */
    @Test
    void aVisargaInsideAWordFollowsItsPrefix() {
        VisargaColons.Applied applied = VisargaColons.apply(List.of(page(4,
                p("2.3", "नि:संदेह यह दु:खद है; ऊँचाई:आधार का अनुपात।"))));

        assertThat(applied.pages().getFirst().paragraphs().getFirst().text())
                .isEqualTo("निःसंदेह यह दुःखद है; ऊँचाई:आधार का अनुपात।");
        assertThat(applied.notes()).containsExactly("ch 2 p4 §2.3: a visarga written as a colon — निः, दुः");
    }

    /**
     * The words ending in a visarga are an open class (any adverb in -तः), and a noun ending in त can
     * take a real colon ("ज्ञात: m = 5 kg"). A word the list does not know is left as written and named,
     * for a ruling or for the list — never guessed.
     */
    @Test
    void anUnlistedWordEndingInAConsonantIsLeftAndNamed() {
        String text = "ज्ञात: m = 5 kg तथा अनन्त: हम पाते हैं।";

        VisargaColons.Applied applied = VisargaColons.apply(List.of(page(2, p("2.2", text))));

        assertThat(applied.pages().getFirst().paragraphs().getFirst().text()).isEqualTo(text);
        assertThat(applied.notes()).isEmpty();
        assertThat(applied.undecided()).containsExactly("ch 2 p2 §2.2: ज्ञात:, अनन्त:");
    }

    @Test
    void aPageWithNothingToRewriteIsHandedBackAsItCame() {
        ExtractedPage page = page(3, p("2.2", "अतः v = 0 तथा क्रमशः x एवं y।"));

        VisargaColons.Applied applied = VisargaColons.apply(List.of(page));

        assertThat(applied.pages().getFirst()).isSameAs(page);
        assertThat(applied.notes()).isEmpty();
    }

    private static ExtractedPage page(int page, NcertPage.Paragraph... paragraphs) {
        return new ExtractedPage((short) 2, page, new BigDecimal("0.90"), null, List.of(paragraphs), null);
    }

    private static NcertPage.Paragraph p(String section, String text) {
        return new NcertPage.Paragraph(section, text, false, List.of());
    }
}

package com.margai.common.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.common.api.ErrorCode;
import com.margai.common.api.Language;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * TECH_PLAN §3.3 and §8.1 "envelope for every error code": every {@link ErrorCode} and every
 * message kind the server sends has authored copy in all three languages, no line falls back to
 * English or to its key, and MessageFormat placeholders are filled.
 */
class MessageCatalogTest {

    private static final List<String> MESSAGE_KINDS = List.of("otp.email.subject", "otp.email.body", "otp.sms.body");

    private final MessageCatalog catalog = new MessageCatalog();

    @Test
    void everyErrorCodeHasCopyInEveryLanguage() {
        for (ErrorCode code : ErrorCode.values()) {
            String english = catalog.message(code.name(), Language.en);
            assertThat(english).as("%s in en", code).isNotBlank().isNotEqualTo(code.name()).doesNotContain("''");
            for (Language other : List.of(Language.hi, Language.hinglish)) {
                String translated = catalog.message(code.name(), other);
                assertThat(translated).as("%s in %s", code, other).isNotBlank().isNotEqualTo(code.name());
                assertThat(translated).as("%s in %s is authored, not the English fallback", code, other).isNotEqualTo(english);
            }
        }
    }

    @Test
    void everyMessageKindHasCopyWithItsPlaceholdersFilled() {
        for (String kind : MESSAGE_KINDS) {
            for (Language language : Language.values()) {
                String text = catalog.message(kind, language, "482913", 5);
                assertThat(text).as("%s in %s", kind, language).isNotBlank().isNotEqualTo(kind)
                        .contains("482913").doesNotContain("{0}").doesNotContain("{1}");
            }
        }
    }

    @Test
    void anUnknownKeyRendersAsItselfSoATestCatchesIt() {
        assertThat(catalog.message("no.such.key", Language.hi)).isEqualTo("no.such.key");
    }
}

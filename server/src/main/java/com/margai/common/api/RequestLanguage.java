package com.margai.common.api;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Which language a response speaks (TECH_PLAN §3.8): the principal's {@code lang} claim when the
 * caller is authenticated, otherwise {@code Accept-Language} on the public routes ({@code hi} →
 * Hindi, {@code hi-Latn} → Hinglish, anything else → English).
 */
public final class RequestLanguage {

    private RequestLanguage() {
    }

    public static Language of(HttpServletRequest request) {
        if (request.getAttribute(Principal.REQUEST_ATTRIBUTE) instanceof Principal principal) {
            return principal.language();
        }
        return fromAcceptLanguage(request.getHeader("Accept-Language"));
    }

    public static Language fromAcceptLanguage(String header) {
        if (header == null || header.isBlank()) {
            return Language.en;
        }
        List<Locale.LanguageRange> ranges;
        try {
            ranges = Locale.LanguageRange.parse(header);
        } catch (IllegalArgumentException malformed) {
            return Language.en;
        }
        for (Locale.LanguageRange range : ranges) {
            Locale locale = Locale.forLanguageTag(range.getRange());
            if ("hi".equals(locale.getLanguage())) {
                return "Latn".equalsIgnoreCase(locale.getScript()) ? Language.hinglish : Language.hi;
            }
            if ("en".equals(locale.getLanguage())) {
                return Language.en;
            }
        }
        return Language.en;
    }
}

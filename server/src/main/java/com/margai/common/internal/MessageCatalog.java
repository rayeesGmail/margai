package com.margai.common.internal;

import com.margai.common.api.Language;
import com.margai.common.api.Messages;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * {@link Messages} over {@code messages/messages_{en,hi,hinglish}.properties} (TECH_PLAN §3.3,
 * §11.7). Hinglish is its own bundle suffix on the server ({@code hinglish}), while the app's
 * locale is {@code hi_Latn} (§5.5); {@code Language} is the one mapping between them.
 */
@Component
class MessageCatalog implements Messages {

    private final MessageSource source;

    MessageCatalog() {
        ResourceBundleMessageSource bundles = new ResourceBundleMessageSource();
        bundles.setBasename("messages/messages");
        bundles.setDefaultEncoding("UTF-8");
        bundles.setFallbackToSystemLocale(false);
        bundles.setUseCodeAsDefaultMessage(false);
        // Every line goes through MessageFormat, so the authoring rule is one rule: double every apostrophe.
        bundles.setAlwaysUseMessageFormat(true);
        this.source = bundles;
    }

    @Override
    public String message(String key, Language language, Object... args) {
        try {
            return source.getMessage(key, args, locale(language));
        } catch (NoSuchMessageException missing) {
            return language == Language.en ? key : message(key, Language.en, args);
        }
    }

    static Locale locale(Language language) {
        return switch (language) {
            case en -> Locale.ENGLISH;
            case hi -> Locale.of("hi");
            case hinglish -> Locale.of("hinglish");
        };
    }
}

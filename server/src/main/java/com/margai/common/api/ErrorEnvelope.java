package com.margai.common.api;

import java.util.Map;

/**
 * The wire shape of every failure (TECH_PLAN §3.3): {@code {error: {code, message_en,
 * message_user_lang, details}}}. Serialised snake_case with nulls omitted (§11.3), so a failure
 * without details has no {@code details} key.
 */
public record ErrorEnvelope(Body error) {

    public record Body(String code, String messageEn, String messageUserLang, Map<String, Object> details) {
    }
}

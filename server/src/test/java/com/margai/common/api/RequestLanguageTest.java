package com.margai.common.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/** TECH_PLAN §3.8: principal language first, then Accept-Language, then English. */
class RequestLanguageTest {

    @Test
    void acceptLanguageMapsToTheThreeValues() {
        assertThat(RequestLanguage.fromAcceptLanguage("hi")).isEqualTo(Language.hi);
        assertThat(RequestLanguage.fromAcceptLanguage("hi-IN,en;q=0.8")).isEqualTo(Language.hi);
        assertThat(RequestLanguage.fromAcceptLanguage("hi-Latn")).isEqualTo(Language.hinglish);
        assertThat(RequestLanguage.fromAcceptLanguage("hi-Latn-IN;q=0.9,hi;q=0.8")).isEqualTo(Language.hinglish);
        assertThat(RequestLanguage.fromAcceptLanguage("en-IN")).isEqualTo(Language.en);
        assertThat(RequestLanguage.fromAcceptLanguage("fr-FR,de;q=0.5")).isEqualTo(Language.en);
        assertThat(RequestLanguage.fromAcceptLanguage("ta-IN,hi;q=0.5")).as("first supported wins").isEqualTo(Language.hi);
        assertThat(RequestLanguage.fromAcceptLanguage(null)).isEqualTo(Language.en);
        assertThat(RequestLanguage.fromAcceptLanguage("  ")).isEqualTo(Language.en);
        assertThat(RequestLanguage.fromAcceptLanguage("not a header ;;; q=")).isEqualTo(Language.en);
    }

    @Test
    void principalWinsOverTheHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "hi");
        request.setAttribute(Principal.REQUEST_ATTRIBUTE, new Principal(UUID.randomUUID(), UserRole.student, Language.hinglish));

        assertThat(RequestLanguage.of(request)).isEqualTo(Language.hinglish);
    }

    @Test
    void headerAloneOnPublicRoutes() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "hi");

        assertThat(RequestLanguage.of(request)).isEqualTo(Language.hi);
        assertThat(RequestLanguage.of(new MockHttpServletRequest())).isEqualTo(Language.en);
    }
}

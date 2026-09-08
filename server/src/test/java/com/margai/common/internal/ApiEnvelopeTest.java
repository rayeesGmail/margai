package com.margai.common.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * Controller slice (TECH_PLAN §8.1 "envelope for every error code"): typed outcomes, validation
 * failures, unknown routes and bugs all render {@code {error: {code, message_en,
 * message_user_lang, details}}} (§3.3); the language follows {@code Accept-Language} on public
 * routes (§3.8); {@code X-Request-Id} is echoed or minted (§1.5); bodies are snake_case with
 * nulls omitted (§11.3); a bug never leaks its message or stack.
 */
@WebMvcTest(controllers = ProbeController.class)
@Import({ApiExceptionHandler.class, DefaultErrorResponses.class, MessageCatalog.class, RequestIdFilter.class})
class ApiEnvelopeTest {

    private static final String UUID_SHAPE = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    /** This slice is about the envelope, not the chain ({@code SecurityChainTest} covers that): let everything through. */
    @TestConfiguration(proxyBeanMethods = false)
    static class PermitEverything {

        @Bean
        SecurityFilterChain permitEverything(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(routes -> routes.anyRequest().permitAll())
                    .build();
        }
    }

    @Autowired
    private MockMvcTester mvc;

    @Test
    void typedOutcomeRendersTheEnvelopeWithItsStatusAndDetails() {
        MvcTestResult result = mvc.get().uri("/api/v1/probe/otp-invalid").exchange();

        assertThat(result).hasStatus(401);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_INVALID");
        assertThat(result).bodyJson().extractingPath("$.error.message_en").isEqualTo("That code didn't match. Try once more.");
        assertThat(result).bodyJson().extractingPath("$.error.message_user_lang")
                .isEqualTo("That code didn't match. Try once more.");
        assertThat(result).bodyJson().extractingPath("$.error.details.attempts_left").isEqualTo(2);
        assertThat(result.getResponse().getHeader("X-Request-Id")).matches(UUID_SHAPE);
    }

    @Test
    void acceptLanguagePicksTheUserLanguageOnPublicRoutes() {
        MvcTestResult hindi = mvc.get().uri("/api/v1/probe/otp-invalid").header("Accept-Language", "hi-IN,hi;q=0.9").exchange();
        MvcTestResult hinglish = mvc.get().uri("/api/v1/probe/otp-invalid").header("Accept-Language", "hi-Latn").exchange();
        MvcTestResult french = mvc.get().uri("/api/v1/probe/otp-invalid").header("Accept-Language", "fr-FR").exchange();

        assertThat(hindi).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("यह कोड मेल नहीं खाया। एक बार और कोशिश करें।");
        assertThat(hindi).bodyJson().extractingPath("$.error.message_en").isEqualTo("That code didn't match. Try once more.");
        assertThat(hinglish).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("Code match nahi hua. Ek baar aur try karo.");
        assertThat(french).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("That code didn't match. Try once more.");
    }

    @Test
    void requestIdIsEchoedWhenPlainAndMintedOtherwise() {
        MvcTestResult echoed = mvc.get().uri("/api/v1/probe/shape").header("X-Request-Id", "client-7f3.a_b").exchange();
        MvcTestResult minted = mvc.get().uri("/api/v1/probe/shape").header("X-Request-Id", "<script>alert(1)</script>").exchange();

        assertThat(echoed.getResponse().getHeader("X-Request-Id")).isEqualTo("client-7f3.a_b");
        assertThat(minted.getResponse().getHeader("X-Request-Id")).matches(UUID_SHAPE);
    }

    @Test
    void bodiesAreSnakeCaseWithNullsOmitted() {
        MvcTestResult result = mvc.get().uri("/api/v1/probe/shape").exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.some_field").isEqualTo("value");
        assertThat(result).bodyJson().extractingPath("$.another_one").isEqualTo(2);
        assertThat(result).bodyJson().doesNotHavePath("$.absent_when_null");
        assertThat(result).bodyJson().doesNotHavePath("$.someField");
    }

    @Test
    void beanValidationFailureListsTheFields() {
        MvcTestResult result = mvc.post().uri("/api/v1/probe/validate").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"count\":0}").exchange();

        assertThat(result).hasStatus(400);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(result).bodyJson().extractingPath("$.error.details.name").isEqualTo("not_blank");
        assertThat(result).bodyJson().extractingPath("$.error.details.count").isEqualTo("min");
        assertThat(result).bodyJson().extractingPath("$.error.message_en").isEqualTo("Some details don't look right. Have a look and try again.");
    }

    @Test
    void validationDetailsAreReasonCodesNeverProse() {
        assertThat(ApiExceptionHandler.reasonCode("NotBlank", "{jakarta.validation.constraints.NotBlank.message}")).isEqualTo("not_blank");
        assertThat(ApiExceptionHandler.reasonCode("Pattern", "code.digits")).isEqualTo("code.digits");
        assertThat(ApiExceptionHandler.reasonCode("Size", "{jakarta.validation.constraints.Size.message}")).isEqualTo("size");
        assertThat(ApiExceptionHandler.reasonCode("NotBlank", "must not be blank")).isEqualTo("not_blank");
        assertThat(ApiExceptionHandler.reasonCode(null, "some prose here")).isEqualTo("invalid");
        assertThat(ApiExceptionHandler.reasonCode("Min", null)).isEqualTo("min");
    }

    @Test
    void reasonCodesDoNotDependOnTheRequestLocale() {
        MvcTestResult japanese = mvc.post().uri("/api/v1/probe/validate").contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "ja").content("{\"name\":\"\",\"count\":0}").exchange();

        assertThat(japanese).hasStatus(400);
        assertThat(japanese).bodyJson().extractingPath("$.error.details.name").isEqualTo("not_blank");
        assertThat(japanese).bodyJson().extractingPath("$.error.details.count").isEqualTo("min");
    }

    @Test
    void validationDetailsUseWireNames() {
        assertThat(ApiExceptionHandler.wireName("challengeId")).isEqualTo("challenge_id");
        assertThat(ApiExceptionHandler.wireName("refreshToken")).isEqualTo("refresh_token");
        assertThat(ApiExceptionHandler.wireName("user.displayName")).isEqualTo("user.display_name");
        assertThat(ApiExceptionHandler.wireName("phone")).isEqualTo("phone");
        assertThat(ApiExceptionHandler.wireName("hoursWeekdayX")).isEqualTo("hours_weekday_x");
        assertThat(ApiExceptionHandler.wireName(null)).isNull();
    }

    @Test
    void serviceRaisedValidationUsesTheSameShape() {
        MvcTestResult result = mvc.get().uri("/api/v1/probe/service-validation").exchange();

        assertThat(result).hasStatus(400);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(result).bodyJson().extractingPath("$.error.details.phone").isEqualTo("channel.unavailable");
    }

    @Test
    void malformedJsonIsAValidationFailure() {
        MvcTestResult result = mvc.post().uri("/api/v1/probe/validate").contentType(MediaType.APPLICATION_JSON)
                .content("{not json").exchange();

        assertThat(result).hasStatus(400);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(result).bodyJson().extractingPath("$.error.details.body").isEqualTo("malformed");
    }

    @Test
    void rateLimitedCarriesRetryAfterTwice() {
        MvcTestResult result = mvc.get().uri("/api/v1/probe/rate-limited").exchange();

        assertThat(result).hasStatus(429);
        assertThat(result.getResponse().getHeader("Retry-After")).isEqualTo("17");
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_RATE_LIMITED");
        assertThat(result).bodyJson().extractingPath("$.error.details.retry_after_s").isEqualTo(17);
    }

    @Test
    void unknownRouteIsNotFound() {
        MvcTestResult result = mvc.get().uri("/api/v1/probe/nowhere").exchange();

        assertThat(result).hasStatus(404);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("NOT_FOUND");
        assertThat(result).bodyJson().extractingPath("$.error.message_en").isEqualTo("We couldn't find that.");
    }

    @Test
    void aBugIsInternalWithTheRequestIdAndNothingElse() throws Exception {
        MvcTestResult result = mvc.get().uri("/api/v1/probe/boom").header("X-Request-Id", "bug-42").exchange();

        assertThat(result).hasStatus(500);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("INTERNAL");
        assertThat(result).bodyJson().extractingPath("$.error.details.request_id").isEqualTo("bug-42");
        assertThat(result.getResponse().getContentAsString())
                .doesNotContain("kaboom")
                .doesNotContain("IllegalStateException")
                .doesNotContain("at com.margai");
    }
}

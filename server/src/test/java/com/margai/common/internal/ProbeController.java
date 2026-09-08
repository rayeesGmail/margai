package com.margai.common.internal;

import com.margai.common.api.OtpException;
import com.margai.common.api.Principal;
import com.margai.common.api.RateLimitedException;
import com.margai.common.api.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test-only routes under {@code /api/v1/probe} that exercise the common web foundation — the
 * envelope for each outcome, validation, snake_case — and, from D7's security chain on, an
 * authenticated route that echoes the principal. Lives in the test tree, so production never
 * serves it; {@code @SpringBootTest} contexts pick it up by component scan.
 */
@RestController
@RequestMapping("/api/v1/probe")
public class ProbeController {

    public record Payload(@NotBlank String name, @Min(1) int count) {
    }

    public record Shape(String someField, Integer anotherOne, String absentWhenNull) {
    }

    @GetMapping("/shape")
    public Shape shape() {
        return new Shape("value", 2, null);
    }

    @PostMapping("/validate")
    public Map<String, Object> validate(@Valid @RequestBody Payload payload) {
        return Map.of("accepted", payload.name());
    }

    @GetMapping("/otp-invalid")
    public void otpInvalid() {
        throw OtpException.invalid(2);
    }

    @GetMapping("/rate-limited")
    public void rateLimited() {
        throw RateLimitedException.otp(Duration.ofSeconds(17));
    }

    @GetMapping("/service-validation")
    public void serviceValidation() {
        throw ValidationException.of("phone", "phone login is not available yet");
    }

    @GetMapping("/boom")
    public void boom() {
        throw new IllegalStateException("kaboom with an internal detail nobody should see");
    }

    @GetMapping("/whoami")
    public Map<String, Object> whoami(HttpServletRequest request) {
        Principal principal = (Principal) request.getAttribute(Principal.REQUEST_ATTRIBUTE);
        return Map.of("user_id", principal.userId().toString(), "role", principal.role().name(),
                "language", principal.language().name());
    }
}

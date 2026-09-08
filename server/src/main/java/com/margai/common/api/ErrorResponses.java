package com.margai.common.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Builds and writes the error envelope (TECH_PLAN §3.3) for code that runs outside Spring
 * MVC's exception handling — the security entry point and filters in {@code auth}. Inside
 * controllers, throw an {@link ApiException} instead; {@code common}'s advice does the rest.
 */
public interface ErrorResponses {

    /** The envelope for a code, in English and in the caller's language (§3.8). */
    ErrorEnvelope envelope(ErrorCode code, Map<String, ?> details, HttpServletRequest request);

    /** Writes the envelope with the code's status, JSON content type and any rate-limit header. */
    void write(HttpServletRequest request, HttpServletResponse response, ApiException failure) throws IOException;
}

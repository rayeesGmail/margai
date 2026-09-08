package com.margai.common.api;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A typed outcome the API reports to the client (TECH_PLAN §11.4: a sealed hierarchy that
 * carries the error code; everything else is a bug and maps to {@code INTERNAL}). Instances are
 * expected outcomes, not faults, so they carry no stack trace. {@code details} is the optional
 * machine-readable part of the envelope (§3.3): ids, seconds, counts and reason codes the app
 * maps to its own copy — never prose, so nothing in it needs translating (SPEC §3).
 */
public abstract sealed class ApiException extends RuntimeException
        permits AuthException, OtpException, RateLimitedException, ValidationException {

    private final ErrorCode code;
    private final Map<String, Object> details;

    protected ApiException(ErrorCode code, Map<String, ?> details) {
        super(code.name(), null, false, false);
        this.code = code;
        this.details = details == null || details.isEmpty()
                ? null
                : Collections.unmodifiableMap(new LinkedHashMap<>(details));
    }

    public ErrorCode code() {
        return code;
    }

    /** {@code null} when the failure has no details, so the envelope omits the field. */
    public Map<String, Object> details() {
        return details;
    }
}

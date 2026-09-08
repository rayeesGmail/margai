package com.margai.common.api;

import java.util.Map;

/**
 * {@code VALIDATION_FAILED} raised by a service for a rule Bean Validation cannot express.
 * {@code details} is field → <em>reason code</em> ({@code phone.invalid}, {@code channel.unavailable},
 * {@code not_blank}, …), never prose: the app renders the reason from its ARB files in the student's
 * language, so no student-facing copy lives in Java (SPEC §3, TECH_PLAN §3.3, §11.7). The handler
 * builds the same shape for annotation failures, so the client sees one contract.
 */
public final class ValidationException extends ApiException {

    private ValidationException(Map<String, String> fieldReasons) {
        super(ErrorCode.VALIDATION_FAILED, fieldReasons);
    }

    public static ValidationException of(String field, String reasonCode) {
        return new ValidationException(Map.of(field, reasonCode));
    }

    public static ValidationException of(Map<String, String> fieldReasons) {
        return new ValidationException(fieldReasons);
    }
}

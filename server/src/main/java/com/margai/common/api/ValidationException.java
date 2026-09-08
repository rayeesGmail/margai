package com.margai.common.api;

import java.util.Map;

/**
 * {@code VALIDATION_FAILED} raised by a service for a rule Bean Validation cannot express
 * (TECH_PLAN §3.3: {@code details} is field → message). The handler builds the same envelope
 * for annotation failures, so the client sees one shape.
 */
public final class ValidationException extends ApiException {

    private ValidationException(Map<String, String> fieldMessages) {
        super(ErrorCode.VALIDATION_FAILED, fieldMessages);
    }

    public static ValidationException of(String field, String message) {
        return new ValidationException(Map.of(field, message));
    }

    public static ValidationException of(Map<String, String> fieldMessages) {
        return new ValidationException(fieldMessages);
    }
}

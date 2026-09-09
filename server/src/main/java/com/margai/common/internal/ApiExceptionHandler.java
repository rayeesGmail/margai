package com.margai.common.internal;

import com.margai.common.api.ApiException;
import com.margai.common.api.ErrorCode;
import com.margai.common.api.ErrorEnvelope;
import com.margai.common.api.ErrorResponses;
import com.margai.common.api.RateLimitedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * TECH_PLAN §1.5 step 8: every exception becomes the envelope of §3.3. Typed outcomes keep
 * their code and status; validation failures list the fields; unknown routes are
 * {@code NOT_FOUND}; a {@code @PreAuthorize} refusal on an admin route (§9.3) is
 * {@code FORBIDDEN}; anything else is a bug — logged with the request id, answered as
 * {@code INTERNAL} with that id and never a stack trace (§11.4).
 */
@RestControllerAdvice
class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private final ErrorResponses responses;

    ApiExceptionHandler(ErrorResponses responses) {
        this.responses = responses;
    }

    @ExceptionHandler(ApiException.class)
    ResponseEntity<ErrorEnvelope> apiFailure(ApiException failure, HttpServletRequest request) {
        ResponseEntity.BodyBuilder response = ResponseEntity.status(failure.code().httpStatus());
        if (failure instanceof RateLimitedException limited) {
            response.header(HttpHeaders.RETRY_AFTER, Long.toString(limited.retryAfterSeconds()));
        }
        return response.body(responses.envelope(failure.code(), failure.details(), request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorEnvelope> invalidBody(MethodArgumentNotValidException failure, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>();
        for (FieldError error : failure.getBindingResult().getFieldErrors()) {
            fields.putIfAbsent(wireName(error.getField()), reasonOf(error));
        }
        failure.getBindingResult().getGlobalErrors()
                .forEach(error -> fields.putIfAbsent(wireName(error.getObjectName()), reasonOf(error)));
        return validationFailed(fields, request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    ResponseEntity<ErrorEnvelope> invalidParameters(HandlerMethodValidationException failure, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>();
        failure.getParameterValidationResults().forEach(result -> result.getResolvableErrors()
                .forEach(error -> fields.putIfAbsent(wireName(result.getMethodParameter().getParameterName()),
                        reasonOf(error))));
        return validationFailed(fields, request);
    }

    /**
     * The reason code of one validation error, independent of the request's locale: the
     * constraint's raw message template when the source violation is reachable, else the bare
     * constraint name. Spring lists codes most-specific first ({@code NotBlank.body.name} …
     * {@code NotBlank}), so the bare constraint is the last one.
     */
    private static String reasonOf(MessageSourceResolvable error) {
        String[] codes = error.getCodes();
        String constraint = codes == null || codes.length == 0 ? null : codes[codes.length - 1];
        String template = null;
        if (error instanceof ObjectError objectError) {
            try {
                template = objectError.unwrap(ConstraintViolation.class).getMessageTemplate();
            } catch (IllegalArgumentException notAConstraintViolation) {
                template = null;
            }
        }
        return reasonCode(constraint, template);
    }

    /**
     * {@code details} values are reason codes, never prose (SPEC §3 "all copy is externalized"): a
     * constraint's own {@code message} template when it was set to a code ({@code code.digits}),
     * else the constraint name in snake_case ({@code NotBlank → not_blank}). The framework's
     * interpolated text — in whatever locale the request asked for — never reaches the wire.
     */
    static String reasonCode(String constraint, String messageTemplate) {
        if (messageTemplate != null && !messageTemplate.isBlank() && !messageTemplate.startsWith("{")
                && messageTemplate.chars().noneMatch(Character::isWhitespace)) {
            return messageTemplate;
        }
        return constraint == null ? "invalid" : wireName(constraint);
    }

    /** Validation names Java fields; the client knows the snake_case wire names (§11.3): {@code challengeId → challenge_id}. */
    static String wireName(String javaName) {
        if (javaName == null) {
            return null;
        }
        StringBuilder wire = new StringBuilder(javaName.length() + 4);
        for (int i = 0; i < javaName.length(); i++) {
            char c = javaName.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0 && javaName.charAt(i - 1) != '.' && javaName.charAt(i - 1) != '_') {
                    wire.append('_');
                }
                wire.append(Character.toLowerCase(c));
            } else {
                wire.append(c);
            }
        }
        return wire.toString();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorEnvelope> unreadableBody(HttpMessageNotReadableException failure, HttpServletRequest request) {
        return validationFailed(Map.of("body", "malformed"), request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ErrorEnvelope> unsupportedMediaType(HttpMediaTypeNotSupportedException failure,
            HttpServletRequest request) {
        return validationFailed(Map.of("content_type", "unsupported"), request);
    }

    /**
     * Method security throws from inside the controller, past the chain's own denied handler
     * (which covers route-level rules) and straight into this advice — without this mapping a
     * student on an admin route would be a logged "bug" and a 500 (D11, DECISIONS).
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ErrorEnvelope> forbidden(AccessDeniedException denied, HttpServletRequest request) {
        return ResponseEntity.status(ErrorCode.FORBIDDEN.httpStatus())
                .body(responses.envelope(ErrorCode.FORBIDDEN, null, request));
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class})
    ResponseEntity<ErrorEnvelope> noSuchRoute(Exception failure, HttpServletRequest request) {
        return ResponseEntity.status(ErrorCode.NOT_FOUND.httpStatus())
                .body(responses.envelope(ErrorCode.NOT_FOUND, null, request));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorEnvelope> bug(Exception failure, HttpServletRequest request) {
        if (failure instanceof ErrorResponse spring && spring.getStatusCode().is4xxClientError()) {
            // A Spring web exception this handler does not name: keep the closest contract code.
            ErrorCode code = spring.getStatusCode().value() == 404 || spring.getStatusCode().value() == 405
                    ? ErrorCode.NOT_FOUND : ErrorCode.VALIDATION_FAILED;
            return ResponseEntity.status(code.httpStatus()).body(responses.envelope(code, null, request));
        }
        String requestId = RequestIdFilter.of(request);
        log.error("unhandled exception on {} {} (request_id={})", request.getMethod(), request.getRequestURI(),
                requestId, failure);
        Map<String, Object> details = requestId == null ? null : Map.of("request_id", requestId);
        return ResponseEntity.status(ErrorCode.INTERNAL.httpStatus())
                .body(responses.envelope(ErrorCode.INTERNAL, details, request));
    }

    private ResponseEntity<ErrorEnvelope> validationFailed(Map<String, Object> fields, HttpServletRequest request) {
        return ResponseEntity.status(ErrorCode.VALIDATION_FAILED.httpStatus())
                .body(responses.envelope(ErrorCode.VALIDATION_FAILED, fields, request));
    }
}

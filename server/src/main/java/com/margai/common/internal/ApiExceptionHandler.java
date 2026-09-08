package com.margai.common.internal;

import com.margai.common.api.ApiException;
import com.margai.common.api.ErrorCode;
import com.margai.common.api.ErrorEnvelope;
import com.margai.common.api.ErrorResponses;
import com.margai.common.api.RateLimitedException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
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
 * {@code NOT_FOUND}; anything else is a bug — logged with the request id, answered as
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
            fields.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        failure.getBindingResult().getGlobalErrors()
                .forEach(error -> fields.putIfAbsent(error.getObjectName(), error.getDefaultMessage()));
        return validationFailed(fields, request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    ResponseEntity<ErrorEnvelope> invalidParameters(HandlerMethodValidationException failure, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>();
        failure.getParameterValidationResults().forEach(result -> result.getResolvableErrors()
                .forEach(error -> fields.putIfAbsent(result.getMethodParameter().getParameterName(),
                        error.getDefaultMessage())));
        return validationFailed(fields, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorEnvelope> unreadableBody(HttpMessageNotReadableException failure, HttpServletRequest request) {
        return validationFailed(Map.of("body", "malformed or missing JSON"), request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ErrorEnvelope> unsupportedMediaType(HttpMediaTypeNotSupportedException failure,
            HttpServletRequest request) {
        return validationFailed(Map.of("content_type", "send application/json"), request);
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

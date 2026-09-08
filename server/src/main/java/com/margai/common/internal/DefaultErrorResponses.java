package com.margai.common.internal;

import com.margai.common.api.ApiException;
import com.margai.common.api.ErrorCode;
import com.margai.common.api.ErrorEnvelope;
import com.margai.common.api.ErrorResponses;
import com.margai.common.api.Language;
import com.margai.common.api.Messages;
import com.margai.common.api.RateLimitedException;
import com.margai.common.api.RequestLanguage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

/** Envelope construction shared by the MVC advice and the security layer (TECH_PLAN §3.3). */
@Component
class DefaultErrorResponses implements ErrorResponses {

    private final Messages messages;
    private final ObjectMapper json;

    DefaultErrorResponses(Messages messages, ObjectMapper json) {
        this.messages = messages;
        this.json = json;
    }

    @Override
    public ErrorEnvelope envelope(ErrorCode code, Map<String, ?> details, HttpServletRequest request) {
        Language language = RequestLanguage.of(request);
        Map<String, Object> copy = details == null || details.isEmpty() ? null : new LinkedHashMap<>(details);
        return new ErrorEnvelope(new ErrorEnvelope.Body(
                code.name(),
                messages.message(code.name(), Language.en),
                messages.message(code.name(), language),
                copy));
    }

    @Override
    public void write(HttpServletRequest request, HttpServletResponse response, ApiException failure) throws IOException {
        response.setStatus(failure.code().httpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        if (failure instanceof RateLimitedException limited) {
            response.setHeader(HttpHeaders.RETRY_AFTER, Long.toString(limited.retryAfterSeconds()));
        }
        json.writeValue(response.getOutputStream(), envelope(failure.code(), failure.details(), request));
        response.flushBuffer();
    }
}

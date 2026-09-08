package com.margai.common.internal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * TECH_PLAN §1.5 step 2: takes {@code X-Request-Id} from the client or mints one, puts it in the
 * MDC as {@code request_id} (§10.1) and echoes it on every response (§3.1). A client value is
 * accepted only when it is short and plain, so a hostile header can never reach the logs.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestIdFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-Request-Id";
    public static final String MDC_KEY = "request_id";
    /** Request attribute holding the id, for handlers that put it into an envelope. */
    public static final String ATTRIBUTE = RequestIdFilter.class.getName();

    private static final Pattern ACCEPTABLE = Pattern.compile("[A-Za-z0-9._-]{1,64}");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestId = requestId(request.getHeader(HEADER));
        request.setAttribute(ATTRIBUTE, requestId);
        response.setHeader(HEADER, requestId);
        MDC.put(MDC_KEY, requestId);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }

    static String requestId(String fromClient) {
        return fromClient != null && ACCEPTABLE.matcher(fromClient).matches() ? fromClient : UUID.randomUUID().toString();
    }

    /** The id of the current request, or {@code null} outside a filtered request. */
    public static String of(HttpServletRequest request) {
        return request.getAttribute(ATTRIBUTE) instanceof String id ? id : null;
    }
}

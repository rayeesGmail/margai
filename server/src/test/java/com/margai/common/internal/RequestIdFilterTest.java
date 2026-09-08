package com.margai.common.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/** TECH_PLAN §1.5 step 2 and §10.1: the id is in the MDC during the request and gone after it. */
class RequestIdFilterTest {

    private final RequestIdFilter filter = new RequestIdFilter();

    @Test
    void mdcHoldsTheIdOnlyWhileTheRequestRuns() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/probe/shape");
        request.addHeader(RequestIdFilter.HEADER, "abc-123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicReference<String> seenInsideChain = new AtomicReference<>();

        filter.doFilter(request, response, new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest req, jakarta.servlet.ServletResponse res) {
                seenInsideChain.set(MDC.get(RequestIdFilter.MDC_KEY));
            }
        });

        assertThat(seenInsideChain.get()).isEqualTo("abc-123");
        assertThat(MDC.get(RequestIdFilter.MDC_KEY)).isNull();
        assertThat(response.getHeader(RequestIdFilter.HEADER)).isEqualTo("abc-123");
        assertThat(RequestIdFilter.of(request)).isEqualTo("abc-123");
    }

    @Test
    void clientIdsAreAcceptedOnlyWhenPlainAndShort() {
        assertThat(RequestIdFilter.requestId("req_2026-09-08.7")).isEqualTo("req_2026-09-08.7");
        assertThat(RequestIdFilter.requestId(null)).hasSize(36);
        assertThat(RequestIdFilter.requestId("")).hasSize(36);
        assertThat(RequestIdFilter.requestId("has space")).hasSize(36);
        assertThat(RequestIdFilter.requestId("x".repeat(65))).hasSize(36);
        assertThat(RequestIdFilter.requestId("<b>")).hasSize(36);
    }
}

package com.margai.ops.web;

import com.margai.auth.api.OtpDeliveryReport;
import com.margai.auth.api.OtpMetrics;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code /admin/metrics} (TECH_PLAN §3.7 ops rows; §10.3): the operations numbers a founder reads
 * before the CloudWatch dashboards of D73 exist. Admin only (§9.3 — the role is the JWT's
 * {@code role} claim, {@code users.role} flagged by hand); a student is {@code FORBIDDEN}. Thin:
 * one call per route, nothing per-user, nothing written.
 */
@RestController
@RequestMapping("/api/v1/admin/metrics")
@PreAuthorize("hasRole('ADMIN')")
class AdminMetricsController {

    private final OtpMetrics otp;

    AdminMetricsController(OtpMetrics otp) {
        this.otp = otp;
    }

    /** PLAN D11 ✅ "OTP success metric visible": the delivery report since this instance started. */
    @GetMapping("/otp")
    OtpDeliveryReport otp() {
        return otp.report();
    }
}

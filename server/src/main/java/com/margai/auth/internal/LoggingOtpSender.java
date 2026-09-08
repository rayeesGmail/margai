package com.margai.auth.internal;

import com.margai.common.api.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * The sandbox delivery channel (TECH_PLAN §1.2 "fake adapters that log"; PLAN D7 "SMS provider
 * sandbox"): the rendered message — which carries the code — goes to the logger
 * {@code margai.otp.sandbox} with a masked destination. That logger is the inbox; the auth
 * module's own loggers never see a code (§9.1, asserted by {@code OtpServiceTest}). Selected by
 * {@code margai.auth.otp.sender = log}, the default outside AWS.
 */
@Component
@ConditionalOnProperty(prefix = "margai.auth.otp", name = "sender", havingValue = "log", matchIfMissing = true)
class LoggingOtpSender implements OtpSender {

    static final String LOGGER_NAME = "margai.otp.sandbox";

    private static final Logger inbox = LoggerFactory.getLogger(LOGGER_NAME);

    private final Messages messages;

    LoggingOtpSender(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void send(OtpDelivery delivery) {
        String key = delivery.channel() == OtpChannel.email ? "otp.email.body" : "otp.sms.body";
        String text = messages.message(key, delivery.language(), delivery.code(), delivery.ttl().toMinutes());
        inbox.info("[sandbox {}] to {} — {}", delivery.channel(),
                Identifiers.mask(delivery.channel(), delivery.destination()), text.replace('\n', ' '));
    }
}

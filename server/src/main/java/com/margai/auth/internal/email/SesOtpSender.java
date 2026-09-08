package com.margai.auth.internal.email;

import com.margai.auth.internal.Identifiers;
import com.margai.auth.internal.OtpChannel;
import com.margai.auth.internal.OtpDelivery;
import com.margai.auth.internal.OtpSendException;
import com.margai.auth.internal.OtpSender;
import com.margai.common.api.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;
import software.amazon.awssdk.services.sesv2.model.SesV2Exception;

/**
 * Sends the OTP email: subject and body from the message catalogs in the student's language
 * (TECH_PLAN §3.8, §11.7), plain text, from the configured verified identity. Provider failures
 * become an {@link OtpSendException} carrying only the SES error code — SES messages can quote
 * the recipient (§9.6) — and are logged here with the masked address. Only this package imports
 * the SES SDK (§1.4, ArchitectureTest).
 */
final class SesOtpSender implements OtpSender {

    static final String SUBJECT_KEY = "otp.email.subject";
    static final String BODY_KEY = "otp.email.body";

    private static final Logger log = LoggerFactory.getLogger(SesOtpSender.class);

    private final SesV2Client ses;
    private final String from;
    private final Messages messages;

    SesOtpSender(SesV2Client ses, String from, Messages messages) {
        this.ses = ses;
        this.from = from;
        this.messages = messages;
    }

    @Override
    public void send(OtpDelivery delivery) {
        if (delivery.channel() != OtpChannel.email) {
            throw new OtpSendException("no sender for channel " + delivery.channel() + " (SMS arrives with F1)");
        }
        long minutes = delivery.ttl().toMinutes();
        SendEmailRequest request = SendEmailRequest.builder()
                .fromEmailAddress(from)
                .destination(Destination.builder().toAddresses(delivery.destination()).build())
                .content(EmailContent.builder().simple(Message.builder()
                        .subject(utf8(messages.message(SUBJECT_KEY, delivery.language(), delivery.code(), minutes)))
                        .body(Body.builder()
                                .text(utf8(messages.message(BODY_KEY, delivery.language(), delivery.code(), minutes)))
                                .build())
                        .build()).build())
                .build();
        String masked = Identifiers.mask(OtpChannel.email, delivery.destination());
        try {
            SendEmailResponse response = ses.sendEmail(request);
            log.info("otp email accepted by SES for {} (message id {})", masked, response.messageId());
        } catch (SesV2Exception rejected) {
            String code = rejected.awsErrorDetails() == null ? "SesV2Exception" : rejected.awsErrorDetails().errorCode();
            log.warn("SES rejected the otp email for {}: {} (HTTP {})", masked, code, rejected.statusCode());
            throw new OtpSendException("ses:" + code);
        } catch (SdkException unavailable) {
            log.warn("SES unreachable for {}: {}", masked, unavailable.getClass().getSimpleName());
            throw new OtpSendException("ses:unavailable");
        }
    }

    private static Content utf8(String text) {
        return Content.builder().data(text).charset("UTF-8").build();
    }
}

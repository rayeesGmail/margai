package com.margai.auth.internal.email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.margai.auth.internal.OtpChannel;
import com.margai.auth.internal.OtpDelivery;
import com.margai.auth.internal.OtpSendException;
import com.margai.common.api.Language;
import com.margai.common.api.Messages;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;
import software.amazon.awssdk.services.sesv2.model.SesV2Exception;

/** The SES request shape, the language of the copy, and failures that never quote the recipient. */
class SesOtpSenderTest {

    private final SesV2Client ses = mock(SesV2Client.class);
    private final Messages messages = (key, language, args) -> key + "|" + language + "|" + args[0] + "|" + args[1];
    private final SesOtpSender sender = new SesOtpSender(ses, "MARG AI <no-reply@margai.example>", messages);
    private final OtpDelivery delivery = new OtpDelivery(OtpChannel.email, "student@example.com", "482913", Language.hinglish,
            Duration.ofMinutes(5));

    @Test
    void sendsPlainTextFromTheVerifiedIdentityInTheStudentsLanguage() {
        when(ses.sendEmail(any(SendEmailRequest.class))).thenReturn(SendEmailResponse.builder().messageId("m-1").build());

        sender.send(delivery);

        ArgumentCaptor<SendEmailRequest> sent = ArgumentCaptor.forClass(SendEmailRequest.class);
        verify(ses).sendEmail(sent.capture());
        SendEmailRequest request = sent.getValue();
        assertThat(request.fromEmailAddress()).isEqualTo("MARG AI <no-reply@margai.example>");
        assertThat(request.destination().toAddresses()).containsExactly("student@example.com");
        assertThat(request.content().simple().subject().data()).isEqualTo("otp.email.subject|hinglish|482913|5");
        assertThat(request.content().simple().subject().charset()).isEqualTo("UTF-8");
        assertThat(request.content().simple().body().text().data()).isEqualTo("otp.email.body|hinglish|482913|5");
        assertThat(request.content().simple().body().html()).isNull();
    }

    @Test
    void aRejectionCarriesOnlyTheErrorCode() {
        when(ses.sendEmail(any(SendEmailRequest.class))).thenThrow(SesV2Exception.builder()
                .statusCode(400)
                .awsErrorDetails(AwsErrorDetails.builder().errorCode("MessageRejected")
                        .errorMessage("Email address is not verified: student@example.com").build())
                .build());

        assertThatThrownBy(() -> sender.send(delivery))
                .isInstanceOf(OtpSendException.class)
                .hasMessage("ses:MessageRejected")
                .satisfies(failure -> assertThat(failure.getCause()).isNull());
    }

    @Test
    void anUnreachableServiceIsUnavailable() {
        when(ses.sendEmail(any(SendEmailRequest.class))).thenThrow(SdkClientException.create("connect timed out"));

        assertThatThrownBy(() -> sender.send(delivery)).isInstanceOf(OtpSendException.class).hasMessage("ses:unavailable");
    }

    @Test
    void smsIsNotThisSendersChannel() {
        OtpDelivery sms = new OtpDelivery(OtpChannel.sms, "+919876543210", "482913", Language.en, Duration.ofMinutes(5));

        assertThatThrownBy(() -> sender.send(sms)).isInstanceOf(OtpSendException.class).hasMessageContaining("sms");
        verify(ses, never()).sendEmail(any(SendEmailRequest.class));
    }
}

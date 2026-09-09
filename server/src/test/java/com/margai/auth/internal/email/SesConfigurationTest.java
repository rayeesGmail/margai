package com.margai.auth.internal.email;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.auth.internal.AuthProperties;
import com.margai.auth.internal.OtpChannel;
import com.margai.auth.internal.OtpSender;
import com.margai.common.api.Messages;
import java.time.Duration;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import software.amazon.awssdk.services.sesv2.SesV2Client;

/** {@code margai.auth.otp.sender} selects the channel adapter; {@code ses} without a sender identity refuses to start. */
class SesConfigurationTest {

    private final Messages messages = (key, language, args) -> key;

    @Test
    void logSenderMeansNoSesClientAtAll() {
        runner("log", "no-reply@margai.example").run(context -> {
            assertThat(context).hasNotFailed();
            assertThat(context).doesNotHaveBean(SesV2Client.class);
            assertThat(context).doesNotHaveBean(OtpSender.class);
        });
    }

    @Test
    void sesSenderNeedsAVerifiedFromAddress() {
        runner("ses", " ").run(context -> {
            assertThat(context).hasFailed();
            assertThat(context.getStartupFailure()).rootCause().hasMessageContaining("margai.auth.otp.email-from");
        });
    }

    @Test
    void sesSenderStartsWithAFromAddress() {
        runner("ses", "no-reply@margai.example").run(context -> {
            assertThat(context).hasNotFailed();
            assertThat(context).hasSingleBean(SesV2Client.class);
            assertThat(context).getBean(OtpSender.class).isInstanceOf(SesOtpSender.class);
        });
    }

    private ApplicationContextRunner runner(String sender, String from) {
        AuthProperties properties = new AuthProperties(
                new AuthProperties.Jwt("", "", Duration.ofMinutes(15), Duration.ofDays(30)),
                new AuthProperties.Otp("", Duration.ofMinutes(5), 5, Duration.ofSeconds(30), 6, Set.of(OtpChannel.email),
                        AuthProperties.Sender.valueOf(sender), from, "ap-south-1", Duration.ofHours(1)),
                Duration.ofMinutes(2));
        return new ApplicationContextRunner()
                .withPropertyValues("margai.auth.otp.sender=" + sender)
                .withBean(AuthProperties.class, () -> properties)
                .withBean(Messages.class, () -> messages)
                .withUserConfiguration(SesConfiguration.class);
    }
}

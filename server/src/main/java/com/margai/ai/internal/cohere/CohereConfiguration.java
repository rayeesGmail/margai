package com.margai.ai.internal.cohere;

import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.EmbeddingClient;
import com.margai.ai.internal.StructuredOutput;
import java.net.http.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * The embedding provider in the {@code live} profile (TECH_PLAN §1.2, §4.9): {@code AI_LIVE=1}
 * locally, the task definition in AWS. The key is configuration from SSM and the call inherits
 * {@code margai.ai.call-timeout} on both connect and read; nothing here retries — the retry
 * decorator owns that policy (DECISIONS D5).
 *
 * <p>Wired on {@code margai.ai.embed.provider}, independently of the completion provider, because
 * the two halves of the seam are separate providers now. The dormant Bedrock client embeds for
 * itself, so running it (§4.11) means setting {@code margai.ai.embed.provider} away from
 * {@code cohere} as well — otherwise this bean is built, and refuses to start without a key it
 * would never use.
 */
@Configuration(proxyBeanMethods = false)
@Profile("live")
@ConditionalOnProperty(name = "margai.ai.embed.provider", havingValue = "cohere", matchIfMissing = true)
class CohereConfiguration {

    @Bean
    EmbeddingClient embeddingClient(AiProperties properties, StructuredOutput codec) {
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(properties.callTimeout()).build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(properties.callTimeout());
        RestClient http = RestClient.builder()
                .baseUrl(properties.cohere().baseUrl())
                .requestFactory(factory)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + key(properties))
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
        return new CohereEmbeddingClient(http, properties.embed(), codec);
    }

    /** A live embedding client without a key is a misconfiguration, not a runtime surprise. */
    private static String key(AiProperties properties) {
        String key = properties.cohere().apiKey();
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("margai.ai.cohere.api-key is blank: the live profile needs the "
                    + "embedding provider key from SSM (/margai/beta/ai/cohere/*) or, on a laptop, from the "
                    + "untracked local environment");
        }
        return key;
    }
}

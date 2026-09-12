package com.margai.storage.internal.s3;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.internal.StorageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * The real object store, wired only when {@code margai.storage.content-bucket} names a bucket —
 * so a test or an unconfigured laptop never builds an AWS client, and a configured run never
 * silently writes into memory. Credentials come from the SDK's default chain: the Identity Center
 * profile {@code margai} on a laptop ({@code AWS_PROFILE=margai}), the task role in AWS
 * (TECH_PLAN §7.4, DECISIONS 2026-09-12 F8), never from configuration.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "margai.storage.content-bucket")
class S3StorageConfiguration {

    @Bean
    S3Client s3Client(StorageProperties properties) {
        return S3Client.builder().region(Region.of(properties.region())).build();
    }

    @Bean
    ObjectStore contentObjectStore(S3Client s3, StorageProperties properties) {
        return new S3ObjectStore(s3, properties.contentBucket());
    }
}

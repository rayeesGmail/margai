package com.margai.storage.internal;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.internal.s3.S3ObjectStores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The storage module's wiring (TECH_PLAN §1.3): one {@link ObjectStore} bean, chosen here rather
 * than by two conditional configurations — which store a context gets must not depend on the
 * order Spring happens to register them in. A configured bucket gives the real store; no bucket
 * gives the in-memory one with a WARN, the way an unconfigured context gets {@code FakeAiClient}
 * (DEV_SPEC §13.7), because a pipeline run against it would write page images into a map that
 * dies with the JVM.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(StorageProperties.class)
class StorageConfiguration {

    private static final Logger log = LoggerFactory.getLogger(StorageConfiguration.class);

    @Bean
    ObjectStore contentObjectStore(StorageProperties properties) {
        if (properties.hasContentBucket()) {
            log.info("object storage: s3://{} in {}", properties.contentBucket(), properties.region());
            return S3ObjectStores.create(properties);
        }
        log.warn("margai.storage.content-bucket is not set: object storage is in-memory and nothing "
                + "written through it survives this process");
        return new InMemoryObjectStore();
    }
}

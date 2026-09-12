package com.margai.storage.internal;

import com.margai.storage.api.ObjectStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The storage module's wiring (TECH_PLAN §1.3). The real S3 store is contributed by
 * {@code internal.s3} when a bucket is configured; this is the fallback, so a context that was
 * never pointed at a bucket still starts — with a WARN, because a pipeline run against it would
 * write its page images into a map that dies with the JVM.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(StorageProperties.class)
class StorageConfiguration {

    private static final Logger log = LoggerFactory.getLogger(StorageConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(ObjectStore.class)
    ObjectStore inMemoryObjectStore() {
        log.warn("margai.storage.content-bucket is not set: object storage is in-memory and nothing "
                + "written through it survives this process");
        return new InMemoryObjectStore();
    }
}

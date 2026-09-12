package com.margai.storage.internal;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code margai.storage.*} (TECH_PLAN §7.3). The content bucket holds the pipeline's source PDFs,
 * page images and JSONL artefacts (§7.6, DECISIONS 2026-09-12 F8); leave it blank and the module
 * falls back to the in-memory store, which is what tests and an unconfigured laptop get.
 *
 * @param contentBucket the content bucket's name, blank when none is configured
 * @param region        the bucket's region; ap-south-1 everywhere this project runs
 */
@ConfigurationProperties(prefix = "margai.storage")
public record StorageProperties(String contentBucket, String region) {

    public StorageProperties {
        contentBucket = contentBucket == null ? "" : contentBucket.trim();
        region = region == null || region.isBlank() ? "ap-south-1" : region.trim();
    }

    public boolean hasContentBucket() {
        return !contentBucket.isEmpty();
    }
}

package com.margai.storage.internal.s3;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.internal.StorageProperties;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Builds the real store without letting an SDK type cross the package boundary: the module's
 * configuration decides which store to make, and only this package names S3 (TECH_PLAN §1.4).
 * Credentials come from the SDK's default chain — the Identity Center profile {@code margai} on a
 * laptop ({@code AWS_PROFILE=margai}), the task role in AWS (§7.4, DECISIONS 2026-09-12 F8) —
 * never from configuration.
 */
public final class S3ObjectStores {

    private S3ObjectStores() {
    }

    public static ObjectStore create(StorageProperties properties) {
        S3Client s3 = S3Client.builder().region(Region.of(properties.region())).build();
        return new S3ObjectStore(s3, properties.contentBucket());
    }
}

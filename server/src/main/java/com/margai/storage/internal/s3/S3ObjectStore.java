package com.margai.storage.internal.s3;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.api.StorageException;
import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 * The real port (TECH_PLAN §1.4: only {@code storage} imports the S3 SDK; inside it only this
 * package, on the same principle as the provider packages in {@code ai}). Credentials come from
 * the SDK's default chain — the Identity Center profile {@code margai} on a laptop, the task role
 * in AWS (DECISIONS 2026-09-12 F8) — never from configuration.
 */
class S3ObjectStore implements ObjectStore {

    private final S3Client s3;
    private final String bucket;

    S3ObjectStore(S3Client s3, String bucket) {
        this.s3 = s3;
        this.bucket = bucket;
    }

    @Override
    public void put(String key, byte[] bytes, String contentType) {
        try {
            s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key).contentType(contentType).build(),
                    RequestBody.fromBytes(bytes));
        } catch (S3Exception e) {
            throw new StorageException("could not write s3://" + bucket + "/" + key, e);
        }
    }

    @Override
    public byte[] get(String key) {
        try {
            ResponseBytes<?> object =
                    s3.getObjectAsBytes(GetObjectRequest.builder().bucket(bucket).key(key).build());
            return object.asByteArray();
        } catch (NoSuchKeyException e) {
            throw new StorageException("no object at s3://" + bucket + "/" + key, e);
        } catch (S3Exception e) {
            throw new StorageException("could not read s3://" + bucket + "/" + key, e);
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            s3.headObject(HeadObjectRequest.builder().bucket(bucket).key(key).build());
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            }
            throw new StorageException("could not stat s3://" + bucket + "/" + key, e);
        }
    }

    @Override
    public List<String> list(String prefix) {
        List<String> keys = new ArrayList<>();
        String continuation = null;
        try {
            do {
                ListObjectsV2Response page = s3.listObjectsV2(ListObjectsV2Request.builder()
                        .bucket(bucket).prefix(prefix).continuationToken(continuation).build());
                page.contents().forEach(object -> keys.add(object.key()));
                continuation = Boolean.TRUE.equals(page.isTruncated()) ? page.nextContinuationToken() : null;
            } while (continuation != null);
        } catch (S3Exception e) {
            throw new StorageException("could not list s3://" + bucket + "/" + prefix, e);
        }
        keys.sort(String::compareTo);
        return List.copyOf(keys);
    }

    @Override
    public String describe() {
        return "s3://" + bucket;
    }
}

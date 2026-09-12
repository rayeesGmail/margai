package com.margai.storage.internal.s3;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.api.StorageException;
import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkException;
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
class S3ObjectStore implements ObjectStore, AutoCloseable {

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
        } catch (SdkException e) {
            throw failure("write", key, e);
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
        } catch (SdkException e) {
            throw failure("read", key, e);
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
            throw failure("stat", key, e);
        } catch (SdkException e) {
            throw failure("stat", key, e);
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
        } catch (SdkException e) {
            throw failure("list", prefix, e);
        }
        keys.sort(String::compareTo);
        return List.copyOf(keys);
    }

    @Override
    public String describe() {
        return "s3://" + bucket;
    }

    /**
     * One readable failure instead of a hundred lines of provider chain. An expired SSO session is
     * much the commonest of these — a pipeline run outlives its login — and it is not a fault in the
     * sense the stack trace implies, so it gets the one line that actually helps: the command that
     * fixes it (D14, found between a render and the extract that followed it 46 minutes later).
     */
    private StorageException failure(String action, String key, SdkException cause) {
        String where = "s3://" + bucket + "/" + key;
        String message = cause.getMessage() == null ? "" : cause.getMessage();
        if (cause instanceof SdkClientException && message.contains("Unable to load credentials")) {
            String profile = System.getenv("AWS_PROFILE");
            return new StorageException("could not " + action + " " + where + ": no usable AWS credentials. "
                    + "An SSO session expires while a long run is going — run `aws sso login"
                    + (profile == null ? "`" : " --profile " + profile + "`")
                    + " and start the command again; it resumes where it stopped.", cause);
        }
        return new StorageException("could not " + action + " " + where + ": " + message, cause);
    }

    /** Spring's default destroy-method inference finds this and closes the SDK client on shutdown. */
    @Override
    public void close() {
        s3.close();
    }
}

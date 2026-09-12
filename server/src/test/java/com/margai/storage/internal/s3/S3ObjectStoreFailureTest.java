package com.margai.storage.internal.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.margai.storage.api.StorageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

/**
 * What a founder reads when S3 refuses. The case that matters is the expired SSO session: a
 * pipeline run outlives its login, and the SDK's answer is a hundred lines of provider chain in
 * which the one useful sentence is buried (D14, hit between a render and the extract 46 minutes
 * later). It is not a fault to debug — it is a login to renew — so the message says so.
 */
@ExtendWith(MockitoExtension.class)
class S3ObjectStoreFailureTest {

    private static final String CREDENTIALS_MESSAGE =
            "Unable to load credentials from any of the providers in the chain …";

    @Mock
    private S3Client s3;

    @Test
    void anExpiredSsoSessionReadsAsALoginToRenew() {
        given(s3.headObject(any(HeadObjectRequest.class)))
                .willThrow(SdkClientException.create(CREDENTIALS_MESSAGE));

        assertThatThrownBy(() -> new S3ObjectStore(s3, "margai-beta-content").exists("pages/bio11/en/1/001.png"))
                .isInstanceOf(StorageException.class)
                .hasMessageContaining("no usable AWS credentials")
                .hasMessageContaining("aws sso login")
                .hasMessageContaining("it resumes where it stopped")
                .hasMessageContaining("s3://margai-beta-content/pages/bio11/en/1/001.png");
    }

    @Test
    void anyOtherClientFailureStillNamesTheObjectAndTheCause() {
        given(s3.listObjectsV2(any(ListObjectsV2Request.class)))
                .willThrow(SdkClientException.create("connection reset"));

        assertThatThrownBy(() -> new S3ObjectStore(s3, "margai-beta-content").list("pages/bio11/en/"))
                .isInstanceOf(StorageException.class)
                .hasMessageContaining("could not list s3://margai-beta-content/pages/bio11/en/")
                .hasMessageContaining("connection reset");
    }

    @Test
    void theStoreStillSaysWhereItWrites() {
        assertThat(new S3ObjectStore(s3, "margai-beta-content").describe()).isEqualTo("s3://margai-beta-content");
    }
}

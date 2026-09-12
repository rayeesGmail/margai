package com.margai.storage.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.api.StorageException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * The port's contract (TECH_PLAN §1.3), pinned on the in-memory implementation: what the pipeline
 * commands rely on — a put that replaces, a get that fails loudly on a missing key, a prefix
 * listing that is sorted, and a description honest enough to appear in a run report.
 * The S3 implementation is exercised for real by the D14 acceptance run, never by a test.
 */
class ObjectStoreTest {

    private final ObjectStore store = new InMemoryObjectStore();

    @Test
    void putThenGetReturnsTheSameBytes() {
        store.put("pages/bio11/en/1/007.png", bytes("page seven"), "image/png");

        assertThat(store.get("pages/bio11/en/1/007.png")).isEqualTo(bytes("page seven"));
        assertThat(store.exists("pages/bio11/en/1/007.png")).isTrue();
    }

    @Test
    void putReplacesSoARerunIsIdempotent() {
        store.put("extract/bio11/en.jsonl", bytes("first"), "application/jsonl");
        store.put("extract/bio11/en.jsonl", bytes("second"), "application/jsonl");

        assertThat(store.get("extract/bio11/en.jsonl")).isEqualTo(bytes("second"));
        assertThat(store.list("extract/")).hasSize(1);
    }

    @Test
    void aMissingKeyFailsLoudly() {
        assertThat(store.exists("pages/bio11/en/1/001.png")).isFalse();

        assertThatThrownBy(() -> store.get("pages/bio11/en/1/001.png"))
                .isInstanceOf(StorageException.class)
                .hasMessageContaining("pages/bio11/en/1/001.png");
    }

    @Test
    void listReturnsOnlyThePrefixSorted() {
        store.put("pages/bio11/en/1/002.png", bytes("b"), "image/png");
        store.put("pages/bio11/en/1/001.png", bytes("a"), "image/png");
        store.put("pages/phy11-part1/en/1/001.png", bytes("c"), "image/png");

        assertThat(store.list("pages/bio11/en/1/"))
                .containsExactly("pages/bio11/en/1/001.png", "pages/bio11/en/1/002.png");
        assertThat(store.list("pages/")).hasSize(3);
    }

    @Test
    void theStoreSaysWhereItWrites() {
        assertThat(store.describe()).contains("in-memory").contains("nothing is persisted");
    }

    @Test
    void theBytesHandedOutAreACopy() {
        byte[] written = bytes("original");
        store.put("k", written, "text/plain");
        written[0] = 'X';

        assertThat(store.get("k")).isEqualTo(bytes("original"));
    }

    private static byte[] bytes(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }
}

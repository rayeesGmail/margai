package com.margai.storage.api;

import java.util.List;

/**
 * The object-storage port (TECH_PLAN §1.3, §1.4): every read and write of a bucket goes through
 * here, so nothing outside {@code storage} knows S3 exists. Keys are full object keys inside the
 * configured bucket — {@code source/ncert/2022-ed/en/bio11/kebo101.pdf},
 * {@code pages/bio11/en/1/007.png}, {@code extract/bio11/en.jsonl} (DECISIONS 2026-09-12 F8).
 *
 * <p>Implementations are idempotent on {@link #put}: writing the same key twice replaces the
 * object, which is what makes {@code ncert render} re-runnable (§6.3).
 */
public interface ObjectStore {

    /** Writes (or replaces) one object. */
    void put(String key, byte[] bytes, String contentType);

    /** Reads one object whole; fails with {@link StorageException} when the key is absent. */
    byte[] get(String key);

    boolean exists(String key);

    /** Every key under a prefix, sorted, so a caller can skip what a previous run already wrote. */
    List<String> list(String prefix);

    /**
     * Where this store actually writes, for the run report and the startup log — a real bucket or
     * the in-memory stand-in, so no evidence can mistake one for the other.
     */
    String describe();
}

package com.margai.storage.internal;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.api.StorageException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The stand-in used whenever no bucket is configured — tests, and any laptop run that has not
 * been pointed at the content bucket. It is the storage counterpart of {@code FakeAiClient}
 * (DEV_SPEC §13.7): the default is the one that cannot touch a real service, and
 * {@link #describe()} says so loudly enough that a run report can never be misread as a real one.
 */
class InMemoryObjectStore implements ObjectStore {

    private final Map<String, byte[]> objects = new ConcurrentHashMap<>();

    @Override
    public void put(String key, byte[] bytes, String contentType) {
        objects.put(key, bytes.clone());
    }

    @Override
    public byte[] get(String key) {
        byte[] bytes = objects.get(key);
        if (bytes == null) {
            throw new StorageException("no object at " + key + " (" + describe() + ")");
        }
        return bytes.clone();
    }

    @Override
    public boolean exists(String key) {
        return objects.containsKey(key);
    }

    @Override
    public List<String> list(String prefix) {
        return objects.keySet().stream().filter(key -> key.startsWith(prefix)).sorted().toList();
    }

    @Override
    public String describe() {
        return "in-memory object store (nothing is persisted)";
    }
}

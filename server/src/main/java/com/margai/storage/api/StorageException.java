package com.margai.storage.api;

/** An object-storage read or write that did not happen: a missing key, or the service refusing. */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

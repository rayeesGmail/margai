package com.margai.ai.api;

import java.util.Objects;
import java.util.Set;

/** An image attached to a {@code vision} request: raw bytes and their media type. */
public record ImagePart(byte[] bytes, String mediaType) {

    public static final Set<String> MEDIA_TYPES = Set.of("image/jpeg", "image/png", "image/webp", "image/gif");

    public ImagePart {
        Objects.requireNonNull(bytes, "bytes");
        if (bytes.length == 0) {
            throw new IllegalArgumentException("image has no bytes");
        }
        if (!MEDIA_TYPES.contains(mediaType)) {
            throw new IllegalArgumentException("unsupported image media type: " + mediaType);
        }
    }

    /** The bare image format name (the media type's subtype), which some providers want instead. */
    public String format() {
        return mediaType.substring("image/".length());
    }
}

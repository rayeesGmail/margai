package com.margai.pipeline.internal;

import java.util.function.Supplier;

/** Enum parsing with the caller's error, for the CSV and YAML readers. */
final class Enums {

    private Enums() {
    }

    static <E extends Enum<E>> E parse(String value, Class<E> type, Supplier<InputFormatException> error) {
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException notAConstant) {
            throw error.get();
        }
    }
}

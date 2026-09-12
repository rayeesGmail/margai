package com.margai.pipeline.internal;

import java.nio.file.Path;

/**
 * A founder-owned input file that cannot be read as its contract says (TECH_PLAN §6.2): the
 * message names the file and the line, so the fix is one edit away. Thrown before anything is
 * written; a command that catches it fails the run with exit code 1.
 */
final class InputFormatException extends RuntimeException {

    /** {@code line} 0 means the problem is with the file as a whole. */
    InputFormatException(Path file, long line, String message) {
        super(file.getFileName() + (line > 0 ? ":" + line : "") + ": " + message);
    }
}

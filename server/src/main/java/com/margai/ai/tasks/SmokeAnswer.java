package com.margai.ai.tasks;

/** Output record of the {@code smoke} prompt: the forced tool must return exactly these two fields. */
public record SmokeAnswer(String greeting, int number) {
}

package com.margai.curriculum.api;

/**
 * The subject a paper's section is printed under, which is not {@link Subject}: NEET printed one
 * Biology section of 90 questions until 2020 and Botany and Zoology sections from 2021, while the
 * taxonomy always splits Biology. A question reaches that split through its tag
 * ({@code questions.node_id}); the section is what the paper's own count is checked against
 * (PLAN D19 ✅).
 */
public enum PaperSubject {
    physics, chemistry, biology, botany, zoology
}

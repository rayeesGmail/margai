package com.margai.curriculum.api;

/**
 * The subject a paper's section is printed under, which is not {@link Subject}: a paper may print
 * one Biology section or separate Botany and Zoology sections — the register records which, paper
 * by paper — while the taxonomy always splits Biology. A question reaches that split through its tag
 * ({@code questions.node_id}); the section is what the paper's own count is checked against
 * (PLAN D19 ✅).
 */
public enum PaperSubject {
    physics, chemistry, biology, botany, zoology;

    /**
     * The taxonomy subjects a question printed under this subject is tagged in. Any biology section
     * takes botany and zoology together: the taxonomy files under botany some topics a Zoology section
     * may ask (biotechnology, biomolecules, microbes), and a question is tagged where the syllabus
     * places it (DECISIONS 2026-10-20).
     */
    public java.util.Set<Subject> taggedIn() {
        return switch (this) {
            case physics -> java.util.Set.of(Subject.physics);
            case chemistry -> java.util.Set.of(Subject.chemistry);
            case biology, botany, zoology -> java.util.Set.of(Subject.botany, Subject.zoology);
        };
    }
}

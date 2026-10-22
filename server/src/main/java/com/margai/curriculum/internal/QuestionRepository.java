package com.margai.curriculum.internal;

import com.margai.curriculum.api.QuestionSource;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface QuestionRepository extends JpaRepository<Question, UUID> {

    /** A paper's questions in its own order (TECH_PLAN §15.6): the natural key's index serves it. */
    List<Question> findBySourceAndExamAndYearAndPaperCodeOrderByQuestionNo(
            QuestionSource source, String exam, Short year, String paperCode);

    /** The bank's size by source — PYQs across papers for the load report's ~2% line (DECISIONS 2026-10-22). */
    long countBySource(QuestionSource source);
}

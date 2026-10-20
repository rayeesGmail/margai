package com.margai.curriculum.api;

/**
 * Where a question comes from ({@code questions.source}, migration V9): an official past paper
 * (D19) or the verified NCERT-style generation of SPEC §9 item 3 (unscheduled, TECH_PLAN §12.2).
 */
public enum QuestionSource {
    pyq, generated
}

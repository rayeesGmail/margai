package com.margai.curriculum.api;

/**
 * A founder input that contradicts the taxonomy it is loading into (a parent the file does not
 * name, a prerequisite of a node that is not a chapter, a cycle). Thrown inside the load's
 * transaction, so nothing of that run is written (TECH_PLAN §6.3 "fails loudly, never half-writes").
 */
public class CurriculumImportException extends RuntimeException {

    public CurriculumImportException(String message) {
        super(message);
    }
}

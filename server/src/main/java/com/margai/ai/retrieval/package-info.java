/**
 * Hybrid retrieval (TECH_PLAN §1.3, §4.3 stage 6, §4.9): the one component that turns a question
 * into NCERT passages, used by the doubt pipeline, by the pipeline's {@code ncert embed} query run
 * and by D23's anchor linking. The SQL it composes lives with the table it reads, in
 * {@code curriculum.api.ParagraphRetrievalRepository}; this package holds the fusion.
 *
 * <p>Exposed as a named interface because the {@code pipeline} module drives the query harness and
 * a feature module may only see another module's named interfaces (§1.4).
 */
@org.springframework.modulith.NamedInterface("retrieval")
package com.margai.ai.retrieval;

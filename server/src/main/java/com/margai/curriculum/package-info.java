/**
 * curriculum module (TECH_PLAN §1.3, read-mostly): syllabus tree, prerequisites, archetype
 * tracks, cutoffs, NCERT books/paragraphs, question bank, topic traps. Owns
 * {@code syllabus_nodes}, {@code syllabus_prerequisites}, {@code archetype_tracks},
 * {@code archetype_track_steps}, {@code cutoffs} from D4 and the content tables of D14–D23.
 * Depends on {@code common} once it exists (§1.4).
 */
@org.springframework.modulith.ApplicationModule(displayName = "curriculum")
package com.margai.curriculum;

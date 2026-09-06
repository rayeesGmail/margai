/**
 * practice module (TECH_PLAN §1.3): sessions, question serving, server-side judging, events,
 * diagnostic. Owns {@code chapter_status} from D4 and the session/event tables of D31–D33.
 * Allowed dependencies per §1.4: {@code common} (once it exists), {@code account :: api},
 * {@code curriculum :: api}. It never reads plan tables (§1.3).
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "practice",
        allowedDependencies = {"account :: api", "curriculum :: api"})
package com.margai.practice;

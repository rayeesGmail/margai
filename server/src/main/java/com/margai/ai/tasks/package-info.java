/**
 * Task classes of the ai module (TECH_PLAN §4.1): each owns one prompt and one output record and
 * is the only kind of caller of {@code AiClient}. Feature modules call tasks, never the client.
 */
@org.springframework.modulith.NamedInterface("tasks")
package com.margai.ai.tasks;

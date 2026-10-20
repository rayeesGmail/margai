package com.margai.pipeline.internal;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code margai.pipeline.pyq.*} — the PYQ commands' knobs (D19), beside {@link PipelineProperties}
 * rather than inside it, so the NCERT commands' record is not reshaped for a second corpus.
 *
 * @param transcribeModel the model {@code pyq extract} must run on: Claude Opus 5.5 at effort low,
 *                        by the founder's D19 ruling (README §1, the open choices), reached through
 *                        the {@code visionopus55} profile. The command refuses any other VISION
 *                        model before the first call, as {@code ncert extract} does — a forgotten
 *                        profile must fail, not transcribe a paper on the default.
 * @param papersDirectory where the approved paper files live under {@code --inputs}
 *                        ({@code papers/}, TECH_PLAN §6.2)
 */
@ConfigurationProperties(prefix = "margai.pipeline.pyq")
public record PyqProperties(String transcribeModel, String papersDirectory) {

    public PyqProperties {
        papersDirectory = papersDirectory == null || papersDirectory.isBlank() ? "papers" : papersDirectory;
    }
}

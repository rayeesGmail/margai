package com.margai.curriculum.api;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The second read of a paragraph ({@code ncert verify --read-pages}, DECISIONS 2026-09-14 "the
 * pair"): a different model than the one that transcribed it read the printed page and said
 * whether this text is what the page prints. It lives on the row, inside
 * {@code ncert_paragraphs.extraction}, beside the provenance of the transcription it judges.
 *
 * <p>A verdict is about <em>words</em>, so it names the text it read by hash: a load that leaves
 * the text alone keeps it, a correction that changes the text drops it, and a verdict can never
 * be recorded against text the row no longer holds.
 *
 * <p>Like the model's confidence, a verdict routes a human's attention and never changes the text
 * by itself — only a founder-approved correction does (TECH_PLAN §6.3, "what is trusted").
 *
 * @param verdict       what the verifier found, after the pipeline's own normalisation and the
 *                      founder's earlier rulings are applied
 * @param differences   the printed and transcribed spans where the verdict is {@code differs}
 * @param textSha256    the SHA-256 of the text that was read, hex
 * @param aiCallIds     the page calls that read it — one per page the paragraph is printed on
 * @param model         the verifying model, from the ledger's own record of the call
 * @param promptVersion the verify prompt, as {@code name.vN}
 */
public record ParagraphVerification(
        Verdict verdict,
        List<Difference> differences,
        String textSha256,
        List<UUID> aiCallIds,
        String model,
        String promptVersion) {

    public ParagraphVerification {
        Objects.requireNonNull(verdict, "verdict");
        Objects.requireNonNull(textSha256, "textSha256");
        differences = differences == null ? List.of() : List.copyOf(differences);
        aiCallIds = aiCallIds == null ? List.of() : List.copyOf(aiCallIds);
    }

    /** Lowercase codes, as every enumeration this project stores (DECISIONS D4). */
    public enum Verdict {
        /** The text is what the page prints. */
        matches,
        /** At least one span differs; {@link #differences} names each. */
        differs,
        /** The verifier could not find this text on the page it is attributed to. */
        not_on_page,
        /** The verifier returned no verdict for it; nothing is known. */
        not_judged
    }

    /**
     * One place the transcription and the print disagree.
     *
     * @param page        the page within the chapter's PDF the span is printed on
     * @param printed     the span as the page prints it, in the transcription's notation
     * @param transcribed the span as the row carries it
     */
    public record Difference(int page, String printed, String transcribed) {
    }

    /** How a verdict names the text it read. */
    public static String sha256(String text) {
        try {
            return HexFormat.of().formatHex(
                    MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is always available", e);
        }
    }
}

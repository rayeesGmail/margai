package com.margai.curriculum.api;

import java.time.LocalDate;

/**
 * Where a paper's answer key came from ({@code pyq_papers.key_source}, migration V9), recorded per
 * the founder's key provenance rule (DECISIONS 2026-10-20): NTA's site keeps only the current
 * year's keys, so a key is accepted from an NTA domain, the Internet Archive's capture of the NTA
 * URL, or a third-party copy of the NTA document — in that order of preference — and never from a
 * publisher or coaching key, which may only cross-check.
 *
 * @param tier       {@code official}, {@code archived-official} or {@code mirrored-official}
 * @param from       where the copy was found
 * @param downloaded the IST date it was downloaded
 * @param file       the key file's content-bucket key
 * @param sha256     the file's sha256, as the founder's MANIFEST.csv records it
 */
public record KeyProvenance(String tier, String from, LocalDate downloaded, String file, String sha256) {
}

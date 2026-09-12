/**
 * storage module (TECH_PLAN §1.3, §1.4): the S3 port — the only place in the server that talks to
 * object storage. Owns no tables. Opened at D14 for the content bucket the pipeline reads and
 * writes (source PDFs, page images, JSONL artefacts); the uploads bucket with its 24-hour
 * lifecycle, signed URLs and deletion join it at D28 (§7.6, SPEC §6.8, R6).
 * Allowed dependency per §1.4: {@code common :: api}.
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "storage",
        allowedDependencies = {"common :: api"})
package com.margai.storage;

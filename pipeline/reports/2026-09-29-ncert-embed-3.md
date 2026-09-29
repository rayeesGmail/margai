# margai-pipeline ncert embed

- run: 2026-09-29 18:38 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1331 paragraph(s) waiting for a vector
- result: FAILED: books.yaml: the embedding provider refused the call (SdkClientException: Unable to execute HTTP request: Read timed out (SDK Attempt Count: 1)) after 746 paragraph(s) this run — those are stored, so re-running embeds only what is left and pays for nothing twice. Check the provider's status and the run's credentials before re-running.
request id: pipeline-ncert-embed-de74985a-b67f-4c90-8d2e-4fb1d220b99b
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'chem11-part1' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 747 | 68599 | ₹7.46 |

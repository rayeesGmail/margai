# margai-pipeline ncert embed

- run: 2026-09-20 14:00 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 880 paragraph(s) waiting for a vector (--redo: every selected paragraph, embedded or not)
- result: FAILED: books.yaml: the embedding provider is out of quota or unavailable (HTTP_429 from the embedding provider) after 74 paragraph(s) this run — those are stored, so re-running embeds only what is left and pays for nothing twice. If it is the per-minute cap, lower margai.pipeline.embed-calls-per-minute (currently 90)
request id: pipeline-ncert-embed-c6864c94-1b66-4aff-98d1-c029885c9605
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the section title prefixed to the paragraph, fragments under 40 characters skipped (D15 experiment; 79 titles read)
concept queries: 15 from ../eval/retrieval-queries.json (5 in Hindi)
pacing: 90 calls/minute (margai.pipeline.embed-calls-per-minute) — about 9 min for 880 paragraphs

## cost

| calls | input tokens | spent |
|---|---|---|
| 75 | 6957 | ₹0.74 |

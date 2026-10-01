# margai-pipeline ncert embed

- run: 2026-09-20 15:52 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 894 paragraph(s) waiting for a vector (--redo: every selected paragraph, embedded or not)
- result: FAILED: books.yaml: the embedding provider is out of quota or unavailable (AccessDeniedException: Bearer Token has expired (Service: BedrockRuntime, Status Code: 403, Request ID: 0ad593b5-a871-4d0a-975b-51415c81d73b) (SDK Attempt Count: 1)) after 0 paragraph(s) this run — those are stored, so re-running embeds only what is left and pays for nothing twice. If it is the per-minute cap, lower margai.pipeline.embed-calls-per-minute (currently 90)
request id: pipeline-ncert-embed-db86aac9-eafc-48da-b540-317e6d13ca2f
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: 15 from ../eval/retrieval-queries.json (5 in Hindi)
pacing: 90 calls/minute (margai.pipeline.embed-calls-per-minute) — about 9 min for 894 paragraphs

## cost

| calls | input tokens | spent |
|---|---|---|
| 1 | 0 | ₹0.00 |

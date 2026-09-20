# margai-pipeline ncert embed

- run: 2026-09-20 16:01 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 894 paragraph(s) waiting for a vector (--redo: every selected paragraph, embedded or not)
- result: FAILED: books.yaml: the embedding provider refused the call (AccessDeniedException: Bearer Token has expired (Service: BedrockRuntime, Status Code: 403, Request ID: e1e35a13-adc6-4884-811d-218554b1bb16) (SDK Attempt Count: 1)) after 0 paragraph(s) this run — those are stored, so re-running embeds only what is left and pays for nothing twice. That reads like credentials, not capacity: refresh the AWS session (`aws sso login --profile margai`) and run it again.
request id: pipeline-ncert-embed-9a9403cc-3961-4ed8-ac3e-be7749ad961a
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: 15 from ../eval/retrieval-queries.json (5 in Hindi)
pacing: 90 calls/minute (margai.pipeline.embed-calls-per-minute) — about 9 min for 894 paragraphs

## cost

| calls | input tokens | spent |
|---|---|---|
| 1 | 0 | ₹0.00 |

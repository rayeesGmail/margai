# margai-pipeline ncert extract

- run: 2026-09-12 23:38 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
request id: pipeline-ncert-extract-ad165011-9137-49d3-8cc0-8efcbb1a7ec2

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 7 | page 13 | SUMMARY | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 17 | 12 | 122 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 17 | 12 | 0 | 5 | 122 |

## low-confidence pages (below 0.80)

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 12 | 26771 | 15840 | 57189 | 5199 | ₹10.71 |
jsonl: extract/phy11-part1/en.jsonl (17 pages, 17 of them from earlier runs)

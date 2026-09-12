# margai-pipeline ncert extract

- run: 2026-09-12 22:32 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
request id: pipeline-ncert-extract-ea80778b-526b-48b7-9c88-5d208c469b85

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 17 | 17 | 135 |

## total

| pages in jsonl | called this run | already done | paragraphs |
|---|---|---|---|
| 17 | 17 | 0 | 135 |

## low-confidence pages (below 0.80)

- ch 7 page 13 — confidence 0.75, 3 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 17 | 37946 | 18720 | 76448 | 4778 | ₹13.13 |
jsonl: extract/phy11-part1/en.jsonl (17 pages, 0 of them from earlier runs)

# margai-pipeline ncert extract

- run: 2026-09-13 23:19 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-7cfaf651-349e-4c2a-91b3-16ce9c50c58d

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 5 | fed as the character authority | 0.411 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 5 | page 16 | SUMMARY | 6 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 5 | 21 | 1 | 7 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 1 | 0 | 6 | 1035 |

## characters that differ from the page's text layer — adjudicate these

checked: 1 of the 1 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 1 of the 1 page(s) called this run
none on the pages checked

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4993 | 958 | 0 | 6448 | ₹1.61 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 143 of them from earlier runs)

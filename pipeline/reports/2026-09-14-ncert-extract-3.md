# margai-pipeline ncert extract

- run: 2026-09-14 15:12 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-7d29d6b3-4b9f-4b2b-bc33-ff178d88c2ee

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 7 | fed as the character authority | 0.389 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 7 | page 13 | SUMMARY | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 17 | 12 | 92 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 17 | 12 | 0 | 5 | 92 |

## characters that differ from the page's text layer — adjudicate these

checked: 12 of the 12 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 12 of the 12 page(s) called this run
- ch 7 p3: only 59% of the page's characters came back — text is missing

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 12 | 71270 | 15968 | 103389 | 9399 | ₹31.22 |
jsonl: extract/phy11-part1/en.jsonl (17 pages, 0 of them from earlier runs)

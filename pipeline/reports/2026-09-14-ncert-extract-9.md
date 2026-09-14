# margai-pipeline ncert extract

- run: 2026-09-14 17:06 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-221554f9-e872-4a69-9baa-e86aa31cd643

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
| 7 | 17 | 0 | 95 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 17 | 0 | 12 | 5 | 95 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 0 page(s) called this run
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 0 page(s) called this run
nothing was checked

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 0 | 0 | 0 | 0 | 0 | ₹0.00 |
jsonl: extract/phy11-part1/en.jsonl (17 pages, 17 of them from earlier runs)

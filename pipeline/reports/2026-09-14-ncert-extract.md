# margai-pipeline ncert extract

- run: 2026-09-14 09:54 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-38ec0f3f-a3d1-4e60-b63d-5b80ab2ce37f

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.306 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 1 | page 9 | SUMMARY | 1 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 9 | 0 | 35 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 252 | 0 | 8 | 1 | 846 |

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
jsonl: extract/bio11/en.jsonl (252 pages, 252 of them from earlier runs)

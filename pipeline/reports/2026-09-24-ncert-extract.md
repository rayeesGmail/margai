# margai-pipeline ncert extract

- run: 2026-09-24 08:05 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-b962f12e-831e-4b32-999e-9f66b0aa44b3

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 12 | fed as the character authority | 0.322 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 12 | page 12 | SUMMARY | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 12 | 13 | 1 | 3 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 252 | 1 | 0 | 2 | 794 |

## characters that differ from the page's text layer — adjudicate these

checked: 1 of the 1 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 1 of the 1 page(s) called this run
none on the pages checked

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 1 of the 1 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 1 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4209 | 394 | 0 | 11025 | ₹8.99 |
jsonl: extract/bio11/en.jsonl (252 pages, 252 of them from earlier runs)

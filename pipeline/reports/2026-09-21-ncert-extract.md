# margai-pipeline ncert extract

- run: 2026-09-21 22:39 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-d4d3d491-443d-40f0-8d85-695da2aa9d30

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
| 1 | 9 | 8 | 34 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 9 | 8 | 0 | 1 | 34 |

## characters that differ from the page's text layer — adjudicate these

checked: 8 of the 8 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 8 of the 8 page(s) called this run
- ch 1 p2: only 0% of the page's characters came back — text is missing

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 8 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 8 | 36652 | 6194 | 75663 | 10809 | ₹39.96 |
jsonl: extract/bio11/en.jsonl (9 pages, 0 of them from earlier runs)

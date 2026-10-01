# margai-pipeline ncert extract

- run: 2026-09-14 18:29 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-3dc14042-d572-4fe5-9750-358277d6a06f

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
| 7 | 17 | 12 | 98 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 17 | 12 | 0 | 5 | 98 |

## characters that differ from the page's text layer — adjudicate these

checked: 12 of the 12 page(s) called this run
- ch 7 p10 §7.8 #9: 'gmm' 4x here, 3x on the page

## pages whose text is not all there — or is there twice

checked: 12 of the 12 page(s) called this run
none on the pages checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 12 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 12 | 69504 | 16445 | 119647 | 10877 | ₹31.98 |
jsonl: extract/phy11-part1/en.jsonl (17 pages, 0 of them from earlier runs)

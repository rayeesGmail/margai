# margai-pipeline ncert extract

- run: 2026-09-25 08:28 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-f46374a8-a804-44bd-a38e-267960165edb

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.306 |
| 2 | fed as the character authority | 0.270 |
| 3 | fed as the character authority | 0.271 |
| 4 | fed as the character authority | 0.262 |
| 5 | fed as the character authority | 0.340 |
| 6 | fed as the character authority | 0.338 |
| 7 | fed as the character authority | 0.326 |
| 8 | fed as the character authority | 0.305 |
| 9 | fed as the character authority | 0.286 |
| 10 | fed as the character authority | 0.302 |
| 11 | fed as the character authority | 0.331 |
| 12 | fed as the character authority | 0.322 |
| 13 | fed as the character authority | 0.305 |
| 14 | fed as the character authority | 0.331 |
| 15 | fed as the character authority | 0.343 |
| 16 | fed as the character authority | 0.332 |
| 17 | fed as the character authority | 0.328 |
| 18 | fed as the character authority | 0.335 |
| 19 | fed as the character authority | 0.287 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 9 | SUMMARY | not sent: nothing taught above the heading | 1 |
| 2 | page 12 | SUMMARY | sent: 19 prose line(s) above the heading | 1 |
| 3 | page 12 | SUMMARY | sent: 9 prose line(s) above the heading | 2 |
| 4 | page 16 | SUMMARY | not sent: nothing taught above the heading | 3 |
| 5 | page 15 | SUMMARY | sent: 12 prose line(s) above the heading | 1 |
| 6 | page 7 | SUMMARY | sent: 21 prose line(s) above the heading | 1 |
| 7 | page 6 | SUMMARY | sent: 13 prose line(s) above the heading | 0 |
| 8 | page 18 | SUMMARY | sent: 5 prose line(s) above the heading | 1 |
| 9 | page 15 | SUMMARY | sent: 20 prose line(s) above the heading | 1 |
| 10 | page 9 | SUMMARY | sent: 11 prose line(s) above the heading | 2 |
| 11 | page 21 | SUMMARY | not sent: nothing taught above the heading | 2 |
| 12 | page 12 | SUMMARY | sent: 6 prose line(s) above the heading | 1 |
| 13 | page 13 | SUMMARY | sent: 18 prose line(s) above the heading | 2 |
| 14 | page 11 | SUMMARY | sent: 6 prose line(s) above the heading | 1 |
| 15 | page 11 | SUMMARY | sent: 16 prose line(s) above the heading | 1 |
| 16 | page 10 | SUMMARY | sent: 15 prose line(s) above the heading | 2 |
| 17 | page 11 | SUMMARY | sent: 27 prose line(s) above the heading | 2 |
| 18 | page 8 | SUMMARY | not sent: nothing taught above the heading | 2 |
| 19 | page 11 | SUMMARY | not sent: nothing taught above the heading | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 9 | 0 | 34 |
| 2 | 13 | 1 | 55 |
| 3 | 14 | 1 | 35 |
| 4 | 18 | 0 | 65 |
| 5 | 16 | 1 | 58 |
| 6 | 8 | 1 | 21 |
| 7 | 6 | 1 | 22 |
| 8 | 19 | 1 | 71 |
| 9 | 16 | 1 | 63 |
| 10 | 11 | 1 | 40 |
| 11 | 22 | 0 | 83 |
| 12 | 13 | 1 | 50 |
| 13 | 15 | 1 | 50 |
| 14 | 12 | 1 | 30 |
| 15 | 12 | 1 | 41 |
| 16 | 12 | 1 | 37 |
| 17 | 13 | 1 | 38 |
| 18 | 9 | 0 | 23 |
| 19 | 14 | 0 | 39 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 252 | 14 | 208 | 30 | 855 |

## characters that differ from the page's text layer — adjudicate these

checked: 14 of the 14 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 14 of the 14 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 2 p12: 4 paragraph(s) — sent: 19 prose line(s) above the heading
- ch 3 p12: 1 paragraph(s) — sent: 9 prose line(s) above the heading
- ch 5 p15: 12 paragraph(s) — sent: 12 prose line(s) above the heading
- ch 6 p7: 4 paragraph(s) — sent: 21 prose line(s) above the heading
- ch 7 p6: 3 paragraph(s) — sent: 13 prose line(s) above the heading
- ch 8 p18: 2 paragraph(s) — sent: 5 prose line(s) above the heading
- ch 9 p15: 5 paragraph(s) — sent: 20 prose line(s) above the heading
- ch 10 p9: 2 paragraph(s) — sent: 11 prose line(s) above the heading
- ch 12 p12: 4 paragraph(s) — sent: 6 prose line(s) above the heading
- ch 13 p13: 5 paragraph(s) — sent: 18 prose line(s) above the heading
- ch 14 p11: 1 paragraph(s) — sent: 6 prose line(s) above the heading
- ch 15 p11: 4 paragraph(s) — sent: 16 prose line(s) above the heading
- ch 16 p10: 4 paragraph(s) — sent: 15 prose line(s) above the heading
- ch 17 p11: 10 paragraph(s) — sent: 27 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 14 of the 14 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 14 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 14 | 67065 | 7671 | 143325 | 11025 | ₹60.17 |
jsonl: extract/bio11/en.jsonl (252 pages, 252 of them from earlier runs)

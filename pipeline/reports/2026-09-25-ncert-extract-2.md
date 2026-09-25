# margai-pipeline ncert extract

- run: 2026-09-25 08:34 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-d109ab5f-19d7-4225-94d1-15f95654f011

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.379 |
| 2 | fed as the character authority | 0.401 |
| 3 | fed as the character authority | 0.470 |
| 4 | fed as the character authority | 0.405 |
| 5 | fed as the character authority | 0.411 |
| 6 | fed as the character authority | 0.412 |
| 7 | fed as the character authority | 0.389 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 10 | SUMMARY | not sent: nothing taught above the heading | 3 |
| 2 | page 9 | SUMMARY | sent: 30 prose line(s) above the heading | 5 |
| 3 | page 17 | SUMMARY | not sent: nothing taught above the heading | 6 |
| 4 | page 18 | SUMMARY | sent: 14 prose line(s) above the heading | 4 |
| 5 | page 16 | SUMMARY | sent: 20 prose line(s) above the heading | 5 |
| 6 | page 32 | SUMMARY | sent: 6 prose line(s) above the heading | 3 |
| 7 | page 13 | SUMMARY | sent: 8 prose line(s) above the heading | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 12 | 0 | 89 |
| 2 | 14 | 1 | 60 |
| 3 | 22 | 0 | 109 |
| 4 | 22 | 1 | 151 |
| 5 | 21 | 1 | 139 |
| 6 | 35 | 1 | 301 |
| 7 | 17 | 1 | 112 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 5 | 108 | 30 | 961 |

## characters that differ from the page's text layer — adjudicate these

checked: 5 of the 5 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 5 of the 5 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 2 p9: 6 paragraph(s) — sent: 30 prose line(s) above the heading
- ch 4 p18: 4 paragraph(s) — sent: 14 prose line(s) above the heading
- ch 5 p16: 3 paragraph(s) — sent: 20 prose line(s) above the heading
- ch 6 p32: 2 paragraph(s) — sent: 6 prose line(s) above the heading
- ch 7 p13: 4 paragraph(s) — sent: 8 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 5 of the 5 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 5 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 5 | 26278 | 2826 | 55125 | 0 | ₹20.69 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 143 of them from earlier runs)

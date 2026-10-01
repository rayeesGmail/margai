# margai-pipeline ncert extract

- run: 2026-09-27 09:42 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of phy12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-569fe9c5-a32f-4756-bee7-e4c70b3bc5a7

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 9 | fed as the character authority | 0.398 |
| 10 | fed as the character authority | 0.378 |
| 11 | fed as the character authority | 0.364 |
| 12 | fed as the character authority | 0.373 |
| 13 | fed as the character authority | 0.381 |
| 14 | fed as the character authority | 0.344 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 9 | page 26 | SUMMARY | sent: 15 prose line(s) above the heading | 8 |
| 10 | page 18 | POINTS TO PONDER | sent: 25 prose line(s) above the heading | 1 |
| 11 | page 13 | SUMMARY | sent: 10 prose line(s) above the heading | 3 |
| 12 | page 14 | SUMMARY | not sent: nothing taught above the heading | 3 |
| 13 | page 13 | SUMMARY | sent: 17 prose line(s) above the heading | 4 |
| 14 | page 18 | SUMMARY | sent: 2 prose line(s) above the heading | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 9 | 34 | 0 | 142 |
| 10 | 19 | 18 | 82 |
| 11 | 16 | 0 | 76 |
| 12 | 16 | 13 | 63 |
| 13 | 17 | 13 | 94 |
| 14 | 21 | 18 | 85 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 123 | 62 | 39 | 22 | 542 |

## characters that differ from the page's text layer — adjudicate these

checked: 62 of the 62 page(s) called this run
- ch 10 p2 §10.1 #1: 'micrometre' 1x here, 0x on the page
- ch 10 p10 §10.4 #2: 'cos' 10x here, 8x on the page
- ch 14 p13 §14.6.2 #6: 'mua' 2x here, 0x on the page
- ch 14 p15 §14.6.2 #2: 'mua' 1x here, 0x on the page
- ch 14 p15 §14.6.2 #7: 'mua' 2x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 62 of the 62 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 10 p18: 0 paragraph(s) — sent: 25 prose line(s) above the heading
- ch 13 p13: 2 paragraph(s) — sent: 17 prose line(s) above the heading
- ch 14 p18: 0 paragraph(s) — sent: 2 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 62 of the 62 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 62 page(s) called this run
- ch 10 p14 §10.6.2 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…gle of (1/2)°.…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 62 | 292628 | 61574 | 687104 | 11264 | ₹307.80 |
jsonl: extract/phy12-part2/en.jsonl (123 pages, 50 of them from earlier runs)

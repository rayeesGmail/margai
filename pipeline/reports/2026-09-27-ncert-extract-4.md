# margai-pipeline ncert extract

- run: 2026-09-27 17:24 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 5 chapters of chem12-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-e04fa435-8631-4aef-8a0e-fcd50d63bfcb

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.345 |
| 2 | fed as the character authority | 0.325 |
| 3 | fed as the character authority | 0.362 |
| 4 | fed as the character authority | 0.322 |
| 5 | fed as the character authority | 0.301 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 27 | SUMMARY | not sent: nothing taught above the heading | 4 |
| 2 | page 28 | SUMMARY | sent: 17 prose line(s) above the heading | 2 |
| 3 | page 24 | SUMMARY | sent: 6 prose line(s) above the heading | 4 |
| 4 | page 26 | SUMMARY | sent: 12 prose line(s) above the heading | 3 |
| 5 | page 20 | SUMMARY | sent: 28 prose line(s) above the heading | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 30 | 26 | 160 |
| 2 | 30 | 0 | 145 |
| 3 | 28 | 24 | 163 |
| 4 | 29 | 26 | 138 |
| 5 | 23 | 20 | 120 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 140 | 96 | 28 | 16 | 726 |

## characters that differ from the page's text layer — adjudicate these

checked: 96 of the 96 page(s) called this run
- ch 3 p12 §3.3.2 #5: 'kdt' 1x here, 0x on the page
- ch 3 p22 §3.4.1 #6: 'deltag' 1x here, 0x on the page
- ch 4 p18 §4.4.1 #9: 'fused' 1x here, 0x on the page
- ch 4 p18 §4.4.1 #9: 'oxidised' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 96 of the 96 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 3 p24: 0 paragraph(s) — sent: 6 prose line(s) above the heading
- ch 4 p26: 1 paragraph(s) — sent: 12 prose line(s) above the heading
- ch 5 p20: 6 paragraph(s) — sent: 28 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 96 of the 96 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 96 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 96 | 463722 | 95034 | 1070080 | 11264 | ₹477.46 |
jsonl: extract/chem12-part1/en.jsonl (140 pages, 30 of them from earlier runs)

# margai-pipeline ncert extract

- run: 2026-09-26 15:35 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-b64697ad-920f-451a-a713-a960db10a341

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 8 | fed as the character authority | 0.385 |
| 9 | fed as the character authority | 0.395 |
| 10 | fed as the character authority | 0.368 |
| 11 | fed as the character authority | 0.388 |
| 12 | fed as the character authority | 0.363 |
| 13 | fed as the character authority | 0.405 |
| 14 | fed as the character authority | 0.385 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 8 | page 10 | SUMMARY | sent: 20 prose line(s) above the heading | 3 |
| 9 | page 18 | SUMMARY | sent: 49 prose line(s) above the heading | 4 |
| 10 | page 20 | SUMMARY | sent: 17 prose line(s) above the heading | 4 |
| 11 | page 14 | SUMMARY | sent: 51 prose line(s) above the heading | 4 |
| 12 | page 13 | SUMMARY | not sent: nothing taught above the heading | 3 |
| 13 | page 14 | SUMMARY | not sent: nothing taught above the heading | 6 |
| 14 | page 18 | SUMMARY | not sent: nothing taught above the heading | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 8 | 13 | 0 | 67 |
| 9 | 22 | 18 | 134 |
| 10 | 24 | 20 | 120 |
| 11 | 18 | 14 | 99 |
| 12 | 15 | 12 | 102 |
| 13 | 19 | 13 | 103 |
| 14 | 22 | 17 | 113 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 133 | 94 | 10 | 29 | 738 |

## characters that differ from the page's text layer — adjudicate these

checked: 94 of the 94 page(s) called this run
- ch 9 p13 §9.5 #1: 'stress' 2x here, 1x on the page
- ch 9 p13 §9.5 #1: 'strain' 2x here, 1x on the page
- ch 14 p16 §14.7 #5: 'cosb' 2x here, 1x on the page

## pages whose text is not all there — or is there twice

checked: 94 of the 94 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 9 p18: 7 paragraph(s) — sent: 49 prose line(s) above the heading
- ch 10 p20: 6 paragraph(s) — sent: 17 prose line(s) above the heading
- ch 11 p14: 2 paragraph(s) — sent: 51 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 94 of the 94 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 94 page(s) called this run
- ch 10 p2 §10.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…ee Celsius (°C) is a commonly used un…"
- ch 10 p2 §10.4 #5: a degree sign not after a number — the layer's Greek letter copied through?: "…perature in °C). When temperature is …"
- ch 10 p13 §10.8.1 #2: a degree sign not after a number — the layer's Greek letter copied through?: "…1) [0–(–12)]°C = 75600 J Q_2 = heat r…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 94 | 499041 | 125286 | 1047552 | 11264 | ₹560.42 |
jsonl: extract/phy11-part2/en.jsonl (133 pages, 13 of them from earlier runs)

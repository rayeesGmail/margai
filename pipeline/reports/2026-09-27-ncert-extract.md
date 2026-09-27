# margai-pipeline ncert extract

- run: 2026-09-27 09:14 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 2 chapters of phy12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-43b5a029-8fa2-4017-b484-78f813c4352b

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 9 | fed as the character authority | 0.398 |
| 11 | fed as the character authority | 0.364 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 9 | page 26 | SUMMARY | sent: 15 prose line(s) above the heading | 8 |
| 11 | page 13 | SUMMARY | sent: 10 prose line(s) above the heading | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 9 | 34 | 26 | 142 |
| 11 | 16 | 13 | 76 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 50 | 39 | 0 | 11 | 218 |

## characters that differ from the page's text layer — adjudicate these

checked: 39 of the 39 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 39 of the 39 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 9 p26: 1 paragraph(s) — sent: 15 prose line(s) above the heading
- ch 11 p13: 4 paragraph(s) — sent: 10 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 39 of the 39 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 39 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 39 | 181357 | 38090 | 428032 | 11264 | ₹193.09 |
jsonl: extract/phy12-part2/en.jsonl (50 pages, 0 of them from earlier runs)

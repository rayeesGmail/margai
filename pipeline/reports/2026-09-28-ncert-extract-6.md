# margai-pipeline ncert extract

- run: 2026-09-28 23:14 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-e444114e-f33f-4088-950e-502f753bc680

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 7 | fed as the character authority | 0.304 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 7 | page 29 | SUMMARY | not sent: nothing taught above the heading | 6 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 34 | 4 | 26 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 144 | 4 | 0 | 6 | 675 |

## characters that differ from the page's text layer — adjudicate these

checked: 4 of the 4 page(s) called this run
- ch 7 p18 §7.4.4 #1: 'rch' 2x here, 0x on the page
- ch 7 p18 §7.4.4 #1: 'rcho' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 4 of the 4 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 4 of the 4 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 4 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 4 | 17671 | 3164 | 45056 | 0 | ₹17.12 |
jsonl: extract/chem12-part2/en.jsonl (144 pages, 144 of them from earlier runs)

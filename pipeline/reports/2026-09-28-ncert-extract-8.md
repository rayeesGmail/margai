# margai-pipeline ncert extract

- run: 2026-09-28 23:19 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-3b35bee4-34d5-43f8-a005-88a4691480fb

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 9 | fed as the character authority | 0.293 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 9 | page 19 | SUMMARY | sent: 5 prose line(s) above the heading | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 9 | 22 | 2 | 14 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 144 | 2 | 0 | 3 | 677 |

## characters that differ from the page's text layer — adjudicate these

checked: 2 of the 2 page(s) called this run
- ch 9 p12 §9.6 #7: 'cocl' 2x here, 1x on the page
- ch 9 p12 §9.6 #7: 'nhcoc' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 2 of the 2 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 2 of the 2 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 2 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 2 | 8934 | 1558 | 22528 | 0 | ₹8.55 |
jsonl: extract/chem12-part2/en.jsonl (144 pages, 144 of them from earlier runs)

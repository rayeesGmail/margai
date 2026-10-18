# margai-pipeline ncert extract

- run: 2026-10-01 17:38 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
artefact: extract/chem12-part2/en.opus55.jsonl (a scratch run's own; the canonical JSONL is untouched)
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-3bf5b930-b882-452e-a227-8b015a8b5023

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
| 7 | 34 | 8 | 39 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 14 | 8 | 0 | 6 | 39 |

## characters that differ from the page's text layer — adjudicate these

checked: 8 of the 8 page(s) called this run
- ch 7 p6 §7.2 #3: 'substituted' 2x here, 0x on the page
- ch 7 p6 §7.2 #3: 'benzene' 1x here, 0x on the page
- ch 7 p8 §7.4.1 #1: 'propan' 1x here, 0x on the page
- ch 7 p8 §7.4.1 #4: 'nabh' 2x here, 1x on the page
- ch 7 p8 §7.4.1 #4: 'rcho' 2x here, 1x on the page
- ch 7 p8 §7.4.1 #4: 'rch' 3x here, 1x on the page
- ch 7 p8 §7.4.1 #4: 'rcor' 1x here, 0x on the page
- ch 7 p8 §7.4.1 #6: 'rcoor' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 8 of the 8 page(s) called this run
- ch 7 p5: only 24% of the page's characters came back — text is missing

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 8 of the 8 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 8 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 8 | 35701 | 5947 | 90112 | 0 | ₹25.21 |
jsonl: extract/chem12-part2/en.opus55.jsonl (14 pages, 0 of them from earlier runs)

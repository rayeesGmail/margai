# margai-pipeline ncert extract

- run: 2026-09-26 08:42 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-9621868b-da64-4edd-bbb9-daf373dffee2

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 11 | fed as the character authority | 0.331 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 11 | page 21 | SUMMARY | not sent: nothing taught above the heading | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 11 | 22 | 2 | 14 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 252 | 2 | 0 | 2 | 857 |

## characters that differ from the page's text layer — adjudicate these

checked: 2 of the 2 page(s) called this run
none on the pages checked

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
| 2 | 9999 | 2543 | 11264 | 11264 | ₹17.08 |
jsonl: extract/bio11/en.jsonl (252 pages, 252 of them from earlier runs)

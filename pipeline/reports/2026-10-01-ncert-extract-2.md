# margai-pipeline ncert extract

- run: 2026-10-01 17:33 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
artefact: extract/bio11/en.opus55.jsonl (a scratch run's own; the canonical JSONL is untouched)
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-817fbbbc-1211-49f7-a10e-99df02314092

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.306 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 9 | SUMMARY | not sent: nothing taught above the heading | 1 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 9 | 8 | 33 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 9 | 8 | 0 | 1 | 33 |

## characters that differ from the page's text layer — adjudicate these

checked: 8 of the 8 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 8 of the 8 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 8 of the 8 page(s) called this run
- ch 1 p1: nothing came back; the layer holds 1233 characters in 10 sentence-length runs
- ch 1 p2: nothing came back; the layer holds 803 characters in 6 sentence-length runs

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 8 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 8 | 35462 | 6114 | 90112 | 0 | ₹25.43 |
jsonl: extract/bio11/en.opus55.jsonl (9 pages, 0 of them from earlier runs)

# margai-pipeline ncert extract

- run: 2026-09-30 06:57 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem11-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-8929b526-79cf-4322-9fcf-1d4d79e47681

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 8 | withheld: illegible | 0.023 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 8 | page 36 | SUMMARY | sent: the heading could not be placed on it | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 8 | 39 | 36 | 286 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 39 | 36 | 0 | 3 | 286 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 36 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 36 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 36 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 36 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 36 | 142477 | 41159 | 394240 | 11264 | ₹180.97 |
jsonl: extract/chem11-part2/en.jsonl (39 pages, 0 of them from earlier runs)

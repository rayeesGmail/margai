# margai-pipeline ncert extract

- run: 2026-10-01 17:36 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 2 chapters of bio12 (en)
- result: ok
content store: s3://margai-beta-content
artefact: extract/bio12/en.opus55.jsonl (a scratch run's own; the canonical JSONL is untouched)
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-30cdeca3-06a4-4f96-96e4-ea5c928d917f

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 7 | fed as the character authority | 0.299 |
| 9 | fed as the character authority | 0.310 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 7 | page 21 | SUMMARY | sent: 24 prose line(s) above the heading | 1 |
| 9 | page 15 | SUMMARY | sent: 6 prose line(s) above the heading | 1 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 22 | 10 | 34 |
| 9 | 16 | 10 | 36 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 22 | 20 | 0 | 2 | 70 |

## characters that differ from the page's text layer — adjudicate these

checked: 20 of the 20 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 20 of the 20 page(s) called this run
none on the pages checked

and 1 page(s) the ratio could not judge:
- ch 7 p6: the layer holds 357 characters, too few to measure a ratio against

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 20 of the 20 page(s) called this run
- ch 9 p2: nothing came back; the layer holds 1255 characters in 11 sentence-length runs
- ch 7 p1: nothing came back; the layer holds 789 characters in 8 sentence-length runs
- ch 7 p2: nothing came back; the layer holds 956 characters in 7 sentence-length runs
- ch 9 p1: nothing came back; the layer holds 803 characters in 6 sentence-length runs

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 20 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 20 | 88646 | 15001 | 225280 | 0 | ₹63.05 |
jsonl: extract/bio12/en.opus55.jsonl (22 pages, 0 of them from earlier runs)

# margai-pipeline ncert extract

- run: 2026-09-26 07:57 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio12 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-e90d0224-f80e-4042-91f6-7fbef1ee3500

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.325 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 23 | SUMMARY | sent: 22 prose line(s) above the heading | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 25 | 23 | 75 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 25 | 23 | 0 | 2 | 75 |

## characters that differ from the page's text layer — adjudicate these

checked: 23 of the 23 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 23 of the 23 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 1 p23: 2 paragraph(s) — sent: 22 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 23 of the 23 page(s) called this run
- ch 1 p2: nothing came back; the layer holds 1207 characters in 11 sentence-length runs
- ch 1 p1: nothing came back; the layer holds 743 characters in 6 sentence-length runs

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 23 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 23 | 111857 | 17697 | 253575 | 11025 | ₹107.90 |
jsonl: extract/bio12/en.jsonl (25 pages, 0 of them from earlier runs)

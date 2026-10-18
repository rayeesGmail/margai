# margai-pipeline ncert extract

- run: 2026-10-01 18:16 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
artefact: extract/chem12-part2/en.opus55m.jsonl (a scratch run's own; the canonical JSONL is untouched)
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-7a229f2f-a2e1-448d-b9e3-342ed8ac2b34

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
| 7 | 34 | 7 | 33 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 13 | 7 | 0 | 6 | 33 |

## characters that differ from the page's text layer — adjudicate these

checked: 7 of the 7 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 7 of the 7 page(s) called this run
- ch 7 p5: only 24% of the page's characters came back — text is missing

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 7 of the 7 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 7 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 7 | 31139 | 7722 | 78848 | 0 | ₹26.55 |
jsonl: extract/chem12-part2/en.opus55m.jsonl (13 pages, 0 of them from earlier runs)

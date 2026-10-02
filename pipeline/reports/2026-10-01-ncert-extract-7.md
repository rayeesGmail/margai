# margai-pipeline ncert extract

- run: 2026-10-01 22:48 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy11-part1/hi.hiopus5.jsonl (a scratch run's own; the canonical JSONL is untouched)
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-ec8399ef-558c-4522-8af6-af3406d8e6ab

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.023 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 10 | सारांश | sent: the heading could not be placed on it | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 12 | 10 | 78 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 12 | 10 | 0 | 2 | 78 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 10 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 10 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 1 p10: 0 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 10 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 10 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 10 | 38349 | 21473 | 101376 | 11264 | ₹76.53 |
jsonl: extract/phy11-part1/hi.hiopus5.jsonl (12 pages, 0 of them from earlier runs)

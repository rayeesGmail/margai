# margai-pipeline ncert extract

- run: 2026-10-01 17:32 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy11-part1/en.opus55.jsonl (a scratch run's own; the canonical JSONL is untouched)
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-fbf7a47b-695f-49e5-a4f7-56099a87f046

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 7 | fed as the character authority | 0.389 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 7 | page 13 | SUMMARY | sent: 8 prose line(s) above the heading | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 17 | 13 | 111 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 17 | 13 | 0 | 4 | 111 |

## characters that differ from the page's text layer — adjudicate these

checked: 13 of the 13 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 13 of the 13 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 7 p13: 4 paragraph(s) — sent: 8 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 13 of the 13 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 13 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 13 | 73843 | 17828 | 135168 | 11264 | ₹66.26 |
jsonl: extract/phy11-part1/en.opus55.jsonl (17 pages, 0 of them from earlier runs)

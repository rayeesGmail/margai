# margai-pipeline ncert extract

- run: 2026-10-11 22:24 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy11-part2/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-2880f52b-ea07-4644-89b5-35567895d05e

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 10 | withheld: illegible | 0.012 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 10 | page 21 | सारांश | sent: the heading could not be placed on it | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 10 | 26 | 1 | 5 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 140 | 1 | 0 | 5 | 684 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 1 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 1 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 1 of the 1 page(s) called this run

words the rows carry that the page does not — a misreading or an addition:
none on the pages checked

symbols the page carries more often, digits and Latin only the rows carry:
none on the pages checked

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 1 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 1 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 10 page 19 — confidence 0.75, 5 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 3989 | 2465 | 0 | 11264 | ₹10.95 |
jsonl: extract/phy11-part2/hi.jsonl (140 pages, 140 of them from earlier runs)

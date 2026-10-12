# margai-pipeline ncert extract

- run: 2026-10-12 15:22 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy12-part1/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-56569394-2ec0-41ba-9305-ee228964e93a

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.016 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 37 | सारांश | sent: the heading could not be placed on it | 6 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 43 | 37 | 223 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 43 | 37 | 0 | 6 | 223 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 37 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 37 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 37 of the 37 page(s) called this run

words the rows carry that the page does not — a misreading or an addition:
- ch 1 p3: 'परिमाण' 1x in the rows, 0x on the page
- ch 1 p5: 'निर्दर्शित' 1x in the rows, 0x on the page
- ch 1 p5: 'ऐम्पियर' 1x in the rows, 0x on the page
- ch 1 p37: 'निर्दिशित' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 1 p8: '•' 1x on the page, 0x in the rows
- ch 1 p9: '•' 2x on the page, 0x in the rows
- ch 1 p18: '1.3' 1x in the rows, 0x on the page
- ch 1 p18: '1.6' 1x in the rows, 0x on the page
- ch 1 p18: '2.0' 1x in the rows, 0x on the page
- ch 1 p24: '×' 2x on the page, 1x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 1 p37: 2 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 37 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 37 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 1 page 9 — confidence 0.75, 8 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 37 | 135486 | 70658 | 405504 | 11264 | ₹188.51 |
jsonl: extract/phy12-part1/hi.jsonl (43 pages, 0 of them from earlier runs)

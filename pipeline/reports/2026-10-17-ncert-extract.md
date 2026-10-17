# margai-pipeline ncert extract

- run: 2026-10-17 06:59 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 2 chapters of bio11 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/bio11/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-57dfe80f-257c-4c03-b7c1-f0046de6b347

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.009 |
| 2 | withheld: illegible | 0.004 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 9 | सारांश | sent: the heading could not be placed on it | 0 |
| 2 | page 12 | सारांश | sent: the heading could not be placed on it | 1 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 9 | 9 | 30 |
| 2 | 13 | 12 | 50 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 22 | 21 | 0 | 1 | 80 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 21 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 21 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 21 of the 21 page(s) called this run

words the rows carry more often than the page — a misreading or an addition:
- ch 2 p4: 'संबंधी' 1x in the rows, 0x on the page
- ch 2 p7: 'पदार्थो' 1x in the rows, 0x on the page
- ch 2 p10: 'वर्गो' 1x in the rows, 0x on the page
- ch 2 p12: 'माइकोबायंट' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
none on the pages checked

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 1 p1: the rows carry 0% of the page's Devanagari words
- ch 1 p2: the rows carry 0% of the page's Devanagari words

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 1 p9: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 2 p12: 4 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 21 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 21 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 2 page 2 — confidence 0.75, 2 paragraphs
- ch 2 page 4 — confidence 0.75, 4 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 21 | 77895 | 30731 | 225280 | 11264 | ₹92.58 |
jsonl: extract/bio11/hi.jsonl (22 pages, 0 of them from earlier runs)

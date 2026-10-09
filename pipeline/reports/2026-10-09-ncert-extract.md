# margai-pipeline ncert extract

- run: 2026-10-09 09:55 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy11-part2/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-657b8406-e390-4c96-8e27-6e1c512f0a87

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 8 | withheld: illegible | 0.015 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 8 | page 11 | सारांश | sent: the heading could not be placed on it | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 8 | 14 | 11 | 55 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 14 | 11 | 0 | 3 | 55 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 11 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 11 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 11 of the 11 page(s) called this run

words the rows carry that the page does not — a misreading or an addition:
- ch 8 p1: 'स्प्रिंग' 3x in the rows, 0x on the page
- ch 8 p2: 'बाह्य' 1x in the rows, 0x on the page
- ch 8 p2: 'अनुदैर्ध्य' 3x in the rows, 0x on the page
- ch 8 p8: 'अनुदैर्ध्य' 3x in the rows, 0x on the page
- ch 8 p8: 'विरुद्ध' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 8 p3: '∝' 1x in the rows, 0x on the page
- ch 8 p8: '×' 16x on the page, 15x in the rows
- ch 8 p9: '×' 14x on the page, 11x in the rows
- ch 8 p11: '×' 7x on the page, 6x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 8 p11: 2 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 11 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 11 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 8 page 8 — confidence 0.75, 6 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 11 | 43470 | 20607 | 112640 | 11264 | ₹59.87 |
jsonl: extract/phy11-part2/hi.jsonl (14 pages, 0 of them from earlier runs)

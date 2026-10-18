# margai-pipeline ncert extract

- run: 2026-10-09 17:15 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy11-part2/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-14fbae97-d7c5-4fa7-911c-f5dbd9506677

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 8 | withheld: illegible | 0.015 |
| 9 | withheld: illegible | 0.023 |
| 10 | withheld: illegible | 0.012 |
| 11 | withheld: illegible | 0.041 |
| 12 | withheld: illegible | 0.010 |
| 13 | withheld: illegible | 0.047 |
| 14 | withheld: illegible | 0.029 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 8 | page 11 | सारांश | sent: the heading could not be placed on it | 3 |
| 9 | page 20 | सारांश | sent: the heading could not be placed on it | 4 |
| 10 | page 21 | सारांश | sent: the heading could not be placed on it | 5 |
| 11 | page 14 | सारांश | sent: the heading could not be placed on it | 4 |
| 12 | page 13 | सारांश | sent: the heading could not be placed on it | 3 |
| 13 | page 13 | सारांश | sent: the heading could not be placed on it | 6 |
| 14 | page 18 | सारांश | sent: the heading could not be placed on it | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 8 | 14 | 0 | 55 |
| 9 | 24 | 0 | 129 |
| 10 | 26 | 1 | 111 |
| 11 | 18 | 14 | 91 |
| 12 | 16 | 13 | 95 |
| 13 | 19 | 13 | 88 |
| 14 | 23 | 18 | 114 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 140 | 59 | 51 | 30 | 683 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 59 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 59 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 59 of the 59 page(s) called this run

words the rows carry that the page does not — a misreading or an addition:
- ch 11 p6: 'शर्तों' 1x in the rows, 0x on the page
- ch 11 p13: 'स्रोत' 1x in the rows, 0x on the page
- ch 11 p14: 'स्रोत' 3x in the rows, 0x on the page
- ch 11 p14: 'किंतु' 1x in the rows, 0x on the page
- ch 12 p10: 'उदाहरणार्थ' 1x in the rows, 0x on the page
- ch 13 p6: 'परछाई' 1x in the rows, 0x on the page
- ch 13 p13: 'परिमाण' 1x in the rows, 0x on the page
- ch 14 p3: 'अनुदैर्ध्य' 1x in the rows, 0x on the page
- ch 14 p4: 'क्वार्ट्ज़' 1x in the rows, 0x on the page
- ch 14 p10: 'अतिव्यापित' 1x in the rows, 0x on the page
- ch 14 p12: 'परिमाण' 1x in the rows, 0x on the page
- ch 14 p17: 'अनुदैर्ध्य' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 11 p12: '→' 1x in the rows, 0x on the page
- ch 12 p7: 'BT' 1x in the rows, 0x on the page
- ch 12 p9: '→' 3x in the rows, 0x on the page
- ch 12 p10: 'BT' 4x in the rows, 0x on the page
- ch 12 p11: 'BT' 10x in the rows, 0x on the page
- ch 12 p12: 'mol' 1x in the rows, 0x on the page
- ch 12 p12: 'BT' 4x in the rows, 0x on the page
- ch 12 p13: '23' 1x in the rows, 0x on the page
- ch 13 p3: '•' 1x on the page, 0x in the rows
- ch 13 p4: '•' 1x on the page, 0x in the rows
- ch 13 p6: '•' 1x on the page, 0x in the rows
- ch 13 p7: '•' 1x on the page, 0x in the rows
- ch 13 p9: '•' 1x on the page, 0x in the rows
- ch 13 p10: '•' 1x on the page, 0x in the rows
- ch 13 p11: '•' 1x on the page, 0x in the rows
- ch 13 p13: '•' 1x on the page, 0x in the rows
- ch 14 p4: '•' 1x on the page, 0x in the rows
- ch 14 p7: '•' 3x on the page, 2x in the rows
- ch 14 p8: '•' 1x on the page, 0x in the rows
- ch 14 p9: 'BT' 1x in the rows, 0x on the page
- ch 14 p10: '•' 1x on the page, 0x in the rows
- ch 14 p16: '•' 1x on the page, 0x in the rows
- ch 14 p18: '•' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 12 p2: the rows carry 42% of the page's Devanagari words

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 10 p21: 4 paragraph(s) — sent: the heading could not be placed on it
- ch 11 p14: 2 paragraph(s) — sent: the heading could not be placed on it
- ch 12 p13: 9 paragraph(s) — sent: the heading could not be placed on it
- ch 13 p13: 7 paragraph(s) — sent: the heading could not be placed on it
- ch 14 p18: 2 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 59 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 59 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 10 page 21 — confidence 0.75, 4 paragraphs
- ch 14 page 14 — confidence 0.7, 4 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 59 | 237861 | 131349 | 653312 | 11264 | ₹339.16 |
jsonl: extract/phy11-part2/hi.jsonl (140 pages, 58 of them from earlier runs)

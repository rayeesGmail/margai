# margai-pipeline ncert extract

- run: 2026-10-15 07:25 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of phy12-part2 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy12-part2/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-d05455b9-8f70-4d1e-be2d-4ab1296f8e76

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 9 | withheld: illegible | 0.016 |
| 10 | withheld: illegible | 0.014 |
| 11 | withheld: illegible | 0.008 |
| 12 | withheld: illegible | 0.005 |
| 13 | withheld: illegible | 0.012 |
| 14 | withheld: illegible | 0.012 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 9 | page 27 | सारांश | sent: the heading could not be placed on it | 7 |
| 10 | page 17 | सारांश | sent: the heading could not be placed on it | 2 |
| 11 | page 13 | सारांश | sent: the heading could not be placed on it | 3 |
| 12 | page 14 | सारांश | sent: the heading could not be placed on it | 2 |
| 13 | page 13 | सारांश | sent: the heading could not be placed on it | 4 |
| 14 | page 18 | सारांश | sent: the heading could not be placed on it | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 9 | 34 | 0 | 138 |
| 10 | 19 | 17 | 76 |
| 11 | 16 | 13 | 71 |
| 12 | 16 | 14 | 61 |
| 13 | 17 | 13 | 88 |
| 14 | 20 | 18 | 78 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 122 | 75 | 27 | 20 | 512 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 75 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 75 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 75 of the 75 page(s) called this run

words the rows carry more often than the page — a misreading or an addition:
- ch 10 p2: 'तरंग' 8x in the rows, 7x on the page
- ch 10 p5: 'बिंदु' 2x in the rows, 1x on the page
- ch 10 p8: 'बिंदु' 7x in the rows, 6x on the page
- ch 10 p10: 'स्रोत' 6x in the rows, 5x on the page
- ch 10 p13: 'स्रोत' 4x in the rows, 3x on the page
- ch 10 p13: 'स्रोतों' 1x in the rows, 0x on the page
- ch 10 p13: 'फ़िज़िक्स' 1x in the rows, 0x on the page
- ch 11 p4: 'क्वार्ट्ज़' 2x in the rows, 1x on the page
- ch 11 p4: 'स्रोत' 2x in the rows, 0x on the page
- ch 11 p5: 'तेजी' 1x in the rows, 0x on the page
- ch 11 p8: 'सन्' 1x in the rows, 0x on the page
- ch 11 p11: 'स्रोत' 2x in the rows, 1x on the page
- ch 12 p2: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 12 p3: 'सरेखित' 1x in the rows, 0x on the page
- ch 12 p13: 'हाइडोजन' 1x in the rows, 0x on the page
- ch 13 p3: 'प्रोटॉन' 9x in the rows, 8x on the page
- ch 13 p9: 'सन्' 1x in the rows, 0x on the page
- ch 13 p12: 'प्लाज़्मा' 1x in the rows, 0x on the page
- ch 14 p2: 'ट्रांज़िस्टर' 1x in the rows, 0x on the page
- ch 14 p9: 'स्रोत' 1x in the rows, 0x on the page
- ch 14 p11: 'ट्रांज़िस्टर' 1x in the rows, 0x on the page
- ch 14 p11: 'चार्ज' 5x in the rows, 1x on the page
- ch 14 p12: 'साम्यवस्था' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 10 p12: '×' 1x on the page, 0x in the rows
- ch 11 p8: '•' 1x on the page, 0x in the rows
- ch 11 p8: 'hnu' 3x in the rows, 0x on the page
- ch 11 p12: 'hnu' 1x in the rows, 0x on the page
- ch 12 p11: 'hv' 1x in the rows, 0x on the page
- ch 13 p13: '×' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 10 p17: 4 paragraph(s) — sent: the heading could not be placed on it
- ch 11 p13: 6 paragraph(s) — sent: the heading could not be placed on it
- ch 12 p14: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 13 p13: 4 paragraph(s) — sent: the heading could not be placed on it
- ch 14 p18: 0 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 75 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 75 page(s) called this run
- ch 10 p14 §10.6.2 #5: a degree sign not after a number — the layer's Greek letter copied through?: "… सूर्य (1/2)° का कोण बनाता है।…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 10 page 7 — confidence 0.75, 2 paragraphs
- ch 10 page 16 — confidence 0.75, 3 paragraphs
- ch 13 page 10 — confidence 0.75, 13 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 75 | 273942 | 140676 | 833536 | 11264 | ₹372.31 |
jsonl: extract/phy12-part2/hi.jsonl (122 pages, 34 of them from earlier runs)

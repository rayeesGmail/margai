# margai-pipeline ncert extract

- run: 2026-10-12 23:52 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 8 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy12-part1/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-bd3b5c75-54a6-44f1-ae54-26235c24439e

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.016 |
| 2 | withheld: illegible | 0.016 |
| 3 | withheld: illegible | 0.018 |
| 4 | withheld: illegible | 0.020 |
| 5 | withheld: illegible | 0.013 |
| 6 | withheld: illegible | 0.010 |
| 7 | withheld: illegible | 0.014 |
| 8 | withheld: illegible | 0.010 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 37 | सारांश | sent: the heading could not be placed on it | 6 |
| 2 | page 32 | सारांश | sent: the heading could not be placed on it | 3 |
| 3 | page 22 | सारांश | sent: the heading could not be placed on it | 4 |
| 4 | page 27 | सारांश | sent: the heading could not be placed on it | 3 |
| 5 | page 15 | सारांश | sent: the heading could not be placed on it | 2 |
| 6 | page 19 | सारांश | sent: the heading could not be placed on it | 3 |
| 7 | page 20 | सारांश | sent: the heading could not be placed on it | 4 |
| 8 | page 12 | सारांश | sent: the heading could not be placed on it | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 43 | 0 | 223 |
| 2 | 35 | 32 | 188 |
| 3 | 26 | 22 | 125 |
| 4 | 30 | 27 | 151 |
| 5 | 17 | 15 | 93 |
| 6 | 22 | 19 | 118 |
| 7 | 24 | 20 | 123 |
| 8 | 14 | 12 | 52 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 211 | 147 | 37 | 27 | 1073 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 147 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 147 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 147 of the 147 page(s) called this run

words the rows carry more often than the page — a misreading or an addition:
- ch 2 p6: 'भाँति' 1x in the rows, 0x on the page
- ch 2 p7: 'को' 4x in the rows, 3x on the page
- ch 2 p14: 'स्रोत' 6x in the rows, 0x on the page
- ch 2 p14: 'स्रोतों' 4x in the rows, 0x on the page
- ch 2 p21: 'निरसित' 1x in the rows, 0x on the page
- ch 2 p28: 'स्रोत' 1x in the rows, 0x on the page
- ch 3 p5: 'परिमाण' 2x in the rows, 1x on the page
- ch 3 p10: 'वहन' 1x in the rows, 0x on the page
- ch 3 p13: 'स्रोत' 2x in the rows, 1x on the page
- ch 3 p14: 'व्यंजक' 1x in the rows, 0x on the page
- ch 3 p17: 'स्रोत' 2x in the rows, 1x on the page
- ch 4 p1: 'सरेखित' 2x in the rows, 0x on the page
- ch 4 p2: 'प्रवर्धित' 1x in the rows, 0x on the page
- ch 4 p12: 'परिविद्ध' 1x in the rows, 0x on the page
- ch 4 p20: 'सरेखित' 1x in the rows, 0x on the page
- ch 4 p20: 'सरेख' 2x in the rows, 0x on the page
- ch 4 p21: 'सरेखित' 2x in the rows, 0x on the page
- ch 5 p1: 'स्रोतों' 1x in the rows, 0x on the page
- ch 5 p5: 'आघूर्णो' 1x in the rows, 0x on the page
- ch 5 p7: 'सभी' 5x in the rows, 3x on the page
- ch 5 p10: 'आघूर्णो' 1x in the rows, 0x on the page
- ch 5 p10: 'पदार्थो' 1x in the rows, 0x on the page
- ch 5 p11: 'नियतांक' 1x in the rows, 0x on the page
- ch 5 p13: 'परिक्रमण' 1x in the rows, 0x on the page
- ch 5 p14: 'सरेखित' 2x in the rows, 0x on the page
- ch 6 p2: 'दिशाएँ' 1x in the rows, 0x on the page
- ch 6 p2: 'निर्दशित' 1x in the rows, 0x on the page
- ch 6 p8: 'प्रेरित' 6x in the rows, 5x on the page
- ch 6 p10: 'परिवर्तनशील' 2x in the rows, 1x on the page
- ch 6 p10: 'सेकंड' 1x in the rows, 0x on the page
- ch 6 p16: 'रूपांतरित' 1x in the rows, 0x on the page
- ch 7 p8: 'हैं' 6x in the rows, 5x on the page
- ch 7 p20: 'ट्राँसफॉर्मरों' 1x in the rows, 0x on the page
- ch 8 p1: 'चुंबकत्व' 1x in the rows, 0x on the page
- ch 8 p4: 'हैं' 10x in the rows, 9x on the page
- ch 8 p8: 'पराबैंगनी' 2x in the rows, 1x on the page
- ch 8 p9: 'लघुतरंग' 1x in the rows, 0x on the page
- ch 8 p10: 'तरंगदैर्ध्य' 3x in the rows, 1x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 2 p2: 'dr' 2x in the rows, 0x on the page
- ch 2 p5: '0.09' 1x in the rows, 0x on the page
- ch 2 p6: '≅' 6x in the rows, 0x on the page
- ch 2 p15: 'qDelta' 1x in the rows, 0x on the page
- ch 2 p16: 'NC' 1x in the rows, 0x on the page
- ch 2 p16: 'sin' 1x in the rows, 0x on the page
- ch 2 p17: '•' 1x in the rows, 0x on the page
- ch 2 p22: '0' 2x in the rows, 0x on the page
- ch 2 p23: 'illegible' 1x in the rows, 0x on the page
- ch 2 p28: 'nV' 1x in the rows, 0x on the page
- ch 3 p11: '85.8' 2x in the rows, 0x on the page
- ch 3 p12: '0.795' 1x in the rows, 0x on the page
- ch 3 p12: '0.23' 1x in the rows, 0x on the page
- ch 3 p22: '∝' 1x on the page, 0x in the rows
- ch 3 p22: '6.0' 1x in the rows, 0x on the page
- ch 3 p22: '63.5' 1x in the rows, 0x on the page
- ch 3 p22: '9.0' 1x in the rows, 0x on the page
- ch 4 p8: '×' 4x on the page, 3x in the rows
- ch 4 p20: '→' 1x in the rows, 0x on the page
- ch 4 p24: 'kphi' 2x in the rows, 0x on the page
- ch 4 p27: '×' 4x on the page, 0x in the rows
- ch 5 p6: '×' 2x on the page, 0x in the rows
- ch 5 p7: '0' 2x in the rows, 0x on the page
- ch 5 p10: '•' 2x in the rows, 0x on the page
- ch 5 p10: 'ds' 2x in the rows, 0x on the page
- ch 5 p12: '≤' 2x on the page, 0x in the rows
- ch 5 p15: '×' 1x on the page, 0x in the rows
- ch 6 p5: 'dPhi' 2x in the rows, 0x on the page
- ch 6 p7: 'illegible' 2x in the rows, 0x on the page
- ch 6 p10: 'dPhi' 1x in the rows, 0x on the page
- ch 6 p11: 'dtheta' 2x in the rows, 0x on the page
- ch 6 p12: 'dPhi' 1x in the rows, 0x on the page
- ch 6 p12: 'NPhi' 2x in the rows, 0x on the page
- ch 6 p16: '0' 1x in the rows, 0x on the page
- ch 6 p18: 'dPhi' 1x in the rows, 0x on the page
- ch 7 p7: '3.14' 1x in the rows, 0x on the page
- ch 7 p18: 'dphi' 2x in the rows, 0x on the page
- ch 7 p19: 'dphi' 2x in the rows, 0x on the page
- ch 8 p3: 'dPhi' 2x in the rows, 0x on the page
- ch 8 p4: 'dPhi' 3x in the rows, 0x on the page
- ch 8 p4: 'dl' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 2 p32: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 3 p22: 2 paragraph(s) — sent: the heading could not be placed on it
- ch 4 p27: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 5 p15: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 6 p19: 6 paragraph(s) — sent: the heading could not be placed on it
- ch 7 p20: 6 paragraph(s) — sent: the heading could not be placed on it
- ch 8 p12: 0 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 147 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 147 page(s) called this run
- ch 3 p11 §3.8 #3: a degree sign not after a number — the layer's Greek letter copied through?: "…0 × 10^(-4) °C^(-1) है।…"
- ch 3 p11 §3.8 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…0 × 10^(-4) °C^(-1) के साथ करने पर हम…"
- ch 3 p11 §3.8 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…820 + 27.0) °C = 847 °C इस प्रकार, ता…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 4 page 11 — confidence 0.72, 6 paragraphs
- ch 4 page 23 — confidence 0.75, 3 paragraphs
- ch 5 page 7 — confidence 0.75, 4 paragraphs
- ch 5 page 12 — confidence 0.75, 8 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 147 | 536878 | 250261 | 1644544 | 11264 | ₹679.12 |
jsonl: extract/phy12-part1/hi.jsonl (211 pages, 43 of them from earlier runs)

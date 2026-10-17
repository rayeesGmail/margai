# margai-pipeline ncert extract

- run: 2026-10-17 08:21 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/bio11/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-5980f34a-58b0-4b04-8ce8-f4bcf30bbb04

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.009 |
| 2 | withheld: illegible | 0.004 |
| 3 | withheld: illegible | 0.007 |
| 4 | withheld: illegible | 0.011 |
| 5 | withheld: illegible | 0.008 |
| 6 | withheld: illegible | 0.007 |
| 7 | withheld: illegible | 0.011 |
| 8 | withheld: illegible | 0.010 |
| 9 | withheld: illegible | 0.005 |
| 10 | withheld: illegible | 0.016 |
| 11 | withheld: illegible | 0.005 |
| 12 | withheld: illegible | 0.006 |
| 13 | withheld: illegible | 0.010 |
| 14 | withheld: illegible | 0.009 |
| 15 | withheld: illegible | 0.016 |
| 16 | withheld: illegible | 0.014 |
| 17 | withheld: illegible | 0.024 |
| 18 | withheld: illegible | 0.016 |
| 19 | withheld: illegible | 0.012 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 9 | सारांश | sent: the heading could not be placed on it | 0 |
| 2 | page 12 | सारांश | sent: the heading could not be placed on it | 1 |
| 3 | page 12 | सारांश | sent: the heading could not be placed on it | 2 |
| 4 | page 16 | सारांश | sent: the heading could not be placed on it | 1 |
| 5 | page 15 | सारांश | sent: the heading could not be placed on it | 1 |
| 6 | page 7 | सारांश | sent: the heading could not be placed on it | 1 |
| 7 | page 7 | सारांश | sent: the heading could not be placed on it | 0 |
| 8 | page 18 | सारांश | sent: the heading could not be placed on it | 1 |
| 9 | page 15 | सारांश | sent: the heading could not be placed on it | 1 |
| 10 | page 10 | सारांश | sent: the heading could not be placed on it | 1 |
| 11 | page 21 | सारांश | sent: the heading could not be placed on it | 1 |
| 12 | page 12 | सारांश | sent: the heading could not be placed on it | 1 |
| 13 | page 13 | सारांश | sent: the heading could not be placed on it | 2 |
| 14 | page 11 | सारांश | sent: the heading could not be placed on it | 1 |
| 15 | page 11 | सारांश | sent: the heading could not be placed on it | 1 |
| 16 | page 10 | सारांश | sent: the heading could not be placed on it | 2 |
| 17 | page 11 | सारांश | sent: the heading could not be placed on it | 2 |
| 18 | page 7 | सारांश | sent: the heading could not be placed on it | 2 |
| 19 | page 11 | सारांश | sent: the heading could not be placed on it | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 9 | 0 | 30 |
| 2 | 13 | 0 | 50 |
| 3 | 14 | 0 | 37 |
| 4 | 17 | 0 | 54 |
| 5 | 16 | 0 | 61 |
| 6 | 8 | 0 | 22 |
| 7 | 7 | 7 | 19 |
| 8 | 19 | 18 | 64 |
| 9 | 16 | 15 | 60 |
| 10 | 11 | 10 | 49 |
| 11 | 22 | 21 | 84 |
| 12 | 13 | 12 | 43 |
| 13 | 15 | 13 | 45 |
| 14 | 12 | 11 | 34 |
| 15 | 12 | 11 | 40 |
| 16 | 12 | 10 | 43 |
| 17 | 13 | 11 | 39 |
| 18 | 9 | 7 | 22 |
| 19 | 14 | 11 | 39 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 252 | 157 | 71 | 24 | 835 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 157 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 157 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 157 of the 157 page(s) called this run

words the rows carry more often than the page — a misreading or an addition:
- ch 7 p4: 'फेफड़े' 2x in the rows, 1x on the page
- ch 7 p5: 'पैराथाईराइड' 1x in the rows, 0x on the page
- ch 7 p6: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 8 p7: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 8 p9: 'इलेक्ट्रॉन' 1x in the rows, 0x on the page
- ch 8 p9: 'पार्श्विक' 1x in the rows, 0x on the page
- ch 8 p16: 'क्रोमेटिन' 1x in the rows, 0x on the page
- ch 9 p3: 'उदाहरणार्थ' 1x in the rows, 0x on the page
- ch 9 p3: 'न्यूक्लिओसाइड' 1x in the rows, 0x on the page
- ch 9 p10: 'स्रोतों' 1x in the rows, 0x on the page
- ch 9 p10: 'परिवर्तन' 4x in the rows, 3x on the page
- ch 9 p10: 'कार्बोनिक' 2x in the rows, 1x on the page
- ch 9 p10: 'एनहाइड्रेज' 1x in the rows, 0x on the page
- ch 9 p13: 'हैं' 7x in the rows, 6x on the page
- ch 9 p15: 'श्रृंखलाओं' 1x in the rows, 0x on the page
- ch 9 p15: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 10 p4: 'संट्रोमियर' 1x in the rows, 0x on the page
- ch 10 p5: 'ध्रुवों' 7x in the rows, 6x on the page
- ch 10 p6: 'प्राणी' 1x in the rows, 0x on the page
- ch 10 p7: 'इलेक्ट्रॉन' 1x in the rows, 0x on the page
- ch 11 p3: 'पदार्थो' 1x in the rows, 0x on the page
- ch 11 p3: 'स्रोत' 1x in the rows, 0x on the page
- ch 11 p8: 'तरंगदैर्ध्यों' 3x in the rows, 1x on the page
- ch 11 p10: 'पदार्थो' 1x in the rows, 0x on the page
- ch 11 p10: 'भाँति' 1x in the rows, 0x on the page
- ch 11 p15: 'ऑक्जेलोएसिटिक' 1x in the rows, 0x on the page
- ch 11 p19: 'साद्रंता' 1x in the rows, 0x on the page
- ch 12 p1: 'और' 2x in the rows, 1x on the page
- ch 12 p3: 'परिस्थितियों' 1x in the rows, 0x on the page
- ch 12 p4: 'ग्लाइकोलिसस' 1x in the rows, 0x on the page
- ch 12 p4: 'फॉस्फोग्लिसरिल्डहाइड' 1x in the rows, 0x on the page
- ch 12 p5: 'डिकार्बोक्सिलेज' 1x in the rows, 0x on the page
- ch 12 p6: 'कार्बोक्सीलिकरण' 1x in the rows, 0x on the page
- ch 12 p7: 'डिकार्बोक्सिलीकरण' 1x in the rows, 0x on the page
- ch 12 p7: 'माइट्रोकोंड्रियल' 1x in the rows, 0x on the page
- ch 13 p3: 'प्रोह' 2x in the rows, 0x on the page
- ch 13 p5: 'संवर्धन' 1x in the rows, 0x on the page
- ch 13 p6: 'स्रोत्र' 1x in the rows, 0x on the page
- ch 13 p9: 'अंतर्गत' 1x in the rows, 0x on the page
- ch 13 p9: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 13 p10: 'कज़िन्स' 1x in the rows, 0x on the page
- ch 13 p12: 'प्रोह' 4x in the rows, 0x on the page
- ch 13 p13: 'परिस्थितियों' 1x in the rows, 0x on the page
- ch 14 p11: 'परिवर्तन' 1x in the rows, 0x on the page
- ch 14 p11: 'घरघराहट' 1x in the rows, 0x on the page
- ch 15 p1: 'फाइब्रिनोजन' 2x in the rows, 1x on the page
- ch 15 p7: 'शिराअलिंदपर्व' 3x in the rows, 2x on the page
- ch 15 p9: 'संबद्धता' 1x in the rows, 0x on the page
- ch 16 p1: 'पदार्थों' 2x in the rows, 1x on the page
- ch 16 p4: 'स्रवण' 1x in the rows, 0x on the page
- ch 16 p4: 'पर्तो' 1x in the rows, 0x on the page
- ch 16 p5: 'स्रवण' 3x in the rows, 0x on the page
- ch 16 p5: 'स्रवित' 1x in the rows, 0x on the page
- ch 16 p6: 'स्रवण' 2x in the rows, 1x on the page
- ch 16 p8: 'स्राव' 1x in the rows, 0x on the page
- ch 16 p8: 'वृद्धि' 2x in the rows, 1x on the page
- ch 16 p8: 'स्रवण' 2x in the rows, 0x on the page
- ch 16 p8: 'स्रवित' 1x in the rows, 0x on the page
- ch 16 p9: 'स्राव' 3x in the rows, 0x on the page
- ch 17 p2: 'पदार्थो' 1x in the rows, 0x on the page
- ch 17 p11: 'पदार्थो' 1x in the rows, 0x on the page
- ch 18 p1: 'स्रावी' 2x in the rows, 1x on the page
- ch 18 p3: 'सान्द्रता' 1x in the rows, 0x on the page
- ch 18 p5: 'स्रावी' 1x in the rows, 0x on the page
- ch 18 p6: 'गोलार्द्धों' 1x in the rows, 0x on the page
- ch 18 p7: 'स्रावी' 1x in the rows, 0x on the page
- ch 18 p7: 'गोलार्द्ध' 1x in the rows, 0x on the page
- ch 18 p7: 'स्राव' 1x in the rows, 0x on the page
- ch 19 p1: 'स्रावी' 6x in the rows, 0x on the page
- ch 19 p1: 'स्राव' 1x in the rows, 0x on the page
- ch 19 p1: 'स्रवित' 1x in the rows, 0x on the page
- ch 19 p1: 'स्रावित' 1x in the rows, 0x on the page
- ch 19 p2: 'ग्रंथियां' 2x in the rows, 1x on the page
- ch 19 p2: 'स्रवित' 3x in the rows, 2x on the page
- ch 19 p2: 'तंत्रिकास्रावी' 1x in the rows, 0x on the page
- ch 19 p2: 'स्राव' 5x in the rows, 2x on the page
- ch 19 p2: 'स्रावित' 1x in the rows, 0x on the page
- ch 19 p3: 'स्राव' 6x in the rows, 0x on the page
- ch 19 p4: 'स्राव' 2x in the rows, 0x on the page
- ch 19 p4: 'स्रावित' 1x in the rows, 0x on the page
- ch 19 p5: 'स्राव' 3x in the rows, 0x on the page
- ch 19 p6: 'स्राव' 2x in the rows, 1x on the page
- ch 19 p7: 'स्राव' 2x in the rows, 0x on the page
- ch 19 p7: 'स्रावी' 3x in the rows, 0x on the page
- ch 19 p8: 'स्राव' 2x in the rows, 0x on the page
- ch 19 p8: 'पदार्थो' 1x in the rows, 0x on the page
- ch 19 p8: 'स्रावी' 1x in the rows, 0x on the page
- ch 19 p9: 'स्राव' 12x in the rows, 0x on the page
- ch 19 p9: 'स्रावी' 5x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 7 p2: 'morphology' 1x in the rows, 0x on the page
- ch 8 p4: '•' 2x on the page, 0x in the rows
- ch 9 p3: '→' 2x in the rows, 0x on the page
- ch 9 p3: 'COO' 2x in the rows, 0x on the page
- ch 10 p3: '•' 4x on the page, 0x in the rows
- ch 10 p3: '0' 1x in the rows, 0x on the page
- ch 10 p4: '•' 4x on the page, 0x in the rows
- ch 10 p5: '•' 5x on the page, 0x in the rows
- ch 10 p6: '•' 3x on the page, 0x in the rows
- ch 11 p5: '→' 2x in the rows, 0x on the page
- ch 11 p6: '→' 1x in the rows, 0x on the page
- ch 12 p4: '×' 5x on the page, 0x in the rows
- ch 12 p6: '•' 2x on the page, 0x in the rows
- ch 12 p9: '•' 5x on the page, 0x in the rows
- ch 12 p10: '•' 2x on the page, 0x in the rows
- ch 12 p11: '→' 1x in the rows, 0x on the page
- ch 12 p12: '→' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 8 p1: the rows carry 0% of the page's Devanagari words
- ch 8 p2: the rows carry 0% of the page's Devanagari words
- ch 8 p8: the rows carry 0% of the page's Devanagari words
- ch 9 p4: the rows carry 0% of the page's Devanagari words
- ch 11 p1: the rows carry 0% of the page's Devanagari words
- ch 11 p2: the rows carry 0% of the page's Devanagari words
- ch 11 p14: the rows carry 54% of the page's Devanagari words
- ch 11 p18: the rows carry 0% of the page's Devanagari words
- ch 14 p1: the rows carry 0% of the page's Devanagari words
- ch 14 p2: the rows carry 0% of the page's Devanagari words
- ch 14 p8: the rows carry 54% of the page's Devanagari words

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 7 p7: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 8 p18: 1 paragraph(s) — sent: the heading could not be placed on it
- ch 9 p15: 7 paragraph(s) — sent: the heading could not be placed on it
- ch 10 p10: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 11 p21: 2 paragraph(s) — sent: the heading could not be placed on it
- ch 12 p12: 3 paragraph(s) — sent: the heading could not be placed on it
- ch 13 p13: 4 paragraph(s) — sent: the heading could not be placed on it
- ch 14 p11: 5 paragraph(s) — sent: the heading could not be placed on it
- ch 15 p11: 3 paragraph(s) — sent: the heading could not be placed on it
- ch 16 p10: 4 paragraph(s) — sent: the heading could not be placed on it
- ch 17 p11: 7 paragraph(s) — sent: the heading could not be placed on it
- ch 18 p7: 4 paragraph(s) — sent: the heading could not be placed on it
- ch 19 p11: 0 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 157 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 157 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 8 page 4 — confidence 0.75, 4 paragraphs
- ch 8 page 10 — confidence 0.78, 4 paragraphs
- ch 9 page 14 — confidence 0.75, 7 paragraphs
- ch 11 page 11 — confidence 0.75, 3 paragraphs
- ch 12 page 4 — confidence 0.75, 4 paragraphs
- ch 14 page 8 — confidence 0.75, 1 paragraphs
- ch 19 page 3 — confidence 0.7, 3 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 157 | 585074 | 227407 | 1757184 | 11264 | ₹657.45 |
jsonl: extract/bio11/hi.jsonl (252 pages, 76 of them from earlier runs)

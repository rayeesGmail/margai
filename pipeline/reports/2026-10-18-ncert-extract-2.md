# margai-pipeline ncert extract

- run: 2026-10-18 13:41 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 13 chapters of bio12 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/bio12/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-5020990b-f493-4d31-9920-c486d38dca19

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.004 |
| 2 | withheld: illegible | 0.008 |
| 3 | withheld: illegible | 0.004 |
| 4 | withheld: illegible | 0.009 |
| 5 | withheld: illegible | 0.005 |
| 6 | withheld: illegible | 0.007 |
| 7 | withheld: illegible | 0.006 |
| 8 | withheld: illegible | 0.010 |
| 9 | withheld: illegible | 0.005 |
| 10 | withheld: illegible | 0.002 |
| 11 | withheld: illegible | 0.005 |
| 12 | withheld: illegible | 0.003 |
| 13 | withheld: illegible | 0.003 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 25 | सारांश | sent: the heading could not be placed on it | 2 |
| 2 | page 16 | सारांश | sent: the heading could not be placed on it | 2 |
| 3 | page 10 | सारांश | sent: the heading could not be placed on it | 2 |
| 4 | page 28 | सारांश | sent: the heading could not be placed on it | 2 |
| 5 | page 31 | सारांश | sent: the heading could not be placed on it | 2 |
| 6 | page 18 | अभ्यास | sent: the heading could not be placed on it | 0 |
| 7 | page 24 | सारांश | sent: the heading could not be placed on it | 1 |
| 8 | page 11 | सारांश | sent: the heading could not be placed on it | 2 |
| 9 | page 16 | अभ्यास | sent: the heading could not be placed on it | 1 |
| 10 | page 10 | सारांश | sent: the heading could not be placed on it | 2 |
| 11 | page 17 | सारांश | sent: the heading could not be placed on it | 1 |
| 12 | page 11 | सारांश | sent: the heading could not be placed on it | 1 |
| 13 | page 11 | सारांश | sent: the heading could not be placed on it | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 27 | 0 | 81 |
| 2 | 18 | 16 | 41 |
| 3 | 12 | 10 | 32 |
| 4 | 30 | 28 | 91 |
| 5 | 33 | 31 | 153 |
| 6 | 18 | 18 | 41 |
| 7 | 25 | 24 | 87 |
| 8 | 13 | 11 | 37 |
| 9 | 17 | 16 | 50 |
| 10 | 12 | 10 | 46 |
| 11 | 18 | 17 | 48 |
| 12 | 12 | 11 | 36 |
| 13 | 13 | 11 | 39 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 248 | 203 | 25 | 20 | 782 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 203 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 203 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 203 of the 203 page(s) called this run

words the rows carry more often than the page — a misreading or an addition:
- ch 2 p8: 'एंड्रोजेंस' 1x in the rows, 0x on the page
- ch 2 p10: 'मेन्सट्रुअल' 2x in the rows, 1x on the page
- ch 2 p12: 'अर्धसूत्री' 2x in the rows, 1x on the page
- ch 2 p12: 'निर्धारण' 2x in the rows, 1x on the page
- ch 2 p14: 'पदार्थो' 2x in the rows, 0x on the page
- ch 2 p15: 'सगर्भता' 6x in the rows, 5x on the page
- ch 3 p8: 'हर्पीज़ा' 1x in the rows, 0x on the page
- ch 3 p9: 'ट्रांसफर' 2x in the rows, 1x on the page
- ch 4 p4: 'मध्य' 1x in the rows, 0x on the page
- ch 4 p4: 'सांख्यिकीय' 1x in the rows, 0x on the page
- ch 4 p6: 'माध्यम' 1x in the rows, 0x on the page
- ch 4 p6: 'अध्ययन' 1x in the rows, 0x on the page
- ch 4 p7: 'माध्यम' 2x in the rows, 0x on the page
- ch 4 p9: 'परिभाषा' 1x in the rows, 0x on the page
- ch 4 p14: 'जोडी़' 1x in the rows, 0x on the page
- ch 4 p23: 'संबंध' 1x in the rows, 0x on the page
- ch 4 p25: 'ग्लोबिनशृंखला' 1x in the rows, 0x on the page
- ch 5 p2: 'श्रृंखला' 2x in the rows, 0x on the page
- ch 5 p2: 'हाइड्रॉक्सिल' 2x in the rows, 1x on the page
- ch 5 p2: 'पॉलीन्यूक्लियोटाइडश्रृंखला' 1x in the rows, 0x on the page
- ch 5 p3: 'फ्रॉन्सिस' 1x in the rows, 0x on the page
- ch 5 p4: 'श्रृंखलाएँ' 1x in the rows, 0x on the page
- ch 5 p4: 'करोड़वाँ' 1x in the rows, 0x on the page
- ch 5 p4: 'पीरीमीडीन' 1x in the rows, 0x on the page
- ch 5 p4: 'श्रृंखलाओं' 1x in the rows, 0x on the page
- ch 5 p11: 'ग्रेडिएंट' 1x in the rows, 0x on the page
- ch 5 p19: 'न्युक्लियोटाइड' 1x in the rows, 0x on the page
- ch 5 p22: 'एककों' 1x in the rows, 0x on the page
- ch 5 p23: 'स्रोत' 2x in the rows, 0x on the page
- ch 5 p24: 'स्रोत' 1x in the rows, 0x on the page
- ch 5 p29: 'आनुवंशिक' 2x in the rows, 1x on the page
- ch 5 p31: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 6 p2: 'सिद्धात' 1x in the rows, 0x on the page
- ch 6 p6: 'चयनित' 1x in the rows, 0x on the page
- ch 6 p9: 'वर्षों' 1x in the rows, 0x on the page
- ch 6 p11: 'प्राइमरोज' 1x in the rows, 0x on the page
- ch 6 p13: 'हुई' 1x in the rows, 0x on the page
- ch 6 p16: 'घोड़े' 2x in the rows, 1x on the page
- ch 6 p17: 'सैपियंस' 1x in the rows, 0x on the page
- ch 7 p5: 'वर्षो' 1x in the rows, 0x on the page
- ch 7 p7: 'आरंभ' 1x in the rows, 0x on the page
- ch 7 p7: 'पदार्थो' 1x in the rows, 0x on the page
- ch 7 p7: 'वु' 2x in the rows, 1x on the page
- ch 7 p8: 'से' 13x in the rows, 12x on the page
- ch 7 p11: 'प्रचुरता' 1x in the rows, 0x on the page
- ch 7 p16: 'ओर' 1x in the rows, 0x on the page
- ch 7 p17: 'रेजोनेंस' 1x in the rows, 0x on the page
- ch 7 p19: 'एंफेटमीन' 1x in the rows, 0x on the page
- ch 8 p3: 'बेकर' 1x in the rows, 0x on the page
- ch 8 p3: 'स्राव' 1x in the rows, 0x on the page
- ch 8 p7: 'स्राव' 2x in the rows, 1x on the page
- ch 8 p11: 'राइज़ोबियम' 1x in the rows, 0x on the page
- ch 9 p7: 'वर्णो' 1x in the rows, 0x on the page
- ch 9 p8: 'एंडोन्यूक्लिएज़' 1x in the rows, 0x on the page
- ch 9 p11: 'स्थानांतरित' 3x in the rows, 2x on the page
- ch 9 p12: 'स्रोत' 2x in the rows, 1x on the page
- ch 9 p14: 'संवर्धन' 4x in the rows, 3x on the page
- ch 9 p14: 'एक्स्पोनेन्शियल' 1x in the rows, 0x on the page
- ch 10 p1: 'पदार्थो' 1x in the rows, 0x on the page
- ch 10 p3: 'वांछित' 1x in the rows, 0x on the page
- ch 10 p5: 'प्रौद्योगिकी' 1x in the rows, 0x on the page
- ch 10 p6: 'अग्नाशय' 1x in the rows, 0x on the page
- ch 10 p6: 'श्रृंखलाओं' 3x in the rows, 0x on the page
- ch 10 p6: 'श्रृंखला' 3x in the rows, 0x on the page
- ch 10 p7: 'श्रृंखला' 2x in the rows, 0x on the page
- ch 11 p4: 'समष्टि' 17x in the rows, 15x on the page
- ch 11 p4: 'ह्रास' 1x in the rows, 0x on the page
- ch 11 p6: 'रिजर्व्स' 1x in the rows, 0x on the page
- ch 11 p6: 'आरंभिक' 1x in the rows, 0x on the page
- ch 12 p1: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 12 p1: 'श्रृंखलाओं' 1x in the rows, 0x on the page
- ch 12 p6: 'श्रृंखला' 11x in the rows, 1x on the page
- ch 12 p6: 'शुरुआत' 1x in the rows, 0x on the page
- ch 12 p6: 'श्रृंखलाओं' 1x in the rows, 0x on the page
- ch 12 p7: 'श्रृंखला' 3x in the rows, 0x on the page
- ch 12 p9: 'खाद्यशृंखला' 1x in the rows, 0x on the page
- ch 12 p10: 'श्रृंखला' 1x in the rows, 0x on the page
- ch 13 p1: 'जातियाँ' 5x in the rows, 3x on the page
- ch 13 p2: 'जातियाँ' 3x in the rows, 0x on the page
- ch 13 p2: 'मैंग्रोव' 1x in the rows, 0x on the page
- ch 13 p2: 'प्रवाल्व' 1x in the rows, 0x on the page
- ch 13 p4: 'पहचानने' 1x in the rows, 0x on the page
- ch 13 p4: 'जातियाँ' 10x in the rows, 2x on the page
- ch 13 p5: 'विकासविदों' 1x in the rows, 0x on the page
- ch 13 p6: 'जातियाँ' 5x in the rows, 1x on the page
- ch 13 p7: 'जातियाँ' 8x in the rows, 1x on the page
- ch 13 p7: 'उपजातियाँ' 1x in the rows, 0x on the page
- ch 13 p8: 'जातियाँ' 6x in the rows, 1x on the page
- ch 13 p9: 'जातियाँ' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 5 p5: '→' 2x in the rows, 0x on the page
- ch 5 p7: '→' 8x in the rows, 0x on the page
- ch 5 p13: '→' 3x in the rows, 0x on the page
- ch 5 p14: '→' 3x in the rows, 0x on the page
- ch 9 p7: '→' 2x in the rows, 0x on the page
- ch 12 p6: '→' 2x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 2 p4: the rows carry 49% of the page's Devanagari words
- ch 4 p1: the rows carry 0% of the page's Devanagari words
- ch 4 p2: the rows carry 0% of the page's Devanagari words
- ch 4 p13: the rows carry 0% of the page's Devanagari words
- ch 4 p16: the rows carry 48% of the page's Devanagari words
- ch 4 p18: the rows carry 21% of the page's Devanagari words
- ch 5 p30: the rows carry 45% of the page's Devanagari words
- ch 7 p1: the rows carry 0% of the page's Devanagari words
- ch 7 p2: the rows carry 0% of the page's Devanagari words
- ch 7 p6: the rows carry 25% of the page's Devanagari words
- ch 7 p14: the rows carry 59% of the page's Devanagari words
- ch 8 p2: the rows carry 0% of the page's Devanagari words
- ch 9 p1: the rows carry 0% of the page's Devanagari words
- ch 9 p2: the rows carry 0% of the page's Devanagari words
- ch 11 p1: the rows carry 0% of the page's Devanagari words
- ch 11 p2: the rows carry 0% of the page's Devanagari words
- ch 12 p9: the rows carry 43% of the page's Devanagari words

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 2 p16: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 3 p10: 2 paragraph(s) — sent: the heading could not be placed on it
- ch 4 p28: 3 paragraph(s) — sent: the heading could not be placed on it
- ch 5 p31: 1 paragraph(s) — sent: the heading could not be placed on it
- ch 6 p18: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 7 p24: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 8 p11: 3 paragraph(s) — sent: the heading could not be placed on it
- ch 9 p16: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 10 p10: 5 paragraph(s) — sent: the heading could not be placed on it
- ch 11 p17: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 12 p11: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 13 p11: 2 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 203 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 203 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 2 page 3 — confidence 0.75, 3 paragraphs
- ch 4 page 5 — confidence 0.72, 3 paragraphs
- ch 4 page 27 — confidence 0.75, 3 paragraphs
- ch 4 page 28 — confidence 0.7, 3 paragraphs
- ch 5 page 15 — confidence 0.75, 5 paragraphs
- ch 5 page 30 — confidence 0.7, 1 paragraphs
- ch 7 page 6 — confidence 0.75, 1 paragraphs
- ch 7 page 19 — confidence 0.75, 3 paragraphs
- ch 11 page 5 — confidence 0.6, 2 paragraphs
- ch 12 page 4 — confidence 0.75, 2 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 203 | 757783 | 299561 | 2275328 | 11264 | ₹859.00 |
jsonl: extract/bio12/hi.jsonl (248 pages, 27 of them from earlier runs)

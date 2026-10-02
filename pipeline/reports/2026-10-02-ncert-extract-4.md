# margai-pipeline ncert extract

- run: 2026-10-02 11:53 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy11-part1/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-e0ae98cb-385b-41e4-b457-041dcee36f43

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.023 |
| 2 | withheld: illegible | 0.057 |
| 3 | withheld: illegible | 0.087 |
| 4 | withheld: illegible | 0.022 |
| 5 | withheld: illegible | 0.024 |
| 6 | withheld: illegible | 0.017 |
| 7 | withheld: illegible | 0.011 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 10 | सारांश | sent: the heading could not be placed on it | 2 |
| 2 | page 10 | सारांश | sent: the heading could not be placed on it | 5 |
| 3 | page 17 | सारांश | sent: the heading could not be placed on it | 5 |
| 4 | page 18 | सारांश | sent: the heading could not be placed on it | 4 |
| 5 | page 16 | सारांश | sent: the heading could not be placed on it | 6 |
| 6 | page 32 | सारांश | sent: the heading could not be placed on it | 4 |
| 7 | page 14 | सारांश | sent: the heading could not be placed on it | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 12 | 10 | 73 |
| 2 | 15 | 10 | 68 |
| 3 | 22 | 17 | 107 |
| 4 | 22 | 18 | 128 |
| 5 | 22 | 16 | 126 |
| 6 | 36 | 32 | 278 |
| 7 | 18 | 14 | 120 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 147 | 117 | 0 | 30 | 900 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 117 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 117 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 117 of the 117 page(s) called this run

words the rows carry that the page does not — a misreading or an addition:
- ch 1 p2: 'प्रकृति' 1x in the rows, 0x on the page
- ch 1 p5: 'चाल' 1x in the rows, 0x on the page
- ch 1 p6: 'अत्यधिक' 1x in the rows, 0x on the page
- ch 1 p7: 'प्रकृति' 1x in the rows, 0x on the page
- ch 2 p1: 'शिराओं' 1x in the rows, 0x on the page
- ch 2 p2: 'मानों' 1x in the rows, 0x on the page
- ch 3 p1: 'पढ़ेंगे' 1x in the rows, 0x on the page
- ch 3 p12: 'ग्रेट' 1x in the rows, 0x on the page
- ch 3 p15: 'इन्ही' 1x in the rows, 0x on the page
- ch 4 p1: 'दिक्' 1x in the rows, 0x on the page
- ch 4 p1: 'स्थान' 1x in the rows, 0x on the page
- ch 4 p3: 'अंतर्दृष्टि' 1x in the rows, 0x on the page
- ch 4 p4: 'पृथ्वी' 3x in the rows, 0x on the page
- ch 4 p4: 'वृद्धि' 2x in the rows, 0x on the page
- ch 4 p4: 'पढ़ेंगे' 1x in the rows, 0x on the page
- ch 4 p4: 'दृढ़' 1x in the rows, 0x on the page
- ch 4 p7: 'परिवर्तन' 1x in the rows, 0x on the page
- ch 4 p9: 'वाला' 1x in the rows, 0x on the page
- ch 4 p13: 'प्रकृति' 1x in the rows, 0x on the page
- ch 4 p15: 'बेयरिंगों' 1x in the rows, 0x on the page
- ch 4 p15: 'वृत्तीय' 1x in the rows, 0x on the page
- ch 4 p16: 'पट्टियों' 1x in the rows, 0x on the page
- ch 4 p17: 'पृथक' 1x in the rows, 0x on the page
- ch 4 p17: 'तृतीय' 1x in the rows, 0x on the page
- ch 5 p1: 'धावक' 1x in the rows, 0x on the page
- ch 5 p9: 'निर्दशित' 1x in the rows, 0x on the page
- ch 5 p10: 'वृत्ताकार' 1x in the rows, 0x on the page
- ch 5 p11: 'फ्रेम' 2x in the rows, 0x on the page
- ch 5 p11: 'लीजिए' 1x in the rows, 0x on the page
- ch 5 p11: 'प्रतीकात्मक' 1x in the rows, 0x on the page
- ch 5 p11: 'अनुरूपण' 1x in the rows, 0x on the page
- ch 5 p11: 'कोई' 1x in the rows, 0x on the page
- ch 5 p12: 'खिंचे' 1x in the rows, 0x on the page
- ch 5 p12: 'पृथ्वी' 1x in the rows, 0x on the page
- ch 5 p15: 'पिंडों' 2x in the rows, 0x on the page
- ch 6 p1: 'होतीं' 1x in the rows, 0x on the page
- ch 6 p1: 'विकृतियाँ' 1x in the rows, 0x on the page
- ch 6 p1: 'विकृति' 1x in the rows, 0x on the page
- ch 6 p3: 'उधर' 1x in the rows, 0x on the page
- ch 6 p4: 'दृष्टिकोण' 1x in the rows, 0x on the page
- ch 6 p5: 'अणुओं' 1x in the rows, 0x on the page
- ch 6 p6: 'दृष्टि' 1x in the rows, 0x on the page
- ch 6 p7: 'पट्टियों' 2x in the rows, 0x on the page
- ch 6 p7: 'पट्टी' 1x in the rows, 0x on the page
- ch 6 p8: 'प्रकृति' 1x in the rows, 0x on the page
- ch 6 p10: 'समागं' 1x in the rows, 0x on the page
- ch 6 p13: 'देखें' 1x in the rows, 0x on the page
- ch 6 p13: 'किंतु' 1x in the rows, 0x on the page
- ch 6 p14: 'सीखा' 1x in the rows, 0x on the page
- ch 6 p16: 'तृतीय' 1x in the rows, 0x on the page
- ch 6 p22: 'अवधारणाएँ' 1x in the rows, 0x on the page
- ch 6 p26: 'उदाहरण' 1x in the rows, 0x on the page
- ch 6 p29: 'आघूर्णो' 3x in the rows, 0x on the page
- ch 6 p29: 'बियरिंगों' 1x in the rows, 0x on the page
- ch 6 p30: 'दृढ़' 1x in the rows, 0x on the page
- ch 6 p30: 'दर्शाते' 1x in the rows, 0x on the page
- ch 6 p31: 'आघूर्णो' 1x in the rows, 0x on the page
- ch 6 p32: 'नृतक' 1x in the rows, 0x on the page
- ch 7 p4: 'प्राकृतिक' 1x in the rows, 0x on the page
- ch 7 p6: 'संकेन्द्रित' 1x in the rows, 0x on the page
- ch 7 p9: 'दृष्टि' 1x in the rows, 0x on the page
- ch 7 p9: 'निर्देशित' 1x in the rows, 0x on the page
- ch 7 p10: 'शीर्षो' 1x in the rows, 0x on the page
- ch 7 p12: 'समीकृत' 1x in the rows, 0x on the page
- ch 7 p13: 'वृत्तीय' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 1 p2: '×' 5x on the page, 0x in the rows
- ch 1 p3: '×' 4x on the page, 0x in the rows
- ch 2 p2: '→' 4x on the page, 0x in the rows
- ch 2 p2: 'dx' 2x in the rows, 0x on the page
- ch 2 p2: 'dt' 2x in the rows, 0x on the page
- ch 2 p3: '•' 1x on the page, 0x in the rows
- ch 2 p3: '5.0' 1x in the rows, 0x on the page
- ch 2 p4: '→' 1x on the page, 0x in the rows
- ch 2 p4: 'dv' 1x in the rows, 0x on the page
- ch 2 p6: '•' 2x on the page, 0x in the rows
- ch 2 p6: 'to' 8x in the rows, 0x on the page
- ch 2 p7: '•' 1x on the page, 0x in the rows
- ch 2 p8: '•' 2x on the page, 0x in the rows
- ch 2 p8: '2.2' 1x in the rows, 0x on the page
- ch 2 p9: '•' 1x on the page, 0x in the rows
- ch 2 p10: '→' 5x on the page, 0x in the rows
- ch 3 p2: '→' 2x on the page, 0x in the rows
- ch 3 p2: '35.6' 1x in the rows, 0x on the page
- ch 3 p2: '24.2' 1x in the rows, 0x on the page
- ch 3 p2: '11.4' 1x in the rows, 0x on the page
- ch 3 p3: 'lambdaA' 3x in the rows, 0x on the page
- ch 3 p3: '1.5' 1x in the rows, 0x on the page
- ch 3 p5: '•' 1x on the page, 0x in the rows
- ch 3 p5: '3.1' 1x in the rows, 0x on the page
- ch 3 p6: '3.14' 1x in the rows, 0x on the page
- ch 3 p6: '3.15' 1x in the rows, 0x on the page
- ch 3 p7: '•' 1x on the page, 0x in the rows
- ch 3 p7: '3.2' 1x in the rows, 0x on the page
- ch 3 p7: '3.10' 1x in the rows, 0x on the page
- ch 3 p8: '•' 1x on the page, 0x in the rows
- ch 3 p8: '3.3' 1x in the rows, 0x on the page
- ch 3 p9: '→' 7x on the page, 0x in the rows
- ch 3 p9: 'dr' 2x in the rows, 0x on the page
- ch 3 p10: '→' 3x on the page, 0x in the rows
- ch 3 p10: 'dx' 3x in the rows, 0x on the page
- ch 3 p10: 'dt' 12x in the rows, 0x on the page
- ch 3 p10: 'dy' 3x in the rows, 0x on the page
- ch 3 p10: '3.14' 1x in the rows, 0x on the page
- ch 3 p10: 'dv' 2x in the rows, 0x on the page
- ch 3 p10: '3.15' 2x in the rows, 0x on the page
- ch 3 p11: '→' 2x on the page, 0x in the rows
- ch 3 p11: '•' 1x on the page, 0x in the rows
- ch 3 p11: '3.15' 2x in the rows, 0x on the page
- ch 3 p11: '3.4' 1x in the rows, 0x on the page
- ch 3 p12: '•' 1x on the page, 0x in the rows
- ch 3 p12: 'dt' 2x in the rows, 0x on the page
- ch 3 p12: '3.10' 1x in the rows, 0x on the page
- ch 3 p12: '3.5' 1x in the rows, 0x on the page
- ch 3 p12: '3.16' 1x in the rows, 0x on the page
- ch 3 p13: '3.16' 1x in the rows, 0x on the page
- ch 3 p13: '3.34' 1x in the rows, 0x on the page
- ch 3 p13: '3.33' 1x in the rows, 0x on the page
- ch 3 p13: '3.17' 2x in the rows, 0x on the page
- ch 3 p14: '•' 3x on the page, 0x in the rows
- ch 3 p14: '3.37' 1x in the rows, 0x on the page
- ch 3 p14: '3.6' 1x in the rows, 0x on the page
- ch 3 p14: '3.7' 1x in the rows, 0x on the page
- ch 3 p14: '3.8' 1x in the rows, 0x on the page
- ch 3 p15: '→' 4x on the page, 0x in the rows
- ch 3 p16: '→' 4x on the page, 0x in the rows
- ch 3 p16: '•' 1x on the page, 0x in the rows
- ch 3 p16: 'lim' 4x in the rows, 0x on the page
- ch 3 p16: '3.18' 1x in the rows, 0x on the page
- ch 3 p16: '3.44' 1x in the rows, 0x on the page
- ch 3 p16: '3.45' 1x in the rows, 0x on the page
- ch 3 p16: '3.46' 1x in the rows, 0x on the page
- ch 3 p16: '3.47' 1x in the rows, 0x on the page
- ch 3 p16: '3.48' 1x in the rows, 0x on the page
- ch 3 p16: '3.9' 1x in the rows, 0x on the page
- ch 4 p4: '•' 1x on the page, 0x in the rows
- ch 4 p4: '4.9' 1x in the rows, 0x on the page
- ch 4 p4: '4.1' 1x in the rows, 0x on the page
- ch 4 p6: '→' 1x on the page, 0x in the rows
- ch 4 p6: 'dt' 7x in the rows, 0x on the page
- ch 4 p6: 'dv' 1x in the rows, 0x on the page
- ch 4 p7: '•' 1x on the page, 0x in the rows
- ch 4 p7: 'dp' 2x in the rows, 0x on the page
- ch 4 p7: '4.2' 1x in the rows, 0x on the page
- ch 4 p8: '•' 1x on the page, 0x in the rows
- ch 4 p8: '4.7' 1x in the rows, 0x on the page
- ch 4 p8: '4.4' 1x in the rows, 0x on the page
- ch 4 p8: '4.8' 1x in the rows, 0x on the page
- ch 4 p9: '•' 1x on the page, 0x in the rows
- ch 4 p9: '4.5' 1x in the rows, 0x on the page
- ch 4 p10: '4.7' 1x in the rows, 0x on the page
- ch 4 p11: '•' 1x on the page, 0x in the rows
- ch 4 p11: '4.6' 1x in the rows, 0x on the page
- ch 4 p13: '•' 2x on the page, 0x in the rows
- ch 4 p13: '4.7' 1x in the rows, 0x on the page
- ch 4 p14: '•' 1x on the page, 0x in the rows
- ch 4 p14: '4.9' 1x in the rows, 0x on the page
- ch 4 p16: '•' 2x on the page, 0x in the rows
- ch 4 p16: '4.14' 1x in the rows, 0x on the page
- ch 4 p16: '4.10' 1x in the rows, 0x on the page
- ch 4 p16: '4.11' 1x in the rows, 0x on the page
- ch 4 p16: '0.2' 1x in the rows, 0x on the page
- ch 4 p17: '•' 1x on the page, 0x in the rows
- ch 4 p17: '4.21' 1x in the rows, 0x on the page
- ch 4 p17: '4.12' 1x in the rows, 0x on the page
- ch 5 p2: '•' 1x on the page, 0x in the rows
- ch 5 p2: 'xB' 1x in the rows, 0x on the page
- ch 5 p2: 'yB' 1x in the rows, 0x on the page
- ch 5 p2: 'zB' 1x in the rows, 0x on the page
- ch 5 p2: 'xA' 1x in the rows, 0x on the page
- ch 5 p2: 'yA' 1x in the rows, 0x on the page
- ch 5 p2: 'zA' 1x in the rows, 0x on the page
- ch 5 p2: 'xd' 1x in the rows, 0x on the page
- ch 5 p2: 'yd' 1x in the rows, 0x on the page
- ch 5 p2: 'zd' 1x in the rows, 0x on the page
- ch 5 p3: '5.6' 1x in the rows, 0x on the page
- ch 5 p4: '•' 1x on the page, 0x in the rows
- ch 5 p4: '×' 4x on the page, 2x in the rows
- ch 5 p4: 'mg' 1x in the rows, 0x on the page
- ch 5 p4: '5.5' 1x in the rows, 0x on the page
- ch 5 p5: '×' 7x on the page, 2x in the rows
- ch 5 p5: '•' 1x on the page, 0x in the rows
- ch 5 p5: '→' 1x on the page, 0x in the rows
- ch 5 p5: '5.2' 1x in the rows, 0x on the page
- ch 5 p5: '5.4' 1x in the rows, 0x on the page
- ch 5 p5: '0.05' 1x in the rows, 0x on the page
- ch 5 p5: '5.3' 4x in the rows, 0x on the page
- ch 5 p5: 'to' 1x in the rows, 0x on the page
- ch 5 p5: '5.6' 1x in the rows, 0x on the page
- ch 5 p6: '→' 3x on the page, 0x in the rows
- ch 5 p6: '•' 1x on the page, 0x in the rows
- ch 5 p6: 'to' 5x in the rows, 0x on the page
- ch 5 p6: '5.7' 2x in the rows, 0x on the page
- ch 5 p6: '5.5' 1x in the rows, 0x on the page
- ch 5 p6: '5.4' 1x in the rows, 0x on the page
- ch 5 p6: 'dt' 5x in the rows, 0x on the page
- ch 5 p6: 'dv' 2x in the rows, 0x on the page
- ch 5 p7: '•' 1x on the page, 0x in the rows
- ch 5 p7: '5.8' 1x in the rows, 0x on the page
- ch 5 p7: 'to' 2x in the rows, 0x on the page
- ch 5 p7: 'dx' 1x in the rows, 0x on the page
- ch 5 p7: 'dh' 1x in the rows, 0x on the page
- ch 5 p8: 'dx' 2x in the rows, 0x on the page
- ch 5 p8: 'to' 2x in the rows, 0x on the page
- ch 5 p8: 'mgh' 1x in the rows, 0x on the page
- ch 5 p8: '•' 3x in the rows, 0x on the page
- ch 5 p8: '5.5' 1x in the rows, 0x on the page
- ch 5 p9: '•' 1x on the page, 0x in the rows
- ch 5 p10: '5.14' 1x in the rows, 0x on the page
- ch 5 p11: '•' 1x on the page, 0x in the rows
- ch 5 p11: 'to' 4x in the rows, 0x on the page
- ch 5 p12: '•' 1x on the page, 0x in the rows
- ch 5 p12: '5.8' 2x in the rows, 0x on the page
- ch 5 p13: '•' 1x on the page, 0x in the rows
- ch 5 p13: 'dt' 2x in the rows, 0x on the page
- ch 5 p13: '5.10' 1x in the rows, 0x on the page
- ch 5 p15: '•' 1x on the page, 0x in the rows
- ch 5 p15: '≃' 2x in the rows, 0x on the page
- ch 5 p15: '5.24' 1x in the rows, 0x on the page
- ch 5 p15: '5.30' 1x in the rows, 0x on the page
- ch 5 p16: '•' 1x on the page, 0x in the rows
- ch 5 p16: '5.28' 1x in the rows, 0x on the page
- ch 5 p16: '5.30' 1x in the rows, 0x on the page
- ch 5 p16: '5.12' 1x in the rows, 0x on the page
- ch 5 p16: '5.10' 1x in the rows, 0x on the page
- ch 5 p16: '5.32' 2x in the rows, 0x on the page
- ch 6 p5: 'nx' 1x in the rows, 0x on the page
- ch 6 p5: 'to' 2x in the rows, 0x on the page
- ch 6 p5: 'ix' 3x in the rows, 0x on the page
- ch 6 p5: 'iy' 1x in the rows, 0x on the page
- ch 6 p5: 'iz' 1x in the rows, 0x on the page
- ch 6 p5: 'ir' 2x in the rows, 0x on the page
- ch 6 p6: '→' 4x on the page, 0x in the rows
- ch 6 p8: 'MR' 1x in the rows, 0x on the page
- ch 6 p8: 'dR' 2x in the rows, 0x on the page
- ch 6 p8: 'dr' 5x in the rows, 0x on the page
- ch 6 p8: 'dV' 2x in the rows, 0x on the page
- ch 6 p8: 'dv' 5x in the rows, 0x on the page
- ch 6 p8: 'MA' 3x in the rows, 0x on the page
- ch 6 p9: 'mv' 1x in the rows, 0x on the page
- ch 6 p9: 'dp' 1x in the rows, 0x on the page
- ch 6 p9: 'nv' 1x in the rows, 0x on the page
- ch 6 p9: 'MV' 1x in the rows, 0x on the page
- ch 6 p9: 'dP' 3x in the rows, 0x on the page
- ch 6 p9: 'dV' 1x in the rows, 0x on the page
- ch 6 p9: 'MA' 1x in the rows, 0x on the page
- ch 6 p11: '→' 6x on the page, 0x in the rows
- ch 6 p11: 'ab' 2x in the rows, 0x on the page
- ch 6 p11: 'sin' 2x in the rows, 0x on the page
- ch 6 p12: '•' 1x in the rows, 0x on the page
- ch 6 p13: '×' 1x on the page, 0x in the rows
- ch 6 p13: 'PCP' 1x in the rows, 0x on the page
- ch 6 p15: '×' 3x on the page, 2x in the rows
- ch 6 p16: 'dl' 3x in the rows, 0x on the page
- ch 6 p16: 'to' 1x in the rows, 0x on the page
- ch 6 p16: 'dL' 1x in the rows, 0x on the page
- ch 6 p17: 'dL' 1x in the rows, 0x on the page
- ch 6 p17: 'dt' 2x in the rows, 0x on the page
- ch 6 p17: 'dP' 1x in the rows, 0x on the page
- ch 6 p18: 'to' 8x in the rows, 0x on the page
- ch 6 p21: 'Mg' 1x in the rows, 0x on the page
- ch 6 p21: 'ig' 2x in the rows, 0x on the page
- ch 6 p21: 'ir' 2x in the rows, 0x on the page
- ch 6 p23: 'to' 2x in the rows, 0x on the page
- ch 6 p24: 'to' 2x in the rows, 0x on the page
- ch 6 p30: 'to' 1x in the rows, 0x on the page
- ch 6 p31: 'dL' 3x in the rows, 0x on the page
- ch 6 p32: '×' 2x on the page, 0x in the rows
- ch 7 p4: '×' 9x on the page, 8x in the rows
- ch 7 p5: '0' 1x in the rows, 0x on the page
- ch 7 p7: 'GM' 1x in the rows, 0x on the page
- ch 7 p9: '×' 1x on the page, 0x in the rows
- ch 7 p9: 'mgh' 1x in the rows, 0x on the page
- ch 7 p9: 'to' 1x in the rows, 0x on the page
- ch 7 p9: 'dr' 1x in the rows, 0x on the page
- ch 7 p9: '∞' 2x in the rows, 0x on the page
- ch 7 p14: '×' 7x on the page, 6x in the rows
- ch 7 p14: '9.81' 1x in the rows, 0x on the page
- ch 7 p14: '6.37' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 1 p2: the rows carry 35% of the page's Devanagari words
- ch 4 p3: the rows carry 54% of the page's Devanagari words
- ch 6 p25: the rows carry 56% of the page's Devanagari words

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 1 p10: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 2 p10: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 3 p17: 0 paragraph(s) — sent: the heading could not be placed on it
- ch 4 p18: 5 paragraph(s) — sent: the heading could not be placed on it
- ch 5 p16: 7 paragraph(s) — sent: the heading could not be placed on it
- ch 6 p32: 1 paragraph(s) — sent: the heading could not be placed on it
- ch 7 p14: 5 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 117 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 117 page(s) called this run
- ch 1 p7 §1.4 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…न्य विमा, [M°], समय की शून्य विमा [T°…"
- ch 1 p7 §1.4 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…ून्य विमा [T°] तथा लम्बाई की 3 विमाएँ…"
- ch 1 p7 §1.5 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…मीय सूत्र [M° L^3 T°] और वेग या चाल क…"
- ch 1 p7 §1.5 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…्र [M° L^3 T°] और वेग या चाल का [M° L…"
- ch 1 p7 §1.5 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…या चाल का [M° L T^-1] है। इसी प्रकार,…"
- ch 1 p7 §1.5 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…ी प्रकार, [M° L T^-2], त्वरण का तथा […"
- ch 1 p7 §1.5 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…था [M L^-3 T°] द्रव्यमान घनत्व का विम…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 1 page 3 — confidence 0.75, 6 paragraphs
- ch 2 page 5 — confidence 0.78, 10 paragraphs
- ch 3 page 5 — confidence 0.7, 5 paragraphs
- ch 3 page 8 — confidence 0.7, 6 paragraphs
- ch 3 page 10 — confidence 0.75, 8 paragraphs
- ch 3 page 14 — confidence 0.75, 13 paragraphs
- ch 4 page 3 — confidence 0.75, 4 paragraphs
- ch 5 page 3 — confidence 0.75, 7 paragraphs
- ch 6 page 3 — confidence 0.75, 2 paragraphs
- ch 6 page 14 — confidence 0.7, 10 paragraphs
- ch 6 page 16 — confidence 0.75, 10 paragraphs
- ch 6 page 25 — confidence 0.7, 1 paragraphs
- ch 6 page 29 — confidence 0.72, 12 paragraphs
- ch 6 page 30 — confidence 0.75, 13 paragraphs
- ch 6 page 32 — confidence 0.75, 1 paragraphs
- ch 7 page 3 — confidence 0.75, 7 paragraphs
- ch 7 page 5 — confidence 0.75, 7 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 117 | 437916 | 255481 | 1295360 | 22528 | ₹651.46 |
jsonl: extract/phy11-part1/hi.jsonl (147 pages, 0 of them from earlier runs)

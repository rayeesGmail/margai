# margai-pipeline ncert verify

- run: 2026-10-16 08:14 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of phy12-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 d0cd71eae59f93c363e4db4be09308088cb172d54607f09ab8d9349ac8e0cdf9

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 9 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 10 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 11 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 12 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 13 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 14 | withheld: illegible text layer | — | — | — | — | — | — | — |

## where rows start against where the print starts paragraphs

none

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
none

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 99 of the 99 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 9 p9: 'पार्श्विक' 1x in the rows, 0x on the page
- ch 9 p19: 'चतुर्भुज' 2x in the rows, 1x on the page
- ch 9 p21: 'पतले' 2x in the rows, 1x on the page
- ch 10 p5: 'बिंदु' 2x in the rows, 1x on the page
- ch 10 p8: 'बिंदु' 7x in the rows, 6x on the page
- ch 10 p12: 'अदीप्त' 1x in the rows, 0x on the page
- ch 11 p8: 'सन्' 1x in the rows, 0x on the page
- ch 12 p7: 'उत्तेजित' 1x in the rows, 0x on the page
- ch 12 p13: 'हाइड्रोजन' 3x in the rows, 2x on the page
- ch 13 p7: 'न्यूक्लियॉन' 15x in the rows, 13x on the page
- ch 13 p8: 'न्यूक्लियॉन' 8x in the rows, 7x on the page
- ch 13 p8: 'न्यूक्लियॉनों' 1x in the rows, 0x on the page
- ch 13 p9: 'सन्' 1x in the rows, 0x on the page
- ch 14 p3: 'संयोजकता' 15x in the rows, 14x on the page
- ch 14 p12: 'इलेक्ट्रॉनों' 3x in the rows, 2x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 9 p7: '2.5' 1x in the rows, 0x on the page
- ch 9 p14: '21' 2x in the rows, 0x on the page
- ch 9 p17: '0.5' 1x in the rows, 0x on the page
- ch 9 p21: '≃' 1x in the rows, 0x on the page
- ch 9 p27: '≅' 1x on the page, 0x in the rows
- ch 10 p12: '×' 1x on the page, 0x in the rows
- ch 11 p8: 'hnu' 3x in the rows, 0x on the page
- ch 11 p12: 'hnu' 1x in the rows, 0x on the page
- ch 13 p13: '×' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (500 rows)
request id: pipeline-ncert-verify-a18a380b-5797-474b-a193-4576798e0af4

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 99 | 62 | 37 |
artefact: verify/phy12-part2/hi.jsonl (99 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 62 | 294931 | 15181 | 442311 | 7251 | ₹76.62 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 9 p5 §9.2.3 ¶9: printed "(Q PM = AB)" · transcribed "(∵ PM = AB)"
- ch 9 p10 §9.4 ¶6: printed "सभी प्रकाशिक परिघटनाओं को आजकल" · transcribed "पूर्ण आंतरिक परावर्तन के लिए एक प्रदर्शन सभी प्रकाशिक परिघटनाओं को आजकल"
- ch 9 p19 §9.6 ¶2: printed "भुजा के अन्य दो कोणों का योग" · transcribed "चतुर्भुज के अन्य दो कोणों का योग"
- ch 10 p12 §10.6 ¶1: printed "बारी-बारी से उदीप्त तथा दीप्त क्षेत्र आते हैं" · transcribed "बारी-बारी से अदीप्त तथा दीप्त क्षेत्र आते हैं"
- ch 11 p13 §11.8 ¶7: printed "m' =" · transcribed "m' = 0.150 kg, वेग v' = 30.0 m/s"
- ch 12 p11 §12.5 ¶1: printed "h nu_if = E_(n_f) – E_(n_i)" · transcribed "h nu_if = E_(n_i) – E_(n_f)"
- ch 13 p3 §13.2 ¶10: printed "यह एक प्रोटॉन, एक इलेक्ट्रॉन" · transcribed "यह एक प्रोट्रॉन, एक इलेक्ट्रॉन"
- ch 13 p9 §13.7 ¶1: printed "नाभिकों, A > 30, वाले भाग एवं भारी नाभिकों, A > 170, वाले" · transcribed "नाभिकों, A < 30, वाले भाग एवं भारी नाभिकों, A > 170, वाले"
- ch 13 p11 §13.7.2 ¶5: printed "^3_2H + ^3_2H -> ^4_2He + ^1_1H + ^1_1H + 12.86 MeV" · transcribed "^3_2He + ^3_2He -> ^4_2He + ^1_1H + ^1_1H + 12.86 MeV"
- ch 14 p12 §14.5.1 ¶6: printed "n-क्षेत्र में होलों की प्राप्ति" · transcribed "p-क्षेत्र में इलेक्ट्रॉनों की प्राप्ति"

## rows the verifier could not find on their page

- ch 12 p7 §12.3 ¶1 "अनुच्छेद 12.1 में उल्लेख किए अनुसार, प्रत्येक"

## running text the page prints that no row carries

- ch 9 p10: "पूर्ण आंतरिक परावर्तन के लिए एक प्रदर्शन (heading only, not counted)"
- ch 9 p18: "f= +10, -10 +30 cm (figure labels not required)"
- ch 10 p6: "करते हैं। (continuation sentence after section heading, part of item 4 already covers)"
- ch 11 p4: "(no running text omitted, but heading-like section numbers excluded as per convention)"
- ch 11 p9: "अल्बर्ट आइंस्टाइन (1879 – 1955) सन् 1879 में जर्मनी में उल्म नामक स्थान..."
- ch 13 p13: "सारांश section (1. प्रत्येक परमाणु में एक नाभिक होता है...)"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 9 p2 §9.2.1 ¶1: printed "आपतित प्रकाश की दिशा में मापी गई दूरियाँ धनात्मक मानी जाती हैं तथा जो दूरियाँ आपतित प्रकाश की दिशा के विपरीत दिशा में मापी जाती हैं वे ऋणात्मक मानी जाती हैं (चित्र 9.2)। x-अक्ष के सापेक्ष" · transcribed "आपतित प्रकाश की दिशा में मापी गई दूरियाँ धनात्मक मानी जाती हैं तथा जो दूरियाँ आपतित प्रकाश की दिशा के विपरीत दिशा में मापी जाती हैं वे ऋणात्मक मानी जाती हैं (चित्र 9.2)। x-अक्ष के सापेक्ष"
- ch 9 p5 §9.2.3 ¶10: printed "∠ APB = ∠ A'PB'" · transcribed "∠ APB = ∠ A'PB'"
- ch 9 p5 §9.2.3 ¶10: printed "समकोण त्रिभुज A'B'P तथा ABP" · transcribed "समकोण त्रिभुज A'B'P तथा ABP"
- ch 9 p7 §9.2.3 ¶22: printed "(b) 29 m, (c) 19 m, तथा (d) 9 m" · transcribed "(b) 29 m, (c) 19 m, तथा (d) 9 m"
- ch 9 p9 §9.3 ¶8: printed "आभासी गहराई (h_1) वास्तविक गहराई (h_2)" · transcribed "आभासी गहराई (h_1) वास्तविक गहराई (h_2)"
- ch 9 p11 §9.4.1 ¶2: printed "लंबी दूरी तक संचरित करने" · transcribed "लंबी दूरी तक संचरित करने"
- ch 9 p12 §9.4.1 ¶3: printed "पूर्ण आंतरिक परावर्तित होता है" · transcribed "पूर्ण आंतरिक परावर्तित होता है"
- ch 9 p12 §9.4.1 ¶3: printed "अंततः दूसरे सिरे" · transcribed "अंततः दूसरे सिरे"
- ch 9 p13 §9.5.1 ¶1: printed "n_2 / v − n_1 / u" · transcribed "n_2 / v − n_1 / u"
- ch 9 p13 §9.5.1 ¶3: printed "वायु में रखे किसी बिंदु स्रोत" · transcribed "वायु में रखे किसी बिंदु स्रोत"
- ch 9 p13 §9.5.1 ¶3: printed "काँच के किसी गोलीय पृष्ठ पर पड़ता है" · transcribed "काँच के किसी गोलीय पृष्ठ पर पड़ता है"
- ch 9 p14 §9.5.2 ¶5: printed "F तथा F' ।" · transcribed "F तथा F'।"
- ch 9 p17 §9.5.4 ¶2: printed "1/v_1 − 1/u = 1/f_1" · transcribed "1/v_1 − 1/u = 1/f_1"
- ch 9 p21 §9.6 ¶11: printed "पतले प्रिज़्म के लिए" · transcribed "पतले प्रिज़्म के लिए"
- ch 10 p3 §10.2 ¶4: printed "v tau" · transcribed "v tau"
- ch 10 p3 §10.2 ¶4: printed "v माध्यम" · transcribed "v माध्यम"
- ch 10 p6 §10.3.3 ¶1: printed "बिंदु B से C तक आगे बढ़ने में लिए गए समय को निरूपित करता है, तब दूरी BC= v tau" · transcribed "बिंदु B से C तक आगे बढ़ने में लिए गए समय को निरूपित करता है, तब दूरी BC= v tau"
- ch 10 p11 §10.5 ¶5: printed "n = 0,±1,±2" · transcribed "n = 0, ±1, ±2"
- ch 10 p16 §10.7 ¶6: printed "ध्रुवित प्रकाश-पुंज पोलोरॉइड P_1" · transcribed "ध्रुवित प्रकाश-पुंज पोलोरॉइड P_1"
- ch 11 p3 §11.3.1 ¶1: printed "हेनरिच हर्ट्ज़" · transcribed "हेनरिच हर्ट्ज़"
- ch 11 p9 §11.6 ¶7: printed "में भाग ले सकने वाले इलेक्ट्रॉनों की संख्या से ही" · transcribed "में भाग ले सकने वाले इलेक्ट्रॉनों की संख्या से ही"
- ch 12 p6 §12.2.2 ¶1: printed "अतः, हाइड्रोजन" · transcribed "अतः, हाइड्रोजन"
- ch 12 p6 §12.2.2 ¶1: printed "हाइड्रोजन परमाणु में गतिशील स्थिर कक्षा के लिए" · transcribed "हाइड्रोजन परमाणु में गतिशील स्थिर कक्षा के लिए"
- ch 12 p7 §12.2.2 ¶5: printed "(1.6 × 10^(-19)C)^2" · transcribed "(1.6 × 10^(-19) C)^2"
- ch 12 p8 §12.3 ¶1: printed "प्रेक्षित रैखिक स्पेक्ट्रम" · transcribed "प्रेक्षित रैखिक स्पेक्ट्रम"
- ch 12 p9 §12.4 ¶3: printed "2.2 × 10^-6 m/s" · transcribed "2.2 × 10^-6 m/s"
- ch 12 p10 §12.4 ¶9: printed "r_n = (n^2/m)(h/2pi)^2 4 pi epsilon_0 / e^2" · transcribed "r_n = (n^2/m)(h/2pi)^2 4 pi epsilon_0 / e^2"
- ch 12 p11 §12.4.1 ¶2: printed "E_3 = –1.51 eV" · transcribed "E_3 = –1.51 eV"
- ch 13 p2 §13.2 ¶4: printed "m_p = 1.00727 u = 1.67262 × 10^(-27) kg" · transcribed "m_p = 1.00727 u = 1.67262 × 10^(-27) kg"
- ch 13 p2 §13.2 ¶5: printed "हाइड्रोजन परमाणु के द्रव्यमान 1.00783 u में से" · transcribed "हाइड्रोजन परमाणु के द्रव्यमान 1.00783 u में से"
- ch 13 p3 §13.2 ¶10: printed "एक मुक्त प्रोटॉन के विपरीत" · transcribed "एक मुक्त प्रोटॉन के विपरीत"
- ch 13 p3 §13.2 ¶13: printed "संकेत चिह्न _Z^A X" · transcribed "संकेत चिह्न _Z^A X"
- ch 13 p3 §13.2 ¶13: printed "संकेत _79^197 Au" · transcribed "संकेत _79^197 Au"
- ch 13 p6 §13.4.2 ¶1: printed "8 × 1.00727 u 8 इलेक्ट्रॉनों का द्रव्यमान = 8 × 0.00055 u इसलिए ^16_8O के नाभिक का अपेक्षित द्रव्यमान = 8 × 2.01593 u" · transcribed "8 × 1.00727 u 8 इलेक्ट्रॉनों का द्रव्यमान = 8 × 0.00055 u इसलिए ^16_8O के नाभिक का अपेक्षित द्रव्यमान = 8 × 2.01593 u"
- ch 13 p6 §13.4.2 ¶6: printed "1.6605 × 10^(-27) kg" · transcribed "1.6605 × 10^(-27) kg"
- ch 13 p8 §13.5 ¶4: printed "r_0 पर स्थितिज" · transcribed "r_0 पर स्थितिज"
- ch 13 p11 §13.7.2 ¶5: printed "^3_2He + ^3_2He -> ^4_2He" · transcribed "^3_2He + ^3_2He -> ^4_2He"
- ch 14 p2 §14.2 ¶2: printed "10^(-2) – 10^(-8)" · transcribed "10^(-2) – 10^(-8)"
- ch 14 p2 §14.2 ¶3: printed "sigma ~ 10^5 – 10^(-6)" · transcribed "sigma ~ 10^5 – 10^(-6)"
- ch 14 p9 §14.4 ¶10: printed "n_i घट कर n_e" · transcribed "n_i घट कर n_e"
- ch 14 p13 §14.6.1 ¶5: printed "इस सांद्रता प्रवणता के कारण p-फलक की संधि के किनारे विसरित होकर" · transcribed "इस सांद्रता प्रवणता के कारण p-फलक की संधि के किनारे विसरित होकर"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 11 p4 §11.4 ¶1: transcribed "क्वार्टज़ गवाक्ष" (printed "क्वार्ट्ज़ गवाक्ष")
- ch 13 p9 §13.6 ¶2: transcribed "नाभिकों, A < 30, वाले" (printed "नाभिकों, A > 30, वाले")

## set aside by code: a heading or a caption listed as omitted text

- ch 9 p2: "9.2 गोलीय दर्पणों द्वारा प्रकाश का परावर्तन"
- ch 9 p2: "9.2.1 चिह्न परिपाटी"
- ch 9 p8: "चित्र 9.8 प्रकाश का अपवर्तन तथा परावर्तन"
- ch 9 p9: "9.4 पूर्ण आंतरिक परावर्तन"
- ch 9 p10: "सारणी 9.1 कुछ पारदर्शी माध्यमों का वायु के सापेक्ष क्रांतिक कोण (table, not counted)"
- ch 9 p12: "9.5 गोलीय पृष्ठों तथा लेंसों द्वारा अपवर्तन"
- ch 9 p12: "9.5.1 किसी गोलीय पृष्ठ पर अपवर्तन"
- ch 9 p20: "चित्र 9.22 किसी त्रिभुजाकार प्रिज़्म के लिए आपतन कोण (caption)"
- ch 9 p21: "9.7 प्रकाशिक यंत्र"
- ch 9 p21: "9.7.1 सूक्ष्मदर्शी"
- ch 10 p4: "10.3 हाइगेंस सिद्धांत का उपयोग करते हुए समतल तरंगों का अपवर्तन तथा परावर्तन"
- ch 10 p4: "10.3.1 समतल तरंगों का अपवर्तन"
- ch 10 p12: "10.6 विवर्तन"
- ch 10 p12: "10.6.1 एकल झिरी"
- ch 10 p16: "चित्र 10.18 (a) दो पोलेरॉइड P_2 तथा P_1 से होकर प्रकाश का पारगमन।"
- ch 11 p1: "11.1 भूमिका"
- ch 11 p7: "11.5 प्रकाश-विद्युत प्रभाव तथा प्रकाश का तरंग सिद्धांत"
- ch 11 p8: "11.6 आइंस्टाइन का प्रकाश-विद्युत समीकरण : विकिरण का ऊर्जा क्वांटम"
- ch 11 p10: "11.7 प्रकाश की कणीय प्रकृति : फ़ोटॉन"
- ch 11 p11: "11.8 द्रव्य की तरंग प्रकृति"
- ch 12 p1: "12.1 भूमिका"
- ch 12 p5: "12.2.1 ऐल्फ़ा-कण प्रक्षेप-पथ"
- ch 12 p7: "12.3 परमाण्वीय स्पेक्ट्रम"
- ch 12 p8: "12.4 हाइड्रोजन परमाणु का बोर मॉडल (heading, not required)"
- ch 12 p11: "12.5 हाइड्रोजन परमाणु का लाइन स्पेक्ट्रम"
- ch 12 p11: "चित्र 12.7 हाइड्रोजन परमाणु के लिए ऊर्जा-स्तर आरेख"
- ch 13 p1: "13.1 भूमिका"
- ch 13 p1: "13.2 परमाणु द्रव्यमान एवं नाभिक की संरचना"
- ch 13 p9: "13.6 रेडियोऐक्टिवता"
- ch 13 p9: "13.7 नाभिकीय ऊर्जा"
- ch 13 p12: "13.7.3 नियंत्रित ताप नाभिकीय संलयन"
- ch 14 p5: "चित्र 14.3 कार्बन, सिलिकॉन या जरमेनियम के लिए तीन-विमीय हीरे जैसी क्रिस्टल संरचना जिसमें जालक अंतराल a क्रमश: 3.56, 5.43 और 5.66 Å है।"
- ch 14 p7: "14.4 अपद्रव्यी अर्धचालक"
- ch 14 p8: "चित्र 14.7 (a) चतुर्थ संयोजी Si या Ge में पंचसंयोजी दाता परमाणु (As, Sb, P, आदि) के अपमिश्रण से बना n-अर्धचालक। (b) n-प्रकार के पदार्थ का साधारणतया प्रयुक्त व्यवस्थात्मक निरूपण जिसमें प्रतिस्थापी दाता के स्थिर क्रोड़ को केवल एक अतिरिक्त प्रभावी धनात्मक आवेश और इससे संबद्ध इलेक्ट्रॉन के साथ दिखाया गया है।"
- ch 14 p10: "चित्र 14.9 T > 0 K पर (a) n-प्रकार के अर्धचालक तथा (b) p-प्रकार के अर्धचालक का ऊर्जा बैंड।"
- ch 14 p12: "14.6 अर्धचालक डायोड"
- ch 14 p12: "14.6.1 अग्रदिशिक बायस में p-n संधि डायोड"
- ch 14 p13: "14.6.2 पश्चदिशिक बायस में p-n संधि डायोड"
flags set aside by the founder's rulings in ncert-corrections.yaml: 8
verdicts recorded on rows: 500

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 9 | 135 | 135 | 132 | 3 | 0 | 0 | 0 | 97.8% |
| 10 | 78 | 78 | 77 | 1 | 0 | 0 | 0 | 98.7% |
| 11 | 68 | 68 | 66 | 1 | 0 | 1 | 0 | 97.1% |
| 12 | 57 | 57 | 55 | 1 | 1 | 0 | 0 | 96.5% |
| 13 | 87 | 87 | 83 | 3 | 0 | 1 | 0 | 95.4% |
| 14 | 75 | 75 | 74 | 1 | 0 | 0 | 0 | 98.7% |
clean for the book (PLAN D15 ✅): 487 of 500 paragraphs, 97.4%
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 6 passages no row carries

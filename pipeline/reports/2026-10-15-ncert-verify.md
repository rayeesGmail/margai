# margai-pipeline ncert verify

- run: 2026-10-15 07:34 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of phy12-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 a23017022a60407db63828d10c75f28b8accaa9280d99998956bbee4106d7bc2

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
- ch 9 p6: 'प्रतिबिंबों' 1x in the rows, 0x on the page
- ch 9 p9: 'पार्श्विक' 1x in the rows, 0x on the page
- ch 9 p11: 'प्रिज़्मों' 1x in the rows, 0x on the page
- ch 9 p21: 'पतले' 2x in the rows, 1x on the page
- ch 9 p21: 'जिससे' 1x in the rows, 0x on the page
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
- ch 9 p7: '2.5' 1x in the rows, 0x on the page
- ch 9 p14: '21' 2x in the rows, 0x on the page
- ch 9 p17: '0.5' 1x in the rows, 0x on the page
- ch 9 p21: '≃' 1x in the rows, 0x on the page
- ch 9 p27: '≅' 1x on the page, 0x in the rows
- ch 10 p12: '×' 1x on the page, 0x in the rows
- ch 11 p8: '•' 1x on the page, 0x in the rows
- ch 11 p8: 'hnu' 3x in the rows, 0x on the page
- ch 11 p12: 'hnu' 1x in the rows, 0x on the page
- ch 12 p11: 'hv' 1x in the rows, 0x on the page
- ch 13 p13: '×' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (465 rows)
request id: pipeline-ncert-verify-c9c50e13-88a3-4f02-8fc6-397648127daa

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 99 | 99 | 0 |
artefact: verify/phy12-part2/hi.jsonl (99 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 99 | 466175 | 22636 | 717849 | 7251 | ₹119.31 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 9 p16 §9.5.3 ¶3: printed "अपवर्तनांक 1.5 है।" · transcribed "अपवर्तनांक 1.5 है।]"
- ch 9 p21 §9.6 ¶6: printed "अर्थात पहले प्रिज़्म के लिए" · transcribed "अर्थात पतले प्रिज़्म के लिए"
- ch 9 p23 §9.7.1 ¶5: printed "≈ θ_0 (9.40)" · transcribed "≈ theta_0 (9.40)"
- ch 9 p23 §9.7.1 ¶6: printed "h/(-v) · v/u = h/(-u) ≈ θ;" · transcribed "h/(-v) · v/u = h/(-u) ≈ theta;"
- ch 10 p9 §10.4 ¶8: printed "चक्र बाद" · transcribed "चक्र बाद पहुँचती हैं [चित्र 10.10(b)]"
- ch 12 p3 §12.2 ¶1: printed "α-कणों के एक पुंज" · transcribed "alpha–कणों के एक पुंज"
- ch 12 p3 §12.2 ¶1: printed "प्रकीर्णित α-कणों का विक्षेप" · transcribed "प्रकीर्णित alpha–कणों का विक्षेप"
- ch 12 p4 §12.2 ¶4: printed "पार करते समय α-कण" · transcribed "पार करते समय alpha-कण"
- ch 12 p4 §12.2 ¶4: printed "चूँकि स्वर्ण-नाभिक α-कण" · transcribed "चूँकि स्वर्ण-नाभिक alpha-कण"
- ch 12 p5 §12.2.1 ¶4: printed "त्रिज्या होगी 10^5 × 7 × 10^8 m = 7 × 10^13 m" · transcribed "त्रिज्या होगी 10^5 × 7 × 10^8 m = 7 × 10^13 m।"
- ch 12 p11 §12.4.1 ¶1: printed "E_1 13.6 eV" · transcribed "E_1 –13.6 eV"
- ch 12 p13 §12.6 ¶10: printed "हाइड्रोजन परमाणु, एकधा आयनित हीलियम, द्वितः आयनित लीथियम" · transcribed "हाइडोजन परमाणु, एकधा आयनित हीलियम, द्वितः आयनित लीथियम"
- ch 13 p2 §13.2 ¶5: printed "सापेक्ष बहुलता क्रमश: 75.4 एवं 24.6% है" · transcribed "इन समस्थानिकों की सापेक्ष बहुलता क्रमशः 75.4 एवं 24.6% है"
- ch 13 p4 §13.3 ¶2: printed "5.5 MeV से अधिक ऊर्जा के α-कण प्रयोग करें" · transcribed "5.5 MeV से अधिक ऊर्जा के alpha-कण प्रयोग करें"
- ch 13 p5 §13.4.2 ¶1: printed "योग Σm के बराबर" · transcribed "योग sum m के बराबर"
- ch 13 p5 §13.4.2 ¶1: printed "द्रव्यमान M, सदैव Σm से कम" · transcribed "द्रव्यमान M, सदैव sum m से कम"
- ch 14 p13 §14.6.1 ¶4: printed "अल्पांश वाहक अंत:क्षेपण (Minority current injection)" · transcribed "अल्पांश वाहक अंतःक्षेपण (Minority carrier injection)"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 10 p6: "करते हैं। (continuation sentence after section heading, part of item 4 already covers)"
- ch 10 p8: "परावर्तित एवं अपवर्तित दोनों प्रकाश की आवृत्तियाँ (checking item 1 wording)"
- ch 11 p9: "अल्बर्ट आइंस्टाइन (1879 – 1955) सन् 1879 में जर्मनी में उल्म नामक स्थान..."
- ch 11 p13: "सारांश 1. किसी इलेक्ट्रॉन को धातु से बाहर निकालने के लिए"
- ch 11 p13: "2. प्रकाश-विद्युत प्रभाव धातुओं से उपयुक्त आवृत्ति के प्रकाश से प्रदीप्त करने पर"
- ch 12 p9: "(i) बोर का पहला अभिगृहीत था कि किसी परमाणु में कोई इलेक्ट्रॉन निश्चित स्थायी कक्षाओं में विकिरण ऊर्जा उत्सर्जित किए बिना परिक्रमण कर सकता है"
- ch 13 p13: "सारांश section (summary points 1 and 2) printed at bottom of page"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 9 p2 §9.2.1 ¶1: printed "आपतित प्रकाश की दिशा में मापी गई दूरियाँ धनात्मक मानी जाती हैं तथा जो दूरियाँ आपतित प्रकाश की दिशा के विपरीत दिशा में मापी जाती हैं वे ऋणात्मक मानी जाती हैं (चित्र 9.2)। x-अक्ष के सापेक्ष" · transcribed "आपतित प्रकाश की दिशा में मापी गई दूरियाँ धनात्मक मानी जाती हैं तथा जो दूरियाँ आपतित प्रकाश की दिशा के विपरीत दिशा में मापी जाती हैं वे ऋणात्मक मानी जाती हैं (चित्र 9.2)। x-अक्ष के सापेक्ष"
- ch 9 p5 §9.2.3 ¶9: printed "(∵ PM = AB)" · transcribed "(∵ PM = AB)"
- ch 9 p5 §9.2.3 ¶9: printed "∠ APB = ∠ A'PB'" · transcribed "∠ APB = ∠ A'PB'"
- ch 9 p5 §9.2.3 ¶10: printed "दर्पण MPN की ओर" · transcribed "दर्पण MPN की ओर"
- ch 9 p7 §9.2.3 ¶21: printed "(b) 29 m, (c) 19 m, तथा (d) 9 m" · transcribed "(b) 29 m, (c) 19 m, तथा (d) 9 m"
- ch 9 p9 §9.3 ¶6: printed "आभासी गहराई (h_1) वास्तविक गहराई (h_2)" · transcribed "आभासी गहराई (h_1) वास्तविक गहराई (h_2)"
- ch 9 p9 §9.4 ¶1: printed "आंतरिक परावर्तन कहते हैं" · transcribed "आंतरिक परावर्तन कहते हैं"
- ch 9 p11 §9.4 ¶7: printed "वापस परावर्तित हो जाए" · transcribed "वापस परावर्तित हो जाए"
- ch 9 p12 §9.4.1 ¶3: printed "पूर्ण आंतरिक परावर्तित होता है" · transcribed "पूर्ण आंतरिक परावर्तित होता है"
- ch 9 p12 §9.4.1 ¶3: printed "अंततः दूसरे सिरे" · transcribed "अंततः दूसरे सिरे"
- ch 9 p13 §9.5.1 ¶1: printed "n_2 / v − n_1 / u" · transcribed "n_2 / v − n_1 / u"
- ch 9 p13 §9.5.1 ¶3: printed "वायु में रखे किसी बिंदु स्रोत" · transcribed "वायु में रखे किसी बिंदु स्रोत"
- ch 9 p13 §9.5.1 ¶3: printed "काँच के किसी गोलीय पृष्ठ पर पड़ता है" · transcribed "काँच के किसी गोलीय पृष्ठ पर पड़ता है"
- ch 9 p14 §9.5.2 ¶3: printed "F तथा F'" · transcribed "F तथा F'"
- ch 9 p17 §9.5.4 ¶2: printed "1/v_1 − 1/u = 1/f_1" · transcribed "1/v_1 − 1/u = 1/f_1"
- ch 9 p17 §9.5.4 ¶2: printed "1/v − 1/v_1 = 1/f_2" · transcribed "1/v − 1/v_1 = 1/f_2"
- ch 10 p3 §10.2 ¶4: printed "v tau" · transcribed "v tau"
- ch 10 p3 §10.2 ¶4: printed "v माध्यम" · transcribed "v माध्यम"
- ch 10 p6 §10.3.3 ¶1: printed "बिंदु B से C तक आगे बढ़ने में लिए गए समय को निरूपित करता है, तब दूरी BC= v tau" · transcribed "बिंदु B से C तक आगे बढ़ने में लिए गए समय को निरूपित करता है, तब दूरी BC= v tau"
- ch 10 p11 §10.5 ¶4: printed "x = x_n = (n+ 1/2 ) lambda D / d" · transcribed "x = x_n = (n + 1/2) lambda D / d"
- ch 10 p16 §10.7 ¶6: printed "ध्रुवित प्रकाश-पुंज पोलोरॉइड P_1" · transcribed "ध्रुवित प्रकाश-पुंज पोलोरॉइड P_1"
- ch 11 p3 §11.3.1 ¶1: printed "हेनरिच हर्ट्ज़" · transcribed "हेनरिच हर्ट्ज़"
- ch 11 p9 §11.6 ¶7: printed "में भाग ले सकने वाले इलेक्ट्रॉनों की संख्या से ही" · transcribed "में भाग ले सकने वाले इलेक्ट्रॉनों की संख्या से ही"
- ch 11 p13 §11.8 ¶9: printed "4.50× kg m/s" · transcribed "4.50× kg m/s"
- ch 12 p2 §12.1 ¶5: printed "अर्नेस्ट मार्सडन (1889 – 1970, जो 20 वर्षीय" · transcribed "अर्नेस्ट मार्सडन (1889 – 1970, जो 20 वर्षीय"
- ch 12 p3 §12.2 ¶1: printed "प्रकीर्णित ऐल्फ़ा-कण परदे" · transcribed "प्रकीर्णित ऐल्फ़ा-कण परदे"
- ch 12 p7 §12.2.2 ¶6: printed "(1.6 × 10^(-19)C)^2" · transcribed "(1.6 × 10^(-19) C)^2"
- ch 12 p8 §12.3 ¶1: printed "प्रेक्षित रैखिक स्पेक्ट्रम" · transcribed "प्रेक्षित रैखिक स्पेक्ट्रम"
- ch 12 p9 §12.4 ¶3: printed "2.2 × 10^-6 m/s है" · transcribed "2.2 × 10^-6 m/s है"
- ch 13 p3 §13.2 ¶13: printed "_79^197 Au" · transcribed "_79^197 Au"
- ch 13 p3 §13.2 ¶13: printed "_Z^A X" · transcribed "_Z^A X"
- ch 13 p8 §13.4.2 ¶15: printed "टूटता है तो" · transcribed "टूटता है तो"
- ch 13 p9 §13.6 ¶2: printed "(i) alpha-क्षय" · transcribed "(i) alpha-क्षय"
- ch 13 p9 §13.6 ¶2: printed "(ii) beta-क्षय" · transcribed "(ii) beta-क्षय"
- ch 13 p9 §13.6 ¶2: printed "(iii) gamma-क्षय" · transcribed "(iii) gamma-क्षय"
- ch 13 p9 §13.6 ¶2: printed "पॉजीट्रॉन (ऐसे कण" · transcribed "पॉजीट्रॉन (ऐसे कण"
- ch 13 p11 §13.7.2 ¶6: printed "^3_2H + ^3_2H -> ^4_2He" · transcribed "^3_2H + ^3_2H -> ^4_2He"
- ch 13 p11 §13.7.2 ¶6: printed "^3_2H + ^3_2H" · transcribed "^3_2H + ^3_2H"
- ch 13 p11 §13.7.2 ¶6: printed "^3_2He + gamma + 5.49 MeV (iii) ^3_2H + ^3_2H -> ^4_2He" · transcribed "^3_2He + gamma + 5.49 MeV (iii) ^3_2H + ^3_2H -> ^4_2He"
- ch 14 p2 §14.2 ¶2: printed "rho ~ 10^(-5) – 10^6" · transcribed "rho ~ 10^(-5) – 10^6"
- ch 14 p2 §14.2 ¶2: printed "sigma ~ 10^5 – 10^(-6)" · transcribed "sigma ~ 10^5 – 10^(-6)"
- ch 14 p9 §14.4 ¶8: printed "n_i घट कर n_e" · transcribed "n_i घट कर n_e"
- ch 14 p12 §14.6 ¶2: printed "साम्या रोधिका (Equilibrium barrier) को दर्शाती है।" · transcribed "साम्या रोधिका (Equilibrium barrier) को दर्शाती है।"
- ch 14 p13 §14.6.2 ¶1: printed "ह्रासी क्षेत्र की चौड़ाई में विद्युत में परिवर्तन" · transcribed "ह्रासी क्षेत्र की चौड़ाई में विद्युत में परिवर्तन"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 9 p13 §9.5.1 ¶1: transcribed "n_1 / OM + n_2 / MC" (printed "n_1 / OM + n_2 / MI")
- ch 10 p17 §10.7 ¶10: transcribed "तीव्रता I_0 है" (printed "तीव्रता I_a है")
- ch 12 p3 §12.2 ¶1: transcribed "रेडियोऐक्टिव स्रोत ... से उत्सर्जित alpha–कणों के" (printed "रेडियोऐक्टिव स्रोत ... से उत्सर्जित α-कणों के")

## set aside by code: a heading or a caption listed as omitted text

- ch 9 p2: "9.2 गोलीय दर्पणों द्वारा प्रकाश का परावर्तन"
- ch 9 p2: "9.2.1 चिह्न परिपाटी"
- ch 9 p9: "9.4 पूर्ण आंतरिक परावर्तन"
- ch 9 p10: "सारणी 9.1 कुछ पारदर्शी माध्यमों का वायु के सापेक्ष क्रांतिक कोण"
- ch 9 p11: "9.4.1 प्रकृति में पूर्ण आंतरिक परावर्तन तथा इसके प्रौद्योगिकीय अनुप्रयोग"
- ch 9 p12: "9.5 गोलीय पृष्ठों तथा लेंसों द्वारा अपवर्तन"
- ch 9 p12: "9.5.1 किसी गोलीय पृष्ठ पर अपवर्तन"
- ch 9 p14: "चित्र 9.16 (a) बिंब की स्थिति तथा उभयोत्तल लेंस द्वारा निर्मित प्रतिबिंब (b) पहले गोलीय पृष्ठ पर अपवर्तन (c) दूसरे गोलीय पृष्ठ पर अपवर्तन।"
- ch 9 p19: "9.6 प्रिज़्म में अपवर्तन (heading, not omitted)"
- ch 9 p21: "9.7 प्रकाशिक यंत्र"
- ch 9 p21: "9.7.1 सूक्ष्मदर्शी"
- ch 10 p4: "10.3 हाइगेंस सिद्धांत का उपयोग करते हुए समतल तरंगों का अपवर्तन तथा परावर्तन"
- ch 10 p4: "10.3.1 समतल तरंगों का अपवर्तन"
- ch 10 p16: "चित्र 10.18 (a) दो पोलेरॉइड P_2 तथा P_1 से होकर प्रकाश का पारगमन।"
- ch 11 p1: "11.1 भूमिका"
- ch 11 p7: "11.5 प्रकाश-विद्युत प्रभाव तथा प्रकाश का तरंग सिद्धांत"
- ch 11 p8: "11.6 आइंस्टाइन का प्रकाश-विद्युत समीकरण : विकिरण का ऊर्जा क्वांटम"
- ch 11 p10: "11.7 प्रकाश की कणीय प्रकृति : फ़ोटॉन"
- ch 12 p1: "12.1 भूमिका"
- ch 12 p5: "12.2.1 ऐल्फ़ा-कण प्रक्षेप-पथ"
- ch 12 p8: "12.4 हाइड्रोजन परमाणु का बोर मॉडल (heading, not required)"
- ch 12 p11: "12.5 हाइड्रोजन परमाणु का लाइन स्पेक्ट्रम"
- ch 13 p1: "13.1 भूमिका"
- ch 13 p1: "13.2 परमाणु द्रव्यमान एवं नाभिक की संरचना"
- ch 13 p7: "चित्र 13.1 द्रव्यमान संख्या के फलन के रूप में प्रति न्यूक्लियॉन बंधन-ऊर्जा।"
- ch 13 p9: "13.7 नाभिकीय ऊर्जा"
- ch 13 p12: "13.7.3 नियंत्रित ताप नाभिकीय संलयन"
- ch 14 p5: "चित्र 14.3 कार्बन, सिलिकॉन या जरमेनियम के लिए तीन-विमीय हीरे जैसी क्रिस्टल संरचना जिसमें जालक अंतराल a क्रमशः: 3.56, 5.43 और 5.66 Å है।"
- ch 14 p7: "14.4 अपद्रव्यी अर्धचालक"
- ch 14 p8: "चित्र 14.7 (a) चतुर्थ संयोजी Si या Ge में पंचसंयोजी दाता परमाणु (As, Sb, P, आदि) के अपमिश्रण से बना n-अर्धचालक। (b) n-प्रकार के पदार्थ का साधारणतया प्रयुक्त व्यवस्थात्मक निरूपण जिसमें प्रतिस्थापी दाता के स्थिर क्रोड़ को केवल एक अतिरिक्त प्रभावी धनात्मक आवेश और इससे संबद्ध इलेक्ट्रॉन के साथ दिखाया गया है।"
- ch 14 p10: "चित्र 14.9 T > 0 K पर (a) n-प्रकार के अर्धचालक तथा (b) p-प्रकार के अर्धचालक का ऊर्जा बैंड।"
- ch 14 p12: "14.6 अर्धचालक डायोड"
- ch 14 p12: "14.6.1 अग्रदिशिक बायस में p-n संधि डायोड"
- ch 14 p15: "चित्र 14.17 (figure caption)"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 465

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 9 | 123 | 123 | 118 | 4 | 0 | 1 | 0 | 95.9% |
| 10 | 72 | 72 | 70 | 1 | 0 | 1 | 0 | 97.2% |
| 11 | 65 | 65 | 65 | 0 | 0 | 0 | 0 | 100.0% |
| 12 | 55 | 55 | 50 | 5 | 0 | 0 | 0 | 90.9% |
| 13 | 82 | 82 | 79 | 3 | 0 | 0 | 0 | 96.3% |
| 14 | 68 | 68 | 67 | 1 | 0 | 0 | 0 | 98.5% |
clean for the book (PLAN D15 ✅): 449 of 465 paragraphs, 96.6%
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 7 passages no row carries

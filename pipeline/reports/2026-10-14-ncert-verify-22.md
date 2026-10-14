# margai-pipeline ncert verify

- run: 2026-10-14 17:33 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 46da7b276a161ffb8a0dec4fdf46206b03c9c70fb9ac5c038bb84f249502f595

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 2 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 3 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 4 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 5 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 6 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 7 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 114 of the 114 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 1 p5: 'की' 9x in the rows, 8x on the page
- ch 1 p5: 'चाल' 1x in the rows, 0x on the page
- ch 3 p16: 'परिमाण' 4x in the rows, 3x on the page
- ch 6 p29: 'दृढ़' 2x in the rows, 1x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 1 p2: '×' 5x on the page, 0x in the rows
- ch 1 p3: '×' 4x on the page, 0x in the rows
- ch 2 p3: '5.0' 1x in the rows, 0x on the page
- ch 3 p11: '→' 2x on the page, 1x in the rows
- ch 3 p16: 'lim' 4x in the rows, 0x on the page
- ch 5 p4: '×' 4x on the page, 2x in the rows
- ch 5 p5: '×' 7x on the page, 2x in the rows
- ch 5 p5: '→' 1x on the page, 0x in the rows
- ch 6 p5: 'ix' 1x in the rows, 0x on the page
- ch 6 p12: '•' 1x in the rows, 0x on the page
- ch 6 p13: '×' 1x on the page, 0x in the rows
- ch 6 p13: 'PCP' 1x in the rows, 0x on the page
- ch 6 p15: '×' 3x on the page, 2x in the rows
- ch 6 p17: 'dP' 1x in the rows, 0x on the page
- ch 6 p21: 'ig' 2x in the rows, 0x on the page
- ch 6 p32: '×' 2x on the page, 0x in the rows
- ch 7 p14: '×' 7x on the page, 6x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 1 p2: the rows carry 35% of the page's Devanagari words
- ch 4 p3: the rows carry 55% of the page's Devanagari words
- ch 6 p25: the rows carry 56% of the page's Devanagari words
no model was called: add --read-pages for the second read

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 2 p9: "सारिणी 2.2 (table)"
- ch 3 p2: "इसी प्रकार, किन्हीं दो बिंदुओं के मध्य विस्थापन सदिश का परिमाण या तो गतिमान वस्तु की पथ-लंबाई से कम होता है या उसके बराबर होता है (bold italic emphasis in print)"
- ch 4 p3: "प्राचीन भारतीय विज्ञान में गति संबंधी धारणाएँ (box text)"
- ch 4 p5: "संवेग (heading)"
- ch 6 p17: "कोणीय संवेग का संरक्षण"
- ch 7 p8: "(b) चित्र 7.8 caption: किसी गहराई d पर g इस प्रकरण में केवल (R_E– d) त्रिज्या का छोटा गोला ही g के लिए योगदान देता है।"
- ch 7 p14: "सारांश section (points 1, 2, 3 and their equations)"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 2 p2 §2.2 ¶4: printed "x(t_1) = 0.08 t^3_1" · transcribed "x(t_1) = 0.08 t^3_1"
- ch 2 p2 §2.2 ¶4: printed "t_2(= t - Delta t/2)" · transcribed "t_2(= t - Delta t/2)"
- ch 2 p2 §2.2 ¶4: printed "x(t_2) = 0.03 t^3_2" · transcribed "x(t_2) = 0.03 t^3_2"
- ch 2 p3 §2.3 ¶3: printed "बिंदु (v_2, t_2) को बिंदु (v_1, t_1)" · transcribed "बिंदु (v_2, t_2) को बिंदु (v_1, t_1)"
- ch 2 p4 §2.3 ¶6: printed "a = a_bar = (v - v_0) / (t - 0) होगा" · transcribed "a = a_bar = (v - v_0) / (t - 0) होगा"
- ch 2 p4 §2.3 ¶6: printed "v= v_0+at" · transcribed "v = v_0 + at"
- ch 2 p5 §2.4 ¶1: printed "= (1/2) (v–v_0) t + v_0 t" · transcribed "= (1/2) (v–v_0) t + v_0 t"
- ch 3 p4 §3.4 ¶4: printed "O सदिश के मुख्य गुण" · transcribed "O सदिश के मुख्य गुण"
- ch 3 p9 §3.7.1 ¶4: printed "v_bar = v_bar_x i_hat + v_bar_y j_hat" · transcribed "v_bar = v_bar_x i_hat + v_bar_y j_hat"
- ch 3 p11 §3.7.1 ¶15: printed "r = 3.0 t i_hat + 2.0 t^2 j_hat + 5.0 k_hat है" · transcribed "r = 3.0 t i_hat + 2.0 t^2 j_hat + 5.0 k_hat है"
- ch 4 p4 §4.4 ¶9: printed "100 m s^-2" · transcribed "100 m s^-2"
- ch 4 p7 §4.5 ¶20: printed "-6750 m s^-2" · transcribed "-6750 m s^-2"
- ch 4 p7 §4.5 ¶21: printed "0.04 kg × 6750 m s^-2" · transcribed "0.04 kg × 6750 m s^-2"
- ch 4 p15 §4.10 ¶9: printed "f ≤ mu_s N = mv^2 / R" · transcribed "f ≤ mu_s N = mv^2 / R"
- ch 5 p3 §5.2 ¶1: printed "v^2 – u^2 = 2 a.d यहाँ a और b" · transcribed "v^2 – u^2 = 2 a.d यहाँ a और b"
- ch 5 p5 §5.4 ¶5: printed "68%" · transcribed "68%"
- ch 5 p6 §5.5 ¶7: printed "W_F -> (आयत ABCD + समलंब CEID)" · transcribed "W_F -> (आयत ABCD + समलंब CEID)"
- ch 5 p9 §5.8 ¶8: printed "E_0 = (1/2) mv_f^2" · transcribed "E_0 = (1/2) mv_f^2"
- ch 6 p7 §6.2 ¶29: printed "अतः यह द्रव्यमान केन्द्र तीनों माध्यिकाओं का संगामी बिन्दु गति त्रिभुज का केन्द्रक G है।" · transcribed "अतः यह द्रव्यमान केन्द्र तीनों माध्यिकाओं का संगामी बिन्दु गति त्रिभुज का केन्द्रक G है।"
- ch 6 p12 §6.5 ¶19: printed "स्पष्टतः" · transcribed "स्पष्टतः"
- ch 6 p17 §6.7.2 ¶15: printed "tau_int = 0" · transcribed "tau_int = 0"
- ch 6 p19 §6.8 ¶14: printed "sum F ≠ 0" · transcribed "sum F ≠ 0"
- ch 6 p21 §6.8.2 ¶3: printed "tau_i = r_i × m_ig" · transcribed "tau_i = r_i × m_ig"
- ch 6 p21 §6.8.2 ¶3: printed "r_i" · transcribed "r_i"
- ch 6 p23 §6.8.2 ¶16: printed "घर्षणविहीन दीवार" · transcribed "घर्षणविहीन दीवार"
- ch 6 p23 §6.8.2 ¶21: printed "≈ 80°" · transcribed "≈ 80°"
- ch 6 p24 §6.9 ¶10: printed "I = Ml^2 / 4" · transcribed "I = Ml^2 / 4"
- ch 6 p28 §6.11 ¶6: printed "F_1(r_1 d theta)sin alpha_1" · transcribed "F_1(r_1 d theta) sin alpha_1"
- ch 6 p30 §6.12 ¶2: printed "L = sum(i=1 to N) r_i × p_i" · transcribed "L = sum(i=1 to N) r_i × p_i"
- ch 6 p30 §6.12 ¶9: printed "L_z sum l_iz = (sum_i m_i r_i^2) omega k_hat" · transcribed "L_z sum l_iz = (sum_i m_i r_i^2) omega k_hat"
- ch 6 p32 §6.12.1 ¶4: printed "पश्चिमी शास्त्रीय नृतक" · transcribed "पश्चिमी शास्त्रीय नृतक"
- ch 7 p1 §7.1 ¶1: printed "गैलीलियो" · transcribed "गैलीलियो"
- ch 7 p3 §7.2 ¶10: printed "L_A = m_p r_A v_A" · transcribed "L_A = m_p r_A v_A."
- ch 7 p5 §7.3 ¶14: printed "F^1_GB = F_GB and F^1_GC = F_GC" · transcribed "F^1_GB = F_GB and F^1_GC = F_GC"
- ch 7 p5 §7.3 ¶14: printed "F'_R = F^1_GA + F^1_GB + F^1_GC = 2.Gm^2 j_hat" · transcribed "F'_R = F^1_GA + F^1_GB + F^1_GC = 2.Gm^2 j_hat"
- ch 7 p6 §7.4 ¶2: printed "theta के अनुक्रमानुपाती तथा tau theta के बराबर हुआ" · transcribed "theta के अनुक्रमानुपाती तथा tau theta के बराबर हुआ"
- ch 7 p11 §7.8 ¶13: printed "R – 4 G M m / 5 R" · transcribed "R – 4 G M m / 5 R"
- ch 7 p13 §7.9 ¶13: printed "6.67 × 10^-11" · transcribed "6.67 × 10^(-11)"
- ch 7 p13 §7.10 ¶2: printed "पृथ्वी के केन्द्र से (R_E+h) दूरी" · transcribed "पृथ्वी के केन्द्र से (R_E+h) दूरी"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 1 p2: "सारणी 1.1 SI मूल राशियाँ एवं उनके मात्रक (table content)"
- ch 1 p3: "सारणी 1.2 सामान्य प्रयोग के लिए SI मात्रकों के अतिरिक्त कुछ अन्य मात्रक"
- ch 1 p6: "1.3.3 अंकगणितीय परिकलनों के परिणामों में अनिश्चितता निर्धारित करने के नियम"
- ch 1 p7: "1.6 विमीय विश्लेषण एवं इसके अनुप्रयोग"
- ch 1 p8: "1.6.1 समीकरणों की विमीय संगति की जाँच"
- ch 1 p9: "1.6.2 विभिन्न भौतिक राशियों के मध्य संबंध व्युत्पन्न करना"
- ch 2 p7: "चित्र 2.6"
- ch 3 p2: "3.2.1 स्थिति एवं विस्थापन सदिश"
- ch 3 p2: "3.2.2 सदिशों की समता"
- ch 3 p3: "3.3 सदिशों की वास्तविक संख्या से गुणा (heading, not omitted - section heading)"
- ch 3 p6: "चित्र 3.9 (a) एकांक सदिश"
- ch 3 p7: "3.6 सदिशों का योग : विश्लेषणात्मक विधि"
- ch 3 p9: "चित्र 3.12 (a) स्थिति सदिश r, (b) विस्थापन Delta r तथा कण का औसत वेग v_bar"
- ch 3 p15: "3.10 एकसमान वृत्तीय गति (section heading, not omitted as it's a heading)"
- ch 4 p5: "4.5 न्यूटन का गति का द्वितीय नियम"
- ch 4 p5: "चित्र 4.3 caption"
- ch 4 p7: "चित्र 4.5 किसी क्षण पर त्वरण का निर्धारण उसी क्षण के बल द्वारा किया जाता है।"
- ch 4 p8: "4.6 न्यूटन का गति का तृतीय नियम"
- ch 4 p9: "4.7 संवेग-संरक्षण"
- ch 4 p12: "4.9.1 घर्षण (section heading, not omitted - excluded as heading)"
- ch 4 p15: "4.10 वर्तुल ( वृतीय ) गति heading section"
- ch 4 p17: "4.11 यांत्रिकी में समस्याओं को हल करना"
- ch 5 p5: "5.5 परिवर्ती बल द्वारा किया गया कार्य"
- ch 5 p7: "5.7 स्थितिज ऊर्जा की अभिधारणा"
- ch 5 p8: "5.8 यांत्रिक ऊर्जा का संरक्षण"
- ch 5 p10: "5.9 किसी स्प्रिंग की स्थितिज ऊर्जा"
- ch 5 p11: "चित्र 5.8 किसी स्प्रिंग से जुड़े हुए गुटके की स्थितिज ऊर्जा V और"
- ch 6 p3: "चित्र 6.4 z-अक्ष के परित: एक दृढ़ पिण्ड का घूर्णन। पिण्ड का प्रत्येक बिन्दु P_1 या P_2 एक वृत्त पर घूमता है जिसका केन्द्र (C_1 या C_2) अक्ष पर स्थित है। वृत्त की त्रिज्या (r_1 या r_2) अक्ष से बिन्दु (P_1 या P_2) की लम्बवत् दूरी है। अक्ष पर स्थित P_3 जैसा बिन्दु स्थिर रहता है।"
- ch 6 p3: "चित्र 6.5 (a) घूमता हुआ लट्टू (इसकी टिप O का धरातल पर सम्पर्क बिन्दु स्थिर है)"
- ch 6 p3: "चित्र 6.5 (b) दोलन करता हुआ मेज का पंखा जिसकी पंखुड़ियाँ घूर्णन गति में हैं। (पंखे की धुरी, बिन्दु O, स्थिर है)"
- ch 6 p4: "चित्र 6.6 (a) एवं 6.6 (b) एक ही पिण्ड की विभिन्न गतियाँ दर्शाती हैं। ध्यान दें, कि P पिण्ड का कोई स्वेच्छ बिन्दु है; O पिण्ड का द्रव्यमान केन्द्र है, जिसके विषय में अगले खण्ड में बताया गया है।"
- ch 6 p8: "6.3 द्रव्यमान केन्द्र की गति"
- ch 6 p9: "6.4 कणों के निकाय का रेखीय संवेग"
- ch 6 p12: "चित्र 6.16 एक स्थिर अक्ष के परित: घूर्णन।"
- ch 6 p14: "6.7.1 एक कण पर आरोपित बल का आघूर्ण"
- ch 6 p15: "चित्र 6.18 caption: tau = r × F, tau उस तल के लम्बवत् है..."
- ch 6 p18: "6.8 दृढ़ पिण्डों का संतुलन"
- ch 6 p20: "6.8.1 आघूर्णों का सिद्धांत"
- ch 6 p22: "चित्र 6.25 अनियमित आकार के फलक का गुरुत्व केन्द्र ज्ञात करना। फलक का गुरुत्व केन्द्र G इसको A कोने से लटकाने पर इससे होकर गुजरने वाली ऊर्ध्वाधर रेखा पर पड़ता है।"
- ch 6 p26: "6.10 अचल अक्ष के परितः शुद्ध घूर्णी गतिकी"
- ch 6 p28: "चित्र 6.30 एक अचल अक्ष के परित: घूमते पिण्ड के किसी कण पर लगे बल F_1 द्वारा किया गया कार्य"
- ch 6 p28: "सारणी 6.2 स्थानांतरीय एवं घूर्णी गति की तुलना"
- ch 6 p30: "6.12 अचल अक्ष के परित: घूर्णी गति का कोणीय संवेग"
- ch 6 p31: "6.12.1 कोणीय संवेग का संरक्षण"
- ch 7 p3: "सारणी 7.1 table (planet data rows)"
- ch 7 p4: "चित्र 7.3 m_2 के कारण m_1 पर गुरुत्वीय बल r के अनुदिश है, यहाँ r, (r_2 − r_1) है।"
- ch 7 p6: "चित्र 7.6 कैवेन्डिश प्रयोग का योजनावत आरेखन"
- ch 7 p7: "7.6 पृथ्वी के पृष्ठ के नीचे तथा ऊपर गुरुत्वीय त्वरण"
- ch 7 p10: "चित्र 7.9 (figure caption/diagram)"
- ch 7 p11: "7.9 भू उपग्रह (heading, not omitted - excluded as section heading)"
flags set aside by the founder's rulings in ncert-corrections.yaml: 19
verdicts recorded on rows: 909

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 84 | 84 | 84 | 0 | 0 | 0 | 0 | 100.0% |
| 2 | 58 | 58 | 58 | 0 | 0 | 0 | 0 | 100.0% |
| 3 | 103 | 103 | 103 | 0 | 0 | 0 | 0 | 100.0% |
| 4 | 142 | 142 | 142 | 0 | 0 | 0 | 0 | 100.0% |
| 5 | 128 | 128 | 128 | 0 | 0 | 0 | 0 | 100.0% |
| 6 | 284 | 284 | 284 | 0 | 0 | 0 | 0 | 100.0% |
| 7 | 110 | 110 | 110 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): 909 of 909 paragraphs, 100.0%
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 7 passages no row carries

# margai-pipeline ncert load

- run: 2026-10-12 08:22 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
extraction: extract/phy11-part2/hi.jsonl sha256 51ab71808bf9184f6d396cde9841deb35f9ace464d5808336d5799347fece79f
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 6fc680f4b087db9ae45bc98f51ccf01ee65add24c2333be0e190460afd7793dd

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## a visarga the model wrote as a colon (deterministic, each one named)

- ch 8 p9 §8.5.6: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p3 §9.2: a visarga written as a colon — अतः
- ch 9 p3 §9.2.1: a visarga written as a colon — अतः, क्रमशः, क्रमशः
- ch 9 p3 §9.2.1: a visarga written as a colon — अतः, पुनः
- ch 9 p8 §9.3: a visarga written as a colon — सामान्यतः
- ch 9 p8 §9.3: a visarga written as a colon — अतः
- ch 9 p10 §9.4: a visarga written as a colon — अतः
- ch 9 p10 §9.4.1: a visarga written as a colon — सन्निकटतः
- ch 9 p11 §9.4.2: a visarga written as a colon — अतः
- ch 9 p11 §9.4.2: a visarga written as a colon — परितः
- ch 9 p16 §9.6.2: a visarga written as a colon — अतः, अतः, अतः
- ch 9 p18 §9.6.4: a visarga written as a colon — अतः, अतः
- ch 10 p3 §10.4: a visarga written as a colon — अतः
- ch 10 p4 §10.5: a visarga written as a colon — पूर्णतः
- ch 10 p4 §10.5: a visarga written as a colon — सामान्यतः
- ch 11 p4 §11.4: a visarga written as a colon — अतः
- ch 11 p11 §11.9: a visarga written as a colon — स्वतः, स्वतः
- ch 11 p11 §11.10: a visarga written as a colon — स्वतः, स्वतः
- ch 11 p11 §11.10: a visarga written as a colon — मुख्यतः
- ch 12 p4 §12.3: a visarga written as a colon — पूर्णतः, अतः
- ch 13 p4 §13.2.2: a visarga written as a colon — अतः
- ch 13 p4 §13.2.2: a visarga written as a colon — अतः
- ch 13 p4 §13.2.2: a visarga written as a colon — अतः, अतः
- ch 13 p9 §13.6: a visarga written as a colon — अतः, अतः
- ch 13 p10 §13.6: a visarga written as a colon — अतः
- ch 13 p10 §13.7: a visarga written as a colon — अतः, अतः
- ch 13 p10 §13.7: a visarga written as a colon — अतः, अतः
- ch 13 p11 §13.7: a visarga written as a colon — अतः
- ch 13 p11 §13.7: a visarga written as a colon — अतः, अतः, अतः
- ch 13 p12 §13.7: a visarga written as a colon — अतः, अतः
- ch 13 p12 §13.8.1: a visarga written as a colon — अतः
- ch 14 p5 §14.3: a visarga written as a colon — अतः, अतः, अतः
- ch 14 p6 §14.3.1: a visarga written as a colon — स्पष्टतः, अतः, अतः
- ch 14 p6 §14.3.2: a visarga written as a colon — सामान्यतः
- ch 14 p6 §14.3.3: a visarga written as a colon — अतः
- ch 14 p6 §14.3.3: a visarga written as a colon — अतः
- ch 14 p7 §14.4: a visarga written as a colon — अतः, अतः
- ch 14 p13 §14.6: a visarga written as a colon — स्पष्टतः
- ch 14 p13 §14.6.1: a visarga written as a colon — अतः, अतः

## a word glued to a colon that the visarga list does not know — adjudicate

left as written: a `text` entry if the page means a visarga, nothing if it is a colon
- ch 9 p8 §9.3: विलोमत:
- ch 10 p9 §10.8: विलोमत:
- ch 12 p2 §12.2: अनुसार:
- ch 12 p9 §12.4.2: संकेत:
- ch 13 p4 §13.2.2: दिष्टत:
- ch 13 p4 §13.2.2: एकदिष्टत:
- ch 13 p12 §13.8: निरपेक्षत:
- ch 14 p5 §14.3: पारित:
- ch 14 p8 §14.4.1: हल:
- ch 14 p16 §14.6.1: हल:

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

- ch 8 page 8 §8.5.6: the English numbers it §8.5.5, from "जब किसी तार पर तनन प्रतिबल" on — 2 paragraph(s) (the Hindi prints 8.5.6 after 8.5.4; the English numbers the section 8.5.5)
- ch 8 page 2 §8.2: a new paragraph starts at "लगाए गए स्पर्शी बल के परिणामस्वरूप" (English ¶6 (the relative displacement and shearing strain) begins here; the Hindi print keeps it in ¶5's paragraph)
- ch 8 page 3 §8.2: "जहाँ theta ऊर्ध्वाधर (बेलन की प्रारंभिक स्थिति)" starts a new paragraph, not the previous page's (English ¶7, the 'where theta' sentence after Eq. (8.3), begins here at the top of p3; the row ran on from p2)
- ch 8 page 3 §8.2: a new paragraph starts at "यदि किसी पुस्तक को हाथ से दबाकर" (English ¶8, the book pressed by hand, begins here)
- ch 8 page 3 §8.2: a new paragraph starts at "इस प्रकार, अपरूपण विकृति = tan theta" (English ¶9, Eq. (8.4), begins here)
- ch 8 page 3 §8.2: a new paragraph starts at "पिण्ड के अंदर आंतरिक प्रत्यानयन बल उत्पन्न" (English ¶11, the internal restoring forces and hydraulic stress, begins here; the Hindi print keeps it in ¶10's paragraph)
- ch 8 page 6 §8.5.1: "दिया है, L_c = 2.2 m" continues the previous page's paragraph (the given values and the working at the top of p6 are the end of English ¶7, Example 8.2's answer, which the row on p5 begins)
- ch 8 page 6 §8.5.1: a new paragraph starts at "आधार देने वाले निष्पादक का द्रव्यमान = 60 kg" (English ¶10, the performer's mass of 60 kg, begins here; the Hindi row ran Example 8.3's answer as one paragraph)
- ch 8 page 6 §8.5.1: a new paragraph starts at "पिरैमिड की तली के निष्पादक के पैरों पर" (English ¶11, the mass supported by the legs (280 – 60 = 220 kg), begins here)
- ch 8 page 6 §8.5.1: a new paragraph starts at "इस आधारित द्रव्यमान का भार" (English ¶12, the weight of the supported mass (2156 N), begins here)
- ch 8 page 6 §8.5.1: a new paragraph starts at "निष्पादक की प्रत्येक उर्वस्थि पर आधारित भार" (English ¶13, the weight on each thighbone (1078 N), begins here)
- ch 8 page 6 §8.5.1: a new paragraph starts at "सारणी 8.1 से अस्थि का यंग गुणांक" (English ¶14, Young's modulus for bone from Table 8.1, begins here)
- ch 8 page 6 §8.5.1: a new paragraph starts at "प्रत्येक उर्वस्थि की लंबाई L = 0.5 m" (English ¶15, the length of each thighbone L = 0.5 m, begins here)
- ch 8 page 6 §8.5.1: a new paragraph starts at "तथा त्रिज्या = 2.0 cm" (English ¶16, the thighbone's radius of 2.0 cm, begins here; the Hindi prints it on the length's line after 'तथा', so the split falls at that clause, not at a sentence start)
- ch 8 page 6 §8.5.1: a new paragraph starts at "इस प्रकार उर्वस्थि के अनुप्रस्थ काट" (English ¶17, the cross-sectional area and the compression ΔL by Eq. (8.8), begins here)
- ch 8 page 6 §8.5.1: a new paragraph starts at "यह बहुत कम अंतर है।" (English ¶18, 'This is a very small change!' and the fractional decrease, begins here)
- ch 8 page 7 §8.5.2: a new paragraph starts at "हम जानते हैं कि अपरूपण विकृति" (English ¶5, the shearing strain and the displacement Δx, begins here at the top of p7's right column; the Hindi row ran Example 8.4's answer as one paragraph)
- ch 8 page 7 §8.5.3: a new paragraph starts at "इस प्रकार ठोस पदार्थ सबसे कम संपीड्य" (English ¶5, solids the least and gases the most compressible, begins here; the Hindi print keeps it in ¶4's paragraph)
- ch 8 page 8 §8.5.3: "भिन्नात्मक संपीडन ΔV/V होगा" joined onto the paragraph before it (the fractional compression ΔV/V is the end of English ¶8, Example 8.5's answer, which the row before it begins)
- ch 8 page 9 §8.5.5: a new paragraph starts at "यह कार्य तार में प्रत्यास्थ स्थैतिक ऊर्जा (U)" (English ¶2, the energy stored as elastic potential energy U and Eq. (8.14), begins here, after the displayed work that ends ¶1)
- ch 8 page 2 §8.2: "को अनुदैर्ध्य प्रतिबल" → "को अनुदैर्घ्य प्रतिबल" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p2 x328-557 y172-188 at zoom 5 and x340-400 at zoom 10; the decoded layer's line y173 agrees)
- ch 8 page 2 §8.2: "अनुदैर्ध्य विकृति कहते हैं। अनुदैर्ध्य विकृति" → "अनुदैर्घ्य विकृति कहते हैं। अनुदैर्घ्य विकृति" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Both places: crop p2 x328-557 y219-235 and x340-410 y247-263, zoom 5; the decoded layer's lines y220 and y248 agree)
- ch 8 page 8 §8.5.4: "पार्श्विक विकृति अनुदैर्ध्य विकृति के" → "पार्श्विक विकृति अनुदैर्घ्य विकृति के" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p8 x328-557 y215-231, zoom 5; the decoded layer's line y216 agrees)
- ch 8 page 8 §8.5.4: "विकृति तथा अनुदैर्ध्य विकृति का" → "विकृति तथा अनुदैर्घ्य विकृति का" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p8 x328-557 y244-260, zoom 5; the decoded layer's line y246 agrees)
- ch 8 page 8 §8.5.4: "है, तो अनुदैर्ध्य विकृति" → "है, तो अनुदैर्घ्य विकृति" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p8 x328-557 y303-319, zoom 5; the decoded layer's line y305 agrees)
- ch 8 page 6 §8.5.1: "220 × 8.8 N = 2156 N" → "220 × 9.8 N = 2156 N" (the page prints = 220 kg wt = 220 × 8.8 N = 2156 N (crop p6 x325-560 y175-195 at zoom 4; the decoded layer's line y181 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the arithmetic, 220 × 9.8 = 2156 where 220 × 8.8 is 1936, and the English row's 220 × 9.8 N = 2156 N)
- ch 8 page 6 §8.5.1: "= – (2156) N" → "= (1/2) (2156) N" (the page sets an en dash where ½ belongs, = – (2156) N = 1078 N (crop p6 x325-560 y210-230 at zoom 4; the decoded layer's line y215 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: each thighbone carries half the load, 2156/2 = 1078, and the English row has ½ (2156) N = 1078 N)
- ch 8 page 6 §8.5.1: "Y = 8.4 × 10^9" → "Y = 9.4 × 10^9" (the page prints bone's Y = 8.4 × 10^9 N m^-2 (crop p6 x325-560 y245-262 at zoom 4; the decoded layer's line y249 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: this edition's Table 8.1 gives bone 9.4 (crop p5 x40-400 y75-260 at zoom 3), the English row has 9.4 × 10^9, and only 9.4 gives the page's result, 1078 × 0.5/(9.4 × 10^9 × 1.26 × 10^-3) = 4.55 × 10^-5 m, where 8.4 gives 5.1 × 10^-5 m)
- ch 8 page 6 §8.5.1: "/(8.4 × 10^9 × 1.26" → "/(9.4 × 10^9 × 1.26" (the page prints [(1078 × 0.5)/(8.4 × 10^9 × 1.26 × 10^-3)] = 4.55 × 10^-5 m (crop p6 x325-560 y360-395 at zoom 4; the decoded layer's line y365 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: bone's 9.4 × 10^9 N m^-2 from this edition's Table 8.1 (crop p5 x40-400 y75-260) and the English row, the only value that gives the 4.55 × 10^-5 m the page prints)
- ch 8 page 7 §8.5.2: "8.0 × 10^4 N का" → "9.0 × 10^4 N का" (the page prints a shearing force of 8.0 × 10^4 N in Example 8.4 (crop p7 x50-280 y295-350 at zoom 4; the decoded layer's line y334 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row's 9.0 × 10^4 N, the force the solution's stress needs, 1.80 × 10^6 N m^-2 × 0.05 m^2 = 9.0 × 10^4 N, where 8.0 would give 1.60 × 10^6 N m^-2)
- ch 8 page 7 §8.5.2: "प्रतिबल = (8.4 × 10^4 N/0.05 m^2)" → "प्रतिबल = (9.0 × 10^4 N/0.05 m^2)" (the page prints the stress as (8.4 × 10^4 N/0.05 m^2) = 1.80 × 10^6 N m^-2 (crop p7 x55-280 y470-522 at zoom 4; the decoded layer's line y492 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning from the arithmetic: 1.80 × 10^6 N m^-2 over 0.05 m^2 needs 9.0 × 10^4 N (8.4 gives 1.68 × 10^6), the force of the statement as the English row prints it; the English row's 9.4 at this place is its own slip, so the arithmetic fixes the value)
- ch 9 page 2 §9.2: a new paragraph starts at "यदि तरल द्वारा A क्षेत्रफल के पिस्टन पर" (English ¶4 (the average pressure, Eq. 9.1) begins here; the Hindi print runs it on at the end of ¶3's paragraph)
- ch 9 page 3 §9.2.1: the row starting "* STP का अर्थ मानक ताप" is numbered after the row starting "4 °C (277 K) पर जल का घनत्व" (page 2) (the STP footnote, printed at the foot of p3 under the §9.2.1 heading (layer y692), is English §9.2 ¶10 and goes back after ¶9, the density of water, before Example 9.1)
- ch 9 page 4 §9.2.1: "तरल के एक अवयव की कल्पना करो" starts a new paragraph, not the previous page's (English ¶4 (the horizontal bar) begins here, an indented paragraph at the top of p4; it does not continue p3's ¶3)
- ch 9 page 4 §9.2.2: a new paragraph starts at "अब यदि rho तरल का घनत्व है तो" (English ¶2 (m = rho hA and Eq. 9.6) begins here, after Eq. (9.5))
- ch 9 page 5 §9.2.2: "यह दाब खुले पृष्ठ के दाब की तुलना में" starts a new paragraph, not the previous page's (English ¶8 (the 100% increase) begins here, at the top of p5; it does not continue Example 9.2's answer)
- ch 9 page 6 §9.2.3: "U नली में भरे द्रव के तलों में" starts a new paragraph, not the previous page's (English ¶5 (pressure equal on both sides of the U-tube) begins here, at the top of p6; it does not continue the manometer paragraph)
- ch 9 page 7 §9.2.4: "नोट : दोनों पिस्टनों के लिए" joined onto the paragraph before it (the note on atmospheric pressure is the last sentence of English ¶6, answer (b) of Example 9.5)
- ch 9 page 7 §9.2.4: a new paragraph starts at "इस बल के संगत वायु दाब P" (English ¶9 (the air pressure P = F_1/A_1) begins here, on a new line after the displayed F_1 (layer y466); the Hindi row runs it on in Example 9.6's answer)
- ch 9 page 9 §9.4: a new paragraph starts at "गतिज ऊर्जा में परिवर्तन Delta K" (English ¶5 (the change in kinetic energy) begins here, after the displayed Delta U)
- ch 9 page 9 §9.4: "यह बर्नूली समीकरण है।" joined onto the paragraph before it (the sentence naming Bernoulli's equation and Eq. (9.13) are the end of English ¶8, which the row before it (Eq. 9.12) begins)
- ch 9 page 11 §9.4.1: a new paragraph starts at "y_2 − y_1 = h लेने पर" (English ¶3 (taking y_2 – y_1 = h, Eq. 9.14) begins here, after the displayed Bernoulli equation that ends ¶2)
- ch 9 page 12 §9.4.2: a new paragraph starts at "औसत चाल v_av = (v_2 + v_1)/2" (English ¶9 (the average speed and the 0.08 ratio) begins here, an indented line on p12 (layer y433); the transcription ran it on in answer (b))
- ch 9 page 14 §9.5.1: "जहाँ rho तथा sigma बूँद तथा तरल" joined onto the paragraph before it (the densities rho and sigma and Eq. (9.18) are the end of English ¶3; the Hindi row was cut at the display)
- ch 9 page 14 §9.5.1: a new paragraph starts at "इस संदर्भ में आप उदाहरण 5.2" (English ¶5 (refer back to the earlier example) begins here, an indented line at the top of p14's right column (layer y81); the transcription ran it on in ¶4's paragraph)
- ch 9 page 18 §9.6.4: a new paragraph starts at "जैसा कि हम बार-बार चर्चा कर रहे हैं" (English ¶2 (the sphere has the least area) begins here; the Hindi print keeps it in ¶1's paragraph)
- ch 9 page 19 §9.6.5: "अनुप्रस्थ काट (त्रिज्या a) की ऊर्ध्वाधर केशनली" continues the previous page's paragraph (p19 opens mid-sentence, continuing p18's last paragraph of English ¶1 (the capillary tube, Eq. 9.27))
- ch 9 page 19 §9.6.5: a new paragraph starts at "अतः बुलबुले के भीतर दाब P_i" (English ¶7 (the pressure inside the bubble) begins here, an indented line on p19 (layer y559); the transcription ran it on in Example 9.10's answer)
- ch 9 page 4 §9.2.2: "m = rhoV = rhohA होगी, इसलिए P_2 – P_1 = rhogh (9.6)" → "m = rho V = rho hA होगी, इसलिए P_2 – P_1 = rho g h (9.6)" (Notation: the Greek name runs into the symbols after it (rhoV, rhohA, rhogh); spaced as the notation requires (the English row has rho V = rho hA, and rho g h elsewhere). Crop p4 x100-311 y396-428 at zoom 6 shows m = rho V = rho hA and P_2 - P_1 = rho g h (9.6); the decoded layer's lines y398 and y416 agree)
- ch 9 page 4 §9.2.2: "P = P_a + rhogh (9.7)" → "P = P_a + rho g h (9.7)" (Notation: rhogh spaced as rho g h. Crop p4 x328-557 y172-215 at zoom 6 shows P = P_a + rho g h (9.7); the decoded layer's line y174 agrees)
- ch 9 page 4 §9.2.2: "अपेक्षा rhogh परिमाण" → "अपेक्षा rho g h परिमाण" (Notation: rhogh spaced as rho g h. Crop p4 x328-557 y172-215 at zoom 6 (line y201); the decoded layer's line y201 agrees)
- ch 9 page 4 §9.2.2: "P = P_a + rhogh =" → "P = P_a + rho g h =" (Notation: rhogh spaced as rho g h. Crop p4 x328-557 y656-670 at zoom 6; the decoded layer's line y658 agrees)
- ch 9 page 4 §9.2.2: "यदि विचाराणीय बिंदु" → "यदि विचारणीय बिंदु" (The page prints the word without the aa-matra after va-cha-ra (vi-cha-ra-nee-ya); the row adds one. Crop p4 x328-450 y110-126 at zoom 10; the decoded layer's line y111 agrees)
- ch 9 page 4 §9.2.2: "भलीभाँति" → "भलीभांति" (The page prints an anusvara dot over bha-aa, not a chandrabindu; as printed. Crop p4 x430-557 y324-340 at zoom 10; the decoded layer's line y326 agrees)
- ch 9 page 8 §9.3: "प्रवाह प्रक्षुब्द्ध प्रवाह" → "प्रवाह प्रक्षुद्ध प्रवाह" (The page prints the stacked da-dha conjunct after ksh-u, with no half ba (p9 prints the same word with a half ba then dha, crop p9 x46-275 y110-125); the row writes neither; as printed. Crop p8 x326-375 y611-629 at zoom 20; the decoded layer's line y613 agrees)
- ch 9 page 8 §9.3: "इसका विलोमत:" → "इसका विलोमतः" (Visarga: an adverb in -tah (conversely), printed with the visarga glyph that this font shares with the colon. Crop p8 x328-557 y518-534 at zoom 6; the decoded layer's line y520 agrees)
- ch 9 page 18 §9.6.4: "उत्तल पार्श्व की ओर दाब का मान अवतल पार्श्व" → "अवतल पार्श्व की ओर दाब का मान उत्तल पार्श्व" (the page prints उत्तल first and अवतल second, making the convex side's pressure the greater (crop p18 x325-560 y186-268 at zoom 4; the decoded layer's lines y190 and y207 agree); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row says the concave side (inside) has the higher pressure than the convex side (outside), as (9.25) and the air bubble the page cites next require)
- ch 10 page 4 §10.5: a new paragraph starts at "यहाँ भी alpha_V पदार्थ का अभिलक्षण है" (English ¶5 begins here, after Eq. (10.5); the Hindi print keeps it in ¶4's paragraph)
- ch 10 page 7 §10.6: a new paragraph starts at "यहाँ Delta Q पदार्थ के ताप में T से" (English ¶5, the 'where ∆Q' line after Eq. (10.10), begins here; the Hindi print keeps it in ¶4's paragraph)
- ch 10 page 7 §10.6: a new paragraph starts at "जहाँ C मोलर विशिष्ट ऊष्मा धारिता कहलाती है" (English ¶10, the 'where C' line after Eq. (10.12), begins here; the Hindi print keeps it in ¶9's paragraph)
- ch 10 page 9 §10.7: "ऐलुमिनियम के गोले का द्रव्यमान (m_1)" joined onto the paragraph before it (the given values and the working are the rest of English ¶4, the Answer to Example 10.3, which the row before it begins)
- ch 10 page 11 §10.8: "किसी पदार्थ का ताप उसकी अवस्था परिवर्तन" → "त्रिक बिंदु किसी पदार्थ का ताप उसकी अवस्था परिवर्तन" (the tinted box prints its title त्रिक बिंदु above this paragraph (crop p11 x55-330 y80-128 at zoom 4; the decoded layer's line y86 agrees) and the transcription left it out; a heading leads the paragraph printed under it (DECISIONS 2026-09-27), as the English row's Triple Point does)
- ch 10 page 11 §10.8: the row starting "त्रिक बिंदु किसी पदार्थ का ताप उसकी अवस्था परिवर्तन (प्रावस्था परिवर्तन)" is numbered after the row starting "क्रियाकलाप 10.3 आधे से अधिक जल से भरा" (page 10) (the Triple Point box, which the Hindi prints at the top of p11 after the burner paragraph, is numbered after Activity 10.3 to pair with English ¶8 (box plus the rest of the activity); the Hindi activity cannot sit both before and after the box, so it stays whole in ¶7, the nearest boundary)
- ch 10 page 16 §10.9.1: a new paragraph starts at "दिया गया है, L_1 = L_2 = L = 0.1m" (English ¶10, the given values, begins here; English ¶9 is the word Answer alone, so the Hindi हल stays as its own row)
- ch 10 page 16 §10.9.1: a new paragraph starts at "अतः, H = H_1 = H_2" (English ¶12 begins here at 'So, H = H_1 = H_2'; the Hindi row keeps it in ¶11's steady-state sentence)
- ch 10 page 16 §10.9.1: "चूंकि A_1 = A_2 = A तथा L_1 = L_2 = L" joined onto the paragraph before it (the junction temperature T_0 is the continuation of English ¶12, which the row before it begins)
- ch 10 page 16 §10.9.1: "T_0 के इस मान का प्रतिस्थापन" joined onto the paragraph before it (the heat current H through either bar is the continuation of English ¶12)
- ch 10 page 16 §10.9.1: "यदि लंबाई L_1 + L_2 = 2L" joined onto the paragraph before it (the compound bar's H′ and K′ are the end of English ¶12)
- ch 10 page 16 §10.9.1: a new paragraph starts at "इस प्रकार, (i) T_O" (English ¶13, result (i), begins here; the Hindi print runs (i) to (iii) on after the K′ formula)
- ch 10 page 16 §10.9.1: a new paragraph starts at "(ii) K′ = 2K_1 K_2" (English ¶14, results (ii) and (iii), begins here)
- ch 10 page 19 §10.9.4: "किसी आदर्श विकिरक के लिए e = 1 होता है।" joined onto the paragraph before it (English ¶5 runs on to the tungsten lamp's 60 W; the re-read of p19 starts a paragraph here, the first read did not)
- ch 10 page 19 §10.9.4: a new paragraph starts at "उस वस्तु के लिए जिसकी उत्सर्जकता e है" (English ¶7, the body with emissivity e and Eq. (10.18), begins here; the Hindi print keeps it in ¶6's paragraph)
- ch 10 page 20 §10.10: "यहाँ k एक धनात्मक नियतांक है" joined onto the paragraph before it (the definition of k and Eq. (10.20) are the rest of English ¶5, which the row before it begins)
- ch 10 page 21 §10.10: a new paragraph starts at "दोनों समीकरणों को विभाजित करने पर" (English ¶14, dividing the two equations, begins here; the Hindi row keeps it in ¶13's paragraph)
- ch 10 page 19 §10.10: "इसका अध्ययन करने के लिए आइए निम्नलिखित क्रियाकलाप करें।" → "इसका अध्ययन करने के लिए आइए निम्नलिखित क्रियाकलाप करें। क्रियाकलाप 10.4 एक विडोलक सहित ऊष्मामापी में कुछ जल, मान लें 300 mL लीजिए और इसे दो छिद्र वाले ढक्कन से ढक दीजिए। ढक्कन के एक छिद्र में विडोलक तथा दूसरे छिद्र में तापमापी लगाइए तथा यह सुनिश्चित कीजिए कि तापमापी का बल्ब जल में डूब जाए। तापमापी का पाठ्यांक नोट कीजिए। यह पाठ्यांक T_1 परिवेश का ताप है। ऊष्मामापी के जल को इतना गर्म कीजिए कि इसका ताप कक्ष ताप (अर्थात् परिवेश के ताप) से लगभग 40 °C अधिक तक पहुँच जाए। तत्पश्चात् ऊष्मा स्रोत को हटाकर जल को गर्म करना बंद कीजिए। विराम घड़ी चलाइए तथा प्रत्येक नियत समय अंतराल जैसे 1 मिनट के पश्चात् विडोलक से धीरे-धीरे विडोलित करते हुए तापमापी के पाठ्यांक नोट कीजिए। जल का ताप परिवेश के ताप से लगभग 5 °C अधिक रहने तक पाठ्यांक नोट करते रहिए। मान लीजिए यह पाठ्यांक (T_2) है। तत्पश्चात् ताप Delta T = T_2 – T_1 को y-अक्ष के अनुदिश लेकर इसके प्रत्येक मान के लिए तदनुरूपी t के मान को x-अक्ष के अनुदिश लेकर ग्राफ आलेखित करिए (चित्र 10.19)।" (the क्रियाकलाप 10.4 box the page prints in the right column (crop p19 x285-570 y375-665, read at zoom 4), the English §10.10 ¶2; both reads of the page (2026-10-09, 2026-10-11) left it out, so it is typed from the page image by the founder's ruling of 2026-10-11, its title leading as a box's does (2026-09-28); the decoded layer's right-column lines y386-y598 agree word for word)
- ch 10 page 19 §10.10: a new paragraph starts at "क्रियाकलाप 10.4 एक विडोलक सहित" (the Activity 10.4 box is the English §10.10 ¶2, a row of its own after the paragraph that introduces it)
- ch 10 page 9 §10.8: "(तथा विलोमत:)" → "(तथा विलोमतः)" (Visarga: the adverb in -tah (and conversely). Crop p9 x46-275 y628-644 at zoom 6; the decoded layer's line y630 agrees)
- ch 10 page 18 §10.9.3: "हलके वर्णो के" → "हलके वर्णों के" (The page prints the anusvara dot over the o-matra (as on the same word two lines above); the row drops it. Crop p18 x82-311 y180-196 at zoom 8; the decoded layer's line y182 agrees)
- ch 10 page 19 §10.9.4: "स्टेफॉन-बोल्ट्समान नियतांक" → "स्टेफॉन-बोल्टसमान नियतांक" (At this place (the right end of the line) the page prints the name with a full ta, no halant, unlike the two occurrences before it; as printed. Crop p19 x200-276 y214-230 at zoom 14 and x46-276 y198-230 at zoom 5; the decoded layer's line y216 agrees)
- ch 10 page 5 §10.5: "नियत ताप पर P Delta V" → "नियत दाब पर P Delta V" (the page prints नियत ताप पर (at constant temperature) above P∆V = µR∆T (crop p5 x288-525 y280-400 at zoom 4; the decoded layer's line y327 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row says at constant pressure, the condition the sentence before names (किसी नियत दाब पर) and the only one under which PV = µRT gives P∆V = µR∆T; at constant temperature ∆T is zero)
- ch 10 page 7 §10.6: "s = S / mu = (1 / mu)" → "C = S / mu = (1 / mu)" (the page prints (10.12) as s = S/µ = (1/µ) ∆Q/∆T and the next line names the quantity C (crop p7 x290-525 y420-515 at zoom 4; the decoded layer's lines y472 and y499 place them); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has C = S/µ, s being (10.11)'s heat capacity per unit mass, and the page's own next sentence (जहाँ C मोलर विशिष्ट ऊष्मा धारिता कहलाती है) names the molar quantity C)
- ch 10 page 10 §10.8: "ठोस से वाष्प में" → "द्रव से वाष्प में" (the page prints अर्थात्, ठोस से वाष्प में अवस्था परिवर्तन, solid to vapour (crop p10 x78-315 y578-652 at zoom 4; the decoded layer's line y609 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row says during the change of state from liquid to vapour, the vaporisation the paragraph defines, and the same sentence names the liquid and the gas as the states that coexist)
- ch 10 page 16 §10.9.1: "ताँबे की छड़ से" → "पीतल की छड़ से" (the page prints ताँबे की छड़ (the copper bar) for the second bar (crop p16 x78-315 y224-272 at zoom 4; the decoded layer's line y240 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: Example 10.7 joins an iron bar to a brass bar, पीतल की छड़, with K_2 = 109 W m^-1 K^-1, the value this solution uses (crop p15 x300-515 y538-650 at zoom 3.5), and the English row says brass bar)
- ch 10 page 16 §10.9.1: "ऊष्मा चालकता K है" → "ऊष्मा चालकता K′ है" (the page prints the compound bar's equivalent conductivity as K with no prime (crop p16 x78-315 y535-575 at zoom 4; the decoded layer's line y556 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has K′, and the page's own next lines name it K′ (H′ = K′A (T_1 − T_2)/2L, K′ = 2K_1K_2/(K_1 + K_2)); a plain K is no quantity of this example)
- ch 11 page 5 §11.5: a new paragraph starts at "ऊर्जा संरक्षण के सामान्य नियम में यह अंतर्निहित है कि" (English ¶2 (the general principle leading to Eq. (11.1)) begins here; the Hindi print runs the three definitions on into it as one paragraph)
- ch 11 page 6 §11.5: a new paragraph starts at "समीकरण (11.3) के अनुप्रयोग के रूप में हमें" (English ¶6, the application of Eq. (11.3) to 1 g of water, begins here; the Hindi print keeps it in ¶5's paragraph)
- ch 11 page 6 §11.5: a new paragraph starts at "अतः Delta W = P (V_g – V_l)" (English ¶7, the working for Delta W and Delta U, begins here)
- ch 11 page 7 §11.6: "यहाँ अधोलिखित V को अंतिम पद में छोड़ दिया गया है" joined onto the paragraph before it (the remark on the dropped subscript and Eq. (11.10) are the end of English ¶9, which the row before it begins; the Hindi row was cut after Eq. (11.9))
- ch 11 page 8 §11.7: a new paragraph starts at "गैस की निश्चित मात्रा के लिए अर्थात् दिए गए mu के लिए" (English ¶3 (a fixed amount of gas, two independent variables) begins here, after the displayed P V = mu R T that ends ¶2)
- ch 11 page 8 §11.8.1: the paragraph starting "* जैसा पहले भी दर्शाया गया है" takes its number after the next paragraph of its section (the footnote on Delta Q being extensive is English ¶3, numbered after ¶2 (the quasi-static paragraph); the Hindi load put it before that paragraph)
- ch 11 page 9 §11.8.2: a new paragraph starts at "Delta V -> 0 लेते हुए राशि Delta W को संपूर्ण प्रक्रम में" (English ¶3 (taking Delta V -> 0 and summing, Eq. (11.12)) begins here, after the displayed Delta W = P Delta V that ends ¶2)
- ch 11 page 11 §11.9: "ऐसा कोई प्रक्रम संभव नहीं है जिसका एकमात्र परिणाम किसी ऊष्मा भंडार" → "केल्विन-प्लैंक का प्रकथन ऐसा कोई प्रक्रम संभव नहीं है जिसका एकमात्र परिणाम किसी ऊष्मा भंडार" (the page prints the bold side heading केल्विन-प्लैंक का प्रकथन on its own line above the italic Kelvin-Planck statement, which the transcription omitted (crop p11 x55-300 y585-730; the layer agrees, a line at y612.1 in the left column); the founder ruled (2026-10-10) that here the Hindi mirrors the English's heading rows, so the heading is restored and the next entry gives it its own row)
- ch 11 page 11 §11.9: a new paragraph starts at "ऐसा कोई प्रक्रम संभव नहीं है जिसका एकमात्र परिणाम किसी ऊष्मा भंडार" (English ¶4, the Kelvin-Planck statement, begins here; the restored heading before it is English ¶3, a row of its own (founder, 2026-10-10))
- ch 11 page 11 §11.9: "ऐसा कोई भी प्रक्रम संभव नहीं है जिसका एकमात्र परिणाम किसी ठंडे" → "क्लासियस का प्रकथन ऐसा कोई भी प्रक्रम संभव नहीं है जिसका एकमात्र परिणाम किसी ठंडे" (the page prints the bold side heading क्लासियस का प्रकथन on its own line above the italic Clausius statement, which the transcription omitted (crop p11 x55-300 y585-730; the layer agrees, a line at y679.5 in the left column); the founder ruled (2026-10-10) that here the Hindi mirrors the English's heading rows, so the heading is restored and the next entry gives it its own row)
- ch 11 page 11 §11.9: a new paragraph starts at "ऐसा कोई भी प्रक्रम संभव नहीं है जिसका एकमात्र परिणाम किसी ठंडे" (English ¶6, the Clausius statement, begins here; the restored heading before it is English ¶5, a row of its own (founder, 2026-10-10))
- ch 11 page 13 §11.11: a new paragraph starts at "समीकरण (11.16) से गैस पर किया गया कार्य" (English ¶10, the work done on the gas in step 4 -> 1 (Eq. (11.21)), begins here; the Hindi print keeps it with step (d))
- ch 11 page 12 §11.11: "प्रक्रमों के किस चक्र" → "प्रकर्मों के किस चक्र" (the page sets a plain क followed by म carrying a reph and the anusvara, not the क्र conjunct the same page prints in प्रक्रमों eleven lines above (crops p12 x58-200 y488-510 and y337-356 at zoom 7; the decoded layer's line y493 agrees): the book's own spelling, which the transcriber repaired; restored as printed)
- ch 11 page 7 §11.6: "ऊष्मा का पुराना मात्रक कैलोरी था।" → "जल की विशिष्ट ऊष्मा धारिता ऊष्मा का पुराना मात्रक कैलोरी था।" (the page prints the side heading जल की विशिष्ट ऊष्मा धारिता, blue italic, above this paragraph (crop p7 x55-300 y250-350 at zoom 3; the decoded layer's line y317 agrees) and the transcription left it out; a side heading leads the paragraph printed under it (DECISIONS 2026-09-27), even where the English row lacks it)
- ch 11 page 2 §11.1: "पिण्ड की आंतरिक स्थूल" → "पिण्ड की आंतिरक स्थूल" (The page prints the i-matra on ta, not on ra (aan-ti-ra-ka): the book's own misspelling, which the row repaired; as printed. Crop p2 x150-215 y419-437 at zoom 16; the decoded layer's line y421 agrees)
- ch 11 page 6 §11.6: "अतिरिक्त शर्तों की" → "अतिरिक्त शर्तो की" (The page prints reph and o-matra with no anusvara dot; the row added one; as printed. Crop p6 x400-435 y378-397 at zoom 25; the decoded layer's line y382 agrees)
- ch 11 page 8 §11.7: "द्रुत प्रसरण" → "द्रुत्र प्रसरण" (The page prints dru followed by the tra conjunct at the end of the line; the book's misprint, which the row repaired; as printed. Crop p8 x260-295 y241-275 at zoom 12; the decoded layer's line y243 agrees)
- ch 11 page 13 §11.11: "एक ही स्रोत" → "एक ही स्त्रोत" (The page prints sa joined to the tra conjunct (the ta-ra form with its loop), not the sa-ra ligature printed on ch 14 p16 (crop p16 x470-540 y275-291); as printed. Crop p13 x380-420 y666-682 at zoom 14; the decoded layer's line y668 agrees)
- ch 11 page 14 §11.11: "कि I स्रोत से" → "कि I स्त्रोत से" (The page prints sa joined to the tra conjunct (the ta-ra form with its loop), not the sa-ra ligature printed on ch 14 p16 (crop p16 x470-540 y275-291); as printed. Crop p14 x75-105 y87-102 at zoom 14; the decoded layer's line y88 agrees)
- ch 11 page 14 §11.11: "Q_1 स्रोत को" → "Q_1 स्त्रोत को" (The page prints sa joined to the tra conjunct (the ta-ra form with its loop), not the sa-ra ligature printed on ch 14 p16 (crop p16 x470-540 y275-291); as printed. Crop p14 x173-293 y147-162 at zoom 8; the decoded layer's line y148 agrees)
- ch 11 page 14 §11.11: "चक्र में स्रोत या" → "चक्र में स्त्रोत या" (The page prints sa joined to the tra conjunct (the ta-ra form with its loop), not the sa-ra ligature printed on ch 14 p16 (crop p16 x470-540 y275-291); as printed. Crop p14 x61-293 y262-277 at zoom 8; the decoded layer's line y263 agrees)
- ch 11 page 10 §11.8.3: "(11.16) से T_2>T_1" → "(11.16) से T_2<T_1" (the page prints (W > 0), तब समीकरण (11.16) से T_2>T_1 (crop p10 x305-545 y345-412 at zoom 4; the decoded layer's line y365 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: (11.16) gives W = µR(T_1 − T_2)/(γ − 1) with γ > 1, so W > 0 means T_2 < T_1, as the English row has; the next sentence, W < 0 giving T_2 > T_1, is the converse and is printed right)
- ch 12 page 3 §12.3: a new paragraph starts at "जहाँ mu मोलों की संख्या है एवं R = N_A k_B" (English ¶3 (where mu is the number of moles) begins here, after Eq. (12.3); the Hindi print runs English ¶2 to ¶4 as one paragraph)
- ch 12 page 3 §12.3: a new paragraph starts at "जहाँ, M गैस का द्रव्यमान है जिसमें N अणु हैं" (English ¶4 (where M is the mass of the gas) begins here, after Eq. (12.4))
- ch 12 page 5 §12.3: "स्पष्टतः, P_1 = mu_1 R T/V वह दाब है" joined onto the paragraph before it (the partial-pressure sentences after Eq. (12.9) are the end of English ¶9, which the row before it begins; the Hindi row was cut at the display)
- ch 12 page 6 §12.3: "(P_1/P_2) = (3/2) (दिया है)" joined onto the paragraph before it (the given ratio (P_1/P_2) = 3/2 is the end of English ¶19, Example 12.4's answer before (i); the Hindi print sets it as an indented line of its own)
- ch 12 page 7 §12.4.1: a new paragraph starts at "दीवार पर लगा बल संवेग हस्तांतरण की दर" (English ¶3 (the force on the wall, Eq. 12.11) begins here, after Eq. (12.10))
- ch 12 page 7 §12.4.1: a new paragraph starts at "जहाँ v चाल है, और v^2_bar वर्गीकृत चालों" (English ¶6 (where v is the speed, Eq. 12.14) begins here, after Eq. (12.13))
- ch 12 page 7 §12.4.2: the row starting "* संकेत E आंतरिक ऊर्जा U" is numbered after the row starting "(ii) अब (1/2) m v_rms^2" (page 8) (the Hindi prints this footnote at the foot of p7, where only English ¶1 to ¶3 begin; the English numbers it ¶13, right after ¶12, Example 12.5's answer (ii))
- ch 12 page 8 §12.4.2: a new paragraph starts at "v^2_bar का वर्गमूल इसकी वर्ग माध्य मूल (rms) चाल" (English ¶7 (the root mean square speed) begins here; the Hindi print keeps it in ¶6's paragraph)
- ch 12 page 8 §12.4.2: a new paragraph starts at "(i) चूंकि फ्लास्क में आर्गन और क्लोरीन" (English ¶11, part (i) of Example 12.5's answer, begins here; the Hindi print keeps it in the answer's opening paragraph)
- ch 12 page 9 §12.4.2: "^235U वह समस्थानिक है जिसकी आवश्यकता" starts a new paragraph, not the previous page's (English ¶17, the bracketed remark on enriching 235U, begins here at the top of p9; it does not continue Example 12.6's answer on p8)
- ch 12 page 9 §12.4.2: a new paragraph starts at "(b) पिस्टन लगे सिलिंडर में पिस्टन को अंदर" (English ¶20, Example 12.7 (b), begins here; the Hindi print runs (a) to (d) as one paragraph)
- ch 12 page 9 §12.4.2: a new paragraph starts at "(c) पिस्टन लगे सिलिंडर में संपीडित गैस" (English ¶21, Example 12.7 (c), begins here)
- ch 12 page 9 §12.4.2: a new paragraph starts at "(d) खेलते समय सचिन तेंदुलकर" (English ¶22, Example 12.7 (d), begins here)
- ch 12 page 10 §12.5: a new paragraph starts at "यहाँ omega_1 एवं omega_2 क्रमशः अक्षों" (English ¶5 (where omega_1 and omega_2 are the angular speeds) begins here, after Eq. (12.25))
- ch 12 page 10 §12.5: the row starting "* परमाणुओं को मिलाने वाली रेखा" is numbered after the row starting "पुनः ध्यान दीजिए, समीकरण (12.26)" (page 10) (the Hindi prints this footnote at the foot of p10, after English ¶9 and ¶10; the English numbers it ¶8, right after ¶7)
- ch 12 page 8 §12.4.2: "= (3/2) k_B T यहाँ m" → "= (3/2) ) k_B T यहाँ m" (the page prints a stray closing bracket after (3/2) at the start of the line (crop p8 x325-560 y218-256 at zoom 5; the decoded layer's line y242 agrees), which the English row keeps as (3/2) ) k_B T; a garble the book prints is restored where the transcriber repaired it (DECISIONS 2026-09-29))
- ch 12 page 10 §12.5: "उदाहरणार्थ यहाँ" → "उदारहणार्थ यहाँ" (The page prints u-daa-ra-ha-naa-rtha (ra and ha transposed), the book's own misspelling, which the row repaired; as printed. Crop p10 x160-290 y270-287 at zoom 12; the decoded layer's line y272 agrees)
- ch 12 page 7 §12.4.1: "Q = (2mv_x) (– n A v_x Delta t)" → "Q = (2mv_x) ((1/2) n A v_x Delta t)" (the page sets an en dash where ½ belongs in (12.10), (– n A v_x ∆t) (crop p7 x40-280 y140-232 at zoom 4; the decoded layer's line y217 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has (½ n A v_x ∆t), and the page itself prints ½A v_x ∆t n four lines above for the molecules that hit the wall, half of them moving towards it)
- ch 12 page 7 §12.4.1: "P = n m v^2_bar (12.12)" → "P = n m v_x^2_bar (12.12)" (the page prints (12.12) as P = n m with the bar over v^2 and no subscript x (crop p7 x40-280 y365-385 at zoom 4; the decoded layer's line y371 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has P = n m v_x^2_bar, the sum of (12.11)'s n m v_x^2 over the groups; without the x the pressure would be three times (12.14)'s (1/3) n m v^2_bar)
- ch 12 page 7 §12.4.1: "v_x^2_bar = v_y^2_bar = v_3^2_bar = (1/3) [v_x^2_bar + v_y^2_bar + v_3^2_bar]" → "v_x^2_bar = v_y^2_bar = v_z^2_bar = (1/3) [v_x^2_bar + v_y^2_bar + v_z^2_bar]" (the page sets a subscript 3 where z belongs, twice in (12.13) (crop p7 x40-280 y435-475 at zoom 4; the decoded layer's lines y441 and y460 agree); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has v_z, the third of the components (v_x, v_y, v_z) the page names)
- ch 12 page 7 §12.4.1: "चालों का माध्यम है" → "चालों का माध्य है" (the page prints माध्यम (crop p7 x40-280 y478-496 at zoom 4; the decoded layer's line y480 agrees), the book's word for a medium (the waves' माध्यम of chapter 14), where the English row says v^2_bar denotes the mean of the squared speed; by the founder's ruling of 2026-10-10 the row writes the meaning: माध्य, the book's word for mean (माध्य मुक्त पथ, §12.7))
- ch 12 page 7 §12.4.1: "संख्या – n Av_x Delta t" → "संख्या (1/2) n Av_x Delta t" (the page sets an en dash where ½ belongs, औसत संख्या – n Av_x ∆t (crop p7 x288-525 y150-178 at zoom 4; the decoded layer's line y158 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has ½ n Av_x ∆t, the number the derivation found for (12.10))
- ch 12 page 7 §12.4.2: "[N x – m v^2_bar ]" → "[N x (1/2) m v^2_bar ]" (the page sets an en dash where ½ belongs in (12.15b), [N x – m v^2_bar] (crop p7 x288-525 y365-400 at zoom 4; the decoded layer's line y387 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has N x ½ m v^2_bar, as the page's own (12.16) gives E = N × (1/2) m v^2_bar; the letter x the page sets for the product stays, as in the English row)
- ch 12 page 7 §12.4.2: "E/ N = – m v^2_bar" → "E/ N = (1/2) m v^2_bar" (the page sets an en dash where ½ belongs in (12.19), E/N = – m v^2_bar (crop p7 x288-525 y460-592 at zoom 4; the decoded layer's line y572 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has E/N = ½ m v^2_bar, as (12.16) on the same page gives)
- ch 12 page 10 §12.5: "– mv_x^2 और" → "(1/2) mv_x^2 और" (the page sets an en dash where ½ belongs before mv_x^2 (crop p10 x75-290 y255-335 at zoom 4; the decoded layer's line y272 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has ½ mv_x^2, the term of (12.24) on the page before)
- ch 12 page 10 §12.5: "मान – k_BT है" → "मान (1/2) k_BT है" (the page sets an en dash where ½ belongs, औसत मान – k_BT (crop p10 x75-290 y255-335 at zoom 4; the decoded layer's line y316 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has ½ k_B T, the average (12.24) gives each such term; a negative average energy would be false)
- ch 12 page 11 §12.6.1: "dU/dT = (3/2) RT (12.28)" → "dU/dT = (3/2) R (12.28)" (the page prints (12.28) as C_v = dU/dT = (3/2) RT (crop p11 x60-290 y340-395 at zoom 4; the decoded layer's line y386 places the number); by the founder's ruling of 2026-10-10 the row writes the meaning from the arithmetic: U = (3/2) RT (12.27) gives dU/dT = (3/2) R, a molar heat capacity; the English row prints the same RT, its own slip, kept there)
- ch 12 page 11 §12.6.2: "gamma = (9/7) R (12.35)" → "gamma = 9/7 (12.35)" (the page prints (12.35) ending γ = 9/7 R (crop p11 x295-530 y140-215 at zoom 4; the decoded layer's line y191 places it); by the founder's ruling of 2026-10-10 the row writes the meaning from the arithmetic: γ = C_p/C_v = (9/2) R/(7/2) R = 9/7, a pure number like (12.31)'s 5/3 and (12.34)'s 7/5; the English row prints the same R, its own slip, kept there)
- ch 12 page 13 §12.7: "l एवं T का" → "l एवं tau का" (the page prints l एवं T, an italic capital T where τ belongs (crop p13 x288-525 y75-200 at zoom 4; the decoded layer's line y95 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row says estimate l and τ, and the lines below compute τ = 6.1 × 10^-10 s; T is this chapter's temperature)
- ch 12 page 13 §12.7: "n = (0.02 × 10^23)" → "n = (6.02 × 10^23)" (the page prints n = (0.02 × 10^23)/(22.4 × 10^-3) = 2.7 × 10^25 m^-3 (crop p13 x288-525 y75-200 at zoom 4; the decoded layer's line y120 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning from the arithmetic: 2.7 × 10^25 m^-3 × 22.4 × 10^-3 m^3 = 6.0 × 10^23, the molecules in a mole at STP (Avogadro's number, 6.02 × 10^23), where 0.02 × 10^23 gives 8.9 × 10^22 m^-3; the English row prints 0.02 too, its own slip, kept there)
- ch 13 page 12 §13.8.1: the English numbers it §13.8, from "यह कहा जाता है कि गैलीलियो ने किसी चर्च में" on — 11 paragraph(s) (the Hindi adds a 13.8.1 heading (simple pendulum) the English does not print; the English numbers the pendulum text 13.8)
- ch 13 page 4 §13.2.2: a new paragraph starts at "यदि इस फलन के कोणांक, omega t" (English ¶3 begins here, after Eq. (13.3a); the Hindi row runs ¶2 and ¶3 together across the page break)
- ch 13 page 5 §13.3: a new paragraph starts at "अतः प्रत्येक आवर्त गति सरल आवर्त गति (SHM)" (English ¶2 begins here; the Hindi row carries it on after ¶1's where-clause at the top of p5)
- ch 13 page 5 §13.3: "चित्र 13.5 में x के साथ t का ग्राफ" joined onto the paragraph before it (the Fig. 13.5 sentence and the naming of A, omega and phi are the end of English ¶2, which the row before it begins)
- ch 13 page 6 §13.3: a new paragraph starts at "यह फलन सरल आवर्त गति का निरूपण करता है" (English ¶10, the remark after part (a)'s working in Example 13.3, begins here)
- ch 13 page 6 §13.3: a new paragraph starts at "यह फलन आवर्ती है, जिसका आवर्तकाल T=pi/omega" (English ¶12, the remark after part (b)'s working in Example 13.3, begins here)
- ch 13 page 7 §13.4: "t समय के बाद यह अगला कोण omega t" joined onto the paragraph before it (the projection argument through the reference circle is the rest of English ¶2, which the row before it begins; the Hindi print breaks it at Fig. 13.10)
- ch 13 page 7 §13.4: a new paragraph starts at "समय t पर x-अक्ष पर OP के प्रक्षेप इस प्रकार" (English ¶8, the projection of OP in part (a) of Example 13.4's answer, begins here)
- ch 13 page 7 §13.4: the row starting "* कोण की प्राकृतिक इकाई रेडियन है" is numbered after the row starting "समय t पर x-अक्ष पर OP के प्रक्षेप इस प्रकार" (page 7) (the radian footnote is English ¶9, numbered before part (b); the Hindi prints (b) from the foot of p7's text above the footnote, so the loaded order put the footnote after it)
- ch 13 page 8 §13.5: "किसी समय t पर, वेग v की दिशा" joined onto the paragraph before it (the direction of the velocity through Eq. (13.10) is the rest of English ¶1, which the row before it begins)
- ch 13 page 8 §13.5: "हमें ज्ञात है कि एकसमानीय वर्तुल गति में" joined onto the paragraph before it (the centripetal acceleration through Eq. (13.11) is the rest of English ¶2, which the row before it begins)
- ch 13 page 9 §13.5: "संगत आलेख को चित्र 13.13 में" joined onto the paragraph before it (the Fig. 13.13 sentences are the rest of English ¶5, which the row before it begins)
- ch 13 page 9 §13.5: a new paragraph starts at "(a) विस्थापन = (5.0 m) cos" (English ¶8, part (a) of Example 13.5's answer, begins here)
- ch 13 page 9 §13.5: a new paragraph starts at "(b) समीकरण (13.9) का उपयोग करने पर" (English ¶9, part (b) of Example 13.5's answer, begins here)
- ch 13 page 9 §13.5: a new paragraph starts at "(c) समीकरण (13.10) का उपयोग करने पर" (English ¶10, part (c) of Example 13.5's answer, begins here)
- ch 13 page 10 §13.6: a new paragraph starts at "तब गुटके पर आरोपित नेट बल" (English ¶6, the net force F = –2kx, begins here)
- ch 13 page 10 §13.7: a new paragraph starts at "अतः सरल आवर्त गति करते किसी कण की स्थितिज ऊर्जा, U(x)" (English ¶4, the potential energy U(x) with Eq. (13.17), begins here; the Hindi print runs English ¶3 to ¶7 as one paragraph)
- ch 13 page 10 §13.7: a new paragraph starts at "इस प्रकार, सरल आवर्त गति करते किसी कण की स्थितिज ऊर्जा भी" (English ¶5 begins here)
- ch 13 page 10 §13.7: a new paragraph starts at "अतः समीकरणों (13.15) तथा (13.17) से" (English ¶6, the total energy E = U + K, begins here)
- ch 13 page 11 §13.7: a new paragraph starts at "त्रिकोणमिती की सामान्य तादात्मक को प्रयोग करने पर" (English ¶7, the trigonometric identity and Eq. (13.18), begins here)
- ch 13 page 11 §13.7: a new paragraph starts at "तब किसी समय t पर इसका विस्थापन" (English ¶12 (the displacement x(t)) begins here; the Hindi runs ¶11 and ¶12 as one sentence, so the split is at the clause after the displayed omega = 7.07 rad s^-1, not at a sentence start)
- ch 13 page 11 §13.7: a new paragraph starts at "अतः, जब कण अपनी माध्य स्थिति से 5 cm दूर है" (English ¶13, the block 5 cm from the mean position, begins here)
- ch 13 page 11 §13.7: a new paragraph starts at "तब गुटके का x = 5 cm पर वेग" (English ¶14, the velocity at x = 5 cm, begins here)
- ch 13 page 11 §13.7: a new paragraph starts at "अतः, गुटके की गतिज ऊर्जा" (English ¶15, the block's kinetic energy, begins here)
- ch 13 page 12 §13.7: "तथा गुटके की स्थितिज ऊर्जा" starts a new paragraph, not the previous page's (English ¶16, the block's potential energy, begins here at the top of p12; the row ran on from p11)
- ch 13 page 12 §13.7: a new paragraph starts at "∴ x = 5 cm पर गुटके की कुल ऊर्जा" (English ¶17, the total energy at x = 5 cm, begins here)
- ch 13 page 12 §13.7: "हम यह भी जानते हैं कि अधिकतम विस्थापन पर" joined onto the paragraph before it (the total energy at maximum displacement is the rest of English ¶17, which the row before it begins)
- ch 13 page 12 §13.7: "यह ऊर्जा 5 cm विस्थापन पर" joined onto the paragraph before it (the remark on conservation of energy closes English ¶17)
- ch 13 page 12 §13.8: "यह कहा जाता है कि गैलीलियो ने किसी चर्च में" joined onto the paragraph before it (the Galileo paragraph is English ¶1; it joins onto the Hindi-only introduction that opens 13.8, which has no English row of its own)
- ch 13 page 13 §13.8: "यह एक प्रत्यानयन बल आघूर्ण है" starts a new paragraph, not the previous page's (English ¶5, the restoring torque through Eq. (13.22), begins here at the top of p13; the row ran on from ¶4 on p12)
- ch 13 page 13 §13.8: a new paragraph starts at "यदि हम यह मानें कि विस्थापन theta छोटा है" (English ¶6, the small-angle series Eq. (13.23), begins here)
- ch 13 page 13 §13.8: "यहाँ theta रेडियन में है" joined onto the paragraph before it (the where-clause after Eq. (13.23) is the end of English ¶6)
- ch 13 page 13 §13.8: a new paragraph starts at "सारणी 13.1 में हमने कोण theta" (English ¶8, on Table 13.1, begins here)
- ch 13 page 13 §13.8: "समीकरण (13.24) तथा समीकरण (13.11) से" joined onto the paragraph before it (Eq. (13.25) is the end of English ¶9, which the row before it begins)
- ch 13 page 13 §13.8: a new paragraph starts at "अब क्योंकि लोलक की डोरी द्रव्यहीन है" (English ¶10, the moment of inertia mL^2 and Eq. (13.26), begins here)
- ch 13 page 13 §13.8: a new paragraph starts at "हर एक सेकंड के बाद टिक करने वाले" (English ¶13, the seconds pendulum's period of 2 s and its length, begins here)
- ch 13 page 4 §13.2.2: "एक दिष्टत:" → "एक दिष्टतः" (Visarga: the adverb (monotonically), split by the line end after the first part, no hyphen; the row's space is kept. Crop p4 x319-551 y370-433 at zoom 5; the decoded layer's line y388 agrees)
- ch 13 page 4 §13.2.2: "एकदिष्टत:" → "एकदिष्टतः" (Visarga: the adverb (monotonically). Crop p4 x319-551 y370-433 at zoom 5 (line y419); the decoded layer's line y419 agrees)
- ch 13 page 6 §13.4: "गेंद की परछाई वृत्त" → "गेंद की परछाईं वृत्त" (The page prints an anusvara dot over the final ii; the row drops it. Crop p6 x490-535 y524-543 at zoom 20; the decoded layer's line y528 agrees)
- ch 13 page 12 §13.8: "निरपेक्षत:" → "निरपेक्षतः" (Visarga: the adverb (absolutely). Crop p12 x70-302 y368-385 at zoom 6; the decoded layer's line y370 agrees)
- ch 13 page 9 §13.5: "sin [(2pi s^-1) × (1.5 s + pi/4)]" → "sin [(2pi s^-1) × 1.5 s + pi/4]" (the page prints (b)'s argument as sin [(2π s^-1) × (1.5 s + π/4)], the bracket taking in π/4 (crop p9 x320-560 y185-222 at zoom 4; the decoded layer's lines y190-215 place it); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has sin [(2π s^-1) × 1.5 s + π/4], the phase of x = (5.0 m) cos [(2π rad/s) t + π/4] at t = 1.5 s, which the next line evaluates as (3π + π/4); the printed bracket adds π/4 to seconds and gives 3π + π^2/2)
- ch 13 page 9 §13.5: "= 130 m s^-2" → "= 140 m s^-2" (the page prints the acceleration as = 130 m s^-2 (crop p9 x320-560 y270-340 at zoom 4; the decoded layer's line y327 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning from the arithmetic: (2π s^-1)^2 × 3.535 m = 39.48 × 3.535 = 139.6 ≈ 140 m s^-2, as the English row has)
- ch 13 page 13 §13.8: "चर राशि कोणीय त्वरण है" → "चर राशि कोणीय विस्थापन है" (the page prints चर राशि कोणीय त्वरण है, the variable being the angular acceleration (crop p13 x315-555 y85-135 at zoom 4; the decoded layer's line y105 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row says the variable is angular displacement, θ in α = −(mgL/I) θ (13.24) as x is in (13.11); कोणीय विस्थापन is the book's term for it (§8.2))
- ch 14 page 2 §14.1: a new paragraph starts at "तीसरी प्रकार की एक अन्य तरंग है जिसे द्रव्य तरंग" (English ¶5, on matter waves, begins here, after Eq. (14.1); the Hindi print keeps it in ¶4's paragraph)
- ch 14 page 4 §14.2: "इन तरंगों में कणों के दोलन पृष्ठ तक ही सीमित" joined onto the paragraph before it (the particle motion in water waves and the ocean sentence close English ¶6; the Hindi print starts a new paragraph at them)
- ch 14 page 4 §14.2: a new paragraph starts at "(a) किसी लंबी कुंडलित कमानी के एक सिरे" (English ¶9, part (a) of Example 14.1, begins here; the Hindi print runs the statement and its four parts as one paragraph)
- ch 14 page 4 §14.2: a new paragraph starts at "(b) द्रव से भरे किसी सिलिंडर में" (English ¶10, part (b), begins here)
- ch 14 page 4 §14.2: a new paragraph starts at "(c) जल के पृष्ठ पर चलती मोटरबोट" (English ¶11, part (c), begins here)
- ch 14 page 4 §14.2: a new paragraph starts at "(d) किसी कंपायमान" (English ¶12, part (d), begins here)
- ch 14 page 6 §14.3.2: a new paragraph starts at "चूंकि कोण में 2pi से प्रत्येक परिवर्तन पर" (English ¶2, the periodicity of sin kx, begins here, after Eq. (14.5); the Hindi row runs English ¶1 to ¶4 as one paragraph)
- ch 14 page 6 §14.3.2: a new paragraph starts at "अर्थात बिंदुओं x तथा x + 2n pi / k" (English ¶3 begins here, after the displayed sin kx identity)
- ch 14 page 6 §14.3.2: a new paragraph starts at "k को संचरण स्थिरांक अथवा कोणीय तरंग संख्या" (English ¶4, naming k and its SI unit, begins here, after Eq. (14.6))
- ch 14 page 6 §14.3.3: a new paragraph starts at "omega को तरंग की कोणीय आवृत्ति कहते हैं" (English ¶4 begins here, after Eq. (14.7); the Hindi print keeps it in ¶3's paragraph)
- ch 14 page 7 §14.3.3: "nu को हर्ट्ज (Hz) में मापते हैं" starts a new paragraph, not the previous page's (English ¶5, nu is measured in hertz, is its own paragraph; the Hindi prints it at the top of p7 and the row carried it on from p6)
- ch 14 page 7 §14.3.3: a new paragraph starts at "यहाँ s(x, t) स्थिति x तथा समय t पर" (English ¶7, where s(x, t) is defined, begins here, after Eq. (14.9))
- ch 14 page 6 §14.3.3: the row starting "*यहाँ भी rad को छोड़ सकते हैं" is numbered after the row starting "यहाँ s(x, t) स्थिति x तथा समय t पर" (page 7) (the footnote on k's unit sits at the foot of p6 in both editions, but the English numbers it ¶8, after ¶5 to ¶7, which the Hindi prints on p7; it goes after English ¶7, the row defining s(x, t))
- ch 14 page 7 §14.3.3: a new paragraph starts at "दूरी x = 30.0 cm तथा समय t = 20 s पर विस्थापन" (English ¶15, the displacement at x = 30.0 cm and t = 20 s, begins here; the Hindi print keeps it in part (c)'s paragraph)
- ch 14 page 8 §14.4.1: a new paragraph starts at "mu की विमा [ML^-1] है तथा T की" (English ¶2, the dimensions of mu and T, begins here; the Hindi print keeps it in ¶1's paragraph)
- ch 14 page 8 §14.4.1: "अतः, यदि T तथा mu ही प्रासंगिक" joined onto the paragraph before it (Eq. (14.13) belongs to English ¶2; the Hindi row was cut at the display before it)
- ch 14 page 8 §14.4.1: "जहाँ C विमाहीन स्थिरांक है" joined onto the paragraph before it (the constant C and Eq. (14.14) close English ¶2; the Hindi row was cut at the display)
- ch 14 page 9 §14.4.2: "यहाँ दाब में परिवर्तन Delta P आयतन विकृति" joined onto the paragraph before it (English ¶1 runs on past Eq. (14.16) to the dimensions of B/rho; the Hindi row was cut at the display)
- ch 14 page 9 §14.4.2: "अतः, यदि B तथा rho ही प्रासंगिक" joined onto the paragraph before it (Eqs. (14.18) and (14.19) close English ¶1; the Hindi row was cut at the display (14.17))
- ch 14 page 10 §14.4.2: a new paragraph starts at "इस प्रकार, आदर्श गैस के लिए रुद्धोष्म" (English ¶10, the adiabatic bulk modulus, begins here; the Hindi print runs English ¶9 to ¶11 as one paragraph)
- ch 14 page 10 §14.4.2: a new paragraph starts at "अतः वायु में ध्वनि की चाल [समीकरण (14.19)]" (English ¶11, the speed of sound with Eq. (14.24), begins here)
- ch 14 page 11 §14.5: "यहाँ हमने (sin A + sin B) के लिए" joined onto the paragraph before it (English ¶6 runs on from Eq. (14.29) through Eq. (14.34) to the Fig. 14.10 sentence; the Hindi rows were cut at each display)
- ch 14 page 11 §14.5: "समीकरण (14.31) यह दर्शाता है कि परिणामी" joined onto the paragraph before it (the reading of Eq. (14.31) and Eq. (14.32) belong to English ¶6)
- ch 14 page 11 §14.5: "यदि phi = 0, अर्थात् दोनों तरंगें" joined onto the paragraph before it (the in-phase case, Eq. (14.33), belongs to English ¶6)
- ch 14 page 12 §14.5: "अर्थात् परिणामी तरंग का आयाम 2a है" continues the previous page's paragraph (the amplitude 2a and the out-of-phase case, Eq. (14.34), belong to English ¶6; the row before it ends p11)
- ch 14 page 12 §14.5: "समीकरण (14.33) दो तरंगों का संपोषी" joined onto the paragraph before it (constructive and destructive interference and the Fig. 14.10 sentence close English ¶6)
- ch 14 page 13 §14.6: "किसी खुली परिसीमा से परावर्तन के लिए" joined onto the paragraph before it (the open boundary and Eq. (14.36) belong to English ¶4; the Hindi row was cut at the display (14.35))
- ch 14 page 13 §14.6: "स्पष्टतः दृढ़ परिसीमा पर" joined onto the paragraph before it (the closing sentence, y = y_i + y_r = 0 at the rigid boundary, ends English ¶4)
- ch 14 page 13 §14.6.1: "सुपरिचित त्रिकोणमितीय तत्समक sin (A + B)" joined onto the paragraph before it (the trigonometric identity and Eq. (14.37) close English ¶2; the Hindi row was cut at the display)
- ch 14 page 14 §14.6.1: "अतः L लंबाई की डोरी पर सीमित" joined onto the paragraph before it (Eq. (14.41) belongs to English ¶6, which runs from the antinodes through Eq. (14.42) to the harmonics nu_n)
- ch 14 page 14 §14.6.1: "तदनुरूपी आवृत्तियों के मान होंगे" joined onto the paragraph before it (Eq. (14.42), the fundamental mode and the harmonics close English ¶6)
- ch 14 page 15 §14.6.1: "यह आवश्यक नहीं है कि कोई तानित डोरी" joined onto the paragraph before it (the superposition of modes and the sitar and violin are the rest of English ¶7, which opens with Fig. 14.13)
- ch 14 page 15 §14.6.1: a new paragraph starts at "संभावित तरंगदैर्घ्य निम्नलिखित संबंध से प्रतिबंधित" (English ¶9, the possible wavelengths with Eq. (14.43), begins here, after the display for L)
- ch 14 page 15 §14.6.1: "मूल विधा n = 0 के संगत है" joined onto the paragraph before it (the fundamental v/4L and the odd harmonics belong to English ¶10, which begins with Eq. (14.44))
- ch 14 page 15 §14.6.1: "चित्र 14.14 एक सिरे पर खुले तथा" joined onto the paragraph before it (Fig. 14.14 and the pipe open at both ends close English ¶10)
- ch 14 page 15 §14.6.1: a new paragraph starts at "उपरोक्त वर्णित निकायों, डोरी एवं वायु कॉलम" (English ¶11, forced oscillations and resonance, begins here; it runs on to p16)
- ch 14 page 16 §14.6.1: "स्पष्ट है कि 1.1 kHz आवृत्ति का स्रोत" joined onto the paragraph before it (the resonance at nu_2 closes English ¶14, the first half of Example 14.5's answer)
- ch 14 page 16 §14.6.1: a new paragraph starts at "L = 30 cm तथा v = 300 m s^-1 के लिए" (English ¶16, the closed pipe's 275 Hz fundamental and no resonance, begins here)
- ch 14 page 17 §14.7: a new paragraph starts at "यहाँ पर हमने प्रतीक y के स्थान पर s" (English ¶3 begins here, after Eq. (14.45); the Hindi print keeps it in ¶2's paragraph)
- ch 14 page 17 §14.7: "cos A + cos B के सुपरिचित त्रिकोणमितीय" joined onto the paragraph before it (the cos A + cos B identity and Eq. (14.46) belong to English ¶3; the Hindi row was cut at the display s = s_1 + s_2)
- ch 14 page 17 §14.7: a new paragraph starts at "यदि हम omega_b = (omega_1 - omega_2) / 2 तथा" (English ¶4 is the definitions of omega_b and omega_a; the Hindi states them here, before Eq. (14.47), which therefore goes with them)
- ch 14 page 17 §14.7: a new paragraph starts at "यदि |omega_1 - omega_2| << omega_1, omega_2; omega_a >> omega_b है" (English ¶5, the interpretation of Eq. (14.47) through Eq. (14.48), begins here)
- ch 14 page 14 §14.6.1: "है, अतः: x = n" → "है, अतः x = n" (the page prints one mark after अत, the visarga, and no colon after it (crop p14 x65-200 y470-490 at zoom 8; the decoded layer's line y476 agrees); the row's added colon is not on the page)
- ch 14 page 3 §14.2: "तरंग को अनुदैर्ध्य तरंग" → "तरंग को अनुदैर्घ्य तरंग" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p3 x70-302 y619-635 at zoom 5; the decoded layer's line y620 agrees)
- ch 14 page 4 §14.2: "चित्र 14.4 में अनुदैर्ध्य तरंगों" → "चित्र 14.4 में अनुदैर्घ्य तरंगों" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p4 x70-302 y124-140 at zoom 5 and x130-180 at zoom 12, against the dha form at y106 (x440-490, zoom 12); the decoded layer's line y125 agrees)
- ch 14 page 4 §14.2: "यह अनुदैर्ध्य तरंग का" → "यह अनुदैर्घ्य तरंग का" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p4 x70-302 y471-487 at zoom 5; the decoded layer's line y472 agrees)
- ch 14 page 4 §14.2: "अथवा अनुदैर्ध्य, प्रगामी" → "अथवा अनुदैर्घ्य, प्रगामी" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p4 x70-302 y487-503 at zoom 5; the decoded layer's line y489 agrees)
- ch 14 page 4 §14.2: "अनुप्रस्थ है, अनुदैर्ध्य है" → "अनुप्रस्थ है, अनुदैर्घ्य है" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p4 x319-551 y438-454 at zoom 5; the decoded layer's line y439 agrees)
- ch 14 page 4 §14.2: "(b) अनुदैर्ध्य (c) अनुप्रस्थ तथा अनुदैर्ध्य (d) अनुदैर्ध्य" → "(b) अनुदैर्घ्य (c) अनुप्रस्थ तथा अनुदैर्घ्य (d) अनुदैर्घ्य" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. All three: crop p4 x319-551 y622-674 at zoom 5 and x340-375 y622-638 at zoom 12; the decoded layer's lines y624, y641 and y659 agree)
- ch 14 page 5 §14.3: "के पारित: दोलन" → "के पारितः दोलन" (Visarga: the adverb (around, about the mean position), which the page spells paa-ri-tah, kept as printed; the mark after ta is the visarga glyph the font shares with the colon. Crop p5 x480-551 y663-680 at zoom 16; the decoded layer's line y665 agrees)
- ch 14 page 10 §14.5: "स्पंद अतिव्यापित होते" → "स्पंद अतिव्याप्ति होते" (The page prints the noun form (half pa, ta with i-matra), not the participle the row wrote; as printed. Crop p10 x319-450 y509-526 at zoom 12; the decoded layer's line y511 agrees)
- ch 14 page 16 §14.6.1: "इसी स्रोत द्वारा" → "इसी स्त्रोत द्वारा" (At this place the page prints sa joined to the tra conjunct, not the sa-ra ligature it prints for the same word at y277 and y543 of this page; as printed. Crop p16 x470-540 y322-338 against x470-540 y275-291, zoom 14; the decoded layer's line y324 agrees)
- ch 14 page 17 §14.7: "हम अनुदैर्ध्य न कि" → "हम अनुदैर्घ्य न कि" (the page spells the word with gha (the half-letter before ya has the flat-topped gha form, unlike the curled dha of the same word where the page prints dha); as printed. Crop p17 x70-302 y428-444 at zoom 5 and x110-160 at zoom 12; the decoded layer's line y429 agrees)
- ch 14 page 6 §14.3.2: "x + 2n pi / k a पर" → "x + 2n pi / k पर" (the page sets an italic a after x + 2nπ/k (crop p6 x65-305 y580-625 at zoom 5; the decoded layer's line y599 agrees), which reads as x + 2nπa/k; by the founder's ruling of 2026-10-10 the row writes the meaning: the points of equal displacement are x and x + 2nπ/k, from sin k[x + 2nπ/k] on the line before, as the English row has)
- ch 14 page 7 §14.3.3: "omega = 30 s^-1" → "omega = 3.0 s^-1" (the page prints ω = 30 s^-1 (crop p7 x65-305 y520-572 at zoom 4; the decoded layer's line y559 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: Example 14.2 gives 3.0 rad/s (y = 0.005 sin (80.0 x – 3.0 t)) and the solution goes on with T = 2π/3.0 s^-1 = 2.09 s; the English row has ω = 3.0 s^-1)
- ch 14 page 11 §14.5: "(14.29) alpha [2 sin" → "(14.29) = a [2 sin" (the page sets a Greek α where = a belongs, before the bracket of (14.30) (crop p11 x315-555 y412-478 at zoom 4; the decoded layer's line y454 places it); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row has = a[2 sin [...] cos (φ/2)], the sum (14.29) by the identity for sin A + sin B, which (14.31) then writes as 2a cos (φ/2) sin (kx – ωt + φ/2))
- ch 14 page 13 §14.6: "सभी बलों पर" → "सभी क्षणों पर" (the page prints y = y_i + y_r = 0 सभी बलों पर, at all forces (crop p13 x200-305 y480-500 at zoom 8; the decoded layer's line y484 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row says at all times, and the page's own words for it in this section are सभी क्षणों पर (crop p12 x315-555 y340-362 at zoom 4))
- ch 14 page 16 §14.6.1: "v = 300 m s^-1" → "v = 330 m s^-1" (the page prints L = 30 cm तथा v = 300 m s^-1 (crop p16 x315-555 y570-730 at zoom 3.5; the decoded layer's line y699 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: Example 14.5 gives the speed of sound as 330 m s^-1, the solution uses 330 for the open pipe, and the 275 Hz the page prints is 330/(4 × 0.30 m), where 300 gives 250 Hz; the English row has 330)
rulings on verifier flags that change no text: 23

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

- ch 11 §11.2 ¶2: "11.1(a)"
- ch 13 §13.2 ¶1: "13.1(c)"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 0 | 388 | 294 |

## sections whose Hindi rows do not number as the English (English is canonical)

95 of 95 sections one to one
| chapter | section | en | hi |
|---|---|---|---|

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 8 | 14 | 11 | 64 | 78.6% |
| 9 | 24 | 19 | 127 | 79.2% |
| 10 | 26 | 21 | 107 | 80.8% |
| 11 | 18 | 14 | 92 | 77.8% |
| 12 | 16 | 13 | 95 | 81.3% |
| 13 | 19 | 13 | 94 | 68.4% |
| 14 | 23 | 18 | 103 | 78.3% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
- ch 14 §14.3.3 ¶5 continues the sentence ¶8 stopped in the middle of: "…ली m^-1 SI मात्रक में मापी जाने वाली राशि है।" + "nu को हर्ट्ज (Hz) में मापते हैं।…"
- ch 14 §14.4.1 ¶2 continues the sentence ¶1 stopped in the middle of: "…संबंध में स्थिरांक संबंधी अनिश्चितता रहती है।" + "mu की विमा [ML^-1] है तथा T की बल की, अर्थात …"

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 140 | 140 | 109 | 682 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

- ch 10 §10.7 ¶5
- ch 11 §11.6 ¶12
- ch 13 §13.8.1 ¶1
- ch 13 §13.8.1 ¶10
- ch 13 §13.8.1 ¶2
- ch 13 §13.8.1 ¶3
- ch 13 §13.8.1 ¶4
- ch 13 §13.8.1 ¶5
- ch 13 §13.8.1 ¶6
- ch 13 §13.8.1 ¶7
- ch 13 §13.8.1 ¶8
- ch 13 §13.8.1 ¶9
- ch 14 §14.4.1 ¶6
- ch 14 §14.5 ¶10
- ch 14 §14.5 ¶11
- ch 14 §14.5 ¶7
- ch 14 §14.5 ¶8
- ch 14 §14.5 ¶9
- ch 14 §14.6 ¶5
- ch 14 §14.6 ¶6
- ch 14 §14.6.1 ¶17
- ch 14 §14.6.1 ¶18
- ch 14 §14.6.1 ¶19
- ch 14 §14.6.1 ¶20
- ch 8 §8.5.6 ¶1

## rows the other edition still carries — this edition's text cleared from them

none

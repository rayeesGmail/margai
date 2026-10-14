# margai-pipeline ncert load dry-run

- run: 2026-10-14 07:50 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 8 chapters of phy12-part1 (hi)
- result: ok
DRY RUN: nothing is written — the database write runs inside a transaction that is rolled back, so every count below is what this load would do
extraction: hi.jsonl sha256 97cf92b1466babf1ba1c780067e3e274ec23867e8542ba7f17e35dbc285a1b2c — a local file read in place of extract/phy12-part1/hi.jsonl, not the content store; the reload's report prints the artefact's sha256 to match
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 c9fa6cd0deff7e6936d20209feccaac609566f757f5a84a93b4db4c6df91f2dc

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## a visarga the model wrote as a colon (deterministic, each one named)

- ch 1 p6 §1.4.3: a visarga written as a colon — अतः
- ch 1 p6 §1.4.3: a visarga written as a colon — अतः, अतः
- ch 1 p6 §1.4.3: a visarga written as a colon — अतः
- ch 1 p6 §1.4.3: a visarga written as a colon — अतः
- ch 1 p18 §1.7.2: a visarga written as a colon — अतः
- ch 1 p18 §1.7.2: a visarga written as a colon — क्रमशः
- ch 1 p18 §1.7.2: a visarga written as a colon — अतः
- ch 1 p20 §1.8: a visarga written as a colon — अतः
- ch 1 p20 §1.8: a visarga written as a colon — अतः
- ch 1 p21 §1.9: a visarga written as a colon — अतः
- ch 1 p21 §1.9: a visarga written as a colon — अतः
- ch 1 p23 §1.9: a visarga written as a colon — अतः
- ch 1 p23 §1.10: a visarga written as a colon — अतः
- ch 1 p34 §1.14.1: a visarga written as a colon — सन्निकटतः
- ch 1 p34 §1.14.2: a visarga written as a colon — अतः
- ch 1 p35 §1.14.2: a visarga written as a colon — सन्निकटतः
- ch 1 p35 §1.14.3: a visarga written as a colon — अतः
- ch 2 p3 §2.1: a visarga written as a colon — स्पष्टतः
- ch 2 p3 §2.2: a visarga written as a colon — अतः
- ch 2 p3 §2.2: a visarga written as a colon — क्रमशः
- ch 2 p4 §2.3: a visarga written as a colon — अतः
- ch 2 p8 §2.5: a visarga written as a colon — अतः
- ch 2 p11 §2.6.1: a visarga written as a colon — क्रमशः
- ch 2 p11 §2.7: a visarga written as a colon — अतः
- ch 2 p16 §2.8.2: a visarga written as a colon — अतः
- ch 2 p17 §2.8.3: a visarga written as a colon — अतः
- ch 2 p17 §2.8.3: a visarga written as a colon — अतः, अतः
- ch 3 p2 §3.3: a visarga written as a colon — सामान्यतः
- ch 3 p2 §3.3: a visarga written as a colon — अतः, अतः
- ch 3 p4 §3.4: a visarga written as a colon — अतः
- ch 3 p4 §3.4: a visarga written as a colon — अतः
- ch 3 p5 §3.4: a visarga written as a colon — प्रायः
- ch 3 p5 §3.5: a visarga written as a colon — पुनः
- ch 3 p7 §3.5: a visarga written as a colon — अतः
- ch 3 p10 §3.8: a visarga written as a colon — अतः
- ch 3 p14 §3.10: a visarga written as a colon — स्पष्टतः
- ch 3 p14 §3.10: a visarga written as a colon — अतः
- ch 3 p16 §3.11: a visarga written as a colon — स्पष्टतः
- ch 3 p16 §3.11: a visarga written as a colon — क्रमशः, अतः
- ch 3 p16 §3.11: a visarga written as a colon — अतः
- ch 3 p18 §3.12: a visarga written as a colon — स्पष्टतः
- ch 3 p18 §3.12: a visarga written as a colon — अतः
- ch 3 p18 §3.12: a visarga written as a colon — अतः
- ch 4 p5 §4.2.3: a visarga written as a colon — अतः
- ch 4 p6 §4.2.3: a visarga written as a colon — अतः, अतः
- ch 4 p6 §4.3: a visarga written as a colon — अतः
- ch 4 p6 §4.3: a visarga written as a colon — अतः
- ch 4 p7 §4.4: a visarga written as a colon — अतः
- ch 4 p8 §4.4: a visarga written as a colon — स्वतः
- ch 4 p13 §4.6: a visarga written as a colon — सामान्यतः
- ch 4 p16 §4.7: a visarga written as a colon — अन्तः
- ch 4 p16 §4.7: a visarga written as a colon — सामान्यतः
- ch 4 p16 §4.7: a visarga written as a colon — अतः
- ch 4 p17 §4.8: a visarga written as a colon — अतः
- ch 4 p17 §4.8: a visarga written as a colon — क्रमशः, क्षैतिजतः
- ch 5 p10 §5.3: a visarga written as a colon — अतः
- ch 5 p12 §5.4: a visarga written as a colon — अतः
- ch 6 p13 §6.7.1: a visarga written as a colon — अंतः, अंतः, अंतः
- ch 6 p17 §6.7.2: a visarga written as a colon — क्रमशः
- ch 7 p1 §7.1: a visarga written as a colon — क्रमशः, सामान्यतः
- ch 7 p7 §7.4: a visarga written as a colon — अतः
- ch 7 p9 §7.5: a visarga written as a colon — अतः
- ch 7 p9 §7.5: a visarga written as a colon — परिणामतः
- ch 7 p11 §7.6.1: a visarga written as a colon — अतः
- ch 7 p11 §7.6.1: a visarga written as a colon — क्रमशः
- ch 7 p18 §7.8: a visarga written as a colon — प्रायः
- ch 8 p4 §8.2: a visarga written as a colon — अतः
- ch 8 p4 §8.2: a visarga written as a colon — अतः
- ch 8 p4 §8.2: a visarga written as a colon — पूर्णतः
- ch 8 p9 §8.4.1: a visarga written as a colon — सामान्यतः

## a word glued to a colon that the visarga list does not know — adjudicate

left as written: a `text` entry if the page means a visarga, nothing if it is a colon
- ch 1 p14 §1.7: विलोमत:
- ch 1 p21 §1.8: त्रिज्यत:, त्रिज्यत:
- ch 1 p36 §1.14.3: हल:
- ch 2 p28 §2.14.2: नोट:
- ch 3 p7 §3.5: तथ्यत:
- ch 3 p18 §3.12: विकर्णत:
- ch 4 p1 §4.1: केंद्रत:
- ch 4 p13 §4.6: अन्त:
- ch 4 p17 §4.8: पार्श्वत:
- ch 8 p4 §8.2: शब्दश:
- ch 8 p7 §8.3.2: स्व:संपोषित

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

- ch 1 page 3 §1.2: "सभी पदार्थ परमाणुओं और/अथवा अणुओं से बने हैं।" joined onto the paragraph before it (the atoms-and-molecules text is the rest of English ¶7, whose first sentence (why material bodies acquire charge) the Hindi row carries alone)
- ch 1 page 3 §1.3: the paragraph starting "* एक तीसरी श्रेणी जिसे अर्धचालक" takes its number after the next paragraph of its section (the semiconductor footnote is English ¶4, numbered after ¶3 (the comb and the metal rod); the Hindi prints the footnote at the foot of p3 and ¶3 at the top of p4)
- ch 1 page 8 §1.5: a new paragraph starts at "epsilon_0 को मुक्त आकाश या निर्वात" (English ¶9 (epsilon_0, the permittivity of free space, and its value) begins here, after Eq. (1.2) which ends ¶8)
- ch 1 page 8 §1.5: a new paragraph starts at "समीकरण (1.3) के संबंध में कुछ टिप्पणियाँ" (English ¶14, 'Some remarks on Eq. (1.3) are relevant', begins here, after Eq. (1.3) which ends ¶13)
- ch 1 page 9 §1.5: "यहाँ पर ऋणात्मक चिह्न आकर्षण बल" joined onto the paragraph before it (the sign of F_e and the gravitational force F_G are the rest of English ¶19, solution (a)(i) of Example 1.3; the Hindi row was cut at the display)
- ch 1 page 9 §1.5: "यहाँ m_p तथा m_e क्रमशः प्रोटॉन" joined onto the paragraph before it (the masses m_p, m_e and the ratio 2.4 × 10^39 are the end of English ¶19; the Hindi row was cut at the display)
- ch 1 page 9 §1.5: "तथापि यहाँ यह उल्लेख करना महत्वपूर्ण" joined onto the paragraph before it (the different signs of the two forces and their values inside a nucleus are the rest of English ¶20, which begins with (ii), the ratio 1.3 × 10^36)
- ch 1 page 10 §1.5: "(b) एक प्रोटॉन द्वारा एक इलेक्ट्रॉन" continues the previous page's paragraph (part (b), the force and the electron's acceleration at the top of p10, is the end of English ¶20, which runs from p9 to p10 and carries (ii) and (b) in one row)
- ch 1 page 11 §1.5: "यहाँ r की तुलना में गोलों A तथा B" joined onto the paragraph before it (the redistribution of charge and F' = F are the rest of English ¶23, Example 1.4's solution; the Hindi row was cut at the display)
- ch 1 page 12 §1.6: "इसी प्रकार q_3 के कारण q_1 पर" joined onto the paragraph before it (the force F_13 on q_1 due to q_3 is the end of English ¶3, which runs from p11 to p12)
- ch 1 page 13 §1.6: "इस प्रकार A पर स्थित आवेश q के कारण" joined onto the paragraph before it (the forces F_1, F_2, F_3 on Q and their zero sum are the rest of English ¶9, Example 1.5's solution)
- ch 1 page 13 §1.6: a new paragraph starts at "सममिति द्वारा भी यह स्पष्ट है कि" (English ¶10 (the zero sum by symmetry, the rotation through 60°) begins here; the Hindi print keeps it in ¶9's paragraph)
- ch 1 page 13 §1.6: a new paragraph starts at "आवेशों के प्रत्येक युगल के लिए आकर्षण" (English ¶13 (each pair's force has the same magnitude F) begins here, after F_1 = F r_hat_1 which ends ¶12)
- ch 1 page 14 §1.6: "इसी प्रकार, C पर स्थित आवेश" joined onto the paragraph before it (the total force F_3 on -q at C is the end of English ¶14, which begins with F_2 on q at B)
- ch 1 page 14 §1.6: a new paragraph starts at "यहाँ रोचक बात यह है कि तीनों" (English ¶15 (the sum of the forces on the three charges is zero) begins here)
- ch 1 page 14 §1.6: a new paragraph starts at "यह परिणाम चौंकाने वाला नहीं है।" (English ¶16 (the result is not surprising; Newton's third law) begins here)
- ch 1 page 14 §1.7: "किसी बिंदु r पर आवेश Q द्वारा उत्पन्न" joined onto the paragraph before it (the field E(r) of Eq. (1.6), the word 'field' and the force of Eq. (1.7) are the rest of English ¶1)
- ch 1 page 16 §1.7.1: a new paragraph starts at "इसी प्रकार r_2 पर स्थित आवेश q_2" (English ¶3 (the field E_2 due to q_2 and the like expressions for E_3 … E_n) begins here, after E_1 which ends ¶2)
- ch 1 page 16 §1.7.1: a new paragraph starts at "अध्यारोपण सिद्धांत द्वारा आवेशों के निकाय के कारण" (English ¶4 (the superposition principle and Eq. (1.10)) begins here)
- ch 1 page 17 §1.7.2: a new paragraph starts at "विरामावस्था से आरंभ करके, इलेक्ट्रॉन के" (English ¶6 (starting from rest, the electron's time of fall t_e) begins here, after a_e = eE/m_e which ends ¶5)
- ch 1 page 18 §1.7.2: "इस प्रकार, समान दूरी गिरने में" joined onto the paragraph before it (the heavier proton's longer fall and the neglect of gravity are the end of English ¶6, which runs from p17 to p18)
- ch 1 page 18 §1.7.2: "धनावेश q_1 के कारण B पर" joined onto the paragraph before it (the fields at B and at C are the rest of English ¶8, Example 1.8's solution, one row from the field at A to the field at C)
- ch 1 page 21 §1.8: a new paragraph starts at "(ii) किसी आवेश मुक्त क्षेत्र में" (English ¶10, property (ii) of field lines, begins here; the Hindi print runs the four properties on in ¶9's paragraph)
- ch 1 page 21 §1.8: a new paragraph starts at "(iii) दो क्षेत्र रेखाएँ एक-दूसरे को" (English ¶11, property (iii) of field lines, begins here)
- ch 1 page 21 §1.8: a new paragraph starts at "(iv) स्थिरवैद्युत क्षेत्र रेखाएँ बंद" (English ¶12, property (iv) of field lines, begins here)
- ch 1 page 21 §1.9: the row starting "* यह कहना उचित नहीं है कि" is numbered after the row starting "अब हम वैद्युत फ्लक्स की परिभाषा" (page 22) (the footnote on the number of field lines, printed at the foot of p21, is English ¶7, numbered after ¶6 (the definition of electric flux, Eq. 1.11))
- ch 1 page 23 §1.10.1: a new paragraph starts at "यहाँ p_hat द्विध्रुव अक्ष" (English ¶3 (p_hat the unit vector along the dipole axis, and E_(+q) of Eq. 1.13(b)) begins here, after Eq. 1.13(a) which ends ¶2)
- ch 1 page 23 §1.10.1: a new paragraph starts at "P पर कुल विद्युत क्षेत्र है" (English ¶4 (the total field at P, Eqs. 1.14 and 1.15) begins here, at the last line of p23, after Eq. 1.13(b))
- ch 1 page 24 §1.10.1: "दो आवेशों +q तथा −q के कारण विद्युत" joined onto the paragraph before it (the side heading (ii), points on the equatorial plane, leads English ¶5 as its first words; the Hindi row under it joins onto the heading)
- ch 1 page 25 §1.10.2: the row starting "* धनात्मक बिंदु आवेशों के संग्रह" is numbered after the row starting "उदाहरण 1.9 ±10 μC के दो आवेश" (page 25) (the footnote on the centre of a collection of charges is English ¶3, numbered after Example 1.9 and before its solution; the Hindi solution starts on p25 above the footnote)
- ch 1 page 26 §1.10.2: "बिंदु P पर आवेश −10 μC के कारण" joined onto the paragraph before it (the field at P due to -10 μC is part of English ¶4, solution (a) of Example 1.9, one row from the field at P to the dipole-formula check)
- ch 1 page 26 §1.10.2: "A तथा B पर स्थित दो आवेशों के कारण P पर" joined onto the paragraph before it (the resultant field at P, 2.7 × 10^5 N C^-1, is part of English ¶4)
- ch 1 page 26 §1.10.2: "इस उदाहरण में अनुपात OP/OB" joined onto the paragraph before it (the ratio OP/OB = 60 and the axial dipole formula are part of English ¶4)
- ch 1 page 26 §1.10.2: "यहाँ p = 2a q द्विध्रुव आघूर्ण" joined onto the paragraph before it (p = 2a q, the dipole moment's magnitude, is part of English ¶4; the Hindi row was cut at the display)
- ch 1 page 26 §1.10.2: "द्विध्रुव अक्ष पर विद्युत क्षेत्र की दिशा सदैव" joined onto the paragraph before it (the field's direction on the axis and p = 5 × 10^-8 C m are part of English ¶4)
- ch 1 page 26 §1.10.2: "अतः E = 2 × 5 × 10^(-8) C m" joined onto the paragraph before it (the field E = 2.6 × 10^5 N C^-1 from the dipole formula is part of English ¶4)
- ch 1 page 26 §1.10.2: "द्विध्रुव आघूर्ण की दिशा AB के अनुदिश है" joined onto the paragraph before it (the direction AB and the agreement with the earlier result are the end of English ¶4)
- ch 1 page 26 §1.10.2: "बिंदु A पर स्थित −10 μC आवेश" joined onto the paragraph before it (the field at Q due to -10 μC at A is the end of English ¶5, solution (b), which begins with the field due to +10 μC at B)
- ch 1 page 26 §1.10.2: "(a) की ही भाँति द्विध्रुव के अक्ष" joined onto the paragraph before it (the check by the equatorial dipole formula is the rest of English ¶6, which begins with the components cancelling along OQ)
- ch 1 page 27 §1.10.2: "इस प्रकरण में विद्युत क्षेत्र की दिशा आघूर्ण" joined onto the paragraph before it (the field's direction opposite to the dipole moment and the agreement are the end of English ¶6, which runs from p26 to p27)
- ch 1 page 30 §1.13: a new paragraph starts at "हम बिना उपपत्ति के गाउस नियम" (English ¶5, Gauss's law stated without proof (Eq. 1.31), begins here; the Hindi print keeps it in ¶4's paragraph)
- ch 1 page 30 §1.13: a new paragraph starts at "(i) गाउस नियम प्रत्येक बंद पृष्ठ" (English ¶9, point (i) (any closed surface), begins here; the Hindi print runs it on at the end of ¶8)
- ch 1 page 32 §1.13: a new paragraph starts at "(a) चित्र में हम यह देखते हैं कि" (English ¶20, part (a) of Example 1.11's solution, begins here; the frozen English carries the label 'Solution' as ¶19, a row of its own, and the Hindi label is left as that row)
- ch 1 page 34 §1.14.1: "गाउसीय पृष्ठ से गुज़रने वाला फ्लक्स = पृष्ठ" starts a new paragraph, not the previous page's (English ¶4 (the flux through the curved part, Gauss's law and Eq. 1.32) begins at the top of p34; the Hindi row ran ¶3 on across the page break)
- ch 1 page 34 §1.14.1: "पृष्ठ में lambda l के बराबर आवेश" joined onto the paragraph before it (the enclosed charge lambda l and E = lambda/(2 pi epsilon_0 r) are part of English ¶4)
- ch 1 page 34 §1.14.1: "सदिश रूप में किसी भी बिंदु पर विद्युत" joined onto the paragraph before it (the vector form, Eq. (1.32), and the direction of E are the end of English ¶4)
- ch 1 page 35 §1.14.3: a new paragraph starts at "यदि q > 0 है तो विद्युत क्षेत्र बहिर्मुखी" (English ¶3 (outward if q > 0; as if the charge were at the centre) begins here, after Eq. (1.34) which ends ¶2)
- ch 1 page 36 §1.14.3: "अर्थात एकसमान आवेशित पतले गोलीय खोल" joined onto the paragraph before it (the zero field inside a charged shell and its experimental meaning are the end of English ¶4, which ends after Eq. (1.35) with that sentence)
- ch 1 page 36 §1.14.3: "नाभिक से r दूरी पर स्थित बिंदु P" joined onto the paragraph before it (Gauss's law, the spherical symmetry and the two cases r < R and r > R are the rest of English ¶6, Example 1.12's solution)
- ch 1 page 37 §1.14.3: "यहाँ गाउसीय पृष्ठ द्वारा परिबद्ध आवेश q" starts a new paragraph, not the previous page's (English ¶9 (the enclosed charge q) begins at the top of p37; the Hindi row ran ¶7 on across the page break, past the footnote English numbers ¶8)
- ch 1 page 37 §1.14.3: a new paragraph starts at "पहले प्राप्त आवेश घनत्व rho का मान" (English ¶10 (substituting rho, and E(r) for r < R) begins here)
- ch 1 page 37 §1.14.3: "अथवा (r) = 0;" → "अथवा E (r) = 0;" (the page prints 'E (r) × 4 π r^2 = 0 अथवा (r) = 0;  r > R' with no E before the second (r) (crop p37 x60-400 y292-380 at zoom 4; the decoded layer's line y326 agrees), which reads as r = 0 for points with r > R; by the founder's ruling of 2026-10-10 the row writes the meaning: the English row's 'E (r) × 4 pi r^2 = 0 or E (r) = 0; r > R', the field outside the neutral atom. The row will then differ from the verifier's quote only in writing pi for π)
- ch 1 page 14 §1.7: "अथवा विलोमत: के" → "अथवा विलोमतः के" (Visarga: an adverb in -tah (vice versa), printed with the visarga glyph that this font shares with the colon. Crop p14 x160-525 y590-610 at zoom 4; the decoded layer's line y594 agrees)
- ch 1 page 21 §1.8: "त्रिज्यत: (अरीय)" → "त्रिज्यतः (अरीय)" (Visarga: an adverb in -tah (radially), printed with the visarga glyph that this font shares with the colon. Crop p21 x44-332 y169-203 at zoom 4; the decoded layer's line y172 agrees)
- ch 1 page 21 §1.8: "त्रिज्यत: अंतर्मुखी" → "त्रिज्यतः अंतर्मुखी" (Visarga: an adverb in -tah (radially), printed with the visarga glyph that this font shares with the colon. Crop p21 x44-332 y169-203 at zoom 4; the decoded layer's line y187 agrees)
- ch 1 page 3 §1.3: "इस अवरोध का परिमाण चालकों" → "इस अवरोध का परिमााण चालकों" (the footnote sets the aa-stroke twice in this word (crop p3 x40-300 y700-732 at zoom 5; the decoded layer's line y718 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 1 page 5 §1.4.3: "रूप से निर्दर्शित किया था" → "रूप से निदर्शित किया था" (the page prints no reph over the first syllable; the only reph stands over the third (crop p5 x40-410 y325-360 at zoom 4; the decoded layer's line y343 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 1 page 8 §1.5: "समीकरण (1.3) q_1 तथा q_2 के किसी" → "• समीकरण (1.3) q_1 तथा q_2 के किसी" (the paragraph opens with a printed list bullet (crop p8 x150-525 y596-650; the decoded layer's line y617 carries it), as the English row does)
- ch 1 page 9 §1.5: "q_2 के कारण q_1 पर आरोपित बल" → "• q_2 के कारण q_1 पर आरोपित बल" (the paragraph opens with a printed list bullet (crop p9 x40-410 y96-252; the decoded layer's line y103 carries it), as the English row does)
- ch 1 page 9 §1.5: "कूलॉम नियम (समीकरण 1.3) से निर्वात" → "• कूलॉम नियम (समीकरण 1.3) से निर्वात" (the paragraph opens with a printed list bullet (crop p9 x40-410 y96-252; the decoded layer's line y188 carries it), as the English row does)
- ch 1 page 15 §1.7: "आवेश Q को किन्हीं अनिर्दिष्ट" → "आवेश Q की किन्हीं अनिर्दिष्ट" (the page prints the postposition with the ii-matra on this line, where the line above has the o-matra in the same phrase (crop p15 x40-410 y272-325 at zoom 4; the decoded layer's line y290 agrees): the book's own grammar, kept as printed (DECISIONS 2026-10-03))
- ch 1 page 25 §1.10.1: "निर्भर नही है वरन्" → "निर्भर नही हैं वरन्" (the page draws an anusvara on the verb (crop p25 x85-175 y130-148 at zoom 10; the decoded layer's line y132 agrees); the negation beside it stays without one, as printed)
- ch 1 page 37 §1.14.3: "बहिर्मुखी निर्दिशित है" → "बहिर्मुखी निदर्शित है" (the page prints the word with a reph over the third syllable and none before it (crop p37 x60-226 y276-293 at zoom 8; the decoded layer's line y278 agrees), the same word as ch 1 p5: the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 1 page 8 §1.5: "(r_n21 = r_n12)" → "(r_21 = r_12)" (the page prints the bracket with a stray n before both subscripts, (r_n21 = r_n12) (crop p8 x150-525 y420-462 at zoom 5; the decoded layer's line y434 agrees), naming two quantities the chapter never defines; the sentence defines r_21 and r_12 as the magnitudes of the vectors r_21 and r_12, and the English row's '(r_12 = r_21)' says they are equal; by the founder's ruling of 2026-10-10 the row writes the meaning, in the Hindi's own order)
- ch 1 page 9 §1.5: "10^(-27) K, m_e" → "10^(-27) kg, m_e" (the page prints the proton's mass as 1.67 × 10^-27 K, a capital K where the unit belongs, while m_e beside it is in kg (crop p9 x60-270 y350-370 at zoom 6; the decoded layer's line y356 agrees); K is the kelvin, a wrong unit for a mass; the English row and this chapter's other proton masses (p10, p18) give 1.67 × 10^-27 kg; by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 1 page 9 §1.5: "(जो सदैव धनात्मक है)" → "(जो सदैव आकर्षी है)" (the page prints the gravitational force as '(which is always positive)' just before F_G = −G m_p m_e / r^2 and just after the sentence saying that the negative sign marks an attractive force (crop p9 x60-400 y445-520 at zoom 4; the decoded layer's line y465 agrees); under that sign convention a force that is always positive is repulsive, the opposite of gravity and of the minus sign the formula carries; the English row reads '(always attractive)'; by the founder's ruling of 2026-10-10 the row writes the meaning, in the word the same solution uses for gravity further down this page ('गुरुत्वाकर्षण बल आकर्षी है', §1.5 ¶21))
- ch 1 page 13 §1.6: "की दूरी AD = (2/3) AD" → "की दूरी AO = (2/3) AD" (the page prints the distance of the centroid from A as AD = (2/3) AD = (1/sqrt(3)) l (crop p13 x55-395 y105-155 at zoom 5; the decoded layer's line y124 agrees), an equation that makes AD zero; the English row reads 'the distance AO of the centroid O from A is (2/3) AD', and the next line of the page continues 'AO = BO = CO'; by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 1 page 18 §1.7.2: "परिमाण E_A के समान है" → "परिमाण E_1A के समान है" (the page prints the magnitude of E_2A as equal to E_A, with no subscript 1 (crop p18 x165-510 y660-715 at zoom 5; the decoded layer's lines y665 and y682 agree), where E_A is the total field the next line computes as E_1A + E_2A = 7.2 × 10^4 N C^-1, twice either part; the arithmetic and the English row ('points towards the right and has the same magnitude' as E_1A, 3.6 × 10^4 N C^-1) give E_1A; a dropped subscript, so by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 1 page 23 §1.10.1: "मान लीजिए बिंदु P द्विध्रुव के केंद्र से q की ओर" → "(i) अक्ष पर स्थित बिंदुओं के लिए मान लीजिए बिंदु P द्विध्रुव के केंद्र से q की ओर" (the page prints the unnumbered side heading '(i) अक्ष पर स्थित बिंदुओं के लिए' in bold italic on its own line above the paragraph (crop p23 x40-410 y570-640 at zoom 4; the decoded layer's line y582 agrees), and the row dropped it; by the side-heading rule (2026-09-27) the heading's words lead the paragraph printed under it, as the English row opens '(i) For points on the axis' and as this section's rows carry the matching '(ii)' heading)
- ch 1 page 29 §1.13: "एकांक सदिश r का परिमाण 1 है" → "एकांक सदिश r_hat का परिमाण 1 है" (the page prints 'चूँकि एकांक सदिश r का परिमाण 1 है' with a bold r and no hat (crop p29 x44-210 y682-703 at zoom 6; the decoded layer's line y700 agrees); a bold r is this chapter's position vector, whose magnitude is r, not 1 — the sentence is about the unit vector r_hat the same paragraph names twice before it, and the English row reads 'since the magnitude of a unit vector is 1'; by the founder's ruling of 2026-10-10, confirmed for this place on 2026-10-13, the row writes the meaning)
- ch 2 page 19 §2.9.1: the English numbers it §2.9, from "किसी ऐसे चालक के विषय में विचार कीजिए" on — 10 paragraph(s) (the Hindi prints a 2.9.1 heading (electrostatic shielding, layer y533) where the English runs the text on in §2.9 as result 6; the English numbers it §2.9 ¶11 onwards)
- ch 2 page 3 §2.2: a new paragraph starts at "यहाँ V_P तथा V_R क्रमशः बिंदु P तथा R" (English ¶3, the 'where V_P and V_R' sentence after Eq. (2.4), begins here; the Hindi row runs it on after the display)
- ch 2 page 4 §2.3: "यहाँ r_hat′, OP′ के अनुदिश कोई एकांक सदिश है।" joined onto the paragraph before it (the 'where r_hat′ is the unit vector' sentence and Eq. (2.6) are the rest of English ¶2, which runs from the intermediate point P′ through Eq. (2.6))
- ch 2 page 5 §2.3: a new paragraph starts at "(b) अब, आवेश 2 × 10^(-9) C को अनंत से" (English ¶8, Example 2.1 part (b), begins here; the Hindi row runs (a) and (b) as one paragraph)
- ch 2 page 5 §2.3: a new paragraph starts at "नहीं, कार्य पथ पर निर्भर नहीं होगा।" (English ¶10, 'No, work done will be path independent', begins here, after the solution's working for (b))
- ch 2 page 6 §2.4: a new paragraph starts at "अब, ज्यामिति द्वारा r_1^2 = r^2" (English ¶3, the geometry of Eq. (2.10), begins here; the Hindi row puts the 'where r_1 and r_2' sentence of ¶2 before it)
- ch 2 page 6 §2.4: "यहाँ r_1 तथा r_2 बिंदु P की क्रमशः" joined onto the paragraph before it (the 'where r_1 and r_2 are the distances' sentence after Eq. (2.9) is the end of English ¶2)
- ch 2 page 6 §2.4: a new paragraph starts at "इसी प्रकार, r_2^2 ≅ r^2" (English ¶5, 'Similarly' and Eq. (2.12), begins here; the Hindi row keeps it with Eq. (2.11))
- ch 2 page 6 §2.4: a new paragraph starts at "समीकरणों (2.9) तथा (2.13) तथा p = 2qa" (English ¶7, using Eqs. (2.9) and (2.13) to reach Eq. (2.14), begins here; the Hindi row keeps it with the binomial step)
- ch 2 page 7 §2.4: a new paragraph starts at "(धनात्मक चिह्न theta = 0 के लिए" (English ¶12, the sign note and the zero potential in the equatorial plane, begins here, after Eq. (2.16))
- ch 2 page 7 §2.5: a new paragraph starts at "यहाँ r_1P बिंदु P तथा आवेश q_1" (English ¶2, 'where r_1P is the distance between q_1 and P', begins here)
- ch 2 page 7 §2.5: a new paragraph starts at "यहाँ r_2P तथा r_3P बिंदु P की" (English ¶4, 'where r_2P and r_3P' and the superposition sum to Eq. (2.18), begins here)
- ch 2 page 8 §2.5: "यदि बिंदु x विस्तारित रेखा OA पर है" joined onto the paragraph before it (the case of x on the extended line OA and the result 9 cm and 45 cm are the rest of English ¶9, which begins with the required point P)
- ch 2 page 9 §2.5: a new paragraph starts at "(b) बिंदु Q और P; A और B के बीच" (English ¶12, Example 2.3 question (b), begins here; the Hindi row runs (a) to (e) as one paragraph)
- ch 2 page 9 §2.5: a new paragraph starts at "(c) Q से P तक एक छोटे धनावेश" (English ¶13, question (c), begins here)
- ch 2 page 9 §2.5: a new paragraph starts at "(d) B से A तक एक छोटे से ऋण आवेश" (English ¶14, question (d), begins here)
- ch 2 page 9 §2.5: a new paragraph starts at "(e) B से A तक जाने में क्या" (English ¶15, question (e), begins here)
- ch 2 page 9 §2.5: a new paragraph starts at "(a) चूँकि V ∝ 1/r, V_P > V_Q;" (English ¶17, answer (a), begins here; the English has the label Solution as ¶16, a row of its own, and the Hindi print sets हल on its own line (layer y493))
- ch 2 page 11 §2.6.1: a new paragraph starts at "(i) विद्युत क्षेत्र उस दिशा में होता है" (English ¶6, conclusion (i), begins here; the Hindi row runs both conclusions on after the lead-in)
- ch 2 page 11 §2.6.1: a new paragraph starts at "(ii) किसी बिंदु पर विद्युत क्षेत्र का परिमाण" (English ¶7, conclusion (ii), begins here)
- ch 2 page 12 §2.7: a new paragraph starts at "आवेश q_3 को अनंत से बिंदु r_3 तक" (English ¶6, the work in bringing q_3 and Eq. (2.25), begins here; the Hindi row keeps it with Eq. (2.24))
- ch 2 page 13 §2.7: a new paragraph starts at "(i) आवेश +q को A पर लाने में" (English ¶11, step (i) of Example 2.4's solution, begins here; the Hindi row keeps it at the end of the solution's opening)
- ch 2 page 14 §2.7: "चारों चरणों (i), (ii), (iii) एवं (iv)" continues the previous page's paragraph (adding the work of steps (i) to (iv), at the top of p14, is the end of English ¶14, which begins with step (iv) on p13)
- ch 2 page 15 §2.8.1: "किसी बाह्य क्षेत्र में आवेश q की r पर" joined onto the paragraph before it (Eq. (2.27) and 'where V(r) is the external potential' are the end of English ¶3)
- ch 2 page 15 §2.8.2: "q_2 पर बाह्य क्षेत्र के विरुद्ध किया गया कार्य" joined onto the paragraph before it (the two displayed works on q_2 are part of English ¶1, which runs through Eq. (2.29))
- ch 2 page 15 §2.8.2: "यहाँ r_12 आवेशों q_1 तथा q_2" joined onto the paragraph before it ('where r_12 is the distance', the superposition step and Eqs. (2.28) and (2.29) are the end of English ¶1)
- ch 2 page 15 §2.8.2: a new paragraph starts at "(b) दोनों आवेशों को एक-दूसरे से" (English ¶3, Example 2.5 question (b), begins here)
- ch 2 page 16 §2.8.2: a new paragraph starts at "(b) W = U_2 – U_1" (English ¶6, answer (b), begins here; the Hindi row keeps it with answer (a))
- ch 2 page 18 §2.9: "किसी उदासीन अथवा आवेशित चालक पर विचार" joined onto the paragraph before it (the numbered heading of result 1 leads the paragraph printed under it, as English ¶2 does; it loaded as a row of its own)
- ch 2 page 18 §2.9: "यदि E पृष्ठ के अभिलंबवत नहीं है" joined onto the paragraph before it (the numbered heading of result 2 leads the paragraph printed under it, as English ¶3 does; it loaded as a row of its own)
- ch 2 page 18 §2.9: "किसी उदासीन चालक के प्रत्येक लघु आयतन" joined onto the paragraph before it (the numbered heading of result 3 leads the paragraph printed under it, as English ¶4 does; it loaded as a row of its own)
- ch 2 page 18 §2.9: "यह उपरोक्त परिणाम 1 तथा 2 का अनुवर्ती है।" joined onto the paragraph before it (the numbered heading of result 4 leads the paragraph printed under it, as English ¶5 does; it loaded as a row of its own)
- ch 2 page 19 §2.9: "किसी यादृच्छिक आकार, आकृति तथा आवेश विन्यास" starts a new paragraph, not the previous page's (English ¶6, the system of conductors, begins here, an indented paragraph at the top of p19 (layer y100, indented); it does not continue p18's result 4)
- ch 2 page 24 §2.12: a new paragraph starts at "बाह्य क्षेत्र I (पट्टिका 1 के ऊपर का क्षेत्र)" (English ¶2, outer region I and Eq. (2.39), begins here; the Hindi row runs the regions on after the lead-in)
- ch 2 page 24 §2.12: a new paragraph starts at "बाह्य क्षेत्र II (पट्टिका 2 के नीचे का क्षेत्र)" (English ¶3, outer region II and Eq. (2.40), begins here)
- ch 2 page 25 §2.12: "विद्युत क्षेत्र की दिशा धनावेशित पट्टिका से" starts a new paragraph, not the previous page's (English ¶5, the direction of the field, begins here at the top of p25; the row ran on from Eq. (2.41) on p24)
- ch 2 page 26 §2.13: a new paragraph starts at "रैखिक परावैद्युतों के लिए, हम अपेक्षा करते हैं" (English ¶3, linear dielectrics and Eqs. (2.49) and (2.50), begins here; the Hindi row keeps it with Eq. (2.48))
- ch 2 page 28 §2.14.2: a new paragraph starts at "यदि इस संयोजन के तुल्य किसी संधारित्र पर" (English ¶2, the equivalent capacitor and Eqs. (2.62) and (2.63), begins here)
- ch 2 page 28 §2.14.2: a new paragraph starts at "तो समीकरण (2.63) से प्रभावी धारिता" (English ¶3, the effective capacitance from Eq. (2.63), begins here; the Hindi fuses it into ¶2's 'if' sentence, so the split falls at the 'then' clause, not at a sentence start)
- ch 2 page 29 §2.14.2: "(a) दिए गए नेटवर्क में तीन संधारित्र" joined onto the paragraph before it (the label हल leads answer (a), as English ¶6 'Solution (a)' does; it loaded as a row of its own)
- ch 2 page 30 §2.15: "आवेश को 2 से 1 पर स्थानांतरित करने में" starts a new paragraph, not the previous page's (English ¶2, the work in transferring charge through Eq. (2.69), begins here at the top of p30; the row ran on from p29)
- ch 2 page 30 §2.15: a new paragraph starts at "पृष्ठीय आवेश घनत्व sigma पट्टिकाओं के बीच" (English ¶5, sigma related to E and Eqs. (2.71) and (2.72), begins here; the Hindi row keeps it with Eq. (2.70))
- ch 2 page 31 §2.15: a new paragraph starts at "(a) संधारित्र पर आवेश Q = CV" (English ¶10, answer (a), begins here; the English has the label Solution as ¶9, a row of its own, and the Hindi print sets हल on its own line (layer y467))
- ch 2 page 31 §2.15: a new paragraph starts at "निकाय को स्थिति (b) तक व्यवस्थित होने में" (English ¶13, the transient period, begins here; the Hindi row keeps it with the question where the energy has gone)
- ch 2 page 6 §2.4: "पहले की ही भाँति, हम" → "पहले की ही भांति, हम" (the page prints an anusvara here, not a chandrabindu (crop p6 x58-258 y196-214 at zoom 8; the decoded layer's line y199 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 7 §2.4: "के पदों को नगण्य" → "के पदों की नगण्य" (the page prints the postposition with the ii-matra (crop p7 x158-526 y173-207 at zoom 5; the decoded layer's line y175 agrees): the book's own grammar, kept as printed (DECISIONS 2026-10-03))
- ch 2 page 14 §2.8.1: "विद्युत क्षेत्र के स्रोत का विशेष" → "विद्युत क्षेत्र के स्त्रोत का विशेष" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y414 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "E उस स्रोत द्वारा" → "E उस स्त्रोत द्वारा" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y540 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "बाह्य स्रोत ज्ञात हो सकता है परंतु प्रायः ये स्रोत अज्ञात" → "बाह्य स्त्रोत ज्ञात हो सकता है परंतु प्रायः ये स्त्रोत अज्ञात" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y554 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "बाह्य स्रोतों के कारण" → "बाह्य स्त्रोतों के कारण" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y568 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "वाले स्रोतों को सार्थक" → "वाले स्त्रोतों को सार्थक" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y582 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "बाह्य स्रोतों को दृढ़" → "बाह्य स्त्रोतों को दृढ़" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y610 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "इसके बाह्य स्रोतों पर" → "इसके बाह्य स्त्रोतों पर" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y610 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "प्रबल स्रोत हमारी" → "प्रबल स्त्रोत हमारी" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y624 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 14 §2.8.1: "वाले स्रोत की स्थितिज" → "वाले स्त्रोत की स्थितिज" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p14 x40-410 y410-682 at zoom 3; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y666 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 22 §2.10: "बाह्य क्षेत्र E_0 में रखा है" → "बाह्य क्षेत्र E_o में रखा है" (the page sets the subscript as a letter o, not a zero (crop p22 x150-300 y668-690 at zoom 10; the decoded layer's line y682 agrees); a look-alike that still reads as E-nought stays as printed (DECISIONS 2026-10-12), as phy11-part1 ch 2 p5's v_o did)
- ch 2 page 23 §2.11: "का प्रतीक [illegible] है।" → "का प्रतीक है।" (the page draws the variable-capacitor symbol as a picture, two plates crossed by an arrow (crop p23 x268-525 y700-740 at zoom 6); it is no character and the text layer carries nothing there. The canonical English row leaves the same picture out, so the transcriber's marker is removed rather than replaced (founder, 2026-10-13))
- ch 2 page 28 §2.14.2: "500 V के स्रोत से" → "500 V के स्त्रोत से" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p28 x60-260 y693-710 at zoom 6; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y695 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 2 page 6 §2.4: "अब, P cos theta" → "अब, p cos theta" (the page prints an italic capital P before cos theta = p•r-hat (crop p6 x55-200 y712-732 at zoom 8; the decoded layer's line y715 agrees); the quantity is the dipole moment's magnitude p of Eq. (2.14) just above, while P in this chapter names the point P and the polarisation; the English row reads 'p cos theta = p.r_hat'; a letter that names another quantity, so by the founder's ruling of 2026-10-10 and the look-alike test (DECISIONS 2026-10-12) the row writes the meaning. The false_positive on the verifier's flag here stands: the row will still differ from the verifier's quote, which gives the bold p an arrow the page does not draw)
- ch 2 page 17 §2.8.3: "10^(-29) cm का" → "10^(-29) C m का" (the page prints the molecule's dipole moment as 10^-29 cm, a lower-case c and m set together (crop p17 x175-512 y370-390 at zoom 6; the decoded layer's line y374 agrees), which reads as centimetres, a length; the English row and this example's own solution two lines on give 10^-29 C m; a wrong unit, so by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 2 page 19 §2.9: "किसी ऐसे चालक के विषय में विचार कीजिए" → "स्थिरवैद्युत परिरक्षण किसी ऐसे चालक के विषय में विचार कीजिए" (the page prints the heading '2.9.1 स्थिरवैद्युत परिरक्षण' directly above this paragraph (crop p19 x40-530 y520-575 at zoom 4, and x260-400 y530-550 at zoom 8; the decoded layer's line y533 agrees); the alignment's section entry files the paragraph under §2.9, where the English row opens with its run-in heading '6. Electrostatic shielding' and the Hindi rows for results 1 to 5 carry their headings; by the side-heading rule (2026-09-27) the heading's words lead the paragraph, without the Hindi section number, which the English address replaces)
- ch 2 page 21 §2.10: "यथार्थ रूप में निरसित नहीं करता" → "यथार्थ रूप में निरक्षित नहीं करता" (the page prints the verb as निरक्षित (crop p21 x262-525 y410-430 at zoom 6; the decoded layer's line y414 agrees), a misspelling of निरसित (cancel), which the same page prints rightly as निरसन six lines above (crop p21 x262-525 y318-338 at zoom 6); a non-word that names no other word or quantity, so the book's own spelling, kept as printed (DECISIONS 2026-10-03, 2026-10-10), as the block keeps प्रबर्धित and परिवद्ध; the transcription repaired it)
- ch 3 page 4 §3.4: "समीकरण (3.5) और (3.7) के संयोजन से" joined onto the paragraph before it (Eqs. (3.8) and (3.9) and the resistivity rho are the end of English ¶5, which the row before it (halving the cross-section, Eq. 3.7) begins)
- ch 3 page 6 §3.5: a new paragraph starts at "|v_d| के मान को समीकरण (3.17) से प्रतिस्थापित" (English ¶5, substituting |v_d| from Eq. (3.17) to give Eq. (3.19), begins here, after the displayed Eq. (3.18); the Hindi row runs English ¶4 to ¶9 as one paragraph)
- ch 3 page 6 §3.5: a new paragraph starts at "परिभाषानुसार, धारा घनत्व के परिमाण |j| से" (English ¶6, I = |j|A (Eq. 3.20), begins here, after the displayed Eq. (3.19))
- ch 3 page 6 §3.5: a new paragraph starts at "अतः समीकरण (3.19) तथा (3.20) से," (English ¶7, Eq. (3.21) from Eqs. (3.19) and (3.20), begins here, after the displayed Eq. (3.20))
- ch 3 page 6 §3.5: a new paragraph starts at "सदिश j, E के समांतर है, इसलिए" (English ¶8, the vector form j = (ne^2/m) tau E (Eq. 3.22), begins here, after the displayed Eq. (3.21))
- ch 3 page 6 §3.5: a new paragraph starts at "अगर हम चालकता sigma का तादात्म्य" (English ¶9, the comparison with Eq. (3.13) that identifies the conductivity sigma (Eq. 3.23), begins here at the foot of p6 (the decoded layer's line y688), after the displayed Eq. (3.22); the Hindi sentence opens with its if-clause)
- ch 3 page 7 §3.5: "इस प्रकार हम देखते हैं कि विद्युत चालकता" joined onto the paragraph before it ('We thus see' that the simple picture reproduces Ohm's law, the assumptions on tau and n and the next-section remark, are the end of English ¶9, which begins at the foot of p6)
- ch 3 page 7 §3.5: a new paragraph starts at "(a) चालक इलेक्ट्रॉन के अपवाह वेग की दिशा" (English ¶12, part (a) of the solution to Example 3.1, begins here; English ¶11 is the word Solution alone, so the Hindi हल stays as its own row)
- ch 3 page 8 §3.5: a new paragraph starts at "(a) उदाहरण 3.1 में कुछ ऐम्पियर" (English ¶17, question (a) of Example 3.2, begins here; English ¶16 is the label Example 3.2 alone, so the Hindi उदाहरण 3.2 stays as its own row)
- ch 3 page 8 §3.5: a new paragraph starts at "(b) किसी चालक के अंदर इलेक्ट्रॉन अपवाह" (English ¶18, question (b) of Example 3.2 (why a steady drift speed), begins here)
- ch 3 page 8 §3.5: a new paragraph starts at "(c) यदि इलेक्ट्रॉन का अपवाह वेग" (English ¶19, question (c) of Example 3.2 (large currents from a small drift speed), begins here)
- ch 3 page 8 §3.5: a new paragraph starts at "(d) जब किसी धातु में इलेक्ट्रॉन" (English ¶20, question (d) of Example 3.2 (do all free electrons move one way), begins here)
- ch 3 page 8 §3.5: a new paragraph starts at "(e) क्या उत्तरोत्तर संघट्टों" (English ¶21, question (e) of Example 3.2 (straight paths between collisions), begins here)
- ch 3 page 8 §3.5: a new paragraph starts at "(a) पूर्ण परिपथ में विद्युत क्षेत्र" (English ¶23, answer (a) of Example 3.2, begins here; English ¶22 is the word Solution alone, so the Hindi हल stays as its own row)
- ch 3 page 8 §3.5: a new paragraph starts at "(c) सरल है, क्योंकि चालक" (English ¶25, answer (c) (the electron number density ~10^29 m^-3), begins here; the Hindi row runs answers (b) to (e) as one paragraph)
- ch 3 page 8 §3.5: a new paragraph starts at "(d) किसी प्रकार नहीं।" (English ¶26, answer (d) (By no means), begins here)
- ch 3 page 8 §3.5: a new paragraph starts at "(e) विद्युत क्षेत्र की अनुपस्थिति में पथ" (English ¶27, answer (e) (straight paths without a field, curved with one), begins here)
- ch 3 page 8 §3.5.1: a new paragraph starts at "गतिशीलता का SI मात्रक m^2/Vs है" (English ¶3, the SI unit of mobility and Eq. (3.25), begins here, after the displayed Eq. (3.24))
- ch 3 page 9 §3.6: a new paragraph starts at "(a) V की I से आनुपातिकता" (English ¶2, deviation (a), begins here, on a line of its own (the decoded layer's line y306); the transcription ran it on in ¶1's paragraph)
- ch 3 page 10 §3.8: "किसी पदार्थ की प्रतिरोधकता प्रति एकांक आयतन" joined onto the paragraph before it (rho's inverse dependence on n and tau, and tau falling as the temperature rises, are the end of English ¶6, which the row before it (Eq. 3.27) begins)
- ch 3 page 11 §3.8: "धातुओं में n की ताप निर्भरता" starts a new paragraph, not the previous page's (English ¶7, n in a metal hardly depending on temperature, begins here at the top of p11; it does not continue ¶6 from p10)
- ch 3 page 11 §3.8: a new paragraph starts at "संबंध R_2 = R_1 [1 + alpha (T_2 – T_1)] का उपयोग" (English ¶11, using R_2 = R_1[1 + alpha(T_2 – T_1)] to get T_2 = 847 °C, begins here, after the displayed R_2 = 85.8 Ω; the Hindi row runs Example 3.3's solution as one paragraph)
- ch 3 page 11 §3.8: a new paragraph starts at "इस प्रकार, तापन अवयव का ताप" (English ¶12, the steady temperature of the heating element (847 °C), begins here)
- ch 3 page 15 §3.10: "समीकरण (3.38) तथा (3.39) को संयोजित करने पर" continues the previous page's paragraph (combining Eqs. (3.38) and (3.39) into Eq. (3.40), at the top of p15, is the end of English ¶7, which the row on p14 (V = IR, Eq. 3.39) begins)
- ch 3 page 16 §3.11: "और हमें प्राप्त होता है" joined onto the paragraph before it (epsilon_eq = epsilon_1 – epsilon_2 (Eq. 3.47) is the end of English ¶6, the two negative terminals joined; the Hindi row was cut at the displayed V_BC)
- ch 3 page 16 §3.11: a new paragraph starts at "(i) n सेलों के श्रेणी संयोजन का" (English ¶8, rule (i) (the equivalent emf of n cells in series), begins here; English ¶7 is the lead-in sentence alone)
- ch 3 page 16 §3.11: a new paragraph starts at "(ii) n सेल के श्रेणी संयोजन का" (English ¶9, rule (ii) (the equivalent internal resistance of n cells in series), begins here)
- ch 3 page 16 §3.11: "चूँकि उतने ही आवेश अन्दर प्रवाहित होते हैं" joined onto the paragraph before it (as much charge flowing in as out, and I = I_1 + I_2 (Eq. 3.48), are the end of English ¶11, the parallel combination, which the row before it begins)
- ch 3 page 18 §3.12: "(a) संधि नियम–किसी संधि पर" joined onto the paragraph before it (step 1 of 3: p18 prints the sentence of English ¶3 twice, as two identical paragraphs (crop p18 x260-530 y170-240; the decoded layer's lines y178 and y208), so neither can be named by its first words; the junction rule joins the second copy for a moment so that the next entry can name it)
- ch 3 page 18 §3.12: "चिह्नों को बनाने कि प्रक्रिया को स्पष्ट करने के बाद अब हम नियमों तथा उपपत्तियों को अभिव्यक्त करेंगे : (a) संधि नियम" joined onto the paragraph before it (step 2 of 3: the print's second copy of English ¶3's sentence has no English of its own; it is kept, joined to the first copy it follows)
- ch 3 page 18 §3.12: a new paragraph starts at "(a) संधि नियम–किसी संधि पर" (step 3 of 3: English ¶4, the junction rule (a), begins here again, as printed)
- ch 3 page 18 §3.12: the English paragraph before the row starting "इस नियम का प्रमाण इस तथ्य से" is not printed in this edition — the row takes the number after it (the Hindi edition does not print English ¶5 ('This applies equally well if instead of a junction of several lines, we consider a point in a line'): rule (a) runs straight into the paragraph on its proof, English ¶6 (crop p18 x265-525 y240-358 at zoom 2.2; p17 and p18 read for the sentence elsewhere); by the founder's ruling of 2026-10-13 ¶5 keeps its English with no Hindi beside it and this row takes ¶6)
- ch 3 page 20 §3.12: "बंद पाश ADCA में किरखोफ के द्वितीय नियम" joined onto the paragraph before it (the loop equations 3.61(a)–(c) and the branch currents are the middle of English ¶15, Example 3.6's solution, which the row before it begins)
- ch 3 page 20 §3.12: "यह आसानी से सत्यापित किया जा सकता है" joined onto the paragraph before it (the check on the remaining loops (BADEB) is the end of English ¶15, Example 3.6's solution)
- ch 3 page 2 §3.3: "तथा धनावेशित इलेक्ट्रॉन एक-दूसरे" → "तथा धनावेशित नाभिक एक-दूसरे" (the page prints 'ऋणावेशित इलेक्ट्रॉन तथा धनावेशित इलेक्ट्रॉन एक-दूसरे' — positively charged electrons (crop p2 x160-525 y436-456 at zoom 5; the decoded layer's line y440 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row's 'the negatively charged electrons and the positively charged nuclei are bound to each other', nuclei written नाभिक as the same paragraph writes it ('व्यष्टिगत नाभिक'))
- ch 3 page 7 §3.5: "तथ्यत: ओम" → "तथ्यतः ओम" (Visarga: an adverb in -tah (in fact), printed with the visarga glyph that this font shares with the colon. Crop p7 x44-408 y97-117 at zoom 4; the decoded layer's line y101 agrees)
- ch 3 page 18 §3.12: "विकर्णत: सम्मुख" → "विकर्णतः सम्मुख" (Visarga: an adverb in -tah (diagonally), printed with the visarga glyph that this font shares with the colon. Crop p18 x172-505 y458-477 at zoom 4; the decoded layer's line y462 agrees)
- ch 3 page 10 §3.8: "धारा वहन करने वाले" → "धारा वहने करने वाले" (the page prints an e-matra on the word (crop p10 x380-525 y684-702 at zoom 10; the decoded layer's line y686 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 3 page 13 §3.9: "यही स्रोत है जिसे" → "यही स्त्रोत है जिसे" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p13 x44-302 y213-232 at zoom 8, the second of the line's two forms; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y216 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 3 page 14 §3.10: "विभवांतर के लिए व्यंजक (I r)" → "विभवांतर के लिए व्यजंक (I r)" (the page sets the anusvara over the third consonant, not the second (crop p14 x318-370 y603-620 at zoom 14; the decoded layer's line y605 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 3 page 17 §3.12: "कोई दूसरा स्रोत) के लिए" → "कोई दूसरा स्त्रोत) के लिए" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p17 x44-300 y681-713 at zoom 6, the second line; the first line's form is sa-ra; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y698 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 3 page 22 §3.13: "I_2 = n = (6.0 × 10^23 / 63.5) × 9.0 × 10^6 I_g = 31.5I_g" → "I_2 = 31.5I_g" (the page prints [3.65(e)] with a stray fragment, I_2 = n = (6.0 × 10^23 / 63.5) × 9.0 × 10^6 I_g = 31.5 I_g (crop p22 x168-512 y365-450 at zoom 4; the layer's lines y403-427 agree), equating I_2 to about 8.5 × 10^28 I_g; the English row and the arithmetic of 63 I_g − 2 I_2 = 0 give I_2 = 31.5 I_g; by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 3 page 22 §3.13: "10 (I − I_g)" → "10 (I_1 − I_g)" (the page prints the mesh BCDB equation as 10 (I − I_g) − 15 I_g − 5 (I_2 + I_g) = 0 with no subscript on the first I (crop p22 x165-450 y210-245 at zoom 6; the decoded layer's line y224 agrees), while the very next line expands it as 10 I_1 − 30 I_g − 5 I_2 = 0; the arithmetic and the English row's 10 (I_1 – I_g) give I_1; a dropped subscript, so by the founder's ruling of 2026-10-10 the row writes the meaning. The meaning entry before it in this row, on the [3.65(e)] fragment, does not reach this span)
- ch 4 page 3 §4.2.2: a new paragraph starts at "(i) यह q, v तथा B" (English ¶3, item (i) (the force depends on q, v and B), begins here; the Hindi print runs it on at the end of ¶2's paragraph)
- ch 4 page 5 §4.2.3: a new paragraph starts at "अधिकांश प्रकरणों में संकलन को समाकलन" (English ¶3, the summation converted to an integral, begins here after the displayed sum; the Hindi print keeps it in ¶2's paragraph)
- ch 4 page 7 §4.3: "कोणीय आवृत्ति omega वेग अथवा ऊर्जा" continues the previous page's paragraph (omega's independence of velocity or energy and the cyclotron remark, at the top of p7, are the end of English ¶4, which the row on p6 begins and which runs through Eq. [4.6(a)])
- ch 4 page 10 §4.5: "चित्र 4.9 के लिए cos theta" joined onto the paragraph before it (cos theta for Fig. 4.9, Eq. (4.10), is the end of English ¶5; the Hindi print sets it as a new paragraph after the integration sentence)
- ch 4 page 10 §4.5: a new paragraph starts at "समीकरणों (4.9) और (4.10), dB_x" (English ¶6, dB_x from Eqs. (4.9) and (4.10), begins here)
- ch 4 page 10 §4.5: a new paragraph starts at "समस्त पाश पर dl अवयवों का संकलन" (English ¶7, the summation of dl over the loop (2πR) and Eq. (4.11), begins here)
- ch 4 page 10 §4.5: "उपरोक्त परिणाम की एक विशेष स्थिति" joined onto the paragraph before it (the special case at the centre of the loop, Eq. (4.12), is the end of English ¶7; the Hindi print starts a new paragraph at it)
- ch 4 page 13 §4.6: "तार को अपने दाएँ हाथ में इस प्रकार" joined onto the paragraph before it (the rule itself (grasp the wire in the right hand, fingers curl with the field) is the end of English ¶9, item (iv), which runs on after 'This rule ... is:'; the Hindi print sets it as its own paragraph)
- ch 4 page 13 §4.6: the paragraph starting "* कृपया ध्यान दीजिए" takes its number after the next paragraph of its section (the footnote on the two right-hand rules is English ¶12, numbered after Example 4.7's statement (¶11); the Hindi prints it at the foot of p13, before the example, which opens p14)
- ch 4 page 15 §4.6: a new paragraph starts at "इस उदाहरण में आवश्यक सममिति विद्यमान है" (English ¶16, the example possessing the symmetry Ampere's law needs, begins here; the Hindi print keeps it in ¶15's paragraph)
- ch 4 page 18 §4.8: a new paragraph starts at "समांतर धाराएँ आकर्षित तथा प्रतिसमांतर धाराएँ" (English ¶7, the rule 'Parallel currents attract, and antiparallel currents repel', begins here; the English ends ¶6 on 'Thus,' inside the sentence, so the split leaves 'इस प्रकार' with ¶6)
- ch 4 page 19 §4.8: a new paragraph starts at "यह ऐम्पियर की परिभाषा में वर्णित बल" (English ¶19, the force being larger than the 2×10^-7 N m^-1 of the ampere's definition, begins here; the Hindi row ran Example 4.9's part (a) as one paragraph)
- ch 4 page 19 §4.8: a new paragraph starts at "बल की दिशा अधोमुखी है।" (English ¶20, the direction of the force being downwards, begins here)
- ch 4 page 18 §4.8: the row starting "* इससे यह अर्थ निकलता है" is numbered after the row starting "उपरोक्त व्यंजक का उपयोग विद्युत धारा" (page 18) (the footnote on Newton's third law, printed at the foot of p18 after Example 4.9's first solution line, is English ¶11 and goes back after ¶10, the ampere defined from Eq. (4.19))
- ch 4 page 23 §4.9.2: a new paragraph starts at "ध्यान दीजिए, पाश का क्षेत्रफल A = pi R^2" (English ¶2, the loop's area A = πR² and B in terms of A, begins here; the Hindi row runs ¶1 to ¶3 as one paragraph)
- ch 4 page 23 §4.9.2: a new paragraph starts at "जैसा कि पहले हमने चुंबकीय आघूर्ण m" (English ¶3, the magnetic moment m = IA and Eq. [4.25(a)], begins here)
- ch 4 page 25 §4.10: a new paragraph starts at "कोष्ठक की राशि का मान किसी दिए गए" (English ¶3, the bracketed quantity being a constant for a given galvanometer, begins here after Eq. (4.26); the Hindi row runs ¶2 to ¶4 as one paragraph)
- ch 4 page 25 §4.10: a new paragraph starts at "गैल्वेनोमीटर का उपयोग कई प्रकार से" (English ¶4, the galvanometer used as a detector, begins here)
- ch 4 page 26 §4.10: a new paragraph starts at "(b) ऐमीटर में रूपांतरित गैल्वेनोमीटर" (English ¶14, part (b) of Example 4.12's solution (the shunted galvanometer), begins here; the Hindi row runs the solution as one paragraph)
- ch 4 page 26 §4.10: a new paragraph starts at "(c) शून्य प्रतिरोध के आदर्श ऐमीटर" (English ¶15, part (c) (the ideal ammeter, I = 1.00 A), begins here)
- ch 4 page 26 §4.10: "(60 + 0.02) Ω - 0.02 Ω" → "(60 + 0.02) Ω ≃ 0.02 Ω" (the page sets a short text hyphen between the fraction and its value, R_G r_s/(R_G + r_s) = 60 Ω × 0.02Ω/(60 + 0.02)Ω - 0.02Ω (crop p26 x170-410 y610-662 at zoom 5 and x290-340 y636-656 at zoom 12; the decoded layer's line y641 agrees), which reads as subtracting 0.02 Ω; by the founder's ruling of 2026-10-10 the row writes the meaning: the English row's 60 Ω × 0.02Ω / (60 + 0.02)Ω ≃ 0.02Ω, the shunted galvanometer's resistance that the next line adds to 3 Ω)
- ch 4 page 1 §4.1: "केंद्रत: वृत्त" → "केंद्रतः वृत्त" (Visarga: the word is an adverb in -tah (centred on the wire's position) in the middle of a clause, where a colon cannot stand; printed with the visarga glyph that this font shares with the colon. Crop p1 x44-408 y538-557 at zoom 4; the decoded layer's line y541 agrees)
- ch 4 page 13 §4.6: "जैसे अन्त: क्षेत्र" → "जैसे अन्तः क्षेत्र" (Visarga: antah (inner), the word the load already restores as अन्तः on p16, printed with the visarga glyph that this font shares with the colon. Crop p13 x44-326 y591-611 at zoom 4; the decoded layer's line y595 agrees)
- ch 4 page 17 §4.8: "पार्श्वत: एक" → "पार्श्वतः एक" (Visarga: an adverb in -tah (sideways), printed with the visarga glyph that this font shares with the colon. Crop p17 x280-408 y505-524 at zoom 5; the decoded layer's line y508 agrees)
- ch 4 page 1 §4.1: "समांतर सरेखित होती है" → "समांतर संरेखित होती है" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p1 x42-120 y553-570 at zoom 10; the decoded layer's line y556 agrees); the transcription dropped it)
- ch 4 page 1 §4.1: "विपरीत दिशा में सरेखित हो जाती" → "विपरीत दिशा में संरेखित हो जाती" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p1 x200-330 y598-615 at zoom 6; the decoded layer's line y601 agrees); the transcription dropped it)
- ch 4 page 2 §4.1: "उत्पन्न, प्रवर्धित, प्रेषित" → "उत्पन्न, प्रबर्धित, प्रेषित" (the page prints ba, not va, after the first conjunct (crop p2 x160-420 y129-146 at zoom 7; the decoded layer's line y131 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 4 page 12 §4.6: "पाश में परिविद्ध विद्युत धारा" → "पाश में परिवद्ध विद्युत धारा" (the page prints no i-matra on the third consonant (crop p12 x159-275 y540-557 at zoom 9; the decoded layer's line y542 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the row's form is a misreading)
- ch 4 page 20 §4.9.1: "अनुदिश सरेखित होने" → "अनुदिश संरेखित होने" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p20 x274-380 y260-277 at zoom 6; the decoded layer's line y262 agrees); the transcription dropped it)
- ch 4 page 20 §4.9.1: "ये सरेख नहीं हैं" → "ये संरेख नहीं हैं" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p20 x292-400 y337-354 at zoom 6; the decoded layer's line y339 agrees); the transcription dropped it)
- ch 4 page 20 §4.9.1: "बल सरेख बन जाते" → "बल संरेख बन जाते" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p20 x159-320 y536-553 at zoom 6; the decoded layer's line y538 agrees); the transcription dropped it)
- ch 4 page 21 §4.9.1: "स्वयं को सरेखित कर लेता" → "स्वयं को संरेखित कर लेता" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p21 x40-400 y238-382 at zoom 3; the decoded layer's line y241 agrees); the transcription dropped it)
- ch 4 page 21 §4.9.1: "व्यास से सरेखित है" → "व्यास से संरेखित है" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p21 x40-400 y238-382 at zoom 3; the decoded layer's line y367 agrees); the transcription dropped it)
- ch 4 page 9 §4.5: "x y तल में स्थित" → "y z तल में स्थित" (the page prints the loop as lying in the x y plane (crop p9 x40-410 y590-615 at zoom 5; the decoded layer's line y598 agrees), while the next sentence makes the x-axis the loop's axis and Fig. 4.9 beside it draws the loop in the Y-Z plane (crop p9 x320-530 y380-720 at zoom 2); a loop in the x-y plane cannot have the x-axis as its axis; the English row reads 'placed in the y-z plane'; by the founder's ruling of 2026-10-10 the row writes the meaning, in the print's own form of the plane's name)
- ch 4 page 21 §4.9.1: "चुंबकीय क्षेत्र की विमाएँ" → "चुंबकीय आघूर्ण की विमाएँ" (the page prints 'as is clear from Eq. (4.22), the dimensions of the magnetic field are [AL^2] and its unit is Am^2' (crop p21 x40-410 y95-130 at zoom 5; the decoded layer's line y100 agrees); Eq. (4.22) defines the magnetic moment m = I A, whose dimensions and unit these are, while the magnetic field's unit is the tesla; the English row reads 'the dimensions of the magnetic moment'; by the founder's ruling of 2026-10-10 the row writes the meaning, in this section's own word for the moment)
- ch 4 page 22 §4.9.1: "से 𝒥 dω/dt" → "से I dω/dt" (the page prints the moment of inertia as a script capital I that sits on the baseline with no descender, as a script I does and a script J does not (crop p22 x193-300 y128-160 at zoom 14; the layer's glyph at y146 agrees, by position); the row's script J names another letter; the rows carry no type style, bold or script, so by the founder's ruling of 2026-10-13 the row writes the moment of inertia as a plain I, as the English row does)
- ch 4 page 22 §4.9.1: "= m sin theta" → "= m B sin theta" (the page prints Newton's second law for the coil as I dω/dt = m sin θ, without B (crop p22 x175-430 y105-355 at zoom 4; the decoded layer's line y137 agrees), a torque without the field; Eq. (4.23) above, the next lines of this solution (m B sin θ dθ) and the English row (mB sin theta) give m B sin θ; a dropped symbol that changes the physics, so by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 4 page 22 §4.9.1: "𝒥 कुंडली" → "I कुंडली" (the page prints the moment of inertia as a script capital I on the baseline, no descender (crop p22 x193-260 y155-172 at zoom 14; the layer's glyph at y170 agrees, by position); the row's script J names another letter; the rows carry no type style, bold or script, so by the founder's ruling of 2026-10-13 the row writes the moment of inertia as a plain I, as the English row does)
- ch 4 page 22 §4.9.1: "(dω/dt) ω" → "(dω/d theta) ω" (the page prints the chain rule as dω/dt = (dω/dθ)(dθ/dt) = (dω/dt) ω, the last fraction over dt (crop p22 x195-305 y172-206 at zoom 10; the decoded layer's denominators at y193 agree), which makes ω equal to 1; the English row's (dω/dtheta) ω and the next line's ω dω = m B sin θ dθ give dω/dθ; by the founder's ruling of 2026-10-10 the row writes the meaning, in the row's own spelling of the fraction before it)
- ch 4 page 22 §4.9.1: "पर, 𝒥 ω dω" → "पर, I ω dω" (the page prints the moment of inertia as a script capital I on the baseline, no descender (crop p22 x175-430 y105-355 at zoom 4; the layer's glyph at y235 agrees, by position); the row's script J names another letter; the rows carry no type style, bold or script, so by the founder's ruling of 2026-10-13 the row writes the moment of inertia as a plain I, as the English row does)
- ch 4 page 22 §4.9.1: "पर, 𝒥 integral" → "पर, I integral" (the page prints the moment of inertia before the integral as a script capital I on the baseline, no descender, the same shape as the four the layer places on this page (crop p22 x193-300 y255-290 at zoom 10; the text layer carries no glyph on this line); the row's script J names another letter; the rows carry no type style, bold or script, so by the founder's ruling of 2026-10-13 the row writes the moment of inertia as a plain I, as the English row does)
- ch 4 page 22 §4.9.1: "d theta 𝒥 ω_f^2" → "d theta I ω_f^2" (the page prints the moment of inertia as a script capital I on the baseline, no descender (crop p22 x175-430 y105-355 at zoom 4; the layer's glyph at y310 agrees, by position); the row's script J names another letter; the rows carry no type style, bold or script, so by the founder's ruling of 2026-10-13 the row writes the moment of inertia as a plain I, as the English row does)
- ch 4 page 22 §4.9.1: "/ 𝒥)^(1/2)" → "/ I)^(1/2)" (the page prints the moment of inertia in the denominator as a script capital I, no descender, the same shape as the four the layer places on this page (crop p22 x193-300 y322-358 at zoom 10; the text layer carries no glyph for this fraction); the row's script J names another letter; the rows carry no type style, bold or script, so by the founder's ruling of 2026-10-13 the row writes the moment of inertia as a plain I, as the English row does)
- ch 4 page 23 §4.9.2: "B mu_0 m / (2 pi x^3)" → "B ≃ mu_0 m / (2 pi x^3)" (the page draws no relation sign between B and the fraction of [4.25(a)], a blank where the second line has its = (crop p23 x55-410 y340-418 at zoom 5; the layer has a symbol-font glyph at that gap that the raster does not draw); the English row reads B ≃ mu_0 m / (2 pi x^3), the approximation for x >> R; a relation sign not drawn, so by the founder's ruling of 2026-10-10, confirmed for this place on 2026-10-13, the row writes the meaning)
- ch 4 page 25 §4.10: "R_G + R R :" → "R_G + R ≃ R :" (the page draws no relation sign between R_G + R and R, a blank (crop p25 x55-260 y668-694 at zoom 8; the layer has a symbol-font glyph at that gap that the raster does not draw); the English row reads R_G + R ≃ R : large, the voltmeter's resistance; a relation sign not drawn, so by the founder's ruling of 2026-10-10, confirmed for this place on 2026-10-13, the row writes the meaning, as the entry for the same sign in §4.10 on p26 does)
- ch 4 page 8 §4.4: "4pi 10^(-7)" → "4pi × 10^(-7)" (the page draws a gap between 4π and 10⁻⁷ (crop p8 x150-262 y636-660 at zoom 8); the text layer has a Bookman Unicode span in that gap, which the raster does not draw; by the founder's ruling of 2026-10-13, as for phy11-part1's four × (DECISIONS 2026-10-03), the row writes the English row's sign, 4 pi × 10^(-7), in the gap the page leaves)
- ch 4 page 12 §4.6: "integral B dl = mu_0 I [4.13(a)]" → "integral(closed) B·dl = mu_0 I [4.13(a)]" (the page draws a plain integral sign and a space between B and dl in Eq. [4.13(a)] (crop p12 x178-245 y300-324 at zoom 14; wider, x180-525 y295-325 at zoom 5); the loop's circle and the dot are only text-layer glyphs there, which the raster does not draw. Three lines lower the same page draws the loop integral with both (crop p12 x440-525 y358-376 at zoom 14), and the English row reads integral(closed) B·dl = mu_0 I [4.13(a)]; by the founder's ruling of 2026-10-13 the row writes the equation as the English row has it)
- ch 5 page 1 §5.1: a new paragraph starts at "चुंबकत्व संबंधी कुछ आम विचार" (English ¶4, the commonly known ideas and item (i), the earth as a magnet, begins here; the Hindi print keeps it in ¶3's paragraph)
- ch 5 page 2 §5.2.1: the row starting "* कुछ पाठ्यपुस्तकों में चुंबकीय" is numbered after the row starting "(i) किसी चुंबक (या धारावाही परिनालिका)" (page 2) (the footnote on magnetic lines of force, printed at the foot of p2 after items (ii) and (iii), is English §5.2.1 ¶3 and goes back after ¶2, item (i))
- ch 5 page 4 §5.2.3: "यहाँ tau प्रत्यानयन आघूर्ण है" joined onto the paragraph before it ('here tau is the restoring torque' is the end of English ¶2, which holds Eq. (5.2) and its magnitude tau = mB sin theta)
- ch 5 page 5 §5.2.3: "(c) क्या प्रत्येक चुंबकीय विन्यास" continues the previous page's paragraph (Example 5.1's question (c), at the top of p5, is the end of English ¶7, which holds questions (b) and (c); it joins (b) at the foot of p4)
- ch 5 page 5 §5.2.4: a new paragraph starts at "इसी प्रकार, r दूरी (r >> l के लिए) पर" (English ¶2, 'Likewise' and the axial field B_A of Eq. (5.5), begins here; the Hindi row runs it on after Eq. (5.4))
- ch 5 page 6 §5.2.4: a new paragraph starts at "(a) किस विन्यास में यह निकाय" (English ¶5, Example 5.2's question (a), begins here; the Hindi row runs the questions on after the statement)
- ch 5 page 6 §5.2.4: a new paragraph starts at "(b) किस विन्यास में निकाय (i)" (English ¶6, Example 5.2's question (b), stable and unstable equilibrium, begins here)
- ch 5 page 6 §5.2.4: a new paragraph starts at "(c) दिखाए गए सभी विन्यासों में" (English ¶7, Example 5.2's question (c), the lowest potential energy, begins here)
- ch 5 page 6 §5.2.4: "उदाहरणार्थ, विन्यास Q_3 में" joined onto the paragraph before it (the configuration Q_3 example is the rest of English ¶9, which begins with the stable and unstable equilibrium sentence)
- ch 5 page 6 §5.2.4: "इस प्रकार, (a) PQ_1 एवं PQ_2" joined onto the paragraph before it ('Thus,' ends English ¶9; the row joins ¶9 so that the split after it leaves 'Thus,' there and starts answer (a) as ¶10)
- ch 5 page 6 §5.2.4: a new paragraph starts at "(a) PQ_1 एवं PQ_2" (English ¶10, answer (a) PQ_1 and PQ_2, begins here)
- ch 5 page 6 §5.2.4: a new paragraph starts at "(b) (i) PQ_3, PQ_6" (English ¶11, answer (b), stable PQ_3, PQ_6 and unstable PQ_5, PQ_4, begins here)
- ch 5 page 6 §5.2.4: a new paragraph starts at "(c) PQ_6" (English ¶12, answer (c) PQ_6, begins here)
- ch 5 page 8 §5.3: "चुंबकत्व के लिए गाउस का नियम" starts a new paragraph, not the previous page's (English ¶5, 'Thus, Gauss's law for magnetism is:', begins here at the top of p8; the row ran on from p7)
- ch 5 page 8 §5.3: a new paragraph starts at "किसी भी बंद सतह से गुज़रने वाला" (English ¶6, the statement of the law, the net flux through any closed surface is zero, begins here)
- ch 5 page 9 §5.3: a new paragraph starts at "(a) गलत है। चुंबकीय बल रेखाएँ" (English ¶9, Example 5.3's answer (a), begins here; the English keeps 'Solution' as ¶8, a row of its own, so the Hindi 'हल' stays as ¶8)
- ch 5 page 9 §5.3: a new paragraph starts at "(a) चुंबकीय क्षेत्र रेखाएँ (हर बिंदु पर)" (English ¶17, Example 5.4's question (a), begins here; the English keeps 'Example 5.4' as ¶16, a row of its own)
- ch 5 page 9 §5.3: a new paragraph starts at "(b) यदि चुंबकीय एकल ध्रुवों" (English ¶18, Example 5.4's question (b), Gauss's law if monopoles existed, begins here)
- ch 5 page 10 §5.4: "M एक सदिश राशि है" joined onto the paragraph before it ('M is a vector' with its dimensions and unit is the end of English ¶2, after Eq. (5.7))
- ch 5 page 11 §5.4: "इस प्रकार कुल चुंबकीय क्षेत्र B" joined onto the paragraph before it (the total field B = mu_0 (H + M), Eq. (5.12), is the end of English ¶5, which begins with H, Eq. (5.11))
- ch 5 page 11 §5.4: "जहाँ chi एक विमाविहीन राशि" joined onto the paragraph before it (the 'where chi' text through Eqs. (5.14), (5.15) and mu = mu_0 mu_r is the rest of English ¶6, which begins 'We repeat our defining procedure' and holds Eq. (5.13))
- ch 5 page 14 §5.5.3: a new paragraph starts at "किसी लौह चुंबकीय पदार्थ के एकल परमाणुओं" (English ¶2, the individual atoms of a ferromagnetic material and domains, begins here; the Hindi print keeps it in ¶1's paragraph)
- ch 5 page 1 §5.1: "भाँति-भाँति के स्रोतों से" → "भाँति-भाँति के स्त्रोतों से" (the page sets the sa-ta-ra ligature here, not the sa-ra conjunct (crop p1 x44-330 y481-498 at zoom 6; compared at zoom 16 with the sa-ra glyph of ch 3 p13 x98-118 y214-232 and the ligature at x272-292 on the same line; the decoded layer's line y483 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 5 page 5 §5.2.4: "द्विध्रुव आघूर्णो वाले" → "द्विध्रुव आघूर्णों वाले" (the page prints an anusvara beside the reph on the last syllable (crop p5 x300-410 y509-526 at zoom 9; the decoded layer's line y511 agrees); the transcription dropped it)
- ch 5 page 10 §5.3: "चुंबकीय आघूर्णो का औसत" → "चुंबकीय आघूर्णों का औसत" (the page prints an anusvara beside the reph on the last syllable (crop p10 x188-505 y387-417 at zoom 5; the decoded layer's line y389 agrees); the transcription dropped it)
- ch 5 page 10 §5.3: "अनुचुंबकीय पदार्थो के संदर्भ" → "अनुचुंबकीय पदार्थों के संदर्भ" (the page prints an anusvara beside the reph on the last syllable (crop p10 x188-505 y387-417 at zoom 5; the decoded layer's line y403 agrees); the transcription dropped it)
- ch 5 page 11 §5.4: "वही नियतांक है" → "वही नियंताक है" (the page sets the anusvara on the second syllable, not the third (crop p11 x40-200 y203-220 at zoom 9; the decoded layer's line y205 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 5 page 13 §5.5.1: "ये परिक्रमण करते" → "ये प्ररिक्रमण करते" (the page prints a ra-stroke under the first consonant as well (crop p13 x180-290 y176-193 at zoom 10; the decoded layer's line y178 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 5 page 14 §5.5.2: "अनुदिश सरेखित हो जाते" → "अनुदिश संरेखित हो जाते" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p14 x160-525 y117-288 at zoom 3; the decoded layer's line y119 agrees); the transcription dropped it)
- ch 5 page 14 §5.5.3: "दिशा में सरेखित हो जाते" → "दिशा में संरेखित हो जाते" (the page prints an anusvara on the first syllable, set just before the e-matra (crop p14 x160-525 y117-288 at zoom 3; the decoded layer's line y273 agrees); the transcription dropped it)
- ch 6 page 5 §6.4: "प्रेरित विद्युत वाहक बल का परिमाण चुंबकीय फ्लक्स में" joined onto the paragraph before it (the statement of Faraday's law printed on p5 is the last sentence of English ¶2, which the row from p4 begins and which ends with the law as stated)
- ch 6 page 5 §6.4: a new paragraph starts at "ऋण चिह्न epsilon की दिशा तथा परिणामतः" (English ¶5, the negative sign giving the direction of epsilon, begins here after Eq. (6.3), which ends English ¶4)
- ch 6 page 5 §6.4: a new paragraph starts at "(a) अधिक विक्षेप प्राप्त करने के लिए" (English ¶11, answer (a) of Example 6.1, begins here; the English keeps the label Solution as ¶10 on its own, so the Hindi label हल stays alone as ¶10)
- ch 6 page 5 §6.4: a new paragraph starts at "प्रयोगात्मक भौतिकी में हमें नवीनता लाने" (English ¶13, on innovation in experimental physics and Faraday, begins here; the Hindi row runs it on after answer (b))
- ch 6 page 6 §6.4: "ध्यान दें कि पृथ्वी का चुंबकीय क्षेत्र भी" joined onto the paragraph before it (the note on the earth's steady field is the last sentence of English ¶15, Example 6.2's solution, which the row before it begins)
- ch 6 page 6 §6.4: "ध्यान दें कि ये epsilon तथा I के" joined onto the paragraph before it (the note that epsilon and I are estimated values is the end of English ¶17, Example 6.3's solution, which the row before it begins)
- ch 6 page 7 §6.5: "प्रेरित विद्युत वाहक बल की दिशा लेंज के नियम" joined onto the paragraph before it (the direction by Lenz's law and Figs. 6.6 (a) and (b) are the rest of English ¶4, the open circuit, which the row before it begins)
- ch 6 page 8 §6.5: "नोट कीजिए कि जब तक लूप पूरी तरह" joined onto the paragraph before it (the note that no current flows while a loop is wholly inside or outside the field is the last sentence of English ¶10, answer (iii) of Example 6.4)
- ch 6 page 9 §6.5: "(d) चित्र 6.9 में वर्णित स्थिति" joined onto the paragraph before it (question (d) on the capacitor's polarity is the end of English ¶13, which carries parts (c) and (d) of Example 6.5 as one row)
- ch 6 page 9 §6.5: a new paragraph starts at "(b) नहीं। विद्युत फ्लक्स परिवर्तित करके" (English ¶15, answer (b) of Example 6.5, begins here; the Hindi row runs it on after answer (a))
- ch 6 page 10 §6.6: "आवेश को P से Q तक ले जाने" joined onto the paragraph before it (the work done in moving the charge from P to Q, W = qvBl, is part of English ¶3 (the Lorentz-force account), which the row before it begins)
- ch 6 page 10 §6.6: "चूँकि प्रति इकाई आवेश पर किया गया" joined onto the paragraph before it (emf as work per unit charge, epsilon = W/q = Blv, is the end of English ¶3)
- ch 6 page 11 §6.6: a new paragraph starts at "यह व्यंजक प्रथम विधि द्वारा प्राप्त" (English ¶9, the result agreeing with Method I, begins here; the Hindi row runs it on after Method II)
- ch 6 page 12 §6.6: a new paragraph starts at "क्योंकि स्पोक के आरपार विद्युत वाहक बल" (English ¶12, the number of spokes being immaterial, begins here; the Hindi row runs it on after Example 6.7's solution)
- ch 6 page 13 §6.7.1: a new paragraph starts at "समीकरण (6.9) तथा समीकरण (6.10) का उपयोग" (English ¶9, M_12 = M_21 = M from Eqs. (6.9) and (6.10), begins here after Eq. (6.11), which ends English ¶8)
- ch 6 page 14 §6.7.1: "ध्यान दीजिए कि हमने M_12 की गणना" joined onto the paragraph before it (the note on the approximate value of Phi_1 is the end of English ¶14, Example 6.8's solution, which the row before it begins)
- ch 6 page 16 §6.7.2: "यह व्यंजक हमें m द्रव्यमान के" joined onto the paragraph before it (the comparison with mv^2/2 and L as electrical inertia is the end of English ¶7, which the row before it (Eq. 6.17) begins)
- ch 6 page 16 §6.7.2: a new paragraph starts at "(a) समीकरण (6.17) से, चुंबकीय ऊर्जा है" (English ¶12, answer (a) of Example 6.9, begins here; the English keeps the label Solution as ¶11 on its own, so the Hindi label हल stays alone as ¶11)
- ch 6 page 18 §6.8: "क्योंकि ज्या फलन (sine function) का मान" joined onto the paragraph before it (the sign of the emf changing with time and its extremum at theta = 90° or 270° (Fig. 6.14) are the end of English ¶5, which the row before it (Eqs. 6.19 and 6.20) begins)
- ch 6 page 19 §6.8: "क्योंकि धारा की दिशा आवर्ती रूप से" starts a new paragraph, not the previous page's (English ¶6, alternating current and Eq. (6.21), begins here at the top of p19; the transcription ran it on from the p18 paragraph)
- ch 6 page 5 §6.4: "प्रेरित विद्युत धारा बल को" → "प्रेरित विद्युत वाहक बल को" (the page prints 'गणितीय रूप में प्रेरित विद्युत धारा बल को' — induced current force — before epsilon = − dPhi_B / dt (6.3) (crop p5 x250-525 y155-215 at zoom 5; the decoded layer's line y164 agrees); by the founder's ruling of 2026-10-10 the row writes the meaning: the English row's 'the induced emf is given by', emf being विद्युत वाहक बल as this section writes it everywhere else, the same page included ('प्रेरित विद्युत वाहक बल को', crop p5 x250-525 y335-355 at zoom 4))
- ch 6 page 2 §6.2: "धारा को निर्दशित करता है" → "धारा को निदर्शित करता है" (the page prints the word with a reph over the third syllable and none before it (crop p2 x42-200 y647-664 at zoom 9; the decoded layer's line y649 agrees), as on ch 1 p5 and p37: the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 6 page 7 §6.5: "ध्यान दीजिए कि [illegible] तथा [illegible] द्वारा" → "ध्यान दीजिए कि तथा द्वारा" (the page draws two small arrow pictures here (crop p7 x178-525 y455-505 at zoom 5); they are no characters and the text layer carries nothing there. The canonical English row leaves the same pictures out, so the transcriber's markers are removed rather than replaced (founder, 2026-10-13))
- ch 6 page 10 §6.6: "के साथ परिवर्तनशील चुंबकीय क्षेत्र एक" → "के साथ परितवर्तनशील चुंबकीय क्षेत्र एक" (the page prints an extra ta after the second syllable (crop p10 x150-300 y538-555 at zoom 10; the decoded layer's line y540 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 6 page 10 §6.6: "50 चक्कर/सेकंड की" → "50 चक्कर/सेंकड की" (the page sets the anusvara on the first syllable, not the second (crop p10 x230-330 y647-663 at zoom 12; the decoded layer's line y649 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 6 page 16 §6.7.2: "रूप में रूपांतरित हो जाएगी" → "रूप में रूपातंरित हो जाएगी" (the page sets the anusvara on the third syllable, not the second (crop p16 x140-262 y467-484 at zoom 12; the decoded layer's line y469 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 6 page 3 §6.2: "उपरोक्त दोनों प्रयोगों में चुंबक" → "प्रयोग 6.3 उपरोक्त दोनों प्रयोगों में चुंबक" (the page prints the label 'प्रयोग 6.3' in italic on its own line above the paragraph (crop p3 x40-530 y125-180 at zoom 4; the decoded layer's line y138 agrees), and the row dropped it; the rows for experiments 6.1 and 6.2 carry their labels as their first words, as the English row opens 'Experiment 6.3')
- ch 6 page 17 §6.7.2: "ऊर्जा क्षेत्र की तीव्रता के समानुपाती" → "ऊर्जा क्षेत्र की तीव्रता के वर्ग के समानुपाती" (the page prints 'in both cases the energy is proportional to the intensity of the field', without 'square' (crop p17 x170-510 y365-384 at zoom 5; the decoded layer's line y369 agrees), right after the two energy densities u_B = B^2/2mu_0 and u_E = epsilon_0 E^2/2 that go as the square; the English row reads 'proportional to the square of the field strength'; a dropped word that changes the physics, so by the founder's ruling of 2026-10-10 the row writes the meaning, in the book's own phrase for a square ('दूरी के वर्ग के', ch 1 §1.5))
- ch 7 page 2 §7.2: "यहाँ धारा आयाम i_m के लिए सूत्र है" joined onto the paragraph before it (the current amplitude i_m = v_m/R, Eq. (7.3), is the end of English ¶2, which the row before it begins; the Hindi row broke it off after Eq. (7.2))
- ch 7 page 4 §7.2: a new paragraph starts at "(a) दिया है P = 100 W" (English ¶13 is the label Solution alone and ¶14, part (a) with P = 100 W and R = 484 Ω, begins here; the Hindi row runs the label हल into (a))
- ch 7 page 8 §7.5: a new paragraph starts at "धारा का मान ज्ञात करने के लिए हम संबंध i = dq / dt" (English ¶4, the current from i = dq/dt, begins here; the Hindi print keeps it in ¶3's paragraph)
- ch 7 page 8 §7.5: a new paragraph starts at "संबंध, cos(omega t) = sin(omega t + pi/2), का उपयोग करने से" (English ¶5, cos(omega t) = sin(omega t + pi/2) and Eqs. (7.16) to (7.18), begins here; the Hindi print keeps it in ¶3's paragraph)
- ch 7 page 9 §7.5: a new paragraph starts at "चित्र 7.8 (a) किसी क्षण t_1 पर फेजर आरेख" (English ¶8, the phasor diagram of Fig. 7.8(a), begins here; the Hindi print keeps it in ¶7's paragraph)
- ch 7 page 9 §7.5: a new paragraph starts at "इस प्रकार हम देखते हैं कि प्रेरक के प्रकरण में" (English ¶10, the current lagging in an inductor and leading in a capacitor by pi/2, begins here; the Hindi print keeps it in ¶9's paragraph)
- ch 7 page 10 §7.5: a new paragraph starts at "स्विच को बंद कर दिया गया है" (English ¶17, the switch closed and the iron rod inserted, begins here; the Hindi row keeps it with Example 7.5's opening sentence)
- ch 7 page 10 §7.5: "छड़ को प्रविष्ट कराते समय" joined onto the paragraph before it (the bulb's glow (a) increases (b) decreases (c) is unchanged is the end of English ¶17, which the split before it begins)
- ch 7 page 11 §7.6.1: a new paragraph starts at "समीकरण (7.22) से v_Rm, v_Cm, एवं v_Lm के मान" (English ¶7, substituting Eq. (7.22) to give Eq. 7.25(a), begins here; the Hindi print keeps it in ¶6's paragraph)
- ch 7 page 11 §7.6.1: a new paragraph starts at "किसी परिपथ में प्रतिरोध से समतुल्यता के आधार पर" (English ¶8, the impedance Z by analogy to resistance and Eq. (7.26), begins here; the Hindi print keeps it in ¶6's paragraph)
- ch 7 page 14 §7.6.2: "दोनों वोल्टताओं V_R एवं V_C का बीजगणितीय योग" joined onto the paragraph before it (the algebraic sum of 311.3 V, the paradox and V_(R+C) = 220 V are the rest of English ¶10, part (b) of Example 7.6's solution, which the row before it begins)
- ch 7 page 14 §7.6.2: a new paragraph starts at "इस प्रकार, यदि दो वोल्टताओं के बीच के कला-अंतर" (English ¶11, the total voltage equal to the source's once the phase difference is taken into account, begins here)
- ch 7 page 15 §7.7: "एक पूर्ण चक्र में माध्य शक्ति समीकरण (7.29)" joined onto the paragraph before it (the average power over a cycle and Eq. 7.30(a) are the rest of English ¶2, which the row with Eq. (7.29) begins)
- ch 7 page 15 §7.7: "अतः क्षयित माध्य शक्ति, न केवल" joined onto the paragraph before it (the dependence on cos phi, the power factor and 'let us discuss the following cases' are the rest of English ¶3, which the row with Eq. 7.30(b) begins)
- ch 7 page 15 §7.7: a new paragraph starts at "(b) परिपथ का शक्ति गुणांक, प्रायः" (English ¶9, part (b) of Example 7.7's statement (the power factor improved by a capacitor), begins here; the Hindi row runs (a) and (b) as one)
- ch 7 page 16 §7.7: "हम शक्ति गुणांक का सुधार कर इसका मान" starts a new paragraph, not the previous page's (English ¶12, improving the power factor by making Z tend to R, begins here at the top of p16; the row ran on from (b)'s cos phi = R/Z on p15, which is English ¶11)
- ch 7 page 17 §7.7: a new paragraph starts at "अनुनाद स्थिति में rms धारा = V / Z" (English ¶22, the rms current at resonance (66.7 A), begins here; the Hindi row runs part (b) of Example 7.9's solution as one)
- ch 7 page 17 §7.7: a new paragraph starts at "अनुनाद स्थिति में शक्ति क्षय P = I^2 × R" (English ¶23, the power dissipated at resonance (13.35 kW), begins here)
- ch 7 page 3 §7.2: "(P = I^2R)" → "(P = i^2R)" (the page prints dc power as (P = i^2R) with a lower-case italic i (crop p3 x235-525 y410-445 at zoom 5; the decoded layer's line y432 agrees); the row's capital I is the transcriber's repair to the English; as printed, the case of the symbol copied exactly: in this chapter i is the instantaneous current, which for a dc current is the current itself, so the print still states dc power, a look-alike that reads as the same quantity (DECISIONS 2026-10-12))
- ch 7 page 8 §7.5: "हम पाते हैं कि (1/omega C)" → "हम पाते हें कि (1/omega C)" (the page prints the e-matra, not the ai-matra, on the auxiliary (crop p8 x40-200 y612-643 at zoom 8; the decoded layer's line y629 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 7 page 20 §7.8: "वास्तविक ट्राँसफॉर्मरों में" → "वास्तविक ट्रॉसफॉर्मरों में" (the page prints the candra-o vowel sign on the first syllable, a crescent with no dot (crop p20 x168-188 y109-130 at zoom 20; the decoded layer's line y114 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 7 page 4 §7.2: "= 0.450A" → "= 0.454A" (the page prints I = P/V = 100 W / 220 V = 0.450A (crop p4 x40-420 y680-725 at zoom 5; the decoded layer's line y705 agrees); 100/220 = 0.4545, so the printed result is false in its third figure; the English row gives 0.454 A; by the founder's ruling of 2026-10-10, confirmed for this place on 2026-10-13, the row writes the meaning, the English row's value)
- ch 7 page 9 §7.5: "पहले की तुलना में दीप्ति से" → "पहले की तुलना में कम दीप्ति से" (the page prints 'reducing C will increase the reactance and the lamp will light with brightness compared to before', with no word for less (crop p9 x165-510 y565-598 at zoom 5; the decoded layer's line y570 agrees), which reads as brighter; a larger reactance passes less current and the lamp dims, as the English row says ('shine less brightly than before') and as the Hindi itself says of the bulb on p10 ('बल्ब की दीप्ति कम हो जाती है'); a dropped word that reverses the physics, so by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 8 page 2 §8.2: "[चित्र 8.1(a)] में एक समांतर प्लेट संधारित्र C" joined onto the paragraph before it (the capacitor of Fig. 8.1(a), the circular loop at P and Eq. (8.2) are the rest of English ¶2, which the Hindi row before begins with Ampere's law, Eq. (8.1))
- ch 8 page 3 §8.2: a new paragraph starts at "क्योंकि यह विरोधाभास हमारे द्वारा लागू किए गए" (English ¶4, the contradiction that shows Ampere's circuital law is missing a term, begins here; the Hindi row keeps it in ¶3's paragraph)
- ch 8 page 3 §8.2: a new paragraph starts at "अब यदि संधारित्र की प्लेटों पर आवेश Q" (English ¶6, the current i = dQ/dt and dPhi_E/dt, begins here after Eq. (8.3); the Hindi row runs it on in ¶5's paragraph)
- ch 8 page 3 §8.2: a new paragraph starts at "यह निर्दिष्ट करता है कि ऐम्पियर के नियम में संगति के लिए" (English ¶7, 'for consistency' and Eq. (8.4), begins here; the Hindi row runs it on in ¶5's paragraph)
- ch 8 page 6 §8.3.1: "मैक्सवेल के सिद्धांत के परीक्षण के लिए किए गए हर्ट्ज़" starts a new paragraph, not the previous page's (English ¶3, the sensation Hertz's test created and Bose's experiment, begins here at the top of p6; the row ran on from p5, where English ¶2 ends with Hertz's experiment (1887))
- ch 8 page 7 §8.3.2: a new paragraph starts at "समीकरण omega = ck, सभी तरंगों के लिए प्रामाणिक संबंध" (English ¶3, the relation omega = ck as the standard one for waves and Eq. [8.9(b)], begins here after Eq. [8.9(a)])
- ch 8 page 8 §8.3.2: a new paragraph starts at "इसकी दिशा के संबंध में हम जानते हैं" (English ¶12, the direction of B in Example 8.1's solution, begins here; the Hindi row runs the solution as one paragraph)
- ch 8 page 8 §8.3.2: a new paragraph starts at "(a) तरंग की आवृत्ति तथा तरंगदैर्घ्य क्या है?" (English ¶14, Example 8.2's question (a), begins here; the Hindi row sets the statement and both questions together)
- ch 8 page 8 §8.3.2: a new paragraph starts at "(b) विद्युत क्षेत्र के लिए व्यंजक लिखिए।" (English ¶15, Example 8.2's question (b), begins here)
- ch 8 page 8 §8.3.2: a new paragraph starts at "(a) दिए गए समीकरण की निम्न समीकरण" (English ¶16 is the Solution label alone and ¶17 begins at part (a); the Hindi row sets them together, so हल stays as its own row)
- ch 8 page 4 §8.2: "(शब्दश:)" → "(शब्दशः)" (Visarga: an adverb in -shah (literally), printed with the visarga glyph that this font shares with the colon. Crop p4 x60-408 y534-553 at zoom 4; the decoded layer's line y537 agrees)
- ch 8 page 7 §8.3.2: "स्व:संपोषित" → "स्वःसंपोषित" (Visarga: the glyph sits inside the compound (self-sustained) with no space after it, where a colon cannot stand; printed with the visarga glyph that this font shares with the colon, as printed (the book's own form of the word). Crop p7 x160-525 y279-299 at zoom 4; the decoded layer's line y283 agrees)
- ch 8 page 1 §8.1: "विद्युत-चुंबकत्व के सभी" → "विद्युत-चुंबकत्च के सभी" (the page prints ca, not va, under the final conjunct (crop p1 x330-470 y652-669 at zoom 12; the decoded layer's line y654 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 8 page 8 §8.4: "पराबैंगनी एवं अवरक्त तरंगों का" → "पराबैगनी एवं अवरक्त तरंगों का" (the page prints no anusvara on the third syllable here, against the next occurrence on the page which has one (crop p8 x250-300 y597-614 at zoom 16; the decoded layer's line y599 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 8 page 9 §8.4.1: "आवृत्तियाँ लघुतरंग बैंडों" → "आवृत्तियाँ लघुतंरग बैंडों" (the page sets the anusvara over the third syllable, not the fourth (crop p9 x380-420 y558-576 at zoom 16, against a correctly set tarang at x206-246 y661-679; the decoded layer's line y561 agrees): the book's own spelling, kept as printed (DECISIONS 2026-10-03); the transcription repaired it)
- ch 8 page 10 §8.4.3: "दीर्घ तरंगदैर्ध्य सिरे" → "दीर्घ तरंगदैर्घ्य सिरे" (the page prints the half gha here, open to the headline (crop p10 x120-180 y198-214 at zoom 14, against the half dha at x75-89 y443-458 at zoom 30; the decoded layer's line y199 agrees); the row misread it)
- ch 8 page 10 §8.4.3: "दीर्घ तरंगदैर्ध्य की अवरक्त" → "दीर्घ तरंगदैर्घ्य की अवरक्त" (the page prints the half gha here, open to the headline (crop p10 x62-76 y309-324 at zoom 30, against the half dha at x75-89 y443-458; the decoded layer's line y310 agrees); the row misread it)
- ch 8 page 9 §8.4.2: "सूक्ष्म तरंगें (लघु" → "सूक्ष्म तरंगों (लघु" (the page prints the oblique plural, the o-matra with its anusvara, before the bracket (crop p9 x150-420 y655-685 at zoom 5; the decoded layer's line y663 agrees); the row's form is the heading's above it, a misreading)
- ch 1 page 32 §1.13: "– 200 i_hat ΔS" → "– 200 i_hat·ΔS" (the page draws î and ΔS with a gap and no dot between them (crop p32 x160-420 y398-436 at zoom 5), where the same line, just before, draws the dot of E·ΔS; the text layer places an MT-Extra glyph in that gap (the decoded layer's line y411), which the raster does not draw, and the English row reads –200 i_hat·DeltaS, a dot product, written in the rows' · for the dot. Unlike ch 4 p12, whose page draws the same sign three lines lower, this place rests on the English row and the layer's glyph, with the dot the page draws for E·ΔS: a sign the raster leaves out, ruled per place (DECISIONS 2026-10-03, 2026-10-13); put to the founder for this place with this round)
- ch 1 page 32 §1.13: "चूँकि i_hat ΔS" → "चूँकि i_hat·ΔS" (the page draws î and ΔS with a gap and no dot between them (crop p32 x160-420 y398-436 at zoom 5), where the line above draws the dot of E·ΔS; the text layer places an MT-Extra glyph in that gap (the decoded layer's line y429), which the raster does not draw, and the English row reads since i_hat·DeltaS = –DeltaS, a dot product, written in the rows' · for the dot. Unlike ch 4 p12, whose page draws the same sign three lines lower, this place rests on the English row and the layer's glyph, with the dot the page draws for E·ΔS: a sign the raster leaves out, ruled per place (DECISIONS 2026-10-03, 2026-10-13); put to the founder for this place with this round)
- ch 5 page 4 §5.2.3: "− m B (5.3)" → "− m·B (5.3)" (the page draws bold m and B with a gap and no dot between them (crop p4 x180-525 y528-545 at zoom 8); the text layer places an MT-Extra glyph in that gap (the decoded layer's line y532), which the raster does not draw, and the English row reads −m.B, its period the dot product that makes U_m a scalar, written in the rows' · for the dot as round one's B·dl is. Unlike ch 4 p12, whose page draws the same sign three lines lower, this place rests on the English row and the layer's glyph alone: a sign the raster leaves out, ruled per place (DECISIONS 2026-10-03, 2026-10-13); put to the founder for this place with this round)
- ch 7 page 3 §7.2: "जानते हैं जूल i^2R द्वारा" → "जानते हैं जूल तापन i^2R द्वारा" (the page prints 'as you know, Joule is given by i^2R', with no word for heating (crop p3 x236-420 y98-117 at zoom 8, and x236-524 y96-160 at zoom 4; the decoded layer's line y101 agrees); the English row reads 'Joule heating is given by i^2R' and the page's own next sentence says जूल तापन. Read as the unit, जूल sets an energy equal to a power, a false statement the ruling of 2026-10-10 writes as its meaning; read as the name of the effect, a slip the clause's i^2 still carries, which stays as printed like the slips ruled on 2026-10-13. Written as the meaning, in the book's own phrase, and put to the founder for this place with this round (found by the ✅ sample read))
rulings on verifier flags that change no text: 50

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

- ch 4 §4.1 ¶4: "4.1(b)"
- ch 5 §5.3 ¶2: "5.2 (b)"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 0 | 3 | 1038 |

## sections whose Hindi rows do not number as the English (English is canonical)

115 of 115 sections one to one
| chapter | section | en | hi | unprinted |
|---|---|---|---|---|

## English paragraphs this edition does not print — left without Hindi by an `unprinted` entry

- ch 3 §3.12 ¶5
pairs: pairs.jsonl — each address's English and Hindi, to read

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 43 | 37 | 195 | 86.0% |
| 2 | 35 | 31 | 192 | 88.6% |
| 3 | 26 | 22 | 126 | 84.6% |
| 4 | 30 | 26 | 152 | 86.7% |
| 5 | 17 | 14 | 97 | 82.4% |
| 6 | 22 | 19 | 104 | 86.4% |
| 7 | 24 | 20 | 123 | 83.3% |
| 8 | 14 | 11 | 52 | 78.6% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
- ch 3 §3.10 ¶2 continues the sentence ¶1 stopped in the middle of: "…था जब यह परिघटना उचित रूप से समझी नहीं गई थी।" + "epsilon का महत्त्व समझने के लिए, सेल से संयोज…"

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 211 | 211 | 180 | 1041 | 100.0% |

## addresses in the database this extraction no longer carried — a load would delete them

none

## rows the other edition still carries — this edition's text a load would clear from them

none

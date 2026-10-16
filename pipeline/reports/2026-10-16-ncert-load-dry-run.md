# margai-pipeline ncert load dry-run

- run: 2026-10-16 07:32 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of phy12-part2 (hi)
- result: ok
DRY RUN: nothing is written — the database write runs inside a transaction that is rolled back, so every count below is what this load would do
extraction: hi.jsonl sha256 0d357bbd1354a4257cf6354ece02ca8ca5cdccd74ab1f3e5275141c277891676 — a local file read in place of extract/phy12-part2/hi.jsonl, not the content store; the reload's report prints the artefact's sha256 to match
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 d0cd71eae59f93c363e4db4be09308088cb172d54607f09ab8d9349ac8e0cdf9

## page-break repairs to the model's continuation flags (deterministic, each one named)

- ch 9 §9.4: page 11 repeated the end of page 10's paragraph; the repeated 75 characters were dropped; what remains is treated as continuing that paragraph

## a visarga the model wrote as a colon (deterministic, each one named)

- ch 9 p8 §9.2.3: a visarga written as a colon — अतः, क्रमशः
- ch 9 p8 §9.3: a visarga written as a colon — प्रायः
- ch 9 p8 §9.3: a visarga written as a colon — क्रमशः, अतः
- ch 9 p9 §9.4: a visarga written as a colon — अंशतः, अंशतः
- ch 9 p9 §9.4: a visarga written as a colon — अंशतः, अंशतः
- ch 9 p16 §9.5.3: a visarga written as a colon — स्पष्टतः, अतः
- ch 9 p16 §9.5.3: a visarga written as a colon — अतः
- ch 9 p27 §9.7.2: a visarga written as a colon — अतः
- ch 10 p1 §10.1: a visarga written as a colon — प्रायः
- ch 10 p6 §10.3.2: a visarga written as a colon — अतः
- ch 10 p8 §10.3.3: a visarga written as a colon — अतः
- ch 10 p9 §10.4: a visarga written as a colon — अतः
- ch 10 p9 §10.4: a visarga written as a colon — अतः
- ch 10 p17 §10.7: a visarga written as a colon — अतः, अतः
- ch 11 p5 §11.4.1: a visarga written as a colon — अतः
- ch 11 p11 §11.7: a visarga written as a colon — अतः
- ch 12 p6 §12.2.1: a visarga written as a colon — अतः
- ch 12 p6 §12.2.1: a visarga written as a colon — अतः
- ch 12 p6 §12.2.2: a visarga written as a colon — अतः
- ch 13 p1 §13.1: a visarga written as a colon — अधिकांशतः
- ch 13 p7 §13.4.2: a visarga written as a colon — अतः
- ch 13 p10 §13.7: a visarga written as a colon — अतः
- ch 13 p10 §13.7.1: a visarga written as a colon — अतः
- ch 13 p12 §13.7.2: a visarga written as a colon — अतः
- ch 13 p12 §13.7.2: a visarga written as a colon — पुनः
- ch 13 p12 §13.7.3: a visarga written as a colon — अतः
- ch 13 p13 §13.7.3: a visarga written as a colon — अतः, वस्तुतः
- ch 13 p13 §13.7.3: a visarga written as a colon — अतः, अतः, अंतः
- ch 13 p13 §13.7.3: a visarga written as a colon — अंतः, अंतः
- ch 14 p1 §14.1: a visarga written as a colon — अधिकांशतः, क्रमशः, सामान्यतः
- ch 14 p4 §14.2: a visarga written as a colon — पूर्णतः, पूर्णतः
- ch 14 p13 §14.6.1: a visarga written as a colon — अंतः
- ch 14 p13 §14.6.2: a visarga written as a colon — अंतः

## a word glued to a colon that the visarga list does not know — adjudicate

left as written: a `text` entry if the page means a visarga, nothing if it is a colon
- ch 9 p9 §9.3: प्रकाशत:
- ch 9 p9 §9.4: प्रकाशत:, प्रकाशत:
- ch 10 p9 §10.4: सारांशत:
- ch 11 p5 §11.4.1: रैखिकत:
- ch 11 p6 §11.4.3: रैखिकत:
- ch 11 p6 §11.4.3: रैखिकत:
- ch 13 p10 §13.7.1: उदाहरणत:
- ch 13 p12 §13.7.2: अंत:
- ch 13 p13 §13.7.3: सिद्धांतत:

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

- ch 9 page 4 §9.2.2: "theta के लघु मानों के लिए, जो कि" continues the previous page's paragraph (tan theta ≈ theta for paraxial rays and Eq. (9.2), FD = CD/2, are the end of English ¶3, which runs from p3 to p4; the Hindi row was cut at Eq. (9.1))
- ch 9 page 5 §9.2.3: a new paragraph starts at "क्योंकि ∠ APB = ∠ A'PB', समकोण त्रिभुज" (English ¶10 (since ∠APB = ∠A'PB', the similar triangles and Eqs. 9.5-9.6) begins here, after Eq. (9.4) which ends ¶9)
- ch 9 page 8 §9.3: a new paragraph starts at "(i) आपतित किरण, अपवर्तित किरण तथा अंतरापृष्ठ" (English ¶2, Snell's law (i) (incident ray, refracted ray and normal in one plane), begins here; the Hindi print runs the laws on in ¶1's paragraph)
- ch 9 page 8 §9.3: a new paragraph starts at "(ii) किन्हीं दो माध्यमों के युगल के लिए," (English ¶3, Snell's law (ii) (the ratio of the sines is constant), begins here)
- ch 9 page 8 §9.3: "याद रखिए, आपतन कोण (i) तथा" joined onto the paragraph before it (the angles i and r, Eq. (9.10) and n_21 are the rest of English ¶3, which begins with law (ii))
- ch 9 page 9 §9.3: "समीकरण (9.10) से यदि n_21 > 1, r < i" starts a new paragraph, not the previous page's (English ¶4 (from Eq. 9.10, n_21 > 1 bends the ray towards the normal) begins here, at the top of p9; it does not continue the p8 paragraph on Eq. (9.10))
- ch 9 page 14 §9.5.2: a new paragraph starts at "दूसरे अंतरापृष्ठ* ADC के लिए भी समान प्रक्रिया" (English ¶2 (the same procedure at the second interface ADC, Eq. 9.18) begins here, after Eq. (9.17) which ends ¶1)
- ch 9 page 14 §9.5.2: a new paragraph starts at "मान लीजिए बिंब अनंत पर है तो," (English ¶4 (the object at infinity, Eq. 9.20) begins here, after Eq. (9.19) which ends ¶3)
- ch 9 page 14 §9.5.2: the paragraph starting "* नोट कीजिए अब ADC के दायीं ओर" takes its number after the next paragraph of its section (the footnote on the refractive indices either side of ADC is English ¶7, numbered after ¶6 (the lens maker's formula); the Hindi prints the footnote at the foot of p14 and ¶6 at the top of p15)
- ch 9 page 15 §9.5.2: a new paragraph starts at "(i) बिंब से निकलने वाली वह किरण" (English ¶13, ray (i) parallel to the principal axis, begins here; the Hindi print runs the three rays on in ¶12's paragraph)
- ch 9 page 15 §9.5.2: a new paragraph starts at "(ii) लेंस के प्रकाशिक केंद्र से गुज़रने" (English ¶14, ray (ii) through the optical centre, begins here)
- ch 9 page 15 §9.5.2: a new paragraph starts at "(iii) (a) किसी उत्तल लेंस के प्रथम" (English ¶15, ray (iii) (a) and (b) through the foci, begins here)
- ch 9 page 17 §9.5.4: a new paragraph starts at "दूसरे लेंस B द्वारा बने प्रतिबिंब के लिए" (English ¶3 (the image by the second lens B, Eq. 9.28) begins here, after Eq. (9.27) which ends ¶2)
- ch 9 page 17 §9.5.4: a new paragraph starts at "समीकरण (9.27) तथा (9.28) को जोड़ने पर," (English ¶4 (adding Eqs. 9.27 and 9.28, Eq. 9.29) begins here)
- ch 9 page 17 §9.5.4: a new paragraph starts at "इन दो लेंसों के तंत्र को f फ़ोकस" (English ¶5 (the two-lens system as one lens of focal length f, Eq. 9.30) begins here, after Eq. (9.29) which ends ¶4)
- ch 9 page 18 §9.5.4: "यहाँ P इस लेंस संयोजन की नेट" joined onto the paragraph before it (P the net power, the algebraic sum and the total magnification of Eq. (9.33) are the rest of English ¶7, which begins with Eq. (9.32); the Hindi row was cut at the display)
- ch 9 page 18 §9.5.4: "पहले लेंस द्वारा निर्मित प्रतिबिंब दूसरे" joined onto the paragraph before it (the first image as the second lens's object, 10 cm to its right, and v_2 = ∞ are part of English ¶10, Example 9.8's solution, one row from v_1 = 15 cm to the final image)
- ch 9 page 19 §9.5.4: "यह आभासी प्रतिबिंब दूसरे लेंस के" joined onto the paragraph before it (the virtual image at infinity as the third lens's object and v_3 = 30 cm are part of English ¶10)
- ch 9 page 19 §9.5.4: "अंतिम प्रतिबिंब तीसरे लेंस के" joined onto the paragraph before it (the final image 30 cm to the right of the third lens is the last sentence of English ¶10)
- ch 9 page 20 §9.6: "इन दोनों समीकरणों की तुलना करने पर," starts a new paragraph, not the previous page's (English ¶4 (comparing the two equations, r_1 + r_2 = A, Eq. 9.34) begins here, at the top of p20; it does not continue ¶3 (the triangle QNR) on p19)
- ch 9 page 20 §9.6: a new paragraph starts at "कुल विचलन delta दोनों फलकों पर" (English ¶5 (the total deviation delta, Eq. 9.35) begins here, after Eq. (9.34) which ends ¶4)
- ch 9 page 20 §9.6: a new paragraph starts at "समीकरण (9.34) से हमें प्राप्त होता है" (English ¶7 (Eq. 9.34 gives r = A/2, Eq. 9.36) begins here, after r_1 = r_2 which ends ¶6)
- ch 9 page 20 §9.6: a new paragraph starts at "इसी प्रकार समीकरण (9.35) से हमें" (English ¶8 (in the same way Eq. 9.35 gives i = (A + D_m)/2, Eq. 9.37) begins here)
- ch 9 page 20 §9.6: a new paragraph starts at "यदि प्रिज़्म के पदार्थ का अपवर्तनांक n_21" (English ¶9 (the refractive index of the prism, Eq. 9.38) begins here, after Eq. (9.37) which ends ¶8)
- ch 9 page 25 §9.7.1: a new paragraph starts at "अतः कुल आवर्धन [समीकरण (9.33) के अनुसार]," (English ¶13 (the total magnification with the image at infinity, Eq. 9.45) begins here, after Eq. 9.44(b) which ends ¶12)
- ch 9 page 9 §9.3: "की तुलना में प्रकाशत: सघन" → "की तुलना में प्रकाशतः सघन" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb प्रकाशतः ('optically'), a visarga word, not a label before a colon (crop p9 x45-410 y114-131 at zoom 3; the decoded layer's line y116 agrees); the load's visarga list does not know it)
- ch 9 page 9 §9.4: "किसी प्रकाशत: सघन माध्यम से प्रकाशत: विरल" → "किसी प्रकाशतः सघन माध्यम से प्रकाशतः विरल" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb प्रकाशतः ('optically'), twice, a visarga word, not a label before a colon (crop p9 x45-410 y629-646 at zoom 3; the decoded layer's line y631 agrees); the load's visarga list does not know it)
- ch 9 page 6 §9.2.3: "आभासी प्रतिबिंबों की रचना" → "आभासी प्रतिबिबों की रचना" (the page prints प्रतिबिबों, with no anusvara on the second बि (crop p6 x160-420 y203-222 at zoom 5); the decoded layer's line y206 agrees; the row adds the anusvara; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 9 page 11 §9.4.1: "गए प्रिज़्मों में" → "गए प्रिज्मों में" (the page prints प्रिज्मों with no nukta under ज, beside a nukta-bearing डिज़ाइन on the same line (crop p11 x70-262 y449-466 at zoom 5); the decoded layer's line y451 agrees; the row adds the nukta; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 9 page 21 §9.7: "हैं जिससे प्रकृति" → "हैं जिनसे प्रकृति" (the page prints 'एक हैं जिनसे प्रकृति ने हमें' (crop p21 x300-410 y338-355 at zoom 6); the decoded layer's line y340 agrees; the row's जिससे repairs the book's plural; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 9 page 10 §9.4: "सभी प्रकाशिक परिघटनाओं को" → "पूर्ण आंतरिक परावर्तन के लिए एक प्रदर्शन सभी प्रकाशिक परिघटनाओं को" (the page prints the italic side heading पूर्ण आंतरिक परावर्तन के लिए एक प्रदर्शन above this paragraph (crop p10 x150-530 y585-645 at zoom 4; the decoded layer's line y594 agrees) and the transcription left it out; a side heading leads its paragraph and is never dropped (DECISIONS 2026-09-27), as the English row leads with "A demonstration for total internal reflection")
- ch 9 page 19 §9.6: "इसलिए इस भुजा के" → "इसलिए इस चतुर्भुज के" (the page prints 'इसलिए इस भुजा के अन्य दो कोणों का योग 180° है' — a side having angles (crop p19 x40-410 y636-692 at zoom 3), where the sentence before names the quadrilateral AQNR and the English row says 'the sum of the other angles of the quadrilateral is 180°': by the founder's ruling of 2026-10-10 the row writes the meaning (the founder ruled this place on 2026-10-16))
- ch 10 page 8 §10.3.3: a new paragraph starts at "(a) जब एकवर्णीय प्रकाश दो माध्यमों" (English ¶7, question (a) of Example 10.1 (why reflected and refracted light keep the incident frequency), begins here; English ¶6 is the label Example 10.1 alone, so the Hindi उदाहरण 10.1 stays as its own row)
- ch 10 page 8 §10.3.3: a new paragraph starts at "(a) परावर्तन तथा अपवर्तन, आपतित प्रकाश" (English ¶11, answer (a) of Example 10.1 (atoms as forced oscillators), begins here; English ¶10 is the word Solution alone, so the Hindi हल stays as its own row)
- ch 10 page 9 §10.4: a new paragraph starts at "अतः परिणामी विस्थापन होगा" (English ¶5, the resultant displacement y = y_1 + y_2 = 2a cos omega t, begins here, after the displayed y_2 = a cos omega t; the Hindi row keeps it at the end of ¶4's paragraph across the p8-p9 break)
- ch 10 page 9 §10.4: "S_1 से निर्गमित तरंगें स्रोत S_2 से आने वाली" joined onto the paragraph before it (the waves from S_1 arriving two and a half cycles later, y_2 = –a cos omega t and destructive interference are the rest of English ¶8, which the row before it (the point R with S_2R – S_1R = –2.5 lambda) begins)
- ch 10 page 10 §10.4: a new paragraph starts at "परिणामी विस्थापन का आयाम 2a cos (phi/2) है" (English ¶10, the amplitude 2a cos(phi/2) and the intensity I = 4I_0 cos^2(phi/2) (Eq. 10.11), begins here, after the displayed resultant displacement and its cos A + cos B identity)
- ch 10 page 11 §10.5: a new paragraph starts at "दूसरी ओर हमें विनाशी व्यतिकरण द्वारा अदीप्त" (English ¶5, destructive interference at x_n = (n + 1/2) lambda D/d (Eq. 10.14), begins here, after the displayed Eq. (10.13); the Hindi row keeps it in ¶4's paragraph)
- ch 10 page 13 §10.6.1: a new paragraph starts at "फ़ोटोग्राफ़ तथा इसके संगत तीव्रता पैटर्न" (English ¶6, the photograph and intensity pattern of Fig. 10.15, begins here; the Hindi row runs it on at the end of ¶5's paragraph)
- ch 10 page 17 §10.7: a new paragraph starts at "अतः, कोण theta = pi/4 के लिए" (English ¶11, the transmitted intensity maximum at theta = pi/4, begins here, after the displayed (I_0/4) sin^2 2theta; the Hindi row keeps it in Example 10.2's solution paragraph)
- ch 10 page 13 §10.6.1: the row starting "* रिचर्ड फ़ाइनमैन को 1965" is numbered after the row starting "व्यतिकरण तथा विवर्तन में प्रकाश ऊर्जा" (page 14) (the Feynman footnote, which the Hindi prints at the foot of p13 (the decoded layer's line y705) under §10.6.1, is English §10.6.2 ¶6 (printed at the foot of p14) and goes after ¶5, energy redistributed in interference and diffraction)
- ch 10 page 17 §10.7: "तीव्रता I_a है" → "तीव्रता I_0 है" (the page prints I with an italic subscript a, 'तीव्रता I_a है' (crop p17 x58-390 y242-300 at zoom 5; the decoded layer's line y244 agrees), for the intensity after the first polaroid P_1, while the next display and the rest of the solution use I_0 (I = I_0 cos^2 theta, same crop) and this page's line above Example 10.2 defines I_0 as that intensity (crop p17 x44-410 y96-118); the English row reads 'Let I_0 be the intensity of polarised light after passing through the first polariser P_1'. I_a names a quantity the solution never uses and leaves its I_0 undefined; by the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 10 page 9 §10.4: "सारांशत: यदि" → "सारांशतः यदि" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb सारांशतः ('in summary'), a visarga word, not a label before a colon (crop p9 x45-410 y604-621 at zoom 3; the decoded layer's line y606 agrees); the load's visarga list does not know it)
- ch 10 page 2 §10.1: "मैक्सवेल तरंग समीकरणों" → "मैक्सवेल तंरग समीकरणों" (the page prints तंरग at the end of the line, the anusvara over त (crop p2 x480-523 y438-454 at zoom 10), where the same page prints तरंग with the anusvara over र (crop p2 x300-400 y100-114 at zoom 10); the decoded layer's line y440 agrees; the row repairs the book's slip; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 10 page 10 §10.5: "सामान्य स्रोत (जैसे" → "सामान्य स्त्रोत (जैसे" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p10 x421-446 y650-665; the decoded layer's line y650 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14))
- ch 10 page 13 §10.6.1: "एकवर्णी स्रोत से" → "एकवर्णी स्त्रोत से" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p13 x196-221 y100-115; the decoded layer's line y101 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14))
- ch 10 page 13 §10.6.1: "द्वितीयक स्रोतों की" → "द्वितीयक स्त्रोतों की" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p13 x44-69 y406-421; the decoded layer's line y406 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14))
- ch 10 page 13 §10.6.1: "ये स्रोत एक ही" → "ये स्त्रोत एक ही" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p13 x128-153 y421-436; the decoded layer's line y422 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14))
- ch 10 page 13 §10.6.1: "ऑन फ़िज़िक्स में" → "ऑन फ़िजिक्स में" (the page prints फ़िजिक्स, a nukta under फ and none under ज (crop p13 x100-160 y568-585 at zoom 14); the decoded layer's line y569 agrees; the row adds the second nukta; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 10 page 12 §10.6: "उदीप्त तथा दीप्त" → "अदीप्त तथा दीप्त" (the page prints उदीप्त ('lit up') in 'बारी-बारी से उदीप्त तथा दीप्त क्षेत्र' (crop p12 x155-525 y455-506 at zoom 4 and x330-525 y472-490 at zoom 6; the decoded layer's line y475 agrees), so the sentence says the shadow's edge shows alternately lit and bright regions; the English row reads 'alternate dark and bright regions just like in interference', and अदीप्त is the word for dark the chapter's rows carry in §10.5 (p11) and §10.6.1 (p13). By the founder's ruling of 2026-10-10 the row writes the meaning: अदीप्त ('dark'))
- ch 11 page 2 §11.2: a new paragraph starts at "(i) तापायनिक उत्सर्जन : उपयुक्त तापन के द्वारा" (English ¶4, process (i) thermionic emission, begins here; the Hindi row runs it on after ¶3's lead-in sentence)
- ch 11 page 2 §11.2: a new paragraph starts at "(ii) क्षेत्र उत्सर्जन : किसी धातु पर लगाया गया" (English ¶5, process (ii) field emission (a field of the order of 10^8 V m^-1), begins here)
- ch 11 page 4 §11.4: a new paragraph starts at "जब संग्राहक पट्टिका A, उत्सर्जक पट्टिका C की तुलना में धनात्मक होगी" (English ¶2, the collector plate A positive with respect to the emitter C and the voltmeter and microammeter readings, begins here; the Hindi row runs it on in ¶1's paragraph)
- ch 11 page 6 §11.4.3: a new paragraph starts at "(i) प्रकाशिक इलेक्ट्रॉनों की उच्चतम गतिज ऊर्जा आपतित विकिरण" (English ¶4, implication (i) (the maximum kinetic energy linear in the frequency and independent of the intensity), begins here; English ¶3 is the lead-in 'These observations have two implications' alone)
- ch 11 page 6 §11.4.3: a new paragraph starts at "(ii) आपतित विकिरण की आवृत्ति nu के लिए, जबकि" (English ¶5, implication (ii) (no emission below the cut-off frequency nu_0), begins here)
- ch 11 page 7 §11.4.3: a new paragraph starts at "(i) किसी दिए गए प्रकाश-संवेदी पदार्थ और आपतित विकिरण की आवृत्ति (देहली" (English ¶10, summary point (i) (the photocurrent proportional to the intensity, Fig. 11.2), begins here; English ¶9 is the lead-in 'We now summarise' alone)
- ch 11 page 7 §11.4.3: a new paragraph starts at "(ii) किसी दिए गए प्रकाश-संवेदी पदार्थ और आपतित विकिरण की आवृत्ति के लिए, संतृप्त" (English ¶11, summary point (ii) (the saturation current proportional to the intensity, the stopping potential independent of it, Fig. 11.3), begins here)
- ch 11 page 10 §11.7: "(ii) प्रत्येक फ़ोटॉन की ऊर्जा E (=h nu) होती है" joined onto the paragraph before it (point (ii), each photon's energy h nu, momentum h nu/c and speed c, is the end of English ¶3, which carries the summary's lead-in with points (i) and (ii); English ¶4 begins at (iii))
- ch 11 page 12 §11.8: "अर्थात, एक फोटॉन का दे ब्रॉग्ली तरंगदैर्घ्य" joined onto the paragraph before it ('That is', the photon's de Broglie wavelength equal to the wavelength of the radiation, after the displayed Eq. (11.7), is the end of English ¶3)
- ch 11 page 13 §11.8: "(b) गेंद के लिए द्रव्यमान m' = 0.150 kg" joined onto the paragraph before it (part (b) of Example 11.3's solution (the ball, lambda' = 1.47 × 10^-34 m) is the middle of English ¶7, the whole solution, which the row before it (part (a), the electron) begins)
- ch 11 page 13 §11.8: "इलेक्ट्रॉन के लिए दे ब्रॉग्ली तरंगदैर्घ्य X-किरण" joined onto the paragraph before it (the closing comparison (the electron's wavelength like an X-ray's, the ball's about 10^-19 times a proton's size) is the end of English ¶7, Example 11.3's solution)
- ch 11 page 5 §11.4.1: "साथ रैखिकत: बढ़ती" → "साथ रैखिकतः बढ़ती" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb रैखिकतः ('linearly'), a visarga word, not a label before a colon (crop p5 x222-522 y129-146 at zoom 3; the decoded layer's line y131 agrees); the load's visarga list does not know it)
- ch 11 page 6 §11.4.3: "रैखिकत: परिवर्तित होता" → "रैखिकतः परिवर्तित होता" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb रैखिकतः ('linearly'), a visarga word, not a label before a colon (crop p6 x60-410 y553-570 at zoom 3; the decoded layer's line y555 agrees); the load's visarga list does not know it)
- ch 11 page 6 §11.4.3: "रैखिकत: परिवर्तित होती" → "रैखिकतः परिवर्तित होती" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb रैखिकतः ('linearly'), a visarga word, not a label before a colon (crop p6 x60-410 y645-662 at zoom 3; the decoded layer's line y647 agrees); the load's visarga list does not know it)
- ch 11 page 4 §11.4: "काँच/क्वार्ट्ज़ की" → "काँच/क्वार्टज़ की" (the page prints क्वार्टज़ with a full ट and no halant (crop p4 x203-245 y252-267 at zoom 12), where three lines lower it prints क्वार्ट्ज़ with the halant (x262-304 y284-299, same montage); the decoded layer's line y252 agrees; the row repairs the first; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 11 page 4 §11.4: "है। स्रोत S से" → "है। स्त्रोत S से" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p4 x155-180 y268-283; the decoded layer's line y268 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14))
- ch 11 page 4 §11.4: "प्रकाश स्रोत की" → "प्रकाश स्त्रोत की" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p4 x198-223 y628-643; the decoded layer's line y628 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14))
- ch 11 page 5 §11.4.2: "धारा तेजी से" → "धारा तेज़ी से" (the page prints तेज़ी with a nukta under ज (crop p5 x440-480 y500-517 at zoom 14); the decoded layer's line y501 agrees; the row drops the nukta, a misreading)
- ch 11 page 7 §11.4.3: "आवृत्ति v_0 को" → "आवृत्ति nu_0 को" (the page prints the threshold frequency with a letter that reads as an italic v (crop p7 x175-400 y97-117 at zoom 6), and in this book an italic v names a speed; the sentence and the English row (§11.4.3 ¶6, 'This minimum, cut-off frequency nu_0') mean ν, and the decoded layer's line y99 agrees with the meaning: by the founder's ruling of 2026-10-10 (a Latin italic v for ν, DECISIONS 2026-10-12) the row writes the meaning)
- ch 11 page 8 §11.6: "समीकरण (11.2) के अनुसार, प्रेक्षण" → "• समीकरण (11.2) के अनुसार, प्रेक्षण" (the page prints a list bullet before this item (crop p8 x40-300 y645-666 at zoom 5); the decoded layer's line y650 agrees; the row drops it, while the English ¶4 and the list's next three Hindi rows (¶5-¶7, p9) carry it: a list bullet the page prints and the rows drop is put back, as for phy11-part1 ch 4 and phy12-part1 ch 1)
- ch 11 page 11 §11.7: "यदि स्रोत के द्वारा" → "यदि स्त्रोत के द्वारा" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p11 x205-229 y204-218; the decoded layer's line y203 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14); the same page's '(b) स्रोत के द्वारा औसत' (§11.7 ¶8, crop x225-250 y131-145, in a montage with this one at zoom 14) prints स्र and keeps the row's spelling)
- ch 12 page 4 §12.2: a new paragraph starts at "इस बल का परिमाण इस प्रकार व्यक्त किया जाता है" (English ¶5, the magnitude of the Coulomb force, Eq. (12.1), begins here at the end of p4; the Hindi print keeps it in ¶4's paragraph and runs it on to p5)
- ch 12 page 5 §12.2.1: "इससे यह भी ज्ञात होता है कि परमाणु में" joined onto the paragraph before it (the atom holding a much greater fraction of empty space than the solar system is the last sentence of English ¶4, Example 12.1's solution, which the row before it begins)
- ch 12 page 6 §12.2.1: a new paragraph starts at "अतः समीपतम दूरी d होगी" (English ¶8, the distance of closest approach d = 2Ze^2/(4 pi epsilon_0 K), begins here; the Hindi row runs Example 12.2's solution from ¶7 to ¶10 as one)
- ch 12 page 6 §12.2.1: a new paragraph starts at "प्राकृतिक स्रोतों के ऐल्फ़ा-कणों में पाई जाने वाली" (English ¶9, the maximum kinetic energy 7.7 MeV of natural alpha-particles and d = 3.84 × 10^-16 Z m, begins here)
- ch 12 page 6 §12.2.1: a new paragraph starts at "पन्नी के पदार्थ स्वर्ण का परमाणु क्रमांक" (English ¶10, gold's Z = 79 and d (Au) = 3.0 × 10^-14 m = 30 fm, begins here)
- ch 12 page 7 §12.2.2: "इससे कक्षीय-त्रिज्या प्राप्त होगी" joined onto the paragraph before it (the orbital radius r = 5.3 × 10^-11 m is the rest of English ¶5, Example 12.3's solution, which the row before it begins; the Hindi row was cut at the display)
- ch 12 page 7 §12.2.2: "परिक्रमण करते इलेक्ट्रॉन का वेग" joined onto the paragraph before it (the electron's velocity v = 2.2 × 10^6 m/s from Eq. (12.3) is the end of English ¶5, Example 12.3's solution)
- ch 12 page 9 §12.4: "क्लासिकी वैद्युतचुंबकीय सिद्धांत के अनुसार हम जानते हैं" joined onto the paragraph before it (the emitted frequency equal to the frequency of revolution, 6.6 × 10^15 Hz, is the end of English ¶3, Example 12.4's solution, which the row before it begins; the Hindi row was cut at the display)
- ch 12 page 10 §12.4: a new paragraph starts at "किसी कक्ष में गतिमान इलेक्ट्रॉन की कुल ऊर्जा" (English ¶11, the negative total energy meaning the electron is bound to the nucleus, begins here after Eq. (12.10); the Hindi row keeps it in ¶10's paragraph)
- ch 12 page 12 §12.5: "चूँकि n_f और n_i दोनों पूर्णांक हैं" starts a new paragraph, not the previous page's (English ¶3, light radiated in discrete frequencies since n_f and n_i are integers, begins here at the top of p12 (the print indents it); the row ran on from ¶1's Eq. (12.11) on p11, past the footnote that is English ¶2)
- ch 12 page 2 §12.1: "एक श्रृंखला दिखाई" → "एक शृंखला दिखाई" (the page prints शृंखला, श with ृ below and no र-stroke (crop p2 x300-330 y147-163 at zoom 12) — the same shape as ch 11 p9's शृंखला (x366-398 y709-725), which the row there carries so, and unlike the श्र of ch 12 p4's श्रेय (x170-195 y243-259), all three in one montage; the decoded layer's line y148 agrees; the row adds a र, a misreading)
- ch 12 page 3 §12.2: "कर सरेखित किया" → "कर संरेखित किया" (the page prints संरेखित with an anusvara over स (crop p3 x326-460 y210-227 at zoom 10); the decoded layer's line y212 agrees; the row drops the anusvara, a misreading)
- ch 12 page 11 §12.5: "आवृत्ति v_if का" → "आवृत्ति nu_if का" (the page prints the photon's frequency with a letter that reads as an italic v (crop p11 x160-521 y640-682 at zoom 4), where the sentence and the English row (nu_(if)) mean ν, and the decoded layer's line y645 agrees with the meaning: by the founder's ruling of 2026-10-10 (a Latin italic v for ν, DECISIONS 2026-10-12) the row writes the meaning)
- ch 12 page 11 §12.5: "hv_if =" → "h nu_if =" (Eq. (12.11) prints hv_if with the same italic-v letter (crop p11 x160-521 y640-682 at zoom 4), where the English row writes h nu_if, the photon's energy, and the decoded layer's line y668 agrees with the meaning: by the founder's ruling of 2026-10-10 (a Latin italic v for ν, DECISIONS 2026-10-12) the row writes the meaning, spelled h nu as the Hindi rows spell hν (ch 11 §11.6 ¶5, ch 12 §12.4 ¶8))
- ch 12 page 11 §12.5: "E_(n_f) – E_(n_i) (12.11)" → "E_(n_i) – E_(n_f) (12.11)" (the page prints (12.11) as hν_if = E_nf − E_ni (crop p11 x160-521 y640-682 at zoom 4; the decoded layer's line y668 agrees), the energy difference reversed: the same sentence sets n_f < n_i, so E_nf < E_ni and the print gives the emitted photon a negative energy; the English row writes h nu_if = E_ni – E_nf. By the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 12 page 11 §12.4.1: "E_3 = –1.53 eV" → "E_3 = –1.51 eV" (the page prints E_3 = –1.53 eV (crop p11 x280-525 y360-395 at zoom 5; the decoded layer's line y363 agrees); the English row reads E_3 = –1.51 eV, Eq. (12.10) gives –13.6/3^2 = –1.51 eV, the same sentence's E_3 – E_1 = 12.09 eV requires –1.51, and the page's own Fig. 12.7 labels n = 3 at −1.51 (crop p11 x40-200 y180-210). By the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 12 page 7 §12.3: "उत्सर्जित की जाती है" → "उत्तेजित की जाती है" (the page prints 'जब किसी परमाण्वीय गैस अथवा वाष्प … उत्सर्जित की जाती है' — the gas 'is emitted' (crop p7 x155-525 y662-730 at zoom 3), where the English row says 'when an atomic gas or vapour is excited at low pressure', a different process, and the chapter's own word for it is उत्तेजित (§12.4.1): by the founder's ruling of 2026-10-10 the row writes the meaning (the founder ruled this place on 2026-10-16))
- ch 12 page 13 §12.6: "हाइडोजन परमाणु" → "हाइड्रोजन परमाणु" (the page draws the र-sign of हाइड्रोजन under the इ, one letter before the ड it belongs to (crop p13 x240-330 y682-703 at zoom 8), and prints हाइड्रोजनसम right on the same line; the row dropped the mark, and kept where the page draws it the word is not valid Devanagari: the row writes the mark on its letter (the founder, 2026-10-16); this also answers the second read's flag at this place)
- ch 13 page 2 §13.2: "अतः इस परिभाषा के अनुसार 1u" joined onto the paragraph before it (the definition 1u = mass of one 12C atom / 12 and Eq. (13.1) are the end of English ¶1; the Hindi row was cut at the display)
- ch 13 page 2 §13.2: "उदाहरण के लिए, क्लोरीन के दो समस्थानिक हैं" joined onto the paragraph before it (chlorine's two isotopes, their abundances and the weighted average 35.47 u are the rest of English ¶3, which begins with the mass spectrometer and isotopes)
- ch 13 page 2 §13.2: a new paragraph starts at "यह हाइड्रोजन परमाणु के द्रव्यमान 1.00783 u में से" (English ¶5 (the proton mass is the hydrogen atom's 1.00783 u less one electron; deuterium and tritium) begins here, after Eq. (13.2) which ends ¶4)
- ch 13 page 3 §13.2: a new paragraph starts at "एक मुक्त प्रोटॉन के विपरीत एक मुक्त न्यूट्रॉन" (English ¶10 (a free neutron, unlike a free proton, is unstable) begins here; the Hindi print keeps it in one paragraph with the Nobel Prize sentence, English ¶9)
- ch 13 page 6 §13.4.2: "इसलिए ^16_8O के नाभिक का अपेक्षित द्रव्यमान" joined onto the paragraph before it (the expected mass of the 16_8O nucleus, 16.12744 u, is the end of English ¶1; the Hindi row was cut at the display)
- ch 13 page 6 §13.4.2: a new paragraph starts at "∴ ^16_8O को इसके घटकों में विभाजित" (English ¶7 (the energy needed to separate 16_8O into its constituents is 127.5 MeV/c^2) begins here, after Example 13.3's solution which ends ¶6)
- ch 13 page 7 §13.4.2: a new paragraph starts at "(i) यह बल आकर्षी है तथा" (English ¶14, conclusion (i) that the force is attractive, begins here; the Hindi row carries it on after the lead-in sentence, English ¶13)
- ch 13 page 8 §13.5: a new paragraph starts at "दो नाभिकों की स्थितिज ऊर्जा और उनके बीच की दूरी" (English ¶4 (the rough plot of the potential energy in Fig. 13.2, its minimum at 0.8 fm) begins here; the Hindi print keeps it in property (ii)'s paragraph)
- ch 13 page 9 §13.6: "इनमें प्रत्येक प्रकार के क्षय पर" joined onto the paragraph before it (the sentence that each type of decay is taken up in the coming subsections is printed only in the Hindi edition (the English goes from gamma-decay straight to 13.7); kept, joined to the paragraph it follows, English ¶2)
- ch 13 page 10 §13.7.1: a new paragraph starts at "A = 240 नाभिक के लिए E_bn लगभग 7.6 MeV" (English ¶8 (E_bn for the A = 240 nucleus is about 7.6 MeV) begins here; the Hindi print ends ¶7's line with 'then' and starts this one on a new line, as the English does)
- ch 13 page 11 §13.7.2: "अतः ताप नाभिकीय संलयन बहुत उच्च ताप" joined onto the paragraph before it (the paragraph that thermonuclear fusion needs very high temperature and pressure, found only inside stars, is printed only in the Hindi edition (the English goes from ¶4 straight to the p-p cycle); kept, joined to the paragraph it follows, English ¶4)
- ch 13 page 12 §13.7.3: a new paragraph starts at "(a) क्या नाभिकीय अभिक्रियाओं के समीकरण" (English ¶3, question (a) of Example 13.4 (are nuclear equations balanced like a chemical equation), begins here; the Hindi row runs the example's statement and its three questions together)
- ch 13 page 12 §13.7.3: a new paragraph starts at "(b) यदि प्रोटॉनों और न्यूट्रॉनों की संख्या" (English ¶4, question (b) of Example 13.4 (how mass converts into energy when protons and neutrons are conserved), begins here)
- ch 13 page 12 §13.7.3: a new paragraph starts at "(c) सामान्य विचार है कि केवल नाभिकीय" (English ¶5, question (c) of Example 13.4 (mass-energy interconversion in chemical reactions), begins here)
- ch 13 page 13 §13.7.3: a new paragraph starts at "नाभिकीय अभिक्रियाओं [जैसे कि समीकरण (13.10)]" (English ¶8 (in nuclear reactions such as Eq. 13.10 the proton and neutron numbers are the same on both sides) begins here; the Hindi print keeps it in answer (a)'s paragraph)
- ch 13 page 10 §13.7.1: "विखंडन के उदाहरणत: जब" → "विखंडन के उदाहरणतः जब" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb उदाहरणतः ('for example'), a visarga word, not a label before a colon (crop p10 x60-410 y277-294 at zoom 3; the decoded layer's line y279 agrees); the load's visarga list does not know it)
- ch 13 page 12 §13.7.2: "तारे के अंत: पटल" → "तारे के अंतः पटल" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the prefix अंतः ('inner', as in the book's अंतःक्षेपण), the page leaving a space before पटल, kept, a visarga word, not a label before a colon (crop p12 x60-410 y250-267 at zoom 3; the decoded layer's line y252 agrees); the load's visarga list does not know it)
- ch 13 page 13 §13.7.3: "दृष्टि से, सिद्धांतत: एक" → "दृष्टि से, सिद्धांततः एक" (the page sets the visarga glued to the word (Walkman-Chanakya draws the visarga and the colon with one glyph), and the word is the adverb सिद्धांततः ('in principle'), a visarga word, not a label before a colon (crop p13 x170-506 y423-439 at zoom 3; the decoded layer's line y425 agrees); the load's visarga list does not know it)
- ch 13 page 3 §13.2: "यह एक प्रोटॉन, एक" → "यह एक प्रोट्रॉन, एक" (the page prints प्रोट्रॉन, a र-stroke under the ट (crop p3 x440-522 y518-535 at zoom 12), where the same line prints प्रोटॉन a few words earlier; the decoded layer's line y520 agrees; the row repairs the book's slip, which still reads as the proton: as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 13 page 12 §13.7.3: "(प्लाज़्मा) का" → "(प्लाज्मा) का" (the page prints प्लाज्मा with no nukta (crop p12 x44-140 y527-544 at zoom 12); the decoded layer's line y529 agrees; the row adds the nukta; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 13 page 3 §13.2: "क्योंकि ड्यूटीरियम एवं ट्राइटियम" → "न्यूट्रॉन की खोज क्योंकि ड्यूटीरियम एवं ट्राइटियम" (the page prints the bold italic side heading न्यूट्रॉन की खोज above this paragraph (crop p3 x155-525 y150-215 at zoom 4, the heading at x158-245 y168-190 at zoom 8; the decoded layer's line y172 agrees) and the transcription left it out; a side heading leads its paragraph and is never dropped (DECISIONS 2026-09-27), as the English row leads with "Discovery of Neutron")
- ch 13 page 9 §13.7: "हलके नाभिकों, A > 30" → "हलके नाभिकों, A < 30" (the page prints 'हलके नाभिकों, A > 30, वाले भाग' (crop p9 x155-525 y588-640 at zoom 4; the decoded layer's line y625 agrees), which puts the lighter nuclei above A = 30, inside the flat region the same paragraph has just placed between A = 30 and A = 170; the English row reads 'For the lighter nuclei region, A < 30', as does the page's own §13.4.2 (ii) 'हलके नाभिकों (A < 30)' on p7. By the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 13 page 11 §13.7.2: "^3_2H + ^3_2H ->" → "^3_2He + ^3_2He ->" (the page prints reaction (iv) of Eq. (13.14) with ^3_2H + ^3_2H on the left (crop p11 x170-525 y685-735 at zoom 5; the decoded layer's line y717 agrees), a hydrogen symbol with Z = 2; the English row reads 3_2He + 3_2He, the ^3_2He that reaction (iii) on the line above produces, and only He-3 + He-3 balances to ^4_2He + 2 ^1_1H. By the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 13 page 7 §13.4.2: "विचाराधीन नाभिक की" → "विचाराधीन न्यूक्लियॉन की" (the page prints 'विचाराधीन नाभिक की बंधन-ऊर्जा' — the nucleus under consideration (crop p7 x170-525 y588-736 at zoom 3), where the English row says 'the binding energy of the nucleon under consideration' and the sentence's own subject is one nucleon: by the founder's ruling of 2026-10-10 the row writes the meaning (the founder ruled this paragraph's four places on 2026-10-16))
- ch 13 page 7 §13.4.2: "यदि किसी नाभिक के नाभिकीय बल" → "यदि किसी न्यूक्लियॉन के नाभिकीय बल" (the page prints 'यदि किसी नाभिक के नाभिकीय बल के परिसर में अधिकतम p न्यूक्लियॉन' (crop p7 x170-525 y588-736 at zoom 3), where the English row says 'if a nucleon can have a maximum of p neighbours within the range of nuclear force': by the founder's ruling of 2026-10-10 the row writes the meaning (the founder, 2026-10-16))
- ch 13 page 7 §13.4.2: "नाभिक की बंधन-ऊर्जा पर A की वृद्धि" → "प्रति न्यूक्लियॉन बंधन-ऊर्जा पर A की वृद्धि" (the page prints 'नाभिक की बंधन-ऊर्जा पर A की वृद्धि का कुल प्रभाव नगण्य रहता है' — the nucleus's binding energy (crop p7 x170-525 y588-736 at zoom 3), where the English row says 'the change in binding energy per nucleon would be small' and the next sentence prints प्रति न्यूक्लियॉन बंधन-ऊर्जा: by the founder's ruling of 2026-10-10 the row writes the meaning (the founder, 2026-10-16); 'माना कि किसी नाभिक की बंधन-ऊर्जा pk है' stays as printed, the English printing 'nucleus' there too)
- ch 13 page 8 §13.4.2: "कोई नाभिक केवल अपने निकट के नाभिकों" → "कोई न्यूक्लियॉन केवल अपने निकट के न्यूक्लियॉनों" (the page prints 'कोई नाभिक केवल अपने निकट के नाभिकों को ही प्रभावित करता है' (crop p8 x40-410 y96-135 at zoom 3), where the English row defines the saturation property as 'a given nucleon influences only nucleons close to it': by the founder's ruling of 2026-10-10 the row writes the meaning (the founder, 2026-10-16))
- ch 14 page 2 §14.2: "विद्युत चालकता (sigma) अथवा प्रतिरोधकता" joined onto the paragraph before it (the side heading 'On the basis of conductivity' leads English ¶1 as its first words; the transcription set it as a row of its own (the page prints it on its own line above the paragraph, layer line y381), so the paragraph under it joins onto it)
- ch 14 page 2 §14.2: a new paragraph starts at "(i) धातु : इनकी प्रतिरोधकता बहुत कम" (English ¶2, item (i) Metals with its rho and sigma ranges, begins here; the Hindi row runs items (i) to (iii) on after the classification sentence)
- ch 14 page 2 §14.2: a new paragraph starts at "(ii) अर्धचालक : इनकी प्रतिरोधकता या चालकता" (English ¶3, item (ii) Semiconductors with its rho and sigma ranges, begins here)
- ch 14 page 2 §14.2: a new paragraph starts at "(iii) विद्युतरोधी : इनकी प्रतिरोधकता बहुत अधिक" (English ¶4, item (iii) Insulators with its rho and sigma ranges, begins here)
- ch 14 page 3 §14.2: "आजकल उपलब्ध अधिकांश अर्धचालक युक्तियाँ" starts a new paragraph, not the previous page's (English ¶7, most current devices being based on Si, Ge and inorganic compounds, begins at the top of p3; the Hindi row carries it on from ¶6's list of semiconductor kinds at the foot of p2)
- ch 14 page 5 §14.3: "चित्र 14.3 में दिखाई गई Si या Ge की संरचना" joined onto the paragraph before it (the 2-dimensional picture of Fig. 14.4, broken bonds and the hole are the rest of English ¶1, which runs from the diamond-like lattice to the hole as an apparent free particle; the Hindi print starts a new paragraph at Fig. 14.4)
- ch 14 page 7 §14.4: a new paragraph starts at "(i) पंच संयोजक (संयोजकता 5); जैसे" (English ¶4, item (i) pentavalent dopants (As, Sb, P), begins here; the Hindi row runs both items on after 'two types of dopants')
- ch 14 page 7 §14.4: a new paragraph starts at "(ii) त्रि संयोजक (संयोजकता 3); जैसे" (English ¶5, item (ii) trivalent dopants (In, B, Al), begins here)
- ch 14 page 13 §14.6.1: a new paragraph starts at "इस सांद्रता प्रवणता के कारण p-फलक" (English ¶5, the injected carriers diffusing because of this concentration gradient (Fig. 14.14), begins here; the Hindi print keeps it in ¶4's paragraph on minority carrier injection)
- ch 14 page 15 §14.6.2: a new paragraph starts at "(a) वक्र से I = 20 mA" (English ¶10, part (a) of Example 14.4's solution (r_fb = 10 Ω), begins here; the Hindi row runs the solution as one paragraph)
- ch 14 page 15 §14.6.2: a new paragraph starts at "(b) वक्र से V =" (English ¶11, part (b) of Example 14.4's solution (r_rb = 1.0 × 10^7 Ω), begins here)
- ch 14 page 2 §14.1: "junction) ट्रांज़िस्टर" → "junction) ट्रांजिस्टर" (the page prints ट्रांजिस्टर with no nukta (crop p2 x252-310 y316-333 at zoom 12); the decoded layer's line y317 agrees; the row adds the nukta; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 14 page 9 §14.4: "का स्रोत केवल" → "का स्त्रोत केवल" (the page prints स्त्रोत, its conjunct drawn as स्त्र (a half स and त्र) — the shape of शास्त्री on ch 10 p5 (crop x362-385 y302-317) and unlike the स्र of स्रोतों on ch 10 p10 (crop x337-362 y682-697), all three in one montage at zoom 10 and 20; crop p9 x298-323 y440-455; the decoded layer's line y440 agrees. The row's स्रोत repairs the book's spelling, which states nothing false: as printed (DECISIONS 2026-10-03, round four 2026-10-14))
- ch 14 page 11 §14.5: "डायोड, ट्रांज़िस्टर आदि" → "डायोड, ट्रांजिस्टर आदि" (the page prints ट्रांजिस्टर with no nukta (crop p11 x317-375 y125-142 at zoom 12, in one montage with p2's); the decoded layer's line y127 agrees; the row adds the nukta; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 14 page 11 §14.5.1: "(या धनात्मक स्पेस-चार्ज क्षेत्र)" → "(या धनात्मक स्पेस-चार्ज़ क्षेत्र)" (the page prints स्पेस-चार्ज़ with a nukta under ज (crop p11 x213-240 y460-476, zoom 12, in one montage with the page's other four स्पेस-चार्ज); the decoded layer's line y461 agrees. Four of the page's five places print the nukta, ¶4's 'ऋणात्मक स्पेस-चार्ज क्षेत्र होने' (x148-175 y595-611) prints none and keeps the row's spelling; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 14 page 11 §14.5.1: "(ऋणात्मक स्पेस-चार्ज क्षेत्र) की" → "(ऋणात्मक स्पेस-चार्ज़ क्षेत्र) की" (the page prints स्पेस-चार्ज़ with a nukta under ज (crop p11 x260-287 y505-521, zoom 12, in one montage with the page's other four स्पेस-चार्ज); the decoded layer's line y506 agrees. Four of the page's five places print the nukta, ¶4's 'ऋणात्मक स्पेस-चार्ज क्षेत्र होने' (x148-175 y595-611) prints none and keeps the row's spelling; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 14 page 11 §14.5.1: "इस स्पेस-चार्ज क्षेत्र को" → "इस स्पेस-चार्ज़ क्षेत्र को" (the page prints स्पेस-चार्ज़ with a nukta under ज (crop p11 x355-382 y520-536, zoom 12, in one montage with the page's other four स्पेस-चार्ज); the decoded layer's line y521 agrees. Four of the page's five places print the nukta, ¶4's 'ऋणात्मक स्पेस-चार्ज क्षेत्र होने' (x148-175 y595-611) prints none and keeps the row's spelling; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 14 page 11 §14.5.1: "धनात्मक स्पेस-चार्ज क्षेत्र तथा" → "धनात्मक स्पेस-चार्ज़ क्षेत्र तथा" (the page prints स्पेस-चार्ज़ with a nukta under ज (crop p11 x240-267 y580-596, zoom 12, in one montage with the page's other four स्पेस-चार्ज); the decoded layer's line y581 agrees. Four of the page's five places print the nukta, ¶4's 'ऋणात्मक स्पेस-चार्ज क्षेत्र होने' (x148-175 y595-611) prints none and keeps the row's spelling; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 14 page 12 §14.5.1: "है। साम्यवस्था में" → "है। साम्यवास्था में" (the page prints साम्यवास्था, the ा after व (crop p12 x330-420 y145-161 at zoom 12); the decoded layer's line y146 agrees; the row's साम्यवस्था is neither the print nor the standard साम्यावस्था; as printed (DECISIONS 2026-10-03, round four 2026-10-14): a spelling the print carries, which states nothing false)
- ch 14 page 3 §14.2: "स्तर चालन बैंड के उच्चतम स्तर" → "स्तर संयोजकता बैंड के उच्चतम स्तर" (the page prints 'यदि चालन बैंड में निम्नतम स्तर चालन बैंड के उच्चतम स्तर से भी नीचे है' ('if the lowest level in the conduction band is below the highest level of the conduction band') (crop p3 x40-410 y400-448 at zoom 4; the decoded layer's line y419 agrees); the English row reads 'lower than the highest level of the valence band', which the rest of the sentence (electrons pass from the valence band) and the overlap that follows require. By the founder's ruling of 2026-10-10 the row writes the meaning: संयोजकता बैंड ('valence band'), the book's own term)
- ch 14 page 9 §14.4: "p-प्रकार का अर्धचालक तब बनता है" → "(ii) p-प्रकार के अर्धचालक p-प्रकार का अर्धचालक तब बनता है" (the page prints the bold italic side heading (ii) p-प्रकार के अर्धचालक above this paragraph (crop p9 x40-365 y155-245 at zoom 4; the decoded layer's line y192 agrees) and the transcription left it out; a side heading leads its paragraph and is never dropped (DECISIONS 2026-09-27), as the English row leads with "(ii) p-type semiconductor")
- ch 14 page 9 §14.4: "समग्र ऋणात्मक उदासीनता" → "समग्र आवेश उदासीनता" (the page prints 'क्रिस्टल एक समग्र ऋणात्मक उदासीनता बनाए रखता है' ('the crystal maintains an overall negative neutrality') (crop p9 x40-365 y525-590 at zoom 4; the decoded layer's line y545 agrees), a sign put on a crystal the sentence's own reason (the carriers' charge equal and opposite to the ionised cores') makes neutral; the English row reads 'the crystal maintains an overall charge neutrality'. By the founder's ruling of 2026-10-10 the row writes the meaning: आवेश ('charge'), the sentence's own word for charge)
- ch 14 page 11 §14.5.1: "इलेक्ट्रॉन p से n की ओर" → "इलेक्ट्रॉन n से p की ओर" (the page prints 'जब कोई इलेक्ट्रॉन p से n की ओर विसरित होता है' ('when an electron diffuses from p to n') (crop p11 x40-410 y412-448 at zoom 4; the decoded layer's line y416 agrees); the English row reads 'from n → p', and the same paragraph says the electron leaves an ionised donor on the n-side and continues 'n -> p'. By the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 14 page 12 §14.5.1: "p-क्षेत्र में होलों की प्राप्ति" → "p-क्षेत्र में इलेक्ट्रॉनों की प्राप्ति" (the page prints 'p-क्षेत्र में होलों की प्राप्ति' ('the gain of holes by the p-region') (crop p12 x205-525 y173-210 at zoom 4; the decoded layer's line y177 agrees), where the p-region loses holes by diffusion; the English row reads 'the gain of electron by the p-region', as the same paragraph prints later ('p-पदार्थ ने इलेक्ट्रॉन अर्जित किए हैं'). By the founder's ruling of 2026-10-10 the row writes the meaning)
- ch 14 page 14 §14.6.2: "हम मिलीमीटर का उपयोग" → "हम मिलीऐमीटर का उपयोग" (the page prints मिलीमीटर ('millimetre', a length) as the meter used for the forward-bias current (crop p14 x155-525 y333-382 at zoom 4 and x330-470 y350-366 at zoom 7; the decoded layer's line y351 agrees); the English row reads 'we use a milliammeter since the expected current is large'. By the founder's ruling of 2026-10-10 the row writes the meaning: मिलीऐमीटर, spelt as the same sentence spells माइक्रोऐमीटर)
rulings on verifier flags that change no text: 19

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

- ch 14 §14.2 ¶17: "14.2(c)"
- ch 14 §14.3 ¶3: "14.5 (b)"
- ch 14 §14.6.2 ¶5: "(b)"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 0 | 276 | 224 |

## sections whose Hindi rows do not number as the English (English is canonical)

70 of 70 sections one to one
| chapter | section | en | hi | unprinted |
|---|---|---|---|---|

## English paragraphs this edition does not print — left without Hindi by an `unprinted` entry

none
pairs: pairs.jsonl — each address's English and Hindi, to read

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 9 | 34 | 26 | 135 | 76.5% |
| 10 | 19 | 17 | 78 | 89.5% |
| 11 | 16 | 13 | 68 | 81.3% |
| 12 | 16 | 13 | 57 | 81.3% |
| 13 | 17 | 13 | 87 | 76.5% |
| 14 | 20 | 17 | 75 | 85.0% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 122 | 122 | 99 | 500 | 100.0% |

## addresses in the database this extraction no longer carried — a load would delete them

- ch 11 §11.7 ¶13
- ch 11 §11.8 ¶10
- ch 11 §11.8 ¶8
- ch 11 §11.8 ¶9
- ch 12 §12.2.2 ¶6
- ch 12 §12.2.2 ¶7
- ch 13 §13.6 ¶3
- ch 13 §13.7.2 ¶10
- ch 14 §14.3 ¶8
- ch 9 §9.2.2 ¶5
- ch 9 §9.5.4 ¶11

## rows the other edition still carries — this edition's text a load would clear from them

none

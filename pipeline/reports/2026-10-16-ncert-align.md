# margai-pipeline ncert align

- run: 2026-10-16 08:10 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 500 English and 500 Hindi row(s) in 6 chapter(s)
- result: ok
request id: pipeline-ncert-align-8ee50bbf-c6af-4569-8870-34cd6a00ba75
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 500 | 186329 | ₹5.28 |

## sections whose rows do not pair one to one

en counts the English rows aligned: a number an `unprinted` entry left is listed below, not here
| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 9 | 9.2.3 | 24 | 24 | 2 |
| 14 | 14.2 | 18 | 18 | 2 |

## English paragraphs the Hindi does not print — numbers an `unprinted` entry left, not aligned

none

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 9 §9.2.3: hi ¶19 = en ¶19+¶20 (split hi)
- ch 9 §9.2.3: hi ¶20+¶21 = en ¶21 (join hi)
- ch 14 §14.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 14 §14.2: hi ¶3+¶4 = en ¶4 (join hi)

## pairs weaker than 0.45 — read both against the pages

- ch 9 §9.2: hi ¶1 = en ¶1 — similarity 0.37 — en «We are familiar with the laws of reflection.…» · hi «हम परावर्तन के नियमों से परिचित हैं। परावर्तन…»
- ch 9 §9.2.3: hi ¶2 = en ¶2 — similarity 0.45 — en «In principle, we can take any two rays…» · hi «सिद्धांत रूप में, हम वस्तु के किसी बिंदु…»
- ch 9 §9.3: hi ¶2 = en ¶2 — similarity 0.42 — en «(i) The incident ray, the refracted ray and…» · hi «(i) आपतित किरण, अपवर्तित किरण तथा अंतरापृष्ठ के…»
- ch 9 §9.5: hi ¶1 = en ¶1 — similarity 0.43 — en «We have so far considered refraction at a…» · hi «अब तक हमने समतल अंतरापृष्ठों पर अपवर्तन के…»
- ch 9 §9.5.1: hi ¶2 = en ¶2 — similarity 0.43 — en «Equation (9.16) gives us a relation between object…» · hi «समीकरण (9.16) से हमें बिंब तथा प्रतिबिंब के…»
- ch 10 §10.2: hi ¶6 = en ¶6 — similarity 0.44 — en «In a similar manner, we can use Huygens…» · hi «इसी विधि द्वारा हम हाइगेंस के सिद्धांत का…»
- ch 10 §10.3.3: hi ¶11 = en ¶11 — similarity 0.45 — en «(a) Reflection and refraction arise through interaction of…» · hi «(a) परावर्तन तथा अपवर्तन, आपतित प्रकाश की पदार्थ…»
- ch 10 §10.6.1: hi ¶9 = en ¶9 — similarity 0.42 — en «In the double-slit experiment, we must note that…» · hi «द्विझिरी प्रयोग में, हमें ध्यान देना चाहिए कि…»
- ch 10 §10.7: hi ¶3 = en ¶3 — similarity 0.43 — en «It should be mentioned that the linearly polarised…» · hi «यह बतलाना आवश्यक है कि [समीकरणों (10.15) तथा…»
- ch 11 §11.4.2: hi ¶4 = en ¶4 — similarity 0.45 — en «We can now repeat this experiment with incident…» · hi «अब हम इस प्रयोग को आपतित विकिरण की…»
- ch 11 §11.4.3: hi ¶3 = en ¶3 — similarity 0.26 — en «These observations have two implications:» · hi «इन प्रेक्षणों में दो तथ्य अंतर्निहित हैं :»
- ch 11 §11.4.3: hi ¶9 = en ¶9 — similarity 0.38 — en «We now summarise the experimental features and observations…» · hi «अब हम इस अनुभाग में वर्णन किए गए…»
- ch 11 §11.4.3: hi ¶11 = en ¶11 — similarity 0.42 — en «(ii) For a given photosensitive material and frequency…» · hi «(ii) किसी दिए गए प्रकाश-संवेदी पदार्थ और आपतित…»
- ch 12 §12.2: hi ¶2 = en ¶2 — similarity 0.44 — en «A typical graph of the total number of…» · hi «चित्र 12.3 में किसी दिए समयांतराल में विभिन्न…»
- ch 12 §12.2.1: hi ¶1 = en ¶1 — similarity 0.43 — en «The trajectory traced by an alpha-particle depends on…» · hi «ऐल्फ़ा-कण द्वारा अनुरेखित प्रक्षेप पथ, संघट्ट के संघट्ट…»
- ch 13 §13.3: hi ¶3 = en ¶3 — similarity 0.42 — en «By performing scattering experiments in which fast electrons,…» · hi «ऐसे प्रकीर्णन प्रयोग करके जिनमें alpha-कणों के स्थान…»
- ch 13 §13.4.2: hi ¶13 = en ¶13 — similarity 0.29 — en «We can draw some conclusions from these two…» · hi «इस प्रकार निम्न निष्कर्षों पर पहुँच सकते हैं…»
- ch 13 §13.7.2: hi ¶3 = en ¶3 — similarity 0.40 — en «When fusion is achieved by raising the temperature…» · hi «ऊर्जा की उपयोगी मात्रा उत्पन्न करने के लिए…»

## numbers one edition of a pair carries and the other does not

- ch 9 §9.2.3 ¶14: only in en 9.6 · only in hi 9.5
- ch 9 §9.2.3 ¶16: only in en 9.7 · only in hi —
- ch 9 §9.4 ¶2: only in en — · only in hi 90
- ch 9 §9.5.1 ¶2: only in en — · only in hi 9.16
- ch 9 §9.5.2 ¶5: only in en 9.17 · only in hi 9.16, 9.16
- ch 9 §9.5.2 ¶9: only in en — · only in hi 9.22
- ch 9 §9.5.2 ¶21: only in en — · only in hi 1.33
- ch 9 §9.5.3 ¶5: only in en — · only in hi 1
- ch 9 §9.6 ¶9: only in en 2, 1 · only in hi 21
- ch 9 §9.7.1 ¶3: only in en — · only in hi 6
- ch 9 §9.7.1 ¶5: only in en — · only in hi 0, 0
- ch 9 §9.7.1 ¶6: only in en — · only in hi 0
- ch 9 §9.7.1 ¶9: only in en — · only in hi 0, 0, 0
- ch 9 §9.7.1 ¶10: only in en — · only in hi 0
- ch 9 §9.7.1 ¶13: only in en — · only in hi 0, 0
- ch 9 §9.7.1 ¶15: only in en — · only in hi 0, 0
- ch 9 §9.7.2 ¶1: only in en — · only in hi 0, 0
- ch 9 §9.7.2 ¶4: only in en — · only in hi 1
- ch 10 §10.1 ¶3: only in en — · only in hi 9
- ch 10 §10.1 ¶6: only in en 1855 · only in hi 1864
- ch 10 §10.3.1 ¶3: only in en — · only in hi 2, 1
- ch 10 §10.4 ¶1: only in en — · only in hi 11
- ch 10 §10.4 ¶8: only in en — · only in hi 2.5
- ch 10 §10.6.1 ¶3: only in en — · only in hi 10.14
- ch 10 §10.6.2 ¶3: only in en 10.15 · only in hi 10.14
- ch 10 §10.7 ¶1: only in en — · only in hi 10.15, 11
- ch 10 §10.7 ¶6: only in en 10.18, 2, 2, 2 · only in hi 1, 1, 1
- ch 10 §10.7 ¶10: only in en 2, 3 · only in hi —
- ch 11 §11.1 ¶1: only in en 19 · only in hi —
- ch 11 §11.7 ¶11: only in en — · only in hi 0, 10, 5.16, 14
- ch 12 §12.4.1 ¶1: only in en 0 · only in hi —
- ch 12 §12.6 ¶2: only in en — · only in hi 11
- ch 12 §12.6 ¶4: only in en 12.15 · only in hi 12.5
- ch 13 §13.6 ¶2: only in en — · only in hi 100
- ch 13 §13.7.1 ¶8: only in en — · only in hi 13.1
- ch 13 §13.7.2 ¶2: only in en — · only in hi 13.13
- ch 14 §14.3 ¶1: only in en — · only in hi 14.4
- ch 14 §14.3 ¶7: only in en 4 · only in hi —
- ch 14 §14.4 ¶7: only in en — · only in hi 4, 5
- ch 14 §14.4 ¶10: only in en — · only in hi 14.8

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 9 §9.2.3 ¶7 — en p4, hi p4, similarity 0.53
- en: Figure 9.5 shows the ray diagram considering three rays. It shows the image A'B' (in this case, real) of an object AB formed by a concave mirror. It does not mean that only three rays emanate from the point A. An infinite number of rays emanate from any source, in all directions. Thus, point A' is image point of A if every ray originating at point A and falling on the concave mirror after reflection passes through the point A'.
- hi: चित्र 9.5 बिंब के बिंदु A से निकलने वाली तीन किरणों को ध्यान में रखकर किरण-आरेख दर्शाता है। इसमें अवतल दर्पण द्वारा बनाया गया बिंब AB का प्रतिबिंब A′B′ (इस स्थिति में वास्तविक) दर्शाया गया है। इसका यह अर्थ नहीं है कि बिंदु A से केवल तीन किरणें ही निकलती हैं। किसी भी स्रोत से सभी दिशाओं में अनंत किरणें निकलती हैं। अतः यदि बिंदु A से निकलने वाली प्रत्येक किरण, अवतल दर्पण द्वारा परावर्तन के पश्चात बिंदु A′ से होकर गुज़रती है तो बिंदु A′ बिंदु A का वास्तविक प्रतिबिंब है।

### 2. ch 9 §9.2.3 ¶8 — en p5, hi p4, similarity 0.61
- en: We now derive the mirror equation or the relation between the object distance (u), image distance (v) and the focal length (f ).
- hi: अब हम दर्पण समीकरण अथवा बिंब दूरी (u), प्रतिबिंब दूरी (v) तथा फ़ोकस दूरी (f) के बीच संबंध व्युत्पन्न करेंगे।

### 3. ch 9 §9.2.3 ¶13 — en p6, hi p6, similarity 0.46
- en: We have derived here the mirror equation, Eq. (9.7), and the magnification formula, Eq. (9.9), for the case of real, inverted image formed by a concave mirror. With the proper use of sign convention, these are, in fact, valid for all the cases of reflection by a spherical mirror (concave or convex) whether the image formed is real or virtual. Figure 9.6 shows the ray diagrams for virtual image formed by a concave and convex mirror. You should verify that Eqs. (9.7) and (9.9) are valid for these cases as well.
- hi: यहाँ पर हमने दर्पण समीकरण [समीकरण (9.7)] तथा आवर्धन सूत्र [समीकरण (9.9)] अवतल दर्पण द्वारा बने वास्तविक तथा उलटे प्रतिबिंब के लिए व्युत्पन्न किए हैं। परंतु वास्तव में उचित चिह्न परिपाटी का उपयोग करने पर, ये संबंध गोलीय दर्पणों (अवतल तथा उत्तल) द्वारा परावर्तन के सभी उदाहरणों (चाहे प्रतिबिंब वास्तविक बने या आभासी) पर लागू होते हैं। चित्र 9.6 में अवतल तथा उत्तल दर्पण द्वारा आभासी प्रतिबिबों की रचना के किरण-आरेख दर्शाए गए हैं। आप स्वयं यह सत्यापित कर सकते हैं कि समीकरण (9.7) तथा (9.9) इन उदाहरणों के लिए भी मान्य हैं।

### 4. ch 9 §9.2.3 ¶14 — en p6, hi p6, similarity 0.46
- en: Example 9.1 Suppose that the lower half of the concave mirror's reflecting surface in Fig. 9.6 is covered with an opaque (non-reflective) material. What effect will this have on the image of an object placed in front of the mirror?
- hi: उदाहरण 9.1 मान लीजिए चित्र 9.5 में दर्शाए अवतल दर्पण के परावर्तक पृष्ठ के नीचे का आधा भाग किसी अपारदर्शी (अपरावर्ती) पदार्थ से ढक दिया गया है। दर्पण के सामने स्थित किसी बिंब के दर्पण द्वारा बने प्रतिबिंब पर इसका क्या प्रभाव पड़ेगा?

### 5. ch 9 §9.2.3 ¶24 — en p8, hi p8, similarity 0.51 — the founder reads this one
- en: Although the jogger has been moving with a constant speed, the speed of his/her image appears to increase substantially as he/she moves closer to the mirror. This phenomenon can be noticed by any person sitting in a stationary car or a bus. In case of moving vehicles, a similar phenomenon could be observed if the vehicle in the rear is moving closer with a constant speed.
- hi: यद्यपि धावक एक अपरिवर्ती चाल से गतिमान है तथापि धावक दर्पण के जैसे-जैसे निकट आएगा उसके प्रतिबिंब की चाल में पर्याप्त वृद्धि प्रतीत होती जाएगी। यह परिघटना किसी स्थिर कार अथवा स्थिर बस में बैठा कोई भी व्यक्ति देख सकता है। यदि पीछे से आने वाला वाहन एक अपरिवर्ती चाल से लगातार पास आ रहा हो तो, चलते हुए वाहनों में इसी प्रकार की परिघटना देखी जा सकती है।

### 6. ch 9 §9.3 ¶5 — en p9, hi p9, similarity 0.53
- en: Note: Optical density should not be confused with mass density, which is mass per unit volume. It is possible that mass density of an optically denser medium may be less than that of an optically rarer medium (optical density is the ratio of the speed of light in two media). For example, turpentine and water. Mass density of turpentine is less than that of water but its optical density is higher.
- hi: नोट : प्रकाशिक घनत्व तथा द्रव्यमान घनत्व के बीच भ्रम उत्पन्न नहीं होना चाहिए। द्रव्यमान घनत्व एकांक आयतन का द्रव्यमान है। यह संभव है कि किसी प्रकाशिक सघन माध्यम का द्रव्यमान घनत्व प्रकाशिक विरल माध्यम के द्रव्यमान घनत्व से कम हो (प्रकाशिक घनत्व दो माध्यमों में प्रकाश की चाल का अनुपात है)। उदाहरण के लिए, तारपीन का तेल तथा जल। तारपीन के तेल का द्रव्यमान घनत्व जल के द्रव्यमान घनत्व से कम होता है। लेकिन इसका प्रकाशिक घनत्व अधिक होता है।

### 7. ch 9 §9.4 ¶1 — en p9, hi p9, similarity 0.62
- en: When light travels from an optically denser medium to a rarer medium at the interface, it is partly reflected back into the same medium and partly refracted to the second medium. This reflection is called the internal reflection.
- hi: जब प्रकाश किसी प्रकाशतः सघन माध्यम से प्रकाशतः विरल माध्यम में गमन करता है, तब अंतरापृष्ठ पर वह अंशतः वापस उसी माध्यम में परावर्तित हो जाता है तथा अंशतः दूसरे माध्यम में अपवर्तित हो जाता है। इस परावर्तन को आंतरिक परावर्तन कहते हैं।

### 8. ch 9 §9.5.2 ¶17 — en p15, hi p15, similarity 0.49
- en: Here again it must be remembered that each point on an object gives out infinite number of rays. All these rays will pass through the same image point after refraction at the lens.
- hi: यहाँ पर यह अवश्य याद रखना चाहिए कि किसी बिंब के प्रत्येक बिंदु से अनंत किरणें उत्सर्जित होती हैं। ये सभी किरणें लेंस से अपवर्तन के पश्चात एक ही प्रतिबिंब बिंदु से गुज़रती हैं।

### 9. ch 9 §9.5.2 ¶19 — en p16, hi p16, similarity 0.51
- en: When we apply the sign convention, we see that, for erect (and virtual) image formed by a convex or concave lens, m is positive, while for an inverted (and real) image, m is negative.
- hi: चिह्न परिपाटी का पालन करने पर हम यह पाते हैं कि उत्तल अथवा अवतल लेंस द्वारा बने सीधे (तथा आभासी) प्रतिबिंब के लिए m धनात्मक होता है, जबकि किसी उलटे (तथा वास्तविक) प्रतिबिंब के लिए m ऋणात्मक होता है।

### 10. ch 9 §9.5.4 ¶6 — en p18, hi p18, similarity 0.68
- en: The derivation is valid for any number of thin lenses in contact. If several thin lenses of focal length f_1, f_2, f_3,... are in contact, the effective focal length of their combination is given by 1/f = 1/f_1 + 1/f_2 + 1/f_3 + … (9.31)
- hi: यह व्युत्पत्ति संपर्क में रखे कई पतले लेंसों के निकाय के लिए भी मान्य है। यदि f_1, f_2, f_3,... फ़ोकस दूरियों के बहुत से लेंस एक-दूसरे के संपर्क में रखे हैं, तो इस संयोजन की प्रभावी फ़ोकस दूरी होगी : 1/f = 1/f_1 + 1/f_2 + 1/f_3 + ... (9.31)

### 11. ch 9 §9.6 ¶1 — en p19, hi p19, similarity 0.61
- en: Figure 9.21 shows the passage of light through a triangular prism ABC. The angles of incidence and refraction at the first face AB are i and r_1, while the angle of incidence (from glass to air) at the second face AC is r_2 and the angle of refraction or emergence e. The angle between the emergent ray RS and the direction of the incident ray PQ is called the angle of deviation, delta.
- hi: चित्र 9.21 में किसी प्रिज़्म ABC से प्रकाश किरण को गुज़रते हुए दर्शाया गया है। पहले फलक AB पर आपतन कोण तथा अपवर्तन कोण क्रमशः i तथा r_1 हैं, जबकि दूसरे फलक (काँच से वायु में) AC पर आपतन कोण r_2 तथा अपवर्तन कोण या निर्गत कोण e हैं। निर्गत किरण RS तथा आपतित किरण की दिशा PQ के बीच के कोण को विचलन कोण delta कहते हैं।

### 12. ch 9 §9.6 ¶10 — en p20, hi p21, similarity 0.57
- en: The angles A and D_m can be measured experimentally. Equation (9.38) thus provides a method of determining refractive index of the material of the prism.
- hi: कोण A तथा D_m की माप प्रयोग द्वारा की जा सकती है। इस प्रकार समीकरण (9.38) प्रिज़्म के पदार्थ के अपवर्तनांक के मापन की विधि है।

### 13. ch 9 §9.7.1 ¶9 — en p23, hi p24, similarity 0.55
- en: We now obtain the magnification due to a compound microscope. The ray diagram of Fig. 9.24 shows that the (linear) magnification due to the objective, namely h'/h, equals m_O = h'/h = L/f_o (9.43) where we have used the result tan beta = (h / f_o) = (h' / L)
- hi: अब हम संयुक्त सूक्ष्मदर्शी के कारण आवर्धन प्राप्त करेंगे। चित्र 9.24 का किरण आरेख यह दर्शाता है कि अभिदृश्यक के कारण (रैखिक) आवर्धन, अर्थात h'/h, बराबर है m_0 = h'/h = L/f_0 (9.43) यहाँ हमने इस परिमाण का उपयोग किया है tan beta = (h/f_0) = (h'/L)

### 14. ch 9 §9.7.2 ¶6 — en p25–26, hi p27, similarity 0.59
- en: For these reasons, modern telescopes use a concave mirror rather than a lens for the objective. Telescopes with mirror objectives are called reflecting telescopes. There is no chromatic aberration in a mirror. Mechanical support is much less of a problem since a mirror weighs much less than a lens of equivalent optical quality, and can be supported over its entire back surface, not just over its rim. One obvious problem with a reflecting telescope is that the objective mirror focusses light inside the telescope tube. One must have an eyepiece and the observer right there, obstructing some light (depending on the size of the observer cage). This is what is done in the very large 200 inch (~5.08 m) diameters, Mt. Palomar telescope, California. The viewer sits near the focal point of the mirror, in a small cage. Another solution to the problem is to deflect the light being focussed by another mirror. One such arrangement using a convex secondary mirror to focus the incident light, which now passes through a hole in the objective primary mirror, is shown in Fig. 9.26. This is known as a Cassegrain telescope, after its inventor. It has the advantages of a large focal length in a short telescope. The largest telescope in India is in Kavalur, Tamil Nadu. It is a 2.34 m diameter reflecting telescope (Cassegrain). It was ground, polished, set up, and is being used by the Indian Institute of Astrophysics, Bangalore. The largest reflecting telescopes in the world are the pair of Keck telescopes in Hawaii, USA, with a reflector of 10 metre in diameter.
- hi: यही कारण है कि आधुनिक दूरदर्शकों में अभिदृश्यक के रूप में लेंस के स्थान पर अवतल दर्पण का उपयोग किया जाता है। ऐसे दूरदर्शकों को जिनमें अभिदृश्यक दर्पण होता है, परावर्ती दूरदर्शक (दूरबीन) कहते हैं। दर्पण में कोई वर्ण विपथन नहीं होता। यांत्रिक सहारा देने की समस्या भी काफ़ी कम होती है क्योंकि लेंस की तुलना में, तुल्य प्रकाशिक गुणता का दर्पण अपेक्षाकृत कम भारी होता है तथा दर्पण को केवल रिम पर ही सहारा देने की बजाय उसके समस्त पीछे के पृष्ठ को सहारा प्रदान किया जा सकता है। परावर्ती दूरबीन की एक सुस्पष्ट समस्या यह होती है कि अभिदृश्यक दर्पण दूरदर्शक की नली के भीतर प्रकाश को फ़ोकसित करता है। अतः नेत्रिका तथा प्रेक्षक को उसी स्थान पर होना चाहिए जिससे प्रकाश के मार्ग में अवरोध के कारण कुछ प्रकाश कम हो जाता है (यह अवरोध प्रेक्षक के बैठने के लिए बनाए गए पिंजरेनुमा कमरे के साइज़ पर निर्भर करता है)। ऐसा ही प्रयोग अति विशाल 200 इंच (~5.08 m) व्यास के माउंट पेलोमर दूरदर्शक, कैलिफ़ोर्निया में किया गया है। प्रेक्षक एक छोटे पिंजरे में दर्पण के फ़ोकस बिंदु के निकट बैठता है। इस समस्या का एक अन्य समाधान यह है कि फोकसित होने वाले प्रकाश को किसी अन्य दर्पण द्वारा विक्षेपित कर दिया जाए। ऐसी ही एक व्यवस्था चित्र 9.26 में दर्शायी गई है, जिसमें आपतित प्रकाश को फ़ोकसित करने के लिए किसी उत्तल द्वितीयक दर्पण का उपयोग किया जाता है जो अब अभिदृश्यक (प्राथमिक दर्पण) के छिद्र से गुज़रता है। इस दूरदर्शक को इसके आविष्कारक के नाम पर कैसेग्रेन दूरदर्शक (Cassegrain telescope) कहते हैं। इसका एक लाभ यह है कि छोटे दूरदर्शक में बड़ी फ़ोकस दूरी होती है। भारतवर्ष में सबसे बड़ा दूरदर्शक कवलूर, तमिलनाडु में है। यह 2.34 m व्यास की कैसेग्रेन परावर्ती दूरदर्शक है। इसे घर्षित किया गया, फिर पॉलिश की गई और व्यवस्थित किया गया तथा अब इसे भारतीय खगोल भौतिकी संस्थान, बंगलुरू द्वारा प्रयोग किया जा रहा है। संसार का सबसे बड़ा परावर्ती दूरदर्शक हवाई, संयुक्त राज्य अमेरिका में कैक दूरदर्शकों का युगल है जिसके परावर्तक का व्यास 10 मीटर है।

### 15. ch 10 §10.2 ¶3 — en p3, hi p3, similarity 0.50
- en: Now, if we know the shape of the wavefront at t = 0, then Huygens principle allows us to determine the shape of the wavefront at a later time tau. Thus, Huygens principle is essentially a geometrical construction, which given the shape of the wafefront at any time allows us to determine the shape of the wavefront at a later time. Let us consider a diverging wave and let F_1F_2 represent a portion of the spherical wavefront at t = 0 (Fig. 10.2). Now, according to Huygens principle, each point of the wavefront is the source of a secondary disturbance and the wavelets emanating from these points spread out in all directions with the speed of the wave. These wavelets emanating from the wavefront are usually referred to as secondary wavelets and if we draw a common tangent to all these spheres, we obtain the new position of the wavefront at a later time.
- hi: अब यदि हमें t = 0 पर किसी तरंगाग्र की आकृति ज्ञात है तो हाइगेंस के सिद्धांत द्वारा हम किसी बाद के समय tau पर तरंगाग्र की आकृति ज्ञात कर सकते हैं। अतः हाइगेंस का सिद्धांत वास्तव में एक ज्यामितीय रचना है जो किसी समय यदि तरंगाग्र की आकृति दी हुई हो तो किसी बाद के समय पर हम तरंगाग्र की आकृति ज्ञात कर सकते हैं। आइए, एक अपसरित तरंग के बारे में विचार करें और मान लीजिए F_1F_2, t = 0 समय पर एक गोलीय तरंगाग्र के एक भाग को प्रदर्शित करता है (चित्र 10.2)। अब हाइगेंस के सिद्धांत के अनुसार, तरंगाग्र का प्रत्येक बिंदु एक द्वितीयक विक्षोभ का स्रोत है और इन बिंदुओं से होने वाली तरंगिकाएँ तरंग की गति से सभी दिशाओं में फैलती हैं। तरंगाग्र से निर्गमन होने वाली इन तरंगिकाओं को प्रायः द्वितीयक तरंगिकाओं के नाम से जाना जाता है और यदि हम इन सभी गोलों पर एक उभयनिष्ठ स्पर्शक पृष्ठ खींचें तो हमें किसी बाद के समय पर तरंगाग्र की नयी स्थिति प्राप्त हो जाती है।

### 16. ch 10 §10.3.1 ¶4 — en p5, hi p5, similarity 0.76
- en: This is the Snell's law of refraction. Further, if lambda_1 and lambda_2 denote the wavelengths of light in medium 1 and medium 2, respectively and if the distance BC is equal to lambda_1 then the distance AE will be equal to lambda_2 (because if the crest from B has reached C in time tau, then the crest from A should have also reached E in time tau); thus, lambda_1 / lambda_2 = BC / AE = v_1 / v_2 or v_1 / lambda_1 = v_2 / lambda_2 (10.7)
- hi: यह स्नैल का अपवर्तन संबंधी नियम है। यदि lambda_1 तथा lambda_2 क्रमशः माध्यम 1 तथा माध्यम 2 में प्रकाश की तरंगदैर्घ्य को निरूपित करते हैं और यदि दूरी BC, lambda_1 के बराबर है तब दूरी AE, lambda_2 के बराबर होगी (क्योंकि यदि कोई शृंग B से C तक tau समय में पहुँचता है तो वह शृंग A से E तक भी tau समय में ही पहुँचेगा); अतः lambda_1 / lambda_2 = BC / AE = v_1 / v_2 अथवा v_1 / lambda_1 = v_2 / lambda_2 (10.7)

### 17. ch 10 §10.3.3 ¶5 — en p7, hi p7, similarity 0.60
- en: From the above discussion it follows that the total time taken from a point on the object to the corresponding point on the image is the same measured along any ray. For example, when a convex lens focusses light to form a real image, although the ray going through the centre traverses a shorter path, but because of the slower speed in glass, the time taken is the same as for rays travelling near the edge of the lens.
- hi: उपरोक्त विवेचन से यह ज्ञात होता है कि वस्तु पर किसी बिंदु से प्रतिबिंब के संगत बिंदु तक लगा कुल समय एक ही होता है, चाहे जिस भी किरण के अनुदिश मापा जाए। उदाहरण के लिए, जब कोई उत्तल लेंस, प्रकाश को एक वास्तविक प्रतिबिंब बनाने के लिए फ़ोकस करता है तो यद्यपि केंद्र से होकर जाने वाली किरणें छोटा पथ तय करती हैं, लेकिन काँच में धीमी चाल के कारण लगने वाला समय उतना ही होता है जितना कि लेंस के किनारे के निकट से होकर चलने वाली किरणों के लिए होता है।

### 18. ch 10 §10.5 ¶5 — en p12, hi p11, similarity 0.69
- en: On the other hand, we will have destructive interference resulting in a dark region when xd/D = (n + 1/2) lambda that is x = x_n = (n + 1/2) lambda D / d; n = 0, ± 1, ± 2 (10.14)
- hi: दूसरी ओर हमें विनाशी व्यतिकरण द्वारा अदीप्त क्षेत्र प्राप्त होंगे जब xd / D = (n + 1/2) lambda, अर्थात x = x_n = (n + 1/2) lambda D / d; n = 0, ±1, ±2 (10.14) के निकट अदीप्त क्षेत्र प्राप्त होंगे।

### 19. ch 10 §10.7 ¶11 — en p17, hi p17, similarity 0.64
- en: Therefore, the transmitted intensity will be maximum when theta = pi/4.
- hi: अतः, कोण theta = pi/4 के लिए पारगमित प्रकाश की तीव्रता अधिकतम होगी।

### 20. ch 11 §11.1 ¶2 — en p2, hi p2, similarity 0.69
- en: Around the same time, in 1887, it was found that certain metals, when irradiated by ultraviolet light, emitted negatively charged particles having small speeds. Also, certain metals when heated to a high temperature were found to emit negatively charged particles. The value of e/m of these particles was found to be the same as that for cathode ray particles. These observations thus established that all these particles, although produced under different conditions, were identical in nature. J. J. Thomson, in 1897, named these particles as electrons, and suggested that they were fundamental, universal constituents of matter. For his epoch-making discovery of electron, through his theoretical and experimental investigations on conduction of electricity by gasses, he was awarded the Nobel Prize in Physics in 1906. In 1913, the American physicist R. A. Millikan (1868-1953) performed the pioneering oil-drop experiment for the precise measurement of the charge on an electron. He found that the charge on an oil-droplet was always an integral multiple of an elementary charge, 1.602 × 10^-19 C. Millikan's experiment established that electric charge is quantised. From the values of charge (e) and specific charge (e/m), the mass (m) of the electron could be determined.
- hi: लगभग उसी समय, 1887 में, यह पाया गया कि जब कुछ निश्चित धातुओं को पराबैंगनी प्रकाश द्वारा किरणित करते हैं तो कम वेग वाले ऋण-आवेशित कण उत्सर्जित होते हैं। इसी प्रकार, जब कुछ निश्चित धातुओं को उच्च ताप तक गरम किया जाता है तो ये ऋण-आवेशित कण उत्सर्जित करते हैं। इन कणों के लिए e/m का मान उतना ही पाया गया जितना कि कैथोड किरण कणों का था। इस प्रकार इन प्रेक्षणों ने यह स्थापित कर दिया कि ये सभी कण, यद्यपि भिन्न दशाओं में उत्पन्न हुए थे, प्रकृति में समान थे। जे.जे. टॉमसन ने, 1897 में, इन कणों को इलेक्ट्रॉन नाम दिया और सुझाया कि ये द्रव्य के मौलिक सार्वत्रिक अवयव हैं। गैसों में विद्युत के संवहन पर उनके सैद्धांतिक तथा प्रायोगिक प्रेक्षणों के द्वारा इलेक्ट्रॉन की इस युगांतकारी खोज के लिए उन्हें 1906 में नोबेल पुरस्कार प्रदान किया गया। 1913 में अमेरिकी भौतिकविज्ञानी आर.ए. मिलिकन (1868–1953) ने इलेक्ट्रॉन पर आवेश के परिशुद्ध मापन के लिए तेल-बूँद का पथ प्रदर्शक प्रयोग किया। उन्होंने यह पाया कि तेल-बिंदुक पर आवेश सदैव एक मूल आवेश, 1.602 × 10^(-19) C का पूर्ण गुणांक है। मिलिकन के प्रयोग ने यह प्रस्थापित कर दिया कि वैद्युतआवेश क्वांटीकृत है। आवेश (e) तथा आपेक्षित आवेश (e/m) के मान से, इलेक्ट्रॉन का द्रव्यमान (m) ज्ञात किया जा सका।

### 21. ch 11 §11.2 ¶2 — en p2, hi p2, similarity 0.59 — the founder reads this one
- en: This unit of energy is commonly used in atomic and nuclear physics. The work function (phi_0) depends on the properties of the metal and the nature of its surface.
- hi: साधारणतया ऊर्जा की इस इकाई का प्रयोग परमाणु तथा नाभिकीय भौतिकी में किया जाता है। कार्य-फलन (phi_0) धातु के गुणों और इसके पृष्ठ की प्रकृति पर निर्भर करता है।

### 22. ch 11 §11.2 ¶3 — en p2, hi p2, similarity 0.68 — the founder reads this one
- en: The minimum energy required for the electron emission from the metal surface can be supplied to the free electrons by any one of the following physical processes:
- hi: धातु के पृष्ठ से इलेक्ट्रॉन उत्सर्जन के लिए मुक्त इलेक्ट्रॉनों को न्यूनतम आवश्यक ऊर्जा निम्न किसी भी एक भौतिक विधि के द्वारा दी जा सकती है :

### 23. ch 11 §11.3.2 ¶4 — en p3–4, hi p3, similarity 0.58 — the founder reads this one
- en: After the discovery of the electron in 1897, it became evident that the incident light causes electrons to be emitted from the emitter plate. Due to negative charge, the emitted electrons are pushed towards the collector plate by the electric field. Hallwachs and Lenard also observed that when ultraviolet light fell on the emitter plate, no electrons were emitted at all when the frequency of the incident light was smaller than a certain minimum value, called the threshold frequency. This minimum frequency depends on the nature of the material of the emitter plate.
- hi: 1897 में इलेक्ट्रॉन की खोज के पश्चात यह निश्चित हो गया कि उत्सर्जक पट्टिका से इलेक्ट्रॉनों के उत्सर्जन का कारक आपतित प्रकाश है। ऋण आवेश के कारण उत्सर्जित इलेक्ट्रॉन विद्युत क्षेत्र द्वारा संग्राहक पट्टिका की ओर धकेले जाते हैं। हालवॉक्स तथा लीनार्ड ने यह भी प्रेक्षित किया कि जब उत्सर्जक पट्टिका पर एक नियत न्यूनतम मान से कम आवृत्ति का पराबैंगनी प्रकाश पड़ता है तो कोई भी इलेक्ट्रॉन उत्सर्जित नहीं होता। इस नियत न्यूनतम आवृत्ति को देहली आवृत्ति (threshold frequency) कहते हैं तथा इसका मान उत्सर्जक पट्टिका के पदार्थ की प्रकृति पर निर्भर करता है।

### 24. ch 11 §11.4.1 ¶1 — en p5, hi p4–5, similarity 0.49 — the founder reads this one
- en: The collector A is maintained at a positive potential with respect to emitter C so that electrons ejected from C are attracted towards collector A. Keeping the frequency of the incident radiation and the potential fixed, the intensity of light is varied and the resulting photoelectric current is measured each time. It is found that the photocurrent increases linearly with intensity of incident light as shown graphically in Fig. 11.2. The photocurrent is directly proportional to the number of photoelectrons emitted per second. This implies that the number of photoelectrons emitted per second is directly proportional to the intensity of incident radiation.
- hi: संग्राहक A को उत्सर्जक C की तुलना में एक धन विभव पर रखा जाता है जिससे C से उत्सर्जित इलेक्ट्रॉन संग्राहक A की ओर आकर्षित होते हैं। आपतित विकिरण की आवृत्ति तथा विभव को स्थिर रखते हुए, प्रकाश की तीव्रता को परिवर्तित किया जाता है और परिणामी प्रकाश-विद्युत धारा को प्रत्येक बार मापा जाता है। यह पाया जाता है कि प्रकाशिक धारा आपतित प्रकाश की तीव्रता के साथ रैखिकतः बढ़ती है जैसा कि चित्र 11.2 में ग्राफीय रूप में दर्शाया गया है। प्रकाशिक धारा उत्सर्जित होने वाले प्रति सेकंड इलेक्ट्रॉनों की संख्या के अनुक्रमानुपाती है, अतः उत्सर्जित होने वाले प्रति सेकंड प्रकाशिक इलेक्ट्रॉनों की संख्या आपतित विकिरण की तीव्रता के समानुपाती है।

### 25. ch 11 §11.4.2 ¶2 — en p5, hi p5, similarity 0.55
- en: We now apply a negative (retarding) potential to the plate A with respect to the plate C and make it increasingly negative gradually. When the polarity is reversed, the electrons are repelled and only the sufficiently energetic electrons are able to reach the collector A. The photocurrent is found to decrease rapidly until it drops to zero at a certain sharply defined, critical value of the negative potential V_0 on the plate A. For a particular frequency of incident radiation, the minimum negative (retarding) potential V_0 given to the plate A for which the photocurrent stops or becomes zero is called the cut-off or stopping potential.
- hi: अब हम पट्टिका A पर पट्टिका C की तुलना में एक ऋण (मंदक) विभव लगाते हैं और इसे धीरे-धीरे अधिक ऋणात्मक करते जाते हैं। जब पट्टिकाओं की ध्रुवता बदली जाती है तो इलेक्ट्रॉन प्रतिकर्षित होते हैं तथा केवल कुछ पर्याप्त ऊर्जा वाले इलेक्ट्रॉन ही संग्राहक A तक पहुँच पाते हैं। यह पाया गया कि प्रकाशिक-धारा तेज़ी से कम होती जाती है जब तक कि यह पट्टिका A पर ऋण विभव V_0 के किसी निश्चित तीक्ष्ण और स्पष्ट क्रांतिक मान पर शून्य नहीं हो जाती। आपतित विकिरण की एक निश्चित आवृत्ति के लिए पट्टिका A पर दिया गया निम्नतम ऋण (मंदक) विभव V_0 जिस पर प्रकाशिक-धारा शून्य हो जाती है, अंतक (Cut-off) अथवा निरोधी विभव (Stopping potential) कहलाता है।

### 26. ch 11 §11.4.2 ¶3 — en p5–6, hi p5, similarity 0.57 — the founder reads this one
- en: The interpretation of the observation in terms of photoelectrons is straightforward. All the photoelectrons emitted from the metal do not have the same energy. Photoelectric current is zero when the stopping potential is sufficient to repel even the most energetic photoelectrons, with the maximum kinetic energy (K_max), so that K_max = e V_0 (11.1)
- hi: प्रकाशिक इलेक्ट्रॉन के द्वारा प्रेक्षण की व्याख्या सीधी है। धातु से उत्सर्जित सभी प्रकाशिक इलेक्ट्रॉन समान ऊर्जा वाले नहीं होते। प्रकाश-विद्युत धारा तब शून्य होती है जब निरोधी विभव अधिकतम ऊर्जा वाले प्रकाशिक इलेक्ट्रॉनों, जिनकी उच्चतम गतिज ऊर्जा (K_उच्च) है, को प्रतिकर्षित करने की अवस्था में हो। अर्थात K_उच्च = e V_0 (11.1)

### 27. ch 11 §11.6 ¶1 — en p8, hi p8, similarity 0.64
- en: In 1905, Albert Einstein (1879-1955) proposed a radically new picture of electromagnetic radiation to explain photoelectric effect. In this picture, photoelectric emission does not take place by continuous absorption of energy from radiation. Radiation energy is built up of discrete units – the so called quanta of energy of radiation. Each quantum of radiant energy has energy h nu, where h is Planck's constant and nu the frequency of light. In photoelectric effect, an electron absorbs a quantum of energy (h nu) of radiation. If this quantum of energy absorbed exceeds the minimum energy needed for the electron to escape from the metal surface (work function phi_0), the electron is emitted with maximum kinetic energy K_max = h nu – phi_0 (11.2)
- hi: सन् 1905 में अल्बर्ट आइंसटाइन (1879 – 1955) ने प्रकाश-विद्युत प्रभाव की व्याख्या के लिए वैद्युतचुंबकीय विकिरण का एक मौलिक रूप से नया चित्रण प्रस्तावित किया। इस चित्रण में, प्रकाश-विद्युत उत्सर्जन विकिरण से संतत ऊर्जा-अवशोषण के द्वारा नहीं होता। विकिरण ऊर्जा विविक्त इकाइयों से बनी होती है–जो विकिरण की ऊर्जा के क्वांटा कहलाते हैं। विकिरण ऊर्जा के प्रत्येक क्वांटम की ऊर्जा hnu होती है, जहाँ h प्लांक स्थिरांक है और nu प्रकाश की आवृत्ति। प्रकाश-विद्युत प्रभाव में, एक इलेक्ट्रॉन विकिरण के एक क्वांटम की ऊर्जा (hnu) अवशोषित करता है। यदि ऊर्जा का यह अवशोषित क्वांटम इलेक्ट्रॉन के लिए धातु की सतह से बाहर निकल आने के लिए निम्नतम आवश्यक ऊर्जा से अधिक होता है (कार्य-फलन, phi_0) तब उत्सर्जित इलेक्ट्रॉन की अधिकतम गतिज ऊर्जा होगी : K_उच्च = hnu – phi_0 (11.2)

### 28. ch 12 §12.2.1 ¶2 — en p5, hi p5, similarity 0.47
- en: The fact that only a small fraction of the number of incident particles rebound back indicates that the number of alpha-particles undergoing head on collision is small. This, in turn, implies that the mass and positive charge of the atom is concentrated in a small volume. Rutherford scattering therefore, is a powerful way to determine an upper limit to the size of the nucleus.
- hi: यह तथ्य कि आपतित कणों में से केवल एक छोटा भाग ही टकराकर वापस लौटता है, यह सूचित करता है कि प्रत्यक्ष संघट्ट की स्थिति में आने वाले ऐल्फ़ा-कणों की संख्या बहुत कम है। इससे ज्ञात होता है कि नाभिक का द्रव्यमान तथा धनात्मक आवेश बहुत छोटे आयतन में संकेंद्रित है। इस प्रकार रदरफोर्ड प्रकीर्णन नाभिक के साइज़ की उच्चसीमा ज्ञात करने का एक शक्तिशाली साधन है।

### 29. ch 12 §12.2.1 ¶6 — en p6, hi p6, similarity 0.52
- en: Solution The key idea here is that throughout the scattering process, the total mechanical energy of the system consisting of an alpha-particle and a gold nucleus is conserved. The system's initial mechanical energy is E_i, before the particle and nucleus interact, and it is equal to its mechanical energy E_f when the alpha-particle momentarily stops. The initial energy E_i is just the kinetic energy K of the incoming alpha- particle. The final energy E_f is just the electric potential energy U of the system. The potential energy U can be calculated from Eq. (12.1).
- hi: हल यहाँ मुख्य धारणा यह है कि प्रकीर्णन प्रक्रम की समस्त अवधि में किसी तंत्र जैसे ऐल्फ़ा-कण और स्वर्ण-नाभिक की कुल यांत्रिक ऊर्जा संरक्षित रहती है। ऐल्फ़ा-कण और नाभिक की अन्योन्यक्रिया से पूर्व तंत्र की प्रारंभिक यांत्रिक ऊर्जा E_i कण के क्षणिक रूप से विरामावस्था में आने पर उसकी यांत्रिक ऊर्जा E_f के बराबर है। प्रारंभिक ऊर्जा E_i आगामी ऐल्फ़ा-कण की गतिज ऊर्जा K के ठीक बराबर है। अंतिम ऊर्जा E_f तंत्र की विद्युत स्थितिज ऊर्जा U ही है। स्थितिज ऊर्जा U का समीकरण (12.1) से परिकलन किया जा सकता है।

### 30. ch 12 §12.4.1 ¶3 — en p11, hi p11, similarity 0.71
- en: The energy level diagram* for the stationary states of a hydrogen atom, computed from Eq. (12.10), is given in Fig. 12.7. The principal quantum number n labels the stationary states in the ascending order of energy. In this diagram, the highest energy state corresponds to n = ∞ in Eq, (12.10) and has an energy of 0 eV. This is the energy of the atom when the electron is completely removed (r = ∞) from the nucleus and is at rest. Observe how the energies of the excited states come closer and closer together as n increases.
- hi: समीकरण (12.10) से अभिकलित हाइड्रोजन परमाणु की स्थायी अवस्थाओं का ऊर्जा स्तर आरेख* चित्र (12.7) में दर्शाया गया है। मुख्य क्वांटम संख्या n स्थायी अवस्थाओं को ऊर्जा के आरोही क्रम में अंकित करता है। इस चित्र में उच्चतम ऊर्जा अवस्था समीकरण (12.10) में n = ∞ के संगत है तथा इसकी ऊर्जा 0 eV है। यह परमाणु की वह ऊर्जा है जब नाभिक से इलेक्ट्रॉन पूरी तरह दूर कर दिया गया है (r = ∞) और वह विराम में है। ध्यान दीजिए कि उत्तेजित अवस्थाओं की ऊर्जाएँ n के बढ़ने पर किस प्रकार पास-पास आ जाती हैं।

### 31. ch 12 §12.6 ¶4 — en p13, hi p13, similarity 0.55 — the founder reads this one
- en: This is the quantum condition proposed by Bohr for the angular momentum of the electron [Eq. (12.15)]. In Section 12.5, we saw that this equation is the basis of explaining the discrete orbits and energy levels in hydrogen atom. Thus de Broglie hypothesis provided an explanation for Bohr's second postulate for the quantisation of angular momentum of the orbiting electron. The quantised electron orbits and energy states are due to the wave nature of the electron and only resonant standing waves can persist.
- hi: यह बोर द्वारा प्रस्तावित इलेक्ट्रॉन के कोणीय संवेग का क्वांटम प्रतिबंध है [समीकरण (12.5)]। अनुच्छेद 12.5 में हमने देखा है कि यह समीकरण हाइड्रोजन परमाणु में ऊर्जा स्तरों तथा विविक्त कक्षाओं की व्याख्या करने का आधार है। इस प्रकार दे ब्रॉग्ली की परिकल्पना, परिक्रामी इलेक्ट्रॉन के कोणीय संवेग के क्वांटमीकरण की बोर द्वारा प्रस्तावित द्वितीय अभिगृहीत के लिए व्याख्या प्रस्तुत करती है। इलेक्ट्रॉन की क्वांटित कक्षाएँ तथा ऊर्जा स्थितियाँ, इलेक्ट्रॉन की तरंग प्रकृति के कारण हैं और केवल अनुनादी अप्रगामी तरंगें ही अवस्थित रह सकती हैं।

### 32. ch 13 §13.2 ¶5 — en p2, hi p2, similarity 0.64 — the founder reads this one
- en: This is equal to the mass of the hydrogen atom (= 1.00783u), minus the mass of a single electron (m_e = 0.00055 u). The other two isotopes of hydrogen are called deuterium and tritium. Tritium nuclei, being unstable, do not occur naturally and are produced artificially in laboratories.
- hi: यह हाइड्रोजन परमाणु के द्रव्यमान 1.00783 u में से, इसमें विद्यमान एक इलेक्ट्रॉन के द्रव्यमान m_e = 0.00055 u को घटाने से प्राप्त द्रव्यमान के बराबर है। हाइड्रोजन के दूसरे दो समस्थानिक ड्यूटीरियम एवं ट्राइटियम कहलाते हैं। ट्राइटियम नाभिक अस्थायी होने के कारण प्रकृति में नहीं पाए जाते और कृत्रिम विधियों द्वारा प्रयोगशालाओं में निर्मित किए जाते हैं।

### 33. ch 13 §13.2 ¶8 — en p3, hi p3, similarity 0.80
- en: The mass of a neutron is now known to a high degree of accuracy. It is m_n = 1.00866 u = 1.6749×10^-27 kg (13.3)
- hi: अब हम न्यूट्रॉन का द्रव्यमान अत्यधिक यथार्थता से जानते हैं। यह है, m_n = 1.00866 u = 1.6749×10^(-27) kg (13.3)

### 34. ch 13 §13.4.1 ¶6 — en p5, hi p5, similarity 0.47 — the founder reads this one
- en: Experimental verification of the Einstein's mass-energy relation has been achieved in the study of nuclear reactions amongst nucleons, nuclei, electrons and other more recently discovered particles. In a reaction the conservation law of energy states that the initial energy and the final energy are equal provided the energy associated with mass is also included. This concept is important in understanding nuclear masses and the interaction of nuclei with one another. They form the subject matter of the next few sections.
- hi: आइंस्टाइन के द्रव्यमान-ऊर्जा संबंध की प्रायोगिक पुष्टि, न्यूक्लियॉनों, नाभिकों, इलेक्ट्रॉनों एवं अन्य हाल ही में खोजे गए कणों के बीच होने वाली नाभिकीय अभिक्रियाओं के अध्ययन में हो चुकी है। किसी अभिक्रिया में ऊर्जा संरक्षण नियम से अभिप्राय है कि यदि द्रव्यमान से संबद्ध ऊर्जा को भी परिकलनों में सम्मिलित करें तो प्रारंभिक ऊर्जा अंतिम ऊर्जा के बराबर होती है। यह संकल्पना, नाभिकों की पारस्परिक अन्योन्य क्रियाओं एवं नाभिकीय द्रव्यमानों को समझने के लिए महत्वपूर्ण है। यही अगले कुछ अनुभागों की विषय-वस्तु है।

### 35. ch 13 §13.4.2 ¶14 — en p7, hi p7, similarity 0.63
- en: (i) The force is attractive and sufficiently strong to produce a binding energy of a few MeV per nucleon.
- hi: (i) यह बल आकर्षी है तथा प्रति न्यूक्लियॉन कुछ MeV बंधन उत्पन्न करने के लिए पर्याप्त प्रबल है।

### 36. ch 13 §13.7.1 ¶1 — en p10, hi p10, similarity 0.55 — the founder reads this one
- en: New possibilities emerge when we go beyond natural radioactive decays and study nuclear reactions by bombarding nuclei with other nuclear particles such as proton, neutron, alpha-particle, etc.
- hi: प्राकृतिक रेडियोऐक्टिव क्षयों के अलावा नाभिकों पर अन्य नाभिकीय कणों जैसे प्रोटॉन, न्यूट्रॉन, ऐल्फा कण आदि के प्रकार से होने वाली नाभिकीय प्रक्रियाओं पर ध्यान देने से नई संभावनाएँ बनती हैं।

### 37. ch 14 §14.2 ¶1 — en p2, hi p2, similarity 0.69
- en: On the basis of conductivity On the basis of the relative values of electrical conductivity (sigma) or resistivity (rho = 1/sigma), the solids are broadly classified as:
- hi: चालकता के आधार पर विद्युत चालकता (sigma) अथवा प्रतिरोधकता (rho = 1/sigma) के सापेक्ष मान के आधार पर ठोस पदार्थों का निम्न प्रकार से वर्गीकरण किया जाता है :

### 38. ch 14 §14.2 ¶6 — en p2, hi p2, similarity 0.71
- en: Our interest in this chapter is in the study of semiconductors which could be: (i) Elemental semiconductors: Si and Ge (ii) Compound semiconductors: Examples are: • Inorganic: CdS, GaAs, CdSe, InP, etc. • Organic: anthracene, doped pthalocyanines, etc. • Organic polymers: polypyrrole, polyaniline, polythiophene, etc.
- hi: इस अध्याय में हमारी रुचि अर्धचालकों के अध्ययन में है जो कई प्रकार के हो सकते हैं। (i) तात्विक अर्धचालक (Elemental semiconductors) – Si और Ge (ii) यौगिक अर्धचालक – उदाहरण हैं : • अकार्बनिक – CdS, GaAs, CdSe, InP, आदि। • कार्बनिक – एंथ्रासीन, मादित (Doped) थैलोस्यानीस, आदि। • कार्बनिक बहुलक (Organic polymers)– पॉलीपाइरोल, पॉलीऐनिलीन, पॉलीथायोफ़ीन आदि।

### 39. ch 14 §14.4 ¶13 — en p10, hi p10, similarity 0.72
- en: Though the above description is grossly approximate and hypothetical, it helps in understanding the difference between metals, insulators and semiconductors (extrinsic and intrinsic) in a simple manner. The difference in the resistivity of C, Si and Ge depends upon the energy gap between their conduction and valence bands. For C (diamond), Si and Ge, the energy gaps are 5.4 eV, 1.1 eV and 0.7 eV, respectively. Sn also is a group IV element but it is a metal because the energy gap in its case is 0 eV.
- hi: यद्यपि उपरोक्त विवरण समग्र रूप से सन्निकट तथा परिकल्पित है परंतु यह सरल ढंग से धातुओं, विद्युतरोधियों तथा अर्धचालकों (नैज तथा अपद्रव्यी) में अंतर को समझने में सहायक है। C, Si तथा Ge की प्रतिरोधकताओं में अंतर इनके चालन तथा संयोजी बैंडों के बीच ऊर्जा अंतराल पर निर्भर करता है। कार्बन (डायमंड), Si तथा Ge के लिए ऊर्जा अंतराल क्रमशः 5.4 eV, 1.1 eV तथा 0.7 eV है। Sn भी चौथे ग्रुप का तत्व है परंतु यह धातु है क्योंकि इसके प्रकरण में ऊर्जा अंतराल 0 eV है।

### 40. ch 14 §14.5.1 ¶1 — en p11, hi p11, similarity 0.60
- en: Consider a thin p-type silicon (p-Si) semiconductor wafer. By adding precisely a small quantity of pentavelent impurity, part of the p-Si wafer can be converted into n-Si. There are several processes by which a semiconductor can be formed. The wafer now contains p-region and n-region and a metallurgical junction between p-, and n- region.
- hi: p-प्रकार के सिलिकॉन (p-Si) अर्धचालक की पतली पटलिका (वेफ़र) पर विचार कीजिए। परिशुद्ध रूप में पंचसंयोजक अशुद्धि की अल्प मात्रा मिलाकर किसी p-Si पटलिका के कुछ भाग को n-Si में परिवर्तित किया जा सकता है। किसी अर्धचालक का निर्माण करने की बहुत-सी प्रक्रियाएँ हैं। अब पटलिका में p-क्षेत्र एवं n-क्षेत्र तथा p- तथा n- क्षेत्रों के बीच एक धातुकर्मी संधि है।

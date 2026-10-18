# margai-pipeline ncert align

- run: 2026-10-12 09:36 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 682 English and 682 Hindi row(s) in 7 chapter(s)
- result: ok
request id: pipeline-ncert-align-0e28a994-83aa-4840-9f4b-c393699bb6c7
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 682 | 245665 | ₹7.15 |

## sections whose rows do not pair one to one

| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 9 | 9.2.2 | 8 | 8 | 2 |
| 9 | 9.2.3 | 13 | 13 | 2 |
| 10 | 10.6 | 11 | 11 | 2 |
| 10 | 10.8.1 | 7 | 7 | 2 |
| 10 | 10.9.4 | 8 | 8 | 2 |
| 11 | 11.6 | 11 | 11 | 2 |
| 12 | 12.4.2 | 24 | 24 | 2 |
| 14 | 14.7 | 8 | 8 | 2 |

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 9 §9.2.2: hi ¶2+¶3 = en ¶2 (join hi)
- ch 9 §9.2.2: hi ¶4 = en ¶3+¶4 (split hi)
- ch 9 §9.2.3: hi ¶10+¶11 = en ¶10 (join hi)
- ch 9 §9.2.3: hi ¶12 = en ¶11+¶12 (split hi)
- ch 10 §10.6: hi ¶1 = en ¶1+¶2 (split hi)
- ch 10 §10.6: hi ¶2+¶3 = en ¶3 (join hi)
- ch 10 §10.8.1: hi ¶5 = en ¶5+¶6 (split hi)
- ch 10 §10.8.1: hi ¶6+¶7 = en ¶7 (join hi)
- ch 10 §10.9.4: hi ¶5+¶6 = en ¶5 (join hi)
- ch 10 §10.9.4: hi ¶7 = en ¶6+¶7 (split hi)
- ch 11 §11.6: hi ¶1+¶2 = en ¶1 (join hi)
- ch 11 §11.6: hi ¶3 = en ¶2+¶3 (split hi)
- ch 12 §12.4.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 12 §12.4.2: hi ¶2+¶3 = en ¶3 (join hi)
- ch 14 §14.7: hi ¶3+¶4 = en ¶3 (join hi)
- ch 14 §14.7: hi ¶5 = en ¶4+¶5 (split hi)

## pairs weaker than 0.45 — read both against the pages

- ch 8 §8.2: hi ¶5 = en ¶5 — similarity 0.42 — en «However, if two equal and opposite deforming forces…» · hi «यदि दो बराबर और विरोधी विरूपक बल बेलन…»
- ch 8 §8.2: hi ¶13 = en ¶13 — similarity 0.33 — en «Since the strain is a ratio of change…» · hi «चूंकि विकृत प्रारंभिक विमा तथा विमा में अंतर…»
- ch 8 §8.4: hi ¶4 = en ¶4 — similarity 0.44 — en «As stated earlier, the stress-strain behaviour varies from…» · hi «जैसा पहले कहा जा चुका है, प्रतिबल-विकृति व्यवहार…»
- ch 8 §8.5.3: hi ¶1 = en ¶1 — similarity 0.42 — en «In Section (8.3), we have seen that when…» · hi «पहले के खंड (8.2) में हम यह देख…»
- ch 9 §9.6.2: hi ¶5 = en ¶5 — similarity 0.45 — en «We make the following observations from above:» · hi «उपरोक्त चर्चा के आधार पर निम्नलिखित प्रेक्षण प्राप्त…»
- ch 9 §9.6.2: hi ¶9 = en ¶9 — similarity 0.44 — en «A fluid will stick to a solid surface…» · hi «यदि ठोस-वायु तथा तरल-वायु पृष्ठीय ऊर्जाओं के योग…»
- ch 9 §9.6.4: hi ¶2 = en ¶2 — similarity 0.43 — en «As we have been saying repeatedly, a liquid-air…» · hi «जैसा कि हम बार-बार चर्चा कर रहे हैं…»
- ch 11 §11.6: hi ¶6 = en ¶6 — similarity 0.39 — en «As Table 11.1 shows, the experimentally measured values…» · hi «जैसा कि सारणी से स्पष्ट है कि भविष्यवाणियाँ…»
- ch 11 §11.8.1: hi ¶6 = en ¶6 — similarity 0.44 — en «We now consider these processes in some detail…» · hi «अब हम इन प्रक्रमों के विषय में विस्तार…»
- ch 11 §11.9: hi ¶3 = en ¶3 — similarity 0.43 — en «Kelvin-Planck statement» · hi «केल्विन-प्लैंक का प्रकथन»
- ch 11 §11.9: hi ¶5 = en ¶5 — similarity 0.23 — en «Clausius statement» · hi «क्लासियस का प्रकथन»
- ch 12 §12.7: hi ¶10 = en ¶10 — similarity 0.39 — en «Using, the kinetic theory of gases, the bulk…» · hi «अणुगति सिद्धांत का उपयोग करके, श्यानता, ऊष्मा-चालकता, एवं…»
- ch 13 §13.2: hi ¶2 = en ¶2 — similarity 0.42 — en «Very often, the body undergoing periodic motion has…» · hi «सामान्यतः आवर्ती गति करने वाले पिण्ड की एक…»
- ch 13 §13.2: hi ¶4 = en ¶4 — similarity 0.40 — en «Simple harmonic motion is the simplest form of…» · hi «सरल आवर्ती गति दोलनीय गति का एक सरल…»
- ch 13 §13.2.1: hi ¶4 = en ¶4 — similarity 0.45 — en «Note, that the frequency, nu, is not necessarily…» · hi «ध्यान दीजिए, आवृत्ति का सदैव ही पूर्णांक होना…»
- ch 13 §13.6: hi ¶7 = en ¶7 — similarity 0.42 — en «Hence the force acting on the mass is…» · hi «अतः, गुटके पर आरोपित बल विस्थापन के अनुक्रमानुपाती…»
- ch 13 §13.7: hi ¶5 = en ¶5 — similarity 0.44 — en «Thus, the potential energy of a particle executing…» · hi «इस प्रकार, सरल आवर्त गति करते किसी कण…»
- ch 13 §13.7: hi ¶8 = en ¶8 — similarity 0.40 — en «The total mechanical energy of a harmonic oscillator…» · hi «जैसा कि संरक्षी बलों के अधीन गतियों के…»
- ch 14 §14.4.1: hi ¶1 = en ¶1 — similarity 0.43 — en «The speed of a mechanical wave is determined…» · hi «किसी यांत्रिक तरंग की चाल विक्षोभ के कारण…»
- ch 14 §14.5: hi ¶4 = en ¶4 — similarity 0.40 — en «The principle of superposition is basic to the…» · hi «अध्यारोपण का सिद्धांत व्यतिकरण की परिघटना का मूल…»
- ch 14 §14.6.1: hi ¶4 = en ¶4 — similarity 0.37 — en «The most significant feature of stationary waves is…» · hi «अप्रगामी तरंगों का सबसे महत्वपूर्ण लक्षण यह है…»
- ch 14 §14.6.1: hi ¶7 = en ¶7 — similarity 0.44 — en «Fig. 14.13 shows the first six harmonics of…» · hi «चित्र 14.13 में दोनों सिरों पर परिबद्ध तानित…»
- ch 14 §14.6.1: hi ¶11 = en ¶11 — similarity 0.40 — en «The systems above, strings and air columns, can…» · hi «उपरोक्त वर्णित निकायों, डोरी एवं वायु कॉलम में…»
- ch 14 §14.6.1: hi ¶12 = en ¶12 — similarity 0.37 — en «Normal modes of a circular membrane rigidly clamped…» · hi «किसी पात्र की परिधि से दृढ़तापूर्वक परिबद्ध वृत्ताकार…»

## numbers one edition of a pair carries and the other does not

- ch 8 §8.1 ¶1: only in en 6 · only in hi 7
- ch 8 §8.5.1 ¶4: only in en 1.0 · only in hi 1
- ch 8 §8.5.1 ¶6: only in en 0.70 · only in hi 0.7
- ch 8 §8.5.1 ¶7: only in en 9.1 · only in hi 8.1
- ch 8 §8.5.1 ¶13: only in en — · only in hi 1, 2
- ch 8 §8.5.1 ¶14: only in en 9.1 · only in hi 8.1
- ch 8 §8.5.1 ¶17: only in en 9.8 · only in hi 8.8
- ch 8 §8.5.2 ¶1: only in en 9.4, 8.12 · only in hi 8.9, 8.4
- ch 8 §8.5.2 ¶2: only in en 9.2, 9.1 · only in hi 8.2, 8.1
- ch 8 §8.5.2 ¶4: only in en 8.6, 9.4 · only in hi 8.5, 9.0
- ch 8 §8.5.3 ¶1: only in en 8.3 · only in hi 8.2, 8.1
- ch 8 §8.6 ¶2: only in en 9.8 · only in hi 10
- ch 8 §8.6 ¶4: only in en 8.16 · only in hi 8.15
- ch 8 §8.6 ¶5: only in en 8.9, 8.9 · only in hi 8.8, 8.8
- ch 9 §9.2.3 ¶1: only in en 0 · only in hi —
- ch 9 §9.2.3 ¶2: only in en — · only in hi 1
- ch 9 §9.2.3 ¶7: only in en 9.7 · only in hi 9.8
- ch 9 §9.2.4 ¶4: only in en 1.0, 3.0, 6.0 · only in hi 1, 3, 6
- ch 9 §9.2.4 ¶7: only in en 5.0, 9.7 · only in hi 9.6, 5
- ch 9 §9.4 ¶2: only in en 1738 · only in hi —
- ch 9 §9.4.1 ¶1: only in en 9.10 · only in hi 9.9
- ch 9 §9.4.1 ¶2: only in en — · only in hi 1, 1
- ch 9 §9.4.1 ¶5: only in en 9.15 · only in hi 9.14
- ch 9 §9.5 ¶5: only in en 9.13 · only in hi 9.15
- ch 9 §9.5 ¶6: only in en 1 · only in hi —
- ch 9 §9.5.1 ¶5: only in en 6.2 · only in hi 5.2
- ch 9 §9.5.1 ¶7: only in en 9.18 · only in hi 9.20
- ch 9 §9.6.5 ¶5: only in en 1 · only in hi —
- ch 9 §9.6.5 ¶6: only in en 8.00 · only in hi 8
- ch 10 §10.5 ¶3: only in en 1, 1, 1, 1 · only in hi —
- ch 10 §10.8 ¶4: only in en — · only in hi 10.2
- ch 10 §10.8 ¶7: only in en — · only in hi 10.3, 10.11, 100
- ch 10 §10.8 ¶8: only in en 10.11, 100 · only in hi —
- ch 10 §10.9.1 ¶5: only in en 10.5 · only in hi 10.6
- ch 10 §10.9.1 ¶12: only in en — · only in hi 0
- ch 10 §10.9.1 ¶13: only in en 0 · only in hi —
- ch 10 §10.10 ¶2: only in en — · only in hi 10.4, 1
- ch 10 §10.10 ¶9: only in en 10.20 · only in hi 10.19
- ch 11 §11.1 ¶4: only in en — · only in hi 19
- ch 11 §11.5 ¶5: only in en 11.1 · only in hi 11.20
- ch 11 §11.6 ¶6: only in en 11.1, 3 · only in hi —
- ch 11 §11.6 ¶7: only in en — · only in hi 1
- ch 11 §11.6 ¶8: only in en — · only in hi 1, 1
- ch 11 §11.6 ¶10: only in en — · only in hi 1
- ch 11 §11.8.1 ¶2: only in en 11.7 · only in hi —
- ch 11 §11.9 ¶2: only in en — · only in hi 1
- ch 11 §11.11 ¶3: only in en 11.9 · only in hi 11.11
- ch 11 §11.11 ¶8: only in en 2 · only in hi —
- ch 12 §12.1 ¶1: only in en — · only in hi 19
- ch 12 §12.2 ¶1: only in en 20 · only in hi —
- ch 12 §12.2 ¶4: only in en — · only in hi 1
- ch 12 §12.3 ¶14: only in en 1 · only in hi —
- ch 12 §12.3 ¶18: only in en 2 · only in hi —
- ch 12 §12.4.1 ¶2: only in en — · only in hi 2, 1
- ch 12 §12.4.1 ¶7: only in en 9 · only in hi 10, 1, 2
- ch 12 §12.4.2 ¶5: only in en — · only in hi 1, 1, 2, 2
- ch 12 §12.4.2 ¶12: only in en — · only in hi 1, 2
- ch 12 §12.4.2 ¶19: only in en 5 · only in hi 6
- ch 12 §12.5 ¶3: only in en — · only in hi 1, 1, 2, 2
- ch 12 §12.5 ¶4: only in en — · only in hi 3
- ch 12 §12.5 ¶9: only in en 12.26 · only in hi —
- ch 12 §12.6.1 ¶1: only in en — · only in hi 1
- ch 12 §12.6.3 ¶1: only in en — · only in hi 1
- ch 12 §12.6.3 ¶4: only in en 1 · only in hi —
- ch 12 §12.6.3 ¶5: only in en 2, 3 · only in hi —
- ch 12 §12.6.4 ¶1: only in en — · only in hi 1
- ch 12 §12.7 ¶5: only in en 0.02 · only in hi 6.02
- ch 13 §13.2.1 ¶6: only in en 1.25 · only in hi —
- ch 13 §13.3 ¶8: only in en 1, 2 · only in hi —
- ch 13 §13.5 ¶6: only in en 5 · only in hi 5.0
- ch 13 §13.7 ¶3: only in en 6 · only in hi 5
- ch 13 §13.7 ¶15: only in en — · only in hi 1, 2
- ch 13 §13.7 ¶16: only in en — · only in hi 1, 2
- ch 13 §13.7 ¶17: only in en — · only in hi 0.19, 0.0625, 1, 2
- ch 13 §13.8 ¶1: only in en 100 · only in hi 1, 2
- ch 13 §13.8 ¶10: only in en 13.25 · only in hi —
- ch 14 §14.1 ¶4: only in en 299, 792 · only in hi 12, 29, 97, 92
- ch 14 §14.3.2 ¶3: only in en 1 · only in hi —
- ch 14 §14.3.3 ¶9: only in en 1, 1 · only in hi —
- ch 14 §14.4 ¶2: only in en 14.12 · only in hi 14.2
- ch 14 §14.4.1 ¶2: only in en — · only in hi 1
- ch 14 §14.4.2 ¶1: only in en 8, 2 · only in hi 1
- ch 14 §14.4.2 ¶2: only in en — · only in hi 1
- ch 14 §14.4.2 ¶7: only in en — · only in hi 1, 1, 0
- ch 14 §14.5 ¶6: only in en — · only in hi 2, 1, 1, 1
- ch 14 §14.6 ¶2: only in en 0 · only in hi —
- ch 14 §14.6 ¶4: only in en 2, 2, 0 · only in hi —
- ch 14 §14.6.1 ¶3: only in en — · only in hi 14.37
- ch 14 §14.6.1 ¶6: only in en — · only in hi 2, 2, 1, 1
- ch 14 §14.6.1 ¶14: only in en 0.6 · only in hi 2, 0.3
- ch 14 §14.6.1 ¶15: only in en 14.15, 14.15, 1 · only in hi 14.40

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 8 §8.4 ¶1 — en p3, hi p3, similarity 0.46 — the founder reads this one
- en: The relation between the stress and the strain for a given material under tensile stress can be found experimentally. In a standard test of tensile properties, a test cylinder or a wire is stretched by an applied force. The fractional change in length (the strain) and the applied force needed to cause the strain are recorded. The applied force is gradually increased in steps and the change in length is noted. A graph is plotted between the stress (which is equal in magnitude to the applied force per unit area) and the strain produced. A typical graph for a metal is shown in Fig. 8.2. Analogous graphs for compression and shear stress may also be obtained. The stress-strain curves vary from material to material. These curves help us to understand how a given material deforms with increasing loads. From the graph, we can see that in the region between O to A, the curve is linear. In this region, Hooke's law is obeyed. The body regains its original dimensions when the applied force is removed. In this region, the solid behaves as an elastic body.
- hi: तनन प्रतिबल के अंतर्गत किसी दिए गए द्रव्य के लिए प्रतिबल तथा विकृति के बीच संबंध प्रयोग द्वारा जाना जा सकता है। तनन गुणों की मानक जाँच में, किसी परीक्षण बेलन या तार को एक प्रत्यारोपित बल द्वारा विस्तारित किया जाता है। लंबाई में भिन्नात्मक अन्तर (विकृति) तथा इस विकृति को उत्पन्न करने के लिए आवश्यक प्रत्यारोपित बल को रिकार्ड करते हैं। प्रत्यारोपित बल को धीरे-धीरे क्रमबद्ध चरणों में बढ़ाते हैं और लंबाई में परिवर्तन को नोट करते जाते हैं। प्रतिबल (जिसका मान एकांक क्षेत्रफल पर लगाए गए बल के मान के बराबर होता है) और उससे उत्पन्न विकृति के बीच एक ग्राफ खींचते हैं। किसी धातु के लिए ऐसा एक प्रारूपिक ग्राफ चित्र 8.2 में दिखाया गया है। संपीडन तथा अपरूपण प्रतिबल के लिए भी सदृश ग्राफ प्राप्त किए जा सकते हैं। भिन्न-भिन्न द्रव्यों के लिए प्रतिबल-विकृति वक्र भिन्न-भिन्न होते हैं। इन वक्रों की सहायता से हम यह समझ सकते हैं कि कोई दिया हुआ द्रव्य बढ़ते हुए लोड के साथ कैसे विरूपित होता है। ग्राफ से हम यह देख सकते हैं कि O से A के बीच में वक्र रैखिक है। इस क्षेत्र में हुक के नियम का पालन होता है। जब प्रत्यारोपित बल को हटा लिया जाता है तो पिण्ड अपनी प्रारंभिक विमाओं को पुनः प्राप्त कर लेता है। इस क्षेत्र में ठोस एक प्रत्यास्थ पिण्ड जैसा आचरण करता है।

### 2. ch 8 §8.4 ¶2 — en p3, hi p4, similarity 0.54
- en: In the region from A to B, stress and strain are not proportional. Nevertheless, the body still returns to its original dimension when the load is removed. The point B in the curve is known as yield point (also known as elastic limit) and the corresponding stress is known as yield strength (sigma_y) of the material.
- hi: A से B के बीच के क्षेत्र में प्रतिबल तथा विकृति अनुक्रमानुपाती नहीं है। फिर भी भार हटाने पर पिण्ड अभी भी अपनी प्रारंभिक विमाओं पर वापस आ जाता है। वक्र में बिंदु B पराभव बिंदु (अथवा प्रत्यास्थ सीमा) कहलाता है और संगत प्रतिबल को द्रव्य का पराभव सामर्थ्य (sigma_y) कहते हैं।

### 3. ch 8 §8.5.1 ¶2 — en p4, hi p4, similarity 0.56
- en: Since strain is a dimensionless quantity, the unit of Young's modulus is the same as that of stress i.e., N m^-2 or Pascal (Pa). Table 8.1 gives the values of Young's moduli and yield strengths of some material.
- hi: चूंकि विकृति एक विमाविहीन राशि है, यंग गुणांक की इकाई वही होती है जो प्रतिबल की, अर्थात्, N m^-2 या पास्कल (Pa)। सारणी 8.1 में कुछ द्रव्यों के यंग गुणांक तथा पराभव सामर्थ्य के मान दिए गए हैं।

### 4. ch 8 §8.5.1 ¶3 — en p4–5, hi p4–5, similarity 0.59
- en: From the data given in Table 8.1, it is noticed that for metals Young's moduli are large. Therefore, these materials require a large force to produce small change in length. To increase the length of a thin steel wire of 0.1 cm^2 cross-sectional area by 0.1%, a force of 2000 N is required. The force required to produce the same strain in aluminium, brass and copper wires having the same cross-sectional area are 690 N, 900 N and 1100 N respectively. It means that steel is more elastic than copper, brass and aluminium. It is for this reason that steel is preferred in heavy-duty machines and in structural designs. Wood, bone, concrete and glass have rather small Young's moduli.
- hi: सारणी 8.1 में दिए गए आँकड़ों से यह पता चलता है कि धातुओं के लिए यंग गुणांक अधिक होता है। इसलिए इन पदार्थों में लंबाई में थोड़ा ही अंतर उत्पन्न करने के लिए बहुत अधिक बल की आवश्यकता होती है। 0.1 cm^2 अनुप्रस्थ परिच्छेद के एक पतले इस्पात के तार की लंबाई को 0.1% से बढ़ाने के लिए 2000 N के बल की आवश्यकता होती है। उसी अनुप्रस्थ परिच्छेद के ऐलुमिनियम, पीतल तथा ताँबे के तारों में उतनी ही विकृति उत्पन्न करने के लिए आवश्यक बल क्रमशः 690 N, 900 N तथा 1100 N होते हैं। इसका अर्थ यह है कि ताँबा, पीतल तथा ऐलुमिनियम से इस्पात अधिक प्रत्यास्थ होता है। इसी कारण से अधिक काम ली जाने वाली मशीनों और संरचनात्मक डिज़ाइनों में इस्पात को अधिक वरीयता दी जाती है। लकड़ी, अस्थि, कंक्रीट तथा काँच के यंग गुणांक कम होते हैं।

### 5. ch 8 §8.5.1 ¶10 — en p6, hi p6, similarity 0.55 — the founder reads this one
- en: Mass of the performer = 60 kg
- hi: आधार देने वाले निष्पादक का द्रव्यमान = 60 kg

### 6. ch 8 §8.5.1 ¶15 — en p6, hi p6, similarity 0.58
- en: Length of each thighbone L = 0.5 m
- hi: प्रत्येक उर्वस्थि की लंबाई L = 0.5 m

### 7. ch 8 §8.5.2 ¶1 — en p6, hi p6, similarity 0.56
- en: The ratio of shearing stress to the corresponding shearing strain is called the shear modulus of the material and is represented by G. It is also called the modulus of rigidity. G = shearing stress (sigma_s)/shearing strain G = (F/A)/(Δx/L) = (F × L)/(A × Δx) (8.10) Similarly, from Eq. (9.4) G = (F/A)/theta = F/(A × theta) (8.11) The shearing stress sigma_s can also be expressed as sigma_s = G × theta (8.12)
- hi: अपरूपण प्रतिबल तथा संगत अपरूपण विकृति का अनुपात द्रव्य का अपरूपण गुणांक कहलाता है तथा इसे G से निरूपित करते हैं। इसे दृढ़ता गुणांक भी कहते हैं : G = अपरूपण प्रतिबल (sigma_s)/अपरूपण विकृति G = (F/A)/(Δx/L) = (F × L)/(A × Δx) (8.9) इसी प्रकार समीकरण (8.4) से G = (F/A)/theta = F/(A × theta) (8.10) अपरूपण प्रतिबल sigma_s को ऐसे भी व्यक्त किया जा सकता है sigma_s = G × theta (8.11)

### 8. ch 9 §9.2 ¶9 — en p2, hi p2–3, similarity 0.65
- en: The density of water at 4°C (277 K) is 1.0 × 10^3 kg m^-3. The relative density of a substance is the ratio of its density to the density of water at 4°C. It is a dimensionless positive scalar quantity. For example the relative density of aluminium is 2.7. Its density is 2.7 × 10^3 kg m^-3. The densities of some common fluids are displayed in Table 9.1.
- hi: 4 °C (277 K) पर जल का घनत्व 1.0 × 10^3 kg m^-3 है। किसी पदार्थ का आपेक्षिक घनत्व (विशिष्ट गुरुत्व) उस पदार्थ के घनत्व तथा जल के 4 °C पर घनत्व का अनुपात होता है। यह विमाहीन धनात्मक अदिश भौतिक राशि है। उदाहरण के लिए ऐलुमिनियम का आपेक्षिक घनत्व 2.7 है। जबकि इसका घनत्व 2.7 × 10^3 kg m^-3 है। सारणी 9.1 में कुछ सामान्य तरलों के घनत्व दर्शाए गए हैं।

### 9. ch 9 §9.2.3 ¶4 — en p5, hi p5, similarity 0.62
- en: An open tube manometer is a useful instrument for measuring pressure differences. It consists of a U-tube containing a suitable liquid i.e., a low density liquid (such as oil) for measuring small pressure differences and a high density liquid (such as mercury) for large pressure differences. One end of the tube is open to the atmosphere and the other end is connected to the system whose pressure we want to measure [see Fig. 9.5 (b)]. The pressure P at A is equal to pressure at point B. What we normally measure is the gauge pressure, which is P − P_a, given by Eq. (9.8) and is proportional to manometer height h.
- hi: दाबांतर को मापने के लिए खुली-नली मैनोमीटर एक लाभप्रद उपकरण है। इस युक्ति में एक U आकार की नली होती है जिसमें उपयुक्त द्रव भरा होता है अर्थात् कम दाबांतर मापने के लिए कम घनत्व का द्रव (जैसे तेल) तथा अधिक दाबांतर के लिए अधिक घनत्व का द्रव (जैसे पारा) भरा जाता है। नली का एक सिरा वायुमंडल में खुला छोड़ दिया जाता है तथा दूसरा सिरा जिस निकाय का दाब ज्ञात करना है, उससे जोड़ दिया जाता है। [देखिए चित्र 9.5 (b)]। बिंदु A पर दाब बिंदु B पर दाब के बराबर है। जिस दाब को हम सामान्यतः मापते हैं वह वास्तव में प्रमापी अथवा गेज़ दाब होता है। यह P – P_a के बराबर होता है जो समीकरण (9.8) द्वारा दिया जाता है तथा मैनोमीटर की ऊँचाई h के अनुपाती होता है।

### 10. ch 9 §9.2.3 ¶9 — en p5, hi p6, similarity 0.70
- en: Example 9.4 At a depth of 1000 m in an ocean (a) what is the absolute pressure? (b) What is the gauge pressure? (c) Find the force acting on the window of area 20 cm × 20 cm of a submarine at this depth, the interior of which is maintained at sea-level atmospheric pressure. (The density of sea water is 1.03 × 10^3 kg m^-3, g = 10 m s^-2.)
- hi: उदाहरण 9.4 समुद्र के नीचे 1000 m गहराई पर (a) परम दाब कितना है? (b) गेज़ दाब कितना है? (c) इस गहराई पर पनडुब्बी की 20 cm × 20 cm क्षेत्रफल वाली खिड़की (जिसके आंतरिक भाग का दाब समुद्र तल पर वायुमण्डलीय दाब के बराबर रखा गया है) पर आरोपित बल का आकलन कीजिए। (समुद्र जल का घनत्व 1.03 × 10^3 kg m^-3, g = 10 m s^-2 )

### 11. ch 9 §9.2.4 ¶8 — en p7, hi p7, similarity 0.82
- en: Answer Since pressure is transmitted undiminished throughout the fluid, F_1 = (A_1 / A_2) F_2 = pi(5 × 10^-2 m)^2 / pi(15 × 10^-2 m)^2 (1350 kg × 9.8 m s^-2) = 1470 N ≈ 1.5 × 10^3 N
- hi: हल : क्योंकि संपूर्ण तरल में दाब बिना ह्रास के संचरित होता है। F_1 = (A_1 / A_2) F_2 = pi(5 × 10^(-2) m)^2 / pi(15 × 10^(-2) m)^2 (1350 kg × 9.8 m s^-2) = 1470 N ≈ 1.5 × 10^3 N

### 12. ch 9 §9.4 ¶4 — en p9, hi p9, similarity 0.67
- en: Part of this work goes into changing the kinetic energy of the fluid, and part goes into changing the gravitational potential energy. If the density of the fluid is rho and ∆m = rho A_1 v_1 ∆t = rho ∆V is the mass passing through the pipe in time ∆t, then change in gravitational potential energy is ∆U = rho g ∆V (h_2 − h_1)
- hi: इस कार्य का कुछ भाग तरल की गतिज ऊर्जा परिवर्तित करने में चला जाता है, तथा शेष भाग तरल की गुरुत्वीय स्थितिज ऊर्जा परिवर्तित करने में चला जाता है। यदि पाइप प्रवाहित तरल का घनत्व rho तथा Delta m = rho A_1 v_1 Delta t = rho Delta V की संहति Delta t समय में पाइप से प्रवाहित है तो गुरुत्वीय स्थितिज ऊर्जा में परिवर्तन Delta U = rho g Delta V (h_2 – h_1)

### 13. ch 9 §9.4.2 ¶1 — en p10, hi p11, similarity 0.49
- en: Dynamic lift is the force that acts on a body, such as airplane wing, a hydrofoil or a spinning ball, by virtue of its motion through a fluid. In many games such as cricket, tennis, baseball, or golf, we notice that a spinning ball deviates from its parabolic trajectory as it moves through air. This deviation can be partly explained on the basis of Bernoulli's principle.
- hi: किसी पिण्ड पर गतिक उत्थापक एक बल है। जैसे निम्न के तरल में गति के कारण वायुयान के पंख पर, जलपर्णी या एक घूमती गेंद पर। कई खेल जैसे क्रिकेट, टेनिस, बेसबॉल या गोलक में हम देखते हैं कि वायु में जाती हुई बॉल अपने परवलीय पथ से हट जाती है। इस हटाव को आंशिक रूप से बर्नूली सिद्धांत से समझाया जा सकता है।

### 14. ch 9 §9.4.2 ¶10 — en p11, hi p12, similarity 0.53 — the founder reads this one
- en: The speed above the wing needs to be only 8 % higher than that below.
- hi: पंखों के ऊपर वायु की चाल पंखों के नीचे वायु की चाल की तुलना में केवल 8 % अधिक होनी चाहिए।

### 15. ch 9 §9.5.1 ¶1 — en p13, hi p14, similarity 0.49
- en: When a body falls through a fluid it drags the layer of the fluid in contact with it. A relative motion between the different layers of the fluid is set and, as a result, the body experiences a retarding force. Falling of a raindrop and swinging of a pendulum bob are some common examples of such motion. It is seen that the viscous force is proportional to the velocity of the object and is opposite to the direction of motion. The other quantities on which the force F depends are viscosity eta of the fluid and radius a of the sphere. Sir George G. Stokes (1819–1903), an English scientist enunciated clearly the viscous drag force F as F = 6 pi eta a v (9.17)
- hi: सामान्यतः जब एक पिण्ड किसी तरल से गिरता है तो वह अपने संपर्क में तरल की परतों को भी खींचता है। तरल की विभिन्न परतों में आपेक्ष गति उत्पन्न हो जाती है और परिणामस्वरूप पिण्ड एक परिणामी मंदक बल अनुभव करता है। स्वतंत्रतापूर्वक गिरती हुई पानी की बूंदें तथा दोलन गति करता हुआ लोलक ऐसी गति के कुछ सामान्य उदाहरण हैं। यह देखा गया है कि श्यानता बल पिण्ड की गति के अनुपाती तथा उसकी दिशा के विपरीत कार्य करता है। दूसरी अन्य राशियाँ जिस पर बल F निर्भर है तरल की श्यानता eta तथा गोल की त्रिज्या a है। एक अंग्रेज़ी वैज्ञानिक सर जॉर्ज जी. स्टोक्स (1819-1903) ने श्यान कर्षण बल F निम्न संबंध द्वारा निरूपित किया है: F = 6 pi eta a v (9.17)

### 16. ch 9 §9.6.5 ¶4 — en p18, hi p19, similarity 0.52
- en: Notice that if the liquid meniscus is convex, as for mercury, i.e., if cos theta is negative then from Eq. (9.28) for example, it is clear that the liquid will be lower in the capillary !
- hi: ध्यान दीजिए, यदि द्रव-नवचंद्रक (मेनिस्कस) उत्तल है जैसा कि पारे में होता है अर्थात् cos theta ऋणात्मक है तो समीकरण (9.28) से यह स्पष्ट है कि केशनली में द्रव का तल नीचे गिर जाता है अर्थात् केशिकीय अपनयन होता है।

### 17. ch 10 §10.6 ¶5 — en p7, hi p7, similarity 0.73
- en: where ∆Q is the amount of heat supplied to the substance to change its temperature from T to T + ∆T.
- hi: यहाँ Delta Q पदार्थ के ताप में T से T + Delta T तक परिवर्तन करने के लिए आवश्यक ऊष्मा की मात्रा है।

### 18. ch 10 §10.8 ¶4 — en p9, hi p10, similarity 0.48 — the founder reads this one
- en: Take a slab of ice. Take a metallic wire and fix two blocks, say 5 kg each, at its ends. Put the wire over the slab as shown in Fig. 10.10. You will observe that the wire passes through the ice slab. This happens due to the fact that just below the wire, ice melts at lower temperature due to increase in pressure. When the wire has passed, water above the wire freezes again. Thus, the wire passes through the slab and the slab does not split. This phenomenon of refreezing is called regelation. Skating is possible on snow due to the formation of water under the skates. Water is formed due to the increase of pressure and it acts as a lubricant.
- hi: क्रियाकलाप 10.2 एक हिम शिला लीजिए। एक धातु का तार लेकर उसके दोनों सिरों से समान भार जैसे 5 kg के बाट बाँधिए। चित्र 10.10 में दर्शाए अनुसार इस तार को हिम शिला के ऊपर रखिए। आप यह देखेंगे कि तार हिमशिला में से पार हो जाता है। ऐसा होने का कारण यह है कि तार के ठीक नीचे दाब में वृद्धि के कारण हिम निम्न ताप पर पिघल जाता है। जब तार वहाँ से गुजर जाता है, तो तार के ऊपर का जल पुनः हिमीभूत हो जाता है। इस प्रकार तार हिम शिला से पार हो जाता है तथा शिला विभक्त नहीं होती। पुनर्हिमीभवन की इस परिघटना को पुनर्हिमायन कहते हैं। हिम पर 'स्केट' के नीचे जल बनने के कारण ही 'स्केटिंग' करना संभव हो पाता है। दाब में वृद्धि के कारण जल बनता है जो स्नेहक की भांति कार्य करता है।

### 19. ch 10 §10.8 ¶6 — en p10, hi p10, similarity 0.61 — the founder reads this one
- en: The change of state from liquid to vapour (or gas) is called vaporisation. It is observed that the temperature remains constant until the entire amount of the liquid is converted into vapour. That is, both the liquid and vapour states of the substance coexist in thermal equilibrium, during the change of state from liquid to vapour. The temperature at which the liquid and the vapour states of the substance coexist is called its boiling point. Let us do the following activity to understand the process of boiling of water.
- hi: द्रव से वाष्प (अथवा गैस) में अवस्था परिवर्तन को वाष्पन कहते हैं। यह पाया गया है कि समस्त द्रव के वाष्प में रूपांतरित होने तक ताप नियत रहता है। अर्थात्, द्रव से वाष्प में अवस्था परिवर्तन की अवधि में पदार्थ की दोनों अवस्थाएँ द्रव तथा गैस तापीय साम्य में सहवर्ती होती हैं। वह ताप जिस पर किसी पदार्थ की द्रव तथा वाष्प दोनों अवस्थाएँ तापीय साम्य में परस्पर सहवर्ती होती हैं उसे उस पदार्थ का क्वथनांक कहते हैं। जल के क्वथन की प्रक्रिया को समझने के लिए आइए निम्नलिखित क्रियाकलाप करें।

### 20. ch 10 §10.8 ¶7 — en p10, hi p10, similarity 0.47
- en: Take a round-bottom flask, more than half filled with water. Keep it over a burner and fix a
- hi: क्रियाकलाप 10.3 आधे से अधिक जल से भरा एक गोल पेंदी का फ्लास्क लीजिए। चित्र 10.11 में दर्शाए अनुसार फ्लास्क के मुख पर लगी कार्क के वेधों से होकर भीतर जाते हुए एक तापमापी तथा एक भाप निकास कार्क में लगाइए और फ्लास्क को बर्नर के ऊपर रखिए। जैसे ही जल गर्म होता है, तो पहले यह देखिए कि वह वायु, जो जल में विलीन थी, छोटे-छोटे बुलबुलों के रूप में बाहर आएगी। तत्पश्चात् भाप के बुलबुले फ्लास्क की तली में बनेंगे, परन्तु जैसे ही वे शीर्षभाग के पास के शीतल जल की ओर ऊपर उठते हैं, संघनित होकर अदृश्य हो जाते हैं। अंततः जैसे ही समस्त जल का ताप 100°C पर पहुँचता है, भाप के बुलबुले पृष्ठ पर पहुँचते हैं और क्वथन होने लगता है। फ्लास्क के भीतर भाप दिखाई नहीं देती, परन्तु जैसे ही फ्लास्क से बाहर निकलती है, यह जल की अत्यंत छोटी बूँदों के रूप में संघनित होकर धुंध के रूप में प्रकट होती है।

### 21. ch 10 §10.9.1 ¶8 — en p15, hi p15, similarity 0.62
- en: Example 10.7 An iron bar (L_1 = 0.1 m, A_1 = 0.02 m^2, K_1 = 79 W m^-1 K^-1) and a brass bar (L_2 = 0.1 m, A_2 = 0.02 m^2, K_2 = 109 W m^-1K^-1) are soldered end to end as shown in Fig. 10.16. The free ends of the iron bar and brass bar are maintained at 373 K and 273 K respectively. Obtain expressions for and hence compute (i) the temperature of the junction of the two bars, (ii) the equivalent thermal conductivity of the compound bar, and (iii) the heat current through the compound bar.
- hi: उदाहरण 10.7 चित्र 10.16 में दर्शाए अनुसार लोहे की किसी छड़ (L_1 = 0.1m, A_1 = 0.02 m^2, K_1 = 79 W m^-1 K^-1) को किसी पीतल की छड़ (L_2 = 0.1m, A_2 = 0.02 m^2, K_2 = 109 W m^-1 K^-1) के साथ सिरे से सिरे को मिलाकर डाला गया है। लोहे की छड़ तथा पीतल की छड़ के स्वतंत्र सिरों को क्रमशः 373 K तथा 273 K पर स्थापित किया गया है। (i) दोनों छड़ों की संधि पर ताप, (ii) संयुक्त छड़ की तुल्य ऊष्मा चालकता, तथा (iii) संयुक्त छड़ में ऊष्मा प्रवाह की दर के लिए व्यंजक निकालिए तथा परिकलित कीजिए।

### 22. ch 11 §11.2 ¶3 — en p2, hi p2, similarity 0.52 — the founder reads this one
- en: * Thermodynamics may also involve other variables that are not so obvious to our senses e.g. entropy, enthalpy, etc., and they are all macroscopic variables. However, a thermodynamic state is specified by five state variables viz., pressure, volume, temperature, internal energy and entropy. Entropy is a measure of disorderness in the system. Enthalpy is a measure of total heat content of the system.
- hi: * ऊष्मागतिकी में अन्य ऐसे चर भी निहित होते हैं जो हमारी इंद्रियों को इतने सुस्पष्ट नहीं होते (उदाहरणार्थ, एंट्रॉपी, एंथाल्पी (संपूर्ण ऊष्मा), आदि जिनके विषय में आप उच्च कक्षाओं में पढ़ेंगे), किंतु ये सभी स्थूल चर हैं । यद्यपि किसी उष्मागतिकीय अवस्था को पाँच अवस्था चरों, जैसे दाब, आयतन, ताप, आंतरिक ऊर्जा और एंट्रॉपी के रूप में निरूपित किया जाता है । किसी निकाय की एंट्रॉपी उसकी अव्यवस्था का माप होता है ।

### 23. ch 11 §11.3 ¶3 — en p3, hi p3, similarity 0.53 — the founder reads this one
- en: We have arrived at the concept of temperature formally via the Zeroth Law. The next question is : how to assign numerical values to temperatures of different bodies ? In other words, how do we construct a scale of temperature ? Thermometry deals with this basic question to which we turn in the next section.
- hi: शून्य कोटि नियम के माध्यम से हमने विधिवत ताप की अवधारणा विकसित की है। हमारे सामने पुनः एक प्रश्न उत्पन्न होता है : भिन्न-भिन्न पिंडों के ताप के लिए हम अंकिक मानों का निर्धारण कैसे करें? दूसरे शब्दों में, हम ताप मापक्रम कैसे बनाएँ? तापमिति इस मौलिक प्रश्न से संबंध रखती है जिसके विषय में हम अगले अनुभाग में अध्ययन करेंगे।

### 24. ch 11 §11.7 ¶1 — en p7–8, hi p8, similarity 0.53 — the founder reads this one
- en: Every equilibrium state of a thermodynamic system is completely described by specific values of some macroscopic variables, also called state variables. For example, an equilibrium state of a gas is completely specified by the values of pressure, volume, temperature, and mass (and composition if there is a mixture of gases). A thermodynamic system is not always in equilibrium. For example, a gas allowed to expand freely against vacuum is not an equilibrium state [Fig. 11.6(a)]. During the rapid expansion, pressure of the gas may not be uniform throughout. Similarly, a mixture of gases undergoing an explosive chemical reaction (e.g. a mixture of petrol vapour and air when ignited by a spark) is not an equilibrium state; again its temperature and pressure are not uniform [Fig. 11.6(b)]. Eventually, the gas attains a uniform temperature and pressure and comes to thermal and mechanical equilibrium with its surroundings.
- hi: किसी भी ऊष्मागतिकीय निकाय की प्रत्येक साम्य अवस्था को कुछ स्थूल चरों के विशिष्ट मानों के उपयोग द्वारा पूरी तरह से वर्णित कर सकते हैं । उदाहरणार्थ, किसी गैस की साम्य अवस्था उसके दाब, आयतन, ताप व द्रव्यमान (तथा संगठन यदि गैसों का सम्मिश्रण है) के मानों द्वारा पूरी तरह से निर्धारित होती है । कोई ऊष्मागतिक निकाय सदैव साम्य स्थिति में नहीं होता । उदाहरणार्थ, किसी गैस को निर्वात के विरुद्ध यदि फैलने दिया जाता है तो यह साम्य अवस्था नहीं होती [चित्र 11.6(a)] । द्रुत्र प्रसरण की अवधि में गैस का दाब संभव है कि सभी स्थानों पर एकसमान न हो । इसी प्रकार, गैसों का वह सम्मिश्रण जिसमें विस्फोटक रासायनिक अभिक्रिया होती है (उदाहरणार्थ, पेट्रोल की वाष्प तथा वायु का मिश्रण जिसे एक चिंगारी से प्रज्ज्वलित किया जाता है) एक साम्य अवस्था नहीं है; इसके अतिरिक्त इसके ताप व दाब एकसमान नहीं हैं [चित्र 11.6(b)]। अंततः, गैस का ताप व दाब एकसमान हो जाता है तथा वह परिवेश के साथ तापीय व यांत्रिक साम्य में आ जाती है ।

### 25. ch 11 §11.8.1 ¶6 — en p9, hi p9, similarity 0.44
- en: We now consider these processes in some detail :
- hi: अब हम इन प्रक्रमों के विषय में विस्तार से अध्ययन करेंगे।

### 26. ch 11 §11.9 ¶1 — en p11, hi p11, similarity 0.59
- en: The First Law of Thermodynamics is the principle of conservation of energy. Common experience shows that there are many conceivable processes that are perfectly allowed by the First Law and yet are never observed. For example, nobody has ever seen a book lying on a table jumping to a height by itself. But such a thing would be possible if the principle of conservation of energy were the only restriction. The table could cool spontaneously, converting some of its internal energy into an equal amount of mechanical energy of the book, which would then hop to a height with potential energy equal to the mechanical energy it acquired. But this never happens. Clearly, some additional basic principle of nature forbids the above, even though it satisfies the energy conservation principle. This principle, which disallows many phenomena consistent with the First Law of Thermodynamics is known as the Second Law of Thermodynamics.
- hi: ऊष्मागतिकी का प्रथम नियम ऊर्जा संरक्षण नियम है । सामान्य अनुभव यह बतलाता है कि ऐसे बहुत से मनोगम्य प्रक्रम हैं जो ऊष्मागतिकी के प्रथम नियम से पूर्णतया अनुमत हैं तथापि कभी भी होते हुए दिखाई नहीं देते । उदाहरणार्थ, ऐसा किसी ने कभी नहीं देखा कि मेज पर पड़ी कोई पुस्तक स्वतः उछलकर किसी ऊँचाई पर पहुँच जाए । किंतु ऐसी बात तभी संभव हो सकती है यदि केवल ऊर्जा संरक्षण नियम का ही नियंत्रण हो । मेज स्वतः ठंडी होकर अपनी आंतरिक ऊर्जा का कुछ अंश पुस्तक की समान मात्रा की यांत्रिक ऊर्जा में रूपांतरित करने में करे और इस यांत्रिक ऊर्जा के कारण पुस्तक उस ऊँचाई तक उछले जिसकी स्थितिज ऊर्जा पुस्तक द्वारा प्राप्त यांत्रिक ऊर्जा के बराबर हो । परंतु ऐसा कदापि नहीं होता। स्पष्ट है कि प्रकृति के किसी आंतरिक मूल नियम के कारण यह निषेध है। यद्यपि यह ऊर्जा संरक्षण नियम का अनुपालन करता है। वह नियम जो ऊष्मागतिकी के प्रथम नियम से संगत अनेक परिघटनाओं को स्वीकृति नहीं देता, ऊष्मागतिकी का द्वितीय नियम कहलाता है ।

### 27. ch 11 §11.9 ¶4 — en p11, hi p11, similarity 0.48 — the founder reads this one
- en: No process is possible whose sole result is the absorption of heat from a reservoir and the complete conversion of the heat into work.
- hi: ऐसा कोई प्रक्रम संभव नहीं है जिसका एकमात्र परिणाम किसी ऊष्मा भंडार से ऊष्मा का अवशोषण करना तथा उस ऊष्मा को पूर्णतया कार्य में रूपांतरित करना हो ।

### 28. ch 12 §12.2 ¶5 — en p3, hi p3, similarity 0.69
- en: Atomic theory is not the end of our quest, but the beginning. We now know that atoms are not indivisible or elementary. They consist of a nucleus and electrons. The nucleus itself is made up of protons and neutrons. The protons and neutrons are again made up of quarks. Even quarks may not be the end of the story. There may be string like elementary entities. Nature always has surprises for us, but the search for truth is often enjoyable and the discoveries beautiful. In this chapter, we shall limit ourselves to understanding the behaviour of gases (and a little bit of solids), as a collection of moving molecules in incessant motion.
- hi: परमाणु सिद्धांत हमारी खोजों का अंत नहीं है बल्कि यह तो इसका एक आरंभ है। अब हम जानते हैं कि परमाणु अविभाज्य या मूल कण नहीं हैं। उनमें एक नाभिक और इलेक्ट्रॉन होते हैं। नाभिक स्वयं प्रोटॉनों और न्यूट्रॉनों से बने होते हैं। यही नहीं प्रोटॉन तथा न्यूट्रॉन क्वार्कों से मिलकर बने होते हैं। हो सकता है कि क्वार्क भी इस कहानी का अंत न हों। यह भी हो सकता है कि स्ट्रिंग (तंतु) जैसी कोई प्राथमिक सत्ता हो। प्रकृति हमारे लिए सदैव ही विलक्षण भरी है, पर, सत्य की खोज आनंददायक होती है और हर आविष्कार में अपना सौंदर्य होता है। इस अध्याय में हम अपना अध्ययन गैसों के (और थोड़ा बहुत ठोसों के) व्यवहार तक ही सीमित रखेंगे। इसके लिए हम उन्हें अनवरत गति करते गतिमान कणों का समूह मानेंगे।

### 29. ch 12 §12.4.2 ¶20 — en p8, hi p9, similarity 0.63
- en: (b) When gas in a cylinder is compressed by pushing in a piston, its temperature rises. Guess at an explanation of this in terms of kinetic theory using (a) above.
- hi: (b) पिस्टन लगे सिलिंडर में पिस्टन को अंदर की ओर धकेल कर जब किसी गैस को संपीडित किया जाता है, तो उस गैस का ताप बढ़ जाता है। ऊपर (a) में प्रयुक्त अणुगति सिद्धांत के आधार पर इस प्रेक्षण की व्याख्या कीजिए।

### 30. ch 12 §12.4.2 ¶22 — en p8, hi p9, similarity 0.56
- en: (d) Sachin Tendulkar used a heavy cricket bat while playing. Did it help him in anyway ?
- hi: (d) खेलते समय सचिन तेंदुलकर एक भारी बल्ले का उपयोग करते हैं। इससे क्या उनको किसी प्रकार की कोई सहायता मिलती है?

### 31. ch 12 §12.7 ¶9 — en p12, hi p13, similarity 0.63 — the founder reads this one
- en: Note that the mean free path is 100 times the interatomic distance ~ 40 Å = 4 × 10^-9 m calculated earlier. It is this large value of mean free path that leads to the typical gaseous behaviour. Gases can not be confined without a container.
- hi: ध्यान दीजिए, माध्य मुक्त पथ, पूर्व परिकलनों द्वारा ज्ञात अंतरापरमाणुक दूरी ~ 40 Å = 4 × 10^-9 m की तुलना में 100 गुनी है। माध्य मुक्त पथ का यह बड़ा मान ही गैसों के प्रारूपिक व्यवहार का मार्गदर्शक है। बिना किसी धारक पात्र के गैसों को सीमित नहीं किया जा सकता है।

### 32. ch 13 §13.5 ¶4 — en p8–9, hi p8–9, similarity 0.57
- en: We note from Eq. (13.11) the important property that acceleration of a particle in SHM is proportional to displacement. For x(t) > 0, a(t) < 0 and for x(t) < 0, a(t) > 0. Thus, whatever the value of x between –A and A, the acceleration a(t) is always directed towards the centre.
- hi: समीकरण (13.11) से हम एक महत्त्वपूर्ण परिणाम पर ध्यान देते हैं कि सरल आवर्त गति में कण का त्वरण इसके विस्थापन के अनुक्रमानुपाती होता है। x(t) > 0 के लिए a(t) < 0 तथा x(t) < 0 के लिए a(t) > 0 होता है। अतः –A तथा A के बीच x का मान कुछ भी हो, त्वरण a(t) हमेशा केन्द्र की ओर निर्दिष्ट रहता है।

### 33. ch 13 §13.6 ¶4 — en p9, hi p10, similarity 0.46
- en: Example 13.6 Two identical springs of spring constant k are attached to a block of mass m and to fixed supports as shown in Fig. 13.14. Show that when the mass is displaced from its equilibrium position on either side, it executes a simple harmonic motion. Find the period of oscillations.
- hi: उदाहरण 13.6 कमानी स्थिरांक k की दो सर्वसम कमानियाँ m संहति के किसी गुटके तथा स्थिर आधारों से चित्र 13.14 में दर्शाए गए अनुसार जुड़ी हुई हैं। यह दर्शाइए कि जब गुटके को अपनी साम्यावस्था की स्थिति से किसी ओर विस्थापित किया जाता है, तब यह सरल आवर्त गति करता है। दोलन का आवर्तकाल ज्ञात कीजिए।

### 34. ch 13 §13.6 ¶7 — en p10, hi p10, similarity 0.42
- en: Hence the force acting on the mass is proportional to the displacement and is directed towards the mean position; therefore, the motion executed by the mass is simple harmonic. The time period of oscillations is, T = 2 pi sqrt(m / 2k)
- hi: अतः, गुटके पर आरोपित बल विस्थापन के अनुक्रमानुपाती तथा माध्य-स्थिति की ओर निर्दिष्ट होता है; इसलिए, गुटके की गति सरल आवर्त गति है। इसमें दोलन का आवर्तकाल, T = 2 pi sqrt(m / 2k) है।

### 35. ch 13 §13.8 ¶6 — en p13, hi p13, similarity 0.83
- en: We can simplify Eq. (13.22) if we assume that the displacement theta is small. We know that sin theta can be expressed as, sin theta = theta – theta^3/3! + theta^5/5! ± ... (13.23) where theta is in radians.
- hi: यदि हम यह मानें कि विस्थापन theta छोटा है, तो समीकरण (13.22) को सरल बना सकते हैं । हम जानते हैं कि sin theta को इस प्रकार व्यक्त किया जा सकता है, sin theta = theta − theta^3/3! + theta^5/5! − ...... (13.23) यहाँ theta रेडियन में है।

### 36. ch 14 §14.1 ¶4 — en p2, hi p2, similarity 0.52
- en: The most familiar type of waves such as waves on a string, water waves, sound waves, seismic waves, etc. is the so-called mechanical waves. These waves require a medium for propagation, they cannot propagate through vacuum. They involve oscillations of constituent particles and depend on the elastic properties of the medium. The electromagnetic waves that you will learn in Class XII are a different type of wave. Electromagnetic waves do not necessarily require a medium - they can travel through vacuum. Light, radiowaves, X-rays, are all electromagnetic waves. In vacuum, all electromagnetic waves have the same speed c, whose value is : c = 299, 792, 458 ms^-1. (14.1)
- hi: किसी डोरी तथा जल में उत्पन्न तरंगों, ध्वनि तरंगों, भूकंपी तरंगों जैसी सुपरिचित तरंगें यांत्रिक तरंगों के रूप में जानी जाती हैं। इन सभी तरंगों के संचरण के लिए माध्यम की आवश्यकता होती है, ये बिना माध्यम के संचरित नहीं हो सकतीं। इनका संचरण माध्यम के कणों के दोलनों के कारण संभव हो पाता है तथा माध्यम के प्रत्यास्थ गुणों पर निर्भर करता है। विद्युत-चुंबकीय तरंगें सर्वथा भिन्न प्रकार की तरंगें होती हैं जिनके विषय में आप कक्षा 12 में अध्ययन करेंगे। विद्युत-चुंबकीय तरंगों के संचरण के लिए माध्यम का होना आवश्यक नहीं है– इनका संचरण निर्वात में भी होता है। प्रकाश, रेडियो तरंगें, X-किरणें सभी विद्युत-चुंबकीय किरणें हैं। निर्वात में सभी विद्युत-चुंबकीय तरंगों की चाल, c, समान होती है जिसका मान है: c = 29,97,92,458 m s^-1 (14.1)

### 37. ch 14 §14.2 ¶1 — en p3, hi p3, similarity 0.51
- en: We have seen that motion of mechanical waves involves oscillations of constituents of the medium. If the constituents of the medium oscillate perpendicular to the direction of wave propagation, we call the wave a transverse wave. If they oscillate along the direction of wave propagation, we call the wave a longitudinal wave.
- hi: हम जानते हैं कि यांत्रिक तरंगों की गति में माध्यम के घटक दोलन करते हैं। यदि माध्यम के घटक तरंग की गति की दिशा के लंबवत् दोलन करते हैं तो ऐसी तरंग को हम अनुप्रस्थ तरंग कहते हैं। यदि माध्यम के घटक तरंग की गति की दिशा के अनुदिश दोलन करते हैं तो तरंग को अनुदैर्घ्य तरंग के रूप में जाना जाता है।

### 38. ch 14 §14.3.3 ¶3 — en p6, hi p6, similarity 0.76
- en: Since sine function repeats after every 2 pi, omega T = 2 pi or omega = 2 pi / T (14.7)
- hi: चूंकि ज्या फलन प्रत्येक 2pi कोण पर पुनरावृत्ति करता है, omega T = 2pi, या omega = 2pi / T (14.7)

### 39. ch 14 §14.4.1 ¶2 — en p8, hi p8, similarity 0.65
- en: The dimension of mu is [ML^-1] and that of T is like force, namely [MLT^-2]. We need to combine these dimensions to get the dimension of speed v [LT^-1]. Simple inspection shows that the quantity T/mu has the relevant dimension [MLT^(-2)] / [ML] = [L^2 T^(-2)] Thus if T and mu are assumed to be the only relevant physical quantities, v = C sqrt(T / mu) (14.13) where C is the undetermined constant of dimensional analysis. In the exact formula, it turms out, C=1. The speed of transverse waves on a stretched string is given by v = sqrt(T / mu) (14.14)
- hi: mu की विमा [ML^-1] है तथा T की बल की, अर्थात [MLT^-2] है। हमें इन विमाओं को इस प्रकार संयोजित करना है कि चाल की विमा [LT^-1] प्राप्त हो। हम आसानी से देख सकते हैं कि अनुपात T/mu में यही विमा है [MLT^-2] / [ML^-1] = [L^2T^-2] अतः, यदि T तथा mu ही प्रासंगिक भौतिक राशियाँ हैं तो v = C sqrt(T / mu) (14.13) जहाँ C विमाहीन स्थिरांक है, जिसे विमीय विश्लेषण द्वारा निर्धारित करना संभव नहीं है । वास्तव में यथार्थ सूत्र में C का मान 1 है। अतः तानित डोरी में अनुप्रस्थ तरंग की चाल v = sqrt(T / mu) (14.14)

### 40. ch 14 §14.4.2 ¶1 — en p8–9, hi p9, similarity 0.53
- en: In a longitudinal wave, the constituents of the medium oscillate forward and backward in the direction of propagation of the wave. We have already seen that the sound waves travel in the form of compressions and rarefactions of small volume elements of air. The elastic property that determines the stress under compressional strain is the bulk modulus of the medium defined by (see Chapter 8) B = − ∆P / (∆V/V) (14.16) Here, the change in pressure ∆P produces a volumetric strain ∆V/V. B has the same dimension as pressure and given in SI units in terms of pascal (Pa). The inertial property relevant for the propagation of wave is the mass density rho, with dimensions [ML^-3]. Simple inspection reveals that quantity B/rho has the relevant dimension: [ML^-2T^-2] / [ML^-3] = [L^2T^-2] (14.17) Thus, if B and rho are considered to be the only relevant physical quantities, v = C sqrt(B / rho) (14.18) where, as before, C is the undetermined constant from dimensional analysis. The exact derivation shows that C=1. Thus, the general formula for longitudinal waves in a medium is: v = sqrt(B / rho) (14.19)
- hi: किसी अनुदैर्घ्य तरंग में माध्यम के अवयव तरंग संचरण की दिशा में अपनी स्थिति के आगे-पीछे दोलन करते हैं । हम पहले भी देख चुके हैं कि ध्वनि तरंगें वायु के लघु आयतन-अवयवों के संपीडनों तथा विरलनों के रूप में गमन करती हैं । संपीडन विकृति में प्रतिबल का निर्धारण करने वाली प्रत्यास्थ गुणधर्म माध्यम का आयतन प्रत्यास्थता गुणांक है जिसे इस प्रकार परिभाषित करते हैं, B = - Delta P / (Delta V/V) (14.16) यहाँ दाब में परिवर्तन Delta P आयतन विकृति Delta V/V उत्पन्न करता है। B की विमा वही है जो दाब की है और SI मात्रक में इसे पास्कल (Pa) में व्यक्त करते हैं। तरंग के संचरण के लिए प्रासंगिक जड़त्वीय गुण द्रव्यमान घनत्व rho है जिसकी विमा [ML^-3] है । हम आसानी से देख सकते हैं कि राशि B/rho में अपेक्षित विमा है : [M L^-1 T^-2] / [M L^-3] = [L^2 T^-2] (14.17) अतः, यदि B तथा rho ही प्रासंगिक भौतिक राशियाँ हैं तो v = C sqrt(B / rho) (14.18) यहाँ C एक स्थिरांक है जिसे विमीय विश्लेषण द्वारा निर्धारित करना संभव नहीं है। यथार्थ उत्पत्ति से C=1 प्राप्त होता है । अतः किसी माध्यम में अनुदैर्घ्य तरंगों की चाल के लिए व्यापक सूत्र है: v = sqrt(B / rho) (14.19)

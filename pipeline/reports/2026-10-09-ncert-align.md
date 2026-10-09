# margai-pipeline ncert align

- run: 2026-10-09 17:21 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 682 English and 623 Hindi row(s) in 7 chapter(s)
- result: ok
request id: pipeline-ncert-align-2196bb0c-7d9c-4f8d-9f4f-b2f8375e3d39
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 623 | 244815 | ₹6.55 |

## sections whose rows do not pair one to one

| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 8 | 8.2 | 13 | 8 | 5 |
| 8 | 8.5.1 | 18 | 10 | 10 |
| 8 | 8.5.2 | 5 | 4 | 1 |
| 8 | 8.5.3 | 8 | 8 | 2 |
| 8 | 8.5.5 | 2 | 0 | 2 |
| 8 | 8.5.6 | 0 | 1 | 1 |
| 9 | 9.2 | 12 | 10 | 2 |
| 9 | 9.2.1 | 4 | 4 | 2 |
| 9 | 9.2.2 | 8 | 6 | 2 |
| 9 | 9.2.3 | 13 | 12 | 3 |
| 9 | 9.2.4 | 11 | 11 | 2 |
| 9 | 9.4 | 11 | 11 | 2 |
| 9 | 9.4.1 | 5 | 4 | 1 |
| 9 | 9.4.2 | 10 | 9 | 1 |
| 9 | 9.5.1 | 7 | 7 | 2 |
| 9 | 9.6.4 | 7 | 6 | 1 |
| 9 | 9.6.5 | 7 | 7 | 2 |
| 10 | 10.5 | 18 | 17 | 1 |
| 10 | 10.6 | 11 | 9 | 4 |
| 10 | 10.7 | 4 | 5 | 1 |
| 10 | 10.8 | 12 | 12 | 2 |
| 10 | 10.8.1 | 7 | 7 | 2 |
| 10 | 10.9.1 | 14 | 13 | 3 |
| 10 | 10.9.4 | 8 | 7 | 1 |
| 10 | 10.10 | 14 | 13 | 3 |
| 11 | 11.5 | 8 | 5 | 3 |
| 11 | 11.6 | 11 | 12 | 3 |
| 11 | 11.7 | 4 | 3 | 1 |
| 11 | 11.8.1 | 6 | 6 | 2 |
| 11 | 11.8.2 | 3 | 2 | 1 |
| 11 | 11.9 | 7 | 5 | 2 |
| 11 | 11.11 | 20 | 19 | 1 |
| 12 | 12.3 | 21 | 21 | 4 |
| 12 | 12.4.1 | 7 | 5 | 2 |
| 12 | 12.4.2 | 24 | 18 | 8 |
| 12 | 12.5 | 11 | 10 | 3 |
| 13 | 13.2.2 | 10 | 9 | 1 |
| 13 | 13.3 | 12 | 10 | 2 |
| 13 | 13.4 | 10 | 10 | 4 |
| 13 | 13.5 | 10 | 10 | 6 |
| 13 | 13.6 | 7 | 6 | 1 |
| 13 | 13.7 | 17 | 9 | 8 |
| 13 | 13.8 | 13 | 1 | 12 |
| 13 | 13.8.1 | 0 | 10 | 10 |
| 14 | 14.1 | 11 | 10 | 1 |
| 14 | 14.2 | 13 | 10 | 5 |
| 14 | 14.3.2 | 4 | 1 | 3 |
| 14 | 14.3.3 | 15 | 11 | 4 |
| 14 | 14.4.1 | 5 | 6 | 1 |
| 14 | 14.4.2 | 12 | 12 | 4 |
| 14 | 14.5 | 6 | 11 | 5 |
| 14 | 14.6 | 4 | 6 | 2 |
| 14 | 14.6.1 | 16 | 20 | 8 |
| 14 | 14.7 | 8 | 6 | 2 |

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 8 §8.2: hi ¶5 = en ¶5+¶6 (split hi)
- ch 8 §8.2: en ¶7 has no hindi
- ch 8 §8.2: en ¶8 has no hindi
- ch 8 §8.2: en ¶9 has no hindi
- ch 8 §8.2: hi ¶6 = en ¶10+¶11 (split hi)
- ch 8 §8.5.1: hi ¶7+¶8 = en ¶7 (join hi)
- ch 8 §8.5.1: hi ¶9 = en ¶8+¶9 (split hi)
- ch 8 §8.5.1: en ¶10 has no hindi
- ch 8 §8.5.1: en ¶11 has no hindi
- ch 8 §8.5.1: en ¶12 has no hindi
- ch 8 §8.5.1: en ¶13 has no hindi
- ch 8 §8.5.1: en ¶14 has no hindi
- ch 8 §8.5.1: en ¶15 has no hindi
- ch 8 §8.5.1: en ¶16 has no hindi
- ch 8 §8.5.1: hi ¶10 = en ¶17+¶18 (split hi)
- ch 8 §8.5.2: hi ¶4 = en ¶4+¶5 (split hi)
- ch 8 §8.5.3: hi ¶4 = en ¶4+¶5 (split hi)
- ch 8 §8.5.3: hi ¶7+¶8 = en ¶8 (join hi)
- ch 8 §8.5.5: en ¶1 has no hindi
- ch 8 §8.5.5: en ¶2 has no hindi
- ch 8 §8.5.6: hi ¶1 has no english
- ch 9 §9.2: hi ¶3 = en ¶3+¶4 (split hi)
- ch 9 §9.2: hi ¶8 = en ¶9+¶10 (split hi)
- ch 9 §9.2.1: hi ¶3 = en ¶3+¶4 (split hi)
- ch 9 §9.2.1: hi ¶4 has no english
- ch 9 §9.2.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 9 §9.2.2: hi ¶6 = en ¶7+¶8 (split hi)
- ch 9 §9.2.3: hi ¶4 = en ¶4+¶5 (split hi)
- ch 9 §9.2.3: hi ¶9+¶10 = en ¶10 (join hi)
- ch 9 §9.2.3: hi ¶11 = en ¶11+¶12 (split hi)
- ch 9 §9.2.4: hi ¶6+¶7 = en ¶6 (join hi)
- ch 9 §9.2.4: hi ¶9 = en ¶8+¶9 (split hi)
- ch 9 §9.4: hi ¶5 = en ¶5+¶6 (split hi)
- ch 9 §9.4: hi ¶6+¶7 = en ¶7 (join hi)
- ch 9 §9.4.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 9 §9.4.2: hi ¶8 = en ¶8+¶9 (split hi)
- ch 9 §9.5.1: hi ¶3+¶4 = en ¶3 (join hi)
- ch 9 §9.5.1: hi ¶5 = en ¶4+¶5 (split hi)
- ch 9 §9.6.4: hi ¶1 = en ¶1+¶2 (split hi)
- ch 9 §9.6.5: hi ¶1+¶2 = en ¶1 (join hi)
- ch 9 §9.6.5: hi ¶7 = en ¶6+¶7 (split hi)
- ch 10 §10.5: hi ¶4 = en ¶4+¶5 (split hi)
- ch 10 §10.6: hi ¶1 = en ¶1+¶2 (split hi)
- ch 10 §10.6: hi ¶2+¶3 = en ¶3 (join hi)
- ch 10 §10.6: hi ¶4 = en ¶4+¶5 (split hi)
- ch 10 §10.6: hi ¶8 = en ¶9+¶10 (split hi)
- ch 10 §10.7: hi ¶4+¶5 = en ¶4 (join hi)
- ch 10 §10.8: hi ¶7 = en ¶7+¶8 (split hi)
- ch 10 §10.8: hi ¶10 has no english
- ch 10 §10.8.1: hi ¶5 = en ¶5+¶6 (split hi)
- ch 10 §10.8.1: hi ¶6+¶7 = en ¶7 (join hi)
- ch 10 §10.9.1: en ¶9 has no hindi
- ch 10 §10.9.1: hi ¶11+¶12 = en ¶12 (join hi)
- ch 10 §10.9.1: hi ¶13 = en ¶13+¶14 (split hi)
- ch 10 §10.9.4: hi ¶6 = en ¶6+¶7 (split hi)
- ch 10 §10.10: hi ¶2 = en ¶2+¶3 (split hi)
- ch 10 §10.10: hi ¶4+¶5 = en ¶5 (join hi)
- ch 10 §10.10: hi ¶13 = en ¶13+¶14 (split hi)
- ch 11 §11.5: hi ¶1 = en ¶1+¶2 (split hi)
- ch 11 §11.5: hi ¶3 = en ¶4+¶5 (split hi)
- ch 11 §11.5: hi ¶4 = en ¶6+¶7 (split hi)
- ch 11 §11.6: hi ¶1+¶2 = en ¶1 (join hi)
- ch 11 §11.6: hi ¶3 = en ¶2+¶3 (split hi)
- ch 11 §11.6: hi ¶10+¶11 = en ¶10 (join hi)
- ch 11 §11.7: hi ¶2 = en ¶2+¶3 (split hi)
- ch 11 §11.8.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 11 §11.8.1: hi ¶3+¶4 = en ¶4 (join hi)
- ch 11 §11.8.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 11 §11.9: hi ¶2 = en ¶2+¶3 (split hi)
- ch 11 §11.9: hi ¶3 = en ¶4+¶5 (split hi)
- ch 11 §11.11: hi ¶9 = en ¶9+¶10 (split hi)
- ch 12 §12.3: hi ¶2 = en ¶2+¶3 (split hi)
- ch 12 §12.3: hi ¶3 = en ¶4+¶5 (split hi)
- ch 12 §12.3: hi ¶7+¶8 = en ¶9 (join hi)
- ch 12 §12.3: hi ¶19+¶20 = en ¶20 (join hi)
- ch 12 §12.4.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 12 §12.4.1: hi ¶4 = en ¶5+¶6 (split hi)
- ch 12 §12.4.2: hi ¶3+¶4 = en ¶3 (join hi)
- ch 12 §12.4.2: hi ¶7 = en ¶6+¶7 (split hi)
- ch 12 §12.4.2: hi ¶10 = en ¶10+¶11 (split hi)
- ch 12 §12.4.2: hi ¶11 = en ¶12+¶13 (split hi)
- ch 12 §12.4.2: hi ¶14 = en ¶16+¶17 (split hi)
- ch 12 §12.4.2: hi ¶16 = en ¶19+¶20 (split hi)
- ch 12 §12.4.2: en ¶21 has no hindi
- ch 12 §12.4.2: en ¶22 has no hindi
- ch 12 §12.5: hi ¶4 = en ¶4+¶5 (split hi)
- ch 12 §12.5: hi ¶7 = en ¶8+¶9 (split hi)
- ch 12 §12.5: hi ¶9 has no english
- ch 13 §13.2.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 13 §13.3: hi ¶9 = en ¶9+¶10 (split hi)
- ch 13 §13.3: hi ¶10 = en ¶11+¶12 (split hi)
- ch 13 §13.4: hi ¶2+¶3 = en ¶2 (join hi)
- ch 13 §13.4: hi ¶8 = en ¶7+¶8 (split hi)
- ch 13 §13.4: hi ¶9 = en ¶9+¶10 (split hi)
- ch 13 §13.4: hi ¶10 has no english
- ch 13 §13.5: hi ¶1+¶2 = en ¶1 (join hi)
- ch 13 §13.5: hi ¶3+¶4 = en ¶2 (join hi)
- ch 13 §13.5: hi ¶7+¶8 = en ¶5 (join hi)
- ch 13 §13.5: hi ¶9 = en ¶6+¶7 (split hi)
- ch 13 §13.5: hi ¶10 = en ¶8+¶9 (split hi)
- ch 13 §13.5: en ¶10 has no hindi
- ch 13 §13.6: hi ¶5 = en ¶5+¶6 (split hi)
- ch 13 §13.7: hi ¶2 = en ¶2+¶3 (split hi)
- ch 13 §13.7: en ¶4 has no hindi
- ch 13 §13.7: en ¶5 has no hindi
- ch 13 §13.7: hi ¶3 = en ¶6+¶7 (split hi)
- ch 13 §13.7: hi ¶6 = en ¶10+¶11 (split hi)
- ch 13 §13.7: en ¶12 has no hindi
- ch 13 §13.7: hi ¶7 = en ¶13+¶14 (split hi)
- ch 13 §13.7: hi ¶8 = en ¶15+¶16 (split hi)
- ch 13 §13.8: en ¶2 has no hindi
- ch 13 §13.8: en ¶3 has no hindi
- ch 13 §13.8: en ¶4 has no hindi
- ch 13 §13.8: en ¶5 has no hindi
- ch 13 §13.8: en ¶6 has no hindi
- ch 13 §13.8: en ¶7 has no hindi
- ch 13 §13.8: en ¶8 has no hindi
- ch 13 §13.8: en ¶9 has no hindi
- ch 13 §13.8: en ¶10 has no hindi
- ch 13 §13.8: en ¶11 has no hindi
- ch 13 §13.8: en ¶12 has no hindi
- ch 13 §13.8: en ¶13 has no hindi
- ch 13 §13.8.1: hi ¶1 has no english
- ch 13 §13.8.1: hi ¶2 has no english
- ch 13 §13.8.1: hi ¶3 has no english
- ch 13 §13.8.1: hi ¶4 has no english
- ch 13 §13.8.1: hi ¶5 has no english
- ch 13 §13.8.1: hi ¶6 has no english
- ch 13 §13.8.1: hi ¶7 has no english
- ch 13 §13.8.1: hi ¶8 has no english
- ch 13 §13.8.1: hi ¶9 has no english
- ch 13 §13.8.1: hi ¶10 has no english
- ch 14 §14.1: hi ¶4 = en ¶4+¶5 (split hi)
- ch 14 §14.2: hi ¶6+¶7 = en ¶6 (join hi)
- ch 14 §14.2: hi ¶8 = en ¶7+¶8 (split hi)
- ch 14 §14.2: en ¶9 has no hindi
- ch 14 §14.2: hi ¶9 = en ¶10+¶11 (split hi)
- ch 14 §14.2: hi ¶10 = en ¶12+¶13 (split hi)
- ch 14 §14.3.2: en ¶1 has no hindi
- ch 14 §14.3.2: hi ¶1 = en ¶2+¶3 (split hi)
- ch 14 §14.3.2: en ¶4 has no hindi
- ch 14 §14.3.3: hi ¶3 = en ¶3+¶4 (split hi)
- ch 14 §14.3.3: hi ¶5 = en ¶6+¶7 (split hi)
- ch 14 §14.3.3: hi ¶6 = en ¶8+¶9 (split hi)
- ch 14 §14.3.3: hi ¶11 = en ¶14+¶15 (split hi)
- ch 14 §14.4.1: hi ¶2+¶3 = en ¶2 (join hi)
- ch 14 §14.4.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 14 §14.4.2: hi ¶3+¶4 = en ¶2 (join hi)
- ch 14 §14.4.2: hi ¶11 = en ¶9+¶10 (split hi)
- ch 14 §14.4.2: hi ¶12 = en ¶11+¶12 (split hi)
- ch 14 §14.5: hi ¶5+¶6 = en ¶5 (join hi)
- ch 14 §14.5: hi ¶7+¶8 = en ¶6 (join hi)
- ch 14 §14.5: hi ¶9 has no english
- ch 14 §14.5: hi ¶10 has no english
- ch 14 §14.5: hi ¶11 has no english
- ch 14 §14.6: hi ¶4+¶5 = en ¶4 (join hi)
- ch 14 §14.6: hi ¶6 has no english
- ch 14 §14.6.1: hi ¶2+¶3 = en ¶2 (join hi)
- ch 14 §14.6.1: hi ¶6+¶7 = en ¶5 (join hi)
- ch 14 §14.6.1: hi ¶8+¶9 = en ¶6 (join hi)
- ch 14 §14.6.1: hi ¶10 has no english
- ch 14 §14.6.1: hi ¶13+¶14 = en ¶9 (join hi)
- ch 14 §14.6.1: hi ¶15 = en ¶10+¶11 (split hi)
- ch 14 §14.6.1: hi ¶18+¶19 = en ¶14 (join hi)
- ch 14 §14.6.1: hi ¶20 = en ¶15+¶16 (split hi)
- ch 14 §14.7: hi ¶2 = en ¶2+¶3 (split hi)
- ch 14 §14.7: hi ¶3 = en ¶4+¶5 (split hi)

## pairs weaker than 0.45 — read both against the pages

- ch 8 §8.2: hi ¶8 = en ¶13 — similarity 0.33 — en «Since the strain is a ratio of change…» · hi «चूंकि विकृत प्रारंभिक विमा तथा विमा में अंतर…»
- ch 8 §8.4: hi ¶4 = en ¶4 — similarity 0.44 — en «As stated earlier, the stress-strain behaviour varies from…» · hi «जैसा पहले कहा जा चुका है, प्रतिबल-विकृति व्यवहार…»
- ch 8 §8.5.3: hi ¶1 = en ¶1 — similarity 0.42 — en «In Section (8.3), we have seen that when…» · hi «पहले के खंड (8.2) में हम यह देख…»
- ch 9 §9.6.2: hi ¶5 = en ¶5 — similarity 0.45 — en «We make the following observations from above:» · hi «उपरोक्त चर्चा के आधार पर निम्नलिखित प्रेक्षण प्राप्त…»
- ch 9 §9.6.2: hi ¶9 = en ¶9 — similarity 0.44 — en «A fluid will stick to a solid surface…» · hi «यदि ठोस-वायु तथा तरल-वायु पृष्ठीय ऊर्जाओं के योग…»
- ch 11 §11.6: hi ¶6 = en ¶6 — similarity 0.39 — en «As Table 11.1 shows, the experimentally measured values…» · hi «जैसा कि सारणी से स्पष्ट है कि भविष्यवाणियाँ…»
- ch 11 §11.8.1: hi ¶6 = en ¶6 — similarity 0.44 — en «We now consider these processes in some detail…» · hi «अब हम इन प्रक्रमों के विषय में विस्तार…»
- ch 12 §12.7: hi ¶10 = en ¶10 — similarity 0.39 — en «Using, the kinetic theory of gases, the bulk…» · hi «अणुगति सिद्धांत का उपयोग करके, श्यानता, ऊष्मा-चालकता, एवं…»
- ch 13 §13.2: hi ¶2 = en ¶2 — similarity 0.42 — en «Very often, the body undergoing periodic motion has…» · hi «सामान्यतः आवर्ती गति करने वाले पिण्ड की एक…»
- ch 13 §13.2: hi ¶4 = en ¶4 — similarity 0.40 — en «Simple harmonic motion is the simplest form of…» · hi «सरल आवर्ती गति दोलनीय गति का एक सरल…»
- ch 13 §13.2.1: hi ¶4 = en ¶4 — similarity 0.45 — en «Note, that the frequency, nu, is not necessarily…» · hi «ध्यान दीजिए, आवृत्ति का सदैव ही पूर्णांक होना…»
- ch 13 §13.3: hi ¶2 = en ¶2 — similarity 0.41 — en «Thus, simple harmonic motion (SHM) is not any…» · hi «चित्र 13.5 में x के साथ t का…»
- ch 13 §13.6: hi ¶6 = en ¶7 — similarity 0.42 — en «Hence the force acting on the mass is…» · hi «अतः, गुटके पर आरोपित बल विस्थापन के अनुक्रमानुपाती…»
- ch 13 §13.7: hi ¶4 = en ¶8 — similarity 0.40 — en «The total mechanical energy of a harmonic oscillator…» · hi «जैसा कि संरक्षी बलों के अधीन गतियों के…»
- ch 13 §13.8: hi ¶1 = en ¶1 — similarity 0.26 — en «It is said that Galileo measured the periods…» · hi «निरपेक्षत: शुद्ध सरल आवर्त गति के कोई भौतिक…»
- ch 14 §14.4.1: hi ¶1 = en ¶1 — similarity 0.43 — en «The speed of a mechanical wave is determined…» · hi «किसी यांत्रिक तरंग की चाल विक्षोभ के कारण…»
- ch 14 §14.5: hi ¶4 = en ¶4 — similarity 0.40 — en «The principle of superposition is basic to the…» · hi «अध्यारोपण का सिद्धांत व्यतिकरण की परिघटना का मूल…»
- ch 14 §14.6.1: hi ¶5 = en ¶4 — similarity 0.37 — en «The most significant feature of stationary waves is…» · hi «अप्रगामी तरंगों का सबसे महत्वपूर्ण लक्षण यह है…»
- ch 14 §14.6.1: hi ¶11 = en ¶7 — similarity 0.45 — en «Fig. 14.13 shows the first six harmonics of…» · hi «यह आवश्यक नहीं है कि कोई तानित डोरी…»
- ch 14 §14.6.1: hi ¶16 = en ¶12 — similarity 0.37 — en «Normal modes of a circular membrane rigidly clamped…» · hi «किसी पात्र की परिधि से दृढ़तापूर्वक परिबद्ध वृत्ताकार…»

## numbers one edition of a pair carries and the other does not

- ch 8 §8.1 ¶1: only in en 6 · only in hi 7
- ch 8 §8.5.1 ¶4: only in en 1.0 · only in hi 1
- ch 8 §8.5.1 ¶6: only in en 0.70 · only in hi 0.7
- ch 8 §8.5.2 ¶1: only in en 9.4, 8.12 · only in hi 8.9, 8.4
- ch 8 §8.5.2 ¶2: only in en 9.2, 9.1 · only in hi 8.2, 8.1
- ch 8 §8.5.2 ¶3: only in en 9.0 · only in hi 8.0
- ch 8 §8.5.3 ¶1: only in en 8.3 · only in hi 8.2, 8.1
- ch 8 §8.6 ¶2: only in en 9.8 · only in hi 10
- ch 8 §8.6 ¶4: only in en 8.16 · only in hi 8.15
- ch 8 §8.6 ¶5: only in en 8.9, 8.9 · only in hi 8.8, 8.8
- ch 9 §9.2.2 ¶1: only in en — · only in hi 1, 2, 9.6
- ch 9 §9.2.3 ¶1: only in en 0 · only in hi —
- ch 9 §9.2.3 ¶2: only in en — · only in hi 1
- ch 9 §9.2.3 ¶7: only in en 9.7 · only in hi 9.8
- ch 9 §9.2.4 ¶4: only in en 1.0, 3.0, 6.0 · only in hi 1, 3, 6
- ch 9 §9.2.4 ¶7: only in en 5.0, 9.7 · only in hi 9.6, 5
- ch 9 §9.4 ¶2: only in en 1738 · only in hi —
- ch 9 §9.4 ¶4: only in en — · only in hi 1, 1, 2, 2, 2, 2
- ch 9 §9.4 ¶8: only in en 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 9.12 · only in hi —
- ch 9 §9.4.1 ¶1: only in en 9.10 · only in hi 9.9
- ch 9 §9.4.1 ¶5: only in en 9.15 · only in hi 9.14
- ch 9 §9.5 ¶5: only in en 9.13 · only in hi 9.15
- ch 9 §9.5 ¶6: only in en 1 · only in hi —
- ch 9 §9.5.1 ¶7: only in en 9.18 · only in hi 9.20
- ch 9 §9.6.5 ¶5: only in en 1 · only in hi —
- ch 10 §10.5 ¶3: only in en 1, 1, 1, 1 · only in hi —
- ch 10 §10.8 ¶4: only in en — · only in hi 10.2
- ch 10 §10.9.1 ¶5: only in en 10.5 · only in hi 10.6
- ch 10 §10.9.1 ¶11: only in en — · only in hi 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 0, 0
- ch 10 §10.9.4 ¶5: only in en — · only in hi 2
- ch 10 §10.9.4 ¶8: only in en 28 · only in hi 20
- ch 10 §10.10 ¶9: only in en 10.20 · only in hi 10.19
- ch 11 §11.1 ¶4: only in en — · only in hi 19
- ch 11 §11.6 ¶6: only in en 11.1, 3 · only in hi —
- ch 11 §11.6 ¶7: only in en — · only in hi 1
- ch 11 §11.6 ¶8: only in en — · only in hi 1, 1
- ch 11 §11.6 ¶9: only in en 11.10 · only in hi —
- ch 11 §11.11 ¶3: only in en 11.9 · only in hi 11.11
- ch 11 §11.11 ¶8: only in en 2 · only in hi —
- ch 12 §12.1 ¶1: only in en — · only in hi 19
- ch 12 §12.2 ¶1: only in en 20 · only in hi —
- ch 12 §12.2 ¶4: only in en — · only in hi 1
- ch 12 §12.3 ¶14: only in en 1 · only in hi —
- ch 12 §12.3 ¶18: only in en 2 · only in hi —
- ch 12 §12.3 ¶19: only in en 1, 1, 2, 2, 2, 2, 3, 3 · only in hi —
- ch 12 §12.4.1 ¶7: only in en 9 · only in hi 10
- ch 12 §12.4.2 ¶1: only in en 13.14 · only in hi 12.14
- ch 12 §12.4.2 ¶5: only in en — · only in hi 1, 1, 2, 2
- ch 12 §12.6.1 ¶1: only in en — · only in hi 1
- ch 12 §12.6.3 ¶1: only in en — · only in hi 1
- ch 12 §12.6.3 ¶4: only in en 1 · only in hi —
- ch 12 §12.6.3 ¶5: only in en 2, 3 · only in hi —
- ch 12 §12.6.4 ¶1: only in en — · only in hi 1
- ch 13 §13.2.1 ¶6: only in en 1.25 · only in hi —
- ch 13 §13.3 ¶1: only in en — · only in hi 13.4, 4
- ch 13 §13.3 ¶2: only in en 13.4, 4 · only in hi —
- ch 13 §13.3 ¶8: only in en 1, 2 · only in hi —
- ch 13 §13.7 ¶17: only in en 5, 0.25, 0.25, 50, 1, 0.1, 0.1 · only in hi —
- ch 13 §13.8 ¶1: only in en 100 · only in hi —
- ch 14 §14.3.3 ¶5: only in en — · only in hi 1, 1, 2
- ch 14 §14.3.3 ¶12: only in en 3.0 · only in hi 30
- ch 14 §14.4 ¶2: only in en 14.12 · only in hi 14.2
- ch 14 §14.4.1 ¶1: only in en — · only in hi 1, 1, 1, 2, 2, 2, 2
- ch 14 §14.4.2 ¶7: only in en — · only in hi 1, 1, 0
- ch 14 §14.6 ¶2: only in en 0 · only in hi —
- ch 14 §14.6.1 ¶3: only in en — · only in hi 14.37
- ch 14 §14.6.1 ¶7: only in en 14.13 · only in hi —
- ch 14 §14.6.1 ¶8: only in en — · only in hi 0, 1, 1, 2, 2, 2, 3, 14.43

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 8 §8.5.1 ¶3 — en p4–5, hi p4–5, similarity 0.59
- en: From the data given in Table 8.1, it is noticed that for metals Young's moduli are large. Therefore, these materials require a large force to produce small change in length. To increase the length of a thin steel wire of 0.1 cm^2 cross-sectional area by 0.1%, a force of 2000 N is required. The force required to produce the same strain in aluminium, brass and copper wires having the same cross-sectional area are 690 N, 900 N and 1100 N respectively. It means that steel is more elastic than copper, brass and aluminium. It is for this reason that steel is preferred in heavy-duty machines and in structural designs. Wood, bone, concrete and glass have rather small Young's moduli.
- hi: सारणी 8.1 में दिए गए आँकड़ों से यह पता चलता है कि धातुओं के लिए यंग गुणांक अधिक होता है। इसलिए इन पदार्थों में लंबाई में थोड़ा ही अंतर उत्पन्न करने के लिए बहुत अधिक बल की आवश्यकता होती है। 0.1 cm^2 अनुप्रस्थ परिच्छेद के एक पतले इस्पात के तार की लंबाई को 0.1% से बढ़ाने के लिए 2000 N के बल की आवश्यकता होती है। उसी अनुप्रस्थ परिच्छेद के ऐलुमिनियम, पीतल तथा ताँबे के तारों में उतनी ही विकृति उत्पन्न करने के लिए आवश्यक बल क्रमशः 690 N, 900 N तथा 1100 N होते हैं। इसका अर्थ यह है कि ताँबा, पीतल तथा ऐलुमिनियम से इस्पात अधिक प्रत्यास्थ होता है। इसी कारण से अधिक काम ली जाने वाली मशीनों और संरचनात्मक डिज़ाइनों में इस्पात को अधिक वरीयता दी जाती है। लकड़ी, अस्थि, कंक्रीट तथा काँच के यंग गुणांक कम होते हैं।

### 2. ch 8 §8.5.1 ¶4 — en p5, hi p5, similarity 0.62
- en: Example 8.1 A structural steel rod has a radius of 10 mm and a length of 1.0 m. A 100 kN force stretches it along its length. Calculate (a) stress, (b) elongation, and (c) strain on the rod. Young's modulus, of structural steel is 2.0 × 10^11 N m^-2.
- hi: उदाहरण 8.1 एक संरचनात्मक इस्पात की छड़ की त्रिज्या 10 mm तथा लंबाई 1 m है। 100 kN का एक बल F इसको लंबाई की दिशा में तनित करता है। छड़ में (a) प्रतिबल, (b) विस्तार, तथा (c) विकृति की गणना कीजिए। संरचनात्मक इस्पात का यंग गुणांक 2.0×10^11 N m^-2 है।

### 3. ch 8 §8.5.2 ¶3 — en p6, hi p7, similarity 0.59
- en: Example 8.4 A square lead slab of side 50 cm and thickness 10 cm is subject to a shearing force (on its narrow face) of 9.0 × 10^4 N. The lower edge is riveted to the floor. How much will the upper edge be displaced?
- hi: उदाहरण 8.4 सीसे के 50 cm भुजा के एक वर्गाकार स्लैब, जिसकी मोटाई 10 cm है, की पतली फलक पर 8.0 × 10^4 N का एक अपरूपक बल लगा है। दूसरा पतला फलक फर्श से रिवेट किया हुआ है। ऊपरी फलक कितनी विस्थापित हो जाएगी?

### 4. ch 8 §8.5.3 ¶1 — en p7, hi p7, similarity 0.42
- en: In Section (8.3), we have seen that when a body is submerged in a fluid, it undergoes a hydraulic stress (equal in magnitude to the hydraulic pressure). This leads to the decrease in the volume of the body thus producing a strain called volume strain [Eq. (8.5)]. The ratio of hydraulic stress to the corresponding hydraulic strain is called bulk modulus. It is denoted by symbol B. B = – p/(∆V/V) (8.12)
- hi: पहले के खंड (8.2) में हम यह देख चुके हैं कि जब किसी पिण्ड को अधिक दाब के एक द्रव में डुबोया जाता है [चित्र 8.1(d)] तो वह जलीय प्रतिबल (जलीय दाब के मान के बराबर) के अंतर्गत चला जाता है। यह पिण्ड के आयतन में कमी उत्पन्न करता है, इस प्रकार एक विकृति, जिसे आयतन विकृति कहते हैं, उत्पन्न होती है (समीकरण 8.5)। जलीय प्रतिबल तथा संगत आयतन विकृति के अनुपात को आयतन गुणांक कहते हैं। यह प्रतीक B से निरूपित किया जाता है : B = – p/(Delta V/V) (8.12)

### 5. ch 8 §8.6 ¶2 — en p9, hi p9, similarity 0.59
- en: Cranes used for lifting and moving heavy loads from one place to another have a thick metal rope to which the load is attached. The rope is pulled up using pulleys and motors. Suppose we want to make a crane, which has a lifting capacity of 10 tonnes or metric tons (1 metric ton = 1000 kg). How thick should the steel rope be? We obviously want that the load does not deform the rope permanently. Therefore, the extension should not exceed the elastic limit. From Table 8.1, we find that mild steel has a yield strength (sigma_y) of about 300 × 10^6 N m^-2. Thus, the area of cross-section (A) of the rope should at least be A ≥ W/sigma_y = Mg/sigma_y (8.15) = (10^4 kg × 9.8 m s^-2)/(300 × 10^6 N m^-2) = 3.3 × 10^-4 m^2 corresponding to a radius of about 1 cm for a rope of circular cross-section. Generally a large margin of safety (of about a factor of ten in the load) is provided. Thus a thicker rope of radius about 3 cm is recommended. A single wire of this radius would practically be a rigid rod. So the ropes are always made of a number of thin wires braided together, like in pigtails, for ease in manufacture, flexibility and strength.
- hi: भारी भारों को उठाने तथा एक स्थान से दूसरे स्थान तक ले जाने के लिए प्रयुक्त क्रेनों में धातु का एक मोटा रस्सा होता है जिससे भार को जोड़ दिया जाता है। रस्से को मोटरों तथा घिरनियों की मदद से खींचा जाता है। मान लें कि हमें एक ऐसा क्रेन बनाना है जिसको उठा सकने की सामर्थ्य 10 मीट्रिक टन (1metric ton = 1000 kg) हो। इस्पात का रस्सा कितना मोटा होना चाहिए? स्पष्टतया, हम यह चाहते हैं कि भार रस्से को स्थायी रूप से विरूपित न कर दे। इसलिए विस्तार प्रत्यास्थ सीमा से अधिक नहीं होना चाहिए। सारणी 8.1 से हमें पता चलता है कि मृदु इस्पात का पराभव सामर्थ्य (sigma_y) लगभग 300 × 10^6 N m^-2 है। इस प्रकार रस्से के अनुप्रस्थ परिच्छेद का क्षेत्रफल का न्यूनतम मान है : A ≥ W/sigma_y = Mg/sigma_y (8.15) = (10^4 kg × 10 m s^-2)/(300 × 10^6 N m^-2) = 3.3 × 10^-4 m^2 जो वृत्ताकार परिच्छेद के रस्से के लिए लगभग 1 cm त्रिज्या के संगत बनता है। साधारणतया, सुरक्षा के लिए भार में एक बड़ा मार्जिन (लगभग दस के गुणक का) दिया जाता है। इस तरह लगभग 3 cm त्रिज्या का एक मोटा रस्सा संस्तुत किया जाता है। इस त्रिज्या का एकल तार व्यावहारिक रूप से एक दृढ़ छड़ हो जाएगा। इसलिए व्यापारिक निर्माण में लचक तथा प्रबलता के लिए ऐसे रस्सों को हमेशा वेणी की तरह बहुत से पतले तारों को गुम्फित करके आसानी से बनाया जाता है।

### 6. ch 8 §8.6 ¶7 — en p10, hi p11, similarity 0.70
- en: At the bottom of a mountain of height h, the force per unit area due to the weight of the mountain is h rho g where rho is the density of the material of the mountain and g is the acceleration due to gravity. The material at the bottom experiences this force in the vertical direction, and the sides of the mountain are free. Therefore, this is not a case of pressure or bulk compression. There is a shear component, approximately h rho g itself. Now the elastic limit for a typical rock is 30 × 10^7 N m^-2. Equating this to h rho g, with rho = 3 × 10^3 kg m^-3 gives h rho g = 30 × 10^7 N m^-2 . h = 30 × 10^7 N m^-2/(3 × 10^3 kg m^-3 × 10 m s^-2) = 10 km which is more than the height of Mt. Everest!
- hi: ऊँचाई h के किसी पर्वत की तली पर, पर्वत के भार के कारण एकांक क्षेत्रफल पर लगने वाला बल h rho g होता है जहाँ rho पर्वत के द्रव्यमान का घनत्व है तथा g गुरुत्वीय त्वरण है। तली पर का द्रव्य ऊर्ध्वाधर दिशा में इस बल का अनुभव करता है, लेकिन पर्वत के किनारे स्वतंत्र हैं। इसलिए यह दाब या आयतन संपीडन जैसी स्थिति नहीं है। यहाँ एक अपरूपक अवयव है जो लगभग h rho g ही है। अब, किसी प्रारूपिक चट्टान की प्रत्यास्थ सीमा 30 × 10^7 N m^-2 होती है। इसे h rho g के बराबर रखने पर जहाँ rho = 3 × 10^3 kg m^-3, हम पाते हैं कि h rho g = 30 × 10^7 N m^-2 या, h = 30×10^7 N m^-2/(3×10^3 kg m^-3 ×10 m s^-2) ≅ 10 km जो माउन्ट एवरेस्ट की ऊँचाई से ज़्यादा है।

### 7. ch 9 §9.1 ¶4 — en p1, hi p1, similarity 0.49
- en: Shear stress can change the shape of a solid keeping its volume fixed. The key property of fluids is that they offer very little resistance to shear stress; their shape changes by application of very small shear stress. The shearing stress of fluids is about million times smaller than that of solids.
- hi: अपरूपण (विरूपण) प्रतिबल ठोस के आयतन में परिवर्तन किए बिना उसकी आकृति बदल सकता है। तरलों का मूल गुण यह है कि वह विरूपण प्रतिबल का बहुत ही न्यून प्रतिरोध करते हैं। फलतः थोड़े से विरूपण प्रतिबल लगाने से भी उनकी आकृति बदल जाती है। ठोसों की अपेक्षा तरलों का अपरूपक प्रतिबल लगभग दस लाखवाँ कम होता है।

### 8. ch 9 §9.3 ¶5 — en p8, hi p8–9, similarity 0.66 — the founder reads this one
- en: Figure 9.8 displays streamlines for some typical flows. For example, Fig. 9.8(a) describes a laminar flow where the velocities at different points in the fluid may have different magnitudes but their directions are parallel. Figure 9.8 (b) gives a sketch of turbulent flow.
- hi: चित्र 9.8 कुछ प्ररूपी प्रवाह की धारारेखाएँ दर्शायी गई हैं। उदाहरण के लिए चित्र 9.8(a) में स्तरीय प्रवाह दर्शाया गया है जहाँ तरल के विभिन्न बिंदुओं पर वेगों के परिमाण भिन्न-भिन्न हो सकते हैं, परन्तु उनकी दिशाएँ एक दूसरे से समानांतर हैं। चित्र 9.8 (b) में प्रक्षुब्ध प्रवाह आलेखित किया गया है।

### 9. ch 9 §9.4 ¶2 — en p8, hi p9, similarity 0.55
- en: Consider a fluid moving in a pipe of varying cross-sectional area. Let the pipe be at varying heights as shown in Fig. 9.9. We now suppose that an incompressible fluid is flowing through the pipe in a steady flow. Its velocity must change as a consequence of equation of continuity. A force is required to produce this acceleration, which is caused by the fluid surrounding it, the pressure must be different in different regions. Bernoulli’s equation is a general expression that relates the pressure difference between two points in a pipe to both velocity changes (kinetic energy change) and elevation (height) changes (potential energy change). The Swiss Physicist Daniel Bernoulli developed this relationship in 1738.
- hi: परिवर्ती अनुप्रस्थ काट के पाइप में तरल प्रवाह पर विचार कीजिए। माना कि पाइप परिवर्ती ऊँचाइयों पर है जैसा कि चित्र 9.9 में दर्शाया गया है। अब माना कि पाइप में एक असंपीड्य तरल अपरिवर्ती प्रवाह से प्रवाहित है। सांतत्य समीकरण के अनुसार इसके वेग में परिवर्तन होना चाहिए। त्वरण उत्पन्न करने के लिए एक बल की आवश्यकता है जो इसे घेरे हुए तरल से उत्पन्न होता है। भिन्न-भिन्न भागों में दाब भिन्न होना चाहिए। पाइप के दो बिंदुओं के बीच दाबांतर का संबंध वेग परिवर्तन (गति ऊर्जा परिवर्तन) तथा उन्नयन (ऊँचाई) में परिवर्तन (स्थिति ऊर्जा में परिवर्तन) दोनों में प्रदर्शित करने वाला सामान्य व्यंजक, बर्नूली का समीकरण है। इस संबंध को स्विस भौतिकविद् डेनियल बर्नूली ने विकसित किया था।

### 10. ch 9 §9.4.1 ¶1 — en p9, hi p10, similarity 0.64
- en: The word efflux means fluid outflow. Torricelli discovered that the speed of efflux from an open tank is given by a formula identical to that of a freely falling body. Consider a tank containing a liquid of density rho with a small hole in its side at a height y_1 from the bottom (see Fig. 9.10). The air above the liquid, whose surface is at height y_2, is at pressure P. From the equation of continuity [Eq. (9.10)] we have v_1 A_1 = v_2 A_2 v_2 = (A_1 / A_2) v_1
- hi: बहिर्वाह शब्द का अर्थ है तरल का बहिर्गमन। टोरिसेली ने यह पता लगाया कि किसी खुली टंकी से तरल के बहिर्वाह की चाल को मुक्त रूप से गिरते पिण्ड की चाल के सूत्र के समरूप सूत्र द्वारा प्रदर्शित किया जा सकता है। rho घनत्व के द्रव से भरी किसी ऐसी टंकी पर विचार कीजिए जिसमें टंकी की तली से y_1 ऊँचाई पर एक छोटा छिद्र है (देखिए चित्र 9.10)। द्रव के ऊपर, जिसका पृष्ठ y_2 ऊँचाई पर है, वायु है जिसका दाब P है। सांतत्य समीकरण (समीकरण 9.9) से v_1 A_1 = v_2 A_2 v_2 = (A_1 / A_2) v_1

### 11. ch 9 §9.4.2 ¶5 — en p10, hi p11, similarity 0.52
- en: Aerofoil or lift on aircraft wing: Figure 9.11 (c) shows an aerofoil, which is a solid piece shaped to provide an upward dynamic lift when it moves horizontally through air. The cross-section of the wings of an aeroplane looks somewhat like the aerofoil shown in Fig. 9.11 (c) with streamlines around it. When the aerofoil moves against the wind, the orientation of the wing relative to flow direction causes the streamlines to crowd together above the wing more than those below it. The flow speed on top is higher than that below it. There is an upward force resulting in a dynamic lift of the wings and this balances the weight of the plane. The following example illustrates this.
- hi: वायुयान के पंख या ऐयरोफॉयल पर उत्थापक : जब ऐयरोफॉयल वायु में क्षैतिज दिशा में चलता है तो चित्र 9.11 (c) में दिखाए अनुसार विशिष्ट आकार के ठोस ऐयरोफॉयल पर गतिक उत्थापक ऊपर की ओर लगता है। चित्र 9.11 (c) के अनुसार वायुयान के पंख की अनुप्रस्थ काट ऐयरोफॉयल जैसी प्रतीत होती है जिसके परितः धारारेखाएँ प्रदर्शित हैं। जब ऐयरोफॉइल हवा के विपरीत चलता है तब पंखों का तरल प्रवाह के सापेक्ष दिक्विन्यास धारारेखाओं को पंख के ऊपर-नीचे की अपेक्षा समीप कर देता है। प्रवाह की गति शीर्ष पर अधिक और नीचे कम होती है। इसके कारण ऊर्ध्वमुखी बल से पंख पर गतिक उत्थापक उत्पन्न होता है और यह वायुयान के भार को संतुलित करता है। निम्न उदाहरण इसे दर्शाता है।

### 12. ch 9 §9.5 ¶2 — en p12, hi p13, similarity 0.49
- en: On account of this motion, a portion of liquid, which at some instant has the shape ABCD, take the shape of AEFD after short interval of time (∆t). During this time interval the liquid has undergone a shear strain of ∆x/l. Since, the strain in a flowing fluid increases with time continuously. Unlike a solid, here the stress is found experimentally to depend on 'rate of change of strain' or 'strain rate' i.e. ∆x/(l ∆t) or v/l instead of strain itself. The coefficient of viscosity (pronounced 'eta') for a fluid is defined as the ratio of shearing stress to the strain rate. eta = (F / A) / (v / l) = F l / (v A) (9.16)
- hi: इस गति के कारण द्रव के एक भाग जो किसी समय ABCD के रूप में था कुछ समय अंतराल (Delta t) में AEFD का स्वरूप ले लेता है। इस समय अंतराल में द्रव में एक अवरूपण विकृति Delta x/l उत्पन्न हो जाती है। चूंकि प्रवाहित द्रव में समय के बढ़ने के अनुसार विकृति बढ़ती जाती है। ठोसों के विपरीत प्रयोगों द्वारा पाया गया है कि प्रतिबल विकृति की अपेक्षा 'विकृति परिवर्तन की दर' या 'विकृति दर' अर्थात् Delta x/(l Delta t) या v/l द्वारा प्रतिबल को प्राप्त किया जाता है। श्यानता गुणांक (उच्चारण 'इटा') की परिभाषा अवरूपण प्रतिबल तथा विकृतिदर के रूप में की जाती है, eta = (F/A) / (v/l) = Fl / (vA) (9.16)

### 13. ch 9 §9.5.1 ¶2 — en p13, hi p14, similarity 0.46 — the founder reads this one
- en: This is known as Stokes’ law. We shall not derive Stokes’ law.
- hi: इसे स्टोक का नियम कहते हैं। हम स्टोक के नियम की व्युत्पत्ति नहीं करेंगे।

### 14. ch 9 §9.6 ¶1 — en p13–14, hi p14, similarity 0.47
- en: You must have noticed that, oil and water do not mix; water wets you and me but not ducks; mercury does not wet glass but water sticks to it, oil rises up a cotton wick, inspite of gravity, Sap and water rise up to the top of the leaves of the tree, hair of a paint brush do not cling together when dry and even when dipped in water but form a fine tip when taken out of it. All these and many more such experiences are related with the free surfaces of liquids. As liquids have no definite shape but have a definite volume, they acquire a free surface when poured in a container. These surfaces possess some additional energy. This phenomenon is known as surface tension and it is concerned with only liquid as gases do not have free surfaces. Let us now understand this phenomena.
- hi: आपने देखा होगा कि तेल तथा जल आपस में नहीं मिलते; जल आपको और मुझे गीला कर देता है परन्तु बतख को नहीं; पारा काँच से नहीं चिपकता किन्तु जल चिपक जाता है; गुरुत्व बल की उपस्थिति में भी तेल रुई की बत्ती से ऊपर चढ़ जाता है। रस तथा पानी पेड़ के शीर्ष की पत्तियों तक ऊपर उठ जाता है, रंग के ब्रुश के बाल सूखे होने पर और पानी में डुबोने पर भी एक दूसरे से नहीं चिपकते लेकिन जब उसके बाहर होते हैं तो एक उत्तम नोक बनाते हैं। ये और ऐसे ही अनेक अनुभव द्रवों की स्वतंत्र सतहों से संबंधित हैं। द्रवों की कोई निश्चित आकृति नहीं होती, परन्तु उनका अपना एक निश्चित आयतन होता है। जब उन्हें किसी पात्र में उड़ेलते हैं तो उनका एक स्वतंत्र पृष्ठ होगा। इन पृष्ठों की कुछ अतिरिक्त ऊर्जा होती है। इस परिघटना को पृष्ठ तनाव कहते हैं। यह केवल द्रवों में हो सकती है क्योंकि गैसों के कोई स्वतंत्र पृष्ठ नहीं होते। अब हम इस परिघटना को समझने का प्रयत्न करते हैं।

### 15. ch 9 §9.6.2 ¶9 — en p16, hi p17, similarity 0.44
- en: A fluid will stick to a solid surface if the surface energy between fluid and the solid is smaller than the sum of surface energies between solid-air, and fluid-air. Now there is attraction between the solid surface and the liquid. It can be directly measured experimentally as schematically shown in Fig. 9.16. A flat vertical glass plate, below which a vessel of some liquid is kept, forms one arm of the balance. The plate is balanced by weights on the other side, with its horizontal edge just over water. The vessel is raised slightly till the liquid just touches the glass plate and pulls it down a little because of surface tension. Weights are added till the plate just clears water.
- hi: यदि ठोस-वायु तथा तरल-वायु पृष्ठीय ऊर्जाओं के योग से तरल तथा ठोस की पृष्ठीय ऊर्जा कम है तो तरल ठोस से चिपकेगा। ठोस तथा द्रव पृष्ठों में आर्कषण बल होता है। चित्र 9.16 में उपकरण के आरेख के अनुसार हम इसे सीधे ही माप सकते हैं। एक चपटी क्षैतिज काँच की प्लेट जिसके नीचे किसी पात्र में द्रव भरा है, तुला की एक भुजा कार्य करती है। प्लेट के क्षैतिज निचले किनारे को पानी से थोड़ा ऊपर रखकर, तुला के दूसरी ओर बाट रखकर संतुलित कर लेते हैं। द्रव से भरे पात्र को थोड़ा ऊपर उठाते हैं ताकि यह काँच की प्लेट के क्षैतिज किनारों को छूने भर लगे और पृष्ठ तनाव के कारण प्लेट को नीचे की ओर खींचने लगे। अब दूसरी ओर कुछ बाट रखते हैं जब तक कि प्लेट द्रव से कुछ अलग न हो जाए।

### 16. ch 9 §9.6.5 ¶5 — en p18, hi p19, similarity 0.60
- en: Example 9.10 The lower end of a capillary tube of diameter 2.00 mm is dipped 8.00 cm below the surface of water in a beaker. What is the pressure required in the tube in order to blow a hemispherical bubble at its end in water? The surface tension of water at temperature of the experiments is 7.30 × 10^-2 Nm^-1. 1 atmospheric pressure = 1.01 × 10^5 Pa, density of water = 1000 kg/m^3, g = 9.80 m s^-2. Also calculate the excess pressure.
- hi: उदाहरण 9.10 2.00 mm व्यास की किसी केशनली का निचला सिरा बीकर में भरे जल के पृष्ठ से 8.00 cm नीचे तक डुबोया जाता है। नली के जल में डूबे सिरे पर अर्धगोलीय बुलबुला फुलाने के लिए नली के भीतर आवश्यक दाब ज्ञात कीजिए। प्रयोग के ताप पर जल का पृष्ठ तनाव 7.30 ×10^-2 Nm^-1 है। जल का घनत्व = 1000 kg/m^3, वायुमण्डलीय दाब = 1.01 × 10^5 Pa तथा g = 9.80 m s^-2। दाब आधिक्य भी परिकलित कीजिए।

### 17. ch 10 §10.4 ¶3 — en p3, hi p3, similarity 0.67
- en: However, measurements on real gases deviate from the values predicted by the ideal gas law at low temperature. But the relationship is linear over a large temperature range, and it looks as though the pressure might reach zero with decreasing temperature if the gas continued to be a gas. The absolute minimum temperature for an ideal gas, therefore, inferred by extrapolating the straight line to the axis, as in Fig. 10.3. This temperature is found to be – 273.15 °C and is designated as absolute zero. Absolute zero is the foundation of the Kelvin temperature scale or absolute scale temperature named after the British scientist Lord Kelvin. On this scale, – 273.15 °C is taken as the zero point, that is 0 K (Fig. 10.4).
- hi: तथापि, निम्न ताप पर वास्तविक गैसों पर ली गई मापों तथा आदर्श गैस नियम द्वारा प्रागुक्त मानों में अंतर पाया गया है। परन्तु एक विस्तृत ताप परिसर में यह संबंध रैखिक है तथा ऐसा प्रतीत होता है कि यदि गैस गैसीय अवस्था में ही बनी रहे तो ताप घटाने पर दाब शून्य हो जाएगा। चित्र 10.3 में दर्शाए अनुसार, सरल रेखा को बहिर्वेशित करके किसी आदर्श गैस के लिए परम निम्निष्ठ ताप प्राप्त किया जा सकता है। इस ताप का मान – 273.15°C पाया गया तथा इसे परम शून्य कहा जाता है। परम शून्य ब्रिटिश वैज्ञानिक लॉर्ड केल्विन के नाम पर केल्विन ताप मापक्रम अथवा परम ताप मापक्रम का आधार है। इस मापक्रम पर – 273.15°C को शून्य बिंदु के रूप में, अर्थात् 0 K लिया जाता है (चित्र 10.4)।

### 18. ch 10 §10.5 ¶8 — en p5, hi p5, similarity 0.65
- en: This means that water has the maximum density at 4 °C. This property has an important environmental effect: bodies of water, such as lakes and ponds, freeze at the top first. As a lake cools toward 4 °C, water near the surface loses energy to the atmosphere, becomes denser, and sinks; the warmer, less dense water near the bottom rises. However, once the colder water on top reaches temperature below 4 °C, it becomes less dense and remains at the surface, where it freezes. If water did not have this property, lakes and ponds would freeze from the bottom up, which would destroy much of their animal and plant life.
- hi: इसका अर्थ यह हुआ कि जल का घनत्व 4°C पर अधिकतम होता है। जल के इस गुण का एक महत्त्वपूर्ण पर्यावरणीय प्रभाव है : तालाबों तथा झीलों जैसे जलाशयों का शीर्ष भाग पहले जमता है। जैसे-जैसे झील 4°C तक ठंडी होती जाती है, पृष्ठ के समीप का जल अपनी ऊर्जा वातावरण को देता जाता है और संघनित होकर डूबता जाता है। तली का उष्ण, अपेक्षाकृत कम संघनित जल ऊपर उठता है। परन्तु, एक बार शीर्षभाग के ठंडे जल का ताप 4°C से नीचे पहुँच जाता है, यह जल कम संघनित बन जाता है, और पृष्ठ पर ही रहता है, जहाँ यह जम जाता है। यदि जल में यह गुण न होता, तो झील तथा तालाब तली से ऊपर की ओर जमते जिससे उसका अधिकांश जलीय जीवन (जल जीव-जन्तु तथा पौधे) नष्ट हो जाता।

### 19. ch 10 §10.9.1 ¶2 — en p13, hi p14, similarity 0.58 — the founder reads this one
- en: Heat conduction may be described quantitatively as the time rate of heat flow in a material for a given temperature difference. Consider a metallic bar of length L and uniform cross-section A with its two ends maintained at different temperatures. This can be done, for example, by putting the ends in thermal contact with large reservoirs at temperatures, say, T_C and T_D, respectively (Fig. 10.14). Let us assume the ideal condition that the sides of the bar are fully insulated so that no heat is exchanged between the sides and the surroundings.
- hi: मात्रात्मक रूप में, ऊष्मा चालन का वर्णन “किसी पदार्थ में किसी दिए गए तापांतर के लिए ऊष्मा प्रवाह की दर” द्वारा किया जाता है। L लंबाई तथा A एकसमान अनुप्रस्थ काट क्षेत्रफल की धातु की किसी ऐसी छड़ पर विचार कीजिए जिसके दोनों सिरों के बीच तापांतर स्थापित किया गया है। उदाहरण के लिए, ऐसा छड़ के सिरों को क्रमशः T_C तथा T_D ताप के ऊष्मा भंडारों के संपर्क में रखकर किया जा सकता है (चित्र 10.14)। अब हम एक ऐसी आदर्श स्थिति की कल्पना करते हैं जिसमें छड़ के पार्श्व पूर्णतः ऊष्मारोधी हैं ताकि पार्श्वों तथा परिवेश के बीच ऊष्मा का विनिमय नहीं होता।

### 20. ch 10 §10.10 ¶8 — en p19, hi p20, similarity 0.48 — the founder reads this one
- en: For small temperature differences, the rate of cooling, due to conduction, convection, and radiation combined, is proportional to the difference in temperature. It is a valid approximation in the transfer of heat from a radiator to a room, the loss of heat through the wall of a room, or the cooling of a cup of tea on the table.
- hi: लघु तापांतरों के लिए, चालन, संवहन तथा विकिरण के संयुक्त प्रभाव के कारण शीतलन की दर तापांतर के अनुक्रमानुपाती होती है। किसी विकिरक से कमरे में ऊष्मा स्थानांतरण, कमरे की दीवारों से पार होकर ऊष्मा-क्षति अथवा मेज पर प्याले में रखी चाय के शीतलन में यह एक वैध सन्निकटन है।

### 21. ch 10 §10.10 ¶9 — en p19–20, hi p20, similarity 0.60 — the founder reads this one
- en: Newton's law of cooling can be verified with the help of the experimental set-up shown in Fig. 10.20(a). The set-up consists of a double-walled vessel (V) containing water between the two walls. A copper calorimeter (C) containing hot water is placed inside the double-walled vessel. Two thermometers through the corks are used to note the temperatures T_2 of water in calorimeter and T_1 of hot water in between the double walls, respectively. Temperature of hot water in the calorimeter is noted after equal intervals of time. A graph is plotted between log_e (T_2–T_1) [or ln(T_2–T_1)] and time (t). The nature of the graph is observed to be a straight line having a negative slope as shown in Fig. 10.20(b). This is in support of Eq. 10.22.
- hi: चित्र 10.20(a) में दर्शायी गई प्रायोगिक व्यवस्था की सहायता से न्यूटन के शीतलन नियम का सत्यापन किया जा सकता है। इसमें दोहरी दीवारों वाला एक बर्तन (V) जिसकी दीवारों के बीच जल भरा होता है, लिया जाता है। इस दोहरी दीवारों वाले बर्तन में तप्त जल से भरा ताँबे का ऊष्मामापी (C) रखते हैं। इसमें दो तापमापियों का उपयोग किया जाता है, जिसमें तापमापी T_1 के द्वारा दोहरी दीवारों के बीच भरे उष्ण जल का ताप, तथा तापमापी T_2 के द्वारा ऊष्मामापी में भरे जल का ताप मापते हैं। ऊष्मामापी के तप्त जल का ताप एक नियमित अंतराल के पश्चात् मापा जाता है। समय t तथा log_e (T_2–T_1) [या ln (T_2–T_1)] के बीच ग्राफ आलेखित किया जाता है जिसकी प्रकृति चित्र 10.19(b) में दर्शाए अनुसार ऋणात्मक प्रवणता की एक सरल रेखा होती है। यह समीकरण 10.22 की पुष्टि करती है।

### 22. ch 11 §11.1 ¶1 — en p1, hi p1, similarity 0.65 — the founder reads this one
- en: In previous chapter we have studied thermal properties of matter. In this chapter we shall study laws that govern thermal energy. We shall study the processes where work is converted into heat and vice versa. In winter, when we rub our palms together, we feel warmer; here work done in rubbing produces the ‘heat’. Conversely, in a steam engine, the ‘heat’ of the steam is used to do useful work in moving the pistons, which in turn rotate the wheels of the train.
- hi: पिछले अध्याय में हमने द्रव्यों के तापीय गुणों का अध्ययन किया। इस अध्याय में हम उन नियमों का अध्ययन करेंगे जो ऊष्मीय ऊर्जा को निर्धारित करते हैं। हम उन प्रक्रियाओं का अध्ययन करेंगे जिनमें कार्य ऊष्मा में परिवर्तित होता है, तथा विलोमतः ऊष्मा भी कार्य में परिवर्तित होती है । शीत ऋतु में जब हम हथेलियों को परस्पर रगड़ते हैं तो हमें गरमी की अनुभूति होती है क्योंकि इस प्रक्रिया में किया गया कार्य ऊष्मा उत्पन्न करता है । इसके विपरीत, भाप इंजन में वाष्प की ऊष्मा का उपयोग लाभप्रद कार्य को संपन्न करने में अर्थात् पिस्टन को गति देने में होता है जिसके परिणामस्वरूप रेलगाड़ी के पहिए घूमते हैं।

### 23. ch 11 §11.1 ¶2 — en p1, hi p1, similarity 0.64
- en: In physics, we need to define the notions of heat, temperature, work, etc. more carefully. Historically, it took a long time to arrive at the proper concept of ‘heat’. Before the modern picture, heat was regarded as a fine invisible fluid filling in the pores of a substance. On contact between a hot body and a cold body, the fluid (called caloric) flowed from the colder to the hotter body ! This is similar to what happens when a horizontal pipe connects two tanks containing water up to different heights. The flow continues until the levels of water in the two tanks are the same. Likewise, in the ‘caloric’ picture of heat, heat flows until the ‘caloric levels’ (i.e., the temperatures) equalise.
- hi: भौतिकी में ऊष्मा, ताप, कार्य आदि की अवधारणाओं को अधिक सावधानीपूर्वक परिभाषित करने की आवश्यकता पड़ती है । ऐतिहासिक रूप से ऊष्मा की सटीक अवधारणा तक पहुँचने के लिए पर्याप्त समय लगा । आधुनिक अवधारणा के पूर्व ऊष्मा को ऐसे सूक्ष्म अदृश्य तरल के रूप में समझा गया जो किसी पदार्थ के रंध्रों में भरा रहता है । गरम व ठंडे पिंडों के पारस्परिक संपर्क में आने पर यह तरल (जिसे कैलॉरिक कहते थे) ठंडे पिंड से अपेक्षाकृत गरम पिंड में बहने लगता है ! यह बिलकुल वैसा ही है जैसा उस समय होता है जब भिन्न-भिन्न ऊँचाइयों तक पानी से भरी दो टंकियों को एक क्षैतिज नल से जोड़ दिया जाता है । जल का बहाव उस समय तक निरंतर बना रहता है जब तक दोनों टंकियों में जल के तल समान न हो जाएँ। इसी के समान ऊष्मा की 'कैलॉरिक' धारणा में ऊष्मा उस समय तक प्रवाहित होती रहती है जब तक कि 'कैलॉरिक तल' (अर्थात् ताप) समान नहीं हो जाते ।

### 24. ch 11 §11.3 ¶2 — en p3, hi p3, similarity 0.60
- en: The Zeroth Law clearly suggests that when two systems A and B, are in thermal equilibrium, there must be a physical quantity that has the same value for both. This thermodynamic variable whose value is equal for two systems in thermal equilibrium is called temperature (T ). Thus, if A and B are separately in equilibrium with C, T_A = T_C and T_B = T_C. This implies that T_A = T_B i.e. the systems A and B are also in thermal equilibrium.
- hi: शून्य कोटि नियम से यह संकेत मिलता है कि जब दो निकाय A व B परस्पर तापीय साम्य में होते हैं, तो ऐसी कोई भौतिक राशि है जो दोनों निकायों के लिए समान मान रखती है। यह ऊष्मागतिक चर, जिसका मान तापीय साम्य वाले निकायों के लिए समान होता है, ताप (T) कहलाता है। अतः यदि A व B साम्यावस्था में तीसरे निकाय C से पृथक् हैं तो T_A = T_C तथा T_B = T_C। इसका तात्पर्य यह है कि T_A = T_B अर्थात् निकाय A व B स्वयं भी तापीय साम्य में हैं।

### 25. ch 11 §11.4 ¶3 — en p4, hi p4, similarity 0.61
- en: Though we have invoked the molecular picture to understand the meaning of internal energy, as far as thermodynamics is concerned, U is simply a macroscopic variable of the system. The important thing about internal energy is that it depends only on the state of the system, not on how that state was achieved. Internal energy U of a system is an example of a thermodynamic 'state variable' – its value depends only on the given state of the system, not on history i.e. not on the 'path' taken to arrive at that state. Thus, the internal energy of a given mass of gas depends on its state described by specific values of pressure, volume and temperature. It does not depend on how this state of the gas came about. Pressure, volume, temperature, and internal energy are thermodynamic state variables of the system (gas) (see section 11.7). If we neglect the small intermolecular forces in a gas, the internal energy of a gas is just the sum of kinetic energies associated with various random motions of its molecules. We will see in the next chapter that in a gas this motion is not only translational (i.e. motion from one point to another in the volume of the container); it also includes rotational and vibrational motion of the molecules (Fig. 11.3).
- hi: यद्यपि हमने आंतरिक ऊर्जा के अर्थ को समझने के लिए आण्विक चित्र प्रस्तुत किया है तथापि जहाँ तक ऊष्मागतिकी का संबंध है, U निकाय का केवल एक स्थूल चर ही है । आंतरिक ऊर्जा के संबंध में एक महत्वपूर्ण तथ्य यह है कि यह केवल निकाय की अवस्था पर निर्भर करती है न कि इस बात पर कि यह अवस्था किस प्रकार प्राप्त हुई । निकाय की आंतरिक ऊर्जा U ऊष्मागतिकीय ‘अवस्था चर’ का एक उदाहरण है । इसका मान निकाय की दी हुई अवस्था पर निर्भर करता है न कि उसके इतिवृत्ति (History) (अर्थात् उस स्थिति तक पहुँचने के लिए अनुसरण किए गए पथ) पर। अतः किसी गैस के दिए गए द्रव्यमान के लिए आंतरिक ऊर्जा उसकी स्थिति पर निर्भर करती है । यह स्थिति दाब, आयतन व ताप के विशिष्ट मानों से वर्णित होती है । यह इस बात पर निर्भर नहीं करती कि गैस की यह स्थिति किस प्रकार प्राप्त हुई । दाब, आयतन, ताप तथा आंतरिक ऊर्जा निकाय (गैस) के ऊष्मागतिकीय अवस्था चर कहलाते हैं (अनुभाग 11.7 देखें) । यदि गैस के अल्पप्रभावी अंतराण्विक बलों की उपेक्षा कर दें तो गैस की आंतरिक ऊर्जा उसके अणुओं की अनेक यादृच्छिक गतियों से संबद्ध गतिज ऊर्जाओं के योग के ठीक बराबर होती है । अगले अध्याय में हम पढ़ेंगे कि किसी गैस में यह गति केवल स्थानांतरीय ही नहीं होती (इसमें गति पात्र के आयतन में एक बिंदु से दूसरे बिंदु के मध्य होती है), वरन् इसमें अणु की घूर्णी तथा कंपन गति भी होती है (चित्र 11.3) ।

### 26. ch 11 §11.4 ¶4 — en p5, hi p5, similarity 0.58 — the founder reads this one
- en: What are the ways of changing internal energy of a system ? Consider again, for simplicity, the system to be a certain mass of gas contained in a cylinder with a movable piston as shown in Fig. 11.4. Experience shows there are two ways of changing the state of the gas (and hence its internal energy). One way is to put the cylinder in contact with a body at a higher temperature than that of the gas. The temperature difference will cause a flow of energy (heat) from the hotter body to the gas, thus increasing the internal energy of the gas. The other way is to push the piston down i.e. to do work on the system, which again results in increasing the internal energy of the gas. Of course, both these things could happen in the reverse direction. With surroundings at a lower temperature, heat would flow from the gas to the surroundings. Likewise, the gas could push the piston up and do work on the surroundings. In short, heat and work are two different modes of altering the state of a thermodynamic system and changing its internal energy.
- hi: किसी निकाय की आंतरिक ऊर्जा में किन उपायों से परिवर्तन किए जा सकते हैं ? सुविधा की दृष्टि से पुनः कल्पना कीजिए कि चित्र 11.4 के अनुसार निकाय किसी दिए गए द्रव्यमान की एक गैस है जो एक सिलिंडर में भरी है जिसमें गतिशील पिस्टन लगा है । अनुभव यह बताता है कि गैस की अवस्था (तथा इस प्रकार उसकी आंतरिक ऊर्जा) परिवर्तित करने के दो उपाय होते हैं । एक उपाय है कि सिलिंडर को उस पिण्ड के संपर्क में रखें जो गैस की अपेक्षा उच्च ताप पर है । तापांतर के कारण ऊर्जा (ऊष्मा) गरम पिण्ड से गैस में प्रवाहित होगी । इससे गैस की आंतरिक ऊर्जा बढ़ जाएगी । दूसरा उपाय है कि पिस्टन को नीचे की ओर दबाया जाए (अर्थात् निकाय पर कार्य किया जाए) । इसमें भी गैस की आंतरिक ऊर्जा बढ़ जाती है । निःसंदेह ये दोनों बातें विपरीत दिशा में भी संभव होती हैं । यदि चारों ओर के परिवेश का ताप कम है तो ऊष्मा गैस से परिवेश में प्रवाहित होगी । इसी प्रकार, गैस पिस्टन को ऊपर की ओर धक्का दे सकती है और परिवेश पर कार्य कर सकती है । संक्षेप में, ऊष्मा और कार्य दो भिन्न-भिन्न विधियाँ हैं जिनसे ऊष्मीय निकाय की स्थिति परिवर्तित होती है तथा उसकी आंतरिक ऊर्जा में परिवर्तन होता है ।

### 27. ch 11 §11.4 ¶5 — en p5, hi p5, similarity 0.55 — the founder reads this one
- en: The notion of heat should be carefully distinguished from the notion of internal energy. Heat is certainly energy, but it is the energy in transit. This is not just a play of words. The distinction is of basic significance. The state of a thermodynamic system is characterised by its internal energy, not heat. A statement like ‘a gas in a given state has a certain amount of heat’ is as meaningless as the statement that ‘a gas in a given state has a certain amount of work’. In contrast, ‘a gas in a given state has a certain amount of internal energy’ is a perfectly meaningful statement. Similarly, the statements ‘a certain amount of heat is supplied to the system’ or ‘a certain amount of work was done by the system’ are perfectly meaningful.
- hi: ऊष्मा एवं आंतरिक ऊर्जा की धारणाओं में अंतर को सावधानीपूर्वक समझना आवश्यक है । ऊष्मा निश्चित रूप से ऊर्जा है परंतु यह ऊर्जा पारगमन में है । यह मात्र शब्दों का खेल नहीं है । दोनों में अंतर मूल महत्त्व का है । किसी ऊष्मागतिकी निकाय की स्थिति उसकी आंतरिक ऊर्जा से अभिलक्षित होती है न कि ऊष्मा से । इस प्रकार का प्रकथन कि 'किसी दी हुई अवस्था में गैस में ऊष्मा की कुछ मात्रा होती है' उतना ही निरर्थक है जितना कि यह प्रकथन कि 'किसी दी हुई स्थिति में गैस में कुछ कार्य निहित होता है ।' इसके विपरीत, 'किसी दी हुई अवस्था में गैस में आंतरिक ऊर्जा की कुछ मात्रा होती है' पूरी तरह से एक सार्थक प्रकथन है । इसी प्रकार से, ऐसे प्रकथन जैसे 'निकाय को एक निश्चित मात्रा की ऊष्मा दी गई है' या 'निकाय द्वारा एक निश्चित मात्रा का कार्य किया गया' पूर्णतः अर्थपूर्ण सार्थक प्रकथन हैं ।

### 28. ch 11 §11.4 ¶6 — en p5, hi p5, similarity 0.56 — the founder reads this one
- en: To summarise, heat and work in thermodynamics are not state variables. They are modes of energy transfer to a system resulting in change in its internal energy, which, as already mentioned, is a state variable.
- hi: संक्षेप में, ऊष्मा व कार्य ऊष्मागतिकी में स्थिति चर नहीं होते । ये किसी निकाय में ऊर्जा स्थानांतरण की विधियाँ होती हैं जिससे उसकी आंतरिक ऊर्जा परिवर्तित होती है। जो, जैसा कि पहले वर्णन कर चुके हैं, एक अवस्था चर होता है ।

### 29. ch 11 §11.8.3 ¶2 — en p10, hi p10, similarity 0.76
- en: Thus if an ideal gas undergoes a change in its state adiabatically from (P_1, V_1) to (P_2, V_2) : P_1 V_1^gamma = P_2 V_2^gamma (11.14)
- hi: अतः यदि कोई आदर्श गैस रुद्धोष्म ढंग से (P_1, V_1) अवस्था से (P_2, V_2) अवस्था में पहुँच जाती है, तो P_1V_1^gamma = P_2V_2^gamma (11.14)

### 30. ch 12 §12.3 ¶1 — en p3, hi p3, similarity 0.71
- en: Properties of gases are easier to understand than those of solids and liquids. This is mainly because in a gas, molecules are far from each other and their mutual interactions are negligible except when two molecules collide. Gases at low pressures and high temperatures much above that at which they liquefy (or solidify) approximately satisfy a simple relation between their pressure, temperature and volume given by (see Chapter 10) PV = KT (12.1) for a given sample of the gas. Here T is the temperature in kelvin or (absolute) scale. K is a constant for the given sample but varies with the volume of the gas. If we now bring in the idea of atoms or molecules, then K is proportional to the number of molecules, (say) N in the sample. We can write K = N k . Observation tells us that this k is same for all gases. It is called Boltzmann constant and is denoted by k_B. As P_1V_1 / N_1T_1 = P_2V_2 / N_2T_2 = constant = k_B (12.2) if P, V and T are same, then N is also same for all gases. This is Avogadro's hypothesis, that the number of molecules per unit volume is the same for all gases at a fixed temperature and pressure. The number in 22.4 litres of any gas is 6.02 × 10^23. This is known as Avogadro number and is denoted by N_A. The mass of 22.4 litres of any gas is equal to its molecular weight in grams at S.T.P (standard temperature 273 K and pressure 1 atm). This amount of substance is called a mole (see Chapter 1 for a more precise definition). Avogadro had guessed the equality of numbers in equal volumes of gas at a fixed temperature and pressure from chemical reactions. Kinetic theory justifies this hypothesis.
- hi: ठोसों एवं द्रवों की तुलना में गैसों के गुणों को समझना आसान है। यह मुख्यतः इस कारण होता है, क्योंकि, गैस में अणु एक दूसरे से दूर-दूर होते हैं और दो अणुओं के संघट्ट की स्थिति को छोड़कर उनके बीच पारस्परिक अन्योन्य क्रियाएँ उपेक्षणीय होती हैं जैसे निम्न दाब व उनके द्रवित (या घनीभूत) होने के तापों की अपेक्षा अत्यधिक उच्च ताप पर अपने ताप, दाब और आयतन में लगभग निम्नलिखित संबंध दर्शाती हैं (देखिए अध्याय 10) PV = KT (12.1) यह संबंध गैस के दिए गए नमूने के लिए है। यहाँ T केल्विन (या परम) पैमाने पर ताप है, K दिए गए नमूने के लिए नियतांक है परंतु आयतन के साथ परिवर्तित होता है यदि अब हम परमाणु या अणु की धारणा लागू करें तो, K दिए गए नमूने में अणुओं की संख्या N के अनुक्रमानुपाती है। हम लिख सकते हैं, K = N k। प्रयोग हमें बताते हैं कि k का मान सभी गैसों के लिए समान है। इसको बोल्ट्समान नियतांक कहा जाता है और k_B द्वारा निर्दिष्ट किया जाता है। P_1V_1 / N_1T_1 = P_2V_2 / N_2T_2 = नियतांक = k_B (12.2) यदि P, V एवं T समान हों तो N भी सभी गैसों के लिए समान होगा। यही आवोगाद्रो परिकल्पना है कि समान ताप एवं दाब पर सभी गैसों के प्रति एकांक आयतन में अणुओं की संख्या समान होती है। किसी गैस के 22.4 लीटर आयतन में यह संख्या 6.02×10^23 है। इस संख्या को आवोगाद्रो संख्या कहा जाता है और संकेत N_A द्वारा चिह्नित किया जाता है। किसी गैस के 22.4 लीटर आयतन का STP (मानक ताप = 273K एवं मानक दाब = 1 एटमौस्फियर) पर द्रव्यमान उस गैस के ग्राम में व्यक्त अणु द्रव्यमान के बराबर है। पदार्थ की यह मात्रा मोल (mole) कहलाती है (अधिक परिशुद्ध परिभाषा के लिए अध्याय 1 देखिए)। आवोगाद्रो ने रासायनिक अभिक्रियाओं के अध्ययन के आधार पर यह अनुमान लगा लिया था कि समान ताप और दाब पर गैसों के समान आयतन में अणुओं की संख्या समान होगी। अणुगति सिद्धांत इस परिकल्पना को न्यायसंगत ठहराता है।

### 31. ch 12 §12.3 ¶7 — en p4, hi p4, similarity 0.54 — the founder reads this one
- en: At low pressures or high temperatures the molecules are far apart and molecular interactions are negligible. Without interactions the gas behaves like an ideal one.
- hi: निम्न दाबों और उच्च तापों पर अणु दूर-दूर होते हैं और उनके बीच की आण्विक अन्योन्य क्रियाएँ उपेक्षणीय होती हैं। अन्योन्य क्रियाओं की अनुपस्थिति में गैस एक आदर्श गैस की तरह व्यवहार करती है।

### 32. ch 12 §12.3 ¶12 — en p5, hi p5, similarity 0.60
- en: Answer For a given mass of water molecules, the density is less if volume is large. So the volume of the vapour is 1000/0.6 = 1/(6 × 10^-4) times larger. If densities of bulk water and water molecules are same, then the fraction of molecular volume to the total volume in liquid state is 1. As volume in vapour state has increased, the fractional volume is less by the same amount, i.e. 6×10^-4.
- hi: हल : जल के किसी दिए गए द्रव्यमान के लिए यदि घनत्व कम हो, तो आयतन अधिक होगा। अतः, वाष्प का आयतन 1000/0.6 = 1/(6×10^-4) गुणा अधिक है। यदि स्थूल जल और जल के अणुओं के घनत्व समान हैं, तो गैस के अणुओं वाले भाग के आयतन, तथा उन्हीं अणुओं का द्रवित होकर जल की अवस्था में आयतन, का अनुपात 1 होगा। चूंकि वाष्प अवस्था में आयतन बढ़ गया है, अतः आंशिक आयतन उसी अनुपात (यानि 6×10^-4 गुणा) में कम हो जाएगा।

### 33. ch 12 §12.5 ¶1 — en p9, hi p9, similarity 0.71
- en: The kinetic energy of a single molecule is epsilon_t = 1/2 mv_x^2 + 1/2 mv_y^2 + 1/2 mv_z^2 (12.22)
- hi: किसी एकल अणु की गतिज ऊर्जा होती है : epsilon_t = (1/2) m v_x^2 + (1/2) m v_y^2 + (1/2) m v_z^2 (12.22)

### 34. ch 12 §12.6.1 ¶3 — en p10, hi p11, similarity 0.76
- en: For an ideal gas, C_p – C_v = R (12.29) where C_p is the molar specific heat at constant pressure. Thus, C_p = (5/2) R (12.30) The ratio of specific heats gamma = C_p / C_v = 5/3 (12.31)
- hi: आदर्श गैस के लिए C_p − C_v = R (12.29) जहाँ, C_p नियत दाब पर मोलर विशिष्ट ऊष्माधारिता है। अतः, C_p = (5/2) R (12.30) इन दो विशिष्ट ऊष्मा धारिताओं का अनुपात gamma = C_p/C_v = 5/3 (12.31)

### 35. ch 12 §12.7 ¶2 — en p12, hi p13, similarity 0.67
- en: Suppose the molecules of a gas are spheres of diameter d. Focus on a single molecule with the average speed <v>. It will suffer collision with any molecule that comes within a distance d between the centres. In time Delta t, it sweeps a volume pi d^2 <v> Delta t wherein any other molecule will collide with it (see Fig. 12.7). If n is the number of molecules per unit volume, the molecule suffers n pi d^2 <v> Delta t collisions in time Delta t. Thus the rate of collisions is n pi d^2 <v> or the time between two successive collisions is on the average, tau = 1/(n pi <v> d^2) (12.38)
- hi: मान लीजिए, किसी गैस के अणु d व्यास के गोले हैं। यहाँ हम अपना ध्यान किसी ऐसे गतिमान अणु पर केंद्रित करेंगे जिसकी माध्य चाल <v> है। यह किसी भी ऐसे दूसरे अणु से संघट्ट करेगा जो इन दो अणुओं के केंद्रों के बीच की दूरी d के अंदर आ जाएगा। Delta t समय में यह आयतन (pi d^2 <v> Delta t) तय करता है जिसमें आने वाला कोई अणु इससे टकराएगा (देखें चित्र 12.7)। यदि प्रति एकांक आयतन में अणुओं की संख्या n हो तो कोई अणु Delta t समय में n pi d^2 <v> Delta t संघट्ट करेगा। इस प्रकार संघट्टों की दर n pi d^2 <v> है। अथवा दो क्रमिक संघट्टों के बीच औसत अंतराल, tau = 1/(n pi <v> d^2) (12.38)

### 36. ch 13 §13.2.1 ¶5 — en p3, hi p3, similarity 0.80
- en: Example 13.1 On an average, a human heart is found to beat 75 times in a minute. Calculate its frequency and period.
- hi: उदाहरण 13.1 कोई मानव हृदय एक मिनट में औसतन 75 बार धड़कन करता पाया जाता है । इसकी आवृत्ति तथा आवर्तकाल परिकलित कीजिए ।

### 37. ch 13 §13.3 ¶5 — en p5–6, hi p6, similarity 0.75
- en: Finally, the quantity omega can be seen to be related to the period of motion T. Taking, for simplicity, phi = 0 in Eq. (13.4), we have x(t) = A cos omega t (13.5) Since the motion has a period T, x(t) is equal to x(t + T). That is, A cos omega t = A cos omega (t + T) (13.6)
- hi: अंततः राशि omega को गति के आवर्तकाल T से संबंधित देखा जा सकता है। सरलता के लिए समीकरण (13.4) में phi = 0 rad लेने पर हमें प्राप्त होता है– x(t) = A cos omega t (13.5) चूंकि गति का आवर्तकाल T है, x(t) का मान x(t+T) के समान होगा। अर्थात्, A cos omega t = A cos omega(t+T) (13.6)

### 38. ch 13 §13.6 ¶4 — en p9, hi p10, similarity 0.46
- en: Example 13.6 Two identical springs of spring constant k are attached to a block of mass m and to fixed supports as shown in Fig. 13.14. Show that when the mass is displaced from its equilibrium position on either side, it executes a simple harmonic motion. Find the period of oscillations.
- hi: उदाहरण 13.6 कमानी स्थिरांक k की दो सर्वसम कमानियाँ M संहति के किसी गुटके तथा स्थिर आधारों से चित्र 13.14 में दर्शाए गए अनुसार जुड़ी हुई हैं। यह दर्शाइए कि जब गुटके को अपनी साम्यावस्था की स्थिति से किसी ओर विस्थापित किया जाता है, तब यह सरल आवर्त गति करता है। दोलन का आवर्तकाल ज्ञात कीजिए।

### 39. ch 14 §14.3.3 ¶12 — en p7, hi p7, similarity 0.79
- en: (b) the angular wave number k and angular frequency omega are k = 80.0 m^-1 and omega = 3.0 s^-1
- hi: (b) कोणीय तरंग संख्या = 80.0 m^-1 तथा कोणीय आवृत्ति omega = 30 s^-1

### 40. ch 14 §14.4 ¶1 — en p7, hi p7, similarity 0.60
- en: To determine the speed of propagation of a travelling wave, we can fix our attention on any particular point on the wave (characterised by some value of the phase) and see how that point moves in time. It is convenient to look at the motion of the crest of the wave. Fig. 14.8 gives the shape of the wave at two instants of time, which differ by a small time internal ∆t. The entire wave pattern is seen to shift to the right (positive direction of x-axis) by a distance ∆x. In particular, the crest shown by a dot (•) moves a distance ∆x in time ∆t. The speed of the wave is then ∆x/∆t. We can put the dot (•) on a point with any other phase. It will move with the same speed v (otherwise the wave pattern will not remain fixed). The motion of a fixed phase point on the wave is given by kx – omega t = constant (14.10) Thus, as time t changes, the position x of the fixed phase point must change so that the phase remains constant. Thus, kx – omega t = k(x+∆x) – omega(t+∆t) or k ∆x – omega ∆t = 0 Taking ∆x, ∆t vanishingly small, this gives dx/dt = omega/k = v (14.11) Relating omega to T and k to lambda, we get v = 2 pi v / (2 pi / lambda) = lambda v = lambda / T (14.12)
- hi: किसी प्रगामी तरंग की चाल निरूपित करने के लिए हम तरंग के किसी बिन्दु (किसी कला कोण द्वारा अभिलक्षित) पर ध्यान केंद्रित कर सकते हैं और देखते हैं कि यह बिंदु समय के साथ किस तरह गमन करता है। तरंग के शीर्ष की गति पर ध्यान देना सुविधाजनक होता है। चित्र 14.8 में दो विभिन्न समयों, जिनके बीच Delta t का लघु समय अंतराल है, पर तरंग का आकार दर्शाया गया है। समस्त तरंग पैटर्न दाईं ओर (x-अक्ष की धनात्मक दिशा) Delta x दूरी चलता है। वास्तव में, बिन्दु (•) द्वारा दर्शाया शीर्ष समय Delta t में दूरी Delta x चलता है। इस प्रकार तरंग की चाल Delta x/Delta t है। किसी अन्य कला वाले बिंदु पर भी हम बिन्दु (•) लगा सकते हैं। यह उसी वेग v से गमन करेगा (अन्यथा तरंग पैटर्न अपरिवर्तित नहीं रहेगा)। तरंग पर किसी निश्चित कला बिंदु की गति को दिया जाता है kx – omega t = नियतांक (14.10) अतः जब समय t बदलता है, तो निश्चित कला बिंदु की स्थिति x भी इस प्रकार बदलती है कि कला कोणांक अचर रहे। अतः kx – omega t = k (x+Delta x)–omega(t + Delta t) या k Delta x – omega Delta t = 0 Delta x, Delta t का अल्पतम मान लेने पर dx/dt = omega/k = v (14.11) omega को T से तथा k को lambda से संबंधित करने पर हमें प्राप्त होता है v = 2pi omega / 2pi k = lambda nu = lambda / T (14.12)

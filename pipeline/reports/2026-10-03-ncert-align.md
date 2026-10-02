# margai-pipeline ncert align

- run: 2026-10-03 00:19 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 909 English and 909 Hindi row(s) in 7 chapter(s)
- result: ok
request id: pipeline-ncert-align-733d7e76-373d-4fce-b452-a0b58bff8bfa
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 909 | 256281 | ₹9.28 |

## sections whose rows do not pair one to one

| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 2 | 2.4 | 33 | 33 | 2 |
| 5 | 5.8 | 16 | 16 | 2 |
| 5 | 5.9 | 19 | 19 | 2 |
| 5 | 5.11.2 | 13 | 13 | 2 |
| 6 | 6.3 | 12 | 12 | 2 |
| 6 | 6.5 | 27 | 27 | 6 |
| 6 | 6.6 | 16 | 16 | 2 |
| 6 | 6.9 | 15 | 15 | 2 |
| 6 | 6.12 | 19 | 19 | 2 |
| 7 | 7.6 | 11 | 11 | 4 |
| 7 | 7.8 | 16 | 16 | 2 |
| 7 | 7.10 | 9 | 9 | 2 |

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 2 §2.4: hi ¶1+¶2 = en ¶1 (join hi)
- ch 2 §2.4: hi ¶3 = en ¶2+¶3 (split hi)
- ch 5 §5.8: hi ¶12+¶13 = en ¶12 (join hi)
- ch 5 §5.8: hi ¶14 = en ¶13+¶14 (split hi)
- ch 5 §5.9: hi ¶2+¶3 = en ¶2 (join hi)
- ch 5 §5.9: hi ¶4 = en ¶3+¶4 (split hi)
- ch 5 §5.11.2: hi ¶2+¶3 = en ¶2 (join hi)
- ch 5 §5.11.2: hi ¶4 = en ¶3+¶4 (split hi)
- ch 6 §6.3: hi ¶2+¶3 = en ¶2 (join hi)
- ch 6 §6.3: hi ¶4 = en ¶3+¶4 (split hi)
- ch 6 §6.5: hi ¶11+¶12 = en ¶11 (join hi)
- ch 6 §6.5: hi ¶13 = en ¶12+¶13 (split hi)
- ch 6 §6.5: hi ¶20 = en ¶20+¶21 (split hi)
- ch 6 §6.5: hi ¶21+¶22 = en ¶22 (join hi)
- ch 6 §6.5: hi ¶24 = en ¶24+¶25 (split hi)
- ch 6 §6.5: hi ¶25+¶26 = en ¶26 (join hi)
- ch 6 §6.6: hi ¶12+¶13 = en ¶12 (join hi)
- ch 6 §6.6: hi ¶14 = en ¶13+¶14 (split hi)
- ch 6 §6.9: hi ¶1+¶2 = en ¶1 (join hi)
- ch 6 §6.9: hi ¶3 = en ¶2+¶3 (split hi)
- ch 6 §6.12: hi ¶16+¶17 = en ¶16 (join hi)
- ch 6 §6.12: hi ¶18 = en ¶17+¶18 (split hi)
- ch 7 §7.6: hi ¶1+¶2 = en ¶1 (join hi)
- ch 7 §7.6: hi ¶3 = en ¶2+¶3 (split hi)
- ch 7 §7.6: hi ¶8+¶9 = en ¶8 (join hi)
- ch 7 §7.6: hi ¶10 = en ¶9+¶10 (split hi)
- ch 7 §7.8: hi ¶6+¶7 = en ¶6 (join hi)
- ch 7 §7.8: hi ¶8 = en ¶7+¶8 (split hi)
- ch 7 §7.10: hi ¶3 = en ¶3+¶4 (split hi)
- ch 7 §7.10: hi ¶4+¶5 = en ¶5 (join hi)

## pairs weaker than 0.45 — read both against the pages

- ch 3 §3.5: hi ¶7 = en ¶7 — similarity 0.45 — en «Now, we have two ways to specify a…» · hi «किसी समतल में एक सदिश A को व्यक्त…»
- ch 4 §4.5: hi ¶11 = en ¶11 — similarity 0.44 — en «The rate of change of momentum of a…» · hi «किसी पिण्ड के संवेग परिवर्तन की दर आरोपित…»
- ch 4 §4.7: hi ¶3 = en ¶3 — similarity 0.44 — en «The total momentum of an isolated system of…» · hi «अन्योन्य क्रिया करने वाले कणों के किसी वियुक्त…»
- ch 4 §4.9.1: hi ¶14 = en ¶14 — similarity 0.40 — en «Rolling friction again has a complex origin, though…» · hi «लोटनिक घर्षण का उद्गम जटिल है यद्यपि यह…»
- ch 4 §4.10: hi ¶3 = en ¶3 — similarity 0.41 — en «The circular motion of a car on a…» · hi «किसी सपाट सड़क तथा किसी ढालू सड़क पर…»
- ch 4 §4.11: hi ¶13 = en ¶13 — similarity 0.33 — en «Action-reaction pairs» · hi «क्रिया-प्रतिक्रिया युगल»
- ch 5 §5.3: hi ¶4 = en ¶4 — similarity 0.36 — en «No work is done if :» · hi «कोई भी कार्य संपन्न हुआ नहीं माना जाता…»
- ch 5 §5.3: hi ¶6 = en ¶6 — similarity 0.40 — en «(ii) the force is zero. A block moving…» · hi «(ii) बल शून्य है। किसी चिकनी क्षैतिज मेज…»
- ch 5 §5.9: hi ¶9 = en ¶9 — similarity 0.39 — en «We note that we have idealised the situation.…» · hi «ध्यान दें कि यहाँ इस स्थिति को हमने…»
- ch 5 §5.9: hi ¶10 = en ¶10 — similarity 0.40 — en «We conclude this section by making a few…» · hi «हम संरक्षी बलों पर कुछ टिप्पणी करते हुए…»
- ch 5 §5.9: hi ¶16 = en ¶16 — similarity 0.41 — en «We invoke the work-energy theorem, rather than the…» · hi «यहाँ हम यांत्रिक ऊर्जा-संरक्षण के सिद्धांत के बजाय…»
- ch 6 §6.1: hi ¶2 = en ¶2 — similarity 0.42 — en «Any real body which we encounter in daily…» · hi «दैनिक जीवन में जितने पिण्ड हमारे संपर्क में…»
- ch 6 §6.3: hi ¶11 = en ¶11 — similarity 0.45 — en «This is the procedure that we followed earlier…» · hi «यही कार्यविधि हमने पिण्डों पर लगे बलों के…»
- ch 6 §6.4: hi ¶12 = en ¶12 — similarity 0.44 — en «In many problems on the system of particles,…» · hi «कणों की निकाय संबंधी बहुत सी समस्याओं में…»
- ch 6 §6.5: hi ¶3 = en ¶3 — similarity 0.37 — en «A vector product of two vectors a and…» · hi «दो सदिशों a एवं b का सदिश गुणनफल…»
- ch 6 §6.6: hi ¶8 = en ¶8 — similarity 0.44 — en «We have characterised pure translation of a body…» · hi «किसी पिण्ड की शुद्ध स्थानांतरण गति का अभिलक्षण…»
- ch 6 §6.7: hi ¶1 = en ¶1 — similarity 0.40 — en «In this section, we shall acquaint ourselves with…» · hi «इस अनुभाग में, हम आपको ऐसी दो राशियों…»
- ch 6 §6.9: hi ¶11 = en ¶11 — similarity 0.43 — en «As the mass of a body resists a…» · hi «क्योंकि, किसी पिण्ड का द्रव्यमान, उसकी रेखीय गत्यावस्था…»
- ch 6 §6.10: hi ¶12 = en ¶12 — similarity 0.44 — en «Answer» · hi «हल :»
- ch 6 §6.11: hi ¶2 = en ¶2 — similarity 0.44 — en «Before we begin, we note a simplification that…» · hi «इससे पहले कि हम अपनी बात शुरू करें,…»
- ch 6 §6.11: hi ¶3 = en ¶3 — similarity 0.42 — en «(1) We need to consider only those forces…» · hi «(1) पिण्ड पर कार्य करने वाले वे बल…»

## numbers one edition of a pair carries and the other does not

- ch 1 §1.2 ¶3: only in en — · only in hi 10
- ch 1 §1.2 ¶4: only in en — · only in hi 1.1
- ch 1 §1.3 ¶1: only in en — · only in hi 3
- ch 1 §1.3 ¶13: only in en — · only in hi 2
- ch 1 §1.3 ¶15: only in en — · only in hi 4
- ch 1 §1.3 ¶18: only in en 1 · only in hi —
- ch 1 §1.3 ¶20: only in en 0 · only in hi —
- ch 1 §1.3.1 ¶1: only in en — · only in hi 4
- ch 1 §1.3.2 ¶1: only in en 1.743, 1.74, 1 · only in hi 2.743, 2.74
- ch 1 §1.3.2 ¶6: only in en 2, 2 · only in hi —
- ch 1 §1.4 ¶2: only in en — · only in hi 3, 3
- ch 1 §1.4 ¶4: only in en — · only in hi 1, 1
- ch 2 §2.1 ¶1: only in en — · only in hi 24
- ch 2 §2.1 ¶4: only in en 4 · only in hi —
- ch 2 §2.2 ¶2: only in en 2.1 · only in hi —
- ch 2 §2.2 ¶3: only in en 2.1 · only in hi —
- ch 2 §2.2 ¶4: only in en 0.08, 0.08, 4.0 · only in hi 4, 0.8, 0.03
- ch 2 §2.2 ¶5: only in en 0.010, 4.0, 4.0 · only in hi 0.01, 4, 4
- ch 2 §2.2 ¶6: only in en — · only in hi 2.1
- ch 2 §2.2 ¶8: only in en — · only in hi 2, 2, 1
- ch 2 §2.3 ¶5: only in en 2.4 · only in hi 2.2, 2.2, 2.2
- ch 2 §2.3 ¶6: only in en — · only in hi 0
- ch 2 §2.4 ¶5: only in en — · only in hi 0
- ch 2 §2.4 ¶13: only in en — · only in hi 0
- ch 2 §2.4 ¶14: only in en — · only in hi 2, 2, 1
- ch 2 §2.4 ¶17: only in en — · only in hi 0, 1, 2
- ch 2 §2.4 ¶22: only in en — · only in hi 2.9
- ch 2 §2.4 ¶26: only in en — · only in hi 0, 0
- ch 2 §2.4 ¶27: only in en 0 · only in hi —
- ch 2 §2.4 ¶28: only in en 4 · only in hi 0
- ch 2 §2.4 ¶29: only in en — · only in hi 1
- ch 2 §2.4 ¶33: only in en — · only in hi 0
- ch 3 §3.4 ¶1: only in en 4.2 · only in hi 3.2
- ch 3 §3.4 ¶3: only in en — · only in hi 0
- ch 3 §3.4 ¶4: only in en 0 · only in hi —
- ch 3 §3.6 ¶6: only in en — · only in hi 3.10
- ch 3 §3.6 ¶8: only in en 3.24 · only in hi —
- ch 3 §3.7.1 ¶5: only in en — · only in hi 0
- ch 3 §3.7.1 ¶6: only in en 1, 2, 3 · only in hi 3.13
- ch 3 §3.7.1 ¶11: only in en 3, 32 · only in hi 3.32
- ch 3 §3.7.1 ¶12: only in en — · only in hi 3.15
- ch 3 §3.8 ¶1: only in en — · only in hi 0, 0
- ch 3 §3.8 ¶2: only in en — · only in hi 0, 0, 0, 0, 0, 0
- ch 3 §3.8 ¶3: only in en 3.9 · only in hi 3.10
- ch 3 §3.8 ¶5: only in en 6, 6 · only in hi 6.0
- ch 3 §3.9 ¶2: only in en — · only in hi 0
- ch 3 §3.9 ¶4: only in en — · only in hi 0, 0, 0, 0, 0
- ch 3 §3.9 ¶5: only in en — · only in hi 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2
- ch 3 §3.9 ¶6: only in en — · only in hi 3.17
- ch 3 §3.9 ¶7: only in en 3.17 · only in hi 0, 0
- ch 3 §3.9 ¶8: only in en 3.37 · only in hi 0, 0, 3.38
- ch 3 §3.9 ¶10: only in en — · only in hi 0, 0
- ch 3 §3.9 ¶11: only in en — · only in hi 90, 1
- ch 3 §3.9 ¶13: only in en — · only in hi 0, 0, 0
- ch 3 §3.9 ¶15: only in en — · only in hi 0, 0
- ch 3 §3.9 ¶17: only in en 2, 9.8, 14, 14 · only in hi 0, 0
- ch 3 §3.10 ¶9: only in en 1 · only in hi —
- ch 3 §3.10 ¶11: only in en 1 · only in hi —
- ch 4 §4.4 ¶7: only in en — · only in hi 1
- ch 4 §4.8 ¶3: only in en — · only in hi 3
- ch 4 §4.8 ¶4: only in en 4.11 · only in hi 4, 11
- ch 4 §4.10 ¶1: only in en 4 · only in hi 3
- ch 4 §4.10 ¶16: only in en — · only in hi 0, 0
- ch 4 §4.10 ¶18: only in en — · only in hi 2
- ch 4 §4.10 ¶20: only in en — · only in hi 0, 0, 0
- ch 5 §5.1 ¶1: only in en 14, 16 · only in hi —
- ch 5 §5.1.1 ¶1: only in en 3 · only in hi 4
- ch 5 §5.2 ¶1: only in en 3 · only in hi 2
- ch 5 §5.2 ¶3: only in en — · only in hi 5.2
- ch 5 §5.2 ¶4: only in en 5.2 · only in hi 5.3, 5.6
- ch 5 §5.2 ¶5: only in en 50.0 · only in hi 50.00
- ch 5 §5.2 ¶7: only in en 10.0 · only in hi 10
- ch 5 §5.3 ¶9: only in en 1811 · only in hi 1818
- ch 5 §5.3 ¶14: only in en 5.3 · only in hi —
- ch 5 §5.4 ¶3: only in en 50.0, 5.2, 2.00 · only in hi 50, 2
- ch 5 §5.5 ¶4: only in en 3.1 · only in hi —
- ch 5 §5.8 ¶8: only in en 0 · only in hi —
- ch 5 §5.8 ¶11: only in en — · only in hi 0
- ch 5 §5.9 ¶5: only in en — · only in hi 1
- ch 5 §5.9 ¶6: only in en 18.0 · only in hi 18
- ch 5 §5.9 ¶18: only in en 10.0 · only in hi 10
- ch 5 §5.10 ¶5: only in en — · only in hi 1
- ch 5 §5.10 ¶7: only in en 1 · only in hi —
- ch 5 §5.11.2 ¶6: only in en — · only in hi 1, 2
- ch 5 §5.11.3 ¶1: only in en 5.23 · only in hi 5.24
- ch 5 §5.11.3 ¶3: only in en — · only in hi 5.28, 5.29
- ch 5 §5.11.3 ¶5: only in en — · only in hi 5.31
- ch 6 §6.1.1 ¶5: only in en 1, 2 · only in hi —
- ch 6 §6.1.1 ¶6: only in en 6.5 · only in hi —
- ch 6 §6.1.1 ¶9: only in en 6.5 · only in hi —
- ch 6 §6.2 ¶1: only in en — · only in hi 6.7
- ch 6 §6.2 ¶10: only in en 6.3 · only in hi —
- ch 6 §6.2 ¶12: only in en 6.4, 6.4 · only in hi —
- ch 6 §6.2 ¶15: only in en 6.4, 6.4, 6.4 · only in hi —
- ch 6 §6.2 ¶30: only in en — · only in hi 6.11
- ch 6 §6.2 ¶31: only in en 3 · only in hi —
- ch 6 §6.3 ¶11: only in en 1, 2 · only in hi —
- ch 6 §6.7.2 ¶17: only in en 6.23 · only in hi 6.27, 6.17
- ch 6 §6.7.2 ¶19: only in en 0 · only in hi —
- ch 6 §6.8 ¶8: only in en 6.30 · only in hi —
- ch 6 §6.8.1 ¶5: only in en 2 · only in hi —
- ch 6 §6.8.1 ¶8: only in en — · only in hi 1
- ch 6 §6.8.2 ¶5: only in en — · only in hi 0
- ch 6 §6.8.2 ¶8: only in en 30 · only in hi 40
- ch 6 §6.8.2 ¶20: only in en — · only in hi 2, 9.8
- ch 6 §6.10 ¶8: only in en 6.36 · only in hi 6.38
- ch 6 §6.10 ¶13: only in en — · only in hi 0, 1
- ch 6 §6.11 ¶5: only in en 6.29 · only in hi —
- ch 6 §6.11 ¶6: only in en 1, 1, 1 · only in hi —
- ch 6 §6.11 ¶7: only in en — · only in hi 1
- ch 6 §6.11 ¶9: only in en — · only in hi 1, 2
- ch 6 §6.12 ¶8: only in en — · only in hi 1
- ch 6 §6.12 ¶10: only in en — · only in hi 6.42
- ch 6 §6.12 ¶15: only in en 6.28 · only in hi 6.26
- ch 7 §7.3 ¶6: only in en — · only in hi 2, 1
- ch 7 §7.3 ¶14: only in en — · only in hi 2, 1, 1, 1, 1, 1
- ch 7 §7.4 ¶1: only in en 1798 · only in hi 1797
- ch 7 §7.5 ¶1: only in en 7.3 · only in hi —
- ch 7 §7.5 ¶6: only in en 2 · only in hi —
- ch 7 §7.7 ¶4: only in en — · only in hi 7.21
- ch 7 §7.7 ¶11: only in en 7.9 · only in hi 4, 2
- ch 7 §7.8 ¶5: only in en — · only in hi 7.28
- ch 7 §7.8 ¶9: only in en — · only in hi 7.32
- ch 7 §7.8 ¶10: only in en — · only in hi 1, 5
- ch 7 §7.8 ¶11: only in en — · only in hi 4
- ch 7 §7.8 ¶16: only in en — · only in hi 4
- ch 7 §7.9 ¶6: only in en 0 · only in hi 7.39
- ch 7 §7.10 ¶1: only in en — · only in hi 7.35, 2

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.3 ¶11 — en p4, hi p4, similarity 0.72
- en: [Thus 123 m = 12300 cm = 123000 mm has three significant figures, the trailing zero(s) being not significant.] However, you can also see the next observation.
- hi: (अतः 123 m = 12300 cm = 123000 mm में तीन ही सार्थक अंक हैं, संख्या में अनुगामी शून्य सार्थक अंक नहीं हैं)। तथापि, आप अगले प्रेक्षण पर भी ध्यान दे सकते हैं।

### 2. ch 1 §1.3 ¶16 — en p4, hi p4, similarity 0.45 — the founder reads this one
- en: Thus, in the scientific notation, no confusion arises about the trailing zero(s) in the base number a. They are always significant.
- hi: इस प्रकार, वैज्ञानिक संकेत में आधार संख्या a के अनुगामी शून्यों के बारे में कोई भ्रांति नहीं रह जाती। वे सदैव सार्थक अंक होते हैं।

### 3. ch 1 §1.3 ¶17 — en p4, hi p4, similarity 0.53 — the founder reads this one
- en: (4) The scientific notation is ideal for reporting measurement. But if this is not adopted, we use the rules adopted in the preceding example :
- hi: (4) किसी भी मापन के प्रस्तुतिकरण की वैज्ञानिक संकेत विधि एक आदर्श विधि है। परन्तु यदि यह विधि नहीं अपनायी जाती, तो हम पूर्वगामी उदाहरण में उल्लिखित नियमों का पालन करते हैं :

### 4. ch 1 §1.3.1 ¶3 — en p5, hi p5, similarity 0.73
- en: Thus, in the example above, density should be reported to three significant figures. Density = 4.237 g / 2.51 cm^3 = 1.69 g cm^-3
- hi: अतः उपरोक्त उदाहरण में घनत्व को तीन सार्थक अंकों तक ही लिखा जाना चाहिए, घनत्व = 4.237g / 2.51 cm^3 = 1.69 g cm^-3

### 5. ch 1 §1.3.1 ¶8 — en p5, hi p5, similarity 0.70
- en: Note that we should not use the rule (1) applicable for multiplication and division and write 664 g as the result in the example of addition and 3.00 × 10^-3 m in the example of subtraction. They do not convey the precision of measurement properly. For addition and subtraction, the rule is in terms of decimal places.
- hi: ध्यान दीजिए, हमें नियम (1) जो गुणा और भाग के लिए लागू होता है, उसे संकलन (योग) के उदाहरण में प्रयोग करके परिणाम को 664 g नहीं लिखना चाहिए और व्यवकलन के उदाहरण में 3.00 × 10^-3 m नहीं लिखना चाहिए। ये माप की परिशुद्धता को उचित रूप से व्यक्त नहीं करते हैं। संकलन और व्यवकलन के लिए यह नियम दशमलव स्थान के पदों में है।

### 6. ch 1 §1.3.2 ¶4 — en p6, hi p6, similarity 0.56
- en: Answer The number of significant figures in the measured length is 4. The calculated area and the volume should therefore be rounded off to 4 significant figures. Surface area of the cube = 6(7.203)^2 m^2 = 311.299254 m^2 = 311.3 m^2 Volume of the cube = (7.203)^3 m^3 = 373.714754 m^3 = 373.7 m^3
- hi: हल मापी गई लम्बाई में सार्थक अंकों की संख्या 4 है। इसलिए, परिकलित क्षेत्रफल एवं आयतन के मानों को भी 4 सार्थक अंकों तक पूर्णांकित किया जाना चाहिए। घन का पृष्ठ क्षेत्रफल = 6(7.203)^2 m^2 = 311.299254 m^2 = 311.3 m^2 घन का आयतन = (7.203)^3 m^3 = 373.714754 m^3 = 373.7 m^3

### 7. ch 2 §2.2 ¶5 — en p3, hi p3, similarity 0.73
- en: We see from Table 2.1 that as we decrease the value of Delta t from 2.0 s to 0.010 s, the value of the average velocity approaches the limiting value 3.84 m s^-1 which is the value of velocity at t = 4.0 s, i.e. the value of dx/dt at t = 4.0 s. In this manner, we can calculate velocity at each instant for motion of the car.
- hi: सारणी 2.1 से स्पष्ट है कि जैसे-जैसे Delta t का मान 2.0 s से घटाते-घटाते 0.01 s करते हैं तो औसत वेग अंततः सीमांत मान 3.84 ms^-1 के बराबर हो जाता है जो t=4 s पर कार का वेग है अर्थात t=4 s पर dx/dt का मान । इस प्रकार गति के हर क्षण के लिए हम कार का वेग निकाल सकते हैं ।

### 8. ch 2 §2.2 ¶10 — en p3, hi p3, similarity 0.68
- en: Instantaneous speed or simply speed is the magnitude of velocity. For example, a velocity of + 24.0 m s^-1 and a velocity of – 24.0 m s^-1 — both have an associated speed of 24.0 m s^-1. It should be noted that though average speed over a finite interval of time is greater or equal to the magnitude of the average velocity, instantaneous speed at an instant is equal to the magnitude of the instantaneous velocity at that instant. Why so ?
- hi: तात्क्षणिक चाल या केवल चाल गतिमान वस्तु के वेग का परिमाण है । उदाहरण के तौर पर, वेग + 24.0 m s^-1 तथा -24.0 m s^-1 दोनों में प्रत्येक का परिमाण 24.0 m s^-1 होगा। यहाँ यह तथ्य ध्यान में रखना है कि जहाँ किसी सीमित समय अंतराल में वस्तु की औसत चाल उसके औसत वेग के परिमाण के या तो बराबर होती है या उससे अधिक होती है वहीं किसी क्षण पर वस्तु की तात्क्षणिक चाल उस क्षण पर उसके तात्क्षणिक वेग के परिमाण के बराबर होती है । ऐसा क्यों होता है ?

### 9. ch 2 §2.3 ¶9 — en p5, hi p5, similarity 0.69
- en: The v-t curve is a straight line parallel to the time axis and the area under it between t = 0 and t = T is the area of the rectangle of height u and base T. Therefore, area = u × T = uT which is the displacement in this time interval. How come in this case an area is equal to a distance? Think! Note the dimensions of quantities on the two coordinate axes, and you will arrive at the answer.
- hi: चित्र में v-t वक्र समय अक्ष के समांतर एक सरल रेखा है । t=0 से t=T के मध्य इस रेखा के अंतर्गत आने वाला क्षेत्रफल उस आयत के क्षेत्रफल के बराबर है जिसकी ऊँचाई u तथा आधार T है । अतएव क्षेत्रफल = u × T = uT, जो इस समय में वस्तु के विस्थापन को व्यक्त करता है । कोई क्षेत्रफल दूरी के बराबर कैसे हो सकता है ? सोचिए ! दोनों निर्देशांक अक्षों के अनुदिश जो राशियाँ अंकित की गई हैं, यदि आप उनकी विमाओं पर ध्यान देंगे तो आपको इस प्रश्न का उत्तर मिल जाएगा।

### 10. ch 2 §2.4 ¶26 — en p8, hi p8, similarity 0.71
- en: Using this equation, we can calculate the position of the object after different time intervals, 0, tau, 2tau, 3tau… which are given in second column of Table 2.2. If we take (–1/2) g tau^2 as y_0 — the position coordinate after first time interval tau, then third column gives the positions in the unit of y_o. The fourth column gives the distances traversed in successive taus. We find that the distances are in the simple ratio 1: 3: 5: 7: 9: 11… as shown in the last column. This law was established by Galileo Galilei (1564-1642) who was the first to make quantitative studies of free fall.
- hi: इस समीकरण की सहायता से हम भिन्न-भिन्न समय अंतरालों 0, tau, 2tau, 3tau, ...... में वस्तु की स्थितियों की गणना कर सकते हैं जिन्हें सारणी 2.2 के दूसरे कॉलम में दिखाया है । यदि प्रथम समय अंतराल tau पर वस्तु का स्थिति निर्देशांक y_0 लें (y_0 = (–1/2)g tau^2) तो आगे के समय अंतरालों के बाद वस्तु की स्थितियों को y_0 के मात्रक में कॉलम तीन में दिए गए तरीके से व्यक्त कर सकते हैं । क्रमिक समय अंतरालों (प्रत्येक tau) में चली गई दूरियों को कॉलम चार में व्यक्त किया गया है । स्पष्ट है कि क्रमशः समय अंतरालों में वस्तु द्वारा चली गई दूरियाँ 1:3:5:7:9:11...... के सरल अनुपात में हैं जैसा कि अंतिम कॉलम में दिखाया गया है । इस नियम को सर्वप्रथम गैलीलियो गैलिली (1564-1642) ने प्रतिपादित किया था जिन्होंने मुक्त रूप से गिरती हुई वस्तु का पहली बार विधिवत परिमाणात्मक अध्ययन किया था ।

### 11. ch 3 §3.1 ¶2 — en p1, hi p1, similarity 0.49 — the founder reads this one
- en: The equations developed in this chapter for motion in a plane can be easily extended to the case of three dimensions.
- hi: हम इस अध्याय में जिन समीकरणों को प्राप्त करेंगे उन्हें आसानी से त्रिविमीय गति के लिए विस्तारित किया जा सकता है ।

### 12. ch 3 §3.2.2 ¶2 — en p2–3, hi p2–3, similarity 0.59
- en: Figure 3.2(a) shows two equal vectors A and B. We can easily check their equality. Shift B parallel to itself until its tail Q coincides with that of A, i.e. Q coincides with O. Then, since their tips S and P also coincide, the two vectors are said to be equal. In general, equality is indicated as A = B. Note that in Fig. 3.2(b), vectors A′ and B′ have the same magnitude but they are not equal because they have different directions. Even if we shift B′ parallel to itself so that its tail Q′ coincides with the tail O′ of A′, the tip S′ of B′ does not coincide with the tip P′ of A′.
- hi: चित्र 3.2(a) में दो समान सदिशों A तथा B को दर्शाया गया है । हम इनकी समानता की परख आसानी से कर सकते हैं । B को स्वयं के समांतर खिसकाइये ताकि उसकी पुच्छ Q सदिश A की पुच्छ O के संपाती हो जाए । फिर क्योंकि उनके शीर्ष S एवं P भी संपाती हैं अतः दोनों सदिश बराबर कहलाएंगे । सामान्यतया इस समानता को A = B के रूप में लिखते हैं । इस बात की ओर ध्यान दीजिए कि चित्र 3.2(b) में यद्यपि सदिशों A' तथा B' के परिमाण समान हैं फिर भी दोनों सदिश समान नहीं हैं क्योंकि उनकी दिशायें अलग-अलग हैं । यदि हम B' को उसके ही समांतर खिसकाएं जिससे उसकी पुच्छ Q', A' की पुच्छ O' से संपाती हो जाए तो भी B' का शीर्ष S', A' के शीर्ष P' का संपाती नहीं होगा ।

### 13. ch 3 §3.10 ¶1 — en p14, hi p15, similarity 0.55
- en: When an object follows a circular path at a constant speed, the motion of the object is called uniform circular motion. The word “uniform” refers to the speed, which is uniform (constant) throughout the motion. Suppose an object is moving with uniform speed v in a circle of radius R as shown in Fig. 3.18. Since the velocity of the object is changing continuously in direction, the object undergoes acceleration. Let us find the magnitude and the direction of this acceleration.
- hi: जब कोई वस्तु एकसमान चाल से एक वृत्ताकार पथ पर चलती है, तो वस्तु की गति को एकसमान वृत्तीय गति कहते हैं । शब्द “एकसमान” उस चाल के संदर्भ में प्रयुक्त हुआ है जो वस्तु की गति की अवधि में एकसमान (नियत) रहती है । माना कि चित्र 3.18 के अनुसार कोई वस्तु एकसमान चाल v से R त्रिज्या वाले वृत्त के अनुदिश गतिमान है । क्योंकि वस्तु के वेग की दिशा में निरन्तर परिवर्तन हो रहा है, अतः उसमें त्वरण उत्पन्न हो रहा है। हमें त्वरण का परिमाण तथा उसकी दिशा ज्ञात करनी है ।

### 14. ch 3 §3.10 ¶2 — en p15, hi p15, similarity 0.57
- en: Let r and r′ be the position vectors and v and v′ the velocities of the object when it is at point P and P′ as shown in Fig. 3.18(a). By definition, velocity at a point is along the tangent at that point in the direction of motion. The velocity vectors v and v′ are as shown in Fig. 3.18(a1). Delta v is obtained in Fig. 3.18 (a2) using the triangle law of vector addition. Since the path is circular, v is perpendicular to r and so is v′ to r′. Therefore, Delta v is perpendicular to Delta r. Since average acceleration is along Delta v (a_bar = Delta v / Delta t), the average acceleration a_bar is perpendicular to Delta r. If we place Delta v on the line that bisects the angle between r and r′, we see that it is directed towards the centre of the circle. Figure 3.18(b) shows the same quantities for smaller time interval. Delta v and hence a_bar is again directed towards the centre. In Fig. 3.18(c), Delta t -> 0 and the average acceleration becomes the instantaneous acceleration. It is directed towards the centre*. Thus, we find that the acceleration of an object in uniform circular motion is always directed towards the centre of the circle. Let us now find the magnitude of the acceleration.
- hi: माना r व r' तथा v व v'कण की स्थिति तथा गति सदिश हैं जब वह गति के दौरान क्रमशः बिंदुओं P व P' पर है (चित्र 3.18a) । परिभाषा के अनुसार, किसी बिंदु पर कण का वेग उस बिंदु पर स्पर्श रेखा के अनुदिश गति की दिशा में होता है । चित्र 3.18(a1) में वेग सदिशों v व v'को दिखाया गया है। चित्र 3.18(a2) में सदिश योग के त्रिभुज नियम का उपयोग करके Delta v निकाल लेते हैं । क्योंकि पथ वृत्तीय है, इसलिए चित्र में, ज्यामिति से स्पष्ट है कि v, r के तथा v', r' के लंबवत् हैं । इसलिए, Delta v, Delta r के लंबवत् होगा । पुनः क्योंकि औसत त्वरण Delta v (a_bar = Delta v / Delta t) के अनुदिश है, इसलिए a_bar भी Delta r के लंबवत् होगा । अब यदि हम Delta v को उस रेखा पर रखें जो r व r' के बीच के कोण को द्विभाजित करती है तो हम देखेंगे कि इसकी दिशा वृत्त के केंद्र की ओर होगी । इन्ही राशियों को चित्र 3.18(b) में छोटे समय अंतराल के लिए दिखाया गया है । Delta v, अतः a_bar की दिशा पुनः केंद्र की ओर होगी । चित्र (3.18c) में Delta t->0 है, इसलिए औसत त्वरण, तात्क्षणिक त्वरण के बराबर हो जाता है । इसकी दिशा केंद्र की ओर होती है* । इस प्रकार, यह निष्कर्ष निकलता है कि एकसमान वृत्तीय गति के लिए वस्तु के त्वरण की दिशा वृत्त के केंद्र की ओर होती है । अब हम इस त्वरण का परिमाण निकालेंगे।

### 15. ch 4 §4.8 ¶5 — en p10, hi p11, similarity 0.59
- en: Example 4.6 See Fig. 4.8. A mass of 6 kg is suspended by a rope of length 2 m from the ceiling. A force of 50 N in the horizontal direction is applied at the mid-point P of the rope, as shown. What is the angle the rope makes with the vertical in equilibrium ? (Take g = 10 m s^-2). Neglect the mass of the rope.
- hi: उदाहरण 4.6 6 kg संहति के किसी पिण्ड को छत से 2 m लंबाई की डोरी द्वारा लटकाया गया है । डोरी के मध्य-बिंदु पर चित्र 4.8 में दर्शाए अनुसार क्षैतिज दिशा में 50 N बल लगाया जाता है । साम्यावस्था में डोरी ऊर्ध्वाधर से कितना कोण बनाती है ? (g = 10 m s^-2 लीजिए)। डोरी की संहति को नगण्य मानिए ।

### 16. ch 4 §4.9 ¶4 — en p11–12, hi p12, similarity 0.53 — the founder reads this one
- en: We learnt that there are four fundamental forces in nature. Of these, the weak and strong forces appear in domains that do not concern us here. Only the gravitational and electrical forces are relevant in the context of mechanics. The different contact forces of mechanics mentioned above fundamentally arise from electrical forces. This may seem surprising since we are talking of uncharged and non-magnetic bodies in mechanics. At the microscopic level, all bodies are made of charged constituents (nuclei and electrons) and the various contact forces arising due to elasticity of bodies, molecular collisions and impacts, etc. can ultimately be traced to the electrical forces between the charged constituents of different bodies. The detailed microscopic origin of these forces is, however, complex and not useful for handling problems in mechanics at the macroscopic scale. This is why they are treated as different types of forces with their characteristic properties determined empirically.
- hi: प्रकृति में केवल चार मूल बल हैं। इनमें दुर्बल तथा प्रबल बल ऐसे प्रभाव क्षेत्र में प्रकट होते हैं, जिनका यहां हमसे संबंध नहीं है। यांत्रिकी के संदर्भ में केवल गुरुत्वाकर्षण तथा वैद्युत बल ही प्रासंगित होते हैं। यांत्रिकी के विभिन्न संपर्क बल जिनका हमने अभी वर्णन किया है, मूल रूप से वैद्युत बलों से ही उत्पन्न होते हैं। यह बात आश्चर्यजनक प्रतीत हो सकती है क्योंकि यांत्रिकी में हम अनावेशित तथा अचुंबकीय पिण्डों की चर्चा कर रहे हैं। परंतु सूक्ष्म स्तर पर, सभी पिण्ड आवेशित अवयवों (नाभिकों तथा इलेक्ट्रॉनों) से मिलकर बने हैं तथा आण्विक संघट्टों प्रतिघातों तथा पिण्डों की प्रत्यास्थता आदि के कारण उत्पन्न विभिन्न संपर्क बलों की खोजबीन से ज्ञात होता है कि अंततः ये विभिन्न पिण्डों के आवेशित अवयवों के बीच वैद्युत बल ही हैं। इन बलों की विस्तृत सूक्ष्म उत्पत्ति के विषय में जानकारी जटिल है तथा स्थूल स्तर पर यांत्रिकी की समस्याओं को हल करने की दृष्टि से उपयोगी नहीं है । यही कारण है कि उन्हें विभिन्न प्रकार के बलों के रूप माना जाता है तथा उनके अभिलाक्षणिक गुणों का आनुभविक निर्धारण किया जाता है।

### 17. ch 4 §4.10 ¶18 — en p16, hi p16, similarity 0.57
- en: Answer On an unbanked road, frictional force alone can provide the centripetal force needed to keep the cyclist moving on a circular turn without slipping. If the speed is too large, or if the turn is too sharp (i.e. of too small a radius) or both, the frictional force is not sufficient to provide the necessary centripetal force, and the cyclist slips. The condition for the cyclist not to slip is given by Eq. (4.18) : v^2 ≤ mu_s R g Now, R = 3 m, g = 9.8 m s^-2, mu_s = 0.1. That is, mu_s R g = 2.94 m^2 s^-2. v = 18 km/h = 5 m s^-1; i.e., v^2 = 25 m^2 s^-2. The condition is not obeyed. The cyclist will slip while taking the circular turn.
- hi: हल सपाट सड़क पर अकेला घर्षण बल ही साइकिल सवार को बिना फिसले वर्तुल मोड़ लेने के लिए आवश्यक अभिकेंद्र बल प्रदान कर सकता है। यदि चाल बहुत अधिक है, तथा/अथवा मोड़ अत्यधिक तीव्र है (अर्थात् त्रिज्या बहुत कम है), तब घर्षण बल इन स्थितियों में आवश्यक अभिकेंद्र बल प्रदान करने के लिए पर्याप्त नहीं होता और साइकिल सवार मोड़ लेते समय फिसल कर गिर जाता है। साइकिल सवार के न फिसलने की शर्त समीकरण (4.18) द्वारा इस प्रकार है : v^2 ≤ mu_s Rg अब, यहाँ इस प्रश्न में R = 3 m, g = 9.8 m s^-2 तथा mu_s = 0.1 अर्थात् mu_s Rg = 2.94 m^2s^-2; तथा v = 18 km/h = 5 m s^-1; अर्थात् v^2 = 25 m^2s^-2 अर्थात्, शर्त v^2 ≤ mu_s Rg का पालन नहीं होता। अतः, साइकिल सवार तीव्र वर्तुल मोड़ लेते समय फिसलकर गिरेगा।

### 18. ch 4 §4.11 ¶12 — en p17, hi p18, similarity 0.64
- en: By the third law, the action of the system on the floor is equal to 267.3 N vertically downward.
- hi: तृतीय नियम के अनुसार फर्श पर निकाय की क्रिया 267.3 N के बराबर है तथा यह ऊर्ध्वाधरतः अधोमुखी है।

### 19. ch 5 §5.5 ¶4 — en p5, hi p5–6, similarity 0.67
- en: If the displacements are allowed to approach zero, then the number of terms in the sum increases without limit, but the sum approaches a definite value equal to the area under the curve in Fig. 5.3(b). Then the work done is W = lim(∆x -> 0) sum(x_i to x_f) F(x) ∆x = integral(x_i to x_f) F(x) dx (5.7) where ‘lim’ stands for the limit of the sum when ∆x tends to zero. Thus, for a varying force the work done can be expressed as a definite integral of force over displacement (see also Appendix 3.1).
- hi: यदि विस्थापनों को अतिसूक्ष्म मान लिया जाए तब योगफल में पदों की संख्या असीमित रूप से बढ़ जाती है लेकिन योगफल एक निश्चित मान के समीप पहुंच जाता है जो चित्र 5.3(b) में वक्र के नीचे के क्षेत्रफल के समान होता है। अतः किया गया कार्य W = lim(Delta x -> 0) sum(x_i to x_f) F(x) Delta x = integral(x_i to x_f) F(x) dx (5.7) जहाँ 'lim' का अर्थ है 'योगफल की सीमा' जबकि Delta x नगण्य रूप से सूक्ष्म मानों की ओर अग्रसर है। इस प्रकार परिवर्ती बल के लिए किए गए कार्य को बल का विस्थापन पर सीमांकित समाकलन, के रूप में व्यक्त कर सकते हैं ।

### 20. ch 5 §5.9 ¶8 — en p11–12, hi p12, similarity 0.73
- en: The kinetic energy of the moving car is K = (1/2) mv^2 = (1/2) × 10^3 × 5 × 5 K = 1.25 × 10^4 J where we have converted 18 km h^-1 to 5 m s^-1 [It is useful to remember that 36 km h^-1 = 10 m s^-1]. At maximum compression x_m, the potential energy V of the spring is equal to the kinetic energy K of the moving car from the principle of conservation of mechanical energy. V = (1/2) k x_m^2 = 1.25 × 10^4 J We obtain x_m = 2.00 m
- hi: गतिमान कार की गतिज ऊर्जा : K = (1/2) m v^2 = (1/2) × 10^3 × 5 × 5 K = 1.25 × 10^4 J जहाँ कार की चाल 18 km h^-1 को इसके SI मान 5 m s^-1 में परिवर्तित कर दिया गया है । [ यहाँ यह ध्यान रखने योग्य है कि 36 km h^-1 = 10 m s^-1 ] । यांत्रिक ऊर्जा-संरक्षण नियम के अनुसार अधिकतम संपीडन x_m पर स्प्रिंग की स्थितिज ऊर्जा (V), गतिशील कार की गतिज ऊर्जा (K) के बराबर होती है। अतः V = (1/2) k x_m^2 = 1.25 × 10^4 J हल करने पर हम प्राप्त करते हैं कि x_m = 2.00 m

### 21. ch 6 §6.1.1 ¶9 — en p4, hi p4, similarity 0.53 — the founder reads this one
- en: We now recapitulate the most important observations of the present section: The motion of a rigid body which is not pivoted or fixed in some way is either a pure translation or a combination of translation and rotation. The motion of a rigid body which is pivoted or fixed in some way is rotation. The rotation may be about an axis that is fixed (e.g. a ceiling fan) or moving (e.g. an oscillating table fan [Fig.6.5(b)]). We shall, in the present chapter, consider rotational motion about a fixed axis only.
- hi: आइये अब हम प्रस्तुत खण्ड में वर्णित महत्वपूर्ण तथ्यों का सार फिर से आपको बतायें। एक ऐसा दृढ़ पिण्ड जो न तो किसी चूल पर टिका हो और न ही किसी रूप में स्थिर हो, दो प्रकार की गति कर सकता है - या तो शुद्ध स्थानांतरण या स्थानांतरण एवं घूर्णन गति का संयोजन। एक ऐसे दृढ़ पिण्ड की गति जो या तो चूल पर टिका हो या किसी न किसी रूप में स्थिर हो, घूर्णी गति होती है। घूर्णन किसी ऐसी अक्ष के परितः हो सकता है जो स्थिर हो (जैसे छत के पंखे में) या फिर एक ऐसी अक्ष के परितः जो स्वयं घूमती हो (जैसे इधर से उधर घूमते मेज के पंखे में)। इस अध्याय में हम एक स्थिर अक्ष के परितः होने वाली घूर्णी गति का ही अध्ययन करेंगे।

### 22. ch 6 §6.2 ¶13 — en p5, hi p5, similarity 0.52 — the founder reads this one
- en: The sum on the right hand side is a vector sum.
- hi: समीकरण के दाहिनी ओर लिखा गया योग सदिश-योग है।

### 23. ch 6 §6.2 ¶24 — en p6, hi p6, similarity 0.66
- en: Example 6.1 Find the centre of mass of three particles at the vertices of an equilateral triangle. The masses of the particles are 100g, 150g, and 200g respectively. Each side of the equilateral triangle is 0.5m long.
- hi: उदाहरण 6.1 एक समबाहु त्रिभुज के शीर्षों पर रखे गए तीन कणों का द्रव्यमान केन्द्र ज्ञात कीजिए। कणों के द्रव्यमान क्रमशः 100g, 150g, एवं 200g हैं। त्रिभुज की प्रत्येक भुजा की लम्बाई 0.5 m है।

### 24. ch 6 §6.3 ¶6 — en p8, hi p8, similarity 0.64
- en: Note when we talk of the force F_1 on the first particle, it is not a single force, but the vector sum of all the forces on the first particle; likewise for the second particle etc. Among these forces on each particle there will be external forces exerted by bodies outside the system and also internal forces exerted by the particles on one another. We know from Newton's third law that these internal forces occur in equal and opposite pairs and in the sum of forces of Eq. (6.10), their contribution is zero. Only the external forces contribute to the equation. We can then rewrite Eq. (6.10) as MA = F_ext (6.11) where F_ext represents the sum of all external forces acting on the particles of the system.
- hi: ध्यान दें कि जब हम पहले कण पर लगने वाले बल F_1 की बात करते हैं, तो यह कोई एकल बल नहीं है, बल्कि, इस कण पर लगने वाले सभी बलों का सदिश योग है। यही बात हम अन्य कणों के विषय में भी कह सकते हैं। प्रत्येक कण पर लगने वाले उन बलों में कुछ बाह्य बल होंगे जो निकाय से बाहर के पिण्डों द्वारा आरोपित होंगे और कुछ आंतरिक बल होंगे जो निकाय के अंदर के कण एक दूसरे पर आरोपित करते हैं। न्यूटन के तृतीय नियम से हम जानते हैं कि ये आंतरिक बल सदैव बराबर परिमाण के और विपरीत दिशा में काम करने वाले जोड़ों के रूप में पाए जाते हैं और इसलिए समीकरण (6.10) में बलों को जोड़ने में इनका योग शून्य हो जाता है। समीकरण में केवल बाह्य बलों का योगदान रह जाता है। समीकरण (6.10) को फिर इस प्रकार लिख सकते हैं MA = F_ext (6.11) जहाँ F_ext निकाय के कणों पर प्रभावी सभी बाह्य बलों का सदिश योग है।

### 25. ch 6 §6.4 ¶13 — en p10, hi p10, similarity 0.54 — the founder reads this one
- en: In astronomy, binary (double) stars is a common occurrence. If there are no external forces, the centre of mass of a double star moves like a free particle, as shown in Fig.6.14 (a). The trajectories of the two stars of equal mass are also shown in the figure; they look complicated. If we go to the centre of mass frame, then we find that there the two stars are moving in a circle, about the centre of mass, which is at rest. Note that the position of the stars have to be diametrically opposite to each other [Fig. 6.14(b)]. Thus in our frame of reference, the trajectories of the stars are a combination of (i) uniform motion in a straight line of the centre of mass and (ii) circular orbits of the stars about the centre of mass.
- hi: खगोलिकी में युग्मित (बायनरी) नक्षत्रों का पाया जाना एक आम बात है। यदि कोई बाह्य बल न लगा हो तो किसी युग्मित नक्षत्र का द्रव्यमान केन्द्र एक मुक्त-कण की तरह चलता है जैसा चित्र 6.14 (a) में दर्शाया गया है। चित्र में समान द्रव्यमान वाले दोनों नक्षत्रों के गमन पथ भी दर्शाये गए हैं; वे काफी जटिल दिखाई पड़ते हैं। यदि हम द्रव्यमान केन्द्र के फ्रेम से देखें तो हम पाते हैं कि ये दोनों नक्षत्र द्रव्यमान केन्द्र के परितः एक वृत्ताकार पथ पर गतिमान हैं जबकि द्रव्यमान केन्द्र स्थिर है। ध्यान दें, कि दोनों नक्षत्रों को वृत्ताकार पथ के व्यास के विपरीत सिरों पर बने रहना है (चित्र 6.14(b))। इस प्रकार इन नक्षत्रों का गमन पथ दो गतियों के संयोजन से निर्मित होता है (i) द्रव्यमान केन्द्र की सरल रेखा में समांग गति (ii) द्रव्यमान केन्द्र के परितः नक्षत्रों की वृत्ताकार कक्षाएँ।

### 26. ch 6 §6.6 ¶7 — en p13, hi p13, similarity 0.66
- en: Note that we use the same angular velocity omega for all the particles. We therefore, refer to omega as the angular velocity of the whole body.
- hi: ध्यान दें कि हमने सभी कणों का समान कोणीय वेग लिया है। इसलिए हम omega को पूरे पिण्ड का कोणीय वेग कह सकते हैं।

### 27. ch 6 §6.7.2 ¶1 — en p15, hi p15, similarity 0.48 — the founder reads this one
- en: Just as the moment of a force is the rotational analogue of force in linear motion, the quantity angular momentum is the rotational analogue of linear momentum. We shall first define angular momentum for the special case of a single particle and look at its usefulness in the context of single particle motion. We shall then extend the definition of angular momentum to systems of particles including rigid bodies.
- hi: जैसे बल आघूर्ण, रेखीय गति में बल का घूर्णी समतुल्य है, ठीक वैसे ही कोणीय संवेग, रेखीय संवेग का घूर्णी समतुल्य है। पहले हम एकल कण के विशिष्ट मामले में कोणीय संवेग को परिभाषित करेंगे और एकल कण की गति के संदर्भ में इसकी उपयोगिता देखेंगे। तब, कोणीय संवेग की परिभाषा को दृढ़ पिण्डों सहित कणों के निकायों के लिए लागू करेंगे।

### 28. ch 6 §6.7.2 ¶5 — en p16, hi p16, similarity 0.57
- en: The physical quantities, moment of a force and angular momentum, have an important relation between them. It is the rotational analogue of the relation between force and linear momentum. For deriving the relation in the context of a single particle, we differentiate l = r × p with respect to time, dl/dt = d/dt (r × p)
- hi: भौतिक राशियों, बल आघूर्ण एवं कोणीय संवेग में एक महत्वपूर्ण पारस्परिक संबंध है। यह संबंध भी बल एवं रेखीय संवेग के बीच के संबंध का घूर्णी समतुल्य है। एकल कण के संदर्भ में यह संबंध व्युत्पन्न करने के लिए हम l = r × p को समय के आधार पर अवकलित करते हैं, dl/dt = d/dt (r × p)

### 29. ch 6 §6.9 ¶6 — en p23, hi p24, similarity 0.62
- en: Compare Eq. (6.35) for the kinetic energy of a rotating body with the expression for the kinetic energy of a body in linear (translational) motion, K = (1/2) m v^2
- hi: समीकरण (6.35) द्वारा व्यक्त घूर्णन करते पिण्ड की गतिज ऊर्जा की रेखीय (स्थानांतरीय) गति करते पिण्ड की गतिज ऊर्जा K = (1/2) m v^2 से तुलना कीजिए।

### 30. ch 6 §6.9 ¶7 — en p23, hi p24, similarity 0.59
- en: Here, m is the mass of the body and v is its velocity. We have already noted the analogy between angular velocity omega (in respect of rotational motion about a fixed axis) and linear velocity v (in respect of linear motion). It is then evident that the parameter, moment of inertia I, is the desired rotational analogue of mass in linear motion. In rotation (about a fixed axis), the moment of inertia plays a similar role as mass does in linear motion.
- hi: यहाँ m पिण्ड का द्रव्यमान और v उसका वेग है। कोणीय वेग omega (किसी स्थिर अक्ष के घूर्णन के संदर्भ में) और रेखीय वेग v (रेखीय गति के संदर्भ में) की समतुल्यता हम पहले से ही जानते हैं। अतः यह स्पष्ट है कि जड़त्व आघूर्ण I, प्राचल द्रव्यमान का घूर्णी समतुल्य है। (स्थिर अक्ष के परितः) घूर्णन में जड़त्व आघूर्ण वही भूमिका अदा करता है जो रेखीय गति में द्रव्यमान।

### 31. ch 6 §6.11 ¶4 — en p27, hi p28, similarity 0.45
- en: (2) We need to consider only those components of the position vectors which are perpendicular to the axis. Components of position vectors along the axis will result in torques perpendicular to the axis and need not be taken into account.
- hi: (2) पिण्ड के कणों की स्थिति-सदिशों के केवल वे अवयव जो घूर्णन अक्ष के लम्बवत् हैं। या यूँ कहें कि बलों और स्थिति सदिशों के अक्ष के अनुदिश लिए गए अवयवों को हमें गणना में लाने की आवश्यकता नहीं है।

### 32. ch 6 §6.11 ¶18 — en p29, hi p29, similarity 0.45
- en: Just as force produces acceleration, torque produces angular acceleration in a body. The angular acceleration is directly proportional to the applied torque and is inversely proportional to the moment of inertia of the body. In this respect, Eq.(6.41) can be called Newton's second law for rotational motion about a fixed axis.
- hi: ठीक वैसे ही जैसे बल पिण्ड में रेखीय त्वरण उत्पन्न करता है, बल आघूर्ण इसमें कोणीय त्वरण पैदा करता है। कोणीय त्वरण, आरोपित बल आघूर्ण के समानुपाती और पिण्ड के जड़त्व आघूर्ण के व्युत्क्रमानुपाती होता है। इस संदर्भ में समीकरण (6.41) को, एक अचल अक्ष के परितः घूर्णन के लिए लागू होने वाला न्यूटन का द्वितीय नियम, कह सकते हैं।

### 33. ch 7 §7.2 ¶7 — en p3, hi p3, similarity 0.64
- en: The law of areas can be understood as a consequence of conservation of angular momentum which is valid for any central force . A central force is such that the force on the planet is along the vector joining the Sun and the planet. Let the Sun be at the origin and let the position and momentum of the planet be denoted by r and p respectively. Then the area swept out by the planet of mass m in time interval Delta t is (Fig. 7.2) Delta A given by Delta A = ½ (r × v Delta t) (7.1) Hence Delta A / Delta t = ½ (r × p)/m, (since v = p/m) = L / (2 m) (7.2)
- hi: क्षेत्रफलों के नियम को कोणीय संवेग संरक्षण का निष्कर्ष माना जा सकता है जो सभी केन्द्रीय बलों के लिए मान्य है। किसी ग्रह पर लगने वाला केन्द्रीय बल, केन्द्रीय सूर्य तथा ग्रह को मिलाने वाले सदिश के अनुदिश कार्य करता है। मान लीजिए सूर्य मूल बिन्दु पर है और यह भी मानिए कि ग्रह की स्थिति तथा संवेग को क्रमशः r तथा p से दर्शाया जाता है, तब m द्रव्यमान के ग्रह द्वारा Delta t समय में प्रसर्पित क्षेत्रफल Delta A (चित्र 7.2) इस प्रकार व्यक्त किया जाता है Delta A = ½ (r × v Delta t) (7.1) अतः Delta A / Delta t = ½ (r × p)/m, (चूँकि v = p/m) = L / (2 m) (7.2)

### 34. ch 7 §7.6 ¶5 — en p7, hi p8, similarity 0.78
- en: Equation (7.15) thus tells us that for small heights h above the value of g decreases by a factor (1 - 2h / R_E).
- hi: इस प्रकार समीकरण (7.15) से हमें प्राप्त होता है कि कम ऊँचाई h के लिए g का मान गुणक (1 – 2h / R_E) द्वारा घटता है।

### 35. ch 7 §7.7 ¶5 — en p8, hi p9, similarity 0.69 — the founder reads this one
- en: If we consider points at arbitrary distance from the surface of the earth, the result just derived is not valid since the assumption that the gravitational force mg is a constant is no longer valid. However, from our discussion we know that a point outside the earth, the force of gravitation on a particle directed towards the centre of the earth is F = G M_E m / r^2 (7.23)
- hi: यदि हम पृथ्वी के पृष्ठ से यादृच्छिक दूरियों के बिन्दुओं पर विचार करें तो उपरोक्त परिणाम प्रामाणिक नहीं होते क्योंकि तब यह मान्यता कि गुरुत्वाकर्षण बल mg अपरिवर्तित रहता है वैध नहीं है। तथापि, अपनी अब तक की चर्चा के आधार पर हम जानते हैं कि पृथ्वी के बाहर के किसी बिन्दु पर स्थित किसी कण पर लगे गुरुत्वीय बल की दिशा पृथ्वी के केन्द्र की ओर निदेशित होती है तथा इस बल का परिमाण है, F = G M_E m / r^2 (7.23)

### 36. ch 7 §7.8 ¶9 — en p10, hi p11, similarity 0.67
- en: Using the value of g and R_E, numerically (V_i)_min ≈ 11.2 km/s. This is called the escape speed, sometimes loosely called the escape velocity.
- hi: समीकरण (7.32) में g और R_E के आंकिक मान रखने पर हमें (V_i)_न्यून ≈ 11.2 km/s प्राप्त होता है। उसे पलायन चाल कहते हैं। कभी-कभी लापरवाही में इसे हम पलायन वेग भी कह देते हैं।

### 37. ch 7 §7.9 ¶4 — en p11, hi p12, similarity 0.82
- en: Thus V decreases as h increases. From equation (7.35),the speed V for h = 0 is V^2 (h = 0) = GM / R_E = gR_E (7.36)
- hi: इस प्रकार h के बढ़ने पर V घटता है। समीकरण (7.35) के अनुसार जब h = 0 है, तो उपग्रह की चाल V है V^2 (h = 0) = GM_E / R_E = gR_E (7.36)

### 38. ch 7 §7.9 ¶11 — en p12, hi p13, similarity 0.63
- en: We note that the orbits of all planets except Mercury and Mars are very close to being circular. For example, the ratio of the semi-minor to semi-major axis for our Earth is, b/a = 0.99986.
- hi: ध्यान देने योग्य तथ्य यह है कि बुध, मंगल तथा प्लूटो के अतिरिक्त सभी ग्रहों की कक्षाएं लगभग वृत्ताकार हैं। उदाहरण के लिए, हमारी पृथ्वी के अर्ध लघु अक्ष तथा अर्ध दीर्घ अक्ष का अनुपात b/a = 0.99986 है।

### 39. ch 7 §7.9 ¶12 — en p12, hi p13, similarity 0.70
- en: Example 7.6 Weighing the Earth : You are given the following data: g = 9.81 ms^-2, R_E = 6.37×10^6 m, the distance to the moon R = 3.84×10^8 m and the time period of the moon's revolution is 27.3 days. Obtain the mass of the Earth M_E in two different ways.
- hi: उत्तर 7.6 पृथ्वी को तोलना : आपको निम्नलिखित आंकड़े दिए गए हैं: g = 9.81 m s^-2, R_E = 6.37×10^6 m, पृथ्वी से चन्द्रमा की दूरी R = 3.84×10^8 m पृथ्वी के परितः चन्द्रमा के परिक्रमण का आवर्त काल = 27.3 दिन। दो भिन्न विधियों द्वारा पृथ्वी का द्रव्यमान प्राप्त कीजिए।

### 40. ch 7 §7.10 ¶6 — en p13, hi p14, similarity 0.68 — the founder reads this one
- en: Example 7.8 A 400 kg satellite is in a circular orbit of radius 2R_E about the Earth. How much energy is required to transfer it to a circular orbit of radius 4R_E? What are the changes in the kinetic and potential energies ?
- hi: उदाहरण 7.8 400 kg द्रव्यमान का कोई उपग्रह पृथ्वी के परित 2R_E त्रिज्या की वृत्तीय कक्षा में परिक्रमण कर रहा है। इसे 4R_E की वृत्तीय कक्षा में स्थानांतरित करने के लिए आवश्यक ऊर्जा परिकलित कीजिए। इसकी गतिज तथा स्थितिज ऊर्जा में कितने परिवर्तन होंगे?

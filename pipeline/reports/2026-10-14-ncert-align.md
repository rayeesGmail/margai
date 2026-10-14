# margai-pipeline ncert align

- run: 2026-10-14 06:36 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1042 English and 1041 Hindi row(s) in 8 chapter(s)
- result: ok
request id: pipeline-ncert-align-257bca11-f74e-4e00-8ef0-7d02528423dc
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 1041 | 320324 | ₹10.71 |

## sections whose rows do not pair one to one

en counts the English rows aligned: a number an `unprinted` entry left is listed below, not here
| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 1 | 1.7.1 | 4 | 4 | 2 |
| 1 | 1.10.1 | 10 | 10 | 2 |
| 2 | 2.4 | 15 | 15 | 2 |
| 2 | 2.7 | 17 | 17 | 2 |
| 2 | 2.15 | 13 | 13 | 2 |
| 4 | 4.8 | 21 | 21 | 2 |
| 4 | 4.9.2 | 8 | 8 | 2 |

## English paragraphs the Hindi does not print — numbers an `unprinted` entry left, not aligned

- ch 3 §3.12 ¶5

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 1 §1.7.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 1 §1.7.1: hi ¶3+¶4 = en ¶4 (join hi)
- ch 1 §1.10.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 1 §1.10.1: hi ¶3+¶4 = en ¶4 (join hi)
- ch 2 §2.4: hi ¶3+¶4 = en ¶3 (join hi)
- ch 2 §2.4: hi ¶5 = en ¶4+¶5 (split hi)
- ch 2 §2.7: hi ¶12+¶13 = en ¶12 (join hi)
- ch 2 §2.7: hi ¶14 = en ¶13+¶14 (split hi)
- ch 2 §2.15: hi ¶4 = en ¶4+¶5 (split hi)
- ch 2 §2.15: hi ¶5+¶6 = en ¶6 (join hi)
- ch 4 §4.8: hi ¶6+¶7 = en ¶6 (join hi)
- ch 4 §4.8: hi ¶8 = en ¶7+¶8 (split hi)
- ch 4 §4.9.2: hi ¶3+¶4 = en ¶3 (join hi)
- ch 4 §4.9.2: hi ¶5 = en ¶4+¶5 (split hi)

## pairs weaker than 0.45 — read both against the pages

- ch 1 §1.2: hi ¶6 = en ¶6 — similarity 0.44 — en «A simple apparatus to detect charge on a…» · hi «आवेशों की उपस्थिति के संसूचन के लिए एक…»
- ch 1 §1.4.3: hi ¶12 = en ¶12 — similarity 0.44 — en «Example 1.2 How much positive and negative charge…» · hi «उदाहरण 1.2 एक कप जल में कितने धन…»
- ch 1 §1.7: hi ¶9 = en ¶9 — similarity 0.44 — en «(iii) For a positive charge, the electric field…» · hi «(iii) धनावेश के कारण विद्युत क्षेत्र आवेश से…»
- ch 1 §1.12: hi ¶5 = en ¶5 — similarity 0.43 — en «* At the microscopic level, charge distribution is…» · hi «* सूक्ष्मदर्शीय स्तर पर, आवेश वितरण असंतत होता…»
- ch 2 §2.1: hi ¶11 = en ¶11 — similarity 0.44 — en «Two important comments may be made at this…» · hi «इस घटनाक्रम में दो महत्वपूर्ण टिप्पणियाँ की जा…»
- ch 2 §2.5: hi ¶10 = en ¶10 — similarity 0.39 — en «Example 2.3 Figures 2.8 (a) and (b) show…» · hi «उदाहरण 2.3 (a) तथा (b) में क्रमशः एकल…»
- ch 2 §2.6: hi ¶4 = en ¶4 — similarity 0.34 — en «If the field were not normal to the…» · hi «यदि विद्युत क्षेत्र समविभव पृष्ठ के अभिलंबवत नहीं…»
- ch 2 §2.9: hi ¶3 = en ¶3 — similarity 0.43 — en «2. At the surface of a charged conductor,…» · hi «2. आवेशित चालक के पृष्ठ पर, पृष्ठ के…»
- ch 2 §2.9: hi ¶6 = en ¶6 — similarity 0.34 — en «In a system of conductors of arbitrary size,…» · hi «किसी यादृच्छिक आकार, आकृति तथा आवेश विन्यास के…»
- ch 2 §2.9: hi ¶15 = en ¶15 — similarity 0.45 — en «(c) Vehicles carrying inflammable materials usually have metallic…» · hi «(c) जो वाहन ज्वलनशील पदार्थ ले जाते हैं…»
- ch 2 §2.10: hi ¶1 = en ¶1 — similarity 0.42 — en «Dielectrics are non-conducting substances. In contrast to conductors,…» · hi «परावैद्युत अचालक पदार्थ होते हैं। चालकों की तुलना…»
- ch 2 §2.10: hi ¶3 = en ¶3 — similarity 0.44 — en «In an external electric field, the positive and…» · hi «किसी बाह्य विद्युत क्षेत्र में अध्रुवी अणु के…»
- ch 2 §2.10: hi ¶4 = en ¶4 — similarity 0.40 — en «A dielectric with polar molecules also develops a…» · hi «ध्रुवी अणुओं का कोई परावैद्युत किसी बाह्य क्षेत्र…»
- ch 2 §2.10: hi ¶7 = en ¶7 — similarity 0.36 — en «The question is: how does the polarised dielectric…» · hi «अब प्रश्न यह है कि कोई ध्रुवित परावैद्युत…»
- ch 2 §2.10: hi ¶8 = en ¶8 — similarity 0.42 — en «Thus, the polarised dielectric is equivalent to two…» · hi «अतः ध्रुवित परावैद्युत दो आवेशित पृष्ठों के तुल्य…»
- ch 2 §2.13: hi ¶7 = en ¶7 — similarity 0.44 — en «Thus, the dielectric constant of a substance is…» · hi «इस प्रकार किसी पदार्थ का परावैद्युतांक एक कारक…»
- ch 3 §3.12: hi ¶3 = en ¶3 — similarity 0.45 — en «Having clarified labelling, we now state the rules…» · hi «चिह्नों को बनाने कि प्रक्रिया को स्पष्ट करने…»
- ch 3 §3.12: hi ¶10 = en ¶10 — similarity 0.38 — en «Solution The network is not reducible to a…» · hi «हल परिपथ जाल को प्रतिरोधकों के सरल श्रेणी…»
- ch 4 §4.6: hi ¶5 = en ¶5 — similarity 0.26 — en «The above result for the infinite wire is…» · hi «उपरोक्त परिणाम अनंत लंबाई के तार के लिए…»
- ch 4 §4.6: hi ¶7 = en ¶7 — similarity 0.44 — en «(ii) The field direction at any point on…» · hi «(ii) इस वृत्त के किसी भी बिंदु पर…»
- ch 4 §4.6: hi ¶16 = en ¶16 — similarity 0.39 — en «This example possesses the required symmetry so that…» · hi «इस उदाहरण में आवश्यक सममिति विद्यमान है इसलिए…»
- ch 4 §4.7: hi ¶1 = en ¶1 — similarity 0.44 — en «We shall discuss a long solenoid. By long…» · hi «हम यहाँ एक लंबी परिनालिका के विषय में…»
- ch 4 §4.9.1: hi ¶20 = en ¶20 — similarity 0.44 — en «(b) A current-carrying circular loop is located in…» · hi «(b) कोई विद्युत वाही वृत्ताकार पाश किसी एकसमान…»
- ch 4 §4.9.1: hi ¶21 = en ¶21 — similarity 0.44 — en «(c) A loop of irregular shape carrying current…» · hi «(c) अनियमित आकृति का कोई विद्युत धारावाही पाश…»
- ch 4 §4.9.2: hi ¶7 = en ¶7 — similarity 0.43 — en «The results obtained above can be shown to…» · hi «उपरोक्त परिणाम किसी भी समतल पाश पर लागू…»
- ch 5 §5.2.1: hi ¶1 = en ¶1 — similarity 0.42 — en «The pattern of iron filings permits us to…» · hi «लौह रेतन के बने पैटर्नों के आधार पर…»
- ch 5 §5.2.1: hi ¶4 = en ¶4 — similarity 0.44 — en «(ii) The tangent to the field line at…» · hi «(ii) क्षेत्र रेखा के किसी बिंदु पर खींची…»
- ch 5 §5.2.2: hi ¶2 = en ¶2 — similarity 0.42 — en «The resemblance of magnetic field lines for a…» · hi «एक छड़ चुंबक की चुंबकीय क्षेत्र रेखाओं की,…»
- ch 5 §5.2.2: hi ¶3 = en ¶3 — similarity 0.39 — en «To make this analogy more firm we may…» · hi «इस साम्यता को और अधिक सुदृढ़ करने के…»
- ch 5 §5.2.2: hi ¶5 = en ¶5 — similarity 0.32 — en «This is also the far axial magnetic field…» · hi «यही समीकरण छड़ चुंबक की अक्ष पर दूर…»
- ch 5 §5.2.3: hi ¶1 = en ¶1 — similarity 0.43 — en «Let's place a small compass needle of known…» · hi «हम एक पतली चुंबकीय सुई का, जिसका चुंबकीय…»
- ch 5 §5.2.3: hi ¶3 = en ¶3 — similarity 0.45 — en «An expression for magnetic potential energy can be…» · hi «चुंबकीय स्थितिज ऊर्जा के लिए व्यंजक प्राप्त करने…»
- ch 5 §5.2.3: hi ¶10 = en ¶10 — similarity 0.41 — en «(b) No force if the field is uniform.…» · hi «(b) यदि क्षेत्र एकसमान हों केवल तभी चुंबक…»
- ch 5 §5.2.4: hi ¶5 = en ¶5 — similarity 0.36 — en «(a) In which configuration the system is not…» · hi «(a) किस विन्यास में यह निकाय संतुलन में…»
- ch 5 §5.3: hi ¶5 = en ¶5 — similarity 0.31 — en «Thus, Gauss's law for magnetism is:» · hi «चुंबकत्व के लिए गाउस का नियम है–»
- ch 5 §5.3: hi ¶18 = en ¶18 — similarity 0.44 — en «(b) If magnetic monopoles existed, how would the…» · hi «(b) यदि चुंबकीय एकल ध्रुवों का अस्तित्व होता…»
- ch 5 §5.3: hi ¶19 = en ¶19 — similarity 0.43 — en «(c) Does a bar magnet exert a torque…» · hi «(c) क्या कोई छड़ चुंबक अपने क्षेत्र की…»
- ch 5 §5.3: hi ¶20 = en ¶20 — similarity 0.45 — en «(d) Magnetic field arises due to charges in…» · hi «(d) गतिमान आवेशों के कारण चुंबकीय क्षेत्र उत्पन्न…»
- ch 6 §6.4: hi ¶2 = en ¶2 — similarity 0.44 — en «The motion of a magnet towards or away…» · hi «प्रयोग 6.1 में कुंडली C_1 की ओर अथवा…»
- ch 6 §6.7.1: hi ¶12 = en ¶12 — similarity 0.42 — en «It is also important to know that the…» · hi «यह जानना भी महत्वपूर्ण है कि कुंडलियों, परिनालिकाओं…»
- ch 7 §7.4: hi ¶6 = en ¶6 — similarity 0.45 — en «The dimension of inductive reactance is the same…» · hi «प्रेरकीय प्रतिघात की विमाएँ वही हैं तो प्रतिरोध…»
- ch 7 §7.4: hi ¶10 = en ¶10 — similarity 0.42 — en «Thus, the average power supplied to an inductor…» · hi «इसलिए एक पूरे चक्र में किसी प्रेरक को…»
- ch 7 §7.7: hi ¶24 = en ¶24 — similarity 0.43 — en «You can see that in the present case,…» · hi «प्रस्तुत प्रकरण में आप देख सकते हैं कि…»

## numbers one edition of a pair carries and the other does not

- ch 1 §1.4.3 ¶3: only in en — · only in hi 1, 11
- ch 1 §1.4.3 ¶10: only in en — · only in hi 1, 1, 1
- ch 1 §1.4.3 ¶11: only in en — · only in hi 1
- ch 1 §1.5 ¶5: only in en — · only in hi 1
- ch 1 §1.5 ¶15: only in en 12 · only in hi —
- ch 1 §1.6 ¶14: only in en 2 · only in hi —
- ch 1 §1.7.2 ¶8: only in en — · only in hi 1
- ch 1 §1.8 ¶9: only in en 3 · only in hi —
- ch 1 §1.10.1 ¶5: only in en 0, 0 · only in hi —
- ch 1 §1.13 ¶3: only in en 0, 0, 0, 0 · only in hi —
- ch 1 §1.14.3 ¶8: only in en — · only in hi 11
- ch 2 §2.1 ¶1: only in en — · only in hi 11
- ch 2 §2.1 ¶12: only in en — · only in hi 2.2
- ch 2 §2.3 ¶2: only in en 0, 0 · only in hi —
- ch 2 §2.3 ¶3: only in en 0, 0, 0 · only in hi —
- ch 2 §2.3 ¶4: only in en 0 · only in hi —
- ch 2 §2.3 ¶9: only in en 0 · only in hi —
- ch 2 §2.4 ¶2: only in en 0 · only in hi —
- ch 2 §2.4 ¶7: only in en 0, 0 · only in hi —
- ch 2 §2.4 ¶9: only in en 0 · only in hi —
- ch 2 §2.4 ¶11: only in en 0 · only in hi —
- ch 2 §2.4 ¶15: only in en 2.5 · only in hi 2.4
- ch 2 §2.5 ¶1: only in en 1, 0 · only in hi —
- ch 2 §2.5 ¶3: only in en 0, 0 · only in hi —
- ch 2 §2.5 ¶4: only in en 0 · only in hi —
- ch 2 §2.5 ¶6: only in en 0, 0 · only in hi —
- ch 2 §2.5 ¶10: only in en 2.8 · only in hi —
- ch 2 §2.7 ¶1: only in en 0, 0 · only in hi 1
- ch 2 §2.7 ¶2: only in en 0 · only in hi —
- ch 2 §2.7 ¶4: only in en 0 · only in hi —
- ch 2 §2.7 ¶5: only in en 0 · only in hi —
- ch 2 §2.7 ¶6: only in en 0 · only in hi —
- ch 2 §2.7 ¶7: only in en 0 · only in hi —
- ch 2 §2.8.1 ¶4: only in en 1, 117 · only in hi 11, 119
- ch 2 §2.8.2 ¶5: only in en 0 · only in hi —
- ch 2 §2.8.3 ¶5: only in en — · only in hi 2.34, 2.32
- ch 2 §2.8.3 ¶9: only in en 1 · only in hi —
- ch 2 §2.9 ¶9: only in en 0, 0 · only in hi —
- ch 2 §2.9 ¶10: only in en — · only in hi 2.35
- ch 2 §2.9 ¶11: only in en 6 · only in hi —
- ch 2 §2.10 ¶7: only in en 0 · only in hi —
- ch 2 §2.11 ¶2: only in en 1 · only in hi —
- ch 2 §2.11 ¶4: only in en — · only in hi 1
- ch 2 §2.13 ¶1: only in en 2.10, 0 · only in hi —
- ch 2 §2.13 ¶2: only in en 2.10 · only in hi —
- ch 2 §2.13 ¶6: only in en 2, 51 · only in hi 1, 2.51
- ch 2 §2.14.1 ¶2: only in en — · only in hi 1, 2
- ch 2 §2.15 ¶8: only in en 2.31 · only in hi —
- ch 3 §3.4 ¶2: only in en 3.2 · only in hi —
- ch 3 §3.4 ¶6: only in en — · only in hi 3.9
- ch 3 §3.4 ¶7: only in en — · only in hi 3.10
- ch 3 §3.4 ¶8: only in en — · only in hi 3.11
- ch 3 §3.5 ¶2: only in en 3.3 · only in hi —
- ch 3 §3.5 ¶9: only in en — · only in hi 2
- ch 3 §3.5 ¶12: only in en — · only in hi 1
- ch 3 §3.5 ¶15: only in en — · only in hi 11
- ch 3 §3.8 ¶2: only in en 3.8 · only in hi —
- ch 3 §3.8 ¶14: only in en 0, 0, 0 · only in hi —
- ch 3 §3.10 ¶1: only in en — · only in hi 3.12
- ch 3 §3.11 ¶5: only in en — · only in hi 3.44, 3.43
- ch 3 §3.11 ¶11: only in en 3.14 · only in hi —
- ch 3 §3.11 ¶17: only in en — · only in hi 3.52, 3.53
- ch 3 §3.12 ¶12: only in en 3 · only in hi —
- ch 3 §3.12 ¶15: only in en 3.61 · only in hi —
- ch 3 §3.13 ¶3: only in en — · only in hi 3.64
- ch 4 §4.2.1 ¶4: only in en 1 · only in hi —
- ch 4 §4.2.2 ¶6: only in en — · only in hi 1
- ch 4 §4.2.2 ¶7: only in en 10, 3.6, 5 · only in hi —
- ch 4 §4.2.3 ¶5: only in en 9.8 · only in hi 9.81
- ch 4 §4.3 ¶1: only in en — · only in hi 11
- ch 4 §4.3 ¶4: only in en — · only in hi 11
- ch 4 §4.3 ¶5: only in en 4.6 · only in hi —
- ch 4 §4.4 ¶3: only in en — · only in hi 0, 4
- ch 4 §4.4 ¶10: only in en 0, 0 · only in hi —
- ch 4 §4.6 ¶1: only in en 4.12 · only in hi —
- ch 4 §4.6 ¶14: only in en — · only in hi 1, 2
- ch 4 §4.7 ¶2: only in en 4.15 · only in hi —
- ch 4 §4.8 ¶12: only in en — · only in hi 1
- ch 4 §4.8 ¶15: only in en 1 · only in hi —
- ch 4 §4.9.1 ¶4: only in en 2 · only in hi —
- ch 4 §4.9.1 ¶15: only in en — · only in hi 2, 10
- ch 4 §4.10 ¶12: only in en 4.23 · only in hi —
- ch 5 §5.2.1 ¶1: only in en 1 · only in hi —
- ch 5 §5.2.1 ¶2: only in en — · only in hi 5.2
- ch 5 §5.2.3 ¶5: only in en 2 · only in hi —
- ch 5 §5.3 ¶7: only in en 5.6 · only in hi —
- ch 5 §5.4 ¶8: only in en — · only in hi 1
- ch 5 §5.5.3 ¶2: only in en 5.8 · only in hi 5.7
- ch 6 §6.4 ¶2: only in en — · only in hi 1, 2
- ch 6 §6.6 ¶6: only in en 1 · only in hi —
- ch 6 §6.8 ¶7: only in en — · only in hi 7
- ch 6 §6.8 ¶8: only in en 5 · only in hi 50
- ch 7 §7.2 ¶5: only in en 7.4 · only in hi 7, 4
- ch 7 §7.4 ¶5: only in en 7.14 · only in hi 7, 14
- ch 7 §7.5 ¶16: only in en 7.9 · only in hi —
- ch 7 §7.6.2 ¶3: only in en 7.16 · only in hi 7.14
- ch 7 §7.6.2 ¶10: only in en — · only in hi 90
- ch 7 §7.7 ¶12: only in en 7.15 · only in hi —
- ch 8 §8.3.2 ¶3: only in en — · only in hi 11
- ch 8 §8.3.2 ¶11: only in en 8.10 · only in hi —

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.4.3 ¶2 — en p5, hi p5, similarity 0.52 — the founder reads this one
- en: The fact that electric charge is always an integral multiple of e is termed as quantisation of charge. There are a large number of situations in physics where certain physical quantities are quantised. The quantisation of charge was first suggested by the experimental laws of electrolysis discovered by English experimentalist Faraday. It was experimentally demonstrated by Millikan in 1912.
- hi: वैद्युत आवेश सदैव e का पूर्णांक गुणज होता है। इस तथ्य को आवेश का क्वांटमीकरण कहते हैं। भौतिकी में ऐसी बहुत सी अवस्थितियाँ हैं जहाँ कुछ भौतिक राशियाँ क्वांटीकृत हैं। आवेश के क्वांटमीकरण का सुझाव सर्वप्रथम अंग्रेज़ प्रयोगकर्ता फैराडे द्वारा खोजे गए विद्युत अपघटन के प्रायोगिक नियमों से प्राप्त हुआ था। सन् 1912 में मिलिकन ने इसे वास्तव में प्रायोगिक रूप से निदर्शित किया था।

### 2. ch 1 §1.4.3 ¶7 — en p5–6, hi p5, similarity 0.53
- en: This situation can be compared with the geometrical concepts of points and lines. A dotted line viewed from a distance appears continuous to us but is not continuous in reality. As many points very close to each other normally give an impression of a continuous line, many small charges taken together appear as a continuous charge distribution.
- hi: इस स्थिति की तुलना बिंदु तथा रेखा की ज्यामितीय परिकल्पनाओं से की जा सकती है। दूर से देखने पर कोई बिंदुकित रेखा हमें सतत प्रतीत होती है परंतु वह वास्तव में सतत नहीं होती। जिस प्रकार एक-दूसरे के अत्यधिक निकट के बहुत से बिंदु हमें सतत रेखा का आभास देते हैं, उसी प्रकार एक साथ लेने पर बहुत से छोटे आवेशों का संकलन भी सतत आवेश वितरण जैसा दिखाई देता है।

### 3. ch 1 §1.4.3 ¶8 — en p6, hi p6, similarity 0.53
- en: At the macroscopic level, one deals with charges that are enormous compared to the magnitude of charge e. Since e = 1.6 × 10^-19 C, a charge of magnituOde, say 1 µC, contains something like 10^13 times the electronic charge. At this scale, the fact that charge can increase or decrease only in units of e is not very different from saying that charge can take continuous values. Thus, at the macroscopic level, the quantisation of charge has no practical consequence and can be ignored. However, at the microscopic level, where the charges involved are of the order of a few tens or hundreds of e, i.e., they can be counted, they appear in discrete lumps and quantisation of charge cannot be ignored. It is the magnitude of scale involved that is very important.
- hi: स्थूल स्तर पर हम ऐसे आवेशों से व्यवहार करते हैं जो इलेक्ट्रॉन e के आवेश की तुलना में परिमाण में अत्यधिक विशाल होते हैं। चूँकि e = 1.6 × 10^(-19) C, परिमाण में 1 mu C आवेश में एक इलेक्ट्रॉन के आवेश का लगभग 10^13 गुना आवेश होता है। इस पैमाने पर, यह तथ्य कि किसी वस्तु में आवेश की कमी अथवा वृद्धि केवल e के मात्रकों में ही हो सकती है, इस कथन से सर्वथा भिन्न नहीं है कि आवेश सतत मान ग्रहण कर सकता है। इस प्रकार, स्थूल स्तर पर आवेश के क्वांटीकरण का कोई व्यावहारिक महत्त्व नहीं है तथा इसकी उपेक्षा की जा सकती है। सूक्ष्म स्तर पर जहाँ आवेश के परिमाण e के कुछ दशक अथवा कुछ शतक कोटि के होते हैं अर्थात जिनकी गणना की जा सकती है, वहाँ पर आवेश विविक्त प्रतीत होते हैं तथा आवेश के क्वांटमीकरण की उपेक्षा नहीं की जा सकती। अतः यह जानना बहुत महत्वपूर्ण है कि किस परिमाण के आवेश की बात हो रही है।

### 4. ch 1 §1.5 ¶1 — en p6–7, hi p6, similarity 0.60
- en: Coulomb's law is a quantitative statement about the force between two point charges. When the linear size of charged bodies are much smaller than the distance separating them, the size may be ignored and the charged bodies are treated as point charges. Coulomb measured the force between two point charges and found that it varied inversely as the square of the distance between the charges and was directly proportional to the product of the magnitude of the two charges and acted along the line joining the two charges. Thus, if two point charges q_1, q_2 are separated by a distance r in vacuum, the magnitude of the force (F) between them is given by F = k |q_1 q_2| / r^2 (1.1)
- hi: कूलॉम नियम दो बिंदु आवेशों के बीच लगे बल के विषय में एक मात्रात्मक प्रकथन है। जब आवेशित वस्तुओं के साइज़ उनको पृथक करने वाली दूरी की तुलना में बहुत कम होते हैं तो ऐसी आवेशित वस्तुओं के साइज़ों की उपेक्षा की जा सकती है और उन्हें बिंदु आवेश माना जा सकता है। कूलॉम ने दो बिंदु आवेशों के बीच लगे बल की माप की और यह पाया कि यह बल दोनों आवेशों के परिमाणों के गुणनफल के अनुक्रमानुपाती तथा उनके बीच की दूरी के वर्ग के व्युत्क्रमानुपाती है तथा यह दोनों आवेशों को मिलाने वाली रेखा के अनुदिश कार्य करता है। इस प्रकार यदि दो बिंदु आवेशों q_1 तथा q_2 के बीच निर्वात में पृथकन r है, तो इनके बीच लगे बल (F) का परिमाण है F = k |q_1 q_2| / r^2 (1.1)

### 5. ch 1 §1.5 ¶6 — en p7, hi p7, similarity 0.54 — the founder reads this one
- en: * A torsion balance is a sensitive device to measure force. It was also used later by Cavendish to measure the very feeble gravitational force between two objects, to verify Newton’s Law of Gravitation.
- hi: * ऐंठन तुला बल मापने की एक सुग्राही युक्ति है। इस तुला का उपयोग बाद में कैवेंडिश ने दो पिंडों के बीच लगे गुरुत्वाकर्षण बल की माप के लिए भी करके न्यूटन के गुरुत्वाकर्षण नियम को सत्यापित किया।

### 6. ch 1 §1.5 ¶10 — en p8, hi p8, similarity 0.52
- en: Since force is a vector, it is better to write Coulomb's law in the vector notation. Let the position vectors of charges q_1 and q_2 be r_1 and r_2 respectively [see Fig.1.3(a)]. We denote force on q_1 due to q_2 by F_12 and force on q_2 due to q_1 by F_21. The two point charges q_1 and q_2 have been numbered 1 and 2 for convenience and the vector leading from 1 to 2 is denoted by r_21: r_21 = r_2 – r_1
- hi: बल एक सदिश है, अतः कूलॉम नियम को सदिश संकेतन में लिखा उत्तम होता है। मान लीजिए q_1 तथा q_2 आवेशों के स्थिति सदिश क्रमशः r_1 तथा r_2 हैं [चित्र 1.3(a) देखिए]। हम q_2 के द्वारा q_1 पर आरोपित बल को F_12 तथा q_1 के द्वारा q_2 पर आरोपित बल को F_21 द्वारा व्यक्त करते हैं। दो बिंदु आवेशों q_1 तथा q_2 को सुविधा के लिए 1 तथा 2 अंक द्वारा व्यक्त किया गया है। साथ ही 1 से 2 की ओर जाते सदिश को r_21 द्वारा व्यक्त किया गया है– r_21 = r_2 – r_1

### 7. ch 1 §1.7 ¶6 — en p14, hi p14, similarity 0.56
- en: * An alternate unit V/m will be introduced in the next chapter.
- hi: * अगले अध्याय में विद्युत क्षेत्र के लिए एक अन्य वैकल्पिक मात्रक V/m पर विचार किया जाएगा।

### 8. ch 1 §1.8 ¶3 — en p20, hi p20, similarity 0.55 — the founder reads this one
- en: We draw the figure on the plane of paper, i.e., in two-dimensions but we live in three-dimensions. So if one wishes to estimate the density of field lines, one has to consider the number of lines per unit cross-sectional area, perpendicular to the lines. Since the electric field decreases as the square of the distance from a point charge and the area enclosing the charge increases as the square of the distance, the number of field lines crossing the enclosing area remains constant, whatever may be the distance of the area from the charge.
- hi: हम कागज़ के पृष्ठ पर चित्र खींचते हैं अर्थात हम द्विविमीय चित्र खींचते हैं, परंतु हम तीन विमाओं में रहते हैं। अतः यदि हमें क्षेत्र रेखाओं के घनत्व का आकलन करना है तो हमें इन रेखाओं के लंबवत अनुप्रस्थ काट के प्रति एकांक क्षेत्रफल में क्षेत्र रेखाओं की संख्या पर विचार करना होता है। चूँकि किसी बिंदु आवेश से दूरी के वर्ग के अनुसार विद्युत क्षेत्र कम होता जाता है तथा आवेश को परिबद्ध करने वाला क्षेत्र दूरी के वर्ग के अनुसार बढ़ता जाता है, परिबद्ध क्षेत्र से गुजरने वाली क्षेत्र रेखाओं की संख्या सदैव नियत रहती है, चाहे आवेश से उस क्षेत्र की दूरी कुछ भी हो।

### 9. ch 1 §1.8 ¶8 — en p20, hi p20, similarity 0.61
- en: * Solid angle is a measure of a cone. Consider the intersection of the given cone with a sphere of radius R. The solid angle Delta Omega of the cone is defined to be equal to Delta S/R^2, where Delta S is the area on the sphere cut out by the cone.
- hi: * घन कोण शंकु की एक माप है। R त्रिज्या के गोले वाले दिए गए शंकु के परिच्छेद पर विचार कीजिए। शंकु के घन कोण Delta Omega की परिभाषा इसे Delta S/R^2 के बराबर मानकर करते हैं, यहाँ Delta S शंकु द्वारा गोले पर काटा गया क्षेत्रफल है।

### 10. ch 1 §1.9 ¶5 — en p22, hi p22, similarity 0.48
- en: Notice one ambiguity here. The direction of an area element is along its normal. But a normal can point in two directions. Which direction do we choose as the direction of the vector associated with the area element? This problem is resolved by some convention appropriate to the given context. For the case of a closed surface, this convention is very simple. The vector associated with every area element of a closed surface is taken to be in the direction of the outward normal. This is the convention used in Fig. 1.16. Thus, the area element vector Delta S at a point on a closed surface equals Delta S n_hat where Delta S is the magnitude of the area element and n_hat is a unit vector in the direction of outward normal at that point.
- hi: यहाँ एक संदिग्धता पर ध्यान दीजिए। किसी क्षेत्रफल अवयव की दिशा उसके अभिलंब के अनुदिश होती है। परंतु अभिलंब दो दिशाएँ संकेत कर सकता है। किसी क्षेत्रफल अवयव से संबद्ध सदिश की दिशा का चयन किस प्रकार किया जाता है? इस समस्या का समाधान इस संदर्भ में उचित कुछ परिपाटियों के निर्धारण द्वारा किया गया है। बंद पृष्ठों के प्रकरणों के लिए यह परिपाटी अति सरल है। किसी बंद पृष्ठ के प्रत्येक क्षेत्रफल अवयव से संबद्ध सदिश की दिशा बहिर्मुखी अभिलंब की दिशा मानी जाती है। इसी परिपाटी का उपयोग चित्र 1.16 में किया गया है। इस प्रकार, किसी बंद पृष्ठ के किसी बिंदु पर क्षेत्रफल अवयव सदिश Delta S का मान Delta S n_hat होता है, यहाँ Delta S क्षेत्रफल सदिश का परिमाण तथा n_hat इस बिंदु पर बहिर्मुखी अभिलंब की दिशा में एकांक सदिश है।

### 11. ch 1 §1.11 ¶6 — en p27, hi p27, similarity 0.55 — the founder reads this one
- en: This torque will tend to align the dipole with the field E. When p is aligned with E, the torque is zero.
- hi: यह बल आघूर्ण द्विध्रुव को क्षेत्र E के साथ संरेखित करने की प्रवृत्ति रखेगा। जब p क्षेत्र E के साथ संरेखित हो जाता है तो बल आघूर्ण शून्य होता है।

### 12. ch 1 §1.12 ¶6 — en p29, hi p29, similarity 0.49
- en: The field due to a continuous charge distribution can be obtained in much the same way as for a system of discrete charges, Eq. (1.10). Suppose a continuous charge distribution in space has a charge density rho. Choose any convenient origin O and let the position vector of any point in the charge distribution be r. The charge density rho may vary from point to point, i.e., it is a function of r. Divide the charge distribution into small volume elements of size Delta V. The charge in a volume element Delta V is rho Delta V.
- hi: विविक्त आवेशों के निकाय के कारण विद्युत क्षेत्र प्राप्त करने [समीकरण (1.10)] की ही भाँति लगभग इसी ढंग से संतत आवेश वितरण के कारण विद्युत क्षेत्र प्राप्त किया जा सकता है। मान लीजिए किसी दिक्स्थान में संतत आवेश वितरण का आवेश घनत्व rho है। कोई सुविधाजनक मूल बिंदु O चुनिए तथा मान लीजिए आवेश वितरण में किसी बिंदु का स्थिति सदिश r है। आवेश घनत्व rho एक बिंदु से दूसरे बिंदु पर भिन्न हो सकता है, अर्थात यह r का फलन है। आवेश वितरण को Delta V आमाप के छोटे आयतन अवयवों में विभाजित कीजिए। आयतन अवयव Delta V में आवेश का परिमाण rho Delta V है।

### 13. ch 1 §1.13 ¶5 — en p30, hi p30, similarity 0.61
- en: We state Gauss's law without proof: Electric flux through a closed surface S = q/epsilon_0 (1.31) q = total charge enclosed by S.
- hi: हम बिना उपपत्ति के गाउस नियम का इस प्रकार उल्लेख करते हैं- किसी बंद पृष्ठ S से गुजरने वाला वैद्युत फ्लक्स = q/epsilon_0 (1.31) यहाँ q पृष्ठ S द्वारा परिबद्ध कुल आवेश है।

### 14. ch 1 §1.14.3 ¶4 — en p35–36, hi p35–36, similarity 0.63
- en: (ii) Field inside the shell: In Fig. 1.28(b), the point P is inside the shell. The Gaussian surface is again a sphere through P centred at O. The flux through the Gaussian surface, calculated as before, is E × 4 pi r^2. However, in this case, the Gaussian surface encloses no charge. Gauss's law then gives E × 4 pi r^2 = 0 i.e., E = 0 (r < R ) (1.35) that is, the field due to a uniformly charged thin shell is zero at all points inside the shell*. This important result is a direct consequence of Gauss's law which follows from Coulomb's law. The experimental verification of this result confirms the 1/r^2 dependence in Coulomb's law.
- hi: (ii) खोल के भीतर विद्युत क्षेत्र–चित्र 1.28(b) में बिंदु P खोल के भीतर है। इस प्रकरण में भी गाउसीय पृष्ठ P से गुज़रने वाला वह गोला है जिसका केंद्र O है। पहले किए गए परिकलनों की ही भाँति गाउसीय पृष्ठ से गुजरने वाला फ्लक्स E × 4 pi r^2 है। तथापि इस प्रकरण में, गाउसीय पृष्ठ में कोई आवेश परिबद्ध नहीं है। तब गाउस नियम के अनुसार E × 4 pi r^2 = 0 अर्थात E = 0 (r < R) (1.35) अर्थात एकसमान आवेशित पतले गोलीय खोल* के कारण उसके भीतर स्थित सभी बिंदुओं पर विद्युत क्षेत्र शून्य है। यह महत्वपूर्ण परिणाम गाउस नियम का प्रत्यक्ष निष्कर्ष है जो कूलॉम नियम से प्राप्त हुआ है। इस नियम का प्रायोगिक सत्यापन कूलॉम नियम में 1/r^2 की निर्भरता की पुष्टि करता है।

### 15. ch 2 §2.10 ¶1 — en p21, hi p21, similarity 0.42 — the founder reads this one
- en: Dielectrics are non-conducting substances. In contrast to conductors, they have no (or negligible number of ) charge carriers. Recall from Section 2.9 what happens when a conductor is placed in an external electric field. The free charge carriers move and charge distribution in the conductor adjusts itself in such a way that the electric field due to induced charges opposes the external field within the conductor. This happens until, in the static situation, the two fields cancel each other and the net electrostatic field in the conductor is zero. In a dielectric, this free movement of charges is not possible. It turns out that the external field induces dipole moment by stretching or re-orienting molecules of the dielectric. The collective effect of all the molecular dipole moments is net charges on the surface of the dielectric which produce a field that opposes the external field. Unlike in a conductor, however, the opposing field so induced does not exactly cancel the external field. It only reduces it. The extent of the effect depends on the nature of the dielectric. To understand the effect, we need to look at the charge distribution of a dielectric at the molecular level.
- hi: परावैद्युत अचालक पदार्थ होते हैं। चालकों की तुलना में इनमें कोई आवेश वाहक नहीं (अथवा नगण्य) होता। अनुभाग (2.9) को याद कीजिए, क्या होता है जब किसी चालक को किसी बाह्य विद्युत क्षेत्र में रखा जाता है? चालक में मुक्त आवेश वाहक गति करके अपने को इस प्रकार समायोजित कर लेते हैं कि प्रेरित आवेशों के कारण विद्युत क्षेत्र बाह्य क्षेत्र का विरोध करता है। यह उस समय तक होता रहता है जब तक कि स्थिर स्थिति में दोनों क्षेत्र एक-दूसरे का निरसन कर देते हैं तथा चालक के भीतर नेट स्थिरवैद्युत क्षेत्र शून्य होता है। किसी परावैद्युत में आवेश की यह मुक्त गति संभव नहीं होती। फिर भी यह पाया जाता है कि बाह्य क्षेत्र परावैद्युत के पृष्ठ पर कुछ आवेश प्रेरित कर देता है जो एक ऐसा क्षेत्र उत्पन्न करता है जो बाह्य क्षेत्र का विरोध करता है। परंतु चालक से भिन्न, इस प्रकार का प्रेरित विद्युत क्षेत्र बाह्य क्षेत्र को यथार्थ रूप में निरक्षित नहीं करता। यह केवल क्षेत्र को घटा देता है। इस प्रभाव की सीमा परावैद्युत की प्रकृति पर निर्भर करती है। इस प्रभाव को समझने के लिए हमें किसी परावैद्युत पदार्थ में आण्विक स्तर पर आवेश वितरण के अध्ययन की आवश्यकता होगी।

### 16. ch 2 §2.11 ¶2 — en p23–24, hi p23, similarity 0.57
- en: The electric field in the region between the conductors is proportional to the charge Q. That is, if the charge on the capacitor is, say doubled, the electric field will also be doubled at every point. (This follows from the direct proportionality between field and charge implied by Coulomb’s law and the superposition principle.) Now, potential difference V is the work done per unit positive charge in taking a small test charge from the conductor 2 to 1 against the field. Consequently, V is also proportional to Q, and the ratio Q/V is a constant: C = Q / V (2.38) The constant C is called the capacitance of the capacitor. C is independent of Q or V, as stated above. The capacitance C depends only on the geometrical configuration (shape, size, separation) of the system of two conductors. [As we shall see later, it also depends on the nature of the insulator (dielectric) separating the two conductors.] The SI unit of capacitance is 1 farad (=1 coulomb volt^-1) or 1 F = 1 C V^-1. A capacitor with fixed capacitance is symbolically shown as --||--, while the one with variable capacitance is shown as .
- hi: चालकों के बीच के क्षेत्र में विद्युत क्षेत्र आवेश Q के अनुक्रमानुपाती होता है। अर्थात, यदि संधारित्र पर आवेश दोगुना कर दिया जाए तो हर बिंदु पर विद्युत क्षेत्र दोगुना हो जाएगा। (यह कूलॉम के नियम तथा अध्यारोपण सिद्धांत द्वारा अंतर्निहित विद्युत क्षेत्र तथा आवेश के बीच अनुक्रमानुपात का अनुगामी है।) अब, विभवांतर V किसी लघु परीक्षण आवेश को क्षेत्र के विरुद्ध चालक 2 से 1 तक ले जाने में प्रति एकांक धनावेश द्वारा किए गए कार्य के बराबर होता है। इसके फलस्वरूप, V भी Q के अनुक्रमानुपाती है, तथा अनुपात Q/V एक नियतांक है – C = Q / V (2.38) यहाँ C एक नियतांक है जिसे संधारित्र की धारिता कहते हैं। जैसा कि पहले वर्णन किया जा चुका है धारिता C आवेश Q अथवा विभवांतर V पर निर्भर नहीं करती। धारिता C केवल दो चालकों के निकाय के ज्यामितीय विन्यास (आकार, आकृति, पृथकन) पर निर्भर करती है। [जैसा कि हम आगे देखेंगे, यह दोनों चालकों को पृथक करने वाले माध्यम अर्थात विद्युतरोधी (परावैद्युत) की प्रकृति पर निर्भर करती है]। धारिता का SI एकांक 1 फैरड = 1 कूलॉम प्रति वोल्ट अथवा 1F = 1 C V^-1 है। नियत धारिता के संधारित्र का प्रतीक ⊣⊢, जबकि परिवर्ती धारिता के संधारित्र का प्रतीक है।

### 17. ch 2 §2.15 ¶2 — en p30, hi p30, similarity 0.64
- en: In transferring positive charge from conductor 2 to conductor 1, work will be done externally, since at any stage conductor 1 is at a higher potential than conductor 2. To calculate the total work done, we first calculate the work done in a small step involving transfer of an infinitesimal (i.e., vanishingly small) amount of charge. Consider the intermediate situation when the conductors 1 and 2 have charges Q' and –Q' respectively. At this stage, the potential difference V' between conductors 1 to 2 is Q'/C, where C is the capacitance of the system. Next imagine that a small charge delta Q' is transferred from conductor 2 to 1. Work done in this step (delta W), resulting in charge Q' on conductor 1 increasing to Q'+ delta Q', is given by delta W = V' delta Q' = (Q'/C) delta Q' (2.68) Integrating eq. (2.68) W = integral(0 to Q) (Q'/C) delta Q' = (1/C) (Q'^2 / 2)|(0 to Q) = Q^2 / 2C We can write the final result, in different ways W = Q^2 / 2C = (1/2) CV^2 = (1/2) QV (2.69)
- hi: आवेश को 2 से 1 पर स्थानांतरित करने में बाह्य कार्य करना होता है, चूँकि हर चरण में चालक 2 की तुलना में चालक 1 अधिक विभव पर होता है। किए गए कुल कार्य का परिकलन करने के लिए पहले हम छोटे-छोटे चरणों में आवेश की अत्यल्प (अर्थात लोप बिंदु तक छोटी) मात्रा को स्थानांतरित करने में किया गया कार्य परिकलित करते हैं। उस माध्य स्थिति पर विचार कीजिए जिसमें चालकों 1 तथा 2 पर क्रमशः आवेश Q' तथा –Q' हैं। इस स्थिति में, चालकों 1 तथा 2 के बीच विभवांतर V' = Q'/C होता है, यहाँ C निकाय की धारिता है। अब यह कल्पना कीजिए कि लघु आवेश delta Q' चालक 2 से 1 में स्थानांतरित किया जाता है। इस चरण में किया गया कार्य (delta w), जिसके परिणामस्वरूप चालक 1 पर आवेश Q' से बढ़कर Q' + delta Q' हो जाता है, इस प्रकार व्यक्त किया जाता है : delta W = V' delta Q' = (Q'/C) delta Q' (2.68) समीकरण (2.68) के समाकलन द्वारा W = integral(0 to Q) (Q'/C) delta Q' = (1/C) (Q'^2/2)|(0 to Q) = Q^2/2C इस परिणाम, को हम भिन्न-भिन्न प्रकार से व्यक्त कर सकते हैं : W = Q^2/2C = (1/2) CV^2 = (1/2) QV (2.69)

### 18. ch 3 §3.3 ¶2 — en p2, hi p2, similarity 0.48
- en: If we consider solid conductors, then of course the atoms are tightly bound to each other so that the current is carried by the negatively charged electrons. There are, however, other types of conductors like electrolytic solutions where positive and negative charges both can move. In our discussions, we will focus only on solid conductors so that the current is carried by the negatively charged electrons in the background of fixed positive ions.
- hi: यदि हम ठोस चालक पर विचार करें तो वास्तव में इनमें परमाणु आपस में निकट रूप से, कस कर आबद्ध होते हैं जिसके कारण ऋण आवेशित इलेक्ट्रॉन विद्युत धारा का वहन करते हैं। तथापि, अन्य प्रकार के चालक भी होते हैं जैसे विद्युत अपघटनी विलयन, जिनमें धनावेश तथा ऋणावेश दोनों गति कर सकते हैं। हम अपनी चर्चा को ठोस चालकों पर ही केंद्रित रखेंगे जिसमें स्थिर धनायनों की पृष्ठभूमि में ऋण आवेशित इलेक्ट्रॉन विद्युत धारा का वहन करते हैं।

### 19. ch 3 §3.7 ¶1 — en p9–10, hi p10, similarity 0.56 — the founder reads this one
- en: The materials are classified as conductors, semiconductors and insulators depending on their resistivities, in an increasing order of their values. Metals have low resistivities in the range of 10^-8 Ωm to 10^-6 Ωm. At the other end are insulators like ceramic, rubber and plastics having resistivities 10^18 times greater than metals or more. In between the two are the semiconductors. These, however, have resistivities characteristically decreasing with a rise in temperature. The resistivities of semiconductors can be decreased by adding small amount of suitable impurities. This last feature is exploited in use of semiconductors for electronic devices.
- hi: प्रतिरोधकता पर निर्भरता तथा उनके बढ़ते हुए मान के अनुसार पदार्थों का वर्गीकरण चालक, अधचालक तथा विद्युतरोधी में किया जाता है। धातुओं की प्रतिरोधकता 10^(-8) Omega m से 10^(-6) Omega m के परिसर में होती है। इसके विपरीत मृत्तिका (सिरेमिक), रबर तथा प्लास्टिक जैसे विद्युतरोधी पदार्थ भी हैं जिनकी प्रतिरोधकता, धातुओं की तुलना में 10^18 गुनी या अधिक है। इन दोनों के मध्य अर्धचालक हैं। इनकी प्रतिरोधकता, तथापि ताप बढ़ाने पर अभिलाक्षणिक रूप से घटती है। अर्धचालक की प्रतिरोधकता उपयुक्त अशुद्धियों को अल्प मात्रा में मिलाने पर कम की जा सकती है। इस अंतिम विशिष्टता का लाभ, इलेक्ट्रॉनिक युक्तियों में उपयोग होने वाले अर्धचालकों के निर्माण में किया जाता है।

### 20. ch 3 §3.11 ¶2 — en p15, hi p15, similarity 0.69
- en: Consider first two cells in series (Fig. 3.13), where one terminal of the two cells is joined together leaving the other terminal in either cell free. epsilon_1, epsilon_2 are the emf's of the two cells and r_1, r_2 their internal resistances, respectively.
- hi: पहले, श्रेणीक्रम में दो सेलों पर विचार करें (चित्र 3.13), जहाँ प्रत्येक के एक टर्मिनल को मुक्त छोड़कर, दोनों सेलों के एक टर्मिनल एक दूसरे से संयोजित हैं। epsilon_1, epsilon_2 दोनों सेलों के विद्युत वाहक बल हैं, तथा r_1, r_2 क्रमशः उनके आंतरिक प्रतिरोध हैं।

### 21. ch 4 §4.2.3 ¶1 — en p4, hi p4, similarity 0.61
- en: We can extend the analysis for force due to magnetic field on a single moving charge to a straight rod carrying current. Consider a rod of a uniform cross-sectional area A and length l. We shall assume one kind of mobile carriers as in a conductor (here electrons). Let the number density of these mobile charge carriers in it be n. Then the total number of mobile charge carriers in it is nlA. For a steady current I in this conducting rod, we may assume that each mobile carrier has an average drift velocity v_d (see Chapter 3). In the presence of an external magnetic field B, the force on these carriers is: F = (nlA)q v_d × B where q is the value of the charge on a carrier. Now nq v_d is the current density j and |(nq v_d)| A is the current I (see Chapter 3 for the discussion of current and current density). Thus, F = [(nq v_d)lA] × B = [ jAl ] × B = Il × B (4.4) where l is a vector of magnitude l, the length of the rod, and with a direction identical to the current I. Note that the current I is not a vector. In the last step leading to Eq. (4.4), we have transferred the vector sign from j to l.
- hi: हम किसी एकल गतिमान आवेश पर चुंबकीय क्षेत्र द्वारा आरोपित बल के विश्लेषण का विस्तार विद्युत धारावाही सीधी छड़ के लिए कर सकते हैं। लंबाई l तथा एकसमान अनुप्रस्थ काट A की किसी छड़ पर विचार करते हैं। हम किसी चालक (जिसमें इलेक्ट्रॉन गतिशील वाहक हैं) की भाँति एक ही प्रकार के गतिशील वाहक मानेंगे। मान लीजिए इन गतिशील आवेश वाहकों का संख्या घनत्व n है तब चालक में कुल गतिशील आवेश वाहकों की संख्या nlA हुई। इस चालक छड़ में अपरिवर्ती विद्युत धारा I के लिए हम यह मान सकते हैं कि प्रत्येक गतिशील वाहक का अपवाह वेग v_d है (अध्याय 3 देखिए)। किसी बाह्य चुंबकीय क्षेत्र B की उपस्थिति में इन वाहकों पर बल F = (nlA)q v_d × B यहाँ q किसी वाहक के आवेश का मान है। अब यहाँ nq v_d विद्युत धारा घनत्व j तथा |(nq v_d)|A विद्युत धारा I है (विद्युत धारा तथा विद्युत धारा घनत्व पर चर्चा के लिए अध्याय 3 देखिए।) इस प्रकार F = [(nq v_d)lA] × B = [ jlA ] × B = Il × B (4.4) यहाँ l एक सदिश है जिसका परिमाण l है जो कि छड़ की लंबाई है, तथा इसकी दिशा विद्युत धारा I के सर्वसम है। ध्यान दीजिए विद्युत धारा सदिश नहीं है। समीकरण (4.4) के अंतिम चरण में हमने सदिश चिह्न को j से l पर स्थानांतरित कर दिया है।

### 22. ch 4 §4.5 ¶8 — en p10, hi p10, similarity 0.47 — the founder reads this one
- en: The magnetic field lines due to a circular wire form closed loops and are shown in Fig. 4.10. The direction of the magnetic field is given by (another) right-hand thumb rule stated below:
- hi: वृत्ताकार तार के कारण चुंबकीय क्षेत्र रेखाएँ बंद वृत्ताकार पाश बनाती हैं जिन्हें चित्र 4.10 में दर्शाया गया है। चुंबकीय क्षेत्र की दिशा (एक अन्य) दक्षिण हस्त अंगुष्ठ नियम द्वारा होती है। यह नियम नीचे दिया गया है,

### 23. ch 4 §4.6 ¶12 — en p13, hi p13, similarity 0.53 — the founder reads this one
- en: * Note that there are two distinct right-hand rules: One which gives the direction of B on the axis of current-loop and the other which gives direction of B for a straight conducting wire. Fingers and thumb play different roles in the two.
- hi: * कृपया ध्यान दीजिए–दो सुस्पष्ट (पृथक) नियम हैं जिन्हें दक्षिण हस्त नियम कहते हैं। इनमें से एक नियम विद्युत धारा पाश के अक्ष पर चुंबकीय क्षेत्र B की दिशा देता है तथा दूसरा सीधे विद्युत धारावाही चालक तार के लिए B की दिशा है। इन नियमों में अँगूठे तथा अँगुलियों की भिन्न भूमिका है।

### 24. ch 4 §4.9.1 ¶12 — en p20, hi p21, similarity 0.55
- en: If the loop has N closely wound turns, the expression for torque, Eq. (4.23), still holds, with m = N I A (4.24)
- hi: यदि पाश में पास-पास सटे हुए N फेरे हैं तो बल आघूर्ण के लिए व्यंजक, समीकरण (4.23) अब भी लागू होता है। तब यह व्यंजक इस प्रकार व्यक्त किया जाता है m = N I A (4.24)

### 25. ch 4 §4.9.1 ¶16 — en p21, hi p21, similarity 0.71
- en: (b) The magnetic moment is given by Eq. (4.24), m = N I A = N I pi r^2 = 100 × 3.2 × 3.14 × 10^(-2) = 10 A m^2 The direction is once again given by the right-hand thumb rule.
- hi: (b) समीकरण (4.24) से चुंबकीय आघूर्ण m = N I A = N I pi r^2 = 100 × 3.2 × 3.14 × 10^(-2) = 10 A m^2 इस बार फिर दिशा दक्षिण हस्त अंगुष्ठ नियम द्वारा प्राप्त होती है।

### 26. ch 5 §5.3 ¶4 — en p8, hi p7, similarity 0.51
- en: The difference between the Gauss's law of magnetism and that for electrostatics is a reflection of the fact that isolated magnetic poles (also called monopoles) are not known to exist. There are no sources or sinks of B; the simplest magnetic element is a dipole or a current loop. All magnetic phenomena can be explained in terms of an arrangement of dipoles and/or current loops.
- hi: चुंबकत्व एवं स्थिरवैद्युतिकी के गाउस नियमों के बीच का अंतर इसी तथ्य की अभिव्यक्ति है कि पृथक्कृत चुंबकीय ध्रुवों (जिन्हें एकध्रुव भी कहते हैं) का अस्तित्व नहीं होता। B का कोई उद्गम या अभिगम नहीं होता है। सरलतम चुंबकीय अवयव एक द्विध्रुव या धारा लूप है। सभी चुंबकीय परिघटनाएँ एक धारा लूप एवं/या द्विध्रुव व्यवस्था के रूप में समझायी जा सकती हैं।

### 27. ch 5 §5.4 ¶7 — en p11, hi p11, similarity 0.61
- en: The three quantities chi, mu_r and mu are interrelated and only one of them is independent. Given one, the other two may be easily determined.
- hi: chi, mu_r एवं mu में तीन राशियाँ परस्पर संबंधित हैं। यदि इनमें से किसी एक का मान ज्ञात हो तो बाकी दोनों के मान ज्ञात किए जा सकते हैं।

### 28. ch 5 §5.5.2 ¶2 — en p13, hi p13, similarity 0.49
- en: The individual atoms (or ions or molecules) of a paramagnetic material possess a permanent magnetic dipole moment of their own. On account of the ceaseless random thermal motion of the atoms, no net magnetisation is seen. In the presence of an external field B_0, which is strong enough, and at low temperatures, the individual atomic dipole moment can be made to align and point in the same direction as B_0. Figure 5.7(b) shows a bar of paramagnetic material placed in an external field. The field lines gets concentrated inside the material, and the field inside is enhanced. In most cases, this enhancement is slight, being one part in 10^5. When placed in a non-uniform magnetic field, the bar will tend to move from weak field to strong.
- hi: किसी अनुचुंबकीय पदार्थ के परमाणुओं (या आयनों या अणुओं) का अपना स्वयं का स्थायी चुंबकीय द्विध्रुव आघूर्ण होता है। परमाणुओं की सतत यादृच्छिक तापीय गति के कारण कोई परिणामी चुंबकीकरण दृष्टिगत नहीं होता। पर्याप्त शक्तिशाली बाह्य चुंबकीय क्षेत्र B_0 की उपस्थिति में एवं निम्न तापों पर अलग-अलग परमाणुओं के द्विध्रुव आघूर्ण सरल रेखाओं में और B_0 की दिशा के अनुदिश संरेखित किए जा सकते हैं। चित्र 5.7 (b) बाह्य चुंबकीय क्षेत्र में रखी हुई अनुचुंबकीय पदार्थ की एक छड़ प्रदर्शित करता है। चुंबकीय क्षेत्र रेखाएँ पदार्थ के अंदर संकेंद्रित हो जाती हैं और अंदर चुंबकीय क्षेत्र बढ़ जाता है। अधिकतर मामलों में यह वृद्धि अति न्यून है, 10^5 भागों में एक भाग। असमान चुंबकीय क्षेत्र में रखने पर यह छड़ निम्न क्षेत्र से उच्च क्षेत्र की ओर चलने की चेष्टा करेगी।

### 29. ch 6 §6.1 ¶2 — en p1–2, hi p1–2, similarity 0.57
- en: When Faraday first made public his discovery that relative motion between a bar magnet and a wire loop produced a small current in the latter, he was asked, “What is the use of it?” His reply was: “What is the use of a new born baby?” The phenomenon of electromagnetic induction is not merely of theoretical or academic interest but also of practical utility. Imagine a world where there is no electricity – no electric lights, no trains, no telephones and no personal computers. The pioneering experiments of Faraday and Henry have led directly to the development of modern day generators and transformers. Today's civilisation owes its progress to a great extent to the discovery of electromagnetic induction.
- hi: जब फैराडे ने प्रथम बार अपनी इस खोज को सार्वजनिक किया कि 'चालक तार से बने लूप तथा दंड चुंबक के बीच सापेक्ष गति कराने पर लूप में क्षीण धारा उत्पन्न होती है', तब उनसे पूछा गया कि 'इसका क्या उपयोग है'? फैराडे का उत्तर था, 'नवजात शिशु का क्या उपयोग होता है?' वैद्युतचुंबकीय प्रेरण केवल सैद्धांतिक या शैक्षिक रूप से ही उपयोगी परिघटना नहीं है वरन व्यावहारिक दृष्टि से विद्युत न हो तो विद्युत प्रकाश न हो, ट्रेन न हो, टेलीफ़ोन न हो और कंप्यूटर न हो। फैराडे एवं हेनरी के इन पुरोगामी (pioneering) प्रयोगों ने ही आधुनिक जनित्रों एवं ट्रांसफार्मरों के विकास को संभव बनाया। आज की सभ्यता के विकास में वैद्युतचुंबकीय प्रेरण की खोज ने एक अहम भूमिका निभाई है।

### 30. ch 6 §6.5 ¶8 — en p8, hi p8, similarity 0.57
- en: Solution (i) The magnetic flux through the rectangular loop abcd increases, due to the motion of the loop into the region of magnetic field, The induced current must flow along the path bcdab so that it opposes the increasing flux.
- hi: हल (i) आयताकार लूप abcd में चुंबकीय फ्लक्स, लूप के चुंबकीय क्षेत्र के भाग की ओर गति करने के कारण बढ़ता है। प्रेरित धारा पथ bcdab के अनुदिश प्रवाहित होनी चाहिए जिससे कि यह बढ़ते हुए फ्लक्स का विरोध कर सके।

### 31. ch 6 §6.7.1 ¶9 — en p13, hi p13, similarity 0.86
- en: Using Eq. (6.9) and Eq. (6.10), we get M_12 = M_21 = M (say) (6.12)
- hi: समीकरण (6.9) तथा समीकरण (6.10) का उपयोग करके हमें प्राप्त होता है M_12 = M_21 = M (माना) (6.12)

### 32. ch 6 §6.8 ¶9 — en p19, hi p19, similarity 0.66
- en: Example 6.10 Kamla peddles a stationary bicycle. The pedals of the bicycle are attached to a 100 turn coil of area 0.10 m^2. The coil rotates at half a revolution per second and it is placed in a uniform magnetic field of 0.01 T perpendicular to the axis of rotation of the coil. What is the maximum voltage generated in the coil?
- hi: उदाहरण 6.10 कमला एक स्थिर साइकिल के पैडल को घुमाती है। पैडल का संबंध 100 फेरों तथा 0.10 m^2 क्षेत्रफल वाली एक कुंडली से है। कुंडली प्रति सेकंड आधा परिक्रमण (चक्कर) कर पाती है तथा यह एक 0.01 T तीव्रता वाले एकसमान चुंबकीय क्षेत्र में, जो कुंडली के घूर्णन अक्ष के लंबवत है, रखी है। कुंडली में उत्पन्न होने वाली अधिकतम वोल्टता क्या होगी?

### 33. ch 7 §7.2 ¶3 — en p2, hi p2, similarity 0.63
- en: Equation (7.3) is Ohm's law, which for resistors, works equally well for both ac and dc voltages. The voltage across a pure resistor and the current through it, given by Eqs. (7.1) and (7.2) are plotted as a function of time in Fig. 7.2. Note, in particular that both v and i reach zero, minimum and maximum values at the same time. Clearly, the voltage and current are in phase with each other.
- hi: समीकरण (7.3) ओम का नियम है जो प्रतिरोधकों के प्रकरण में ac एवं dc दोनों प्रकार की वोल्टताओं के लिए समान रूप से लागू होता है। समीकरण (7.1) एवं समीकरण (7.2) द्वारा व्यक्त किसी शुद्ध प्रतिरोधक के सिरों के बीच लगाई गई वोल्टता एवं इसमें प्रवाहित होने वाली धारा को चित्र 7.2 में समय के फलन के रूप में आलेखित किया गया है। इस तथ्य पर विशेष ध्यान दीजिए कि v एवं i दोनों ही शून्य, न्यूनतम एवं अधिकतम मानों की स्थितियाँ साथ-साथ ही प्राप्त करती हैं। अतः स्पष्ट है कि वोल्टता एवं धारा एक दूसरे के साथ समान कला में हैं।

### 34. ch 7 §7.2 ¶4 — en p2–3, hi p2–3, similarity 0.56 — the founder reads this one
- en: We see that, like the applied voltage, the current varies sinusoidally and has corresponding positive and negative values during each cycle. Thus, the sum of the instantaneous current values over one complete cycle is zero, and the average current is zero. The fact that the average current is zero, however, does not mean that the average power consumed is zero and that there is no dissipation of electrical energy. As you know, Joule heating is given by i^2R and depends on i^2 (which is always positive whether i is positive or negative) and not on i. Thus, there is Joule heating and dissipation of electrical energy when an ac current passes through a resistor.
- hi: हम देखते हैं कि प्रयुक्त वोल्टता की भाँति ही धारा भी ज्या-वक्रीय रूप में परिवर्तित होती है और तदनुसार ही प्रत्येक चक्र में इसके धनात्मक एवं ऋणात्मक मान प्राप्त होते हैं। अतः एक संपूर्ण चक्र में तात्क्षणिक धारा मानों का योग शून्य होता है तथा माध्य धारा शून्य होती है। तथापि माध्य धारा शून्य है इस तथ्य का यह अर्थ नहीं है कि व्यय होने वाली माध्य शक्ति भी शून्य है, और विद्युत ऊर्जा का क्षय नहीं हो रहा है। जैसा कि आप जानते हैं जूल i^2R द्वारा व्यक्त होता है और i^2 (जो सदैव धनात्मक ही होता है चाहे i धनात्मक हो या ऋणात्मक) पर निर्भर करता है, न कि i पर। अतः जब किसी प्रतिरोधक से ac धारा प्रवाहित होती है तो जूल तापन एवं वैद्युत ऊर्जा का क्षय होता है।

### 35. ch 7 §7.3 ¶2 — en p5, hi p5, similarity 0.63
- en: From Fig. 7.4(a) we see that phasors V and I for the case of a resistor are in the same direction. This is so for all times. This means that the phase angle between the voltage and the current is zero.
- hi: चित्र 7.4(a) से हम यह समझ सकते हैं कि प्रतिरोधक के लिए फेजर्स V एवं I एक ही दिशा में होते हैं। ऐसा हर समय होता है। इसका अर्थ है कि वोल्टता एवं धारा के बीच कला कोण शून्य होता है।

### 36. ch 7 §7.6.2 ¶2 — en p13, hi p13, similarity 0.70
- en: For an RLC circuit driven with voltage of amplitude v_m and frequency omega, we found that the current amplitude is given by i_m = v_m / Z = v_m / sqrt(R^2 + (X_C - X_L)^2) with X_c = 1/omega C and X_L = omega L. So if omega is varied, then at a particular frequency omega_0, X_c = X_L, and the impedance is minimum (Z = sqrt(R^2 + 0^2) = R). This frequency is called the resonant frequency: X_c = X_L or 1/(omega_0 C) = omega_0 L or omega_0 = 1 / sqrt(LC) (7.28) At resonant frequency, the current amplitude is maximum; i_m = v_m/R.
- hi: v_m आयाम एवं omega आवृत्ति की वोल्टता द्वारा संचालित RLC परिपथ के लिए हम पाते हैं कि धारा आयाम, i_m = v_m / Z = v_m / sqrt(R^2 + (X_C - X_L)^2) यहाँ X_c = 1/omega C एवं X_L = omega L अतः यदि omega को परिवर्तित किया जाए तो एक विशिष्ट आवृत्ति omega_0 पर X_c = X_L एवं प्रतिबाधा Z का मान न्यूनतम (Z = sqrt(R^2 + 0^2) = R) हो जाता है। यह आवृत्ति अनुनादी आवृत्ति कहलाती है : X_c = X_L या 1 / (omega_0 C) = omega_0 L या omega_0 = 1 / sqrt(LC) (7.28) अनुनादी आवृत्ति पर धारा का आयाम अधिकतम होता है और इसका मान है, i_m = v_m/R

### 37. ch 7 §7.6.2 ¶4 — en p13, hi p13, similarity 0.50
- en: We see that the current amplitude is maximum at the resonant frequency. Since i_m = v_m / R at resonance, the current amplitude for case (i) is twice to that for case (ii).
- hi: हम देखते हैं कि अनुनादी आवृत्ति पर धारा का आयाम अधिकतम होता है। चूँकि i_m = v_m / R अनुनाद की स्थिति में, प्रकरण (i) में धारा का परिमाण प्रकरण (ii) की स्थिति में धारा के परिमाण से दोगुना है।

### 38. ch 7 §7.8 ¶2 — en p18, hi p18, similarity 0.63
- en: A transformer consists of two sets of coils, insulated from each other. They are wound on a soft-iron core, either one on top of the other as in Fig. 7.16(a) or on separate limbs of the core as in Fig. 7.16(b). One of the coils called the primary coil has N_p turns. The other coil is called the secondary coil; it has N_s turns. Often the primary coil is the input coil and the secondary coil is the output coil of the transformer.
- hi: ट्रांसफार्मर में दो कुंडलियाँ होती हैं जो एक दूसरे से विद्युतरुद्ध होती हैं। वे एक कोमल-लौह-क्रोड पर लिपटी होती हैं। लपेटने की विधि या तो चित्र 7.16 (a) की भाँति होती है, जिसमें एक कुंडली दूसरी के ऊपर लिपटी होती है, या फिर चित्र 7.16 (b) की भाँति जिसमें दोनों कुंडलियाँ क्रोड की अलग-अलग भुजाओं पर लिपटी होती हैं। एक कुंडली को प्राथमिक कुंडली (primary coil) कहते हैं इसमें N_p लपेटे होते हैं। दूसरी कुंडली को द्वितीयक कुंडली (secondary coil) कहते हैं, इसमें N_s लपेटे होते हैं। प्रायः प्राथमिक कुंडली निवेशी कुंडली होती है एवं द्वितीयक कुंडली ट्रांसफार्मर की निर्गत कुंडली होती है।

### 39. ch 7 §7.8 ¶19 — en p20, hi p20, similarity 0.55 — the founder reads this one
- en: (iv) Hysteresis: The magnetisation of the core is repeatedly reversed by the alternating magnetic field. The resulting expenditure of energy in the core appears as heat and is kept to a minimum by using a magnetic material which has a low hysteresis loss.
- hi: (iv) शैथिल्य (Hysteresis)–प्रत्यावर्ती चुंबकीय क्षेत्र द्वारा क्रोड का चुंबकन बार-बार उत्क्रमित होता है। इस प्रक्रिया में व्यय होने वाली ऊर्जा क्रोड में ऊष्मा के रूप में प्रकट होती है। कम शैथिल्य वाले पदार्थ का क्रोड में उपयोग करके इस प्रभाव को कम रखा जाता है।

### 40. ch 8 §8.2 ¶9 — en p3–4, hi p3–4, similarity 0.62
- en: The generalisation made by Maxwell then is the following. The source of a magnetic field is not just the conduction electric current due to flowing charges, but also the time rate of change of electric field. More precisely, the total current i is the sum of the conduction current denoted by i_c, and the displacement current denoted by i_d (= epsilon_0 (d Phi_E / dt)). So we have i = i_c + i_d = i_c + epsilon_0 (d Phi_E / dt) (8.5)
- hi: मैक्सवेल द्वारा किया गया व्यापकीकरण निम्न है। चुंबकीय क्षेत्र का स्रोत केवल प्रवाहमान आवेशों से निर्मित चालन विद्युत धारा ही नहीं होती, अपितु समय के सापेक्ष विद्युत क्षेत्र में परिवर्तन की दर भी इसका कारण बन सकती है। अधिक स्पष्टता से इस बात को कहें तो कुल धारा i, i_c द्वारा निर्दिष्ट चालन धारा एवं i_d (= epsilon_0 (dPhi_E/dt)) द्वारा निर्दिष्ट विस्थापन धारा के योग के बराबर होती है। अतः i = i_c + i_d = i_c + epsilon_0 dPhi_E / dt (8.5)

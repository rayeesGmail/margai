# margai-pipeline ncert align

- run: 2026-10-17 08:26 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 748 English and 717 Hindi row(s) in 19 chapter(s)
- result: ok
request id: pipeline-ncert-align-48ab92f8-30c0-43ee-821d-f3f9b2278c1a
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 717 | 329669 | ₹7.85 |

## sections whose rows do not pair one to one

en counts the English rows aligned: a number an `unprinted` entry left is listed below, not here
| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 1 | 1.1 | 15 | 13 | 2 |
| 2 | 2 | 6 | 5 | 1 |
| 2 | 2.3 | 9 | 6 | 3 |
| 3 | 3.1 | 4 | 5 | 1 |
| 4 | 4.1.1 | 3 | 1 | 2 |
| 4 | 4.2 | 2 | 1 | 1 |
| 4 | 4.2.1 | 2 | 1 | 1 |
| 4 | 4.2.2 | 2 | 1 | 1 |
| 4 | 4.2.4 | 2 | 1 | 1 |
| 4 | 4.2.5 | 2 | 1 | 1 |
| 4 | 4.2.6 | 2 | 1 | 1 |
| 4 | 4.2.7 | 5 | 1 | 4 |
| 4 | 4.2.11 | 7 | 7 | 2 |
| 4 | 4.2.11.7 | 3 | 2 | 1 |
| 5 | 5.5 | 4 | 5 | 1 |
| 5 | 5.9 | 17 | 17 | 2 |
| 6 | 6.1.1 | 2 | 3 | 1 |
| 6 | 6.1.3 | 1 | 2 | 1 |
| 6 | 6.2.6 | 3 | 2 | 1 |
| 7 | 7.1 | 1 | 2 | 1 |
| 8 | 8.4 | 2 | 3 | 1 |
| 8 | 8.4.1 | 6 | 5 | 1 |
| 8 | 8.5 | 3 | 2 | 1 |
| 8 | 8.5.1 | 7 | 7 | 2 |
| 8 | 8.5.3.1 | 4 | 3 | 1 |
| 8 | 8.5.10 | 6 | 5 | 1 |
| 9 | 9.1 | 7 | 6 | 1 |
| 9 | 9.2 | 2 | 1 | 1 |
| 9 | 9.3 | 5 | 4 | 1 |
| 9 | 9.5 | 2 | 3 | 1 |
| 9 | 9.8.1 | 2 | 5 | 3 |
| 9 | 9.8.3 | 7 | 5 | 2 |
| 10 | 10.1.1 | 8 | 9 | 1 |
| 10 | 10.2.1 | 4 | 5 | 3 |
| 10 | 10.2.2 | 1 | 2 | 1 |
| 10 | 10.4 | 6 | 4 | 2 |
| 10 | 10.4.1 | 7 | 9 | 2 |
| 10 | 10.4.2 | 4 | 5 | 1 |
| 10 | 10.5 | 1 | 0 | 1 |
| 11 | 11.2 | 9 | 11 | 2 |
| 11 | 11.6.1 | 2 | 1 | 1 |
| 11 | 11.6.3 | 9 | 8 | 1 |
| 11 | 11.7 | 4 | 3 | 1 |
| 11 | 11.7.2 | 8 | 7 | 1 |
| 11 | 11.8 | 11 | 10 | 1 |
| 11 | 11.10 | 5 | 3 | 2 |
| 11 | 11.10.2 | 3 | 2 | 1 |
| 12 | 12.1 | 4 | 3 | 1 |
| 12 | 12.2 | 4 | 5 | 1 |
| 12 | 12.4 | 5 | 5 | 2 |
| 12 | 12.5 | 7 | 3 | 4 |
| 12 | 12.7 | 6 | 4 | 2 |
| 13 | 13.1.3 | 1 | 2 | 1 |
| 13 | 13.1.4 | 7 | 5 | 2 |
| 13 | 13.4.2 | 6 | 5 | 1 |
| 13 | 13.4.3.1 | 4 | 2 | 2 |
| 13 | 13.4.3.2 | 3 | 1 | 2 |
| 13 | 13.4.3.5 | 5 | 4 | 1 |
| 14 | 14.1.1 | 3 | 3 | 2 |
| 14 | 14.2 | 1 | 2 | 1 |
| 14 | 14.2.1 | 10 | 10 | 4 |
| 14 | 14.6 | 3 | 4 | 1 |
| 15 | 15.1.1 | 2 | 1 | 1 |
| 15 | 15.1.3.1 | 2 | 3 | 1 |
| 15 | 15.2 | 1 | 2 | 1 |
| 15 | 15.3.1 | 3 | 4 | 1 |
| 15 | 15.3.3 | 6 | 4 | 2 |
| 16 | 16 | 3 | 9 | 6 |
| 16 | 16.1 | 5 | 7 | 2 |
| 16 | 16.2 | 6 | 4 | 2 |
| 16 | 16.6 | 1 | 2 | 1 |
| 17 | 17 | 2 | 1 | 1 |
| 17 | 17.2.2 | 3 | 2 | 1 |
| 18 | 18.1 | 2 | 1 | 1 |
| 18 | 18.2 | 5 | 2 | 3 |
| 18 | 18.3.1 | 2 | 3 | 1 |
| 18 | 18.4.1 | 1 | 2 | 1 |
| 19 | 19.2.1 | 1 | 2 | 1 |
| 19 | 19.2.2 | 3 | 4 | 1 |
| 19 | 19.2.4 | 3 | 2 | 1 |
| 19 | 19.2.7 | 5 | 4 | 1 |
| 19 | 19.2.8 | 4 | 3 | 1 |
| 19 | 19.3 | 3 | 2 | 1 |
| 19 | 19.4 | 2 | 3 | 1 |

## English paragraphs the Hindi does not print — numbers an `unprinted` entry left, not aligned

none

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 1 §1.1: hi ¶5 = en ¶5+¶6 (split hi)
- ch 1 §1.1: hi ¶6 = en ¶7+¶8 (split hi)
- ch 2 §2: hi ¶3 = en ¶3+¶4 (split hi)
- ch 2 §2.3: hi ¶4 = en ¶4+¶5 (split hi)
- ch 2 §2.3: en ¶6 has no hindi
- ch 2 §2.3: hi ¶5 = en ¶7+¶8 (split hi)
- ch 3 §3.1: hi ¶4 has no english
- ch 4 §4.1.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.1.1: en ¶3 has no hindi
- ch 4 §4.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2.4: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2.5: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2.6: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2.7: en ¶2 has no hindi
- ch 4 §4.2.7: en ¶3 has no hindi
- ch 4 §4.2.7: en ¶4 has no hindi
- ch 4 §4.2.7: en ¶5 has no hindi
- ch 4 §4.2.11: en ¶2 has no hindi
- ch 4 §4.2.11: hi ¶4+¶5 = en ¶5 (join hi)
- ch 4 §4.2.11.7: en ¶3 has no hindi
- ch 5 §5.5: hi ¶1+¶2 = en ¶1 (join hi)
- ch 5 §5.9: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.9: hi ¶2 has no english
- ch 6 §6.1.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 6 §6.1.3: hi ¶1+¶2 = en ¶1 (join hi)
- ch 6 §6.2.6: en ¶3 has no hindi
- ch 7 §7.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 8 §8.4: hi ¶2+¶3 = en ¶2 (join hi)
- ch 8 §8.4.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 8 §8.5: hi ¶2 = en ¶2+¶3 (split hi)
- ch 8 §8.5.1: hi ¶3 = en ¶3+¶4 (split hi)
- ch 8 §8.5.1: hi ¶6+¶7 = en ¶7 (join hi)
- ch 8 §8.5.3.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 8 §8.5.10: hi ¶5 = en ¶5+¶6 (split hi)
- ch 9 §9.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 9 §9.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 9 §9.3: hi ¶3 = en ¶3+¶4 (split hi)
- ch 9 §9.5: hi ¶1+¶2 = en ¶1 (join hi)
- ch 9 §9.8.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 9 §9.8.1: hi ¶3 has no english
- ch 9 §9.8.1: hi ¶4+¶5 = en ¶2 (join hi)
- ch 9 §9.8.3: hi ¶3 = en ¶3+¶4 (split hi)
- ch 9 §9.8.3: hi ¶4 = en ¶5+¶6 (split hi)
- ch 10 §10.1.1: hi ¶4+¶5 = en ¶4 (join hi)
- ch 10 §10.2.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 10 §10.2.1: hi ¶3 has no english
- ch 10 §10.2.1: hi ¶5 = en ¶3+¶4 (split hi)
- ch 10 §10.2.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 10 §10.4: en ¶4 has no hindi
- ch 10 §10.4: hi ¶4 = en ¶5+¶6 (split hi)
- ch 10 §10.4.1: hi ¶2+¶3 = en ¶2 (join hi)
- ch 10 §10.4.1: hi ¶4+¶5 = en ¶3 (join hi)
- ch 10 §10.4.2: hi ¶4+¶5 = en ¶4 (join hi)
- ch 10 §10.5: en ¶1 has no hindi
- ch 11 §11.2: hi ¶7+¶8 = en ¶7 (join hi)
- ch 11 §11.2: hi ¶10+¶11 = en ¶9 (join hi)
- ch 11 §11.6.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 11 §11.6.3: hi ¶2 = en ¶2+¶3 (split hi)
- ch 11 §11.7: en ¶2 has no hindi
- ch 11 §11.7.2: hi ¶7 = en ¶7+¶8 (split hi)
- ch 11 §11.8: hi ¶7 = en ¶7+¶8 (split hi)
- ch 11 §11.10: hi ¶3 = en ¶3+¶4 (split hi)
- ch 11 §11.10: en ¶5 has no hindi
- ch 11 §11.10.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 12 §12.1: hi ¶3 = en ¶3+¶4 (split hi)
- ch 12 §12.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 12 §12.4: hi ¶1+¶2 = en ¶1 (join hi)
- ch 12 §12.4: hi ¶3 = en ¶2+¶3 (split hi)
- ch 12 §12.5: en ¶1 has no hindi
- ch 12 §12.5: hi ¶1 = en ¶2+¶3 (split hi)
- ch 12 §12.5: en ¶4 has no hindi
- ch 12 §12.5: hi ¶2 = en ¶5+¶6 (split hi)
- ch 12 §12.7: hi ¶1 = en ¶1+¶2 (split hi)
- ch 12 §12.7: hi ¶2 = en ¶3+¶4 (split hi)
- ch 13 §13.1.3: hi ¶1+¶2 = en ¶1 (join hi)
- ch 13 §13.1.4: hi ¶2 = en ¶2+¶3 (split hi)
- ch 13 §13.1.4: hi ¶3 = en ¶4+¶5 (split hi)
- ch 13 §13.4.2: hi ¶5 = en ¶5+¶6 (split hi)
- ch 13 §13.4.3.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 13 §13.4.3.1: en ¶3 has no hindi
- ch 13 §13.4.3.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 13 §13.4.3.2: en ¶3 has no hindi
- ch 13 §13.4.3.5: hi ¶2 = en ¶2+¶3 (split hi)
- ch 14 §14.1.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 14 §14.1.1: hi ¶3 = en ¶2+¶3 (split hi)
- ch 14 §14.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 14 §14.2.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 14 §14.2.1: hi ¶3+¶4 = en ¶4 (join hi)
- ch 14 §14.2.1: hi ¶6 = en ¶6+¶7 (split hi)
- ch 14 §14.2.1: hi ¶7+¶8 = en ¶8 (join hi)
- ch 14 §14.6: hi ¶1+¶2 = en ¶1 (join hi)
- ch 15 §15.1.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 15 §15.1.3.1: hi ¶2+¶3 = en ¶2 (join hi)
- ch 15 §15.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 15 §15.3.1: hi ¶3+¶4 = en ¶3 (join hi)
- ch 15 §15.3.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 15 §15.3.3: hi ¶3 = en ¶4+¶5 (split hi)
- ch 16 §16: hi ¶1+¶2 = en ¶1 (join hi)
- ch 16 §16: hi ¶3+¶4 = en ¶2 (join hi)
- ch 16 §16: hi ¶5 has no english
- ch 16 §16: hi ¶6+¶7 = en ¶3 (join hi)
- ch 16 §16: hi ¶8 has no english
- ch 16 §16: hi ¶9 has no english
- ch 16 §16.1: hi ¶4 has no english
- ch 16 §16.1: hi ¶5+¶6 = en ¶4 (join hi)
- ch 16 §16.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 16 §16.2: hi ¶4 = en ¶5+¶6 (split hi)
- ch 16 §16.6: hi ¶1+¶2 = en ¶1 (join hi)
- ch 17 §17: hi ¶1 = en ¶1+¶2 (split hi)
- ch 17 §17.2.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 18 §18.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 18 §18.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 18 §18.2: en ¶3 has no hindi
- ch 18 §18.2: hi ¶2 = en ¶4+¶5 (split hi)
- ch 18 §18.3.1: hi ¶2+¶3 = en ¶2 (join hi)
- ch 18 §18.4.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 19 §19.2.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 19 §19.2.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 19 §19.2.4: hi ¶2 = en ¶2+¶3 (split hi)
- ch 19 §19.2.7: hi ¶2 = en ¶2+¶3 (split hi)
- ch 19 §19.2.8: hi ¶2 = en ¶2+¶3 (split hi)
- ch 19 §19.3: hi ¶2 = en ¶2+¶3 (split hi)
- ch 19 §19.4: hi ¶1+¶2 = en ¶1 (join hi)

## pairs weaker than 0.45 — read both against the pages

- ch 2 §2.3: hi ¶6 = en ¶9 — similarity 0.44 — en «The morphology of the mycelium, mode of spore…» · hi «कवक जाल की आकारिकी, बीजाणु बनने तथा फलन…»
- ch 3 §3.1.3: hi ¶3 = en ¶3 — similarity 0.44 — en «The red algae usually reproduce vegetatively by fragmentation.…» · hi «इसमें कायिक जनन विखंडन, अलैंगिक जनन अचल स्पोर…»
- ch 4 §4.2.3: hi ¶2 = en ¶2 — similarity 0.35 — en «Examples: Pleurobrachia and Ctenoplana.» · hi «टीनोफोर की मुख्य विशेषता है। नर एवं मादा…»
- ch 4 §4.2.11: hi ¶1 = en ¶1 — similarity 0.40 — en «Animals belonging to phylum Chordata are fundamentally characterised…» · hi «कशेरुकी संघ के प्राणियों में तीन मूलभूत लक्षण…»
- ch 4 §4.2.11: hi ¶6 = en ¶6 — similarity 0.39 — en «The members of subphylum Vertebrata possess notochord during…» · hi «कशेरुकी संघ के प्राणियों में पृष्ठ रज्जु भ्रूणीय…»
- ch 4 §4.2.11: hi ¶7 = en ¶7 — similarity 0.33 — en «The subphylum Vertebrata is further divided as follows:» · hi «उपसंघ वर्टीब्रेटा को पुनः निम्न उपवर्ग में विभाजित…»
- ch 5 §5.3.1: hi ¶1 = en ¶1 — similarity 0.42 — en «The arrangement of veins and the veinlets in…» · hi «पत्ती पर शिरा तथा शिरिकाओं के विन्यास को…»
- ch 5 §5.3.2: hi ¶3 = en ¶3 — similarity 0.42 — en «In palmately compound leaves, the leaflets are attached…» · hi «हस्ताकार संयुक्त पत्तियों में पत्रक एक ही बिंदु…»
- ch 5 §5.7.2: hi ¶1 = en ¶1 — similarity 0.43 — en «Generally, monocotyledonous seeds are endospermic but some as…» · hi «प्रायः एकबीजपत्री बीज भ्रूणपोषी होते हैं लेकिन उनमें…»
- ch 5 §5.9: hi ¶4 = en ¶4 — similarity 0.45 — en «Stem: herbaceous rarely woody, aerial; erect, cylindrical, branched,…» · hi «तनाः शाकीय, कभी-कभी काष्ठीय; वायवीय, सीधा, सिलिंडिराकर, शाखित,…»
- ch 5 §5.9: hi ¶5 = en ¶5 — similarity 0.38 — en «Leaves: alternate, simple, rarely pinnately compound, exstipulate; venation…» · hi «पत्तियाँ: एकांतर, सरल, कर्मी संयुक्त पिच्छाकार अनुपर्णी, जालिका…»
- ch 5 §5.9: hi ¶6 = en ¶6 — similarity 0.24 — en «Floral Characters» · hi «पुष्पी अभिलक्षणः»
- ch 5 §5.9: hi ¶7 = en ¶7 — similarity 0.40 — en «Inflorescence : Solitary, axillary or cymose as in…» · hi «पुष्पक्रमः एकल, कक्षीय, ससीमाक्षी जैसे सोलैनम में;»
- ch 5 §5.9: hi ¶8 = en ¶8 — similarity 0.30 — en «Flower: bisexual, actinomorphic» · hi «फूलः उभयलिंगी, त्रिज्यसममिति»
- ch 5 §5.9: hi ¶10 = en ¶10 — similarity 0.41 — en «Corolla: petals five, united; valvate aestivation» · hi «कोरोलाः पाँच दल, संयुक्त, कोरस्पर्शी पुष्पदल विन्यास»
- ch 5 §5.9: hi ¶11 = en ¶11 — similarity 0.24 — en «Androecium: stamens five, epipetalous» · hi «पुमंगः पाँच पुंकेसर, दललग्न»
- ch 5 §5.9: hi ¶12 = en ¶12 — similarity 0.35 — en «Gynoecium: bicarpellary obligately placed, syncarpous; ovary superior, bilocular,…» · hi «जायांगः द्विअंडपी, युक्तांडपी, तिरछी अंडाशय ऊर्ध्वावर्ती, द्विकोष्ठी, बीजांडासन…»
- ch 5 §5.9: hi ¶13 = en ¶13 — similarity 0.35 — en «Fruits: berry or capsule» · hi «फलः संपुट अथवा सरस»
- ch 5 §5.9: hi ¶14 = en ¶14 — similarity 0.34 — en «Seeds: many, endospermous» · hi «बीजः भ्रूणपोषी, अनेक»
- ch 5 §5.9: hi ¶16 = en ¶16 — similarity 0.40 — en «Economic Importance» · hi «आर्थिक महत्व»
- ch 6 §6.1.1: hi ¶3 = en ¶2 — similarity 0.45 — en «The cells of epidermis bear a number of…» · hi «बाह्यत्वचा की कोशिकाओं पर अनेक रोम होते हैं।…»
- ch 6 §6.2.2: hi ¶1 = en ¶1 — similarity 0.41 — en «The anatomy of the monocot root is similar…» · hi «एक बीजपत्री मूल का शारीर बहुत अधिक द्विबीजपत्री…»
- ch 6 §6.2.4: hi ¶1 = en ¶1 — similarity 0.38 — en «The monocot stem has a sclerenchymatous hypodermis, a…» · hi «एकबीजपत्री तने की शारीरिक रचना द्विबीजपत्री तने से…»
- ch 6 §6.2.6: hi ¶2 = en ¶2 — similarity 0.43 — en «In grasses, certain adaxial epidermal cells along the…» · hi «घास में ऊपरी बाह्यत्वचा कुछ कोशिकाएँ लंबी, खाली…»
- ch 7 §7.2.1: hi ¶1 = en ¶1 — similarity 0.44 — en «Have you ever touched the skin of frog?…» · hi «क्या आपने कभी मेंढक की त्वचा को छुआ…»
- ch 7 §7.2.2: hi ¶1 = en ¶1 — similarity 0.33 — en «The body cavity of frogs accommodate different organ…» · hi «मेंढक की देह गुहा में पाचन तंत्र, श्वसन…»
- ch 7 §7.2.2: hi ¶2 = en ¶2 — similarity 0.41 — en «The digestive system consists of alimentary canal and…» · hi «यकृत पित्त रस स्रावित करता है जो पित्ताशय…»
- ch 8 §8.5.3: hi ¶1 = en ¶1 — similarity 0.43 — en «While each of the membranous organelles is distinct…» · hi «झिल्लीदार अंगक कार्य व संरचना के आधार पर…»
- ch 8 §8.5.3.1: hi ¶1 = en ¶1 — similarity 0.42 — en «Electron microscopic studies of eukaryotic cells reveal the…» · hi «इलेक्ट्रॉन सूक्ष्मदर्शी से अध्ययन के पश्चात् यह पता…»
- ch 8 §8.5.3.1: hi ¶3 = en ¶4 — similarity 0.43 — en «The smooth endoplasmic reticulum is the major site…» · hi «चिकनी अंतर्द्रव्यी जालिका प्राणियों में लिपिड संश्लेषण के…»
- ch 8 §8.5.3.4: hi ¶2 = en ¶2 — similarity 0.40 — en «In plants, the tonoplast facilitates the transport of…» · hi «पौधों में बहुत से आयन व दूसरे पदार्थ…»
- ch 8 §8.5.9: hi ¶1 = en ¶1 — similarity 0.40 — en «Centrosome is an organelle usually containing two cylindrical…» · hi «तारककाय वह अंगक है जो दो बेलनाकार संरचना…»
- ch 8 §8.5.10: hi ¶2 = en ¶2 — similarity 0.42 — en «The interphase nucleus (nucleus of a cell when…» · hi «अंतरकाल अवस्था केंद्रक (कोशिका केंद्रक जिसका विभाजन नहीं…»
- ch 9 §9.8.2: hi ¶4 = en ¶4 — similarity 0.43 — en «Enzymes eventually bring down this energy barrier making…» · hi «एंजाइम ऊर्जा अवरोध को घटाकर क्रियाधार से उत्पाद…»
- ch 9 §9.8.3: hi ¶2 = en ¶2 — similarity 0.43 — en «The formation of the ES complex is essential…» · hi «एंजाइम-क्रियाधार जटिल (ES) का निर्माण उत्प्रेरण के लिए…»
- ch 10 §10.2.1: hi ¶4 = en ¶2 — similarity 0.42 — en «Chromosomal material condenses to form compact mitotic chromosomes.…» · hi «गुणसूत्रीय द्रव्य संघनित होकर ठोस गुणसूत्र बन जाता…»
- ch 10 §10.2.3: hi ¶1 = en ¶1 — similarity 0.37 — en «At the onset of anaphase, each chromosome arranged…» · hi «पश्चावस्था के प्रारंभ में मध्यावस्था पट्टिका पर आए…»
- ch 10 §10.2.4: hi ¶1 = en ¶1 — similarity 0.42 — en «At the beginning of the final stage of…» · hi «सूत्री विभाजन की अंतिम अवस्था के प्रारंभ में…»
- ch 10 §10.3: hi ¶1 = en ¶1 — similarity 0.41 — en «Mitosis or the equational division is usually restricted…» · hi «सूत्री विभाजन या मध्यवर्तीय विभाजन केवल द्विगुणित कोशिकाओं…»
- ch 10 §10.4: hi ¶3 = en ¶3 — similarity 0.42 — en «Meiosis I is initiated after the parental chromosomes…» · hi «S अवस्था में पैतृक गुणसूत्रों के प्रतिकृति के…»
- ch 10 §10.4.1: hi ¶6 = en ¶4 — similarity 0.44 — en «The final stage of meiotic prophase I is…» · hi «अर्धसूत्री पूर्वावस्था I की अंतिम अवस्था पारगतिक्रम (डायाकाइनेसिस)…»
- ch 10 §10.4.1: hi ¶7 = en ¶5 — similarity 0.33 — en «Metaphase I: The bivalent chromosomes align on the…» · hi «मध्यावस्था I : युगली गुणसूत्र मध्यरेखा पट्टिका पर…»
- ch 10 §10.4.1: hi ¶8 = en ¶6 — similarity 0.31 — en «Anaphase I: The homologous chromosomes separate, while sister…» · hi «पश्चावस्था I : समजात गुणसूत्र पृथक् हो जाते…»
- ch 10 §10.4.2: hi ¶2 = en ¶2 — similarity 0.41 — en «Metaphase II: At this stage, the chromosomes align…» · hi «मध्यावस्था II : इस अवस्था में गुणसूत्र मध्यांश…»
- ch 10 §10.4.2: hi ¶3 = en ¶3 — similarity 0.39 — en «Anaphase II: It begins with the simultaneous splitting…» · hi «पश्चावस्था II : इस अवस्था में गुणसूत्रबिंदु अलग…»
- ch 11 §11.3: hi ¶2 = en ¶2 — similarity 0.43 — en «You would recollect from previous unit that the…» · hi «आपने पिछली इकाई में पढ़ा होगा कि पत्तियों…»
- ch 14 §14.2.1: hi ¶5 = en ¶5 — similarity 0.41 — en «By adding up a few respiratory volumes described…» · hi «ऊपर वर्णित कुछ श्वसन संबंधी आयतनों को जोड़कर…»
- ch 15 §15.3.3: hi ¶2 = en ¶3 — similarity 0.36 — en «The P-wave represents the electrical excitation (or depolarisation)…» · hi «हृदय क्रियाओं के विस्तृत मूल्यांकन के लिए कई…»
- ch 16 §16.1: hi ¶3 = en ¶3 — similarity 0.44 — en «The renal tubule begins with a double walled…» · hi «वृक्क नलिका दोहरी झिल्ली युक्त प्यालेनुमा बोमेन संपुट…»
- ch 16 §16.1: hi ¶7 = en ¶5 — similarity 0.43 — en «The efferent arteriole emerging from the glomerulus forms…» · hi «गुच्छ से निकलने वाली अपवाही धमनिका, वृक्कीय नलिका…»
- ch 16 §16.2: hi ¶3 = en ¶4 — similarity 0.33 — en «The kidneys have built-in mechanisms for the regulation…» · hi «गुच्छ निस्यंदन की दर के नियमन के लिए…»
- ch 16 §16.8: hi ¶1 = en ¶1 — similarity 0.43 — en «Malfunctioning of kidneys can lead to accumulation of…» · hi «वृक्कों की कुसंक्रिया के फलस्वरूप रक्त में यूरिया…»
- ch 16 §16.8: hi ¶2 = en ¶2 — similarity 0.38 — en «Kidney transplantation is the ultimate method in the…» · hi «वृक्क की क्रियाहीनता को दूर करने का अंतिम…»
- ch 17 §17.1: hi ¶3 = en ¶3 — similarity 0.43 — en «Ciliary movement occurs in most of our internal…» · hi «हमारे अधिकांश नलिकाकार अंगों में, जो पक्ष्माभ उपभित्ति…»
- ch 17 §17.4: hi ¶3 = en ¶3 — similarity 0.42 — en «In cartilaginous joints, the bones involved are joined…» · hi «उपास्थि युक्त जोड़ों में, अस्थियाँ आपस में उपास्थियों…»
- ch 19 §19.2.2: hi ¶4 = en ¶3 — similarity 0.44 — en «An impairment affecting synthesis or release of ADH…» · hi «मेलानोसाइट प्रेरक हार्मोन, मेलानोसाइट्स (मेलानीन युक्त कोशिकाओं) पर…»

## numbers one edition of a pair carries and the other does not

- ch 1 §1.2.7 ¶2: only in en 1.1 · only in hi —
- ch 2 §2 ¶6: only in en 3, 4 · only in hi —
- ch 2 §2.1.2 ¶3: only in en 2.3 · only in hi —
- ch 2 §2.3.1 ¶1: only in en 2.5 · only in hi —
- ch 2 §2.4 ¶1: only in en 8 · only in hi 3
- ch 2 §2.4 ¶2: only in en — · only in hi 2
- ch 2 §2.6 ¶3: only in en 2.6 · only in hi —
- ch 2 §2.6 ¶4: only in en 2.6 · only in hi —
- ch 3 §3.2 ¶3: only in en — · only in hi 3.2
- ch 3 §3.3 ¶1: only in en — · only in hi 350
- ch 3 §3.4 ¶1: only in en 2 · only in hi —
- ch 4 §4.1.4 ¶1: only in en 4.3, 4.3 · only in hi —
- ch 4 §4.2.11 ¶1: only in en — · only in hi 4.1
- ch 6 §6.2.4 ¶1: only in en — · only in hi 6.4
- ch 7 §7.2.2 ¶6: only in en — · only in hi 10
- ch 7 §7.2.2 ¶9: only in en 2500, 3000 · only in hi 2, 500, 3, 000
- ch 8 §8.1 ¶1: only in en — · only in hi 1, 2
- ch 8 §8.5.4 ¶1: only in en 1.0 · only in hi 1
- ch 8 §8.5.5 ¶3: only in en 1 · only in hi —
- ch 8 §8.5.6 ¶2: only in en 80, 70, 8.9, 60, 40, 50, 30 · only in hi —
- ch 8 §8.5.10 ¶4: only in en — · only in hi 46, 23, 12
- ch 9 §9.1 ¶3: only in en — · only in hi 21, 21, 3
- ch 9 §9.1 ¶5: only in en — · only in hi 3, 3, 2
- ch 9 §9.4 ¶1: only in en 9.3 · only in hi 9.2
- ch 9 §9.6 ¶2: only in en 2 · only in hi —
- ch 9 §9.7 ¶1: only in en 3 · only in hi —
- ch 9 §9.7 ¶2: only in en 4 · only in hi —
- ch 9 §9.8 ¶1: only in en — · only in hi 9.3, 0, 0, 0
- ch 9 §9.8.5 ¶2: only in en — · only in hi 1, 1, 1
- ch 9 §9.8.5 ¶3: only in en — · only in hi 1, 1, 1
- ch 9 §9.8.5 ¶7: only in en 2 · only in hi —
- ch 10 §10.1.1 ¶1: only in en 24, 90 · only in hi —
- ch 10 §10.1.1 ¶2: only in en — · only in hi 1, 2
- ch 10 §10.1.1 ¶3: only in en 24 · only in hi —
- ch 10 §10.1.1 ¶5: only in en — · only in hi 1
- ch 10 §10.2.5 ¶1: only in en 10.2 · only in hi 10, 2
- ch 11 §11.2 ¶6: only in en 2 · only in hi —
- ch 11 §11.6.3 ¶7: only in en — · only in hi 12
- ch 11 §11.7 ¶4: only in en 4 · only in hi —
- ch 11 §11.7.1 ¶1: only in en 3 · only in hi —
- ch 11 §11.7.1 ¶2: only in en 5 · only in hi 1
- ch 11 §11.8 ¶1: only in en — · only in hi 4
- ch 11 §11.9 ¶1: only in en 2 · only in hi —
- ch 11 §11.9 ¶2: only in en — · only in hi 2
- ch 11 §11.9 ¶3: only in en 2, 2, 2 · only in hi 3
- ch 11 §11.9 ¶6: only in en 11.1 · only in hi —
- ch 12 §12.1 ¶1: only in en 2, 2 · only in hi —
- ch 12 §12.2 ¶4: only in en 2, 2, 2 · only in hi —
- ch 12 §12.3 ¶1: only in en 2 · only in hi —
- ch 12 §12.3 ¶2: only in en — · only in hi 7
- ch 12 §12.3 ¶3: only in en 2, 2 · only in hi —
- ch 12 §12.4.1 ¶2: only in en 2, 2 · only in hi —
- ch 12 §12.4.2 ¶1: only in en 2, 2 · only in hi —
- ch 12 §12.4.2 ¶2: only in en 3 · only in hi —
- ch 12 §12.4.2 ¶3: only in en 1, 0 · only in hi —
- ch 12 §12.5 ¶7: only in en 2 · only in hi —
- ch 13 §13.1.4 ¶1: only in en — · only in hi 13.4
- ch 13 §13.1.4 ¶7: only in en 1, 1 · only in hi —
- ch 13 §13.4.1 ¶1: only in en 3, 6 · only in hi —
- ch 13 §13.4.2 ¶2: only in en 1926 · only in hi —
- ch 14 §14.4.1 ¶1: only in en 2, 2 · only in hi —
- ch 14 §14.4.2 ¶1: only in en — · only in hi 2
- ch 14 §14.4.2 ¶2: only in en 2, 14.4 · only in hi 17.4
- ch 15 §15.1.2 ¶2: only in en 3 · only in hi 50, 50
- ch 15 §15.1.2 ¶3: only in en 3 · only in hi —
- ch 15 §15.1.2 ¶4: only in en 1, 500, 500, 00, 00, 3, 3 · only in hi 1.5, 3.5
- ch 15 §15.1.3.1 ¶1: only in en 15.1 · only in hi —
- ch 15 §15.3 ¶2: only in en 2, 3, 4 · only in hi —
- ch 15 §15.3.2 ¶2: only in en 5000 · only in hi 5
- ch 15 §15.4 ¶2: only in en 15.4, 2 · only in hi —
- ch 15 §15.6 ¶1: only in en 140, 90 · only in hi —
- ch 16 §16.1 ¶2: only in en — · only in hi 10
- ch 16 §16.2 ¶1: only in en — · only in hi 3
- ch 16 §16.3 ¶3: only in en 3, 3 · only in hi —
- ch 16 §16.4 ¶1: only in en 1, 1 · only in hi —
- ch 17 §17.1 ¶1: only in en — · only in hi 8
- ch 17 §17.2 ¶1: only in en 8 · only in hi —
- ch 17 §17.3 ¶3: only in en — · only in hi 7
- ch 17 §17.3 ¶4: only in en 2 · only in hi —
- ch 17 §17.3 ¶5: only in en 14 · only in hi —
- ch 18 §18.4 ¶1: only in en 24 · only in hi —
- ch 18 §18.4 ¶2: only in en — · only in hi 3
- ch 19 §19.2.5 ¶2: only in en — · only in hi 2, 2
- ch 19 §19.2.8 ¶1: only in en 1, 2 · only in hi 10, 20
- ch 19 §19.4 ¶2: only in en — · only in hi 2

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.2.5 ¶1 — en p7, hi p7, similarity 0.67
- en: This category includes related orders. For example, order Primata comprising monkey, gorilla and gibbon is placed in class Mammalia along with order Carnivora that includes animals like tiger, cat and dog. Class Mammalia has other orders also.
- hi: इस संवर्ग में संबंधित गण आते हैं। उदाहरणार्थ प्राइमेटा गण जिसमें बंदर, गोरिला तथा गिब्बॉन आते हैं, और कारनीवोरा गण जिसमें बाघ, बिल्ली तथा कुत्ता आते हैं, को मैमेलिया वर्ग में रखा गया है। इसके अतिरिक्त मैमेलिया वर्ग में अन्य गण भी आते हैं।

### 2. ch 1 §1.2.7 ¶4 — en p8, hi p8, similarity 0.57 — the founder reads this one
- en: Table 1.1 indicates the taxonomic categories to which some common organisms like housefly, man, mango and wheat belong.
- hi: तालिका 1.1 में कुछ सामान्य जीवों जैसे घरेलू मक्खी, मानव, आम तथा गेहूँ के विभिन्न वर्गिकी संवर्गों को दिखाया गया है।

### 3. ch 2 §2 ¶1 — en p1, hi p1, similarity 0.61
- en: Since the dawn of civilisation, there have been many attempts to classify living organisms. It was done instinctively not using criteria that were scientific but borne out of a need to use organisms for our own use – for food, shelter and clothing. Aristotle was the earliest to attempt a more scientific basis for classification. He used simple morphological characters to classify plants into trees, shrubs and herbs. He also divided animals into two groups, those which had red blood and those that did not.
- hi: सभ्यता के प्रारंभ से ही मानव ने सजीव प्राणियों के वर्गीकरण के अनेक प्रयास किए हैं। वर्गीकरण के ये प्रयास वैज्ञानिक मानदंडों की जगह सहज बुद्धि पर आधारित हमारे भोजन, वस्त्र एवं आवास जैसी सामान्य उपयोगिता के वस्तुओं के उपयोग की आवश्यकताओं पर आधारित थे। इन प्रयासों में जीवों के वर्गीकरण के वैज्ञानिक मानदंडों का उपयोग सर्वप्रथम अरस्तू ने किया था। उन्होंने पादपों को सरल आकारिक लक्षणों के आधार पर वृक्ष, झाड़ी एवं शाक में वर्गीकृत किया था। जबकि उन्होंने प्राणियों का वर्गीकरण लाल रक्त की उपस्थिति अथवा अनुपस्थिति के आधार पर किया था।

### 4. ch 2 §2.1.1 ¶1 — en p4, hi p4, similarity 0.59 — the founder reads this one
- en: These bacteria are special since they live in some of the most harsh habitats such as extreme salty areas (halophiles), hot springs (thermoacidophiles) and marshy areas (methanogens). Archaebacteria differ from other bacteria in having a different cell wall structure and this feature is responsible for their survival in extreme conditions. Methanogens are present in the gut of several ruminant animals such as cows and buffaloes and they are responsible for the production of methane (biogas) from the dung of these animals.
- hi: ये विशिष्ट प्रकार के बैक्टीरिया होते हैं, ये बैक्टीरिया अत्यंत कठिन वास स्थानों, जैसे-अत्यंत लवणीय क्षेत्र (हैलोफी), गर्म झरने (थर्मोएसिडोफिलस) एवं कच्छ क्षेत्र (मैथेनोजेन) में पाए जाते हैं। आद्य बैक्टीरिया तथा अन्य बैक्टीरिया की कोशिका भित्ति की संरचना एक दूसरे से भिन्न होती है। यही लक्षण उन्हें प्रतिकूल अवस्थाओं में जीवित रखने के लिए उत्तरदायी हैं। मैथेनोजेन अनेक रूमिनेंट पशुओं (जैसे गाय एवं भैंस) के आंत्र में पाए जाते हैं तथा इनके गोबर से मिथेन (जैव गैस) का उत्पादन करते हैं।

### 5. ch 2 §2.2 ¶1 — en p5, hi p5, similarity 0.58
- en: All single-celled eukaryotes are placed under Protista, but the boundaries of this kingdom are not well defined. What may be ‘a photosynthetic protistan’ to one biologist may be ‘a plant’ to another. In this book we include Chrysophytes, Dinoflagellates, Euglenoids, Slime moulds and Protozoans under Protista. Members of Protista are primarily aquatic. This kingdom forms a link with the others dealing with plants, animals and fungi. Being eukaryotes, the protistan cell body contains a well defined nucleus and other membrane-bound organelles. Some have flagella or cilia. Protists reproduce asexually and sexually by a process involving cell fusion and zygote formation.
- hi: सभी एककोशिक यूकैरियोटिक को प्रोटिस्टा के अंतर्गत रखा गया है, परंतु इस जगत की सीमाएं ठीक तरह से निर्धारित नहीं हो पाई हैं। एक जीव वैज्ञानिक के लिए जो 'प्रकाशसंश्लेषी प्रोटिस्टा' है, वही दूसरे के लिए 'एक पादप' हो सकता है। क्राइसोफाइट, डायनोफ्लैजिलेट, युग्लीनॉइड, अवपंक कवक एवं प्रोटोजोआ सभी को इस पुस्तक में प्रोटिस्टा के अंतर्गत रखा गया है। प्राथमिक रूप से प्रोटिस्टा के सदस्य जलीय होते हैं। यूकैरियोटिक होने के कारण इनकी कोशिका में एक सुसंगठित केंद्रक एवं अन्य झिल्लीबद्ध कोशिकांग पाए जाते हैं। कुछ प्रोटिस्टा में कशाभ एवं पक्ष्माभ भी पाए जाते हैं। ये अलैंगिक, तथा कोशिका संलयन एवं युग्मनज (जाइगोट) बनने की विधि द्वारा लैंगिक प्रजनन करते हैं।

### 6. ch 2 §2.2.4 ¶1 — en p6, hi p6, similarity 0.47
- en: Slime moulds are saprophytic protists. The body moves along decaying twigs and leaves engulfing organic material. Under suitable conditions, they form an aggregation called plasmodium which may grow and spread over several feet. During unfavourable conditions, the plasmodium differentiates and forms fruiting bodies bearing spores at their tips. The spores possess true walls. They are extremely resistant and survive for many years, even under adverse conditions. The spores are dispersed by air currents.
- hi: अवपंक कवक मृतपोषी प्रोटिस्टा हैं। ये सड़ती हुई टहनियों तथा पत्तों के साथ गति करते हुए जैविक पदार्थों का भक्षण करते हैं। अनुकूल परिस्थितियों में ये समूह (प्लाज्मोडियम) बनाते हैं, जो कई फीट तक की लंबाई का हो सकता है। प्रतिकूल परिस्थितियों में ये बिखरकर सिरों पर बीजाणुयुक्त फलनकाय बनाते हैं। इन बीजाणुओं का परिक्षेपण वायु के साथ होता है।

### 7. ch 3 §3.1.2 ¶1 — en p4–5, hi p5, similarity 0.54
- en: The members of phaeophyceae or brown algae are found primarily in marine habitats. They show great variation in size and form. They range from simple branched, filamentous forms (Ectocarpus) to profusely branched forms as represented by kelps, which may reach a height of 100 metres. They possess chlorophyll a, c, carotenoids and xanthophylls. They vary in colour from olive green to various shades of brown depending upon the amount of the xanthophyll pigment, fucoxanthin present in them. Food is stored as complex carbohydrates, which may be in the form of laminarin or mannitol. The vegetative cells have a cellulosic wall usually covered on the outside by a gelatinous coating of algin. The protoplast contains, in addition to plastids, a centrally located vacuole and nucleus. The plant body is usually attached to the substratum by a holdfast, and has a stalk, the stipe and leaf like photosynthetic organ – the frond. Vegetative reproduction takes place by fragmentation. Asexual reproduction in most brown algae is by biflagellate zoospores that are pear-shaped and have two unequal laterally attached flagella.
- hi: फीयोफाइसी अथवा भूरे शैवाल मुख्यतः समुद्री आवास में पाए जाते हैं। उनके माप तथा आकार में बहुत विभिन्नताएं होती हैं। ये सरल शाखित, तंतुमयी (एक्टोकार्पस) से लेकर सघन शाखित जैसे केल्प तक हो सकते हैं। केल्प की ऊँचाई 100 मीटर तक हो सकती है। इनमें क्लोरोफ़िल a, c, कैरोटिनॉइड तथा जैंथोफिल होता है। इनका रंग जैतूनी हरे से लेकर भूरे के विभिन्न शेड तक हो सकता है। ये शेड जैंथोफिल वर्णक, फ्युकोजैंथिन की मात्रा पर निर्भर करते हैं। इनमें जटिल कार्बोहाइड्रेट के रूप में भोजन संचित होता है। यह भोजन लैमिनेरिन अथवा मैनीटोल के रूप में हो सकता है। कायिक कोशिका में सेल्यूलोज से बनी कोशिका भित्ति होती है जिसके बाहर की ओर एल्जिन का जिलैटिनी अस्तर होता है। प्रोटोप्लास्ट में लवक के अतिरिक्त केंद्र में रसधानी तथा केंद्रक होते हैं। पौधा प्रायः संलग्नक द्वारा अधःस्तर (स्बस्ट्रेटम) से जुड़ा रहता है और इसमें एक वृंत तथा पत्ती की तरह का प्रकाश-संश्लेषी अंग होता है। इसमें कायिक जनन विखंडन विधि द्वारा होता है। अलैंगिक जनन नाशपाती के आकार वाले दो फ्लैजिला युक्त जूस्पोर द्वारा होता है। इसके फ्लैजिला असमान होते हैं तथा वे पार्श्वीय रूप से जुड़े होते हैं।

### 8. ch 3 §3.4 ¶2 — en p11, hi p11, similarity 0.45 — the founder reads this one
- en: The gymnosperms are heterosporous; they produce haploid microspores and megaspores. The two kinds of spores are produced within sporangia that are borne on sporophylls which are arranged spirally along an axis to form lax or compact strobili or cones. The strobili bearing microsporophylls and microsporangia are called microsporangiate or male strobili. The microspores develop into a male gametophytic generation which is highly reduced and is confined to only a limited number of cells. This reduced gametophyte is called a pollen grain. The development of pollen grains take place within the microsporangia. The cones bearing megasporophylls with ovules or megasporangia are called macrosporangiate or female strobili. The male or female cones or strobili may be borne on the same tree (Pinus). However, in cycas male cones and megasporophylls are borne on different trees. The megaspore mother cell is differentiated from one of the cells of the nucellus. The nucellus is protected by envelopes and the composite structure is called an ovule. The ovules are borne on megasporophylls which may be clustered to form the female cones. The megaspore mother cell divides meiotically to form four megaspores. One of the megaspores enclosed within the megasporangium develops into a multicellular female gametophyte that bears two or more archegonia or female sex organs. The multicellular female gametophyte is also retained within megasporangium.
- hi: जिम्नोस्पर्म विषम बीजाणु होते हैं; वे अगुणित लघुबीजाणु तथा वृहद् बीजाणु बनाते हैं। बीजाणुधानी में दो प्रकार के बीजाणु उत्पन्न होते हैं। बीजाणुधानी बीजाणुपर्ण पर होते हैं। बीजाणुपर्ण सर्पिल की तरह तने पर लगे रहते हैं। ये शलथ अथवा सघन शंकु बनाते हैं। शंकु जिन पर लघुबीजाणुपर्ण तथा लघुबीजाणुधानी होती हैं; उन्हें लघुबीजाणुधानिक अथवा नरशंकु कहते हैं। प्रत्येक लघुबीजाणु से नर युग्मकोद्भिद् संतति उत्पन्न होती है, जो बहुत ही न्यूनीकृत होती है और यह कुछ ही कोशिकाओं में सीमित रहती हैं। इस न्यूनीकृत नर युग्मकोद्भिद् को परागकण कहते हैं। परागकणों का विकास लघुबीजाणुधानी में होता है। जिस शंकु पर गुरु बीजाणुपर्ण तथा गुरु बीजाणुधानी होती है; उन्हें गुरु बीजाणुधानिक अथवा मादा शंकु कहते हैं। दो प्रकार के नर अथवा मादा शंकु एक ही वृक्ष (पाइनस) अथवा विभिन्न वृक्षों पर (साइकैस) पर स्थित हो सकते हैं। गुरु बीजाणु मातृ कोशिका बीजांड काय की एक कोशिका से विभेदित हो जाता है। बीजांडकाय एक अस्तर द्वारा सुरक्षित रहता है और इस सघन रचना को बीजांड कहते हैं। बीजांड गुरु बीजाणुपर्ण पर होते हैं, जो एक गुच्छा बनाकर मादा शंकु बनाते हैं। गुरु बीजाणु मातृ कोशिका में मिऑसिस द्वारा चार गुरु बीजाणु बन जाते हैं। गुरु बीजाणुधानी (बीजांडकाय) स्थित अकेला गुरुबीजाणु मादा युग्मकोद्भिद् में विकसित होता है। इसमें दो अथवा दो से अधिक स्त्रीधानी अथवा मादा जनन अंग होते हैं। बहुकोशिक मादा युग्मकोद्भिद् भी गुरु बीजाणुधानी में ही रह जाता है।

### 9. ch 4 §4.1.2 ¶1 — en p2, hi p2, similarity 0.59
- en: Animals can be categorised on the basis of their symmetry. Sponges are mostly asymmetrical, i.e., any plane that passes through the centre does not divide them into equal halves. When any plane passing through the central axis of the body divides the organism into two identical halves, it is called radial symmetry. Coelenterates, ctenophores and echinoderms have this kind of body plan (Figure 4.1a). Animals like annelids, arthropods, etc., where the body can be divided into identical left and right halves in only one plane, exhibit bilateral symmetry (Figure 4.1b).
- hi: प्राणी को सममिति के आधार पर भी श्रेणीबद्ध किया जा सकता है। स्पंज मुख्यतः असममिति होते हैं; अर्थात् किसी भी केंद्रीय अक्ष से गुजरने वाली रेखा इन्हें दो बराबर भागों विभाजित नहीं करती। जब किसी भी केंद्रीय अक्ष से गुजरने वाली रेखा प्राणि के शरीर को दो समरूप भागों में विभाजित करती है तो इसे अरीय सममिति कहते हैं। सीलेंटरेट, टीनोफोर, तथा एकाइनोडर्म में इसी प्रकार की सममिति होती है (चित्र 4.1 अ)। किंतु ऐनेलिड, आर्थोपोड, आदि में एक ही अक्ष से गुजरने वाली रेखा द्वारा शरीर दो समरूप दाएं व बाएं भाग में बाँटा जा सकता है। इसे द्विपार्श्व सममिति कहते हैं। (चित्र 4.1 ब)

### 10. ch 4 §4.2.8 ¶2 — en p9, hi p9, similarity 0.65
- en: Examples: Pila (Apple snail), Pinctada (Pearl oyster), Sepia (Cuttlefish), Loligo (Squid), Octopus (Devil fish), Aplysia (Sea-hare), Dentalium (Tusk shell) and Chaetopleura (Chiton).
- hi: उदाहरण– पाइला (सेब घोंघा), पिंकटाडा (मुक्ता शुक्ति), सीपिया (कटलफिश), लोलिगो (स्क्विड), ऑक्टोपस (बेताल मछली), एप्लाइसिया (समुद्री खरगोश), डेन्टेलियम (रद कवचर), कीटोप्लयूरा (काइटन)

### 11. ch 4 §4.2.11.1 ¶2 — en p11, hi p11, similarity 0.58 — the founder reads this one
- en: Examples: Petromyzon (Lamprey) and Myxine (Hagfish).
- hi: उदाहरण– पेट्रोमाइज़ॉन (लैम्प्रे) तथा मिक्सीन (हैग फीश)

### 12. ch 5 §5 ¶1 — en p3, hi p3, similarity 0.56
- en: The wide range in the structure of higher plants will never fail to fascinate us. Even though the angiosperms show such a large diversity in external structure or morphology, they are all characterised by presence of roots, stems, leaves, flowers and fruits.
- hi: यद्यपि एंजियोस्पर्म की आकारिकी अथवा बाह्य संरचना में बहुत विविधता पाई जाती है फिर भी इन उच्च पादपों का विशाल समूह हमें अपनी ओर आकर्षित करता है। इन उच्च पादपों में मूल, स्तंभ, पत्तियाँ, पुष्प तथा फलों की उपस्थिति इसका मुख्य अभिलक्षण है।

### 13. ch 5 §5.3.1 ¶1 — en p6, hi p6, similarity 0.42
- en: The arrangement of veins and the veinlets in the lamina of leaf is termed as venation. When the veinlets form a network, the venation is termed as reticulate (Figure 5.4 b). When the veins run parallel to each other within a lamina, the venation is termed as parallel (Figure 5.4 c). Leaves of dicotyledonous plants generally possess reticulate venation, while parallel venation is the characteristic of most monocotyledons.
- hi: पत्ती पर शिरा तथा शिरिकाओं के विन्यास को शिराविन्यास कहते हैं। जब शिरिकाएँ स्तरिका पर एक जाल-सा बनाती हैं तब उसे जालिका शिराविन्यास कहते हैं (चित्र 5.4 ब)। यह प्रायः द्विबीजपत्री पौधों में मिलता है। जब शिरिकाएँ समानांतर होती हैं उसे समानांतर शिराविन्यास कहते हैं (चित्र 5.4 स)। यह प्रायः एक बीजपत्री पौधों में मिलता है।

### 14. ch 5 §5.5 ¶2 — en p8, hi p8, similarity 0.61
- en: In symmetry, the flower may be actinomorphic (radial symmetry) or zygomorphic (bilateral symmetry). When a flower can be divided into two equal radial halves in any radial plane passing through the centre, it is said to be actinomorphic, e.g., mustard, datura, chilli. When it can be divided into two similar halves only in one particular vertical plane, it is zygomorphic, e.g., pea, gulmohur, bean, Cassia. A flower is asymmetric (irregular) if it cannot be divided into two similar halves by any vertical plane passing through the centre, as in canna.
- hi: सममिति में फूल त्रिज्यसममिति (नियमित) अथवा एकव्याससममित (द्विपार्श्विक) हो सकते हैं। जब किसी फूल को दो बराबर भागों में विभक्त किया जा सके तब उसे त्रिज्यसममिति कहते हैं। इसके उदाहरण हैं सरसों, धतूरा, मिर्च। लेकिन जब फूल को केवल एक विशेष ऊर्ध्वाधर समतल से दो समान भागों में विभक्त किया जाए तो उसे एकव्याससममित कहते हैं। इसके उदाहरण हैं- मटर, गुलमोहर, सेम, केसिया आदि। जब कोई फूल बीच से किसी भी ऊर्ध्वाधर समतल से दो समान भागों में विभक्त न हो सके तो उसे असममिति अथवा अनियमित कहते हैं। जैसे कि केना।

### 15. ch 6 §6.1 ¶1 — en p1, hi p1, similarity 0.61
- en: We were discussing types of tissues based on the types of cells present. Let us now consider how tissues vary depending on their location in the plant body. Their structure and function would also be dependent on location. On the basis of their structure and location, there are three types of tissue systems. These are the epidermal tissue system, the ground or fundamental tissue system and the vascular or conducting tissue system.
- hi: हम अब तक विभिन्न प्रकार के ऊतकों तथा उनमें स्थित कोशिकाओं के प्रकार के आधार पर चर्चा कर रहे थे। आओ, अब हम देखें कि पौधे के विभिन्न स्थानों पर स्थित ऊतक कैसे एक दूसरे से भिन्न होते हैं। उनकी रचना तथा कार्य भी उनकी स्थिति के अनुसार होते हैं। रचना तथा स्थिति के आधार पर ऊतक तंत्र तीन प्रकार का होता है। ये तंत्र हैं- बाह्यत्वचीय ऊतक तंत्र, भरण अथवा मौलिक ऊतक तंत्र, संवहनी ऊतक तंत्र।

### 16. ch 8 §8.2 ¶2 — en p4, hi p4, similarity 0.62 — the founder reads this one
- en: Schleiden and Schwann together formulated the cell theory. This theory however, did not explain as to how new cells were formed. Rudolf Virchow (1855) first explained that cells divided and new cells are formed from pre-existing cells (Omnis cellula-e cellula). He modified the hypothesis of Schleiden and Schwann to give the cell theory a final shape. Cell theory as understood today is: (i) all living organisms are composed of cells and products of cells. (ii) all cells arise from pre-existing cells.
- hi: स्लाइडेन व श्वान ने संयुक्त रूप से कोशिका सिद्धांत को प्रतिपादित किया। यद्यपि इनका सिद्धांत यह बताने में असफल रहा कि नई कोशिकाओं का निर्माण कैसे होता है। पहली बार रडोल्फ बिर्चो (1855) ने स्पष्ट किया कि कोशिका विभाजित होती है और नई कोशिकाओं का निर्माण पूर्व स्थित कोशिकाओं के विभाजन से होता है (ओमनिस सेलुल-इ सेलुला)। इन्होंने स्लाइडेन व श्वान की कल्पना को रूपांतरित कर नई कोशिका सिद्धांत को प्रतिपादित किया। वर्तमान समय के परिप्रेक्ष्य में कोशिका सिद्धांत निम्नवत है: सभी जीव कोशिका व कोशिका उत्पाद से बने होते हैं। सभी कोशिकाएं पूर्व स्थित कोशिकाओं से निर्मित होती हैं।

### 17. ch 8 §8.5.1 ¶1 — en p9, hi p9, similarity 0.57 — the founder reads this one
- en: The detailed structure of the membrane was studied only after the advent of the electron microscope in the 1950s. Meanwhile, chemical studies on the cell membrane, especially in human red blood cells (RBCs), enabled the scientists to deduce the possible structure of plasma membrane.
- hi: वर्ष 1950 के इलेक्ट्रॉन सूक्ष्मदर्शी की खोज के बाद कोशिका झिल्ली की विस्तृत संरचना का ज्ञान संभव हो सका है। इस बीच मनुष्य की लाल रक्तकणिकाओं की कोशिका झिल्ली के रासायनिक अध्ययन के बाद जीवद्रव्यझिल्ली की संभावित संरचना के बारे में जानकारी प्राप्त हो सकी।

### 18. ch 8 §8.5.1 ¶5 — en p10, hi p9, similarity 0.49
- en: An improved model of the structure of cell membrane was proposed by Singer and Nicolson (1972) widely accepted as fluid mosaic model (Figure 8.4). According to this, the quasi-fluid nature of lipid enables lateral movement of proteins within the overall bilayer. This ability to move within the membrane is measured as its fluidity.
- hi: कोशिका झिल्ली का उन्नत नमूना 1972 में सिंगर व निकोल्सन द्वारा प्रतिपादित किया गया जिसे तरल किर्मीर नमूना के रूप में व्यापक रूप से स्वीकार कर किया गया (चित्र 8.4)। इसके अनुसार लिपिड के अर्धतरलीय प्रकृति के कारण फास्फोलिपिड द्विसतह के भीतर प्रोटीन पार्श्विक गति करता है। झिल्ली के भीतर गति करने की क्षमता उसकी तरलता पर निर्भर करती है।

### 19. ch 8 §8.5.1 ¶6 — en p10, hi p10, similarity 0.57 — the founder reads this one
- en: The fluid nature of the membrane is also important from the point of view of functions like cell growth, formation of intercellular junctions, secretion, endocytosis, cell division etc.
- hi: झिल्ली की तरलीय प्रकृति इसके कार्य जैसे- कोशिकावृद्धि, अंतरकोशिकीय संयोजन का निर्माण, स्रवण, अंतकोशिक, कोशिका विभाजन इत्यादि की दृष्टि में महत्वपूर्ण है।

### 20. ch 8 §8.5.6 ¶2 — en p14, hi p15, similarity 0.65
- en: The eukaryotic ribosomes are 80S while the prokaryotic ribosomes are 70S. Each ribosome has two subunits, larger and smaller subunits (Fig 8.9). The two subunits of 80S ribosomes are 60S and 40S while that of 70S ribosomes are 50S and 30S. Here ‘S’ (Svedberg’s Unit) stands for the sedimentation coefficient; it is indirectly a measure of density and size. Both 70S and 80S ribosomes are composed of two subunits.
- hi: यूकैरियोटिक राइबोसोम 80S व प्रोकैरियोटिक राइबोसोम 70S प्रकार के होते हैं। प्रत्येक राइबोसोम में उपइकाइयाँ बड़ी व छोटी उपइकाई होती है। यहाँ पर 'S' (स्वेडवर्ग इकाई) अवसादन गुणांक को प्रदर्शित करता है। यह अपरोक्ष रूप में आकार व घनत्व को व्यक्त करता है। दोनों 70S व 80S राइबोसोम दो उपइकाइयों से बना होता है।

### 21. ch 9 §9.8.5 ¶3 — en p14, hi p14, similarity 0.63
- en: Transferases: Enzymes catalysing a transfer of a group, G (other than hydrogen) between a pair of substrate S and S' e.g., S - G + S' -> S + S' - G
- hi: ट्रांसफरेजेज : एंजाइम क्रियाधारकों के एक जोड़े S व S^1 के बीच एक समूह (हाइड्रोजन के अतिरिक्त) के स्थानातंरण को उत्प्रेरित करते हैं। जैसे- S- G+ S^1 -> S + S^1 G

### 22. ch 9 §9.8.5 ¶7 — en p14, hi p15, similarity 0.62
- en: Ligases: Enzymes catalysing the linking together of 2 compounds, e.g., enzymes which catalyse joining of C-O, C-S, C-N, P-O etc. bonds.
- hi: लाइगेजेज : एंजाइम दो यौगिकों के आपस में जुड़ने को उत्प्रेरित करते हैं, जैसे एंजाइम जो कार्बन-ऑक्सीजन, कार्बन-सल्फर, कार्बन-नाइट्रोजन व फॉस्फोरस-ऑक्सीजन बंधों के निर्माण के लिए उत्प्रेरित करते हैं।

### 23. ch 10 §10.4.1 ¶1 — en p7, hi p7, similarity 0.57 — the founder reads this one
- en: Prophase I: Prophase of the first meiotic division is typically longer and more complex when compared to prophase of mitosis. It has been further subdivided into the following five phases based on chromosomal behaviour, i.e., Leptotene, Zygotene, Pachytene, Diplotene and Diakinesis.
- hi: पूर्वावस्था I : अर्धसूत्री विभाजन I की पूर्वावस्था की तुलना समसूत्री विभाजन की पूर्वावस्था से की जाए तो, यह अधिक लंबी व जटिल होती है। गुणसूत्रों के व्यवहार के आधार पर इसे पाँच प्रावस्थाओं में उपविभाजित किया गया है जैसे-तनुपट्ट (लेप्टोटीन), युग्मपट्ट (जाइगोटीन), स्थूलपट्ट (पैकेटीन), द्विपट्ट (डिप्लोटीन) व पारगतिक्रम (डायकाइनेसिस)।

### 24. ch 11 §11.1 ¶1 — en p3, hi p3, similarity 0.69
- en: Let us try to find out what we already know about photosynthesis. Some simple experiments you may have done in the earlier classes have shown that chlorophyll (green pigment of the leaf), light and CO_2 are required for photosynthesis to occur.
- hi: आइए, पहले यह पता करें कि हम प्रकाश-संश्लेषण के विषय में क्या जानते हैं। पिछली कक्षाओं में आपने कुछ सरल प्रयोग किए होंगे। जिनसे पता लगा होगा कि क्लोरोफिल (पत्तियों का हरा वर्णक), प्रकाश तथा कार्बनडाइऑक्साइड (CO_2) प्रकाश-संश्लेषण के लिए आवश्यक है।

### 25. ch 11 §11.4 ¶1 — en p7, hi p7, similarity 0.64
- en: Looking at plants have you ever wondered why and how there are so many shades of green in their leaves – even in the same plant? We can look for an answer to this question by trying to separate the leaf pigments of any green plant through paper chromatography. A chromatographic separation of the leaf pigments shows that the colour that we see in leaves is not due to a single pigment but due to four pigments: Chlorophyll a (bright or blue green in the chromatogram), chlorophyll b (yellow green), xanthophylls (yellow) and carotenoids (yellow to yellow-orange). Let us now see what roles various pigments play in photosynthesis.
- hi: जब आप किसी पौधे को देख रहे होते हैं तो क्या कभी आश्चर्य हुआ है कि उसी पौधे में पत्तियों के हरे रंग में सूक्ष्म अंतर क्यों और कैसे है? हम इस प्रश्न का उत्तर देने के लिए किसी भी हरे पादप के पर्णवर्णकों को पेपर क्रोमेटोग्राफी (कागज वर्णलेखिकी) द्वारा अलग कर सकते हैं। क्रोमेटोग्राफी से पता लगता है कि पत्तियों में स्थित वर्णक के कारण जो हरापन दिखाई देता है, वह किसी एक वर्णक के कारण नहीं, बल्कि चार वर्णकों: क्लोरोफिल ए (क्रोमेटोग्राफी में चमकीला अथवा नीला हरा), क्लोरोफिल बी (पीला हरा), जैन्थोफिल (पीला) तथा कारटीनोएड (पीले से नारंगी पीले) के कारण होता है। आइए, अब देखें कि प्रकाश-संश्लेषण में विभिन्न वर्णकों की क्या भूमिका है।

### 26. ch 11 §11.4 ¶4 — en p7, hi p8, similarity 0.57
- en: Now look at Figure 11.3b showing the wavelengths at which maximum photosynthesis occurs in a plant. Can you see that the wavelengths at which there is maximum absorption by chlorophyll a, i.e., in the blue and the red regions, also shows higher rate of photosynthesis. Hence, we can conclude that chlorophyll a is the chief pigment associated with photosynthesis. But by looking at Figure 11.3c can you say that there is a complete one-to-one overlap between the absorption spectrum of chlorophyll a and the action spectrum of photosynthesis?
- hi: अब आप चित्र 11.3 (ब) को देखें जिसमें उन तरंगदैर्ध्यों को दिखाया गया है, जहाँ पर पादप में अधिकतम प्रकाश-संश्लेषण होता है। क्या आप देख रहे हैं कि तरंगदैर्घ्य क्लोरोफिल 'ए' अर्थात् नीला तथा लाल क्षेत्र में अवशोषण करता है, उस क्षेत्र में प्रकाश-संश्लेषण की दर भी अधिकतम है। अतः हम कह सकते हैं कि क्लोरोफिल 'ए' प्रकाश-संश्लेषण के लिए एक प्रमुख वर्णक है लेकिन चित्र 11.3(स) देखने पर क्या आप कह सकते हैं कि क्लोरोफिल 'ए' के अवशोषण स्पेक्ट्रम तथा प्रकाश-संश्लेषण के क्रियात्मक स्पेक्ट्रम के बीच पूर्णतः परस्पर व्यापन है?

### 27. ch 11 §11.7.2 ¶2 — en p13, hi p15, similarity 0.65
- en: For ease of understanding, the Calvin cycle can be described under three stages: carboxylation, reduction and regeneration.
- hi: केल्विन चक्र को आसानी से समझने के लिए इसको तीन चरणों - कार्बोक्सिलीकरण (कार्बोक्सीलेशन), रिडक्शन तथा रिजनरेशन में वर्णन करते हैं।

### 28. ch 11 §11.9 ¶1 — en p17, hi p17, similarity 0.71
- en: Let us try and understand one more process that creates an important difference between C_3 and C_4 plants – Photorespiration. To understand photorespiration we have to know a little bit more about the first step of the Calvin pathway – the first CO_2 fixation step. This is the reaction where RuBP combines with CO_2 to form 2 molecules of 3PGA, that is catalysed by RuBisCO. RuBP + CO_2 -> (RuBisCo) 2 × 3PGA
- hi: आइए, हम एक और प्रक्रिया- प्रकाश श्वसन को जानने का प्रयत्न करते हैं, जो C_3 एवं C_4 पौधों में महत्वपूर्ण अंतर करती है। प्रकाश श्वसन समझने के लिए, हमें केल्विन पथ के प्रथम चरण अर्थात् CO_2 स्थिरीकरण के पहले चरण के विषय में कुछ अधिक जानकारी करनी होगी। यह वह अभिक्रिया है जहाँ RuBP कार्बन डाईऑक्साइड से संयोजित कर 3 पीजीए के 2 अणुओं का गठन करता है और एक एंजाइम रिबूलोज विसफोस्फेट कार्बोक्सीलेस ऑक्सीजिनेस (RuBisCO) के द्वारा उत्प्रेरित होता है। RuBP + CO_2 -> 2x3PGA (रुबिस्को)

### 29. ch 13 §13.1.5 ¶1 — en p6, hi p6, similarity 0.68
- en: Why do you not try to write down what you think are necessary conditions for growth? This list may have water, oxygen and nutrients as very essential elements for growth. The plant cells grow in size by cell enlargement which in turn requires water. Turgidity of cells helps in extension growth. Thus, plant growth and further development is intimately linked to the water status of the plant. Water also provides the medium for enzymatic activities needed for growth. Oxygen helps in releasing metabolic energy essential for growth activities. Nutrients (macro and micro essential elements) are required by plants for the synthesis of protoplasm and act as source of energy.
- hi: आप यह लिखने की कोशिश क्यों नहीं करते कि पौधों की वृद्धि के लिए जरूरी चीजें क्या हैं? इस सूची में जल, ऑक्सीजन तथा पोषक तत्व अवश्य होने चाहिए जो वृद्धि के लिए अनिवार्य हैं। पौधों की कोशिकाएं अपने आकार में बड़ी होकर वृद्धि करती है जिसके लिए जल की आवश्यकता होती है। इसलिए एक पादप की वृद्धि और उसका परिवर्धन उसमें पानी की स्थिति या उपलब्धता से जुड़ी है। वृद्धि के लिए आवश्यक एंजाइमों की क्रियाशीलता के लिए जल एक माध्यम उपलब्ध करता है तथा ऑक्सीजन उपाचयी ऊर्जा को मुक्त करने में मदद करती है। पौधों द्वारा पोषकों (स्थूल एवं सूक्ष्म आवश्यक तत्व) की आवश्यकता जीवद्रव्य के संश्लेषण तथा ऊर्जा के स्रोत्र के रूप में काम करने के लिए होती है।

### 30. ch 13 §13.2 ¶1 — en p7, hi p7, similarity 0.50 — the founder reads this one
- en: The cells derived from root apical and shoot-apical meristems and cambium differentiate and mature to perform specific functions. This act leading to maturation is termed as differentiation. During differentiation, cells undergo few to major structural changes both in their cell walls and protoplasm. For example, to form a tracheary element, the cells would lose their protoplasm. They also develop a very strong, elastic, lignocellulosic secondary cell walls, to carry water to long distances even under extreme tension. Try to correlate the various anatomical features you encounter in plants to the functions they perform.
- hi: मूल शिखाग्र विभज्योतक तथा प्ररोह शिखाग्र विभज्योतक से आने वाली कोशिकाएं और कैंबियम विभेदित होती है। तथा विशिष्ट क्रियाकलाप को संपन्न करने के लिए परिपक्व होती है। यह परिपक्वता की ओर अग्रसर होने वाली कार्यवाही विभेदन कहलाती है। वे अपनी कोशिकाभित्ति एवं जीवद्रव्य दोनों में ही या कुछ व्यापक संरचनात्मक बदलावों से गुजरती है। उदाहरणस्वरूप एक वाहिकीय तत्व के बनने में कोशिका अपने जीव द्रव्य को खो देती है और बाद में एक बहुत सुदृढ़ तन्यतापूर्ण लिग्नोसेल्युलोसिक (काष्ठ कोशिका सधानी) द्वितीय कोशिका भित्ति विकसित होती है, जो लंबी दूरी तक सर्वोच्च तनाव में भी जल को वहन करने के लिए उपर्युक्त होता है। आप पौधों के शरीर की विभिन्न रचनात्मक विशिष्टताओं एवं उसकी संबंधित क्रियाशीलता से संबंध स्थापित करने की कोशिश करें।

### 31. ch 14 §14.5 ¶1 — en p10, hi p10–11, similarity 0.66
- en: Human beings have a significant ability to maintain and moderate the respiratory rhythm to suit the demands of the body tissues. This is done by the neural system. A specialised centre present in the medulla region of the brain called respiratory rhythm centre is primarily responsible for this regulation. Another centre present in the pons region of the brain called pneumotaxic centre can moderate the functions of the respiratory rhythm centre. Neural signal from this centre can reduce the duration of inspiration and thereby alter the respiratory rate. A chemosensitive area is situated adjacent to the rhythm centre which is highly sensitive to CO_2 and hydrogen ions. Increase in these substances can activate this centre, which in turn can signal the rhythm centre to make necessary adjustments in the respiratory process by which these substances can be eliminated. Receptors associated with aortic arch and carotid artery also can recognise changes in CO_2 and H^+ concentration and send necessary signals to the rhythm centre for remedial actions. The role of oxygen in the regulation of respiratory rhythm is quite insignificant.
- hi: मानव में अपने शरीर के ऊतकों की माँग के अनुरूप श्वसन की लय को संतुलित और स्थिर बनाए रखने की एक महत्वपूर्ण क्षमता है। यह नियमन तंत्रिका तंत्र द्वारा संपन्न होता है। मस्तिष्क के मेड्यूला क्षेत्र में एक विशिष्ट श्वसन लयकेंद्र विद्यमान होता है, जो मुख्य रूप से श्वसन के नियमन के लिए उत्तरदायी होता है। मस्तिष्क के पोंस क्षेत्र में एक अन्य केंद्र स्थित होता है जिसे श्वासप्रभावी (श्वास अनुचन) (Pneumotaxic) केंद्र कहते हैं जो श्वसन लयकेंद्र के कार्यों को संयत (सुधार) कर सकता है। इस केंद्र के तंत्रिका संकेत अंतःश्वसन की अवधि को कम कर सकते हैं और इस प्रकार श्वसन दर (Respiratory rate) को परिवर्तित कर सकते हैं। लयकेंद्र के पास एक रसोसंवेदी (Chemosensitive) केंद्र लयकेंद्र के लिए अति संवेदी होता है, जो CO_2 और हाइड्रोजन आयनों के लिए अति संवेदी होता है। इन पदार्थो की वृद्धि से यह केंद्र सक्रिय होकर श्वसन प्रक्रिया में आवश्यक समायोजन करता है, जिससे ये पदार्थ निष्कासित किए जा सकें। महाधमनी चाप (Aortic arch) और ग्रीवा धमनी (Carotid artery) से जुड़ी संवेदी संरचनाएं भी CO_2 और H^+ सांद्रता के परिवर्तन को पहचान सकते हैं तथा उपचारात्मक कार्यवाही हेतु लयकेंद्र को आवश्यक संकेत दे सकते हैं। श्वसन लय के नियमन में ऑक्सीजन की भूमिका बहुत ही महत्वहीन है।

### 32. ch 16 §16.8 ¶4 — en p10, hi p10, similarity 0.49 — the founder reads this one
- en: Glomerulonephritis: Inflammation of glomeruli of kidney.
- hi: ग्लोमेलोनेफ्राइटिस (गुच्छ शोथ): वृक्क के गुच्छ-शोथ की प्रदाहकता।

### 33. ch 17 §17.2 ¶2 — en p2, hi p2, similarity 0.50
- en: Skeletal muscles are closely associated with the skeletal components of the body. They have a striped appearance under the microscope and hence are called striated muscles. As their activities are under the voluntary control of the nervous system, they are known as voluntary muscles too. They are primarily involved in locomotory actions and changes of body postures.
- hi: कंकाल पेशियाँ शारीरिक कंकाल अवयवों के निकट संपर्क में होती हैं। सूक्ष्मदर्शी द्वारा देखने पर इनमें धारियाँ दिखती हैं, अतः इन्हें रेखित पेशी कहते हैं। चूँकि इनकी क्रियाओं का तंत्रिका तंत्र द्वारा ऐच्छिक नियंत्रण होता है, अतः इन्हें ऐच्छिक पेशी भी कहते हैं। ये मुख्य रूप से चलन क्रिया और शारीरिक मुद्रा बदलने में सहायक होती हैं।

### 34. ch 17 §17.3 ¶4 — en p9, hi p9, similarity 0.63
- en: There are 12 pairs of ribs. Each rib is a thin flat bone connected dorsally to the vertebral column and ventrally to the sternum. It has two articulation surfaces on its dorsal end and is hence called bicephalic. First seven pairs of ribs are called true ribs. Dorsally, they are attached to the thoracic vertebrae and ventrally connected to the sternum with the help of hyaline cartilage. The 8th, 9th and 10th pairs of ribs do not articulate directly with the sternum but join the seventh rib with the help of hyaline cartilage. These are called vertebrochondral (false) ribs. Last 2 pairs (11th and 12th) of ribs are not connected ventrally and are therefore, called floating ribs. Thoracic vertebrae, ribs and sternum together form the rib cage (Figure 17.8).
- hi: पसलियों (Ribs) की 12 जोड़ियाँ होती हैं। प्रत्येक पसली एक पतली चपटी अस्थि है जो पृष्ठ भाग में कशेरुक दंड और अधर भाग में उरोस्थि के साथ जुड़ी होती हैं। इसके पृष्ठ सिरे पर दो संधियोजन सतहें होती हैं जिसके कारण इसे द्विशिरस्थ (bicephalic) भी कहते हैं। प्रथम सात जोड़ी पसलियों को वास्तविक पसलियाँ कहते हैं। पृष्ठ में ये वक्षीय कशेरुकों और अधरीय भाग में उरोस्थि से काचाभ उपास्थि (hyaline cartilage) की सहायता से जुड़ी होती हैं। 8वीं, 9वीं और 10वीं जोड़ी-पसलियाँ उरोस्थि के साथ सीधे संधियोजित नहीं होतीं, बल्कि काचाभ उपास्थि के सहयोग से सातवीं पसली से जुड़ती हैं। इन्हें वर्टिब्रोकांड्रल (कूट) पसलियाँ कहते हैं। पसलियों की अंतिम दो जोड़ियाँ (11वीं और 12वीं) अधर में जुड़ी हुई नहीं होतीं, इसलिए उन्हें प्लावी पसलियाँ (floating ribs) कहते हैं। वक्षीय कशेरुक, पसलियाँ और उरोस्थि मिलकर पसली पंजर (rib cage) की संरचना करते हैं (चित्र 17.8)।

### 35. ch 17 §17.3 ¶7 — en p10, hi p10, similarity 0.61
- en: Pelvic girdle consists of two coxal bones (Figure 17.10). Each coxal bone is formed by the fusion of three bones – ilium, ischium and pubis. At the point of fusion of the above bones is a cavity called acetabulum to which the thigh bone articulates. The two halves of the pelvic girdle meet ventrally to form the pubic symphysis containing fibrous cartilage.
- hi: श्रोणि मेखला (Pelvic girdle) में दो श्रोणि अस्थियाँ होती हैं (चित्र 17.10)। प्रत्येक श्रोणि अस्थि तीन अस्थियों के संलयन से बनी होती है- इलियम, इस्चियम और प्युबिस। इन अस्थियों के संयोजन स्थल पर एक गुहा एसिटैबुलम होती है जिससे उरु अस्थि संधियोजन करती है। अधर भाग में श्रोणि मेखला के दोनों भाग मिलकर प्युबिक संलयन (Pubic symphysis) बनाते हैं जिसमें रेशेदार उपास्थि होती है।

### 36. ch 18 §18 ¶1 — en p1, hi p1, similarity 0.53
- en: As you know, the functions of the organs/organ systems in our body must be coordinated to maintain homeostasis. Coordination is the process through which two or more organs interact and complement the functions of one another. For example, when we do physical exercises, the energy demand is increased for maintaining an increased muscular activity. The supply of oxygen is also increased. The increased supply of oxygen necessitates an increase in the rate of respiration, heart beat and increased blood flow via blood vessels. When physical exercise is stopped, the activities of nerves, lungs, heart and kidney gradually return to their normal conditions. Thus, the functions of muscles, lungs, heart, blood vessels, kidney and other organs are coordinated while performing physical exercises. In our body the neural system and the endocrine system jointly coordinate and integrate all the activities of the organs so that they function in a synchronised fashion.
- hi: जैसा कि तुम जानते हो मानव शरीर में बहुत से अंग एवं अंग तंत्र पाए जाते हैं जो कि स्वतंत्र रूप से कार्य करने में असमर्थ होते हैं। जैव स्थिरता (समअवस्था) बनने हेतु इन अंगों के कार्यों में समन्वय अत्यधिक आवश्यक है। समन्वयता एक ऐसी क्रियाविधि है, जिसके द्वारा दो या अधिक अंगों में क्रियाशीलता बढ़ती है व एक-दूसरे अंगों के कार्यों में मदद मिलती है। उदाहरणार्थ, जब हम शारीरिक व्यायाम करते हैं तो पेशियों के संचालन हेतु ऊर्जा की आवश्यकता भी बढ़ जाती है। ऑक्सीजन की आवश्यकता में भी वृद्धि हो जाती है। ऑक्सीजन की अधिक आपूर्ति के लिए श्वसन दर, हृदय स्पंदन, दर एवं वृक्क वाहिनियों में रक्त प्रवाह की दर बढ़ना स्वाभाविक हो जाता है। जब शारीरिक व्यायाम बंद कर देते हैं तो तंत्रिकीय क्रियाएं, फुप्फुस, हृदय रुधिर वाहिनियों, वृक्क व अन्य अंगों के कार्यों में समन्वय स्थापित हो जाता है। हमारे शरीर में तंत्रिका तंत्र एवं अंतःस्रावी तंत्र सम्मिलित रूप से अन्य अंगों की क्रियाओं में समन्वय करते हैं तथा उन्हें एकीकृत करते हैं, जिससे सभी क्रियाएं एक साथ संचालित होती रहती हैं।

### 37. ch 19 §19.1 ¶1 — en p1, hi p1, similarity 0.51
- en: Endocrine glands lack ducts and are hence, called ductless glands. Their secretions are called hormones. The classical definition of hormone as a chemical produced by endocrine glands and released into the blood and transported to a distantly located target organ has current scientific definition as follows: Hormones are non-nutrient chemicals which act as intercellular messengers and are produced in trace amounts. The new definition covers a number of new molecules in addition to the hormones secreted by the organised endocrine glands. Invertebrates possess very simple endocrine systems with few hormones whereas a large number of chemicals act as hormones and provide coordination in the vertebrates. The human endocrine system is described here.
- hi: अंतःस्रावी ग्रंथियों में नलिकाएं नहीं होती हैं अतः वे नलिकाविहीन ग्रंथियां कहलाती हैं। इनके स्राव हार्मोन कहलाते हैं। हार्मोन की चिरसम्मत परिभाषा के अनुसार 'हार्मोन अंतःस्रावी ग्रंथियों द्वारा स्रवित रक्त में मुक्त किए जाने वाले रसायन हैं, जो दूरस्थ लक्ष्य अंग तक पहुँचाए जाते हैं।' परंतु इस परिभाषा को अब रूपांतरित किया गया है जिसके अनुसार 'हार्मोन सूक्ष्म मात्रा में उत्पन्न होने वाले अपोषक रसायन हैं जो अंतरकोशिकीय संदेशवाहक के रूप में कार्य करते हैं' इस नई परिभाषा के अंतर्गत सुनियोजित अंतःस्रावी ग्रंथियों से स्रावित हार्मोन के अतिरिक्त कई नये अणु भी सम्मिलित हो जाते हैं। अकशेरुकियों में कम हार्मोन के साथ एक सरल अंतःस्रावी तंत्र होता है जबकि कशेरूकियों में कई रसायन हार्मोन की तरह कार्य कर उनमें समन्वय स्थापित करते हैं। यहाँ मानव अंतःस्रावी तंत्र का वर्णन किया गया है।

### 38. ch 19 §19.2.2 ¶3 — en p4, hi p4, similarity 0.44
- en: An impairment affecting synthesis or release of ADH results in a diminished ability of the kidney to conserve water leading to water loss and dehydration. This condition is known as Diabetes Insipidus.
- hi: मेलानोसाइट प्रेरक हार्मोन, मेलानोसाइट्स (मेलानीन युक्त कोशिकाओं) पर क्रियाशील होता है तथा त्वचा की वर्णकता का नियमन करता है। ऑक्सीटॉसिन हमारे शरीर की चिकनी पेशियों पर कार्य करता है और उनके संकुचन को प्रेरित करता है। मादाओं में यह प्रसव के समय गर्भाशयी पेशियों के संकुचन और दुग्ध ग्रंथियों से दूध के स्राव को प्रेरित करता है। वेसोप्रेसिन मुख्यतः वृक्क की दूरस्थ संवलित नलिका से जल एवं आयनों के पुनरावशोषण को प्रेरित करता है, जिससे मूत्र के साथ जल का ह्रास (डाइयूरेसिस) कम हो। अतः इसे प्रतिमूत्रल हार्मोन या एंटी-डाइयूरेटिक हार्मोन (ADH) भी कहते हैं। ए.डी.एच. के संश्लेषण अथवा स्रावण को प्रभावित करने वाली विकृति के परिणामस्वरूप वृक्क की जल संरक्षण की क्षमता में ह्रास होता है। फलतः जल का ह्रास एवं निर्जलीकरण हो जाता है। इस अवस्थिति को उदकमेह (डायबिटीज इन्सीपिडस) कहते हैं।

### 39. ch 19 §19.2.7 ¶5 — en p7, hi p7, similarity 0.60
- en: Glucocorticoids stimulate gluconeogenesis, lipolysis and proteolysis; and inhibit cellular uptake and utilisation of amino acids. Cortisol is also involved in maintaining the cardio-vascular system as well as the kidney functions. Glucocorticoids, particularly cortisol, produces anti-inflammatory reactions and suppresses the immune response. Cortisol stimulates the RBC production. Aldosterone acts mainly at the renal tubules and stimulates the reabsorption of Na+ and water and excretion of K+ and phosphate ions. Thus, aldosterone helps in the maintenance of electrolytes, body fluid volume, osmotic pressure and blood pressure. Small amounts of androgenic steroids are also secreted by the adrenal cortex which play a role in the growth of axial hair, pubic hair and facial hair during puberty.
- hi: ग्लूकोकॉर्टिकोइड ग्लाइकोजन संश्लेषण, ग्लूकोनियोजिनेसिस, वसा अपघटन और प्रोटीन अपघटन को प्रेरित करते हैं तथा एमीनो अम्लों के कोशिकीय ग्रहण और उपयोग को अवरोधित करते हैं। कॉर्टिसॉल, हृदय संवहनी तंत्र के रखरखाव तथा वृक्क की क्रियाओं में भी संलग्न होता है। ग्लूकोकॉर्टिकोइड एवं विशेष रूप से कॉर्टिसाल प्रतिशोथ प्रतिक्रियाओं को प्रेरित करता है तथा प्रतिरक्षा तंत्र की प्रतिक्रिया को अवरोधित करता है। कॉर्टिसाल लाल रुधिर कणिकाओं के उत्पादन को प्रेरित करता है। एल्डोस्टीरॉन मुख्यतः वृक्क नलिकाओं पर कार्य करता है और Na^+ एवं जल के पुनरावशोषण तथा K^+ व फॉस्फेट आयन के उत्सर्जन को प्रेरित करता है। इस प्रकार एल्डोस्टीरॉन, वैद्युत अपघट्यों, शरीर द्रव के आयतन, परासरणी दाब और रक्त दाब को बनाए रखने में सहायक होता है। एड्रीनल वल्कुट द्वारा कुछ मात्रा में एंड्रोजेनिक स्टीराइड का भी स्राव होता है जो यौवनारंभ के समय अक्षीय रोम, जघन रोम, तथा मुख (आनन) रोम की वृद्धि में भूमिका अदा करते हैं। एड्रिनल वल्कुट द्वारा हार्मोन के अल्प स्रावण के कारण कार्बोहाइड्रेट उपापचय पर विपरीत प्रभाव पड़ता है जिसके कारण अत्यंत दुर्बलता एवं थकावट का अनुभव होता है तथा एडीसन रोग हो जाता है।

### 40. ch 19 §19.3 ¶1 — en p9, hi p9, similarity 0.57
- en: Now you know about the endocrine glands and their hormones. However, as mentioned earlier, hormones are also secreted by some tissues which are not endocrine glands. For example, the atrial wall of our heart secretes a very important peptide hormone called atrial natriuretic factor (ANF), which decreases blood pressure. When blood pressure is increased, ANF is secreted which causes dilation of the blood vessels. This reduces the blood pressure.
- hi: अब तक आप अंतःस्रावी ग्रंथियों और उनके हार्मोन के बारे में समझ चुके होंगे। यद्यपि पहले इंगित किया जा चुका है कि हार्मोन का स्राव कुछ अन्य अंगों द्वारा भी होता है जो अंतःस्रावी ग्रंथियां नहीं हैं। उदाहरण के लिए हृदय की अलिंद भित्ति द्वारा एक पेप्टाईड हार्मोन का स्राव होता है, जिसे एट्रियल नेट्रियुरेटिक कारक (एएनएफ) कहते हैं। यह रक्त दाब को कम करता है। जब रक्त दाब बढ़ जाता है, तो एएनएफ के स्राव और इसकी क्रिया के फलस्वरूप रक्त वाहिकाएं विस्फारित हो जाती हैं तथा रक्त दाब कम हो जाता है।

# margai-pipeline ncert align

- run: 2026-10-17 14:00 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 748 English and 746 Hindi row(s) in 19 chapter(s)
- result: ok
request id: pipeline-ncert-align-685d63a8-05b6-4847-8d75-7adf27604c7c
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 746 | 329817 | ₹8.17 |

## sections whose rows do not pair one to one

en counts the English rows aligned: a number an `unprinted` entry left before the section's last Hindi row is listed below, not here; one left after it is aligned, and the load's report names it
| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 2 | 2 | 6 | 6 | 2 |
| 4 | 4.2.11.7 | 3 | 2 | 1 |
| 5 | 5.9 | 17 | 17 | 2 |
| 8 | 8.5.10 | 6 | 5 | 1 |
| 14 | 14.2.1 | 10 | 10 | 4 |

## English paragraphs the Hindi does not print — numbers an `unprinted` entry left before the section's last Hindi row, not aligned

none

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 2 §2: hi ¶3 = en ¶3+¶4 (split hi)
- ch 2 §2: hi ¶4+¶5 = en ¶5 (join hi)
- ch 4 §4.2.11.7: en ¶3 has no hindi
- ch 5 §5.9: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.9: hi ¶2 has no english
- ch 8 §8.5.10: hi ¶5 = en ¶5+¶6 (split hi)
- ch 14 §14.2.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 14 §14.2.1: hi ¶3+¶4 = en ¶4 (join hi)
- ch 14 §14.2.1: hi ¶6 = en ¶6+¶7 (split hi)
- ch 14 §14.2.1: hi ¶7+¶8 = en ¶8 (join hi)

## pairs weaker than 0.45 — read both against the pages

- ch 2 §2.3: hi ¶7 = en ¶7 — similarity 0.30 — en «(iii) Meiosis in zygote resulting in haploid spores.» · hi «(iii) युग्मनज में मिऑसिस के कारण अगुणित बीजाणु…»
- ch 2 §2.3: hi ¶9 = en ¶9 — similarity 0.44 — en «The morphology of the mycelium, mode of spore…» · hi «कवक जाल की आकारिकी, बीजाणु बनने तथा फलन…»
- ch 3 §3.1.3: hi ¶3 = en ¶3 — similarity 0.44 — en «The red algae usually reproduce vegetatively by fragmentation.…» · hi «इसमें कायिक जनन विखंडन, अलैंगिक जनन अचल स्पोर…»
- ch 4 §4.2: hi ¶2 = en ¶2 — similarity 0.31 — en «The important characteristic features of the different phyla…» · hi «जिसका वर्णन इस अध्याय के शेष भाग में…»
- ch 4 §4.2.4: hi ¶2 = en ¶2 — similarity 0.43 — en «Examples: Taenia (Tapeworm), Fasciola (Liver fluke).» · hi «उदाहरण– टीनिया (फीताकृमि), फेसियोला (पर्णकृमि)»
- ch 4 §4.2.7: hi ¶4 = en ¶4 — similarity 0.41 — en «Gregarious pest – Locusta (Locust)» · hi «यूथपीड़क टिड्डी (लोकस्टा);»
- ch 4 §4.2.11: hi ¶2 = en ¶2 — similarity 0.36 — en «Table 4.1 presents a comparison of salient features…» · hi «सारणी 4.1 अरज्जुकी एवं रज्जुकी में विशिष्ट लक्षणों…»
- ch 4 §4.2.11: hi ¶6 = en ¶6 — similarity 0.39 — en «The members of subphylum Vertebrata possess notochord during…» · hi «कशेरुकी संघ के प्राणियों में पृष्ठ रज्जु भ्रूणीय…»
- ch 4 §4.2.11: hi ¶7 = en ¶7 — similarity 0.33 — en «The subphylum Vertebrata is further divided as follows:» · hi «उपसंघ वर्टीब्रेटा को पुनः निम्न उपवर्ग में विभाजित…»
- ch 5 §5.3.1: hi ¶1 = en ¶1 — similarity 0.42 — en «The arrangement of veins and the veinlets in…» · hi «पत्ती पर शिरा तथा शिरिकाओं के विन्यास को…»
- ch 5 §5.3.2: hi ¶3 = en ¶3 — similarity 0.42 — en «In palmately compound leaves, the leaflets are attached…» · hi «हस्ताकार संयुक्त पत्तियों में पत्रक एक ही बिंदु…»
- ch 5 §5.7.2: hi ¶1 = en ¶1 — similarity 0.43 — en «Generally, monocotyledonous seeds are endospermic but some as…» · hi «प्रायः एकबीजपत्री बीज भ्रूणपोषी होते हैं लेकिन उनमें…»
- ch 5 §5.9: hi ¶4 = en ¶4 — similarity 0.44 — en «Stem: herbaceous rarely woody, aerial; erect, cylindrical, branched,…» · hi «तना: शाकीय, कभी-कभी काष्ठीय; वायवीय, सीधा, सिलिंडिराकर, शाखित,…»
- ch 5 §5.9: hi ¶5 = en ¶5 — similarity 0.39 — en «Leaves: alternate, simple, rarely pinnately compound, exstipulate; venation…» · hi «पत्तियाँ: एकांतर, सरल, कर्मी संयुक्त पिच्छाकार अननुपर्णी, जालिका…»
- ch 5 §5.9: hi ¶6 = en ¶6 — similarity 0.25 — en «Floral Characters» · hi «पुष्पी अभिलक्षण:»
- ch 5 §5.9: hi ¶7 = en ¶7 — similarity 0.44 — en «Inflorescence : Solitary, axillary or cymose as in…» · hi «पुष्पक्रम: एकल, कक्षीय, ससीमाक्षी जैसे सोलैनम में;»
- ch 5 §5.9: hi ¶8 = en ¶8 — similarity 0.36 — en «Flower: bisexual, actinomorphic» · hi «फूल: उभयलिंगी, त्रिज्यसममिति»
- ch 5 §5.9: hi ¶11 = en ¶11 — similarity 0.25 — en «Androecium: stamens five, epipetalous» · hi «पुमंग: पाँच पुंकेसर, दललग्न»
- ch 5 §5.9: hi ¶12 = en ¶12 — similarity 0.35 — en «Gynoecium: bicarpellary obligately placed, syncarpous; ovary superior, bilocular,…» · hi «जायांग: द्विअंडपी, युक्तांडपी, तिरछी अंडाशय ऊर्ध्वावर्ती, द्विकोष्ठी, बीजांडासन…»
- ch 5 §5.9: hi ¶13 = en ¶13 — similarity 0.38 — en «Fruits: berry or capsule» · hi «फल: संपुट अथवा सरस»
- ch 5 §5.9: hi ¶14 = en ¶14 — similarity 0.40 — en «Seeds: many, endospermous» · hi «बीज: भ्रूणपोषी, अनेक»
- ch 5 §5.9: hi ¶16 = en ¶16 — similarity 0.40 — en «Economic Importance» · hi «आर्थिक महत्व»
- ch 6 §6.1.1: hi ¶2 = en ¶2 — similarity 0.45 — en «The cells of epidermis bear a number of…» · hi «बाह्यत्वचा की कोशिकाओं पर अनेक रोम होते हैं।…»
- ch 6 §6.2.2: hi ¶1 = en ¶1 — similarity 0.41 — en «The anatomy of the monocot root is similar…» · hi «एक बीजपत्री मूल का शारीर बहुत अधिक द्विबीजपत्री…»
- ch 6 §6.2.4: hi ¶1 = en ¶1 — similarity 0.38 — en «The monocot stem has a sclerenchymatous hypodermis, a…» · hi «एकबीजपत्री तने की शारीरिक रचना द्विबीजपत्री तने से…»
- ch 6 §6.2.6: hi ¶3 = en ¶3 — similarity 0.38 — en «The parallel venation in monocot leaves is reflected…» · hi «एक बीजपत्री की पत्तियों में शिरा विन्यास समानांतर…»
- ch 7 §7.2.1: hi ¶1 = en ¶1 — similarity 0.44 — en «Have you ever touched the skin of frog?…» · hi «क्या आपने कभी मेंढक की त्वचा को छुआ…»
- ch 7 §7.2.2: hi ¶1 = en ¶1 — similarity 0.40 — en «The body cavity of frogs accommodate different organ…» · hi «मेंढक की देह गुहा में पाचन तंत्र, श्वसन…»
- ch 8 §8.4.1: hi ¶6 = en ¶6 — similarity 0.45 — en «Besides flagella, Pili and Fimbriae are also surface…» · hi «जीवाणुओं के सतह पर पाई जाने वाली संरचना…»
- ch 8 §8.5.3: hi ¶1 = en ¶1 — similarity 0.43 — en «While each of the membranous organelles is distinct…» · hi «झिल्लीदार अंगक कार्य व संरचना के आधार पर…»
- ch 8 §8.5.3.1: hi ¶1 = en ¶1 — similarity 0.42 — en «Electron microscopic studies of eukaryotic cells reveal the…» · hi «इलेक्ट्रॉन सूक्ष्मदर्शी से अध्ययन के पश्चात् यह पता…»
- ch 8 §8.5.3.1: hi ¶3 = en ¶3 — similarity 0.42 — en «RER is frequently observed in the cells actively…» · hi «जो कोशिकाएं प्रोटीन संश्लेषण एवं स्रवण में सक्रिय…»
- ch 8 §8.5.3.1: hi ¶4 = en ¶4 — similarity 0.43 — en «The smooth endoplasmic reticulum is the major site…» · hi «चिकनी अंतर्द्रव्यी जालिका प्राणियों में लिपिड संश्लेषण के…»
- ch 8 §8.5.3.4: hi ¶2 = en ¶2 — similarity 0.40 — en «In plants, the tonoplast facilitates the transport of…» · hi «पौधों में बहुत से आयन व दूसरे पदार्थ…»
- ch 8 §8.5.9: hi ¶1 = en ¶1 — similarity 0.40 — en «Centrosome is an organelle usually containing two cylindrical…» · hi «तारककाय वह अंगक है जो दो बेलनाकार संरचना…»
- ch 8 §8.5.10: hi ¶2 = en ¶2 — similarity 0.42 — en «The interphase nucleus (nucleus of a cell when…» · hi «अंतरकाल अवस्था केंद्रक (कोशिका केंद्रक जिसका विभाजन नहीं…»
- ch 9 §9.8.2: hi ¶4 = en ¶4 — similarity 0.43 — en «Enzymes eventually bring down this energy barrier making…» · hi «एंजाइम ऊर्जा अवरोध को घटाकर क्रियाधार से उत्पाद…»
- ch 9 §9.8.3: hi ¶2 = en ¶2 — similarity 0.43 — en «The formation of the ES complex is essential…» · hi «एंजाइम-क्रियाधार जटिल (ES) का निर्माण उत्प्रेरण के लिए…»
- ch 10 §10.2.1: hi ¶2 = en ¶2 — similarity 0.41 — en «Chromosomal material condenses to form compact mitotic chromosomes.…» · hi «• गुणसूत्रीय द्रव्य संघनित होकर ठोस गुणसूत्र बन…»
- ch 10 §10.2.2: hi ¶1 = en ¶1 — similarity 0.41 — en «The complete disintegration of the nuclear envelope marks…» · hi «केंद्रक आवरण के पूर्णरूप से विघटित होने के…»
- ch 10 §10.2.3: hi ¶1 = en ¶1 — similarity 0.37 — en «At the onset of anaphase, each chromosome arranged…» · hi «पश्चावस्था के प्रारंभ में मध्यावस्था पट्टिका पर आए…»
- ch 10 §10.2.4: hi ¶1 = en ¶1 — similarity 0.42 — en «At the beginning of the final stage of…» · hi «सूत्री विभाजन की अंतिम अवस्था के प्रारंभ में…»
- ch 10 §10.3: hi ¶1 = en ¶1 — similarity 0.43 — en «Mitosis or the equational division is usually restricted…» · hi «सूत्री विभाजन या मध्यवर्तीय विभाजन केवल द्विगुणित कोशिकाओं…»
- ch 10 §10.4: hi ¶3 = en ¶3 — similarity 0.42 — en «Meiosis I is initiated after the parental chromosomes…» · hi «• S अवस्था में पैतृक गुणसूत्रों के प्रतिकृति…»
- ch 10 §10.4: hi ¶4 = en ¶4 — similarity 0.36 — en «Meiosis involves pairing of homologous chromosomes and recombination…» · hi «• अर्धसूत्री विभाजन में समजात गुणसूत्रों का युगलन…»
- ch 10 §10.4: hi ¶6 = en ¶6 — similarity 0.34 — en «Meiotic events can be grouped under the following…» · hi «अर्धसूत्री विभाजन को निम्न अवस्थाओं में वर्गीकृत किया…»
- ch 10 §10.4.1: hi ¶4 = en ¶4 — similarity 0.44 — en «The final stage of meiotic prophase I is…» · hi «अर्धसूत्री पूर्वावस्था I की अंतिम अवस्था पारगतिक्रम (डायाकाइनेसिस)…»
- ch 10 §10.4.1: hi ¶5 = en ¶5 — similarity 0.33 — en «Metaphase I: The bivalent chromosomes align on the…» · hi «मध्यावस्था I : युगली गुणसूत्र मध्यरेखा पट्टिका पर…»
- ch 10 §10.4.1: hi ¶6 = en ¶6 — similarity 0.31 — en «Anaphase I: The homologous chromosomes separate, while sister…» · hi «पश्चावस्था I : समजात गुणसूत्र पृथक् हो जाते…»
- ch 10 §10.4.2: hi ¶2 = en ¶2 — similarity 0.41 — en «Metaphase II: At this stage, the chromosomes align…» · hi «मध्यावस्था II : इस अवस्था में गुणसूत्र मध्यांश…»
- ch 10 §10.4.2: hi ¶3 = en ¶3 — similarity 0.39 — en «Anaphase II: It begins with the simultaneous splitting…» · hi «पश्चावस्था II : इस अवस्था में गुणसूत्रबिंदु अलग…»
- ch 11 §11.3: hi ¶2 = en ¶2 — similarity 0.43 — en «You would recollect from previous unit that the…» · hi «आपने पिछली इकाई में पढ़ा होगा कि पत्तियों…»
- ch 11 §11.7: hi ¶2 = en ¶2 — similarity 0.43 — en «Can we, hence, say that calling the biosynthetic…» · hi «अतः जैव संश्लेषण चरण को अप्रकाशी अभिक्रिया (डार्क…»
- ch 12 §12.7: hi ¶2 = en ¶2 — similarity 0.43 — en «The respiratory quotient depends upon the type of…» · hi «साँस गुणांक, साँस के दौरान उपयोग में आने…»
- ch 14 §14.2.1: hi ¶5 = en ¶5 — similarity 0.41 — en «By adding up a few respiratory volumes described…» · hi «ऊपर वर्णित कुछ श्वसन संबंधी आयतनों को जोड़कर…»
- ch 15 §15.3.3: hi ¶3 = en ¶3 — similarity 0.44 — en «The P-wave represents the electrical excitation (or depolarisation)…» · hi «पी तरंग को आलिंद के उद्दीपन/विध्रुवण के रूप…»
- ch 16 §16.1: hi ¶5 = en ¶5 — similarity 0.43 — en «The efferent arteriole emerging from the glomerulus forms…» · hi «गुच्छ से निकलने वाली अपवाही धमनिका, वृक्कीय नलिका…»
- ch 16 §16.2: hi ¶4 = en ¶4 — similarity 0.34 — en «The kidneys have built-in mechanisms for the regulation…» · hi «गुच्छ निस्यंदन की दर के नियमन के लिए…»
- ch 16 §16.8: hi ¶1 = en ¶1 — similarity 0.43 — en «Malfunctioning of kidneys can lead to accumulation of…» · hi «वृक्कों की कुसंक्रिया के फलस्वरूप रक्त में यूरिया…»
- ch 16 §16.8: hi ¶2 = en ¶2 — similarity 0.38 — en «Kidney transplantation is the ultimate method in the…» · hi «वृक्क की क्रियाहीनता को दूर करने का अंतिम…»
- ch 17 §17.1: hi ¶3 = en ¶3 — similarity 0.44 — en «Ciliary movement occurs in most of our internal…» · hi «हमारे अधिकांश नलिकाकार अंगों में, जो पक्ष्माभ उपभित्ति…»
- ch 17 §17.4: hi ¶3 = en ¶3 — similarity 0.42 — en «In cartilaginous joints, the bones involved are joined…» · hi «उपास्थि युक्त जोड़ों में, अस्थियाँ आपस में उपास्थियों…»
- ch 18 §18.2: hi ¶3 = en ¶3 — similarity 0.43 — en «The afferent nerve fibres transmit impulses from tissues/organs…» · hi «संवेदी या अभिवाही तंत्रिकाएं उद्दीपनों को ऊतकों/अंगों से…»
- ch 18 §18.3.1: hi ¶2 = en ¶2 — similarity 0.44 — en «You might be curious to know about the…» · hi «आप यह जानने के लिए उत्सुक होंगे कि…»

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
- ch 4 §4.2 ¶1: only in en 4.4 · only in hi —
- ch 4 §4.2 ¶2: only in en — · only in hi 4.4
- ch 4 §4.2.2 ¶1: only in en 4.6 · only in hi —
- ch 4 §4.2.6 ¶1: only in en — · only in hi 4.11
- ch 6 §6.2.4 ¶1: only in en — · only in hi 6.4
- ch 7 §7.1 ¶1: only in en — · only in hi 12
- ch 7 §7.2.2 ¶1: only in en 7.2 · only in hi —
- ch 7 §7.2.2 ¶2: only in en — · only in hi 7.2
- ch 7 §7.2.2 ¶6: only in en — · only in hi 10
- ch 7 §7.2.2 ¶9: only in en 2500, 3000 · only in hi 2, 500, 3, 000
- ch 8 §8.1 ¶1: only in en — · only in hi 1, 2
- ch 8 §8.5.4 ¶1: only in en 1.0 · only in hi 1
- ch 8 §8.5.5 ¶3: only in en 1 · only in hi —
- ch 8 §8.5.6 ¶2: only in en 80, 70, 8.9, 60, 40, 50, 30 · only in hi —
- ch 8 §8.5.10 ¶4: only in en — · only in hi 46, 23, 12
- ch 9 §9.1 ¶1: only in en 3 · only in hi —
- ch 9 §9.1 ¶3: only in en — · only in hi 20, 20, 3
- ch 9 §9.1 ¶5: only in en — · only in hi 3, 3, 2
- ch 9 §9.4 ¶1: only in en 9.3 · only in hi 9.2
- ch 9 §9.5 ¶1: only in en 2, 2, 2 · only in hi —
- ch 9 §9.6 ¶2: only in en 2 · only in hi —
- ch 9 §9.7 ¶1: only in en 3 · only in hi —
- ch 9 §9.7 ¶2: only in en 4 · only in hi —
- ch 9 §9.8 ¶1: only in en — · only in hi 9.3, 0, 0, 0
- ch 9 §9.8.1 ¶1: only in en 2, 3, 600, 000 · only in hi 0, 600000
- ch 9 §9.8.5 ¶2: only in en — · only in hi 1, 1, 1
- ch 9 §9.8.5 ¶3: only in en — · only in hi 1, 1, 1
- ch 9 §9.8.5 ¶7: only in en 2 · only in hi —
- ch 10 §10.1.1 ¶1: only in en 24, 90 · only in hi —
- ch 10 §10.1.1 ¶2: only in en — · only in hi 1, 2
- ch 10 §10.1.1 ¶3: only in en 24 · only in hi —
- ch 10 §10.1.1 ¶4: only in en 1, 2 · only in hi —
- ch 10 §10.1.1 ¶5: only in en — · only in hi 1
- ch 10 §10.1.1 ¶7: only in en 0 · only in hi —
- ch 10 §10.2.5 ¶1: only in en 10.2 · only in hi 10, 2
- ch 11 §11.2 ¶6: only in en 2 · only in hi —
- ch 11 §11.2 ¶7: only in en — · only in hi 6
- ch 11 §11.2 ¶9: only in en 2, 2 · only in hi 12
- ch 11 §11.6.1 ¶1: only in en 2 · only in hi —
- ch 11 §11.6.3 ¶7: only in en — · only in hi 12
- ch 11 §11.7 ¶4: only in en 4 · only in hi —
- ch 11 §11.7.1 ¶1: only in en 3 · only in hi —
- ch 11 §11.7.1 ¶2: only in en 5 · only in hi 1
- ch 11 §11.8 ¶1: only in en — · only in hi 4
- ch 11 §11.9 ¶1: only in en 2 · only in hi —
- ch 11 §11.9 ¶2: only in en — · only in hi 2
- ch 11 §11.9 ¶3: only in en 2, 2, 2 · only in hi 3
- ch 11 §11.9 ¶6: only in en 11.1 · only in hi —
- ch 11 §11.10.2 ¶1: only in en — · only in hi 2
- ch 12 §12.1 ¶1: only in en 2, 2 · only in hi —
- ch 12 §12.1 ¶4: only in en 2 · only in hi —
- ch 12 §12.2 ¶4: only in en 2, 2, 2 · only in hi —
- ch 12 §12.3 ¶1: only in en 2 · only in hi —
- ch 12 §12.3 ¶2: only in en — · only in hi 7
- ch 12 §12.3 ¶3: only in en 2, 2 · only in hi —
- ch 12 §12.4 ¶1: only in en 2, 2 · only in hi 3
- ch 12 §12.4.1 ¶2: only in en 2, 2 · only in hi —
- ch 12 §12.4.2 ¶1: only in en 2, 2 · only in hi —
- ch 12 §12.4.2 ¶2: only in en 3 · only in hi —
- ch 12 §12.4.2 ¶3: only in en 1, 0 · only in hi —
- ch 12 §12.5 ¶7: only in en 2 · only in hi —
- ch 12 §12.7 ¶1: only in en 2, 2, 2, 2 · only in hi —
- ch 12 §12.7 ¶4: only in en 1 · only in hi 1.00
- ch 13 §13.1.4 ¶1: only in en — · only in hi 13.4
- ch 13 §13.1.4 ¶2: only in en 13.4 · only in hi —
- ch 13 §13.1.4 ¶3: only in en 0, 0 · only in hi —
- ch 13 §13.1.4 ¶7: only in en 1, 1 · only in hi —
- ch 13 §13.4.1 ¶1: only in en 3, 6 · only in hi —
- ch 13 §13.4.2 ¶2: only in en 1926 · only in hi —
- ch 13 §13.4.3.2 ¶1: only in en 100 · only in hi 1, 2, 3, 3
- ch 14 §14.4.1 ¶1: only in en 2, 2 · only in hi —
- ch 14 §14.4.2 ¶1: only in en — · only in hi 2
- ch 14 §14.4.2 ¶2: only in en 2, 14.4 · only in hi 17.4
- ch 15 §15.1.2 ¶2: only in en 3 · only in hi 50, 55
- ch 15 §15.1.2 ¶3: only in en 3 · only in hi —
- ch 15 §15.1.2 ¶4: only in en 1, 500, 500, 00, 00, 3, 3 · only in hi 1.5, 3.5
- ch 15 §15.3 ¶2: only in en 2, 3, 4 · only in hi —
- ch 15 §15.3.1 ¶3: only in en 1, 1 · only in hi —
- ch 15 §15.3.2 ¶2: only in en 5000 · only in hi 5
- ch 15 §15.4 ¶2: only in en 15.4, 2 · only in hi —
- ch 15 §15.6 ¶1: only in en 140, 90 · only in hi —
- ch 16 §16.1 ¶2: only in en — · only in hi 10
- ch 16 §16.1 ¶4: only in en — · only in hi 16.5
- ch 16 §16.2 ¶1: only in en — · only in hi 3
- ch 16 §16.3 ¶3: only in en 3, 3 · only in hi —
- ch 16 §16.4 ¶1: only in en 1, 1 · only in hi —
- ch 16 §16.6 ¶1: only in en 6.0 · only in hi 6, 6
- ch 17 §17.1 ¶1: only in en — · only in hi 8
- ch 17 §17.2 ¶1: only in en 8 · only in hi —
- ch 17 §17.3 ¶3: only in en — · only in hi 7
- ch 17 §17.3 ¶4: only in en 2 · only in hi —
- ch 17 §17.3 ¶5: only in en 14 · only in hi —
- ch 18 §18.3.1 ¶2: only in en 18.2 · only in hi —
- ch 18 §18.4 ¶1: only in en 24 · only in hi —
- ch 18 §18.4 ¶2: only in en — · only in hi 3
- ch 19 §19.2.1 ¶1: only in en 19.1 · only in hi —
- ch 19 §19.2.5 ¶2: only in en — · only in hi 2
- ch 19 §19.2.8 ¶1: only in en 1, 2 · only in hi 10, 20
- ch 19 §19.4 ¶2: only in en — · only in hi 2

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.2.1 ¶1 — en p6–7, hi p6–7, similarity 0.61
- en: Taxonomic studies consider a group of individual organisms with fundamental similarities as a species. One should be able to distinguish one species from the other closely related species based on the distinct morphological differences. Let us consider Mangifera indica, Solanum tuberosum (potato) and Panthera leo (lion). All the three names, indica, tuberosum and leo, represent the specific epithets, while the first words Mangifera, Solanum and Panthera are genera and represents another higher level of taxon or category. Each genus may have one or more than one specific epithets representing different organisms, but having morphological similarities. For example, Panthera has another specific epithet called tigris and Solanum includes species like nigrum and melongena. Human beings belong to the species sapiens which is grouped in the genus Homo. The scientific name thus, for human being, is written as Homo sapiens.
- hi: वर्गिकी अध्ययन में जीवों के वर्ग, जिसमें मौलिक समानता होती है, उसे स्पीशीज कहते हैं। हम किसी भी स्पीशज को उसमें समीपस्थ संबंधित स्पीशीज से, उनके आकारिकीय विभिन्नता के आधार पर उन्हें एक दूसरे से अलग कर सकते हैं। हम इसके लिए मैंजीफेरा इंडिका (आम) सोलेनम टयूबरोसम (आलू) तथा पेंथरा लिओ (शेर) के उदाहरण लेते हैं। इन सभी तीनों नामों में इंडिका, टयूबरोसम तथा लिओ जाति संकेत पद हैं। जबकि पहले शब्द मैंजीफेरा, सोलेनम, तथा पेंथरा वंश के नाम हैं और यह टैक्सा अथवा संवर्ग का भी निरूपण करते हैं। प्रत्येक वंश में एक अथवा एक से अधिक जाति संकेत पद हो सकते हैं जो विभिन्न जीवों, जिनमें आकारकीय गुण समान हों, को दिखाते हैं। उदाहरणार्थ, पेंथरा में एक अन्य जाति संकेत पद है जिसे टिगरिस कहते हैं। सोलेनम वंश में नाइग्रिम, मेलांजेना भी आते हैं। मानव की जाति सेपियंस है, जो होमों वंश में आता है। इसलिए मानव का वैज्ञानिक नाम होमोसेपियंस है।

### 2. ch 1 §1.2.6 ¶1 — en p7–8, hi p7–8, similarity 0.61
- en: Classes comprising animals like fishes, amphibians, reptiles, birds along with mammals constitute the next higher category called Phylum. All these, based on the common features like presence of notochord and dorsal hollow neural system, are included in phylum Chordata. In case of plants, classes with a few similar characters are assigned to a higher category called Division.
- hi: वर्ग जिसमें जंतु जैसे मछली, उभयचर, सरीसृप, पक्षी तथा स्तनधारी आते हैं, अगले उच्चतर संवर्ग, जिसे संघ कहते हैं, का निर्माण करते हैं। इन सभी को एक समान गुणों जैसे पृष्ठरज्जु (नोटोकॉर्ड) तथा पृष्ठीय खोखला तंत्रिका तंत्र के होने के आधार पर कॉर्डेटा संघ में रखा गया है। पौधों में इन वर्गों, जिसमें कुछ ही एक समान लक्षण होते हैं, को उच्चतर संवर्ग भाग (डिविजन) में रखा गया है।

### 3. ch 1 §1.2.7 ¶1 — en p8, hi p8, similarity 0.61
- en: All animals belonging to various phyla are assigned to the highest category called Kingdom Animalia in the classification system of animals. The Kingdom Plantae, on the other hand, is distinct, and comprises all plants from various divisions. Henceforth, we will refer to these two groups as animal and plant kingdoms.
- hi: जंतु के वर्गिकी तंत्र में विभिन्न संघों के सभी प्राणियों को उच्चतम संवर्ग जगत में रखा गया है। जबकि पादप जगत में विभिन्न भाग (डिविजन) के सभी पौधों को रखा गया है। विभिन्न संघों के सभी प्राणियों को एक अलग जगत एनिमेलिया में रखा गया है जिससे कि उन्हें पौधों से अलग किया जा सके। पौधों को प्लांटी जगत में रखा गया है। भविष्य में हम इन दो वर्गों को जंतु तथा पादप जगत कहेंगे।

### 4. ch 2 §2.1 ¶1 — en p3, hi p3, similarity 0.65
- en: Bacteria are the sole members of the Kingdom Monera. They are the most abundant micro-organisms. Bacteria occur almost everywhere. Hundreds of bacteria are present in a handful of soil. They also live in extreme habitats such as hot springs, deserts, snow and deep oceans where very few other life forms can survive. Many of them live in or on other organisms as parasites.
- hi: सभी बैक्टीरिया मॉनेरा जगत के अंतर्गत आते हैं। ये सूक्ष्मजीवियों में सर्वाधिक संख्या में होते हैं और लगभग सभी स्थानों पर पाए जाते हैं। मुट्ठी भर मिट्टी में सैकड़ों प्रकार के बैक्टीरिया देखे गए हैं। ये गर्म जल के झरनों, मरूस्थल, बर्फ एवं गहरे समुद्र जैसे विषम एवं प्रतिकूल वास स्थानों, जहाँ दूसरे जीव मुश्किल से ही जीवित रह पाते हैं, में भी पाए जाते हैं। कई बैक्टीरिया तो अन्य जीवों पर या उनके भीतर परजीवी के रूप में रहते हैं।

### 5. ch 2 §2.1.2 ¶2 — en p4–5, hi p4, similarity 0.60 — the founder reads this one
- en: Heterotrophic bacteria are most abundant in nature. The majority are important decomposers. Many of them have a significant impact on human affairs. They are helpful in making curd from milk, production of antibiotics, fixing nitrogen in legume roots, etc. Some are pathogens causing damage to human beings, crops, farm animals and pets. Cholera, typhoid, tetanus, citrus canker are well known diseases caused by different bacteria.
- hi: परपोषी बैक्टीरिया प्रकृति में बहुलता से पाए जाते हैं और इनमें अधिकतर महत्वपूर्ण अपघटक होते हैं। इन परपोषी बैक्टीरिया में से अनेक का मनुष्य के जीवन संबधी गतिविधियों पर महत्वपूर्ण प्रभाव पड़ता है। ये दूध से दही बनाने में, प्रतिजैविकों के उत्पादन में, लेग्युम पादप की जड़ों में नाइट्रोजन स्थिरिकरण में सहायता करते हैं। कुछ बैक्टीरिया रोगजनक होते हैं जो मनुष्यों, फसलों, फार्म एवं पालतू पशुओं को हानि पहुँचाते हैं। विभिन्न बैक्टीरिया के कारण हैजा, टायफॉयड, टिटनेस, साइट्रस, कैंकर जैसी बीमारियां होती हैं।

### 6. ch 2 §2.2.1 ¶1 — en p5, hi p5, similarity 0.57
- en: This group includes diatoms and golden algae (desmids). They are found in fresh water as well as in marine environments. They are microscopic and float passively in water currents (plankton). Most of them are photosynthetic. In diatoms the cell walls form two thin overlapping shells, which fit together as in a soap box. The walls are embedded with silica and thus the walls are indestructible. Thus, diatoms have left behind large amount of cell wall deposits in their habitat; this accumulation over billions of years is referred to as ‘diatomaceous earth’. Being gritty this soil is used in polishing, filtration of oils and syrups. Diatoms are the chief ‘producers’ in the oceans.
- hi: इस समूह के अंतर्गत डाइएटम तथा सुनहरे शैवाल (डेस्मिड) आते हैं। ये स्वच्छ जल एवं लवणीय (समुद्री) पर्यावरण दोनों में पाए जाते हैं। ये अत्यंत सूक्ष्म होते हैं तथा जलधारा के साथ निश्चेष्ट रूप से बहते हैं। डाइएटम में कोशिका भित्ति साबुनदानी की तरह इसी के अनुरूप दो अतिछादित कवच बनाती है। इन भित्तियों में सिलिका होती है, जिस कारण ये नष्ट नहीं होते हैं। इस प्रकार मृत डाइएटम अपने परिवेश (वास स्थान) में कोशिका भित्ति के अवशेष बहुत बड़ी संख्या में छोड़ जाते हैं। करोड़ों वर्षों में जमा हुए इस अवशेष को 'डाइएटमी मृदा' कहते हैं। कणमय होने के कारण इस मृदा का उपयोग पॉलिश करने, तेलों तथा सिरप के निस्यंदन में होता है। ये समुद्र के मुख्य उत्पादक हैं।

### 7. ch 3 §3 ¶3 — en p1–2, hi p1, similarity 0.51
- en: Let us also look at classification within angiosperms to understand some of the concerns that influenced the classification systems. The earliest systems of classification used only gross superficial morphological characters such as habit, colour, number and shape of leaves, etc. They were based mainly on vegetative characters or on the androecium structure (system given by Linnaeus). Such systems were artificial; they separated the closely related species since they were based on a few characteristics. Also, the artificial systems gave equal weightage to vegetative and sexual characteristics; this is not acceptable since we know that often the vegetative characters are more easily affected by environment. As against this, natural classification systems developed, which were based on natural affinities among the organisms and consider, not only the external features, but also internal features, like ultra-structure, anatomy, embryology and phytochemistry. Such a classification for flowering plants was given by George Bentham and Joseph Dalton Hooker.
- hi: आओ, इस तंत्र को प्रभावित करने वाले बिंदुओं को समझने के लिए एंजियोस्पर्म के वर्गीकरण को देखें। पहले दिए वर्गीकरण में हम आकारिकी के गुणों जैसे प्रकृति, रंग, पत्तियों की संख्या तथा आकृति के आधार आदि पर वर्गीकरण करते थे। वे मुख्यतः कायिक गुणों अथवा पुमंग की रचना के आधार पर हैं तथा (लीनियस के अनुसार) ऐसे वर्गीकरण कृत्रिम थे, क्योंकि उन्होंने बहुत ही समीप वाली संबंधित स्पीशीज को अलग कर दिया था। इसका कारण था कि वे बहुत ही कम गुणों पर आधारित थे। कृत्रिम वर्गीकरण में कायिक तथा लैंगिक गुणों को समान मान्यता दी गई थी। यह अब स्वीकार नहीं है, क्योंकि हम जानते हैं कि कायिक गुणों में प्रायः पर्यावरण के अनुसार परिवर्तन हो जाता है। इसके विपरीत, प्राकृतिक वर्गीकरण जीवों में प्राकृतिक संबंध तथा बाह्य गुणों के साथ-साथ भीतरी गुणों, जैसे-परा-रचना, शारीर, भ्रूण विज्ञान तथा पादप रसायन के आधार पर विकसित हुआ है। पुष्पी पादपों के इस वर्गीकरण को जॉर्ज बेंथम तथा जोसेफ़ डॉल्टन हूकर ने सुझाया था।

### 8. ch 3 §3.2.1 ¶1 — en p7, hi p7, similarity 0.58
- en: The liverworts grow usually in moist, shady habitats such as banks of streams, marshy ground, damp soil, bark of trees and deep in the woods. The plant body of a liverwort is thalloid, e.g., Marchantia. The thallus is dorsiventral and closely appressed to the substrate. The leafy members have tiny leaf-like appendages in two rows on the stem-like structures.
- hi: लिवरवर्ट प्रायः नमी छायादार स्थानों जैसे नदियों के किनारे, दल-दले स्थानों, गीली मिट्टी, पेड़ों की छालों आदि पर उगते हैं। लिवरवर्ट की पादपकाय थैलासाभ (मारकेंशिया) होती हैं। थैलस पृष्ठाधर होते हैं तथा अधःस्तर बिल्कुल चिपके रहते हैं। इसके पत्तीदार सदस्यों में पत्तियों की तरह की छोटी-छोटी संरचनाएँ होती हैं जो तने की तरह की रचना पर दो कतारों में होती हैं।

### 9. ch 3 §3.3 ¶2 — en p8–10, hi p8–10, similarity 0.47 — the founder reads this one
- en: You may recall that in bryophytes the dominant phase in the life cycle is the gametophytic plant body. However, in pteridophytes, the main plant body is a sporophyte which is differentiated into true root, stem and leaves (Figure 3.3). These organs possess well-differentiated vascular tissues. The leaves in pteridophyta are small (microphylls) as in Selaginella or large (macrophylls) as in ferns. The sporophytes bear sporangia that are subtended by leaf-like appendages called sporophylls. In some cases sporophylls may form distinct compact structures called strobili or cones (Selaginella, Equisetum). The sporangia produce spores by meiosis in spore mother cells. The spores germinate to give rise to inconspicuous, small but multicellular, free-living, mostly photosynthetic thalloid gametophytes called prothallus. These gametophytes require cool, damp, shady places to grow. Because of this specific restricted requirement and the need for water for fertilisation, the spread of living pteridophytes is limited and restricted to narrow geographical regions. The gametophytes bear male and female sex organs called antheridia and archegonia, respectively. Water is required for transfer of antherozoids – the male gametes released from the antheridia, to the mouth of archegonium. Fusion of male gamete with the egg present in the archegonium result in the formation of zygote. Zygote thereafter produces a multicellular well-differentiated sporophyte which is the dominant phase of the pteridophytes. In majority of the pteridophytes all the spores are of similar kinds; such plants are called homosporous. Genera like Selaginella and Salvinia which produce two kinds of spores, macro (large) and micro (small) spores, are known as heterosporous. The megaspores and microspores germinate and give rise to female and male gametophytes, respectively. The female gametophytes in these plants are retained on the parent sporophytes for variable periods. The development of the zygotes into young embryos take place within the female gametophytes. This event is a precursor to the seed habit considered an important step in evolution.
- hi: आपको याद होगा कि ब्रायोफाइट के जीवन में युग्मकोद्भिद् प्रभावी अवस्था होती है (चित्र 3.3)। लेकिन टेरिडोफाइट में मुख्य पादपकाय स्पोरोफाइट है, जिसमें वास्तविक मूल, तना तथा पत्तियाँ होती हैं। इन अंगों में सुस्पष्ट संवहन ऊतक होते हैं। टैरिडोफाइट में पत्तियाँ छोटी, लघुपर्ण उदाहरणतः सिलैजिनैला अथवा बड़ी, बृहत्पर्ण हो सकती है; जैसे फर्न। स्पोरोफाइट में बीजाणुधानी होती हैं; जो पत्ती की तरह के बीजाणुपर्ण पर लगी रहती हैं। कुछ टैरिडोफाइट में बीजाणुपर्ण सघन होकर एक सुस्पष्ट रचना बनाते हैं जिन्हें शंकु कहते हैं। उदाहरणतः सिलैजिनेला, इक्वीसीटम । बीजाणुधानी के स्थित बीजाणुमातृ कोशिका में मिऑसिस के कारण बीजाणु बनते हैं। बीजाणु अंकुरित होने पर एक अस्पष्ट, छोटा बहुकोशिक, मुक्तजीवी, अधिकांशतः प्रकाशसंश्लेषी थैलाभ युग्मकोद्भिद् बनाते हैं; जिसे प्रोथैलस कहते हैं। इन युग्मकोद्भिदों को उगने के लिए ठंडा, गीला, छायादार स्थान चाहिए। इसकी विशिष्ट, सीमित आवश्यकताएँ और निषेचन के लिए पानी की आवश्यकता होने के कारण जीवित टैरिडोफाइट का फैलाव भी सीमित है और कम भौगोलिक क्षेत्रों तक सीमित हैं। युग्मकोद्भिद् के नर तथा मादा अंग होते हैं; जिन्हें क्रमशः पुंधानी तथा स्त्रीधानी कहते हैं। पुंधानी से पुमणु के निकलने के बाद उसे स्त्रीधानी के मुँह तक पहुँचने के लिए पानी की आवश्यकता होती हैं। स्त्रीधानी में स्थित अंडे से नर युग्मक संगलन हो जाता है और युग्मनज बनता है। उसके बाद युग्मनज से बहुकोशिक, सुस्पष्ट स्पोरोफाइट बन जाता है जो टैरिडोफाइट की प्रभावी अवस्था है। यद्यपि अधिकांश टैरिडोफाइट में, जहाँ स्पोर एक ही प्रकार के होते हैं, उन पौधों को समबीजाणुक कहते हैं। सिलैजिनेला, साल्वीनिया में दो प्रकार के - बृहद् (बड़े) तथा लघु (छोटे) स्पोर बनते हैं; जिन्हें विषमबीजाणु कहते हैं। बड़े बृहद् बीजाणु (मादा) तथा छोटे लघु बीजाणु (नर) से क्रमशः मादा तथा नर युग्मकोद्भिद् बन जाते हैं ऐसे पौधों में मादा युग्मकोद्भिद् अपनी आवश्यकताओं की पूर्ति के लिए पैतृक स्पोरोफाइट से जुड़ा रहता है। मादा युग्मकोद्भिद् में युग्मनज का विकास होता है; जिससे एक नया शैशव भ्रूण बनता है। यह घटना बहुत महत्त्वपूर्ण समझी जाती है जो बीजी प्रकृति की ओर ले जाती है।

### 10. ch 4 §4.1.1 ¶2 — en p2, hi p2, similarity 0.66
- en: (i) open type in which the blood is pumped out of the heart and the cells and tissues are directly bathed in it and
- hi: (i) खुले परिसंचरण-तंत्र में रक्त का बहाव हृदय से सीधे बाहर भेजा जाता है तथा कोशिका एवं ऊतक इसमें डूबे रहते हैं।।

### 11. ch 4 §4.2.2 ¶2 — en p5, hi p5, similarity 0.60 — the founder reads this one
- en: Examples: Physalia (Portuguese man-of-war), Adamsia (Sea anemone), Pennatula (Sea-pen), Gorgonia (Sea-fan) and Meandrina (Brain coral).
- hi: उदाहरण– फाइसेलिया (पुर्तगाली युद्ध मानव) एडमसिया (समुद्र ऐनीमोन) पेनेट्युला (समुद्री पिच्छ) गोरगोनिया (समुद्री व्यंजन) तक्ष तथा मेन्डरीना (ब्रेन कोरल)।

### 12. ch 4 §4.2.7 ¶5 — en p8, hi p8, similarity 0.58
- en: Living fossil – Limulus (King crab).
- hi: तथा जीवित जीवाश्म लिमूलस (राज कर्कट किंग क्रेब) आदि।

### 13. ch 4 §4.2.11 ¶2 — en p10, hi p10, similarity 0.36
- en: Table 4.1 presents a comparison of salient features of chordates and non-chordates.
- hi: सारणी 4.1 अरज्जुकी एवं रज्जुकी में विशिष्ट लक्षणों की तुलना।

### 14. ch 4 §4.2.11.1 ¶2 — en p11, hi p11, similarity 0.58 — the founder reads this one
- en: Examples: Petromyzon (Lamprey) and Myxine (Hagfish).
- hi: उदाहरण– पेट्रोमाइजॉन (लैम्प्रे) तथा मिक्सीन (हैग फीश)

### 15. ch 5 §5.5.1.1 ¶1 — en p9, hi p9, similarity 0.58
- en: The calyx is the outermost whorl of the flower and the members are called sepals. Generally, sepals are green, leaf like and protect the flower in the bud stage. The calyx may be gamosepalous (sepals united) or polysepalous (sepals free).
- hi: केल्किस पुष्प का सबसे बाहरी चक्र है और इसकी इकाई को बाह्य दल कहते हैं। प्रायः बाह्य दल हरी पत्तियों की तरह होते हैं और कली की अवस्था में फूल की रक्षा करते हैं। केल्किस संयुक्त बाह्य दली (जुड़े हुए बाह्य दल) अथवा पृथक बाह्य दली (मुक्त बाह्य दल) होते हैं।

### 16. ch 6 §6.1.2 ¶1 — en p2, hi p2, similarity 0.52
- en: All tissues except epidermis and vascular bundles constitute the ground tissue. It consists of simple tissues such as parenchyma, collenchyma and sclerenchyma. Parenchymatous cells are usually present in cortex, pericycle, pith and medullary rays, in the primary stems and roots. In leaves, the ground tissue consists of thin-walled chloroplast containing cells and is called mesophyll.
- hi: बाह्यत्वचा तथा संवहन बंडल के अतिरिक्त सभी ऊतक भरण ऊतक बनाते हैं। इसमें सरल ऊतक जैसे पैरेंकाइमा, कॉलेंकाइमा तथा स्कलेरंकाइमा होते हैं। प्राथमिक तने में पेरेंकाइमी कोशिकाएं प्रायः वल्कुट, (कॉर्टेक्स) परिरंभ, पिथ तथा मज्जाकिरण में होती हैं। पत्तियों में भरण ऊतक पतली भित्ति वाले तथा क्लोरोप्लास्ट युक्त होते हैं और इसे पर्णमध्योतक (मेजोफिल) कहते हैं।

### 17. ch 7 §7.2.1 ¶1 — en p2, hi p3, similarity 0.44
- en: Have you ever touched the skin of frog? The skin is smooth and slippery due to the presence of mucus. The skin is always maintained in a moist condition. The colour of dorsal side of body is generally olive green with dark irregular spots. On the ventral side the skin is uniformly pale yellow. The frog never drinks water but absorb it through the skin.
- hi: क्या आपने कभी मेंढक की त्वचा को छुआ है? मेंढक की त्वचा श्लेषमा (म्युकस) से ढकी होने के कारण चिकनी तथा फिसलनी होती है। इसकी त्वचा सदैव आर्द्र रहती है। मेंढक की ऊपरी सतह धानी हरे रंग की होती है, जिसमें अनियमित धब्बे होते हैं, जबकि नीचे की सतह हल्की पीली होती है। मेंढक कभी पानी नहीं पीता; बल्कि त्वचा द्वारा इसका अवशोषण करता है।

### 18. ch 8 §8.3 ¶1 — en p4, hi p4, similarity 0.53 — the founder reads this one
- en: You have earlier observed cells in an onion peel and/or human cheek cells under the microscope. Let us recollect their structure. The onion cell which is a typical plant cell, has a distinct cell wall as its outer boundary and just within it is the cell membrane. The cells of the human cheek have an outer membrane as the delimiting structure of the cell. Inside each cell is a dense membrane bound structure called nucleus. This nucleus contains the chromosomes which in turn contain the genetic material, DNA. Cells that have membrane bound nuclei are called eukaryotic whereas cells that lack a membrane bound nucleus are prokaryotic. In both prokaryotic and eukaryotic cells, a semi-fluid matrix called cytoplasm occupies the volume of the cell. The cytoplasm is the main arena of cellular activities in both the plant and animal cells. Various chemical reactions occur in it to keep the cell in the 'living state'.
- hi: आरंभ में आप प्याज के छिलके और/या मनुष्य की गाल की कोशिकाओं को सूक्ष्मदर्शी से देख चुके होंगे। उनकी संरचना का स्मरण करें। प्याज की कोशिका जो एक प्रारूपी पादप कोशिका है, जिसके बाहरी सतह पर एक स्पष्ट कोशिका भित्ति व इसके ठीक नीचे कोशिका झिल्ली होती है। मनुष्य की गाल की कोशिका के संगठन में बाहर की तरफ केवल एक झिल्ली संरचना निकलती दिखाई पड़ती है। प्रत्येक कोशिका के भीतर एक सघन झिल्लीयुक्त संरचना मिलती है, जिसे केंद्रक कहते हैं। इस केंद्रक में गुणसूत्र (क्रोमोसोम) होता है, जिसमें आनुवंशिक पदार्थ डीएनए होता है। जिस कोशिका में झिल्लीयुक्त केंद्रक होता है, उसे यूकैरियोट व जिसमें झिल्लीयुक्त केंद्रक नहीं मिलता उसे प्रोकैरियाट कहते हैं। दोनों यूकैरियाटिक व प्रोकैरियोटिक कोशिकाओं में इसके आयतन को घेरे हुए एक अर्द्धतरल आव्यूह मिलता है जिसे कोशिकाद्रव्य कहते हैं। दोनों पादप व जंतु कोशिकाओं में कोशिकीय क्रियाओं हेतु कोशिकाद्रव्य एक प्रमुख स्थल होता है। कोशिका की 'जीव अवस्था' संबंधी विभिन्न रासायनिक अभिक्रियाएं यहीं संपन्न होती हैं।

### 19. ch 8 §8.5.5 ¶3 — en p14, hi p14, similarity 0.52
- en: Majority of the chloroplasts of the green plants are found in the mesophyll cells of the leaves. These are lens-shaped, oval, spherical, discoid or even ribbon-like organelles having variable length (5-10µm) and width (2-4µm). Their number varies from 1 per cell of the Chlamydomonas, a green alga to 20-40 per cell in the mesophyll.
- hi: हरे पौधों के अधिकतर हरितलवक पत्ती की पर्णमध्योतक कोशिकाओं में पाए जाते हैं। हरित लवक लेंस के आकार के अंडाकार, गोलाकार, चक्रिक व फीते के आकार के अंगक होते हैं जो विभिन्न लंबाई (5-10 माइक्रोमीटर) व चौड़ाई (2-4 माइक्रोमीटर) के होते हैं। इनकी संख्या भिन्न हो सकती है जैसे प्रत्येक कोशिका में एक (क्लेमाइडोमोनास-हरितशैवाल) से 20 से 40 प्रति कोशिका पर्णमध्योतक कोशिका हो सकती है।

### 20. ch 9 §9.4 ¶2 — en p6–7, hi p6–7, similarity 0.60
- en: Each protein is a polymer of amino acids. As there are 20 types of amino acids (e.g., alanine, cysteine, proline, tryptophan, lysine, etc.), a protein is a heteropolymer and not a homopolymer. A homopolymer has only one type of monomer repeating ‘n’ number of times. This information about the amino acid content is important as later in your nutrition lessons, you will learn that certain amino acids are essential for our health and they have to be supplied through our diet. Hence, dietary proteins are the source of essential amino acids. Therefore, amino acids can be essential or non-essential. The latter are those which our body can make, while we get essential amino acids through our diet/food. Proteins carry out many functions in living organisms, some transport nutrients across cell membrane, some fight infectious organisms, some are hormones, some are enzymes, etc. (Table 9.5). Collagen is the most abundant protein in animal world and Ribulose bisphosphate Carboxylase-Oxygenase (RuBisCO) is the most abundant protein in the whole of the biosphere.
- hi: प्रत्येक प्रोटीन अमीनो अम्ल का बहुलक है। अमीनो अम्ल 20 प्रकार के होने से (जैसे-एलेनीन, सिस्टीन, प्रोलीन, ट्रीप्टोफान, लाइसीन आदि) होते हैं। प्रोटीन समबहुलक नहीं, बल्कि विषम बहुलक होते हैं। एक समबहुलक एक एकलक की कई बार आवर्ती के कारण बनता है। अमीनो अम्ल के बारे में यह जानकारी अति महत्वपूर्ण है जैसा कि बाद में पोषण अध्याय में आप पढ़ेंगे कि कुछ अमीनो अम्ल स्वास्थ्य के लिए अति आवश्यक होते हैं, जिनकी आपूर्ति खाद्य पदार्थों के द्वारा होती है। इस तरह आहार की प्रोटीन इन आवश्यक अमीनो अम्ल की स्रोत होती हैं। इस प्रकार से अमीनो अम्ल अनिवार्य या अनानिवार्य हो सकते हैं। अनानिवार्य वे होते हैं जो हमारे शरीर में बनते हैं। जबकि हम अनिवार्य अमीनो अम्लों की आपूर्ति अपने खाद्य पदार्थ से करते हैं। प्रोटीन जीवों में बहुत सारे कार्य करते हैं, इनमें कुछ पोषकों के कोशिका झिल्ली से होकर अभिगमन, करने तथा कुछ संक्रामक जीवों से बचाने में सहायक होती हैं और कुछ एंजाइम के रूप में होती हैं (तालिका 9.5)।

### 21. ch 9 §9.8.1 ¶2 — en p11, hi p11, similarity 0.63
- en: There are thousands of types of enzymes each catalysing a unique chemical or metabolic reaction. A multistep chemical reaction, when each of the steps is catalysed by the same enzyme complex or different enzymes, is called a metabolic pathway. For example, Glucose -> 2 Pyruvic acid C_6H_12O_6 + O_2 -> 2C_3H_4 O_3 + 2H_2O is actually a metabolic pathway in which glucose becomes pyruvic acid through ten different enzyme catalysed metabolic reactions. When you study respiration in Chapter 12, you will study these reactions. At this stage you should know that this very metabolic pathway with one or two additional reactions gives rise to a variety of metabolic end products. In our skeletal muscle, under anaerobic conditions, lactic acid is formed. Under normal aerobic conditions, pyruvic acid is formed. In yeast, during fermentation, the same pathway leads to the production of ethanol (alcohol). Hence, in different conditions different products are possible.
- hi: हजारों प्रकार के एंजाइम होते हैं जो विशेष प्रकार की रासायनिक व उपापचयी अभिक्रियाओं को उत्प्रेरित करते हैं। बहुचरणीय रासायनिक अभिक्रियाओं में जहाँ प्रत्येक चरण एक ही जटिल एंजाइम या विभिन्न प्रकार के एंजाइम से उत्प्रेरित होती है, तो इन्हें उपापचयी पथ कहते हैं। जैसे उदाहरण– ग्लूकोज -> 2 पाइरूविक अम्ल C_6H_12O_6 + O_2 -> 2C_3H_4O_3 + 2H_2O ग्लूकोज से पाइरूविक अम्ल का निर्माण एक रासायनिक पथ द्वारा होता है, जिसमें दस विभिन्न प्रकार के एंजाइम उपापचयी अभिक्रिया को उत्प्रेरित करते हैं। आप अध्याय 12 में उपरोक्त अभिक्रियाओं के बारे में अध्ययन करेंगे। इस अवस्था में आपको जान लेना चाहिए कि एक ही उपापचयी पथ एक या दो अतिरिक्त अभिक्रियाओं के द्वारा विभिन्न प्रकार के उपापचयी उत्पाद बनाते हैं। हमारी कंकाली पेशियों में अनॉक्सी स्थिति में लैक्टिक अम्ल का निर्माण होता है जबकि सामान्य ऑक्सी स्थिति में पाइरूविक अम्ल का निर्माण होता है। खमीर में किण्वन के दौरान उपरोक्त पथ द्वारा इथेनाल (एल्कोहल) का निर्माण होता है। विभिन्न दिशाओं में विभिन्न प्रकार के उत्पाद का निर्माण संभव है।

### 22. ch 10 §10.1.1 ¶5 — en p2, hi p2–3, similarity 0.62
- en: G_1 phase corresponds to the interval between mitosis and initiation of DNA replication. During G_1 phase, the cell is metabolically active and continuously grows but does not replicate its DNA. S or synthesis phase marks the period during which DNA synthesis or replication takes place. During this time the amount of DNA per cell doubles. If the initial amount of DNA is denoted as 2C then it increases to 4C. However, there is no increase in the chromosome number; if the cell had diploid or 2n number of chromosomes at G_1, even after S phase the number of chromosomes remains the same, i.e., 2n.
- hi: पश्च सूत्री अंतरकाल प्रावस्था (जी_1फेस) समसूत्री विभाजन एवं डीएनए प्रतिकृतिकरण के बीच अंतराल को प्रदर्शित करता है। जी_1 प्रावस्था में कोशिका उपापचयी रूप से सक्रिय होती हैं एवं लगातार वृद्धि करती है, परंतु इसका डीएनए प्रतिकृति नहीं करता। एस फेस या संश्लेषण प्रावस्था के दौरान डीएनए का निर्माण एवं इसकी प्रतिकृति होती है। इस दौरान डीएनए की मात्रा दुगुनी हो जाती है। यदि डीएनए की प्रारंभिक मात्रा को 2 C से चिह्नित किया जाए तो यह बढ़कर 4 C हो जाती है, यद्यपि गणसूत्र की संख्या में कोई वृद्धि नहीं होती। यदि G_1 प्रावस्था में कोशिका द्विगुणित है या 2n गुणसूत्र है तो S प्रावस्था के बाद भी इसकी संख्या वही रहती है, जो G_1 अवस्था में थी अर्थात 2n होगी।

### 23. ch 10 §10.3 ¶2 — en p6, hi p6, similarity 0.48 — the founder reads this one
- en: Mitosis usually results in the production of diploid daughter cells with identical genetic complement. The growth of multicellular organisms is due to mitosis. Cell growth results in disturbing the ratio between the nucleus and the cytoplasm. It therefore becomes essential for the cell to divide to restore the nucleo-cytoplasmic ratio. A very significant contribution of mitosis is cell repair. The cells of the upper layer of the epidermis, cells of the lining of the gut, and blood cells are being constantly replaced. Mitotic divisions in the meristematic tissues – the apical and the lateral cambium, result in a continuous growth of plants throughout their life.
- hi: इस विभाजन से बनने वाली द्विगुणित संतति कोशिकाएं साधारणतया समान आनुवंशिक अवयव वाली होती है। बहुकोशिकीय जीवधारियों की वृद्धि सूत्री विभाजन के कारण होती है। कोशिका वृद्धि के परिणामस्वरूप केंद्रक व कोशिकाद्रव्य के बीच का अनुपात अव्यवस्थित हो जाता है। इसलिए यह आवश्यक हो जाता है कि कोशिका विभाजित होकर केंद्रक कोशिकाद्रव्य अनुपात को बनाए रखे। सूत्री विभाजन का एक महत्वपूर्ण योगदान यह है कि इसके द्वारा कोशिका की मरम्मत होती है। अधिचर्म की उपरी सतह की कोशिकाएं, आहार नाल की भीतरी सतह की कोशिकाएं एवं रक्त कोशिकाएं निरंतर प्रतिस्थापित होती रहती है।

### 24. ch 11 §11.6.3 ¶2 — en p10, hi p11, similarity 0.50
- en: Let us understand what causes the proton gradient across the membrane. We need to consider again the processes that take place during the activation of electrons and their transport to determine the steps that cause a proton gradient to develop (Figure 11.7).
- hi: आइए, यह समझें कि किन कारणों से प्रोटोन प्रवणता झिल्लिका के आर-पार होती है? हमें पुनः उन प्रक्रियाओं पर ध्यान देना होगा जो इलेक्ट्रॉन के सक्रियता और उनके परिवहन के समय संपन्न होता है, ताकि उन चरणों को सुनिश्चित किया जा सके जिनके कारण प्रोटोन प्रवणता का विकास होता (चित्र 11.7) है।

### 25. ch 11 §11.6.3 ¶4 — en p11, hi p11, similarity 0.58 — the founder reads this one
- en: (b) As electrons move through the photosystems, protons are transported across the membrane. This happens because the primary accepter of electron which is located towards the outer side of the membrane transfers its electron not to an electron carrier but to an H carrier. Hence, this molecule removes a proton from the stroma while transporting an electron. When this molecule passes on its electron to the electron carrier on the inner side of the membrane, the proton is released into the inner side or the lumen side of the membrane.
- hi: (ब) जैसे ही इलेक्ट्रॉन्स फोटोसिस्टम के माध्यम से गति करते हैं, प्रोटोन झिल्लिका के पार चला जाता है। ऐसा इसलिए होता है, क्योंकि इलेक्ट्रॉन का प्राथमिक ग्राही, जो कि झिल्लिका के बाहर की ओर स्थित होता है, यह अपने इलेक्ट्रॉन को एक इलेक्ट्रॉन वाहक को स्थानांतरित नहीं करता, बल्कि एक हाइड्रोजन वाहक को करता है। अतः इलेक्ट्रॉन प्रवाह के समय यह अणु स्ट्रोमा से एक प्रोटोन को ले लेता है, जब यह अणु अपने इलेक्ट्रॉन को झिल्ली के भीतरी ओर स्थित इलेक्ट्रॉन वाहक को देता है, तब प्रोटोन के अंदर ओर अथवा झिल्ली की अवकाशिका की ओर मुक्त होता है।

### 26. ch 11 §11.9 ¶2 — en p17, hi p17, similarity 0.74
- en: RuBisCO that is the most abundant enzyme in the world (Do you wonder why?) is characterised by the fact that its active site can bind to both CO_2 and O_2 – hence the name. Can you think how this could be possible? RuBisCO has a much greater affinity for CO_2 when the CO_2: O_2 is nearly equal. Imagine what would happen if this were not so! This binding is competitive. It is the relative concentration of O_2 and CO_2 that determines which of the two will bind to the enzyme.
- hi: रुबिस्को नामक एंजाइम विश्व में सबसे ज्यादा प्रचुर है (आपको आश्चर्य होता है क्यों?) और इसका यह गुण है कि इसकी सक्रिय जगह CO_2 एवं O_2 दोनों से बंधित हो सकता है। इसलिए इसे रुबिस्को कहते हैं। क्या आप सोच सकते हैं कि यह केसे संभव है? जब CO_2 व O_2 का अनुपात लगभग समान होता है रुबिस्को में O_2 की अपेक्षा CO_2 के लिए अधिक बंधुता है। कल्पना कीजिए कि यदि ऐसा नहीं होता तो क्या होता! यह आबंधता प्रतियोगितात्मक है। O_2 अथवा CO_2 इनमें से कौन आबंध होगा, यह उनकी सापेक्ष सांद्रता पर निर्भर करता है।

### 27. ch 12 §12.7 ¶5 — en p12, hi p12, similarity 0.57
- en: When proteins are respiratory substrates the ratio would be about 0.9.
- hi: जब प्रोटीन श्वसनी क्रियाधार के रूप में प्रयुक्त होता है तब अनुपात 0.9 के लगभग होते है।

### 28. ch 13 §13.1.4 ¶2 — en p5, hi p5, similarity 0.54
- en: The growth rate shows an increase that may be arithmetic or geometrical (Figure 13.4).
- hi: वृद्धि दर अंकगणितीय या ज्यामितीय (रेखागणितीय) संवंर्धन हो सकती है।

### 29. ch 13 §13.1.4 ¶5 — en p5, hi p5, similarity 0.72
- en: The exponential growth can be expressed as W_1 = W_0 e^rt W_1 = final size (weight, height, number etc.) W_0 = initial size at the beginning of the period r = growth rate t = time of growth e = base of natural logarithms
- hi: चरघातांकीय वृद्धि को इस प्रकार प्रकट किया जा सकता है: W_1 = W_0 e^(rt) W_1= अंतिम आकार (भार, ऊंचाई, संख्या आदि) W_0= प्रथम आकार प्रारंभिक समय में r = वृद्धि दर t = समय में वृद्धि e = स्वाभाविक लघुगाणिक का आधार

### 30. ch 13 §13.4.1 ¶1 — en p9, hi p9, similarity 0.60
- en: The plant growth regulators (PGRs) are small, simple molecules of diverse chemical composition. They could be indole compounds (indole-3-acetic acid, IAA); adenine derivatives (N^6-furfurylamino purine, kinetin), derivatives of carotenoids (abscisic acid, ABA); terpenes (gibberellic acid, GA_3) or gases (ethylene, C_2H_4). Plant growth regulators are variously described as plant growth substances, plant hormones or phytohormones in literature.
- hi: पादप वृद्धि नियामक विविध रासायनिक संघटनों वाले साधारण तथा लघु अणु होते हैं। ये इंडोल सम्मिश्रण (इंडोल-3 एसिटिक अम्ल, आई ए ए); ऐडनीन व्युत्पन्न फरफ्युराइल ऐमिनो प्युरीन काइनटिन) केराटिनायड तथा वसा अम्लों के व्युत्पन्नक (एंसीसिक एसिड, ए बी ए), टर्पीन (जिबेरेलिक एसिड, जी ए) या गैसेस (एथीलिन C_2H_4) आदि हो सकते हैं। पादप वृद्धि नियामक को पाठय सामग्री में, पादप वृद्धि तत्व, पादप हार्मोन तथा फाइटोहार्मोन के नाम से वर्णित किया गया है।

### 31. ch 13 §13.4.3.2 ¶1 — en p11, hi p11, similarity 0.64
- en: Gibberellins are another kind of promotory PGR. There are more than 100 gibberellins reported from widely different organisms such as fungi and higher plants. They are denoted as GA_1, GA_2, GA_3 and so on. However, Gibberellic acid (GA_3) was one of the first gibberellins to be discovered and remains the most intensively studied form. All GAs are acidic. They produce a wide range of physiological responses in the plants. Their ability to cause an increase in length of axis is used to increase the length of grapes stalks. Gibberellins, cause fruits like apple to elongate and improve its shape. They also delay senescence. Thus, the fruits can be left on the tree longer so as to extend the market period. GA_3 is used to speed up the malting process in brewing industry.
- hi: जिबरेलिन एक अन्य प्रकार का प्रोत्साहक पी जी आर है। सौ से अधिक जिबरेलिन की सूचना विभिन्न जीवों से आ चुकी है जैसे कि कवकों और उच्च पादपों से। इन्हें जी ए_1 (GA_1)जी ए_2 (GA_2)जी ए_3 (GA_3)और इसी तरह से नामित किया गया है। हालांकि जी ए_3 वह जिबरेलिन है जिसकी सबसे पहले खोज की गई थी और अभी भी सभी से अधिक सघनता से अध्ययन किया जाने वाला स्वरूप है। सभी जी ए एस (GAs) अम्लीय होते हैं। ये पौधों में एक व्यापक दायरे की कायिकीय अनुक्रिया देते हैं। ये अक्ष की लंबाई बढ़ाने की क्षमता रखते हैं, अतः अंगूर के डंठल की लंबाई बढ़ाने में प्रयोग किये जाते हैं। जिबरेलिन सेव जैसे फलों को लंबा बनाते हैं ताकि वे उचित रूप ले सकें। ये जरावस्था को भी रोकते हैं, ताकि फल पेड़ पर अधिक समय तक लगे रह सकें और बाजार में मिल सकें। जी ए_3 (GA_3) को आसव (शराब) उद्योग में माल्टिंग की गति बढ़ाने के लिए उपयोग किया जाता है।

### 32. ch 13 §13.4.3.3 ¶1 — en p11–12, hi p12, similarity 0.55 — the founder reads this one
- en: Cytokinins have specific effects on cytokinesis, and were discovered as kinetin (a modified form of adenine, a purine) from the autoclaved herring sperm DNA. Kinetin does not occur naturally in plants. Search for natural substances with cytokinin-like activities led to the isolation of zeatin from corn-kernels and coconut milk. Since the discovery of zeatin, several naturally occurring cytokinins, and some synthetic compounds with cell division promoting activity, have been identified. Natural cytokinins are synthesised in regions where rapid cell division occurs, for example, root apices, developing shoot buds, young fruits etc. It helps to produce new leaves, chloroplasts in leaves, lateral shoot growth and adventitious shoot formation. Cytokinins help overcome the apical dominance. They promote nutrient mobilisation which helps in the delay of leaf senescence.
- hi: साइटोकाइनिन अपना विशेष प्रभाव साइटोकिनेसिस (कोशिकाद्रव्य विभाजन) में डालता है और इसे काइनेटिन(एडेनिन का रूपांतरित रूप एक प्युरीन) के रूप में आटोक्लेबड्र हेरिंग के शुक्राणु से खोजा गया था। काइनेटिन पौधों में प्राकृतिक रूप से नहीं पाया जाता है। साइटोकाइनिन जैसे पदार्थों की खोज के क्रम में मक्का की अष्ठि तथा नारियल दूध से जियाटीन अलग किया जा सका। जियाटिन के खोज के बाद अनेकों प्राकृतिक रूप से प्राप्त साइटोकाइनिन तथा कोशिका विभाजन प्रोत्साहक पहचाने गए। प्राकृतिक साइटोकाइनिन उन क्षेत्रों में संश्लेषित होता है, जहाँ तीव्र कोशिका विभाजन संपन्न होता है, उदाहरण के लिए मूल शिखाग्र, विकासशील प्ररोह कलिकाएं तथा तरुणफल आदि। यह नई पत्तियों में हरितलवक पार्श्व प्ररोह वृद्धि तथा आपस्थानिक प्ररोह संरचना में मदद करता है। साइटोकाइनिन शिखाग्र प्राधान्यता से छुटकारा दिलाता है। वे पोषकों के संचारण को बढ़ावा देते हैं जिससे पत्तियों की जरावस्था को देरी करने में मदद मिलती है।

### 33. ch 13 §13.4.3.5 ¶5 — en p13, hi p13, similarity 0.58
- en: We shall discuss briefly the role of light and temperature (both of them, the extrinsic factors) on initiation of flowering.
- hi: हम लोग संक्षेप में प्रकाश और ताप (दोनों बाह्य कारक है) के पुष्पन आरंभ करने की भूमिका को पढ़ेंगे।

### 34. ch 14 §14.2.1 ¶5 — en p7, hi p7, similarity 0.41
- en: By adding up a few respiratory volumes described above, one can derive various pulmonary capacities, which can be used in clinical diagnosis.
- hi: ऊपर वर्णित कुछ श्वसन संबंधी आयतनों को जोड़कर फुप्फुसी क्षमताएं (फुप्फुसी धारिताएं) निकाली जा सकती हैं जिनका नैदानिक उद्देश्यों में उपयोग किया जा सकता है।

### 35. ch 15 §15.2 ¶1 — en p5, hi p5, similarity 0.59
- en: As the blood passes through the capillaries in tissues, some water along with many small water soluble substances move out into the spaces between the cells of tissues leaving the larger proteins and most of the formed elements in the blood vessels. This fluid released out is called the interstitial fluid or tissue fluid. It has the same mineral distribution as that in plasma. Exchange of nutrients, gases, etc., between the blood and the cells always occur through this fluid. An elaborate network of vessels called the lymphatic system collects this fluid and drains it back to the major veins. The fluid present in the lymphatic system is called the lymph. Lymph is a colourless fluid containing specialised lymphocytes which are responsible for the immune responses of the body. Lymph is also an important carrier for nutrients, hormones, etc. Fats are absorbed through lymph in the lacteals present in the intestinal villi.
- hi: रक्त जब ऊतक की कोशिकाओं से होकर गुजरता है तब बड़े प्रोटीन अणु एवं संगठित पदार्थों को छोड़कर रक्त से जल एवं जल में घुलनशील पदार्थ कोशिकाओं से बाहर निकल जाते हैं। इस तरल को अंतराली द्रव या ऊतक द्रव कहते हैं। इसमें प्लैज्मा के समान ही खनिज लवण पाए जाते हैं। रक्त तथा कोशिकाओं के बीच पोषक पदार्थ एवं गैसों का आदान प्रदान इसी द्रव से होता है। वाहिकाओं का विस्तृत जाल जो लसीका तंत्र (लिंफैटिक सिस्टम) कहलाता है इस द्रव को एकत्र कर बड़ी शिराओं में वापस छोड़ता है। लसीका तंत्र में उपस्थित यह द्रव/तरल को लसीका कहते हैं। लसीका एक रंगहीन द्रव है जिसमें विशिष्ट लिंफोसाइट मिलते हैं। लिंफोसाइट शरीर की प्रतिरक्षा अनुक्रिया के लिए उत्तरदायी है। लसीका पोषक पदार्थ, हार्मोन आदि के संवाहन के लिए महत्वपूर्ण होते हैं। आंत्र अकुंर में उपस्थित लैक्टियल वसा को लसीका द्वारा अवशोषित करते हैं।

### 36. ch 15 §15.4 ¶2 — en p9–10, hi p9, similarity 0.56
- en: As mentioned earlier, the blood pumped by the right ventricle enters the pulmonary artery, whereas the left ventricle pumps blood into the aorta. The deoxygenated blood pumped into the pulmonary artery is passed on to the lungs from where the oxygenated blood is carried by the pulmonary veins into the left atrium. This pathway constitutes the pulmonary circulation. The oxygenated blood entering the aorta is carried by a network of arteries, arterioles and capillaries to the tissues from where the deoxygenated blood is collected by a system of venules, veins and vena cava and emptied into the right atrium. This is the systemic circulation (Figure 15.4). The systemic circulation provides nutrients, O_2 and other essential substances to the tissues and takes CO_2 and other harmful substances away for elimination. A unique vascular connection exists between the digestive tract and liver called hepatic portal system. The hepatic portal vein carries blood from intestine to the liver before it is delivered to the systemic circulation. A special coronary system of blood vessels is present in our body exclusively for the circulation of blood to and from the cardiac musculature.
- hi: जैसा कि पहले बताया जा चुका है कि दाहिने निलय द्वारा पंप किया गया रक्त फुप्फुसीय धमनियों में जाता है जबकि बाएं निलय से रक्त महाधमनी में जाता है। ऑक्सीजन रहित रक्त, फेफड़ों में ऑक्सीजन युक्त होकर फुप्फुस शिराओं से होता हुआ बाएं अलिंद में आता है। यह संचरण पथ फुप्फुस संचरण कहलाता है। ऑक्सीजनित रक्त महाधमनी से होता हुआ धमनी, धमनिकाओं तथा केशिकाओं (केपिलरीज) से होता हुआ ऊतकों तक जाता है। और वहाँ से ऑक्सीजन रहित होकर शिरा, शिराओं तथा महाशिरा से होता हुआ दाहिने अलिंद में आता है। यह एक क्रमबद्ध परिसंचरण है। यह क्रमबद्ध परिसंचरण पोषक पदार्थ, ऑक्सीजन तथा अन्य जरूरी पदार्थों को ऊतकों तक पहुँचाता है तथा वहाँ से कार्बनडाइऑक्साइड (CO_2) तथा अन्य हानिकारक पदार्थों को बाहर निकालने के लिए ऊतकों से दूर ले जाता है। एक अनूठी संवहनी संबर्द्धता आहार नाल तथा यकृत के बीच उपस्थित होती है जिसे यकृत निवाहिका परिसंचरण तंत्र (हिपेटिकपोर्टल सिस्टम) कहते हैं। यकृत निवाहिका शिरा रक्त को इसके पहले कि वह क्रमबद्ध परिसंचरण में आंत्र से यकृत तक पहुँचाती है। हमारे शरीर में एक विशेष हृद परिसंचरण तंत्र (कोरोनरी सिस्टम) पाया जाता है, जो रक्त सिर्फ को हृद पेशी न्यास तक ले जाता है तथा वापस लाता है।

### 37. ch 15 §15.6 ¶3 — en p11, hi p11, similarity 0.58
- en: Angina: It is also called ‘angina pectoris’. A symptom of acute chest pain appears when no enough oxygen is reaching the heart muscle. Angina can occur in men and women of any age but it is more common among the middle-aged and elderly. It occurs due to conditions that affect the blood flow.
- hi: हृदशूल (एंजाइना) : इसको एंजाइना पेक्टोरिस (हृदशूल पेक्टोरिस) भी कहते हैं। हृद पेशी में जब पर्याप्त ऑक्सीजन नहीं पहुँचती है तब सीने में दर्द (वक्ष पीड़ा) होता है जो एंजाइना (हृदशूल) की पहचान है। हृदशूल स्त्री या पुरुष दोनों में किसी भी उम्र में हो सकता है, लेकिन मध्यावस्था तथा वृद्धावस्था में यह सामान्यतः होता है। यह अवस्था रक्त बहाव के प्रभावित होने से होती है।

### 38. ch 18 §18.3.2 ¶1 — en p5, hi p4, similarity 0.58 — the founder reads this one
- en: A nerve impulse is transmitted from one neuron to another through junctions called synapses. A synapse is formed by the membranes of a pre-synaptic neuron and a post-synaptic neuron, which may or may not be separated by a gap called synaptic cleft. There are two types of synapses, namely, electrical synapses and chemical synapses. At electrical synapses, the membranes of pre- and post-synaptic neurons are in very close proximity. Electrical current can flow directly from one neuron into the other across these synapses. Transmission of an impulse across electrical synapses is very similar to impulse conduction along a single axon. Impulse transmission across an electrical synapse is always faster than that across a chemical synapse. Electrical synapses are rare in our system.
- hi: तंत्रिका आवेगों का एक न्यूरॉन से दूसरे न्यूरॉन तक संचरण सिनेप्सिस द्वारा होता है। एक सिनेप्स का निर्माण पूर्व सिनैप्टिक न्यूरॉन तथा पश्च सिनेप्टिन न्यूरॉन की झिल्ली द्वारा होता है, जो कि सिनेप्टिक दरार द्वारा विभक्त हो भी सकती है या नहीं भी। सिनेप्स दो प्रकार के होते हैं, विद्युत सिनेप्स एवं रासायनिक सिनेप्स। विद्युत सिनेप्सिस पर, पूर्व और पश्च सिनेप्टिक न्यूरॉन की झिल्लियाँ एक दूसरे के समीप होती है। एक न्यूरॉन से दूसरे न्यूरॉन तक विद्युत धारा का प्रवाह सिनेप्सिस से होता है। विद्युतीय सिनेप्सिस से आवेग का संचरण, एक तंत्रिकाक्ष से आवेग के संचरण के समान होता है। विद्युतीय-सिनेप्सिस से आवेग का संचरण, रासायनिक सिनेप्सिस से संचरण की तुलना में अधिक तीव्र होता है। हमारे तंत्र में विद्युतीय सिनेप्सिस बहुत कम होते हैं।

### 39. ch 18 §18.3.2 ¶2 — en p5–6, hi p4–5, similarity 0.48
- en: At a chemical synapse, the membranes of the pre- and post-synaptic neurons are separated by a fluid-filled space called synaptic cleft (Figure 18.3). Do you know how the pre-synaptic neuron transmits an impulse (action potential) across the synaptic cleft to the post-synaptic neuron? Chemicals called neurotransmitters are involved in the transmission of impulses at these synapses. The axon terminals contain vesicles filled with these neurotransmitters. When an impulse (action potential) arrives at the axon terminal, it stimulates the movement of the synaptic vesicles towards the membrane where they fuse with the plasma membrane and release their neurotransmitters in the synaptic cleft. The released neurotransmitters bind to their specific receptors, present on the post-synaptic membrane. This binding opens ion channels allowing the entry of ions which can generate a new potential in the post-synaptic neuron. The new potential developed may be either excitatory or inhibitory.
- hi: रासायनिक सिनेप्स पर, पूर्व एवं पश्च सिनेप्टिक न्यूरोंस की झिल्लियाँ द्रव से भरे अवकाश द्वारा पृथक होती है जिसे सिनेप्टिक दरार कहते हैं (चित्र 18.3)। क्या आप जानते हैं किस प्रकार पूर्व सिनेप्टिक आवेग (सक्रिय विभव) का संचरण सिनेप्टिक दरार से पश्च सिनेप्टिक न्यूरॉन तक करते हैं? सिनेप्सिस द्वारा आवेगों के संचरण में न्यूरोट्रॉसमीटर (तंत्रिका संचारी) कहलाने वाले रसायन सम्मिलित होते हैं। तंत्रिकाक्ष के छोर पर स्थित (आश्रय पुटिकाऐं) तंत्रिका संचारी अणुओं से भरी होती हैं। जब तक आवेग तंत्रिकाक्ष के छोर तक पहुँचता है। यह सिनेप्टिक पुटिका की गति को झिल्ली की ओर उत्तेजित करता है, जहाँ वे प्लाज्मा झिल्ली के साथ जुड़कर तंत्रिका संचारी अणुओं को सिनेप्टिक दरार में मुक्त कर देते हैं। मुक्त किए गए तंत्रिका संचारी अणु पश्च सिनेप्टिक झिल्ली पर स्थित विशिष्ट ग्राहियों से जुड़ जाते हैं। इस जुड़ाव के फलस्वरूप आयन चैनल खुल जाते हैं और उसमें आयनों के आगमन से पश्च सिनेप्टिक झिल्ली पर नया विभव उत्पन्न हो जाता है। उत्पन्न हुआ नया विभव उत्तेजक या अवरोधक हो सकता है।

### 40. ch 19 §19.2.8 ¶2 — en p7, hi p8, similarity 0.71 — the founder reads this one
- en: Glucagon is a peptide hormone, and plays an important role in maintaining the normal blood glucose levels. Glucagon acts mainly on the liver cells (hepatocytes) and stimulates glycogenolysis resulting in an increased blood sugar (hyperglycemia). In addition, this hormone stimulates the process of gluconeogenesis which also contributes to hyperglycemia. Glucagon reduces the cellular glucose uptake and utilisation. Thus, glucagon is a hyperglycemic hormone.
- hi: ग्लूकागॉन एक पेप्टाइड हार्मोन है जो सामान्य रक्त शर्करा स्तर के नियमन में मुख्य भूमिका निभाता है। ग्लूकागॉन मुख्य रूप से यकृत कोशिकाओं पर कार्य कर ग्लाइकोजेन अपघटन को प्रेरित करता है जिसके फलस्वरूप रक्त शर्करा का स्तर बढ़ जाता है। इसके अतिरिक्त ग्लूकागॉन ग्लूकोनियोजिनेसिस की प्रक्रिया को भी प्रेरित करता है जिससे कि हाइपरग्लाइसिमिया ( अति ग्लूकोज रक्तता ) होती है। ग्लूकागॉन कोशिकीय शर्करा के अभिग्रहण और उपयोग को कम करता है। अतः ग्लूकोगॉन हाइपरग्लाइसिमिक हार्मोन है।

# margai-pipeline ncert align

- run: 2026-10-18 16:51 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 673 English and 672 Hindi row(s) in 13 chapter(s)
- result: ok
request id: pipeline-ncert-align-b1a59809-623c-49ff-881a-f2519a3b081e
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 672 | 358495 | ₹7.66 |

## sections whose rows do not pair one to one

en counts the English rows aligned: a number an `unprinted` entry left before the section's last Hindi row is listed below, not here; one left after it is aligned, and the load's report names it
| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 4 | 4.2 | 17 | 17 | 2 |
| 4 | 4.3 | 3 | 3 | 2 |
| 12 | 12.4 | 13 | 13 | 2 |

## English paragraphs the Hindi does not print — numbers an `unprinted` entry left before the section's last Hindi row, not aligned

- ch 5 §5.6 ¶10

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 4 §4.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2: hi ¶2+¶3 = en ¶3 (join hi)
- ch 4 §4.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.3: hi ¶2+¶3 = en ¶3 (join hi)
- ch 12 §12.4: hi ¶5+¶6 = en ¶5 (join hi)
- ch 12 §12.4: hi ¶7 = en ¶6+¶7 (split hi)

## pairs weaker than 0.45 — read both against the pages

- ch 1 §1.2.1: hi ¶2 = en ¶2 — similarity 0.44 — en «A typical angiosperm anther is bilobed with each…» · hi «एक प्रारूपिक आवृतबीजी परागकोश द्विपालित होते हैं। तथा…»
- ch 1 §1.2.1: hi ¶6 = en ¶6 — similarity 0.36 — en «Microsporogenesis : As the anther develops, the cells…» · hi «लघुबीजाणुजनन — जैसे-जैसे परागकोश विकसित होता है, बीजाणुजन…»
- ch 1 §1.2.1: hi ¶9 = en ¶9 — similarity 0.43 — en «Pollen grains are generally spherical measuring about 25-50…» · hi «परागकण सामान्यतः गोलाकार (गोलीय) होते हैं, जिनका व्यास…»
- ch 1 §1.2.2: hi ¶2 = en ¶2 — similarity 0.40 — en «The Megasporangium (Ovule) : Let us familiarise ourselves…» · hi «गुरूबीजाणुधानी (बीजांड) — आइए एक प्ररूपी आवृत्तबीजी बीजांड…»
- ch 1 §1.2.2: hi ¶5 = en ¶5 — similarity 0.38 — en «Female gametophyte : In a majority of flowering…» · hi «स्त्री (मादा) युग्मकोद्भिद् — अधिकांश पुष्पी पादपों में…»
- ch 1 §1.2.3: hi ¶1 = en ¶1 — similarity 0.41 — en «In the preceding sections you have learnt that…» · hi «पूर्ववर्ती भाग में आप पढ़ चुके हैं कि…»
- ch 1 §1.2.3: hi ¶10 = en ¶10 — similarity 0.44 — en «Both wind and water pollinated flowers are not…» · hi «वायु एवं जल परागित पुष्प न तो बहुत…»
- ch 1 §1.2.3: hi ¶17 = en ¶17 — similarity 0.40 — en «Pollen-pistil Interaction : Pollination does not guarantee the…» · hi «पराग-स्त्रीकेसर संकर्षण (पारस्परिकक्रिया) — परागण बिल्कुल सही प्रकार…»
- ch 1 §1.2.3: hi ¶18 = en ¶18 — similarity 0.39 — en «As mentioned earlier, following compatible pollination, the pollen…» · hi «जैसा कि पहले बताया जा चुका है; सुयोग्य…»
- ch 1 §1.4.2: hi ¶1 = en ¶1 — similarity 0.43 — en «Embryo develops at the micropylar end of the…» · hi «भ्रूण भ्रूणकोश या पुटी के बीजांडद्वारी सिरे पर…»
- ch 1 §1.5: hi ¶2 = en ¶2 — similarity 0.45 — en «Hybrid varieties of several of our food and…» · hi «बहुत से हमारे खाद्य एवं शाक फसलों की…»
- ch 4 §4.2: hi ¶14 = en ¶14 — similarity 0.42 — en «Using Punnett square, try to find out the…» · hi «पनेट वर्ग का प्रयोग करके परीक्षार्थ संकरण में…»
- ch 4 §4.2: hi ¶15 = en ¶15 — similarity 0.44 — en «What ratio did you get?» · hi «आपको क्या अनुपात मिला?»
- ch 4 §4.2: hi ¶16 = en ¶16 — similarity 0.38 — en «Using the genotypes of this cross, can you…» · hi «इस संकरण प्रयोग की जीनोटाइपों का उपयोग करके…»
- ch 5 §5.1.1: hi ¶6 = en ¶6 — similarity 0.41 — en «(ii) The two chains have anti-parallel polarity. It…» · hi «(ख) दोनों शृंखलाएँ प्रति समानांतर ध्रुवणता रखती है।…»
- ch 5 §5.1.1: hi ¶9 = en ¶9 — similarity 0.39 — en «(v) The plane of one base pair stacks…» · hi «(ङ) द्विकुंडली में एक क्षार युग्म की सतह…»
- ch 5 §5.1.1: hi ¶12 = en ¶12 — similarity 0.44 — en «In some viruses the flow of information is…» · hi «कुछ विषाणुओं में उपरोक्त बहाव विपरीत दिशा आरएनए…»
- ch 5 §5.1.2: hi ¶5 = en ¶5 — similarity 0.43 — en «Theoretically, how many such beads (nucleosomes) do you…» · hi «सैद्धांतिक रूप से तुम सोच सकते हो कि…»
- ch 5 §5.2: hi ¶5 = en ¶5 — similarity 0.43 — en «He concluded that the R strain bacteria had…» · hi «ग्रीफिथ ने बताया कि आर-प्रभेद जीवाणु ताप मृत…»
- ch 5 §5.2.2: hi ¶2 = en ¶2 — similarity 0.41 — en «Can you recall the two chemical differences between…» · hi «क्या आप डीएनए व आरएनए के बीच दो…»
- ch 5 §5.4: hi ¶3 = en ¶3 — similarity 0.43 — en «The scheme suggested that the two strands would…» · hi «उपरोक्त योजना से स्पष्ट है कि दोनों रज्जुक…»
- ch 5 §5.4.1: hi ¶4 = en ¶4 — similarity 0.38 — en «Can you recall what centrifugal force is, and…» · hi «क्या तुम अपकेंद्रबल के बारे में बता सकते…»
- ch 5 §5.4.2: hi ¶4 = en ¶4 — similarity 0.43 — en «Further, not every detail of replication is understood…» · hi «प्रतिकृति के बारे में अभी विस्तृत रूप से…»
- ch 5 §5.5.1: hi ¶3 = en ¶3 — similarity 0.33 — en «Can you now write the sequence of RNA…» · hi «उपरोक्त डीएनए से अनुलेखित आरएनए के अनुक्रमों को…»
- ch 5 §5.5.3: hi ¶4 = en ¶4 — similarity 0.28 — en «In eukaryotes, there are two additional complexities –» · hi «सुकेंद्रकी में दो अतिरिक्त जटिलताएँ हैं —»
- ch 5 §5.6: hi ¶4 = en ¶4 — similarity 0.27 — en «The salient features of genetic code are as…» · hi «आनुवंशिक कूट की प्रमुख विशेषताएँ निम्न है —»
- ch 5 §5.6: hi ¶6 = en ¶6 — similarity 0.41 — en «(ii) Some amino acids are coded by more…» · hi «(ग) कुछ अमीनो अम्ल का कूट लेखन एक…»
- ch 5 §5.6: hi ¶7 = en ¶7 — similarity 0.41 — en «(iii) The codon is read in mRNA in…» · hi «(घ) प्रकूट दूत आरएनए में लगातार पढ़े जाते…»
- ch 5 §5.6: hi ¶11 = en ¶11 — similarity 0.35 — en «If following is the sequence of nucleotides in…» · hi «यदि एक दूत आरएनए में निम्न न्युक्लियोटाइड अनुक्रम…»
- ch 5 §5.6: hi ¶13 = en ¶13 — similarity 0.40 — en «Now try the opposite. Following is the sequence…» · hi «अब इसके विपरीत के बारे में बताओ। नीचे…»
- ch 5 §5.6: hi ¶16 = en ¶16 — similarity 0.37 — en «Can you now correlate which two properties of…» · hi «क्या आप अब आनुवंशिक कूट की दो विशेषताओं…»
- ch 5 §5.8.1: hi ¶5 = en ¶5 — similarity 0.37 — en «Remember, glucose or galactose cannot act as inducers…» · hi «याद रखो लैक-प्रचालेक किए ग्लूकोज या गैलेक्टोज प्रेरक…»
- ch 5 §5.9.1: hi ¶7 = en ¶7 — similarity 0.45 — en «(vii) Repetitive sequences are stretches of DNA sequences…» · hi «(छ) पुनरावृत्ति अनुक्रम डीएनए के फैले हुए भाग…»
- ch 8 §8.6: hi ¶2 = en ¶2 — similarity 0.38 — en «Fungi are also known to form symbiotic associations…» · hi «कवक पादपों के साथ सहजीवी संबंध (माइकोराइजा) स्थापित…»
- ch 9 §9.1: hi ¶1 = en ¶1 — similarity 0.45 — en «Among many, the two core techniques that enabled…» · hi «आधुनिक जैव प्रौद्योगिकी के विकास में निम्न दो…»
- ch 9 §9.2.2: hi ¶2 = en ¶2 — similarity 0.38 — en «The following are the features that are required…» · hi «संवाहक में क्लोनिंग करने हेतु निम्न विशेषताओं की…»
- ch 9 §9.2.3: hi ¶3 = en ¶3 — similarity 0.41 — en «Now that we have learnt about the tools…» · hi «आप पुनर्योगज डीएनए निर्माण के तरीकों के बारे…»
- ch 9 §9.3: hi ¶1 = en ¶1 — similarity 0.44 — en «Recombinant DNA technology involves several steps in specific…» · hi «पुनर्योगज डीएनए प्रौद्योगिकी (टेक्नोलॉजी) चरण विशिष्ट अनुक्रम में…»
- ch 10 §10.1: hi ¶12 = en ¶12 — similarity 0.44 — en «Specific Bt toxin genes were isolated from Bacillus…» · hi «विशिष्ट बीटी जीव विष जींस बैसीलस थुरीनजिएंसिस से…»
- ch 12 §12.1: hi ¶4 = en ¶4 — similarity 0.44 — en «To understand the ethos of an aquatic ecosystem…» · hi «एक जलीय पारितंत्र के गुण धर्म (प्रकृति) को…»

## numbers one edition of a pair carries and the other does not

- ch 1 §1.1 ¶2: only in en — · only in hi 11
- ch 1 §1.2.1 ¶8: only in en 1.4 · only in hi —
- ch 1 §1.2.1 ¶9: only in en 2, 3 · only in hi 1.4
- ch 1 §1.2.2 ¶1: only in en — · only in hi 11
- ch 1 §1.2.2 ¶5: only in en — · only in hi 1.8
- ch 1 §1.2.2 ¶6: only in en — · only in hi 6
- ch 1 §1.4.3 ¶2: only in en 10, 15 · only in hi —
- ch 1 §1.4.3 ¶9: only in en — · only in hi 1000
- ch 2 §2 ¶1: only in en — · only in hi 50
- ch 2 §2.3 ¶3: only in en 200, 300 · only in hi 20, 30
- ch 2 §2.3 ¶5: only in en 000, 000 · only in hi 1, 1
- ch 2 §2.5 ¶3: only in en 5 · only in hi 4
- ch 2 §2.6 ¶4: only in en — · only in hi 9
- ch 3 §3 ¶1: only in en 2 · only in hi —
- ch 3 §3.2 ¶1: only in en 2011 · only in hi 6000, 35, 1000, 2001
- ch 3 §3.2 ¶4: only in en — · only in hi 4, 6, 6
- ch 3 §3.2 ¶7: only in en 7 · only in hi —
- ch 3 §3.2 ¶9: only in en 3.4 · only in hi —
- ch 3 §3.3 ¶1: only in en — · only in hi 4.5, 5
- ch 3 §3.4 ¶1: only in en 7 · only in hi —
- ch 4 §4 ¶4: only in en 1000 · only in hi 10000
- ch 4 §4.2 ¶10: only in en 1 · only in hi —
- ch 4 §4.2 ¶11: only in en 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 2, 2, 2, 2, 2, 2 · only in hi —
- ch 4 §4.2.2.1 ¶1: only in en 2 · only in hi —
- ch 4 §4.2.2.2 ¶1: only in en — · only in hi 2, 6, 6
- ch 4 §4.3.1 ¶4: only in en — · only in hi 2
- ch 4 §4.3.1 ¶5: only in en 1 · only in hi 50, 4, 4, 4
- ch 4 §4.3.3 ¶2: only in en 4.11 · only in hi —
- ch 4 §4.7 ¶2: only in en 5 · only in hi —
- ch 4 §4.7 ¶3: only in en 5 · only in hi —
- ch 4 §4.8.1 ¶2: only in en 4.13 · only in hi 4.14
- ch 4 §4.8.2 ¶1: only in en 4.14 · only in hi 4.15
- ch 4 §4.8.2 ¶4: only in en 4.15, 4.15 · only in hi 4.16
- ch 4 §4.8.3 ¶4: only in en 4.16 · only in hi 4.17
- ch 4 §4.8.3 ¶5: only in en 4.17 · only in hi 4.18
- ch 4 §4.8.3 ¶6: only in en 0, 4.17 · only in hi 4.18
- ch 5 §5 ¶1: only in en — · only in hi 11
- ch 5 §5 ¶2: only in en — · only in hi 11
- ch 5 §5.1.1 ¶8: only in en — · only in hi 100
- ch 5 §5.1.1 ¶9: only in en 5.3 · only in hi —
- ch 5 §5.1.1 ¶10: only in en — · only in hi 5.3
- ch 5 §5.4.1 ¶2: only in en — · only in hi 15, 14
- ch 5 §5.4.2 ¶2: only in en — · only in hi 5, 3
- ch 5 §5.5.1 ¶2: only in en — · only in hi 5, 5, 3, 3
- ch 5 §5.5.3 ¶5: only in en — · only in hi 5
- ch 5 §5.6 ¶2: only in en 4, 20, 20 · only in hi —
- ch 5 §5.6 ¶5: only in en 3 · only in hi —
- ch 5 §5.6.1 ¶1: only in en 4 · only in hi 5
- ch 5 §5.6.2 ¶2: only in en 5.12 · only in hi —
- ch 5 §5.9.1 ¶3: only in en 1, 40 · only in hi 140
- ch 5 §5.9.1 ¶5: only in en 2 · only in hi —
- ch 5 §5.10 ¶5: only in en — · only in hi 5.16
- ch 6 §6.1 ¶2: only in en — · only in hi 1380, 450, 2, 2, 50, 400
- ch 6 §6.1 ¶4: only in en 4, 4, 3, 3, 2 · only in hi —
- ch 6 §6.1 ¶5: only in en 2000 · only in hi 3, 300, 200
- ch 6 §6.2 ¶1: only in en — · only in hi 19
- ch 6 §6.3 ¶3: only in en 6.3 · only in hi —
- ch 6 §6.3 ¶7: only in en 6.4 · only in hi —
- ch 6 §6.4 ¶1: only in en 6.7 · only in hi —
- ch 6 §6.7 ¶2: only in en 2 · only in hi —
- ch 6 §6.9 ¶1: only in en 1, 00 · only in hi 2, 100
- ch 7 §7.1 ¶6: only in en 7.1 · only in hi 7.2
- ch 7 §7.1 ¶12: only in en 10 · only in hi —
- ch 7 §7.3 ¶3: only in en 7.6 · only in hi —
- ch 8 §8 ¶1: only in en 0 · only in hi 11
- ch 8 §8 ¶3: only in en 7 · only in hi 8
- ch 8 §8.2.2 ¶1: only in en — · only in hi 20
- ch 9 §9.2.2 ¶5: only in en — · only in hi 322
- ch 9 §9.2.3 ¶1: only in en 0 · only in hi —
- ch 9 §9.3.3 ¶1: only in en 1 · only in hi —
- ch 10 §10.1 ¶12: only in en — · only in hi 1, 1, 2
- ch 10 §10.2.2 ¶2: only in en 4 · only in hi —
- ch 10 §10.4 ¶6: only in en 200, 000 · only in hi 2
- ch 11 §11.1.2 ¶14: only in en 1, 3 · only in hi 8
- ch 11 §11.1.2 ¶16: only in en 11.3 · only in hi 11.2
- ch 11 §11.1.4 ¶8: only in en — · only in hi 10
- ch 12 §12.1 ¶1: only in en — · only in hi 13
- ch 13 §13.1 ¶2: only in en 000, 000, 1 · only in hi 1000
- ch 13 §13.1.1 ¶1: only in en — · only in hi 70
- ch 13 §13.1.1 ¶4: only in en 45, 000 · only in hi 45000
- ch 13 §13.1.2 ¶1: only in en 1, 1, 1, 400, 200, 000, 000, 000, 3, 300, 25 · only in hi 1400, 1200, 3000, 1300, 20
- ch 13 §13.1.3 ¶2: only in en 000, 000 · only in hi —
- ch 13 §13.1.4 ¶1: only in en 000 · only in hi 20
- ch 13 §13.1.4 ¶2: only in en 1, 000 · only in hi 5, 1000
- ch 13 §13.1.4 ¶5: only in en 1000 · only in hi —
- ch 13 §13.2.2 ¶2: only in en 2 · only in hi 9, 13.2, 3

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.2.2 ¶3 — en p9, hi p10, similarity 0.46 — the founder reads this one
- en: Enclosed within the integuments is a mass of cells called the nucellus. Cells of the nucellus have abundant reserve food materials. Located in the nucellus is the embryo sac or female gametophyte. An ovule generally has a single embryo sac formed from a megaspore.
- hi: अध्यावरणी से घिरा हुआ कोशिकाओं का एक पुंज होता है, जिसे बीजांडकाय कहते हैं। बीजांडकाय की कोशिकाओं में प्रचुरता से आरक्षित आहार सामग्री होती है। बीजांडकाय भ्रूणकोष या मादा युग्मकोद्भिद् बीजांडकाय में स्थित होता है। एक बीजांड में, सामान्यतः एक अकेला भ्रूणकोष होता है जो एक गुरूबीजाणु से निर्मित होता है।

### 2. ch 1 §1.2.2 ¶4 — en p9–10, hi p10, similarity 0.47 — the founder reads this one
- en: Megasporogenesis : The process of formation of megaspores from the megaspore mother cell is called megasporogenesis. Ovules generally differentiate a single megaspore mother cell (MMC) in the micropylar region of the nucellus. It is a large cell containing dense cytoplasm and a prominent nucleus. The MMC undergoes meiotic division. What is the importance of the MMC undergoing meiosis? Meiosis results in the production of four megaspores (Figure 1.8a).
- hi: गुरूबीजाणु जनन — गुरूबीजाणुमातृकोशिकाओं से गुरूबीजाणुओं की रचना के प्रक्रम को गुरूबीजाणुजनन कहते हैं। बीजांड प्रायः एक अकेले गुरूबीजाणु मातृ कोशिका से बीजांडकाय के बीजांडद्वारी क्षेत्र में विलग होते हैं। यह एक बड़ी कोशिका है जो सघन जीवद्रव्य से समाहित एवं एक सुस्पष्ट केंद्रक युक्त होती है। गुरूबीजाणुमातृकोशिका अर्धसूत्री विभाजन से गुजरती है। गुरूबीजाणु मातृकोशिका का अर्धसूत्रीविभाजन से गुजरने का क्या महत्त्व है? अर्धसूत्रीविभाजन के परिणाम स्वरूप चार गुरूबीजाणुओं का उत्पादन होता है (चित्र 1.8 अ)

### 3. ch 1 §1.2.3 ¶2 — en p11, hi p12, similarity 0.55
- en: Pollination is the mechanism to achieve this objective. Transfer of pollen grains (shed from the anther) to the stigma of a pistil is termed pollination. Flowering plants have evolved an amazing array of adaptations to achieve pollination. They make use of external agents to achieve pollination. Can you list the possible external agents?
- hi: इस उद्देश्य की प्राप्ति के लिए परागण एक प्रक्रम है। परागकणों (परागकोश से झड़ने के पश्चात्) का स्त्रीकेसर के वर्तिकाग्र तक स्थानांतरण या संचारण को परागण कहा जाता है। परागण की प्राप्ति के लिए पुष्पी पादपों ने एक आश्चर्य जनक अनुकूलन व्यूह विकसित किया है। ये परागण को प्राप्त करने के लिए बाह्य कारकों का उपयोग करते हैं। क्या आप इस समय अधिक से अधिक बाहरी कारकों की सूची बना सकते हैं?

### 4. ch 1 §1.2.3 ¶3 — en p11, hi p12, similarity 0.60
- en: Kinds of Pollination : Depending on the source of pollen, pollination can be divided into three types.
- hi: परागण के प्रकार — पराग के स्रोत को ध्यान में रखते हुए, परागण को तीन प्रकारों में बाँटा जा सकता है।

### 5. ch 1 §1.2.3 ¶10 — en p13, hi p14, similarity 0.44
- en: Both wind and water pollinated flowers are not very colourful and do not produce nectar. What would be the reason for this?
- hi: वायु एवं जल परागित पुष्प न तो बहुत रंग युक्त होते हैं और न ही मकरंद पैदा करते हैं। इसका क्या कारण हो सकता है?

### 6. ch 1 §1.2.3 ¶15 — en p15, hi p15, similarity 0.50
- en: Why don’t you observe some flowers of the following plants (or any others available to you): Cucumber, Mango, Peepal, Coriander, Papaya, Onion, Lobia, Cotton, Tobacco, Rose, Lemon, Eucalyptus, Banana? Try to find out which animals visit them and whether they could be pollinators.You’ll have to patiently observe the flowers over a few days and at different times of the day. You could also try to see whether there is any correlation in the characteristics of a flower to the animal that visits it. Carefully observe if any of the visitors come in contact with the anthers and the stigma as only such visitors can bring about pollination. Many insects may consume pollen or the nectar without bringing about pollination. Such floral visitors are referred to as pollen/nectar robbers. You may or may not be able to identify the pollinators, but you will surely enjoy your efforts!
- hi: क्यों न आप निम्नलिखित पादपों (या कोई भी जो उपलब्ध हो) के पुष्पों का अवलोकन करें जैसे खीरा, आम, पीपल, धनिया, पपीता, प्याज, लोबिया, कपास, तंबाकू, गुलाब, नींबू, यूकेलिप्टस, केला आदि। यह पता करने की कोशिश करें कि इन पुष्पों के पास कौन से प्राणि आते हैं और क्या वे परागण कर सकते हैं? आप को धैर्य के साथ कुछ दिनों तक, भिन्न-भिन्न समयों पर उन्हें देखना होगा। आप यह देखने की भी कोशिश करें कि क्या उस पुष्प तक आने वाले (भ्रमणकारी) प्राणि तथा एक पुष्प की विशिष्टता के बीच कोई सह सम्बद्धता है या नहीं। परागकोश एवं वर्तिकाग्र के संपर्क में आने वाले भ्रमणकर्ता प्राणि का ध्यानपूर्वक अवलोकन करें जो परागण को पूरा करता हो। बहुत सारे कीट या भ्रमणकर्ता बिना परागण किए पराग या मकरंद का भक्षण कर लेते हैं। अतः ऐसे आगंतुकों या भ्रमणकारियों को पराग/मकरंद दस्यु के रूप में संदर्भित किया जाता है। आप शायद परागणकारियों को पहचान सके या न पहचान सकें, पर आप निश्चित रूप से अपने प्रयास में आनंद प्राप्त करेंगे।

### 7. ch 1 §1.2.3 ¶19 — en p17, hi p18, similarity 0.54
- en: You can easily study pollen germination by dusting some pollen from flowers such as pea, chickpea, Crotalaria, balsam and Vinca on a glass slide containing a drop of sugar solution (about 10 per cent). After about 15–30 minutes, observe the slide under the low power lens of the microscope. You are likely to see pollen tubes coming out of the pollen grains.
- hi: आप एक काँच की पट्टी (स्लाइड) पर जल शर्करा घोल (10 प्रतिशत घोल) की एक बूँद पर मटर, चना (चिकपी), क्रोटालेरिया, बालसम (गुलमेंहदी) तथा विनका (सदाबहार) के पुष्पों से कुछ पराग झाड़ कर गिराने के पश्चात् पराग जनन का अध्ययन आसानी के साथ कर सकते हैं। स्लाइड पर इन्हें रखने के 15 से 30 मिनट के बाद स्लाइड को कम शक्ति (लो पावर) के लेंस वाले सूक्ष्मदर्शी यंत्र पर रखकर अवलोकित करें। बहुत संभव है कि आप परागकणों से निकलती हुई पराग नलिकाओं को देख पाएँ।

### 8. ch 2 §2.2 ¶6 — en p6, hi p6, similarity 0.58 — the founder reads this one
- en: A functional mammary gland is characteristic of all female mammals. The mammary glands are paired structures (breasts) that contain glandular tissue and variable amount of fat. The glandular tissue of each breast is divided into 15-20 mammary lobes containing clusters of cells called alveoli (Figure 2.4). The cells of alveoli secrete milk, which is stored in the cavities (lumens) of alveoli. The alveoli open into mammary tubules. The tubules of each lobe join to form a mammary duct. Several mammary ducts join to form a wider mammary ampulla which is connected to lactiferous duct and opens at the nipple, from where milk is expressed during breastfeeding.
- hi: कार्यशील स्तन ग्रंथि सभी मादा स्तनधारियों का अभिलक्षण है। स्तन ग्रंथियाँ (स्तन) युग्म संरचना हैं जिनमें ग्रंथिल ऊतक और विभिन्न मात्रा में वसा होते हैं। प्रत्येक स्तन का ग्रंथिल ऊतक 15-20 स्तन पालियों (मैमरी लोब्स) में विभक्त होता है। इसमें कोशिकाओं के गुच्छ होते हैं जिन्हें कूपिका कहते हैं (चित्र 2.4)। कूपिकाओं की कोशिकाओं से दुग्ध स्रवित होता है और जो कूपिकाओं की गुहाओं (अवकाशिकाओं) में एकत्र होता है। कूपिकाएँ स्तन नलिकाओं में खुलती हैं। प्रत्येक पालि की नलिकाएँ मिलकर स्तनवाहिनी (मैमरी डक्ट्स) का निर्माण करती हैं। कई स्तन वाहिनियाँ मिलकर एक वृहद स्तन तुंबिका बनाती है जो दुग्ध वाहिनी (लैक्टिफेरस डक्ट) से जुड़ी होती हैं जिससे की दूध स्तन से बाहर निकलता है।

### 9. ch 2 §2.7 ¶1 — en p13, hi p15, similarity 0.62
- en: The average duration of human pregnancy is about 9 months which is called the gestation period. Vigorous contraction of the uterus at the end of pregnancy causes expulsion/delivery of the foetus. This process of delivery of the foetus (childbirth) is called parturition. Parturition is induced by a complex neuroendocrine mechanism. The signals for parturition originate from the fully developed foetus and the placenta which induce mild uterine contractions called foetal ejection reflex. This triggers release of oxytocin from the maternal pituitary. Oxytocin acts on the uterine muscle and causes stronger uterine contractions, which in turn stimulates further secretion of oxytocin. The stimulatory reflex between the uterine contraction and oxytocin secretion continues resulting in stronger and stronger contractions. This leads to expulsion of the baby out of the uterus through the birth canal – parturition. Soon after the infant is delivered, the placenta is also expelled out of the uterus. What do you think the doctors inject to induce delivery?
- hi: मानव में सगर्भता की औसत अवधि लगभग 9 माह होती है जिसे गर्भावधि (जेस्टेशन पीरियड) कहते हैं। सगर्भता के अंत में गर्भाशय के जोरदार संकुचनों के कारण गर्भ बाहर निकल आता है। गर्भ के बाहर निकलने की इस क्रिया को शिशु-जन्म या प्रसव (पारट्युरिशन) कहा जाता है। प्रसव एक जटिल तंत्रिअंतःस्रावी (न्यूरोइन्डोक्राइन) क्रियाविधि द्वारा प्रेरित होता है। प्रसव के लिए संकेत पूर्णविकसित गर्भ एवं अपरा से उत्पन्न होते हैं जो हल्के (माइल्ड) गर्भाशय संकुचनों को प्रेरित करते हैं जिन्हें गर्भ उत्क्षेपन प्रतिवर्त (फीटल इजेक्शन रेफलेक्स) कहते हैं। यह मातृ पीयूष ग्रंथि से ऑक्सीटोसिन के निकलने की क्रिया को सक्रिय बनाती है। ऑक्सीटोसिन गर्भाशय पेशी पर कार्य करता है और इसके कारण जोर-जोर से गर्भाशय सकुंचन होने लगते हैं। गर्भाशय संकुचन ऑक्सीटोसिन के अधिक स्रवण को उद्दीपित करता है। गर्भाशय संकुचनों तथा ऑक्सीटोसिन स्राव के बीच लगातार उद्दीपक प्रतिवर्त के कारण यह संकुचन तीव्र से तीव्रतर होता जाता है। इससे शिशु, माँ के गर्भाशय से जनन नाल द्वारा बाहर आ जाता है यानी प्रसव सम्पन्न हो जाता है। शिशु के जन्म के तुरन्त बाद ही अपरा भी गर्भाशय से बाहर निकल जाता है। आप क्या सोचते हैं, डॉक्टर प्रसव को प्रेरित करने के लिए इंजेक्शन देता है?

### 10. ch 3 §3.1 ¶3 — en p2, hi p2–3, similarity 0.52 — the founder reads this one
- en: Successful implementation of various action plans to attain reproductive health requires strong infrastructural facilities, professional expertise and material support. These are essential to provide medical assistance and care to people in reproduction-related problems like pregnancy, delivery, STDs, abortions, contraception, menstrual problems, infertility, etc. Implementation of better techniques and new strategies from time to time are also required to provide more efficient care and assistance to people. Statutory ban on amniocentesis for sex-determination to legally check increasing menace of female foeticides, massive child immunisation, etc., are some programmes that merit mention in this connection. In aminocentesis some of the amniotic fluid of the developing foetus is taken to analyse the fetal cells and dissolved substances. This procedure is used to test for the presence of certain genetic disorders such as, down syndrome, haemoplilia, sickle-cell anemia, etc., determine the survivability of the foetus.
- hi: जनन स्वास्थ्य प्राप्ति के लिए विभिन्न कार्ययोजनाओं के सफलतापूर्वक क्रियान्वयन के लिए मजबूत संरचनात्मक सुविधाओं, व्यावसायिक विशेषज्ञता, तथा भरपूर भौतिक सहारों की आवश्यकता होती है। लोगों को जनन संबंधी समस्याओं जैसे कि सगर्भता, प्रसव, यौन संचारित रोगों, गर्भपात, गर्भनिरोधकों, ऋतुस्राव (माहवारी) संबंधी समस्याओं, बंध्यता (बाँझपन) आदि के बारे में चिकित्सा सहायता एवं देखभाल उपलब्ध कराना आवश्यक है। समय-समय पर बेहतर तकनीकों और नई कार्यनीतियों को क्रियान्वित करने की भी आवश्यकता है; ताकि लोगों की अधिक सुचारू रूप से देखभाल और सहायता की जा सके। बढ़ती मादा भ्रूण हत्या की कानूनी रोक के लिए उल्बवेधन (ऐमीनोसैंटैसिस) जाँच लिंग परीक्षण पर वैधानिक प्रतिबंध तथा व्यापक बाल प्रतिरक्षीकरण (टीका) आदि कुछ महत्त्वपूर्ण कार्यक्रमों को भी शामिल किया गया है। एमीनो सेंटैसिस में एमनीओटिक द्रव्य में घुले पदार्थों व विकासशील भ्रूण की कोशिकाओं का विश्लेषण किया जाता है। इस विधि से भ्रूण में होने वाले विभिन्न आनुवांशिक विकार जैसे डाउन सिंड्रोम, हीमोफीलिया, सिकल सैन एनीमिया आदि की उपस्थिति का पता लगाया जाता है तथा जनन संबंधित विभिन्न क्षेत्रों पर अनुसंधान को बढ़ावा दिया जाता है।

### 11. ch 3 §3.2 ¶8 — en p5, hi p6, similarity 0.63 — the founder reads this one
- en: Progestogens alone or in combination with estrogen can also be used by females as injections or implants under the skin (Figure 3.3). Their mode of action is similar to that of pills and their effective periods are much longer. Administration of progestogens or progestogen-estrogen combinations or IUDs within 72 hours of coitus have been found to be very effective as emergency contraceptives as they could be used to avoid possible pregnancy due to rape or casual unprotected intercourse.
- hi: स्त्रियों द्वारा प्रोजेस्टोजन अकेले या फिर एस्ट्रोजन के साथ इसका संयोजन भी टीके या त्वचा के नीचे अंतर्रोप (इंप्लांट) के रूप में किया जा सकता है (चित्र 3.3)। इसके कार्य की विधि ठीक गर्भनिरोधक गोलियों की भाँति होती है तथा काफी लंबी अवधि के लिए प्रभावशाली होते हैं। मैथुन के 72 घंटे के भीतर ही प्रोजेस्टोजन या प्रोजेस्टोजन-एस्ट्रोजन संयोजनों का प्रयोग या आई यू डी के उपयोग को आपातकालिक गर्भनिरोधक के रूप में बहुत ही प्रभावी पाया गया है और इन्हें बलात्कार या सामान्य (लापरवाहीपूर्ण) असुरक्षित यौन संबंधों के कारण होने वाली संभावित सगर्भता से बचने के लिए लिया जा सकता है।

### 12. ch 4 §4 ¶1 — en p3, hi p3, similarity 0.58 — the founder reads this one
- en: Have you ever wondered why an elephant always gives birth only to a baby elephant and not some other animal? Or why a mango seed forms only a mango plant and not any other plant?
- hi: क्या आपने कभी यह सोचा कि हाथी हमेशा शिशु हाथी को ही जन्म देता है किसी अन्य जानवर को क्यों नहीं या आम के बीज से आम का पौधा ही उगता है, कोई अन्य पौधा क्यों नहीं?

### 13. ch 4 §4.2 ¶11 — en p8, hi p8, similarity 0.69
- en: The 1/4 : 1/2 : 1/4 ratio of TT: Tt: tt is mathematically condensable to the form of the binomial expression (ax +by)^2, that has the gametes bearing genes T or t in equal frequency of ½. The expression is expanded as given below : (1/2T + 1/2 t)^2 = (1/2T + 1/2t) X (1/2T + 1/2t) = 1/4 TT + 1/2Tt + 1/4 tt
- hi: TT: Tt: tt का 1/4 : 1/2 : 1/4 के अनुपात को गणित के द्विनामी पद (ax+ by)^2 में व्यक्त किया जा सकता है जिसमें T और t जीनधारी युग्मक समान आवृत्ति 1/2 में रहते हैं। इस पद का इस प्रकार विस्तार किया जाता है- (½T + ½ t)^2 = (½T + ½ t) x (½ T + ½ t)= ¼ TT + ½ Tt + ¼ tt

### 14. ch 4 §4.2.2 ¶1 — en p9, hi p9–10, similarity 0.55
- en: This law is based on the fact that the alleles do not show any blending and that both the characters are recovered as such in the F_2 generation though one of these is not seen at the F_1 stage. Though the parents contain two alleles during gamete formation, the factors or alleles of a pair segregate from each other such that a gamete receives only one of the two factors. Of course, a homozygous parent produces all gametes that are similar while a heterozygous one produces two kinds of gametes each having one allele with equal proportion.
- hi: इस नियम का आधार यह तथ्य है कि अलील आपस में घुलमिल (सम्मिश्रण) नहीं पाते और F_2 पीढ़ी में दोनों लक्षणों की फिर से अभिव्यक्ति हो जाती है, भले ही F_1 पीढ़ी में एक प्रकट नहीं होता। यद्यपि जनकों में दोनों अलील विद्यमान होते हैं। युग्मक बनने के समय कारकों के एक जोड़े या अलील के सदस्य विसंयोजित हो जाते हैं और युग्मक को दो में से एक ही कारक प्राप्त होता है। समयुग्मजी जनक द्वारा उत्पन्न सभी युग्मक समान होते हैं जबकि विषमयुग्मजी जनक दो प्रकार के युग्मक उत्पन्न करता है जिनमें समान अनुपात में एक एक अलील होता है।

### 15. ch 4 §4.2.2.2 ¶3 — en p12, hi p12, similarity 0.55
- en: Occasionally, a single gene product may produce more than one effect. For example, starch synthesis in pea seeds is controlled by one gene. It has two alleles (B and b). Starch is synthesised effectively by BB homozygotes and therefore, large starch grains are produced. In contrast, bb homozygotes have lesser efficiency in starch synthesis and produce smaller starch grains. After maturation of the seeds, BB seeds are round and the bb seeds are wrinkled. Heterozygotes produce round seeds, and so B seems to be the dominant allele. But, the starch grains produced are of intermediate size in Bb seeds. So if starch grain size is considered as the phenotype, then from this angle, the alleles show incomplete dominance.
- hi: कभी-कभी एकल जीन उत्पाद, एक से अधिक प्रभाव छोड़ सकता है। उदाहरण के लिए मटर के बीजों में मंड संश्लेषण का नियंत्रण एक जीन करता है। इसके दो अलील (B और b) होते हैं। कुशल रूप से मंड संश्लेषण BB समयुग्मजों से होता है और इस प्रकार विशाल मंड कण उत्पन्न हो जाते हैं। इसके विपरीत bb समयुग्मजी मंड संश्लेषण में कम कुशल होते हैं और छोटे मंड कणों का उत्पादन कर सकते हैं। पकने पर BB बीज गोल होते हैं और bb झुर्रीदार। विषमयुग्मनज गोल बीज पैदा करते हैं इसलिए लगता है कि B प्रभावी अलील है। Bb बीजों में उत्पन्न मंड कण मझोले आकार के होते हैं। अतएव यदि मंड कण का आकार एक फीनोटाइप मान लिया जाए तो इस दृष्टिकोण से यह अलील अपूर्ण प्रभाविता ही दर्शाता है।

### 16. ch 4 §4.8.2 ¶5 — en p25, hi p25, similarity 0.51 — the founder reads this one
- en: Phenylketonuria : This inborn error of metabolism is also inherited as the autosomal recessive trait. The affected individual lacks an enzyme that converts the amino acid phenylalanine into tyrosine. As a result of this phenylalanine is accumulated and converted into phenylpyruvic acid and other derivatives. Accumulation of these in brain results in mental retardation. These are also excreted through urine because of its poor absorption by kidney.
- hi: फीनाइल कीटोनूरिया- यह जन्मजात उपापचयी त्रुटि भी अलिंग क्रोमोसोम अप्रभावी लक्षण की भाँति ही वंशागति प्रदर्शित करती है। रोगी व्यक्ति में फीनाइल ऐलेनीन अमीनो अम्ल को टाइरोसीन अमीनो अम्ल में बदलने के लिए आवश्यक एक एंजाइम की कमी हो जाती है। परिणामस्वरुप फीनाइल ऐलेनीन एकत्रित होता जाता है और फीनाइलपाइरूविक अम्ल तथा अन्य व्युत्पन्नों में बदलता जाता है। इनके एकत्रीकरण से मानसिक दुर्बलता आ जाती है। वृक्क द्वारा कम अवशोषित हो सकने के कारण ये मूत्र के साथ उत्सर्जित हो जाते हैं।

### 17. ch 5 §5.2 ¶4 — en p6–7, hi p7, similarity 0.61
- en: Griffith was able to kill bacteria by heating them. He observed that heat-killed S strain bacteria injected into mice did not kill them. When he injected a mixture of heat-killed S and live R bacteria, the mice died. Moreover, he recovered living S bacteria from the dead mice.
- hi: ग्रीफिथ ने जीवाणु को गर्म करने पर उन्हें मृत पाया। उसने पाया कि गर्म करने पर मृत एस प्रभेद जीवाणु को चूहे में प्रवेश कराने से उसकी मृत्यु नहीं हो पायी। लेकिन जब उसमें गर्म करने से मृत एस व सजीव आर जीवाणु के मिश्रण को चूहे में प्रवेश कराया तब चूहे की मृत्यु हो गयी। इस मृत चूहे से उसने सजीव एस जीवाणु को विलगित किया।

### 18. ch 5 §5.2.2 ¶6 — en p9, hi p9, similarity 0.54
- en: If one examines each requirement one by one, because of rule of base pairing and complementarity, both the nucleic acids (DNA and RNA) have the ability to direct their duplications. The other molecules in the living system, such as proteins fail to fulfill first criteria itself.
- hi: यदि कोई क्षार युग्मन पूरकता के सिद्धांत को ध्यान में रखते हुए परीक्षण करता है, तब वह पाएगा कि दोनों न्यूक्लिक अम्लों (डीएनए व आरएनए) में स्वयं प्रतिकृति की क्षमता होती है। सजीव तंत्र के अन्य अणुओं में जैसे - प्रोटीन स्वयं प्रथम मापदंड को पूर्ण करने में असफल है।

### 19. ch 5 §5.2.2 ¶7 — en p9, hi p9–10, similarity 0.58
- en: The genetic material should be stable enough not to change with different stages of life cycle, age or with change in physiology of the organism. Stability as one of the properties of genetic material was very evident in Griffith’s ‘transforming principle’ itself that heat, which killed the bacteria, at least did not destroy some of the properties of genetic material. This now can easily be explained in light of the DNA that the two strands being complementary if separated by heating come together, when appropriate conditions are provided. Further, 2'-OH group present at every nucleotide in RNA is a reactive group and makes RNA labile and easily degradable. RNA is also now known to be catalytic, hence reactive. Therefore, DNA chemically is less reactive and structurally more stable when compared to RNA. Therefore, among the two nucleic acids, the DNA is a better genetic material.
- hi: आनुवंशिक पदार्थ इतना स्थायी होना चाहिए कि जीवन चक्र की विभिन्न अवस्थाओं, उम्र या जीव की शरीरक्रिया में परिवर्तन से इसमें परिवर्तन नहीं होना चाहिए। आनुवंशिक पदार्थ का स्थायित्व उसकी एक प्रमुख विशेषता है जो ग्रिफिथ के 'रूपांतरित कारक' से स्पष्ट है जिसमें ताप से जीवाणु की मृत्यु हो जाती है, लेकिन आनुवंशिक पदार्थ की कुछ विशेषताएँ नष्ट नहीं हो पाती हैं। डीएनए के परिपेक्ष में इस बात को और अच्छे ढंग से समझ सकते हैं कि डीएनए की दोनों रज्जुक एक दूसरे के पूरक होते हैं जो गर्म करने पर एक दूसरे से अलग हो जाते हैं; लेकिन पुनः उचित स्थिति के आने पर एक दूसरे से जुड़ जाते हैं। आरएनए के प्रत्येक न्यूक्लियोटाइड पर 2'-हाइड्रॉक्सिल समूह मिलता है यह क्रियाशील समूह है जिससे आरएनए अस्थिर व आसानी से विखंडित हो जाता है। इस कारण से डीएनए रासायनिक संगठन की दृष्टि से कम सक्रिय व संरचनात्मक दृष्टि में अधिक स्थायी होता है। इस कारण दोनों न्यूक्लिक अम्लों में डीएनए एक अच्छा आनुवंशिक पदार्थ माना गया है।

### 20. ch 5 §5.4.2 ¶2 — en p12, hi p13, similarity 0.55
- en: In addition to DNA-dependent DNA polymerases, many additional enzymes are required to complete the process of replication with high degree of accuracy. For long DNA molecules, since the two strands of DNA cannot be separated in its entire length (due to very high energy requirement), the replication occur within a small opening of the DNA helix, referred to as replication fork. The DNA-dependent DNA polymerases catalyse polymerisation only in one direction, that is 5'->3'. This creates some additional complications at the replicating fork. Consequently, on one strand (the template with polarity 3'->5'), the replication is continuous, while on the other (the template with polarity 5'->3'), it is discontinuous. The discontinuously synthesised fragments are later joined by the enzyme DNA ligase (Figure 5.8).
- hi: अधिक शुद्धता के साथ प्रतिकृति प्रक्रम को पूर्ण करने हेतु डीएनए पर निर्भर डीएनए पॉलीमरेज के साथ अन्य एंजाइम की आवश्यकता होती है। लंबे डीएनए अणुओं के दोनों रज्जुक एक साथ पृथक नहीं होते (क्योंकि इसके लिए अधिक ऊर्जा की आवश्यकता होती है), प्रतिकृति हेतु डीएनए कुंडलिनी छोटे-छोटे भाग में खुलते हैं जिसे प्रतिकृति द्विशाख कहते हैं। डीएनए पर निर्भर डीएनए पॉलीमरेज बहुलकन केवल एक दिशा 5' से 3' (5'->3') की ओर उत्प्रेरित करता है। यह प्रतिकृति द्विशाख पर कुछ जटिलता पैदा करती है फलस्वरूप, (3' -> 5' ध्रुवता वाली टेम्प्लेट) की लड़ी पर प्रतिकृत सतत् होता रहता है जबकि दूसरी लड़ी (5' -> 3' ध्रुवता वाले टेम्प्लेट) पर यह असतत् होता है। तत्पश्चात यह असतत् रूप से संश्लेषित खंड एंजाइम डीएनए लाइगेज द्वारा आपस में जुड़ जाते हैं (चित्र 5.8)।

### 21. ch 5 §5.9.1 ¶2 — en p26, hi p27, similarity 0.70
- en: (ii) The average gene consists of 3000 bases, but sizes vary greatly, with the largest known human gene being dystrophin at 2.4 million bases.
- hi: (ख) औसतन प्रत्येक जीन में 3000 क्षार स्थित हैं जिनके आकार में अधिक विभिन्नताएँ हैं। मनुष्य को ज्ञात सबसे बड़ी जीन डिसट्रॉफिन (Dystrophin) में 2.4 मिलियन क्षार मिले हैं।

### 22. ch 5 §5.10 ¶1 — en p27, hi p28, similarity 0.57
- en: As stated in the preceding section, 99.9 per cent of base sequence among humans is the same. Assuming human genome as 3 × 10^9 bp, in how many base sequences would there be differences? It is these differences in sequence of DNA which make every individual unique in their phenotypic appearance. If one aims to find out genetic differences between two individuals or among individuals of a population, sequencing the DNA every time would be a daunting and expensive task. Imagine trying to compare two sets of 3 × 10^6 base pairs. DNA fingerprinting is a very quick way to compare the DNA sequences of any two individuals.
- hi: पिछले खंड में यह बताया गया है कि मनुष्यों में मिलने वाले क्षार अनुक्रम लगभग 99.9 प्रतिशत समान होते हैं। यह मानते हुए कि मानव जीनोम में 3×10^9 क्षार युग्म है तो कितने क्षार अनुक्रमों में अंतर है? डीएनए के अनुक्रम में मिलने वाले अंतर व्यक्ति विशेष के समलक्षणीय रूप आकार को निर्धारित करते हैं। यदि किसी का उद्देश्य दो व्यक्तियों या किसी जनसंख्या के लोगों के बीच आनुवंशिक विभिन्नता का पता लगाना हो तो हमेशा डीएनए का अनुक्रम ज्ञात करना होगा जो एक कठिन व महँगा कार्य है। कल्पना करो कि दो 3×10^6 क्षार के बीच तुलना की है। दो व्यक्तियों के डीएनए अनुक्रमों के बीच तुलना करने हेतु डीएनए अंगुलिछापी एक त्वरित विधि है।

### 23. ch 6 §6.3 ¶5 — en p6, hi p7, similarity 0.66
- en: Man has bred selected plants and animals for agriculture, horticulture, sport or security. Man has domesticated many wild animals and crops. This intensive breeding programme has created breeds that differ from other breeds (e.g., dogs) but still are of the same group. It is argued that if within hundreds of years, man could create new breeds, could not nature have done the same over millions of years?
- hi: मनुष्य ने पादपों एवं पशुओं को कृषि, बागवानी, खेल तथा सुरक्षा के लिए चुना और बहुत सारे जंगली जानवरों को पालतू बनाया तथा खेती-फसलें उगाईं। इन सघन प्रजनन कार्यक्रमों द्वारा नस्लें तैयार हुईं; जोकि अन्य वंश प्रकारों से भिन्न थीं (जैसे कुत्ता); लेकिन फिर भी वह उसी समूह से है। यह तर्क दिया जा रहा है कि यद्यपि मनुष्य सौ साल के भीतर नई नस्ल पैदा कर सकता है तो प्रकृति भी यही कार्य लाखों-लाख वर्ष में क्यों नहीं कर सकती।

### 24. ch 6 §6.8 ¶4 — en p15, hi p16, similarity 0.64
- en: A rough sketch of the evolution of life forms, their times on a geological scale are indicated in (Figures 6.9 and 6.10).
- hi: जीवन के स्वरूपों के विकास, भू-भौगोलिक मापदंड पर उनकी समय तथा भू-भौगोलिक समयावधि एवं उनके संकेतों को स्थूल रेखा चित्रों 6.9 व 6.10 में दर्शाया गया है।

### 25. ch 7 §7.1 ¶9 — en p7, hi p7–8, similarity 0.61
- en: Wuchereria (W. bancrofti and W. malayi), the filarial worms cause a slowly developing chronic inflammation of the organs in which they live for many years, usually the lymphatic vessels of the lower limbs and the disease is called elephantiasis or filariasis (Figure 7.2). The genital organs are also often affected, resulting in gross deformities. The pathogens are transmitted to a healthy person through the bite by the female mosquito vectors.
- hi: वुचेरेरिया (वु. बैंक्रोफ्टाई और वु.मैलाई) ऐसे फाइलेरिआई कृमि हैं जिनके कारण अंगों में, प्रायः अर्धः पाद की लसीका वाहिकाओं (लिम्फैटिक वेसेल्स) में, धीरे-धीरे बढ़ने वाला दीर्घकालिक शोथ हो जाता है। ये कृमि इन अंगों में कई वर्षों तक रहते हैं। यह रोग हस्तिपाद (हाथी पाँव) या श्लीपद (एलीफैंशीएसिस) अथवा फाइलेरिया (फाइलेरिएसिस) कहलाता है (चित्र 7.2)। जननिक अंग भी अक्सर इस रोग से प्रभावित हो जाते हैं और इसकी वजह से बड़ी भारी विरूपताएँ आ जाती हैं। रोगवाहक मादा मच्छर जब किसी स्वस्थ व्यक्ति को काटती है, तो रोगजनक उस व्यक्ति के शरीर में संचारित हो जाते हैं।

### 26. ch 7 §7.2.4 ¶2 — en p10, hi p11, similarity 0.47 — the founder reads this one
- en: Recombinant DNA technology has allowed the production of antigenic polypeptides of pathogen in bacteria or yeast. Vaccines produced using this approach allow large scale production and hence greater availability for immunisation, e.g., hepatitis B vaccine produced from yeast.
- hi: पुनर्योगज डीएनए (रिकम्बिनेंट डीएनए) प्रौद्योगिकी से जीवाणु या खमीर (यीस्ट) में रोगजनक की प्रतिजनी पॉलिपेप्टाइड का उत्पादन होने लगा है। इस विधि से टीकों का बड़े पैमाने पर उत्पादन होने लगा है और इसलिए प्रतिरक्षीकरण हेतु उन टीकों की उपलब्धता खूब बढ़ी है, जैसे-खमीर से बनने वाला यकृतशोथ बी टीका।

### 27. ch 7 §7.5.3 ¶2 — en p19, hi p22, similarity 0.68
- en: There may even be some far-reaching implications of drug/alcohol abuse. If an abuser is unable to get money to buy drugs/alcohol he/she may turn to stealing. The adverse effects are just not restricted to the person who is using drugs or alcohol. At times, a drug/alcohol addict becomes the cause of mental and financial distress to his/her entire family and friends.
- hi: ड्रग/ऐल्कोहल के कुप्रयोग के दूरगामी परिणाम भी हो सकते हैं। अगर कुप्रयोगकर्ता को ड्रग/ऐल्कोहल खरीदने के लिए पैसे नहीं मिलें तो वह चोरी का सहारा ले सकता/सकती है। ये प्रतिकूल प्रभाव केवल ड्रग/ऐल्कोहल का सेवन करने वाले तक सीमित नहीं रहता। कभी-कभी ड्रग/ऐल्कोहल आदि का अपने परिवार या मित्र आदि के लिए भी मानसिक और आर्थिक कष्ट का कारण बन सकता/सकती है।

### 28. ch 7 §7.5.3 ¶4 — en p20, hi p22, similarity 0.68
- en: The use of alcohol during adolescence may also have long-term effects. It could lead to heavy drinking in adulthood. The chronic use of drugs and alcohol damages nervous system and liver (cirrhosis). The use of drugs and alcohol during pregnancy is also known to adversely affect the foetus.
- hi: किशोरावस्था के दौरान ऐल्कोहल के सेवन का दीर्घकालिक प्रभाव भी हो सकता हैं; इससे प्रौढ़ावस्था में बहुत अधिक पीने की लत लग सकती है। ड्रग और ऐल्कोहल के चिरकारी उपयोग से तंत्रिका तंत्र और यकृत को क्षति (सेरोसिस) पहुँचती है। ऐसा भी ज्ञात है कि गर्भावस्था के दौरान ड्रगों एवं ऐल्कोहल का उपयोग गर्भ पर प्रतिकूल प्रभाव डालता है।

### 29. ch 8 §8.5 ¶2 — en p8–9, hi p9–10, similarity 0.52
- en: Biological control of pests and diseases: In agriculture, there is a method of controlling pests that relies on natural predation rather than introduced chemicals. A key belief of the organic farmer is that biodiversity furthers health. The more variety a landscape has, the more sustainable it is. The organic farmer, therefore, works to create a system where the insects that are sometimes called pests are not eradicated, but instead are kept at manageable levels by a complex system of checks and balances within a living and vibrant ecosystem. Contrary to the ‘conventional’ farming practices which often use chemical methods to kill both useful and harmful life forms indiscriminately, this is a holistic approach that seeks to develop an understanding of the webs of interaction between the myriad of organisms that constitute the field fauna and flora. The organic farmer holds the view that the eradication of the creatures that are often described as pests is not only possible, but also undesirable, for without them the beneficial predatory and parasitic insects which depend upon them as food or hosts would not be able to survive. Thus, the use of biocontrol measures will greatly reduce our dependence on toxic chemicals and pesticides. An important part of the biological farming approach is to become familiar with the various life forms that inhabit the field, predators as well as pests, and also their life cycles, patterns of feeding and the habitats that they prefer. This will help develop appropriate means of biocontrol.
- hi: पीड़क तथा रोगों का जैव नियंत्रण- कृषि में, पीड़कों के नियंत्रण की यह विधि रसायनों के प्रयोग की तुलना में प्राकृतिक परभक्षण पर अधिक निर्भर करती है। आर्गेनिक फॉर्मर (कृषक) के अनुसार जैव विविधता ही स्वास्थ्य की कुंजी है। भूदृश्य पर जितनी अधिक किस्में होंगी, वह उतनी ही अधिक स्थायित्व प्रदान करती हैं अतः आर्गेनिक फॉर्मर (कृषक) एक तंत्र को विकसित करने के लिए कार्य करते हैं, जिसमें कीट, इन्हें पीड़क भी कहते हैं, उन्मीलित न हो, वे इसके बजाय उन्हें नियंत्रणीय स्तर पर एक जीवित तथा कंपायमान पारिस्थितिक तंत्र के भीतर संतुलन तथा जाँच के जटिल तंत्र का निर्माण करते हैं। अधिक परंपरागत 'फार्मिंग प्रैक्टिस' के विपरीत बहुधा लाभप्रद तथा हानिप्रद बागवानी जीवों को रसायन प्रयोग द्वारा मार दिया जाता है। जैव नियंत्रण एक प्रकार का समग्रतात्मक दृष्टिकोण है, जिससे करोड़ों जीवों के मध्य पारस्परिक क्रियाओं के जाल की व्याख्या का विकास होता है। खेत प्राणिजात (फौना) तथा पेड़-पौधों (फ्लोरा) का निर्माण करते हैं। आर्गेनिक फॉर्मर (कृषक)में बहुधा एक दृष्टिकोण विकसित हो जाता है; उदाहरणार्थ जीवों का उन्मूलन, जिन्हें बहुधा पीड़क कहा गया है, उनका उन्मूलन केवल संभव ही नहीं; बल्कि अवांछनीय भी है। इनके बिना लाभप्रद परभक्षी तथा परजीवी कीट जीवित नहीं रह पायेंगे जो पीड़कों पर अपने पोषण अथवा भोजन के लिए आश्रित है। इस प्रकार जैवनियंत्रण विधि से विषाक्त रसायन तथा पीड़कनाशियों पर हमारी जो आश्रिता है, वह काफी हद तक घट जायेगी। जीवविज्ञानी फॉर्मिंग के उपगमन (प्रस्ताव) का महत्त्वपूर्ण भाग विभिन्न जीवन के रूपों से परिचित होता है। उदाहरणार्थ खेतों को परभक्षी तथा पीड़कों द्वारा उनके जीवन चक्र, आहार ग्रहण करने की विधि तथा वास स्थल के रूप में पसंद किया है। यह सब मिलकर जैव नियंत्रण के उचित साधनों को विकसित करने में सहायक होते हैं।

### 30. ch 10 §10.1 ¶1 — en p1–2, hi p1, similarity 0.69
- en: Let us take a look at the three options that can be thought for increasing food production (i) agro-chemical based agriculture; (ii) organic agriculture; and
- hi: खाद्य उत्पादन में वृद्धि हेतु हम तीन संभावनाओं के बारे में सोच सकते हैं-— (क) कृषि रसायन आधारित कृषि (ख) कार्बनिक कृषि और

### 31. ch 10 §10.1 ¶11 — en p3, hi p3–4, similarity 0.48 — the founder reads this one
- en: Bt Cotton: Some strains of Bacillus thuringiensis produce proteins that kill certain insects such as lepidopterans (tobacco budworm, armyworm), coleopterans (beetles) and dipterans (flies, mosquitoes). B. thuringiensis forms protein crystals during a particular phase of their growth. These crystals contain a toxic insecticidal protein. Why does this toxin not kill the Bacillus? Actually, the Bt toxin protein exist as inactive protoxins but once an insect ingest the inactive toxin, it is converted into an active form of toxin due to the alkaline pH of the gut which solubilise the crystals. The activated toxin binds to the surface of midgut epithelial cells and create pores that cause cell swelling and lysis and eventually cause death of the insect.
- hi: बीटी कपास— बैसीलस थूरीनजिएंसीस की कुछ नस्लें ऐसी प्रोटीन का निर्माण करती हैं जो विशिष्ट कीटों जैसे— लीथीडोप्टेशन (तंबाकू की कलिका कीड़ा, सैनिक कीड़ा), कोलियोप्टेरान (भृंग) व डीप्टेरान (मक्खी, मच्छर) को मारने में सहायक है। बी. थूरीनजिएंसीस अपनी वृद्धि के विशेष अवस्था में कुछ प्रोटीन रवा का निर्माण करती है। इन रवों में विषाक्त कीटनाशक प्रोटीन होता है। यह जीवविष बैसीलस को क्यों नहीं मारता है? वास्तव में बीटी जीव-विष प्रोटीन, प्राक्जीव विष निष्क्रिय रूप में होता है, ज्योंहि कीट इस निष्क्रिय जीव विष को खाता है, इसके रवे आँत में क्षारीय पी एच के कारण घुलनशील होकर सक्रिय रूप में परिवर्तन हो जाते हैं। सक्रिय जीव विष मध्य आँत के उपकलीय कोशिकाओं की सतह से बँधकर उसमें छिद्रों का निर्माण करते हैं, जिस कारण से कोशिकाएँ फूलकर फट जाती हैं और परिणामस्वरूप कीट की मृत्यु हो जाती है।

### 32. ch 10 §10.1 ¶14 — en p4, hi p4–5, similarity 0.46
- en: Using Agrobacterium vectors, nematode-specific genes were introduced into the host plant (Figure 10.2). The introduction of DNA was such that it produced both sense and anti-sense RNA in the host cells. These two RNA’s being complementary to each other formed a double stranded (dsRNA) that initiated RNAi and thus, silenced the specific mRNA of the nematode. The consequence was that the parasite could not survive in a transgenic host expressing specific interfering RNA. The transgenic plant therefore got itself protected from the parasite (Figure 10.2).
- hi: एग्रोबैक्टिरियम संवाहकों का उपयोग कर सूत्रकृमि विशिष्ट जीनों को परपोषी पौधों में प्रवेश कराया जा चुका है (चित्र 10.2)। डीएनए का प्रवेश इस प्रकार कराया जाता है कि परपोषी कोशिकाओं में अर्थ (सैंस) व प्रति-अर्थ (ऐंटीसैंस) आरएनए का निर्माण करता है। ये दोनों आरएनए एक दूसरे के पूरक होते हैं जो द्विसूत्रीय आरएनए का निर्माण करते हैं; जिससे आरएनए अंतरक्षेप प्रारंभ होता है और इसी कारण से सूत्रकृमि के विशिष्ट दूत आरएनए निष्क्रिय हो जाते हैं। इसके फलस्वरूप पारजीवी परपोषी में विशिष्ट अंतरक्षेपी आरएनए की उपस्थिति से परजीवी जीवित नहीं रह पाता है। इस प्रकार पारजीवी पौधे अपनी सुरक्षा परजीवी से करते हैं (चित्र 10.2)।

### 33. ch 10 §10.2.3 ¶4 — en p7, hi p7–8, similarity 0.49
- en: ELISA is based on the principle of antigen-antibody interaction. Infection by pathogen can be detected by the presence of antigens (proteins, glycoproteins, etc.) or by detecting the antibodies synthesised against the pathogen.
- hi: एंजाइम सहलग्न प्रतिरक्षा शोषक आमापन (एलाइजा) प्रतिजन- प्रतिरक्षी पारस्परिक क्रिया के सिद्धांत पर कार्य करता है। रोग जनकों के द्वारा उत्पन्न संक्रमण की पहचान प्रतिजनों (प्रोटीनजन, ग्लाइकोप्रोटींस आदि) की उपस्थिति या रोग जनकों के विरूद्ध संश्लेषित प्रतिरक्षी की पहचान के आधार पर की जाती है।

### 34. ch 10 §10.4 ¶6 — en p9, hi p9–10, similarity 0.54
- en: Rice is an important food grain, the presence of which goes back thousands of years in Asia's agricultural history. There are an estimated 200,000 varieties of rice in India alone. The diversity of rice in India is one of the richest in the world. Basmati rice is distinct for its unique aroma and flavour and 27 documented varieties of Basmati are grown in India. There is reference to Basmati in ancient texts, folklore and poetry, as it has been grown for centuries. In 1997, an American company got patent rights on Basmati rice through the US Patent and Trademark Office. This allowed the company to sell a 'new' variety of Basmati, in the US and abroad. This 'new' variety of Basmati had actually been derived from Indian farmer's varieties. Indian Basmati was crossed with semi-dwarf varieties and claimed as an invention or a novelty. The patent extends to functional equivalents, implying that other people selling Basmati rice could be restricted by the patent. Several attempts have also been made to patent uses, products and processes based on Indian traditional herbal medicines, e.g., turmeric neem. If we are not vigilant and we do not immediately counter these patent applications, other countries/individuals may encash on our rich legacy and we may not be able to do anything about it.
- hi: धान एक महत्त्वपूर्ण खाद्यान्न है जिसके बारे में हजारों वर्ष पूर्व एशिया के कृषि के इतिहास में वर्णन मिलता है। एक अनुमान के अनुसार केवल भारत में धान की लगभग 2 लाख किस्में मिलती हैं। भारत में धान की जो विविधता है, वह विश्व की सर्वाधिक विविधताओं में एक है। बासमती धान अपनी सुगंध व स्वाद के लिए मशहूर है और इसकी 27 पहचानी गयी किस्में भारत में उगायी जाती हैं। पुराने ग्रंथों, लोकसाहित्य व कविताओं में बासमती का वर्णन मिलता है, जिससे यह पता चलता है कि यह कई सौ वर्ष पहले से उगाया जाता रहा है। वर्ष 1997 में एक अमरीकी कंपनी ने बासमती धान पर अमेरिकन एकस्व व ट्रेडमार्क कार्यालय द्वारा एकस्व अधिकार प्राप्त कर लिया था। इससे कंपनी बासमती की नई किस्मों को अमेरिका व विदेशों में बेच सकती है। बासमती की यह नयी किस्म वास्तव में भारतीय किसानों की किस्मों से विकसित की गयी थी। भारतीय बासमती को अर्द्ध बौनी किस्मों से संकरण कराकर नयी खोज या एक नयी उपलब्धि का दावा किया था। एकाधिकार के लागू होने के बाद इस एकाधिकार के तहत अन्य लोगों द्वारा बासमती का विक्रय प्रतिबंधित हो सकता था।

### 35. ch 10 §10.4 ¶9 — en p9, hi p10, similarity 0.59 — the founder reads this one
- en: There has been growing realisation of the injustice, inadequate compensation, and benefit sharing between developed and developing countries. Therefore, some nations are developing laws to prevent such unauthorised exploitation of their bio-resources and traditional knowledge.
- hi: विकसित व विकासशील राष्ट्रों के बीच अन्याय, अपर्याप्त क्षतिपूर्ति व लाभों की भागीदारी के प्रति भावना विकसित हो रही है। इसके कारण कुछ राष्ट्रों ने अपने जैव संसाधनों व परंपरागत ज्ञान का बिना पूर्व अनुमति के उपयोग पर प्रतिबंध के लिए नियमों को बना रहे हैं।

### 36. ch 11 §11.1.1 ¶4 — en p4, hi p5, similarity 0.65
- en: A population at any given time is composed of individuals of different ages. If the age distribution (per cent individuals of a given age or age group) is plotted for the population, the resulting structure is called an age pyramid (Figure 11.1). For human population, the age pyramids generally show age distribution of males and females in a diagram. The shape of the pyramids reflects the growth status of the population - (a) whether it is growing, (b) stable or (c) declining.
- hi: किसी दिए गए समय में समष्टि भिन्न आयु वाले व्यष्टियों से मिलकर बनती है। अगर समष्टि के लिए आयु वितरण (दी गई आयु अथवा आयु वर्ग के व्यष्टियों का प्रतिशत) आलेखित (प्लॉटेड) किया जाता है तो बनने वाली संरचना आयु पिरैमिड कहलाती है (चित्र 11.1)। मानव समष्टि के लिए आयु पिरैमिड आमतौर पर नर और स्त्रियों का आयु वितरण संयुक्त आरेख को दर्शाता है। पिरैमिड का आकार समष्टि की स्थिति प्रतिबिंबित करता है - (क) क्या यह बढ़ रहा है, (ख) स्थिर है या (ग) घट रहा है।

### 37. ch 11 §11.1.2 ¶7 — en p6, hi p7, similarity 0.69
- en: You can see from the above equation (Fig. 11.2) that population density will increase if the number of births plus the number of immigrants (B + I) is more than the number of deaths plus the number of emigrants (D + E). Under normal conditions, births and deaths are the most important factors influencing population density, the other two factors assuming importance only under special conditions. For instance, if a new habitat is just being colonised, immigration may contribute more significantly to population growth than birth rates.
- hi: ऊपर दिए गए समीकरण चित्र (11.2) से आप देख सकते हैं कि अगर जन्म लेने वालों की संख्या जमा आप्रवासियों की संख्या (B+I) मरने वालों की संख्या जमा उत्प्रवासियों की संख्या (D+E) से अधिक है तो समष्टि घनत्व बढ़ जाएगा अन्यथा यह घट जाएगा। सामान्य परिस्थितियों में, समष्टि घनत्व को प्रभावित करने वाले कारकों में जन्म और मृत्यु सबसे महत्त्वपूर्ण है, दूसरे दो कारक विशेष परिस्थितियों में ही महत्त्वपूर्ण बन जाते हैं। उदाहरण के लिए, अगर आवास की बस्ती अभी बनी है, तो जन्म दरों की अपेक्षा आप्रवासन समष्टि की वृद्धि में अधिक महत्त्वपूर्ण है।

### 38. ch 11 §11.1.4 ¶14 — en p14, hi p14–15, similarity 0.59
- en: (iv) Commensalism: This is the interaction in which one species benefits and the other is neither harmed nor benefited. An orchid growing as an epiphyte on a mango branch, and barnacles growing on the back of a whale benefit while neither the mango tree nor the whale derives any apparent benefit. The cattle egret and grazing cattle in close association, a sight you are most likely to catch if you live in farmed rural areas, is a classic example of commensalism. The egrets always forage close to where the cattle are grazing because the cattle, as they move, stir up and flush out insects from the vegetation that otherwise might be difficult for the egrets to find and catch. Another example of commensalism is the interaction between sea anemone that has stinging tentacles and the clown fish that lives among them. The fish gets protection from predators which stay away from the stinging tentacles. The anemone does not appear to derive any benefit by hosting the clown fish.
- hi: (घ) सहभोजिता — यह ऐसी पारस्परिक क्रिया है जिसमें एक जाति को लाभ होता है और दूसरी को न हानि न लाभ होता है। आम की शाखा पर अधिपादप (एपीफाइट) के रूप में उगने वाला ऑर्किड और ह्वेल की पीठ को आवास बनाने वाले बार्नेकल को फायदा होता है जबकि आम के पेड़ और ह्वेल को उनसे कोई लाभ नहीं होता। पक्षी बगुला और चारण पशु निकट साहचर्य में रहते हैं। यदि आप कृषि फार्म वाले ग्रामीण क्षेत्र में रहते हैं तो आपको यह दृश्य देखने को मिलेगा। सहभोजिता का यह उत्कृष्ट उदाहरण है। जहाँ पशु चरते हैं उसके पास ही बगुले भोजन प्राप्ति के लिए रहते हैं क्योंकि जब पशु चलते हैं तो वनस्पति को हिलाते हैं और उसमें से कीट बाहर निकालते हैं। बगुले उन कीटों को खाते हैं अन्यथा वनस्पतिक कीटों को ढूँढ़ना और पकड़ना बगुले के लिए कठिन होता। सहभोजिता का दूसरा उदाहरण समुद्री ऐनिमोन दंशन स्पर्शक (स्टिंगिंग टेंटेकल) होते हैं, जिसमें उनके बीच रहने वाली क्लाउन मछली का है। मछली को परभक्षियों से सुरक्षा मिलती है जो दंशन स्पर्शकों से दूर रहते हैं। क्लाउन मछली से ऐनिमोन को कोई लाभ मिलता हो ऐसा नहीं लगता।

### 39. ch 12 §12.3 ¶4 — en p4, hi p5, similarity 0.58
- en: Decomposition is largely an oxygen-requiring process. The rate of decomposition is controlled by chemical composition of detritus and climatic factors. In a particular climatic condition, decomposition rate is slower if detritus is rich in lignin and chitin, and quicker, if detritus is rich in nitrogen and water-soluble substances like sugars. Temperature and soil moisture are the most important climatic factors that regulate decomposition through their effects on the activities of soil microbes. Warm and moist environment favour decomposition whereas low temperature and anaerobiosis inhibit decomposition resulting in build up of organic materials.
- hi: अपघटन एक प्रक्रिया है जिसमें ऑक्सीजन की आवश्यकता होती है। अपघटन की दर जलवायुवीय घटकों तथा अपरद के रासायनिक संघटनों द्वारा निर्धारित होती है। एक विशिष्ट जलवायुवीय परिस्थिति में; यदि अपरद काइटिन तथा लिग्निन से भरपूर होता है तब अपघटन दर धीमी होती हैं, यदि अपरद नाइट्रोजन तथा जलविलेय तत्त्वों जैसे चीनी आदि से भरपूर होता है तब यह तेज होती है। ताप एवं मृदा की नमी बहुत ही महत्त्वपूर्ण जलवायुवीय घटक है जो मृदा के सूक्ष्मजीवों की क्रियाओं द्वारा अपघटन की गति को नियमित करते हैं। गरम एवं आर्द्र पर्यावरण में अपघटन की गति तेज होती है जबकि निम्न ताप एवं अवायुजीवन अपघटन की गति को धीमा करती है जिसके परिणाम स्वरूप कार्बनिक पदार्थों का भंडार जमा हो जाता है।

### 40. ch 12 §12.4 ¶4 — en p5, hi p5–6, similarity 0.61
- en: You have read about the food chains and webs that exist in nature. Starting from the plants (or producers) food chains or rather webs are formed such that an animal feeds on a plant or on another animal and in turn is food for another. The chain or web is formed because of this interdependency. No energy that is trapped into an organism remains in it for ever. The energy trapped by the producer, hence, is either passed on to a consumer or the organism dies. Death of organism is the beginning of the detritus food chain/web.
- hi: आपने खाद्य शृंखलाओं तथा जालों (बेब्स) के बारे में पढ़ा है जो कि प्रकृति में विद्यमान हैं। पादप (या उत्पादक) से प्रारंभ होकर खाद्य शृंखला या जाल इस प्रकार से बने होते हैं कि प्रत्येक प्राणी जो एक पादप से आहार ग्रहण करता है या अन्य प्राणी पर निर्भर करता है और बदले में वह किसी अन्य के लिए आहार बनाता है। इस परस्पर अंतर निर्भरता के कारण शृंखला जाल (वेब) की रचना होती है। किसी भी जीव द्वारा आबद्ध (ग्रहण) की गई ऊर्जा सदैव के लिए संचित नहीं रहती है। उत्पादक द्वारा आबद्ध की गई ऊर्जा या तो उपभोक्ता को भंज दी जाती है या वह जीव मृत हो जाती है। एक जीव की मृत्यु अपरद खाद्य शृंखला / जाल की शरुआत होती है।

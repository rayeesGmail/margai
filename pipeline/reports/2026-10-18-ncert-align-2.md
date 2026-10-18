# margai-pipeline ncert align

- run: 2026-10-18 13:45 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 673 English and 639 Hindi row(s) in 13 chapter(s)
- result: ok
request id: pipeline-ncert-align-13fc1c79-72d8-491f-8217-679566361902
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 639 | 358904 | ₹7.38 |

## sections whose rows do not pair one to one

en counts the English rows aligned: a number an `unprinted` entry left before the section's last Hindi row is listed below, not here; one left after it is aligned, and the load's report names it
| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 1 | 1.2.1 | 12 | 12 | 2 |
| 1 | 1.2.2 | 7 | 8 | 1 |
| 1 | 1.2.3 | 22 | 23 | 3 |
| 1 | 1.4.3 | 9 | 8 | 1 |
| 4 | 4.1 | 2 | 1 | 1 |
| 4 | 4.2 | 17 | 16 | 3 |
| 4 | 4.2.2.2 | 4 | 5 | 1 |
| 4 | 4.3 | 3 | 3 | 2 |
| 4 | 4.3.1 | 6 | 5 | 1 |
| 4 | 4.3.2 | 5 | 6 | 1 |
| 4 | 4.6.1 | 1 | 2 | 1 |
| 4 | 4.6.2 | 2 | 1 | 1 |
| 4 | 4.6.3 | 0 | 5 | 5 |
| 4 | 4.8.2 | 6 | 6 | 2 |
| 4 | 4.8.3 | 6 | 0 | 6 |
| 5 | 5.1.1 | 12 | 11 | 1 |
| 5 | 5.2.2 | 10 | 7 | 3 |
| 5 | 5.4.1 | 8 | 6 | 2 |
| 5 | 5.5 | 2 | 1 | 1 |
| 5 | 5.5.1 | 4 | 6 | 2 |
| 5 | 5.6 | 16 | 14 | 4 |
| 5 | 5.6.1 | 14 | 7 | 7 |
| 5 | 5.9 | 7 | 9 | 2 |
| 5 | 5.9.1 | 9 | 7 | 2 |
| 6 | 6.3 | 8 | 8 | 2 |
| 6 | 6.7 | 4 | 2 | 2 |
| 6 | 6.8 | 4 | 3 | 1 |
| 6 | 6.9 | 1 | 2 | 1 |
| 7 | 7 | 4 | 7 | 3 |
| 7 | 7.1 | 12 | 13 | 1 |
| 7 | 7.2 | 2 | 1 | 1 |
| 7 | 7.2.1 | 3 | 5 | 2 |
| 7 | 7.2.2 | 3 | 2 | 1 |
| 9 | 9 | 3 | 2 | 1 |
| 9 | 9.1 | 8 | 6 | 4 |
| 9 | 9.2.1 | 12 | 11 | 1 |
| 9 | 9.3.5 | 6 | 4 | 2 |
| 10 | 10.1 | 14 | 10 | 4 |
| 10 | 10.2.1 | 4 | 2 | 2 |
| 11 | 11.1.2 | 18 | 12 | 6 |
| 12 | 12.4 | 13 | 13 | 2 |
| 12 | 12.5 | 7 | 6 | 1 |
| 13 | 13.1.2 | 4 | 2 | 2 |

## English paragraphs the Hindi does not print — numbers an `unprinted` entry left before the section's last Hindi row, not aligned

none

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 1 §1.2.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 1 §1.2.1: hi ¶8+¶9 = en ¶9 (join hi)
- ch 1 §1.2.2: hi ¶7+¶8 = en ¶7 (join hi)
- ch 1 §1.2.3: hi ¶11 = en ¶11+¶12 (split hi)
- ch 1 §1.2.3: hi ¶15+¶16 = en ¶16 (join hi)
- ch 1 §1.2.3: hi ¶18+¶19 = en ¶18 (join hi)
- ch 1 §1.4.3: hi ¶8 = en ¶8+¶9 (split hi)
- ch 4 §4.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.2: hi ¶2+¶3 = en ¶3 (join hi)
- ch 4 §4.2: hi ¶14 = en ¶14+¶15 (split hi)
- ch 4 §4.2.2.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 4 §4.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.3: hi ¶2+¶3 = en ¶3 (join hi)
- ch 4 §4.3.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 4 §4.3.2: hi ¶5+¶6 = en ¶5 (join hi)
- ch 4 §4.6.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 4 §4.6.2: en ¶2 has no hindi
- ch 4 §4.6.3: hi ¶1 has no english
- ch 4 §4.6.3: hi ¶2 has no english
- ch 4 §4.6.3: hi ¶3 has no english
- ch 4 §4.6.3: hi ¶4 has no english
- ch 4 §4.6.3: hi ¶5 has no english
- ch 4 §4.8.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.8.2: hi ¶3+¶4 = en ¶4 (join hi)
- ch 4 §4.8.3: en ¶1 has no hindi
- ch 4 §4.8.3: en ¶2 has no hindi
- ch 4 §4.8.3: en ¶3 has no hindi
- ch 4 §4.8.3: en ¶4 has no hindi
- ch 4 §4.8.3: en ¶5 has no hindi
- ch 4 §4.8.3: en ¶6 has no hindi
- ch 5 §5.1.1: hi ¶10 = en ¶10+¶11 (split hi)
- ch 5 §5.2.2: hi ¶3 = en ¶3+¶4 (split hi)
- ch 5 §5.2.2: en ¶5 has no hindi
- ch 5 §5.2.2: hi ¶7 = en ¶9+¶10 (split hi)
- ch 5 §5.4.1: hi ¶3 = en ¶3+¶4 (split hi)
- ch 5 §5.4.1: en ¶5 has no hindi
- ch 5 §5.5: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.5.1: hi ¶3+¶4 = en ¶3 (join hi)
- ch 5 §5.5.1: hi ¶5+¶6 = en ¶4 (join hi)
- ch 5 §5.6: hi ¶6+¶7 = en ¶6 (join hi)
- ch 5 §5.6: hi ¶10 = en ¶9+¶10 (split hi)
- ch 5 §5.6: hi ¶11 = en ¶11+¶12 (split hi)
- ch 5 §5.6: hi ¶12 = en ¶13+¶14 (split hi)
- ch 5 §5.6.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 5 §5.6.1: hi ¶3 = en ¶4+¶5 (split hi)
- ch 5 §5.6.1: hi ¶4 = en ¶6+¶7 (split hi)
- ch 5 §5.6.1: hi ¶5 = en ¶8+¶9 (split hi)
- ch 5 §5.6.1: hi ¶6 = en ¶10+¶11 (split hi)
- ch 5 §5.6.1: en ¶12 has no hindi
- ch 5 §5.6.1: en ¶13 has no hindi
- ch 5 §5.9: hi ¶4 has no english
- ch 5 §5.9: hi ¶5+¶6 = en ¶4 (join hi)
- ch 5 §5.9.1: hi ¶4 = en ¶4+¶5 (split hi)
- ch 5 §5.9.1: hi ¶5 = en ¶6+¶7 (split hi)
- ch 6 §6.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 6 §6.3: hi ¶2+¶3 = en ¶3 (join hi)
- ch 6 §6.7: en ¶1 has no hindi
- ch 6 §6.7: hi ¶1 = en ¶2+¶3 (split hi)
- ch 6 §6.8: hi ¶3 = en ¶3+¶4 (split hi)
- ch 6 §6.9: hi ¶1+¶2 = en ¶1 (join hi)
- ch 7 §7: hi ¶2 has no english
- ch 7 §7: hi ¶3 has no english
- ch 7 §7: hi ¶4+¶5 = en ¶2 (join hi)
- ch 7 §7.1: hi ¶5+¶6 = en ¶5 (join hi)
- ch 7 §7.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 7 §7.2.1: hi ¶1+¶2 = en ¶1 (join hi)
- ch 7 §7.2.1: hi ¶3+¶4 = en ¶2 (join hi)
- ch 7 §7.2.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 9 §9: hi ¶2 = en ¶2+¶3 (split hi)
- ch 9 §9.1: en ¶1 has no hindi
- ch 9 §9.1: hi ¶1 = en ¶2+¶3 (split hi)
- ch 9 §9.1: hi ¶2 = en ¶4+¶5 (split hi)
- ch 9 §9.1: hi ¶4 has no english
- ch 9 §9.2.1: hi ¶4 = en ¶4+¶5 (split hi)
- ch 9 §9.3.5: hi ¶2 = en ¶2+¶3 (split hi)
- ch 9 §9.3.5: hi ¶3 = en ¶4+¶5 (split hi)
- ch 10 §10.1: en ¶1 has no hindi
- ch 10 §10.1: hi ¶1 = en ¶2+¶3 (split hi)
- ch 10 §10.1: hi ¶2 = en ¶4+¶5 (split hi)
- ch 10 §10.1: hi ¶4 = en ¶7+¶8 (split hi)
- ch 10 §10.2.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 10 §10.2.1: hi ¶2 = en ¶3+¶4 (split hi)
- ch 11 §11.1.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 11 §11.1.2: en ¶3 has no hindi
- ch 11 §11.1.2: en ¶4 has no hindi
- ch 11 §11.1.2: hi ¶2 = en ¶5+¶6 (split hi)
- ch 11 §11.1.2: hi ¶11 = en ¶15+¶16 (split hi)
- ch 11 §11.1.2: en ¶17 has no hindi
- ch 12 §12.4: hi ¶5+¶6 = en ¶5 (join hi)
- ch 12 §12.4: hi ¶7 = en ¶6+¶7 (split hi)
- ch 12 §12.5: hi ¶6 = en ¶6+¶7 (split hi)
- ch 13 §13.1.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 13 §13.1.2: hi ¶2 = en ¶3+¶4 (split hi)

## pairs weaker than 0.45 — read both against the pages

- ch 1 §1.2.1: hi ¶5 = en ¶6 — similarity 0.36 — en «Microsporogenesis : As the anther develops, the cells…» · hi «लघुबीजाणुजनन — जैसे-जैसे परागकोश विकसित होता है, बीजाणुजन…»
- ch 1 §1.2.2: hi ¶2 = en ¶2 — similarity 0.40 — en «The Megasporangium (Ovule) : Let us familiarise ourselves…» · hi «गुरूबीजाणुधानी (बीजांड) — आइए एक प्ररूपी आवृत्तबीजी बीजांड…»
- ch 1 §1.2.2: hi ¶5 = en ¶5 — similarity 0.38 — en «Female gametophyte : In a majority of flowering…» · hi «स्त्री (मादा) युग्मकोद्भिद् — अधिकांश पुष्पी पादपों में…»
- ch 1 §1.2.3: hi ¶1 = en ¶1 — similarity 0.41 — en «In the preceding sections you have learnt that…» · hi «पूर्ववर्ती भाग में आप पढ़ चुके हैं कि…»
- ch 1 §1.2.3: hi ¶10 = en ¶10 — similarity 0.44 — en «Both wind and water pollinated flowers are not…» · hi «वायु एवं जल परागित पुष्प न तो बहुत…»
- ch 1 §1.2.3: hi ¶17 = en ¶17 — similarity 0.40 — en «Pollen-pistil Interaction : Pollination does not guarantee the…» · hi «पराग-स्त्रीकेसर संकर्षण (पारस्परिकक्रिया) — परागण बिल्कुल सही प्रकार…»
- ch 1 §1.4.2: hi ¶1 = en ¶1 — similarity 0.43 — en «Embryo develops at the micropylar end of the…» · hi «भ्रूण भ्रूणकोश या पुटी के बीजांडद्वारी सिरे पर…»
- ch 1 §1.4.2: hi ¶2 = en ¶2 — similarity 0.42 — en «A typical dicotyledonous embryo (Figure 1.14a), consists of…» · hi «एक प्ररूपी द्विबीजपत्री भ्रूण (चित्र 1.14 अ) में…»
- ch 1 §1.5: hi ¶2 = en ¶2 — similarity 0.45 — en «Hybrid varieties of several of our food and…» · hi «बहुत से हमारे खाद्य एवं शाक फसलों की…»
- ch 3 §3.1: hi ¶4 = en ¶4 — similarity 0.39 — en «Research on various reproduction-related areas are encouraged and…» · hi «यौन संबंधित मामलों के बारे में बेहतर जागरूकता,…»
- ch 4 §4.2: hi ¶15 = en ¶16 — similarity 0.39 — en «Using the genotypes of this cross, can you…» · hi «इस संकरण प्रयोग की जीनोटाइपों का उपयोग करके…»
- ch 5 §5.1.1: hi ¶6 = en ¶6 — similarity 0.41 — en «(ii) The two chains have anti-parallel polarity. It…» · hi «(ख) दोनों शृंखलाएँ प्रति समानांतर ध्रुवणता रखती है।…»
- ch 5 §5.1.1: hi ¶9 = en ¶9 — similarity 0.39 — en «(v) The plane of one base pair stacks…» · hi «(ङ) द्विकुंडली में एक क्षार युग्म की सतह…»
- ch 5 §5.1.1: hi ¶11 = en ¶12 — similarity 0.44 — en «In some viruses the flow of information is…» · hi «कुछ विषाणुओं में उपरोक्त बहाव विपरीत दिशा आरएनए…»
- ch 5 §5.1.2: hi ¶5 = en ¶5 — similarity 0.43 — en «Theoretically, how many such beads (nucleosomes) do you…» · hi «सैद्धांतिक रूप से तुम सोच सकते हो कि…»
- ch 5 §5.2: hi ¶5 = en ¶5 — similarity 0.43 — en «He concluded that the R strain bacteria had…» · hi «ग्रीफिथ ने बताया कि आर-प्रभेद जीवाणु ताप मृत…»
- ch 5 §5.2.2: hi ¶2 = en ¶2 — similarity 0.41 — en «Can you recall the two chemical differences between…» · hi «क्या आप डीएनए व आरएनए के बीच दो…»
- ch 5 §5.4: hi ¶3 = en ¶3 — similarity 0.43 — en «The scheme suggested that the two strands would…» · hi «उपरोक्त योजना से स्पष्ट है कि दोनों रज्जुक…»
- ch 5 §5.4.2: hi ¶4 = en ¶4 — similarity 0.42 — en «Further, not every detail of replication is understood…» · hi «प्रतिकृति के बारे में अभी विस्तृत रूप से…»
- ch 5 §5.5.3: hi ¶4 = en ¶4 — similarity 0.28 — en «In eukaryotes, there are two additional complexities –» · hi «सुकेंद्रकी में दो अतिरिक्त जटिलताएँ हैं —»
- ch 5 §5.6: hi ¶4 = en ¶4 — similarity 0.27 — en «The salient features of genetic code are as…» · hi «आनुवंशिक कूट की प्रमुख विशेषताएँ निम्न है —»
- ch 5 §5.6: hi ¶8 = en ¶7 — similarity 0.41 — en «(iii) The codon is read in mRNA in…» · hi «(घ) प्रकूट दूत आरएनए में लगातार पढ़े जाते…»
- ch 5 §5.6: hi ¶14 = en ¶16 — similarity 0.37 — en «Can you now correlate which two properties of…» · hi «क्या आप अब आनुवंशिक कूट की दो विशेषताओं…»
- ch 5 §5.8.1: hi ¶5 = en ¶5 — similarity 0.37 — en «Remember, glucose or galactose cannot act as inducers…» · hi «याद रखो लैक-प्रचालेक किए ग्लूकोज या गैलेक्टोज प्रेरक…»
- ch 8 §8.6: hi ¶2 = en ¶2 — similarity 0.38 — en «Fungi are also known to form symbiotic associations…» · hi «कवक पादपों के साथ सहजीवी संबंध (माइकोराइजा) स्थापित…»
- ch 9 §9.2.2: hi ¶2 = en ¶2 — similarity 0.38 — en «The following are the features that are required…» · hi «संवाहक में क्लोनिंग करने हेतु निम्न विशेषताओं की…»
- ch 9 §9.2.3: hi ¶3 = en ¶3 — similarity 0.41 — en «Now that we have learnt about the tools…» · hi «आप पुनर्योगज डीएनए निर्माण के तरीकों के बारे…»
- ch 9 §9.3: hi ¶1 = en ¶1 — similarity 0.44 — en «Recombinant DNA technology involves several steps in specific…» · hi «पुनर्योगज डीएनए प्रौद्योगिकी (टेक्नोलॉजी) चरण विशिष्ट अनुक्रम में…»
- ch 10 §10.1: hi ¶8 = en ¶12 — similarity 0.44 — en «Specific Bt toxin genes were isolated from Bacillus…» · hi «विशिष्ट बीटी जीव विष जींस बैसीलस थुरीनजिएंसिस से…»
- ch 12 §12.1: hi ¶4 = en ¶4 — similarity 0.44 — en «To understand the ethos of an aquatic ecosystem…» · hi «एक जलीय पारितंत्र के गुण धर्म (प्रकृति) को…»

## numbers one edition of a pair carries and the other does not

- ch 1 §1.1 ¶2: only in en — · only in hi 11
- ch 1 §1.2.1 ¶8: only in en 1.4 · only in hi —
- ch 1 §1.2.2 ¶1: only in en — · only in hi 11
- ch 1 §1.2.2 ¶5: only in en — · only in hi 1.8
- ch 1 §1.2.2 ¶6: only in en — · only in hi 6
- ch 1 §1.4.3 ¶2: only in en 10, 15 · only in hi —
- ch 2 §2 ¶1: only in en — · only in hi 50
- ch 2 §2.3 ¶3: only in en 200, 300 · only in hi 20, 30
- ch 2 §2.3 ¶5: only in en 000, 000 · only in hi 1, 1
- ch 2 §2.5 ¶3: only in en 5 · only in hi 4
- ch 2 §2.6 ¶4: only in en — · only in hi 9
- ch 2 §2.7 ¶1: only in en 9 · only in hi 9.5
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
- ch 4 §4.3.1 ¶4: only in en — · only in hi 2
- ch 4 §4.3.1 ¶5: only in en 1 · only in hi 50, 4, 4, 4
- ch 4 §4.3.3 ¶2: only in en 4.11 · only in hi —
- ch 4 §4.7 ¶2: only in en 5 · only in hi —
- ch 4 §4.7 ¶3: only in en 5 · only in hi —
- ch 4 §4.8.1 ¶2: only in en 4.13 · only in hi 4.14
- ch 5 §5 ¶1: only in en — · only in hi 11
- ch 5 §5 ¶2: only in en — · only in hi 11
- ch 5 §5.1.1 ¶8: only in en — · only in hi 10
- ch 5 §5.1.1 ¶9: only in en 5.3 · only in hi —
- ch 5 §5.4.1 ¶2: only in en — · only in hi 15, 14
- ch 5 §5.4.2 ¶2: only in en — · only in hi 5, 3
- ch 5 §5.5.3 ¶5: only in en — · only in hi 5
- ch 5 §5.6 ¶2: only in en 4, 20, 20 · only in hi —
- ch 5 §5.6 ¶5: only in en 3 · only in hi —
- ch 5 §5.6.1 ¶1: only in en 4 · only in hi 5
- ch 5 §5.6.2 ¶2: only in en 5.12 · only in hi —
- ch 5 §5.9 ¶7: only in en — · only in hi 3
- ch 5 §5.9.1 ¶3: only in en 1, 40 · only in hi 140
- ch 5 §5.10 ¶5: only in en — · only in hi 5.16
- ch 6 §6.1 ¶2: only in en — · only in hi 1380, 450, 2, 2, 50, 400
- ch 6 §6.1 ¶4: only in en 4, 4, 3, 3, 2 · only in hi —
- ch 6 §6.1 ¶5: only in en 2000 · only in hi 3, 300, 200
- ch 6 §6.2 ¶1: only in en — · only in hi 19
- ch 6 §6.3 ¶7: only in en 6.4 · only in hi —
- ch 6 §6.4 ¶1: only in en 6.7 · only in hi —
- ch 7 §7.1 ¶2: only in en — · only in hi 0, 0
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
- ch 10 §10.3 ¶4: only in en 1997 · only in hi 1977
- ch 10 §10.4 ¶6: only in en 200, 000, 1997 · only in hi 2, 1977
- ch 11 §11.1.2 ¶12: only in en 0, 0 · only in hi —
- ch 11 §11.1.2 ¶14: only in en 1, 3 · only in hi 8
- ch 11 §11.1.4 ¶8: only in en — · only in hi 10
- ch 12 §12.1 ¶1: only in en — · only in hi 13
- ch 13 §13.1 ¶2: only in en 000, 000, 1 · only in hi 1000
- ch 13 §13.1.1 ¶1: only in en 50 · only in hi 25, 70
- ch 13 §13.1.1 ¶4: only in en 45, 000 · only in hi 45000
- ch 13 §13.1.3 ¶2: only in en 000, 000 · only in hi —
- ch 13 §13.1.4 ¶1: only in en 000 · only in hi 20
- ch 13 §13.1.4 ¶2: only in en 1, 000 · only in hi 5, 1000
- ch 13 §13.1.4 ¶5: only in en 1000 · only in hi —
- ch 13 §13.2.2 ¶2: only in en 2 · only in hi 9, 13.2, 3

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.2.2 ¶6 — en p11, hi p10, similarity 0.46 — the founder reads this one
- en: Let us study about the formation of the embryo sac in detail. (Figure 1.8b). The nucleus of the functional megaspore divides mitotically to form two nuclei which move to the opposite poles, forming the 2-nucleate embryo sac. Two more sequential mitotic nuclear divisions result in the formation of the 4-nucleate and later the 8-nucleate stages of the embryo sac. It is of interest to note that these mitotic divisions are strictly free nuclear, that is, nuclear divisions are not followed immediately by cell wall formation. After the 8-nucleate stage, cell walls are laid down leading to the organisation of the typical female gametophyte or embryo sac. Observe the distribution of cells inside the embryo sac (Figure 1.8b, c). Six of the eight nuclei are surrounded by cell walls and organised into cells; the remaining two nuclei, called polar nuclei are situated below the egg apparatus in the large central cell.
- hi: आइए! भ्रूणकोष के निर्माण का थोड़ा और विस्तार से अध्ययन करें (चित्र 1.8 ब) क्रियाशील गुरूबीजाणु के केंद्रक समसूत्री विभाजन के द्वारा दो केंद्रकी (न्युक्लीआई) बनाते हैं, जो विपरीत ध्रुवों को चले जाते हैं और 2-न्युक्लियेट (केंद्रीय) भ्रूणकोष की रचना करते हैं। दो अन्य क्रमिक समसूत्री केंद्रकीय विभाजन के परिणामस्वरूप 4-केंद्रीय (न्युक्लियेट) और तत्पश्चात् 8-केंद्रीय (न्युक्लियेट) भ्रूणकोष की संरचना करते हैं। यहाँ पर यह ध्यान देना महत्त्वपूर्ण है कि ये सूत्री विभाजन सही अर्थो में मुक्त केंद्रक (न्युकिलयर) है, जो केंद्रकीय विभाजन से तुरंत ही कोशिका भित्ति रचना द्वारा नहीं अनुपालित किया जाता है। 8-न्युक्लियेट चरण के पश्चात् कोशिका भित्ति की नींव पड़ती है, जो विशिष्ट (प्ररूपी) मादा युग्मकोद्भिद् या भ्रूणकोष के संगठन का रूप लेती है। भ्रूणकोष के भीतर कोशिकाओं के वितरण का अवलोकन कीजिए (चित्र 1.8 ब, स) आठ में से 6-न्युक्लीआई भित्ति कोशिकाओं से घिरी होती हैं और कोशिकाओं में संयोजित रहते हैं। शेष बचे दो न्युक्लीआई ध्रुवीय न्यूक्लीआई कहलाते हैं, जो अंडउपकरण के नीचे बड़े केंद्रीय कोशिका में स्थित होते हैं।

### 2. ch 1 §1.2.3 ¶1 — en p11, hi p12, similarity 0.41
- en: In the preceding sections you have learnt that the male and female gametes in flowering plants are produced in the pollen grain and embryo sac, respectively. As both types of gametes are non-motile, they have to be brought together for fertilisation to occur. How is this achieved?
- hi: पूर्ववर्ती भाग में आप पढ़ चुके हैं कि पुष्पीय पादपों में नर एवं मादा युग्मक क्रमशः परागकण एवं भ्रूणकोश में पैदा होते हैं। चूँकि दोनों ही प्रकार के युग्मक अचल हैं अतः निषेचन संपन्न होने के लिए दोनों को ही एक साथ लाना होता है। यह कैसे सम्पन्न हो?

### 3. ch 1 §1.2.3 ¶6 — en p12, hi p13, similarity 0.51
- en: (iii) Xenogamy – Transfer of pollen grains from anther to the stigma of a different plant (Figure 1.9b). This is the only type of pollination which during pollination brings genetically different types of pollen grains to the stigma.
- hi: (ग) परनिषेचन — इसमें भिन्न पादपों के पराग कोश से भिन्न पादपों के वर्तिकाग्र तक परागकणों का स्थानांतरण होता है (चित्र 1.9 ब)। मात्र यह ही केवल एक प्रकार का परागण है, जिसमें परागण के समय आनुवंशिकतः भिन्न प्रकार के परागकणों का आगमन होता है।

### 4. ch 1 §1.2.3 ¶7 — en p12, hi p13, similarity 0.53
- en: Agents of Pollination : Plants use two abiotic (wind and water) and one biotic (animals) agents to achieve pollination. Majority of plants use biotic agents for pollination. Only a small proportion of plants use abiotic agents. Pollen grains coming in contact with the stigma is a chance factor in both wind and water pollination. To compensate for this uncertainties and associated loss of pollen grains, the flowers produce enormous amount of pollen when compared to the number of ovules available for pollination.
- hi: परागण के अभिकर्मक या कारक — पौधे परागण के कारक या अभिकर्मक के रूप में दो अजीवीय (वायु एवं जल) तथा एक जीवीय (प्राणि) कारक (एजेंट) का उपयोग कर लक्ष्य प्राप्त करते हैं। अधिकतर पौधे परागण के लिए जीवीय कारकों का उपयोग करते हैं। बहुत कम पौधे अजीवीय कारकों का उपयोग करते हैं। वायु तथा जल दोनों ही कारकों में परागकण का वर्तिकाग्र के संपर्क में आना महज संयोगात्मक घटना है। इस प्रकार की अनिश्चितता तथा परागकणों के ह्रास से जुड़े 'तथ्यों' की क्षतिपूर्ति हेतु पौधे, बीजांडों की तुलना में, असंख्य मात्रा में परागकण उत्पन्न करते हैं।

### 5. ch 1 §1.2.3 ¶17 — en p15–16, hi p16, similarity 0.40
- en: Pollen-pistil Interaction : Pollination does not guarantee the transfer of the right type of pollen (compatible pollen of the same species as the stigma). Often, pollen of the wrong type, either from other species or from the same plant (if it is self-incompatible), also land on the stigma. The pistil has the ability to recognise the pollen, whether it is of the right type (compatible) or of the wrong type (incompatible). If it is of the right type, the pistil accepts the pollen and promotes post-pollination events that leads to fertilisation. If the pollen is of the wrong type, the pistil rejects the pollen by preventing pollen germination on the stigma or the pollen tube growth in the style. The ability of the pistil to recognise the pollen followed by its acceptance or rejection is the result of a continuous dialogue between pollen grain and the pistil. This dialogue is mediated by chemical components of the pollen interacting with those of the pistil. It is only in recent years that botanists have been able to identify some of the pollen and pistil components and the interactions leading to the recognition, followed by acceptance or rejection.
- hi: पराग-स्त्रीकेसर संकर्षण (पारस्परिकक्रिया) — परागण बिल्कुल सही प्रकार के परागकणों का स्थानांतरण सुनिश्चित नहीं कराता है (ठीक उसी प्रजाति का सुयोग्य पराग वर्तिकाग्र तक पहुँचे)। प्रायः गलत प्रकार के पराग भी उसी वर्तिकाग्र पर आ पड़ते हैं (जिसमें ये, या तो उसी पादप से होते हैं या फिर अन्य पादप से; (यदि वह स्व परागण के अयोग्य है)। स्त्रीकेसर में यह सक्षमता होती है कि वह पराग को पहचान सके कि वह उसी वर्ग के सही प्रकार का पराग (सुयोग्य) है या फिर गलत प्रकार का (अयोग्य) है। यदि पराग सही प्रकार का होता है तो स्त्रीकेसर उसे स्वीकार कर लेता है तथा परागण-पश्च घटना के लिए प्रोत्साहित करता है जो कि निषेचन की ओर बढ़ता है। यदि पराग गलत प्रकार का होता है तो स्त्रीकेसर वर्तिकाग्र पर पराग अंकुरण या वर्तिका में पराग नलिका वृद्धि रोककर पराग को अस्वीकार कर देता है। एक स्त्रीकेसर द्वारा पराग के पहचानने की सक्षमता उसकी स्वीकृति या अस्वीकृति द्वारा अनुपालित होती है, जो परागकणों एवं स्त्रीकेसर के बीच निरंतर संवाद का परिणाम है। इस संवाद की मध्यस्थता स्त्रीकेसर तथा पराग के रासायनिक घटकों के संकर्षण द्वारा होता है। अभी हाल ही में कुछ वनस्पति विज्ञानियों ने स्त्रीकेसर एवं पराग के घटकों को पहचाना है तथा उनके बीच संकर्षण (परस्परक्रिया) को स्वीकृति या अस्वीकृति के अनुपालन से जाना है।

### 6. ch 1 §1.3 ¶1 — en p18, hi p18–19, similarity 0.54
- en: After entering one of the synergids, the pollen tube releases the two male gametes into the cytoplasm of the synergid. One of the male gametes moves towards the egg cell and fuses with its nucleus thus completing the syngamy. This results in the formation of a diploid cell, the zygote. The other male gamete moves towards the two polar nuclei located in the central cell and fuses with them to produce a triploid primary endosperm nucleus (PEN) (Figure 1.13a). As this involves the fusion of three haploid nuclei it is termed triple fusion. Since two types of fusions, syngamy and triple fusion take place in an embryo sac the phenomenon is termed double fertilisation, an event unique to flowering plants. The central cell after triple fusion becomes the primary endosperm cell (PEC) and develops into the endosperm while the zygote develops into an embryo.
- hi: एक सहायकोशिका में प्रवेश करने के पश्चात् पराग नलिका द्वारा सहायकोशिका के जीव द्रव्य में दो नर युग्मक अवमुक्त किए जाते हैं। इनमें से एक नर युग्मक अंड कोशिका की ओर गति करता है और केंद्रक के साथ संगलित होता है, जिससे युग्मक संलयन पूर्ण होता है। जिसके परिणाम में एक द्विगुणित कोशिका युग्मनज (जाइगोट) की रचना होती है। दूसरा नर युग्मक केंद्रीय कोशिका में स्थित दो ध्रुवीय न्युक्ली (केंद्रिकी) की ओर गति करता है और उनसे संगलित होकर त्रिगुणित (प्राइमरी इंडोस्पर्म न्युकिलयस (प्राथमिक भ्रूणपोष केंद्रक) बनाता है। जैसा कि इसके अन्तर्गत तीन अगुणितक न्युक्ली (केंद्रिकी) सम्मिलित होते हैं। अतः इसे त्रिसंलयन कहते हैं (चित्र 1.13 अ)। चूँकि एक भ्रूण पुटी (भ्रूणकोश) में दो प्रकार के संलयन (संगलन), युग्मकसंलयन तथा त्रिसंलयन स्थान लेते हैं अतः इस परिघटना को दोहरा निषेचन कहा जाता है। जो कि पुष्पी पादपों के लिए एक अनूठी घटना है। त्रिसंलयन के पश्चात् केंद्रीय कोशिका प्राथमिक भ्रूणपोष कोशिका बन जाती है तथा भ्रूणपोष के रूप में विकसित होने लगती है जबकि युग्मनज एक भ्रूण के रूप में विकसित होता है।

### 7. ch 1 §1.4.2 ¶1 — en p19, hi p20, similarity 0.43 — the founder reads this one
- en: Embryo develops at the micropylar end of the embryo sac where the zygote is situated. Most zygotes divide only after certain amount of endosperm is formed. This is an adaptation to provide assured nutrition to the developing embryo. Though the seeds differ greatly, the early stages of embryo development (embryogeny) are similar in both monocotyledons and dicotyledons. Figure 1.13 depicts the stages of embryogeny in a dicotyledonous embryo. The zygote gives rise to the proembryo and subsequently to the globular, heart-shaped and mature embryo.
- hi: भ्रूण भ्रूणकोश या पुटी के बीजांडद्वारी सिरे पर विकसित होता है; जहाँ पर युग्मनज स्थित होता है। अधिकतर युग्मनज तब विभाजित होते हैं जब कुछ निश्चित सीमा तक भ्रूणपोष विकसित हो जाता है। यह एक प्रकार का अनुकूलन है ताकि विकासशील भ्रूण को सुनिश्चित पोषण प्राप्त हो सके। यद्यपि बीज में व्यापक भिन्नता होती है। भ्रूण विकास (भ्रूणोद्भव) की प्रारंभिक अवस्था (चरण) एक बीजपत्री तथा द्विबीजपत्री दोनों ही में समान होती है (चित्र 1.13)। युग्मनज प्राक्भ्रूण के रूप में वृद्धि करता है और इसके सापेक्ष ही गोलाकार, हृदयाकार तथा परिपक्व भ्रूण में वृद्धि करता है।

### 8. ch 2 §2.5 ¶3 — en p11, hi p12, similarity 0.61
- en: One has to remember that the sex of the baby has been decided at this stage itself. Let us see how? As you know the chromosome pattern in the human female is XX and that in the male is XY. Therefore, all the haploid gametes (ova) produced by the female have the sex chromosome X whereas in the male gametes (sperms) the sex chromosome could be either X or Y, hence, 50 per cent of sperms carry the X chromosome while the other 50 per cent carry the Y. After fusion of the male and female gametes the zygote would carry either XX or XY depending on whether the sperm carrying X or Y fertilised the ovum. The zygote carrying XX would develop into a female baby and XY would form a male (you will learn more about the chromosomal patterns in Chapter 5). That is why, scientifically it is correct to say that the sex of the baby is determined by the father and not by the mother!
- hi: हमें यह बात ध्यान में रखनी चाहिए कि इसी चरण में शिशु के लिंग का निर्धारण यानी लड़का या लड़की का होना निश्चित हो जाता है। आइए! देखें यह कैसे होता है? जैसा कि आप जानते हैं कि स्त्री में गुणसूत्र का स्वरूप XX है तथा पुरुष में XY होता है। इसलिए स्त्री द्वारा उत्पादित सभी अगुणित युग्मकों (अंडाणु) में X लिंग गुणसूत्र होते हैं जबकि पुरुष युग्मकों (शुक्राणुओं) में लिंग गुणसूत्र या तो X या Y लिंग गुणसूत्र होते हैं। इसलिए 50 प्रतिशत शुक्राणु में X लिंग गुणसूत्र होते हैं और दूसरे 50 प्रतिशत शुक्राणु में Y लिंग गुणसूत्र होते हैं। इसलिए पुरूष एवं स्त्री युग्मकों के संलयन के पश्चात् युग्मनज में या तो XX या XY लिंग गुणसूत्र की संभावना होगी। यह इस बात पर निर्भर करेगा कि X या Y लिंग गुणसूत्र वाले शुक्राणुओं में से कौन अंडाणु का निषेचन करता है। जिस युग्मनज में XX गुणसूत्र होंगे वह एक मादा शिशु (लड़की) के रूप में जबकि XY गुणसूत्र वाला युग्मनज नर शिशु (लड़का) के रूप में विकसित होगा। आप गुणसूत्र प्रतिमानों के बारे में और अधिक विस्तार से जानकारी अध्याय 4 में सीख सकेंगे। इसी कारण कहा जाता है कि वैज्ञानिक रूप से यह कहना सत्य है कि एक शिशु के लिंग का निर्धारण उसके पिता द्वारा होता है न कि माता के द्वारा।

### 9. ch 3 §3.2 ¶4 — en p4, hi p4, similarity 0.59
- en: Natural methods work on the principle of avoiding chances of ovum and sperms meeting. Periodic abstinence is one such method in which the couples avoid or abstain from coitus from day 10 to 17 of the menstrual cycle when ovulation could be expected. As chances of fertilisation are very high during this period, it is called the fertile period. Therefore, by abstaining from coitus during this period, conception could be prevented. Withdrawal or coitus interruptus is another method in which the male partner withdraws his penis from the vagina just before ejaculation so as to avoid insemination. Lactational amenorrhea (absence of menstruation) method is based on the fact that ovulation and therefore the cycle do not occur during the period of intense lactation following parturition. Therefore, as long as the mother breast-feeds the child fully, chances of conception are almost nil. However, this method has been reported to be effective only upto a maximum period of six months following parturition. As no medicines or devices are used in these methods, side effects are almost nil. Chances of failure, though, of this method are also high.
- hi: प्राकृतिक विधियाँ — ये विधियाँ अंडाणु (ओवम) एवं शुक्राणु के संगम को रोकने के सिद्धांत पर कार्य करती हैं। इनमें से एक उपाय आवधिक संयम है जिसमें एक दंपति माहवारी चक्र के 10वें से 17वें दिन के बीच की अवधि, के दौरान मैथुन से बचते हैं जिसे अंडोत्सर्जन की अपेक्षित अवधि मानते हैं। इस अवधि के दौरान निषेचन एवं उर्वर (गर्भधारण) के अवसर बहुत अधिक होने के कारण इसे निषेच्य अवधि भी कहा जाता है। इस तरह से, इस दौरान मैथुन (सहवास) न करने पर गर्भाधान से बचा जा सकता है। बाह्य स्खलन या अंतरित मैथुन (कोइटस इन्ट्रप्सन) एक अन्य विधि है जिसमें पुरुष साथी संभोग के दौरान वीर्य स्खलन से ठीक पहले स्त्री की योनि से अपना लिंग बाहर निकाल कर वीर्यसेचन से बच सकता है। स्तनपान अनार्तव (लैक्टेशनल एमेनोरिया) विधि भी इस तथ्य पर निर्भर करती है कि प्रसव के बाद, स्त्री द्वारा शिशु को भरपूर स्तनपान कराने के दौरान अंडोत्सर्ग और आर्तव चक्र शुरू नहीं होता है। इसलिए जितने दिनों तक माता शिशु को पूर्णतः स्तनपान कराना जारी रखती है (इस दौरान शिशु को माँ के दूध के अलावा, ऊपर से पानी या अतिरिक्त दूध भी नहीं दिया जाना चाहिए। यह अवधि 4-6 माह की होती है), गर्भधारण के अवसर लगभग शून्य होते हैं। यह विधि प्रसव के बाद ज्यादा से ज्यादा 6 माह की अवधि तक ही कारगर मानी गई है। चूँकि उपर्युक्त विधियों में किसी दवा या साधन का उपयोग नहीं होता, अतः इसके दुष्प्रभाव लगभग शून्य के बराबर हैं। हालाँकि, इसके असफल रहने की दर काफी अधिक है।

### 10. ch 3 §3.2 ¶9 — en p5–6, hi p6, similarity 0.60 — the founder reads this one
- en: Surgical methods, also called sterilisation, are generally advised for the male/female partner as a terminal method to prevent any more pregnancies. Surgical intervention blocks gamete transport and thereby prevent conception. Sterilisation procedure in the male is called 'vasectomy' and that in the female, 'tubectomy'. In vasectomy, a small part of the vas deferens is removed or tied up through a small incision on the scrotum (Figure 3.4a) whereas in tubectomy, a small part of the fallopian tube is removed (Figure 3.4b) or tied up through a small incision in the abdomen or through vagina. These techniques are highly effective but their reversibility is very poor.
- hi: शल्यक्रिया विधियाँ जिन्हें बंध्यकरण भी कहते हैं; प्रायः उन लोगों के लिए सुझाई जाती हैं, जिन्हें आगे गर्भावस्था नहीं चाहिए तथा वे इसे स्थाई माध्यम के रूप में (पुरुष/स्त्री में से एक) अपनाना चाहते हैं। शल्यक्रिया की दखलंदाजी से युग्मक परिवहन (संचार) रोक दिया जाता है; फलतः गर्भाधान नहीं होता है। बंध्यकरण प्रक्रिया को पुरुषों के लिए 'शुक्रवाहक-उच्छेदन (वासैक्टोमी)', तथा महिलाओं के लिए डिंबवाहिनी (फैलोपी) 'नलिका उच्छेदन (टूबैक्टोमी)' कहा जाता है। जनसाधारण इन क्रियाओं को 'पुरुष या महिला नसबंदी' के नाम से जानते हैं, शुक्रवाहक-उच्छेदन में अंडकोष (इस्क्रोटम) शुक्रवाहक में चीरा मारकर छोटा सा भाग काटकर निकाल अथवा बांध दिया जाता है (3.4 अ) जबकि स्त्री के उदर में छोटा सा चीरा मारकर अथवा योनि द्वारा डिंबवाहिनी नली का छोटा सा भाग निकाल या बाँध दिया जाता है। यह तकनीकें बहुत ही प्रभावशाली होती हैं पर इनमें पूर्वस्थित लाने की गुंजाइश बहुत ही कम होती है।

### 11. ch 3 §3.5 ¶3 — en p8, hi p9–10, similarity 0.57
- en: Transfer of an ovum collected from a donor into the fallopian tube (GIFT – gamete intra fallopian transfer) of another female who cannot produce one, but can provide suitable environment for fertilisation and further development is another method attempted. Intra cytoplasmic sperm injection (ICSI) is another specialised procedure to form an embryo in the laboratory in which a sperm is directly injected into the ovum. Infertility cases either due to inability of the male partner to inseminate the female or due to very low sperm counts in the ejaculates, could be corrected by artificial insemination (AI) technique. In this technique, the semen collected either from the husband or a healthy donor is artificially introduced either into the vagina or into the uterus (IUI – intra-uterine insemination) of the female.
- hi: ऐसे मामले में जहाँ स्त्रियाँ अंडाणु उत्पन्न नहीं कर सकतीं; लेकिन जो निषेचन और भ्रूण के परिवर्धन के लिए उपयुक्त वातावरण प्रदान कर सकती हैं, उनके लिए एक अन्य तरीका अपनाया जा सकता है। इसमें दाता से अंडाणु लेकर उन स्त्रियों की फैलोपी नलिका में स्थानांतरित (जी आई एफ टी) कर दिया जाता है। प्रयोगशाला में भ्रूण बनाने के लिए अंतः कोशिकीय शुक्राणु निक्षेपण (आई सी एस आई) दूसरी विशिष्ट प्रक्रिया है जिसमें शुक्राणु को सीधे ही अंडाणु में अंतःक्षेपित किया जाता है। बंध्यता के ऐसे मामलों में जिनमें पुरुष साथी स्त्री को वीर्यसेचित कर सकने के योग्य नहीं है अथवा जिसके स्खलित वीर्य में शुक्राणुओं की संख्या बहुत ही कम है, ऐसे दोष का निवारण कृत्रिम वीर्यसेचन (ए आई) तकनीक से किया जा सकता है। इस तकनीक में पति या स्वस्थ दाता से शुक्र लेकर कृत्रिम रूप से या तो स्त्री की योनि में अथवा उसके गर्भाशय में प्रविष्ट किया जाता है। इसे अंतः गर्भाशय वीर्यसेचन (आई यू आई) कहते हैं।

### 12. ch 4 §4.2 ¶10 — en p8, hi p8, similarity 0.63
- en: From the Punnett square it is easily seen that 1/4th of the random fertilisations lead to TT, 1/2 lead to Tt and 1/4th to tt. Though the F_1 have a genotype of Tt, but the phenotypic character seen is 'tall'. At F_2, 3/4th of the plants are tall, where some of them are TT while others are Tt. Externally it is not possible to distinguish between the plants with the genotypes TT and Tt. Hence, within the genopytic pair Tt only one character 'T' tall is expressed. Hence the character T or 'tall' is said to dominate over the other allele t or 'dwarf' character. It is thus due to this dominance of one character over the other that all the F_1 are tall (though the genotype is Tt) and in the F_2 3/4th of the plants are tall (though genotypically 1/2 are Tt and only 1/4th are TT). This leads to a phenotypic ratio of 3/4th tall : (1/4 TT + 1/2 Tt) and 1/4th tt, i.e., a 3:1 ratio, but a genotypic ratio of 1:2:1.
- hi: पनेट वर्ग से आसानी से देखा जा सकता है कि यादृच्छिक निषेचन के परिणामस्वरूप 1/4 TT, 1/2 Tt और 1/4 tt जीनोटाइप पैदा करते हैं। साथ ही केवल 'लंबा' फीनोटाइप दृष्टिगोचर होता है । F_2 अवस्था में 3/4 पौधे लंबे होते हैं जिनमें से कुछ TT और कुछ Tt होते हैं। बाहर से देखने पर TT और Tt पौधों में भेद नहीं किया जा सकता। अतः जीनोटाइप जोड़े Tt से केवल एक ही लक्षण (T) 'लंबे' की अभिव्यक्ति होती है। इसीलिए कहा जाता है कि लक्षण 'लंबा', बौने लक्षण अर्थात् अलील t के ऊपर प्रभावी हो जाता है। इसी लक्षण प्रभाविता का यह असर होता है कि F_1 के सारे के सारे पौधे लंबे होते हैं (भले ही जीनोटाइप Tt हो) और F_2 पीढ़ी में 3/4 पौधे लंबे होते हैं (भले ही जीनोटाइप के लिहाज से 1/2 Tt और 1/4 TT हों) अंत में जीनोटाइप अनुपात 3/4 लंबे (1/4TT, 1/2Tt) और 1/4 ठिगने (tt) दूसरे शब्दों में 3:1 का अनुपात पर जीनोटाइप अनुपात होता है 1:2 :1।

### 13. ch 4 §4.2.2.2 ¶2 — en p12, hi p12, similarity 0.60
- en: Do you realise that the example of ABO blood grouping also provides a good example of multiple alleles? Here you can see that there are more than two, i.e., three alleles, governing the same character. Since in an individual only two alleles can be present, multiple alleles can be found only when population studies are made.
- hi: आप यह भी समझ गए होंगे कि ABO रुधिर समूहन बहुअलील (मल्टीपल अलील) का भी अच्छा उदाहरण प्रस्तुत करता है। आप देख सकते हैं कि यहाँ पर दो से अधिक अर्थात् तीन अलील एक ही लक्षण को नियंत्रित करते हैं। क्योंकि एक व्यक्ति में दो ही अलील विद्यमान रह सकते हैं, अतः बहुअलील की क्रियाशीलता समष्टि (जीन संख्या) के अध्ययन में ही दिख सकती है।

### 14. ch 4 §4.3.2 ¶3 — en p16, hi p16, similarity 0.60
- en: During Anaphase of meiosis I, the two chromosome pairs can align at the metaphase plate independently of each other (Figure 4.9). To understand this, compare the chromosomes of four different colour in the left and right columns. In the left column (Possibility I) orange and green is segregating together. But in the right hand column (Possibility II) the orange chromosome is segregating with the red chromosomes.
- hi: अर्धसूत्रण I की पश्चावस्था में क्रोमोसोम के दो जोड़े मेटाफेस पट्टिका पर एक दूसरे से स्वतंत्र रूप से पंक्तिबद्ध हो सकते हैं (चित्र 4.9)। इसे समझने के लिए दाएँ और बाएँ स्तंभों के चार अलग रंगों के क्रोमोसोम की तुलना करो। बाएँ स्तंभ में (संभावना I) नारंगी और हरे एक साथ विसंयोजित हैं किंतु दाहिने स्तंभ (संभावना II) में नारंगी क्रामोसोम लाल क्रोमोसोमों के साथ विसंयोजित हो रहा है।

### 15. ch 4 §4.6 ¶2 — en p20, hi p21, similarity 0.68
- en: These observations led to the investigation of a number of species to understand the mechanism of sex determination. In a number of other insects and mammals including man, XY type of sex determination is seen where both male and female have same number of chromosomes. Among the males an X-chromosome is present but its counter part is distinctly smaller and called the Y-chromosome. Females, however, have a pair of X-chromosomes. Both males and females bear same number of autosomes. Hence, the males have autosomes plus XY, while female have autosomes plus XX. In human beings and in Drosophila the males have one X and one Y chromosome, whereas females have a pair of X-chromosomes besides autosomes (Figure 4.12 a, b).
- hi: इन प्रेक्षणों की प्रेरणा से लिंग-निर्धारण की क्रियाविधि को समझने के लिए अन्य जातियों में भी अन्वेषण प्रेरित किए गए। कई अन्य कीटों तथा मानव समेत स्तनधारियों में XY प्रकार का लिंग निर्धारण देखा जाता है जहाँ नर और मादा दोनों में क्रोमोसोम संख्या समान होती है। नर में एक क्रोमोसोम तो X होता है पर उसका जोड़ीदार स्पष्टतः छोटा होता है और Y क्रोमोसोम कहलाता है। अलिंग सूत्रों की संख्या नर और मादा में बराबर होती है। दूसरे शब्दों में नर में अलिंग सूत्र के साथ XY और मादा में अलिंग सूत्र के साथ XX उदाहरणार्थ मानव तथा ड्रोसोफिला में नर में अलिंग क्रोमोसोम के अलावा एक X और एक Y क्रोमोसोम होता है जबकि मादा में अलिंग क्रोमोसोमों के अलावा एक जोड़ा X क्रोमोसोम का (चित्र 4.12 अ तथा ब)।

### 16. ch 5 §5.2 ¶2 — en p6, hi p6, similarity 0.63 — the founder reads this one
- en: In 1928, Frederick Griffith, in a series of experiments with Streptococcus pneumoniae (bacterium responsible for pneumonia), witnessed a miraculous transformation in the bacteria. During the course of his experiment, a living organism (bacteria) had changed in physical form.
- hi: रूपांतरीय सिद्धांत - वर्ष 1928 में फ्रेडेरिक ग्रिफीथ ने स्ट्रेप्टोकोकस नीमोनी (जीवाणु जो निमोनिया के लिए जिम्मेदार है) के साथ कई प्रयोगों से रूपांतरण की अच्छे ढंग से व्याख्या थी। उनके प्रयोगों के दौरान एक सजीव जीव (जीवाणु) के प्राकृतिक रूप में परिवर्तन हो गया।

### 17. ch 5 §5.4.2 ¶1 — en p12, hi p12–13, similarity 0.59
- en: In living cells, such as E. coli, the process of replication requires a set of catalysts (enzymes). The main enzyme is referred to as DNA-dependent DNA polymerase, since it uses a DNA template to catalyse the polymerisation of deoxynucleotides. These enzymes are highly efficient enzymes as they have to catalyse polymerisation of a large number of nucleotides in a very short time. E. coli that has only 4.6 × 10^6 bp (compare it with human whose diploid content is 6.6 × 10^9 bp), completes the process of replication within 18 minutes; that means the average rate of polymerisation has to be approximately 2000 bp per second. Not only do these polymerases have to be fast, but they also have to catalyse the reaction with high degree of accuracy. Any mistake during replication would result into mutations. Furthermore, energetically replication is a very expensive process. Deoxyribonucleoside triphosphates serve dual purposes. In addition to acting as substrates, they provide energy for polymerisation reaction (the two terminal phosphates in a deoxynucleoside triphosphates are high-energy phosphates, same as in case of ATP).
- hi: सजीव कोशिकाओं जैसे ई.कोलाई में प्रतिकृति हेतु उत्प्रेरकों (एंजाइम) के समूहों की आवश्यकता होती है। मुख्य एंजाइम जो डीएनए पर निर्भर है, वह डीएनए पॉलीमरेज है। यह डीएनए टेम्प्लेट का उपयोग डीऑक्सीन्युक्लियोराइड के बहुलकन को उत्प्रेरित करता है। यह एंजाइम काफी प्रभावी है, क्योंकि बहुत ही कम समय में अधिक संख्या में न्युक्लियाटाइडस के बहुलकन को उत्प्रेरित करता है। कल्पना करो कि ई. कोलाई में 4.6×10^6 क्षारयुग्मक मिलते हैं (इसकी तुलना मानव के द्विगुणित संख्या 6.6×10^9 क्षारयुग्मक से करो) जिनमें प्रतिकृति प्रक्रिया के पूर्ण होने में 18 मिनट लगते हैं। इसका मतलब बहुलीकरण की औसत दर लगभग 2000 क्षारयुग्मक प्रति सेकंड है। ये केवल बहुलीकरण को तेज नहीं करते, बल्कि अधिक शुद्धता के साथ अभिक्रिया को उत्प्रेरित भी करते हैं। प्रतिकृति में किसी भी तरह की गलती के परिणामस्वरूप उत्परिवर्तन होता है। इसके अतिरिक्त ऊर्जा की दृष्टि से प्रतिकृति एक महँगी प्रक्रिया है। अतः डीऑक्सीराइबोन्यूक्लियोसाइड ट्राइफॉस्फेट दोहरे उद्देश्य की पूर्ति करता है। इसके अतिरिक्त अवस्तर की तरह कार्य करते हुए बहुलीकरण अभिक्रिया के लिए ऊर्जा प्रदान करता है। (ATP की तरह डीऑक्सीन्यूक्लीओसाइड ट्राइफॉस्फेट में दो टरमिनल फॉस्फेट उच्च ऊर्जा वाले फॉस्फेट हैं)।

### 18. ch 5 §5.5.1 ¶2 — en p14, hi p14, similarity 0.49
- en: There is a convention in defining the two strands of the DNA in the structural gene of a transcription unit. Since the two strands have opposite polarity and the DNA-dependent RNA polymerase also catalyse the polymerisation in only one direction, that is, 5'→3', the strand that has the polarity 3'→5' acts as a template, and is also referred to as template strand. The other strand which has the polarity (5'→3') and the sequence same as RNA (except thymine at the place of uracil), is displaced during transcription. Strangely, this strand (which does not code for anything) is referred to as coding strand. All the reference point while defining a transcription unit is made with coding strand. To explain the point, a hypothetical sequence from a transcription unit is represented below: 3'-ATGCATGCATGCATGCATGCATGC-5' Template Strand 5'-TACGTACGTACGTACGTACGTACG-3' Coding Strand
- hi: अनुलेखन इकाई के संरचनात्मक जीन डीएनए के द्विरज्जुक का ही भाग है। चूँकि रज्जुक विपरीत ध्रुवत्व की ओर होते हैं इसलिए डीएनए - निर्भर आरएनए पॉलीमरेज बहुलकन केवल एकदिशा 5' से 3' (5' -> 3') की ओर उत्प्रेरित होते हैं। रज्जुक जिसमें ध्रुवत्व 3' से 5' (3' -> 5') की ओर है। वह टेम्पलेट के रूप में कार्य करते हैं इसलिए यह टेम्पलेट रज्जुक कहलाता है। दूसरी लड़ी जिसमें ध्रुवत्व (5' -> 3') व अनुक्रम आरएनए जैसा होता है (थाइमीन के अलावा इस जगह पर यूरेसिल होता है)। अनुलेखन के दौरान स्थानांतरित हो जाता है। यह रज्जुक (जो किसी भी चीज़ के लिए कूटलेखन नहीं करता है) कूटलेखन रज्जुक कहलाता है। सभी उपर्युक्त बिंदु जो अनुलेखन इकाई के भाग है कूटलेखन रज्जुक से बने होते हैं। उन बिंदुओं की व्याख्या के लिए अनुलेखन ईकाई के परिकल्पित अनुक्रमों को नीचे दिखाया गया है।

### 19. ch 5 §5.6 ¶5 — en p18, hi p19, similarity 0.47
- en: (i) The codon is triplet. 61 codons code for amino acids and 3 codons do not code for any amino acids, hence they function as stop codons.
- hi: (क) प्रकूट त्रिक होता है। 61 प्रकूट अमीनो अम्ल का कूट लेखन करते हैं व तीन प्रकूट का कूट लेखन नहीं करते हैं इस कारण से यह रोध प्रकूट के रूप में कार्य करता है।

### 20. ch 5 §5.6 ¶8 — en p18, hi p19, similarity 0.59
- en: (iv) The code is nearly universal: for example, from bacteria to human UUU would code for Phenylalanine (phe). Some exceptions to this rule have been found in mitochondrial codons, and in some protozoans.
- hi: (ङ) कूट लगभग सार्वभौमिक होते हैं, उदाहरणार्थ — जीवाणु से मनुष्य में यू यू यू (UUU) फेनिलएलेनीन (पीएचइ) का कूटलेखन करता है। इस नियम के कुछ अपवाद सूचकणिका प्रकूट व कुछ आदि जंतुओं (प्रोटोजोआ) में मिलता है।

### 21. ch 5 §5.6 ¶15 — en p19, hi p20, similarity 0.55
- en: Do you face any difficulty in predicting the opposite?
- hi: क्या आप इसका विपरीत पता लगाने में परेशानी महसूस करते हैं? क्यों?

### 22. ch 5 §5.8.1 ¶6 — en p23, hi p25, similarity 0.48
- en: Regulation of lac operon by repressor is referred to as negative regulation. Lac operon is under control of positive regulation as well, but it is beyond the scope of discussion at this level.
- hi: दमनकारी द्वारा लैक-प्रचालेक के नियमन को ऋणात्मक नियमन (निगेटीव रेग्यूलेशन) कहते है। लैक प्रचालेक धनात्मक नियमन (पाजीटीव रेग्यूलेशन) के नियंत्रण में भी होता है, लेकिन इस स्तर पर इसके बारे में चर्चा नहीं कि जाएगी।

### 23. ch 7 §7 ¶1 — en p3, hi p3, similarity 0.68 — the founder reads this one
- en: Health, for a long time, was considered as a state of body and mind where there was a balance of certain 'humors'. This is what early Greeks like Hippocrates as well as Indian Ayurveda system of medicine asserted. It was thought that persons with 'blackbile' belonged to hot personality and would have fevers. This idea was arrived at by pure reflective thought. The discovery of blood circulation by William Harvey using experimental method and the demonstration of normal body temperature in persons with blackbile using thermometer disproved the 'good humor' hypothesis of health. In later years, biology stated that mind influences, through neural system and endocrine system, our immune system and that our immune system maintains our health. Hence, mind and mental state can affect our health. Of course, health is affected by – (i) genetic disorders – deficiencies with which a child is born and deficiencies/defects which the child inherits from parents from birth; (ii) infections and (iii) life style including food and water we take, rest and exercise we give to our bodies, habits that we have or lack etc.
- hi: एक लंबे समय तक स्वास्थ्य को शरीर और मन की ऐसी स्थिति माना गया, जिसमें देह के कुछ तरलों (ह्यूमर) का संतुलन बना रहता था। प्राचीन यूनानवासी जैसेकि हिप्पोक्रेटीज और चिकित्सा का भारतीय आयुर्वेद तंत्र भी यही दावा करते थे। ऐसा माना जाता था कि 'काले पित्त' (ब्लैक बाइल) वाले व्यक्ति गरम व्यक्तित्व वाले होते हैं और उन्हें बुखार होता है। इस प्रकार के निष्कर्ष का कारण मात्र विमर्शी, विचारधारा थी। विलियम हार्वे द्वारा प्रयोगात्मक विधि अपनाते हुए रक्त परिसंचरण की खोज और थर्मामीटर का उपयोग करते हुए काले पित्त वाले व्यक्तियों के शरीर का तापमान प्रसामान्य दर्शाने से स्वास्थ्य के 'अच्छे तरल' की परिकल्पना (हाइपोथिसिस) को गलत सिद्ध कर दिया। कुछ वर्षो बाद जैविकी ने बताया कि तंत्रिका तंत्र (न्यूरल सिस्टम) और अंतः स्रावी तंत्र (इंडोक्राइन सिस्टम) हमारे प्रतिरक्षा तंत्र (इम्यून सिस्टम) को प्रभावित करता है और हमारा प्रतिरक्षा तंत्र हमारे स्वास्थ्य को बनाए रखता है। इस प्रकार मन और मानसिक अवस्था हमारे स्वास्थ्य पर असर डालती हैं। निःसंदेह, स्वास्थ्य निम्नलिखित बातों से प्रभावित होता है —

### 24. ch 7 §7.1 ¶2 — en p4–5, hi p5, similarity 0.64
- en: Salmonella typhi is a pathogenic bacterium which causes typhoid fever in human beings. These pathogens generally enter the small intestine through food and water contaminated with them and migrate to other organs through blood. Sustained high fever (39° to 40°C), weakness, stomach pain, constipation, headache and loss of appetite are some of the common symptoms of this disease. Intestinal perforation and death may occur in severe cases. Typhoid fever could be confirmed by Widal test : A classic case in medicine, that of Mary Mallon nicknamed Typhoid Mary, is worth mentioning here. She was a cook by profession and was a typhoid carrier who continued to spread typhoid for several years through the food she prepared.
- hi: साल्मोनेला टाइफी एक रोगजनक जीवाणु है जिससे मानव में टाइफॉइड ज्वर होता है। यह रोगजनक आमतौर से संदूषित (कंटामिनेटेड) भोजन और पानी द्वारा छोटी आँत में घुस जाता है और वहाँ से रक्त द्वारा शरीर के अन्य अंगों में पहुँच जाता है। इस रोग के कुछ सामान्य लक्षण हैं— लगातार उच्च ज्वर (39^0 से 40^0 सेंटी.), कमजोरी, आमाशय में पीड़ा, कब्ज, सिरदर्द और भूख न लगना आदि। गंभीर मामलों में आंत्र में छेद और मृत्यु भी हो सकती है। टाइफॉइड ज्वर की पुष्टि विडाल परीक्षण से हो सकती है। आयुर्विज्ञान में एक चिरप्रतिष्ठित मामला, मैरी मैलॉन (उपनाम टाइफाइड मैरी) का है जिसका यहाँ उल्लेख करना सटीक है। वह पेशे से रसोईया थी और जो खाना वह बनाती थी उसके द्वारा वर्षो तक टाइफॉइड वाहक के रूप में टाइफॉइड फैलाती रही।

### 25. ch 7 §7.3 ¶4 — en p14, hi p15–16, similarity 0.66
- en: Prevention of AIDS : As AIDS has no cure, prevention is the best option. Moreover, HIV infection, more often, spreads due to conscious behaviour patterns and is not something that happens inadvertently, like pneumonia or typhoid. Of course, infection in blood transfusion patients, new-borns (from mother) etc., may take place due to poor monitoring. The only excuse may be ignorance and it has been rightly said – “don't die of ignorance”. In our country the National AIDS Control Organisation (NACO) and other non-governmental organisation (NGOs) are doing a lot to educate people about AIDS. WHO has started a number of programmes to prevent the spreading of HIV infection. Making blood (from blood banks) safe from HIV, ensuring the use of only disposable needles and syringes in public and private hospitals and clinics, free distribution of condoms, controlling drug abuse, advocating safe sex and promoting regular check-ups for HIV in susceptible populations, are some such steps taken up.
- hi: एड्स की रोकथाम - एड्स को ठीक नहीं किया जा सकता; इसलिए इसकी रोकथाम ही सबसे उत्तम उपाय है। इसके अलावा, एचआईवी संक्रमण सचेतन व्यवहार पैटर्न के कारण फैलता है न कि न्यूमोनिया या टाइफॉइड की तरह अनजानें में। यह ठीक है कि रक्त-आधान रोगियों में, नवजातों में (माँ से) आदि में यह संक्रमण सही निगरानी (मॉनिटरिंग) न होने के कारण हो सकता है। एकमात्र बहाना अनभिज्ञता हो सकती है लेकिन कहावत बिल्कुल सही है कि 'अज्ञानता के कारण मत मरो'। हमारे देश में राष्ट्रीय एड्स नियंत्रण संगठन (एन ए सी ओ-नेशनल एड्स कंट्रोल आर्गेनाइजेशन) और अन्य गैर-सरकारी संगठन (एन जी ओ) लोगों को एड्स के बारे में शिक्षित करने के लिए बहुत काम कर रहे हैं। एचआईवी संक्रमण को फैलने से रोकने के लिए विश्व स्वास्थ्य संगठन ने अनेक कार्यक्रम आरंभ किए हैं। रक्त बैंकों के रक्त को एचआईवी से मुक्त करना, सार्वजनिक और निजी अस्पतालों और क्लिनिकों में केवल प्रयोज्य (डिस्पोजेबल) सुईयाँ और सिरिंज ही काम में लाई जाएँ-इसकी व्यवस्था करना, कंडोम का मुफ्त वितरण, ड्रग के कुप्रयोग को नियंत्रित करना, सुरक्षित यौन संबंधों की सिफारिश करना और सुग्राही समष्टि (ससेप्टेबुल पॉपुलेशन) में एचआईवी के लिए नियमित जाँच को बढ़ावा देना, इन कार्यक्रमों में से कुछ एक है।

### 26. ch 7 §7.5 ¶2 — en p16, hi p18, similarity 0.66 — the founder reads this one
- en: The drugs, which are commonly abused are opioids, cannabinoids and coca alkaloids. Majority of these are obtained from flowering plants. Some are obtained from fungi.
- hi: आमतौर पर जिन ड्रगों का कुप्रयोग किया जाता है वे हैं— ओपिऑइड्स, कैनेबिनॉइड्स और कोका एल्कैलॉइड्स। इनमें से अधिकांश पुष्पी पादपों से प्राप्त किए जाते हैं। कुछ ऐसे भी हैं जो कवकों से निकाले जाते हैं।

### 27. ch 7 §7.5.3 ¶2 — en p19, hi p22, similarity 0.68
- en: There may even be some far-reaching implications of drug/alcohol abuse. If an abuser is unable to get money to buy drugs/alcohol he/she may turn to stealing. The adverse effects are just not restricted to the person who is using drugs or alcohol. At times, a drug/alcohol addict becomes the cause of mental and financial distress to his/her entire family and friends.
- hi: ड्रग/ऐल्कोहल के कुप्रयोग के दूरगामी परिणाम भी हो सकते हैं। अगर कुप्रयोगकर्ता को ड्रग/ऐल्कोहल खरीदने के लिए पैसे नहीं मिलें तो वह चोरी का सहारा ले सकता/सकती है। ये प्रतिकूल प्रभाव केवल ड्रग/ऐल्कोहल का सेवन करने वाले तक सीमित नहीं रहता। कभी-कभी ड्रग/ऐल्कोहल आदि का अपने परिवार या मित्र आदि के लिए भी मानसिक और आर्थिक कष्ट का कारण बन सकता/सकती है।

### 28. ch 7 §7.5.3 ¶5 — en p20, hi p22, similarity 0.66
- en: Another misuse of drugs is what certain sportspersons do to enhance their performance. They (mis)use narcotic analgesics, anabolic steroids, diuretics and certain hormones in sports to increase muscle strength and bulk and to promote aggressiveness and as a result increase athletic performance. The side-effects of the use of anabolic steroids in females include masculinisation (features like males), increased aggressiveness, mood swings, depression, abnormal menstrual cycles, excessive hair growth on the face and body, enlargement of clitoris, deepening of voice. In males it includes acne, increased aggressiveness, mood swings, depression, reduction of size of the testicles, decreased sperm production, potential for kidney and liver dysfunction, breast enlargement, premature baldness, enlargement of the prostate gland. These effects may be permanent with prolonged use. In the adolescent male or female, severe facial and body acne, and premature closure of the growth centres of the long bones may result in stunted growth.
- hi: ड्रगों का एक और दुरुपयोग कुछ खिलाड़ियों द्वारा अपने प्रदर्शन को और बेहतर करने के लिए हो सकता है। वे खेलों में स्वापक पीड़ा हर (नारकोटिक एनलजेसिक्स), उपचयी स्टेराइडों, मूत्रल दवाओं और कुछ हॉर्मोनों का कुप्रयोग, मांसपेशियों को शक्तिशाली बनाने और अक्रामकता को बढ़ाने और फलस्वरूप खेल प्रदर्शन के लिए करते हैं। महिलाओं में उपचयी स्टेराइडों के सेवन के अनुषंगी प्रभावों में पुंस्त्वन (मैस्कुलिनाइजेसन) (यानी पुरूष जैसे लक्षण), बढ़ी अक्रामकता, भावदशा में उतार-चढ़ाव, अवसाद, असामान्य आर्तव चक्र, मुँह और शरीर पर बालों की अत्यधिक वृद्धि, भगशेफ का बढ़ जाना, आवाज का गहरा होना शामिल हैं। पुरूषों में मुँहासे, बढ़ी अक्रामकता, भावदशा में उतार चढ़ाव, अवसाद, वृषणों के आकार का घटना, शुक्राणु उत्पादन में कमी, यकृत और वृक्क की संभावित दुष्क्रियता (डिस्फंकसन), वक्ष (स्तन) का बढ़ना, समयपूर्व गंजापन, प्रोस्टेट ग्रंथि का बढ़ना शामिल हैं। लंबे समय तक सेवन से ये प्रभाव स्थायी हो सकते हैं। युवा नर या स्त्रियों में, मुँह और शरीर के सख्त मुँहासे, और लंबी अस्थियों के वृद्धि केंद्रों का कालपूर्व बंद होने के फलस्वरूप वृद्धि रुद्ध हो सकती है।

### 29. ch 8 §8.2.3 ¶3 — en p5, hi p5–6, similarity 0.55 — the founder reads this one
- en: Another bioactive molecule, cyclosporin A, that is used as an immunosuppressive agent in organ-transplant patients, is produced by the fungus Trichoderma polysporum. Statins produced by the yeast Monascus purpureus have been commercialised as blood-cholesterol lowering agents. It acts by competitively inhibiting the enzyme responsible for synthesis of cholesterol.
- hi: अन्य जैव सक्रिय अणु 'साइक्लोस्पोरिन-ए' है। जिसका प्रयोग अंग प्रतिरोपण में प्रतिरक्षा निरोधक (इम्युनोसप्रेसिव) कारक के रूप में रोगियों में किया जाता है। इसका उत्पादन ट्राइकोडर्मा पॉलोस्पोरम नामक कवक से किया जाता है। मोनॉस्कस परप्यूरीअस यीस्ट से उत्पन्न इस स्टैटिन का व्यापारिक स्तर पर प्रयोग रक्त-कॉलिस्ट्रॉल को कम करने वाले कारक के रूप में किया जाता है। कॉलिस्ट्रॉल के संश्लेषण के लिए उत्तरदायी एंजाइम स्पर्धा संदमन (निरोधण) की तरह क्रिया करते हैं।

### 30. ch 8 §8.5 ¶4 — en p9, hi p10, similarity 0.51
- en: A biological control being developed for use in the treatment of plant disease is the fungus Trichoderma. Trichoderma species are free-living fungi that are very common in the root ecosystems. They are effective biocontrol agents of several plant pathogens.
- hi: जैव वैज्ञानिक नियंत्रण के तहत कवक ट्राइकोडर्मा का उपयोग पादप रोगों के उपचार में किया जाता है। ट्राइकोडर्मा प्रजाति एक मुक्त जीवित कवक है, जो मूल-पारिस्थितिक तंत्र में सामान्य रूप से पाया जाता है। यह बहुत से पादप रोगजनकों के प्रभावशील जैव नियंत्रण कारक हैं।

### 31. ch 9 §9.2.1 ¶7 — en p7, hi p7–8, similarity 0.56
- en: Restriction enzymes cut the strand of DNA a little away from the centre of the palindrome sites, but between the same two bases on the opposite strands. This leaves single stranded portions at the ends. There are overhanging stretches called sticky ends on each strand (Figure 9.1). These are named so because they form hydrogen bonds with their complementary cut counterparts. This stickiness of the ends facilitates the action of the enzyme DNA ligase.
- hi: प्रतिबंधन एंजाइम डीएनए लड़ी को पैलिंड्रोम स्थल के केंद्र से थोड़ी दूरी पर लेकिन विपरीत लड़ियों में दो समान क्षारकों के बीच काटते हैं। जिसके फलस्वरूप सिरों पर एक लड़ीय भाग रह जाता है। प्रत्येक लड़ी में प्रलंबी फैलाव मिलते हैं जिन्हें चिपचिपा (स्टिकी) सिरा कहते हैं (चित्र 9.1)। इसे यह नाम इसलिए दिया गया है क्योंकि यह अपने पूरक कटे प्रतिरूप के साथ हाइड्रोजन आबंध (बॉन्ड) बनाते हैं। सिरों का यह चिपचिपापन एंजाइम डीएनए लाइगेज के कार्य में सहायता प्रदान करता है।

### 32. ch 10 §10.1 ¶14 — en p4, hi p4–5, similarity 0.46
- en: Using Agrobacterium vectors, nematode-specific genes were introduced into the host plant (Figure 10.2). The introduction of DNA was such that it produced both sense and anti-sense RNA in the host cells. These two RNA’s being complementary to each other formed a double stranded (dsRNA) that initiated RNAi and thus, silenced the specific mRNA of the nematode. The consequence was that the parasite could not survive in a transgenic host expressing specific interfering RNA. The transgenic plant therefore got itself protected from the parasite (Figure 10.2).
- hi: एग्रोबैक्टिरियम संवाहकों का उपयोग कर सूत्रकृमि विशिष्ट जीनों को परपोषी पौधों में प्रवेश कराया जा चुका है (चित्र 10.2)। डीएनए का प्रवेश इस प्रकार कराया जाता है कि परपोषी कोशिकाओं में अर्थ (सैंस) व प्रति-अर्थ (ऐंटीसैंस) आरएनए का निर्माण करता है। ये दोनों आरएनए एक दूसरे के पूरक होते हैं जो द्विसूत्रीय आरएनए का निर्माण करते हैं; जिससे आरएनए अंतरक्षेप प्रारंभ होता है और इसी कारण से सूत्रकृमि के विशिष्ट दूत आरएनए निष्क्रिय हो जाते हैं। इसके फलस्वरूप पारजीवी परपोषी में विशिष्ट अंतरक्षेपी आरएनए की उपस्थिति से परजीवी जीवित नहीं रह पाता है। इस प्रकार पारजीवी पौधे अपनी सुरक्षा परजीवी से करते हैं (चित्र 10.2)।

### 33. ch 10 §10.2.2 ¶1 — en p6, hi p6, similarity 0.63 — the founder reads this one
- en: If a person is born with a hereditary disease, can a corrective therapy be taken for such a disease? Gene therapy is an attempt to do this. Gene therapy is a collection of methods that allows correction of a gene defect that has been diagnosed in a child/embryo. Here genes are inserted into a person's cells and tissues to treat a disease. Correction of a genetic defect involves delivery of a normal gene into the individual or embryo to take over the function of and compensate for the non-functional gene.
- hi: यदि एक व्यक्ति आनुवंशिक रोग के साथ पैदा हुआ है, तो क्या इस रोग के उपचार हेतु कोई चिकित्सा व्यवस्था है? जीन चिकित्सा ऐसा ही एक प्रयास है। जीन चिकित्सा में उन विधियों का सहयोग लेते हैं जिनके द्वारा किसी बच्चे या भ्रूण में चिह्नित किए गए जीन दोषों का सुधार किया जाता है। उसमें रोग के उपचार हेतु जीनों को व्यक्ति की कोशिकाओं या ऊतकों में प्रवेश कराया जाता है। आनुवंशिक दोष वाली कोशिकाओं के उपचार हेतु सामान्य जीन को व्यक्ति या भ्रूण में स्थानांतरित करते हैं जो निष्क्रिय जीन की क्षतिपूर्ति कर उसके कार्यों को संपन्न करते हैं।

### 34. ch 11 §11.1.2 ¶9 — en p7, hi p7, similarity 0.67
- en: (i) Exponential growth: Resource (food and space) availability is obviously essential for the unimpeded growth of a population. Ideally, when resources in the habitat are unlimited, each species has the ability to realise fully its innate potential to grow in number, as Darwin observed while developing his theory of natural selection. Then the population grows in an exponential or geometric fashion. If in a population of size N, the birth rates (not total number but per capita births) are represented as b and death rates (again, per capita death rates) as d, then the increase or decrease in N during a unit time period t (dN/dt) will be dN/dt = (b – d) × N Let (b–d) = r, then dN/dt = rN
- hi: (क) चरघातांकी वृद्धि— किसी समष्टि की अबाधित वृद्धि के लिए स्पष्टतः संसाधन (आहार और स्थान) उपलब्ध होना अत्यावश्यक है। आदर्शत: आवास में जब संसाधन असीमित होते हैं तो प्रत्येक जाति में संख्या में वृद्धि कर सकने की अपनी जन्मजात शक्ति को पूरी तरह अनुभव करने की योग्यता होती है जैसा कि डार्विन ने अपने प्राकृतिक वरण के सिद्धांत को प्रतिपादित करते हुए प्रेक्षित किया। तब समष्टि चरघातांकी (एक्सपोनेन्शियल) अथवा ज्यामितीय (ज्योमेट्रिकल) शैली में वृद्धि करती है। अगर N साइज की समष्टि में, जन्म दरें (कुल संख्या नहीं, बल्कि प्रति व्यक्ति जन्म) b के रूप में और मृत्यु दरें (प्रति व्यक्ति मृत्यु दर) d के रूप में निरूपित की जाती हैं तब इकाई समय अवधि t (dN/ dt) के दौरान वृद्धि या कमी निम्नलिखित होगी— dN/ dt = ( b - d) x N मान लीजिए ( b-d) = r, तब dN/ dt= rN

### 35. ch 11 §11.1.2 ¶13 — en p8, hi p8, similarity 0.56 — the founder reads this one
- en: Darwin showed how even a slow growing animal like elephant could reach enormous numbers in the absence of checks. The following is an anecdote popularly narrated to demonstrate dramatically how fast a huge population could build up when growing exponentially.
- hi: असीमित संसाधन परिस्थितियों में चरघातांकी रूप से वृद्धि करने वाली कोई भी जाति थोड़े समय में ही विशाल समष्टि घनत्वों तक पहुँच सकती है। डार्विन ने दर्शाया कि हाथी जैसा धीमे बढ़ने वाला प्राणी, रोक न होने पर विशाल संख्या तक पहुँच सकता है। चरघातांकी रूप से वृद्धि करने पर विशाल समष्टि कितनी जल्दी निर्मित हो जाती है। इसके बारे में निम्नलिखित किस्सा लोकप्रिय है–

### 36. ch 12 §12.5 ¶3 — en p9, hi p9–10, similarity 0.68 — the founder reads this one
- en: In most ecosystems, all the pyramids, of number, of energy and biomass are upright, i.e., producers are more in number and biomass than the herbivores, and herbivores are more in number and biomass than the carnivores. Also energy at a lower trophic level is always more than at a higher level.
- hi: अधिकतर पारिस्थितिक तंत्रों में संख्याओं, ऊर्जा तथा जैव मात्रा के सभी पिरैमिड आधार से ऊपर की ओर होते हैं। अर्थात् शाकाहारियों की अपेक्षा उत्पादकों की संख्या एवं जैव मात्रा अधिक होती है और इसी तरह से शाकाहारियों की संख्या एवं जैव मात्रा मांसाहारियों की अपेक्षा अधिक होती है। इसी प्रकार से निम्न पोषण स्तर में ऊर्जा की मात्रा ऊपरी पोषण स्तर से अधिक होती है।

### 37. ch 13 §13.1 ¶3 — en p2, hi p2, similarity 0.55
- en: (ii) Species diversity : The diversity at the species level, for example, the Western Ghats have a greater amphibian species diversity than the Eastern Ghats.
- hi: (ख) जातीय (स्पीशीज़) विविधता- यह भिन्नता जाति स्तर पर है। उदाहरणार्थ-पश्चिमी घाट की उभयचर जातियों की विविधता पूर्वी घाट से अधिक है।

### 38. ch 13 §13.1.4 ¶1 — en p6–7, hi p7, similarity 0.60
- en: While it is doubtful if any new species are being added (through speciation) into the earth's treasury of species, there is no doubt about their continuing losses. The biological wealth of our planet has been declining rapidly and the accusing finger is clearly pointing to human activities. The colonisation of tropical Pacific Islands by humans is said to have led to the extinction of more than 2,000 species of native birds. The IUCN Red List (2004) documents the extinction of 784 species (including 338 vertebrates, 359 invertebrates and 87 plants) in the last 500 years. Some examples of recent extinctions include the dodo (Mauritius), quagga (Africa), thylacine (Australia), Steller's Sea Cow (Russia) and three subspecies (Bali, Javan, Caspian) of tiger. The last twenty years alone have witnessed the disappearance of 27 species. Careful analysis of records shows that extinctions across taxa are not random; some groups like amphibians appear to be more vulnerable to extinction. Adding to the grim scenario of extinctions is the fact that more than 15,500 species world-wide are facing the threat of extinction. Presently, 12 per cent of all bird species, 23 per cent of all mammal species, 32 per cent of all amphibian species and 31per cent of all gymnosperm species in the world face the threat of extinction.
- hi: जबकि यह संदेहपूर्ण है कि क्या (जाति उद्भवन द्वारा) कुछ नई जातियाँ पृथ्वी की जातियों में सम्मलित होती हैं, लेकिन इसमें कोई संदेह नहीं है कि उनकी निरंतर हानि होगी। हमारे ग्रह पर जैव संपदा भंडार में तेजी से हानि हो रही है। इसके लिए मानव क्रियाकलाप मुख्य कारण है। मानव द्वारा प्रशांत उष्ण कटिबंधिय द्वीपों पर आवासीय बस्तियाँ स्थापित करने से वहाँ के मूल पक्षियों की 2 हजार से अधिक जातियाँ विलुप्त हो गई हैं। आई.यू.सी.एन. की लाल सूची (2004) के साक्ष्यों के अनुसार पिछले 500 वर्षों में 784 जातियाँ (338 कशेरुकी, 359 अकशेरुकी तथा 87 पादप) लुप्त हो गयी हैं। नयी विलुप्त जातियों में मॉरीशस की डोडो, अफ्रीका की क्वैगा, आस्ट्रेलिया की थाइलेसिन, रूस की स्टेलर समुद्री गाय एवं बाली, जावा तथा कैस्पियन के बाघ की तीन उपजातियाँ शामिल हैं। पिछले 20 वर्षों में 27 जातियाँ विलुप्त हो गयी हैं। आँकड़ों का सावधानी पूर्वक विश्लेषण दर्शाता है कि जातियाँ वर्गको (टैक्सा) के विलोपन यादृच्छिक (रैंडम) नहीं हैं जैसे की उभयचर समूह अधिक विलुप्त हुए हैं। इस भयानक कथा के संदर्भ में एक तथ्य यह भी है कि विश्व की 15,500 से भी अधिक जातियाँ विलुप्ति के कगार पर हैं। इस समय 12 प्रतिशत पक्षी, 23 प्रतिशत स्तनधारी, 32 प्रतिशत उभयचर तथा 31 प्रतिशत आवृत्तबीजी की जातियाँ विलुप्ति के कगार पर हैं।

### 39. ch 13 §13.1.4 ¶4 — en p7, hi p7, similarity 0.63
- en: Causes of biodiversity losses: The accelerated rates of species extinctions that the world is facing now are largely due to human activities. There are four major causes (‘ The Evil Quartet ’ is the sobriquet used to describe them).
- hi: जैव विविधता की क्षति के कारण- जातीय विलोपन की बढ़ती हुई दर जिसका विश्व सामना कर रहा है वह मुख्यरूप से मानव क्रियाकलापों के कारण है। इसके चार मुख्य कारण हैं (निम्न उपशीर्षकों में इसका वर्णन किया गया है)-

### 40. ch 13 §13.2.2 ¶3 — en p10, hi p10, similarity 0.73
- en: In India, ecologically unique and biodiversity-rich regions are legally protected as biosphere reserves, national parks and sanctuaries. India now has 14 biosphere reserves, 90 national parks and 448 wildlife sanctuaries. India has also a history of religious and cultural traditions that emphasised protection of nature. In many cultures, tracts of forest were set aside, and all the trees and wildlife within were venerated and given total protection. Such sacred groves are found in Khasi and Jaintia Hills in Meghalaya, Aravalli Hills of Rajasthan, Western Ghat regions of Karnataka and Maharashtra and the Sarguja, Chanda and Bastar areas of Madhya Pradesh. In Meghalaya, the sacred groves are the last refuges for a large number of rare and threatened plants.
- hi: भारत में पारिस्थितिकतः अद्वितीय और जैवविविधता-समृद्ध क्षेत्रों को राष्ट्रीय उद्यानों, वन्यजीव अभ्यारणों, जीवमंडल आरक्षितियों (बायोस्फीयर रिजर्व) के रूप में कानूनी सुरक्षा प्रदान की गई है। अब भारत में 14 जीवमंडल संरक्षित क्षेत्र, 90 राष्ट्रीय उद्यान तथा 448 वन्य जीव अभ्यारण हैं। भारत में सांस्कृतिक व धार्मिक परंपरा का इतिहास जो प्रकृति की रक्षा करने पर जोर देता है। बहुत सी संस्कृतियों में वनों के लिए अलग भूभाग छोड़े जाते थे और उनमें सभी पौधों तथा वन्यजीवों की पूजा की जाती थी। इस तरह के पवित्र उपवन या आश्रय मेघालय की खासी तथा जयंतिया पहाड़ी, राजस्थान की अरावली, कर्नाटक तथा महाराष्ट्र के पश्चिमी घाट व मध्यप्रदेश की सरगूजा, चंदा व बस्तर क्षेत्र है। मेघालय के पवित्र उपवन बहुत सी दुर्लभ व संकटोत्पन्न पादपों की अंतिम शरणस्थली हैं।

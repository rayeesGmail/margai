# margai-pipeline ncert align

- run: 2026-10-15 07:28 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 500 English and 465 Hindi row(s) in 6 chapter(s)
- result: ok
request id: pipeline-ncert-align-f84aefda-5d96-4248-abb5-51b3cd37646f
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 465 | 186199 | ₹4.92 |

## sections whose rows do not pair one to one

en counts the English rows aligned: a number an `unprinted` entry left is listed below, not here
| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 9 | 9.2.2 | 4 | 5 | 1 |
| 9 | 9.2.3 | 24 | 23 | 3 |
| 9 | 9.3 | 8 | 6 | 2 |
| 9 | 9.5.2 | 21 | 16 | 7 |
| 9 | 9.5.4 | 10 | 11 | 7 |
| 9 | 9.6 | 12 | 7 | 5 |
| 9 | 9.7.1 | 16 | 15 | 1 |
| 10 | 10.3.3 | 13 | 11 | 2 |
| 10 | 10.4 | 13 | 12 | 3 |
| 10 | 10.5 | 6 | 5 | 1 |
| 10 | 10.6.1 | 9 | 9 | 2 |
| 10 | 10.6.2 | 6 | 5 | 1 |
| 10 | 10.7 | 11 | 10 | 1 |
| 11 | 11.2 | 6 | 4 | 2 |
| 11 | 11.4 | 3 | 2 | 1 |
| 11 | 11.4.3 | 13 | 9 | 4 |
| 11 | 11.7 | 12 | 13 | 1 |
| 11 | 11.8 | 7 | 10 | 3 |
| 12 | 12.2 | 5 | 4 | 1 |
| 12 | 12.2.1 | 11 | 9 | 4 |
| 12 | 12.2.2 | 5 | 7 | 2 |
| 12 | 12.4 | 11 | 11 | 2 |
| 12 | 12.5 | 5 | 4 | 1 |
| 13 | 13.2 | 15 | 15 | 4 |
| 13 | 13.4.2 | 17 | 16 | 3 |
| 13 | 13.5 | 6 | 5 | 1 |
| 13 | 13.6 | 2 | 3 | 1 |
| 13 | 13.7.1 | 12 | 11 | 1 |
| 13 | 13.7.2 | 9 | 10 | 1 |
| 13 | 13.7.3 | 10 | 6 | 4 |
| 14 | 14.2 | 18 | 15 | 3 |
| 14 | 14.3 | 7 | 8 | 1 |
| 14 | 14.4 | 15 | 13 | 2 |
| 14 | 14.6.1 | 5 | 4 | 1 |
| 14 | 14.6.2 | 11 | 9 | 2 |

## English paragraphs the Hindi does not print — numbers an `unprinted` entry left, not aligned

none

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 9 §9.2.2: hi ¶4+¶5 = en ¶4 (join hi)
- ch 9 §9.2.3: hi ¶9 = en ¶9+¶10 (split hi)
- ch 9 §9.2.3: hi ¶18 = en ¶19+¶20 (split hi)
- ch 9 §9.2.3: hi ¶19+¶20 = en ¶21 (join hi)
- ch 9 §9.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 9 §9.3: hi ¶2 = en ¶3+¶4 (split hi)
- ch 9 §9.5.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 9 §9.5.2: hi ¶2 = en ¶3+¶4 (split hi)
- ch 9 §9.5.2: hi ¶3 = en ¶5+¶6 (split hi)
- ch 9 §9.5.2: hi ¶5+¶6 = en ¶8 (join hi)
- ch 9 §9.5.2: hi ¶9 = en ¶11+¶12 (split hi)
- ch 9 §9.5.2: hi ¶10 = en ¶13+¶14 (split hi)
- ch 9 §9.5.2: hi ¶11 = en ¶15+¶16 (split hi)
- ch 9 §9.5.4: hi ¶1 = en ¶1+¶2 (split hi)
- ch 9 §9.5.4: hi ¶2 = en ¶3+¶4 (split hi)
- ch 9 §9.5.4: hi ¶3 = en ¶5+¶6 (split hi)
- ch 9 §9.5.4: hi ¶4+¶5 = en ¶7 (join hi)
- ch 9 §9.5.4: hi ¶7+¶8 = en ¶9 (join hi)
- ch 9 §9.5.4: hi ¶9+¶10 = en ¶10 (join hi)
- ch 9 §9.5.4: hi ¶11 has no english
- ch 9 §9.6: hi ¶2 = en ¶2+¶3 (split hi)
- ch 9 §9.6: hi ¶3 = en ¶4+¶5 (split hi)
- ch 9 §9.6: hi ¶4 = en ¶6+¶7 (split hi)
- ch 9 §9.6: hi ¶5 = en ¶8+¶9 (split hi)
- ch 9 §9.6: hi ¶6 = en ¶10+¶11 (split hi)
- ch 9 §9.7.1: hi ¶12 = en ¶12+¶13 (split hi)
- ch 10 §10.3.3: hi ¶6 = en ¶6+¶7 (split hi)
- ch 10 §10.3.3: en ¶10 has no hindi
- ch 10 §10.4: hi ¶4 = en ¶4+¶5 (split hi)
- ch 10 §10.4: hi ¶7+¶8 = en ¶8 (join hi)
- ch 10 §10.4: hi ¶9 = en ¶9+¶10 (split hi)
- ch 10 §10.5: hi ¶4 = en ¶4+¶5 (split hi)
- ch 10 §10.6.1: hi ¶5 = en ¶5+¶6 (split hi)
- ch 10 §10.6.1: hi ¶8 has no english
- ch 10 §10.6.2: en ¶6 has no hindi
- ch 10 §10.7: hi ¶10 = en ¶10+¶11 (split hi)
- ch 11 §11.2: hi ¶3 = en ¶3+¶4 (split hi)
- ch 11 §11.2: hi ¶4 = en ¶5+¶6 (split hi)
- ch 11 §11.4: hi ¶1 = en ¶1+¶2 (split hi)
- ch 11 §11.4.3: en ¶3 has no hindi
- ch 11 §11.4.3: hi ¶3 = en ¶4+¶5 (split hi)
- ch 11 §11.4.3: en ¶9 has no hindi
- ch 11 §11.4.3: hi ¶7 = en ¶10+¶11 (split hi)
- ch 11 §11.7: hi ¶4+¶5 = en ¶4 (join hi)
- ch 11 §11.8: hi ¶3+¶4 = en ¶3 (join hi)
- ch 11 §11.8: hi ¶7+¶8 = en ¶6 (join hi)
- ch 11 §11.8: hi ¶9+¶10 = en ¶7 (join hi)
- ch 12 §12.2: hi ¶4 = en ¶4+¶5 (split hi)
- ch 12 §12.2.1: hi ¶4+¶5 = en ¶4 (join hi)
- ch 12 §12.2.1: hi ¶7 = en ¶6+¶7 (split hi)
- ch 12 §12.2.1: hi ¶8 = en ¶8+¶9 (split hi)
- ch 12 §12.2.1: hi ¶9 = en ¶10+¶11 (split hi)
- ch 12 §12.2.2: hi ¶4+¶5 = en ¶4 (join hi)
- ch 12 §12.2.2: hi ¶6+¶7 = en ¶5 (join hi)
- ch 12 §12.4: hi ¶3+¶4 = en ¶3 (join hi)
- ch 12 §12.4: hi ¶11 = en ¶10+¶11 (split hi)
- ch 12 §12.5: hi ¶3 = en ¶3+¶4 (split hi)
- ch 13 §13.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 13 §13.2: hi ¶4+¶5 = en ¶3 (join hi)
- ch 13 §13.2: hi ¶6 = en ¶4+¶5 (split hi)
- ch 13 §13.2: hi ¶10 = en ¶9+¶10 (split hi)
- ch 13 §13.4.2: hi ¶2+¶3 = en ¶2 (join hi)
- ch 13 §13.4.2: hi ¶7 = en ¶6+¶7 (split hi)
- ch 13 §13.4.2: hi ¶13 = en ¶13+¶14 (split hi)
- ch 13 §13.5: hi ¶3 = en ¶3+¶4 (split hi)
- ch 13 §13.6: hi ¶3 has no english
- ch 13 §13.7.1: hi ¶7 = en ¶7+¶8 (split hi)
- ch 13 §13.7.2: hi ¶4+¶5 = en ¶4 (join hi)
- ch 13 §13.7.3: hi ¶2 = en ¶2+¶3 (split hi)
- ch 13 §13.7.3: en ¶4 has no hindi
- ch 13 §13.7.3: en ¶5 has no hindi
- ch 13 §13.7.3: hi ¶4 = en ¶7+¶8 (split hi)
- ch 14 §14.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 14 §14.2: hi ¶3 = en ¶4+¶5 (split hi)
- ch 14 §14.2: hi ¶4 = en ¶6+¶7 (split hi)
- ch 14 §14.3: hi ¶1+¶2 = en ¶1 (join hi)
- ch 14 §14.4: hi ¶3 = en ¶3+¶4 (split hi)
- ch 14 §14.4: hi ¶4 = en ¶5+¶6 (split hi)
- ch 14 §14.6.1: hi ¶4 = en ¶4+¶5 (split hi)
- ch 14 §14.6.2: hi ¶8 = en ¶8+¶9 (split hi)
- ch 14 §14.6.2: hi ¶9 = en ¶10+¶11 (split hi)

## pairs weaker than 0.45 — read both against the pages

- ch 9 §9.2: hi ¶1 = en ¶1 — similarity 0.37 — en «We are familiar with the laws of reflection.…» · hi «हम परावर्तन के नियमों से परिचित हैं। परावर्तन…»
- ch 9 §9.2.3: hi ¶2 = en ¶2 — similarity 0.45 — en «In principle, we can take any two rays…» · hi «सिद्धांत रूप में, हम वस्तु के किसी बिंदु…»
- ch 9 §9.5: hi ¶1 = en ¶1 — similarity 0.43 — en «We have so far considered refraction at a…» · hi «अब तक हमने समतल अंतरापृष्ठों पर अपवर्तन के…»
- ch 9 §9.5.1: hi ¶2 = en ¶2 — similarity 0.43 — en «Equation (9.16) gives us a relation between object…» · hi «समीकरण (9.16) से हमें बिंब तथा प्रतिबिंब के…»
- ch 10 §10.2: hi ¶6 = en ¶6 — similarity 0.44 — en «In a similar manner, we can use Huygens…» · hi «इसी विधि द्वारा हम हाइगेंस के सिद्धांत का…»
- ch 10 §10.3.3: hi ¶9 = en ¶11 — similarity 0.43 — en «(a) Reflection and refraction arise through interaction of…» · hi «हल (a) परावर्तन तथा अपवर्तन, आपतित प्रकाश की…»
- ch 10 §10.6.1: hi ¶9 = en ¶9 — similarity 0.42 — en «In the double-slit experiment, we must note that…» · hi «द्विझिरी प्रयोग में, हमें ध्यान देना चाहिए कि…»
- ch 10 §10.7: hi ¶3 = en ¶3 — similarity 0.43 — en «It should be mentioned that the linearly polarised…» · hi «यह बतलाना आवश्यक है कि [समीकरणों (10.15) तथा…»
- ch 11 §11.4.2: hi ¶4 = en ¶4 — similarity 0.45 — en «We can now repeat this experiment with incident…» · hi «अब हम इस प्रयोग को आपतित विकिरण की…»
- ch 12 §12.2: hi ¶2 = en ¶2 — similarity 0.44 — en «A typical graph of the total number of…» · hi «चित्र 12.3 में किसी दिए समयांतराल में विभिन्न…»
- ch 12 §12.2.1: hi ¶1 = en ¶1 — similarity 0.43 — en «The trajectory traced by an alpha-particle depends on…» · hi «ऐल्फ़ा-कण द्वारा अनुरेखित प्रक्षेप पथ, संघट्ट के संघट्ट…»
- ch 13 §13.3: hi ¶3 = en ¶3 — similarity 0.42 — en «By performing scattering experiments in which fast electrons,…» · hi «ऐसे प्रकीर्णन प्रयोग करके जिनमें alpha-कणों के स्थान…»
- ch 13 §13.7.2: hi ¶3 = en ¶3 — similarity 0.40 — en «When fusion is achieved by raising the temperature…» · hi «ऊर्जा की उपयोगी मात्रा उत्पन्न करने के लिए…»
- ch 14 §14.2: hi ¶1 = en ¶1 — similarity 0.26 — en «On the basis of conductivity On the basis…» · hi «चालकता के आधार पर»

## numbers one edition of a pair carries and the other does not

- ch 9 §9.2.2 ¶3: only in en 2, 2, 2, 2, 9.1, 9.2 · only in hi —
- ch 9 §9.2.3 ¶14: only in en 9.6 · only in hi 9.5
- ch 9 §9.2.3 ¶16: only in en 9.7 · only in hi —
- ch 9 §9.4 ¶2: only in en — · only in hi 90
- ch 9 §9.5.1 ¶2: only in en — · only in hi 9.16
- ch 9 §9.5.2 ¶9: only in en — · only in hi 9.22
- ch 9 §9.5.2 ¶21: only in en — · only in hi 1.33
- ch 9 §9.5.3 ¶5: only in en — · only in hi 1
- ch 9 §9.7.1 ¶3: only in en — · only in hi 6
- ch 9 §9.7.1 ¶5: only in en — · only in hi 0, 0
- ch 9 §9.7.1 ¶6: only in en — · only in hi 0
- ch 9 §9.7.1 ¶9: only in en — · only in hi 0, 0, 0
- ch 9 §9.7.1 ¶10: only in en — · only in hi 0
- ch 9 §9.7.1 ¶15: only in en — · only in hi 0, 0
- ch 9 §9.7.2 ¶1: only in en — · only in hi 0, 0
- ch 9 §9.7.2 ¶4: only in en — · only in hi 1
- ch 10 §10.1 ¶3: only in en — · only in hi 9
- ch 10 §10.1 ¶6: only in en 1855 · only in hi 1864
- ch 10 §10.3.1 ¶3: only in en — · only in hi 2, 1
- ch 10 §10.4 ¶1: only in en — · only in hi 11
- ch 10 §10.6.1 ¶3: only in en — · only in hi 10.14
- ch 10 §10.6.2 ¶3: only in en 10.15 · only in hi 10.14
- ch 10 §10.7 ¶1: only in en — · only in hi 10.15, 11
- ch 10 §10.7 ¶6: only in en 10.18, 2, 2, 2 · only in hi 1, 1, 1
- ch 11 §11.1 ¶1: only in en 19 · only in hi —
- ch 11 §11.7 ¶11: only in en — · only in hi 0, 10, 5.16, 14
- ch 12 §12.4.1 ¶1: only in en 0 · only in hi —
- ch 12 §12.4.1 ¶2: only in en 1.51 · only in hi 1.53
- ch 12 §12.6 ¶2: only in en — · only in hi 11
- ch 12 §12.6 ¶4: only in en 12.15 · only in hi 12.5
- ch 13 §13.4.2 ¶1: only in en 16, 8, 8, 2.01593, 16.12744 · only in hi —
- ch 13 §13.6 ¶2: only in en — · only in hi 100
- ch 13 §13.7.2 ¶2: only in en — · only in hi 13.13
- ch 14 §14.2 ¶1: only in en 1 · only in hi —
- ch 14 §14.3 ¶7: only in en 4 · only in hi —
- ch 14 §14.4 ¶7: only in en — · only in hi 4, 5
- ch 14 §14.4 ¶10: only in en — · only in hi 14.8

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 9 §9.2.3 ¶8 — en p5, hi p4, similarity 0.61
- en: We now derive the mirror equation or the relation between the object distance (u), image distance (v) and the focal length (f ).
- hi: अब हम दर्पण समीकरण अथवा बिंब दूरी (u), प्रतिबिंब दूरी (v) तथा फ़ोकस दूरी (f) के बीच संबंध व्युत्पन्न करेंगे।

### 2. ch 9 §9.2.3 ¶11 — en p5, hi p5, similarity 0.71
- en: Equation (9.6) is a relation involving magnitude of distances. We now apply the sign convention. We note that light travels from the object to the mirror MPN. Hence this is taken as the positive direction. To reach the object AB, image A'B' as well as the focus F from the pole P, we have to travel opposite to the direction of incident light. Hence, all the three will have negative signs. Thus, B' P = –v, FP = –f, BP = –u Using these in Eq. (9.6), we get (–v + f) / (– f ) = (–v) / (–u) or (v – f ) / f = v / u v / f = 1 + v / u Dividing it by v, we get 1/v + 1/u = 1/f (9.7) This relation is known as the mirror equation.
- hi: समीकरण (9.6) में दूरियों के परिमाण सम्मिलित हैं। अब हम चिह्न परिपाटी को लागू करते हैं। हम नोट करते हैं कि प्रकाश बिंब से दर्पण MPN की ओर गमन करता है। इस प्रकार इस दिशा को धनात्मक लिया जाता है। ध्रुव P से बिंब AB, प्रतिबिंब A'B' तथा फ़ोकस F तक पहुँचने के लिए हमें आपतित प्रकाश की दिशा के विपरीत दिशा में गमन करना पड़ता है। इसलिए, इन तीनों के चिह्न ऋणात्मक होंगे। अतः B' P = –v, FP = –f, BP = –u समीकरण (9.6) में इनका उपयोग करने पर प्राप्त होता है (–v + f) / (–f) = (–v) / (–u) अथवा (v – f) / f = v / u v / f = 1 + v / u इसे v से भाग देने पर हमें प्राप्त होगा 1/v + 1/u = 1/f (9.7) यह संबंध दर्पण समीकरण कहलाता है।

### 3. ch 9 §9.2.3 ¶16 — en p6, hi p6, similarity 0.53 — the founder reads this one
- en: Example 9.2 A mobile phone lies along the principal axis of a concave mirror, as shown in Fig. 9.7. Show by suitable diagram, the formation of its image. Explain why the magnification is not uniform. Will the distortion of image depend on the location of the phone with respect to the mirror?
- hi: उदाहरण 9.2 किसी अवतल दर्पण के मुख्य अक्ष पर एक मोबाइल फोन रखा है। उचित किरण आरेख द्वारा प्रतिबिंब की रचना दर्शाइए। व्याख्या कीजिए कि आवर्धन एकसमान क्यों नहीं है। क्या प्रतिबिंब की विकृति दर्पण के सापेक्ष फोन की स्थिति पर निर्भर करती है?

### 4. ch 9 §9.2.3 ¶17 — en p7, hi p7, similarity 0.59
- en: Solution The ray diagram for the formation of the image of the phone is shown in Fig. 9.7. The image of the part which is on the plane perpendicular to principal axis will be on the same plane. It will be of the same size, i.e., B'C = BC. You can yourself realise why the image is distorted.
- hi: हल चित्र 9.7 में फ़ोन के प्रतिबिंब की रचना का प्रकाश-किरण आरेख दर्शाया गया है। मुख्य अक्ष के लंबवत समतल में स्थित भाग का प्रतिबिंब उसी समतल में होगा। यह उसी साइज़ का होगा, अर्थात B'C = BC। आप स्वयं ही पूर्ण रूप से समझ सकते हैं कि प्रतिबिंब में विकृति क्यों है?

### 5. ch 9 §9.3 ¶7 — en p9, hi p9, similarity 0.69
- en: It also follows that if n_32 is the refractive index of medium 3 with respect to medium 2 then n_32 = n_31 × n_12, where n_31 is the refractive index of medium 3 with respect to medium 1.
- hi: यदि n_32 माध्यम 3 का माध्यम 2 के सापेक्ष अपवर्तनांक है तो यह भी स्पष्ट है कि n_32 = n_31 × n_12, यहाँ n_31 माध्यम 3 का माध्यम 1 के सापेक्ष अपवर्तनांक है।

### 6. ch 9 §9.4 ¶4 — en p10, hi p10, similarity 0.54
- en: For values of i larger than i_c, Snell's law of refraction cannot be satisfied, and hence no refraction is possible.
- hi: i के i_c से अधिक मानों के लिए स्नेल के अपवर्तन के नियम को लागू नहीं किया जा सकता, अतः कोई अपवर्तन संभव नहीं होता।

### 7. ch 9 §9.4 ¶8 — en p11, hi p11, similarity 0.60
- en: Pour this water in a long test tube and shine the laser light from top, as shown in Fig. 9.12(c). Adjust the direction of the laser beam such that it is totally internally reflected every time it strikes the walls of the tube. This is similar to what happens in optical fibres.
- hi: इस जल को एक लंबी परखनली में उलटिए तथा लेसर प्रकाश को इसके ऊपर से डालिए जैसा कि चित्र 9.12 (c) में दर्शाया गया है। लेसर किरण-पुंज की दिशा को इस प्रकार समायोजित कीजिए कि प्रत्येक बार जब यह परखनली की दीवारों से टकराए तो इसका पूर्ण आंतरिक परावर्तन हो। यह दृश्य ऐसा ही है जैसा कि प्रकाशिक तंतुओं में होता है।

### 8. ch 9 §9.5.1 ¶3 — en p13, hi p13, similarity 0.64
- en: Example 9.5 Light from a point source in air falls on a spherical glass surface (n = 1.5 and radius of curvature = 20 cm). The distance of the light source from the glass surface is 100 cm. At what position the image is formed?
- hi: उदाहरण 9.5 वायु में रखे किसी बिंदु स्रोत से प्रकाश काँच के किसी गोलीय पृष्ठ पर पड़ता है। (n = 1.5 तथा वक्रता त्रिज्या = 20 cm)। प्रकाश स्रोत की काँच के पृष्ठ से दूरी 100 cm है। प्रतिबिंब कहाँ बनेगा?

### 9. ch 9 §9.5.2 ¶19 — en p16, hi p16, similarity 0.51 — the founder reads this one
- en: When we apply the sign convention, we see that, for erect (and virtual) image formed by a convex or concave lens, m is positive, while for an inverted (and real) image, m is negative.
- hi: चिह्न परिपाटी का पालन करने पर हम यह पाते हैं कि उत्तल अथवा अवतल लेंस द्वारा बने सीधे (तथा आभासी) प्रतिबिंब के लिए m धनात्मक होता है, जबकि किसी उलटे (तथा वास्तविक) प्रतिबिंब के लिए m ऋणात्मक होता है।

### 10. ch 9 §9.7.1 ¶6 — en p22, hi p23, similarity 0.59
- en: We now find the angle subtended at the eye by the image when the object is at u. From the relations h'/h = m = v/u we have the angle subtended by the image tan theta_i = h'/(-v) = h/(-v) · v/u = h/(-u) ≈ theta. The angle subtended by the object, when it is at u = –f. theta_i = (h/f) (9.41) as is clear from Fig. 9.23(c). The angular magnification is, therefore m = (theta_i / theta_o) = D/f (9.42)
- hi: अब हम प्रतिबिंब द्वारा नेत्र पर अंतरित कोण, जबकि बिंब u पर रखा है, ज्ञात करते हैं। संबंध h′/h = m = v/u से प्रतिबिंब द्वारा नेत्र पर अंतरित कोण tan theta_i = h′/(-v) = h/(-v) · v/u = h/(-u) ≈ theta; बिंब द्वारा अंतरित कोण, जबकि बिंब अब u = -f पर है theta_i = (h/f) (9.41) जैसा कि चित्र 9.23 (c) से स्पष्ट है। अतः कोणीय आवर्धन (आवर्धन क्षमता) है m = (theta_i/theta_0) = D/f (9.42)

### 11. ch 9 §9.7.1 ¶8 — en p23, hi p23–24, similarity 0.59
- en: A simple microscope has a limited maximum magnification (≤ 9) for realistic focal lengths. For much larger magnifications, one uses two lenses, one compounding the effect of the other. This is known as a compound microscope. A schematic diagram of a compound microscope is shown in Fig. 9.24. The lens nearest the object, called the objective, forms a real, inverted, magnified image of the object. This serves as the object for the second lens, the eyepiece, which functions essentially like a simple microscope or magnifier, produces the final image, which is enlarged and virtual. The first inverted image is thus near (at or within) the focal plane of the eyepiece, at a distance appropriate for final image formation at infinity, or a little closer for image formation at the near point. Clearly, the final image is inverted with respect to the original object.
- hi: वास्तविक फ़ोकस दूरियों के लेंसों के लिए किसी सरल सूक्ष्मदर्शी का अधिकतम आवर्धन (≤ 9) होता है। अधिक आवर्धन के लिए दो लेंसों का उपयोग किया जाता है, जिनमें एक लेंस दूसरे लेंस के प्रभाव को संयुक्त (बढ़ाता) करता है। इसे संयुक्त सूक्ष्मदर्शी कहते हैं। चित्र 9.24 में संयुक्त सूक्ष्मदर्शी का व्यवस्था आरेख दर्शाया गया है। बिंब के सबसे निकट के लेंस को अभिदृश्यक (objective) कहते हैं जो बिंब का वास्तविक, उलटा, आवर्धित प्रतिबिंब बनाता है। यह प्रतिबिंब दूसरे लेंस के लिए बिंब का कार्य करता है। इस दूसरे लेंस को नेत्रिका (eye-piece) कहते हैं, जो वास्तविक रूप से सरल सूक्ष्मदर्शी अथवा आवर्धक के रूप में कार्य करके अंतिम आवर्धित आभासी प्रतिबिंब बनाता है। इस प्रकार पहला उलटा प्रतिबिंब नेत्रिका के फोकस बिंदु के निकट (फ़ोकस पर या इसके अंदर) होता है, यह नेत्रिका से इतनी दूरी पर होता है जो अंतिम प्रतिबिंब को अनंत पर बनाने के लिए उपयुक्त होती है तथा उस स्थिति के भी काफ़ी निकट होती है जिस पर यदि प्रतिबिंब स्थित हो तो अंतिम निकट बिंदु पर बने। स्पष्टतः, अंतिम प्रतिबिंब मूल बिंब के सापेक्ष उलटा बनता है।

### 12. ch 9 §9.7.2 ¶1 — en p24, hi p25, similarity 0.57
- en: The telescope is used to provide angular magnification of distant objects (Fig. 9.25). It also has an objective and an eyepiece. But here, the objective has a large focal length and a much larger aperture than the eyepiece. Light from a distant object enters the objective and a real image is formed in the tube at its second focal point. The eyepiece magnifies this image producing a final inverted image. The magnifying power m is the ratio of the angle beta subtended at the eye by the final image to the angle alpha which the object subtends at the lens or the eye. Hence m ≈ beta / alpha ≈ (h / f_e) . (f_o / h) = f_o / f_e (9.46)
- hi: दूरदर्शक अथवा दूरबीन (चित्र 9.25) का उपयोग दूर की वस्तुओं को कोणीय आवर्धन प्रदान करने के लिए किया जाता है। इसमें भी एक अभिदृश्यक तथा एक नेत्रिका होती है। परंतु यहाँ पर, नेत्रिका की अपेक्षा अभिदृश्यक की फ़ोकस दूरी अधिक तथा इसका द्वारक भी काफ़ी अधिक होता है। किसी दूरस्थ बिंब से चलकर प्रकाश अभिदृश्यक में प्रवेश करता है तथा ट्यूब के अंदर इसके द्वितीय फ़ोकस पर वास्तविक प्रतिबिंब बनता है। नेत्रिका इस प्रतिबिंब को आवर्धित करके अंतिम उलटा प्रतिबिंब बनाती है। आवर्धन क्षमता m, प्रतिबिंब द्वारा नेत्र पर अंतरित कोण beta तथा बिंब द्वारा नेत्र पर अथवा लेंस पर अंतरित कोण alpha के अनुपात द्वारा परिभाषित किया जाता है। अतः m ≈ beta / alpha ≈ (h / f_e).(f_0 / h) = f_0 / f_e (9.46)

### 13. ch 10 §10.1 ¶2 — en p1, hi p1, similarity 0.58
- en: In 1678, the Dutch physicist Christiaan Huygens put forward the wave theory of light – it is this wave model of light that we will discuss in this chapter. As we will see, the wave model could satisfactorily explain the phenomena of reflection and refraction; however, it predicted that on refraction if the wave bends towards the normal then the speed of light would be less in the second medium. This is in contradiction to the prediction made by using the corpuscular model of light. It was much later confirmed by experiments where it was shown that the speed of light in water is less than the speed in air confirming the prediction of the wave model; Foucault carried out this experiment in 1850.
- hi: सन् 1678 में डच भौतिकविद क्रिस्टिआन हाइगेंस ने प्रकाश के तरंग सिद्धांत को प्रस्तुत किया– इस अध्याय में हम प्रकाश के इसी तरंग सिद्धांत पर विचार करेंगे। हम देखेंगे कि तरंग मॉडल परावर्तन तथा अपवर्तन की घटनाओं की संतोषप्रद रूप से व्याख्या कर सकता है; तथापि, यह प्रागुक्त करता है कि अपवर्तन के समय यदि तरंग अभिलंब की ओर मुड़ती है तो दूसरे माध्यम में प्रकाश की चाल कम होगी। यह प्रकाश के कणिका मॉडल को उपयोग करते समय की गई प्रागुक्ति के विपरीत है। सन् 1850 में फूको द्वारा किए गए प्रयोग द्वारा दर्शाया गया कि जल में प्रकाश की चाल वायु में प्रकाश की चाल से कम है। इस प्रकार तरंग मॉडल की प्रागुक्ति की पुष्टि की गई।

### 14. ch 10 §10.2 ¶1 — en p3, hi p3, similarity 0.47
- en: We would first define a wavefront: when we drop a small stone on a calm pool of water, waves spread out from the point of impact. Every point on the surface starts oscillating with time. At any instant, a photograph of the surface would show circular rings on which the disturbance is maximum. Clearly, all points on such a circle are oscillating in phase because they are at the same distance from the source. Such a locus of points, which oscillate in phase is called a wavefront; thus a wavefront is defined as a surface of constant phase. The speed with which the wavefront moves outwards from the source is called the speed of the wave. The energy of the wave travels in a direction perpendicular to the wavefront.
- hi: सर्वप्रथम हम तरंगाग्र को परिभाषित करेंगे। जब हम किसी शांत जल के तालाब में एक छोटा पत्थर फेंकते हैं तब प्रतिघात बिंदु से चारों ओर तरंगें फैलती हैं। पृष्ठ का प्रत्येक बिंदु समय के साथ दोलन करना प्रारंभ कर देता है। किसी एक क्षण पर पृष्ठ का फ़ोटोग्राफ़ उन वृत्ताकार वलयों को दर्शाएगा जिनके ऊपर विक्षोभ अधिकतम हैं। स्पष्टतः इस प्रकार के वृत्त के सभी बिंदु समान कला में दोलन करते हैं क्योंकि वे स्रोत से समान दूरी पर हैं। समान कला में दोलन करते ऐसे सभी बिंदुओं का बिंदु पथ तरंगाग्र कहलाता है। अतः एक तरंगाग्र को एक समान कला के पृष्ठ के रूप में परिभाषित किया जाता है। जिस गति के साथ तरंगाग्र स्रोत से बाहर की ओर बढ़ता है, वह तरंग की चाल कहलाती है। तरंग की ऊर्जा तरंगाग्र के लंबवत चलती है।

### 15. ch 10 §10.3.1 ¶4 — en p5, hi p5, similarity 0.76
- en: This is the Snell's law of refraction. Further, if lambda_1 and lambda_2 denote the wavelengths of light in medium 1 and medium 2, respectively and if the distance BC is equal to lambda_1 then the distance AE will be equal to lambda_2 (because if the crest from B has reached C in time tau, then the crest from A should have also reached E in time tau); thus, lambda_1 / lambda_2 = BC / AE = v_1 / v_2 or v_1 / lambda_1 = v_2 / lambda_2 (10.7)
- hi: यह स्नैल का अपवर्तन संबंधी नियम है। यदि lambda_1 तथा lambda_2 क्रमशः माध्यम 1 तथा माध्यम 2 में प्रकाश की तरंगदैर्घ्य को निरूपित करते हैं और यदि दूरी BC, lambda_1 के बराबर है तब दूरी AE, lambda_2 के बराबर होगी (क्योंकि यदि कोई शृंग B से C तक tau समय में पहुँचता है तो वह शृंग A से E तक भी tau समय में ही पहुँचेगा); अतः lambda_1 / lambda_2 = BC / AE = v_1 / v_2 अथवा v_1 / lambda_1 = v_2 / lambda_2 (10.7)

### 16. ch 10 §10.3.3 ¶12 — en p8, hi p8, similarity 0.59
- en: (b) No. Energy carried by a wave depends on the amplitude of the wave, not on the speed of wave propagation.
- hi: (b) नहीं। तरंग द्वारा ले जाने वाली ऊर्जा तरंग के आयाम पर निर्भर करती है, यह तरंग संचरण की चाल पर निर्भर नहीं करती।

### 17. ch 10 §10.5 ¶6 — en p12, hi p11, similarity 0.62
- en: Thus dark and bright bands appear on the screen, as shown in Fig. 10.13. Such bands are called fringes. Equations (10.13) and (10.14) show that dark and bright fringes are equally spaced.
- hi: इस प्रकार चित्र 10.13 की भाँति परदे पर अदीप्त तथा दीप्त बैंड दिखलाई देंगे। ऐसे बैंडों को फ्रिंज कहते हैं। समीकरण (10.13) तथा (10.14) दर्शाते हैं कि काले तथा दीप्त फ्रिंज समान दूरी पर हैं।

### 18. ch 10 §10.6.2 ¶1 — en p14, hi p14, similarity 0.63
- en: It is surprisingly easy to see the single-slit diffraction pattern for oneself. The equipment needed can be found in most homes — two razor blades and one clear glass electric bulb preferably with a straight filament. One has to hold the two blades so that the edges are parallel and have a narrow slit in between. This is easily done with the thumb and forefingers (Fig. 10.16).
- hi: एकल झिरी विवर्तन पैटर्न को स्वयं ही देखना आश्चर्यजनक रूप से सरल है। आवश्यक उपकरण अधिकांश घरों में पाया जा सकता है– दो रेज़र ब्लेड तथा एक पारदर्शक काँच का विद्युत बल्ब (किसी सीधे तंतु वाले बल्ब को वरीयता प्रदान करें)। दोनों ब्लेडों को इस प्रकार पकड़ा जाता है कि उनके किनारे समांतर हों और दोनों के बीच एक संकीर्ण झिरी बने। यह सरलता से अँगूठे तथा उँगलियों के द्वारा भी किया जा सकता है (चित्र 10.16)।

### 19. ch 10 §10.7 ¶3 — en p16, hi p15, similarity 0.43
- en: It should be mentioned that the linearly polarised waves [described by Eqs. (10.15) and (10.17)] are all transverse waves; i.e., the displacement of each point of the string is always at right angles to the direction of propagation of the wave. Finally, if the plane of vibration of the string is changed randomly in very short intervals of time, then we have what is known as an unpolarised wave. Thus, for an unpolarised wave the displacement will be randomly changing with time though it will always be perpendicular to the direction of propagation.
- hi: यह बतलाना आवश्यक है कि [समीकरणों (10.15) तथा (10.17) से वर्णित] सभी रैखिकतः ध्रुवित तरंगें अनुप्रस्थ तरंगें होती हैं; अर्थात डोरी के प्रत्येक बिंदु का विस्थापन सदैव तरंग संचरण की दिशा के लंबवत होता है। अंततः, यदि डोरी के कंपन के तल को अत्यंत अल्प अंतराल में यादृच्छिकतः बदला जाए तो हमें अध्रुवित तरंग प्राप्त होगी। इस प्रकार एक अध्रुवित तरंग के लिए विस्थापन, समय के साथ, यादृच्छिकतः बदलता रहता है, यद्यपि यह सदैव तरंग संचरण की दिशा के लंबवत रहता है।

### 20. ch 10 §10.7 ¶4 — en p16, hi p16, similarity 0.55 — the founder reads this one
- en: Light waves are transverse in nature; i.e., the electric field associated with a propagating light wave is always at right angles to the direction of propagation of the wave. This can be easily demonstrated using a simple polaroid. You must have seen thin plastic like sheets, which are called polaroids. A polaroid consists of long chain molecules aligned in a particular direction. The electric vectors (associated with the propagating light wave) along the direction of the aligned molecules get absorbed. Thus, if an unpolarised light wave is incident on such a polaroid then the light wave will get linearly polarised with the electric vector oscillating along a direction perpendicular to the aligned molecules; this direction is known as the pass-axis of the polaroid.
- hi: प्रकाश की तरंगों की प्रकृति अनुप्रस्थ होती है; अर्थात संचरित हो रही प्रकाश तरंग से संबद्ध विद्युत क्षेत्र सदैव तरंग संचरण की दिशा के लंबवत होता है। इसे एक सरल पोलेरॉइड का उपयोग करके सरलता से प्रदर्शित किया जा सकता है। आपने पतली प्लास्टिक जैसी शीटें देखी होंगी जिन्हें पोलेरॉइड कहते हैं। पोलेरॉइड में अणुओं की एक लंबी शृंखला होती है जो एक विशेष दिशा में पंक्तिबद्ध होते हैं। पंक्तिबद्ध अणुओं की दिशा के अनुदिश विद्युत सदिश (संचरित होती प्रकाश तरंगों से संबद्ध) अवशोषित हो जाता है। इस प्रकार यदि कोई अध्रुवित प्रकाश तरंग ऐसे पोलेरॉइड पर आपतित होती तो प्रकाश तरंग रेखीय ध्रुवित हो जाती है, जिसमें विद्युत सदिश पंक्तिबद्ध अणुओं की लंबवत दिशा के अनुदिश दोलन करता है, इस दिशा को पोलेरॉइड की पारित-अक्ष (pass-axis) कहते हैं।

### 21. ch 11 §11.1 ¶2 — en p2, hi p2, similarity 0.69
- en: Around the same time, in 1887, it was found that certain metals, when irradiated by ultraviolet light, emitted negatively charged particles having small speeds. Also, certain metals when heated to a high temperature were found to emit negatively charged particles. The value of e/m of these particles was found to be the same as that for cathode ray particles. These observations thus established that all these particles, although produced under different conditions, were identical in nature. J. J. Thomson, in 1897, named these particles as electrons, and suggested that they were fundamental, universal constituents of matter. For his epoch-making discovery of electron, through his theoretical and experimental investigations on conduction of electricity by gasses, he was awarded the Nobel Prize in Physics in 1906. In 1913, the American physicist R. A. Millikan (1868-1953) performed the pioneering oil-drop experiment for the precise measurement of the charge on an electron. He found that the charge on an oil-droplet was always an integral multiple of an elementary charge, 1.602 × 10^-19 C. Millikan's experiment established that electric charge is quantised. From the values of charge (e) and specific charge (e/m), the mass (m) of the electron could be determined.
- hi: लगभग उसी समय, 1887 में, यह पाया गया कि जब कुछ निश्चित धातुओं को पराबैंगनी प्रकाश द्वारा किरणित करते हैं तो कम वेग वाले ऋण-आवेशित कण उत्सर्जित होते हैं। इसी प्रकार, जब कुछ निश्चित धातुओं को उच्च ताप तक गरम किया जाता है तो ये ऋण-आवेशित कण उत्सर्जित करते हैं। इन कणों के लिए e/m का मान उतना ही पाया गया जितना कि कैथोड किरण कणों का था। इस प्रकार इन प्रेक्षणों ने यह स्थापित कर दिया कि ये सभी कण, यद्यपि भिन्न दशाओं में उत्पन्न हुए थे, प्रकृति में समान थे। जे.जे. टॉमसन ने, 1897 में, इन कणों को इलेक्ट्रॉन नाम दिया और सुझाया कि ये द्रव्य के मौलिक सार्वत्रिक अवयव हैं। गैसों में विद्युत के संवहन पर उनके सैद्धांतिक तथा प्रायोगिक प्रेक्षणों के द्वारा इलेक्ट्रॉन की इस युगांतकारी खोज के लिए उन्हें 1906 में नोबेल पुरस्कार प्रदान किया गया। 1913 में अमेरिकी भौतिकविज्ञानी आर.ए. मिलिकन (1868–1953) ने इलेक्ट्रॉन पर आवेश के परिशुद्ध मापन के लिए तेल-बूँद का पथ प्रदर्शक प्रयोग किया। उन्होंने यह पाया कि तेल-बिंदुक पर आवेश सदैव एक मूल आवेश, 1.602 × 10^(-19) C का पूर्ण गुणांक है। मिलिकन के प्रयोग ने यह प्रस्थापित कर दिया कि वैद्युतआवेश क्वांटीकृत है। आवेश (e) तथा आपेक्षित आवेश (e/m) के मान से, इलेक्ट्रॉन का द्रव्यमान (m) ज्ञात किया जा सका।

### 22. ch 11 §11.6 ¶1 — en p8, hi p8, similarity 0.64
- en: In 1905, Albert Einstein (1879-1955) proposed a radically new picture of electromagnetic radiation to explain photoelectric effect. In this picture, photoelectric emission does not take place by continuous absorption of energy from radiation. Radiation energy is built up of discrete units – the so called quanta of energy of radiation. Each quantum of radiant energy has energy h nu, where h is Planck's constant and nu the frequency of light. In photoelectric effect, an electron absorbs a quantum of energy (h nu) of radiation. If this quantum of energy absorbed exceeds the minimum energy needed for the electron to escape from the metal surface (work function phi_0), the electron is emitted with maximum kinetic energy K_max = h nu – phi_0 (11.2)
- hi: सन् 1905 में अल्बर्ट आइंसटाइन (1879 – 1955) ने प्रकाश-विद्युत प्रभाव की व्याख्या के लिए वैद्युतचुंबकीय विकिरण का एक मौलिक रूप से नया चित्रण प्रस्तावित किया। इस चित्रण में, प्रकाश-विद्युत उत्सर्जन विकिरण से संतत ऊर्जा-अवशोषण के द्वारा नहीं होता। विकिरण ऊर्जा विविक्त इकाइयों से बनी होती है–जो विकिरण की ऊर्जा के क्वांटा कहलाते हैं। विकिरण ऊर्जा के प्रत्येक क्वांटम की ऊर्जा hnu होती है, जहाँ h प्लांक स्थिरांक है और nu प्रकाश की आवृत्ति। प्रकाश-विद्युत प्रभाव में, एक इलेक्ट्रॉन विकिरण के एक क्वांटम की ऊर्जा (hnu) अवशोषित करता है। यदि ऊर्जा का यह अवशोषित क्वांटम इलेक्ट्रॉन के लिए धातु की सतह से बाहर निकल आने के लिए निम्नतम आवश्यक ऊर्जा से अधिक होता है (कार्य-फलन, phi_0) तब उत्सर्जित इलेक्ट्रॉन की अधिकतम गतिज ऊर्जा होगी : K_उच्च = hnu – phi_0 (11.2)

### 23. ch 12 §12.1 ¶3 — en p1–2, hi p1–2, similarity 0.53 — the founder reads this one
- en: We know that condensed matter (solids and liquids) and dense gases at all temperatures emit electromagnetic radiation in which a continuous distribution of several wavelengths is present, though with different intensities. This radiation is considered to be due to oscillations of atoms and molecules, governed by the interaction of each atom or molecule with its neighbours. In contrast, light emitted from rarefied gases heated in a flame, or excited electrically in a glow tube such as the familiar neon sign or mercury vapour light has only certain discrete wavelengths. The spectrum appears as a series of bright lines. In such gases, the average spacing between atoms is large. Hence, the radiation emitted can be considered due to individual atoms rather than because of interactions between atoms or molecules.
- hi: हम जानते हैं कि संघनित पदार्थ (ठोस तथा द्रव) तथा सघन गैसें सभी तापों पर वैद्युतचुंबकीय विकिरण उत्सर्जित करते हैं जिसमें अनेक तरंगदैर्घ्यों का संतत वितरण विद्यमान होता है यद्यपि उनकी तीव्रताएँ भिन्न होती हैं। यह समझा गया कि यह विकिरण परमाणुओं तथा अणुओं के दोलनों के कारण होता है, जो प्रत्येक परमाणु अथवा अणु का अपने समीप के परमाणुओं या अणुओं के साथ होने वाली अन्योन्य क्रिया से नियंत्रित होता है। इसके विपरीत ज्वाला में गर्म की गई विरलित गैसों द्वारा उत्सर्जित प्रकाश अथवा किसी तापदीप्त नलिका में विद्युत उत्तेजित गैस, जैसे निऑन साइन अथवा पारद-वाष्प प्रकाश में केवल निश्चित विविक्त तरंगदैर्घ्य होती हैं। इनके स्पेक्ट्रम में चमकीली रेखाओं की एक श्रृंखला दिखाई देती है। ऐसी गैसों में परमाणुओं के मध्य अंतराल अधिक होता है। अतः, उत्सर्जित विकिरण, परमाणुओं अथवा अणुओं के बीच अन्योन्य क्रियाओं के परिणामस्वरूप नहीं, बल्कि व्यष्टिगत परमाणुओं के कारण माना जा सकता है।

### 24. ch 12 §12.1 ¶4 — en p2, hi p2, similarity 0.61 — the founder reads this one
- en: In the early nineteenth century it was also established that each element is associated with a characteristic spectrum of radiation, for example, hydrogen always gives a set of lines with fixed relative position between the lines. This fact suggested an intimate relationship between the internal structure of an atom and the spectrum of radiation emitted by it. In 1885, Johann Jakob Balmer (1825 – 1898) obtained a simple empirical formula which gave the wavelengths of a group of lines emitted by atomic hydrogen. Since hydrogen is simplest of the elements known, we shall consider its spectrum in detail in this chapter.
- hi: उन्नीसवीं शताब्दी के प्रारंभ में ही यह स्थापित हो गया था कि प्रत्येक तत्व से उत्सर्जित विकिरण का एक अभिलाक्षणिक स्पेक्ट्रम होता है। उदाहरण के लिए, हाइड्रोजन स्पेक्ट्रम सदैव रेखाओं का एक समुच्चय होता है जिसमें रेखाओं के बीच की आपेक्षिक स्थितियाँ निश्चित होती हैं। इस तथ्य ने किसी परमाणु की आंतरिक संरचना और इससे उत्सर्जित विकिरण के स्पेक्ट्रम के बीच घनिष्ठ संबंध की ओर संकेत किया। सन् 1885 में जान जेकब बामर (1825 – 1898) ने परमाण्वीय हाइड्रोजन से उत्सर्जित रेखाओं के समूह की आवृत्तियों के लिए एक सरल आनुभविक सूत्र प्राप्त किया। चूँकि हाइड्रोजन एक सरलतम ज्ञात तत्व है, हम इसके स्पेक्ट्रम का इस अध्याय में विस्तार से अध्ययन करेंगे।

### 25. ch 12 §12.1 ¶6 — en p2, hi p2, similarity 0.63 — the founder reads this one
- en: Rutherford's nuclear model was a major step towards how we see the atom today. However, it could not explain why atoms emit light of only discrete wavelengths. How could an atom as simple as hydrogen, consisting of a single electron and a single proton, emit a complex spectrum of specific wavelengths? In the classical picture of an atom, the electron revolves round the nucleus much like the way a planet revolves round the sun. However, we shall see that there are some serious difficulties in accepting such a model.
- hi: परमाणु के जिस वर्तमान रूप को हम जानते हैं, रदरफोर्ड का नाभिकीय मॉडल उस दिशा में एक बड़ा कदम था। तथापि इसके द्वारा यह व्याख्या नहीं का जा सकी कि परमाणु केवल विविक्त (discrete) तरंगदैर्घ्य का प्रकाश ही क्यों उत्सर्जित करता है। हाइड्रोजन जैसा एक सरल परमाणु जिसमें एक इलेक्ट्रॉन तथा एक प्रोटॉन होता है, विशेष तरंगदैर्घ्य का एक जटिल स्पेक्ट्रम कैसे उत्सर्जित करता है? परमाणु के क्लासिकी चित्रण में, इलेक्ट्रॉन नाभिक के चारों ओर ठीक ऐसे ही परिक्रमा करता है जैसे कि सूर्य के चारों ओर ग्रह परिक्रमा करते हैं। तथापि, हम देखेंगे कि इस मॉडल को स्वीकार करने में कुछ गंभीर कठिनाइयाँ हैं।

### 26. ch 12 §12.2 ¶2 — en p3–4, hi p3–4, similarity 0.44
- en: A typical graph of the total number of alpha-particles scattered at different angles, in a given interval of time, is shown in Fig. 12.3. The dots in this figure represent the data points and the solid curve is the theoretical prediction based on the assumption that the target atom has a small, dense, positively charged nucleus. Many of the alpha-particles pass through the foil. It means that they do not suffer any collisions. Only about 0.14% of the incident alpha-particles scatter by more than 1°; and about 1 in 8000 deflect by more than 90°. Rutherford argued that, to deflect the alpha-particle backwards, it must experience a large repulsive force. This force could be provided if the greater part of the mass of the atom and its positive charge were concentrated tightly at its centre. Then the incoming alpha-particle could get very close to the positive charge without penetrating it, and such a close encounter would result in a large deflection. This agreement supported the hypothesis of the nuclear atom. This is why Rutherford is credited with the discovery of the nucleus.
- hi: चित्र 12.3 में किसी दिए समयांतराल में विभिन्न कोणों पर प्रकीर्णित कुल ऐल्फ़ा-कणों की संख्या का प्रारूपिक आलेख दर्शाया गया है। इस चित्र में दिखाए गए बिंदु प्रयोग में प्राप्त आँकड़ों को निरूपित करते हैं और संतत वक्र सैद्धांतिक पूर्वानुमान है जो इस कल्पना पर आधारित है कि परमाणु में एक सूक्ष्म सघन तथा धनावेशित नाभिक है। बहुत से ऐल्फ़ा-कण स्वर्ण पन्नी को पार कर जाते हैं। इसका अर्थ है उनमें संघट्टन नहीं होता। आपतित ऐल्फ़ा-कणों में से केवल 0.14% (लगभग) का 1° के कोण से अधिक प्रकीर्णन होता है तथा 8000 ऐल्फ़ा-कणों में से लगभग 1 कण 90° से अधिक विक्षेपित होता है। रदरफोर्ड ने तर्क किया कि ऐल्फ़ा-कणों को विपरीत दिशा में विक्षेपित करने के लिए, इन पर बहुत अधिक प्रतिकर्षण बल लगना चाहिए। इतना अधिक बल तभी प्राप्त हो सकता है यदि परमाणु का अधिकांश द्रव्यमान तथा इसका धन-आवेश इसके केंद्र पर दृढ़ता पूर्वक संकेंद्रित हो। तब अंदर आता हुआ ऐल्फ़ा-कण धन आवेश को भेदे बिना इसके अत्यंत समीप आ सकता है तथा इस प्रकार के समागम के परिणामस्वरूप अधिक विक्षेप होगा। इससे नाभिकीय परमाणु की परिकल्पना की पुष्टि होती है। यही कारण है कि रदरफोर्ड को नाभिक की खोज का श्रेय दिया जाता है।

### 27. ch 12 §12.2 ¶3 — en p4, hi p4, similarity 0.58
- en: In Rutherford's nuclear model of the atom, the entire positive charge and most of the mass of the atom are concentrated in the nucleus with the electrons some distance away. The electrons would be moving in orbits about the nucleus just as the planets do around the sun. Rutherford's experiments suggested the size of the nucleus to be about 10^-15 m to 10^-14 m. From kinetic theory, the size of an atom was known to be 10^-10 m, about 10,000 to 100,000 times larger than the size of the nucleus. Thus, the electrons would seem to be at a distance from the nucleus of about 10,000 to 100,000 times the size of the nucleus itself. Thus, most of an atom is empty space. With the atom being largely empty space, it is easy to see why most alpha-particles go right through a thin metal foil. However, when alpha-particle happens to come near a nucleus, the intense electric field there scatters it through a large angle. The atomic electrons, being so light, do not appreciably affect the alpha-particles.
- hi: रदरफोर्ड के परमाणु के नाभिकीय मॉडल में, परमाणु का कुल धनावेश तथा इसका अधिकांश द्रव्यमान परमाणु के बहुत छोटे से आयतन में संकेंद्रित होता है जिसे नाभिक कहते हैं तथा इलेक्ट्रॉन इससे कुछ दूर होते हैं। इलेक्ट्रॉन, नाभिक के चारों ओर कक्षा में चक्कर लगाते हैं, ठीक ऐसे ही जैसे सूर्य के चारों ओर ग्रह चक्कर लगाते हैं। रदरफोर्ड के प्रयोगों ने सुझाया कि नाभिक का साइज़ लगभग 10^(-15) m से 10^(-14) m हो सकता है। गतिज सिद्धांत के अनुसार परमाणु का साइज़ 10^(-10) m माना जाता है, जो कि नाभिक के साइज़ की अपेक्षा लगभग 10,000 से 100,000 गुना बड़ा है। इस प्रकार, नाभिक से इलेक्ट्रॉन नाभिक के साइज़ की अपेक्षा लगभग 10,000 से 100,000 गुना दूर दिखाई देगा। इस प्रकार, परमाणु के भीतर का अधिकांश भाग खाली है। परमाणु के भीतर का अधिकतर भाग खाली होने के कारण यह समझना आसान है कि अधिकतर ऐल्फ़ा-कण पतली धातु की पन्नी से बिना विक्षेपित हुए बाहर क्यों निकल जाते हैं। तथापि, जब कोई ऐल्फ़ा-कण नाभिक के समीप आता है तो वहाँ पर विद्यमान प्रबल विद्युत बल इसे बड़े कोण से प्रकीर्णित कर देता है। परमाणु के इलेक्ट्रॉन अत्यंत हलके होने के कारण ऐल्फ़ा-कणों पर पर्याप्त प्रभाव नहीं डाल पाते।

### 28. ch 12 §12.4 ¶1 — en p8, hi p8, similarity 0.48 — the founder reads this one
- en: The model of the atom proposed by Rutherford assumes that the atom, consisting of a central nucleus and revolving electron is stable much like sun-planet system which the model imitates. However, there are some fundamental differences between the two situations. While the planetary system is held by gravitational force, the nucleus-electron system being charged objects, interact by Coulomb’s Law of force. We know that an object which moves in a circle is being constantly accelerated – the acceleration being centripetal in nature. According to classical electromagnetic theory, an accelerating charged particle emits radiation in the form of electromagnetic waves. The energy of an accelerating electron should therefore, continuously decrease. The electron would spiral inward and eventually fall into the nucleus (Fig. 12.6). Thus, such an atom can not be stable. Further, according to the classical electromagnetic theory, the frequency of the electromagnetic waves emitted by the revolving electrons is equal to the frequency of revolution. As the electrons spiral inwards, their angular velocities and hence their frequencies would change continuously, and so will the frequency of the light emitted. Thus, they would emit a continuous spectrum, in contradiction to the line spectrum actually observed. Clearly Rutherford model tells only a part of the story implying that the classical ideas are not sufficient to explain the atomic structure.
- hi: परमाणु के रदरफोर्ड द्वारा प्रस्तावित मॉडल में यह मान लिया गया है कि परमाणु के केंद्र में नाभिक होता है तथा परिक्रमा करते इलेक्ट्रॉन स्थिर हैं ठीक वैसे ही जैसा सौर परिवार में होता है जिसका अनुकरण करके इस मॉडल को विकसित किया गया। तथापि, दोनों स्थितियों में कुछ मूलभूत अंतर है। ग्रहीय तंत्र गुरुत्वीय बल के कारण बँधा है, जबकि नाभिक-इलेक्ट्रॉन तंत्र में आवेशित कण होने के कारण, बल के कूलॉम-नियम द्वारा अन्योन्य क्रिया होती है। हम जानते हैं कि वृत्ताकार पथ में घूमती कोई वस्तु लगातार त्वरण में होती है, और इस त्वरण की प्रकृति अभिकेंद्री है। क्लासिकी वैद्युतचुंबकीय सिद्धांत के अनुसार कोई त्वरित आवेशित कण वैद्युतचुंबकीय तरंगों के रूप में विकिरण उत्सर्जित करता है। अतः त्वरित इलेक्ट्रॉन की ऊर्जा निरंतर घटनी चाहिए। इलेक्ट्रॉन अंदर की ओर सर्पिल पथ पर चलेगा तथा अंततः नाभिक में गिर जाएगा (चित्र 12.6)। अतः ऐसा परमाणु स्थायी नहीं हो सकता। इसके अतिरिक्त, क्लासिकी वैद्युतचुंबकीय सिद्धांत के अनुसार परिक्रमी इलेक्ट्रॉनों द्वारा उत्सर्जित वैद्युतचुंबकीय तरंगों की आवृत्ति परिक्रमण-आवृत्ति के बराबर होती है। जब इलेक्ट्रॉन सर्पिल पथ पर अंदर नाभिक की ओर आते हैं तो उनके कोणीय वेग और इस प्रकार उनकी आवृत्तियाँ निरंतर परिवर्तित होंगी। फलस्वरूप उत्सर्जित प्रकाश की आवृत्ति भी निरंतर परिवर्तित होनी चाहिए। अतः इन्हें एक संतत स्पेक्ट्रम उत्सर्जित करना चाहिए जो वास्तव में प्रेक्षित रैखिक स्पेक्ट्रम के विपरीत है। स्पष्टतया रदरफोर्ड का मॉडल केवल तस्वीर का एक पहलू दिखलाता है जिसका अर्थ है कि क्लासिकी विचार परमाणु संरचना की व्याख्या करने के लिए पर्याप्त नहीं है।

### 29. ch 12 §12.4 ¶7 — en p10, hi p10, similarity 0.63
- en: (iii) Bohr's third postulate incorporated into atomic theory the early quantum concepts that had been developed by Planck and Einstein. It states that an electron might make a transition from one of its specified non-radiating orbits to another of lower energy. When it does so, a photon is emitted having energy equal to the energy difference between the initial and final states. The frequency of the emitted photon is then given by h nu = E_i – E_f (12.6) where E_i and E_f are the energies of the initial and final states and E_i > E_f.
- hi: (iii) बोर के तीसरे अभिगृहीत में परमाणु सिद्धांत में प्लांक तथा आइंसटाइन द्वारा विकसित प्रारंभिक क्वांटम परिकल्पनाओं को समाविष्ट किया गया। इसके अनुसार कोई इलेक्ट्रॉन अपने विशेष रूप से उल्लिखित अविकिरणी कक्षा से दूसरी निम्न ऊर्जा वाली कक्षा में संक्रमण कर सकता है। जब यह ऐसा करता है तो एक फोटॉन उत्सर्जित होता है जिसकी ऊर्जा प्रारंभिक एवं अंतिम अवस्थाओं की ऊर्जा के अंतर के बराबर होती है। उत्सर्जित फोटॉन की आवृत्ति निम्न व्यंजक द्वारा दी जाती है : h nu = E_i – E_f (12.6) जहाँ E_i एवं E_f प्रारंभिक और अंतिम अवस्थाओं की ऊर्जाएँ हैं, E_i > E_f।

### 30. ch 12 §12.4 ¶8 — en p10, hi p10, similarity 0.65 — the founder reads this one
- en: For a hydrogen atom, Eq. (12.4) gives the expression to determine the energies of different energy states. But then this equation requires the radius r of the electron orbit. To calculate r, Bohr's second postulate about the angular momentum of the electron–the quantisation condition – is used.
- hi: समीकरण (12.4) में हाइड्रोजन परमाणु के लिए विभिन्न ऊर्जा स्थितियों की ऊर्जाएँ ज्ञात करने का व्यंजक दिया गया है। लेकिन इस समीकरण में इलेक्ट्रॉन कक्ष की त्रिज्या r की आवश्यकता है। r का मान परिकलित करने के लिए, इलेक्ट्रॉन के कोणीय संवेग से संबंधित बोर के दूसरे अभिगृहीत-क्वांटमीकरण प्रतिबंध का प्रयोग करते हैं।

### 31. ch 12 §12.4 ¶9 — en p10, hi p10, similarity 0.71
- en: The radius of n^th possible orbit thus found is r_n = (n^2/m)(h/2pi)^2 (4 pi epsilon_0 / e^2) (12.7)
- hi: इस प्रकार किसी संभावित n^th कक्षा की त्रिज्या प्राप्त होती है r_n = (n^2/m)(h/2pi)^2 4 pi epsilon_0 / e^2 (12.7)

### 32. ch 12 §12.4.1 ¶1 — en p11, hi p11, similarity 0.66
- en: The energy of an atom is the least (largest negative value) when its electron is revolving in an orbit closest to the nucleus i.e., the one for which n = 1. For n = 2, 3, ... the absolute value of the energy E is smaller, hence the energy is progressively larger in the outer orbits. The lowest state of the atom, called the ground state, is that of the lowest energy, with the electron revolving in the orbit of smallest radius, the Bohr radius, a_0. The energy of this state (n = 1), E_1 is –13.6 eV. Therefore, the minimum energy required to free the electron from the ground state of the hydrogen atom is 13.6 eV. It is called the ionisation energy of the hydrogen atom. This prediction of the Bohr's model is in excellent agreement with the experimental value of ionisation energy.
- hi: परमाणु की ऊर्जा उस समय न्यूनतम (अधिकतम ऋणात्मक मान) होती है जब उसका इलेक्ट्रॉन नाभिक की निकटतम कक्षा (अर्थात् n = 1) में परिक्रमण करता है। n = 2, 3 ... के लिए, ऊर्जा E के निरपेक्ष मान कम होते जाते हैं, अतः बाह्य कक्षा की ओर जाने पर कक्षाओं में ऊर्जा अधिक होती जाती है। परमाणु की न्यूनतम अवस्था में जिसे निम्नतम अवस्था कहते हैं इलेक्ट्रॉन की ऊर्जा न्यूनतम होती है, तथा इलेक्ट्रॉन, न्यूनतम त्रिज्या (बोर त्रिज्या a_o) की कक्षा में परिक्रमण करता है। इस अवस्था की ऊर्जा (n = 1), E_1 –13.6 eV है। अतः हाइड्रोजन परमाणु की निम्नतम अवस्था से इलेक्ट्रॉन को मुक्त कराने के लिए आवश्यक न्यूनतम ऊर्जा 13.6 eV है। इसे हाइड्रोजन परमाणु की आयनन ऊर्जा भी कहते हैं। बोर के मॉडल के आधार पर प्राप्त मान आयनन ऊर्जा के प्रायोगिक मानों से उत्तम अनुरूपता रखता है।

### 33. ch 13 §13.2 ¶2 — en p2, hi p2, similarity 0.64
- en: The atomic masses of various elements expressed in atomic mass unit (u) are close to being integral multiples of the mass of a hydrogen atom. There are, however, many striking exceptions to this rule. For example, the atomic mass of chlorine atom is 35.46 u.
- hi: परमाणु द्रव्यमान मात्रक (u) में व्यक्त करने पर विभिन्न तत्वों के परमाणु द्रव्यमान, हाइड्रोजन परमाणु के द्रव्यमान के पूर्ण गुणज के निकट पाए जाते हैं। परंतु इस नियम के अनेक प्रभावशाली अपवाद भी हैं। उदाहरण के लिए, क्लोरीन का परमाणु द्रव्यमान 35.46 u है।

### 34. ch 13 §13.7.1 ¶1 — en p10, hi p10, similarity 0.55 — the founder reads this one
- en: New possibilities emerge when we go beyond natural radioactive decays and study nuclear reactions by bombarding nuclei with other nuclear particles such as proton, neutron, alpha-particle, etc.
- hi: प्राकृतिक रेडियोऐक्टिव क्षयों के अलावा नाभिकों पर अन्य नाभिकीय कणों जैसे प्रोटॉन, न्यूट्रॉन, ऐल्फा कण आदि के प्रकार से होने वाली नाभिकीय प्रक्रियाओं पर ध्यान देने से नई संभावनाएँ बनती हैं।

### 35. ch 13 §13.7.1 ¶5 — en p10, hi p10, similarity 0.58
- en: The fragment products are radioactive nuclei; they emit beta particles in succession to achieve stable end products.
- hi: ये विखंडित उत्पाद रेडियोऐक्टिव नाभिक होते हैं और इनमें तब तक beta-क्षय का क्रम चलता रहता है जब तक कि अंत में स्थायी खंड प्राप्त न हो जाएँ।

### 36. ch 14 §14.2 ¶12 — en p3, hi p3–4, similarity 0.65
- en: At the distance between the atoms in the crystal lattices of Si and Ge, the energy band of these 8N states is split apart into two which are separated by an energy gap E_g (Fig. 14.1). The lower band which is completely occupied by the 4N valence electrons at temperature of absolute zero is the valence band. The other band consisting of 4N energy states, called the conduction band, is completely empty at absolute zero.
- hi: Si तथा Ge के क्रिस्टल जालकों में परमाणुओं के बीच की दूरियों पर, इन 8N स्तरों का ऊर्जा बैंड दो भागों में टूट जाता है, जिनके बीच ऊर्जा अंतराल E_g (चित्र 14.1) का पृथकन होता है। तापक्रम के परम शून्य पर 4N संयोजकता इलेक्ट्रॉनों से पूर्णतः घिरा निम्न बैंड संयोजकता बैंड होता है। अन्य बैंड जिनमें 4N ऊर्जा स्तर होते हैं उन्हें चालन बैंड कहते हैं, तथा यह परम शून्य पर पूर्णतः रिक्त होता है।

### 37. ch 14 §14.3 ¶4 — en p6, hi p6, similarity 0.53 — the founder reads this one
- en: It may be noted that apart from the process of generation of conduction electrons and holes, a simultaneous process of recombination occurs in which the electrons recombine with the holes. At equilibrium, the rate of generation is equal to the rate of recombination of charge carriers. The recombination occurs due to an electron colliding with a hole.
- hi: यहाँ ध्यान देने योग्य बात यह है कि चालन इलेक्ट्रॉनों तथा होलों के उत्पन्न होने के साथ-साथ पुनःसंयोजन का प्रक्रम होता है जिसमें इलेक्ट्रॉन होल के साथ पुनःसंयोजित होते हैं। साम्यावस्था में आवेश वाहकों के उत्पन्न होने की दर उनके पुनःसंयोजन की दर के बराबर होती है। इस पुनःसंयोजन का कारण इलेक्ट्रॉनों का होलों से संघट्ट करना है।

### 38. ch 14 §14.4 ¶14 — en p10, hi p10, similarity 0.79
- en: Example 14.2 Suppose a pure Si crystal has 5 × 10^28 atoms m^-3. It is doped by 1 ppm concentration of pentavalent As. Calculate the number of electrons and holes. Given that n_i =1.5 × 10^16 m^-3.
- hi: उदाहरण 14.2 मान लीजिए किसी शुद्ध Si क्रिस्टल में 5 × 10^28 परमाणु m^-3 है। इसे पंचसंयोजी As से 1 ppm सांद्रता पर अपमिश्रित किया जाता है। इलेक्ट्रॉनों तथा होलों की संख्या परिकलित कीजिए, दिया है कि n_i =1.5 × 10^16 m^-3।

### 39. ch 14 §14.5.1 ¶1 — en p11, hi p11, similarity 0.60
- en: Consider a thin p-type silicon (p-Si) semiconductor wafer. By adding precisely a small quantity of pentavelent impurity, part of the p-Si wafer can be converted into n-Si. There are several processes by which a semiconductor can be formed. The wafer now contains p-region and n-region and a metallurgical junction between p-, and n- region.
- hi: p-प्रकार के सिलिकॉन (p-Si) अर्धचालक की पतली पटलिका (वेफ़र) पर विचार कीजिए। परिशुद्ध रूप में पंचसंयोजक अशुद्धि की अल्प मात्रा मिलाकर किसी p-Si पटलिका के कुछ भाग को n-Si में परिवर्तित किया जा सकता है। किसी अर्धचालक का निर्माण करने की बहुत-सी प्रक्रियाएँ हैं। अब पटलिका में p-क्षेत्र एवं n-क्षेत्र तथा p- तथा n- क्षेत्रों के बीच एक धातुकर्मी संधि है।

### 40. ch 14 §14.6.2 ¶7 — en p15, hi p15, similarity 0.67
- en: The above discussion shows that the p-n junction diode primerly allows the flow of current only in one direction (forward bias). The forward bias resistance is low as compared to the reverse bias resistance. This property is used for rectification of ac voltages as discussed in the next section. For diodes, we define a quantity called dynamic resistance as the ratio of small change in voltage ΔV to a small change in current ΔI: r_d = ΔV / ΔI (14.6)
- hi: ऊपर दी गई विवेचना यह दिखाती है कि p-n डायोड मूल रूप से धारा के प्रवाह को केवल एक ही दिशा में (अग्रदिशिक बायस) प्रतिबंधित करता है। पश्चदिशिक बायस प्रतिरोध की तुलना में अग्रदिशिक बायस प्रतिरोध कम होता है। इस गुण का उपयोग प्रत्यावर्ती (ac) वोल्टता के दिष्टकरण के लिए किया गया है, जिसे अगले अनुभाग में समझाया गया है। डायोडों के लिए हम एक अन्य भौतिक राशि जिसे गतिक प्रतिरोध कहते हैं, को ''वोल्टता में लघु परिवर्तन Delta V तथा विद्युत धारा में लघु परिवर्तन Delta I के अनुपात'' के रूप में परिभाषित करते हैं: r_d = Delta V / Delta I (14.6)

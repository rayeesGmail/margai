# margai-pipeline ncert retrieve rewrite

- run: 2026-10-19 07:57 IST
- input: ../eval/retrieval-queries.json
- sha256: 1e09b094db1e257b9a0afe8651987ad4c6ecd1c025e0010d41b2d04b2860cafb
- read: 40 concept queries — 15 acceptance, 15 baseline, 6 diagnostic
- result: ok

## the corpus searched — every English paragraph carries a vector

| book | subject | with a vector | without |
|---|---|---|---|
| phy11-part1 | physics | 909 | 0 |
| phy11-part2 | physics | 682 | 0 |
| phy12-part1 | physics | 1042 | 0 |
| phy12-part2 | physics | 500 | 0 |
| chem11-part1 | chemistry | 1331 | 0 |
| chem11-part2 | chemistry | 534 | 0 |
| chem12-part1 | chemistry | 657 | 0 |
| chem12-part2 | chemistry | 582 | 0 |
| bio11 | biology | 748 | 0 |
| bio12 | biology | 673 | 0 |
request id: pipeline-ncert-retrieve-fce6a719-3505-45c4-bdee-480e9f7b6c25

## cost

| calls | input tokens | spent |
|---|---|---|
| 130 | 14450 | ₹5.01 |

## D17's tick, fused (founder, DECISIONS 2026-10-19) — MET

the bar on the acceptance set and the held-out line, each non-English query retrieved with its English search version and fused, never the rewrite alone; every miss is diagnosed beside this report before the ✅ is judged
| check | needed | got | met |
|---|---|---|---|
| in the answer set | ≥ 13 of 15 | 15 of 15 | yes |
| every Hindi query | all | 6 of 6 | yes |
| physics | ≥ 4 of 5 | 5 of 5 | yes |
| chemistry | ≥ 4 of 5 | 5 of 5 | yes |
| biology | ≥ 4 of 5 | 5 of 5 | yes |
| held-out queries | ≥ 3 of 4 | 3 of 4 | yes |

## the same checks, unfused (for comparison)

| check | needed | got | met |
|---|---|---|---|
| in the answer set | ≥ 13 of 15 | 12 of 15 | no |
| every Hindi query | all | 4 of 6 | no |
| physics | ≥ 4 of 5 | 4 of 5 | yes |
| chemistry | ≥ 4 of 5 | 3 of 5 | no |
| biology | ≥ 4 of 5 | 5 of 5 | yes |
| held-out queries | ≥ 3 of 4 | 2 of 4 | no |

## acceptance — unfused against fused

| mode | set | queries | in the answer set | hit@1 | hit@3 | MRR |
|---|---|---|---|---|---|---|
| unfused | all | 15 | 12/15 (80%) | 10/15 (67%) | 12/15 (80%) | 0.733 |
| unfused | en | 6 | 6/6 (100%) | 5/6 (83%) | 6/6 (100%) | 0.917 |
| unfused | hi | 6 | 4/6 (67%) | 3/6 (50%) | 4/6 (67%) | 0.583 |
| unfused | hinglish | 3 | 2/3 (67%) | 2/3 (67%) | 2/3 (67%) | 0.667 |
| unfused | physics | 5 | 4/5 (80%) | 2/5 (40%) | 4/5 (80%) | 0.600 |
| unfused | chemistry | 5 | 3/5 (60%) | 3/5 (60%) | 3/5 (60%) | 0.600 |
| unfused | biology | 5 | 5/5 (100%) | 5/5 (100%) | 5/5 (100%) | 1.000 |
| fused | all | 15 | 15/15 (100%) | 11/15 (73%) | 12/15 (80%) | 0.802 |
| fused | en | 6 | 6/6 (100%) | 5/6 (83%) | 6/6 (100%) | 0.917 |
| fused | hi | 6 | 6/6 (100%) | 4/6 (67%) | 4/6 (67%) | 0.742 |
| fused | hinglish | 3 | 3/3 (100%) | 2/3 (67%) | 2/3 (67%) | 0.694 |
| fused | physics | 5 | 5/5 (100%) | 3/5 (60%) | 4/5 (80%) | 0.717 |
| fused | chemistry | 5 | 5/5 (100%) | 3/5 (60%) | 3/5 (60%) | 0.690 |
| fused | biology | 5 | 5/5 (100%) | 5/5 (100%) | 5/5 (100%) | 1.000 |

## baseline — unfused against fused

| mode | set | queries | in the answer set | hit@1 | hit@3 | MRR |
|---|---|---|---|---|---|---|
| unfused | all | 15 | 13/15 (87%) | 9/15 (60%) | 10/15 (67%) | 0.637 |
| unfused | en | 10 | 10/10 (100%) | 6/10 (60%) | 7/10 (70%) | 0.656 |
| unfused | hi | 5 | 3/5 (60%) | 3/5 (60%) | 3/5 (60%) | 0.600 |
| unfused | physics | 15 | 13/15 (87%) | 9/15 (60%) | 10/15 (67%) | 0.637 |
| fused | all | 15 | 15/15 (100%) | 10/15 (67%) | 12/15 (80%) | 0.737 |
| fused | en | 10 | 10/10 (100%) | 6/10 (60%) | 7/10 (70%) | 0.656 |
| fused | hi | 5 | 5/5 (100%) | 4/5 (80%) | 5/5 (100%) | 0.900 |
| fused | physics | 15 | 15/15 (100%) | 10/15 (67%) | 12/15 (80%) | 0.737 |

## diagnostic — unfused against fused

| mode | set | queries | in the answer set | hit@1 | hit@3 | MRR |
|---|---|---|---|---|---|---|
| unfused | all | 6 | 5/6 (83%) | 4/6 (67%) | 5/6 (83%) | 0.750 |
| unfused | en | 5 | 4/5 (80%) | 4/5 (80%) | 4/5 (80%) | 0.800 |
| unfused | hinglish | 1 | 1/1 (100%) | 0/1 (0%) | 1/1 (100%) | 0.500 |
| unfused | chemistry | 6 | 5/6 (83%) | 4/6 (67%) | 5/6 (83%) | 0.750 |
| fused | all | 6 | 5/6 (83%) | 4/6 (67%) | 5/6 (83%) | 0.750 |
| fused | en | 5 | 4/5 (80%) | 4/5 (80%) | 4/5 (80%) | 0.800 |
| fused | hinglish | 1 | 1/1 (100%) | 0/1 (0%) | 1/1 (100%) | 0.500 |
| fused | chemistry | 6 | 5/6 (83%) | 4/6 (67%) | 5/6 (83%) | 0.750 |

## heldout — unfused against fused

| mode | set | queries | in the answer set | hit@1 | hit@3 | MRR |
|---|---|---|---|---|---|---|
| unfused | all | 4 | 2/4 (50%) | 2/4 (50%) | 2/4 (50%) | 0.500 |
| unfused | hi | 2 | 0/2 (0%) | 0/2 (0%) | 0/2 (0%) | 0.000 |
| unfused | hinglish | 2 | 2/2 (100%) | 2/2 (100%) | 2/2 (100%) | 1.000 |
| unfused | physics | 1 | 0/1 (0%) | 0/1 (0%) | 0/1 (0%) | 0.000 |
| unfused | chemistry | 2 | 1/2 (50%) | 1/2 (50%) | 1/2 (50%) | 0.500 |
| unfused | biology | 1 | 1/1 (100%) | 1/1 (100%) | 1/1 (100%) | 1.000 |
| fused | all | 4 | 3/4 (75%) | 2/4 (50%) | 2/4 (50%) | 0.542 |
| fused | hi | 2 | 1/2 (50%) | 0/2 (0%) | 0/2 (0%) | 0.083 |
| fused | hinglish | 2 | 2/2 (100%) | 2/2 (100%) | 2/2 (100%) | 1.000 |
| fused | physics | 1 | 0/1 (0%) | 0/1 (0%) | 0/1 (0%) | 0.000 |
| fused | chemistry | 2 | 2/2 (100%) | 1/2 (50%) | 1/2 (50%) | 0.583 |
| fused | biology | 1 | 1/1 (100%) | 1/1 (100%) | 1/1 (100%) | 1.000 |

## the English acceptance queries rewritten too (founder's diagnostic, not part of the bar)

| mode | set | queries | in the answer set | hit@1 | hit@3 | MRR |
|---|---|---|---|---|---|---|
| unfused | all | 6 | 6/6 (100%) | 5/6 (83%) | 6/6 (100%) | 0.917 |
| unfused | en | 6 | 6/6 (100%) | 5/6 (83%) | 6/6 (100%) | 0.917 |
| unfused | physics | 2 | 2/2 (100%) | 1/2 (50%) | 2/2 (100%) | 0.750 |
| unfused | chemistry | 2 | 2/2 (100%) | 2/2 (100%) | 2/2 (100%) | 1.000 |
| unfused | biology | 2 | 2/2 (100%) | 2/2 (100%) | 2/2 (100%) | 1.000 |
| rewritten and fused | all | 6 | 6/6 (100%) | 6/6 (100%) | 6/6 (100%) | 1.000 |
| rewritten and fused | en | 6 | 6/6 (100%) | 6/6 (100%) | 6/6 (100%) | 1.000 |
| rewritten and fused | physics | 2 | 2/2 (100%) | 2/2 (100%) | 2/2 (100%) | 1.000 |
| rewritten and fused | chemistry | 2 | 2/2 (100%) | 2/2 (100%) | 2/2 (100%) | 1.000 |
| rewritten and fused | biology | 2 | 2/2 (100%) | 2/2 (100%) | 2/2 (100%) | 1.000 |
| id | rank unfused | rank rewritten and fused |
|---|---|---|
| a03-comb-attracts-paper | 2 | 1 |
| a04-optical-fibre | 1 | 1 |
| a07-catalyst-equilibrium | 1 | 1 |
| a08-boiling-point-elevation | 1 | 1 |
| a12-rh-incompatibility | 1 | 1 |
| a13-dna-fingerprinting | 1 | 1 |

## acceptance — no subject filter, unfused (diagnostic)

| set | queries | in the answer set | hit@1 | hit@3 | MRR |
|---|---|---|---|---|---|
| all | 15 | 12/15 (80%) | 10/15 (67%) | 12/15 (80%) | 0.733 |
| en | 6 | 6/6 (100%) | 5/6 (83%) | 6/6 (100%) | 0.917 |
| hi | 6 | 4/6 (67%) | 3/6 (50%) | 4/6 (67%) | 0.583 |
| hinglish | 3 | 2/3 (67%) | 2/3 (67%) | 2/3 (67%) | 0.667 |
| physics | 5 | 4/5 (80%) | 2/5 (40%) | 4/5 (80%) | 0.600 |
| chemistry | 5 | 3/5 (60%) | 3/5 (60%) | 3/5 (60%) | 0.600 |
| biology | 5 | 5/5 (100%) | 5/5 (100%) | 5/5 (100%) | 1.000 |

## the search versions the model wrote

| id | lang | query | search version |
|---|---|---|---|
| q09-moment-of-inertia-hi | hi | किसी वस्तु को घुमाना शुरू करना कितना कठिन होगा यह किन बातों पर निर्भर करता है | factors affecting difficulty of starting rotation of an object moment of inertia |
| q12-dimensional-analysis-hi | hi | विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या नहीं | What dimensional analysis can and cannot tell about an equation |
| q13-inertia-hi | hi | जड़त्व किसे कहते हैं और न्यूटन के पहले नियम से इसका क्या संबंध है | inertia definition and its relation to Newton's first law of motion |
| q14-momentum-conservation-hi | hi | बंदूक से गोली चलाने पर बंदूक पीछे क्यों हटती है | Why does a gun recoil backward when a bullet is fired from it |
| q15-gravity-with-depth-hi | hi | पृथ्वी की सतह से नीचे जाने पर गुरुत्वीय त्वरण का मान कैसे बदलता है | How does acceleration due to gravity vary with depth below Earth's surface |
| a01-photoelectric-stopping-potential | hinglish | sir photoelectric effect me stopping potential frequency badhane se kyu badhta hai, intensity badhane se kyu nahi badhta | photoelectric effect why stopping potential increases with frequency but not with intensity |
| a02-apparent-depth-hi | hi | पानी से भरी बाल्टी की तली ऊपर उठी हुई क्यों दिखती है असली गहराई से कम | Why does the bottom of a water-filled bucket appear raised and shallower than actual depth due to refraction |
| a03-comb-attracts-paper | en | why does a plastic comb pull small pieces of paper after I run it through my dry hair | why does a charged plastic comb attract small pieces of paper after rubbing through dry hair |
| a04-optical-fibre | en | how does light stay inside an optical fibre all the way to the other end | how does total internal reflection keep light inside an optical fibre |
| a05-thermal-expansion-lid-hi | hi | गरम पानी में रखने पर धातु का कसा हुआ ढक्कन आसानी से क्यों खुल जाता है? | Why does a metal lid on a jar open easily when placed in hot water due to thermal expansion |
| a06-sn1-racemisation | hinglish | SN1 reaction me racemisation kyu hota hai matlab product dono forms me kaise aata hai | SN1 reaction mechanism racemisation why product forms both stereoisomers |
| a07-catalyst-equilibrium | en | if catalyst makes the reaction faster then why doesnt the equilibrium shift or K change | why does a catalyst not change the equilibrium constant or shift equilibrium position |
| a08-boiling-point-elevation | en | why does water boil at a higher temperature when sugar is dissolved in it | why does boiling point of water increase when sugar is dissolved in it |
| a09-aufbau-order-hi | hi | परमाणु के कक्षकों में इलेक्ट्रॉन किस क्रम में भरे जाते हैं? | order of filling electrons in atomic orbitals aufbau principle |
| a10-ionisation-enthalpy-trend-hi | hi | आवर्त में बाएँ से दाएँ जाने पर आयनन एन्थैल्पी क्यों बढ़ती है? | Why does ionisation enthalpy increase from left to right across a period? |
| a11-double-fertilisation | hinglish | bhai double fertilisation me ek sperm egg se milta hai to dusra kisse fuse hota hai aur triple fusion ka matlab kya hai | double fertilisation in flowering plants one sperm fuses with egg and second sperm fuses with what triple fusion meaning |
| a12-rh-incompatibility | en | why does the second baby get affected when mother is rh negative and father positive but first baby is fine | Why does Rh incompatibility affect the second baby when mother is Rh negative and father is Rh positive but the first baby is unaffected |
| a13-dna-fingerprinting | en | how can a drop of blood be used to identify a person or find out who the father is | DNA fingerprinting and blood groups for identification and paternity determination |
| a14-light-reactions-site-hi | hi | क्लोरोप्लास्ट में प्रकाश अभिक्रियाएँ कहाँ होती हैं और शर्करा कहाँ बनती है? | Where do light reactions occur in chloroplast and where is sugar synthesized |
| a15-vaccination-hi | hi | टीका लगवाने से शरीर बीमारी से कैसे बचता है? | How does vaccination protect the body from disease through immunology and immune response |
| d06-rosenmund | hinglish | acid chloride ko aldehyde mein kaise badalte hain | conversion of acid chloride to aldehyde mechanism |
| h01-pressure-on-equilibrium-hi | hi | रासायनिक साम्य पर दाब बढ़ाने का क्या असर होता है | effect of increasing pressure on chemical equilibrium |
| h02-carbocation-stability | hinglish | carbocation ki stability tertiary > secondary > primary kyu hoti hai | Why is carbocation stability tertiary greater than secondary greater than primary |
| h03-capacitor-current-leads-hi | hi | प्रत्यावर्ती धारा में संधारित्र वाली धारा वोल्टता से आगे क्यों चलती है | Why does current lead voltage in a capacitor in alternating current |
| h04-okazaki-lagging-strand | hinglish | DNA replication me ek strand continuous aur dusra okazaki fragments me kyu banta hai | Why is one strand of DNA replicated continuously and the other in Okazaki fragments |

## per query

| id | set | subject | lang | rank unfused | rank fused | expected |
|---|---|---|---|---|---|---|
| q01-significant-figures | baseline | physics | en | 3 | 3 | [phy11-part1 ch 1 §1.3.1 ¶2] |
| q02-speed-vs-velocity | baseline | physics | en | 1 | 1 | [phy11-part1 ch 2 §2.2 ¶10] |
| q03-displacement-vs-path | baseline | physics | en | 15 | 15 | [phy11-part1 ch 3 §3.2.1 ¶2] |
| q04-friction-contact-area | baseline | physics | en | 1 | 1 | [phy11-part1 ch 4 §4.9.1 ¶2] |
| q05-inertia-bus | baseline | physics | en | 1 | 1 | [phy11-part1 ch 4 §4.4 ¶8] |
| q06-car-turning-flat-road | baseline | physics | en | 12 | 12 | [phy11-part1 ch 4 §4.10 ¶9, phy11-part1 ch 4 §4.10 ¶18] |
| q07-zero-work | baseline | physics | en | 13 | 13 | [phy11-part1 ch 5 §5.3 ¶7] |
| q08-rotational-kinetic-energy | baseline | physics | en | 1 | 1 | [phy11-part1 ch 6 §6.9 ¶2, phy11-part1 ch 6 §6.9 ¶4, phy11-part1 ch 6 §6.9 ¶6] |
| q09-moment-of-inertia-hi | baseline | physics | hi | miss | 1 | [phy11-part1 ch 6 §6.9 ¶13, phy11-part1 ch 6 §6.9 ¶11] |
| q10-angular-momentum-conservation | baseline | physics | en | 1 | 1 | [phy11-part1 ch 6 §6.12.1 ¶3] |
| q11-escape-speed | baseline | physics | en | 1 | 1 | [phy11-part1 ch 7 §7.8 ¶9, phy11-part1 ch 7 §7.8 ¶6] |
| q12-dimensional-analysis-hi | baseline | physics | hi | miss | 2 | [phy11-part1 ch 1 §1.6.2 ¶9, phy11-part1 ch 1 §1.6 ¶1] |
| q13-inertia-hi | baseline | physics | hi | 1 | 1 | [phy11-part1 ch 4 §4.4 ¶8, phy11-part1 ch 4 §4.4 ¶2] |
| q14-momentum-conservation-hi | baseline | physics | hi | 1 | 1 | [phy11-part1 ch 4 §4.7 ¶1] |
| q15-gravity-with-depth-hi | baseline | physics | hi | 1 | 1 | [phy11-part1 ch 7 §7.6 ¶11] |
| a01-photoelectric-stopping-potential | acceptance | physics | hinglish | miss | 12 | [phy12-part2 ch 11 §11.6 ¶2, phy12-part2 ch 11 §11.6 ¶4, phy12-part2 ch 11 §11.6 ¶8] |
| a02-apparent-depth-hi | acceptance | physics | hi | 2 | 1 | [phy12-part2 ch 9 §9.3 ¶8] |
| a03-comb-attracts-paper | acceptance | physics | en | 2 | 2 | [phy12-part1 ch 1 §1.11 ¶9, phy12-part1 ch 2 §2.9 ¶17] |
| a04-optical-fibre | acceptance | physics | en | 1 | 1 | [phy12-part2 ch 9 §9.4.1 ¶2, phy12-part2 ch 9 §9.4.1 ¶3] |
| a05-thermal-expansion-lid-hi | acceptance | physics | hi | 1 | 1 | [phy11-part2 ch 10 §10.5 ¶1, phy11-part2 ch 10 §10.5 ¶2] |
| a06-sn1-racemisation | acceptance | chemistry | hinglish | 1 | 1 | [chem12-part2 ch 6 §6.7.1 ¶49] |
| a07-catalyst-equilibrium | acceptance | chemistry | en | 1 | 1 | [chem11-part1 ch 6 §6.8.5 ¶1, chem12-part1 ch 3 §3.4.1 ¶5] |
| a08-boiling-point-elevation | acceptance | chemistry | en | 1 | 1 | [chem12-part1 ch 1 §1.6.2 ¶1, chem12-part1 ch 1 §1.6.2 ¶2] |
| a09-aufbau-order-hi | acceptance | chemistry | hi | miss | 4 | [chem11-part1 ch 2 §2.6.4 ¶2, chem11-part1 ch 2 §2.6.4 ¶3] |
| a10-ionisation-enthalpy-trend-hi | acceptance | chemistry | hi | miss | 5 | [chem11-part1 ch 3 §3.7.1 ¶15] |
| a11-double-fertilisation | acceptance | biology | hinglish | 1 | 1 | [bio12 ch 1 §1.3 ¶1] |
| a12-rh-incompatibility | acceptance | biology | en | 1 | 1 | [bio11 ch 15 §15.1.3.2 ¶1] |
| a13-dna-fingerprinting | acceptance | biology | en | 1 | 1 | [bio12 ch 5 §5.10 ¶2, bio12 ch 5 §5.10 ¶5] |
| a14-light-reactions-site-hi | acceptance | biology | hi | 1 | 1 | [bio11 ch 11 §11.3 ¶3] |
| a15-vaccination-hi | acceptance | biology | hi | 1 | 1 | [bio12 ch 7 §7.2.4 ¶1] |
| d01-cannizzaro | diagnostic | chemistry | en | miss | miss | [chem12-part2 ch 8 §8.4 ¶35] |
| d02-aldol | diagnostic | chemistry | en | 1 | 1 | [chem12-part2 ch 8 §8.4 ¶31, chem12-part2 ch 8 §8.4 ¶32] |
| d03-etard | diagnostic | chemistry | en | 1 | 1 | [chem12-part2 ch 8 §8.2.2 ¶7] |
| d04-reimer-tiemann | diagnostic | chemistry | en | 1 | 1 | [chem12-part2 ch 7 §7.4.4 ¶56] |
| d05-gabriel | diagnostic | chemistry | en | 1 | 1 | [chem12-part2 ch 9 §9.4 ¶11] |
| d06-rosenmund | diagnostic | chemistry | hinglish | 2 | 2 | [chem12-part2 ch 8 §8.2.2 ¶1] |
| h01-pressure-on-equilibrium-hi | heldout | chemistry | hi | miss | 6 | [chem11-part1 ch 6 §6.8.2 ¶1, chem11-part1 ch 6 §6.8.2 ¶2] |
| h02-carbocation-stability | heldout | chemistry | hinglish | 1 | 1 | [chem11-part2 ch 8 §8.7.1 ¶4, chem11-part2 ch 8 §8.7.9 ¶3, chem11-part2 ch 8 §8.7.9 ¶4, chem11-part2 ch 8 §8.7.9 ¶10] |
| h03-capacitor-current-leads-hi | heldout | physics | hi | miss | miss | [phy12-part1 ch 7 §7.5 ¶4, phy12-part1 ch 7 §7.5 ¶5, phy12-part1 ch 7 §7.5 ¶7] |
| h04-okazaki-lagging-strand | heldout | biology | hinglish | 1 | 1 | [bio12 ch 5 §5.4.2 ¶2] |

## misses, fused — what was expected, and what came back (2)


**d01-cannizzaro** (diagnostic, chemistry, en) — what do you get when benzaldehyde is heated with concentrated sodium hydroxide
  - expected chem12-part2 ch 8 §8.4 ¶35 · 5. Other reactions (i) Cannizzaro reaction: Aldehydes which do not have an alpha-hydrogen…
  came back:
  1. chem12-part2 ch 7 §7.4.2 ¶3 · both · similarity 0.529 · 2. From benzenesulphonic acid Benzene is sulphonated with oleum and benzene sulphonic aci…
  2. chem11-part2 ch 8 §8.10.2 ¶6 · text · (ii) Kjeldahl's method: The compound containing nitrogen is heated with concentrated sulp…
  3. chem12-part2 ch 6 §6.7.2 ¶8 · vector · similarity 0.500 · Replacement by hydroxyl group Chlorobenzene can be converted into phenol by heating in aq…

**h03-capacitor-current-leads-hi** (heldout, physics, hi) — प्रत्यावर्ती धारा में संधारित्र वाली धारा वोल्टता से आगे क्यों चलती है
  search version: Why does current lead voltage in a capacitor in alternating current
  - expected phy12-part1 ch 7 §7.5 ¶4 · To find the current, we use the relation i = dq / dt i = d/dt (v_m C sin omega t) = omega…
  - expected phy12-part1 ch 7 §7.5 ¶5 · Using the relation, cos(omega t) = sin(omega t + pi/2), we have i = i_m sin(omega t + pi/…
  - expected phy12-part1 ch 7 §7.5 ¶7 · A comparison of Eq. (7.16) with the equation of source voltage, Eq. (7.1) shows that the …
  came back:
  1. phy12-part1 ch 7 §7.3 ¶1 · both · similarity 0.464 · In the previous section, we learnt that the current through a resistor is in phase with t…
  2. phy12-part2 ch 14 §14.7 ¶4 · text · The circuit using two diodes, shown in Fig. 14.19(a), gives output rectified voltage corr…
  3. phy12-part1 ch 7 §7.5 ¶3 · both · similarity 0.491 · When the capacitor is connected to an ac source, as in Fig. 7.7, it limits or regulates t…

## top 3 passages per query, fused


**q01-significant-figures** (en) — if I multiply two measured lengths how many digits should I keep in the answer
  1. phy11-part1 ch 1 §1.3 ¶1 · both · similarity 0.501 · Every measurement involves errors. Thus, the result of measurement should be reported in …
  2. phy11-part1 ch 1 §1.3.3 ¶10 · vector · similarity 0.573 · This example justifies the idea to retain one more extra digit (than the number of digits…
  3. ✅ phy11-part1 ch 1 §1.3.1 ¶2 · vector · similarity 0.522 · (1) In multiplication or division, the final result should retain as many significant fig…

**q02-speed-vs-velocity** (en) — is speed the same thing as velocity or is there a difference
  1. ✅ phy11-part1 ch 2 §2.2 ¶10 · vector · similarity 0.562 · Instantaneous speed or simply speed is the magnitude of velocity. For example, a velocity…
  2. phy11-part2 ch 12 §12.4.2 ¶19 · text · Example 12.7 (a) When a molecule (or an elastic ball) hits a ( massive) wall, it rebounds…
  3. phy11-part1 ch 1 §1.4 ¶5 · vector · similarity 0.516 · Note that in this type of representation, the magnitudes are not considered. It is the qu…

**q03-displacement-vs-path** (en) — if I walk around a park and come back to where I started how much have I moved
  1. phy11-part1 ch 3 §3.7.1 ¶2 · vector · similarity 0.372 · Suppose a particle moves along the curve shown by the thick line and is at P at time t an…
  2. phy11-part1 ch 4 §4.4 ¶8 · text · The property of inertia contained in the First law is evident in many situations. Suppose…
  3. phy12-part1 ch 2 §2.1 ¶9 · vector · similarity 0.370 · (Note here that this displacement is in an opposite sense to the electric force and hence…

**q04-friction-contact-area** (en) — does friction depend on how much surface area is touching
  1. ✅ phy11-part1 ch 4 §4.9.1 ¶2 · both · similarity 0.478 · We know from experience that as the applied force exceeds a certain limit, the body begin…
  2. phy11-part1 ch 4 §4.9.1 ¶14 · both · similarity 0.411 · Rolling friction again has a complex origin, though somewhat different from that of stati…
  3. phy11-part1 ch 4 §4.9.1 ¶4 · vector · similarity 0.418 · Thus, when two bodies are in contact, each experiences a contact force by the other. Fric…

**q05-inertia-bus** (en) — why do I get thrown backwards when a bus suddenly starts moving
  1. ✅ phy11-part1 ch 4 §4.4 ¶8 · both · similarity 0.532 · The property of inertia contained in the First law is evident in many situations. Suppose…
  2. phy12-part2 ch 9 §9.2.3 ¶24 · vector · similarity 0.399 · Although the jogger has been moving with a constant speed, the speed of his/her image app…
  3. phy12-part2 ch 14 §14.4 ¶12 · text · The semiconductor’s energy band structure is affected by doping. In the case of extrinsic…

**q06-car-turning-flat-road** (en) — what stops a car from skidding sideways when it takes a turn on a flat road
  1. phy11-part1 ch 4 §4.10 ¶11 · both · similarity 0.379 · Motion of a car on a banked road We can reduce the contribution of friction to the circul…
  2. phy11-part1 ch 4 §4.9.1 ¶16 · both · similarity 0.364 · In many practical situations, however, friction is critically needed. Kinetic friction th…
  3. phy11-part1 ch 4 §4.10 ¶20 · vector · similarity 0.441 · Answer On a banked road, the horizontal component of the normal force and the frictional …

**q07-zero-work** (en) — if I carry my bag across a flat room have I done any work on it
  1. phy11-part1 ch 5 §5.3 ¶3 · vector · similarity 0.381 · We see that if there is no displacement, there is no work done even if the force is large…
  2. phy12-part1 ch 3 §3.5 ¶4 · text · Because of the drift, there will be net transport of charges across any area perpendicula…
  3. phy11-part1 ch 5 §5.3 ¶5 · vector · similarity 0.367 · (i) the displacement is zero as seen in the example above. A weightlifter holding a 150 k…

**q08-rotational-kinetic-energy** (en) — a spinning wheel is made of many particles so what is its total kinetic energy 1/2 mv^2 for each one
  1. ✅ phy11-part1 ch 6 §6.9 ¶2 · both · similarity 0.598 · where m_i is the mass of the particle. The total kinetic energy K of the body is then giv…
  2. phy11-part1 ch 6 §6.9 ¶1 · both · similarity 0.536 · We have already mentioned that we are developing the study of rotational motion parallel …
  3. phy11-part2 ch 13 §13.7 ¶2 · both · similarity 0.478 · In section 13.5 we have seen that the velocity of a particle executing SHM, is a periodic…

**q09-moment-of-inertia-hi** (hi) — किसी वस्तु को घुमाना शुरू करना कितना कठिन होगा यह किन बातों पर निर्भर करता है
  1. ✅ phy11-part1 ch 6 §6.9 ¶11 · both · similarity 0.406 · As the mass of a body resists a change in its state of linear motion, it is a measure of …
  2. phy11-part2 ch 10 §10.6 ¶1 · vector · similarity 0.363 · Take some water in a vessel and start heating it on a burner. Soon you will notice that b…
  3. phy11-part2 ch 11 §11.11 ¶2 · text · We expect the ideal engine operating between two temperatures to be a reversible engine. …

**q10-angular-momentum-conservation** (en) — why does a skater spin faster when she pulls her arms in
  1. ✅ phy11-part1 ch 6 §6.12.1 ¶3 · both · similarity 0.404 · This then is the required form, for fixed axis rotation, of Eq. (6.29a), which expresses …
  2. phy11-part1 ch 6 §6.1.1 ¶6 · text · In some examples of rotation, however, the axis may not be fixed. A prominent example of …
  3. phy11-part1 ch 4 §4.5 ¶7 · vector · similarity 0.383 · • A seasoned cricketer catches a cricket ball coming in with great speed far more easily …

**q11-escape-speed** (en) — how fast does a rocket have to go to leave the earth for good
  1. ✅ phy11-part1 ch 7 §7.8 ¶6 · vector · similarity 0.463 · The minimum value of V_i corresponds to the case when the L.H.S. of Eq. (7.29) equals zer…
  2. phy11-part2 ch 10 §10.9.1 ¶5 · text · Compare the relatively large thermal conductivities of good thermal conductors and, metal…
  3. phy11-part1 ch 7 §7.8 ¶10 · vector · similarity 0.453 · Equation (7.32) applies equally well to an object thrown from the surface of the moon wit…

**q12-dimensional-analysis-hi** (hi) — विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या नहीं
  1. phy11-part1 ch 1 §1.6.1 ¶5 · both · similarity 0.527 · It may be noted that a test of consistency of dimensions tells us no more and no less tha…
  2. ✅ phy11-part1 ch 1 §1.6.2 ¶9 · both · similarity 0.551 · Dimensional analysis is very useful in deducing relations among the interdependent physic…
  3. phy11-part1 ch 1 §1.6.1 ¶1 · both · similarity 0.477 · The magnitudes of physical quantities may be added together or subtracted from one anothe…

**q13-inertia-hi** (hi) — जड़त्व किसे कहते हैं और न्यूटन के पहले नियम से इसका क्या संबंध है
  1. ✅ phy11-part1 ch 4 §4.4 ¶8 · both · similarity 0.581 · The property of inertia contained in the First law is evident in many situations. Suppose…
  2. ✅ phy11-part1 ch 4 §4.4 ¶2 · both · similarity 0.545 · Newton built on Galileo’s ideas and laid the foundation of mechanics in terms of three la…
  3. phy11-part1 ch 4 §4.6 ¶1 · both · similarity 0.332 · The second law relates the external force on a body to its acceleration. What is the orig…

**q14-momentum-conservation-hi** (hi) — बंदूक से गोली चलाने पर बंदूक पीछे क्यों हटती है
  1. ✅ phy11-part1 ch 4 §4.7 ¶1 · both · similarity 0.483 · The second and third laws of motion lead to an important consequence: the law of conserva…
  2. phy11-part1 ch 4 §4.5 ¶6 · both · similarity 0.383 · • Speed is another important parameter to consider. A bullet fired by a gun can easily pi…
  3. phy11-part1 ch 4 §4.5 ¶20 · both · similarity 0.420 · Answer The retardation ‘a’ of the bullet (assumed constant) is given by a = -u^2 / 2s = (…

**q15-gravity-with-depth-hi** (hi) — पृथ्वी की सतह से नीचे जाने पर गुरुत्वीय त्वरण का मान कैसे बदलता है
  1. ✅ phy11-part1 ch 7 §7.6 ¶11 · both · similarity 0.662 · Thus, as we go down below earth's surface, the acceleration due gravity decreases by a fa…
  2. phy11-part1 ch 2 §2.4 ¶21 · both · similarity 0.537 · Answer An object released near the surface of the Earth is accelerated downward under the…
  3. phy11-part1 ch 7 §7.6 ¶6 · both · similarity 0.470 · Now, consider a point mass m at a depth d below the surface of the earth (Fig. 7.8(b)), s…

**a01-photoelectric-stopping-potential** (hinglish) — sir photoelectric effect me stopping potential frequency badhane se kyu badhta hai, intensity badhane se kyu nahi badhta
  1. phy12-part2 ch 11 §11.4.3 ¶12 · both · similarity 0.649 · (iii) For a given photosensitive material, there exists a certain minimum cut-off frequen…
  2. phy12-part2 ch 11 §11.4.2 ¶4 · both · similarity 0.649 · We can now repeat this experiment with incident radiation of the same frequency but of hi…
  3. phy12-part2 ch 11 §11.4.3 ¶1 · both · similarity 0.580 · We now study the relation between the frequency nu of the incident radiation and the stop…

**a02-apparent-depth-hi** (hi) — पानी से भरी बाल्टी की तली ऊपर उठी हुई क्यों दिखती है असली गहराई से कम
  1. ✅ phy12-part2 ch 9 §9.3 ¶8 · both · similarity 0.529 · Some elementary results based on the laws of refraction follow immediately. For a rectang…
  2. phy11-part2 ch 9 §9.2.2 ¶5 · both · similarity 0.407 · The area of the cylinder is not appearing in the expression of absolute pressure in Eq. (…
  3. phy12-part2 ch 14 §14.4 ¶12 · text · The semiconductor’s energy band structure is affected by doping. In the case of extrinsic…

**a03-comb-attracts-paper** (en) — why does a plastic comb pull small pieces of paper after I run it through my dry hair
  1. phy12-part1 ch 2 §2.9 ¶13 · both · similarity 0.591 · Example 2.7 (a) A comb run through one’s dry hair attracts small bits of paper. Why? What…
  2. ✅ phy12-part1 ch 2 §2.9 ¶17 · both · similarity 0.607 · Solution (a) This is because the comb gets charged by friction. The molecules in the pape…
  3. ✅ phy12-part1 ch 1 §1.11 ¶9 · both · similarity 0.509 · This brings us to a common observation in frictional electricity. A comb run through dry …

**a04-optical-fibre** (en) — how does light stay inside an optical fibre all the way to the other end
  1. ✅ phy12-part2 ch 9 §9.4.1 ¶3 · both · similarity 0.589 · When a signal in the form of light is directed at one end of the fibre at a suitable angl…
  2. phy12-part2 ch 9 §9.4.1 ¶4 · both · similarity 0.452 · A bundle of optical fibres can be put to several uses. Optical fibres are extensively use…
  3. phy12-part2 ch 9 §9.4.1 ¶5 · both · similarity 0.505 · The main requirement in fabricating optical fibres is that there should be very little ab…

**a05-thermal-expansion-lid-hi** (hi) — गरम पानी में रखने पर धातु का कसा हुआ ढक्कन आसानी से क्यों खुल जाता है?
  1. ✅ phy11-part2 ch 10 §10.5 ¶1 · both · similarity 0.589 · You may have observed that sometimes sealed bottles with metallic lids are so tightly scr…
  2. phy11-part2 ch 10 §10.5 ¶3 · vector · similarity 0.420 · If the substance is in the form of a long rod, then for small change in temperature, ΔT, …
  3. phy11-part2 ch 10 §10.5 ¶6 · vector · similarity 0.407 · Table 10.2 gives the values of coefficient of volume expansion of some common substances …

**a06-sn1-racemisation** (hinglish) — SN1 reaction me racemisation kyu hota hai matlab product dono forms me kaise aata hai
  1. ✅ chem12-part2 ch 6 §6.7.1 ¶49 · both · similarity 0.552 · In case of optically active alkyl halides, S_N1 reactions are accompanied by racemisation…
  2. chem11-part2 ch 8 §8.7.2 ¶1 · text · Ions are generally not formed in the reactions of organic compounds. Molecules as such pa…
  3. chem12-part2 ch 6 §6.7.1 ¶46 · vector · similarity 0.499 · Now let us have a fresh look at S_N1 and S_N2 mechanisms by taking examples of optically …

**a07-catalyst-equilibrium** (en) — if catalyst makes the reaction faster then why doesnt the equilibrium shift or K change
  1. ✅ chem12-part1 ch 3 §3.4.1 ¶5 · both · similarity 0.560 · A small amount of the catalyst can catalyse a large amount of reactants. A catalyst does …
  2. chem11-part1 ch 6 §6.8 ¶2 · both · similarity 0.475 · Equilibrium constant, K_c is independent of initial concentrations. But if a system at eq…
  3. ✅ chem11-part1 ch 6 §6.8.5 ¶1 · both · similarity 0.591 · A catalyst increases the rate of the chemical reaction by making available a new low ener…

**a08-boiling-point-elevation** (en) — why does water boil at a higher temperature when sugar is dissolved in it
  1. ✅ chem12-part1 ch 1 §1.6.2 ¶1 · both · similarity 0.504 · The vapour pressure of a liquid increases with increase of temperature. It boils at the t…
  2. chem11-part1 ch 6 §6.1.4 ¶1 · both · similarity 0.460 · Solids in liquids We know from our experience that we can dissolve only a limited amount …
  3. chem12-part1 ch 1 §1.6.2 ¶7 · both · similarity 0.478 · Example 1.7 18 g of glucose, C_6H_12O_6, is dissolved in 1 kg of water in a saucepan. At …

**a09-aufbau-order-hi** (hi) — परमाणु के कक्षकों में इलेक्ट्रॉन किस क्रम में भरे जाते हैं?
  1. chem11-part1 ch 2 §2.6.4 ¶1 · both · similarity 0.684 · The filling of electrons into the orbitals of different atoms takes place according to th…
  2. chem11-part1 ch 2 §2.6.4 ¶4 · both · similarity 0.586 · The order may be remembered by using the method given in Fig. 2.17. Starting from the top…
  3. chem11-part1 ch 2 §2.6.4 ¶5 · both · similarity 0.485 · Pauli Exclusion Principle The number of electrons to be filled in various orbitals is res…

**a10-ionisation-enthalpy-trend-hi** (hi) — आवर्त में बाएँ से दाएँ जाने पर आयनन एन्थैल्पी क्यों बढ़ती है?
  1. chem11-part1 ch 3 §3.7.3 ¶2 · both · similarity 0.578 · The atomic and ionic radii, as we know, generally decrease in a period from left to right…
  2. chem11-part1 ch 3 §3.7.1 ¶21 · both · similarity 0.596 · The variation in electron gain enthalpies of elements is less systematic than for ionizat…
  3. chem11-part1 ch 3 §3.7.1 ¶27 · both · similarity 0.582 · Electronegativity generally increases across a period from left to right (say from lithiu…

**a11-double-fertilisation** (hinglish) — bhai double fertilisation me ek sperm egg se milta hai to dusra kisse fuse hota hai aur triple fusion ka matlab kya hai
  1. ✅ bio12 ch 1 §1.3 ¶1 · both · similarity 0.626 · After entering one of the synergids, the pollen tube releases the two male gametes into t…
  2. bio12 ch 2 §2.5 ¶2 · both · similarity 0.502 · The process of fusion of a sperm with an ovum is called fertilisation. During fertilisati…
  3. bio12 ch 3 §3.5 ¶2 · both · similarity 0.413 · In vitro fertilisation (IVF–fertilisation outside the body in almost similar conditions a…

**a12-rh-incompatibility** (en) — why does the second baby get affected when mother is rh negative and father positive but first baby is fine
  1. ✅ bio11 ch 15 §15.1.3.2 ¶1 · both · similarity 0.531 · Another antigen, the Rh antigen similar to one present in Rhesus monkeys (hence Rh), is a…
  2. bio12 ch 2 §2.5 ¶3 · both · similarity 0.401 · One has to remember that the sex of the baby has been decided at this stage itself. Let u…
  3. bio12 ch 4 §4.8.2 ¶3 · both · similarity 0.363 · Haemophilia : This sex linked recessive disease, which shows its transmission from unaffe…

**a13-dna-fingerprinting** (en) — how can a drop of blood be used to identify a person or find out who the father is
  1. ✅ bio12 ch 5 §5.10 ¶2 · vector · similarity 0.419 · DNA fingerprinting involves identifying differences in some specific regions in DNA seque…
  2. bio12 ch 4 §4.2.2.2 ¶1 · text · Till now we were discussing crosses where the F_1 resembled either of the two parents (do…
  3. bio12 ch 7 §7.4 ¶5 · vector · similarity 0.344 · Antibodies against cancer-specific antigens are also used for detection of certain cancer…

**a14-light-reactions-site-hi** (hi) — क्लोरोप्लास्ट में प्रकाश अभिक्रियाएँ कहाँ होती हैं और शर्करा कहाँ बनती है?
  1. ✅ bio11 ch 11 §11.3 ¶3 · both · similarity 0.584 · You have studied the structure of chloroplast in Chapter 8. Within the chloroplast there …
  2. bio11 ch 11 §11.7 ¶1 · both · similarity 0.482 · We learnt that the products of light reaction are ATP, NADPH and O_2. Of these O_2 diffus…
  3. bio11 ch 11 §11.5 ¶1 · both · similarity 0.461 · Light reactions or the ‘Photochemical’ phase include light absorption, water splitting, o…

**a15-vaccination-hi** (hi) — टीका लगवाने से शरीर बीमारी से कैसे बचता है?
  1. ✅ bio12 ch 7 §7.2.4 ¶1 · both · similarity 0.515 · The principle of immunisation or vaccination is based on the property of ‘memory’ of the …
  2. bio12 ch 7 §7.2.3 ¶1 · both · similarity 0.399 · When a host is exposed to antigens, which may be in the form of living or dead microbes o…
  3. bio12 ch 7 §7.2 ¶1 · both · similarity 0.384 · Everyday we are exposed to large number of infectious agents. However, only a few of thes…

**d01-cannizzaro** (en) — what do you get when benzaldehyde is heated with concentrated sodium hydroxide
  1. chem12-part2 ch 7 §7.4.2 ¶3 · both · similarity 0.529 · 2. From benzenesulphonic acid Benzene is sulphonated with oleum and benzene sulphonic aci…
  2. chem11-part2 ch 8 §8.10.2 ¶6 · text · (ii) Kjeldahl's method: The compound containing nitrogen is heated with concentrated sulp…
  3. chem12-part2 ch 6 §6.7.2 ¶8 · vector · similarity 0.500 · Replacement by hydroxyl group Chlorobenzene can be converted into phenol by heating in aq…

**d02-aldol** (en) — what is formed when ethanal is treated with dilute alkali
  1. ✅ chem12-part2 ch 8 §8.4 ¶31 · both · similarity 0.438 · (i) Aldol condensation: Aldehydes and ketones having at least one alpha-hydrogen undergo …
  2. chem11-part2 ch 9 §9.4.3 ¶2 · vector · similarity 0.443 · 2. From vicinal dihalides : Vicinal dihalides on treatment with alcoholic potassium hydro…
  3. chem12-part2 ch 7 §7.4.2 ¶4 · text · 3. From diazonium salts A diazonium salt is formed by treating an aromatic primary amine …

**d03-etard** (en) — how can toluene be turned into benzaldehyde with chromyl chloride
  1. ✅ chem12-part2 ch 8 §8.2.2 ¶7 · both · similarity 0.564 · (a) Use of chromyl chloride (CrO_2Cl_2): Chromyl chloride oxidises methyl group to a chro…
  2. chem12-part2 ch 8 §8.2.2 ¶9 · both · similarity 0.584 · (ii) By side chain chlorination followed by hydrolysis Side chain chlorination of toluene…
  3. chem12-part2 ch 8 §8.2.2 ¶8 · both · similarity 0.627 · (b) Use of chromic oxide (CrO_3): Toluene or substituted toluene is converted to benzylid…

**d04-reimer-tiemann** (en) — what product forms when phenol is treated with chloroform and sodium hydroxide
  1. ✅ chem12-part2 ch 7 §7.4.4 ¶56 · both · similarity 0.612 · 3. Reimer-Tiemann reaction On treating phenol with chloroform in the presence of sodium h…
  2. chem12-part2 ch 7 §7.4.4 ¶55 · both · similarity 0.545 · 2. Kolbe’s reaction Phenoxide ion generated by treating phenol with sodium hydroxide is e…
  3. chem12-part2 ch 7 §7.4.4 ¶7 · both · similarity 0.593 · In addition to this, phenols react with aqueous sodium hydroxide to form sodium phenoxide…

**d05-gabriel** (en) — how is a primary amine made from phthalimide
  1. ✅ chem12-part2 ch 9 §9.4 ¶11 · both · similarity 0.647 · 5. Gabriel phthalimide synthesis Gabriel synthesis is used for the preparation of primary…
  2. chem12-part2 ch 9 §9.6 ¶23 · both · similarity 0.461 · 4. Carbylamine reaction Aliphatic and aromatic primary amines on heating with chloroform …
  3. chem12-part2 ch 9 §9.4 ¶5 · both · similarity 0.399 · Ammonolysis has the disadvantage of yielding a mixture of primary, secondary and tertiary…

**d06-rosenmund** (hinglish) — acid chloride ko aldehyde mein kaise badalte hain
  1. chem12-part2 ch 8 §8.2.2 ¶2 · both · similarity 0.467 · 2. From nitriles and esters Nitriles are reduced to corresponding imine with stannous chl…
  2. ✅ chem12-part2 ch 8 §8.2.2 ¶1 · both · similarity 0.493 · 1. From acyl chloride (acid chloride) Acyl chloride (acid chloride) is hydrogenated over …
  3. chem12-part2 ch 8 §8.4 ¶14 · text · Dry hydrogen chloride protonates the oxygen of the carbonyl compounds and therefore, incr…

**h01-pressure-on-equilibrium-hi** (hi) — रासायनिक साम्य पर दाब बढ़ाने का क्या असर होता है
  1. chem11-part1 ch 6 §6.8 ¶2 · both · similarity 0.459 · Equilibrium constant, K_c is independent of initial concentrations. But if a system at eq…
  2. chem11-part1 ch 6 §6.1.2 ¶2 · both · similarity 0.498 · At equilibrium the pressure exerted by the water molecules at a given temperature remains…
  3. chem11-part1 ch 5 §5.1.4 ¶10 · vector · similarity 0.322 · By conventions of IUPAC in chemical thermodynamics. The positive sign expresses that w_ad…

**h02-carbocation-stability** (hinglish) — carbocation ki stability tertiary > secondary > primary kyu hoti hai
  1. ✅ chem11-part2 ch 8 §8.7.1 ¶4 · both · similarity 0.560 · A species having a carbon atom possessing sextext of electrons and a positive charge is c…
  2. chem12-part2 ch 6 §6.7.1 ¶25 · both · similarity 0.505 · Of the two primary bromides, the carbocation intermediate derived from (CH_3)_2CHCH_2Br i…
  3. chem11-part2 ch 8 §8.7.1 ¶6 · both · similarity 0.474 · Alkyl radicals are classified as primary, secondary, or tertiary. Alkyl radical stability…

**h03-capacitor-current-leads-hi** (hi) — प्रत्यावर्ती धारा में संधारित्र वाली धारा वोल्टता से आगे क्यों चलती है
  1. phy12-part1 ch 7 §7.3 ¶1 · both · similarity 0.464 · In the previous section, we learnt that the current through a resistor is in phase with t…
  2. phy12-part2 ch 14 §14.7 ¶4 · text · The circuit using two diodes, shown in Fig. 14.19(a), gives output rectified voltage corr…
  3. phy12-part1 ch 7 §7.5 ¶3 · both · similarity 0.491 · When the capacitor is connected to an ac source, as in Fig. 7.7, it limits or regulates t…

**h04-okazaki-lagging-strand** (hinglish) — DNA replication me ek strand continuous aur dusra okazaki fragments me kyu banta hai
  1. ✅ bio12 ch 5 §5.4.2 ¶2 · both · similarity 0.516 · In addition to DNA-dependent DNA polymerases, many additional enzymes are required to com…
  2. bio12 ch 5 §5.4 ¶3 · both · similarity 0.402 · The scheme suggested that the two strands would separate and act as a template for the sy…
  3. bio12 ch 5 §5.5 ¶1 · both · similarity 0.410 · The process of copying genetic information from one strand of the DNA into RNA is termed …

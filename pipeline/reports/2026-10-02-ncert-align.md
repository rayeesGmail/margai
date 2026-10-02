# margai-pipeline ncert align

- run: 2026-10-02 11:58 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 909 English and 827 Hindi row(s) in 7 chapter(s)
- result: ok
request id: pipeline-ncert-align-d60dae2b-4cb4-4b2f-b083-bea75dac8c01
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 827 | 256176 | ₹8.50 |

## sections whose rows do not pair one to one

| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 1 | 1.2 | 8 | 7 | 1 |
| 1 | 1.3 | 21 | 13 | 8 |
| 1 | 1.3.3 | 10 | 9 | 1 |
| 1 | 1.4 | 5 | 4 | 1 |
| 1 | 1.6.1 | 11 | 8 | 3 |
| 1 | 1.6.2 | 10 | 6 | 4 |
| 2 | 2.2 | 10 | 9 | 1 |
| 2 | 2.3 | 11 | 12 | 1 |
| 2 | 2.4 | 33 | 39 | 12 |
| 3 | 3.3 | 5 | 3 | 2 |
| 3 | 3.4 | 10 | 6 | 4 |
| 3 | 3.5 | 12 | 13 | 7 |
| 3 | 3.6 | 11 | 9 | 6 |
| 3 | 3.7.1 | 17 | 17 | 4 |
| 3 | 3.8 | 5 | 7 | 2 |
| 3 | 3.9 | 19 | 21 | 6 |
| 3 | 3.10 | 12 | 9 | 5 |
| 4 | 4.3 | 6 | 5 | 1 |
| 4 | 4.4 | 10 | 8 | 2 |
| 4 | 4.5 | 30 | 26 | 6 |
| 4 | 4.6 | 13 | 13 | 4 |
| 4 | 4.8 | 10 | 6 | 4 |
| 4 | 4.9 | 5 | 5 | 2 |
| 4 | 4.9.1 | 16 | 15 | 3 |
| 4 | 4.10 | 20 | 12 | 12 |
| 4 | 4.11 | 18 | 11 | 7 |
| 5 | 5.1.1 | 9 | 6 | 3 |
| 5 | 5.2 | 8 | 6 | 2 |
| 5 | 5.3 | 14 | 13 | 1 |
| 5 | 5.4 | 5 | 4 | 1 |
| 5 | 5.5 | 8 | 8 | 2 |
| 5 | 5.6 | 5 | 4 | 1 |
| 5 | 5.7 | 7 | 5 | 2 |
| 5 | 5.8 | 16 | 18 | 6 |
| 5 | 5.9 | 19 | 19 | 4 |
| 5 | 5.10 | 10 | 7 | 3 |
| 5 | 5.11 | 3 | 2 | 1 |
| 5 | 5.11.1 | 3 | 2 | 1 |
| 5 | 5.11.2 | 13 | 10 | 3 |
| 5 | 5.11.3 | 7 | 9 | 4 |
| 6 | 6.1.1 | 9 | 8 | 1 |
| 6 | 6.2 | 33 | 29 | 4 |
| 6 | 6.3 | 12 | 12 | 2 |
| 6 | 6.4 | 14 | 17 | 3 |
| 6 | 6.5 | 27 | 23 | 10 |
| 6 | 6.6 | 16 | 15 | 3 |
| 6 | 6.7.2 | 29 | 25 | 4 |
| 6 | 6.8 | 21 | 22 | 3 |
| 6 | 6.8.2 | 21 | 19 | 6 |
| 6 | 6.9 | 15 | 15 | 8 |
| 6 | 6.10 | 15 | 11 | 4 |
| 6 | 6.11 | 28 | 23 | 7 |
| 6 | 6.12 | 19 | 17 | 4 |
| 7 | 7.3 | 17 | 15 | 6 |
| 7 | 7.6 | 11 | 10 | 5 |
| 7 | 7.7 | 12 | 13 | 3 |
| 7 | 7.8 | 16 | 16 | 2 |
| 7 | 7.9 | 19 | 21 | 4 |
| 7 | 7.10 | 9 | 9 | 2 |

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 1 §1.2: hi ¶6 = en ¶6+¶7 (split hi)
- ch 1 §1.3: hi ¶3 = en ¶3+¶4 (split hi)
- ch 1 §1.3: en ¶5 has no hindi
- ch 1 §1.3: en ¶6 has no hindi
- ch 1 §1.3: en ¶7 has no hindi
- ch 1 §1.3: hi ¶4 = en ¶8+¶9 (split hi)
- ch 1 §1.3: hi ¶5 = en ¶10+¶11 (split hi)
- ch 1 §1.3: hi ¶11 = en ¶17+¶18 (split hi)
- ch 1 §1.3: hi ¶12 = en ¶19+¶20 (split hi)
- ch 1 §1.3.3: hi ¶5 = en ¶5+¶6 (split hi)
- ch 1 §1.4: hi ¶3 = en ¶3+¶4 (split hi)
- ch 1 §1.6.1: hi ¶3 = en ¶3+¶4 (split hi)
- ch 1 §1.6.1: hi ¶6 = en ¶7+¶8 (split hi)
- ch 1 §1.6.1: hi ¶7 = en ¶9+¶10 (split hi)
- ch 1 §1.6.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 1 §1.6.2: en ¶4 has no hindi
- ch 1 §1.6.2: hi ¶3 = en ¶5+¶6 (split hi)
- ch 1 §1.6.2: hi ¶4 = en ¶7+¶8 (split hi)
- ch 2 §2.2: hi ¶3 = en ¶3+¶4 (split hi)
- ch 2 §2.3: hi ¶6+¶7 = en ¶6 (join hi)
- ch 2 §2.4: hi ¶2 has no english
- ch 2 §2.4: hi ¶3 has no english
- ch 2 §2.4: hi ¶4+¶5 = en ¶2 (join hi)
- ch 2 §2.4: hi ¶6+¶7 = en ¶3 (join hi)
- ch 2 §2.4: hi ¶8+¶9 = en ¶4 (join hi)
- ch 2 §2.4: hi ¶12+¶13 = en ¶7 (join hi)
- ch 2 §2.4: hi ¶17+¶18 = en ¶11 (join hi)
- ch 2 §2.4: hi ¶21+¶22 = en ¶14 (join hi)
- ch 2 §2.4: hi ¶23 = en ¶15+¶16 (split hi)
- ch 2 §2.4: hi ¶28+¶29 = en ¶21 (join hi)
- ch 2 §2.4: hi ¶30 = en ¶22+¶23 (split hi)
- ch 2 §2.4: hi ¶38 = en ¶31+¶32 (split hi)
- ch 3 §3.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 3 §3.3: hi ¶2 = en ¶3+¶4 (split hi)
- ch 3 §3.4: hi ¶1 = en ¶1+¶2 (split hi)
- ch 3 §3.4: en ¶3 has no hindi
- ch 3 §3.4: en ¶4 has no hindi
- ch 3 §3.4: hi ¶3 = en ¶6+¶7 (split hi)
- ch 3 §3.5: hi ¶1+¶2 = en ¶1 (join hi)
- ch 3 §3.5: hi ¶3 = en ¶2+¶3 (split hi)
- ch 3 §3.5: hi ¶4+¶5 = en ¶4 (join hi)
- ch 3 §3.5: hi ¶8 = en ¶7+¶8 (split hi)
- ch 3 §3.5: hi ¶9 = en ¶9+¶10 (split hi)
- ch 3 §3.5: hi ¶10+¶11 = en ¶11 (join hi)
- ch 3 §3.5: hi ¶12+¶13 = en ¶12 (join hi)
- ch 3 §3.6: hi ¶1 = en ¶1+¶2 (split hi)
- ch 3 §3.6: en ¶3 has no hindi
- ch 3 §3.6: hi ¶2 = en ¶4+¶5 (split hi)
- ch 3 §3.6: hi ¶5+¶6 = en ¶8 (join hi)
- ch 3 §3.6: hi ¶7 = en ¶9+¶10 (split hi)
- ch 3 §3.6: hi ¶8+¶9 = en ¶11 (join hi)
- ch 3 §3.7.1: hi ¶4+¶5 = en ¶4 (join hi)
- ch 3 §3.7.1: hi ¶6 = en ¶5+¶6 (split hi)
- ch 3 §3.7.1: hi ¶9+¶10 = en ¶9 (join hi)
- ch 3 §3.7.1: hi ¶17 = en ¶16+¶17 (split hi)
- ch 3 §3.8: hi ¶1+¶2 = en ¶1 (join hi)
- ch 3 §3.8: hi ¶3+¶4 = en ¶2 (join hi)
- ch 3 §3.9: hi ¶1+¶2 = en ¶1 (join hi)
- ch 3 §3.9: hi ¶7 = en ¶6+¶7 (split hi)
- ch 3 §3.9: hi ¶9 has no english
- ch 3 §3.9: hi ¶11 has no english
- ch 3 §3.9: hi ¶15+¶16 = en ¶13 (join hi)
- ch 3 §3.9: hi ¶21 = en ¶18+¶19 (split hi)
- ch 3 §3.10: hi ¶3 = en ¶3+¶4 (split hi)
- ch 3 §3.10: hi ¶4+¶5 = en ¶5 (join hi)
- ch 3 §3.10: hi ¶6 = en ¶6+¶7 (split hi)
- ch 3 §3.10: hi ¶7 = en ¶8+¶9 (split hi)
- ch 3 §3.10: hi ¶9 = en ¶11+¶12 (split hi)
- ch 4 §4.3: hi ¶3 = en ¶3+¶4 (split hi)
- ch 4 §4.4: hi ¶2 = en ¶2+¶3 (split hi)
- ch 4 §4.4: hi ¶3 = en ¶4+¶5 (split hi)
- ch 4 §4.5: hi ¶2 = en ¶2+¶3 (split hi)
- ch 4 §4.5: hi ¶13 = en ¶14+¶15 (split hi)
- ch 4 §4.5: hi ¶17 = en ¶19+¶20 (split hi)
- ch 4 §4.5: hi ¶18 = en ¶21+¶22 (split hi)
- ch 4 §4.5: hi ¶20 = en ¶24+¶25 (split hi)
- ch 4 §4.5: hi ¶21 has no english
- ch 4 §4.6: hi ¶2 = en ¶2+¶3 (split hi)
- ch 4 §4.6: hi ¶4 = en ¶5+¶6 (split hi)
- ch 4 §4.6: hi ¶9+¶10 = en ¶11 (join hi)
- ch 4 §4.6: hi ¶11+¶12 = en ¶12 (join hi)
- ch 4 §4.8: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.8: en ¶5 has no hindi
- ch 4 §4.8: hi ¶6 = en ¶8+¶9 (split hi)
- ch 4 §4.8: en ¶10 has no hindi
- ch 4 §4.9: hi ¶4 has no english
- ch 4 §4.9: hi ¶5 = en ¶4+¶5 (split hi)
- ch 4 §4.9.1: hi ¶2+¶3 = en ¶2 (join hi)
- ch 4 §4.9.1: hi ¶8 = en ¶7+¶8 (split hi)
- ch 4 §4.9.1: hi ¶9 = en ¶9+¶10 (split hi)
- ch 4 §4.10: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.10: hi ¶3 = en ¶4+¶5 (split hi)
- ch 4 §4.10: en ¶6 has no hindi
- ch 4 §4.10: en ¶7 has no hindi
- ch 4 §4.10: en ¶8 has no hindi
- ch 4 §4.10: hi ¶4 = en ¶9+¶10 (split hi)
- ch 4 §4.10: hi ¶5 = en ¶11+¶12 (split hi)
- ch 4 §4.10: en ¶13 has no hindi
- ch 4 §4.10: en ¶14 has no hindi
- ch 4 §4.10: hi ¶6 = en ¶15+¶16 (split hi)
- ch 4 §4.10: hi ¶8+¶9 = en ¶18 (join hi)
- ch 4 §4.10: hi ¶11+¶12 = en ¶20 (join hi)
- ch 4 §4.11: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.11: hi ¶2 = en ¶3+¶4 (split hi)
- ch 4 §4.11: en ¶9 has no hindi
- ch 4 §4.11: hi ¶8 = en ¶11+¶12 (split hi)
- ch 4 §4.11: en ¶13 has no hindi
- ch 4 §4.11: hi ¶9 = en ¶14+¶15 (split hi)
- ch 4 §4.11: hi ¶10 = en ¶16+¶17 (split hi)
- ch 5 §5.1.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.1.1: hi ¶4 = en ¶5+¶6 (split hi)
- ch 5 §5.1.1: en ¶7 has no hindi
- ch 5 §5.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.2: hi ¶5 = en ¶6+¶7 (split hi)
- ch 5 §5.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.4: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.5: hi ¶2 = en ¶2+¶3 (split hi)
- ch 5 §5.5: hi ¶5 has no english
- ch 5 §5.6: hi ¶4 = en ¶4+¶5 (split hi)
- ch 5 §5.7: hi ¶4 = en ¶4+¶5 (split hi)
- ch 5 §5.7: hi ¶5 = en ¶6+¶7 (split hi)
- ch 5 §5.8: hi ¶4 = en ¶4+¶5 (split hi)
- ch 5 §5.8: hi ¶6 = en ¶7+¶8 (split hi)
- ch 5 §5.8: hi ¶7+¶8 = en ¶9 (join hi)
- ch 5 §5.8: hi ¶9 has no english
- ch 5 §5.8: hi ¶10 has no english
- ch 5 §5.8: hi ¶14+¶15 = en ¶13 (join hi)
- ch 5 §5.9: hi ¶2 = en ¶2+¶3 (split hi)
- ch 5 §5.9: hi ¶5 = en ¶6+¶7 (split hi)
- ch 5 §5.9: hi ¶6+¶7 = en ¶8 (join hi)
- ch 5 §5.9: hi ¶8 has no english
- ch 5 §5.10: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.10: hi ¶2 = en ¶3+¶4 (split hi)
- ch 5 §5.10: hi ¶5 = en ¶7+¶8 (split hi)
- ch 5 §5.11: hi ¶2 = en ¶2+¶3 (split hi)
- ch 5 §5.11.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.11.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.11.2: hi ¶8 = en ¶9+¶10 (split hi)
- ch 5 §5.11.2: hi ¶9 = en ¶11+¶12 (split hi)
- ch 5 §5.11.3: hi ¶1+¶2 = en ¶1 (join hi)
- ch 5 §5.11.3: hi ¶3 = en ¶2+¶3 (split hi)
- ch 5 §5.11.3: hi ¶5+¶6 = en ¶5 (join hi)
- ch 5 §5.11.3: hi ¶7 has no english
- ch 6 §6.1.1: hi ¶6 = en ¶6+¶7 (split hi)
- ch 6 §6.2: hi ¶5 = en ¶5+¶6 (split hi)
- ch 6 §6.2: hi ¶6 = en ¶7+¶8 (split hi)
- ch 6 §6.2: hi ¶9 = en ¶11+¶12 (split hi)
- ch 6 §6.2: hi ¶21 = en ¶24+¶25 (split hi)
- ch 6 §6.3: hi ¶2+¶3 = en ¶2 (join hi)
- ch 6 §6.3: hi ¶4 = en ¶3+¶4 (split hi)
- ch 6 §6.4: hi ¶3+¶4 = en ¶3 (join hi)
- ch 6 §6.4: hi ¶5+¶6 = en ¶4 (join hi)
- ch 6 §6.4: hi ¶7 has no english
- ch 6 §6.5: hi ¶3 = en ¶3+¶4 (split hi)
- ch 6 §6.5: hi ¶4 = en ¶5+¶6 (split hi)
- ch 6 §6.5: hi ¶5 = en ¶7+¶8 (split hi)
- ch 6 §6.5: hi ¶8 = en ¶11+¶12 (split hi)
- ch 6 §6.5: hi ¶10 = en ¶14+¶15 (split hi)
- ch 6 §6.5: hi ¶15 = en ¶20+¶21 (split hi)
- ch 6 §6.5: hi ¶16+¶17 = en ¶22 (join hi)
- ch 6 §6.5: hi ¶19 = en ¶24+¶25 (split hi)
- ch 6 §6.5: hi ¶20+¶21 = en ¶26 (join hi)
- ch 6 §6.5: hi ¶22+¶23 = en ¶27 (join hi)
- ch 6 §6.6: hi ¶9 = en ¶9+¶10 (split hi)
- ch 6 §6.6: hi ¶11+¶12 = en ¶12 (join hi)
- ch 6 §6.6: hi ¶13 = en ¶13+¶14 (split hi)
- ch 6 §6.7.2: hi ¶3 = en ¶3+¶4 (split hi)
- ch 6 §6.7.2: hi ¶4 = en ¶5+¶6 (split hi)
- ch 6 §6.7.2: hi ¶5 = en ¶7+¶8 (split hi)
- ch 6 §6.7.2: hi ¶24 = en ¶27+¶28 (split hi)
- ch 6 §6.8: hi ¶4 = en ¶4+¶5 (split hi)
- ch 6 §6.8: hi ¶8+¶9 = en ¶9 (join hi)
- ch 6 §6.8: hi ¶10 has no english
- ch 6 §6.8.2: hi ¶11+¶12 = en ¶11 (join hi)
- ch 6 §6.8.2: hi ¶14 = en ¶13+¶14 (split hi)
- ch 6 §6.8.2: hi ¶15 = en ¶15+¶16 (split hi)
- ch 6 §6.8.2: hi ¶16 has no english
- ch 6 §6.8.2: hi ¶18 = en ¶18+¶19 (split hi)
- ch 6 §6.8.2: hi ¶19 = en ¶20+¶21 (split hi)
- ch 6 §6.9: hi ¶1+¶2 = en ¶1 (join hi)
- ch 6 §6.9: hi ¶3 = en ¶2+¶3 (split hi)
- ch 6 §6.9: hi ¶4+¶5 = en ¶4 (join hi)
- ch 6 §6.9: hi ¶7 = en ¶6+¶7 (split hi)
- ch 6 §6.9: hi ¶9+¶10 = en ¶9 (join hi)
- ch 6 §6.9: hi ¶11+¶12 = en ¶10 (join hi)
- ch 6 §6.9: hi ¶14 = en ¶12+¶13 (split hi)
- ch 6 §6.9: en ¶14 has no hindi
- ch 6 §6.10: hi ¶4 = en ¶4+¶5 (split hi)
- ch 6 §6.10: hi ¶5 = en ¶6+¶7 (split hi)
- ch 6 §6.10: en ¶12 has no hindi
- ch 6 §6.10: hi ¶10 = en ¶13+¶14 (split hi)
- ch 6 §6.11: hi ¶4+¶5 = en ¶4 (join hi)
- ch 6 §6.11: hi ¶9 = en ¶8+¶9 (split hi)
- ch 6 §6.11: hi ¶19 = en ¶19+¶20 (split hi)
- ch 6 §6.11: en ¶21 has no hindi
- ch 6 §6.11: en ¶22 has no hindi
- ch 6 §6.11: en ¶23 has no hindi
- ch 6 §6.11: en ¶24 has no hindi
- ch 6 §6.12: hi ¶4+¶5 = en ¶4 (join hi)
- ch 6 §6.12: hi ¶6 = en ¶5+¶6 (split hi)
- ch 6 §6.12: hi ¶16 = en ¶16+¶17 (split hi)
- ch 6 §6.12: en ¶18 has no hindi
- ch 7 §7.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 7 §7.3: hi ¶4+¶5 = en ¶5 (join hi)
- ch 7 §7.3: hi ¶7 = en ¶7+¶8 (split hi)
- ch 7 §7.3: hi ¶8 = en ¶9+¶10 (split hi)
- ch 7 §7.3: hi ¶9 = en ¶11+¶12 (split hi)
- ch 7 §7.3: hi ¶14+¶15 = en ¶17 (join hi)
- ch 7 §7.6: hi ¶1 = en ¶1+¶2 (split hi)
- ch 7 §7.6: hi ¶2+¶3 = en ¶3 (join hi)
- ch 7 §7.6: hi ¶4 = en ¶4+¶5 (split hi)
- ch 7 §7.6: hi ¶7+¶8 = en ¶8 (join hi)
- ch 7 §7.6: hi ¶9 = en ¶9+¶10 (split hi)
- ch 7 §7.7: hi ¶6+¶7 = en ¶6 (join hi)
- ch 7 §7.7: hi ¶8+¶9 = en ¶7 (join hi)
- ch 7 §7.7: hi ¶13 = en ¶11+¶12 (split hi)
- ch 7 §7.8: hi ¶6+¶7 = en ¶6 (join hi)
- ch 7 §7.8: hi ¶8 = en ¶7+¶8 (split hi)
- ch 7 §7.9: hi ¶3+¶4 = en ¶3 (join hi)
- ch 7 §7.9: hi ¶5+¶6 = en ¶4 (join hi)
- ch 7 §7.9: hi ¶7+¶8 = en ¶5 (join hi)
- ch 7 §7.9: hi ¶9 = en ¶6+¶7 (split hi)
- ch 7 §7.10: hi ¶3 = en ¶3+¶4 (split hi)
- ch 7 §7.10: hi ¶4+¶5 = en ¶5 (join hi)

## pairs weaker than 0.45 — read both against the pages

- ch 4 §4.5: hi ¶10 = en ¶11 — similarity 0.44 — en «The rate of change of momentum of a…» · hi «किसी पिण्ड के संवेग परिवर्तन की दर आरोपित…»
- ch 4 §4.7: hi ¶3 = en ¶3 — similarity 0.44 — en «The total momentum of an isolated system of…» · hi «अन्योन्य क्रिया करने वाले कणों के किसी वियुक्त…»
- ch 4 §4.8: hi ¶5 = en ¶7 — similarity 0.30 — en «Answer Figures 4.8(b) and 4.8(c) are known as…» · hi «उदाहरण 4.6 6 kg संहति के किसी पिण्ड…»
- ch 4 §4.9.1: hi ¶13 = en ¶14 — similarity 0.40 — en «Rolling friction again has a complex origin, though…» · hi «लोटनिक घर्षण का उद्गम जटिल है यद्यपि यह…»
- ch 4 §4.10: hi ¶2 = en ¶3 — similarity 0.41 — en «The circular motion of a car on a…» · hi «किसी सपाट सड़क तथा किसी ढालू सड़क पर…»
- ch 5 §5.3: hi ¶3 = en ¶4 — similarity 0.36 — en «No work is done if :» · hi «कोई भी कार्य संपन्न हुआ नहीं माना जाता…»
- ch 5 §5.3: hi ¶5 = en ¶6 — similarity 0.40 — en «(ii) the force is zero. A block moving…» · hi «(ii) बल शून्य है। किसी चिकनी क्षैतिज मेज…»
- ch 5 §5.9: hi ¶9 = en ¶9 — similarity 0.39 — en «We note that we have idealised the situation.…» · hi «ध्यान दें कि यहाँ इस स्थिति को हमने…»
- ch 5 §5.9: hi ¶10 = en ¶10 — similarity 0.40 — en «We conclude this section by making a few…» · hi «हम संरक्षी बलों पर कुछ टिप्पणी करते हुए…»
- ch 5 §5.9: hi ¶16 = en ¶16 — similarity 0.41 — en «We invoke the work-energy theorem, rather than the…» · hi «यहाँ हम यांत्रिक ऊर्जा-संरक्षण के सिद्धांत के बजाय…»
- ch 6 §6.1: hi ¶2 = en ¶2 — similarity 0.42 — en «Any real body which we encounter in daily…» · hi «दैनिक जीवन में जितने पिण्ड हमारे संपर्क में…»
- ch 6 §6.3: hi ¶11 = en ¶11 — similarity 0.45 — en «This is the procedure that we followed earlier…» · hi «यही कार्यविधि हमने पिण्डों पर लगे बलों के…»
- ch 6 §6.4: hi ¶15 = en ¶12 — similarity 0.44 — en «In many problems on the system of particles,…» · hi «कणों की निकाय संबंधी बहुत सी समस्याओं में…»
- ch 6 §6.6: hi ¶8 = en ¶8 — similarity 0.44 — en «We have characterised pure translation of a body…» · hi «किसी पिण्ड की शुद्ध स्थानांतरण गति का अभिलक्षण…»
- ch 6 §6.7: hi ¶1 = en ¶1 — similarity 0.40 — en «In this section, we shall acquaint ourselves with…» · hi «इस अनुभाग में, हम आपको ऐसी दो राशियों…»
- ch 6 §6.9: hi ¶13 = en ¶11 — similarity 0.43 — en «As the mass of a body resists a…» · hi «क्योंकि, किसी पिण्ड का द्रव्यमान, उसकी रेखीय गत्यावस्था…»
- ch 6 §6.11: hi ¶2 = en ¶2 — similarity 0.44 — en «Before we begin, we note a simplification that…» · hi «इससे पहले कि हम अपनी बात शुरू करें,…»
- ch 6 §6.11: hi ¶3 = en ¶3 — similarity 0.42 — en «(1) We need to consider only those forces…» · hi «(1) पिण्ड पर कार्य करने वाले वे बल…»

## numbers one edition of a pair carries and the other does not

- ch 1 §1.2 ¶3: only in en — · only in hi 10
- ch 1 §1.2 ¶4: only in en — · only in hi 1.1
- ch 1 §1.3 ¶1: only in en — · only in hi 3
- ch 1 §1.3 ¶13: only in en — · only in hi 2
- ch 1 §1.3 ¶15: only in en — · only in hi 4
- ch 1 §1.3.1 ¶1: only in en — · only in hi 4
- ch 1 §1.3.2 ¶1: only in en 1.743, 1.74, 1 · only in hi 2.743, 2.74
- ch 1 §1.3.2 ¶6: only in en 2, 2 · only in hi —
- ch 1 §1.3.3 ¶8: only in en 1.02, 1.02, 0.01, 0.01, 9.89, 9.89, 100, 100, 1, 0.1 · only in hi —
- ch 1 §1.3.3 ¶9: only in en — · only in hi 1.02, 1.02, 0.01, 0.01, 100, 100, 1, 9.89, 9.89, 0.1
- ch 1 §1.4 ¶2: only in en — · only in hi 3, 3
- ch 2 §2.1 ¶1: only in en — · only in hi 24
- ch 2 §2.1 ¶4: only in en 4 · only in hi —
- ch 2 §2.2 ¶2: only in en 2.1 · only in hi —
- ch 2 §2.2 ¶5: only in en 0.010, 4.0, 4.0 · only in hi 0.01, 4, 4
- ch 2 §2.2 ¶6: only in en — · only in hi 2.1
- ch 2 §2.2 ¶8: only in en — · only in hi 2, 2, 1
- ch 2 §2.3 ¶5: only in en 2.4 · only in hi 2.2, 2.2, 2.2
- ch 2 §2.4 ¶1: only in en 0, 0, 0, 0, 2.5, 1, 2 · only in hi —
- ch 2 §2.4 ¶5: only in en — · only in hi 0
- ch 2 §2.4 ¶13: only in en — · only in hi 0
- ch 2 §2.4 ¶17: only in en — · only in hi 0, 1, 2
- ch 2 §2.4 ¶25: only in en — · only in hi 1, 1, 2, 2, 2, 0, 0, 0, 0, 3, 3, 2.2, 5, 7, 9, 11
- ch 2 §2.4 ¶26: only in en 0, 0, 2, 2, 2, 3, 3, 2.2, 1, 1, 5, 7, 9, 11 · only in hi —
- ch 2 §2.4 ¶27: only in en 0 · only in hi —
- ch 2 §2.4 ¶28: only in en 4 · only in hi 0
- ch 2 §2.4 ¶29: only in en — · only in hi 1
- ch 2 §2.4 ¶33: only in en — · only in hi 0
- ch 3 §3.5 ¶5: only in en — · only in hi 3.9
- ch 3 §3.5 ¶6: only in en 3.9 · only in hi —
- ch 3 §3.6 ¶6: only in en — · only in hi 3.10
- ch 3 §3.7.1 ¶11: only in en 3, 32 · only in hi 3.32
- ch 3 §3.7.1 ¶12: only in en — · only in hi 3.15
- ch 3 §3.7.1 ¶15: only in en — · only in hi 1.0
- ch 3 §3.8 ¶3: only in en 3.9 · only in hi 3.10
- ch 3 §3.8 ¶5: only in en 6, 6 · only in hi 6.0
- ch 3 §3.9 ¶2: only in en — · only in hi 0
- ch 3 §3.9 ¶4: only in en — · only in hi 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3.16, 3.34, 1, 2, 2, 3.37
- ch 3 §3.9 ¶5: only in en 3.16, 3.34, 2, 3.37 · only in hi 0, 0, 0, 0, 0
- ch 3 §3.9 ¶8: only in en 3.37 · only in hi 0, 0, 3.38
- ch 3 §3.9 ¶10: only in en — · only in hi 0, 0
- ch 3 §3.9 ¶11: only in en — · only in hi 90, 1
- ch 3 §3.9 ¶15: only in en — · only in hi 0, 0
- ch 3 §3.9 ¶17: only in en 2, 9.8, 14, 14 · only in hi 0, 0
- ch 4 §4.4 ¶7: only in en — · only in hi 1
- ch 4 §4.8 ¶3: only in en — · only in hi 3
- ch 4 §4.8 ¶4: only in en 4.11 · only in hi 4, 11
- ch 4 §4.8 ¶7: only in en 4.8, 4.8, 4.8 · only in hi 4.6, 6, 2, 2, 50, 10
- ch 5 §5.1 ¶1: only in en 14, 16 · only in hi —
- ch 5 §5.2 ¶3: only in en — · only in hi 5.2
- ch 5 §5.2 ¶4: only in en 5.2 · only in hi 5.3, 5.6
- ch 5 §5.2 ¶5: only in en 50.0 · only in hi 50.00
- ch 5 §5.3 ¶9: only in en 1811 · only in hi 1818
- ch 5 §5.3 ¶14: only in en 5.3 · only in hi —
- ch 5 §5.4 ¶3: only in en 50.0, 5.2, 2.00 · only in hi 50, 2
- ch 5 §5.5 ¶4: only in en 3.1 · only in hi —
- ch 5 §5.5 ¶6: only in en 5.4 · only in hi —
- ch 5 §5.7 ¶3: only in en — · only in hi 2
- ch 5 §5.8 ¶11: only in en — · only in hi 0
- ch 5 §5.9 ¶4: only in en — · only in hi 2, 2, 2, 2, 5.17
- ch 5 §5.9 ¶5: only in en — · only in hi 1
- ch 5 §5.9 ¶18: only in en 10.0 · only in hi 10
- ch 5 §5.10 ¶5: only in en — · only in hi 1
- ch 5 §5.11.2 ¶3: only in en — · only in hi 1, 1, 1, 1, 2, 2, 5.23, 5.26
- ch 5 §5.11.2 ¶4: only in en 5.23, 1, 1, 1, 1, 2, 2, 5.26 · only in hi —
- ch 5 §5.11.2 ¶6: only in en — · only in hi 1, 2
- ch 6 §6.1.1 ¶5: only in en 1, 2 · only in hi —
- ch 6 §6.1.1 ¶9: only in en 6.5 · only in hi —
- ch 6 §6.2 ¶1: only in en — · only in hi 6.7
- ch 6 §6.2 ¶10: only in en 6.3 · only in hi —
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
- ch 6 §6.10 ¶8: only in en 6.36 · only in hi 6.38
- ch 6 §6.11 ¶5: only in en 6.29 · only in hi —
- ch 6 §6.11 ¶6: only in en 1, 1, 1 · only in hi —
- ch 6 §6.11 ¶7: only in en — · only in hi 1
- ch 6 §6.12 ¶8: only in en — · only in hi 1
- ch 6 §6.12 ¶10: only in en — · only in hi 6.42
- ch 6 §6.12 ¶15: only in en 6.28 · only in hi 6.26
- ch 7 §7.3 ¶6: only in en — · only in hi 2, 1
- ch 7 §7.3 ¶14: only in en — · only in hi 2, 1, 1, 1, 1, 1
- ch 7 §7.4 ¶1: only in en 1798 · only in hi 1797
- ch 7 §7.5 ¶1: only in en 7.3 · only in hi —
- ch 7 §7.5 ¶6: only in en 2 · only in hi —
- ch 7 §7.7 ¶4: only in en — · only in hi 7.21
- ch 7 §7.8 ¶5: only in en — · only in hi 7.28
- ch 7 §7.8 ¶9: only in en — · only in hi 7.32
- ch 7 §7.8 ¶10: only in en — · only in hi 1, 5
- ch 7 §7.8 ¶11: only in en — · only in hi 4
- ch 7 §7.8 ¶16: only in en — · only in hi 4
- ch 7 §7.10 ¶1: only in en — · only in hi 7.35, 2

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.3.1 ¶5 — en p5, hi p5, similarity 0.57
- en: (2) In addition or subtraction, the final result should retain as many decimal places as are there in the number with the least decimal places.
- hi: (2) संख्याओं के संकलन अथवा व्यवकलन से प्राप्त अंतिम परिणाम में दशमलव के बाद उतने ही सार्थक अंक रहने देने चाहिए जितने कि संकलित या व्यवकलित की जाने वाली किसी राशि में दशमलव के बाद कम से कम हैं।

### 2. ch 1 §1.3.2 ¶2 — en p5–6, hi p5–6, similarity 0.64
- en: In any involved or complex multi-step calculation, you should retain, in intermediate steps, one digit more than the significant digits and round off to proper significant figures at the end of the calculation. Similarly, a number known to be within many significant figures, such as in 2.99792458 × 10^8 m/s for the speed of light in vacuum, is rounded off to an approximate value 3 × 10^8 m/s , which is often employed in computations. Finally, remember that exact numbers that appear in formulae like 2 pi in T = 2pi sqrt(L/g), have a large (infinite) number of significant figures. The value of pi = 3.1415926.... is known to a large number of significant figures. You may take the value as 3.142 or 3.14 for pi, with limited number of significant figures as required in specific cases.
- hi: किसी भी उलझन वाले अथवा बहुपदी जटिल परिकलन में, मध्यवर्ती पदों में सार्थक अंकों से एक अंक अधिक रहने देना चाहिए, जिसे परिकलन के अंत में उचित सार्थक अंकों तक पूर्णांकित कर देना चाहिए। इसी प्रकार, एक संख्या जो कई सार्थक अंकों तक ज्ञात है, जैसे निर्वात में प्रकाश का वेग, जिसके लिए, प्रायः 2.99792458 × 10^8 m/s को सन्निकट मान 3 × 10^8 m/s में पूर्णांकित कर परिकलनों में उपयोग करते हैं। अंत में ध्यान रखिये कि सूत्रों में उपयोग होने वाली यथार्थ संख्याएं, जैसे T = 2pi sqrt(L/g) में 2 pi, में सार्थक अंकों की संख्या अत्यधिक (अनन्त) है। pi = 3.1415926.... का मान बहुत अधिक सार्थक अंकों तक ज्ञात है लेकिन आम मापित राशियों में परिशुद्धि के आधार पर pi का मान 3.142 या 3.14 भी लेना तर्क सम्मत है।

### 3. ch 1 §1.3.2 ¶3 — en p6, hi p6, similarity 0.49
- en: Example 1.1 Each side of a cube is measured to be 7.203 m. What are the total surface area and the volume of the cube to appropriate significant figures?
- hi: उदाहरण 1.1 किसी घन की प्रत्येक भुजा की माप 7.203 m है। उचित सार्थक अंकों तक घन का कुल पृष्ठ क्षेत्रफल एवं आयतन ज्ञात कीजिए।

### 4. ch 1 §1.3.3 ¶4 — en p6, hi p6, similarity 0.55
- en: (2) If a set of experimental data is specified to n significant figures, a result obtained by combining the data will also be valid to n significant figures.
- hi: (2) यदि किसी प्रायोगिक आंकड़े के समुच्चय में n सार्थक अंकों का उल्लेख है, तो आंकड़े के संयोजन से प्राप्त परिणाम भी n सार्थक अंकों तक वैध होगा।

### 5. ch 1 §1.4 ¶1 — en p7, hi p7, similarity 0.52 — the founder reads this one
- en: The nature of a physical quantity is described by its dimensions. All the physical quantities represented by derived units can be expressed in terms of some combination of seven fundamental or base quantities. We shall call these base quantities as the seven dimensions of the physical world, which are denoted with square brackets [ ]. Thus, length has the dimension [L], mass [M], time [T], electric current [A], thermodynamic temperature [K], luminous intensity [cd], and amount of substance [mol]. The dimensions of a physical quantity are the powers (or exponents) to which the base quantities are raised to represent that quantity. Note that using the square brackets [ ] round a quantity means that we are dealing with 'the dimensions of' the quantity.
- hi: किसी भौतिक राशि की प्रकृति की व्याख्या उसकी विमाओं द्वारा की जाती है। व्युत्पन्न मात्रकों द्वारा व्यक्त होने वाली सभी भौतिक राशियाँ, सात मूल राशियों के संयोजन के पदों में प्रस्तुत की जा सकती हैं। इन मूल राशियों को हम भौतिक संसार की सात विमाएँ कह सकते हैं और इन्हें गुरु कोष्ठक के साथ निर्दिष्ट किया जाता है। इस प्रकार, लम्बाई की विमा [L], विद्युत धारा की [A], ऊष्मागतिकीय ताप की [K], ज्योति तीव्रता की [cd], और पदार्थ की मात्रा की [mol] है। किसी भौतिक राशि की विमाएँ उन घातों (या घातांकों) को कहते हैं, जिन्हें उस राशि को व्यक्त करने के लिए मूल राशियों पर चढ़ाना पड़ता है। ध्यान दीजिए किसी राशि को गुरु कोष्ठक [ ] से घेरने का यह अर्थ है कि हम उस राशि की विमा पर विचार कर रहे हैं।

### 6. ch 1 §1.5 ¶2 — en p7, hi p7, similarity 0.59
- en: An equation obtained by equating a physical quantity with its dimensional formula is called the dimensional equation of the physical quantity. Thus, the dimensional equations are the equations, which represent the dimensions of a physical quantity in terms of the base quantities. For example, the dimensional equations of volume [V], speed [v], force [F] and mass density [rho] may be expressed as [V] = [M^0 L^3 T^0] [v] = [M^0 L T^-1] [F] = [M L T^-2] [rho] = [M L^-3 T^0]
- hi: किसी भौतिक राशि को उसके विमीय सूत्र के बराबर लिखने पर प्राप्त समीकरण को उस राशि का विमीय समीकरण कहते हैं। अतः विमीय समीकरण वह समीकरण है जिसमें किसी भौतिक राशि को मूल राशियों और उनकी विमाओं के पदों में निरूपित किया जाता है। उदाहरण के लिए, आयतन [V], चाल [v], बल [F] और द्रव्यमान घनत्व [rho] की विमीय समीकरण को इस प्रकार व्यक्त किया जा सकता है : [V] = [M^0 L^3 T^0] [v] = [M^0 L T^-1] [F] = [M L T^-2] [rho] = [M L^-3 T^0]

### 7. ch 2 §2.4 ¶6 — en p6, hi p6, similarity 0.52
- en: Example 2.2 Obtain equations of motion for constant acceleration using method of calculus.
- hi: उदाहरण 2.2 कलन-विधि का उपयोग कर एकसमान त्वरण के लिए शुद्धगतिक समीकरण प्राप्त कीजिए।

### 8. ch 2 §2.4 ¶30 — en p9, hi p9, similarity 0.54 — the founder reads this one
- en: Stopping distance is an important factor considered in setting speed limits, for example, in school zones.
- hi: कुछ क्षेत्रों, जैसे किसी विद्यालय के निकट वाहनों की चाल की सीमा के निर्धारण में अवरोधन दूरी का ज्ञान एक महत्त्वपूर्ण कारक होता है ।

### 9. ch 3 §3.2 ¶2 — en p2, hi p2, similarity 0.48 — the founder reads this one
- en: A vector quantity is a quantity that has both a magnitude and a direction and obeys the triangle law of addition or equivalently the parallelogram law of addition. So, a vector is specified by giving its magnitude by a number and its direction. Some physical quantities that are represented by vectors are displacement, velocity, acceleration and force.
- hi: एक सदिश राशि वह राशि है जिसमें परिमाण तथा दिशा दोनों होते हैं तथा वह योग संबंधी त्रिभुज के नियम अथवा समानान्तर चतुर्भुज के योग संबंधी नियम का पालन करती है । इस प्रकार, एक सदिश को उसके परिमाण की संख्या तथा दिशा द्वारा व्यक्त करते हैं । कुछ भौतिक राशियाँ जिन्हें सदिशों द्वारा व्यक्त करते हैं, वे हैं विस्थापन, वेग, त्वरण तथा बल ।

### 10. ch 3 §3.4 ¶5 — en p4, hi p4, similarity 0.55
- en: What is the physical meaning of a zero vector? Consider the position and displacement vectors in a plane as shown in Fig. 3.1(a). Now suppose that an object which is at P at time t, moves to P′ and then comes back to P. Then, what is its displacement? Since the initial and final positions coincide, the displacement is a “null vector”.
- hi: शून्य सदिश का भौतिक अर्थ क्या है ? जैसाकि चित्र 3.1(a) में दिखाया गया है हम किसी समतल में स्थिति एवं विस्थापन सदिशों पर विचार करते हैं । मान लीजिए कि किसी क्षण t पर कोई वस्तु P पर है । वह P' तक जाकर पुन: P पर वापस आ जाती है । इस स्थिति में वस्तु का विस्थापन क्या होगा ? चूंकि प्रारंभिक एवं अंतिम स्थितियां संपाती हो जाती हैं, इसलिए विस्थापन "शून्य सदिश" होगा ।

### 11. ch 3 §3.9 ¶4 — en p12, hi p13, similarity 0.65
- en: The components of initial velocity v_o are : v_ox = v_o cos theta_o v_oy = v_o sin theta_o (3.36)
- hi: प्रारंभिक वेग v_0 के घटक निम्न प्रकार होंगे : v_ox = v_0 cos theta_0 v_oy = v_0 sin theta_0 (3.36) यदि चित्र 3.16 के अनुसार वस्तु की प्रारंभिक स्थिति निर्देश तंत्र के मूल बिंदु पर हो, तो x_0 = 0, y_0 = 0 इस प्रकार समीकरण (3.34b) को निम्न प्रकार से लिखेंगे : x = v_ox t = (v_0 cos theta_0)t तथा, y = (v_0 sin theta_0) t - (1/2) g t^2 (3.37)

### 12. ch 3 §3.9 ¶16 — en p14, hi p14, similarity 0.80
- en: Example 3.8 A cricket ball is thrown at a speed of 28 m s^–1 in a direction 30° above the horizontal. Calculate (a) the maximum height, (b) the time taken by the ball to return to the same level, and (c) the distance from the thrower to the point where the ball returns to the same level.
- hi: उदाहरण 3.8 : क्षैतिज से ऊपर की ओर 30° का कोण बनाते हुए एक क्रिकेट गेंद 28 m s^-1 की चाल से फेंकी जाती है । (a) अधिकतम ऊँचाई की गणना कीजिए, (b) उसी स्तर पर वापस पहुँचने में लगे समय की गणना कीजिए, तथा (c) फेंकने वाले बिंदु से उस बिंदु की दूरी जहाँ गेंद उसी स्तर पर पहुँची है, की गणना कीजिए ।

### 13. ch 4 §4.1 ¶3 — en p1, hi p1, similarity 0.55
- en: In these examples, the external agency of force (hands, wind, stream, etc) is in contact with the object. This is not always necessary. A stone released from the top of a building accelerates downward due to the gravitational pull of the earth. A bar magnet can attract an iron nail from a distance. This shows that external agencies (e.g. gravitational and magnetic forces ) can exert force on a body even from a distance.
- hi: इन उदाहरणों में, बल का बाह्य साधन (हाथ, वायु, जलधारा, आदि) पिण्ड के संपर्क में है। परंतु यह सदैव आवश्यक नहीं है। किसी भवन के शिखर से बिना अधोमुखी धक्का दिये मुक्त किया गया पत्थर पृथ्वी के गुरुत्वीय खिंचाव के कारण त्वरित हो जाता है। कोई छड़ चुंबक लोहे की कीलों को दूर से ही, अपनी ओर आकर्षित कर लेता है। यह दर्शाता है कि बाह्य साधन ( इन उदाहरणों में गुरुत्वीय एवं चुंबकीय बल ) एक दूरी से भी किसी पिण्ड पर बल लगा सकता है।

### 14. ch 4 §4.1 ¶4 — en p1, hi p1, similarity 0.53 — the founder reads this one
- en: In short, a force is required to put a stationary body in motion or stop a moving body, and some external agency is needed to provide this force. The external agency may or may not be in contact with the body.
- hi: संक्षेप में, किसी रुके हुए पिण्ड को गति प्रदान करने तथा गतिमान पिण्ड को रोकने के लिए बल की आवश्यकता होती है, तथा इस बल को प्रदान करने के लिए किसी बाह्य साधन की आवश्यकता होती है। यह बाह्य साधन उस पिण्ड के संपर्क में भी हो सकता है, और नहीं भी।

### 15. ch 4 §4.5 ¶27 — en p8, hi p8, similarity 0.60
- en: A large force acting for a short time to produce a finite change in momentum is called an impulsive force. In the history of science, impulsive forces were put in a conceptually different category from ordinary forces. Newtonian mechanics has no such distinction. Impulsive force is like any other force – except that it is large and acts for a short time.
- hi: परिमित संवेग परिवर्तन उत्पन्न करने के लिए, कम समय के लिए कार्यरत रहने वाले बड़े बल को आवेगी बल कहते हैं। यद्यपि विज्ञान के इतिहास में आवेगी बलों को संकल्पनात्मक रूप से सामान्य बलों से अलग श्रेणी में रखा गया, न्यूटनी यांत्रिकी में ऐसा कोई विभेदन नहीं किया गया है। अन्य बलों की भांति आवेगी बल भी बल ही है–केवल यह बड़ा है और कम समय के लिए कार्यरत रहता है।

### 16. ch 4 §4.9.1 ¶14 — en p14, hi p14–15, similarity 0.40
- en: Rolling friction again has a complex origin, though somewhat different from that of static and sliding friction. During rolling, the surfaces in contact get momentarily deformed a little, and this results in a finite area (not a point) of the body being in contact with the surface. The net effect is that the component of the contact force parallel to the surface opposes motion.
- hi: लोटनिक घर्षण का उद्गम जटिल है यद्यपि यह स्थैतिक तथा सर्पी घर्षण के उद्गम से कुछ भिन्न है। लोटनिक गति के समय संपर्क पृष्ठों में क्षणमात्र के लिए विरूपण होता है, तथा इसके फलस्वरूप पिण्ड का कुछ परिमित क्षेत्रफल (कोई बिंदु नहीं), लोटनिक गति के समय पृष्ठ के संपर्क में होता है। इसका नेट प्रभाव यह होता है कि संपर्क बल का एक घटक पृष्ठ के समान्तर प्रकट होता है जो गति का अवरोध करता है।

### 17. ch 4 §4.9.1 ¶16 — en p14, hi p15, similarity 0.59
- en: In many practical situations, however, friction is critically needed. Kinetic friction that dissipates power is nevertheless important for quickly stopping relative motion. It is made use of by brakes in machines and automobiles. Similarly, static friction is important in daily life. We are able to walk because of friction. It is impossible for a car to move on a very slippery road. On an ordinary road, the friction between the tyres and the road provides the necessary external force to accelerate the car.
- hi: तथापि, बहुत-सी व्यावहारिक स्थितियों में, घर्षण अत्यन्त आवश्यक होता है । गतिज घर्षण में ऊर्जा-क्षय होता है, फिर भी आपेक्षिक गति को शीघ्र समाप्त करने में इसकी महत्त्वपूर्ण भूमिका है। मशीनों तथा यंत्रों में ब्रेक की भांति इसका उपयोग किया जाता है। इसी प्रकार स्थैतिक घर्षण भी हमारे दैनिक जीवन में अत्यन्त महत्त्वपूर्ण है। हम घर्षण के कारण ही फर्श पर चल पाते हैं । अत्यधिक फिसलन वाली सड़क पर कार को चला पाना असंभव होता है। किसी साधारण सड़क पर, टायरों और सड़क के बीच घर्षण पहिए की घूर्णी गति को लोटनिक गति में रूपांतरित करके कार को त्वरित करने के लिए आवश्यक बाह्य बल प्रदान करता है ।

### 18. ch 4 §4.10 ¶3 — en p15, hi p15, similarity 0.41
- en: The circular motion of a car on a flat and banked road give interesting application of the laws of motion.
- hi: किसी सपाट सड़क तथा किसी ढालू सड़क पर कार की वर्तुल गति, गति के नियमों के रोचक उदाहरण हैं।

### 19. ch 5 §5.2 ¶3 — en p3, hi p3, similarity 0.61
- en: The above equation provides a motivation for the definitions of work and kinetic energy. The left side of the equation is the difference in the quantity 'half the mass times the square of the speed' from its initial value to its final value. We call each of these quantities the 'kinetic energy', denoted by K. The right side is a product of the displacement and the component of the force along the displacement. This quantity is called 'work' and is denoted by W. Eq. (5.2b) is then K_f − K_i = W (5.3) where K_i and K_f are respectively the initial and final kinetic energies of the object. Work refers to the force and the displacement over which it acts. Work is done by a force on the body over a certain displacement.
- hi: उपरोक्त समीकरण कार्य एवं गतिज ऊर्जा को परिभाषित करने के लिए प्रेरित करता है। समीकरण (5.2 b) में बायाँ पक्ष वस्तु के द्रव्यमान के आधे और उसकी चाल के वर्ग के गुणनफल के अंतिम और आरंभिक मान का अंतर है। हम इनमें से प्रत्येक राशि को ‘गतिज ऊर्जा’ कहते हैं और संकेत K से निर्दिष्ट करते हैं। समीकरण का दायाँ पक्ष वस्तु पर आरोपित बल का विस्थापन के अनुदिश घटक और वस्तु के विस्थापन का गुणनफल है। इस राशि को ‘कार्य’ कहते हैं और इसे संकेत W से निर्दिष्ट करते हैं। अतः समीकरण (5.2 b) को निम्न प्रकार लिख सकते हैं : K_f – K_i = W (5.3) जहाँ K_i तथा K_f वस्तु की आरंभिक एवं अंतिम गतिज ऊर्जा हैं। कार्य किसी वस्तु पर लगने वाले बल और इसके विस्थापन के संबंध को बताता है। अतः किसी निश्चित विस्थापन के दौरान वस्तु पर लगाया गया बल कार्य करता है।

### 20. ch 5 §5.2 ¶5 — en p3, hi p3, similarity 0.57 — the founder reads this one
- en: Example 5.2 It is well known that a raindrop falls under the influence of the downward gravitational force and the opposing resistive force. The latter is known to be proportional to the speed of the drop but is otherwise undetermined. Consider a drop of mass 1.00 g falling from a height 1.00 km. It hits the ground with a speed of 50.0 m s^-1. (a) What is the work done by the gravitational force ? What is the work done by the unknown resistive force?
- hi: उदाहरण 5.2 हम अच्छी तरह जानते हैं कि वर्षा की बूँद नीचे की ओर लगने वाले गुरुत्वाकर्षण बल और बूँद के गिरने की दिशा के विपरीत लगने वाले प्रतिरोधी बल के प्रभाव के अधीन गिरती है। प्रतिरोधी बल बूँद की चाल के अनुक्रमानुपाती, परंतु अनिर्धारित होता है। माना कि 1.00 g द्रव्यमान की वर्षा की बूँद 1.00 km ऊँचाई से गिर रही है। यह धरातल पर 50.00 m s^-1 की चाल से संघट्ट करती है। (a) गुरुत्वीय बल द्वारा किया गया कार्य क्या है? (b) अज्ञात प्रतिरोधी बल द्वारा किया गया कार्य क्या है ?

### 21. ch 5 §5.2 ¶8 — en p3, hi p3, similarity 0.70
- en: (b) From the work-energy theorem Delta K = W_g + W_r where W_r is the work done by the resistive force on the raindrop. Thus W_r = Delta K − W_g = 1.25 − 10 = − 8.75 J is negative.
- hi: (b) कार्य-ऊर्जा प्रमेय से, Delta K = W_g + W_r जहाँ W_r प्रतिरोधी बल द्वारा किया गया कार्य है। अतः W_r = Delta K – W_g = 1.25 – 10 = – 8.75 J ऋणात्मक है।

### 22. ch 5 §5.5 ¶5 — en p6, hi p6, similarity 0.64
- en: Example 5.5 A woman pushes a trunk on a railway platform which has a rough surface. She applies a force of 100 N over a distance of 10 m. Thereafter, she gets progressively tired and her applied force reduces linearly with distance to 50 N. The total distance through which the trunk has been moved is 20 m. Plot the force applied by the woman and the frictional force, which is 50 N versus displacement. Calculate the work done by the two forces over 20 m.
- hi: उदाहरण 5.5 कोई स्त्री खुरदरी सतह वाले रेलवे प्लेटफार्म पर संदूक को खिसकाती है। वह 10 m की दूरी तक 100 N का बल आरोपित करती है। उसके पश्चात्, उत्तरोत्तर वह थक जाती है और उसके द्वारा आरोपित बल रेखीय रूप से घटकर 50 N हो जाता है। संदूक को कुल 20 m की दूरी तक खिसकाया जाता है। स्त्री द्वारा संदूक पर आरोपित बल और घर्षण बल जो कि 50 N है, तथा विस्थापन के बीच ग्राफ खींचिए। दोनों बलों द्वारा 20 m तक किए गए कार्य का परिकलन कीजिए ।

### 23. ch 5 §5.11.2 ¶8 — en p15, hi p15, similarity 0.57 — the founder reads this one
- en: Example 5.11 Slowing down of neutrons: In a nuclear reactor a neutron of high speed (typically 10^7 m s^-1) must be slowed to 10^3 m s^-1 so that it can have a high probability of interacting with isotope ^235_92 U and causing it to fission. Show that a neutron can lose most of its kinetic energy in an elastic collision with a light nuclei like deuterium or carbon which has a mass of only a few times the neutron mass. The material making up the light nuclei, usually heavy water (D_2O) or graphite, is called a moderator.
- hi: उदाहरण 5.11 गतिशील न्यूट्रॉनों का मंदन : किसी नाभिकीय रिऐक्टर में तीव्रगामी न्यूट्रॉन (विशिष्ट रूप से वेग 10^7 m s^-1) को 10^3 m s^-1 के वेग तक मंदित कर दिया जाना चाहिए ताकि नाभिकीय विखंडन अभिक्रिया में न्यूट्रॉन की यूरेनियम के समस्थानिक ^235_92U से अन्योन्यक्रिया करने की प्रायिकता उच्च हो जाए। सिद्ध कीजिए कि न्यूट्रॉन एक हलके नाभिक, जैसे ड्यूटीरियम या कार्बन जिसका द्रव्यमान न्यूट्रॉन के द्रव्यमान का मात्र कुछ गुना है, से प्रत्यास्थ संघट्ट करने में अपनी अधिकांश गतिज ऊर्जा की क्षति कर देता है। ऐसे पदार्थ प्रायः भारी जल (D_2O) अथवा ग्रेफाइट, जो न्यूट्रॉनों की गति को मंद कर देते हैं, 'मंदक' कहलाते हैं ।

### 24. ch 6 §6.2 ¶17 — en p6, hi p6, similarity 0.68
- en: Here M is the total mass of the body. The coordinates of the centre of mass now are X = (1/M) integral x dm, Y = (1/M) integral y dm and Z = (1/M) integral z dm (6.5a)
- hi: यहाँ M पिण्ड का कुल द्रव्यमान है। द्रव्यमान केन्द्र के निर्देशांकों को अब हम इस प्रकार लिख सकते हैं X = (1/M) integral x dm, Y = (1/M) integral y dm और Z = (1/M) integral z dm (6.5a)

### 25. ch 6 §6.2 ¶28 — en p7, hi p7, similarity 0.49
- en: Answer The lamina (∆LMN) may be subdivided into narrow strips each parallel to the base (MN) as shown in Fig. 6.10
- hi: हल फलक (Delta LMN) को आधार (MN) के समान्तर पतली पट्टियों में बांटा जा सकता है जैसा चित्र 6.10 में दर्शाया गया है।

### 26. ch 6 §6.3 ¶11 — en p8–9, hi p8–9, similarity 0.45
- en: This is the procedure that we followed earlier in analysing forces on bodies and solving problems without explicitly outlining and justifying the procedure. We now realise that in earlier studies we assumed, without saying so, that rotational motion and/or internal motion of the particles were either absent or negligible. We no longer need to do this. We have not only found the justification of the procedure we followed earlier; but we also have found how to describe and separate the translational motion of (1) a rigid body which may be rotating as well, or (2) a system of particles with all kinds of internal motion.
- hi: यही कार्यविधि हमने पिण्डों पर लगे बलों के विश्लेषण और उनसे जुड़ी समस्या के हल के लिए अपनाई थी। हालांकि, इसके लिए कोई स्पष्ट कारण नहीं बताया गया था। अब हम यह समझ सकते हैं, कि पूर्व के अध्ययनों में, हमने बिन कहे ही यह मान लिया था कि निकाय में घूर्णी गति, एवं कणों में आंतरिक गति या तो थी ही नहीं और यदि थी तो नगण्य थी। आगे से हमें यह मानने की आवश्यकता नहीं रहेगी। न केवल हमें अपनी पहले अपनाई गई पद्धति का औचित्य समझ में आ गया है, वरन्, हमने वह विधि भी ज्ञात कर ली है जिसके द्वारा (i) ऐसे दृढ़ पिण्ड की जिसमें घूर्णी गति भी हो, (ii) एक ऐसे निकाय की जिसके कणों में तरह-तरह की आंतरिक गतियाँ हों, स्थानांतरण गति को अलग करके समझा समझाया जा सकता है।

### 27. ch 6 §6.4 ¶2 — en p9, hi p9, similarity 0.70
- en: Let us also recall that Newton's second law written in symbolic form for a single particle is F = dp / dt (6.13) where F is the force on the particle. Let us consider a system of n particles with masses m_1, m_2,...m_n respectively and velocities v_1, v_2,.......v_n respectively. The particles may be interacting and have external forces acting on them. The linear momentum of the first particle is m_1v_1, of the second particle is m_2v_2 and so on.
- hi: और, एकल कण के लिए न्यूटन के द्वितीय नियम को हम सांकेतिक भाषा में लिख सकते हैं F = dp / dt (6.13) जहाँ F कण पर आरोपित बल है। आइये, अब हम n कणों के एक निकाय पर विचार करें जिनके द्रव्यमान क्रमशः m_1, m_2,...m_n है और वेग क्रमशः v_1, v_2,.......v_n हैं। कण, परस्पर अन्योन्य क्रियारत हो सकते हैं और उन पर बाह्य बल भी लगे हो सकते हैं। पहले कण का रेखीय संवेग m_1v_1, दूसरे कण का रेखीय संवेग m_2v_2 और इसी प्रकार अन्य कणों के रेखीय संवेग भी हैं।

### 28. ch 6 §6.5 ¶19 — en p11, hi p12, similarity 0.77
- en: This follows since magnitude of a × a is a^2 sin 0° = 0 .
- hi: स्पष्टतः ऐसा इसलिए है क्योंकि a × a का परिमाण a^2 sin 0° = 0 ।

### 29. ch 6 §6.6.1 ¶2 — en p14, hi p14, similarity 0.66
- en: If the axis of rotation is fixed, the direction of omega and hence, that of alpha is fixed. In this case the vector equation reduces to a scalar equation alpha = d omega / dt (6.22)
- hi: यदि घूर्णन अक्ष स्थिर है तो omega की दिशा और इसलिए alpha की दिशा भी स्थिर होगी। इस स्थिति में तब सदिश समीकरण अदिश समीकरण में बदल जाती है और हम लिख सकते हैं- alpha = d omega / dt (6.22)

### 30. ch 6 §6.8.1 ¶1 — en p20, hi p20, similarity 0.46
- en: An ideal lever is essentially a light (i.e. of negligible mass) rod pivoted at a point along its length. This point is called the fulcrum. A see-saw on the children's playground is a typical example of a lever. Two forces F_1 and F_2, parallel to each other and usually perpendicular to the lever, as shown here, act on the lever at distances d_1 and d_2 respectively from the fulcrum as shown in Fig. 6.23.
- hi: एक आदर्श उत्तोलक, अनिवार्य रूप से, एक ऐसी हलकी (यानि नगण्य द्रव्यमान वाली) छड़ है जो अपनी लम्बाई के अनुदिश लिए गए किसी बिन्दु के परित: घूम सकती हो। यह बिन्दु आलम्ब कहलाता है। बच्चों के खेल के मैदान में लगा सी-सा, उत्तोलक का एक प्रतिनिधिक उदाहरण है। दो बल F_1 एवं F_2, जो एक दूसरे के समांतर हैं उत्तोलक के सिरों पर, इसके लम्बवत् तथा आलम्ब से क्रमश: d_1 एवं d_2 दूरियों पर लगाये गए हैं जैसा चित्र 6.23 में दर्शाया गया है।

### 31. ch 6 §6.8.1 ¶3 — en p20, hi p20, similarity 0.54
- en: For considering rotational equilibrium we take the moments about the fulcrum; the sum of moments must be zero, d_1F_1 – d_2F_2 = 0 (ii)
- hi: और घूर्णी संतुलन में, आलम्ब के परित: आघूर्ण लेने पर, इन आघूर्णों का योग शून्य होगा। अत: d_1F_1−d_2F_2 = 0 (ii)

### 32. ch 6 §6.11 ¶2 — en p27, hi p27, similarity 0.44
- en: Before we begin, we note a simplification that arises in the case of rotational motion about a fixed axis. Since the axis is fixed, only those components of torques, which are along the direction of the fixed axis need to be considered in our discussion. Only these components can cause the body to rotate about the axis. A component of the torque perpendicular to the axis of rotation will tend to turn the axis from its position. We specifically assume that there will arise necessary forces of constraint to cancel the effect of the perpendicular components of the (external) torques, so that the fixed position of the axis will be maintained. The perpendicular components of the torques, therefore need not be taken into account. This means that for our calculation of torques on a rigid body:
- hi: इससे पहले कि हम अपनी बात शुरू करें, एक अचल अक्ष के परितः घूर्णी गति में एक सरलीकरण की ओर ध्यान दिलाना आवश्यक है। क्योंकि अक्ष स्थिर है, हमें अपने विवेचन में बल आघूर्णों एवं कोणीय संवेगों के इसके अनुदिश अवयवों पर ही विचार करने की आवश्यकता होगी। केवल यही घटक पिण्ड को घूर्णन कराते हैं। बल आघूर्ण का अक्ष से अभिलंबवत घटक अक्ष को उसकी स्थिति से घुमाने का प्रयास करता है। हालांकि हम मानकर चलेंगे कि बल आघूर्ण के इस घटक को संतुलित करने हेतु आवश्यक बल आघूर्ण उत्पन्न होंगे जो अक्ष की स्थिति बनाए रखने के लिए उत्तरदायी होंगे। अतः इन अभिलंबवत् बल आघूर्ण के घटकों पर विचार में करने की आवश्यकता नहीं है। पर्याय में हमें निम्न विचार में लाने की आवश्यकता है:

### 33. ch 7 §7.3 ¶4 — en p4, hi p4, similarity 0.66
- en: The quotation is essentially from Newton's famous treatise called 'Mathematical Principles of Natural Philosophy' (Principia for short).
- hi: यह उद्धरण तत्वतः न्यूटन के प्रसिद्ध शोध प्रबन्ध “प्राकृतिक दर्शन के गणितीय सिद्धांत” (Mathematical Principles of Natural Philosophy) जिसे संक्षेप में प्रिंसिपिया (Principia) कहते हैं, से प्राप्त होता है।

### 34. ch 7 §7.4 ¶1 — en p5, hi p6, similarity 0.64 — the founder reads this one
- en: The value of the gravitational constant G entering the Universal law of gravitation can be determined experimentally and this was first done by English scientist Henry Cavendish in 1798. The apparatus used by him is schematically shown in Fig.7.6
- hi: गुरुत्वाकर्षण के सार्वत्रिक नियम में प्रयुक्त गुरुत्वीय स्थिरांक G के मान को प्रायोगिक आधार पर ज्ञात किया जा सकता है तथा इस प्रकार के प्रयोग को सर्वप्रथम अंग्रेज वैज्ञानिक हेनरी कैवेन्डिश ने 1797 में किया था। उनके द्वारा उपयोग किए गए उपकरण को व्यवस्था चित्र 7.6 में दर्शाया गया है।

### 35. ch 7 §7.5 ¶6 — en p7, hi p7, similarity 0.67
- en: The acceleration experienced by the mass m, which is usually denoted by the symbol g is related to F by Newton's 2nd law by relation F = mg. Thus g = F/m = GM_E / R_E^2 (7.12)
- hi: यहाँ M_E तथा R_E क्रमशः पृथ्वी का द्रव्यमान तथा त्रिज्या है। द्रव्यमान m द्वारा अनुभव किया जाने वाला त्वरण जिसे प्रायः प्रतीक g द्वारा निर्दिष्ट किया जाता है, न्यूटन के द्वितीय नियम द्वारा बल F से संबंध F = mg द्वारा संबंधित होता है। इस प्रकार g = F/m = GM_E / R_E^2 (7.12)

### 36. ch 7 §7.6 ¶7 — en p7, hi p8, similarity 0.53 — the founder reads this one
- en: Since mass of a sphere is proportional to be cube of its radius.
- hi: क्योंकि, किसी गोले का द्रव्यमान उसकी त्रिज्या के घन के अनुक्रमानुपाती होता है।

### 37. ch 7 §7.7 ¶9 — en p9, hi p10, similarity 0.55 — the founder reads this one
- en: It should be noted that an isolated system of particles will have the total potential energy that equals the sum of energies (given by the above equation) for all possible pairs of its constituent particles. This is an example of the application of the superposition principle.
- hi: यह भी ध्यान दिया जाना चाहिए कि कणों के किसी सभी वियुक्त निकाय की कुल स्थितिज ऊर्जा, अवयवों/कणों के सभी संभावित युग्मों की ऊर्जाओं (उपरोक्त समीकरण द्वारा परिकलित) के योग के बराबर होती है। यह अध्यारोपण सिद्धांत के एक अनुप्रयोग का उदाहरण है।

### 38. ch 7 §7.8 ¶2 — en p9, hi p10, similarity 0.60
- en: The principle of conservation of energy helps us to answer this question. Suppose the object did reach infinity and that its speed there was V_f. The energy of an object is the sum of potential and kinetic energy. As before W_1 denotes that gravitational potential energy of the object at infinity. The total energy of the projectile at infinity then is E (infinity) = W_1 + mV_f^2 / 2 (7.26)
- hi: इस प्रश्न का उत्तर देने में ऊर्जा संरक्षण नियम हमारी सहायता करता है। मान लीजिए फेंका गया पिण्ड अनन्त तक पहुंचता है और वहाँ उसकी चाल V_f है। किसी पिण्ड की ऊर्जा स्थितिज तथा गतिज ऊर्जाओं का योग होती है। पहले की ही भांति W_1 पिण्ड की अनन्त पर गुरुत्वीय स्थितिज ऊर्जा को निर्दिष्ट करता है। तब प्रक्षेप्य की अनन्त पर कुल ऊर्जा E(अनन्त) = W_1 + mV_f^2 / 2 (7.26)

### 39. ch 7 §7.9 ¶1 — en p11, hi p11–12, similarity 0.62 — the founder reads this one
- en: Earth satellites are objects which revolve around the earth. Their motion is very similar to the motion of planets around the Sun and hence Kepler's laws of planetary motion are equally applicable to them. In particular, their orbits around the earth are circular or elliptic. Moon is the only natural satellite of the earth with a near circular orbit with a time period of approximately 27.3 days which is also roughly equal to the rotational period of the moon about its own axis. Since, 1957, advances in technology have enabled many countries including India to launch artificial earth satellites for practical use in fields like telecommunication, geophysics and meteorology.
- hi: भू उपग्रह वह पिण्ड है जो पृथ्वी के परितः परिक्रमण करते हैं। इनकी गतियां, ग्रहों की सूर्य के परितः गतियों के बहुत समान होती हैं, अतः केप्लर के ग्रहीय गति नियम इन पर भी समान रूप से लागू होते हैं। विशेष बात यह है कि इन उपग्रहों की पृथ्वी के परितः कक्षाएं वृत्ताकार अथवा दीर्घवृत्ताकार है। पृथ्वी का एकमात्र प्राकृतिक उपग्रह चन्द्रमा है जिसकी लगभग वृत्ताकार कक्षा है और लगभग 27.3 दिन का परिक्रमण काल है जो चन्द्रमा के अपनी अक्ष के परितः घूर्णन काल के लगभग समान है। वर्ष 1957 के पश्चात् विज्ञान तथा प्रौद्योगिकी में उन्नति के फलस्वरूप भारत सहित कई देश दूर संचार, भू भौतिकी, मौसम विज्ञान के क्षेत्र में व्यावहारिक उपयोगों के लिए मानव-निर्मित भू उपग्रहों को कक्षाओं में प्रमोचित करने योग्य बन गए हैं।

### 40. ch 7 §7.10 ¶9 — en p13, hi p14, similarity 0.75
- en: The change in potential energy is twice the change in the total energy, namely Delta V = V_f - V_i = - 6.25 × 10^9 J
- hi: स्थितिज ऊर्जा में होने वाला परिवर्तन कुल ऊर्जा का दो गुना है, अर्थात् ΔV = V_f − V_i = − 6.25 × 10^9 J

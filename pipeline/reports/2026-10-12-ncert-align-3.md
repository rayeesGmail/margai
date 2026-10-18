# margai-pipeline ncert align

- run: 2026-10-12 23:59 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1042 English and 984 Hindi row(s) in 8 chapter(s)
- result: ok
request id: pipeline-ncert-align-7ae42288-5a77-410b-a84d-b8e1f252b1ab
English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored English vectors of its section — nothing is stored or changed

## cost

| calls | input tokens | spent |
|---|---|---|
| 984 | 320291 | ₹10.13 |

## sections whose rows do not pair one to one

| chapter | section | en | hi | proposals |
|---|---|---|---|---|
| 1 | 1.2 | 8 | 9 | 1 |
| 1 | 1.3 | 4 | 4 | 2 |
| 1 | 1.5 | 23 | 26 | 7 |
| 1 | 1.6 | 16 | 15 | 5 |
| 1 | 1.7 | 10 | 11 | 1 |
| 1 | 1.7.1 | 4 | 2 | 2 |
| 1 | 1.7.2 | 8 | 9 | 3 |
| 1 | 1.8 | 12 | 9 | 3 |
| 1 | 1.9 | 9 | 9 | 2 |
| 1 | 1.10.1 | 10 | 9 | 3 |
| 1 | 1.10.2 | 6 | 16 | 10 |
| 1 | 1.13 | 23 | 20 | 3 |
| 1 | 1.14.1 | 6 | 7 | 1 |
| 1 | 1.14.3 | 11 | 10 | 5 |
| 2 | 2.2 | 6 | 5 | 1 |
| 2 | 2.3 | 10 | 9 | 3 |
| 2 | 2.4 | 15 | 12 | 3 |
| 2 | 2.5 | 21 | 15 | 8 |
| 2 | 2.6.1 | 7 | 5 | 2 |
| 2 | 2.7 | 17 | 16 | 3 |
| 2 | 2.8.1 | 4 | 5 | 1 |
| 2 | 2.8.2 | 7 | 7 | 4 |
| 2 | 2.9 | 20 | 13 | 13 |
| 2 | 2.9.1 | 0 | 10 | 10 |
| 2 | 2.12 | 8 | 5 | 3 |
| 2 | 2.13 | 9 | 8 | 1 |
| 2 | 2.14.2 | 7 | 6 | 3 |
| 2 | 2.15 | 13 | 9 | 4 |
| 3 | 3.4 | 8 | 9 | 1 |
| 3 | 3.5 | 27 | 13 | 14 |
| 3 | 3.5.1 | 3 | 2 | 1 |
| 3 | 3.6 | 5 | 4 | 1 |
| 3 | 3.8 | 14 | 12 | 4 |
| 3 | 3.10 | 8 | 9 | 1 |
| 3 | 3.11 | 20 | 20 | 4 |
| 3 | 3.12 | 15 | 17 | 4 |
| 4 | 4.2.2 | 7 | 6 | 1 |
| 4 | 4.2.3 | 7 | 6 | 1 |
| 4 | 4.3 | 8 | 9 | 1 |
| 4 | 4.5 | 15 | 15 | 2 |
| 4 | 4.6 | 17 | 17 | 4 |
| 4 | 4.8 | 21 | 18 | 5 |
| 4 | 4.9.2 | 8 | 6 | 2 |
| 4 | 4.10 | 15 | 11 | 4 |
| 5 | 5.1 | 9 | 8 | 1 |
| 5 | 5.2.1 | 7 | 7 | 2 |
| 5 | 5.2.3 | 12 | 14 | 2 |
| 5 | 5.2.4 | 12 | 7 | 7 |
| 5 | 5.3 | 24 | 19 | 5 |
| 5 | 5.4 | 12 | 15 | 3 |
| 5 | 5.5.3 | 4 | 3 | 1 |
| 6 | 6.4 | 17 | 17 | 6 |
| 6 | 6.5 | 17 | 19 | 4 |
| 6 | 6.6 | 12 | 12 | 4 |
| 6 | 6.7.1 | 17 | 17 | 2 |
| 6 | 6.7.2 | 15 | 15 | 2 |
| 7 | 7.2 | 16 | 16 | 2 |
| 7 | 7.5 | 18 | 14 | 4 |
| 7 | 7.6.1 | 13 | 11 | 2 |
| 7 | 7.6.2 | 11 | 11 | 2 |
| 7 | 7.7 | 26 | 24 | 6 |
| 8 | 8.2 | 14 | 12 | 4 |
| 8 | 8.3.1 | 4 | 3 | 1 |
| 8 | 8.3.2 | 18 | 13 | 5 |

## proposed Hindi joins and splits — adjudicate each; nothing is applied

- ch 1 §1.2: hi ¶7+¶8 = en ¶7 (join hi)
- ch 1 §1.3: hi ¶3 has no english
- ch 1 §1.3: en ¶4 has no hindi
- ch 1 §1.5: hi ¶8 = en ¶8+¶9 (split hi)
- ch 1 §1.5: hi ¶13 = en ¶14+¶15 (split hi)
- ch 1 §1.5: hi ¶16+¶17 = en ¶18 (join hi)
- ch 1 §1.5: hi ¶18+¶19 = en ¶19 (join hi)
- ch 1 §1.5: hi ¶20+¶21 = en ¶20 (join hi)
- ch 1 §1.5: hi ¶22+¶23 = en ¶21 (join hi)
- ch 1 §1.5: hi ¶25+¶26 = en ¶23 (join hi)
- ch 1 §1.6: hi ¶4+¶5 = en ¶4 (join hi)
- ch 1 §1.6: hi ¶10+¶11 = en ¶9 (join hi)
- ch 1 §1.6: hi ¶12 = en ¶10+¶11 (split hi)
- ch 1 §1.6: hi ¶13 = en ¶12+¶13 (split hi)
- ch 1 §1.6: hi ¶15 = en ¶15+¶16 (split hi)
- ch 1 §1.7: hi ¶1+¶2 = en ¶1 (join hi)
- ch 1 §1.7.1: hi ¶1 = en ¶1+¶2 (split hi)
- ch 1 §1.7.1: hi ¶2 = en ¶3+¶4 (split hi)
- ch 1 §1.7.2: hi ¶4 = en ¶4+¶5 (split hi)
- ch 1 §1.7.2: hi ¶5+¶6 = en ¶6 (join hi)
- ch 1 §1.7.2: hi ¶8+¶9 = en ¶8 (join hi)
- ch 1 §1.8: hi ¶9 = en ¶9+¶10 (split hi)
- ch 1 §1.8: en ¶11 has no hindi
- ch 1 §1.8: en ¶12 has no hindi
- ch 1 §1.9: hi ¶2+¶3 = en ¶2 (join hi)
- ch 1 §1.9: hi ¶7 = en ¶6+¶7 (split hi)
- ch 1 §1.10.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 1 §1.10.1: hi ¶3 has no english
- ch 1 §1.10.1: hi ¶4 = en ¶4+¶5 (split hi)
- ch 1 §1.10.2: hi ¶2+¶3 = en ¶2 (join hi)
- ch 1 §1.10.2: hi ¶5+¶6 = en ¶4 (join hi)
- ch 1 §1.10.2: hi ¶7 has no english
- ch 1 §1.10.2: hi ¶8 has no english
- ch 1 §1.10.2: hi ¶9 has no english
- ch 1 §1.10.2: hi ¶10 has no english
- ch 1 §1.10.2: hi ¶11 has no english
- ch 1 §1.10.2: hi ¶12+¶13 = en ¶5 (join hi)
- ch 1 §1.10.2: hi ¶14+¶15 = en ¶6 (join hi)
- ch 1 §1.10.2: hi ¶16 has no english
- ch 1 §1.13: hi ¶4 = en ¶4+¶5 (split hi)
- ch 1 §1.13: hi ¶7 = en ¶8+¶9 (split hi)
- ch 1 §1.13: en ¶19 has no hindi
- ch 1 §1.14.1: hi ¶4+¶5 = en ¶4 (join hi)
- ch 1 §1.14.3: hi ¶2 = en ¶2+¶3 (split hi)
- ch 1 §1.14.3: hi ¶3+¶4 = en ¶4 (join hi)
- ch 1 §1.14.3: hi ¶7+¶8 = en ¶7 (join hi)
- ch 1 §1.14.3: en ¶9 has no hindi
- ch 1 §1.14.3: hi ¶10 = en ¶10+¶11 (split hi)
- ch 2 §2.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 2 §2.3: hi ¶2+¶3 = en ¶2 (join hi)
- ch 2 §2.3: hi ¶8 = en ¶7+¶8 (split hi)
- ch 2 §2.3: hi ¶9 = en ¶9+¶10 (split hi)
- ch 2 §2.4: hi ¶4 = en ¶4+¶5 (split hi)
- ch 2 §2.4: hi ¶5 = en ¶6+¶7 (split hi)
- ch 2 §2.4: hi ¶9 = en ¶11+¶12 (split hi)
- ch 2 §2.5: hi ¶1 = en ¶1+¶2 (split hi)
- ch 2 §2.5: hi ¶2 = en ¶3+¶4 (split hi)
- ch 2 §2.5: hi ¶7+¶8 = en ¶9 (join hi)
- ch 2 §2.5: hi ¶10 = en ¶11+¶12 (split hi)
- ch 2 §2.5: en ¶13 has no hindi
- ch 2 §2.5: en ¶14 has no hindi
- ch 2 §2.5: en ¶15 has no hindi
- ch 2 §2.5: en ¶16 has no hindi
- ch 2 §2.6.1: hi ¶3 = en ¶3+¶4 (split hi)
- ch 2 §2.6.1: hi ¶5 = en ¶6+¶7 (split hi)
- ch 2 §2.7: hi ¶5 = en ¶5+¶6 (split hi)
- ch 2 §2.7: hi ¶10 = en ¶11+¶12 (split hi)
- ch 2 §2.7: hi ¶12+¶13 = en ¶14 (join hi)
- ch 2 §2.8.1: hi ¶3+¶4 = en ¶3 (join hi)
- ch 2 §2.8.2: hi ¶1+¶2 = en ¶1 (join hi)
- ch 2 §2.8.2: hi ¶3 has no english
- ch 2 §2.8.2: hi ¶4 = en ¶2+¶3 (split hi)
- ch 2 §2.8.2: hi ¶6 = en ¶5+¶6 (split hi)
- ch 2 §2.9: hi ¶2+¶3 = en ¶2 (join hi)
- ch 2 §2.9: hi ¶4 has no english
- ch 2 §2.9: hi ¶6+¶7 = en ¶4 (join hi)
- ch 2 §2.9: hi ¶13 = en ¶10+¶11 (split hi)
- ch 2 §2.9: en ¶12 has no hindi
- ch 2 §2.9: en ¶13 has no hindi
- ch 2 §2.9: en ¶14 has no hindi
- ch 2 §2.9: en ¶15 has no hindi
- ch 2 §2.9: en ¶16 has no hindi
- ch 2 §2.9: en ¶17 has no hindi
- ch 2 §2.9: en ¶18 has no hindi
- ch 2 §2.9: en ¶19 has no hindi
- ch 2 §2.9: en ¶20 has no hindi
- ch 2 §2.9.1: hi ¶1 has no english
- ch 2 §2.9.1: hi ¶2 has no english
- ch 2 §2.9.1: hi ¶3 has no english
- ch 2 §2.9.1: hi ¶4 has no english
- ch 2 §2.9.1: hi ¶5 has no english
- ch 2 §2.9.1: hi ¶6 has no english
- ch 2 §2.9.1: hi ¶7 has no english
- ch 2 §2.9.1: hi ¶8 has no english
- ch 2 §2.9.1: hi ¶9 has no english
- ch 2 §2.9.1: hi ¶10 has no english
- ch 2 §2.12: hi ¶1 = en ¶1+¶2 (split hi)
- ch 2 §2.12: hi ¶2 = en ¶3+¶4 (split hi)
- ch 2 §2.12: hi ¶3 = en ¶5+¶6 (split hi)
- ch 2 §2.13: hi ¶2 = en ¶2+¶3 (split hi)
- ch 2 §2.14.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 2 §2.14.2: hi ¶2 = en ¶3+¶4 (split hi)
- ch 2 §2.14.2: hi ¶4 has no english
- ch 2 §2.15: hi ¶1 = en ¶1+¶2 (split hi)
- ch 2 §2.15: hi ¶3 = en ¶4+¶5 (split hi)
- ch 2 §2.15: en ¶9 has no hindi
- ch 2 §2.15: hi ¶9 = en ¶12+¶13 (split hi)
- ch 3 §3.4: hi ¶5+¶6 = en ¶5 (join hi)
- ch 3 §3.5: hi ¶4 = en ¶4+¶5 (split hi)
- ch 3 §3.5: en ¶6 has no hindi
- ch 3 §3.5: en ¶7 has no hindi
- ch 3 §3.5: hi ¶5 = en ¶8+¶9 (split hi)
- ch 3 §3.5: en ¶11 has no hindi
- ch 3 §3.5: hi ¶10 = en ¶15+¶16 (split hi)
- ch 3 §3.5: hi ¶11 = en ¶17+¶18 (split hi)
- ch 3 §3.5: en ¶19 has no hindi
- ch 3 §3.5: en ¶20 has no hindi
- ch 3 §3.5: en ¶21 has no hindi
- ch 3 §3.5: en ¶22 has no hindi
- ch 3 §3.5: hi ¶12 = en ¶23+¶24 (split hi)
- ch 3 §3.5: hi ¶13 = en ¶25+¶26 (split hi)
- ch 3 §3.5: en ¶27 has no hindi
- ch 3 §3.5.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 3 §3.6: hi ¶1 = en ¶1+¶2 (split hi)
- ch 3 §3.8: hi ¶6+¶7 = en ¶6 (join hi)
- ch 3 §3.8: hi ¶8 = en ¶7+¶8 (split hi)
- ch 3 §3.8: hi ¶10 = en ¶10+¶11 (split hi)
- ch 3 §3.8: hi ¶11 = en ¶12+¶13 (split hi)
- ch 3 §3.10: hi ¶7+¶8 = en ¶7 (join hi)
- ch 3 §3.11: hi ¶6+¶7 = en ¶6 (join hi)
- ch 3 §3.11: hi ¶8 = en ¶7+¶8 (split hi)
- ch 3 §3.11: hi ¶9 = en ¶9+¶10 (split hi)
- ch 3 §3.11: hi ¶10+¶11 = en ¶11 (join hi)
- ch 3 §3.12: hi ¶3+¶4 = en ¶3 (join hi)
- ch 3 §3.12: hi ¶6 = en ¶5+¶6 (split hi)
- ch 3 §3.12: hi ¶14+¶15 = en ¶14 (join hi)
- ch 3 §3.12: hi ¶16+¶17 = en ¶15 (join hi)
- ch 4 §4.2.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 4 §4.2.3: hi ¶2 = en ¶2+¶3 (split hi)
- ch 4 §4.3: hi ¶4+¶5 = en ¶4 (join hi)
- ch 4 §4.5: hi ¶4+¶5 = en ¶4 (join hi)
- ch 4 §4.5: hi ¶6 = en ¶5+¶6 (split hi)
- ch 4 §4.6: hi ¶9+¶10 = en ¶9 (join hi)
- ch 4 §4.6: en ¶11 has no hindi
- ch 4 §4.6: hi ¶13+¶14 = en ¶13 (join hi)
- ch 4 §4.6: hi ¶17 = en ¶16+¶17 (split hi)
- ch 4 §4.8: hi ¶6 = en ¶6+¶7 (split hi)
- ch 4 §4.8: en ¶11 has no hindi
- ch 4 §4.8: hi ¶16 has no english
- ch 4 §4.8: hi ¶17 = en ¶18+¶19 (split hi)
- ch 4 §4.8: hi ¶18 = en ¶20+¶21 (split hi)
- ch 4 §4.9.2: hi ¶1 = en ¶1+¶2 (split hi)
- ch 4 §4.9.2: hi ¶2 = en ¶3+¶4 (split hi)
- ch 4 §4.10: hi ¶2 = en ¶2+¶3 (split hi)
- ch 4 §4.10: hi ¶3 = en ¶4+¶5 (split hi)
- ch 4 §4.10: hi ¶10 = en ¶12+¶13 (split hi)
- ch 4 §4.10: hi ¶11 = en ¶14+¶15 (split hi)
- ch 5 §5.1: hi ¶3 = en ¶3+¶4 (split hi)
- ch 5 §5.2.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 5 §5.2.1: hi ¶5+¶6 = en ¶6 (join hi)
- ch 5 §5.2.3: hi ¶2+¶3 = en ¶2 (join hi)
- ch 5 §5.2.3: hi ¶8+¶9 = en ¶7 (join hi)
- ch 5 §5.2.4: hi ¶1 = en ¶1+¶2 (split hi)
- ch 5 §5.2.4: hi ¶3 = en ¶4+¶5 (split hi)
- ch 5 §5.2.4: en ¶6 has no hindi
- ch 5 §5.2.4: en ¶7 has no hindi
- ch 5 §5.2.4: hi ¶5+¶6 = en ¶9 (join hi)
- ch 5 §5.2.4: hi ¶7 = en ¶10+¶11 (split hi)
- ch 5 §5.2.4: en ¶12 has no hindi
- ch 5 §5.3: hi ¶4 = en ¶4+¶5 (split hi)
- ch 5 §5.3: en ¶6 has no hindi
- ch 5 §5.3: en ¶8 has no hindi
- ch 5 §5.3: hi ¶13 = en ¶16+¶17 (split hi)
- ch 5 §5.3: en ¶18 has no hindi
- ch 5 §5.4: hi ¶2+¶3 = en ¶2 (join hi)
- ch 5 §5.4: hi ¶6+¶7 = en ¶5 (join hi)
- ch 5 §5.4: hi ¶8+¶9 = en ¶6 (join hi)
- ch 5 §5.5.3: hi ¶1 = en ¶1+¶2 (split hi)
- ch 6 §6.4: hi ¶4 has no english
- ch 6 §6.4: hi ¶5 = en ¶4+¶5 (split hi)
- ch 6 §6.4: en ¶10 has no hindi
- ch 6 §6.4: hi ¶11 = en ¶12+¶13 (split hi)
- ch 6 §6.4: hi ¶13+¶14 = en ¶15 (join hi)
- ch 6 §6.4: hi ¶16+¶17 = en ¶17 (join hi)
- ch 6 §6.5: hi ¶4+¶5 = en ¶4 (join hi)
- ch 6 §6.5: hi ¶12+¶13 = en ¶11 (join hi)
- ch 6 §6.5: hi ¶15+¶16 = en ¶13 (join hi)
- ch 6 §6.5: hi ¶17 = en ¶14+¶15 (split hi)
- ch 6 §6.6: hi ¶3+¶4 = en ¶3 (join hi)
- ch 6 §6.6: hi ¶5+¶6 = en ¶4 (join hi)
- ch 6 §6.6: hi ¶10 = en ¶8+¶9 (split hi)
- ch 6 §6.6: hi ¶12 = en ¶11+¶12 (split hi)
- ch 6 §6.7.1: hi ¶8 = en ¶8+¶9 (split hi)
- ch 6 §6.7.1: hi ¶13+¶14 = en ¶14 (join hi)
- ch 6 §6.7.2: hi ¶7+¶8 = en ¶7 (join hi)
- ch 6 §6.7.2: en ¶11 has no hindi
- ch 7 §7.2: hi ¶2+¶3 = en ¶2 (join hi)
- ch 7 §7.2: en ¶13 has no hindi
- ch 7 §7.5: hi ¶3 = en ¶3+¶4 (split hi)
- ch 7 §7.5: hi ¶4 = en ¶5+¶6 (split hi)
- ch 7 §7.5: hi ¶5 = en ¶7+¶8 (split hi)
- ch 7 §7.5: hi ¶6 = en ¶9+¶10 (split hi)
- ch 7 §7.6.1: hi ¶6 = en ¶6+¶7 (split hi)
- ch 7 §7.6.1: hi ¶7 = en ¶8+¶9 (split hi)
- ch 7 §7.6.2: hi ¶9+¶10 = en ¶9 (join hi)
- ch 7 §7.6.2: hi ¶11 = en ¶10+¶11 (split hi)
- ch 7 §7.7: hi ¶2+¶3 = en ¶2 (join hi)
- ch 7 §7.7: hi ¶4+¶5 = en ¶3 (join hi)
- ch 7 §7.7: hi ¶10 = en ¶8+¶9 (split hi)
- ch 7 §7.7: hi ¶11 = en ¶10+¶11 (split hi)
- ch 7 §7.7: hi ¶20 = en ¶20+¶21 (split hi)
- ch 7 §7.7: hi ¶21 = en ¶22+¶23 (split hi)
- ch 8 §8.2: hi ¶2+¶3 = en ¶2 (join hi)
- ch 8 §8.2: hi ¶4 = en ¶3+¶4 (split hi)
- ch 8 §8.2: hi ¶5 = en ¶5+¶6 (split hi)
- ch 8 §8.2: hi ¶6 = en ¶7+¶8 (split hi)
- ch 8 §8.3.1: hi ¶2 = en ¶2+¶3 (split hi)
- ch 8 §8.3.2: hi ¶2 = en ¶2+¶3 (split hi)
- ch 8 §8.3.2: hi ¶10 = en ¶11+¶12 (split hi)
- ch 8 §8.3.2: hi ¶11 = en ¶13+¶14 (split hi)
- ch 8 §8.3.2: en ¶15 has no hindi
- ch 8 §8.3.2: en ¶16 has no hindi

## pairs weaker than 0.45 — read both against the pages

- ch 1 §1.2: hi ¶6 = en ¶6 — similarity 0.44 — en «A simple apparatus to detect charge on a…» · hi «आवेशों की उपस्थिति के संसूचन के लिए एक…»
- ch 1 §1.4.3: hi ¶12 = en ¶12 — similarity 0.44 — en «Example 1.2 How much positive and negative charge…» · hi «उदाहरण 1.2 एक कप जल में कितने धन…»
- ch 1 §1.7: hi ¶10 = en ¶9 — similarity 0.44 — en «(iii) For a positive charge, the electric field…» · hi «(iii) धनावेश के कारण विद्युत क्षेत्र आवेश से…»
- ch 1 §1.12: hi ¶5 = en ¶5 — similarity 0.43 — en «* At the microscopic level, charge distribution is…» · hi «* सूक्ष्मदर्शीय स्तर पर, आवेश वितरण असंतत होता…»
- ch 2 §2.1: hi ¶11 = en ¶11 — similarity 0.44 — en «Two important comments may be made at this…» · hi «इस घटनाक्रम में दो महत्वपूर्ण टिप्पणियाँ की जा…»
- ch 2 §2.5: hi ¶9 = en ¶10 — similarity 0.39 — en «Example 2.3 Figures 2.8 (a) and (b) show…» · hi «उदाहरण 2.3 (a) तथा (b) में क्रमशः एकल…»
- ch 2 §2.6: hi ¶4 = en ¶4 — similarity 0.34 — en «If the field were not normal to the…» · hi «यदि विद्युत क्षेत्र समविभव पृष्ठ के अभिलंबवत नहीं…»
- ch 2 §2.6: hi ¶5 = en ¶5 — similarity 0.45 — en «For a uniform electric field E, say, along…» · hi «किसी अक्ष के अनुदिश, मान लीजिए x-अक्ष के…»
- ch 2 §2.6.1: hi ¶4 = en ¶5 — similarity 0.37 — en «We thus arrive at two important conclusions concerning…» · hi «स्पष्ट है कि delta V ऋणात्मक है, delta…»
- ch 2 §2.9: hi ¶5 = en ¶3 — similarity 0.44 — en «2. At the surface of a charged conductor,…» · hi «यदि E पृष्ठ के अभिलंबवत नहीं है तो…»
- ch 2 §2.9: hi ¶8 = en ¶5 — similarity 0.44 — en «4. Electrostatic potential is constant throughout the volume…» · hi «4. चालक के समस्त आयतन में स्थिरवैद्युत विभव…»
- ch 2 §2.9: hi ¶9 = en ¶6 — similarity 0.33 — en «In a system of conductors of arbitrary size,…» · hi «यह उपरोक्त परिणाम 1 तथा 2 का अनुवर्ती…»
- ch 2 §2.10: hi ¶1 = en ¶1 — similarity 0.42 — en «Dielectrics are non-conducting substances. In contrast to conductors,…» · hi «परावैद्युत अचालक पदार्थ होते हैं। चालकों की तुलना…»
- ch 2 §2.10: hi ¶3 = en ¶3 — similarity 0.44 — en «In an external electric field, the positive and…» · hi «किसी बाह्य विद्युत क्षेत्र में अध्रुवी अणु के…»
- ch 2 §2.10: hi ¶4 = en ¶4 — similarity 0.40 — en «A dielectric with polar molecules also develops a…» · hi «ध्रुवी अणुओं का कोई परावैद्युत किसी बाह्य क्षेत्र…»
- ch 2 §2.10: hi ¶7 = en ¶7 — similarity 0.37 — en «The question is: how does the polarised dielectric…» · hi «अब प्रश्न यह है कि कोई ध्रुवित परावैद्युत…»
- ch 2 §2.10: hi ¶8 = en ¶8 — similarity 0.42 — en «Thus, the polarised dielectric is equivalent to two…» · hi «अतः ध्रुवित परावैद्युत दो आवेशित पृष्ठों के तुल्य…»
- ch 2 §2.13: hi ¶6 = en ¶7 — similarity 0.44 — en «Thus, the dielectric constant of a substance is…» · hi «इस प्रकार किसी पदार्थ का परावैद्युतांक एक कारक…»
- ch 3 §3.12: hi ¶10 = en ¶10 — similarity 0.39 — en «Solution The network is not reducible to a…» · hi «हल परिपथ जाल को प्रतिरोधकों के सरल श्रेणी…»
- ch 4 §4.6: hi ¶5 = en ¶5 — similarity 0.26 — en «The above result for the infinite wire is…» · hi «उपरोक्त परिणाम अनंत लंबाई के तार के लिए…»
- ch 4 §4.6: hi ¶7 = en ¶7 — similarity 0.44 — en «(ii) The field direction at any point on…» · hi «(ii) इस वृत्त के किसी भी बिंदु पर…»
- ch 4 §4.7: hi ¶1 = en ¶1 — similarity 0.44 — en «We shall discuss a long solenoid. By long…» · hi «हम यहाँ एक लंबी परिनालिका के विषय में…»
- ch 4 §4.9.1: hi ¶20 = en ¶20 — similarity 0.44 — en «(b) A current-carrying circular loop is located in…» · hi «(b) कोई विद्युत वाही वृत्ताकार पाश किसी एकसमान…»
- ch 4 §4.9.1: hi ¶21 = en ¶21 — similarity 0.44 — en «(c) A loop of irregular shape carrying current…» · hi «(c) अनियमित आकृति का कोई विद्युत धारावाही पाश…»
- ch 4 §4.9.2: hi ¶5 = en ¶7 — similarity 0.43 — en «The results obtained above can be shown to…» · hi «उपरोक्त परिणाम किसी भी समतल पाश पर लागू…»
- ch 5 §5.2.1: hi ¶1 = en ¶1 — similarity 0.42 — en «The pattern of iron filings permits us to…» · hi «लौह रेतन के बने पैटर्नों के आधार पर…»
- ch 5 §5.2.1: hi ¶3 = en ¶4 — similarity 0.44 — en «(ii) The tangent to the field line at…» · hi «(ii) क्षेत्र रेखा के किसी बिंदु पर खींची…»
- ch 5 §5.2.2: hi ¶2 = en ¶2 — similarity 0.42 — en «The resemblance of magnetic field lines for a…» · hi «एक छड़ चुंबक की चुंबकीय क्षेत्र रेखाओं की,…»
- ch 5 §5.2.2: hi ¶3 = en ¶3 — similarity 0.39 — en «To make this analogy more firm we may…» · hi «इस साम्यता को और अधिक सुदृढ़ करने के…»
- ch 5 §5.2.2: hi ¶5 = en ¶5 — similarity 0.32 — en «This is also the far axial magnetic field…» · hi «यही समीकरण छड़ चुंबक की अक्ष पर दूर…»
- ch 5 §5.2.3: hi ¶1 = en ¶1 — similarity 0.43 — en «Let's place a small compass needle of known…» · hi «हम एक पतली चुंबकीय सुई का, जिसका चुंबकीय…»
- ch 5 §5.2.3: hi ¶4 = en ¶3 — similarity 0.45 — en «An expression for magnetic potential energy can be…» · hi «चुंबकीय स्थितिज ऊर्जा के लिए व्यंजक प्राप्त करने…»
- ch 5 §5.2.3: hi ¶12 = en ¶10 — similarity 0.41 — en «(b) No force if the field is uniform.…» · hi «(b) यदि क्षेत्र एकसमान हों केवल तभी चुंबक…»
- ch 5 §5.3: hi ¶14 = en ¶19 — similarity 0.43 — en «(c) Does a bar magnet exert a torque…» · hi «(c) क्या कोई छड़ चुंबक अपने क्षेत्र की…»
- ch 5 §5.3: hi ¶15 = en ¶20 — similarity 0.45 — en «(d) Magnetic field arises due to charges in…» · hi «(d) गतिमान आवेशों के कारण चुंबकीय क्षेत्र उत्पन्न…»
- ch 6 §6.4: hi ¶2 = en ¶2 — similarity 0.44 — en «The motion of a magnet towards or away…» · hi «प्रयोग 6.1 में कुंडली C_1 की ओर अथवा…»
- ch 6 §6.7.1: hi ¶11 = en ¶12 — similarity 0.42 — en «It is also important to know that the…» · hi «यह जानना भी महत्वपूर्ण है कि कुंडलियों, परिनालिकाओं…»
- ch 7 §7.4: hi ¶6 = en ¶6 — similarity 0.45 — en «The dimension of inductive reactance is the same…» · hi «प्रेरकीय प्रतिघात की विमाएँ वही हैं तो प्रतिरोध…»
- ch 7 §7.4: hi ¶10 = en ¶10 — similarity 0.42 — en «Thus, the average power supplied to an inductor…» · hi «इसलिए एक पूरे चक्र में किसी प्रेरक को…»
- ch 7 §7.7: hi ¶22 = en ¶24 — similarity 0.42 — en «You can see that in the present case,…» · hi «प्रस्तुत प्रकरण में आप देख सकते हैं कि…»

## numbers one edition of a pair carries and the other does not

- ch 1 §1.4.3 ¶3: only in en — · only in hi 1, 11
- ch 1 §1.4.3 ¶10: only in en — · only in hi 1, 1, 1
- ch 1 §1.4.3 ¶11: only in en — · only in hi 1
- ch 1 §1.5 ¶5: only in en — · only in hi 1
- ch 1 §1.5 ¶13: only in en — · only in hi 1.3
- ch 1 §1.6 ¶3: only in en 1, 1, 1, 1, 2, 2, 3, 3, 3, 4, 0, 13, 13, 13, 13 · only in hi —
- ch 1 §1.6 ¶14: only in en 2, 3, 3 · only in hi —
- ch 1 §1.13 ¶3: only in en 0, 0, 0, 0 · only in hi —
- ch 1 §1.14.1 ¶3: only in en — · only in hi 2
- ch 1 §1.14.3 ¶8: only in en — · only in hi 11
- ch 2 §2.1 ¶1: only in en — · only in hi 11
- ch 2 §2.1 ¶12: only in en — · only in hi 2.2
- ch 2 §2.3 ¶3: only in en 0, 0, 0 · only in hi —
- ch 2 §2.3 ¶4: only in en 0 · only in hi —
- ch 2 §2.4 ¶2: only in en 1, 0, 2 · only in hi —
- ch 2 §2.4 ¶3: only in en — · only in hi 1, 2
- ch 2 §2.4 ¶9: only in en 0 · only in hi —
- ch 2 §2.4 ¶15: only in en 2.5 · only in hi 2.4
- ch 2 §2.5 ¶6: only in en 0, 0 · only in hi —
- ch 2 §2.5 ¶10: only in en 2.8 · only in hi —
- ch 2 §2.6.1 ¶5: only in en — · only in hi 2.20, 2.21
- ch 2 §2.7 ¶1: only in en 0, 0 · only in hi 1
- ch 2 §2.7 ¶2: only in en 0 · only in hi —
- ch 2 §2.7 ¶4: only in en 0 · only in hi —
- ch 2 §2.7 ¶7: only in en 0 · only in hi —
- ch 2 §2.7 ¶13: only in en 0, 0, 0 · only in hi —
- ch 2 §2.8.1 ¶4: only in en 1, 117 · only in hi 11, 119
- ch 2 §2.8.3 ¶5: only in en — · only in hi 2.34, 2.32
- ch 2 §2.8.3 ¶9: only in en 1 · only in hi —
- ch 2 §2.9 ¶3: only in en 2 · only in hi —
- ch 2 §2.9 ¶5: only in en 1, 2, 0 · only in hi —
- ch 2 §2.9 ¶6: only in en — · only in hi 1, 2, 0
- ch 2 §2.9 ¶9: only in en 0, 0 · only in hi —
- ch 2 §2.11 ¶2: only in en 1 · only in hi —
- ch 2 §2.11 ¶4: only in en — · only in hi 1
- ch 2 §2.13 ¶1: only in en 2.10, 0 · only in hi —
- ch 2 §2.13 ¶6: only in en 2, 51 · only in hi 1, 2.51
- ch 2 §2.14.1 ¶2: only in en — · only in hi 1, 2
- ch 2 §2.15 ¶8: only in en 2.31 · only in hi —
- ch 3 §3.4 ¶2: only in en 3.2 · only in hi —
- ch 3 §3.4 ¶6: only in en — · only in hi 3.9
- ch 3 §3.4 ¶7: only in en — · only in hi 3.10
- ch 3 §3.4 ¶8: only in en — · only in hi 3.11
- ch 3 §3.5 ¶2: only in en 3.3 · only in hi —
- ch 3 §3.5 ¶12: only in en — · only in hi 1
- ch 3 §3.8 ¶2: only in en 3.8 · only in hi —
- ch 3 §3.8 ¶14: only in en 0, 0, 0 · only in hi —
- ch 3 §3.10 ¶1: only in en — · only in hi 3.12
- ch 3 §3.11 ¶5: only in en — · only in hi 3.44, 3.43
- ch 3 §3.11 ¶17: only in en — · only in hi 3.52, 3.53
- ch 3 §3.12 ¶12: only in en 3 · only in hi —
- ch 3 §3.13 ¶3: only in en — · only in hi 3.64
- ch 3 §3.13 ¶7: only in en 1 · only in hi 10, 10, 6, 6.0, 23, 63.5, 9.0
- ch 4 §4.2.1 ¶4: only in en 1 · only in hi —
- ch 4 §4.2.2 ¶6: only in en — · only in hi 1
- ch 4 §4.2.2 ¶7: only in en 10, 3.6, 5 · only in hi —
- ch 4 §4.2.3 ¶5: only in en 9.8 · only in hi 9.81
- ch 4 §4.3 ¶1: only in en — · only in hi 11
- ch 4 §4.3 ¶5: only in en 4.6 · only in hi —
- ch 4 §4.4 ¶3: only in en — · only in hi 0, 4
- ch 4 §4.4 ¶10: only in en 0, 0 · only in hi —
- ch 4 §4.5 ¶7: only in en 2, 2, 2, 2, 2, 2, 0, 3, 4.11 · only in hi —
- ch 4 §4.6 ¶1: only in en 4.12 · only in hi —
- ch 4 §4.6 ¶14: only in en — · only in hi 1, 2
- ch 4 §4.7 ¶2: only in en 4.15 · only in hi —
- ch 4 §4.8 ¶12: only in en — · only in hi 1
- ch 4 §4.8 ¶15: only in en 1 · only in hi —
- ch 4 §4.9.1 ¶4: only in en 2 · only in hi —
- ch 4 §4.9.1 ¶15: only in en — · only in hi 2, 10
- ch 4 §4.9.2 ¶5: only in en 1 · only in hi —
- ch 5 §5.2.1 ¶1: only in en 1 · only in hi —
- ch 5 §5.2.3 ¶5: only in en 2 · only in hi —
- ch 5 §5.3 ¶7: only in en 5.6 · only in hi —
- ch 5 §5.4 ¶8: only in en — · only in hi 1
- ch 6 §6.2 ¶5: only in en 6.3 · only in hi —
- ch 6 §6.4 ¶2: only in en — · only in hi 1, 2
- ch 6 §6.6 ¶6: only in en 1 · only in hi —
- ch 6 §6.8 ¶5: only in en 1, 1, 6.14, 90, 270 · only in hi —
- ch 6 §6.8 ¶6: only in en — · only in hi 1, 1, 6.14, 90, 270
- ch 6 §6.8 ¶7: only in en — · only in hi 7
- ch 6 §6.8 ¶8: only in en 5 · only in hi 50
- ch 7 §7.2 ¶5: only in en 7.4 · only in hi 7, 4
- ch 7 §7.2 ¶16: only in en 0.454 · only in hi 0.450
- ch 7 §7.4 ¶5: only in en 7.14 · only in hi 7, 14
- ch 7 §7.5 ¶16: only in en 7.9 · only in hi —
- ch 7 §7.6.2 ¶3: only in en 7.16 · only in hi 7.14
- ch 7 §7.7 ¶12: only in en 7.15 · only in hi —

## the ✅ sample: 40 aligned pair(s), seed 16

read each Hindi paragraph against its page and its English partner; 10 of them, the most matra- and conjunct-dense, are the founder's

### 1. ch 1 §1.4.3 ¶4 — en p5, hi p5, similarity 0.77
- en: Thus, there are about 6 × 10^18 electrons in a charge of –1C. In electrostatics, charges of this large magnitude are seldom encountered and hence we use smaller units 1 muC (micro coulomb) = 10^(-6) C or 1 mC (milli coulomb) = 10^(-3) C.
- hi: इस प्रकार, –1C आवेश में लगभग 6 × 10^18 इलेक्ट्रॉन होते हैं। स्थिरवैद्युतिकी में इतने विशाल परिमाण के आवेशों से यदा-कदा ही सामना होता है और इसीलिए हम इसके छोटे मात्रकों 1 mu C (माइक्रोकूलॉम) = 10^-6 C अथवा 1 mC (मिलीकूलॉम) = 10^-3 C का उपयोग करते हैं।

### 2. ch 1 §1.4.3 ¶9 — en p6, hi p6, similarity 0.73
- en: Example 1.1 If 10^9 electrons move out of a body to another body every second, how much time is required to get a total charge of 1 C on the other body?
- hi: उदाहरण 1.1 यदि किसी पिंड से एक सेकंड में 10^9 इलेक्ट्रॉन किसी अन्य पिंड में स्थानांतरित होते हैं तो 1C आवेश के स्थानांतरण में कितना समय लगेगा?

### 3. ch 1 §1.4.3 ¶10 — en p6, hi p6, similarity 0.71
- en: Solution In one second 10^9 electrons move out of the body. Therefore the charge given out in one second is 1.6 × 10^-19 × 10^9 C = 1.6 × 10^-10 C. The time required to accumulate a charge of 1 C can then be estimated to be 1 C ÷ (1.6 × 10^-10 C/s) = 6.25 × 10^9 s = 6.25 × 10^9 ÷ (365 × 24 × 3600) years = 198 years. Thus to collect a charge of one coulomb, from a body from which 10^9 electrons move out every second, we will need approximately 200 years. One coulomb is, therefore, a very large unit for many practical purposes.
- hi: हल 1सेकंड में पिंड से 10^9 इलेक्ट्रॉन निकलते हैं, अतः पिंड द्वारा 1s में दिया जाने वाला आवेश 1.6 × 10^(-19) × 10^9 C = 1.6 × 10^(-10) C तब 1 C आवेश के संचित होने के समय का आकलन 1 C ÷ (1.6 × 10^(-10) C/s) = 6.25 × 10^9 s = 6.25 × 10^9 ÷ (365 × 24 × 3600) वर्ष = 198 वर्ष। इस प्रकार जिस पिंड से 10^9 इलेक्ट्रॉन प्रति सेकंड की दर से उत्सर्जन हो रहा है, उससे 1 C आवेश संचित करने में लगभग 200 वर्ष लगेंगे। अतः बहुत से व्यावहारिक कार्यों की दृष्टि से एक कूलॉम आवेश का एक अति विशाल मात्रक है।

### 4. ch 1 §1.5 ¶3 — en p7, hi p7, similarity 0.60
- en: Coulomb’s law, a simple mathematical statement, was initially experimentally arrived at in the manner described above. While the original experiments established it at a macroscopic scale, it has also been established down to subatomic level (r ~ 10^(-10) m).
- hi: कूलॉम नियम जो कि एक सरल गणितीय कथन है, उस तक आरंभ में, ऊपर वर्णित प्रयोगों के आधार पर पहुँचा गया। यद्यपि इन मूल प्रयोगों ने इसे स्थूल स्तर पर स्थापित किया, अवपरमाणुक स्तर (r ~ 10^(-10) m) तक भी इसे स्थापित किया जा चुका है।

### 5. ch 1 §1.5 ¶10 — en p8, hi p8, similarity 0.52
- en: Since force is a vector, it is better to write Coulomb's law in the vector notation. Let the position vectors of charges q_1 and q_2 be r_1 and r_2 respectively [see Fig.1.3(a)]. We denote force on q_1 due to q_2 by F_12 and force on q_2 due to q_1 by F_21. The two point charges q_1 and q_2 have been numbered 1 and 2 for convenience and the vector leading from 1 to 2 is denoted by r_21: r_21 = r_2 – r_1
- hi: बल एक सदिश है, अतः कूलॉम नियम को सदिश संकेतन में लिखा उत्तम होता है। मान लीजिए q_1 तथा q_2 आवेशों के स्थिति सदिश क्रमशः r_1 तथा r_2 हैं [चित्र 1.3(a) देखिए]। हम q_2 के द्वारा q_1 पर आरोपित बल को F_12 तथा q_1 के द्वारा q_2 पर आरोपित बल को F_21 द्वारा व्यक्त करते हैं। दो बिंदु आवेशों q_1 तथा q_2 को सुविधा के लिए 1 तथा 2 अंक द्वारा व्यक्त किया गया है। साथ ही 1 से 2 की ओर जाते सदिश को r_21 द्वारा व्यक्त किया गया है– r_21 = r_2 – r_1

### 6. ch 1 §1.5 ¶16 — en p9, hi p9, similarity 0.73
- en: · The force F_12 on charge q_1 due to charge q_2, is obtained from Eq. (1.3), by simply interchanging 1 and 2, i.e., F_12 = (1 / 4 pi epsilon_0) (q_1 q_2 / r_12^2) r_hat_12 = −F_21 Thus, Coulomb's law agrees with the Newton's third law.
- hi: q_2 के कारण q_1 पर आरोपित बल F_12 को समीकरण (1.3) में 1 तथा 2 में सरल अंतर्परिवर्तन करके प्राप्त किया जा सकता है, अर्थात F_12 = (1 / 4 pi epsilon_0) (q_1 q_2 / r_12^2) r_hat_12 = −F_21 इस प्रकार, कूलॉम नियम न्यूटन के गति के तृतीय नियम के अनुरूप ही है।

### 7. ch 1 §1.9 ¶5 — en p22, hi p22, similarity 0.48
- en: Notice one ambiguity here. The direction of an area element is along its normal. But a normal can point in two directions. Which direction do we choose as the direction of the vector associated with the area element? This problem is resolved by some convention appropriate to the given context. For the case of a closed surface, this convention is very simple. The vector associated with every area element of a closed surface is taken to be in the direction of the outward normal. This is the convention used in Fig. 1.16. Thus, the area element vector Delta S at a point on a closed surface equals Delta S n_hat where Delta S is the magnitude of the area element and n_hat is a unit vector in the direction of outward normal at that point.
- hi: यहाँ एक संदिग्धता पर ध्यान दीजिए। किसी क्षेत्रफल अवयव की दिशा उसके अभिलंब के अनुदिश होती है। परंतु अभिलंब दो दिशाएँ संकेत कर सकता है। किसी क्षेत्रफल अवयव से संबद्ध सदिश की दिशा का चयन किस प्रकार किया जाता है? इस समस्या का समाधान इस संदर्भ में उचित कुछ परिपाटियों के निर्धारण द्वारा किया गया है। बंद पृष्ठों के प्रकरणों के लिए यह परिपाटी अति सरल है। किसी बंद पृष्ठ के प्रत्येक क्षेत्रफल अवयव से संबद्ध सदिश की दिशा बहिर्मुखी अभिलंब की दिशा मानी जाती है। इसी परिपाटी का उपयोग चित्र 1.16 में किया गया है। इस प्रकार, किसी बंद पृष्ठ के किसी बिंदु पर क्षेत्रफल अवयव सदिश Delta S का मान Delta S n_hat होता है, यहाँ Delta S क्षेत्रफल सदिश का परिमाण तथा n_hat इस बिंदु पर बहिर्मुखी अभिलंब की दिशा में एकांक सदिश है।

### 8. ch 1 §1.11 ¶4 — en p27, hi p27, similarity 0.63
- en: Its direction is normal to the plane of the paper, coming out of it.
- hi: इसकी दिशा कागज़ के तल के अभिलंबवत इससे बाहर की ओर है।

### 9. ch 1 §1.11 ¶9 — en p27–28, hi p27–28, similarity 0.49 — the founder reads this one
- en: This brings us to a common observation in frictional electricity. A comb run through dry hair attracts pieces of paper. The comb, as we know, acquires charge through friction. But the paper is not charged. What then explains the attractive force? Taking the clue from the preceding discussion, the charged comb 'polarises' the piece of paper, i.e., induces a net dipole moment in the direction of field. Further, the electric field due to the comb is not uniform. This non-uniformity of the field makes a dipole to experience a net force on it. In this situation, it is easily seen that the paper should move in the direction of the comb!
- hi: इससे हमारा ध्यान घर्षण विद्युत के सामान्य प्रेक्षणों पर जाता है। शुष्क बालों में फेरी गई कंघी कागज़ के छोटे टुकड़ों को आकर्षित करती है। जैसाकि हम जानते हैं कि कंघी घर्षण द्वारा आवेश अर्जित करती है। परंतु कागज़ आवेशित नहीं है तो फिर इस आकर्षक बल का स्पष्टीकरण कैसे करें? पिछली चर्चा से संकेत पाकर हम कह सकते हैं कि आवेशित कंघी कागज़ के टुकड़ों को ध्रुवित कर देती है, अर्थात कागज़ के टुकड़ों में क्षेत्र की दिशा में नेट द्विध्रुव आघूर्ण प्रेरित कर देती है। इसके अतिरिक्त कंघी के कारण विद्युत क्षेत्र एकसमान नहीं है। इस असमान क्षेत्र के कारण द्विध्रुव पर एक नेट बल कार्यरत हो जाता है। इस स्थिति में यह आसानी से देखा जा सकता है कि कागज़ के टुकड़े कंघी की दिशा में गति करते हैं।

### 10. ch 1 §1.13 ¶1 — en p29, hi p29, similarity 0.51 — the founder reads this one
- en: As a simple application of the notion of electric flux, let us consider the total flux through a sphere of radius r, which encloses a point charge q at its centre. Divide the sphere into small area elements, as shown in Fig. 1.22.
- hi: वैद्युत फ्लक्स की अवधारणा के सरल अनुप्रयोग के रूप में आइए किसी r त्रिज्या के ऐसे गोले जिसके केंद्र पर कोई बिंदु आवेश q परिबद्ध है, से गुजरने वाले कुल फ्लक्स पर विचार करें। चित्र 1.22 में दर्शाए अनुसार इस गोले को छोटे क्षेत्रफल अवयवों में विभाजित करते हैं।

### 11. ch 1 §1.14.2 ¶2 — en p34, hi p34, similarity 0.47 — the founder reads this one
- en: We can take the Gaussian surface to be a rectangular parallelepiped of cross-sectional area A, as shown. (A cylindrical surface will also do.) As seen from the figure, only the two faces 1 and 2 will contribute to the flux; electric field lines are parallel to the other faces and they, therefore, do not contribute to the total flux.
- hi: हम गाउसीय पृष्ठ को चित्र में दर्शाए अनुसार A अनुप्रस्थकाट क्षेत्रफल के आयताकार समांतर षट्फलक जैसा ले सकते हैं (वैसे तो बेलनाकार पृष्ठ से भी यह कार्य हो सकता है)। जैसा चित्र से दृष्टिगोचर होता है, केवल दो फलक 1 तथा 2 ही फ्लक्स में योगदान देंगे; विद्युत क्षेत्र रेखाएँ अन्य फलकों के समांतर हैं और वे इसीलिए कुल फ्लक्स में योगदान नहीं देतीं।

### 12. ch 2 §2.1 ¶2 — en p1, hi p1, similarity 0.49 — the founder reads this one
- en: Coulomb force between two (stationary) charges is also a conservative force. This is not surprising, since both have inverse-square dependence on distance and differ mainly in the proportionality constants – the masses in the gravitational law are replaced by charges in Coulomb’s law. Thus, like the potential energy of a mass in a gravitational field, we can define electrostatic potential energy of a charge in an electrostatic field.
- hi: गुरुत्वाकर्षण बल की भाँति दो स्थिर आवेशों के बीच लगने वाला कूलॉम बल भी संरक्षी बल होता है। यह कोई आश्चर्य की बात नहीं है, क्योंकि गणितीय रूप में यह बल गुरुत्वाकर्षण बल के समान है; दोनों में दूरी की व्युत्क्रम वर्ग निर्भरता है और प्रमुख रूप से आनुपातिकता स्थिरांक में भिन्नता है। गुरुत्वाकर्षण नियम की संहतियाँ कूलॉम नियम में आवेशों द्वारा प्रतिस्थापित हो जाती हैं। इस प्रकार, गुरुत्वीय क्षेत्र में संहतियों की स्थितिज ऊर्जा की ही भाँति हम किसी स्थिरवैद्युत क्षेत्र में आवेश की स्थिरवैद्युत स्थितिज ऊर्जा को परिभाषित कर सकते हैं।

### 13. ch 2 §2.1 ¶9 — en p2, hi p2, similarity 0.54 — the founder reads this one
- en: (Note here that this displacement is in an opposite sense to the electric force and hence work done by electric field is negative, i.e., –W_RP.)
- hi: (ध्यान दीजिए, यहाँ पर यह विस्थापन विद्युत बल के विपरीत है, इसलिए विद्युत क्षेत्र द्वारा किया गया कार्य ऋणात्मक है, अर्थात –W_RP)

### 14. ch 2 §2.5 ¶21 — en p9, hi p9, similarity 0.54
- en: (e) Due to force of repulsion on the negative charge, velocity decreases and hence the kinetic energy decreases in going from B to A.
- hi: (e) ऋणावेश पर प्रतिकर्षी बल लगने के कारण वेग घटता है अतः B से A तक जाने में गतिज ऊर्जा घट जाती है।

### 15. ch 2 §2.8.3 ¶8 — en p17, hi p17, similarity 0.57 — the founder reads this one
- en: Example 2.6 A molecule of a substance has a permanent electric dipole moment of magnitude 10^-29 C m. A mole of this substance is polarised (at low temperature) by applying a strong electrostatic field of magnitude 10^6 V m^-1. The direction of the field is suddenly changed by an angle of 60º. Estimate the heat released by the substance in aligning its dipoles along the new direction of the field. For simplicity, assume 100% polarisation of the sample.
- hi: उदाहरण 2.6 एक पदार्थ के अणु में 10^(-29) cm का स्थायी वैद्युत द्विध्रुव आघूर्ण है। 10^6 V m^-1 परिमाण के एक शक्तिशाली स्थिरवैद्युत क्षेत्र को लगाकर इस पदार्थ के एक मोल (निम्न ताप पर) को ध्रुवित किया गया है। अचानक क्षेत्र की दिशा 60° कोण से बदल दी जाती है। क्षेत्र की नयी दिशा में द्विध्रुवों को पंक्तिबद्ध करने में उन्मुक्त ऊष्मा ऊर्जा का आकलन कीजिए। सुविधा के लिए नमूने का ध्रुवण 100% माना जा सकता है।

### 16. ch 2 §2.10 ¶8 — en p23, hi p23, similarity 0.42 — the founder reads this one
- en: Thus, the polarised dielectric is equivalent to two charged surfaces with induced surface charge densities, say sigma_p and –sigma_p. Clearly, the field produced by these surface charges opposes the external field. The total field in the dielectric is, thereby, reduced from the case when no dielectric is present. We should note that the surface charge density ±sigma_p arises from bound (not free charges) in the dielectric.
- hi: अतः ध्रुवित परावैद्युत दो आवेशित पृष्ठों के तुल्य होता है, जिनके प्रेरित पृष्ठीय आवेश घनत्व, sigma_p तथा –sigma_p हैं। स्पष्ट है कि इन पृष्ठीय आवेशों द्वारा उत्पन्न विद्युत क्षेत्र बाह्य क्षेत्र का विरोध करते हैं। इस प्रकार परावैद्युत में कुल क्षेत्र, उस प्रकरण की तुलना में जिसमें कोई परावैद्युत नहीं है, कम हो जाता है। यहाँ ध्यान देने योग्य बात यह है कि, पृष्ठीय आवेश घनत्व ± sigma_p परावैद्युत में मुक्त आवेशों के कारण नहीं वरन् परिबद्ध आवेशों से उत्पन्न होता है।

### 17. ch 2 §2.11 ¶1 — en p23, hi p23, similarity 0.52
- en: A capacitor is a system of two conductors separated by an insulator (Fig. 2.24). The conductors have charges, say Q_1 and Q_2, and potentials V_1 and V_2. Usually, in practice, the two conductors have charges Q and – Q, with potential difference V = V_1 – V_2 between them. We shall consider only this kind of charge configuration of the capacitor. (Even a single conductor can be used as a capacitor by assuming the other at infinity.) The conductors may be so charged by connecting them to the two terminals of a battery. Q is called the charge of the capacitor, though this, in fact, is the charge on one of the conductors – the total charge of the capacitor is zero.
- hi: कोई संधारित्र विद्युतरोधी द्वारा पृथक दो चालकों का एक निकाय होता है (चित्र 2.24)। चालकों पर आवेश Q_1 तथा Q_2 तथा उनके विभव क्रमशः V_1 तथा V_2 हैं। प्रायः, व्यवहार में, दो चालकों पर आवेश Q तथा –Q होते हैं तथा उनमें विभवांतर V = V_1 – V_2 होता है। हम केवल इसी प्रकार के विन्यास के संधारित्र पर विचार करेंगे। (एक सरल चालक को भी संधारित्र की भाँति प्रयोग किया जा सकता है, यदि दूसरे को अनंत पर माने) दोनों चालकों को किसी बैटरी के दो टर्मिनलों से संयोजित करके आवेशित कराया जा सकता है। Q को संधारित्र का आवेश कहते हैं, यद्यपि, वास्तव में यह संधारित्र के एक चालक पर आवेश होता है–संधारित्र का कुल आवेश शून्य होता है।

### 18. ch 3 §3.8 ¶3 — en p10, hi p10, similarity 0.73
- en: Equation (3.26) thus, can be used approximately over a limited range of T around any reference temperature T_0, where the graph can be approximated as a straight line.
- hi: अतः समीकरण (3.26) को किसी संदर्भ ताप T_0 के लगभग किसी सीमित परिसर में उपयोग कर सकते हैं, जहाँ ग्राफ करीब-करीब एक सरल रेखा होगी।

### 19. ch 3 §3.11 ¶14 — en p16, hi p16, similarity 0.89
- en: Combining the last three equations I = I_1 + I_2 = (epsilon_1 – V) / r_1 + (epsilon_2 – V) / r_2 = (epsilon_1 / r_1 + epsilon_2 / r_2) – V (1/r_1 + 1/r_2) (3.51)
- hi: पिछले तीनों समीकरणों को संयोजित करने पर I = I_1 + I_2 = (epsilon_1 − V)/r_1 + (epsilon_2 − V)/r_2 = (epsilon_1/r_1 + epsilon_2/r_2) − V (1/r_1 + 1/r_2) (3.51)

### 20. ch 3 §3.12 ¶4 — en p17, hi p18, similarity 0.64
- en: (a) Junction rule: At any junction, the sum of the currents entering the junction is equal to the sum of currents leaving the junction (Fig. 3.15).
- hi: (a) संधि नियम–किसी संधि पर संधि से प्रवेश करने वाली विद्युत धाराओं का योग इस संधि से निकलने वाली विद्युत धाराओं के योग के बराबर होता है (चित्र 3.15)।

### 21. ch 4 §4.1 ¶2 — en p1–2, hi p1–2, similarity 0.65
- en: Following this, there was intense experimentation. In 1864, the laws obeyed by electricity and magnetism were unified and formulated by James Maxwell who then realised that light was electromagnetic waves. Radio waves were discovered by Hertz, and produced by J.C.Bose and G. Marconi by the end of the 19^th century. A remarkable scientific and technological progress took place in the 20^th century. This was due to our increased understanding of electromagnetism and the invention of devices for production, amplification, transmission and detection of electromagnetic waves.
- hi: इसके पश्चात प्रयोगों की गति तीव्र हो गई। सन 1864 में विद्युत तथा चुंबकत्व के सर्वमान्य नियमों को जेम्स मैक्सवेल ने एकीकृत करके नए नियम बनाए और यह स्पष्ट अनुभव किया कि प्रकाश वास्तव में विद्युत चुंबकीय तरंगें हैं। हर्ट्ज़ ने रेडियो तरंगों की खोज की तथा 19वीं शताब्दी के अंत तक सर जे.सी. बोस तथा मार्कोनी ने इन तरंगों को उत्पन्न किया। 20वीं शताब्दी में विज्ञान तथा प्रौद्योगिकी में आश्चर्यजनक प्रगति हुई है। यह प्रगति विद्युत चुंबकत्व के हमारे बढ़ते ज्ञान तथा विद्युत चुंबकीय तरंगों को उत्पन्न, प्रवर्धित, प्रेषित तथा संसूचित करने वाली युक्तियों की खोज के कारण हुई है।

### 22. ch 4 §4.3 ¶2 — en p6, hi p6, similarity 0.48
- en: We shall consider motion of a charged particle in a uniform magnetic field. First consider the case of v perpendicular to B. The perpendicular force, q v × B, acts as a centripetal force and produces a circular motion perpendicular to the magnetic field. The particle will describe a circle if v and B are perpendicular to each other (Fig. 4.5).
- hi: हम किसी एकसमान चुंबकीय क्षेत्र में आवेशित कण की गति पर विचार करेंगे। पहले उस स्थिति पर विचार कीजिए जिसमें वेग v चुंबकीय क्षेत्र B के लंबवत है। लंबवत बल q v × B अभिकेंद्र बल की भाँति कार्य करता है तथा चुंबकीय क्षेत्र के लंबवत वर्तुल गति उत्पन्न करता है। यदि v तथा B एक दूसरे के लंबवत हैं, तो कण (अर्थात किसी वृत्त के अनुदिश) वर्तुल गति करेगा (चित्र 4.5)।

### 23. ch 4 §4.4 ¶9 — en p8, hi p8, similarity 0.65
- en: (iv) There is an angle dependence in the Biot-Savart law which is not present in the electrostatic case. In Fig. 4.7, the magnetic field at any point in the direction of dl (the dashed line) is zero. Along this line, theta = 0, sin theta = 0 and from Eq. [4.7(a)], |dB| = 0.
- hi: (iv) बायो-सावर्ट नियम में कोण पर निर्भरता है जो स्थिर वैद्युत क्षेत्र में नहीं होती। चित्र 4.7 में, दिशा dl (डैश युक्त रेखा में किसी भी बिंदु पर चुंबकीय क्षेत्र शून्य है। इस दिशा के अनुदिश theta = 0, sin theta = 0 तथा समीकरण [4.7(a)], |dB| = 0

### 24. ch 4 §4.8 ¶14 — en p18, hi p18, similarity 0.54
- en: The SI unit of charge, namely, the coulomb, can now be defined in terms of the ampere.
- hi: आवेश के SI मात्रक, अर्थात कूलॉम को अब हम ऐम्पियर के पदों में परिभाषित कर सकते हैं।

### 25. ch 4 §4.8 ¶16 — en p18, hi p18, similarity 0.63
- en: Example 4.9 The horizontal component of the earth's magnetic field at a certain place is 3.0 ×10^-5 T and the direction of the field is from the geographic south to the geographic north. A very long straight conductor is carrying a steady current of 1A. What is the force per unit length on it when it is placed on a horizontal table and the direction of the current is (a) east to west; (b) south to north?
- hi: उदाहरण 4.9 किसी निर्धारित स्थान पर पृथ्वी के चुंबकीय क्षेत्र का क्षैतिज घटक 3.0×10^(-5) T है, तथा इस क्षेत्र की दिशा भौगोलिक दक्षिण से भौगोलिक उत्तर की ओर है। किसी अत्यधिक लंबे सीधे चालक से 1A की अपरिवर्ती धारा प्रवाहित हो रही है। जब यह तार किसी क्षैतिज मेज़ पर रखा है तथा विद्युत धारा के प्रवाह की दिशाएँ (a) पूर्व से पश्चिम की ओर; (b) दक्षिण से उत्तर की ओर हैं तो तार की प्रत्येक एकांक लंबाई पर बल कितना है?

### 26. ch 4 §4.10 ¶7 — en p24, hi p25, similarity 0.52 — the founder reads this one
- en: A convenient way for the manufacturer to increase the sensitivity is to increase the number of turns N. We choose galvanometers having sensitivities of value, required by our experiment.
- hi: किसी भी उत्पादक के लिए गैल्वेनोमीटर की सुग्राहिता में वृद्धि करने का सरल उपाय यह है कि वह कुंडली में फेरों की संख्या N में वृद्धि कर दे। हम अपने प्रयोग की आवश्यकता के अनुसार गैल्वेनोमीटर का चयन करते हैं।

### 27. ch 5 §5.4 ¶8 — en p11, hi p12, similarity 0.57
- en: Example 5.5 A solenoid has a core of a material with relative permeability 400. The windings of the solenoid are insulated from the core and carry a current of 2A. If the number of turns is 1000 per metre, calculate (a) H, (b) M, (c) B and (d) the magnetising current I_m.
- hi: उदाहरण 5.5 एक परिनालिका के क्रोड में भरे पदार्थ की आपेक्षिक चुंबकशीलता 400 है। परिनालिका के विद्युतीय रूप से पृथक्कृत फेरों में 2A की धारा प्रवाहित हो रही है। यदि इसकी प्रति 1m लंबाई में फेरों की संख्या 1000 है तो (a) H, (b) M, (c) B एवं (d) चुंबककारी धारा I_m की गणना कीजिए।

### 28. ch 5 §5.5.1 ¶4 — en p13, hi p13, similarity 0.55
- en: Some diamagnetic materials are bismuth, copper, lead, silicon, nitrogen (at STP), water and sodium chloride. Diamagnetism is present in all the substances. However, the effect is so weak in most cases that it gets shifted by other effects like paramagnetism, ferromagnetism, etc.
- hi: कुछ प्रतिचुंबकीय पदार्थ हैं– बिस्मथ, ताँबा, सीसा, सिलिकन, नाइट्रोजन (STP पर), पानी एवं सोडियम क्लोराइड। प्रति चुंबकत्व सभी पदार्थों में विद्यमान होता है। परंतु, अधिकांश पदार्थों के लिए यह इतना क्षीण होता है कि अनुचुंबकत्व एवं लौह चुंबकत्व जैसे प्रभाव इस पर हावी हो जाते हैं।

### 29. ch 5 §5.5.2 ¶2 — en p13, hi p13, similarity 0.49
- en: The individual atoms (or ions or molecules) of a paramagnetic material possess a permanent magnetic dipole moment of their own. On account of the ceaseless random thermal motion of the atoms, no net magnetisation is seen. In the presence of an external field B_0, which is strong enough, and at low temperatures, the individual atomic dipole moment can be made to align and point in the same direction as B_0. Figure 5.7(b) shows a bar of paramagnetic material placed in an external field. The field lines gets concentrated inside the material, and the field inside is enhanced. In most cases, this enhancement is slight, being one part in 10^5. When placed in a non-uniform magnetic field, the bar will tend to move from weak field to strong.
- hi: किसी अनुचुंबकीय पदार्थ के परमाणुओं (या आयनों या अणुओं) का अपना स्वयं का स्थायी चुंबकीय द्विध्रुव आघूर्ण होता है। परमाणुओं की सतत यादृच्छिक तापीय गति के कारण कोई परिणामी चुंबकीकरण दृष्टिगत नहीं होता। पर्याप्त शक्तिशाली बाह्य चुंबकीय क्षेत्र B_0 की उपस्थिति में एवं निम्न तापों पर अलग-अलग परमाणुओं के द्विध्रुव आघूर्ण सरल रेखाओं में और B_0 की दिशा के अनुदिश संरेखित किए जा सकते हैं। चित्र 5.7 (b) बाह्य चुंबकीय क्षेत्र में रखी हुई अनुचुंबकीय पदार्थ की एक छड़ प्रदर्शित करता है। चुंबकीय क्षेत्र रेखाएँ पदार्थ के अंदर संकेंद्रित हो जाती हैं और अंदर चुंबकीय क्षेत्र बढ़ जाता है। अधिकतर मामलों में यह वृद्धि अति न्यून है, 10^5 भागों में एक भाग। असमान चुंबकीय क्षेत्र में रखने पर यह छड़ निम्न क्षेत्र से उच्च क्षेत्र की ओर चलने की चेष्टा करेगी।

### 30. ch 6 §6.2 ¶6 — en p3, hi p3, similarity 0.51 — the founder reads this one
- en: It is observed that the galvanometer shows a momentary deflection when the tapping key K is pressed. The pointer in the galvanometer returns to zero immediately. If the key is held pressed continuously, there is no deflection in the galvanometer. When the key is released, a momentory deflection is observed again, but in the opposite direction. It is also observed that the deflection increases dramatically when an iron rod is inserted into the coils along their axis.
- hi: यह देखा जाता है कि दाब-कुंजी K को दबाने पर धारामापी एक क्षणिक विक्षेप दर्शाता है और फिर इसका संकेतक तत्काल शून्य पर वापस आ जाता है। यदि कुंजी को लगातार दबाकर रखा जाए तो धारामापी में कोई विक्षेप नहीं होता। जब कुंजी को छोड़ा जाता है तो फिर से एक क्षणिक विक्षेप देखा जाता है, लेकिन यह विक्षेप विपरीत दिशा में होता है। यह भी देखा गया है कि यदि कुंडलियों में उनके अक्ष के अनुदिश एक लोहे की छड़ रख दी जाए तो विक्षेप नाटकीय रूप से बढ़ जाता है।

### 31. ch 6 §6.4 ¶16 — en p6, hi p6, similarity 0.54
- en: Example 6.3 A circular coil of radius 10 cm, 500 turns and resistance 2 Ω is placed with its plane perpendicular to the horizontal component of the earth's magnetic field. It is rotated about its vertical diameter through 180° in 0.25 s. Estimate the magnitudes of the emf and current induced in the coil. Horizontal component of the earth's magnetic field at the place is 3.0 × 10^-5 T.
- hi: उदाहरण 6.3 10 cm त्रिज्या, 500 फेरों तथा 2 Omega प्रतिरोध की एक वृत्ताकार कुंडली को इसके तल के लंबवत पृथ्वी के चुंबकीय क्षेत्र के क्षैतिज घटक में रखा गया है। इसे अपने ऊर्ध्व व्यास के परितः 0.25 s में 180° से घुमाया गया कुंडली में प्रेरित विद्युत वाहक बल तथा विद्युत धारा का आकलन कीजिए। दिए गए स्थान पर पृथ्वी के चुंबकीय क्षेत्र के क्षैतिज घटक का मान 3.0 × 10^(-5) T है।

### 32. ch 6 §6.5 ¶16 — en p9, hi p9, similarity 0.52
- en: (c) The induced emf is expected to be constant only in the case of the rectangular loop. In the case of circular loop, the rate of change of area of the loop during its passage out of the field region is not constant, hence induced emf will vary accordingly.
- hi: (c) आयताकार लूप के लिए प्रेरित विद्युत वाहक बल के स्थिर रहने की अपेक्षा कर सकते हैं। वृत्ताकार लूप में क्षेत्र के प्रभाव से बाहर निकलते समय लूप के क्षेत्रफल के परिवर्तन की दर स्थिर नहीं है, अतः प्रेरित विद्युत वाहक बल तदनुसार बदलेगा।

### 33. ch 6 §6.7 ¶1 — en p12, hi p12, similarity 0.57
- en: An electric current can be induced in a coil by flux change produced by another coil in its vicinity or flux change produced by the same coil. These two situations are described separately in the next two sub-sections. However, in both the cases, the flux through a coil is proportional to the current. That is, Phi_B ∝ I.
- hi: एक कुंडली के निकट रखी दूसरी कुंडली में फ्लक्स परिवर्तन से अथवा उसी कुंडली में फ्लक्स परिवर्तन से, उस कुंडली में विद्युत धारा प्रेरित हो सकती है। ये दोनों स्थितियाँ अगले दो उपखंडों में अलग-अलग वर्णित की गई हैं। तथापि, इन दोनों स्थितियों में, कुंडली में फ्लक्स धारा के समानुपाती है। अर्थात् Phi_B ∝ I

### 34. ch 6 §6.7.2 ¶9 — en p16, hi p16, similarity 0.78
- en: Therefore, using Faraday's law, epsilon_1 = -M_11 dI_1/dt - M_12 dI_2/dt M_11 is the self-inductance and is written as L_1. Therefore, epsilon_1 = -L_1 dI_1/dt - M_12 dI_2/dt
- hi: अतः, फैराडे का नियम उपयोग करने पर, epsilon_1 = -M_11 dI_1 / dt - M_12 dI_2 / dt M_11 स्व-प्रेरकत्व है तथा इसे L_1 द्वारा लिखा जाता है। इसलिए, epsilon_1 = -L_1 dI_1 / dt - M_12 dI_2 / dt

### 35. ch 6 §6.8 ¶11 — en p19, hi p19, similarity 0.51
- en: We urge you to explore such alternative possibilities for power generation.
- hi: हम आपसे आग्रह करते हैं कि विद्युत शक्ति उत्पादन के लिए वैकल्पिक संभावनाओं का पता लगाएँ।

### 36. ch 7 §7.2 ¶3 — en p2, hi p2, similarity 0.63
- en: Equation (7.3) is Ohm's law, which for resistors, works equally well for both ac and dc voltages. The voltage across a pure resistor and the current through it, given by Eqs. (7.1) and (7.2) are plotted as a function of time in Fig. 7.2. Note, in particular that both v and i reach zero, minimum and maximum values at the same time. Clearly, the voltage and current are in phase with each other.
- hi: समीकरण (7.3) ओम का नियम है जो प्रतिरोधकों के प्रकरण में ac एवं dc दोनों प्रकार की वोल्टताओं के लिए समान रूप से लागू होता है। समीकरण (7.1) एवं समीकरण (7.2) द्वारा व्यक्त किसी शुद्ध प्रतिरोधक के सिरों के बीच लगाई गई वोल्टता एवं इसमें प्रवाहित होने वाली धारा को चित्र 7.2 में समय के फलन के रूप में आलेखित किया गया है। इस तथ्य पर विशेष ध्यान दीजिए कि v एवं i दोनों ही शून्य, न्यूनतम एवं अधिकतम मानों की स्थितियाँ साथ-साथ ही प्राप्त करती हैं। अतः स्पष्ट है कि वोल्टता एवं धारा एक दूसरे के साथ समान कला में हैं।

### 37. ch 7 §7.8 ¶6 — en p18–19, hi p19, similarity 0.69
- en: But epsilon_p = v_p. If this were not so, the primary current would be infinite since the primary has zero resistance (as assumed). If the secondary is an open circuit or the current taken from it is small, then to a good approximation epsilon_s = v_s where v_s is the voltage across the secondary. Therefore, Eqs. (7.31) and (7.32) can be written as v_s = −N_s dphi / dt [7.31(a)] v_p = −N_p dphi / dt [7.32(a)]
- hi: लेकिन, epsilon_p = v_p यदि ऐसा नहीं होता तो प्रारंभिक कुंडली (जिसका प्रतिरोध हमने शून्य माना है) में अनंत परिमाण की धारा प्रवाहित होती। यदि द्वितीयक कुंडली के सिरे मुक्त हों अथवा इससे बहुत कम धारा ली जा रही हो तो पर्याप्त सन्निकट मान तक epsilon_s = v_s यहाँ v_s द्वितीयक कुंडली के सिरों के बीच वोल्टता है। अतः समीकरणों (7.31) एवं (7.32) को हम इस प्रकार लिख सकते हैं – v_s = −N_s dphi / dt [7.31(a)] v_p = −N_p dphi / dt [7.32(a)]

### 38. ch 8 §8.3.2 ¶1 — en p6, hi p6, similarity 0.49
- en: It can be shown from Maxwell's equations that electric and magnetic fields in an electromagnetic wave are perpendicular to each other, and to the direction of propagation. It appears reasonable, say from our discussion of the displacement current. Consider Fig. 8.2. The electric field inside the plates of the capacitor is directed perpendicular to the plates. The magnetic field this gives rise to via the displacement current is along the perimeter of a circle parallel to the capacitor plates. So B and E are perpendicular in this case. This is a general feature.
- hi: मैक्सवेल के समीकरणों के आधार पर यह दर्शाया जा सकता है कि किसी वैद्युतचुंबकीय तरंग में विद्युतीय एवं चुंबकीय क्षेत्र एक-दूसरे के लंबवत होते हैं और इसके गमन की दिशा के भी। विस्थापन धारा पर दिए गए विवेचन के आधार पर भी यह तर्कसंगत प्रतीत होता है। चित्र 8.2 पर विचार कीजिए। संधारित्र में प्लेटों के बीच विद्युत क्षेत्र प्लेटों के लंबवत है। विस्थापन धारा के द्वारा उत्पन्न चुंबकीय क्षेत्र संधारित्र की प्लेटों के समांतर वृत्त के अनुदिश है। अतः इस स्थिति में B तथा E परस्पर लंबवत हैं। यह एक सामान्य लक्षण है।

### 39. ch 8 §8.4.3 ¶1 — en p10, hi p10, similarity 0.59
- en: Infrared waves are produced by hot bodies and molecules. This band lies adjacent to the low-frequency or long-wave length end of the visible spectrum. Infrared waves are sometimes referred to as heat waves. This is because water molecules present in most materials readily absorb infrared waves (many other molecules, for example, CO_2, NH_3, also absorb infrared waves). After absorption, their thermal motion increases, that is, they heat up and heat their surroundings. Infrared lamps are used in physical therapy. Infrared radiation also plays an important role in maintaining the earth's warmth or average temperature through the greenhouse effect. Incoming visible light (which passes relatively easily through the atmosphere) is absorbed by the earth's surface and re-radiated as infrared (longer wavelength) radiations. This radiation is trapped by greenhouse gases such as carbon dioxide and water vapour. Infrared detectors are used in Earth satellites, both for military purposes and to observe growth of crops. Electronic devices (for example semiconductor light emitting diodes) also emit infrared and are widely used in the remote switches of household electronic systems such as TV sets, video recorders and hi-fi systems.
- hi: अवरक्त तरंगें (Infrared Waves) गर्म पिंडों एवं अणुओं से उत्पन्न होती हैं। यह बैंड दृश्य स्पेक्ट्रम के निम्न आवृत्ति या दीर्घ तरंगदैर्ध्य सिरे से संलग्नित होता है। अवरक्त तरंगों को कभी-कभी ऊष्मा तरंगें भी कहा जाता है। ऐसा इसलिए है क्योंकि अधिकांश पदार्थों में विद्यमान जल के अणु अवरक्त तरंगों को तुरंत अवशोषित कर लेते हैं (कई अन्य अणु, जैसे, CO_2, NH_3, आदि भी अवरक्त तरंगों को अवशोषित कर लेते हैं।) अवशोषण के पश्चात उनकी तापीय गति बढ़ जाती है, अर्थात वे गर्म हो जाते हैं और अपने परिवेश को गर्म करने लगते हैं। अवरक्त लैम्पों का उपयोग कायचिकित्सा में किया जाता है। अवरक्त विकिरण की पृथ्वी की गर्मी अर्थात माध्य ताप बनाए रखने में भी हरित गृह प्रभाव के द्वारा एक अहम भूमिका है। पृथ्वी पर आने वाला दृश्य प्रकाश (जो अपेक्षाकृत सरलतापूर्वक वायुमंडल से गुजर जाता है, पृथ्वी के पृष्ठ द्वारा अवशोषित हो जाता है और दीर्घ तरंगदैर्ध्य की अवरक्त तरंगों के रूप में पुनर्विकिरित हो जाता है। यह विकिरण, कार्बन डाइऑक्साइड एवं जल वाष्प जैसे हरित गृह प्रभावकारी गैसों के द्वारा वायुमंडल में रोक लिया जाता है। उपग्रहों में लगे अवरक्त संसूचकों का उपयोग सैनिक उद्देश्यों एवं फसलों की वृद्धि का प्रेक्षण करने के लिए किया जाता है। इलैक्ट्रॉनिक युक्तियाँ (उदाहरण के लिए प्रकाश उत्सर्जक डायोड) भी अवरक्त तरंगें उत्सर्जित करती हैं और घरेलू इलैक्ट्रॉनिक प्रणालियों जैसे टी.वी. सैट, वीडियो रिकॉर्डर एवं हाई-फाई प्रणालियों के रिमोट नियंत्रकों में ये बहुलता से प्रयोग की जाती हैं।

### 40. ch 8 §8.4.7 ¶2 — en p11, hi p11, similarity 0.53 — the founder reads this one
- en: Table 8.1 summarises different types of electromagnetic waves, their production and detections. As mentioned earlier, the demarcation between different regions is not sharp and there are overlaps.
- hi: सारणी 8.1 में विभिन्न प्रकार की वैद्युतचुंबकीय तरंगों, उनके उत्पादन एवं संसूचन को सार रूप में प्रस्तुत किया गया है। जैसा कि पहले बताया गया है, विभिन्न किरणों के क्षेत्रों के मध्य कोई तीक्ष्ण सीमाएँ नहीं हैं तथा ये दूसरे क्षेत्रों में भी व्यापित होते हैं।

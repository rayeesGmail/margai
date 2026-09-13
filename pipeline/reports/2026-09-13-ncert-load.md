# margai-pipeline ncert load

- run: 2026-09-13 21:47 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## page-break repairs to the model's numbering (deterministic, each one named)

none

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 0 | 106 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 7 | 17 | 12 | 106 | 70.6% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
- ch 7 §7.5 ¶5 continues the sentence ¶4 stopped in the middle of: "…e sphere M_r of radius r is (4/3) π ρ r^3 and" + "hence F = G m ((4/3) ρ r) (r^3 / r^2) = G m (…"
- ch 7 §7.9 ¶5 continues the sentence ¶4 stopped in the middle of: "…s to our aid, T_M^2 / T_E^2 = R_MS^3 / R_ES^3" + "where R_MS is the Mars-Sun distance and R_ES …"

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 143 | 17 | 12 | 106 | — |

## addresses in the database this extraction no longer carries

- ch 7 §7.3 ¶17
- ch 7 §7.3 ¶18
- ch 7 §7.3 ¶19
- ch 7 §7.3 ¶20
- ch 7 §7.3 ¶21
- ch 7 §7.3 ¶22
- ch 7 §7.3 ¶23
- ch 7 §7.3 ¶24
- ch 7 §7.4 ¶7
- ch 7 §7.5 ¶10
- ch 7 §7.5 ¶9
- ch 7 §7.6 ¶12
- ch 7 §7.6 ¶13
- ch 7 §7.6 ¶14
- ch 7 §7.6 ¶15
- ch 7 §7.6 ¶16
- ch 7 §7.6 ¶17
- ch 7 §7.8 ¶19
- ch 7 §7.9 ¶15
- ch 7 §7.9 ¶16
- ch 7 §7.9 ¶17
- ch 7 §7.9 ¶18
- ch 7 §7.9 ¶19
- ch 7 §7.9 ¶20
- ch 7 §7.9 ¶21
- ch 7 §7.9 ¶22
- ch 7 §7.9 ¶23
- ch 7 §7.9 ¶24
- ch 7 §7.9 ¶25

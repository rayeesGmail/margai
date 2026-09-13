# margai-pipeline ncert load

- run: 2026-09-13 23:20 IST
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
| 0 | 7 | 161 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 5 | 21 | 15 | 168 | 71.4% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
- ch 5 §5.1.1 ¶2 continues the sentence ¶1 stopped in the middle of: "…any two vectors A and B, denoted as A.B (read" + "as A dot B) is defined as A.B = A B cos θ (5.…"
- ch 5 §5.1.1 ¶17 continues the sentence ¶16 stopped in the middle of: "…F_x^2 + F_y^2 + F_z^2 = 9 + 16 + 25 = 50 unit" + "and d.d = d^2 = d_x^2 + d_y^2 + d_z^2 = 25 + …"
- ch 5 §5.4 ¶3 continues the sentence ¶2 stopped in the middle of: "…ergy of an object is a measure of the work an" + "object can do by the virtue of its motion. Th…"
- ch 5 §5.6 ¶6 continues the sentence ¶5 stopped in the middle of: "…wton's second law is 'integrated over' and is" + "not available explicitly. Another observation…"
- ch 5 §5.7 ¶8 continues the sentence ¶7 stopped in the middle of: "…r simplicity, in one dimension) the potential" + "energy V(x) is defined if the force F(x) can …"
- ch 5 §5.9 ¶10 continues the sentence ¶9 stopped in the middle of: "… given by 1/2 k x_m^2 − 1/2 k x^2 + 1/2 m v^2" + "where we have invoked the conservation of mec…"
- ch 5 §5.9 ¶11 continues the sentence ¶10 stopped in the middle of: "…ition, x = 0, i.e., 1/2 m v_m^2 = 1/2 k x_m^2" + "where v_m is the maximum speed.…"
- ch 5 §5.9 ¶17 continues the sentence ¶16 stopped in the middle of: "… m v^2 = 1/2 × 10^3 × 5 × 5 K = 1.25 × 10^4 J" + "where we have converted 18 km h^−1 to 5 m s^−…"
- ch 5 §5.9 ¶31 continues the sentence ¶30 stopped in the middle of: "…(− μ m g + [μ^2 m^2 g^2 + m k v^2]^(1/2)) / k" + "where we take the positive square root since …"
- ch 5 §5.9 ¶32 continues the sentence ¶31 stopped in the middle of: "…ng in numerical values we obtain x_m = 1.35 m" + "which, as expected, is less than the result i…"
- ch 5 §5.9 ¶34 continues the sentence ¶33 stopped in the middle of: "…-conservative forces over the path. Note that" + "unlike the conservative force, W_nc depends o…"
- ch 5 §5.11.2 ¶11 continues the sentence ¶10 stopped in the middle of: "…rgy of the neutron is K_1i = (1/2) m_1 v_1i^2" + "while its final kinetic energy from Eq. (5.26…"
- ch 5 §5.11.2 ¶13 continues the sentence ¶12 stopped in the middle of: "…= K_1f / K_1i = ((m_1 - m_2) / (m_1 + m_2))^2" + "while the fractional kinetic energy gained by…"

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 143 | 21 | 15 | 168 | — |

## addresses in the database this extraction no longer carries

- ch 5 §5.10 ¶11
- ch 5 §5.11 ¶4
- ch 5 §5.11 ¶5
- ch 5 §5.2 ¶1
- ch 5 §5.2 ¶2
- ch 5 §5.2 ¶3
- ch 5 §5.2 ¶4
- ch 5 §5.2 ¶5
- ch 5 §5.2 ¶6
- ch 5 §5.2 ¶7
- ch 5 §5.5 ¶8
- ch 5 §5.5 ¶9
- ch 5 §5.6 ¶10
- ch 5 §5.6 ¶11

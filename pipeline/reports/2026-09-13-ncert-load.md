# margai-pipeline ncert load

- run: 2026-09-13 20:41 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## page-break repairs to the model's numbering (deterministic, each one named)

none

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 37 | 929 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 12 | 9 | 76 | 75.0% |
| 2 | 14 | 8 | 61 | 57.1% |
| 3 | 22 | 16 | 138 | 72.7% |
| 4 | 22 | 17 | 134 | 77.3% |
| 5 | 21 | 15 | 159 | 71.4% |
| 6 | 35 | 31 | 289 | 88.6% |
| 7 | 17 | 12 | 109 | 70.6% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
- ch 1 §1.3.2 ¶3 continues the sentence ¶2 stopped in the middle of: "…i sqrt(L / g), have a large (infinite) number" + "of significant figures. The value of π = 3.14…"
- ch 1 §1.3.3 ¶13 continues the sentence ¶12 stopped in the middle of: "…the arithmetic operations may be carried out;" + "otherwise rounding errors can build up. For e…"
- ch 2 §2.2 ¶5 continues the sentence ¶4 stopped in the middle of: "…and the fourth and the fifth columns give the" + "corresponding values of x, i.e. x(t_1) = 0.08…"
- ch 3 §3.9 ¶18 continues the sentence ¶17 stopped in the middle of: "… = 0 during its fall is called the horizontal" + "range, R. It is the distance travelled during…"
- ch 4 §4.5 ¶30 continues the sentence ¶29 stopped in the middle of: "…Now, v = dy / dt = u + gt" + "acceleration, a = dv / dt = g…"
- ch 4 §4.9 ¶5 continues the sentence ¶4 stopped in the middle of: "…m electrical forces. This may seem surprising" + "since we are talking of uncharged and non-mag…"
- ch 4 §4.11 ¶2 continues the sentence ¶1 stopped in the middle of: "…as the environment. We have followed the same" + "method in solved examples. To handle a typica…"
- ch 5 §5.1.1 ¶15 continues the sentence ¶14 stopped in the middle of: "…F_x^2 + F_y^2 + F_z^2 = 9 + 16 + 25 = 50 unit" + "and d.d = d^2 = d_x^2 + d_y^2 + d_z^2 = 25 + …"
- ch 5 §5.1.1 ¶16 continues the sentence ¶15 stopped in the middle of: "…d_x^2 + d_y^2 + d_z^2 = 25 + 16 + 9 = 50 unit" + "cos θ = 16 / (sqrt(50) sqrt(50)) = 16 / 50 = …"
- ch 5 §5.4 ¶3 continues the sentence ¶2 stopped in the middle of: "…ergy of an object is a measure of the work an" + "object can do by the virtue of its motion. Th…"
- ch 5 §5.6 ¶8 continues the sentence ¶7 stopped in the middle of: "…wton's second law is 'integrated over' and is" + "not available explicitly. Another observation…"
- ch 5 §5.7 ¶8 continues the sentence ¶7 stopped in the middle of: "…r simplicity, in one dimension) the potential" + "energy V(x) is defined if the force F(x) can …"
- ch 5 §5.9 ¶10 continues the sentence ¶9 stopped in the middle of: "… given by 1/2 k x_m^2 - 1/2 k x^2 + 1/2 m v^2" + "where we have invoked the conservation of mec…"
- ch 5 §5.9 ¶11 continues the sentence ¶10 stopped in the middle of: "…ition, x = 0, i.e., 1/2 m v_m^2 = 1/2 k x_m^2" + "where v_m is the maximum speed.…"
- ch 5 §5.9 ¶17 continues the sentence ¶16 stopped in the middle of: "… m v^2 = 1/2 × 10^3 × 5 × 5 K = 1.25 × 10^4 J" + "where we have converted 18 km h^-1 to 5 m s^-…"
- ch 5 §5.9 ¶31 continues the sentence ¶30 stopped in the middle of: "…ng in numerical values we obtain x_m = 1.35 m" + "which, as expected, is less than the result i…"
- ch 6 §6.1.1 ¶7 continues the sentence ¶6 stopped in the middle of: "… [Fig.6.5(b)]. You may have observed that the" + "axis of rotation of such a fan has an oscilla…"
- ch 6 §6.2 ¶21 continues the sentence ¶20 stopped in the middle of: "…r_i = x_i i_hat + y_i j_hat + z_i k_hat" + "and R = X i_hat + Y j_hat + Z k_hat…"
- ch 6 §6.2 ¶26 continues the sentence ¶25 stopped in the middle of: "…ations. Since the spacing of the particles is" + "small, we can treat the body as a continuous …"
- ch 6 §6.2 ¶46 continues the sentence ¶45 stopped in the middle of: "…hree squares that make up the L shaped lamina" + "are made of the same material and have the sa…"
- ch 6 §6.8.1 ¶9 continues the sentence ¶8 stopped in the middle of: "…f a balance is a lever. Try to find more such" + "examples and identify the fulcrum, the effort…"
- ch 6 §6.8.2 ¶6 continues the sentence ¶5 stopped in the middle of: "…centre of mass in uniform gravity or gravity-" + "free space. We note that this is true because…"
- ch 6 §6.12 ¶11 continues the sentence ¶10 stopped in the middle of: "…ntre of the circle described by the particle;" + "and L_z = sum l_iz = (sum m_i r_i^2) ω k_hat …"
- ch 7 §7.3 ¶12 continues the sentence ¶11 stopped in the middle of: "… The individual forces in vector notation are" + "cases, a simple law results when you do that:…"
- ch 7 §7.5 ¶5 continues the sentence ¶4 stopped in the middle of: "…e sphere M_r of radius r is (4/3) π ρ r^3 and" + "hence F = G m ((4/3) π r / r^2) = G m (M_E / …"
- ch 7 §7.9 ¶10 continues the sentence ¶9 stopped in the middle of: "…s to our aid, T_M^2 / T_E^2 = R_MS^3 / R_ES^3" + "where R_MS is the Mars-Sun distance and R_ES …"

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 143 | 143 | 108 | 966 | 100.0% |

## addresses in the database this extraction no longer carries

- ch 1 §1.2 ¶10
- ch 1 §1.3 ¶9
- ch 1 §1.3.2 ¶8
- ch 1 §1.4 ¶5
- ch 2 §2.4 ¶30
- ch 2 §2.4 ¶31
- ch 2 §2.4 ¶32
- ch 2 §2.4 ¶33
- ch 2 §2.4 ¶34
- ch 2 §2.4 ¶35
- ch 2 §2.4 ¶36
- ch 2 §2.4 ¶37
- ch 3 §3.10 ¶19
- ch 3 §3.2.2 ¶3
- ch 3 §3.7.1 ¶11
- ch 3 §3.7.1 ¶12
- ch 3 §3.7.1 ¶13
- ch 3 §3.7.2 ¶1
- ch 3 §3.7.2 ¶10
- ch 3 §3.7.2 ¶11
- ch 3 §3.7.2 ¶12
- ch 3 §3.7.2 ¶13
- ch 3 §3.7.2 ¶2
- ch 3 §3.7.2 ¶3
- ch 3 §3.7.2 ¶4
- ch 3 §3.7.2 ¶5
- ch 3 §3.7.2 ¶6
- ch 3 §3.7.2 ¶7
- ch 3 §3.7.2 ¶8
- ch 3 §3.7.2 ¶9
- ch 4 §4.5 ¶34
- ch 4 §4.5 ¶35
- ch 4 §4.5 ¶36
- ch 4 §4.6 ¶1
- ch 4 §4.6 ¶2
- ch 5 §5.1.1 ¶17
- ch 5 §5.11.1 ¶1
- ch 5 §5.11.1 ¶2
- ch 5 §5.11.1 ¶3
- ch 5 §5.5 ¶8
- ch 5 §5.5 ¶9
- ch 6 §6.10 ¶16
- ch 6 §6.11 ¶25
- ch 6 §6.11 ¶26
- ch 6 §6.11 ¶27
- ch 6 §6.11 ¶28
- ch 6 §6.11 ¶29
- ch 6 §6.12 ¶21
- ch 6 §6.4 ¶15
- ch 6 §6.7.3 ¶8
- ch 6 §6.7.5 ¶1
- ch 6 §6.7.5 ¶2
- ch 6 §6.8 ¶24
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
- ch 7 §7.7 ¶13
- ch 7 §7.7 ¶14
- ch 7 §7.9 ¶18
- ch 7 §7.9 ¶19
- ch 7 §7.9 ¶20
- ch 7 §7.9 ¶21
- ch 7 §7.9 ¶22
- ch 7 §7.9 ¶23
- ch 7 §7.9 ¶24
- ch 7 §7.9 ¶25

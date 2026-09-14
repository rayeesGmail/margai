# margai-pipeline ncert load

- run: 2026-09-14 09:17 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## page-break repairs to the model's numbering (deterministic, each one named)

- ch 6 §6.7.4: page 18 opens with "Answer" at ¶5, the number page 17 ended on — a label always begins a new paragraph, so ¶5 and everything after it in §6.7.4 move up by one

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 1023 | 0 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 12 | 9 | 83 | 75.0% |
| 2 | 14 | 8 | 71 | 57.1% |
| 3 | 22 | 16 | 145 | 72.7% |
| 4 | 22 | 17 | 140 | 77.3% |
| 5 | 21 | 15 | 170 | 71.4% |
| 6 | 35 | 31 | 292 | 88.6% |
| 7 | 17 | 12 | 122 | 70.6% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
- ch 1 §1.2 ¶7 continues the sentence ¶6 stopped in the middle of: "…18. The scheme is now for international usage" + "in scientific, technical, industrial and comm…"
- ch 1 §1.3 ¶9 continues the sentence ¶8 stopped in the middle of: "…act number and it can be written as 2.0, 2.00" + "or 2.0000 as required. Similarly, in T = t / …"
- ch 1 §1.3.2 ¶3 continues the sentence ¶2 stopped in the middle of: "…i sqrt(L / g), have a large (infinite) number" + "of significant figures. The value of π = 3.14…"
- ch 2 §2.2 ¶5 continues the sentence ¶4 stopped in the middle of: "…and the fourth and the fifth columns give the" + "corresponding values of x, i.e. x(t_1) = 0.08…"
- ch 2 §2.4 ¶6 continues the sentence ¶5 stopped in the middle of: "…Therefore, x = (1/2) a t^2 + v_0 t" + "or, x = v_0 t + (1/2) a t^2 (2.6)…"
- ch 3 §3.2.2 ¶3 continues the sentence ¶2 stopped in the middle of: "…o be equal. In general, equality is indicated" + "as A = B. Note that in Fig. 3.2(b), vectors A…"
- ch 4 §4.5 ¶10 continues the sentence ¶9 stopped in the middle of: "…In the preceding observations, the vector" + "character of momentum has not been evident. I…"
- ch 4 §4.5 ¶29 continues the sentence ¶28 stopped in the middle of: "…Now, v = d y / d t = u + g t" + "acceleration, a = d v / d t = g…"
- ch 4 §4.6 ¶11 continues the sentence ¶10 stopped in the middle of: "…ody or a system of particles thus cancel away" + "in pairs. This is an important fact that enab…"
- ch 4 §4.9 ¶5 continues the sentence ¶4 stopped in the middle of: "…m electrical forces. This may seem surprising" + "since we are talking of uncharged and non-mag…"
- ch 4 §4.10 ¶2 continues the sentence ¶1 stopped in the middle of: "… for motion of a planet around the sun is the" + "is the static friction that provides the cent…"
- ch 4 §4.11 ¶2 continues the sentence ¶1 stopped in the middle of: "…as the environment. We have followed the same" + "method in solved examples. To handle a typica…"
- ch 5 §5.1.1 ¶15 continues the sentence ¶14 stopped in the middle of: "…F_x^2 + F_y^2 + F_z^2 = 9 + 16 + 25 = 50 unit" + "and d.d = d^2 = d_x^2 + d_y^2 + d_z^2 = 25 + …"
- ch 5 §5.4 ¶3 continues the sentence ¶2 stopped in the middle of: "…ergy of an object is a measure of the work an" + "object can do by the virtue of its motion. Th…"
- ch 5 §5.6 ¶7 continues the sentence ¶6 stopped in the middle of: "…wton's second law is 'integrated over' and is" + "not available explicitly. Another observation…"
- ch 5 §5.7 ¶8 continues the sentence ¶7 stopped in the middle of: "…r simplicity, in one dimension) the potential" + "energy V(x) is defined if the force F(x) can …"
- ch 5 §5.9 ¶10 continues the sentence ¶9 stopped in the middle of: "… given by 1/2 k x_m^2 − 1/2 k x^2 + 1/2 m v^2" + "where we have invoked the conservation of mec…"
- ch 5 §5.9 ¶11 continues the sentence ¶10 stopped in the middle of: "…ition, x = 0, i.e., 1/2 m v_m^2 = 1/2 k x_m^2" + "where v_m is the maximum speed.…"
- ch 5 §5.9 ¶17 continues the sentence ¶16 stopped in the middle of: "… m v^2 = 1/2 × 10^3 × 5 × 5 K = 1.25 × 10^4 J" + "where we have converted 18 km h^-1 to 5 m s^-…"
- ch 5 §5.9 ¶32 continues the sentence ¶31 stopped in the middle of: "…-conservative forces over the path. Note that" + "unlike the conservative force, W_nc depends o…"
- ch 5 §5.11.2 ¶11 continues the sentence ¶10 stopped in the middle of: "…rgy of the neutron is K_1i = (1/2) m_1 v_1i^2" + "while its final kinetic energy from Eq. (5.26…"
- ch 5 §5.11.2 ¶13 continues the sentence ¶12 stopped in the middle of: "…= K_1f / K_1i = ((m_1 - m_2) / (m_1 + m_2))^2" + "while the fractional kinetic energy gained by…"
- ch 6 §6.1.1 ¶7 continues the sentence ¶6 stopped in the middle of: "… [Fig.6.5(b)]. You may have observed that the" + "axis of rotation of such a fan has an oscilla…"
- ch 6 §6.2 ¶21 continues the sentence ¶20 stopped in the middle of: "…r_i = x_i i_hat + y_i j_hat + z_i k_hat" + "and R = X i_hat + Y j_hat + Z k_hat…"
- ch 6 §6.2 ¶47 continues the sentence ¶46 stopped in the middle of: "…hree squares that make up the L shaped lamina" + "are made of the same material and have the sa…"
- ch 6 §6.8 ¶13 continues the sentence ¶12 stopped in the middle of: "…ent conditions to be satisfied for mechanical" + "equilibrium of a rigid body. In a number of p…"
- ch 6 §6.8.1 ¶7 continues the sentence ¶6 stopped in the middle of: "…f a balance is a lever. Try to find more such" + "examples and identify the fulcrum, the effort…"
- ch 6 §6.8.2 ¶6 continues the sentence ¶5 stopped in the middle of: "…centre of mass in uniform gravity or gravity-" + "free space. We note that this is true because…"
- ch 6 §6.12 ¶11 continues the sentence ¶10 stopped in the middle of: "…ntre of the circle described by the particle;" + "and L_z = sum l_iz = (sum m_i r_i^2) ω k_hat …"
- ch 7 §7.3 ¶21 continues the sentence ¶20 stopped in the middle of: "…s easily done using calculus. For two special" + "cases, a simple law results when you do that:…"
- ch 7 §7.5 ¶5 continues the sentence ¶4 stopped in the middle of: "…e sphere M_r of radius r is (4/3) π ρ r^3 and" + "hence F = Gm((4/3)ρ r^3 / r^2) = Gm(M_E / R_E…"
- ch 7 §7.9 ¶13 continues the sentence ¶12 stopped in the middle of: "…s to our aid, T_M^2 / T_E^2 = R_MS^3 / R_ES^3" + "where R_MS is the Mars-Sun distance and R_ES …"

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 143 | 143 | 108 | 1023 | 100.0% |

## addresses in the database this extraction no longer carries

none

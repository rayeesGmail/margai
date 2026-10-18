# margai-pipeline ncert verify

- run: 2026-10-01 17:39 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 a0704559072ce8ec1cb01acd27fae7247f3ebd21546b8da676ee7e4c584c9f69

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 7 | 13 | 7 | 5 | 8 | 0 | 1 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 7 p3: 9 rows start here, the print starts 9 paragraphs — rows the print does not start: §7.2 ¶11 "Since r_A > r_p, v_p > v_A" · printed starts no row begins with: "equal times to traverse BAC and"
- ch 7 p4: 9 rows start here, the print starts 10 paragraphs — rows the print does not start: §7.3 ¶3 "Every body in the universe attracts every" · §7.3 ¶8 "The total force on m_1 is F_1" · printed starts no row begins with: "(a) What is the force acting" · "(b) What is the force if" · "Take AG = BG = CG"
- ch 7 p5: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.3 ¶14 "(1) The force of attraction between a" · §7.3 ¶15 "(2) The force of attraction due to" · §7.4 ¶1 "The value of the gravitational constant G" · printed starts no row begins with: "F = 2Gm j ̂+ 2Gm" · "this force works out to be" · "the Universal law of gravitation can"
- ch 7 p7: 7 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "This is clearly less than the" · "For , using binomial expression," · "Since mass of a sphere is"
- ch 7 p8: 10 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "Substituting for M from above ,"
- ch 7 p9: 9 rows start here, the print starts 9 paragraphs — rows the print does not start: §7.7 ¶12 "Hence, W(r) = – 4 G m^2" · printed starts no row begins with: "so that once again W ="
- ch 7 p12: 14 rows start here, the print starts 13 paragraphs — rows the print does not start: §7.9 ¶16 "Answer Given k = 10^-13 s^2 m^-3"
- ch 7 p13: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.10 ¶7 "Answer Initially, E_i = − G M_E"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

- ch 7 §7.3 ¶10: figure_refs carries "Fig. 7.5", which the paragraph never mentions

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
none
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (105 rows)
request id: pipeline-ncert-verify-0e4d515a-dcd2-496a-b375-5894d34cb774

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 13 | 13 | 0 |
artefact: verify/phy11-part1/en.sonnet5.jsonl (13 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 13 | 63576 | 4531 | 87012 | 7251 | ₹18.78 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 7 p3 §7.3 ¶1: printed "known then to be about 3.84 x 10^8 m" · transcribed "known then to be about 3.84 × 10^8m"
- ch 7 p10 §7.8 ¶10: printed "r_E replaced by the radius" · transcribed "r_E replaced by the radius of the moon"
- ch 7 p10 §7.8 ¶12: printed "6R - r)^2 = 4r^2" · transcribed "(6R – r)^2 = 4r^2"
- ch 7 p12 §7.10 ¶4: printed "being negative but twice its magnitude" · transcribed "being negative but twice is magnitude"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 7 p13: "SUMMARY"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 7 p3 §7.2 ¶9: printed "traverse BAC and CPB" · transcribed "traverse BAC and CPB ?"
- ch 7 p4 §7.3 ¶6: printed "F_12 = - F_21" · transcribed "F_12 = − F_21"
- ch 7 p4 §7.3 ¶8: printed "F_1 = (G m_2 m_1 / r_21^2) r_hat_21 + (G m_3 m_1 / r_31^2) r_hat_31 + (G m_4 m_1 / r_41^2) r_hat_41" · transcribed "F_1 = (G m_2 m_1 / r_21^2) r_hat_21 + (G m_3 m_1 / r_31^2) r_hat_31 + (G m_4 m_1 / r_41^2) r_hat_41"
- ch 7 p6 §7.5 ¶4: printed "M_E = (4 pi / 3) R_E^3 rho" · transcribed "M_E = (4 pi / 3) R_E^3 rho"
- ch 7 p6 §7.5 ¶4: printed "M_r of radius r is (4 pi/3) rho r^3 and" · transcribed "M_r of radius r is (4 pi / 3) rho r^3 and"
- ch 7 p7 §7.6 ¶4: printed "the result quoted in the previous section" · transcribed "the result quoted in the previous section"
- ch 7 p7 §7.6 ¶4: printed "M_s/M_E = (R_E – d)^3 / R_E^3 (7.16)" · transcribed "M_s/M_E = (R_E – d)^3 / R_E^3 ( 7.16)"
- ch 7 p9 §7.7 ¶13: printed "U(r) = – 4√2 G m / l" · transcribed "U(r) = – 4 sqrt(2) G m / l"
- ch 7 p9 §7.7 ¶13: printed "square (r = √2 l/2) is" · transcribed "square (r = sqrt(2) l/2) is"
- ch 7 p9 §7.8 ¶2: printed "speed there was V_f" · transcribed "speed there was V_f"
- ch 7 p9 §7.8 ¶2: printed "E (∞) = W_1 + mV_f^2 / 2" · transcribed "E (∞) = W_1 + mV_f^2 / 2"
- ch 7 p10 §7.8 ¶4: printed "mV_i^2 / 2 - GmM_E / (h + R_E) = mV_f^2 / 2" · transcribed "mV_i^2 / 2 – GmM_E / (h + R_E) = mV_f^2 / 2"
- ch 7 p10 §7.8 ¶13: printed "E_i = (1/2) m v^2 - G M m / R - 4 G M m / 5 R" · transcribed "E_i = (1/2) m v^2 – G M m / R – 4 G M m / 5 R"
- ch 7 p10 §7.8 ¶14: printed "E_N = - G M m / 2 R - 4 G M m / 4 R" · transcribed "E_N = – G M m / 2 R – 4 G M m / 4 R"
- ch 7 p10 §7.8 ¶15: printed "= - GM / 2R - GM / R" · transcribed "= – GM / 2R – GM / R"
- ch 7 p11 §7.9 ¶8: printed "459 × 60" · transcribed "459 × 60"
- ch 7 p11 §7.9 ¶8: printed "6.67 × (4.59 × 6)^2 × 10^(-5)" · transcribed "6.67 × (4.59 × 6)^2 × 10^(-5)"
- ch 7 p12 §7.9 ¶15: printed "k = 10^(-13) s^2 m^-3" · transcribed "k = 10^-13 s^2 m^-3"
- ch 7 p12 §7.10 ¶4: printed "an circularly orbiting" · transcribed "an circularly orbiting"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 7 p4: "Fig. 7.3 Gravitational force on m_1 due to m_2 is along r where the vector r is (r_2 - r_1)."
- ch 7 p6: "7.5 ACCELERATION DUE TO GRAVITY OF THE EARTH"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 105

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 105 | 105 | 101 | 4 | 0 | 0 | 1 | 95.2% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 8 page-level start flags, 0 numbered equations the print carries that the rows do not, 1 passages no row carries

# margai-pipeline ncert verify

- run: 2026-09-16 22:52 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 7 | 12 | 6 | 5 | 5 | 0 | 0 | 0 | 1 |

## where rows start against where the print starts paragraphs

- ch 7 p3: 8 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "equal times to traverse BAC and"
- ch 7 p4: 11 rows start here, the print starts 9 paragraphs — rows the print does not start: §7.3 ¶3 "Every body in the universe attracts every" · §7.3 ¶8 "The total force on m_1 is F_1"
- ch 7 p5: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §7.3 ¶16 "(1) The force of attraction between a" · §7.3 ¶17 "(2) The force of attraction due to" · §7.4 ¶1 "The value of the gravitational constant G" · printed starts no row begins with: "this force works out to be" · "the Universal law of gravitation can"
- ch 7 p11: 11 rows start here, the print starts 10 paragraphs — rows the print does not start: §7.9 ¶7 "Which is approximately 85 minutes."
- ch 7 p12: 14 rows start here, the print starts 13 paragraphs — rows the print does not start: §7.9 ¶17 "Answer Given k = 10^-13 s^2 m^-3"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 7 p7: the print starts "For , using binomial expression," where §7.6 ¶4 starts "For h/R_E << 1, using binomial expression," — the layer dropped the line's math

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 6d8fa83dd3a9821ab2aec82cbf44fc8af82c434911b3ce6c48c23507377a9630
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (106 rows)
request id: pipeline-ncert-verify-f2002d33-22dd-4c75-a16f-588f96ffc764

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 4 | 4 | 0 |
artefact: verify/phy11-part1/en.jsonl (12 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 4 | 20265 | 1427 | 21753 | 7251 | ₹6.99 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 7 p7 §7.6 ¶7: printed "proportional to the cube" · transcribed "proportional to be cube"
- ch 7 p8 §7.6 ¶11: printed "acceleration due to gravity decreases" · transcribed "acceleration due gravity decreases"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 7 p3 §7.2 ¶7: printed "Delta A / Delta t =½ (r × p)/m" · transcribed "Delta A / Delta t = ½ (r × p)/m"
- ch 7 p3 §7.2 ¶9: printed "traverse BAC and CPB" · transcribed "traverse BAC and CPB"
- ch 7 p3 §7.3 ¶1: printed "R_m was already known then to be about 3.84 × 10^8 m" · transcribed "R_m was already known then to be about 3.84 × 10^8 m"
- ch 7 p5 §7.3 ¶12: printed "F_GB = Gm(2m) / 1 (-i_hat cos 30° - j_hat sin 30°)" · transcribed "F_GB = Gm(2m) / 1 (-i_hat cos 30° - j_hat sin 30°)"
- ch 7 p6 §7.5 ¶3: printed "radius r for which" · transcribed "radius r for which"
- ch 7 p6 §7.5 ¶3: printed "M_r is concentrated" · transcribed "M_r is concentrated"
- ch 7 p6 §7.5 ¶3: printed "F = Gm(M_r) / r^2" · transcribed "F = Gm (M_r) / r^2"
- ch 7 p9 §7.7 ¶12: printed "− 4√2 G m / l" · transcribed "− 4 sqrt(2) G m / l"
- ch 7 p9 §7.8 ¶2: printed "E (infinity) = W_1 + mV_f^2 / 2" · transcribed "E (infinity) = W_1 + mV_f^2 / 2"
- ch 7 p10 §7.8 ¶13: printed "E_i = (1/2) m v^2 – G M m / R – 4 G M m / 5 R" · transcribed "E_i = (1/2) m v^2 – G M m / R – 4 G M m / 5 R ."
- ch 7 p10 §7.8 ¶14: printed "E_N = – G M m / 2 R – 4 G M m / 4 R" · transcribed "E_N = – G M m / 2 R – 4 G M m / 4 R ."
- ch 7 p10 §7.8 ¶15: printed "(1/2) v^2 – GM / R – 4GM / 5R = – GM / 2R – GM / R" · transcribed "(1/2) v^2 – GM / R – 4GM / 5R = – GM / 2R – GM / R"
- ch 7 p11 §7.9 ¶9: printed "6.67 × 10^-11 × (459 × 60)^2" · transcribed "6.67 × 10^(-11) × (459 × 60)^2"
- ch 7 p12 §7.10 ¶1: printed "K.E" · transcribed "K.E"
- ch 7 p12 §7.10 ¶1: printed "Gm M_E / (2(R_E + h))" · transcribed "Gm M_E / (2(R_E + h))"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 2
verdicts recorded on rows: 106

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 106 | 106 | 104 | 2 | 0 | 0 | 0 | 98.1% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 5 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

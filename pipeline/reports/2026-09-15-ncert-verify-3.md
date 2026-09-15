# margai-pipeline ncert verify

- run: 2026-09-15 08:16 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags |
|---|---|---|---|---|---|---|
| 7 | 12 | 6 | 5 | 7 | 0 | 1 |

## where rows start against where the print starts paragraphs

- ch 7 p3: 7 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "where v is the velocity, L" · "equal times to traverse BAC and"
- ch 7 p4: 11 rows start here, the print starts 9 paragraphs — rows the print does not start: §7.3 ¶3 "Every body in the universe attracts every" · §7.3 ¶8 "The total force on m_1 is F_1"
- ch 7 p5: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §7.3 ¶16 "(1) The force of attraction between a" · §7.3 ¶17 "(2) The force of attraction due to" · §7.4 ¶1 "The value of the gravitational constant G" · printed starts no row begins with: "this force works out to be" · "the Universal law of gravitation can"
- ch 7 p7: 9 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "For , using binomial expression,"
- ch 7 p8: 10 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "Substituting for M from above ,"
- ch 7 p11: 10 rows start here, the print starts 10 paragraphs — rows the print does not start: §7.9 ¶6 "Which is approximately 85 minutes." · printed starts no row begins with: "where we have used the relation"
- ch 7 p12: 14 rows start here, the print starts 13 paragraphs — rows the print does not start: §7.9 ¶16 "Answer Given k = 10^-13 s^2 m^-3"

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

- ch 7 §7.3 ¶9: figure_refs carries "Fig. 7.5", which the paragraph never mentions
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 c87ac7d43c1f0a2695ee9020ec40b08bd10ccf1cb6a215edb4d4608924446393
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (102 rows)
request id: pipeline-ncert-verify-2fb5a965-088d-4923-962b-540b928b601b

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 12 | 9 | 3 |
artefact: verify/phy11-part1/en.jsonl (12 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 9 | 50644 | 3788 | 65259 | 7251 | ₹15.37 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 7 p9 §7.7 ¶12: printed "− 4√2 G m / l" · transcribed "− 4 sqrt(2) G m / l"
- ch 7 p10 §7.8 ¶10: printed "R_E replaced by the radius" · transcribed "r_E replaced by the radius"
- ch 7 p10 §7.8 ¶13: printed "E_i = (1/2) m v^2 – G M m / R – 4 G M m / 5 R" · transcribed "E_i = (1/2) m v^2 – G M m / R – 4 G M m / 5 R ."
- ch 7 p10 §7.8 ¶14: printed "E_N = – G M m / 2 R – 4 G M m / 4 R" · transcribed "E_N = – G M m / 2 R – 4 G M m / 4 R ."
- ch 7 p11 §7.9 ¶8: printed "6.67 × 10^-11 × (459 × 60)^2" · transcribed "6.67 × 10^(-11) × (459 × 60)^2"
- ch 7 p11 §7.9 ¶8: printed "6.67×(4.59×6)^2 × 10^-5" · transcribed "6.67 × (4.59 × 6)^2 × 10^(-5)"
- ch 7 p12 §7.10 ¶4: printed "total energy of a satellite" · transcribed "total energy of an circularly orbiting satellite"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 7 p8: "7.7 GRAVITATIONAL POTENTIAL ENERGY"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 7 p5 §7.3 ¶12: printed "F_GB = Gm(2m) / 1 (-i_hat cos 30° - j_hat sin 30°)" · transcribed "F_GB = Gm(2m) / 1 (-i_hat cos 30° - j_hat sin 30°)"
- ch 7 p6 §7.5 ¶3: printed "radius r for which" · transcribed "radius r for which"
- ch 7 p6 §7.5 ¶3: printed "M_r is concentrated" · transcribed "M_r is concentrated"
- ch 7 p6 §7.5 ¶3: printed "F = Gm(M_r) / r^2" · transcribed "F = Gm (M_r) / r^2"
- ch 7 p9 §7.8 ¶2: printed "E (infinity) = W_1 + mV_f^2 / 2" · transcribed "E (infinity) = W_1 + mV_f^2 / 2"
- ch 7 p10 §7.8 ¶15: printed "(1/2) v^2 – GM / R – 4GM / 5R = – GM / 2R – GM / R" · transcribed "(1/2) v^2 – GM / R – 4GM / 5R = – GM / 2R – GM / R"
- ch 7 p12 §7.10 ¶1: printed "K.E" · transcribed "K.E"
- ch 7 p12 §7.10 ¶1: printed "Gm M_E / (2(R_E + h))" · transcribed "Gm M_E / (2(R_E + h))"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 102

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 102 | 102 | 96 | 6 | 0 | 0 | 1 | 93.1% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 7 page-level start flags, 1 passages no row carries

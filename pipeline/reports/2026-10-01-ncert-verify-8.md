# margai-pipeline ncert verify

- run: 2026-10-01 17:41 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: FAILED: InvalidOutputException: model output failed schema validation: does not map onto PageVerdicts: Cannot construct instance of `com.margai.ai.tasks.PageVerdicts$ItemVerdict`, problem: item 1 is 'matches' and must name no difference
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
verifier: claude-sonnet-5-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (105 rows)
request id: pipeline-ncert-verify-b0504b38-a267-49f9-9159-5197eb192222

# margai-pipeline ncert verify

- run: 2026-10-01 18:09 IST
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
verifier: claude-sonnet-5-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (105 rows)
request id: pipeline-ncert-verify-99fe45e3-3dad-4a0f-8fd2-8d62b41f2df1

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 13 | 10 | 3 |
artefact: verify/phy11-part1/en.sonnet55.jsonl (13 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 10 | 48470 | 8040 | 64638 | 7182 | ₹18.79 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

none

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 7 p4 §7.3 ¶2: transcribed "assumes the gravitational" (printed "assumes that the gravitational")

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 105

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 105 | 105 | 104 | 0 | 0 | 1 | 1 | 98.1% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 8 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

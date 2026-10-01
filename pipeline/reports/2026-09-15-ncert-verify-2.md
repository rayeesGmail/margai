# margai-pipeline ncert verify

- run: 2026-09-15 08:00 IST
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
request id: pipeline-ncert-verify-59b371fd-526f-4f8c-8bb3-b53171b5e98d

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part1/en.jsonl (1 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 9216 | 186 | 7245 | 7245 | ₹3.59 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant

none

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 1

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 102 | 1 | 1 | 0 | 0 | 0 | 1 | — (101 rows without a verdict) |
clean for the book (PLAN D15 ✅): not computed — some rows have no verdict yet
not in the clean share, adjudicate before recording it: 7 page-level start flags, 0 passages no row carries

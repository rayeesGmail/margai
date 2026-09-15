# margai-pipeline ncert verify

- run: 2026-09-15 07:49 IST
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
no model was called: add --read-pages for the second read
no second read in the artefact yet: verify/phy11-part1/en.jsonl does not exist

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 102 | 0 | 0 | 0 | 0 | 0 | 1 | — (102 rows without a verdict) |
clean for the book (PLAN D15 ✅): not computed — some rows have no verdict yet
not in the clean share, adjudicate before recording it: 7 page-level start flags, 0 passages no row carries

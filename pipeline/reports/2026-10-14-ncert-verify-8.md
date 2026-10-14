# margai-pipeline ncert verify

- run: 2026-10-14 09:09 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 bee4bb9e64493f7c640123cc499627cbf3a5d2eb0f28142e3db986bb2ee1dfef

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 2 | withheld: illegible text layer | — | — | — | — | — | — | — |

## where rows start against where the print starts paragraphs

none

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
none

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 31 of the 31 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
none on the pages checked

symbols the page carries more often, digits and Latin only the rows carry:
- ch 2 p2: 'dr' 2x in the rows, 0x on the page
- ch 2 p5: '0.09' 1x in the rows, 0x on the page
- ch 2 p6: '≅' 6x in the rows, 0x on the page
- ch 2 p15: 'qDelta' 1x in the rows, 0x on the page
- ch 2 p16: 'NC' 1x in the rows, 0x on the page
- ch 2 p16: 'sin' 1x in the rows, 0x on the page
- ch 2 p17: '•' 1x in the rows, 0x on the page
- ch 2 p22: '0' 1x in the rows, 0x on the page
- ch 2 p28: 'nV' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (192 rows)
request id: pipeline-ncert-verify-d3c1a8d4-8b8f-4ca8-83fa-e5d5b7faf6fe

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part1/hi.jsonl (180 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5232 | 292 | 0 | 7251 | ₹2.84 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 2 p17 §2.8.3 ¶8: printed "10^(-29) C m" · transcribed "10^(-29) C m का स्थायी वैद्युत द्विध्रुव आघूर्ण है"

## rows the verifier could not find on their page

- ch 2 p17 §2.8.3 ¶6 "ध्यान दीजिए कि U'(theta) तथा U(theta) में"

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 2 p7 §2.5 ¶1: printed "V_1 = (1 / 4 pi epsilon_o) (q_1 / r_1P)" · transcribed "V_1 = (1 / 4 pi epsilon_o) (q_1 / r_1P)"
- ch 2 p8 §2.5 ¶9: printed "3/x - 2/(x-15) = 0" · transcribed "3/x - 2/(x - 15) = 0"
- ch 2 p11 §2.7 ¶1: printed "r_12" · transcribed "r_12"
- ch 2 p12 §2.7 ¶4: printed "q_2 V_1(r_2) = (1 / 4 pi epsilon_o) (q_1 q_2 / r_12) (2.23)" · transcribed "q_2 V_1(r_2) = (1 / 4 pi epsilon_o) (q_1 q_2 / r_12) (2.23)"
- ch 2 p15 §2.8.1 ¶4: printed "1 keV = 10^3eV = 1.6 × 10^(-16)J" · transcribed "1 keV = 10^3eV = 1.6 × 10^(-16)J"
- ch 2 p15 §2.8.1 ¶4: printed "1 GeV = 10^9eV = 1.6 × 10^(-10)J" · transcribed "1 GeV = 10^9eV = 1.6 × 10^(-10)J"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 2 p17: "2.9 चालक-स्थिरवैद्युतिकी"
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 192

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 2 | 192 | 192 | 190 | 1 | 1 | 0 | 0 | 99.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

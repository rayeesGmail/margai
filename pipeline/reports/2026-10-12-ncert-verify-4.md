# margai-pipeline ncert verify

- run: 2026-10-12 09:37 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 292038febfafa9d8ef855c6243257caaa578578440437e5bb8a8500b441e013e

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 13 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 13 of the 13 page(s) the loaded rows are on

words the rows carry that the page does not — a misreading or an addition:
- ch 13 p13: 'परिमाण' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 13 p3: '•' 1x on the page, 0x in the rows
- ch 13 p4: '•' 1x on the page, 0x in the rows
- ch 13 p6: '•' 1x on the page, 0x in the rows
- ch 13 p7: '•' 1x on the page, 0x in the rows
- ch 13 p9: '•' 1x on the page, 0x in the rows
- ch 13 p9: '140' 1x in the rows, 0x on the page
- ch 13 p10: '•' 1x on the page, 0x in the rows
- ch 13 p11: '•' 1x on the page, 0x in the rows
- ch 13 p13: '•' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (94 rows)
request id: pipeline-ncert-verify-d35b9ac4-8b1f-4e8f-8312-a3894fcebc1e

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part2/hi.jsonl (109 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5137 | 309 | 7251 | 0 | ₹1.34 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 13 p10 §13.6 ¶4: printed "M संहति के किसी गुटके" · transcribed "m संहति के किसी गुटके"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 13 p11 §13.7 ¶15: printed "0.6123 ms^-1" · transcribed "0.6123 m s^-1"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 6
verdicts recorded on rows: 94

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 13 | 94 | 94 | 93 | 1 | 0 | 0 | 0 | 98.9% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

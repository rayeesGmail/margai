# margai-pipeline ncert verify

- run: 2026-10-16 23:15 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 6963a69e3e71dcef5f80471bb851563dd0ca71d261211c60d5e2d464ad24c247

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 10 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 17 of the 17 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 10 p5: 'बिंदु' 2x in the rows, 1x on the page
- ch 10 p8: 'बिंदु' 7x in the rows, 6x on the page
- ch 10 p12: 'अदीप्त' 1x in the rows, 0x on the page
- ch 10 p17: 'एवं' 3x in the rows, 2x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 10 p12: '×' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (78 rows)
request id: pipeline-ncert-verify-e3c7f7f7-3bee-4283-8526-d87881395344

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part2/hi.jsonl (99 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 3998 | 201 | 0 | 7251 | ₹2.54 |

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

- ch 10 p3 §10.2 ¶4: printed "v tau" · transcribed "v tau"
- ch 10 p3 §10.2 ¶4: printed "v माध्यम" · transcribed "v माध्यम"
- ch 10 p6 §10.3.3 ¶1: printed "बिंदु B से C तक आगे बढ़ने में लिए गए समय को निरूपित करता है, तब दूरी BC= v tau" · transcribed "बिंदु B से C तक आगे बढ़ने में लिए गए समय को निरूपित करता है, तब दूरी BC= v tau"
- ch 10 p11 §10.5 ¶5: printed "n = 0,±1,±2" · transcribed "n = 0, ±1, ±2"
- ch 10 p16 §10.7 ¶6: printed "ध्रुवित प्रकाश-पुंज पोलोरॉइड P_1" · transcribed "ध्रुवित प्रकाश-पुंज पोलोरॉइड P_1"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 2
verdicts recorded on rows: 78

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 10 | 78 | 78 | 78 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

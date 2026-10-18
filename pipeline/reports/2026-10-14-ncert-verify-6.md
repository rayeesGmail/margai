# margai-pipeline ncert verify

- run: 2026-10-14 08:17 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 0f75ff489d4a49ff092ffa67a53778eb414da36fee89bdb2dba61b3f1517f03b

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 6 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 19 of the 19 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 6 p2: 'दिशाएँ' 1x in the rows, 0x on the page
- ch 6 p5: 'वाहक' 6x in the rows, 5x on the page
- ch 6 p8: 'प्रेरित' 6x in the rows, 5x on the page
- ch 6 p17: 'के' 14x in the rows, 13x on the page
- ch 6 p17: 'वर्ग' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 6 p5: 'dPhi' 2x in the rows, 0x on the page
- ch 6 p10: 'dPhi' 1x in the rows, 0x on the page
- ch 6 p11: 'dtheta' 2x in the rows, 0x on the page
- ch 6 p12: 'dPhi' 1x in the rows, 0x on the page
- ch 6 p12: 'NPhi' 2x in the rows, 0x on the page
- ch 6 p16: '0' 1x in the rows, 0x on the page
- ch 6 p18: 'dPhi' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (104 rows)
request id: pipeline-ncert-verify-2e1b466f-ffc5-4d05-91b7-70fe5b7c105d

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part1/hi.jsonl (180 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4772 | 386 | 7251 | 0 | ₹1.34 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 6 p5: "माइकल फैराडे [1791-1867] biography box (margin)"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 6 p13 §6.7.1 ¶8: printed "M_21 = mu_0 n_1 n_2 pi r_1^2 l (6.11)" · transcribed "M_21 = mu_0 n_1 n_2 pi r_1^2 l (6.11)"
- ch 6 p13 §6.7.1 ¶9: printed "समीकरण (6.9) तथा समीकरण (6.10) का उपयोग" · transcribed "समीकरण (6.9) तथा समीकरण (6.10) का उपयोग"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 6 p5 §6.4 ¶4: transcribed "प्रेरित विद्युत धारा बल को" (printed "प्रेरित विद्युत वाहक बल को")

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 104

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 6 | 104 | 104 | 103 | 0 | 0 | 1 | 0 | 99.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 1 passages no row carries

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
| 3 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 22 of the 22 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 3 p2: 'नाभिक' 2x in the rows, 1x on the page
- ch 3 p5: 'परिमाण' 2x in the rows, 1x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 3 p11: '85.8' 2x in the rows, 0x on the page
- ch 3 p12: '0.795' 1x in the rows, 0x on the page
- ch 3 p12: '0.23' 1x in the rows, 0x on the page
- ch 3 p22: '×' 3x on the page, 0x in the rows
- ch 3 p22: '∝' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (126 rows)
request id: pipeline-ncert-verify-200a116d-8151-4958-a0b4-8798a295bf64

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part1/hi.jsonl (180 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4552 | 253 | 7251 | 0 | ₹1.18 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 3 p17 §3.11 ¶19: printed "हमने दोनों टर्मिनलों को" · transcribed "हमने दोनों टर्मिनलों को एक साथ"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 3 p3 §3.3 ¶5: printed "उस प्रकाश" · transcribed "उस प्रकाश"
- ch 3 p12 §3.9 ¶2: printed "स्थितिज ऊर्जा Q V(A)" · transcribed "स्थितिज ऊर्जा Q V(A)"
- ch 3 p12 §3.9 ¶2: printed "= Delta Q[(V (B) – V (A)]" · transcribed "= Delta Q[(V (B) – V (A)]"
- ch 3 p14 §3.10 ¶7: printed "V = I R" · transcribed "V = I R"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 3 p17: "3.12 किरखोफ के नियम"
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 126

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 3 | 126 | 126 | 125 | 1 | 0 | 0 | 0 | 99.2% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

# margai-pipeline ncert verify

- run: 2026-10-14 08:16 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 0f75ff489d4a49ff092ffa67a53778eb414da36fee89bdb2dba61b3f1517f03b

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 5 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 14 of the 14 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 5 p7: 'सभी' 5x in the rows, 3x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 5 p6: '×' 2x on the page, 0x in the rows
- ch 5 p7: '0' 2x in the rows, 0x on the page
- ch 5 p10: '•' 2x in the rows, 0x on the page
- ch 5 p10: 'ds' 2x in the rows, 0x on the page
- ch 5 p12: '≤' 2x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (97 rows)
request id: pipeline-ncert-verify-8d9a02e7-83e3-4357-bf70-64799ebf3dfa

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part1/hi.jsonl (180 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4527 | 409 | 7251 | 0 | ₹1.32 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 5 p4 §5.2.3 ¶4: printed "U_m = integral tau(theta) d theta = integral mB sin theta d theta = -mB cos theta = -m B (5.3)" · transcribed "U_m = integral tau(theta) d theta = integral mB sin theta d theta = −mB cos theta = − m·B (5.3)"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 5 p6 §5.2.4 ¶8: printed "B_P = −(mu_0 / 4 pi) m_P / r^3" · transcribed "B_P = −(mu_0 / 4 pi) m_P / r^3"
- ch 5 p6 §5.2.4 ¶8: printed "B_P = (mu_0 2 / 4 pi) m_P / r^3" · transcribed "B_P = (mu_0 2 / 4 pi) m_P / r^3"
- ch 5 p14 §5.5.3 ¶2: printed "चित्र 5.7 (b) वह स्थिति दर्शाता है" · transcribed "चित्र 5.7 (b) वह स्थिति दर्शाता है"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 5 p4: "5.2.3 एकसमान चुंबकीय क्षेत्र में द्विध्रुव"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 97

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 5 | 97 | 97 | 96 | 1 | 0 | 0 | 0 | 99.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

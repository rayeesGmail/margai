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
| 14 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 18 of the 18 page(s) the loaded rows are on

words the rows carry that the page does not — a misreading or an addition:
- ch 14 p4: 'क्वार्ट्ज़' 1x in the rows, 0x on the page
- ch 14 p9: 'अपेक्षित' 1x in the rows, 0x on the page
- ch 14 p12: 'परिमाण' 1x in the rows, 0x on the page
- ch 14 p13: 'क्षणों' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 14 p4: '•' 1x on the page, 0x in the rows
- ch 14 p7: '•' 3x on the page, 2x in the rows
- ch 14 p8: '•' 1x on the page, 0x in the rows
- ch 14 p9: 'BT' 1x in the rows, 0x on the page
- ch 14 p10: '•' 1x on the page, 0x in the rows
- ch 14 p16: '•' 1x on the page, 0x in the rows
- ch 14 p18: '•' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (103 rows)
request id: pipeline-ncert-verify-142627d8-7221-45ce-83f6-d1a0fb2093d4

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part2/hi.jsonl (109 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5372 | 206 | 7251 | 0 | ₹1.29 |

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

- ch 14 p9 §14.4.2 ¶4: printed "चाल का आकलन" · transcribed "चाल का आकलन"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 14 p9: "सारणी 14.1 कुछ माध्यमों में ध्वनि की चाल"
flags set aside by the founder's rulings in ncert-corrections.yaml: 5
verdicts recorded on rows: 103

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 14 | 103 | 103 | 103 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

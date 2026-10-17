# margai-pipeline ncert verify

- run: 2026-10-17 15:47 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 f613146196e740f5e8d71bf2da2e1dfaab056ee67d41220543883fd96c3ef124

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

checked: 12 of the 12 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 2 p4: 'होते' 7x in the rows, 6x on the page
- ch 2 p4: 'हैं' 25x in the rows, 24x on the page
- ch 2 p4: 'अधिकांश' 1x in the rows, 0x on the page
- ch 2 p4: 'परपोषी' 3x in the rows, 2x on the page
- ch 2 p8: 'अगुणित' 4x in the rows, 3x on the page

symbols the page carries more often, digits and Latin only the rows carry:
none on the pages checked

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (48 rows)
request id: pipeline-ncert-verify-45da86c4-96a3-40c6-a3fe-b11229117f11

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/bio11/hi.jsonl (208 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4909 | 182 | 0 | 7251 | ₹2.68 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 2 p4 §2.1.2 ¶2: printed "साइट्रस, कैंकर" · transcribed "साइट्रस कैंकर"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 2 p6 §2.2.2 ¶1: printed "लाल डायनोफ्लैसिलेट की संख्या" · transcribed "लाल डायनोफ्लैसिलेट की संख्या"
- ch 2 p7 §2.3 ¶1: printed "किट्ट रोग पक्सिनिया के" · transcribed "किट्ट रोग पक्सिनिया के"
- ch 2 p10 §2.5 ¶1: printed "परोक्ष तथा अपरोक्ष रूप से" · transcribed "परोक्ष तथा अपरोक्ष रूप से"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 5
verdicts recorded on rows: 48

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 2 | 48 | 48 | 47 | 1 | 0 | 0 | 0 | 97.9% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

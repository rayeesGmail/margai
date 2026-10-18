# margai-pipeline ncert verify

- run: 2026-10-14 16:40 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 d387f91733c8b9a7468c9126e2ead4f9552df90c098a336d43317c19763f3bbc

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 11 | withheld: illegible text layer | — | — | — | — | — | — | — |

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
none on the pages checked

symbols the page carries more often, digits and Latin only the rows carry:
- ch 11 p12: '→' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (92 rows)
request id: pipeline-ncert-verify-13dcb514-016d-4960-ac26-efab6ff89fea

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part2/hi.jsonl (109 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5845 | 269 | 7251 | 0 | ₹1.43 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 11 p2 §11.2 ¶3: printed "यद्यपि किसी ऊष्मागतिकीय अवस्था को पाँच अवस्था चरों" · transcribed "यद्यपि किसी उष्मागतिकीय अवस्था को पाँच अवस्था चरों"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 11 p1 §11.1 ¶2: printed "न हो जाएँ।" · transcribed "न हो जाएँ।"
- ch 11 p8 §11.8.1 ¶3: printed "किन्तु Delta Q निकाय की कुल मात्रा समानुपातिक है।" · transcribed "किन्तु Delta Q निकाय की कुल मात्रा समानुपातिक है।"
- ch 11 p12 §11.11 ¶3: printed "चित्र 11.11 में" · transcribed "चित्र 11.11 में"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 11 p2: "11.2 तापीय साम्य"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 92

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 11 | 92 | 92 | 91 | 1 | 0 | 0 | 0 | 98.9% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

# margai-pipeline ncert verify

- run: 2026-10-17 15:48 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 f613146196e740f5e8d71bf2da2e1dfaab056ee67d41220543883fd96c3ef124

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

checked: 9 of the 9 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 10 p6: 'प्राणी' 1x in the rows, 0x on the page
- ch 10 p7: 'इलेक्ट्रॉन' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
none on the pages checked

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (39 rows)
request id: pipeline-ncert-verify-ee17cd58-f3e7-4a99-82c1-310ec7fd7546

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/bio11/hi.jsonl (208 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4741 | 123 | 7251 | 0 | ₹1.10 |

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

- ch 10 p2 §10.1.1 ¶4: printed "संगत संतति गणसूत्र" · transcribed "संगत संतति गणसूत्र"
- ch 10 p7 §10.4.1 ¶2: printed "तनुपट्ट (लिप्टोटीन) अवस्था" · transcribed "तनुपट्ट (लिप्टोटीन) अवस्था"
- ch 10 p9 §10.4.2 ¶2: printed "तर्कुतंतु की सूक्ष्मनलिकाएं" · transcribed "तर्कुतंतु की सूक्ष्मनलिकाएं"
- ch 10 p9 §10.4.2 ¶3: printed "सूक्ष्म नलिकाओं" · transcribed "सूक्ष्मनलिकाओं"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 1
verdicts recorded on rows: 39

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 10 | 39 | 39 | 39 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

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
| 4 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 15 of the 15 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 4 p2: 'केशिकाएं' 1x in the rows, 0x on the page
- ch 4 p5: 'व्यजन' 1x in the rows, 0x on the page
- ch 4 p9: 'कॉर्डेटा' 1x in the rows, 0x on the page
- ch 4 p9: 'खुले' 1x in the rows, 0x on the page
- ch 4 p10: 'ब्रैंकिओस्टोमा' 1x in the rows, 0x on the page
- ch 4 p12: 'स्टिंग' 1x in the rows, 0x on the page
- ch 4 p12: 'रे' 1x in the rows, 0x on the page
- ch 4 p14: 'फुप्फुस' 3x in the rows, 2x on the page

symbols the page carries more often, digits and Latin only the rows carry:
none on the pages checked

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 4 p15: the rows carry 22% of the page's Devanagari words
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (57 rows)
request id: pipeline-ncert-verify-87012eb4-f423-46b7-ae6e-179153410be6

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/bio11/hi.jsonl (208 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4176 | 186 | 7251 | 0 | ₹1.05 |

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

- ch 4 p4 §4.2.2 ¶1: printed "अधिकांशत: समुद्री" · transcribed "अधिकांशतः समुद्री"
- ch 4 p5 §4.2.2 ¶1: printed "एकांतरण (मेटाजनेसिस)" · transcribed "एकांतरण (मेटाजनेसिस)"
- ch 4 p5 §4.2.2 ¶1: printed "पीढ़ी एकांतरण" · transcribed "पीढ़ी एकांतरण"
- ch 4 p6 §4.2.4 ¶1: printed "परपोषी से सीधे" · transcribed "परपोषी से सीधे"
- ch 4 p6 §4.2.4 ¶1: printed "उर्त्सजन में" · transcribed "उर्त्सजन में"
- ch 4 p8 §4.2.7 ¶1: printed "वक्ष तथा उदर" · transcribed "वक्ष तथा उदर"
- ch 4 p9 §4.2.10 ¶1: printed "हेमीकार्डेटा के कॉलर क्षेत्र में अल्पविकसित संरचना होती है जिसे स्टोमोकार्ड कहते हैं जो पृष्ठरज्जु के समान" · transcribed "हेमीकार्डेटा के कॉलर क्षेत्र में अल्पविकसित संरचना होती है जिसे स्टोमोकार्ड कहते हैं जो पृष्ठरज्जु के समान"
- ch 4 p15 §4.2.11.7 ¶2: printed "अंडज-औरनिथोरिंकस," · transcribed "अंडज-औरनिथोरिंकस,"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 7
verdicts recorded on rows: 57

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 4 | 57 | 57 | 57 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

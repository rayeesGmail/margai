# margai-pipeline ncert verify

- run: 2026-10-01 22:54 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 a0704559072ce8ec1cb01acd27fae7247f3ebd21546b8da676ee7e4c584c9f69

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | withheld: illegible text layer | — | — | — | — | — | — | — |

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
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (68 rows)
request id: pipeline-ncert-verify-007fb4a8-0865-481f-9a35-5a98fcb3a212

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 9 | 9 | 0 |
artefact: verify/phy11-part1/hi.hiopus55-s5.jsonl (9 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 9 | 47096 | 2350 | 65259 | 0 | ₹11.80 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 1 p5 §1.3.1 ¶4: printed "3.00×10^8 m/s" · transcribed "3.00 × 10^8 m/s^-1"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 1 p2: "सारणी 1.1 SI मूल राशियाँ एवं उनके मात्रक (table content)"
- ch 1 p3: "1.3 सार्थक अंक"
- ch 1 p6: "1.3.3 अंकगणितीय परिकलनों के परिणामों में अनिश्चितता निर्धारित करने के नियम"
- ch 1 p7: "1.6 विमीय विश्लेषण एवं इसके अनुप्रयोग"
- ch 1 p9: "1.6.2 विभिन्न भौतिक राशियों के मध्य संबंध व्युत्पन्न करना"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 1 p4 §1.3 ¶10: printed "प्रेक्षण (a) में" · transcribed "प्रेक्षण (a) में"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 1 p8: "1.6.1 समीकरणों की विमीय संगति की जाँच"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 68

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 68 | 68 | 67 | 1 | 0 | 0 | 0 | 98.5% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 5 passages no row carries

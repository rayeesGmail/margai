# margai-pipeline ncert verify

- run: 2026-10-01 22:52 IST
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
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (72 rows)
request id: pipeline-ncert-verify-6ecf43df-22b0-47b2-bce5-a13664b72291

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 9 | 9 | 0 |
artefact: verify/phy11-part1/hi.hiopus5-s5.jsonl (9 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 9 | 47157 | 2700 | 58008 | 7251 | ₹13.63 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 1 p2: "सारणी 1.1 (SI मूल राशियाँ एवं उनके मात्रक, table content)"
- ch 1 p3: "सारणी 1.2 सामान्य प्रयोग के लिए SI मात्रकों के अतिरिक्त कुछ अन्य मात्रक (table)"
- ch 1 p3: "1.3 सार्थक अंक (heading)"
- ch 1 p6: "1.3.3 अंकगणितीय परिकलनों के परिणामों में अनिश्चितता निर्धारित करने के नियम"
- ch 1 p7: "किसी भौतिक राशि की विमाएँ उन घातों (या घातांकों) को कहते हैं"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 1 p4 §1.3 ¶10: printed "पृथ्वी का व्यास (1.28×10^7m)" · transcribed "पृथ्वी का व्यास (1.28×10^7 m)"
- ch 1 p5 §1.3.1 ¶4: printed "3.00 × 10^8 m/s^-1" · transcribed "3.00 × 10^8 m/s^-1"
- ch 1 p7 §1.4 ¶1: printed "किसी भौतिक राशि की विमाएँ उन घातों ( या घातांकों ) को कहते हैं, जिन्हें उस राशि को व्यक्त करने के लिए मूल राशियों पर चढ़ाना पड़ता है।" · transcribed "किसी भौतिक राशि की विमाएँ उन घातों (या घातांकों) को कहते हैं, जिन्हें उस राशि को व्यक्त करने के लिए मूल राशियों पर चढ़ाना पड़ता है।"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 1 p8: "1.6.1 समीकरणों की विमीय संगति की जाँच"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 72

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 72 | 72 | 72 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 5 passages no row carries

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
| 15 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 11 of the 11 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 15 p7: 'निलय' 14x in the rows, 13x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 15 p2: '55' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (35 rows)
request id: pipeline-ncert-verify-cd15dccf-11a8-4aec-8a2e-d06deb66ecec

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/bio11/hi.jsonl (208 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4919 | 128 | 7251 | 0 | ₹1.14 |

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

- ch 15 p9 §15.3.3 ¶4: printed "संकुचन क्यू तरंग के तुरंत बाद" · transcribed "संकुचन क्यू तरंग के तुरंत बाद"
- ch 15 p9 §15.3.3 ¶5: printed "टी तरंग का अंत प्रकुंचन अवस्था की समाप्ति" · transcribed "टी तरंग का अंत प्रकुंचन अवस्था की समाप्ति"
- ch 15 p9 §15.4 ¶2: printed "रक्त सिर्फ को हृद पेशी न्यास" · transcribed "रक्त सिर्फ को हृद पेशी न्यास"
- ch 15 p10 §15.6 ¶1: printed "विराम काल (सहज) रक्त दाब कहते" · transcribed "विराम काल (सहज) रक्त दाब कहते"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 1
verdicts recorded on rows: 35

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 15 | 35 | 35 | 35 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

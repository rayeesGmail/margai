# margai-pipeline ncert verify

- run: 2026-10-14 16:41 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 d387f91733c8b9a7468c9126e2ead4f9552df90c098a336d43317c19763f3bbc

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

words the rows carry more often than the page — a misreading or an addition:
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
request id: pipeline-ncert-verify-682f6777-2c8a-4d83-b761-890c9dda2030

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 2 | 2 | 0 |
artefact: verify/phy11-part2/hi.jsonl (109 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 2 | 10994 | 610 | 14502 | 0 | ₹2.80 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 14 p11 §14.5 ¶6: printed "a [2 sin [((kx – omega t) + (kx – omega t + phi)) / 2]" · transcribed "a [2 sin [((kx – omega t) + (kx – omega t + phi)) / 2] cos (phi / 2)]"
- ch 14 p13 §14.6 ¶4: printed "y = y_i + y_r = 0 सभी बलों पर।" · transcribed "y = y_i + y_r = 0 सभी क्षणों पर।"

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

- ch 14 p11: "चित्र 14.9 समान एवं विपरीत विस्थापन वाली विपरीत दिशा में गमन करती दो स्पंद।"
- ch 14 p13: "14.6.1 अप्रगामी तरंगें तथा प्रसामान्य विधाएँ"
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 103

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 14 | 103 | 103 | 101 | 2 | 0 | 0 | 0 | 98.1% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

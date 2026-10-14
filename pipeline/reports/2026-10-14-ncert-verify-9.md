# margai-pipeline ncert verify

- run: 2026-10-14 09:09 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 bee4bb9e64493f7c640123cc499627cbf3a5d2eb0f28142e3db986bb2ee1dfef

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

checked: 26 of the 26 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 4 p21: 'आघूर्ण' 14x in the rows, 13x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 4 p12: 'closed' 1x in the rows, 0x on the page
- ch 4 p20: '→' 1x in the rows, 0x on the page
- ch 4 p23: '≃' 1x in the rows, 0x on the page
- ch 4 p24: 'kphi' 2x in the rows, 0x on the page
- ch 4 p26: '≃' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (152 rows)
request id: pipeline-ncert-verify-8df6a8d6-992a-4542-9e27-7fec8767829a

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part1/hi.jsonl (180 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4553 | 122 | 7251 | 0 | ₹1.06 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

- ch 4 p22 §4.9.1 ¶19
- ch 4 p22 §4.9.1 ¶20
- ch 4 p22 §4.9.1 ¶21
- ch 4 p22 §4.9.1 ¶22
- ch 4 p22 §4.9.1 ¶23
- ch 4 p22 §4.9.1 ¶24
- ch 4 p22 §4.9.2 ¶1

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 4 p17 §4.8 ¶4: printed "खंड L पर बल F_ab के बराबर तथा 'b' की ओर निर्दिष्ट ज्ञात" · transcribed "खंड L पर बल F_ab के बराबर तथा 'b' की ओर निर्दिष्ट ज्ञात"
- ch 4 p20 §4.9.1 ¶8: printed "tau = F_1 (a/2) sin theta + F_2 (a/2) sin theta" · transcribed "tau = F_1 (a/2) sin theta + F_2 (a/2) sin theta"
- ch 4 p22 §4.9.1 ¶18: printed "I dω/dt = m B sin theta" · transcribed "I dω/dt = m B sin theta"
- ch 4 p23 §4.9.2 ¶3: printed "B ≈ mu_0 m / (2 pi x^3)" · transcribed "B ≃ mu_0 m / (2 pi x^3)"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 5
verdicts recorded on rows: 152

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 4 | 152 | 152 | 145 | 0 | 0 | 7 | 0 | 95.4% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

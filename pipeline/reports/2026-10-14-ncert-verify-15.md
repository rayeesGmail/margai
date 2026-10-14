# margai-pipeline ncert verify

- run: 2026-10-14 16:40 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 d387f91733c8b9a7468c9126e2ead4f9552df90c098a336d43317c19763f3bbc

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 6 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 32 of the 32 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 6 p29: 'दृढ़' 2x in the rows, 1x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 6 p5: 'ix' 1x in the rows, 0x on the page
- ch 6 p12: '•' 1x in the rows, 0x on the page
- ch 6 p13: '×' 1x on the page, 0x in the rows
- ch 6 p13: 'PCP' 1x in the rows, 0x on the page
- ch 6 p15: '×' 3x on the page, 2x in the rows
- ch 6 p17: 'dP' 1x in the rows, 0x on the page
- ch 6 p21: 'ig' 2x in the rows, 0x on the page
- ch 6 p32: '×' 2x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 6 p25: the rows carry 56% of the page's Devanagari words
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (284 rows)
request id: pipeline-ncert-verify-fec58010-b19b-4322-b93c-0bf61db3cd8f

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 5 | 5 | 0 |
artefact: verify/phy11-part1/hi.jsonl (114 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 5 | 32701 | 2236 | 43506 | 0 | ₹8.71 |

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

- ch 6 p7 §6.2 ¶29: printed "अतः यह द्रव्यमान केन्द्र तीनों माध्यिकाओं का संगामी बिन्दु गति त्रिभुज का केन्द्रक G है।" · transcribed "अतः यह द्रव्यमान केन्द्र तीनों माध्यिकाओं का संगामी बिन्दु गति त्रिभुज का केन्द्रक G है।"
- ch 6 p12 §6.5 ¶19: printed "स्पष्टतः" · transcribed "स्पष्टतः"
- ch 6 p17 §6.7.2 ¶15: printed "tau_int = 0" · transcribed "tau_int = 0"
- ch 6 p19 §6.8 ¶14: printed "sum F ≠ 0" · transcribed "sum F ≠ 0"
- ch 6 p21 §6.8.2 ¶3: printed "tau_i = r_i × m_ig" · transcribed "tau_i = r_i × m_ig"
- ch 6 p21 §6.8.2 ¶3: printed "r_i" · transcribed "r_i"
- ch 6 p23 §6.8.2 ¶16: printed "घर्षणविहीन दीवार" · transcribed "घर्षणविहीन दीवार"
- ch 6 p23 §6.8.2 ¶21: printed "≈ 80°" · transcribed "≈ 80°"
- ch 6 p24 §6.9 ¶10: printed "I = Ml^2 / 4" · transcribed "I = Ml^2 / 4"
- ch 6 p28 §6.11 ¶6: printed "F_1(r_1 d theta)sin alpha_1" · transcribed "F_1(r_1 d theta) sin alpha_1"
- ch 6 p30 §6.12 ¶2: printed "L = sum(i=1 to N) r_i × p_i" · transcribed "L = sum(i=1 to N) r_i × p_i"
- ch 6 p30 §6.12 ¶9: printed "L_z sum l_iz = (sum_i m_i r_i^2) omega k_hat" · transcribed "L_z sum l_iz = (sum_i m_i r_i^2) omega k_hat"
- ch 6 p32 §6.12.1 ¶4: printed "पश्चिमी शास्त्रीय नृतक" · transcribed "पश्चिमी शास्त्रीय नृतक"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 6 p14: "6.7.1 एक कण पर आरोपित बल का आघूर्ण"
flags set aside by the founder's rulings in ncert-corrections.yaml: 6
verdicts recorded on rows: 284

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 6 | 284 | 284 | 284 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

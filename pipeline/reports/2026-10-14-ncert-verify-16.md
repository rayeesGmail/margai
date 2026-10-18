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
| 7 | withheld: illegible text layer | — | — | — | — | — | — | — |

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
- ch 7 p14: '×' 7x on the page, 6x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (110 rows)
request id: pipeline-ncert-verify-6d0430b6-8f96-45c3-8748-4da29ac2454c

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part1/hi.jsonl (114 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5732 | 228 | 7251 | 0 | ₹1.37 |

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

- ch 7 p1 §7.1 ¶1: printed "गैलीलियो" · transcribed "गैलीलियो"
- ch 7 p3 §7.2 ¶10: printed "L_A = m_p r_A v_A" · transcribed "L_A = m_p r_A v_A."
- ch 7 p5 §7.3 ¶14: printed "F^1_GB = F_GB and F^1_GC = F_GC" · transcribed "F^1_GB = F_GB and F^1_GC = F_GC"
- ch 7 p5 §7.3 ¶14: printed "F'_R = F^1_GA + F^1_GB + F^1_GC = 2.Gm^2 j_hat" · transcribed "F'_R = F^1_GA + F^1_GB + F^1_GC = 2.Gm^2 j_hat"
- ch 7 p6 §7.4 ¶2: printed "theta के अनुक्रमानुपाती तथा tau theta के बराबर हुआ" · transcribed "theta के अनुक्रमानुपाती तथा tau theta के बराबर हुआ"
- ch 7 p11 §7.8 ¶13: printed "R – 4 G M m / 5 R" · transcribed "R – 4 G M m / 5 R"
- ch 7 p13 §7.9 ¶13: printed "6.67 × 10^-11" · transcribed "6.67 × 10^(-11)"
- ch 7 p13 §7.10 ¶2: printed "पृथ्वी के केन्द्र से (R_E+h) दूरी" · transcribed "पृथ्वी के केन्द्र से (R_E+h) दूरी"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 110

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 110 | 110 | 110 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

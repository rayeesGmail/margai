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
| 9 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 19 of the 19 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
none on the pages checked

symbols the page carries more often, digits and Latin only the rows carry:
- ch 9 p3: '×' 10x on the page, 4x in the rows
- ch 9 p3: '≈' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (127 rows)
request id: pipeline-ncert-verify-11bfca58-dacd-4d0b-99a1-7dfdb35ff22d

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 2 | 2 | 0 |
artefact: verify/phy11-part2/hi.jsonl (109 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 2 | 10807 | 603 | 14502 | 0 | ₹2.76 |

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

- ch 9 p3 §9.2.1 ¶2: printed "तदनरूपी बल F_a, F_b तथा F_c" · transcribed "तदनरूपी बल F_a, F_b तथा F_c"
- ch 9 p3 §9.2.1 ¶2: printed "फलकों BEFC, ADFC तथा ADEB को A_a, A_b तथा A_c" · transcribed "फलकों BEFC, ADFC तथा ADEB को A_a, A_b तथा A_c"
- ch 9 p3 §9.2.1 ¶2: printed "F_b sinθ = F_c, F_b cosθ = F_a" · transcribed "F_b sinθ = F_c, F_b cosθ = F_a"
- ch 9 p3 §9.2.1 ¶2: printed "F_b / A_b = F_c / A_c = F_a / A_a" · transcribed "F_b / A_b = F_c / A_c = F_a / A_a"
- ch 9 p6 §9.2.3 ¶7: printed "h = 7989 m" · transcribed "h = 7989 m"
- ch 9 p13 §9.5 ¶2: printed "eta = (F/A) / (v/l) = Fl / (vA)" · transcribed "eta = (F/A) / (v/l) = Fl / (vA)"
- ch 9 p16 §9.6.2 ¶2: printed "माना यह आंतरिक बल F है" · transcribed "माना यह आंतरिक बल F है"
- ch 9 p16 §9.6.2 ¶4: printed "हमें तरल के द्रवों या ठोस पृष्ठों पर" · transcribed "हमें तरल के द्रवों या ठोस पृष्ठों पर"
- ch 9 p19 §9.6.5 ¶2: printed "P_0 + h rho g = P_i = P_A" · transcribed "P_0 + h rho g = P_i = P_A"
- ch 9 p19 §9.6.5 ¶3: printed "(5 × 10^-4 m)" · transcribed "(5 × 10^-4 m)"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 127

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 9 | 127 | 127 | 127 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

# margai-pipeline ncert verify

- run: 2026-10-14 21:37 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 cc0327b3cf9acb4719122cdc610fa9460de5be9d2d743e52ad7d14dc7789f965

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 2 | withheld: illegible text layer | — | — | — | — | — | — | — |

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
none on the pages checked

symbols the page carries more often, digits and Latin only the rows carry:
- ch 2 p3: '5.0' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (58 rows)
request id: pipeline-ncert-verify-ead0e8e9-d755-4e2c-ad60-dcb434797d47

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part1/hi.jsonl (114 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4768 | 349 | 0 | 7251 | ₹2.81 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 2 p2 §2.2 ¶4: printed "x = 0.8 t^3 है" · transcribed "x = 0.08 t^3 है"
- ch 2 p2 §2.2 ¶4: printed "t_2(= t - Delta t/2)" · transcribed "t_2(= t + Delta t/2)"
- ch 2 p2 §2.2 ¶4: printed "x(t_2) = 0.03 t^3_2" · transcribed "x(t_2) = 0.08 t^3_2"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 2 p2 §2.2 ¶3: printed "t = 4 s (बिंदु P)" · transcribed "t = 4 s (बिंदु P)"
- ch 2 p2 §2.2 ¶3: printed "3 s से 5 s" · transcribed "3 s से 5 s"
- ch 2 p3 §2.3 ¶3: printed "बिंदु (v_2, t_2) को बिंदु (v_1, t_1)" · transcribed "बिंदु (v_2, t_2) को बिंदु (v_1, t_1)"
- ch 2 p4 §2.3 ¶6: printed "a = a_bar = (v - v_0) / (t - 0) होगा" · transcribed "a = a_bar = (v - v_0) / (t - 0) होगा"
- ch 2 p4 §2.3 ¶6: printed "v= v_0+at" · transcribed "v = v_0 + at"
- ch 2 p5 §2.4 ¶1: printed "= (1/2) (v–v_0) t + v_0 t" · transcribed "= (1/2) (v–v_0) t + v_0 t"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 1
verdicts recorded on rows: 58

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 2 | 58 | 58 | 57 | 1 | 0 | 0 | 0 | 98.3% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

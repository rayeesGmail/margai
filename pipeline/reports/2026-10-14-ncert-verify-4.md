# margai-pipeline ncert verify

- run: 2026-10-14 08:17 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 0f75ff489d4a49ff092ffa67a53778eb414da36fee89bdb2dba61b3f1517f03b

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

checked: 20 of the 20 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 7 p3: 'तापन' 2x in the rows, 1x on the page
- ch 7 p9: 'कम' 4x in the rows, 3x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 7 p4: '0.454' 1x in the rows, 0x on the page
- ch 7 p7: '3.14' 1x in the rows, 0x on the page
- ch 7 p18: 'dphi' 2x in the rows, 0x on the page
- ch 7 p19: 'dphi' 2x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (123 rows)
request id: pipeline-ncert-verify-10eafb5e-8d23-4021-b5cf-29ec68b2a0ef

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part1/hi.jsonl (180 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4069 | 210 | 7251 | 0 | ₹1.06 |

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

- ch 7 p3 §7.2 ¶6: printed "dc शक्ति (P = i^2R)" · transcribed "dc शक्ति (P = i^2R)"
- ch 7 p10 §7.6 ¶1: printed "epsilon" · transcribed "epsilon"
- ch 7 p15 §7.7 ¶5: printed "pi/2" · transcribed "pi/2"
- ch 7 p15 §7.7 ¶7: printed "P = I^2Z = I^2 R" · transcribed "P = I^2Z = I^2 R"
- ch 7 p15 §7.7 ¶10: printed "P = I V cos phi" · transcribed "P = I V cos phi"
- ch 7 p17 §7.7 ¶20: printed "= 222.1 rad/s" · transcribed "= 222.1 rad/s"
- ch 7 p18 §7.7 ¶26: printed "समस्वरित संधारित्र" · transcribed "समस्वरित संधारित्र"
- ch 7 p18 §7.7 ¶26: printed "संसूचित होता है" · transcribed "संसूचित होता है"
- ch 7 p20 §7.8 ¶20: printed "घट जाती है।" · transcribed "घट जाती है।"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 3
verdicts recorded on rows: 123

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 123 | 123 | 123 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

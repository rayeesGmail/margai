# margai-pipeline ncert verify

- run: 2026-10-01 18:10 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 a0704559072ce8ec1cb01acd27fae7247f3ebd21546b8da676ee7e4c584c9f69

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 6 | 2 | 3 | 4 | 0 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 1 p3: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §1 ¶1 "How wonderful is the living world! The"
- ch 1 p4: 7 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "They are Latinised or derived from" · "the second component denotes the specific" · "separately underlined, or printed in italics"
- ch 1 p5: 7 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "illustrated with the example of Mangifera"
- ch 1 p8: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.2.7 ¶4 "Table 1.1 indicates the taxonomic categories to"

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
verifier: claude-sonnet-5-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (29 rows)
request id: pipeline-ncert-verify-2675515e-fa59-46a8-accb-41cecb249709

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 6 | 6 | 0 |
artefact: verify/bio11/en.sonnet55.jsonl (6 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 6 | 22879 | 1846 | 43092 | 0 | ₹6.58 |

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

none

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 29

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 29 | 29 | 29 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 4 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

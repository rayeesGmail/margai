# margai-pipeline ncert verify

- run: 2026-09-26 09:02 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 d18ab82b054865d52fb2b4af96ffe58adf23c77b045ca13c51011ca675096fc6

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 11 | 17 | 8 | 8 | 3 | 0 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 11 p3: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §11 ¶1 "All animals including human beings depend on" · §11.1 ¶1 "Let us try to find out what" · printed starts no row begins with: "Transport that transform light energy into" · "ATP and NADPH"
- ch 11 p8: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §11.5 ¶1 "Light reactions or the ‘Photochemical’ phase include" · printed starts no row begins with: "different wavelengths of light. The single"
- ch 11 p14: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §11.7.2 ¶4 "2. Reduction – These are a series" · §11.7.2 ¶5 "3. Regeneration – Regeneration of the CO_2"

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
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (79 rows)
request id: pipeline-ncert-verify-a9b9d474-dc7d-450b-83c9-157e7dfdef43

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 17 | 2 | 15 |
artefact: verify/bio11/en.jsonl (207 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 2 | 8396 | 547 | 7251 | 7251 | ₹3.78 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 11 p15: "In Out Six CO_2 One glucose 18 ATP 18 ADP 12 NADPH 12 NADP"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 11 p8 §11.4 ¶5: printed "photosynthesis takes place in" · transcribed "photosynthesis takes place in"
- ch 11 p8 §11.4 ¶5: printed "for photosyntesis but" · transcribed "for photosyntesis but"
- ch 11 p9 §11.6 ¶1: printed "characterstic shape" · transcribed "characterstic shape"
- ch 11 p13 §11.7.2 ¶3: printed "most crucial step of the Calvin cycle" · transcribed "most crucial step of the Calvin cycle"
- ch 11 p17 §11.9 ¶3: printed "decreased. Here the RuBP instead of being converted to 2 molecules of PGA binds with O_2 to form one molecule of phosphoglycerate and phosphoglycolate" · transcribed "decreased. Here the RuBP instead of being converted to 2 molecules of PGA binds with O_2 to form one molecule of phosphoglycerate and phosphoglycolate"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 11 p9: "11.6.2 Cyclic and Non-cyclic Photo-phosphorylation"
- ch 11 p14: "Figure 11.8 The Calvin cycle proceeds in three stages"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 79

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 11 | 79 | 79 | 79 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 3 page-level start flags, 0 numbered equations the print carries that the rows do not, 1 passages no row carries

# margai-pipeline ncert verify

- run: 2026-09-24 08:23 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 6f4e190eb7330c89fa93c60157905d323de5df769f6e3c4b6d594b428aee72c7

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 11 | 17 | 8 | 8 | 5 | 0 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 11 p3: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §11 ¶1 "All animals including human beings depend on" · §11.1 ¶1 "Let us try to find out what" · printed starts no row begins with: "Transport that transform light energy into" · "ATP and NADPH"
- ch 11 p8: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §11.5 ¶1 "Light reactions or the ‘Photochemical’ phase include" · printed starts no row begins with: "different wavelengths of light. The single"
- ch 11 p12: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "Can we, hence, say that calling"
- ch 11 p14: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §11.7.2 ¶4 "2. Reduction – These are a series" · §11.7.2 ¶5 "3. Regeneration – Regeneration of the CO_2"
- ch 11 p17: 6 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "Based on the above discussion can"

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
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (77 rows)
request id: pipeline-ncert-verify-f2d137dd-54b8-466d-b385-1d9223a1a4dd

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/bio11/en.jsonl (193 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 3905 | 201 | 7251 | 0 | ₹1.02 |

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

- ch 11 p8 §11.4 ¶5: printed "photosynthesis takes place in" · transcribed "photosynthesis takes place in"
- ch 11 p8 §11.4 ¶5: printed "for photosyntesis but" · transcribed "for photosyntesis but"
- ch 11 p9 §11.6 ¶1: printed "characterstic shape" · transcribed "characterstic shape"
- ch 11 p13 §11.7.2 ¶3: printed "most crucial step of the Calvin cycle" · transcribed "most crucial step of the Calvin cycle"
- ch 11 p17 §11.9 ¶3: printed "binds with O_2 to form one molecule of phosphoglycerate" · transcribed "binds with O_2 to form one molecule of phosphoglycerate"
- ch 11 p17 §11.9 ¶3: printed "pathway, there is neither synthesis of sugars, nor of ATP. Rather it results in the release of CO_2 with the utilisation of ATP. In the photorespiratory pathway there is no synthesis of ATP or NADPH." · transcribed "pathway, there is neither synthesis of sugars, nor of ATP. Rather it results in the release of CO_2 with the utilisation of ATP. In the photorespiratory pathway there is no synthesis of ATP or NADPH."

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 11 p9: "11.6.2 Cyclic and Non-cyclic Photo-phosphorylation"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 77

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 11 | 77 | 77 | 77 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 5 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

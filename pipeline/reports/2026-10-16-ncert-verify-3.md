# margai-pipeline ncert verify

- run: 2026-10-16 23:15 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 6963a69e3e71dcef5f80471bb851563dd0ca71d261211c60d5e2d464ad24c247

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 12 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 13 of the 13 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 12 p7: 'उत्तेजित' 1x in the rows, 0x on the page
- ch 12 p13: 'हाइड्रोजन' 3x in the rows, 2x on the page

symbols the page carries more often, digits and Latin only the rows carry:
none on the pages checked

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (57 rows)
request id: pipeline-ncert-verify-bc03a69d-cefa-41b4-8a31-42aebd71cee3

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part2/hi.jsonl (99 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4466 | 295 | 7251 | 0 | ₹1.20 |

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

- ch 12 p6 §12.2.2 ¶1: printed "अतः, हाइड्रोजन" · transcribed "अतः, हाइड्रोजन"
- ch 12 p6 §12.2.2 ¶1: printed "हाइड्रोजन परमाणु में गतिशील स्थिर कक्षा के लिए" · transcribed "हाइड्रोजन परमाणु में गतिशील स्थिर कक्षा के लिए"
- ch 12 p7 §12.2.2 ¶5: printed "(1.6 × 10^(-19) C)^2" · transcribed "(1.6 × 10^(-19) C)^2"
- ch 12 p7 §12.2.2 ¶5: printed "= −13.6 × 1.6 × 10^(-19) J" · transcribed "= –13.6 × 1.6 × 10^(-19) J"
- ch 12 p8 §12.3 ¶1: printed "प्रेक्षित रैखिक स्पेक्ट्रम" · transcribed "प्रेक्षित रैखिक स्पेक्ट्रम"
- ch 12 p9 §12.4 ¶3: printed "2.2 × 10^-6 m/s" · transcribed "2.2 × 10^-6 m/s"
- ch 12 p10 §12.4 ¶9: printed "r_n = (n^2/m)(h/2pi)^2 4 pi epsilon_0 / e^2" · transcribed "r_n = (n^2/m)(h/2pi)^2 4 pi epsilon_0 / e^2"
- ch 12 p11 §12.4.1 ¶2: printed "E_3 = –1.51 eV" · transcribed "E_3 = –1.51 eV"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 12 p7: "12.3 परमाण्वीय स्पेक्ट्रम"
flags set aside by the founder's rulings in ncert-corrections.yaml: 1
verdicts recorded on rows: 57

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 12 | 57 | 57 | 57 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

# margai-pipeline ncert verify

- run: 2026-10-17 15:48 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 f613146196e740f5e8d71bf2da2e1dfaab056ee67d41220543883fd96c3ef124

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

checked: 14 of the 14 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 9 p3: 'न्यूक्लिओसाइड' 1x in the rows, 0x on the page
- ch 9 p10: 'कार्बोनिक' 2x in the rows, 1x on the page
- ch 9 p10: 'एनहाइड्रेज' 1x in the rows, 0x on the page
- ch 9 p11: 'मिलियन' 1x in the rows, 0x on the page
- ch 9 p13: 'हैं' 7x in the rows, 6x on the page
- ch 9 p14: 'अपचयित' 2x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 9 p3: '→' 2x in the rows, 0x on the page
- ch 9 p3: 'COO' 2x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (54 rows)
request id: pipeline-ncert-verify-62af6628-4210-4572-8dfc-3a7975ad58c9

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/bio11/hi.jsonl (208 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4513 | 484 | 7251 | 0 | ₹1.38 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 9 p10 §9.8.1 ¶1: printed "दर = δP / δt" · transcribed "दर = delta P / delta t"
- ch 9 p14 §9.8.5 ¶2: printed "S व के S¹ बीच" · transcribed "S व के S' बीच"
- ch 9 p14 §9.8.5 ¶2: printed "S(उपचयित) +S¹ (ऑक्सीकृत) =S (ऑक्सीकृत) +S¹ (उपचयित)" · transcribed "S(अपचयित) +S' (ऑक्सीकृत) =S (ऑक्सीकृत) +S' (अपचयित)"
- ch 9 p14 §9.8.5 ¶3: printed "S व S¹ के बीच" · transcribed "S व S' के बीच"
- ch 9 p14 §9.8.5 ¶3: printed "S- G+ S¹ → S + S¹ G" · transcribed "S- G+ S' -> S + S' G"
- ch 9 p14 §9.8.5 ¶5: printed "X—C—C-Y → X-Y+ C=C" · transcribed "X-C—C-Y -> X-Y+ C=C"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 9 p3 §9.1 ¶4: printed "ट्रिप्टोफान" · transcribed "ट्रिप्टोफान"
- ch 9 p11 §9.8.2 ¶1: printed "अविकल्पीय निर्माण होता है। ई (E)" · transcribed "अविकल्पीय निर्माण होता है। ई (E)"
- ch 9 p15 §9.8.6 ¶5: printed "निर्णायक भूमिका" · transcribed "निर्णायक भूमिका"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 3
verdicts recorded on rows: 54

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 9 | 54 | 54 | 50 | 4 | 0 | 0 | 0 | 92.6% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

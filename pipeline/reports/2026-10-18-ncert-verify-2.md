# margai-pipeline ncert verify

- run: 2026-10-18 08:58 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 e9a2c4c85b242a03a3300156b7ca3e761e61fb4e54d3735f7636f7d867d7966d

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 10 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 21 of the 21 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 10 p5: 'दाब' 3x in the rows, 2x on the page
- ch 10 p10: 'द्रव' 6x in the rows, 5x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 10 p3: '∝' 2x in the rows, 0x on the page
- ch 10 p5: '×' 13x on the page, 2x in the rows
- ch 10 p6: '≈' 2x in the rows, 0x on the page
- ch 10 p6: '≃' 2x in the rows, 0x on the page
- ch 10 p19: '28' 1x in the rows, 0x on the page

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (107 rows)
request id: pipeline-ncert-verify-f6c4c6a7-5640-4001-bbf7-967e952627f2

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part2/hi.jsonl (109 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5867 | 231 | 7251 | 0 | ₹1.40 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 10 p10 §10.8 ¶6: printed "ठोस से वाष्प" · transcribed "द्रव से वाष्प में अवस्था परिवर्तन की अवधि"
- ch 10 p19 §10.9.4 ¶8: printed "त्वचा का ताप 20°C है।" · transcribed "त्वचा का ताप 28°C है।"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 10 p2 §10.3 ¶2: printed "भाप-बिंदु के मान क्रमश: 32°F तथा 212°F हैं" · transcribed "भाप-बिंदु के मान क्रमशः 32°F तथा 212°F हैं"
- ch 10 p2 §10.4 ¶1: printed "रसायनज्ञ रॉबर्ट बॉयल" · transcribed "रसायनज्ञ रॉबर्ट बॉयल"
- ch 10 p4 §10.5 ¶3: printed "alpha_l Delta T (10.4) जहाँ alpha_l को" · transcribed "alpha_l Delta T (10.4) जहाँ alpha_l को"
- ch 10 p6 §10.5 ¶18: printed "L_T1 = 5.231 m" · transcribed "L_T1 = 5.231 m"
- ch 10 p6 §10.5 ¶18: printed "T_2 = 218°C" · transcribed "T_2 = 218°C"
- ch 10 p7 §10.6 ¶9: printed "मोल mu" · transcribed "मोल mu"
- ch 10 p13 §10.8.1 ¶5: printed "m_2L_f" · transcribed "m_2L_f"
- ch 10 p14 §10.9.1 ¶5: printed "प्लास्टिक फेन" · transcribed "प्लास्टिक फेन"
- ch 10 p14 §10.9.1 ¶5: printed "फोन-रोधन" · transcribed "फोन-रोधन"
- ch 10 p14 §10.9.1 ¶5: printed "इसके विपरीत" · transcribed "इसके विपरीत"
- ch 10 p20 §10.10 ¶5: printed "समीकरणों (10.15) तथा (10.16) से हमें प्राप्त होता है" · transcribed "समीकरणों (10.15) तथा (10.16) से हमें प्राप्त होता है"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 107

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 10 | 107 | 107 | 105 | 2 | 0 | 0 | 0 | 98.1% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

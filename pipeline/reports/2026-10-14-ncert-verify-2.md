# margai-pipeline ncert verify

- run: 2026-10-14 08:16 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (hi)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 0f75ff489d4a49ff092ffa67a53778eb414da36fee89bdb2dba61b3f1517f03b

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | withheld: illegible text layer | — | — | — | — | — | — | — |

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

checked: 37 of the 37 page(s) the loaded rows are on

words the rows carry more often than the page — a misreading or an addition:
- ch 1 p9: 'आकर्षी' 2x in the rows, 1x on the page
- ch 1 p25: 'हैं' 11x in the rows, 10x on the page
- ch 1 p26: 'द्विध्रुव' 9x in the rows, 8x on the page
- ch 1 p36: 'परिमाण' 3x in the rows, 2x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 1 p18: '1.3' 1x in the rows, 0x on the page
- ch 1 p18: '1.6' 1x in the rows, 0x on the page
- ch 1 p18: '2.0' 1x in the rows, 0x on the page
- ch 1 p24: '×' 2x on the page, 1x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
none on the pages checked
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (195 rows)
request id: pipeline-ncert-verify-f6f3a2a4-d46e-46f7-9a08-2d22e6bd81dd

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy12-part1/hi.jsonl (180 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4394 | 228 | 0 | 7251 | ₹2.63 |

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

- ch 1 p8 §1.5 ¶12: printed "r_21 एवं r_12 द्वारा होता है (r_21 = r_12)" · transcribed "r_21 एवं r_12 द्वारा होता है (r_21 = r_12)"
- ch 1 p9 §1.5 ¶18: printed "m_e = 9.11 × 10^(-31) kg)" · transcribed "m_e = 9.11 × 10^(-31) kg)"
- ch 1 p9 §1.5 ¶19: printed "F_G = −G m_p m_e / r^2 यहाँ m_p तथा m_e क्रमशः प्रोटॉन" · transcribed "F_G = −G m_p m_e / r^2 यहाँ m_p तथा m_e क्रमशः प्रोटॉन"
- ch 1 p13 §1.6 ¶9: printed "से केंद्रक की दूरी AO = (2/3) AD" · transcribed "से केंद्रक की दूरी AO = (2/3) AD"
- ch 1 p13 §1.6 ¶12: printed "F_1 = F r_hat_1" · transcribed "F_1 = F r_hat_1"
- ch 1 p24 §1.10.1 ¶5: printed "E_(-q) = q / (4 pi epsilon_o) 1 / (r^2 + a^2)" · transcribed "E_(-q) = q / (4 pi epsilon_o) 1 / (r^2 + a^2)"
- ch 1 p29 §1.12 ¶7: printed "स्थिति सदिश R के साथ" · transcribed "स्थिति सदिश R के साथ"
- ch 1 p29 §1.12 ¶7: printed "r'^2) r_hat' (1.26)" · transcribed "r'^2) r_hat' (1.26)"
- ch 1 p29 §1.13 ¶3: printed "गोले से गुजरने वाला कुल फ्लस्क" · transcribed "गोले से गुजरने वाला कुल फ्लस्क"
- ch 1 p31 §1.13 ¶16: printed "n_hat_L = E_L ΔS cos theta" · transcribed "n_hat_L = E_L ΔS cos theta"
- ch 1 p31 §1.13 ¶16: printed "= –E_L a^2" · transcribed "= –E_L a^2"
- ch 1 p31 §1.13 ¶16: printed "phi_R = E_R·ΔS = E_R ΔS cos theta" · transcribed "phi_R = E_R·ΔS = E_R ΔS cos theta"
- ch 1 p31 §1.13 ¶16: printed "= E_R a^2" · transcribed "= E_R a^2"
- ch 1 p36 §1.14.3 ¶8: printed "द्रव्यमान खोल" · transcribed "द्रव्यमान खोल"
- ch 1 p37 §1.14.3 ¶11: printed "E (r) = 0" · transcribed "E (r) = 0"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 9
verdicts recorded on rows: 195

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 195 | 195 | 195 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 0 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

# margai-pipeline ncert verify

- run: 2026-10-01 18:11 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 2 chapters of bio12 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 a0704559072ce8ec1cb01acd27fae7247f3ebd21546b8da676ee7e4c584c9f69

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 7 | 8 | 6 | 1 | 3 | 0 | 0 | 0 | 0 |
| 9 | 8 | 7 | 0 | 6 | 0 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 7 p3: 1 rows start here, the print starts 5 paragraphs — rows the print does not start: §7 ¶1 "Health, for a long time, was considered" · printed starts no row begins with: "(i) genetic disorders – deficiencies with" · "from parents from birth;" · "(ii) infections and" · "(iii) life style including food and" · "exercise we give to our bodies,"
- ch 7 p8: 7 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "prevents entry of the micro-organisms. Mucus" · "tracts also help in trapping microbes" · "tears from eyes–all prevent microbial growth." · "like polymorpho-nuclear leukocytes (PMNL-neutrophils) and"
- ch 7 p9: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.2.2 ¶3 "The B-lymphocytes produce an army of proteins" · printed starts no row begins with: "interferons which protect non-infected cells from"
- ch 9 p3: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §9 ¶1 "Biotechnology deals with techniques of using live"
- ch 9 p4: 5 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "phenotype of the host organism." · "like antibiotics, vaccines, enzymes, etc."
- ch 9 p5: 5 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "(i) identification of DNA with desirable" · "(ii) introduction of the identified DNA" · "(iii) maintenance of introduced DNA in" · "to its progeny."
- ch 9 p8: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.2.1 ¶11 "Separation and isolation of DNA fragments :" · §9.2.1 ¶12 "The separated DNA fragments can be visualised"
- ch 9 p9: 4 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.2.2 ¶2 "The following are the features that are" · printed starts no row begins with: "cloned in a vector whose origin" · "against any of these antibiotics."
- ch 9 p10: 3 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "these are identified as recombinant colonies."

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
verifier: claude-sonnet-5-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (60 rows)
request id: pipeline-ncert-verify-9ef82de8-b2c8-4421-b656-8c0a54c6d1ae

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 16 | 16 | 0 |
artefact: verify/bio12/en.sonnet55.jsonl (16 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 16 | 60636 | 4857 | 114912 | 0 | ₹17.44 |

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
verdicts recorded on rows: 60

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 29 | 29 | 29 | 0 | 0 | 0 | 0 | 100.0% |
| 9 | 31 | 31 | 31 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 9 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

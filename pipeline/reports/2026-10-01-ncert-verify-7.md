# margai-pipeline ncert verify

- run: 2026-10-01 17:41 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 a0704559072ce8ec1cb01acd27fae7247f3ebd21546b8da676ee7e4c584c9f69

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 7 | 8 | 6 | 1 | 8 | 0 | 1 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 7 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §7 ¶1 "You have learnt that substitution of one"
- ch 7 p2: 8 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.1 ¶1 "The classification of compounds makes their study" · §7.1.1 ¶1 "Alcohols and phenols may be classified as" · §7.1.1 ¶2 "Monohydric alcohols may be further classified according" · §7.1.1 ¶4 "Primary, secondary and tertiary alcohols: In these" · §7.1.1 ¶5 "Allylic alcohols: In these alcohols, the —OH" · §7.1.1 ¶6 "Benzylic alcohols: In these alcohols, the —OH" · printed starts no row begins with: "Mono, Di, polyhydric compounds depending on"
- ch 7 p3: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.1.1 ¶7 "Allylic and benzylic alcohols may be primary," · §7.2 ¶1 "(a) Alcohols: The common name of an" · printed starts no row begins with: "groups attached to the oxygen atom" · "7.2 Nomenclature (a) Alcohols: The common"
- ch 7 p4: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.2 ¶2 "According to IUPAC system, the name of" · printed starts no row begins with: "Table 7.1: Common and IUPAC Names"
- ch 7 p5: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §7.2 ¶5 "Dihydroxy derivatives of benzene are known as" · §7.2 ¶6 "(c) Ethers: Common names of ethers are"
- ch 7 p6: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.2 ¶7 "If both the alkyl groups are the" · §7.2 ¶8 "According to IUPAC system of nomenclature, ethers" · §7.2 ¶10 "Solution (i) 4-Chloro-2,3-dimethylpentan-1-ol (ii) 2-Ethoxypropane (iii) 2,6-Dimethylphenol" · §7.3 ¶1 "In alcohols, the oxygen of the –OH" · printed starts no row begins with: "7.3 Structures of In alcohols, the"
- ch 7 p7: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §7.4.1 ¶1 "Alcohols are prepared by the following methods:" · §7.4.1 ¶2 "1. From alkenes" · §7.4.1 ¶4 "Mechanism" · §7.4.1 ¶5 "The mechanism of the reaction involves the" · printed starts no row begins with: "Step 2: Nucleophilic attack of water" · "Step 3: Deprotonation to form an"
- ch 7 p8: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.4.1 ¶10 "(ii) By reduction of carboxylic acids and" · printed starts no row begins with: "alcohol is obtained in excellent yield."

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

- ch 7 §7.2 ¶6: figure_refs carries "Table 7.2", which the paragraph never mentions

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
- ch 7 §7.1.1 ¶7 starts a paragraph at the top of p3, but the print continues p2's: "Allylic and benzylic alcohols may be" — ruled noise: p3 opens with the display of primary, secondary and tertiary benzylic alcohols; the capitalised sentence after it stays as transcribed
- ch 7 §7.2 ¶7 starts a paragraph at the top of p6, but the print continues p5's: "If both the alkyl groups are" — ruled noise: p5 ends in the ether examples; p6 sets its lines flush at x 220 whether they continue or not ("According to IUPAC…" below is flush too), so the flush top proves nothing
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5-5 (38 rows)
request id: pipeline-ncert-verify-ef7b26b8-b5cd-420d-8b7b-0188a076242e

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 8 | 8 | 0 |
artefact: verify/chem12-part2/en.sonnet5.jsonl (8 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 8 | 29673 | 1838 | 58008 | 0 | ₹8.08 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 7 p7 §7.4.1 ¶5: printed "Step 1: Protonation of alkene" · transcribed "Step 1: Protonation of alkene to form carbocation by electrophilic attack of H_3O^+."
- ch 7 p8 §7.4.1 ¶6: printed "CH_3–CH–CH_2 | H  BH_2" · transcribed "CH_3–CH(H)–CH_2(BH_2);"
- ch 7 p8 §7.4.1 ¶6: printed "H_2O, 3H_2O_2, OH_bar" · transcribed "H_2O, 3H_2O_2, OH^-"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 7 p2: "CH2OH C2H5OH Monohydric Dihydric Trihydric structures diagram"
- ch 7 p2: "Primary (1°) Secondary (2°) Tertiary (3°) diagram"
- ch 7 p2: "CH2=CH-CH2-OH Primary Secondary Tertiary diagram for allylic alcohols"
- ch 7 p7: "C=C< + H2O <-> >C-C< H OH"
- ch 7 p7: "CH3CH=CH2 + H2O <-> CH3-CH-CH3 OH"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 7 p6 §7.2 ¶9: printed "(iii) H_3C, OH, CH_3 substituted benzene" · transcribed "(iii) H_3C, OH, CH_3 substituted benzene"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 7 p3: "7.1 Classify the following as primary, secondary and tertiary alcohols"
- ch 7 p3: "7.2 Identify allylic alcohols in the above examples"
- ch 7 p4: "Table 7.1: Common and IUPAC Names of Some Alcohols"
- ch 7 p6: "7.3 Name the following compounds according to IUPAC system"
- ch 7 p6: "Fig. 7.1: Structures of methanol, phenol and methoxymethane"
flags set aside by the founder's rulings in ncert-corrections.yaml: 1
verdicts recorded on rows: 38

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 38 | 38 | 36 | 2 | 0 | 0 | 1 | 92.1% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 8 page-level start flags, 0 numbered equations the print carries that the rows do not, 5 passages no row carries

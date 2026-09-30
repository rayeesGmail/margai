# margai-pipeline ncert extract

- run: 2026-09-30 07:24 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 3 chapters of chem11-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-4ccfcf11-b087-4740-a6eb-15e842e2bb36

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 7 | fed as the character authority | 0.318 |
| 8 | withheld: illegible | 0.023 |
| 9 | fed as the character authority | 0.275 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 7 | page 18 | SUMMARY | not sent: nothing taught above the heading | 4 |
| 8 | page 36 | SUMMARY | sent: the heading could not be placed on it | 3 |
| 9 | page 32 | SUMMARY | not sent: nothing taught above the heading | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 21 | 17 | 156 |
| 8 | 39 | 0 | 286 |
| 9 | 33 | 31 | 233 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 93 | 48 | 36 | 9 | 675 |

## characters that differ from the page's text layer — adjudicate these

checked: 48 of the 48 page(s) called this run
- ch 7 p4 §7.2.1 #6: 'release' 1x here, 0x on the page
- ch 7 p4 §7.2.1 #8: 'release' 1x here, 0x on the page
- ch 7 p4 §7.2.1 #10: 'release' 1x here, 0x on the page
- ch 7 p6 §7.3 #5: 'however' 1x here, 0x on the page
- ch 7 p6 §7.3 #5: 'come' 1x here, 0x on the page
- ch 7 p6 §7.3 #5: 'peroxides' 4x here, 3x on the page
- ch 7 p6 §7.3 #5: 'superoxides' 2x here, 1x on the page
- ch 7 p6 §7.3 #7: 'halogens' 1x here, 0x on the page
- ch 7 p6 §7.3 #7: 'they' 1x here, 0x on the page
- ch 7 p6 §7.3 #7: 'occur' 1x here, 0x on the page
- ch 7 p6 §7.3 #7: 'halide' 1x here, 0x on the page
- ch 7 p6 §7.3 #8: 'must' 3x here, 2x on the page
- ch 7 p7 §7.3 #11: 'from' 2x here, 1x on the page
- ch 7 p8 §7.3.1 #4: 'nah' 1x here, 0x on the page
- ch 7 p8 §7.3.1 #4: 'kcl' 2x here, 1x on the page
- ch 7 p8 §7.3.1 #8: 'mgcl' 1x here, 0x on the page
- ch 7 p9 §7.3.1 #5: 'copper' 1x here, 0x on the page
- ch 9 p2 §9.1 #1: 'multiple' 1x here, 0x on the page
- ch 9 p2 §9.2.1 #5: 'propane' 1x here, 0x on the page
- ch 9 p3 §9.2.1 #4: 'with' 1x here, 0x on the page
- ch 9 p3 §9.2.1 #4: 'groups' 1x here, 0x on the page
- ch 9 p3 §9.2.1 #4: 'above' 1x here, 0x on the page
- ch 9 p3 §9.2.1 #4: 'below' 1x here, 0x on the page
- ch 9 p3 §9.2.1 #5: 'primary' 3x here, 2x on the page
- ch 9 p3 §9.2.1 #5: 'secondary' 2x here, 1x on the page
- ch 9 p3 §9.2.1 #5: 'tertiary' 2x here, 1x on the page
- ch 9 p3 §9.2.1 #5: 'quaternary' 2x here, 1x on the page
- ch 9 p3 §9.2.1 #5: 'identify' 1x here, 0x on the page
- ch 9 p4 §9.2.1 #4: 'write' 2x here, 1x on the page
- ch 9 p4 §9.2.1 #4: 'iupac' 1x here, 0x on the page
- ch 9 p6 §9.2.1 #1: 'substituents' 1x here, 0x on the page
- ch 9 p6 §9.2.1 #2: 'substituents' 1x here, 0x on the page
- ch 9 p6 §9.2.1 #5: 'substituents' 2x here, 0x on the page
- ch 9 p6 §9.2.1 #7: 'substituent' 1x here, 0x on the page
- ch 9 p6 §9.2.1 #8: 'substituents' 1x here, 0x on the page
- ch 9 p7 §9.2.2 #2: 'brch' 1x here, 0x on the page
- ch 9 p7 §9.2.2 #2: 'nabr' 4x here, 0x on the page
- ch 9 p7 §9.2.2 #2: 'brc' 2x here, 0x on the page
- ch 9 p7 §9.2.2 #2: 'bromoethane' 1x here, 0x on the page
- ch 9 p7 §9.2.2 #2: 'butane' 1x here, 0x on the page
- ch 9 p8 §9.2.3 #3: 'dimethylpropane' 2x here, 1x on the page
- ch 9 p8 §9.2.3 #5: 'presence' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #2: 'fluorination' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #2: 'violent' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #2: 'iodination' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #2: 'very' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #2: 'reversible' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #3: 'supposed' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #3: 'proceed' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #3: 'via' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #3: 'involving' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #3: 'given' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #5: 'dot' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #6: 'dot' 2x here, 0x on the page
- ch 9 p9 §9.2.3 #7: 'dot' 2x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'above' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'repeat' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'respectively' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'thereby' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'setup' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'directly' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'give' 2x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'principal' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'given' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'explain' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'highly' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #8: 'dot' 4x here, 0x on the page
- ch 9 p9 §9.2.3 #10: 'dot' 6x here, 0x on the page
- ch 9 p9 §9.2.3 #11: 'above' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #11: 'helps' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #13: 'dioxide' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #13: 'evolution' 1x here, 0x on the page
- ch 9 p9 §9.2.3 #15: 'evolution' 1x here, 0x on the page
- ch 9 p10 §9.2.3 #4: 'alcl' 1x here, 0x on the page
- ch 9 p10 §9.2.3 #4: 'hcl' 1x here, 0x on the page
- ch 9 p11 §9.2.4 #1: 'permits' 1x here, 0x on the page
- ch 9 p11 §9.2.4 #3: 'paper' 1x here, 0x on the page
- ch 9 p12 §9.2.4 #1: 'this' 2x here, 1x on the page
- ch 9 p15 §9.3.4 #8: 'liquid' 2x here, 1x on the page
- ch 9 p15 §9.3.4 #8: 'alkene' 5x here, 4x on the page
- ch 9 p16 §9.3.4 #3: 'chbr' 1x here, 0x on the page
- ch 9 p16 §9.3.4 #4: 'represented' 1x here, 0x on the page
- ch 9 p16 §9.3.5 #8: 'oxidation' 1x here, 0x on the page
- ch 9 p16 §9.3.5 #8: 'ozonolysis' 1x here, 0x on the page
- ch 9 p17 §9.3.5 #1: 'ccl' 1x here, 0x on the page
- ch 9 p17 §9.3.5 #1: 'with' 2x here, 1x on the page
- ch 9 p17 §9.3.5 #1: 'ethene' 1x here, 0x on the page
- ch 9 p17 §9.3.5 #1: 'dibromoethane' 1x here, 0x on the page
- ch 9 p17 §9.3.5 #1: 'propene' 1x here, 0x on the page
- ch 9 p17 §9.3.5 #1: 'dichloropropane' 1x here, 0x on the page
- ch 9 p17 §9.3.5 #6: 'propene' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #1: 'hso' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #1: 'ethyl' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #1: 'hydrogen' 2x here, 0x on the page
- ch 9 p19 §9.3.5 #1: 'sulphate' 2x here, 0x on the page
- ch 9 p19 §9.3.5 #1: 'hoso' 3x here, 0x on the page
- ch 9 p19 §9.3.5 #1: 'oso' 4x here, 0x on the page
- ch 9 p19 §9.3.5 #1: 'propyl' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #2: 'methylpropene' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #2: 'methylpropan' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #3: 'kmno' 3x here, 2x on the page
- ch 9 p19 §9.3.5 #3: 'dil' 3x here, 1x on the page
- ch 9 p19 §9.3.5 #3: 'ethane' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #3: 'diol' 2x here, 0x on the page
- ch 9 p19 §9.3.5 #3: 'glycol' 2x here, 1x on the page
- ch 9 p19 §9.3.5 #3: 'propane' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #4: 'methylpropene' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #4: 'propan' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #5: 'ozonide' 4x here, 2x on the page
- ch 9 p19 §9.3.5 #5: 'propene' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #5: 'cho' 3x here, 0x on the page
- ch 9 p19 §9.3.5 #5: 'hcho' 2x here, 0x on the page
- ch 9 p19 §9.3.5 #5: 'ethanal' 2x here, 0x on the page
- ch 9 p19 §9.3.5 #5: 'methanal' 1x here, 0x on the page
- ch 9 p19 §9.3.5 #5: 'propan' 1x here, 0x on the page
- ch 9 p20 §9.4.1 #6: 'butyne' 1x here, 0x on the page
- ch 9 p20 §9.4.1 #6: 'pent' 2x here, 0x on the page
- ch 9 p20 §9.4.1 #6: 'methylbut' 1x here, 0x on the page
- ch 9 p21 §9.4.1 #1: 'above' 1x here, 0x on the page
- ch 9 p21 §9.4.1 #1: 'below' 1x here, 0x on the page
- ch 9 p21 §9.4.2 #4: 'enthalpy' 3x here, 2x on the page
- ch 9 p22 §9.4.4 #7: 'nanh' 3x here, 2x on the page
- ch 9 p23 §9.4.4 #3: 'cbr' 2x here, 0x on the page
- ch 9 p23 §9.4.4 #3: 'chbr' 3x here, 1x on the page
- ch 9 p23 §9.4.4 #3: 'dibromopropene' 1x here, 0x on the page
- ch 9 p23 §9.4.4 #3: 'tetrabromopropane' 1x here, 0x on the page
- ch 9 p23 §9.4.4 #5: 'hbr' 5x here, 3x on the page
- ch 9 p23 §9.4.4 #5: 'chbr' 2x here, 1x on the page
- ch 9 p23 §9.4.4 #6: 'bromopropene' 1x here, 0x on the page
- ch 9 p23 §9.4.4 #6: 'dibromopropane' 1x here, 0x on the page
- ch 9 p23 §9.4.4 #7: 'one' 2x here, 1x on the page
- ch 9 p23 §9.4.4 #7: 'isomerisation' 2x here, 0x on the page
- ch 9 p23 §9.4.4 #7: 'ethanal' 1x here, 0x on the page
- ch 9 p23 §9.4.4 #7: 'propanone' 1x here, 0x on the page
- ch 9 p23 §9.4.4 #9: 'represented' 1x here, 0x on the page
- ch 9 p27 §9.5.4 #9: 'coona' 1x here, 0x on the page
- ch 9 p27 §9.5.4 #9: 'naoh' 1x here, 0x on the page
- ch 9 p27 §9.5.4 #9: 'cao' 1x here, 0x on the page
- ch 9 p29 §9.5.5 #5: 'hso' 1x here, 0x on the page
- ch 9 p30 §9.5.6 #8: 'phenol' 2x here, 1x on the page
- ch 9 p30 §9.5.6 #9: 'therefore' 1x here, 0x on the page
- ch 9 p31 §9.5.6 #1: 'nhr' 1x here, 0x on the page
- ch 9 p31 §9.5.6 #1: 'nhcoch' 1x here, 0x on the page
- ch 9 p31 §9.5.6 #1: 'och' 2x here, 1x on the page
- ch 9 p31 §9.5.6 #2: 'positions' 1x here, 0x on the page
- ch 9 p31 §9.5.6 #3: 'cho' 1x here, 0x on the page
- ch 9 p31 §9.5.6 #3: 'cor' 1x here, 0x on the page
- ch 9 p31 §9.5.6 #3: 'cooh' 1x here, 0x on the page
- ch 9 p31 §9.5.6 #3: 'coor' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 48 of the 48 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 48 of the 48 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 48 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 48 | 250461 | 64657 | 529408 | 11264 | ₹288.58 |
jsonl: extract/chem11-part2/en.jsonl (93 pages, 39 of them from earlier runs)

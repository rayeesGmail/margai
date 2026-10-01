# margai-pipeline ncert extract

- run: 2026-09-28 07:43 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 2 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-7fd1f198-8e2a-4c25-93b9-62018cac3d0c

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 8 | fed as the character authority | 0.265 |
| 10 | fed as the character authority | 0.323 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 8 | page 28 | SUMMARY | sent: 12 prose line(s) above the heading | 4 |
| 10 | page 21 | SUMMARY | sent: 27 prose line(s) above the heading | 1 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 8 | 32 | 28 | 139 |
| 10 | 22 | 21 | 98 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 54 | 49 | 0 | 5 | 237 |

## characters that differ from the page's text layer — adjudicate these

checked: 49 of the 49 page(s) called this run
- ch 8 p6 §8.2.2 #4: 'rcn' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'sncl' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'hcl' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'rch' 2x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'rcho' 1x here, 0x on the page
- ch 8 p7 §8.2.3 #6: 'cdcl' 2x here, 0x on the page
- ch 8 p12 §8.4 #2: 'nhz' 1x here, 0x on the page
- ch 8 p13 §8.4 #1: 'reduction' 3x here, 2x on the page
- ch 8 p13 §8.4 #1: 'ethylene' 2x here, 1x on the page
- ch 8 p13 §8.4 #1: 'glycol' 2x here, 1x on the page
- ch 8 p13 §8.4 #1: 'wolff' 2x here, 1x on the page
- ch 8 p13 §8.4 #1: 'kishner' 2x here, 1x on the page
- ch 8 p13 §8.4 #1: 'hcl' 1x here, 0x on the page
- ch 8 p13 §8.4 #1: 'clemmensen' 1x here, 0x on the page
- ch 8 p13 §8.4 #1: 'nnh' 1x here, 0x on the page
- ch 8 p13 §8.4 #1: 'koh' 1x here, 0x on the page
- ch 8 p13 §8.4 #1: 'rduction' 1x here, 0x on the page
- ch 8 p13 §8.4 #3: 'cho' 1x here, 0x on the page
- ch 8 p13 §8.4 #3: 'cooh' 1x here, 0x on the page
- ch 8 p13 §8.4 #4: 'bond' 3x here, 1x on the page
- ch 8 p13 §8.4 #4: 'cleavage' 3x here, 1x on the page
- ch 8 p13 §8.4 #4: 'cooh' 4x here, 0x on the page
- ch 8 p13 §8.4 #6: 'rcho' 1x here, 0x on the page
- ch 8 p13 §8.4 #6: 'rcoo' 1x here, 0x on the page
- ch 8 p13 §8.4 #7: 'brown' 2x here, 1x on the page
- ch 8 p13 §8.4 #7: 'cho' 1x here, 0x on the page
- ch 8 p13 §8.4 #7: 'rcoo' 1x here, 0x on the page
- ch 8 p13 §8.4 #7: 'ppt' 1x here, 0x on the page
- ch 8 p19 §8.7 #4: 'kmno' 2x here, 1x on the page
- ch 8 p19 §8.7 #4: 'alkaline' 2x here, 1x on the page
- ch 8 p19 §8.7 #4: 'cro' 2x here, 1x on the page
- ch 8 p19 §8.7 #4: 'rch' 1x here, 0x on the page
- ch 8 p19 §8.7 #4: 'rcooh' 1x here, 0x on the page
- ch 8 p19 §8.7 #4: 'decanol' 1x here, 0x on the page
- ch 8 p19 §8.7 #4: 'decanoic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'cro' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'jones' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'reagent' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'cooh' 3x here, 0x on the page
- ch 8 p22 §8.7 #1: 'butan' 4x here, 0x on the page
- ch 8 p22 §8.7 #1: 'butanoic' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'acid' 8x here, 1x on the page
- ch 8 p22 §8.7 #1: 'hbr' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'kcn' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'heat' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'benzyl' 3x here, 0x on the page
- ch 8 p22 §8.7 #1: 'alcohol' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'bromide' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'cyanide' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'phenylethanoic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'nitrobromobenzene' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'ether' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'armgbr' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dry' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'ice' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'nitrobenzoic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'methylacetophenone' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'kmno' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'koh' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dipotassium' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'benzene' 3x here, 2x on the page
- ch 8 p22 §8.7 #1: 'dicarboxylate' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dil' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dicarboxylic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'terephthalic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'cyclohexene' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'hexane' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dioic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'adipic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'ammoniacal' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'agno' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'tollens' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'butanal' 1x here, 0x on the page
- ch 8 p23 §8.9.1 #6: 'cooh' 3x here, 1x on the page
- ch 8 p23 §8.9.1 #6: 'coo' 6x here, 1x on the page
- ch 8 p23 §8.9.1 #6: 'sodium' 1x here, 0x on the page
- ch 8 p23 §8.9.1 #6: 'naoh' 1x here, 0x on the page
- ch 8 p23 §8.9.1 #6: 'nahco' 1x here, 0x on the page
- ch 8 p24 §8.9.1 #1: 'rcoo' 4x here, 0x on the page
- ch 8 p24 §8.9.1 #1: 'rcooh' 3x here, 0x on the page
- ch 8 p25 §8.9.1 #2: 'continue' 3x here, 2x on the page
- ch 8 p25 §8.9.2 #6: 'rcooh' 1x here, 0x on the page
- ch 8 p25 §8.9.2 #6: 'rcoor' 1x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'pcl' 6x here, 4x on the page
- ch 8 p26 §8.9.2 #2: 'socl' 4x here, 3x on the page
- ch 8 p26 §8.9.2 #2: 'rcooh' 3x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'rcocl' 3x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'pocl' 1x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'hcl' 2x here, 0x on the page
- ch 8 p27 §8.9.3 #1: 'lialh' 1x here, 0x on the page
- ch 8 p27 §8.9.3 #1: 'ether' 1x here, 0x on the page
- ch 8 p27 §8.9.3 #2: 'naoh' 2x here, 1x on the page
- ch 8 p27 §8.9.3 #2: 'cao' 2x here, 1x on the page
- ch 8 p27 §8.9.3 #2: 'coona' 1x here, 0x on the page
- ch 8 p27 §8.9.3 #2: 'heat' 2x here, 1x on the page
- ch 8 p27 §8.9.4 #4: 'phosphorus' 2x here, 1x on the page
- ch 8 p27 §8.9.4 #4: 'halocarboxylic' 2x here, 1x on the page
- ch 8 p27 §8.9.4 #4: 'cooh' 2x here, 1x on the page

## pages whose text is not all there — or is there twice

checked: 49 of the 49 page(s) called this run
- ch 8 p4: only 9% of the page's characters came back — text is missing

and 1 page(s) the ratio could not judge:
- ch 8 p22: the layer holds 184 characters, too few to measure a ratio against

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 8 p28: 2 paragraph(s) — sent: 12 prose line(s) above the heading
- ch 10 p21: 2 paragraph(s) — sent: 27 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 49 of the 49 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 49 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 8 page 22 — confidence 0.72, 1 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 49 | 223803 | 37318 | 540672 | 11264 | ₹215.59 |
jsonl: extract/chem12-part2/en.jsonl (54 pages, 0 of them from earlier runs)

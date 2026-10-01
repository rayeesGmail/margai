# margai-pipeline ncert extract

- run: 2026-09-28 15:11 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 5 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-8d798ebe-6599-4f23-b375-e37b1ef172cc

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 6 | fed as the character authority | 0.296 |
| 7 | fed as the character authority | 0.304 |
| 8 | fed as the character authority | 0.265 |
| 9 | fed as the character authority | 0.293 |
| 10 | fed as the character authority | 0.323 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 6 | page 30 | SUMMARY | sent: 30 prose line(s) above the heading | 4 |
| 7 | page 29 | SUMMARY | not sent: nothing taught above the heading | 6 |
| 8 | page 28 | SUMMARY | sent: 12 prose line(s) above the heading | 4 |
| 9 | page 19 | SUMMARY | sent: 5 prose line(s) above the heading | 3 |
| 10 | page 21 | SUMMARY | sent: 27 prose line(s) above the heading | 1 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 6 | 34 | 0 | 155 |
| 7 | 34 | 18 | 179 |
| 8 | 32 | 0 | 139 |
| 9 | 22 | 19 | 106 |
| 10 | 22 | 0 | 98 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 144 | 37 | 89 | 18 | 677 |

## characters that differ from the page's text layer — adjudicate these

checked: 37 of the 37 page(s) called this run
- ch 7 p13 §7.4.4 #3: 'aluminium' 2x here, 1x on the page
- ch 7 p13 §7.4.4 #3: 'alkoxide' 2x here, 1x on the page
- ch 7 p13 §7.4.4 #3: 'tert' 2x here, 0x on the page
- ch 7 p13 §7.4.4 #3: 'butyl' 1x here, 0x on the page
- ch 7 p13 §7.4.4 #3: 'butoxide' 1x here, 0x on the page
- ch 7 p16 §7.4.4 #1: 'roh' 3x here, 1x on the page
- ch 7 p16 §7.4.4 #1: 'cooh' 2x here, 0x on the page
- ch 7 p16 §7.4.4 #1: 'rocor' 3x here, 1x on the page
- ch 7 p16 §7.4.4 #1: 'aroh' 1x here, 0x on the page
- ch 7 p16 §7.4.4 #1: 'cocl' 1x here, 0x on the page
- ch 7 p17 §7.4.4 #1: 'chohch' 1x here, 0x on the page
- ch 7 p18 §7.4.4 #1: 'rch' 2x here, 0x on the page
- ch 7 p18 §7.4.4 #1: 'rcho' 1x here, 0x on the page
- ch 7 p22 §7.5 #3: 'zno' 2x here, 1x on the page
- ch 7 p22 §7.5 #3: 'atm' 1x here, 0x on the page
- ch 7 p22 §7.5 #6: 'glucose' 3x here, 2x on the page
- ch 7 p22 §7.5 #6: 'fructose' 3x here, 2x on the page
- ch 7 p22 §7.5 #6: 'invertase' 2x here, 1x on the page
- ch 7 p23 §7.6.1 #3: 'iii' 1x here, 0x on the page
- ch 9 p5 §9.4 #1: 'rnh' 1x here, 0x on the page
- ch 9 p5 §9.4 #2: 'naoh' 1x here, 0x on the page
- ch 9 p5 §9.4 #6: 'chloroethane' 1x here, 0x on the page
- ch 9 p5 §9.4 #6: 'ethanamine' 4x here, 0x on the page
- ch 9 p5 §9.4 #6: 'ethylethanamine' 2x here, 0x on the page
- ch 9 p5 §9.4 #6: 'diethylethanamine' 1x here, 0x on the page
- ch 9 p5 §9.4 #6: 'benzylamine' 1x here, 0x on the page
- ch 9 p5 §9.4 #6: 'dimethylphenylmethanamine' 1x here, 0x on the page
- ch 9 p6 §9.4 #2: 'naoh' 1x here, 0x on the page
- ch 9 p6 §9.4 #2: 'nabr' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'nacn' 2x here, 0x on the page
- ch 9 p6 §9.4 #4: 'reduction' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'chloroethane' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'propanenitrile' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'propan' 2x here, 0x on the page
- ch 9 p6 §9.4 #4: 'chlorophenylmethane' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'benzyl' 2x here, 0x on the page
- ch 9 p6 §9.4 #4: 'chloride' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'phenylethanenitrile' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'cyanide' 1x here, 0x on the page
- ch 9 p6 §9.4 #4: 'phenylethanamine' 1x here, 0x on the page
- ch 9 p8 §9.6 #6: 'salt' 2x here, 1x on the page
- ch 9 p9 §9.6 #1: 'rnh' 2x here, 0x on the page
- ch 9 p12 §9.6 #7: 'cocl' 2x here, 1x on the page
- ch 9 p12 §9.6 #7: 'nhcoc' 1x here, 0x on the page
- ch 9 p13 §9.6 #1: 'chcl' 1x here, 0x on the page
- ch 9 p13 §9.6 #1: 'koh' 1x here, 0x on the page
- ch 9 p13 §9.6 #1: 'heat' 2x here, 1x on the page
- ch 9 p13 §9.6 #1: 'kcl' 1x here, 0x on the page
- ch 9 p13 §9.6 #3: 'hno' 1x here, 0x on the page
- ch 9 p13 §9.6 #3: 'nano' 1x here, 0x on the page
- ch 9 p13 §9.6 #3: 'hcl' 2x here, 1x on the page
- ch 9 p13 §9.6 #3: 'roh' 1x here, 0x on the page
- ch 9 p13 §9.6 #4: 'nano' 1x here, 0x on the page
- ch 9 p13 §9.6 #4: 'nacl' 1x here, 0x on the page
- ch 9 p17 §9.9 #5: 'arn' 3x here, 0x on the page
- ch 9 p17 §9.9 #5: 'hcl' 1x here, 0x on the page
- ch 9 p17 §9.9 #5: 'arcl' 1x here, 0x on the page
- ch 9 p17 §9.9 #5: 'hbr' 1x here, 0x on the page
- ch 9 p17 §9.9 #5: 'arbr' 1x here, 0x on the page
- ch 9 p17 §9.9 #5: 'cucn' 1x here, 0x on the page
- ch 9 p17 §9.9 #5: 'kcn' 1x here, 0x on the page
- ch 9 p17 §9.9 #5: 'arcn' 1x here, 0x on the page
- ch 9 p17 §9.9 #6: 'arn' 2x here, 0x on the page
- ch 9 p17 §9.9 #6: 'hcl' 1x here, 0x on the page
- ch 9 p17 §9.9 #6: 'arcl' 1x here, 0x on the page
- ch 9 p17 §9.9 #6: 'cux' 2x here, 0x on the page
- ch 9 p17 §9.9 #6: 'hbr' 1x here, 0x on the page
- ch 9 p17 §9.9 #6: 'arbr' 1x here, 0x on the page
- ch 9 p17 §9.9 #8: 'arn' 1x here, 0x on the page
- ch 9 p17 §9.9 #8: 'ari' 1x here, 0x on the page
- ch 9 p17 §9.9 #8: 'kcl' 1x here, 0x on the page
- ch 9 p17 §9.9 #9: 'arn' 2x here, 0x on the page
- ch 9 p17 §9.9 #9: 'hbf' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 37 of the 37 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 9 p19: 2 paragraph(s) — sent: 5 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 37 of the 37 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 37 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 37 | 166930 | 31414 | 405504 | 11264 | ₹170.54 |
jsonl: extract/chem12-part2/en.jsonl (144 pages, 98 of them from earlier runs)

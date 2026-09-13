# margai-pipeline ncert extract

- run: 2026-09-13 15:07 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-eced540e-53f5-4b0e-913d-b063e342f532

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.306 |
| 2 | fed as the character authority | 0.270 |
| 3 | fed as the character authority | 0.271 |
| 4 | fed as the character authority | 0.262 |
| 5 | fed as the character authority | 0.340 |
| 6 | fed as the character authority | 0.338 |
| 7 | fed as the character authority | 0.326 |
| 8 | fed as the character authority | 0.305 |
| 9 | fed as the character authority | 0.286 |
| 10 | fed as the character authority | 0.302 |
| 11 | fed as the character authority | 0.331 |
| 12 | fed as the character authority | 0.322 |
| 13 | fed as the character authority | 0.305 |
| 14 | fed as the character authority | 0.331 |
| 15 | fed as the character authority | 0.343 |
| 16 | fed as the character authority | 0.332 |
| 17 | fed as the character authority | 0.328 |
| 18 | fed as the character authority | 0.335 |
| 19 | fed as the character authority | 0.287 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 1 | page 9 | SUMMARY | 1 |
| 2 | page 12 | SUMMARY | 2 |
| 3 | page 12 | SUMMARY | 3 |
| 4 | page 16 | SUMMARY | 3 |
| 5 | page 15 | SUMMARY | 2 |
| 6 | page 7 | SUMMARY | 2 |
| 7 | page 6 | SUMMARY | 1 |
| 8 | page 18 | SUMMARY | 2 |
| 9 | page 15 | SUMMARY | 2 |
| 10 | page 9 | SUMMARY | 3 |
| 11 | page 21 | SUMMARY | 2 |
| 12 | page 12 | SUMMARY | 2 |
| 13 | page 13 | SUMMARY | 3 |
| 14 | page 11 | SUMMARY | 2 |
| 15 | page 11 | SUMMARY | 2 |
| 16 | page 10 | SUMMARY | 3 |
| 17 | page 11 | SUMMARY | 3 |
| 18 | page 8 | SUMMARY | 2 |
| 19 | page 11 | SUMMARY | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 9 | 8 | 35 |
| 2 | 13 | 11 | 52 |
| 3 | 14 | 11 | 35 |
| 4 | 18 | 15 | 63 |
| 5 | 16 | 14 | 48 |
| 6 | 8 | 6 | 17 |
| 7 | 6 | 5 | 19 |
| 8 | 19 | 17 | 73 |
| 9 | 16 | 14 | 67 |
| 10 | 11 | 8 | 43 |
| 11 | 22 | 20 | 92 |
| 12 | 13 | 11 | 55 |
| 13 | 15 | 12 | 46 |
| 14 | 12 | 10 | 36 |
| 15 | 12 | 10 | 38 |
| 16 | 12 | 9 | 35 |
| 17 | 13 | 10 | 27 |
| 18 | 9 | 7 | 24 |
| 19 | 14 | 10 | 41 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 252 | 208 | 0 | 44 | 846 |

## characters that differ from the page's text layer — adjudicate these

checked: 208 of the 208 page(s) called this run
- ch 3 p9 §3.3 ¶3: 'free' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'living' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'multicellular' 3x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'gametophytes' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'called' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'prothallus' 2x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'the' 9x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'usually' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'green' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'and' 3x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'photosynthetic' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'male' 2x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'female' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'gametes' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'are' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'produced' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'gametophyte' 3x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'after' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'fertilisation' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'zygote' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'develops' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'into' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'new' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'sporophyte' 2x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'thus' 2x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'completing' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'life' 2x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'cycle' 2x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'shows' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'clear' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'alternation' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'generations' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'between' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'diploid' 1x here, 0x on the page
- ch 3 p9 §3.3 ¶3: 'haploid' 1x here, 0x on the page
- ch 5 p1 §5 ¶1: 'physiological' 1x here, 0x on the page
- ch 6 p4 §6.2.1 ¶3: 'parenchymatous' 2x here, 1x on the page
- ch 9 p5 §9.2 ¶3: 'physiological' 1x here, 0x on the page
- ch 10 p5 §10.2.5 ¶1: 'therefore' 1x here, 0x on the page
- ch 11 p5 §11.2 ¶9: '2H_2A' is not on the page (as '2H2A')
- ch 11 p5 §11.2 ¶10: '6CO_2' is not on the page (as '6CO2')
- ch 11 p5 §11.2 ¶10: '12H_2O' is not on the page (as '12H2O')
- ch 11 p5 §11.2 ¶10: '6H_2O' is not on the page (as '6H2O')
- ch 11 p5 §11.2 ¶10: '6O_2' is not on the page (as '6O2')
- ch 11 p9 §11.6 ¶1: 'characteristic' 1x here, 0x on the page
- ch 11 p9 §11.6.1 ¶2: '2H_2O' is not on the page (as '2H2O')
- ch 12 p3 §12.1 ¶9: 'C_6H_12O_6' is not on the page (as 'C6H12O6')
- ch 12 p3 §12.1 ¶9: '6O_2' is not on the page (as '6O2')
- ch 12 p3 §12.1 ¶9: '6CO_2' is not on the page (as '6CO2')
- ch 12 p3 §12.1 ¶9: '6H_2O' is not on the page (as '6H2O')
- ch 12 p6 §12.4 ¶5: 'Mg^2' is not on the page (as 'Mg2')
- ch 12 p7 §12.4.1 ¶2: '2H_2O' is not on the page (as '2H2O')
- ch 12 p7 §12.4.1 ¶2: '3CO_2' is not on the page (as '3CO2')
- ch 13 p5 §13.1.4 ¶3: 'where' 1x here, 0x on the page
- ch 13 p5 §13.1.4 ¶5: 'where' 1x here, 0x on the page
- ch 14 p10 §14.4.2 ¶2: 'H_2CO_3' is not on the page (as 'H2CO3')
- ch 15 p2 §15.1.1 ¶2: 'primarily' 1x here, 0x on the page
- ch 16 p9 §16.6 ¶2: 'characteristic' 2x here, 1x on the page
- ch 19 p6 §19.2.7 ¶3: 'pupillary' 1x here, 0x on the page
- ch 19 p10 §19.4 ¶1: 'tissue' 1x here, 0x on the page
- ch 19 p10 §19.4 ¶1: 'hence' 1x here, 0x on the page

## pages whose paragraph count does not match the page's shape

checked: 208 of the 208 page(s) called this run
none on the pages checked

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 208 | 892595 | 135445 | 1202010 | 11670 | ₹154.50 |
jsonl: extract/bio11/en.jsonl (252 pages, 0 of them from earlier runs)

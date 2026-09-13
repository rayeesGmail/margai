# margai-pipeline ncert extract

- run: 2026-09-13 22:40 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-770ecf1d-a2dd-4a73-a80a-f4bc8090be70

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.379 |
| 2 | fed as the character authority | 0.401 |
| 3 | fed as the character authority | 0.470 |
| 4 | fed as the character authority | 0.405 |
| 5 | fed as the character authority | 0.411 |
| 6 | fed as the character authority | 0.412 |
| 7 | fed as the character authority | 0.389 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 1 | page 10 | SUMMARY | 3 |
| 2 | page 9 | SUMMARY | 6 |
| 3 | page 17 | SUMMARY | 6 |
| 4 | page 18 | SUMMARY | 5 |
| 5 | page 16 | SUMMARY | 6 |
| 6 | page 32 | SUMMARY | 4 |
| 7 | page 13 | SUMMARY | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 12 | 9 | 84 |
| 2 | 14 | 8 | 68 |
| 3 | 22 | 16 | 152 |
| 4 | 22 | 17 | 149 |
| 5 | 21 | 15 | 170 |
| 6 | 35 | 31 | 297 |
| 7 | 17 | 12 | 115 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 108 | 0 | 35 | 1035 |

## characters that differ from the page's text layer — adjudicate these

checked: 108 of the 108 page(s) called this run
- ch 2 p8 §2.4 ¶31: 'taus' 1x here, 0x on the page
- ch 4 p15 §4.10 ¶1: 'towards' 2x here, 1x on the page
- ch 5 p13 §5.11 ¶2: 'consider' 2x here, 1x on the page
- ch 6 p8 §6.2 ¶45: 'suppose' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶45: 'three' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶45: 'squares' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶45: 'make' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶45: 'shaped' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶45: 'lamina' 2x here, 1x on the page
- ch 6 p18 §6.8 ¶5: 'they' 3x here, 2x on the page

## pages whose text is not all there — or is there twice

checked: 108 of the 108 page(s) called this run
- ch 1 p2: only 21% of the page's characters came back — text is missing
- ch 4 p3: only 58% of the page's characters came back — text is missing
- ch 6 p16: only 55% of the page's characters came back — text is missing
- ch 6 p25: only 45% of the page's characters came back — text is missing

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 6 page 25 — confidence 0.75, 1 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 108 | 568145 | 132336 | 667359 | 6237 | ₹117.88 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 0 of them from earlier runs)

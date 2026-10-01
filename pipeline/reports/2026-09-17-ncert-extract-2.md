# margai-pipeline ncert extract

- run: 2026-09-17 08:22 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-6ab7afd0-28ca-4818-8d49-f98056456477

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
| 1 | 12 | 0 | 89 |
| 2 | 14 | 8 | 54 |
| 3 | 22 | 16 | 109 |
| 4 | 22 | 17 | 147 |
| 5 | 21 | 15 | 136 |
| 6 | 35 | 31 | 299 |
| 7 | 17 | 0 | 108 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 87 | 21 | 35 | 942 |

## characters that differ from the page's text layer — adjudicate these

checked: 87 of the 87 page(s) called this run
- ch 2 p8 §2.4 #3: 'taus' 1x here, 0x on the page
- ch 3 p16 §3.10 #5: 'pir' 4x here, 0x on the page
- ch 3 p16 §3.10 #5: 'pirnu' 2x here, 0x on the page
- ch 3 p16 §3.10 #5: 'pinu' 1x here, 0x on the page
- ch 5 p6 §5.6 #5: 'fdx' 4x here, 1x on the page
- ch 6 p18 §6.8 #13: 'they' 3x here, 2x on the page
- ch 6 p23 §6.9 #6: 'upsilon' 2x here, 0x on the page
- ch 6 p23 §6.9 #10: 'upsilon' 1x here, 0x on the page
- ch 6 p28 §6.11 #2: 'cosphi' 1x here, 0x on the page
- ch 6 p28 §6.11 #2: 'sinalpha' 1x here, 0x on the page
- ch 6 p28 §6.11 #3: 'sinalpha' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 87 of the 87 page(s) called this run
- ch 4 p3: only 58% of the page's characters came back — text is missing
- ch 6 p16: only 55% of the page's characters came back — text is missing
- ch 6 p25: only 45% of the page's characters came back — text is missing

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 87 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 87 | 460074 | 119023 | 929574 | 10809 | ₹523.18 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 29 of them from earlier runs)

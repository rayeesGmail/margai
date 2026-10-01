# margai-pipeline ncert extract

- run: 2026-09-22 06:58 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-3ba5b34c-7023-48b4-a050-617e8bf6cd19

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
| 1 | 9 | 0 | 34 |
| 2 | 13 | 11 | 51 |
| 3 | 14 | 11 | 34 |
| 4 | 18 | 15 | 65 |
| 5 | 16 | 14 | 46 |
| 6 | 8 | 6 | 17 |
| 7 | 6 | 5 | 19 |
| 8 | 19 | 17 | 69 |
| 9 | 16 | 14 | 58 |
| 10 | 11 | 8 | 38 |
| 11 | 22 | 20 | 83 |
| 12 | 13 | 11 | 46 |
| 13 | 15 | 12 | 45 |
| 14 | 12 | 10 | 29 |
| 15 | 12 | 10 | 37 |
| 16 | 12 | 9 | 33 |
| 17 | 13 | 10 | 28 |
| 18 | 9 | 7 | 23 |
| 19 | 14 | 10 | 39 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 252 | 200 | 8 | 44 | 794 |

## characters that differ from the page's text layer — adjudicate these

checked: 200 of the 200 page(s) called this run
- ch 5 p13 §5.8 #2: 'underline' 1x here, 0x on the page
- ch 14 p10 §14.4.2 #1: 'anhydrase' 3x here, 2x on the page

## pages whose text is not all there — or is there twice

checked: 200 of the 200 page(s) called this run
- ch 3 p6: only 45% of the page's characters came back — text is missing
- ch 4 p15: only 32% of the page's characters came back — text is missing
- ch 5 p1: only 0% of the page's characters came back — text is missing
- ch 5 p2: only 0% of the page's characters came back — text is missing
- ch 8 p1: only 0% of the page's characters came back — text is missing
- ch 8 p2: only 0% of the page's characters came back — text is missing
- ch 8 p8: only 0% of the page's characters came back — text is missing
- ch 9 p4: only 0% of the page's characters came back — text is missing
- ch 11 p1: only 0% of the page's characters came back — text is missing
- ch 11 p2: only 0% of the page's characters came back — text is missing
- ch 11 p14: only 49% of the page's characters came back — text is missing
- ch 11 p18: only 0% of the page's characters came back — text is missing
- ch 14 p1: only 0% of the page's characters came back — text is missing

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 200 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 200 | 922581 | 157837 | 2150991 | 10809 | ₹874.04 |
jsonl: extract/bio11/en.jsonl (252 pages, 9 of them from earlier runs)

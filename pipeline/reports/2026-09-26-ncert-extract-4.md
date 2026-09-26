# margai-pipeline ncert extract

- run: 2026-09-26 09:43 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 13 chapters of bio12 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-9b00204c-ca24-4e7b-ba44-ce61113739e0

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.325 |
| 2 | fed as the character authority | 0.328 |
| 3 | fed as the character authority | 0.290 |
| 4 | fed as the character authority | 0.334 |
| 5 | fed as the character authority | 0.324 |
| 6 | fed as the character authority | 0.283 |
| 7 | fed as the character authority | 0.299 |
| 8 | fed as the character authority | 0.325 |
| 9 | fed as the character authority | 0.310 |
| 10 | fed as the character authority | 0.291 |
| 11 | fed as the character authority | 0.318 |
| 12 | fed as the character authority | 0.330 |
| 13 | fed as the character authority | 0.298 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 23 | SUMMARY | sent: 22 prose line(s) above the heading | 2 |
| 2 | page 14 | SUMMARY | not sent: nothing taught above the heading | 2 |
| 3 | page 9 | SUMMARY | not sent: nothing taught above the heading | 2 |
| 4 | page 27 | SUMMARY | not sent: nothing taught above the heading | 2 |
| 5 | page 30 | SUMMARY | not sent: nothing taught above the heading | 2 |
| 6 | page 17 | EXERCISES | sent: 16 prose line(s) above the heading | 0 |
| 7 | page 21 | SUMMARY | sent: 24 prose line(s) above the heading | 1 |
| 8 | page 10 | SUMMARY | sent: 27 prose line(s) above the heading | 2 |
| 9 | page 15 | SUMMARY | sent: 6 prose line(s) above the heading | 1 |
| 10 | page 10 | EXERCISES | sent: 33 prose line(s) above the heading | 1 |
| 11 | page 16 | SUMMARY | not sent: nothing taught above the heading | 2 |
| 12 | page 10 | SUMMARY | sent: 6 prose line(s) above the heading | 1 |
| 13 | page 10 | SUMMARY | sent: 34 prose line(s) above the heading | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 25 | 0 | 77 |
| 2 | 15 | 13 | 39 |
| 3 | 10 | 8 | 30 |
| 4 | 28 | 26 | 88 |
| 5 | 31 | 29 | 168 |
| 6 | 17 | 17 | 45 |
| 7 | 22 | 21 | 79 |
| 8 | 12 | 10 | 35 |
| 9 | 16 | 15 | 56 |
| 10 | 11 | 10 | 46 |
| 11 | 17 | 15 | 51 |
| 12 | 11 | 10 | 36 |
| 13 | 13 | 10 | 41 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 228 | 184 | 23 | 21 | 791 |

## characters that differ from the page's text layer — adjudicate these

checked: 184 of the 184 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 184 of the 184 page(s) called this run
- ch 5 p29: only 20% of the page's characters came back — text is missing

and 2 page(s) the ratio could not judge:
- ch 4 p18: the layer holds 396 characters, too few to measure a ratio against
- ch 7 p6: the layer holds 357 characters, too few to measure a ratio against

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 6 p17: 0 paragraph(s) — sent: 16 prose line(s) above the heading
- ch 7 p21: 4 paragraph(s) — sent: 24 prose line(s) above the heading
- ch 8 p10: 3 paragraph(s) — sent: 27 prose line(s) above the heading
- ch 9 p15: 1 paragraph(s) — sent: 6 prose line(s) above the heading
- ch 10 p10: 0 paragraph(s) — sent: 33 prose line(s) above the heading
- ch 12 p10: 1 paragraph(s) — sent: 6 prose line(s) above the heading
- ch 13 p10: 4 paragraph(s) — sent: 34 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 184 of the 184 page(s) called this run
- ch 4 p2: nothing came back; the layer holds 1234 characters in 11 sentence-length runs
- ch 9 p2: nothing came back; the layer holds 1255 characters in 11 sentence-length runs
- ch 7 p1: nothing came back; the layer holds 789 characters in 8 sentence-length runs
- ch 11 p1: nothing came back; the layer holds 884 characters in 8 sentence-length runs
- ch 4 p1: nothing came back; the layer holds 879 characters in 7 sentence-length runs
- ch 7 p2: nothing came back; the layer holds 956 characters in 7 sentence-length runs
- ch 9 p1: nothing came back; the layer holds 803 characters in 6 sentence-length runs
- ch 11 p2: nothing came back; the layer holds 1028 characters in 6 sentence-length runs
- ch 8 p2: nothing came back; the layer holds 360 characters in 3 sentence-length runs
- ch 4 p13: nothing came back; the layer holds 158 characters and no sentence-length run

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 184 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 184 | 850539 | 149648 | 2061312 | 11264 | ₹819.38 |
jsonl: extract/bio12/en.jsonl (228 pages, 25 of them from earlier runs)

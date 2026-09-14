# margai-pipeline ncert extract

- run: 2026-09-14 09:15 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-5bcde925-8b8b-4709-bedc-d7113b31572a

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 4 | fed as the character authority | 0.405 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 4 | page 18 | SUMMARY | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 4 | 22 | 1 | 8 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 1 | 0 | 5 | 1043 |

## characters that differ from the page's text layer — adjudicate these

checked: 1 of the 1 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 1 of the 1 page(s) called this run
none on the pages checked

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5332 | 1239 | 0 | 6448 | ₹1.77 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 143 of them from earlier runs)

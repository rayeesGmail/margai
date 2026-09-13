# margai-pipeline ncert extract

- run: 2026-09-13 21:59 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled, plus a thumbnail of the whole page for layout)
request id: pipeline-ncert-extract-719b0680-4298-4f73-a0a6-04a1a034769b

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 7 | fed as the character authority | 0.389 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 7 | page 13 | SUMMARY | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 17 | 12 | 114 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 12 | 0 | 5 | 996 |

## characters that differ from the page's text layer — adjudicate these

checked: 12 of the 12 page(s) called this run
- ch 7 p6 §7.4 ¶2: 'neighbouring' 3x here, 2x on the page

## pages whose text is not all there — or is there twice

checked: 12 of the 12 page(s) called this run
none on the pages checked

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 12 | 70968 | 14990 | 68607 | 6237 | ₹14.52 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 143 of them from earlier runs)

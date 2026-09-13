# margai-pipeline ncert extract

- run: 2026-09-13 22:54 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-23112474-37b0-4fd4-b130-0a91be78886b

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 6 | fed as the character authority | 0.412 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 6 | page 32 | SUMMARY | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 6 | 35 | 1 | 6 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 1 | 0 | 4 | 1035 |

## characters that differ from the page's text layer — adjudicate these

checked: 1 of the 1 page(s) called this run
- ch 6 p18 §6.8 ¶5: 'they' 3x here, 2x on the page

## pages whose text is not all there — or is there twice

checked: 1 of the 1 page(s) called this run
none on the pages checked

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 5461 | 1329 | 0 | 6237 | ₹1.80 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 143 of them from earlier runs)

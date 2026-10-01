# margai-pipeline ncert extract

- run: 2026-09-26 23:06 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-25fa80b4-846e-4eae-8a58-4e4baf2f5e90

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.386 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 37 | SUMMARY | sent: 12 prose line(s) above the heading | 7 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 44 | 37 | 222 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 44 | 37 | 0 | 7 | 222 |

## characters that differ from the page's text layer — adjudicate these

checked: 37 of the 37 page(s) called this run
- ch 1 p5 §1.4.3 #5: 'muc' 1x here, 0x on the page
- ch 1 p5 §1.4.3 #7: 'muc' 1x here, 0x on the page
- ch 1 p23 §1.9 #1: 'deltas' 1x here, 0x on the page
- ch 1 p23 §1.9 #2: 'deltas' 1x here, 0x on the page
- ch 1 p23 §1.9 #3: 'deltas' 1x here, 0x on the page
- ch 1 p31 §1.13 #5: 'deltas' 9x here, 0x on the page
- ch 1 p32 §1.13 #5: 'deltas' 8x here, 0x on the page
- ch 1 p32 §1.13 #6: 'deltas' 2x here, 0x on the page
- ch 1 p34 §1.14.2 #6: 'deltas' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 37 of the 37 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 1 p37: 4 paragraph(s) — sent: 12 prose line(s) above the heading

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
| 37 | 184277 | 40230 | 405504 | 11264 | ₹198.22 |
jsonl: extract/phy12-part1/en.jsonl (44 pages, 0 of them from earlier runs)

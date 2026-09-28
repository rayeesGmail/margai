# margai-pipeline ncert extract

- run: 2026-09-28 23:13 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-36b66e75-6a70-4064-ab13-02edd412bc6a

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 6 | fed as the character authority | 0.296 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 6 | page 30 | SUMMARY | sent: 30 prose line(s) above the heading | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 6 | 34 | 2 | 7 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 144 | 2 | 0 | 4 | 677 |

## characters that differ from the page's text layer — adjudicate these

checked: 2 of the 2 page(s) called this run
- ch 6 p15 §6.7.1 #2: 'coh' 3x here, 2x on the page
- ch 6 p22 §6.7.1 #4: 'dry' 2x here, 1x on the page
- ch 6 p22 §6.7.1 #4: 'ether' 2x here, 1x on the page
- ch 6 p22 §6.7.1 #4: 'mgbr' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 2 of the 2 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 2 of the 2 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 2 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 2 | 8925 | 1443 | 11264 | 11264 | ₹14.12 |
jsonl: extract/chem12-part2/en.jsonl (144 pages, 144 of them from earlier runs)

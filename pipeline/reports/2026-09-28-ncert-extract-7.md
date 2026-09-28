# margai-pipeline ncert extract

- run: 2026-09-28 23:17 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-8547e68a-2e69-4866-9ec3-2ea46407e152

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 8 | fed as the character authority | 0.265 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 8 | page 28 | SUMMARY | sent: 12 prose line(s) above the heading | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 8 | 32 | 7 | 39 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 144 | 7 | 0 | 4 | 676 |

## characters that differ from the page's text layer — adjudicate these

checked: 7 of the 7 page(s) called this run
- ch 8 p8 §8.2.3 #1: 'mgbr' 2x here, 0x on the page
- ch 8 p8 §8.2.3 #1: 'ether' 1x here, 0x on the page
- ch 8 p8 §8.2.3 #1: 'nmgbr' 1x here, 0x on the page
- ch 8 p8 §8.2.3 #1: 'propiophenone' 1x here, 0x on the page
- ch 8 p8 §8.2.3 #1: 'phenylpropanone' 1x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'pcl' 6x here, 4x on the page
- ch 8 p26 §8.9.2 #2: 'socl' 4x here, 3x on the page
- ch 8 p26 §8.9.2 #2: 'rcooh' 3x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'rcocl' 3x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'pocl' 1x here, 0x on the page
- ch 8 p26 §8.9.2 #2: 'hcl' 2x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 7 of the 7 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 7 of the 7 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 7 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 7 | 31026 | 5317 | 78848 | 0 | ₹29.51 |
jsonl: extract/chem12-part2/en.jsonl (144 pages, 144 of them from earlier runs)

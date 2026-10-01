# margai-pipeline ncert extract

- run: 2026-09-29 15:36 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-b7e9eac2-ee85-4f6f-901c-637958c5cbec

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 5 | fed as the character authority | 0.328 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 5 | page 29 | SUMMARY | not sent: nothing taught above the heading | 4 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 5 | 32 | 28 | 285 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 32 | 28 | 0 | 4 | 285 |

## characters that differ from the page's text layer — adjudicate these

checked: 28 of the 28 page(s) called this run
- ch 5 p6 §5.2.1 #1: 'force' 3x here, 2x on the page
- ch 5 p14 §5.4 #3: 'pdelta' 1x here, 0x on the page
- ch 5 p25 §5.6 #5: 'surr' 5x here, 4x on the page
- ch 5 p25 §5.6 #5: 'pressure' 1x here, 0x on the page
- ch 5 p28 §5.7 #7: 'conh' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 28 of the 28 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 28 of the 28 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 28 page(s) called this run
- ch 5 p13 §5.4 #8: a degree sign not after a number — the layer's Greek letter copied through?: "…r is 4.2 J/g°C…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 28 | 148986 | 39752 | 304128 | 11264 | ₹176.65 |
jsonl: extract/chem11-part1/en.jsonl (32 pages, 0 of them from earlier runs)

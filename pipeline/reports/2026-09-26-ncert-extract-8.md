# margai-pipeline ncert extract

- run: 2026-09-26 23:38 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 8 chapters of phy12-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-9a881ecd-ab3c-4faa-a59d-ecdb1e1d551d

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.386 |
| 2 | fed as the character authority | 0.396 |
| 3 | fed as the character authority | 0.388 |
| 4 | fed as the character authority | 0.415 |
| 5 | fed as the character authority | 0.374 |
| 6 | fed as the character authority | 0.393 |
| 7 | fed as the character authority | 0.400 |
| 8 | fed as the character authority | 0.347 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 37 | SUMMARY | sent: 12 prose line(s) above the heading | 7 |
| 2 | page 32 | SUMMARY | sent: 8 prose line(s) above the heading | 4 |
| 3 | page 22 | SUMMARY | sent: 9 prose line(s) above the heading | 4 |
| 4 | page 26 | SUMMARY | sent: 4 prose line(s) above the heading | 3 |
| 5 | page 14 | SUMMARY | sent: 39 prose line(s) above the heading | 4 |
| 6 | page 20 | SUMMARY | not sent: nothing taught above the heading | 4 |
| 7 | page 20 | SUMMARY | sent: 19 prose line(s) above the heading | 4 |
| 8 | page 12 | SUMMARY | not sent: nothing taught above the heading | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 44 | 0 | 222 |
| 2 | 36 | 32 | 211 |
| 3 | 26 | 22 | 140 |
| 4 | 29 | 26 | 161 |
| 5 | 18 | 14 | 101 |
| 6 | 23 | 19 | 114 |
| 7 | 24 | 20 | 132 |
| 8 | 14 | 11 | 57 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 214 | 144 | 37 | 33 | 1138 |

## characters that differ from the page's text layer — adjudicate these

checked: 144 of the 144 page(s) called this run
- ch 2 p15 §2.8.2 #4: 'muc' 2x here, 1x on the page
- ch 2 p16 §2.8.2 #4: 'muc' 4x here, 0x on the page
- ch 2 p16 §2.8.3 #7: 'cos' 3x here, 2x on the page
- ch 2 p24 §2.11 #3: 'microf' 1x here, 0x on the page
- ch 4 p4 §4.2.3 #6: 'idl' 1x here, 0x on the page
- ch 4 p12 §4.6 #1: 'closed' 3x here, 2x on the page
- ch 4 p21 §4.9.1 #4: 'cos' 1x here, 0x on the page
- ch 4 p24 §4.10 #4: 'mua' 1x here, 0x on the page
- ch 6 p12 §6.7 #6: 'nphi' 2x here, 0x on the page
- ch 8 p3 §8.2 #3: 'see' 2x here, 1x on the page

## pages whose text is not all there — or is there twice

checked: 144 of the 144 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 2 p32: 3 paragraph(s) — sent: 8 prose line(s) above the heading
- ch 3 p22: 2 paragraph(s) — sent: 9 prose line(s) above the heading
- ch 4 p26: 3 paragraph(s) — sent: 4 prose line(s) above the heading
- ch 5 p14: 4 paragraph(s) — sent: 39 prose line(s) above the heading
- ch 7 p20: 5 paragraph(s) — sent: 19 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 144 of the 144 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 144 page(s) called this run
- ch 3 p11 §3.8 #5: a degree sign not after a number — the layer's Greek letter copied through?: "…0 × 10^(-4) °C^(-1).…"
- ch 3 p11 §3.8 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…0 × 10^(-4) °C^(-1), we get T_2 – T_1…"
- ch 3 p11 §3.8 #7: a degree sign not after a number — the layer's Greek letter copied through?: "…820 + 27.0) °C = 847 °C…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 144 | 713044 | 145989 | 1610752 | 11264 | ₹728.81 |
jsonl: extract/phy12-part1/en.jsonl (214 pages, 44 of them from earlier runs)

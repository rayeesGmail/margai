# margai-pipeline ncert extract

- run: 2026-09-17 07:37 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-aa942f6c-69a4-4f04-8c7f-5daf1113e4dc

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.379 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 1 | page 10 | SUMMARY | 3 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 12 | 9 | 89 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 29 | 9 | 0 | 3 | 197 |

## characters that differ from the page's text layer — adjudicate these

checked: 9 of the 9 page(s) called this run
none on the pages checked

## pages whose text is not all there — or is there twice

checked: 9 of the 9 page(s) called this run
- ch 1 p2: only 31% of the page's characters came back — text is missing

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 9 page(s) called this run
- ch 1 p7 §1.4 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…n in mass [M°], zero dimension in tim…"
- ch 1 p7 §1.4 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…n in time [T°] and three dimensions i…"
- ch 1 p7 §1.5 #8: a degree sign not after a number — the layer's Greek letter copied through?: "…volume is [M° L^3 T°], and that of sp…"
- ch 1 p7 §1.5 #8: a degree sign not after a number — the layer's Greek letter copied through?: "…is [M° L^3 T°], and that of speed or …"
- ch 1 p7 §1.5 #8: a degree sign not after a number — the layer's Greek letter copied through?: "…locity is [M° L T^-1]. Similarly, [M°…"
- ch 1 p7 §1.5 #8: a degree sign not after a number — the layer's Greek letter copied through?: "…imilarly, [M° L T^-2] is the dimensio…"
- ch 1 p7 §1.5 #8: a degree sign not after a number — the layer's Greek letter copied through?: "…nd [M L^-3 T°] that of mass density.…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 9 | 48283 | 12954 | 86472 | 10809 | ₹60.89 |
jsonl: extract/phy11-part1/en.jsonl (29 pages, 17 of them from earlier runs)

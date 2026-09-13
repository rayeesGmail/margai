# margai-pipeline ncert extract

- run: 2026-09-13 20:00 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-4f244197-5a06-4a40-9ded-2f6c273b48bd

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | fed as the character authority | 0.379 |
| 2 | fed as the character authority | 0.401 |
| 3 | fed as the character authority | 0.470 |
| 4 | fed as the character authority | 0.405 |
| 5 | fed as the character authority | 0.411 |
| 6 | fed as the character authority | 0.412 |
| 7 | fed as the character authority | 0.389 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 1 | page 10 | SUMMARY | 3 |
| 2 | page 9 | SUMMARY | 6 |
| 3 | page 17 | SUMMARY | 6 |
| 4 | page 18 | SUMMARY | 5 |
| 5 | page 16 | SUMMARY | 6 |
| 6 | page 32 | SUMMARY | 4 |
| 7 | page 13 | SUMMARY | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 12 | 9 | 80 |
| 2 | 14 | 8 | 61 |
| 3 | 22 | 16 | 142 |
| 4 | 22 | 17 | 141 |
| 5 | 21 | 15 | 163 |
| 6 | 35 | 31 | 297 |
| 7 | 17 | 12 | 111 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 143 | 108 | 0 | 35 | 995 |

## characters that differ from the page's text layer — adjudicate these

checked: 108 of the 108 page(s) called this run
- ch 2 p3 §2.3 ¶2: 'bar' 2x here, 0x on the page
- ch 2 p4 §2.3 ¶7: 'bar' 1x here, 0x on the page
- ch 2 p5 §2.4 ¶6: 'bar' 2x here, 0x on the page
- ch 2 p5 §2.4 ¶8: 'bar' 1x here, 0x on the page
- ch 2 p6 §2.4 ¶11: 'integral' 3x here, 0x on the page
- ch 2 p6 §2.4 ¶12: 'integral' 3x here, 0x on the page
- ch 2 p6 §2.4 ¶13: 'integral' 2x here, 0x on the page
- ch 2 p6 §2.4 ¶16: 'integral' 3x here, 0x on the page
- ch 2 p6 §2.4 ¶17: 'integral' 3x here, 0x on the page
- ch 2 p6 §2.4 ¶18: 'integral' 2x here, 0x on the page
- ch 2 p8 §2.4 ¶25: 'been' 1x here, 0x on the page
- ch 2 p8 §2.4 ¶25: 'plotted' 1x here, 0x on the page
- ch 2 p8 §2.4 ¶27: 'taus' 1x here, 0x on the page
- ch 3 p5 §3.4 ¶10: 'thus' 1x here, 0x on the page
- ch 3 p5 §3.4 ¶10: 'methods' 1x here, 0x on the page
- ch 3 p8 §3.6 ¶9: 'triangle' 2x here, 0x on the page
- ch 3 p9 §3.7.1 ¶4: 'bar' 2x here, 0x on the page
- ch 3 p9 §3.7.1 ¶5: 'bar' 3x here, 0x on the page
- ch 3 p9 §3.7.1 ¶6: 'bar' 1x here, 0x on the page
- ch 3 p10 §3.7.1 ¶7: 'bar' 1x here, 0x on the page
- ch 3 p10 §3.8 ¶1: 'bar' 3x here, 0x on the page
- ch 3 p15 §3.10 ¶2: 'bar' 3x here, 0x on the page
- ch 4 p15 §4.9.1 ¶18: 'much' 1x here, 0x on the page
- ch 4 p15 §4.9.1 ¶18: 'smaller' 1x here, 0x on the page
- ch 4 p15 §4.9.1 ¶18: 'even' 1x here, 0x on the page
- ch 4 p15 §4.9.1 ¶18: 'orders' 1x here, 0x on the page
- ch 4 p15 §4.9.1 ¶18: 'magnitude' 1x here, 0x on the page
- ch 4 p15 §4.9.1 ¶18: 'than' 1x here, 0x on the page
- ch 4 p15 §4.9.1 ¶18: 'static' 3x here, 2x on the page
- ch 4 p15 §4.9.1 ¶18: 'sliding' 1x here, 0x on the page
- ch 5 p5 §5.5 ¶3: 'integral' 2x here, 1x on the page
- ch 5 p7 §5.6 ¶10: 'integral' 1x here, 0x on the page
- ch 5 p8 §5.7 ¶9: 'integral' 2x here, 0x on the page
- ch 5 p10 §5.9 ¶3: 'integral' 2x here, 0x on the page
- ch 5 p11 §5.9 ¶5: 'integral' 1x here, 0x on the page
- ch 5 p11 §5.9 ¶6: 'integral' 1x here, 0x on the page
- ch 5 p13 §5.11 ¶2: 'consider' 2x here, 1x on the page
- ch 6 p6 §6.2 ¶26: 'sum' 6x here, 1x on the page
- ch 6 p6 §6.2 ¶27: 'sum' 5x here, 1x on the page
- ch 6 p6 §6.2 ¶36: 'integral' 8x here, 5x on the page
- ch 6 p8 §6.2 ¶46: 'are' 2x here, 1x on the page
- ch 6 p8 §6.2 ¶46: 'made' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'same' 2x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'material' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'thickness' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'shape' 2x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'lies' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'line' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'could' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'guessed' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'without' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'calculations' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'you' 2x here, 1x on the page
- ch 6 p8 §6.2 ¶46: 'tell' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'why' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'suppose' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'three' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'squares' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'make' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'shaped' 1x here, 0x on the page
- ch 6 p8 §6.2 ¶46: 'lamina' 2x here, 1x on the page
- ch 6 p14 §6.6 ¶14: 'perp' 3x here, 2x on the page
- ch 6 p16 §6.7.3 ¶1: 'sum' 1x here, 0x on the page
- ch 6 p16 §6.7.3 ¶2: 'sum' 2x here, 0x on the page
- ch 6 p17 §6.7.3 ¶4: 'sum' 8x here, 3x on the page
- ch 6 p18 §6.8 ¶9: 'sum' 3x here, 2x on the page
- ch 6 p18 §6.8 ¶10: 'sum' 3x here, 2x on the page
- ch 6 p21 §6.8.2 ¶5: 'sum' 3x here, 2x on the page
- ch 6 p23 §6.9 ¶1: 'sum' 3x here, 2x on the page
- ch 6 p26 §6.10 ¶7: 'integral' 1x here, 0x on the page
- ch 6 p28 §6.11 ¶8: 'dtheta' 5x here, 1x on the page
- ch 6 p28 §6.11 ¶8: 'angle' 3x here, 2x on the page
- ch 6 p28 §6.11 ¶11: 'dtheta' 2x here, 1x on the page
- ch 6 p30 §6.12 ¶9: 'sum' 3x here, 1x on the page
- ch 6 p30 §6.12 ¶11: 'sum' 3x here, 1x on the page
- ch 6 p31 §6.12 ¶17: 'perp' 2x here, 1x on the page
- ch 7 p8 §7.7 ¶7: 'integral' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 108 of the 108 page(s) called this run
- ch 1 p2: only 21% of the page's characters came back — text is missing
- ch 4 p3: only 58% of the page's characters came back — text is missing
- ch 6 p16: only 55% of the page's characters came back — text is missing
- ch 6 p25: only 45% of the page's characters came back — text is missing

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 2 page 6 — confidence 0.45, 12 paragraphs
- ch 2 page 8 — confidence 0.75, 5 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 108 | 552394 | 128701 | 647024 | 12208 | ₹115.37 |
jsonl: extract/phy11-part1/en.jsonl (143 pages, 0 of them from earlier runs)

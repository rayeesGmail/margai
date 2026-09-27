# margai-pipeline ncert extract

- run: 2026-09-27 16:48 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-055b9f34-c23b-426b-bcc4-24c7d454bab0

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 2 | fed as the character authority | 0.325 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 2 | page 28 | SUMMARY | sent: 17 prose line(s) above the heading | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 2 | 30 | 28 | 145 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 30 | 28 | 0 | 2 | 145 |

## characters that differ from the page's text layer — adjudicate these

checked: 28 of the 28 page(s) called this run
- ch 2 p8 §2.3 #4: 'lnq' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 28 of the 28 page(s) called this run
none on the pages checked

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 2 p28: 1 paragraph(s) — sent: 17 prose line(s) above the heading

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 28 of the 28 page(s) called this run
- ch 2 p7: nothing came back; the layer holds 808 characters in 4 sentence-length runs

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 28 page(s) called this run
- ch 2 p17 §2.4.2 #3: a degree sign not after a number — the layer's Greek letter copied through?: "…ymbol Lambda°_m. The variation in Lam…"
- ch 2 p17 §2.4.2 #5: a degree sign not after a number — the layer's Greek letter copied through?: "…a_m = Lambda°_m – A c^(1/2) (2.23)…"
- ch 2 p17 §2.4.2 #6: a degree sign not after a number — the layer's Greek letter copied through?: "…al to Lambda°_m and slope equal to ‘–…"
- ch 2 p18 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…es of Lambda°_m and A for KCl.…"
- ch 2 p18 §2.4.2 #2: a degree sign not after a number — the layer's Greek letter copied through?: "… that Lambda°_m = 150.0 S cm^2 mol^-1…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…h examined Λ°_m values for a number o…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…ference in Λ°_m of the electrolytes N…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "… at 298 K: Λ°_m (KCl) – Λ°_m (NaCl) =…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…_m (KCl) – Λ°_m (NaCl) = Λ°_m (KBr) –…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…m (NaCl) = Λ°_m (KBr) – Λ°_m (NaBr) =…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…_m (KBr) – Λ°_m (NaBr) = Λ°_m (KI) – …"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…m (NaBr) = Λ°_m (KI) – Λ°_m (NaI) ≃ 2…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…°_m (KI) – Λ°_m (NaI) ≃ 23.4 S cm^2 m…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…found that Λ°_m (NaBr) – Λ°_m (NaCl) …"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…m (NaBr) – Λ°_m (NaCl) = Λ°_m (KBr) –…"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…m (NaCl) = Λ°_m (KBr) – Λ°_m (KCl) ≃ …"
- ch 2 p19 §2.4.2 #1: a degree sign not after a number — the layer's Greek letter copied through?: "…_m (KBr) – Λ°_m (KCl) ≃ 1.8 S cm^2 mo…"
- ch 2 p19 §2.4.2 #2: a degree sign not after a number — the layer's Greek letter copied through?: "…. Thus, if λ°_Na^+ and λ°_Cl^- are li…"
- ch 2 p19 §2.4.2 #2: a degree sign not after a number — the layer's Greek letter copied through?: "…°_Na^+ and λ°_Cl^- are limiting molar…"
- ch 2 p19 §2.4.2 #2: a degree sign not after a number — the layer's Greek letter copied through?: "… equation: Λ°_m (NaCl) = λ°_Na^+ + λ°…"
- ch 2 p19 §2.4.2 #2: a degree sign not after a number — the layer's Greek letter copied through?: "…m (NaCl) = λ°_Na^+ + λ°_Cl^- (2.24)…"
- ch 2 p19 §2.4.2 #2: a degree sign not after a number — the layer's Greek letter copied through?: "… λ°_Na^+ + λ°_Cl^- (2.24)…"
- ch 2 p19 §2.4.2 #3: a degree sign not after a number — the layer's Greek letter copied through?: "… given by: Λ°_m = nu_+ λ°_+ + nu_- λ°…"
- ch 2 p19 §2.4.2 #3: a degree sign not after a number — the layer's Greek letter copied through?: "…°_m = nu_+ λ°_+ + nu_- λ°_- (2.25)…"
- ch 2 p19 §2.4.2 #3: a degree sign not after a number — the layer's Greek letter copied through?: "…°_+ + nu_- λ°_- (2.25)…"
- ch 2 p19 §2.4.2 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…Here, λ°_+ and λ°_- are the limi…"
- ch 2 p19 §2.4.2 #4: a degree sign not after a number — the layer's Greek letter copied through?: "…, λ°_+ and λ°_- are the limiting mola…"
- ch 2 p19 §2.4.2 #4: a degree sign not after a number — the layer's Greek letter copied through?: "… values of λ° for some cations and an…"
- ch 2 p19 §2.4.2 #5: a degree sign not after a number — the layer's Greek letter copied through?: "…Therefore, Λ°_m cannot be obtained by…"
- ch 2 p19 §2.4.2 #5: a degree sign not after a number — the layer's Greek letter copied through?: "…Therefore, Λ°_m for weak electrolytes…"

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 28 | 136065 | 28470 | 304128 | 11264 | ₹145.46 |
jsonl: extract/chem12-part1/en.jsonl (30 pages, 0 of them from earlier runs)

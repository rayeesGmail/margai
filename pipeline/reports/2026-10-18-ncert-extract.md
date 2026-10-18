# margai-pipeline ncert extract

- run: 2026-10-18 12:51 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio12 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/bio12/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-e5507319-f4ee-4e5e-a207-af06496e871a

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 1 | withheld: illegible | 0.004 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 1 | page 25 | सारांश | sent: the heading could not be placed on it | 2 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 1 | 27 | 25 | 81 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 27 | 25 | 0 | 2 | 81 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 25 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 25 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 25 of the 25 page(s) called this run

words the rows carry more often than the page — a misreading or an addition:
- ch 1 p3: 'सूँघते' 1x in the rows, 0x on the page
- ch 1 p3: 'श्रृंगारिक' 1x in the rows, 0x on the page
- ch 1 p4: 'प्राइमोर्डियम' 1x in the rows, 0x on the page
- ch 1 p10: 'बीजांडवृंत' 2x in the rows, 0x on the page
- ch 1 p10: 'न्युक्लियेट' 4x in the rows, 0x on the page
- ch 1 p10: 'अर्थो' 1x in the rows, 0x on the page
- ch 1 p13: 'कोर्नकैब' 1x in the rows, 0x on the page
- ch 1 p21: 'मूलांकुर' 1x in the rows, 0x on the page
- ch 1 p24: 'एस्ट्रेरेसिया' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
none on the pages checked

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 1 p1: the rows carry 0% of the page's Devanagari words
- ch 1 p2: the rows carry 0% of the page's Devanagari words
- ch 1 p11: the rows carry 60% of the page's Devanagari words
- ch 1 p17: the rows carry 54% of the page's Devanagari words

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 1 p25: 0 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 25 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 25 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 1 page 11 — confidence 0.75, 2 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 25 | 93182 | 33042 | 270336 | 11264 | ₹103.06 |
jsonl: extract/bio12/hi.jsonl (27 pages, 0 of them from earlier runs)

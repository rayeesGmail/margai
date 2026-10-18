# margai-pipeline ncert extract

- run: 2026-10-15 06:53 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy12-part2 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy12-part2/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-01ea5346-5b84-4dc7-ba09-17390d0faf1c

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 9 | withheld: illegible | 0.016 |

## end-of-chapter apparatus (every page after the heading is never sent to the model)

| chapter | starts at | heading | its own page | pages not sent |
|---|---|---|---|---|
| 9 | page 27 | सारांश | sent: the heading could not be placed on it | 7 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 9 | 34 | 27 | 138 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 34 | 27 | 0 | 7 | 138 |

## characters that differ from the page's text layer — adjudicate these

checked: 0 of the 27 page(s) called this run (the rest had no usable text layer)
nothing was checked

## pages whose text is not all there — or is there twice

checked: 0 of the 27 page(s) called this run (the rest had no usable text layer)
nothing was checked

## Hindi rows against the decoded layer — a check only; the layer is never sent or stored

checked: 27 of the 27 page(s) called this run

words the rows carry more often than the page — a misreading or an addition:
- ch 9 p6: 'प्रतिबिंबों' 1x in the rows, 0x on the page
- ch 9 p9: 'पार्श्विक' 1x in the rows, 0x on the page
- ch 9 p11: 'प्रिज़्मों' 1x in the rows, 0x on the page
- ch 9 p21: 'पतले' 2x in the rows, 1x on the page
- ch 9 p21: 'जिससे' 1x in the rows, 0x on the page

symbols the page carries more often, digits and Latin only the rows carry:
- ch 9 p7: '2.5' 1x in the rows, 0x on the page
- ch 9 p14: '21' 2x in the rows, 0x on the page
- ch 9 p17: '0.5' 1x in the rows, 0x on the page
- ch 9 p21: '≃' 1x in the rows, 0x on the page
- ch 9 p27: '≅' 1x on the page, 0x in the rows

pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:
- ch 9 p22: the rows carry 0% of the page's Devanagari words

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
- ch 9 p27: 1 paragraph(s) — sent: the heading could not be placed on it

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 0 of the 27 page(s) called this run (the rest had no usable text layer)
nothing was checked

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 27 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 27 | 98734 | 41511 | 292864 | 11264 | ₹120.73 |
jsonl: extract/phy12-part2/hi.jsonl (34 pages, 0 of them from earlier runs)

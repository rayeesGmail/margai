# margai-pipeline ncert extract

- run: 2026-09-28 08:04 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-12a57b7f-a2e9-48a4-9ea5-e29358322969

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
| 8 | 32 | 3 | 14 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 54 | 3 | 0 | 4 | 237 |

## characters that differ from the page's text layer — adjudicate these

checked: 3 of the 3 page(s) called this run
- ch 8 p6 §8.2.2 #4: 'rcn' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'sncl' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'hcl' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'rch' 2x here, 0x on the page
- ch 8 p6 §8.2.2 #4: 'rcho' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #5: 'rcn' 1x here, 0x on the page
- ch 8 p6 §8.2.2 #5: 'alh' 3x here, 2x on the page
- ch 8 p22 §8.7 #1: 'cro' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'jones' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'reagent' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'cooh' 3x here, 0x on the page
- ch 8 p22 §8.7 #1: 'butan' 4x here, 0x on the page
- ch 8 p22 §8.7 #1: 'butanoic' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'acid' 8x here, 1x on the page
- ch 8 p22 §8.7 #1: 'hbr' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'kcn' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'heat' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'benzyl' 3x here, 0x on the page
- ch 8 p22 §8.7 #1: 'alcohol' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'bromide' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'cyanide' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'phenylethanoic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'nitrobromobenzene' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'ether' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'armgbr' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dry' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'ice' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'nitrobenzoic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'methylacetophenone' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'kmno' 2x here, 0x on the page
- ch 8 p22 §8.7 #1: 'koh' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dipotassium' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'benzene' 3x here, 2x on the page
- ch 8 p22 §8.7 #1: 'dicarboxylate' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dil' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dicarboxylic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'terephthalic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'cyclohexene' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'hexane' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'dioic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'adipic' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'ammoniacal' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'agno' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'tollens' 1x here, 0x on the page
- ch 8 p22 §8.7 #1: 'butanal' 1x here, 0x on the page

## pages whose text is not all there — or is there twice

checked: 3 of the 3 page(s) called this run
none on the pages checked

and 1 page(s) the ratio could not judge:
- ch 8 p22: the layer holds 184 characters, too few to measure a ratio against

## pages the Summary starts on — confirm the rows carry what is above the heading and nothing below it

the coverage ratio cannot judge these: the layer carries the Summary, the rows must not
none called this run

## pages that returned no running text — confirm each is a plate, a biography or a table

checked: 3 of the 3 page(s) called this run
every page checked returned something

## notation to adjudicate — a glyph the layer garbled and the model copied

checked: every paragraph of the 3 page(s) called this run
none

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

- ch 8 page 22 — confidence 0.65, 1 paragraphs

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 3 | 12741 | 2271 | 22528 | 11264 | ₹18.21 |
jsonl: extract/chem12-part2/en.jsonl (54 pages, 54 of them from earlier runs)

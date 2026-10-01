# margai-pipeline ncert embed

- run: 2026-09-29 19:10 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 585 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-9e99d229-17fa-4b19-8f98-c02251919716
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'chem11-part1' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 585 | 48999 | ₹5.85 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 0 |
| 2 | 0 |
| 3 | 0 |
| 4 | 0 |
| 5 | 241 |
| 6 | 344 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 585 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

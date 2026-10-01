# margai-pipeline ncert embed

- run: 2026-09-26 09:07 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 4 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-41228b23-5362-4dd7-8260-ee426b57a082
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'bio11' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 4 | 447 | ₹0.04 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 0 |
| 2 | 0 |
| 3 | 0 |
| 4 | 0 |
| 5 | 0 |
| 6 | 0 |
| 7 | 0 |
| 8 | 0 |
| 9 | 0 |
| 10 | 0 |
| 11 | 4 |
| 12 | 0 |
| 13 | 0 |
| 14 | 0 |
| 15 | 0 |
| 16 | 0 |
| 17 | 0 |
| 18 | 0 |
| 19 | 0 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 4 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

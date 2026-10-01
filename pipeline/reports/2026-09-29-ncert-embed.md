# margai-pipeline ncert embed

- run: 2026-09-29 08:04 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 582 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-948f81ca-b5f6-4b11-8836-675a85704ae7
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'chem12-part2' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 582 | 44918 | ₹5.82 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 6 | 129 |
| 7 | 159 |
| 8 | 115 |
| 9 | 90 |
| 10 | 89 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 582 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

# margai-pipeline ncert embed

- run: 2026-09-27 12:15 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 500 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-1ad813b1-e55a-4da8-aa47-086e58072ca0
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'phy12-part2' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 500 | 50763 | ₹5.00 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 9 | 135 |
| 10 | 78 |
| 11 | 68 |
| 12 | 57 |
| 13 | 87 |
| 14 | 75 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 500 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

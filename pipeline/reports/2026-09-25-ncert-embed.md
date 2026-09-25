# margai-pipeline ncert embed

- run: 2026-09-25 22:55 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 746 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-dc1faf7b-bd63-403a-b0b8-22c22ff6f0ef
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'bio11' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 746 | 82226 | ₹7.46 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 29 |
| 2 | 48 |
| 3 | 29 |
| 4 | 58 |
| 5 | 52 |
| 6 | 16 |
| 7 | 17 |
| 8 | 62 |
| 9 | 54 |
| 10 | 39 |
| 11 | 77 |
| 12 | 41 |
| 13 | 46 |
| 14 | 26 |
| 15 | 35 |
| 16 | 31 |
| 17 | 33 |
| 18 | 20 |
| 19 | 33 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 746 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

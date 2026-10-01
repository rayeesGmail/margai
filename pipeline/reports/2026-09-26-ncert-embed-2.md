# margai-pipeline ncert embed

- run: 2026-09-26 11:46 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 673 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-5a23342d-a48b-4e67-befb-64dc39f90f14
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'bio12' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 673 | 81143 | ₹6.73 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 64 |
| 2 | 30 |
| 3 | 24 |
| 4 | 77 |
| 5 | 145 |
| 6 | 32 |
| 7 | 69 |
| 8 | 31 |
| 9 | 47 |
| 10 | 43 |
| 11 | 44 |
| 12 | 33 |
| 13 | 34 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 673 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

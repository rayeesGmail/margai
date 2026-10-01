# margai-pipeline ncert embed

- run: 2026-09-27 18:45 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 657 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-5197da80-d902-4b5d-ace6-31ee57609e65
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'chem12-part1' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 657 | 61327 | ₹6.57 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 139 |
| 2 | 130 |
| 3 | 148 |
| 4 | 125 |
| 5 | 115 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 657 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

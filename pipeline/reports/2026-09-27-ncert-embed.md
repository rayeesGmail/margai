# margai-pipeline ncert embed

- run: 2026-09-27 01:08 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1042 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-4f50afd7-8127-43e9-84af-390d0f93b1ac
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'phy12-part1' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 1042 | 94466 | ₹10.42 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 195 |
| 2 | 192 |
| 3 | 127 |
| 4 | 152 |
| 5 | 97 |
| 6 | 104 |
| 7 | 123 |
| 8 | 52 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 1042 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

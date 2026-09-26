# margai-pipeline ncert embed

- run: 2026-09-26 20:34 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 682 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-a57355aa-3172-44c0-a5aa-fad2ec68f195
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'phy11-part2' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 682 | 72565 | ₹6.82 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 8 | 64 |
| 9 | 127 |
| 10 | 107 |
| 11 | 92 |
| 12 | 95 |
| 13 | 94 |
| 14 | 103 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 682 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

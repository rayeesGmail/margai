# margai-pipeline ncert embed

- run: 2026-10-01 07:30 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 534 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-81e6f468-306b-48ed-ac1c-c17a3228ac0d
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: the set at ../eval/retrieval-queries.json is written against 'phy11-part1', not 'chem11-part2' — not run

## cost

| calls | input tokens | spent |
|---|---|---|
| 534 | 50908 | ₹5.34 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 7 | 127 |
| 8 | 215 |
| 9 | 192 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 534 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

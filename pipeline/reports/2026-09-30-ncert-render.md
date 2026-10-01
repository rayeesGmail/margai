# margai-pipeline ncert render

- run: 2026-09-30 06:46 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 3 chapters of chem11-part2 (en)
- result: ok
content store: s3://margai-beta-content

## pages per chapter

| chapter | source | pages | rendered | already there |
|---|---|---|---|---|
| 7 | kech201.pdf | 21 | 21 | 0 |
| 8 | kech202.pdf | 39 | 39 | 0 |
| 9 | kech203.pdf | 33 | 33 | 0 |

## total

| chapters | pages | rendered | skipped |
|---|---|---|---|
| 3 | 93 | 93 | 0 |
ncert_books.pages_en = 93

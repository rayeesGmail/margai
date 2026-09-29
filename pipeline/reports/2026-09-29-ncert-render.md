# margai-pipeline ncert render

- run: 2026-09-29 15:25 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of chem11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## pages per chapter

| chapter | source | pages | rendered | already there |
|---|---|---|---|---|
| 1 | kech101.pdf | 28 | 28 | 0 |
| 2 | kech102.pdf | 45 | 45 | 0 |
| 3 | kech103.pdf | 26 | 26 | 0 |
| 4 | kech104.pdf | 36 | 36 | 0 |
| 5 | kech105.pdf | 32 | 32 | 0 |
| 6 | kech106.pdf | 53 | 53 | 0 |

## total

| chapters | pages | rendered | skipped |
|---|---|---|---|
| 6 | 220 | 220 | 0 |
ncert_books.pages_en = 220

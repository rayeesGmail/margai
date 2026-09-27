# margai-pipeline ncert render

- run: 2026-09-27 09:00 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of phy12-part2 (en)
- result: ok
content store: s3://margai-beta-content

## pages per chapter

| chapter | source | pages | rendered | already there |
|---|---|---|---|---|
| 9 | leph201.pdf | 34 | 34 | 0 |
| 10 | leph202.pdf | 19 | 19 | 0 |
| 11 | leph203.pdf | 16 | 16 | 0 |
| 12 | leph204.pdf | 16 | 16 | 0 |
| 13 | leph205.pdf | 17 | 17 | 0 |
| 14 | leph206.pdf | 21 | 21 | 0 |

## total

| chapters | pages | rendered | skipped |
|---|---|---|---|
| 6 | 123 | 123 | 0 |
ncert_books.pages_en = 123

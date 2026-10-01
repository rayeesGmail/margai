# margai-pipeline ncert render

- run: 2026-09-27 15:07 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 5 chapters of chem12-part1 (en)
- result: ok
content store: s3://margai-beta-content

## pages per chapter

| chapter | source | pages | rendered | already there |
|---|---|---|---|---|
| 1 | lech101.pdf | 30 | 30 | 0 |
| 2 | lech102.pdf | 30 | 30 | 0 |
| 3 | lech103.pdf | 28 | 28 | 0 |
| 4 | lech104.pdf | 29 | 29 | 0 |
| 5 | lech105.pdf | 23 | 23 | 0 |

## total

| chapters | pages | rendered | skipped |
|---|---|---|---|
| 5 | 140 | 140 | 0 |
ncert_books.pages_en = 140

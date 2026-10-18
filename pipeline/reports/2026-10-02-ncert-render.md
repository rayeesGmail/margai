# margai-pipeline ncert render

- run: 2026-10-02 11:05 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content

## pages per chapter

| chapter | source | pages | rendered | already there |
|---|---|---|---|---|
| 1 | khph101.pdf | 12 | 0 | 12 |
| 2 | khph102.pdf | 15 | 15 | 0 |
| 3 | khph103.pdf | 22 | 22 | 0 |
| 4 | khph104.pdf | 22 | 22 | 0 |
| 5 | khph105.pdf | 22 | 22 | 0 |
| 6 | khph106.pdf | 36 | 36 | 0 |
| 7 | khph107.pdf | 18 | 18 | 0 |

## total

| chapters | pages | rendered | skipped |
|---|---|---|---|
| 7 | 147 | 135 | 12 |
ncert_books.pages_hi = 147

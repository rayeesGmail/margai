# margai-pipeline ncert render

- run: 2026-10-01 22:44 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content

## pages per chapter

| chapter | source | pages | rendered | already there |
|---|---|---|---|---|
| 1 | khph101.pdf | 12 | 12 | 0 |

## total

| chapters | pages | rendered | skipped |
|---|---|---|---|
| 1 | 12 | 12 | 0 |
ncert_books.pages_hi left alone: this run rendered a chapter subset

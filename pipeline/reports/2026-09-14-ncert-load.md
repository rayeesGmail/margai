# margai-pipeline ncert load

- run: 2026-09-14 09:57 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content

## page-break repairs to the model's numbering (deterministic, each one named)

none

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 33 | 0 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 9 | 8 | 33 | 88.9% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 252 | 9 | 8 | 33 | — |

## addresses in the database this extraction no longer carries

none

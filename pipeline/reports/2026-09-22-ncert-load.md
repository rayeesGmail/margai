# margai-pipeline ncert load

- run: 2026-09-22 07:15 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 2fba95d0e02ba94d65e8741c805fd6d12eb6e9b3e9436a4e046af78c4f552dd4

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

none
rulings on verifier flags that change no text: 0

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

none

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 668 | 30 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 9 | 7 | 30 | 77.8% |
| 2 | 13 | 11 | 45 | 84.6% |
| 3 | 14 | 9 | 29 | 64.3% |
| 4 | 18 | 15 | 59 | 83.3% |
| 5 | 16 | 12 | 40 | 75.0% |
| 6 | 8 | 6 | 13 | 75.0% |
| 7 | 6 | 5 | 15 | 83.3% |
| 8 | 19 | 14 | 60 | 73.7% |
| 9 | 16 | 13 | 50 | 81.3% |
| 10 | 11 | 8 | 37 | 72.7% |
| 11 | 22 | 17 | 77 | 77.3% |
| 12 | 13 | 11 | 39 | 84.6% |
| 13 | 15 | 12 | 42 | 80.0% |
| 14 | 12 | 8 | 25 | 66.7% |
| 15 | 12 | 10 | 32 | 83.3% |
| 16 | 12 | 9 | 28 | 75.0% |
| 17 | 13 | 10 | 24 | 76.9% |
| 18 | 9 | 7 | 20 | 77.8% |
| 19 | 14 | 10 | 33 | 71.4% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 252 | 252 | 194 | 698 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

none

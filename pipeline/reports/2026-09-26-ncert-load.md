# margai-pipeline ncert load

- run: 2026-09-26 08:02 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of bio12 (en)
- result: ok
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 d18ab82b054865d52fb2b4af96ffe58adf23c77b045ca13c51011ca675096fc6

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
| 62 | 0 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 25 | 21 | 62 | 84.0% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 228 | 25 | 21 | 62 | — |

## addresses in the database this extraction no longer carried — deleted

none

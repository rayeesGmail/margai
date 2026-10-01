# margai-pipeline ncert load

- run: 2026-09-28 07:47 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 2 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 f97ae5097718350ac7d354ae9a534744062f79a9e188c618b684640c2fd1ec8d

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
| 223 | 0 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 8 | 32 | 28 | 133 | 87.5% |
| 10 | 22 | 21 | 90 | 95.5% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 144 | 54 | 49 | 223 | — |

## addresses in the database this extraction no longer carried — deleted

none

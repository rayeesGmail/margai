# margai-pipeline ncert load

- run: 2026-09-23 09:58 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 730d14b67f15503e425390c9b79a07330ce7b64bb7fd4e29f3c07f0766eed7a5

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

- ch 1 page 1 §1: the paragraph starting "Biology is the science of life forms and living processes." dropped (the Unit 1 opener essay, not chapter text)
- ch 3 page 7 §3.2: "Bryophytes are also called amphibians of" continues the previous page's paragraph (p6's last line sits at 533.8 of a 534.0 measure: §3.2 ¶1 runs on past the figure)
- ch 4 page 3 §4.1.3: "Those animals in which the developing" continues the previous page's paragraph (p2's last line is at the measure; diploblastic and triploblastic are one printed paragraph)
- ch 12 page 5 §12.2: "There are three major ways in" continues the previous page's paragraph (p4's last line is at the measure; the three fates of pyruvate continue that sentence)
rulings on verifier flags that change no text: 15

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

none

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 0 | 5 | 689 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 9 | 6 | 29 | 66.7% |
| 2 | 13 | 11 | 45 | 84.6% |
| 3 | 14 | 9 | 28 | 64.3% |
| 4 | 18 | 15 | 58 | 83.3% |
| 5 | 16 | 12 | 40 | 75.0% |
| 6 | 8 | 6 | 13 | 75.0% |
| 7 | 6 | 5 | 15 | 83.3% |
| 8 | 19 | 14 | 60 | 73.7% |
| 9 | 16 | 13 | 50 | 81.3% |
| 10 | 11 | 8 | 37 | 72.7% |
| 11 | 22 | 17 | 77 | 77.3% |
| 12 | 13 | 11 | 38 | 84.6% |
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
| 252 | 252 | 193 | 694 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

- ch 12 §12.2 ¶5
- ch 3 §3.2 ¶4
- ch 4 §4.1.3 ¶2

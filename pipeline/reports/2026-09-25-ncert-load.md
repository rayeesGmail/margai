# margai-pipeline ncert load

- run: 2026-09-25 08:38 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 6f4e190eb7330c89fa93c60157905d323de5df769f6e3c4b6d594b428aee72c7

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

- ch 1 page 1 §1: the paragraph starting "Biology is the science of life forms and living processes." dropped (the Unit 1 opener essay, not chapter text)
- ch 3 page 7 §3.2: "Bryophytes are also called amphibians of" continues the previous page's paragraph (p6's last line sits at 533.8 of a 534.0 measure: §3.2 ¶1 runs on past the figure)
- ch 4 page 3 §4.1.3: "Those animals in which the developing" continues the previous page's paragraph (p2's last line is at the measure; diploblastic and triploblastic are one printed paragraph)
- ch 12 page 5 §12.2: "There are three major ways in" continues the previous page's paragraph (p4's last line is at the measure; the three fates of pyruvate continue that sentence)
- ch 9 page 12 §9.8.3: "E + S ES -> EP -> E + P" → "E + S <-> ES -> EP -> E + P" (the reversible arrow of the ES-complex step was dropped)
rulings on verifier flags that change no text: 25

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

none

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 52 | 9 | 685 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 9 | 6 | 29 | 66.7% |
| 2 | 13 | 12 | 48 | 92.3% |
| 3 | 14 | 10 | 29 | 71.4% |
| 4 | 18 | 15 | 58 | 83.3% |
| 5 | 16 | 13 | 52 | 81.3% |
| 6 | 8 | 7 | 16 | 87.5% |
| 7 | 6 | 6 | 17 | 100.0% |
| 8 | 19 | 15 | 62 | 78.9% |
| 9 | 16 | 14 | 54 | 87.5% |
| 10 | 11 | 9 | 39 | 81.8% |
| 11 | 22 | 17 | 77 | 77.3% |
| 12 | 13 | 12 | 41 | 92.3% |
| 13 | 15 | 13 | 46 | 86.7% |
| 14 | 12 | 9 | 26 | 75.0% |
| 15 | 12 | 11 | 35 | 91.7% |
| 16 | 12 | 10 | 31 | 83.3% |
| 17 | 13 | 11 | 33 | 84.6% |
| 18 | 9 | 7 | 20 | 77.8% |
| 19 | 14 | 10 | 33 | 71.4% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 252 | 252 | 207 | 746 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

none

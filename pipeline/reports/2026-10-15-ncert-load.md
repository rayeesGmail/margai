# margai-pipeline ncert load

- run: 2026-10-15 07:25 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of phy12-part2 (hi)
- result: ok
content store: s3://margai-beta-content
extraction: extract/phy12-part2/hi.jsonl sha256 0d357bbd1354a4257cf6354ece02ca8ca5cdccd74ab1f3e5275141c277891676
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 a23017022a60407db63828d10c75f28b8accaa9280d99998956bbee4106d7bc2

## page-break repairs to the model's continuation flags (deterministic, each one named)

- ch 9 §9.4: page 11 repeated the end of page 10's paragraph; the repeated 75 characters were dropped; what remains is treated as continuing that paragraph

## a visarga the model wrote as a colon (deterministic, each one named)

- ch 9 p8 §9.2.3: a visarga written as a colon — अतः, क्रमशः
- ch 9 p8 §9.3: a visarga written as a colon — प्रायः
- ch 9 p8 §9.3: a visarga written as a colon — क्रमशः, अतः
- ch 9 p9 §9.4: a visarga written as a colon — अंशतः, अंशतः
- ch 9 p9 §9.4: a visarga written as a colon — अंशतः, अंशतः
- ch 9 p16 §9.5.3: a visarga written as a colon — स्पष्टतः, अतः
- ch 9 p16 §9.5.3: a visarga written as a colon — अतः
- ch 9 p27 §9.7.2: a visarga written as a colon — अतः
- ch 10 p1 §10.1: a visarga written as a colon — प्रायः
- ch 10 p6 §10.3.2: a visarga written as a colon — अतः
- ch 10 p8 §10.3.3: a visarga written as a colon — अतः
- ch 10 p9 §10.4: a visarga written as a colon — अतः
- ch 10 p9 §10.4: a visarga written as a colon — अतः
- ch 10 p17 §10.7: a visarga written as a colon — अतः, अतः
- ch 11 p5 §11.4.1: a visarga written as a colon — अतः
- ch 11 p11 §11.7: a visarga written as a colon — अतः
- ch 12 p6 §12.2.1: a visarga written as a colon — अतः
- ch 12 p6 §12.2.1: a visarga written as a colon — अतः
- ch 12 p6 §12.2.2: a visarga written as a colon — अतः
- ch 13 p1 §13.1: a visarga written as a colon — अधिकांशतः
- ch 13 p7 §13.4.2: a visarga written as a colon — अतः
- ch 13 p10 §13.7: a visarga written as a colon — अतः
- ch 13 p10 §13.7.1: a visarga written as a colon — अतः
- ch 13 p12 §13.7.2: a visarga written as a colon — अतः
- ch 13 p12 §13.7.2: a visarga written as a colon — पुनः
- ch 13 p12 §13.7.3: a visarga written as a colon — अतः
- ch 13 p13 §13.7.3: a visarga written as a colon — अतः, वस्तुतः
- ch 13 p13 §13.7.3: a visarga written as a colon — अतः, अतः, अंतः
- ch 13 p13 §13.7.3: a visarga written as a colon — अंतः, अंतः
- ch 14 p1 §14.1: a visarga written as a colon — अधिकांशतः, क्रमशः, सामान्यतः
- ch 14 p4 §14.2: a visarga written as a colon — पूर्णतः, पूर्णतः
- ch 14 p13 §14.6.1: a visarga written as a colon — अंतः
- ch 14 p13 §14.6.2: a visarga written as a colon — अंतः

## a word glued to a colon that the visarga list does not know — adjudicate

left as written: a `text` entry if the page means a visarga, nothing if it is a colon
- ch 9 p9 §9.3: प्रकाशत:
- ch 9 p9 §9.4: प्रकाशत:, प्रकाशत:
- ch 10 p9 §10.4: सारांशत:
- ch 11 p5 §11.4.1: रैखिकत:
- ch 11 p6 §11.4.3: रैखिकत:
- ch 11 p6 §11.4.3: रैखिकत:
- ch 13 p10 §13.7.1: उदाहरणत:
- ch 13 p12 §13.7.2: अंत:
- ch 13 p13 §13.7.3: सिद्धांतत:

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

none
rulings on verifier flags that change no text: 0

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

- ch 14 §14.2 ¶14: "14.2(c)"
- ch 14 §14.3 ¶4: "14.5 (b)"
- ch 14 §14.6.2 ¶5: "(b)"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 11 | 454 | 0 |

## sections whose Hindi rows do not number as the English (English is canonical)

38 of 70 sections one to one
| chapter | section | en | hi | unprinted |
|---|---|---|---|---|
| 9 | 9.2.2 | 4 | 5 | 0 |
| 9 | 9.2.3 | 24 | 23 | 0 |
| 9 | 9.3 | 8 | 6 | 0 |
| 9 | 9.5.2 | 21 | 16 | 0 |
| 9 | 9.5.4 | 10 | 11 | 0 |
| 9 | 9.6 | 12 | 7 | 0 |
| 9 | 9.7.1 | 16 | 15 | 0 |
| 10 | 10.3.3 | 13 | 11 | 0 |
| 10 | 10.4 | 13 | 12 | 0 |
| 10 | 10.5 | 6 | 5 | 0 |
| 10 | 10.6.2 | 6 | 5 | 0 |
| 10 | 10.7 | 11 | 10 | 0 |
| 11 | 11.2 | 6 | 4 | 0 |
| 11 | 11.4 | 3 | 2 | 0 |
| 11 | 11.4.3 | 13 | 9 | 0 |
| 11 | 11.7 | 12 | 13 | 0 |
| 11 | 11.8 | 7 | 10 | 0 |
| 12 | 12.2 | 5 | 4 | 0 |
| 12 | 12.2.1 | 11 | 9 | 0 |
| 12 | 12.2.2 | 5 | 7 | 0 |
| 12 | 12.5 | 5 | 4 | 0 |
| 13 | 13.4.2 | 17 | 16 | 0 |
| 13 | 13.5 | 6 | 5 | 0 |
| 13 | 13.6 | 2 | 3 | 0 |
| 13 | 13.7.1 | 12 | 11 | 0 |
| 13 | 13.7.2 | 9 | 10 | 0 |
| 13 | 13.7.3 | 10 | 6 | 0 |
| 14 | 14.2 | 18 | 15 | 0 |
| 14 | 14.3 | 7 | 8 | 0 |
| 14 | 14.4 | 15 | 13 | 0 |
| 14 | 14.6.1 | 5 | 4 | 0 |
| 14 | 14.6.2 | 11 | 9 | 0 |

## English paragraphs this edition does not print — left without Hindi by an `unprinted` entry

none

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 9 | 34 | 26 | 123 | 76.5% |
| 10 | 19 | 17 | 72 | 89.5% |
| 11 | 16 | 13 | 65 | 81.3% |
| 12 | 16 | 13 | 55 | 81.3% |
| 13 | 17 | 13 | 82 | 76.5% |
| 14 | 20 | 17 | 68 | 85.0% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 122 | 122 | 99 | 465 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

none

## rows the other edition still carries — this edition's text cleared from them

none

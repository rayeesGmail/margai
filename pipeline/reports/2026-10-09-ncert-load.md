# margai-pipeline ncert load

- run: 2026-10-09 17:15 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part2 (hi)
- result: ok
content store: s3://margai-beta-content
extraction: extract/phy11-part2/hi.jsonl sha256 d759e73f975c6023685143f1ba00f8518073e397b98b50d92d1fea0e9d21bb15
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 db7f3a50fec7ad4f7baf3d32fb932d933827ccbb1d20aec125d48b1cfd05fc2d

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## a visarga the model wrote as a colon (deterministic, each one named)

- ch 8 p9 §8.5.6: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p2 §9.2: a visarga written as a colon — अतः
- ch 9 p3 §9.2: a visarga written as a colon — अतः
- ch 9 p3 §9.2.1: a visarga written as a colon — अतः, क्रमशः, क्रमशः
- ch 9 p3 §9.2.1: a visarga written as a colon — अतः, पुनः
- ch 9 p8 §9.3: a visarga written as a colon — सामान्यतः
- ch 9 p8 §9.3: a visarga written as a colon — अतः
- ch 9 p10 §9.4: a visarga written as a colon — अतः
- ch 9 p10 §9.4.1: a visarga written as a colon — सन्निकटतः
- ch 9 p11 §9.4.2: a visarga written as a colon — अतः
- ch 9 p11 §9.4.2: a visarga written as a colon — परितः
- ch 9 p16 §9.6.2: a visarga written as a colon — अतः, अतः, अतः
- ch 9 p18 §9.6.4: a visarga written as a colon — अतः, अतः
- ch 10 p3 §10.4: a visarga written as a colon — अतः
- ch 10 p4 §10.5: a visarga written as a colon — पूर्णतः
- ch 10 p4 §10.5: a visarga written as a colon — सामान्यतः
- ch 10 p19 §10.9.4: a visarga written as a colon — अतः
- ch 10 p19 §10.10: a visarga written as a colon — अंततः
- ch 11 p4 §11.4: a visarga written as a colon — अतः
- ch 11 p11 §11.9: a visarga written as a colon — स्वतः, स्वतः
- ch 11 p11 §11.10: a visarga written as a colon — स्वतः, स्वतः
- ch 11 p11 §11.10: a visarga written as a colon — मुख्यतः
- ch 12 p4 §12.3: a visarga written as a colon — पूर्णतः, अतः
- ch 13 p4 §13.2.2: a visarga written as a colon — अतः
- ch 13 p4 §13.2.2: a visarga written as a colon — अतः
- ch 13 p4 §13.2.2: a visarga written as a colon — अतः, अतः
- ch 13 p9 §13.6: a visarga written as a colon — अतः, अतः
- ch 13 p10 §13.6: a visarga written as a colon — अतः
- ch 13 p10 §13.7: a visarga written as a colon — अतः, अतः
- ch 13 p10 §13.7: a visarga written as a colon — अतः, अतः
- ch 13 p11 §13.7: a visarga written as a colon — अतः
- ch 13 p11 §13.7: a visarga written as a colon — अतः, अतः, अतः
- ch 13 p12 §13.7: a visarga written as a colon — अतः, अतः
- ch 13 p12 §13.8.1: a visarga written as a colon — अतः
- ch 14 p5 §14.3: a visarga written as a colon — अतः, अतः, अतः
- ch 14 p6 §14.3.1: a visarga written as a colon — स्पष्टतः, अतः, अतः
- ch 14 p6 §14.3.2: a visarga written as a colon — सामान्यतः
- ch 14 p6 §14.3.3: a visarga written as a colon — अतः
- ch 14 p6 §14.3.3: a visarga written as a colon — अतः
- ch 14 p7 §14.4: a visarga written as a colon — अतः, अतः
- ch 14 p13 §14.6: a visarga written as a colon — स्पष्टतः
- ch 14 p13 §14.6.1: a visarga written as a colon — अतः, अतः

## a word glued to a colon that the visarga list does not know — adjudicate

left as written: a `text` entry if the page means a visarga, nothing if it is a colon
- ch 9 p8 §9.3: विलोमत:
- ch 10 p9 §10.8: विलोमत:
- ch 12 p2 §12.2: अनुसार:
- ch 12 p9 §12.4.2: संकेत:
- ch 13 p4 §13.2.2: दिष्टत:
- ch 13 p4 §13.2.2: एकदिष्टत:
- ch 13 p12 §13.8: निरपेक्षत:
- ch 14 p5 §14.3: पारित:
- ch 14 p8 §14.4.1: हल:
- ch 14 p16 §14.6.1: हल:

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

none
rulings on verifier flags that change no text: 0

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

- ch 11 §11.2 ¶2: "11.1(a)"
- ch 13 §13.2 ¶1: "13.1(c)"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 25 | 598 | 0 |

## sections whose Hindi rows do not number as the English (English is canonical)

56 of 97 sections one to one
| chapter | section | en | hi |
|---|---|---|---|
| 8 | 8.2 | 13 | 8 |
| 8 | 8.5.1 | 18 | 10 |
| 8 | 8.5.2 | 5 | 4 |
| 8 | 8.5.5 | 2 | 0 |
| 8 | 8.5.6 | 0 | 1 |
| 9 | 9.2 | 12 | 10 |
| 9 | 9.2.2 | 8 | 6 |
| 9 | 9.2.3 | 13 | 12 |
| 9 | 9.4.1 | 5 | 4 |
| 9 | 9.4.2 | 10 | 9 |
| 9 | 9.6.4 | 7 | 6 |
| 10 | 10.5 | 18 | 17 |
| 10 | 10.6 | 11 | 9 |
| 10 | 10.7 | 4 | 5 |
| 10 | 10.9.1 | 14 | 13 |
| 10 | 10.9.4 | 8 | 7 |
| 10 | 10.10 | 14 | 13 |
| 11 | 11.5 | 8 | 5 |
| 11 | 11.6 | 11 | 12 |
| 11 | 11.7 | 4 | 3 |
| 11 | 11.8.2 | 3 | 2 |
| 11 | 11.9 | 7 | 5 |
| 11 | 11.11 | 20 | 19 |
| 12 | 12.4.1 | 7 | 5 |
| 12 | 12.4.2 | 24 | 18 |
| 12 | 12.5 | 11 | 10 |
| 13 | 13.2.2 | 10 | 9 |
| 13 | 13.3 | 12 | 10 |
| 13 | 13.6 | 7 | 6 |
| 13 | 13.7 | 17 | 9 |
| 13 | 13.8 | 13 | 1 |
| 13 | 13.8.1 | 0 | 10 |
| 14 | 14.1 | 11 | 10 |
| 14 | 14.2 | 13 | 10 |
| 14 | 14.3.2 | 4 | 1 |
| 14 | 14.3.3 | 15 | 11 |
| 14 | 14.4.1 | 5 | 6 |
| 14 | 14.5 | 6 | 11 |
| 14 | 14.6 | 4 | 6 |
| 14 | 14.6.1 | 16 | 20 |
| 14 | 14.7 | 8 | 6 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 8 | 14 | 11 | 49 | 78.6% |
| 9 | 24 | 19 | 119 | 79.2% |
| 10 | 26 | 21 | 102 | 80.8% |
| 11 | 18 | 14 | 85 | 77.8% |
| 12 | 16 | 13 | 86 | 81.3% |
| 13 | 19 | 13 | 80 | 68.4% |
| 14 | 23 | 18 | 102 | 78.3% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 140 | 140 | 109 | 623 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

none

## rows the other edition still carries — this edition's text cleared from them

none

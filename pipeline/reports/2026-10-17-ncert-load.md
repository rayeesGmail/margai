# margai-pipeline ncert load

- run: 2026-10-17 08:21 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (hi)
- result: ok
content store: s3://margai-beta-content
extraction: extract/bio11/hi.jsonl sha256 c3644542150e617135d8a6108a240ad4dd550262666eb95e4ecf3b06922c9e81
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 6963a69e3e71dcef5f80471bb851563dd0ca71d261211c60d5e2d464ad24c247

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## a visarga the model wrote as a colon (deterministic, each one named)

- ch 1 p3 §1.1: a visarga written as a colon — स्पष्टतः
- ch 1 p4 §1.1: a visarga written as a colon — स्पष्टतः
- ch 1 p4 §1.1: a visarga written as a colon — प्रायः
- ch 2 p2 §2: a visarga written as a colon — अतः
- ch 2 p5 §2.2.2: a visarga written as a colon — मुख्यतः
- ch 2 p7 §2.3: a visarga written as a colon — अतः
- ch 2 p8 §2.3.2: a visarga written as a colon — सामान्यतः
- ch 2 p9 §2.3.3: a visarga written as a colon — प्रायः
- ch 2 p9 §2.3.4: a visarga written as a colon — प्रायः
- ch 2 p11 §2.6: a visarga written as a colon — मुख्यतः
- ch 2 p11 §2.6: a visarga written as a colon — प्रायः, प्रायः
- ch 3 p4 §3.1.1: a visarga written as a colon — प्रायः, प्रायः
- ch 3 p4 §3.1.1: a visarga written as a colon — प्रायः
- ch 3 p5 §3.1.2: a visarga written as a colon — मुख्यतः, प्रायः, अधः
- ch 3 p5 §3.1.3: a visarga written as a colon — अधिकांशतः
- ch 3 p6 §3.2: a visarga written as a colon — प्रायः
- ch 3 p8 §3.2.2: a visarga written as a colon — प्रायः
- ch 3 p8 §3.3: a visarga written as a colon — अधिकांशतः
- ch 4 p3 §4.2.1: a visarga written as a colon — सामान्यतः, सामान्यतः
- ch 4 p7 §4.2.5: a visarga written as a colon — प्रायः
- ch 4 p7 §4.2.6: a visarga written as a colon — स्पष्टतः
- ch 4 p8 §4.2.7: a visarga written as a colon — अधिकांशतः
- ch 4 p8 §4.2.8: a visarga written as a colon — सामान्यतः
- ch 4 p9 §4.2.8: a visarga written as a colon — सामान्यतः
- ch 4 p9 §4.2.9: a visarga written as a colon — सामान्यतः, सामान्यतः
- ch 4 p10 §4.2.11: a visarga written as a colon — सामान्यतः
- ch 4 p14 §4.2.11.6: a visarga written as a colon — सामान्यतः, अंतः
- ch 5 p10 §5.5.1.3: a visarga written as a colon — प्रायः
- ch 5 p11 §5.5.1.4: a visarga written as a colon — प्रायः
- ch 5 p11 §5.6: a visarga written as a colon — प्रायः
- ch 5 p14 §5.9: a visarga written as a colon — प्रायः
- ch 6 p6 §6.2.4: a visarga written as a colon — प्रायः
- ch 7 p4 §7.2.2: a visarga written as a colon — अतः
- ch 8 p11 §8.5.3.1: a visarga written as a colon — प्रायः
- ch 8 p14 §8.5.5: a visarga written as a colon — अंतः
- ch 9 p8 §9.6: a visarga written as a colon — क्रमशः
- ch 9 p12 §9.8.2: a visarga written as a colon — स्वतः, अंतः
- ch 10 p8 §10.4.1: a visarga written as a colon — पुनः
- ch 11 p5 §11.2: a visarga written as a colon — अतः
- ch 11 p5 §11.2: a visarga written as a colon — प्रायः, प्रायः
- ch 11 p6 §11.3: a visarga written as a colon — सामान्यतः
- ch 11 p7 §11.3: a visarga written as a colon — पूर्णतः, अतः
- ch 11 p8 §11.4: a visarga written as a colon — स्पष्टतः
- ch 11 p8 §11.4: a visarga written as a colon — अतः, पूर्णतः
- ch 11 p9 §11.5: a visarga written as a colon — अतः, अतः
- ch 11 p9 §11.6: a visarga written as a colon — पुनः
- ch 11 p19 §11.9: a visarga written as a colon — संभवतः
- ch 11 p19 §11.10: a visarga written as a colon — प्रायः, अतः
- ch 11 p20 §11.10.2: a visarga written as a colon — अतः
- ch 12 p2 §12: a visarga written as a colon — प्रायः
- ch 12 p3 §12.1: a visarga written as a colon — अतः
- ch 12 p4 §12.2: a visarga written as a colon — सामान्यतः
- ch 12 p5 §12.3: a visarga written as a colon — पुनः
- ch 13 p3 §13.1.2: a visarga written as a colon — मुख्यतः, अतः
- ch 13 p3 §13.1.3: a visarga written as a colon — मुख्यतः
- ch 13 p9 §13.3: a visarga written as a colon — अतः
- ch 13 p10 §13.4.3.1: a visarga written as a colon — प्रायः
- ch 14 p5 §14.2: a visarga written as a colon — अंतः, निः
- ch 14 p6 §14.2: a visarga written as a colon — अंतः, अंतः, निः, अंतः, निः
- ch 14 p6 §14.2.1: a visarga written as a colon — निः, निः
- ch 14 p9 §14.3: a visarga written as a colon — अंतः, अंतः, अंतः
- ch 14 p10 §14.4.2: a visarga written as a colon — पुनः
- ch 14 p10 §14.5: a visarga written as a colon — अंतः
- ch 15 p2 §15.1.2: a visarga written as a colon — सामान्यतः
- ch 16 p4 §16.2: a visarga written as a colon — पुनः
- ch 17 p3 §17.2: a visarga written as a colon — अंतः, अतः, क्रमशः
- ch 17 p10 §17.3: a visarga written as a colon — सामान्यतः
- ch 17 p10 §17.4: a visarga written as a colon — मुख्यतः
- ch 18 p4 §18.3.1: a visarga written as a colon — अतः, पुनः
- ch 19 p1 §19: a visarga written as a colon — अंतः
- ch 19 p1 §19.1: a visarga written as a colon — अंतः, अतः, अंतः, अंतः, अंतः, अंतः
- ch 19 p2 §19.2: a visarga written as a colon — अंतः, अंतः, अंतः, अंतः
- ch 19 p3 §19.2.2: a visarga written as a colon — विशेषतः
- ch 19 p7 §19.2.7: a visarga written as a colon — मुख्यतः
- ch 19 p7 §19.2.8: a visarga written as a colon — अंतः, अंतः

## a word glued to a colon that the visarga list does not know — adjudicate

left as written: a `text` entry if the page means a visarga, nothing if it is a colon
- ch 2 p6 §2.2.2: उदाहरण:
- ch 2 p6 §2.2.3: उदाहरण:
- ch 2 p7 §2.2.5: उदाहरण:
- ch 3 p8 §3.3: उदाहरणत:, उदाहरणत:
- ch 4 p2 §4.1.3: अंत:
- ch 4 p3 §4.1.4: अंत:
- ch 4 p9 §4.2.9: अंत:
- ch 4 p10 §4.2.11: द्विपार्श्वत:
- ch 5 p10 §5.5.1.2: उदाहरण:
- ch 5 p14 §5.8: कुटुंब:
- ch 6 p6 §6.2.5: बहि:फ्लोएमी
- ch 8 p11 §8.5.2: मुख्यतय:
- ch 8 p11 §8.5.3: अंत:, अंत:
- ch 11 p5 §11.2: प्रमुखत:
- ch 11 p9 §11.6: अंत:
- ch 11 p19 §11.10: अनुसार:
- ch 14 p6 §14.2: अंत:
- ch 14 p6 §14.2.1: अंत:, अंत:
- ch 19 p2 §19.2.1: उदाहरणार्थ:
- ch 19 p7 §19.2.8: बहि:स्रावी

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

none
rulings on verifier flags that change no text: 0

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

- ch 1 §1.2.7 ¶4: "तालिका 1.1"
- ch 2 §2 ¶3: "तालिका 2.1"
- ch 9 §9 ¶1: "तालिका 9.1"
- ch 9 §9.2 ¶1: "तालिका 9.3"
- ch 9 §9.3 ¶4: "तालिका 9.4"
- ch 9 §9.4 ¶2: "तालिका 9.5"
- ch 14 §14.3 ¶2: "तालिका 14.1"
- ch 15 §15.1.3.1 ¶1: "तालिका 15.1"
- ch 15 §15.1.3.1 ¶2: "तालिका 15.1"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 39 | 678 | 0 |

## sections whose Hindi rows do not number as the English (English is canonical)

193 of 271 sections one to one
| chapter | section | en | hi | unprinted |
|---|---|---|---|---|
| 1 | 1.1 | 15 | 13 | 0 |
| 2 | 2 | 6 | 5 | 0 |
| 2 | 2.3 | 9 | 6 | 0 |
| 3 | 3.1 | 4 | 5 | 0 |
| 4 | 4.1.1 | 3 | 1 | 0 |
| 4 | 4.2 | 2 | 1 | 0 |
| 4 | 4.2.1 | 2 | 1 | 0 |
| 4 | 4.2.2 | 2 | 1 | 0 |
| 4 | 4.2.4 | 2 | 1 | 0 |
| 4 | 4.2.5 | 2 | 1 | 0 |
| 4 | 4.2.6 | 2 | 1 | 0 |
| 4 | 4.2.7 | 5 | 1 | 0 |
| 4 | 4.2.11.7 | 3 | 2 | 0 |
| 5 | 5.5 | 4 | 5 | 0 |
| 6 | 6.1.1 | 2 | 3 | 0 |
| 6 | 6.1.3 | 1 | 2 | 0 |
| 6 | 6.2.6 | 3 | 2 | 0 |
| 7 | 7.1 | 1 | 2 | 0 |
| 8 | 8.4 | 2 | 3 | 0 |
| 8 | 8.4.1 | 6 | 5 | 0 |
| 8 | 8.5 | 3 | 2 | 0 |
| 8 | 8.5.3.1 | 4 | 3 | 0 |
| 8 | 8.5.10 | 6 | 5 | 0 |
| 9 | 9.1 | 7 | 6 | 0 |
| 9 | 9.2 | 2 | 1 | 0 |
| 9 | 9.3 | 5 | 4 | 0 |
| 9 | 9.5 | 2 | 3 | 0 |
| 9 | 9.8.1 | 2 | 5 | 0 |
| 9 | 9.8.3 | 7 | 5 | 0 |
| 10 | 10.1.1 | 8 | 9 | 0 |
| 10 | 10.2.1 | 4 | 5 | 0 |
| 10 | 10.2.2 | 1 | 2 | 0 |
| 10 | 10.4 | 6 | 4 | 0 |
| 10 | 10.4.1 | 7 | 9 | 0 |
| 10 | 10.4.2 | 4 | 5 | 0 |
| 10 | 10.5 | 1 | 0 | 0 |
| 11 | 11.2 | 9 | 11 | 0 |
| 11 | 11.6.1 | 2 | 1 | 0 |
| 11 | 11.6.3 | 9 | 8 | 0 |
| 11 | 11.7 | 4 | 3 | 0 |
| 11 | 11.7.2 | 8 | 7 | 0 |
| 11 | 11.8 | 11 | 10 | 0 |
| 11 | 11.10 | 5 | 3 | 0 |
| 11 | 11.10.2 | 3 | 2 | 0 |
| 12 | 12.1 | 4 | 3 | 0 |
| 12 | 12.2 | 4 | 5 | 0 |
| 12 | 12.5 | 7 | 3 | 0 |
| 12 | 12.7 | 6 | 4 | 0 |
| 13 | 13.1.3 | 1 | 2 | 0 |
| 13 | 13.1.4 | 7 | 5 | 0 |
| 13 | 13.4.2 | 6 | 5 | 0 |
| 13 | 13.4.3.1 | 4 | 2 | 0 |
| 13 | 13.4.3.2 | 3 | 1 | 0 |
| 13 | 13.4.3.5 | 5 | 4 | 0 |
| 14 | 14.2 | 1 | 2 | 0 |
| 14 | 14.6 | 3 | 4 | 0 |
| 15 | 15.1.1 | 2 | 1 | 0 |
| 15 | 15.1.3.1 | 2 | 3 | 0 |
| 15 | 15.2 | 1 | 2 | 0 |
| 15 | 15.3.1 | 3 | 4 | 0 |
| 15 | 15.3.3 | 6 | 4 | 0 |
| 16 | 16 | 3 | 9 | 0 |
| 16 | 16.1 | 5 | 7 | 0 |
| 16 | 16.2 | 6 | 4 | 0 |
| 16 | 16.6 | 1 | 2 | 0 |
| 17 | 17 | 2 | 1 | 0 |
| 17 | 17.2.2 | 3 | 2 | 0 |
| 18 | 18.1 | 2 | 1 | 0 |
| 18 | 18.2 | 5 | 2 | 0 |
| 18 | 18.3.1 | 2 | 3 | 0 |
| 18 | 18.4.1 | 1 | 2 | 0 |
| 19 | 19.2.1 | 1 | 2 | 0 |
| 19 | 19.2.2 | 3 | 4 | 0 |
| 19 | 19.2.4 | 3 | 2 | 0 |
| 19 | 19.2.7 | 5 | 4 | 0 |
| 19 | 19.2.8 | 4 | 3 | 0 |
| 19 | 19.3 | 3 | 2 | 0 |
| 19 | 19.4 | 2 | 3 | 0 |

## English paragraphs this edition does not print — left without Hindi by an `unprinted` entry

none

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 9 | 6 | 27 | 66.7% |
| 2 | 13 | 12 | 44 | 92.3% |
| 3 | 14 | 10 | 30 | 71.4% |
| 4 | 17 | 15 | 45 | 88.2% |
| 5 | 16 | 13 | 53 | 81.3% |
| 6 | 8 | 7 | 17 | 87.5% |
| 7 | 7 | 6 | 18 | 85.7% |
| 8 | 19 | 15 | 59 | 78.9% |
| 9 | 16 | 14 | 53 | 87.5% |
| 10 | 11 | 9 | 42 | 81.8% |
| 11 | 22 | 18 | 73 | 81.8% |
| 12 | 13 | 12 | 35 | 92.3% |
| 13 | 15 | 13 | 39 | 86.7% |
| 14 | 12 | 9 | 28 | 75.0% |
| 15 | 12 | 11 | 35 | 91.7% |
| 16 | 12 | 10 | 38 | 83.3% |
| 17 | 13 | 11 | 31 | 84.6% |
| 18 | 9 | 7 | 18 | 77.8% |
| 19 | 14 | 10 | 32 | 71.4% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 252 | 252 | 208 | 717 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

none

## rows the other edition still carries — this edition's text cleared from them

none

# margai-pipeline ncert load

- run: 2026-10-02 06:39 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (hi)
- result: ok
content store: s3://margai-beta-content
artefact: extract/phy11-part1/hi.hiopus55h.jsonl (a scratch run's draw)
corrections: not applied to a scratch run — its draw is loaded as the model wrote it

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

none
rulings on verifier flags that change no text: 0

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

- ch 1 §1.2 ¶4: "सारणी 1.1"
- ch 1 §1.2 ¶4: "चित्र 1.1(a)"
- ch 1 §1.2 ¶4: "चित्र 1.1(b)"
- ch 1 §1.2 ¶7: "सारणी 1.2"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 66 | 0 | 0 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 12 | 9 | 66 | 75.0% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| unknown | 12 | 9 | 66 | — |
coverage unavailable: ncert_books.pages_hi is not set — `ncert render` records it, and a chapter-subset render deliberately leaves it alone

## addresses in the database this extraction no longer carried — deleted

none

## rows the other edition still carries — this edition's text cleared from them

none

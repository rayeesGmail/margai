# margai-pipeline ncert load

- run: 2026-09-17 22:29 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 0e707cec31b2737c2e26d084640a3de099dd03a82ba7b3606d64790c11ac3db1

## page-break repairs to the model's continuation flags (deterministic, each one named)

- ch 6 §6.8: page 19 repeated the end of page 18's paragraph; the repeated 42 characters were dropped, and the 124 characters before the repeat with them, since they cannot be on this page; what remains is treated as continuing that paragraph

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

- ch 7 page 3 §7.2: a new paragraph starts at "where v is the velocity" (indented after the display (7.2))
- ch 7 page 7 §7.6: a new paragraph starts at "For h/R_E << 1, using binomial expression" (indented after the display for g(h))
- ch 7 page 8 §7.6: a new paragraph starts at "Substituting for M_s from above" (indented after the display (7.17))
- ch 7 page 11 §7.9: a new paragraph starts at "where we have used the relation" (indented after the display (7.36))
- ch 7 page 4 §7.3: "Fig. 7.5" removed from the paragraph holding "Three equal masses of m kg each" (Example 7.2's stem names no figure; the figure belongs to part (b), which carries it too)
rulings on verifier flags that change no text: 4

## zero exponents the book set as a degree sign (deterministic, each one named)

- ch 1 p7 §1.4: a zero exponent set as a degree sign, written as the convention has it — [M^0], [T^0]
- ch 1 p7 §1.5: a zero exponent set as a degree sign, written as the convention has it — [M^0 L^3 T^0], [M^0 L T^-1], [M^0 L T^-2], [M L^-3 T^0]

## figure_refs that are not figure or table labels — dropped

- ch 2 §2.3 ¶5: "Figs. 2.4 (a), (b) and (c)"
- ch 3 §3.7.1 ¶12: "Figs. 3.15(a) to (d)"
- ch 3 §3.7.1 ¶12: "Figs. 3.15 (a), (b) and (c)"
- ch 4 §4.8 ¶7: "Figures 4.8(b)"
- ch 4 §4.8 ¶7: "4.8(c)"
- ch 7 §7.6 ¶1: "Eq. (7.5)"

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 786 | 0 | 106 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 1 | 12 | 9 | 84 | 75.0% |
| 2 | 14 | 8 | 53 | 57.1% |
| 3 | 22 | 16 | 101 | 72.7% |
| 4 | 22 | 17 | 139 | 77.3% |
| 5 | 21 | 15 | 128 | 71.4% |
| 6 | 35 | 31 | 281 | 88.6% |
| 7 | 17 | 12 | 106 | 70.6% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
- ch 1 §1.6.2 ¶4 continues the sentence ¶3 stopped in the middle of: "…product may be written as : T = k l^x g^y m^z" + "where k is dimensionless constant and x, y an…"
- ch 5 §5.9 ¶19 continues the sentence ¶18 stopped in the middle of: "…−mu m g + [mu^2 m^2 g^2 + m k v^2]^(1/2)) / k" + "where we take the positive square root since …"
- ch 5 §5.9 ¶20 continues the sentence ¶19 stopped in the middle of: "…ng in numerical values we obtain x_m = 1.35 m" + "which, as expected, is less than the result i…"

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 143 | 143 | 108 | 892 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

none

# pyq cross-check — neet-2025-45 against its second official copy

The founder's instruction (2026-10-21; DECISIONS): compare every stem and option (text, numbers, symbols, units) of
the reviewed code-45 transcription against the second official copy,
`2025/neet-2025-45-booklet-en-ccitt-watermarked.pdf` (English-only, 1-bit CCITT, lossless, 150 dpi, 32 pages,
sha256 6ef57645218a1c712fc3ec7a764c85d8fe212e10c26a68fe699c3725f72b24db, MANIFEST.csv); resolve any difference against the page images of both official copies, with NTA's Hindi
column as tie-breaker; report every fix. The transcription checked is
`pipeline/data/pyq/neet-2025-45.reviewed.json` as it stood after the review
(`2026-10-20-pyq-review-neet-2025-45.md`, 26 corrections), before the founder's approval.

## How it was read

- **The copy's own scan, without the overlays.** Each of the copy's 32 pages is one CCITT image of 1240 × 1752
  pixels; the coaching logos (SPM & Lalan's) are separate images drawn over it. The check read the CCITT image alone,
  so no logo hid any text, cut into quarters (each column's top and bottom) in exact pixels.
- **Six checkers** (pages 2–6, 7–11, 12–16, 17–21, 22–26, 27–32 — Q1–Q180, every one inside the ranges) compared
  every stem and option word for word, number for number, symbol for symbol and unit for unit, under the notation of
  `pyq_extract.v1` (a convention is not a difference); figure descriptions and drawn options were outside the
  instruction. **Claude resolved every reported difference** on the images of both official copies and NTA's Hindi
  column. The coaching retypes in `reference-only-do-not-load/` were not needed and not used.

## Differences reported, and how each was resolved

| Q | field | the second copy shows | the transcription had | resolution |
|---|---|---|---|---|
| 61 | stem | "Λ°₊ = 349.6 S cm² mol⁻¹ and Λ°₋ = 50.4 S cm² mol⁻¹" — a small capital Λ | lambda°_+ … lambda°_- (lower case, the review's close call on the bilingual copy's 72-dpi page 19) | **fixed** to Lambda°_+ and Lambda°_-: the bilingual copy's English and Hindi columns print the same capital Λ |
| 173 | stem | "pre-requisite for / re-differentiation." — the hyphen in the middle of a line | redifferentiation (the review had joined it as a line-break hyphen) | **fixed back** to re-differentiation: the hyphen is printed; on the bilingual copy the line merely broke at it |
| 1 | stem | θo (θo << 1), the subscript a small letter o | theta_0 (theta_0 << 1) | no change: theta-naught either way, written theta_0 by the notation |
| 42 | stem | P = a³b²/c√d, inline | P = a^3 b^2 / (c sqrt(d)) | no change: the brackets are the notation's for the printed denominator c√d |
| 49 | stem | the reagents over and under the drawn arrow | in the figure description, not the stem | no change: they are part of the drawn scheme, described in `figures` |
| 50 | stem | "R_II" — the subscript H without its crossbar | R_H | no change: this copy, also 1-bit at 150 dpi, loses the same thin stroke; R_H is the given Rydberg energy |

## Fixes — 3, in 2 questions

Q61 (two places: Lambda°_+, Lambda°_-) and Q173 (re-differentiation). The reviewed file now holds 27 corrections
against the draft (the review's 26, less Q173's join, plus Q61's two), and its answers are unchanged: 178 single,
Q40 and Q63 accepting 1 and 2.

## What the second copy confirms

Every other stem and option agrees — that rests on the six checkers reporting no other difference. These were also
seen on this copy's own crops, not inferred from silence: Q7 option 4 prints "2 + 3 sin θ", the review's correction of
"sin 0" (Claude, page 3); Q104 underlines both G (Claude, page 19 — the checkers' brief listed G_underline/G_bar
among the notation to ignore, so this rests on that viewing); and, seen by the spec audit of this report, Q37 prints
E/2m as a slanted fraction (page 8), and the six misprints kept as printed are printed the same here — Q5 "breaks"
twice (page 2), Q14 "N/m" (page 4), Q29 "The chambers contains" (page 7), Q90 option 3's stray "+" (page 17), Q113
"Which of following organisms" (page 21), Q126 "foam braker" (page 23).

## For D31

The second copy's logos are overlays, not part of its scan: drawn without them, its pages carry no logo — including
where the founder notes the translucent logo crosses a figure. Both copies are 1-bit at 150 dpi (the bilingual
copy's page 19 a 72-dpi JPEG), so neither is sharper by resolution; which is cleaner is a per-figure call at D31,
as the founder ruled.

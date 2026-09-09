# NCERT Inputs Manifest — English, 2022 Revised Edition

Source: ncert.nic.in official textbook pages (chapter-wise PDFs)
Downloaded: 2026-09-08
Layout: <book-folder>/<ncert-coded-chapter>.pdf
Filename decode: class(k=11, l=12) + lang(e/h) + subject(ph/ch/bo) + part + chapter(2 digits)
Special files: *ps.pdf = prelims/front matter · *an/a1*.pdf = appendix/answers (optional for ingest)

| book folder  | official title                            | edition          | reprint  | chapters | complete | text OK |
|--------------|-------------------------------------------|------------------|----------|----------|----------|---------|
| phy11-part1  | Physics Part-I, Textbook for Class XI     | Revised Nov 2022 | Apr 2026 | 01–07    | ✓        | ⚠ PUA   |
| phy11-part2  | Physics Part-II, Textbook for Class XI    | Revised Jan 2023 | Apr 2026 | 08–14    | ✓        | ✓       |
| chem11-part1 | Chemistry Part-I, Textbook for Class XI   | Revised Oct 2022 | Feb 2026 | 01–06    | ✓        | ✓       |
| chem11-part2 | Chemistry Part-II, Textbook for Class XI  | Revised Oct 2022 | Feb 2026 | 07–09    | ✓        | ✓       |
| phy12-part1  | Physics Part-I, Textbook for Class XII    | Revised Nov 2022 | Feb 2026 | 01–08    | ✓        | ✓       |
| phy12-part2  | Physics Part-II, Textbook for Class XII   | Revised Nov 2022 | Dec 2025 | 09–14    | ✓        | ✓       |
| chem12-part1 | Chemistry Part-I, Textbook for Class XII  | Revised Oct 2022 | Feb 2026 | 01–05    | ✓        | ✓       |
| chem12-part2 | Chemistry Part-II, Textbook for Class XII | Revised Nov 2022 | Feb 2026 | 06–10    | ✓        | ✓       |
| bio11        | Biology, Textbook for Class XI            | Revised Nov 2022 | Feb 2026 | 01–19    | ✓        | ✓       |
| bio12        | Biology, Textbook for Class XII           | Revised Nov 2022 | Jan 2026 | 01–13    | ✓        | ✓       |

Notes:
- "chapters" = the book's own numbering on its contents page (in *ps.pdf). Part-II folders number
  files from 01: kech201 = Unit 7, keph201 = Chapter 8, lech201 = Unit 6, leph201 = Chapter 9.
  Chemistry calls its chapters "Units".
- "complete" = highest chapter number matches contents page; no gaps in sequence (checked against
  the file list on 2026-09-09).
- "edition" = the "Revised Edition" line on the edition page; "reprint" = the last date under the
  "Reprinted" list that follows it. Every PDF is the 2026-27 print run (running footer).
- "text OK" = every page of every file extracted with PyMuPDF; only blank/figure-only pages and the
  tail pages of *an.pdf yield no text. ⚠ PUA (phy11-part1): keph107.pdf (Gravitation) pages
  1, 3, 4, 8, 13, 14, 16 of 17 and most of keph1ps.pdf extract as private-use codepoints U+F020–
  U+F0FF (cp1252 byte + 0xF000, e.g. U+F043 U+F048 = "CH"). The text is real and decodes by
  subtracting 0xF000; ingest must do that or Chapter 7 loses 7 of its 17 pages.
- Every page carries the text footer "Reprint 2026-27" and a diagonal "© NCERT not to be
  republished" watermark (graphic, not text). Strip the footer string at ingest.
- phy11-part2 is dated "Revised Edition January 2023, Pausha 1944" and its © line reads 2006, 2023.
  It is the same rationalised 2022-23 syllabus as the rest and stays in this folder.
- © line as printed: "© National Council of Educational Research and Training, <first-edition
  year>, 2022" — 2006 for bio11, bio12, chem11-*, phy11-part1, phy12-part1; 2007 for chem12-*,
  phy12-part2; phy11-part2 prints 2006, 2023.
- NCERT textbook codes (imprint page): phy11-part1 11086 · phy11-part2 11087 · chem11-part1 11082 ·
  chem11-part2 11083 · phy12-part1 12089 · phy12-part2 12090 · chem12-part1 12085 ·
  chem12-part2 12086 · bio11 11080 · bio12 12083.

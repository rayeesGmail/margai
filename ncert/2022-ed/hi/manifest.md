# NCERT Inputs Manifest — Hindi, 2022 Revised Edition

Source: ncert.nic.in official textbook pages (chapter-wise PDFs)
Downloaded: 2026-09-08 (assumed: same batch as en/; folders placed in the repo 2026-09-09)
Layout: <book-folder>/<ncert-coded-chapter>.pdf
Filename decode: class(k=11, l=12) + lang(e/h) + subject(ph/ch/bo) + part + chapter(2 digits)
Special files: *ps.pdf = prelims/front matter · *an/a1*.pdf = appendix/answers (optional for ingest)

| book folder  | official title                            | edition          | reprint  | chapters | complete | text OK    |
|--------------|-------------------------------------------|------------------|----------|----------|----------|------------|
| phy11-part1  | भौतिकी भाग 1, कक्षा 11 के लिए पाठ्यपुस्तक      | Revised Dec 2022 | Jan 2025 | 01–07    | ✓        | ✗ Chanakya |
| phy11-part2  | भौतिकी भाग 2, कक्षा 11 के लिए पाठ्यपुस्तक      | Revised Nov 2022 | Dec 2024 | 08–14    | ✓        | ✗ Chanakya |
| chem11-part1 | रसायन, कक्षा 11 के लिए पाठ्यपुस्तक (भाग 1)    | Revised Aug 2022 | Jan 2025 | 01–06    | ✓        | ✗ Chanakya |
| chem11-part2 | रसायन भाग 2, कक्षा 11 के लिए पाठ्यपुस्तक      | Revised Sep 2022 | Jan 2025 | 07–09    | ✓        | ✗ Chanakya |
| phy12-part1  | भौतिकी भाग 1, कक्षा 12 के लिए पाठ्यपुस्तक      | Revised Dec 2022 | Dec 2025 | 01–08    | ✓        | ✗ Chanakya |
| phy12-part2  | भौतिकी भाग 2, कक्षा 12 के लिए पाठ्यपुस्तक      | Revised Sep 2022 | Dec 2025 | 09–14    | ✓        | ✗ Chanakya |
| chem12-part1 | रसायन भाग 1, कक्षा 12 के लिए पाठ्यपुस्तक      | Revised Oct 2022 | Dec 2025 | 01–05    | ✓        | ✗ Chanakya |
| chem12-part2 | रसायन भाग 2, कक्षा 12 के लिए पाठ्यपुस्तक      | Revised Nov 2022 | Dec 2025 | 06–10    | ✓        | ✗ Chanakya |
| bio11        | जीव विज्ञान, कक्षा 11 के लिए पाठ्यपुस्तक       | Revised Nov 2022 | Mar 2026 | 01–19    | ✓        | ✗ Chanakya |
| bio12        | जीव विज्ञान, कक्षा 12 के लिए पाठ्यपुस्तक       | Revised Nov 2022 | Dec 2025 | 01–13    | ✓        | ✗ Chanakya |

Notes:
- "chapters" = the book's own numbering on its विषय-सूची page (in *ps.pdf). Part-2 folders number
  files from 01: khch201 = एकक 7, khph201 = अध्याय 8, lhch201 = एकक 6, lhph201 = अध्याय 9.
  Chemistry calls its chapters एकक (Unit). Chapter lists match the English books 1:1.
- "complete" = highest chapter number matches contents page; no gaps in sequence (checked against
  the file list on 2026-09-09).
- "edition" = the संशोधित संस्करण line on the edition page; "reprint" = the last date under the
  पुनर्मुद्रण list that follows it. Revised-edition months differ from the English books (e.g.
  phy11-part1 Dec 2022 vs Nov 2022, chem11-part1 Aug 2022 vs Oct 2022). Every PDF is the 2026-27
  print run (running footer "Reprint 2026-27").
- "text OK" = ✗ for every book. Pages are selectable text, not scanned images, but the Hindi is set
  in the legacy 8-bit fonts Walkman-Chanakya-901/905 (plus Chanakya and KrutiDev501 in places) with
  no ToUnicode map, so extraction yields glyph codes ("HkkSfrdh" for भौतिकी) and zero Unicode
  Devanagari across all 1,976 pages. Ingest needs a Chanakya→Unicode converter (or OCR) plus a QA
  pass before any Hindi chunk is embedded; until then en/ is the only grounding source.
- Every page carries the text footer "Reprint 2026-27" and a diagonal "© NCERT not to be
  republished" watermark (graphic, not text). Strip the footer string at ingest.
- chem11-part1: the title page and imprint print only "रसायन" (no भाग 1); its ISBN 81-7450-516-4 is
  listed as भाग 1 in the Part-2 imprint. It has no separate *an.pdf: khch1a1.pdf (24 pp) holds
  परिशिष्ट I, the लघुगणक tables and the उत्तरमाला.
- © line as printed: "© राष्ट्रीय शैक्षिक अनुसंधान और प्रशिक्षण परिषद्, <first-edition year>, 2022" —
  2006 for the class-11 books, 2007 for the class-12 books.
- NCERT textbook codes (imprint page): phy11-part1 11088 · phy11-part2 11089 · chem11-part1 11084 ·
  chem11-part2 11085 · phy12-part1 12091 · phy12-part2 12092 · chem12-part1 12087 ·
  chem12-part2 12088 · bio11 11081 · bio12 12084.

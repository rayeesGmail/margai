# NEET (UG) Syllabus Inputs Manifest

Source: NMC / NTA official syllabus PDFs, placed by the founder 2026-09-09. The PDFs are git-ignored
like the NCERT and PYQ inputs (DECISIONS 2026-09-09 D12, 2026-09-10 D13); this manifest is tracked.

| file | exam | pages | producer | notes |
|---|---|---|---|---|
| `syllabus-2025.pdf` | NEET (UG) 2025 | 15 | iLovePDF (modified 2026-01-02) | syllabus only, "SYLLABUS FOR NEET (UG) - 2025" |
| `syllabus-2026.pdf` | NEET (UG) 2026 | 18 | Acrobat Distiller (created 2026-01-08) | NMC letter U-14023/19/NEET(UG Exam)/UGMEB dated 22-12-2025 + public notice + "SYLLABUS FOR NEET (UG) - 2026" |

Structure (both years): Physics 20 units, Chemistry 20 units, Biology 10 units — 50 NTA units. Every
unit is prose and semicolon lists; the text extracts cleanly with PyMuPDF from both files (the 2026
cover page is an image). Two typographical quirks in the 2026 file: Chemistry "UNIT I" (roman one)
and "UNITS 15: HYDROCARBONS".

Diff 2025 → 2026 (checked 2026-09-10, content lines only, cover letters and eOffice footers
removed): **no content change** — the single difference is a line wrap in Chemistry Unit 13
("extraction, and"). The D13 taxonomy therefore follows the 2026 file with no edition fork.

Used by: `pipeline/inputs/taxonomy.csv` (units and topics), see `pipeline/inputs/README.md`.

# NEET PYQ sources and D19 rulings (from the founder)

Read this whole file before acting. It holds my rulings on your D19 plan (PYQ ingest and tagging), the source papers and keys you asked for (F18), what has already been checked, and the rules for using each file. Where it differs from your plan, this file wins.

`MANIFEST.csv` lists every file with its year, code, languages, form, pages, sha256, provenance, key check and notes. Treat it as the input for the `pipeline/inputs/pyq-papers.yaml` register.

---

## 1. Rulings on the D19 plan

**Approved, with the rulings below.** Proceed with T1 to T8 on `d19-pyq-ingest`. Run the spec audit before each commit.

### Tick rule (changed)
D19 ticks when T1 to T8 are done and **2025 code 45** is loaded with every section count matching. Every other paper in this pack loads as its own evidence line, with the same count check. D22 runs on whatever is loaded by then and reruns when all papers are in; its report states the papers used. The Week-4 gate requires every sourced paper loaded and any missing paper logged. Loading these papers is in D19's scope, not overflow. Anything that spills past D22 goes to the slippage log.

### Your questions
1. **Sources: (a).** Use only official booklets in paper order plus the final key. Never use compilations, publisher keys or publisher solutions anywhere. Log F18 for me; this pack delivers most of it. Prefer bilingual (English + Hindi) booklets. Record which papers carry official Hindi text, so the parked Hindi pass extracts NTA's Hindi instead of translating. In this pack that is 2019, 2020, 2021, 2023, 2024 and 2025 (and 2018, whose key is pending); 2022 and the Re-NEET are English only.
2. **Cancelled 3 May 2026 paper: (a), don't load it.** Delete any copy that carries a candidate's personal details (an AglaSem phone scan of code 11 has a candidate's name and roll number on its cover).
3. **Years.** Load the papers in this pack: 2019–2025 plus the 2026 Re-NEET (8 papers), and 2018 only if a fitting key turns up (section 6). Amend SPEC §6.2 and §9 item 1 from "15+ years" to the actual span ("NEET 2019–2026", or "2018–2026" if 2018 loads). Earlier years go to PARKED as a founder decision. Weightage should consider recency, because the 2024 NMC syllabus revision makes older papers less representative; propose the weighting at D22.
4. **Figures: (a).** Record them now. Crop and check them in a buffer before D31. Never serve a figure question without its crop. The load report counts figure questions per subject and section.
5. **Second read: (a).** Add to D20: every disagreement between the AI solution and the official key triggers a check of that question's transcription against the page image before anything else. Log transcription errors found this way per paper.
6. **Old code-45 scan with a candidate's personal details.** The clean code-45 booklet in this pack replaces it, so don't redact it: delete it entirely. Remove `extras/NEET_45_compressed…pdf` locally, and prepare the aws commands for me to delete it from `source/pyq/` in the content bucket, including old versions if the bucket is versioned. I'll run them. Remove any references to that file.

### Your proposals 1–8
Approved as written, plus:
- **Gap 4 (dropped and multi-answer questions).** `pyq_papers.marking` records, per paper, NTA's rule for dropped questions and multiple accepted answers, as published in that year's notice. Cite the notice in the register. Mock scoring follows the register, never a default.
- **Open choices: all accepted.** That covers keys stored as 1–4, file names, the nearest-chapter node plus the flag for out-of-syllabus questions, topic or chapter tagging with ≤2 secondary tags, Opus 5.5 at low effort for extraction, Sonnet 5.5 batched for tagging, and the register folded into load.
- **Watermarks and marks.** The extraction prompt must ignore watermarks ("aglasem.com", "admission.aglasem.com", "prepp"), stamped booklet numbers, coaching marks and any handwriting. None of these is ever read as content or as a key.
- **Lawyer agenda.** Add to the F9 agenda in DECISIONS: use of NTA question papers in a paid app.

---

## 2. Key provenance rule
NTA's site keeps only the current year's keys. Accept a key from these tiers, in order of preference:
1. **official:** downloaded from an NTA domain;
2. **archived-official:** the Internet Archive's capture of the NTA URL;
3. **mirrored-official:** a copy of the NTA document on a third-party site.

A publisher or coaching key is never a source; it may only be a cross-check.

**Amendment for tier 3.** A mirrored key is accepted without a second copy if it fits its booklet on a spot check AND the D20 full solve-vs-key comparison shows no cluster of disagreements. A cluster triggers a second-copy check.

Record per paper in the register: key_source tier, where it came from, the download date (2026-10-20 for this pack unless noted) and the sha256 from `MANIFEST.csv`. A paper with no fitting key is not loaded; log it under F18.

---

## 3. What has been checked already (by my reviewer)
- **Every booklet:** the cover candidate fields are blank, except the Re-NEET scan, whose cover was removed. There's no coloured pen ink on inner pages, again except the Re-NEET. Each runs to its final question, and its code matches its key.
- **Every key:** spot-checked against its booklet by solving the opening questions independently. Results are in the table. The checks prove fit, not every answer; D20's full comparison still applies.

| Paper | Booklet file(s) | Key file | Tier | Spot check | Special entries for THIS code |
|---|---|---|---|---|---|
| 2019 P1 (5 May exam) | `2019/…P1-booklet-hi-en.pdf` | `2019/…final-key-nta.pdf` | 3 | 3/3 | Q6 = {3,4}, Q72 = {1,2} (the key prints "F" and "A"; legend: A=1,2 C=1,4 D=2,3 F=3,4) |
| 2020 E1 | `2020/…E1-booklet-en.pdf` + `…E1-booklet-hi-en.pdf` | `2020/…final-key-nta.pdf` | 3 | 10/10 | none |
| 2021 M4 | `2021/…M4-booklet-en.pdf` + `…M4-booklet-hi-en.pdf` | `2021/…final-key-nta-all-codes.pdf` | 3 | 4/4 | none |
| 2022 R6 | `2022/…R6-booklet-en.pdf` | `2022/…final-key-nta-all-codes.pdf` | **1** (NTA site) | 5/5 | Q95 dropped (printed "5"; no legend in this file, inferred from one "5" per main-series set, all in Chemistry Section B; matches the 2023 legend) |
| 2023 G6 | `2023/…G6-booklet-hi-en.pdf` | `2023/…final-key-all-codes.pdf` | 3 (not on NTA letterhead) | 6/6 | Q32 dropped ("5"), Q141 = {2,4} ("E"); legend: 5 = dropped, B = 1&3, E = 2&4 |
| 2024 S6 | `2024/…S6-booklet-hi-en.pdf` | `2024/…final-key-nta-revised-2024-07-26-all-codes.pdf` | 3 | 3/3 | none. This is the key **revised 26.07.2024 per the Supreme Court order of 23.07.2024**; use it, never the earlier final key |
| 2025 45 | `2025/…45-booklet-hi-en.pdf` | `2025/…final-key-nta-codes-45-48.pdf` | 3 | 3/3 | Q40 = {1,2}, Q63 = {1,2} |
| 2026 Re-NEET 80 | `2026-reneet/…80-booklet-en-scan-cover-removed.pdf` | `2026-reneet/…final-key-nta-codes-50-60-70-80.pdf` | 3 | 2/2 (Q15 = 2, Q18 = 2) | Q2 dropped, Q5 = {1,2} |

Read every multi-code key by its code block; the block for each booklet code is labelled in the file.

---

## 4. Per-file notes for the register (verify page ranges yourself)
- **2018 AA:** original typeset Word PDF with a text layer. The Hindi text layer is in a legacy font encoding (garbled as text, correct visually), so use vision for Hindi; the English text layer is clean, but symbols such as ± and Ω drop out. p1 is the cover; p42–43 are rough work; p44 is the back cover. **Not loadable yet; see section 6.**
- **2019 P1:** clean office scan. p1 is the cover (blank fields; booklet serial number only); p42–43 are rough work; p44 is the back cover.
- **2020 E1:** take English from the English file's text layer. That file has 21 pages against the 24 declared, because its rough-work pages are absent; all 180 questions are present. The bilingual file carries official Hindi. In this paper Biology is Q1–90.
- **2021 M4:** both files are digital and have all 200 questions (Sections A/B, attempt 10 of 15 in each B).
- **2022 R6:** English only. p1 is an AglaSem cover, not the booklet, and the booklet's own cover is missing. Q1 is on p2 and Q200 on p26. Slightly skewed scan.
- **2023 G6:** p1 is an AglaSem cover and p2 the booklet cover; Q1 is on p3 and Q200 on p47. There's an edge watermark.
- **2024 S6:** p1 is an AglaSem cover and p2 the booklet cover; Q1 is on p3. p48 is rough work, p49 the back cover and p50 an AglaSem ad page.
- **2025 45:** p1 is the cover (blank fields). Q180 is on p46; p47 is rough work and p48 the back cover.
- **2026 Re-NEET 80:** see section 5.

---

## 5. Re-NEET 2026 (21 June 2026), special rules
- **Paper of record:** the code 80 booklet scan, a **fallback source**. Page 1 (the cover, with an invigilator's signature) has already been removed from this copy, so the file is 27 pages and starts at the first question page. Store only this version.
- **Its problems:**
  - a candidate's ticks, lines drawn across match-the-columns tables (Q18, for example) and margin notes;
  - a "prepp" watermark and a stamped booklet number across the text;
  - curved pages.

  The booklet declares 32 pages; confirm that the missing ones hold no questions (questions appear to run 1–180 continuously).
- **Extraction rules:** ignore all handwriting, watermarks and the stamp. Flag every question where handwriting touches the stem, options or a table, and check it against the page image. Take answers **only** from NTA's key, never from the candidate's ticks.
- **Key verification already done:** code 80 Q15 = 2 and Q18 = 2 match independent solves. The key's code-50 block agrees with an independent exam-day coaching answer set on 175 of 180. All 5 differences are explained: Q26 is dropped by NTA, where the coaching set said "no option correct"; Q38 accepts {2,3} where the coaching set gave 2; and three answers were marked uncertain by the coaching set. Accept the key.
- **Cross-check aid:** `reference-only-do-not-load/…coaching-retype-CROSSCHECK-ONLY.pdf` is a coaching institute's retyped **code 50** paper.
  - **Never load it,** and never use its answers or figures.
  - Use its text only to cross-check the code 80 transcription. Match each question **by content**, because the codes shuffle both question order AND option order: code 80 Q5 {1,2} is code 50 Q38 {2,3}.
  - Flag any stem or option text that differs, for a check against the code 80 page image.

---

## 6. 2018: pending
The 2018 AA booklet is included because it is the best 2018 source (original typeset, bilingual). The only key found ("NEET-2018 KEY", CODE ACHLA / Set AA, dated 20/05/18, no CBSE header) agreed with independent solves on only 1 of 8 questions, so it's **rejected**. Q2, Q3, Q5, Q6, Q8, Q9 and Q10 disagree; only Q4 agrees.

CBSE seems to have shown 2018 answers to each candidate rather than publishing a public all-codes key. The Word file may also be a pre-print version whose option order changed before printing. **Don't load 2018** unless I provide a key that fits. Record it under F18 as "booklet sourced, key not found".

---

## 7. Order of work
1. T1–T6 as planned.
2. T7: build the register from `MANIFEST.csv` for all 8 loadable papers. Stage **2025 code 45** first; it's the tick. I run extract; you check every flag, plus all 180 questions against the page images; I approve and commit; I run tag and load.
3. The remaining papers, in this order: 2024 S6, 2023 G6, 2022 R6, 2021 M4, 2020 E1, 2019 P1, then the Re-NEET last because it needs the extra checks in section 5.
4. T8: docs, DECISIONS rows for every ruling here, TRACKER, then the spec audit.

For the personal-data deletions (section 1, ruling 6) and the bucket uploads of this pack, prepare the exact commands for me to run, since you have no aws access.

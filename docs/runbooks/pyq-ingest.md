# Runbook — PYQ ingest (D19)

How an official NEET paper becomes questions in the bank: `pyq extract` → review → approval → `pyq tag` →
`pyq load`. The founder's rulings are in `neet-pyq-sources/README.md`; the decisions behind each step are the
2026-10-20 · D19 rows of `docs/DECISIONS.md`. Commands run from `server/` with the jar built by
`./mvnw -q -DskipTests package` (copy it out of `target/` before a long run, so a commit's verify cannot
rebuild it underneath the run).

| Variable | Why |
|---|---|
| `AI_LIVE=1` | the `live` profile; without it every call answers from a fixture, and `extract`/`tag` refuse to run |
| `MARGAI_AI_ANTHROPIC_API_KEY` | exported in the shell beforehand, never written into a script (`ai-provider-keys.md`) |
| `AWS_PROFILE=margai` | the content bucket, after `aws sso login --profile margai` |
| `DB_URL=jdbc:postgresql://localhost:5432/margai_d15` | the pipeline database, where the NCERT corpus is — D23 anchors questions to its paragraphs |

## 0. Once: the pack in the bucket, and the taxonomy in the pipeline database

1. Upload the pack's booklets and keys to `source/neet-pyq-sources/…` (the founder; the reference-only retype
   stays local — DECISIONS 2026-10-20). `pyq extract` refuses a booklet whose sha256 is not MANIFEST.csv's.
2. Before the first `pyq load`, load the taxonomy into `margai_d15` (₹0, idempotent; it holds the NCERT corpus
   but no syllabus nodes):

   ```
   DB_URL=… java -jar <jar> --spring.profiles.active=pipeline taxonomy load
   DB_URL=… java -jar <jar> --spring.profiles.active=pipeline taxonomy prerequisites
   ```

## 1. Register the paper

`pipeline/inputs/pyq-papers.yaml` (founder-owned; Claude builds each entry from MANIFEST.csv and the booklet's
own pages): the booklet file(s) with their sha256 and the pages that hold questions, the sections as printed
(or, with no headings, as the cover declares the subjects and the questions turn), the marking the booklet
prints, and the key's provenance with the code's special entries from README §3. The reader refuses a paper
that disagrees with itself; `PyqPapersYamlReaderTest` holds every file to MANIFEST.csv.

## 2. `pyq extract` — the founder runs it

```
AI_LIVE=1 AWS_PROFILE=margai DB_URL=… caffeinate -i java -jar <jar> \
  --spring.profiles.active=pipeline,live,visionopus55 pyq extract --paper <code>
```

One VISION call per register page on Claude Opus 5.5 at effort low (any other VISION model is refused before
the first call), roughly ₹5–6 a page. It resumes from `extract/pyq/<code>.jsonl`; whatever stops it, the pages
paid for are kept and the bill is reported. It writes the draft `pipeline/data/pyq/<code>.draft.json` (answers
empty) and the report `pipeline/reports/<date>-pyq-extract.md` with every flag.

**Resolution.**
- **The default:** pages are drawn at the configured 150 dpi, in two tiles.
- **Finer scans:** a booklet scanned finer than 150 dpi is read at its scan's own dpi, set with a system property *before* `-jar`, since the runner hands every non-`--spring.` argument to the command parser (DECISIONS 2026-10-22; 2023 G6 is 200 dpi):

  ```
  … java -Dmargai.pipeline.render-dpi=200 -jar <jar> --spring.profiles.active=pipeline,live,visionopus55 pyq extract --paper neet-2023-g6
  ```

- **One paper, one resolution:** every later run on that paper sets the same property — a resume, a `--redo`, an `--images-only`. A run whose dpi or tiling is not the one its pages were drawn at is refused before any call. The refusal names the property to set where all the pages share one resolution, and always the pages to read again.
- **Changing a paper's resolution:** use `--redo --change-resolution` over every page drawn at the other one; the refusal names those pages.
- **Check every report:** its "page images:" line shows the dpi the run used.

Each page image is kept at `pages/pyq/<code>/<page>.png` in the bucket and its JSONL line records the image's
sha256, dpi and renderer version (the founder's acceptance, DECISIONS 2026-10-20). Claude's, in the same
DECISIONS: the image is kept before its call, a copy goes under `pipeline/data/pyq/pages/<code>/`, the line also
records the tiles, and the report lists every page's sha256. A paper read before images were kept (2025 code 45)
gets them with no model call (the founder's), run twice (Claude's) — the first keeps them, the second shows they
draw the same bytes again:

```
AWS_PROFILE=margai DB_URL=… java -jar <jar> --spring.profiles.active=pipeline pyq extract --paper <code> --images-only
```

## 3. Review — Claude

1. Every flag in the report, and **every question**, against the page images (README §7): stem, options,
   figures, numbering. Every reader works from `pyq-review-reader-rules.md` beside this runbook (Claude's brief,
   kept with the rulings since 2026-10-24 so each paper is read the same way). *Since 2026-10-24 (the founder;
   DECISIONS; SPEC §6.2): words printed run together with no spaces, a single missing space between two words
   included, are a typesetting fault, not content — the text shows them normally spaced and a `record_only`
   as-printed entry keeps the printed form. A paper already approved takes the rule at its next reload, not a
   reload of its own for this (2024 S6's Q124 "waterlily"), and any three-word joins a scan missed are fixed when
   found, at that paper's next reload.* *(Claude's, DECISIONS 2026-10-24: that entry is the one whose printed span
   its field does not carry, which `PaperFileJson` accepts only so.)* The page images are the saved ones the model read (the founder's acceptance):
   `pipeline/data/pyq/pages/<code>/`, each file's sha256 checked against the page-image table of the extract
   report that kept it (for 2025 code 45, its `--images-only` report — and code 45's are re-renders, not
   provably the bytes its calls read; DECISIONS 2026-10-20). Where a glyph is too small to settle at
   150 dpi, a zoomed drawing of the same PDF region settles it and the review report says so (Claude's, for the
   founder to overrule; DECISIONS 2026-10-20). A corrected question keeps its `extraction` provenance; each change is listed in the
   review report `pipeline/reports/<date>-pyq-review-<code>.md` with the page it was read on.
   Each misprint kept as printed (a wrong character the paper prints, as opposed to a stroke the scan lost —
   DECISIONS 2026-10-20 and 2026-10-21; one the English column alone prints included, by SPEC §6.2 (the founder,
   2026-10-21). Whether the paper printed it: a wrong whole character in a JBIG2 or other lossy 1-bit scan is never
   evidence on its own, and only a lossless copy, a non-JBIG2 scan or the other language column can settle it (the
   founder, same DECISIONS) — the other column settles it where it prints the same wrong character (a Latin glyph in
   the other column of the same JBIG2 page only where no substitution could have produced it; Claude's, approved
   by the founder with 2023 G6's review, DECISIONS 2026-10-24), and a non-JBIG2
   scan is read as one that is not lossy 1-bit (Claude's readings, same DECISIONS, for the founder to overrule); a
   case nothing settles is read as
   meant, with no as-printed note, and listed in the review report as "cause unsettled", with no per-item ruling — it
   goes to the founder only when the intended reading itself is in doubt, or when the character touches the keyed
   answer, a number, a unit, a sign or a formula and the Hindi column, the second official copy and D20's
   solve-vs-key check together still cannot settle it; the founder, same DECISIONS) is recorded with a one-line description of the intended reading, which the
   app can show as an "as printed in the original paper" note (the founder, 2026-10-21; PLAN D31, D40) — as an
   `as_printed` entry of its question: the field (`stem` or `option:N`), the span as printed (no more than the wrong
   word or sign and what it needs to name its place), the intended reading, and the note (Claude's shape, DECISIONS
   2026-10-21). The review assigns every kept misprint its display class (the founder, 2026-10-21): `show` for one
   that could change how a student reads the question — a wrong unit, symbol, number, sign or formula, or a
   misspelling that forms a different real word — and `record_only` for a pure spelling or grammar slip a student
   reads correctly; only `show` notes are displayed, and `pyq load`'s report counts both.
2. The answers from NTA's final key for the code, read from the key PDF's own text layer by a script (no
   model), checked for 1..N once each and for exactly the register's special entries.
3. For the Re-NEET (README §5): every question with `handwriting touches` checked against the image, and the
   coaching retype of code 50 used only to cross-check the text, matched by content — never its answers or
   figures, and never loaded.

## 4. Approval — the founder

The reviewed file goes to `pipeline/inputs/papers/<code>.json` only on the founder's approval and is committed
with the review report. `pyq tag` and `pyq load` read only that file, and refuse it if it fails its register
entry (every declared question once, options (1)–(4), an answer per question agreeing with the special entries).

## 5. `pyq tag` — the founder runs it

```
AI_LIVE=1 AWS_PROFILE=margai DB_URL=… java -jar <jar> --spring.profiles.active=pipeline,live pyq tag --paper <code>
```

One REASON call per question (Claude Sonnet 5.5), the outline of the section's subject from `taxonomy.csv` as
the cached prefix — any biology section (Biology, Botany or Zoology) gets botany and zoology together, and a
section with no printed heading takes the subject the register gives it; every code is checked in Java and a
bad tag is kept with its problem, never loaded. The report
lists tags with problems (re-tag with `--redo --questions N`), the spread per chapter, every out-of-syllabus
question and the founder's seeded sample of 40.

*Since 2026-10-21 (the founder; DECISIONS):* out of syllabus means the topic is absent from the **current** NMC
syllabus **and** the current NCERT books — topics cut in 2024, for instance, which will appear in the 2019–2023
papers. A question NCERT teaches is in syllabus and tagged to that chapter. Where no taxonomy node fits it, it goes
on the taxonomy-gap list with a proposed node — never the flag. *(Claude's, same DECISIONS, for the founder to
overrule: before an out-of-syllabus flag stands, Claude looks for the question's NCERT anchor — in the corpus, and in
the books' PDFs for the pages the corpus leaves out, unit openers and biography pages.)*

*Since 2026-10-22 (the founder's approval of 2026-10-21; DECISIONS):* `pyq_tag.v2` reads each figure's description
where the booklet prints it. A tag strictly below the review floor (0.5) is on **the founder's review list** in the
report and loads as `review`, never as a normal tag, until he rules. **His rulings** go in
`pipeline/inputs/pyq-tag-rulings.yaml` — Claude drafts each entry (from his rulings, the review list, his 40-question
sample and the out-of-syllabus check above), he approves the file, and it is committed — and the report lists each
ruling beside the tagger's tag, naming any the taxonomy would refuse. *(Claude's, same DECISIONS, for the founder to
overrule: the file's shape; its per-paper `approved_file_sha256`, which Claude fills, so the rulings are refused once
the approved file changes until each is confirmed again; a paper code the register lacks refused.)* `pyq tag` re-tags
a tag of another prompt version, so a paper tagged under v1 is redone by naming its questions, `--redo --questions
N,…`, never by a bare run.

## 6. `pyq load` — the D19 check, the founder runs it (it reads the tags from the content bucket)

```
AWS_PROFILE=margai DB_URL=… java -jar <jar> --spring.profiles.active=pipeline pyq load --paper <code>
```

No model. One transaction: the paper, its questions and their secondary topics; the paper's questions are read
back and counted per printed section, and any section that does not hold what it declares rolls the load back.
The report's first table is the D19 ✅ — declared against loaded per section, with the figure questions per
section (founder's ruling 4) — and it names the marking values still waiting for NTA's notice (F18).

*Since 2026-10-22 (the founder's approval of 2026-10-21; DECISIONS):* a question the founder ruled loads his ruling
as `tag_status = founder`; a tagger's tag below the floor loads as `review`. *(Claude's, same DECISIONS, for the
founder to overrule: the tagger's tag is kept beside a ruling, and a ruled question needs no valid tag of its own;
the report counts the three statuses and lists the review questions, the rulings, the taxonomy-gap list and any
question with no NCERT anchor, which D20 does not solve until the founder rules; a paper loaded before V11 reads
`model` for every question until it is loaded again.)* *Since 2026-10-22 (the founder; DECISIONS):* no AI solution
is written for a "no NCERT anchor" question; the load report counts them for the paper and against the whole PYQ
bank, and flags the bank's share above ~2% for the founder to revisit — tell him when it does.

## Order (README §7)

2025 code 45 first — it is D19's tick — then 2024 S6, 2023 G6, 2022 R6, 2021 M4, 2020 E1, 2019 P1, and the
Re-NEET (code 80) last. 2022 R6 and the Re-NEET are not in the register yet: the pack holds no marking for
either (their covers are missing) and no exam date for 2022, so each is registered when F18 supplies them —
Claude's call, for the founder to overrule (DECISIONS 2026-10-20); until then the order skips them. 2018 AA is registered only if a fitting key turns up (README §6).

# NCERT ingest — register, render, extract, load

The four `ncert` commands of TECH_PLAN §6.3, in the order they must run, and the spot-check that
closes PLAN D14's ✅. D14 does the two English pilot books; D15 repeats it for the remaining eight,
D16 for the Hindi editions.

Two of the four need credentials **Claude does not have** — the AWS profile for the content bucket
and the live provider key — so `render`, `extract` and `load` are founder-run, as the D5 live smoke
was. `register` is database-only and Claude runs it.

## Before you start

| Needs | Why |
|---|---|
| `AWS_PROFILE=margai` | the content bucket (`margai-beta-content`), for `render`, `extract`, `load` |
| `MARGAI_AI_ANTHROPIC_API_KEY` | the VISION calls, for `extract` only (docs/runbooks/ai-provider-keys.md) |
| `AI_LIVE=1` | the `live` profile; without it every call answers from a fixture (DEV_SPEC §13.7) |
| a database | local: `docker compose up -d db`; the commands read `DB_URL` |

Build once: `cd server && ./mvnw -q -DskipTests package`, then run everything from `server/`.

The pipeline profile sets the content bucket and raises the ledger's daily budget to ₹2,000 for a
run (`application-pipeline.yml`). The console workspace spend limit is the independent backstop;
neither replaces the other.

## 1. register — books.yaml into ncert_books

```
DB_URL=jdbc:postgresql://localhost:5432/margai_d14 \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline ncert register
```

No AWS, no model, no cost. Expect `inserted 10` on a fresh database and `unchanged 10` on a re-run.

## 2. render — source PDFs into page images

```
AWS_PROFILE=margai DB_URL=… \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline \
  ncert render --book bio11 --lang en
```

Reads `source/ncert/2022-ed/en/<book>/*.pdf`, writes `pages/{book}/{lang}/{chapter}/{page}.png` at
150 DPI, and records the total in `ncert_books.pages_en`. No model, so no model cost — S3 storage
and requests only.

Re-runnable: a page already in the bucket is not rendered again. `--chapters 7,8` renders a subset
(and then leaves the page count alone, because a subset is not the book's total). **`--redo`
re-renders pages that are already there** — needed whenever the pages in the bucket are wrong
rather than missing, which is not hypothetical: the first real run rendered without a JPEG2000
decoder and PDFBox answered by drawing those pages *without their figures* instead of failing.

A page PDFBox cannot draw completely now **fails the run**, whatever the reason. That guarantee is
structural, not a list of formats: NCERT needed two different image decoders and the second was
found only after a guard written for the first, on a run that reported `result: ok` while writing a
page with a blanked image. If a render ever *did* write such pages, every page it wrote is suspect
and `--redo` is the only way back.

Before a book is rendered for the first time, the cheap pre-flight is
`./mvnw test -Dtest=PdfPageRendererTest`: with the PDFs on the machine it draws every page of both
pilot books and fails on any decoder gap, for free, in about 90 seconds.

Expect roughly 264 pages for `bio11` and 184 for `phy11-part1`, matching `ncert/2022-ed/en/manifest.md`.

## 3. extract — page images through the VISION tier

**This is the command that spends.** One model call per page.

```
AI_LIVE=1 AWS_PROFILE=margai MARGAI_AI_ANTHROPIC_API_KEY=… DB_URL=… \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live \
  ncert extract --book bio11 --lang en
```

Start with one chapter (`--chapters 1`) and read its report before letting the book run: the cost
line and the low-confidence list are both in it, and a prompt that is reading the pages wrongly is
cheapest to catch after twenty pages rather than after two hundred.

**The end-of-chapter apparatus is never sent.** Before calling for any page, the command reads the
chapter's own text layer and finds where Summary / Points to Ponder / Exercises / Answers begins;
that page and everything after it is recorded as skipped and costs nothing. This is not a
politeness — NCERT numbers its exercises with the chapter number (Chapter 7's questions are 7.1,
7.2, 7.3), so a page of them is indistinguishable from a page of sections, and asking the model to
ignore them did not work. The report's **apparatus table** says, per chapter, where the boundary fell and
which heading found it. **Read it.** A boundary that looks too early means real teaching is being
skipped; `—  not found: every page is sent` means the text layer was unreadable and nothing was
skipped, which is safe but means the model will see the exercises for that chapter.

**The page's own text layer is sent with the image** (D14, DECISIONS 2026-09-13). The image decides
layout and reading order; the text layer decides characters, because these books are digitally
typeset and every glyph the model misread at D14 was already correct in the PDF. The report's **text
layer table** — the first one it prints — says, per chapter, whether the layer was fed or withheld
and the legibility score behind that call. `withheld: illegible` is expected for **every Hindi book** and for `chem11-part2/kech202.pdf`,
whose text is a custom-encoded font; it is not expected for any other English chapter, and one that
appears is worth stopping for.

Estimated cost at `claude-haiku-4-5` rates, built from the one real measurement rather than from a
rate card: ch 7 of `phy11-part1` cost **₹12.71 for 12 billed pages — ₹1.06 each** (17 rendered, 5
apparatus pages never sent) with tiling and no text layer. The text layer adds ~30–40% of *input*
tokens, which is a smaller share of the bill than it sounds — output tokens price 5× input and
cache reads a tenth — so call it **₹1.15–1.35 per billed page**. Against ~20–25% of pages being
apparatus: `bio11` ≈ 211 billed of 264 → **₹250–285**, `phy11-part1` ≈ 138 billed of 184 →
**₹160–185**. Both books together, **about ₹430**.

The report's cost line is the truth — it comes from the `ai_calls` ledger, not from an estimate —
and the first chapter's report is where to check this estimate before letting a book run. Two
numbers in it are worth reading directly: `cache write` on the first call is the cached prefix's
real token count (it must clear 4,600, the founder's floor on the cheap model's own tokenizer; the
v2 template estimates ~5,325 by the startup tripwire's cruder proxy), and `cache read` on every
call after it is the proof the prefix is actually being cached rather than re-sent at full price.

**Read the two new report sections before the load.** Each begins with a `checked:` line saying how
many of the pages called this run it actually looked at — a page the run resumed over and a chapter
whose text layer was withheld are not checked, and the sections say so rather than printing `none`.

`characters that differ from the page's text layer` asks two questions of every paragraph, both
against that page's own layer: **is this word on the page at all** (a word used more often than the
whole page holds it is invented or misread) and **is this symbol on the page** (`m_p`, `R_E`,
`10^8` are glued back to the `mp`, `RE`, `108` the layer actually contains, and looked for). What it
cannot see, so that `none` is read correctly: punctuation is ignored, so **a dropped `×`, a lost
leading minus and a dropped prime will not appear here** — on these files they cannot, because NCERT
sets those glyphs in a Symbol font with no Unicode mapping and `Kepler's` reaches the text layer as
`Keplers`. Nor will a digit `1` read as a letter `l`. Those four are what step 4 of the ✅ below is
for.

`pages whose paragraph count does not match the page's shape` is a deliberately independent check —
it knows nothing about characters, so it still has something to say where the layer itself is wrong.
Both route attention; neither refuses. The low-confidence list is the weakest of the three signals
(D14: the model was uniformly confident on a run that carried three real defects), so read it last.

Resumable: pages already in `extract/{book}/{lang}.jsonl` are not called for again, so an
interrupted run costs nothing to finish and a re-run costs nothing at all. `--redo` deliberately
pays again for pages already done.

**One verified run per book is the corpus.** Extract, audit, load, and from then on treat a
re-extraction as a corpus event rather than a re-run: segmentation legitimately differs between
extractions, and from D17 a chapter's paragraphs carry embeddings and question anchors that a silent
re-cut would re-point. If a book must be re-extracted after that, it is re-embedded and re-anchored
with it (DECISIONS 2026-09-13; the guard lands with D17).

## 4. load — JSONL into ncert_paragraphs

```
AWS_PROFILE=margai DB_URL=… \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline \
  ncert load --book bio11 --lang en
```

No model, no cost. Upserts on the paragraph address. A paragraph that straddles a page break — with
or without a figure page in between — is joined into one row carrying every page it came from.

The report gives two different numbers, and they answer different questions:

- **Coverage** = pages extracted ÷ pages rendered. This is the D14/D15 ✅ number: it asks whether
  the whole book was read. Anything below 100% on a whole-book load prints `INCOMPLETE` and names
  how many pages are missing — run `ncert extract` again before trusting the load.
- **Text yield** (per chapter) = pages that produced paragraphs ÷ pages extracted. This is expected
  to sit below 100%: chapter-opening plates, full-page figures and exercise pages produce nothing
  and are read correctly. What matters is that no chapter is near *zero*, which would mean pages
  were rendered but not read.

The load refuses rather than guesses. It stops, names the page and prints the `--redo` that fixes
it, when a section does not belong to its chapter, when a section is not a printed section number,
when a paragraph number is out of range, or when two different paragraphs claim one address — the
signature of a page whose numbering restarted. Nothing is written when it refuses.

## The D14 ✅ — 20 random paragraphs against the PDFs

Sample across both books, weighted by nothing — random is the point:

```sql
SELECT b.code, p.chapter_no, p.section, p.para_no,
       p.extraction->'pages' AS pdf_pages,
       p.extraction->>'confidence' AS confidence,
       left(p.text_en, 240) AS text
  FROM ncert_paragraphs p JOIN ncert_books b ON b.id = p.book_id
 WHERE b.code IN ('bio11', 'phy11-part1')
 ORDER BY random()
 LIMIT 20;
```

For each row, open `ncert/2022-ed/en/<book>/<file>.pdf` at the page in `pdf_pages` — the file is the
one `books.yaml` maps to that `chapter_no` — and check three things:

1. **The text is the book's.** Same wording, same spelling, nothing summarised, nothing added. A
   paragraph that reads better than NCERT is a paragraph to reject.
2. **The address is right.** The section printed above it matches `section`, and the paragraph is
   where `para_no` says within that section.
3. **The flags are right.** `figure_refs` matches the figures the paragraph actually names.
   (`has_equations` is no longer the model's to get wrong — it is computed in Java from the
   transcribed text, so a wrong value there is a regex to fix, not a paragraph to reject.)
4. **Read at least five of the twenty against the rendered page image**, not against the PDF's text
   layer or a text search of it — open `pages/{book}/en/{chapter}/{page}.png`, or the PDF page as it
   renders on screen. This is not fussiness: the model is now *given* the text layer, and the
   character diff *checks* against the text layer, so the two share a source and agree wherever that
   source is wrong. The page as printed is the only thing outside that loop, and a human eye is the
   only thing that reads it. At D14 this caught what the mechanical check structurally could not
   (DECISIONS 2026-09-13, FIX 4).

Record the twenty rows and the verdict in the TRACKER day log. A failure in (1) is serious and
means the prompt or the render DPI needs work before D15; a failure in (2) is the anchor being
wrong, which is what the whole addressing scheme exists to prevent.

**Re-running the audit after an extraction change.** Sample the *same pages* as the previous audit,
not a fresh random twenty: a defect table is only evidence if the two runs are comparable. The D14
table — the defect list, what tiling fixed and what the v2 prompt is meant to fix — is in the TRACKER
day log for 2026-09-13; work down it item by item and record which are gone, which survive and
which are new. A new defect class matters more than a surviving one: surviving defects were already
priced in, a new one means a change made something worse.

Also read the extract report's low-confidence list: those pages are the model telling you where to
look, and they should be checked whether or not the random sample lands on them — while remembering
that at D14 it was silent about every defect that mattered.

## If something goes wrong

| Symptom | Cause | Fix |
|---|---|---|
| `input file not found: …/books.yaml` | run from the wrong directory | run from `server/`, or pass `--inputs` |
| `book '…' is not registered` | step 1 not run against this database | run `ncert register` |
| `chapter N of '…' has no rendered pages` | step 2 not run, or a chapter subset | run `ncert render` for that chapter |
| `no extraction at extract/…` | step 3 not run for this edition | run `ncert extract` |
| the report says `content store: in-memory` | no bucket configured | the `pipeline` profile sets it — check the profile is active |
| every page comes back with low confidence | the render DPI, or a bad scan | try `margai.pipeline.render-dpi=200` on one chapter |
| the run stops with a budget refusal | the ledger's daily cap | it is per IST day; either wait, or raise it deliberately |

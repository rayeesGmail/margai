# NCERT ingest — register, render, extract, load, verify

The five `ncert` commands of TECH_PLAN §6.3, in the order they must run, and the spot-check that
closes PLAN D14's ✅. D14 does the two English pilot books; D15 repeats it for the remaining eight,
with the second read (`ncert verify`, D15) before a book is taken as canonical; D16 for the Hindi
editions.

Four of the five need credentials **Claude does not have** — the AWS profile for the content bucket
and the live provider key — so `render`, `extract`, `load` and `verify` are founder-run, as the D5
live smoke was. `register` is database-only and Claude runs it.

## Before you start

| Needs | Why |
|---|---|
| `AWS_PROFILE=margai` | the content bucket (`margai-beta-content`), for `render`, `extract`, `load` |
| `MARGAI_AI_ANTHROPIC_API_KEY` | the VISION calls, for `extract` and `verify --read-pages` only (docs/runbooks/ai-provider-keys.md) |
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

**Since prompt v3 (D15, 2026-09-14) the model does not number paragraphs.** It returns each
paragraph's section and text and, on the page's first paragraph, one flag — whether it is the
rest of the paragraph the previous page ended in — and `ncert load` counts ¶1, ¶2, ¶3 per section
across pages. So the extract report names a paragraph by its position on the page, `ch 6 p8 §6.2
#1`, because ¶n does not exist until the load. A blank paragraph or a flag on any paragraph but
the first is refused where the model's output is decoded and the page is re-called at once, with
both attempts on the ledger. Nothing of the previous page's text travels with the call — only its
section and one fact, whether its last paragraph stopped mid-sentence — so the flag is judged
from the page's own typography, with the rule that a continuation can only sit at the top of the
left or only column: the first v3 measurement showed
the cheap model echoing a quoted tail on two of twelve pages, once as a paraphrase no repair can
see. The prompt also checks its own headings before finishing, because the reasoning model on the
same measurement skipped a heading at a column top and filed the next two pages under the
section before it.

### The chapter-7 dry run, after any change to extraction

`phy11-part1` chapter 7 (Gravitation, `keph107.pdf`, 17 pages of which 12 are billed) is the
standing test chapter, and it costs about ₹15. Every defect in the D14 table came from it, so it is
the one chapter where a new run can be compared against a known result instead of being read cold.
Seven of its seventeen pages also carry the private-use encoding that broke text extraction at D9,
which makes it the hardest case for FIX 1 as well as the cheapest.

```
# 0. an artefact from an earlier prompt version must be moved aside first: the run refuses it by
#    name, and the old version's corpus stays re-derivable from its own artefact (FIX 5)
aws s3 mv s3://margai-beta-content/extract/phy11-part1/en.jsonl s3://margai-beta-content/extract/phy11-part1/en.v2.jsonl --profile margai

# 1. extract one chapter
AI_LIVE=1 AWS_PROFILE=margai MARGAI_AI_ANTHROPIC_API_KEY=… DB_URL=… \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live \
  ncert extract --book phy11-part1 --lang en --chapters 7

# 2. load the same chapter (no model, no cost; coverage prints — for a subset, which is correct)
AWS_PROFILE=margai DB_URL=… \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline \
  ncert load --book phy11-part1 --lang en --chapters 7
```

Read these six things in the extract report, in this order, before spending anything more:

1. **`| 7 | fed as the character authority |`** in the text layer table. If it says `withheld`, FIX 1
   did not happen on this chapter and every number below is about something else.
2. **`cache write` on the cost line** — the cached prefix's real token count on the model's own
   tokenizer. It must be at or above 4,600. `cache read` on the calls after the first proves the
   prefix is being cached rather than re-sent at full price.
3. **The apparatus boundary** — chapter 7's exercises begin at the page the table names, and the
   pages not sent should be 5 of 17.
4. **`characters that differ …`** — this is the section the whole day was about. A handful of lines
   is the expected shape. A wall of them means the check is mis-tuned against this file and is worth
   stopping for; `none` with a `checked:` count of zero means nothing was examined.
5. **The cost per billed page** — measured at ₹1.07–1.09 across the whole of `phy11-part1` with the
   text layer and bands (₹117.88 for 108 billed pages, 2026-09-13).
   Meaningfully above that changes the estimate for the other nine books, not just this one.
6. **That the load succeeds at all.** Two paragraphs at one address was the failure three
   separate D14 runs hit; since v3 the loader numbers, so it cannot happen. What the loader still
   refuses by name, with the `--redo` that fixes it, is a continuation flag that cannot be one:
   nothing before it in the chapter, a different section, an absent page between.

Biology has its own dry run: `bio11` chapter 1 (`kebo101.pdf`, 9 pages, 8 billed, about ₹8),
with a unit-opener biography, binomials, "Figure 1.1" refs and Table 1.1 on its pages (D15).

Then compare the paragraphs against the D14 defect table in the TRACKER day log for 2026-09-13 —
the same sample pages, item by item. Four of its nine items (the dropped prime, the lost minus, the
dropped `×`, `1` read as `l`) cannot appear in the report by construction and have to be looked for
by eye on the rendered page.

Nothing here is wasted: the chapter stays in the JSONL, and the full-book run resumes over it
without paying for those pages again.

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

Cost at `claude-haiku-4-5` rates, measured on a whole book rather than estimated: the canonical
extraction of `phy11-part1` on 2026-09-13 cost **₹117.88 for 108 billed pages — ₹1.09 each** (143
rendered, 35 apparatus pages never sent), with the text layer and two bands per page. The text
layer's extra input tokens turned out to cost less than feared, because output tokens price 5×
input and cache reads a tenth. So plan on **₹1.05–1.15 per billed page and ~75% of pages billed**:
`bio11` ≈ 200 billed of 264 → **about ₹220**; a book the size of `phy11-part1` → **about ₹120**.
A chapter-7 dry run is ₹13.

The report's cost line is the truth — it comes from the `ai_calls` ledger, not from an estimate —
and the first chapter's report is where to check this estimate before letting a book run. Two
numbers in it are worth reading directly: `cache write` on the first call is the cached prefix's
real token count (it must clear 4,600, the founder's floor on the cheap model's own tokenizer; the
v2 template measured 6,448 on the real tokenizer on 2026-09-13, and v3 is longer — its own figure is
read from the first v3 run's `cache write`), and `cache read` on every
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

No model, no cost. Numbers the paragraphs — ¶1, ¶2, ¶3 per section in reading order across pages
(v3, D15) — and upserts on the address. A paragraph that straddles a page break — with or without
a figure page in between — is joined on the extraction's flag into one row carrying every page it
came from. **Rows of the loaded chapters that this extraction no longer produces are deleted and
listed** under their own heading (DECISIONS 2026-09-14): with the loader numbering, a one-page
redo shifts every address after it, so a re-extraction always leaves some. Two exceptions: a row
still holding the other edition's text is kept (the Hindi cut is not the English one), and a row
that is anchored refuses the whole load — re-extracting an anchored chapter is a corpus event
that re-anchors what it moved (D17), not a load.

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
when a paragraph is blank, or when a page's first paragraph claims to continue the previous page
and cannot — nothing precedes it in the chapter, the previous page ended in a different section,
or a page between them is missing from the JSONL. Every refusal is named at once, with one
`--redo` per chapter. Nothing is written when it refuses.

Every run's report is its own file: a second run of the same command on the same day writes
`<date>-<command>-2.md`, a third `-3.md`, and nothing overwrites an earlier run's (D15).

**The load applies `pipeline/inputs/ncert-corrections.yaml`** (D15) after its page-break repairs and
before it numbers: the founder's rulings on what `ncert verify` flagged. `text`, `join` and `split`
entries change the frozen run's pages; `misprint` and `false_positive` rule on a flag and change
nothing. Each applied entry is one line of the report; an entry whose span is not on its page exactly
once refuses the load by name. The file's own header and `pipeline/inputs/README.md` say how to write
one. Each row now also records where each of its pages' parts begins (`pageStarts` in the row's
`extraction`), which verify needs — a row loaded before 2026-09-14's build has none, and verify asks for
the reload.

## 5. verify — the second read (D15, DECISIONS 2026-09-14 "the pair")

Claude Opus 5 transcribes; Claude Sonnet 5 reads every page again and judges each paragraph with one
question — *does this text match the print?* — naming the printed and transcribed spans where not.
Beside it, free code checks hold the rows to the print's typography. **Nothing here changes a word:**
a flag is adjudicated against the rendered page, and the outcome is an entry in
`ncert-corrections.yaml` that the next `ncert load` applies.

```
# free: layout checks, and the second read's verdicts re-judged from the artefact with today's rulings
AWS_PROFILE=margai DB_URL=… java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline ncert verify --book phy11-part1 --lang en --chapters 7

# the second read: one Sonnet call per page, on the visionsonnet shape (the provider key sourced first, below)
AI_LIVE=1 AWS_PROFILE=margai DB_URL=… java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live,visionsonnet ncert verify --book phy11-part1 --lang en --chapters 7 --read-pages
```

Give each command on one line: a space after a `\` continuation broke a run on 2026-09-14. **The
provider key never goes on the command line**, where it lands in the shell history: source the untracked
key file into the shell first, as docs/runbooks/ai-provider-keys.md says. (The extract commands above
still show the older inline form; follow the key runbook for them too.)

**What it sends.** Each page's bands — the images the transcriber read — and never the text layer: the
transcriber was told to trust the layer for characters, and a verifier handed the same authority would
repeat its misreadings. With the bands, every paragraph's part printed on that page as a numbered item;
a paragraph that straddles a page break is judged on each page for its own part, marked as beginning
on the previous page or running on to the next.

**It refuses before any call** when the second read would not be the ruling's: on the fake client (no
`AI_LIVE=1`), on a verify tier that is not `margai.pipeline.verify-model` — `claude-sonnet-5`, which only
the `visionsonnet` profile sets — when the `ai_calls` ledger says the verifying model transcribed the
rows, and when the ledger cannot name a row's transcriber at all (run it against the database the
extraction ran against). The report's first line of the read says both models: `verifier: claude-sonnet-5 (prompt
ncert_verify.v1); transcribed by: claude-opus-5 (102 rows)`.

**It resumes.** Every page read is a line of `verify/{book}/{lang}.jsonl` in the content bucket, holding
the model's answer as given. A page is read again only when the text of its paragraphs changed since
(a correction, a reload), when the prompt version changed, when the read was made by any model but the
pinned verifier, or with `--redo`. `--pages 4,6` reads only those pages of each selected chapter; both
options belong to `--read-pages` and are refused without it. Code's judgements and the founder's rulings
are applied when the report is written, never stored in the artefact, and **every run — the free one
too — re-judges the rows from the artefact and writes the verdicts onto them**, so a `misprint` or
`false_positive` ruling takes effect on the next run at ₹0. A `text`, `join` or `split` correction is
different: it changes the words of that page's paragraphs (and a join or split renumbers the rest of
its section), so after the re-load those rows have no current read, the free run reports them "without
a verdict", and `--read-pages` reads just those pages again (≈ ₹1 each) — the rest resume.

**Read the report in this order:**

1. **`verifier: … transcribed by: …`** — two different models, or stop.
2. **`the second read's flags — adjudicate these against the page`** — one line per difference: the
   address, the page, the span as printed, the span as the row carries it. Render the page (pymupdf
   from the scratchpad) and look. Each real one becomes a `text` entry; a verifier error becomes a
   `false_positive`; the book's own error, kept, becomes a `misprint`.
3. **`rows the verifier could not find on their page`** and **`running text the page prints that no
   row carries`** — the second is the one check that sees lost text (run 9's three equations on page
   5); a real one is re-extracted or, for a sentence, joined in with a correction.
4. **The free checks**: `where rows start against where the print starts paragraphs` (per page, the
   rows that begin there against the indents, headings, labels and item markers the print begins
   there), `joins across page breaks against the print`, `figure_refs against the paragraph and the
   chapter's captions`. These are measured guesses about typography — they route attention and never
   refuse. A real segmentation defect becomes a `join` or `split` entry.
5. **`set aside by code`** — spans that differ only in spacing or a glyph variant (≅ ≃ ≈, the dashes,
   quotation marks, × and ·), or not at all (the verifier listing a span it checked — seen on the first
   calibration pages), which leave the row matching, and spans the verifier quoted that the row
   does not carry, which leave the row **not judged** — a claim nobody can place is not a match. Skim
   them: a long list of the second kind means the verifier is misquoting, which is a prompt problem.
6. **`clean paragraphs`** — per chapter: rows, verdicts, matches, differs, not on page, not judged,
   join or figure flags, and the clean share: the second read matches and no join or figure flag names
   the row. The line under it, `clean for the book (PLAN D15 ✅)`, appears when every chapter of the
   book was selected, every chapter has loaded rows and every row has a verdict. **Two signals name no
   row and are not in the share** — page-level start flags and passages no row carries — and the line
   after it counts them: adjudicate both before recording the number, which is the one from the run
   after adjudication.
7. **The cost line**, from the ledger.

**Cost** at the config's Sonnet 5 price row, estimated before the first run: about ₹1.05 per page
(two bands ~3,250 input tokens, the page's paragraphs ~800, a ~4,400-token cached prefix at a tenth,
~300 output tokens): chapter 7's 12 pages ≈ ₹14, `phy11-part1` ≈ ₹190, the ten English books ≈
₹2,000. The first chapter's report is where to check it.

### The verifier's chapter-7 calibration, before any book

Chapter 7 in `margai_d15` holds Opus run 11's 102 rows. It is the calibration set because every row of
it was read against the rendered pages on 2026-09-14 — so a flag can be scored as real or not without
reading cold.

```
# 1. reload chapter 7 so its rows carry pageStarts (no model, no cost; 102 updated, 0 inserted)
AWS_PROFILE=margai DB_URL=jdbc:postgresql://localhost:5432/margai_d15 java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline ncert load --book phy11-part1 --lang en --chapters 7

# 2. the free checks (no model, no cost)
AWS_PROFILE=margai DB_URL=jdbc:postgresql://localhost:5432/margai_d15 java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline ncert verify --book phy11-part1 --lang en --chapters 7

# 3. the second read (~₹14; the provider key sourced into the shell first, never typed here)
AI_LIVE=1 AWS_PROFILE=margai DB_URL=jdbc:postgresql://localhost:5432/margai_d15 java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live,visionsonnet ncert verify --book phy11-part1 --lang en --chapters 7 --read-pages
```

**The pass mark, set before the run (founder, D15 plan question 5):** the vector r in §7.3 ¶5 is flagged
(printed `|r|^3 r`, transcribed `|r|^3 r_hat`), and **no more than 10 flags on the 102 rows turn out to
be wrong against the page**. Every flag is read against the rendered page and scored; the false-positive
count and each miss go into the TRACKER day log. If the vector r is missed, one call per page is the
wrong unit and the fallback is one paragraph per call (about 3.5× the cost) — a build change, decided
then, not a prompt rule. **The prompt does not describe chapter 7**: its examples are invented and a
test keeps the must-find's text out. It does teach the must-find's *class* by name — a hat written by
analogy where the page prints a bold vector — so finding the vector r shows a taught class found in
unseen text, not the verifier's reach into classes nobody named; the calibration's other flags and its
misses say more about that. The notation block copied from the frozen extraction prompt quotes a few
chapter-7 symbols (`F'_GB`, `g(h) ≅`, `F_GA`) as conventions both readers were given; a find on exactly
those counts for less. **Bracket flags** are raised only where a bracket changes what a sum, an exponent
or a function covers; the grouping of products and quotients (`G Mm / d^2 L` for G(Mm/d²)L) is left to
the transcription's conventions and not flagged (DECISIONS 2026-09-14), so it is not scored.

A ₹0 preview of the free checks on these rows and the real `keph107.pdf`, run before the build was
committed, raised 7 page-level start flags and 1 figure flag and no join flag; two of them look like
real defects of run 11 (§7.9 ¶4 carries "where we have used the relation…", which page 11 indents after
a display; `Fig. 7.5` sits on Example 7.2's stem rather than on its part (b)). The calibration reads all
of them against the pages.

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

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

If a render ever logs `Cannot read JPEG2000 image`, every page it wrote is suspect and must be
re-rendered with `--redo`. The renderer now refuses to start at all when that decoder is missing,
so this cannot recur silently.

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

Estimated cost at `claude-haiku-4-5` rates: about ₹0.55–1.10 per page, so roughly ₹250 for `bio11`
and ₹170 for `phy11-part1`. The report's cost line is the truth — it comes from the `ai_calls`
ledger, not from an estimate.

Resumable: pages already in `extract/{book}/{lang}.jsonl` are not called for again, so an
interrupted run costs nothing to finish and a re-run costs nothing at all. `--redo` deliberately
pays again for pages already done.

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
3. **The flags are right.** `has_equations` true exactly when there is a mathematical or chemical
   expression; `figure_refs` matches the figures the paragraph actually names.

Record the twenty rows and the verdict in the TRACKER day log. A failure in (1) is serious and
means the prompt or the render DPI needs work before D15; a failure in (2) is the anchor being
wrong, which is what the whole addressing scheme exists to prevent.

Also read the extract report's low-confidence list: those pages are the model telling you where to
look, and they should be checked whether or not the random sample lands on them.

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

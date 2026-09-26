# NCERT ingest — register, render, extract, load, verify, embed

The six `ncert` commands of TECH_PLAN §6.3, in the order they must run, and the spot-check that
closes PLAN D14's ✅. D14 does the two English pilot books; D15 repeats it for the remaining eight,
with the second read (`ncert verify`, D15) before a book is taken as canonical; D16 for the Hindi
editions.

*Order amended 2026-09-19/20 (founder; DECISIONS, PLAN D15): the **first verified book is embedded
and its retrieval tested before the other nine are extracted**, so `ncert embed` (§7 below) runs on
`phy11-part1` now rather than at D17. The embedding pin moves provider, model and width together,
so a wrong pin re-embeds the corpus — cheap across 894 paragraphs, expensive across ~9,000.*

Five of the six need credentials **Claude does not have** — the AWS profile for the content bucket
and the live provider keys — so `render`, `extract`, `load`, `verify` and `embed` are founder-run,
as the D5 live smoke was. `register` is database-only and Claude runs it.

## Before you start

| Needs | Why |
|---|---|
| `AWS_PROFILE=margai` | the content bucket (`margai-beta-content`) for `render`, `extract`, `load` — and since 2026-09-20 the **Bedrock embedding calls** for `embed` too, which is the one command that needs it without touching S3 |
| `MARGAI_AI_ANTHROPIC_API_KEY` | the VISION calls, for `extract` and `verify --read-pages` only (docs/runbooks/ai-provider-keys.md) |
| ~~`MARGAI_AI_COHERE_API_KEY`~~ | **no longer needed** (2026-09-20, DECISIONS): embeddings moved to Bedrock, which authenticates by IAM. `ncert embed` needs `AWS_PROFILE=margai` instead, and there is no second provider key to hold or rotate |
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

**Before a book is rendered for the first time, run the free pre-flight.** With the PDFs on the
machine it draws every page of the chapters `books.yaml` names — the same files `render` will
render — at 72 DPI, and fails on any decoder gap. It costs nothing but time:

```
cd server
./mvnw test -Dtest=PdfPageRendererTest#everyPageOfEverySelectedBookRendersWithNoMissingDecoder \
  -Dncert.preflight=bio11
```

`-Dncert.preflight` takes `all` (every book of `books.yaml`) or a comma-separated list of book
codes; **left off it sweeps the two pilot books**, which is what `./mvnw verify` pays on every
commit — about 80 seconds for 395 pages. All ten is about 7 minutes for 1,690, so it is run
deliberately before a render rather than on every commit. A code `books.yaml` does not carry fails
the run instead of quietly sweeping nothing.

The test prints a page count per book. Check it against `ncert/2022-ed/en/manifest.md` before
rendering — 143 pages for `phy11-part1`, 252 for `bio11` — and note that these are chapter pages
only: prelims, answers and appendices are deliberately outside the sweep because nothing renders
them.

**Run all ten 2026-09-20 (D15): 79 chapters, 1,690 pages, every page drawn, no decoder gap.** So
the eight books never yet rendered carry no JPEG2000/JBIG2 surprise, and the guard has to be
re-run only when the PDFs or the decoder dependencies change.

## 3. extract — page images through the VISION tier

**This is the command that spends.** One model call per page.

```
AI_LIVE=1 AWS_PROFILE=margai MARGAI_AI_ANTHROPIC_API_KEY=… DB_URL=… \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live,visionopus \
  ncert extract --book bio11 --lang en
```

**`visionopus` is not optional.** Opus 5 transcribes by the pair ruling of 2026-09-14 (DECISIONS),
and that profile is the only thing that selects it: the VISION tier's default in `application.yml`
is `claude-haiku-4-5`, which the D15 dry runs put out of this job after three runs and three
different layout failures on the two-column page. Unlike `ncert verify --read-pages`, which refuses
to start unless the tier is `margai.pipeline.verify-model`, **`ncert extract` has no transcriber
guard** — leave the profile off and the run spends a book's budget on the model that was rejected,
and says so nowhere but the ledger. Until that guard exists, the startup log is the check: the
`AiClient chain` must end in `anthropic` rather than `fake`, and the VISION tier must read
`claude-opus-5`.

Start with one chapter (`--chapters 1`) and read its report before letting the book run: the cost
line and the low-confidence list are both in it, and a prompt that is reading the pages wrongly is
cheapest to catch after twenty pages rather than after two hundred.

**Prompt v5 (2026-09-26) is the active version** and differs from v4 in one rule: an activity or a
question set in italics in the body of the page, with no box around it, is running text. After
each book's load, sweep its italic runs against the rows (every run over 25 characters that no row
carries — 60 missed bio11 ch 11 p12's one-line question — less contents lists, captions and overprinted headwords) and `--redo --pages` any page it
names (DECISIONS 2026-09-26).

**Prompt v4 (2026-09-23) is the active version** and differs from v3 in one rule: a unit opener —
the page with the unit number set large above a drawing and a few paragraphs of framing prose,
facing the unit's biography — carries no running text and returns no paragraphs. It is what the
eight remaining books run behind; `phy11-part1` and `bio11` are canonical on v3 and are not
re-read, because this command resumes on a page's presence in the JSONL and not on the prompt
version (prompt-changelog 2026-09-23).

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

# 1. extract one chapter (visionopus, or the run silently reads on Haiku — see §3)
AI_LIVE=1 AWS_PROFILE=margai MARGAI_AI_ANTHROPIC_API_KEY=… DB_URL=… \
  java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live,visionopus \
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
every page after that heading's page is recorded as skipped and costs nothing. This is not a
politeness — NCERT numbers its exercises with the chapter number (Chapter 7's questions are 7.1,
7.2, 7.3), so a page of them is indistinguishable from a page of sections, and asking the model to
ignore them did not work.

**The heading's own page is sent when anything is taught above the heading** (2026-09-24). Until then
it never was, and whatever was printed above the Summary went with it — prose in 14 of bio11's 19
chapters and 5 of phy11-part1's 7, including a named subsection of §14.6 and chapter 7's Example 7.8
— with no metric able to see it. Where the heading sits is read from the page's glyph positions, not
the text layer's line order, which on bio11 ch 14 p11 puts the heading first. Across all ten books,
53 of the 78 heading pages are sent. The prompt's own rule skips the Summary on the page.

The report's **apparatus table** says, per chapter, where the boundary fell, which heading found it,
and what happened to its page (`sent: N prose line(s) above the heading`, `not sent: nothing taught
above the heading`, or `sent: the heading could not be placed on it`). **Read it.** A boundary that
looks too early means real teaching is being skipped; `—  not found: every page is sent` means the
text layer was unreadable and nothing was skipped, which is safe but means the model will see the
exercises for that chapter. The **pages the Summary starts on** section lists each heading page called,
with its paragraph count: read each against the rendered page — the rows should carry what is above
the heading and nothing below it. The coverage ratio cannot judge these pages, because the layer
carries the Summary and the rows must not. A resume picks these pages up by itself: a page an earlier
run recorded as apparatus and this run sends is read, so `ncert extract --book B --lang en` with no
`--redo` re-extracts exactly the heading pages the old rule discarded, in one run.

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

**A chapter already adjudicated stays as it was extracted.** The corrections in
`ncert-corrections.yaml` are written against one run's exact words — a span that must occur once on
its page — so re-extracting a chapter that has them invalidates them by name. Run the book's own
extraction without `--redo`: it resumes over that chapter's pages, keeps its lines of the JSONL, and
pays nothing for them. Copy the JSONL aside first (`aws s3 cp` to a dated key) rather than moving it:
a move is what makes the run pay for the chapter again. This is how `phy11-part1` chapter 7 keeps
Opus run 11 through the book's corpus event (founder's ruling, 2026-09-16).

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
before it numbers: the founder's rulings on what `ncert verify` flagged. `text`, `join`, `split`,
`figure_ref` (one label removed from or added to the paragraph holding a span) and `drop` (a paragraph
that is not running text removed) change the frozen run's pages; `misprint`, `false_positive` and
`noise` rule on a flag and change nothing, and `ncert verify` reads those three itself. The first two
answer the second read, matched on the spans the verifier quoted; `noise` answers one of the **free**
checks, which quotes no span, so it names the check (`flag: join` or `flag: figure`) and the words the
paragraph starts with — and only those two checks, because they are the two that enter the clean share
(D15, 2026-09-19). Each applied entry is one line of the report; an entry whose span is not on its page exactly
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
different: it changes the words of that page's paragraphs, so after the re-load those rows have no
current read, the free run reports them "without a verdict", and `--read-pages` reads just those pages
again (≈ ₹1 each) — the rest resume. **Renumbering is not a change of words.** A join or split moves
every later row of its section down a number, and a read is matched to the page's words, never to the
address a row had when the page was read (founder's ruling, 2026-09-16): a read made of exactly the
page's parts is mapped to them in order, so those rows keep their verdicts at their new addresses and
the page is not read again. On a page a correction did change, every part whose words appear exactly
once in the read keeps its verdict — this is what leaves only the rewritten paragraphs to pay for —
and a part the read cannot be matched to unambiguously is left without one, to be read again.

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
   chapter's captions`, and `numbered equations the print carries that the rows do not`. These are
   measured guesses about typography — they route attention and never refuse. A real segmentation
   defect becomes a `join` or `split` entry; a join or figure flag read against its page and found
   wrong becomes a `noise` entry, which is what takes the row off the clean share's wrong side, and the
   next run lists it under `free-check flags set aside by the founder's rulings` with its reason. **Do
   not read the start check's silence as a clean page**: on `phy11-part1` it named three of the four
   paragraphs a row had swallowed and missed the fourth outright (ch 6 p17, 2026-09-19), because a line
   the layer strips of its subscripts could not be seen as prose at all. That hole is closed, but the
   check remains a floor on segmentation defects, never a ceiling. The equation check is the one aimed at the second read's
   blind spot: a dropped displayed equation takes its number with it, and the number is the part of it
   the text layer keeps, so the page's own `(7.n)` labels are counted against the numbers its rows
   carry — both the label beside an equation and every sentence referring to it. It speaks only where
   the print carries a number more often than the rows, since a number the layer itself loses (chapter
   7's own `(7.16)`) says nothing about a row. On chapter 7 as corrected it is silent; on the seeded
   copy it names the dropped `(7.35)` and the altered `(7.12)` (founder's ruling, 2026-09-16). The
   section under it, `printed starts paired with a row only after allowing for math the layer dropped`,
   names every pairing the start check needed that allowance for, and the summary table counts them per
   chapter under `starts paired`: each one quieted a page, so read them against the page when a page looks
   too clean, and watch the count rather than the list on a whole book.
5. **`set aside by code`** — spans that differ only in spacing or a glyph variant (≅ ≃ ≈, the dashes,
   quotation marks, × and ·), or not at all (the verifier listing a span it checked — seen on the first
   calibration pages), which leave the row matching, and spans the verifier quoted that the row
   does not carry, which leave the row **not judged** — a claim nobody can place is not a match. Skim
   them: a long list of the second kind means the verifier is misquoting, which is a prompt problem.
6. **`clean paragraphs`** — per chapter: rows, verdicts, matches, differs, not on page, not judged,
   join or figure flags, and the clean share: the second read matches and no join or figure flag names
   the row. The line under it, `clean for the book (PLAN D15 ✅)`, appears when every chapter of the
   book was selected, every chapter has loaded rows and every row has a verdict. **Three signals name
   no row and are not in the share** — page-level start flags, numbered equations the print carries
   that the rows do not, and passages no row carries — and the line after it counts them: adjudicate
   all three before recording the number, which is the one from the run after adjudication.
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

**Result, 2026-09-15 (TRACKER day log):** the pass mark set before the run — the vector r in §7.3 ¶5
flagged, no more than 10 wrong flags — is **void**: page 4 prints r̂ in all three forms of Eq. (7.5), so
the must-find was the book's own print and the verifier's `matches` was right (DECISIONS 2026-09-15). The
second read's 8 signals were all wrong against the page; the free checks found 5 real defects among ~10
noise items. Chapter 7 as transcribed has no character defect left, so it can measure false flags but not
recall — which is what the seeded run below is for.

**What the second read does not see — aim the spot reads here (measured 2026-09-15, two draws each on seeded
defects):** a **dropped prime** (F'_GB written F_GB), an **approximation sign read as =** (the verifier
quotes the page's ≅ as =), and a **dropped displayed equation**. It found, twice where repeated, a changed
word, digit or exponent, an added hat, a subscript's case, a lost leading minus, a lost bracket around a
sum, a changed equation number and a dropped sentence. The free checks cannot see the first two either —
the symbol fonts leave primes and ≅ unmapped in the text layer — so a book is not clean of them until a
person has read its primes and approximation signs against the page. **The third is now code's** (ruling,
2026-09-16): a numbered displayed equation leaves its number in the layer, and the equation check holds
the page's numbers to its rows, which catches the seeded drop the paid read missed twice. An unnumbered
display is still nobody's.

### The seeded recall run

A scratch copy of the database, `margai_d15_seeded`, whose chapter-7 rows carry a dozen injected defects
listed in the TRACKER day log, is read with its own artefact tag so the real `verify/phy11-part1/en.jsonl`
is never touched:

```
AI_LIVE=1 AWS_PROFILE=margai DB_URL=jdbc:postgresql://localhost:5432/margai_d15_seeded java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live,visionsonnet ncert verify --book phy11-part1 --lang en --chapters 7 --read-pages --artefact-tag seeded
```

Score it against the seed list: each seeded defect found (a flag naming it) or missed, and every other
flag against the page. The code set-asides added for the seeded run — `√x` / `sqrt(x)`, a single-token
exponent `^-n` / `^(-n)`, a trailing full stop — are seeded against on purpose: none of the seeds is of
those classes.

**The prompt does not describe chapter 7**: its examples are invented and a test keeps the calibration's
text out. It does teach the classes that matter by name — a hat written by analogy where the page prints a
bold vector, a prime, a lost minus — so a seeded defect of a taught class found shows the verifier doing
what it was taught on unseen text. The notation block copied from the frozen extraction prompt quotes a
few chapter-7 symbols (`F'_GB`, `g(h) ≅`, `F_GA`) as conventions both readers were given. **Bracket flags**
are raised only where a bracket changes what a sum, an exponent or a function covers; the grouping of
products and quotients is left to the transcription's conventions and not flagged (DECISIONS 2026-09-14).

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

## 7. embed — a vector per paragraph, and the scored concept queries

Runs after a book is canonical (verified, loaded, its clean share read). It embeds `text_en` and
only that (§6.4): the pinned multilingual model is what is meant to carry a Hindi question to an
English paragraph, and whether it does is what this run measures.

```
DB_URL=jdbc:postgresql://localhost:5432/margai_d15 \
AWS_PROFILE=margai AI_LIVE=1 \
java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline,live \
  ncert embed --book phy11-part1
```

Since 2026-09-20 the embedding provider is **Bedrock** (`global.cohere.embed-v4:0`, an inference
profile — v4 has no on-demand throughput), so this needs `AWS_PROFILE=margai` and **no embedding
key at all**: IAM is the auth. `MARGAI_AI_ANTHROPIC_API_KEY` is still required because the `live`
profile wires the completion provider whatever command you are running.

It **resumes**, because a paragraph waits for a vector exactly when its `embedding` is null, which
is also how `ncert load` expresses staleness. So a second run over an embedded book calls for
nothing and costs nothing, and a run interrupted at paragraph 800 keeps its 800. Re-running is
therefore the cheap way to re-score after a retrieval-parameter change: it embeds nothing and pays
only for the query embeddings.

Then it runs `eval/retrieval-queries.json` through the hybrid retriever and prints, per query, the
top three passages, and over the set **hit@1, hit@3 and MRR — with Hindi scored on its own line.**
Read the Hindi line first: it is the cross-lingual pin, and §4.9's founder rider of 2026-09-12 says
a swap happens while it is still free, which is now and not after nine more books.

Cost for phy11-part1's 894 paragraphs: **well under ₹5** at the pinned model's 0.12 USD/1M tokens.

`--chapters 7` embeds one chapter, for staging a book the way `extract` is staged.
`--redo` re-embeds paragraphs that already have vectors. `--queries none` embeds without scoring —
needed for any book the committed query set is not written against.

### The D15 chunk experiment: `--context section`

`--context none` is the default and is §6.4 as written — the paragraph's own `text_en` and nothing
else. `--context section` embeds an experimental input instead:

- the section's printed title prefixed, from `pipeline/inputs/ncert-section-titles.yaml`
  (`5.3 Work · (iii) the force and displacement are mutually perpendicular…`), because `section`
  in the database is the bare number `5.3` and a number means nothing to an embedding model;
- paragraphs under `embed-min-characters` left unembedded — `Answer`, `No work is done if :`,
  `(ii) Normal reaction, N` can never be usefully retrieved and compete for a place in the top k.

Stored text, addresses and what a student is shown are untouched; only the embedded string changes.
Both force a corpus re-embed, which is why they are measured on one book before nine more exist.

To run the A/B against the committed baseline (8/15 · 10/15 · MRR 0.618):

```
DB_URL=… AI_LIVE=1 java -jar target/server-0.1.0-SNAPSHOT.jar \
  --spring.profiles.active=pipeline,live \
  ncert embed --book phy11-part1 --redo --context section
```

`--redo` is required: the existing vectors are the control, and without it nothing is re-embedded.
About ₹9 and ten minutes. **The bar, set in advance:** adopt if q07 moves from rank 11 into the top
three *and* nothing currently in the top three drops out. A one-hit swing on 15 queries is noise.
Reverting is the same command without `--context section`, for another ₹9.

## If something goes wrong

| Symptom | Cause | Fix |
|---|---|---|
| `input file not found: …/books.yaml` | run from the wrong directory | run from `server/`, or pass `--inputs` |
| `no concept queries at …` (from `embed`) | run from the wrong directory, so the default `../eval/retrieval-queries.json` did not resolve | run from `server/`, pass `--queries` with the right path, or `--queries none` to embed without scoring |
| `the AI client is the fake` (from `embed`) | `AI_LIVE=1` or the `live` profile missing | a fixture vector on a real row is invisible to every check, so the run refuses rather than writing one |
| `embedding is over the canonical English text` | `--lang hi` | there is no Hindi embedding pass (§6.4); drop the flag |
| the `live` profile will not start | `MARGAI_AI_COHERE_API_KEY` not set | it is a second key, distinct from the Anthropic one (docs/runbooks/ai-provider-keys.md) |
| `book '…' is not registered` | step 1 not run against this database | run `ncert register` |
| `chapter N of '…' has no rendered pages` | step 2 not run, or a chapter subset | run `ncert render` for that chapter |
| `no extraction at extract/…` | step 3 not run for this edition | run `ncert extract` |
| the report says `content store: in-memory` | no bucket configured | the `pipeline` profile sets it — check the profile is active |
| every page comes back with low confidence | the render DPI, or a bad scan | try `margai.pipeline.render-dpi=200` on one chapter |
| the run stops with a budget refusal | the ledger's daily cap | it is per IST day; either wait, or raise it deliberately |

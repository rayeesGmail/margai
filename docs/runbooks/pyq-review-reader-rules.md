# PYQ review — the rules every reader follows

The brief each reader of a PYQ review gets (runbook `pyq-ingest.md` §3; README §7 of the founder's pack). It gathers
the founder's rulings and Claude's approved readings on how a paper is read (DECISIONS 2026-10-20 to 2026-10-24), so
that every paper is read the same way. The crops a reader
works from are cut from the saved page images each extraction call read. They are the only source: never the PDF,
and never memory of the paper.

## What the transcription must be

1. **Exactly what is printed, in the prompt's notation** (`server/src/main/resources/prompts/pyq_extract.v1.stg`,
   the notation lines). Where the prompt and the NCERT corpus differ, use the corpus's form where it writes one, and
   the prompt's where it writes both (DECISIONS 2026-10-22):
   - A reaction's condition goes straight after its own arrow: `A -> (NaCN) B -> (H_3O^+) C`, never after the
     product.
   - **Exponents.** An expression's exponent of more than one character is bracketed, as the prompt says:
     `10^(-5)`, `e^(-E/kT)`, `(1.52)^(3/2)`, `sin^(-1)`. Two exceptions:
     - **A unit's power is never bracketed.** It stays as the prompt's units line writes it: `m s^-1`,
       `J mol^-1 K^-1`, `N m^-2`. The founder's words, 2026-10-24, are about negative powers: "Unit powers are not
       bracketed, powers of ten are, and expression exponents such as sin^(-1) stay bracketed" — `10^(-4)`, never
       `10^-4`.
     - **A positive whole-number power and a nuclide stay unbracketed**, as the corpus writes them: `10^11`,
       `2^10`, `^235_92U` (DECISIONS 2026-10-22). A fractional or compound positive exponent is still bracketed, so
       it reads as one: `(1.52)^(3/2)`.
   - A minus sign printed apart from its number is written against it: `-50 cm`, not `- 50 cm`.
   - A unit's μ is the character (μF, μC, μA). Other Greek letters go by name (alpha, lambda, nu, epsilon_0),
     including a glyph the page sets for a Greek letter (∈ for ε). A variable the stem defines keeps its printed
     glyph by name, whatever letter that is (ϑ as `theta`; the founder, 2026-10-24).
   - **Dimensions, units, quotes and grids** (the founder, 2026-10-24, approving 2021 M4's review):
     - A dimension's power is written as the corpus writes dimensional formulae, unbracketed: `[M L T^-2]`, `[T^-1]`.
     - A unit is written exactly as printed: "ms^-1" printed run together stays so, with no note. A space after a
       unit's exponent only ends the superscript (`JK^-1 mol^-1`; Claude's reading of the same rule, for the founder
       to overrule).
     - Quotes and apostrophes are straight (`'`, `"`).
     - An answer grid printed under (a) (b) (c) (d) is written as pairs: `(a)-(iv), (b)-(i), (c)-(iii), (d)-(ii)`.
2. **Figures** are described as drawn: what kind each is, and every label, value and unit printed in it.
   - Never describe what a figure implies, or name what the question asks the student to work out ("a D-shaped gate
     with an output bubble", not "a NAND gate", where the question turns on the gates).
   - Check every description against its drawing: each label, value, arrow direction, sign and polarity, and which
     item is where.
   - A misprint drawn in a figure, such as a wrong sign or a missing bracket in a formula, is described as drawn and
     reported like any misprint. The reviewer notes it on the figure: "a wrong formula in a figure teaches the wrong
     formula as much as one in an option" (the founder, 2026-10-24).
3. **Tables** are Markdown tables, row by row, as printed.

## How to read a damaged scan

- **Lost ink.** A 1-bit scan loses thin strokes: an e's bar, a t's or H's crossbar, a minus sign, a hyphen, a decimal
  point, part of a subscript. Such a place is read as meant (the gap rule). Say what the image shows and what is
  meant.
- **A wrong whole character is never evidence on its own.** A JBIG2 scan can substitute one glyph's shape for
  another's. Check the other language column, which prints the same numbers, formulae, units and list letters.
  A Latin glyph in the other column of the same page shares the page's symbol dictionary, so it settles a case only
  where a substitution could not have produced it: a glyph of clearly different size, or one drawn where the other
  could not sit.
- **A misprint is kept as printed.** This covers:
  - a missing, extra or misplaced letter, word or apostrophe;
  - an extra space or mark: "inner most" where NCERT writes "innermost", or a stray comma;
  - a word in the wrong number.

  It applies where the page shows no gap a lost letter would leave. Report it with what is printed and what is
  meant. A missing letter at the end of a line, where spacing cannot show a gap, is not settled this way.
- **Words printed run together with no spaces** are a typesetting fault, not content. This includes a single missing
  space between two words. The text shows them normally spaced, and the printed form is kept in a record-only note
  (the founder, 2026-10-24; SPEC §6.2). Report the run-together form. An abbreviation's full stop with no space
  after it ("E.coli") is not words run together: it is kept as printed, with no note (the founder, 2026-10-24).
- **A word NCERT itself spells so** is not a misprint. A word NCERT spells two ways is held to the right one. Report
  it with the NCERT spelling you know, and the reviewer checks the corpus.
- **Cause unsettled.** These are read as meant:
  - one letter for another, that the other column does not settle;
  - a missing letter or hyphen where spacing cannot show a gap, that the other column does not settle.

  If it touches the keyed answer, a number, a unit, a sign or a formula, flag it: the founder rules on those.

## How the reviewer classes what you report

Each misprint kept as printed has a display class (the founder, 2026-10-21):
- `show`, where it could change how a student reads the question: a wrong unit, symbol, number, sign or formula, or
  a misspelling that forms a different real word;
- `record_only`, for a spelling or grammar slip a student reads correctly.

You need not class them, but say what the slip could change.

## How to report

- Report every difference between the draft and the page, each with:
  - the draft's text;
  - what the page prints;
  - what the other column prints at that place.
- Report a draft that silently corrected a printed misprint too.
- Report nothing you have not seen on a crop.
- When unsure, say so, with what you see.

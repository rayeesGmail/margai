# pyq review — neet-2023-g6 (NEET (UG) 2023, booklet code G6)

This is the review README §7 asks for before the founder's approval: every flag of the extract run, and all 200 questions read against the booklet's page images, with the answers filled from NTA's final key.

- **Draft:** `pipeline/data/pyq/neet-2023-g6.draft.json`, from `pyq extract`. Its report, `2026-10-23-pyq-extract.md`, covers pages 3–47 (45 in all) at 200 dpi: 200 questions, every section at its declared count, 57 figure flags in 22 questions, no page below 0.80 confidence, ₹190.91.
- **Reviewed file:** `pipeline/data/pyq/neet-2023-g6.reviewed.json`. It enters `pipeline/inputs/papers/neet-2023-g6.json` only on the founder's approval.
- **Layout:** the reviewed file is written in the draft's own layout. PaperFileJson reads it and writes it back byte for byte (196,664 characters), and its checks against the register entry find no problem. So `diff` of draft against reviewed shows exactly the corrections, the 32 as-printed entries and the answers below.
- **The founder ruled on 2026-10-24 (DECISIONS):**
  - the cockroach spellings are kept as printed, "Collaterial" provisionally, with an F19 check;
  - Q31's ϑ is kept;
  - Q133's run-together option is shown spaced, with a `record_only` note of the printed form. That is now the rule for every paper, a single missing space included, so Q189's "gillslits" is shown spaced too (his answer of 2026-10-24; SPEC §6.2);
  - unit powers are not bracketed, powers of ten are, and an expression's exponent such as Q8's `sin^(-1)` stays bracketed;
  - the rest of the review is approved, Q170's T_H included.

## How it was read

- **On the saved page images, which are the bytes the calls read.** `pyq extract` kept each page's image with its call. Each local copy (`pipeline/data/pyq/pages/neet-2023-g6/`) was checked against the sha256 table of `2026-10-23-pyq-extract.md`, and 45 of 45 match. No reader opened the PDF.
- **The booklet is a scan, and the only copy.**
  - Pages 3–47 are each one 1-bit JBIG2 image of 1653 × 2336 pixels on a 595 × 841 pt page: a 200-dpi scan.
  - The run drew the pages at that dpi, so the saved images (1652 × 2336) are the scan's own pixel grid, and a zoom only enlarges. Since its guard was built on 2026-10-23, `pyq extract` refuses any run of this paper at another dpi or tiling (DECISIONS 2026-10-22).
  - The pack holds no second copy of G6, so NTA's Hindi column is the only other reading of each question.
- **Every question, character by character, against the English column.**
  - Five readers split the 45 pages (3–11, 12–20, 21–29, 30–38, 39–47), each on enlarged crops of both columns.
  - They filed every difference with its exact span, checked every figure description against its drawing and for an answer hint, and recorded each misprint with what the Hindi column prints at the same place.
  - Five more readers swept the same pages for strokes the scan lost (below), checking each place against the Hindi column.
- **Claude checked every filed difference on the page image before accepting it.** Nothing was changed on a reader's word alone.
  - Each page with a finding was read whole. Nearest-neighbour enlargements of the scan's own pixels settled Q21's cells, Q144's O against the page's digit 0, and Q170's subscript.
  - Every correction is held to the notation `pyq_extract.v1` writes and the NCERT corpus spells (DECISIONS 2026-10-22). Where the two differ, the corpus's form is used where it writes one (a reaction's condition goes straight after its arrow), and the prompt's where it writes both (a power of ten with a negative exponent is bracketed).
  - Unit powers stay unbracketed as the prompt writes units ("Units as printed: m s^-1"), as 2024 S6's and code 45's approved files keep them (Claude's reading, approved by the founder 2026-10-24).
  - A minus sign printed apart from its number is written against it ("-50 cm"), as 2024 S6's Q88 (DECISIONS 2026-10-24).
- **NCERT's own spellings are not misprints** (DECISIONS 2026-10-24). Two of the readers' misprints, and one place no reader filed, are NCERT's spellings, so they stand with no note:
  - Q110's "2, 4-D" (bio11 prints it so);
  - Q114's "Monoadelphous" (bio11's "monoadelphous");
  - Q200's "store house" (bio11 prints it so, twice).

  Q11's "Bracket series" is not one of them: chem11 ch 2 prints "Bracket" in its running text but "Brackett" in its Table 2.3, so it is kept as a misprint (below).

## Corrections: 33 in 19 questions

There are:
- 1 of meaning: Q170's T_H, which is the keyed option;
- 3 to figure descriptions: Q21, Q39 and Q98;
- 21 of notation, among them the spaced minus signs of Q45's and Q99's options, each question's keyed option 4 included;
- 7 cause unsettled (the section after the kept misprints);
- 1 of spacing: Q189's "gillslits" shown as "gill slits". Under the founder's ruling 3, which covers a single missing space (his answer, 2026-10-24).

| Q | page | field | kind | the draft had | the reviewed file reads (the page, in the corpus notation) | why |
|---|---|---|---|---|---|---|
| 21 | 6 | figure, stem | figure | a 10 V cell, a junction point E, a 5 V cell | a 10 V cell (short plate toward A, long plate toward E), a dot labelled E, a 5 V cell (long plate toward E, short plate toward B) | the description left out both cells' polarity, on which the current's direction turns; read pixel by pixel, both long plates face E (the Hindi column's copy is drawn the same), and E is a marked dot on the wire, not a junction; polarity described as drawn, not as which way the cells act |
| 27 | 7 | option 1 | notation | 10^-4 | 10^(-4) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 27 | 7 | option 2 | notation | 10^-4 | 10^(-4) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 27 | 7 | option 3 | notation | 10^-4 | 10^(-4) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 27 | 7 | option 4 | notation | 10^-4 | 10^(-4) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 37 | 9 | option 1 | notation | 10^-1 | 10^(-1) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 37 | 9 | option 2 | notation | 10^-4 | 10^(-4) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 37 | 9 | option 3 | notation | 10^-3 | 10^(-3) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 37 | 9 | option 4 | notation | 10^-2 | 10^(-2) | a negative power is bracketed, as the prompt writes it (DECISIONS 2026-10-22), as 2024 S6's Q43 and Q89 |
| 39 | 9 | figure, stem | figure | input A through a NOT gate and input B through a NOT gate, both outputs feed a NAND gate with output Y | input A through a triangle with an output bubble and input B through a triangle with an output bubble; both outputs feed a D-shaped gate with an output bubble, whose output is Y | the gates' names say what the question asks the student to work out from their symbols (the truth table follows from them), so what is drawn replaces them, as 2024 S6's Q11 (DECISIONS 2026-10-21) |
| 40 | 9 | stem | cause unsettled | Then those are connected | When those are connected | one letter for another (T for W) that the Hindi column cannot settle (it reads जब, 'when'); read as meant |
| 45 | 10 | option 1 | notation | - 50 cm | -50 cm | the page prints a minus sign; a dash and a space read as punctuation, so the sign is written against its number, as 2024 S6's Q88 (DECISIONS 2026-10-24) |
| 45 | 10 | option 3 | notation | - 40 cm | -40 cm | the page prints a minus sign; a dash and a space read as punctuation, so the sign is written against its number, as 2024 S6's Q88 (DECISIONS 2026-10-24); the sign alone tells this option from option 2 (40 cm) |
| 45 | 10 | option 4 | notation | - 100 cm | -100 cm | the page prints a minus sign; a dash and a space read as punctuation, so the sign is written against its number, as 2024 S6's Q88 (DECISIONS 2026-10-24) |
| 49 | 11 | stem | notation | (∈_0 = permittivity | (epsilon_0 = permittivity | the page sets epsilon with the ∈ glyph, as the stem's own definition shows; Greek letters by name, as the corpus writes epsilon_0 (105 rows) and 2024 S6's Q14 |
| 49 | 11 | stem | notation | (4 pi ∈_0) | (4 pi epsilon_0) | as above |
| 56 | 13 | stem | notation | [CaCO_3 -> CaO + CO_2 (1200 K)] | [CaCO_3 -> (1200 K) CaO + CO_2] | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28, Q86 and Q97 |
| 73 | 17 | stem | cause unsettled | lone pair of electrons | lone pairs of electrons | a missing final letter at the end of a line, where spacing cannot show a letter-width blank; the Hindi column's plural (युगलों) gives the meaning but cannot show what the English printed; read as meant, as Q161 |
| 85 | 21 | option 1 | notation | -> Product ((i) LiAlH_4 (ii) H_3O⊕) | -> ((i) LiAlH_4 (ii) H_3O⊕) Product | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28, Q86 and Q97 |
| 85 | 21 | option 2 | notation | -> Product (Br_2 / KOH) | -> (Br_2 / KOH) Product | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28, Q86 and Q97 |
| 85 | 21 | option 3 | notation | -> Product ((i) LiAlH_4 (ii) H_3O⊕) | -> ((i) LiAlH_4 (ii) H_3O⊕) Product | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28, Q86 and Q97 |
| 85 | 21 | option 4 | notation | -> Product ((i) LiAlH_4 (ii) H_3O⊕) | -> ((i) LiAlH_4 (ii) H_3O⊕) Product | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28, Q86 and Q97 |
| 98 | 25 | figure, stem | figure | on the sp3 carbon | on the bottom carbon | the drawing shows a lone pair and ⊖ below the ring's bottom vertex and no hybridisation; a hybridisation is what a Hückel's-rule count turns on (and sp3 is wrong for the aromatic anion) |
| 99 | 25 | option 1 | notation | – 13.73 cal | -13.73 cal | the page prints a minus sign; a dash and a space read as punctuation, so the sign is written against its number, as 2024 S6's Q88 (DECISIONS 2026-10-24) |
| 99 | 25 | option 3 | notation | – 137.26 cal | -137.26 cal | the page prints a minus sign; a dash and a space read as punctuation, so the sign is written against its number, as 2024 S6's Q88 (DECISIONS 2026-10-24) |
| 99 | 25 | option 4 | notation | – 1381.80 cal | -1381.80 cal | the page prints a minus sign; a dash and a space read as punctuation, so the sign is written against its number, as 2024 S6's Q88 (DECISIONS 2026-10-24) |
| 108 | 27 | option 3 | cause unsettled | Over exploitation | Over-exploitation | a hyphen is a thin stroke this scan can lose, and a lost one leaves a blank no wider than the word space the page shows; the Hindi column translates the word (अतिदोहन); read as meant, NCERT's "Over-exploitation" |
| 161 | 38 | stem | cause unsettled | options give below | options given below | a missing letter at the end of a line, where spacing cannot show a letter-width blank, and the Hindi column has no word for 'given'; read as meant, as 2024 S6's Q143 |
| 164 | 38 | stem | cause unsettled | Mycorrtizae | Mycorrhizae | one letter for another (t for h), which the Hindi column's माइकोराइजी cannot settle; read as meant, NCERT's spelling |
| 170 | 40 | option 2 | **meaning** | T_II cells | T_H cells | the subscript is an H whose crossbar the scan lost (read pixel by pixel; the Hindi column keeps a stub of the bar), as it prints KOH, OH and COOH as KOII, OII and COOII on pages 12–24; 'T_II' is no cell type, and NCERT's helper T-lymphocytes are T_H |
| 187 | 44 | stem | cause unsettled | and post reproductive age groups | and post-reproductive age groups | a hyphen the scan can lose, as Q108, in the line that prints "pre-reproductive" hyphenated; read as meant |
| 189 | 45 | stem | spacing | gillslits | gill slits | words printed run together are a typesetting fault, shown normally spaced with the printed form kept in a record_only note (the founder's ruling 3 on G6, 2026-10-24), a single missing space included (his answer, same day; SPEC §6.2) |
| 199 | 47 | stem | cause unsettled | options give below | options given below | a missing letter at the end of a line, as Q161; read as meant |

## Checked against the saved page images

- **The figure flags: 57 descriptions in 22 questions.** 54 stand as the extraction wrote them. 3 are corrected above:
  - **Q21:** the description left out both cells' polarity, which the current's direction turns on. Both long plates face E, in both columns.
  - **Q39:** the description named the gates.
  - **Q98:** the description stated a hybridisation the drawing does not show.

  The readers checked all 57 against their drawings. Claude also read the drawings of every figure question but Q3 and Q30 on the page.
- **Names in the descriptions,** by the 2026-10-21 rule (DECISIONS; Claude's, for the founder to overrule).
  - **Replaced by what is drawn:** Q39's gate names, because the truth table follows from them, as 2024 S6's Q11. Also Q98's "sp3", which is what a Hückel's-rule count turns on.
  - **Kept, as descriptions that name no answer:** each was checked against its drawing.
    - Q52's benzenediazonium chloride, 4-bromophenol and phenol;
    - Q57's cyclohexanone;
    - Q75's acetyl groups and Q93's acetyl and formyl groups, the reactants' groups as drawn;
    - Q87's benzyl phenyl ether;
    - Q94's bromobenzene, ethylbenzene and biphenyl;
    - Q98's species names (naphthalene, cyclopentadienyl, cyclobutadiene, cyclopropene, cyclooctatetraene, anthracene). None of these says which option is the product or which species obeys Hückel's rule, as 2024 S6's Q86.
  - **Rechecked before D31:** every description is checked again for an answer hint before it serves as alt text at D31. So are the extraction's flags kept with each question in `questions.extraction`, which keep the draft's words as provenance (Q39's gate names, Q98's "sp3").

## The booklet's own errors: 32 as-printed entries

The transcription copies a printed misprint, except words printed run together (below), which are shown spaced. So 30 of the 32 entries are kept as printed in the text. Each one is recorded as an `as_printed` entry of its question. The entry holds the span as printed, the intended reading, a one-line note that quotes no more than those, and its display class (the founder, 2026-10-21):
- `show`, for a misprint that could change how a student reads the question;
- `record_only`, for a spelling or grammar slip a student reads correctly.

**Words printed run together (Q133, Q189).** These are a typesetting fault, not content (the founder's ruling 3, 2026-10-24). The text shows them normally spaced, and the `record_only` entry keeps the printed form, which the text no longer carries. Q133 option 1's whole line and Q189's "gillslits", a single missing space, are the two in this paper (the founder, 2026-10-24; SPEC §6.2). `PaperFileJson` accepts such an entry only as `record_only`, and only where its field prints the intended reading.

**Why the others count as printed.** A lossy 1-bit scan proves no misprint by a wrong whole character alone (the founder, 2026-10-21). So each other entry below is one of two kinds:
- A missing, extra or misplaced letter, word or apostrophe, an extra space, or a word in the wrong number. (A missing space between words falls under ruling 3, the founder, 2026-10-24.) Taking this kind as printed is Claude's reading of the founder's rule, which he approved (DECISIONS 2026-10-22): a JBIG2 encoder substitutes one glyph's bitmap for another of like shape, and does not add, drop or move a glyph. Q74's and Q172's stray commas are extra marks, read by the same rule (Claude's, approved by the founder 2026-10-24).
- A wrong character that NTA's Hindi column prints the same:
  - Q106's 1-6;
  - Q113's 5 srRNA;
  - Q118's मैक्लिओइड और मैककार्थी;
  - Q144's capital O;
  - Q149's मेलोनेट.

**Latin glyphs in the Hindi column (Q106, Q144).** Both columns are one JBIG2 image, so for a Latin glyph the Hindi column shares the English column's symbol dictionary and is not wholly independent. Such a glyph settles a case only where a substitution could not have produced it (Claude's reading, approved by the founder 2026-10-24):
- **Q144's O.** An encoder matches only symbols of nearly the same size. The O is 20 pixels wide, as wide as the A beside it, and the page's digit 0 (Q146's options) is 12–13. A substituted 0 would therefore not be drawn as this O.
- **Q106's "1-6".** A substituted glyph is drawn where the original stood. The mark between 1 and 6 sits at hyphen height, level with the word's other two hyphens. A comma would sit on the baseline.

Q113's "5 srRNA" is a misplaced letter, which no substitution makes. Q118's and Q149's Hindi are Devanagari.

**No blank where a letter or word is missing.** Claude checked each missing letter on the image: the page shows no letter-width blank at Q11, Q66, Q119, Q122 or Q130. It shows no word-width blank at the missing words of Q44, Q155, Q177, Q178, Q181 and Q184. Q73's "pair" and Q161's and Q199's "give" each end their line, where spacing shows nothing, so they are read as meant instead (the next section).

**Four are `show`:**
- Q11's "Bracket series", a misspelling that forms a different real word;
- Q113's "5 srRNA", which garbles the Svedberg unit (S) of "5S rRNA";
- Q144's two "O(B)", where 0 (zero) is meant.

Their Hindi lines are written at the crop work before D31 (DECISIONS 2026-10-21).

| Q | at | printed | intended | the note | display |
|---|---|---|---|---|---|
| 11 | stem | Bracket series | Brackett series | Printed "Bracket series"; the Brackett series is meant. | show |
| 34 | option 2 | Huygen's principle | Huygens principle | Printed "Huygen's"; Huygens principle is meant. | record_only |
| 36 | stem | inner most | innermost | Printed "inner most"; innermost is meant. | record_only |
| 44 | option 4 | away from page | away from the page | Printed without "the"; "away from the page" is meant, as option 3. | record_only |
| 55 | stem | Reasons R | Reason R | Printed "Reasons R"; Reason R is meant. | record_only |
| 62 | stem | Reasons R | Reason R | Printed "Reasons R"; Reason R is meant. | record_only |
| 66 | stem | heterogenous | heterogeneous | Printed "heterogenous"; heterogeneous is meant. | record_only |
| 74 | stem | theory, regarded | theory regarded | Printed "theory, regarded"; "theory regarded" is meant. | record_only |
| 76 | stem | Reasons R | Reason R | Printed "Reasons R"; Reason R is meant. | record_only |
| 82 | stem | outer most | outermost | Printed "outer most"; outermost is meant. | record_only |
| 84 | stem | Reasons R | Reason R | Printed "Reasons R"; Reason R is meant. | record_only |
| 102 | option 1 | breakes | breaks | Printed "breakes"; breaks is meant. | record_only |
| 106 | stem | fructose-1-6-diphosphate | fructose-1,6-diphosphate | Printed "1-6"; fructose-1,6-diphosphate is meant. | record_only |
| 113 | option 3 | 5 srRNA | 5S rRNA | Printed "5 srRNA"; "5S rRNA" is meant. | show |
| 117 | stem | an absorption maxima | an absorption maximum | Printed "an absorption maxima"; "an absorption maximum" is meant. | record_only |
| 118 | option 4 | Macleoid and McCarthy | MacLeod and McCarty | Printed "Macleoid and McCarthy"; MacLeod and McCarty are meant. | record_only |
| 119 | stem | following phytohormone | following phytohormones | Printed "phytohormone"; phytohormones is meant. | record_only |
| 122 | stem | In angiosperm, | In angiosperms, | Printed "angiosperm"; angiosperms is meant. | record_only |
| 130 | stem | Detrivores | Detritivores | Printed "Detrivores"; detritivores is meant. | record_only |
| 133 | option 1 | morethantwogenesaffectingasinglecharacter. | more than two genes affecting a single character. | Printed with no spaces; "more than two genes affecting a single character." is meant. | record_only |
| 144 | stem | +(A), O(B) | +(A), 0(B) | Printed with the letter O; 0 (zero) is meant. | show |
| 144 | stem | -(A), O(B) | -(A), 0(B) | Printed with the letter O; 0 (zero) is meant. | show |
| 149 | stem | Melonate | Malonate | Printed "Melonate"; malonate is meant. | record_only |
| 155 | option 1 | Statement I incorrect | Statement I is incorrect | Printed "Statement I incorrect"; "Statement I is incorrect" is meant. | record_only |
| 172 | stem | haemoglobin, consists | haemoglobin consists | Printed "haemoglobin, consists"; "haemoglobin consists" is meant. | record_only |
| 177 | option 1 | Statement I incorrect | Statement I is incorrect | Printed "Statement I incorrect"; "Statement I is incorrect" is meant. | record_only |
| 178 | stem | Given below are statements | Given below are two statements | Printed "Given below are statements"; "Given below are two statements" is meant. | record_only |
| 181 | option 1 | Statement I incorrect | Statement I is incorrect | Printed "Statement I incorrect"; "Statement I is incorrect" is meant. | record_only |
| 184 | option 1 | Statement I false | Statement I is false | Printed "Statement I false"; "Statement I is false" is meant. | record_only |
| 186 | stem | parts of human brain that helps | parts of human brain that help | Printed "parts of human brain that helps"; "that help" is meant. | record_only |
| 187 | stem | pre-reproductives | pre-reproductive | Printed "pre-reproductives"; pre-reproductive is meant. | record_only |
| 189 | stem | gillslits | gill slits | Printed "gillslits"; gill slits is meant. | record_only |

## Cause unsettled — read as meant, no note (7 places in 7 questions)

A wrong or missing character that nothing settles is read as meant, with no as-printed note, and listed here. The scan cannot show whether the paper printed it, and no copy prints it differently in a way that decides. It goes to the founder only when the intended reading is in doubt, or when it touches the keyed answer, a number, a unit, a sign or a formula and the Hindi column, the second official copy and D20's solve-vs-key check together still cannot settle it (the founder, 2026-10-21). None of the seven does:

- **Q40: "Then those are connected …".** One letter for another, T for W. The Hindi column reads जब ("when"). Read as "When".
- **Q73: "lone pair of electrons".** The missing final letter ends its line, where spacing cannot show a letter-width blank. The Hindi column's plural gives the meaning but cannot show what the English printed. Read as "lone pairs".
- **Q108 option 3: "Over exploitation"; Q187 III: "post reproductive".** A hyphen is a thin stroke this scan can lose, and a lost one leaves a blank no wider than the word space each page shows. Q108's Hindi translates the word (अतिदोहन), and Q187's line prints "pre-reproductive" hyphenated. Read as "Over-exploitation" (NCERT's form) and "post-reproductive" (Claude's reading, approved by the founder 2026-10-24).
- **Q161 and Q199: "options give below".** The missing letter ends its line in both questions. Read as "given", as 2024 S6's Q143.
- **Q164: "Mycorrtizae".** One letter for another, t for h. The Hindi column's माइकोराइजी cannot settle it. Read as "Mycorrhizae", NCERT's spelling.

## Strokes the scan lost (transcribed as meant)

A gap is not a misprint: a character whose ink is missing is read as meant where the context fixes it (DECISIONS 2026-10-20 and 2026-10-21). This scan loses far fewer strokes than 2024 S6's (30 places against 195). Those it loses are H's crossbar (KOII, OII, COOII, T_II), A's (Λ), italic t's (ı), T's, the 1's flag, the bar of e (E_ccll, ethcr) and two faint decimal points.

All 45 pages were swept: **30 places in 16 questions. The extraction read 29 of them as meant and copied 1 literally, which is corrected above (Q170 option 2's T_II for T_H). None was unclear.**

- **Pages 30–38:** the sweep found no lost stroke. Its three wrong-letter places are printed letters, not gaps:
  - Q149's "Melonate", kept as printed;
  - Q161's "Urecose", the founder's ruling below;
  - Q164's "Mycorrtizae", cause unsettled.

  So they are not in the table.
- **Claude's own sightings:** of the places read as meant, Claude also saw Q37, Q42, Q57, Q75, Q76, Q85, Q87, Q93 and Q94 on the page.
- **The Hindi column:** where only the Hindi column lost a stroke, nothing is listed. That matters only to the parked Hindi pass.
- **"Transcribed as"** is the extraction's text.

| Q | page | field | the image shows | transcribed as | NTA's Hindi column |
|---|---|---|---|---|---|
| 8 | 4 | option 1 | sin^-1(10 t1 / ı2): the denominator's italic t has no crossbar and pr… | t_2 | the same gap: the denominator t_2 also prints like ı2 |
| 8 | 4 | option 2 | sin^-1(t2 / t1): the denominator's t keeps only a short stub of its c… | t_1 | t_1 with its crossbar |
| 8 | 4 | option 3 | sin^-1(10 t2 / t1): the denominator's t keeps only a short stub of it… | t_1 | t_1 with its crossbar |
| 8 | 4 | option 4 | sin^-1(t1 / 10 ı2): the denominator's italic t has no crossbar and pr… | t_2 | the same gap: 10 t_2 also prints like 10 ı2 |
| 37 | 9 | option 2 | 3×10^-4 °C^-I: the exponent's 1 prints as a heavy flagless bar, like… | 3 × 10^-4 °C^-1 | °C^-1 with a normal flagged 1 (the Hindi column's options 1… |
| 37 | 9 | option 4 | 3×10^-2 °C^-I: the exponent's 1 prints as a heavy flagless bar, like… | 3 × 10^-2 °C^-1 | °C^-1 with a normal flagged 1 |
| 39 | 9 | option 1 | truth table rows 0 l 0 and l l 1: the 1s in column B row 2 and column… | \| 0 \| 1 \| 0 \| and \| 1 \| 1 \| 1 \| | the same entries print as normal flagged 1s |
| 42 | 10 | figure:stem | horizontal axis label ı (s): the italic t has no crossbar | horizontal axis t (s) | the same gap: the axis label also prints ı (s) |
| 42 | 10 | stem | acceleration of the particle at ı = 2 s: the italic t has no crossbar | t = 2 s | t = 2 with the full crossbar |
| 44 | 10 | option 4 | mu_0 i/4R [l − 2/π]: the 1 inside the bracket has lost its flag and p… | mu_0 i/(4R) [1 - 2/pi] pointed away from page | [1 − 2/π] with a normal flagged 1 |
| 45 | 10 | figure:stem | n1 = 1 .5: the decimal point is a faint speck, much smaller than norm… | n_1 = 1.5 | 1.5 with a visible (small) decimal point |
| 45 | 10 | figure:stem | n2 = 1 .6: the decimal point is a faint speck, much smaller than norm… | n_2 = 1.6 | 1.6 with a visible decimal point |
| 52 | 12 | figure:option 1 | OI I above the ring (crossbar of H gone, two separate uprights) | Benzene ring with OH at top and Br at para position (4-bromophenol) | The same gap: OI I (crossbar of H gone in the Hindi column… |
| 52 | 12 | figure:stem | (ii) Mg/dry ether with the y of 'dry' broken in its descender and the… | (ii) Mg/dry ether | (ii) Mg/शुष्क ईथर, printed in full (a Hindi word, so not a… |
| 57 | 13 | figure:option 1 | COOI I (crossbar of the final H gone) | Cyclohexene ring with COOH on a carbon of the C=C double bond (ring d… | COOH, printed in full |
| 57 | 13 | figure:option 3 | COOI I (crossbar of the final H gone) | Cyclohexane ring with COOH substituent. | COOH, printed in full |
| 75 | 18 | figure:option 3 | upper label OI I (crossbar of H gone); the lower label on the benzene… | Cyclohexane ring with CH(OH)CH3 group (OH labelled) at one carbon and… | OH, printed in full (both labels) |
| 75 | 18 | figure:option 4 | upper label OI I above the CH2 (crossbar of H gone); CH2OH on the ben… | Cyclohexane ring with CH2 group bearing OH (drawn as OH \| CH2) at on… | OH printed in full (the Hindi CH2 under it has lost its H c… |
| 76 | 18 | stem | Reasons R : E_ccll is an intensive property (the bar of the first e o… | Reasons R : E_cell is an intensive property | E_सेल (Hindi subscript, so it does not settle the Latin gly… |
| 77 | 19 | figure:option 1 | horizontal-axis label prints as an upright with only the two serif ti… | graph: P on vertical axis, T on horizontal axis | The same gap: the axis label shows only the serif tips of t… |
| 77 | 19 | figure:option 3 | label of the steepest line prints I_3 (the T's crossbar mostly gone,… | three straight lines from the origin labelled T_3 (steepest), T_2, T_… | T_3, printed in full |
| 85 | 21 | option 1 | (i) LiΛlH4 over the arrow: the A has lost its crossbar and prints as… | (i) LiAlH_4 | (i) LiAlH4 with the A whole; only the H's crossbar is partl… |
| 85 | 21 | option 1 | (ii) II3O ⊕ under the arrow: the H has lost its crossbar and prints a… | (ii) H_3O⊕ | (ii) H3O ⊕, the H whole |
| 85 | 21 | option 2 | Br2 / KOII over the arrow: the H of KOH has lost its crossbar | (Br_2 / KOH) | Br2 / KOH: the H's crossbar is only a stub, but it is there |
| 85 | 21 | option 4 | (ii) II3O ⊕ under the arrow: the H has lost its crossbar | (ii) H_3O⊕ | (ii) H3O ⊕, the H whole. The Hindi option 4 has its own gap… |
| 87 | 22 | figure:option 1 | Λ= before the toluene ring: the A of the product label has lost its c… | A = benzene ring–CH_3 and B = benzene ring–I | Λ= : the same gap; the Hindi option 1 also prints Λ |
| 87 | 22 | figure:option 3 | Λ= before the benzyl-alcohol ring: the A has lost its crossbar | A = benzene ring–CH_2OH and B = benzene ring–I | Λ= : the same gap; the Hindi option 3 also prints Λ |
| 93 | 24 | figure:option 2 | OII at the end of the CH2 arm: the H of OH has lost its crossbar | benzene ring with ortho substituents: CH(OH)CH_3 (OH labelled) and CH… | OH, its crossbar only partly printed (a short stub) |
| 94 | 24 | figure:stem | Na/dry ethcr under the last arrow: the second e of ether has lost its… | (bromobenzene drawn as benzene ring with Br, Na/dry ether) | Na/शुष्क ईथर (dry ether in Devanagari, so no Latin letters… |
| 170 | 40 | option 2 | T with a subscript of two serifed vertical stems side by side, no cro… | T_II cells — copied as printed; corrected above | The left stem keeps a one-pixel stub of the crossbar at mid… |

## Close calls left as transcribed

- **Q31's ϑ.** Both columns print the script theta for the speed, in the stem and in all four options. It is kept as `theta`: the stem defines it and uses it consistently (the founder, 2026-10-24).
- **Q32.** Its options print no root signs in either column ("5 : 2", "3 : 5", …). The right ratio, √3 : √5, is in none of them, and NTA dropped the question.
- **Q60, "more than Cu^+ salts"; Q104, "ESTs refers to".** An elliptical comparison, and NCERT's term taken as a name, not misprints.
- **Open compounds** (Claude's reading, approved by the founder 2026-10-24). Q36's "inner most" and Q82's "outer most" split words NCERT writes closed (innermost and outermost, in 3 and 18 rows), so they are kept as misprints, as 2024 S6's "down stream" and "out door". A missing hyphen is a stroke the scan can lose, so Q108's and Q187's are cause unsettled (above). Q36's and Q82's gaps could be lost hyphens too, but NCERT writes both words closed, so a misprint stands either way; their notes, which are not displayed, quote the gap as a space. Q115's "sub stage" is left as printed: NCERT does not write the word, nothing gives a form to read it as, and it reads the same as "sub-stage".
- **Q79, "ohm^-1 cm^-1" and "60 ohm".** Printed in words. Q99 option 2's "1372.60 cal" has no minus in either column and no gap where one was lost.
- **Q58's "phosphorous acid", Q62's "high solubility in O_2", Q64 D's "lowest".** These are the questions' own statements to be judged, not misprints.

## The founder's ruling: two cockroach spellings — "Urecose gland" (Q161 List II, Q198 B) and "Collaterial glands" (Q198 E)

Both words name parts of the cockroach. The current NCERT books cut bio11's cockroach section, and the loaded corpus has neither spelling of either word.

- **"Urecose" (Q161, Q198).** Both questions print it in the English column. Q198's Hindi prints the same e (यूरेकोस); Q161's prints यूरीकोज. The usual spelling is "uricose".
- **"Collaterial" (Q198).** The usual spelling is "colleterial". The Hindi column translates the word (श्लेषक ग्रंथियां), so it cannot settle the spelling.

**Ruled: all three are kept as printed, with no note** (the founder, 2026-10-24; Claude had recommended it).
- **"Urecose"** is the earlier NCERT edition's own spelling ("fat body, nephrocytes and urecose glands also help in excretion"). The paper followed the book, and the earlier-edition content will show the same.
- **"Collaterial"** is less certain. The earlier edition's text says "collateral glands", and its figure of the female reproductive system probably labels them "Collaterial glands", which would be the paper's source. It stands provisionally.
- **An F19 check:** when the earlier-edition book arrives, both spellings are confirmed, in the cockroach excretion paragraph and the female reproductive system figure. If the book disagrees, only the note changes.

## Answers

- **The key file.** From NTA's final answer key for all codes (`2023/neet-2023-final-key-all-codes.pdf`, sha256 04ed02fd…). It is tier 3: not on NTA letterhead, a Word print from a third-party mirror (MANIFEST.csv; README §3).
- **The page.** Page 23 of the 48-page file, found by its header "BOOK CODE : G6". It carries "DATE OF EXAM - 07.05.2023" and the legend "5 DENOTES QUESTION IS DROPPED", "B DENOTES BOTH 1 & 3 ARE CORRECT", "E DENOTES BOTH 2 & 4 ARE CORRECT".
- **How it was read.** From the key's own text layer by a script, with no model. Checked for 200 answers, 1 to 200 once each.
- **Special entries.** Exactly the register's, as README §3 has them: Q32 is "5", dropped, and Q141 is "E", 2 and 4. Of the 198 single answers, keys 1, 2, 3 and 4 answer 32, 52, 51 and 63 questions.
- **Spot check.** Solved independently, each matching the key:

  | Q | working | key |
  |---|---|---|
  | Q1 | 2.20 eV exceeds only Cs's 2.14 eV work function | 2 |
  | Q2 | zero | 2 |
  | Q21 | the cells oppose, so 5 V drives 0.5 A through 10 Ω from A to B through E; this needs the polarity the description now gives | 3 |
  | Q30 | 3 μF in series with 6 μF gives 2 μF | 2 |
  | Q38 | X_L = 5 Ω, X_C = 10 Ω, Z = 5√5 Ω | 4 |
  | Q42 | a = −ω²x = −π²/16 m s⁻² | 1 |
  | Q49 | Kq(1/0.02 − 1/0.08) = (3/8) × 10² Kq | 2 |
  | Q52 | benzene | 3 |
  | Q53 | allylic | 4 |
  | Q57 | the α,β-unsaturated acid | 1 |
  | Q75 | Clemmensen gives the ethyl groups | 2 |
  | Q77 | P against 1/V | 3 |
  | Q80 | 2-bromo-2-methylbutane | 2 |
  | Q81 | chloroprene | 3 |
  | Q87 | benzyl iodide and phenol | 4 |
  | Q93 | the carboxylate, keeping the acetyl group | 4 |
  | Q94 | ethylbenzene | 2 |

- **Q32.** The ratio, √(2/5) : √(2/3) = √3 : √5, is in no option, which fits the drop.
- **Still to come.** D20's full solve-against-key comparison is the rest of README §2's tier-3 check.

## Result

- **Questions:** 200.
- **Corrections:** 33, of which 1 is of meaning (Q170's T_H, the keyed option).
- **As-printed entries:** 32 with their notes, 4 `show` and 28 `record_only`. 30 are kept as printed in the text; Q133's and Q189's run-together words are shown spaced.
- **Cause unsettled:** 7 places, read as meant; none goes to the founder.
- **Ruled by the founder:** the cockroach spellings kept as printed ("Collaterial" provisionally, an F19 check); Q31's ϑ kept; Q133 and Q189 spaced with `record_only` notes.
- **Answers:** 198 single, Q141 multiple, Q32 dropped.
- **Lost strokes:** 30, of which the extraction read 29 as meant and 1 is corrected.

Next: the founder's commit, which places the reviewed file at `pipeline/inputs/papers/neet-2023-g6.json`; then `pyq tag` and `pyq load`.

# pyq review — neet-2024-s6 (NEET (UG) 2024, booklet code S6)

The review README §7 asks for before the founder's approval: every flag of the extract run and all 200 questions read
against the booklet's page images, and the answers filled from NTA's final key. Draft:
`pipeline/data/pyq/neet-2024-s6.draft.json` from `pyq extract` (report `2026-10-22-pyq-extract.md`: pages 3–47, 45 in
all, 200 questions, every section at its declared count, 67 figure flags in 32 questions, no page below 0.80
confidence, ₹157.63). Reviewed file: `pipeline/data/pyq/neet-2024-s6.reviewed.json` — it enters
`pipeline/inputs/papers/neet-2024-s6.json` only on the founder's approval. It is written in the draft's own layout:
PaperFileJson reads it and writes it back byte for byte (200,469 characters), and its checks against the register
entry find no problem, so `diff` of draft against reviewed shows exactly the corrections, the 15 as-printed entries
and the answers below. One question, Q24, goes to the founder (the cause-unsettled section).

## How it was read

- **On the saved page images, which are the bytes the calls read.** `pyq extract` kept each page's image with its
  call (unlike code 45's re-renders); each local copy (`pipeline/data/pyq/pages/neet-2024-s6/`) was checked against
  the sha256 table of `2026-10-22-pyq-extract.md`: 45 of 45 match. No reader opened the PDF.
- **The booklet is a scan, and the only copy.** Pages 3–47 are each one 1-bit JBIG2 image of 1240 × 1753 pixels
  (beside a 28 × 28 logo), with no text layer; drawn at 150 dpi from the 566.7 × 841 pt page, the saved images are
  1180 × 1752 — the scan resampled to the page, so nothing finer than the scan exists for any glyph or drawing; a
  zoom only enlarges. The pack holds no second copy of S6, so NTA's Hindi column is the only other reading of each
  question.
- **Every question, character by character, against the English column.** Five readers split the 45 pages (3–11,
  12–20, 21–29, 30–38, 39–47), each on enlarged crops of both columns, filing every difference with its exact span,
  checking every figure description against its drawing and for an answer hint, and recording each misprint with
  what the Hindi column prints at the same place. Five more swept the same pages for strokes the scan lost (below),
  each place checked against the Hindi column.
- **Claude checked every filed difference on the page image before accepting it** — nothing was changed on a
  reader's word alone — with nearest-neighbour enlargements of the scan's own pixels where a glyph or a drawing
  needed it (Q11, Q24, Q39's diodes and cells in both columns, Q62, Q66, Q69, Q86, Q87, Q114, Q135, Q160, Q177), and
  held every correction to the notation `pyq_extract.v1` writes and the NCERT corpus spells — where the two differ,
  the corpus's form where it writes one (a reaction's condition straight after its arrow; a positive power or a
  nuclide unbracketed), the prompt's where it writes both (a negative power bracketed) (DECISIONS 2026-10-22). Of the
  sweepers' "read as meant" places,
  Claude saw Q3, Q15, Q33, Q34, Q62, Q66, Q96, Q178 and Q200 on the page as well.

## Corrections — 41 in 26 questions

4 of meaning (Q69's three charge signs, Q87's carbonate ion), 10 to figure descriptions, 21 of notation and 6 cause
unsettled (the section after the kept misprints).

| Q | page | field | kind | the draft had | the page prints (in the corpus notation) | why |
|---|---|---|---|---|---|---|
| 5 | 4 | stem | notation | (mu A) | (μA) | the page prints μA; a unit's μ is written as the character, as this paper writes μF and μC (Q14, Q30, Q38) and the corpus spells them |
| 11 | 5 | figure, stem | figure | logic circuit: input A to a NAND gate, input B to a NOR gate; both outputs feed a NOR gate whose output is Y | logic circuit: input A joined to both inputs of a D-shaped gate with an output bubble; input B joined to both inputs of an OR-shaped gate with an output bubble; both outputs feed an OR-shaped gate with an output bubble, whose output is Y | an answer hint: the question asks which gate the circuit equals, and reading the symbols is part of it, so the gates are described as drawn (code 45 Q8); the tied inputs were left out |
| 14 | 6 | stem | notation | ε_0 | epsilon_0 | a Greek symbol by name (`pyq_extract.v1`), as Q17 writes it and the corpus spells it 105 times; the page prints ∈₀ |
| 24 | 8 | stem | cause unsettled | E = hv | E = h nu | a v-shaped glyph in both columns where frequency is meant; a 1-bit scan cannot show whether it is a Latin v or a Greek nu, and the key (3: A, B, C and D) holds only with nu; the corpus spells E = h nu |
| 24 | 8 | stem | cause unsettled | p = hv / c | p = h nu / c | as statement A |
| 28 | 9 | stem | notation | ^290_82 X -> Y (alpha) -> Z (e^+) -> P (beta^-) -> Q (e^-) | ^290_82 X -> (alpha) Y -> (e^+) Z -> (beta^-) P -> (e^-) Q | each emission is printed over its own arrow; the corpus writes a chain's condition straight after its arrow (RNH_2 -> (RX) R_2NH -> (RX) R_3N), and after the product it can be paired with the next arrow |
| 39 | 11 | figure, option 1 | figure | Wheatstone bridge: upper arms 10Ω and 10Ω, lower arms 15Ω and 5Ω; galvanometer G across middle; element D (circled) beside 5Ω arm; cell E and key K in external loop | Wheatstone bridge: upper arms 10Ω and 10Ω, lower arms 15Ω and 5Ω; galvanometer G across middle; a diode D (circled) in series with the 5Ω resistor in the lower right arm, between it and the right corner, its triangle and bar pointing toward the right corner; cell E (longer plate on the right) and key K in external loop | the options differ only in where D sits, which way it points and the cell's polarity; read pixel by pixel in both columns |
| 39 | 11 | figure, option 2 | figure | Wheatstone bridge: 10Ω, 10Ω upper, 15Ω, 5Ω lower, galvanometer G, element D near 5Ω arm; cell E and key K; right side of outer loop drawn dotted | Wheatstone bridge: 10Ω, 10Ω upper, 15Ω, 5Ω lower, galvanometer G, a diode D (circled) in series with the 5Ω resistor in the lower right arm, between it and the right corner, its triangle and bar pointing toward the right corner; cell E (longer plate on the left) and key K | as option 1; 'right side of outer loop drawn dotted' was a thin wire the scan broke up (the Hindi column breaks a different wire of the same drawings), so it is dropped, not described |
| 39 | 11 | figure, option 3 | figure | Wheatstone bridge: 10Ω, 10Ω upper, 15Ω, 5Ω lower, galvanometer G, element D in series with 5Ω arm; cell E and key K | Wheatstone bridge: 10Ω, 10Ω upper, 15Ω, 5Ω lower, galvanometer G, a diode D (circled) in series with the 5Ω resistor in the lower right arm, between it and the right corner, its triangle and bar pointing toward the 5Ω resistor and the bottom corner; cell E (longer plate on the left) and key K | as option 1 |
| 39 | 11 | figure, option 4 | figure | Wheatstone bridge: 10Ω, 10Ω upper, 15Ω lower left; element D connected in parallel with 5Ω resistor in lower right arm; galvanometer G; cell E and key K | Wheatstone bridge: 10Ω, 10Ω upper, 15Ω lower left; in the lower right arm a diode D (circled) and the 5Ω resistor in parallel, each between the right corner and the bottom corner, the diode's triangle and bar pointing toward the right corner; galvanometer G; cell E (longer plate on the left) and key K | as option 1 |
| 43 | 12 | stem | notation | 10^-5 °C | 10^(-5) °C | an exponent of more than one character is bracketed (`pyq_extract.v1`, whose own example is 10^(-11)), as this paper's seven other negative powers and all fifteen of code 45's are; the corpus writes both forms |
| 43 | 12 | stem | notation | 10^-3 m^2 | 10^(-3) m^2 | as above |
| 59 | 15 | option 1 | notation | BrCl_(g) | BrCl(g) | states in brackets, as printed (`pyq_extract.v1`), as Br_2(g) in the same option; the corpus writes X(g) 236 times and never X_(g) |
| 59 | 15 | option 3 | notation | HI_(g) | HI(g) | as above |
| 59 | 15 | option 4 | notation | CO_(g) + | CO(g) + | as above |
| 59 | 15 | option 4 | notation | H_2O_(g) | H_2O(g) | as above |
| 61 | 16 | figure, stem | notation | (allyl group) → cyclohexane | (allyl group) -> cyclohexane | the arrow as `pyq_extract.v1` writes it, as Q67's descriptions and code 45's do |
| 69 | 18 | figure, option 1 | **meaning** | a charge sign in a circle (printed as negative in English column) | a circled plus sign (⊕) | a lost stroke copied: the English keeps only the bar, NTA's Hindi column the upright; the stem asks for a carbocation and option 3 prints ⊕ whole |
| 69 | 18 | figure, option 2 | **meaning** | charge, circled minus below | circled plus sign (⊕) below | a lost stroke copied: the English keeps only the bar, the Hindi column the bar and a stub of the upright; the stem names every option a carbocation |
| 69 | 18 | figure, option 4 | **meaning** | a charge sign in a circle (appears negative) | a circled plus sign (⊕) | a lost stroke copied: the English keeps only a stub of the upright, the Hindi column only the bar; together, and by the stem, ⊕ |
| 86 | 22 | figure, stem | notation | --PBr_3--> A (major) --alc. KOH, Δ--> B (major) | -> (PBr_3) A (major) -> (alc. KOH, Δ) B (major) | each arrow's conditions straight after it, as Q28 and Q97 (DECISIONS 2026-10-22), and the arrow as `pyq_extract.v1` writes it, as Q61 |
| 87 | 22 | option 1 | **meaning** | CO_3^2 ion | CO_3^2- ion | a printing gap: both columns leave a minus-wide blank after the raised 2 (the gap rule); the carbonate ion is CO_3^2- |
| 88 | 22 | option 3 | notation | – 413.14 calories | -413.14 calories | the page prints a minus sign; an en dash and a space read as punctuation, and the sign alone tells this option from option 4 (413.14 calories) |
| 89 | 22 | stem | notation | 3.0 × 10^-3 M, | 3.0 × 10^(-3) M, | as Q43 |
| 89 | 22 | stem | notation | 4.2 × 10^-3 M | 4.2 × 10^(-3) M | as Q43 |
| 89 | 22 | stem | notation | 2.8 × 10^-3 M. | 2.8 × 10^(-3) M. | as Q43 |
| 89 | 22 | stem | notation | 2NO_(g) | 2NO(g) | as Q59 |
| 89 | 22 | stem | notation | NO_(g) is | NO(g) is | as Q59 |
| 89 | 22 | stem | notation | NO_(g) at | NO(g) at | as Q59 |
| 96 | 24 | stem | notation | (Π) | (Pi) | a Greek symbol by name; the corpus writes osmotic pressure as Pi (chem12 eq. 1.40) |
| 97 | 24 | stem | notation | CH_3 – CH_2 – CH_2 – I -> A (NaCN) / -> B (OH^-, Partial hydrolysis) -> C (major) (NaOH, Br_2) | CH_3 – CH_2 – CH_2 – I -> (NaCN) A / -> (OH^-, Partial hydrolysis) B -> (NaOH, Br_2) C (major) | as Q28: each reagent is printed over its own arrow, and the corpus writes it straight after that arrow |
| 114 | 27 | figure, stem | figure | (a) left flower with cup-like thalamus, (b) right flower. | (a) the ovary sits free at the bottom of a deep cup formed by the thalamus, a gap between it and the cup's wall, and the sepals, petals and stamens arise from the rim of the cup, at about the height of the top of the ovary; (b) the ovary sits on a shallow, saucer-like thalamus, and the sepals, petals and stamens arise from its rim, around the lower part of the ovary. | the description drew only (a); both flowers described as drawn, with no class named |
| 116 | 27 | stem | cause unsettled | is called; | is called: | a wrong whole character in a JBIG2 scan proves nothing alone; NTA's Hindi column prints a colon |
| 133 | 31 | option 1 | cause unsettled | Promotor | Promoter | one letter for another in a JBIG2 scan proves nothing alone, and the Hindi column transliterates the word, so it cannot settle it; NCERT spells promoter |
| 135 | 31 | figure, stem | figure | Diagram of a stomatal apparatus surrounded by epidermal cells, with pointers labelled A (epidermal cell wall at right), B (subsidiary/epidermal cell region around guard cells), C and D (guard cell walls / stomatal pore region). | Diagram of a stomatal apparatus: a pair of bean-shaped cells around a central slit, surrounded by a stippled ring, among larger cells; pointers: A into the large cell at the upper right, B to the stippled ring around the bean-shaped pair, C to the outer edge of the right bean-shaped cell, D into the central slit between the bean-shaped cells. | an answer hint: the names (guard cell, subsidiary, epidermal) answer which component has thin outer and thick inner walls and rule options out; A's arrowhead is inside the cell, not on its wall |
| 143 | 34 | stem | cause unsettled | Jacque | Jacques | a missing letter at a line end, where spacing cannot show a letter-width blank, and the Hindi column spells the name in Devanagari, so nothing settles whether the paper dropped it; NCERT spells Jacques Monod |
| 144 | 34 | option 1 | notation | 100x/3x | (100x)/(3x) | printed as 100x stacked over 3x; unbracketed it reads as (100x/3)·x (code 45 Q37) |
| 146 | 35 | figure, stem | figure | filament-like | thread-like | an answer hint: 'filament' is NCERT's name for a stamen's stalk, and an option turns on well exposed stamens; described as drawn |
| 160 | 38 | figure, stem | figure | (a) long cylindrical striated fibres side by side; (b) spindle-shaped unstriated fibres, each with one nucleus; (c) branched striated fibres with junctions between them | (a) long cylindrical fibres side by side, each with cross-bands along its length; (b) spindle-shaped fibres tapering at both ends, each with one dark nucleus and no cross-bands; (c) cross-banded fibres that branch and join one another | an answer hint: 'striated' and 'unstriated' are the textbook names of the muscle types the question asks the student to identify; described as drawn |
| 177 | 42 | figure, stem | figure | two shaded gene regions labelled X and Y near the bottom left. | X marks a densely stippled block on the ring at the lower left; Y marks a short segment at the bottom of the ring, between two cross-lines, just left of the Pvu II line. | only X's region is shaded and at the lower left; Y's is at the bottom, beside Pvu II |
| 196 | 47 | stem | cause unsettled | Promotor | Promoter | as Q133 option 1 (the Hindi column prints the Hindi term) |

## Checked against the saved page images

- **The figure flags** — 67 descriptions in 32 questions: 52 stand as the extraction wrote them, and 15 in 10
  questions are corrected above — Q69's three signs; Q39's four, whose options differ only in where the diode D
  sits, which way it points and the cell's polarity, none of which the descriptions said (read pixel by pixel in
  both columns, which agree; option 2's "dotted" side was a thin wire the scan broke up, as it breaks other wires of
  the same drawings in the Hindi column, so it is dropped rather than described); Q11, Q61 (its arrow), Q86 (its
  arrows), Q114, Q135, Q146, Q160 and Q177.
- **Names in the descriptions**, by the 2026-10-21 rule (DECISIONS, Claude's, for the founder to overrule): Q11's gate
  names, Q135's guard, subsidiary and epidermal cells, Q146's "filament-like" (NCERT's name for a stamen's stalk) and
  Q160's "striated" and "unstriated" — NCERT's names for the muscle types the question asks — are replaced by what is
  drawn. Kept, as descriptions that name no answer: Q49's and Q73's slopes (what the graphs show), Q122's "embryo
  region" (all four pointers are in the one embryo), Q86's compound names (each checked against its drawing; option
  3's B is 4-methylcyclohexene), and Q39's "diode", the standard symbol's name, with its direction and the cell's
  polarity described as drawn, not as bias (as code 45's Q11). Every description is checked again for an answer hint
  before it serves as alt text at D31, and so are the extraction's flags kept with the question in
  `questions.extraction`, which keep the draft's words as provenance (Q11's gate names, Q69's "negative" signs, Q135's
  cell names, Q146's "filament-like", Q160's "striated").

## The booklet's own errors, kept as printed (15)

The transcription copies a printed misprint, and each is recorded as an `as_printed` entry of its question — the
span as printed, the intended reading, a one-line note that quotes no more than those — with its display class (the
founder, 2026-10-21): `show` for one that could change how a student reads the question, `record_only` for a
spelling or grammar slip a student reads correctly. A lossy 1-bit scan proves no misprint by a wrong whole character
alone (the founder, 2026-10-21); each one below is a missing, extra or misplaced letter, word, space or apostrophe,
or a word in the wrong number, or is printed the same in NTA's Hindi column (Q100's वोल्टमीटर, Q196's SnRNAs).
Taking the first kind as printed is Claude's reading of the founder's rule (DECISIONS 2026-10-22, for the founder to
overrule): a JBIG2 encoder substitutes one glyph's bitmap for another of like shape and does not add, drop or move a
glyph, and this scan loses strokes, not letters — across the 195 lost-stroke places below it never lost a whole
letter. Where a letter is missing, the page shows no letter-width blank at its place (Claude checked Q31, Q72, Q81,
Q98, Q104, Q109 and Q141 on the image; Q143's "Jacque" ends its line, where spacing shows nothing, so it is read as
meant instead — the next section). One is `show`:
Q100's "voltmeter", a different real word naming another instrument; its Hindi line is written at the crop work
before D31 (DECISIONS 2026-10-21).

| Q | at | printed | intended | the note | display |
|---|---|---|---|---|---|
| 5 | stem | lies | lie | 'lies' is printed where 'lie' is meant. | record_only |
| 31 | stem | mass | masses | 'mass' is printed where 'masses' is meant. | record_only |
| 60 | stem | Tollen's | Tollens' | 'Tollen's' is printed where 'Tollens'' is meant. | record_only |
| 60 | stem | options from the given below | answer from the options given below | 'options from the given below' is printed where 'answer from the options given below' is meant. | record_only |
| 72 | stem | solubility | solubilities | 'solubility' is printed where 'solubilities' is meant. | record_only |
| 81 | stem | point of hydrides | points of hydrides | 'point of hydrides' is printed where 'points of hydrides' is meant. | record_only |
| 98 | stem | acid | acids | 'acid' is printed where 'acids' is meant. | record_only |
| 100 | stem | voltmeter | voltameter | 'voltmeter' is printed where 'voltameter' is meant. | show |
| 104 | stem | following genotype | following genotypes | 'following genotype' is printed where 'following genotypes' is meant. | record_only |
| 109 | stem | begining | beginning | 'begining' is printed where 'beginning' is meant. | record_only |
| 133 | stem | down stream | downstream | 'down stream' is printed where 'downstream' is meant. | record_only |
| 141 | stem | statement | statements | 'statement' is printed where 'statements' is meant. | record_only |
| 142 | stem | out door | outdoor | 'out door' is printed where 'outdoor' is meant. | record_only |
| 177 | stem | showing | shows | 'showing' is printed where 'shows' is meant. | record_only |
| 196 | stem | SnRNAs | snRNAs | 'SnRNAs' is printed where 'snRNAs' is meant. | record_only |

## Cause unsettled — read as meant, no note (6 places in 5 questions)

A wrong whole character that nothing settles — the scan cannot show whether the paper printed it or the encoder
substituted it, and no copy prints it differently in a way that decides — is read as meant, with no as-printed note,
and listed here; it goes to the founder only when the intended reading is in doubt, or when it touches the keyed
answer, a number, a unit, a sign or a formula and the Hindi column, the second official copy and D20's solve-vs-key
check together still cannot settle it (the founder, 2026-10-21):

- **Q24, statements A and C: "E = hv", "p = hv / c" — for the founder.** Both columns print a v-shaped glyph where
  frequency is meant; at 150 dpi a 1-bit scan cannot show whether it is a Latin v or a Greek nu. It touches a
  formula, and nothing can settle which the paper printed: the Hindi column prints the same glyph, the pack holds no
  second copy, and D20's solve-vs-key check, not yet run, can only confirm that the key fits a frequency (option 3,
  "A, B, C and D only", makes A and C true). So it goes to the founder under his condition (b). The file reads it as
  meant, `h nu`, the corpus spelling, until he rules; the alternative is to keep "hv" with an as-printed note.
- **Q116: "is called;"** where the stem ends; NTA's Hindi column prints a colon. Read as "is called:".
- **Q133 option 1 and Q196: "Promotor".** One letter for another; the Hindi column transliterates the word (Q133) or
  prints the Hindi term उन्नायक (Q196), so it cannot settle it. Read as "Promoter", NCERT's spelling.
- **Q143: "Jacque".** A missing letter at the end of a line, where spacing cannot show a letter-width blank, and the
  Hindi column spells the name in Devanagari. Read as "Jacques", NCERT's spelling of Jacques Monod.

## Strokes the scan lost (transcribed as meant)

A gap is not a misprint: a character whose ink is missing is read as meant where the context fixes it (DECISIONS
2026-10-20 and 2026-10-21). This scan loses thin strokes throughout — A's and H's crossbars (A prints as Λ, H as II),
italic t's crossbar, superscript minus signs, a stroke of "+" or ⊕, F's and E's arms, and the bar of e, which leaves
a c. That last is how Q62's "Fc^3+/Fc^2+" and Q66's "Bc^3+" print, which the sweeper classed as substituted: Claude
saw both on the page, and the c of "Fc" is the c that ends "duc" for "due" on the same line, as "Bc" stands beside
"statc" for "state" — the e's bar this scan drops across the running text of both columns (the Hindi column's Latin
text prints Q62's "Fc" too, and Q66's Be whole). So all 45 pages were swept: **195 places in 109 questions — 190 read
as meant by the extraction, and 5 copied literally and corrected above (Q69 options 1, 2 and 4's ⊕; Q87 option 1's
minus; Q39 option 2's broken wire, described as dotted); none unclear.** The list is complete for symbols, labels,
numbers and signs; a broken T or F in an ordinary word is listed where a sweeper saw one, and the transcription has
the right word at every one. Where only the Hindi column lost a stroke, nothing is listed — it matters only to the
parked Hindi pass. "Transcribed as" is the extraction's text.

| Q | page | field | the image shows | transcribed as | NTA's Hindi column |
|---|---|---|---|---|---|
| 1 | 3 | stem | B.  n2 = 4 lo n1 = 2  (the crossbar of the t in 'to' is gone, so it r… | \| B. n_2 = 4 to n_1 = 2 \| II. 434.1 \| | से (a Devanagari word; no Latin t to compare) |
| 2 | 3 | option 3 | Λ-II, B-I, C-III, D-IV  (the A has no crossbar) | A-II, B-I, C-III, D-IV | A-II, B-I, C-III, D-IV (A printed whole) |
| 3 | 3 | stem | 9.8 × 10 ⁶ kg m²  (no minus before the raised 6; at most a faint dot… | 9.8 × 10^(-6) kg m^2 | 9.8 × 10⁻⁶ kg m² (minus printed whole) |
| 4 | 4 | stem | Λn unpolarised light beam | An unpolarised light beam strikes a glass surface at Brewster's angle… | no Latin counterpart (the stem begins 'एक अध्रुवित प्रकाश प… |
| 5 | 4 | option 2 | Λ is correct but B is incorrect. | A is correct but B is incorrect. | A सही है एवं B गलत है। (A printed whole) |
| 5 | 4 | option 4 | Both Λ and B are correct. | Both A and B are correct. | दोनों A व B सही हैं। (A printed whole) |
| 5 | 4 | stem | statements Λ and B | Consider the following statements A and B and identify the correct an… | कथनों A व B (A printed whole) |
| 6 | 4 | stem | Λ light ray enters through a right angled prism | A light ray enters through a right angled prism at point P | no Latin counterpart (the stem begins 'एक प्रकाश किरण') |
| 13 | 6 | stem | Λt any instant of time ι, | At any instant of time t, | किसी क्षण ι पर (the italic t has no crossbar there too; no… |
| 13 | 6 | stem | given by 2ι − 1 (SI unit) | given by 2t - 1 (SI unit) | 2ι − 1 (SI मात्रक में) (the same gap) |
| 15 | 6 | stem | 0.07 Nm ¹  (no minus before the raised 1) | 0.07 N m^-1 | 0.07 Nm⁻¹ (minus printed whole) |
| 17 | 7 | option 2 | Both Λ and R are true and R is the correct explanation of A. | Both A and R are true and R is the correct explanation of A. | दोनों A व R सही हैं तथा R, A की सही व्याख्या है। (A printed… |
| 17 | 7 | option 3 | correct explanation of Λ. | Both A and R are true and R is NOT the correct explanation of A. | R, A की सही व्याख्या नहीं है। (A printed whole) |
| 17 | 7 | stem | choose the correcı answer  (the italic t in 'correct' has no crossbar) | choose the correct answer from the options given below: | no Latin counterpart (सही उत्तर) |
| 19 | 7 | option 3 | BΛ and CD  (italic A with no crossbar) | BA and CD | BA एवं CD (A printed whole) |
| 19 | 7 | option 4 | ΛB and CD  (italic A with no crossbar) | AB and CD | AB एवं CD (A printed whole) |
| 22 | 8 | stem | mosı appropriaıe answer  (both italic t's have no crossbar) | choose the most appropriate answer from the options given below : | no Latin counterpart |
| 24 | 8 | option 1 | A, B, D and E only  (the arms of the E are faint) | A, B, D and E only | केवल Λ, B, D व E (E faint in the same way; here the A loses… |
| 24 | 8 | stem | L. Photon possesses positive charge.  (the top and middle arms of the… | E. Photon possesses positive charge. | E. फोटान पर धनात्मक आवेश होता है। (E printed whole) |
| 26 | 9 | stem | x = 5 sin(πι + π/3) m  (the italic t has no crossbar) | If x = 5 sin(pi t + pi/3) m | x = 5sin(πι + π/3) m (the same gap) |
| 30 | 9 | option 2 | 2 μF'  (the italic F lacks most of its stem and lower arm) | 2 μF | 2 μF (printed whole) |
| 30 | 9 | stem | between terminal Λ and terminal B  (italic A with no crossbar) | the equivalent capacitance between terminal A and terminal B is : | टर्मिनल Λ व टर्मिनल B (the same gap) |
| 33 | 10 | option 1 | 3.92 m s ·²  (the minus is reduced to a dot) | 3.92 m s^-2 | 3.92 m s ·² (the same gap) |
| 33 | 10 | option 2 | 19.6 m s ·²  (the minus is reduced to a dot) | 19.6 m s^-2 | 19.6 m s ·² (the same gap) |
| 34 | 10 | stem | 2 × 10¹¹ N m ·²  (the minus is reduced to a dot) | 2 × 10^11 N m^-2 | 2 × 10¹¹ N m⁻² (minus printed whole) |
| 36 | 11 | stem | Λ small telescope has an objective | A small telescope has an objective of focal length 140 cm | no Latin counterpart |
| 38 | 11 | figure:stem | the right-hand vertical wire of the circuit loop is broken into dots | circuit: capacitor C = 10 μF (plates marked + and −) connected in a l… | The Hindi copy prints the right-hand wire solid and breaks… |
| 38 | 11 | option 1 | 0.35 Λ  (italic A with no crossbar) | 0.35 A | 0.35 A (printed whole) |
| 38 | 11 | option 2 | 0.58 Λ  (italic A with no crossbar) | 0.58 A | 0.58 A (printed whole) |
| 38 | 11 | option 3 | 0.93 Λ  (italic A with no crossbar) | 0.93 A | 0.93 A (printed whole) |
| 38 | 11 | option 4 | 1.20 Λ  (italic A with no crossbar) | 1.20 A | 1.20 A (printed whole) |
| 39 | 11 | figure:option 2 | the right-hand vertical wire of the outer cell loop is broken into do… | Wheatstone bridge: 10Ω, 10Ω upper, 15Ω, 5Ω lower, galvanometer G, ele… — copied as printed; corrected above | The same right-hand wire is broken into dots in the Hindi o… |
| 41 | 12 | stem | The velocity (v) – time (ı) plot | The velocity (v) – time (t) plot | वेग (v) – समय (ı) — the same lost crossbar |
| 41 | 12 | stem | acceleration (ɑ) – time (ı) graph | The acceleration (a) – time (t) graph | त्वरण (ɑ) – समय (ı) — the same lost crossbar |
| 41 | 12 | stem | acceleration (ɑ) with the top of the bowl open, reads like a slanted u | The acceleration (a) | त्वरण (ɑ) — the same open bowl |
| 43 | 12 | stem | Λ metallic bar of Young's modulus | A metallic bar of Young's modulus | no Latin A at this spot (the Hindi sentence opens with 0.5… |
| 43 | 12 | stem | expansion 10  ⁵ °C  ¹ | coefficient of linear thermal expansion 10^-5 °C^-1 | 10⁻⁵ °C⁻¹ — full minus signs |
| 44 | 12 | stem | Λ.  hold the sheet there if it is magnetic. | A. hold the sheet there if it is magnetic. | Λ. — the same lost crossbar |
| 46 | 13 | option 3 | l flows in the same direction as l. | displacement current of magnitude equal to I flows in the same direct… | [ परिमाण ... धारा I की — the same serif loss on the first I |
| 46 | 13 | option 4 | l flows in a direction opposite to that of l. | displacement current of magnitude equal to I flows in a direction opp… | [ की विपरीत दिशा में [ — the same serif loss |
| 50 | 13 | option 1 | αβ/ı | alpha beta / t | αβ/ı — the same lost crossbar |
| 50 | 13 | option 2 | βı/α | beta t / alpha | βı/α — the same lost crossbar |
| 50 | 13 | option 3 | αı/β | alpha t / beta | αt/β — full crossbar |
| 50 | 13 | option 4 | αβı | alpha beta t | αβı — the same lost crossbar |
| 50 | 13 | stem | Λ force defined by | A force defined by | no Latin A at this spot (the Hindi sentence opens with F =… |
| 50 | 13 | stem | F = αı² + βı ... at a given time ı. | F = alpha t^2 + beta t acts on a particle at a given time t. | F = αı² + βı ... समय ı पर — the same lost crossbars |
| 52 | 14 | option 2 | Λ-I, B-IV, C-II, D-III | A-I, B-IV, C-II, D-III | A-I, B-IV, C-II, D-III — full A |
| 52 | 14 | option 4 | Λ-III, B-IV, C-I, D-II | A-III, B-IV, C-I, D-II | A-III, B-IV, C-I, D-II — full A |
| 55 | 15 | stem | D. Λdiabatic process | D. Adiabatic process | रूद्धोष्म प्रक्रम — Devanagari, no Latin A at this spot |
| 56 | 15 | option 1 | Λ-II, B-I, C-IV, D-III | A-II, B-I, C-IV, D-III | A-II, B-I, C-IV, D-III — full A |
| 56 | 15 | option 4 | Λ-III, B-IV, C-II, D-I | A-III, B-IV, C-II, D-I | A-III, B-IV, C-II, D-I — full A |
| 56 | 15 | stem | Λ. m_l | A. m_l | A. m_l — full A |
| 58 | 15 | option 4 | 'Te — the crossbar of T broken, its left arm detached | Te | 'Te — the same broken crossbar |
| 59 | 15 | option 3 | II_2(g) + I_2(g) ⇌ 2 III_(g) | H_2(g) + I_2(g) ⇌ 2 HI_(g) | H_2(g) + I_2(g) ⇌ 2 HI_(g) — full H |
| 59 | 15 | option 4 | CO_(g) + II_2O_(g) ⇌ CO_2(g) + II_2(g) | CO_(g) + H_2O_(g) ⇌ CO_2(g) + H_2(g) | CO_(g) + II_2O_(g) ⇌ CO_2(g) + II_2(g) — the same lost cros… |
| 60 | 16 | stem | C. IICN | C. HCN | C. HCN — full H |
| 60 | 16 | stem | D. NII_2OII | D. NH_2OH | D. NH_2OH — full H |
| 61 | 16 | figure:stem | CH_2 – CH ⁼ CH_2 → (the double bond's lower line almost gone; one str… | cyclohexane ring attached to CH_2 – CH = CH_2 (allyl group) | CII_2 – CII – CII_2 → — the second line of = lost entirely… |
| 61 | 16 | figure:stem | product: CII_2 – CII_2 – CIIO | cyclohexane ring attached to CH_2 – CH_2 – CHO | CII_2 – CII_2 – CIIO — the same lost crossbars |
| 62 | 16 | stem | Fc^3+/Fc^2+ | Fe^3+/Fe^2+ | Fc^3+/Fc^2+ — the same e printed as c |
| 63 | 16 | stem | Statement II : [Co(NII_3)_6]^3+ is diamagnetic | Statement II : [Co(NH_3)_6]^3+ is diamagnetic | [Co(NH_3)_6]^3+ — full H |
| 66 | 17 | stem | Bc^3+ ion | Be^3+ ion | Be^3+ — full e |
| 67 | 17 | option 3 | Λ-III, B-I, C-II, D-IV | A-III, B-I, C-II, D-IV | A-III, B-I, C-II, D-IV — full A |
| 67 | 17 | option 4 | Λ-IV, B-I, C-II, D-III | A-IV, B-I, C-II, D-III | Λ-IV — the same lost crossbar |
| 68 | 18 | stem | Λctivation energy of any chemical reaction | Activation energy of any chemical reaction | Devanagari, no Latin A at this spot |
| 69 | 18 | figure:option 1 | a circled minus (⊖) on the ring carbon bearing CH_3 — the vertical st… | that carbon bears a charge sign in a circle (printed as negative in E… — copied as printed; corrected above | a circle with the vertical stroke and a faint horizontal —… |
| 69 | 18 | figure:option 2 | a circled minus (⊖) below CH — the vertical stroke of ⊕ gone | Skeletal chain H_3C–CH2–CH(charge, circled minus below)–CH(CH_3)–CH_3 — copied as printed; corrected above | a circle with the horizontal and a stub of the vertical bel… |
| 69 | 18 | figure:option 2 | right-hand end CII_3 | Skeletal chain H_3C–CH2–CH(charge, circled minus below)–CH(CH_3)–CH_3 | CII_3 — the same lost crossbar |
| 69 | 18 | figure:option 3 | CII_3 – C(⊕)H – CII_2 – CII(CH_3)CH_3 | Chain CH_3–C(H)(circled plus)–CH_2–CH(CH_3)–CH_3 | CII_3, CII_2, CII — the same lost crossbars |
| 69 | 18 | figure:option 4 | a circle with only a dot in it (⊙) above CH_2 — both strokes of ⊕ nea… | Cyclopentane ring attached to CH_2 bearing a charge sign in a circle… — copied as printed; corrected above | a circled minus (⊖) — the vertical lost |
| 69 | 18 | figure:option 4 | CII_2 on the cyclopentyl ring | Cyclopentane ring attached to CH_2 | CII_2 — the same lost crossbar |
| 71 | 18 | option 3 | CII_3 – CII_2 – CH – OII | CH_3–CH_2–CH(CH_3)–OH | CH_3 – CH_2 – CH – OH — full H |
| 71 | 18 | option 4 | branch CII_3 below CH | CH_3–CH(CH_3)–CH_2OH | CH_3 — full H |
| 72 | 19 | stem | (K_II) | (K_H) | (K_II) — the same lost crossbar |
| 73 | 19 | stem | ln k vs 1/'I' (the T's crossbar broken to two stubs) | ln k vs 1/T | 1/T — full crossbar |
| 75 | 19 | figure:option 2 | OII | benzene ring with NO_2 and OH on adjacent carbons (ortho-nitrophenol) | OII — the same lost crossbar |
| 75 | 19 | figure:option 4 | IIO | benzene ring with NO_2 and HO at meta positions (meta-nitrophenol) | IIO — the same lost crossbar |
| 76 | 19 | option 4 | 7.ero mg (the base stroke of Z mostly gone) | Zero mg | Zero mg — full Z |
| 78 | 20 | stem | Λ.  Ti^3+ | A. Ti^3+ | A. Ti^3+ — full A |
| 79 | 20 | option 1 | 2.271098 l. of helium at STP (the foot of L nearly gone) | 2.271098 L of helium at STP | 2.271098 L — full L |
| 80 | 20 | stem | B. [Co(NII_3)_5(SO_4)]Br | B. [Co(NH_3)_5(SO_4)]Br | B. [Co(NH_3)_5(SO_4)]Br — full H |
| 81 | 20 | stem | H_2'Te (the T's crossbar broken, left arm detached) | H_2Te | II_2Tc — T intact, e printed as c and H crossbars lost |
| 83 | 21 | option 4 | Λ-II, B-III, C-I, D-IV (A without its crossbar) | A-II, B-III, C-I, D-IV | A-II, B-III, C-I, D-IV (A whole) |
| 83 | 21 | stem | D.  1 mol of I'eO to I'e2O3   IV. 5F  (both F's of Fe have lost most… | \| D. 1 mol of FeO to Fe_2O_3 \| IV. 5F \| | 1 मोल FcO से Fc2O3 में ... IV. 5F: the F is whole there, bu… |
| 86 | 22 | figure:option 1 | Λ =  [ring with II3C, OII, Br] ;  B −  [ring ketone with II3C] (A los… | A = cyclohexane ring with OH carbon flanked by H_3C and Br (2-bromo-6… | Λ −  ... ; B = (the Hindi A has also lost its crossbar and… |
| 86 | 22 | figure:option 2 | A =  [ring with Br and II3C] ; B = [ring with H3C] (left H crossbar g… | A = cyclohexane with Br and adjacent H_3C (1-bromo-2-methylcyclohexan… | Λ − [II3C ...] ; B = [H3C ...]: the Hindi A and = are broke… |
| 86 | 22 | figure:option 3 | Λ =  [ring with Br and H3C] ; B = [cyclohexene with H3C] | A = cyclohexane with Br and adjacent H_3C; B = cyclohexene with H_3C… | Λ = (the same gap) |
| 86 | 22 | figure:option 4 | Λ =  [ring with II3C, OH, Br] ; B = [ring with II3C, OH] (A and H cro… | A = cyclohexane with OH carbon flanked by H_3C and Br; B = cyclohexen… | Λ = [II3C ...] ; B = [H3C ...] (the same A gap) |
| 86 | 22 | figure:stem | II3C and OII on the ring (H crossbars gone); the arrow labels A, Δ, B… | Reaction scheme: 2-methylcyclohexan-1-ol (cyclohexane ring with OH an… | H3C and OH printed whole |
| 86 | 22 | stem | Major products Λ and B formed in the following reaction sequence | Major products A and B formed in the following reaction sequence, are | मुख्य उत्पाद, A और B हैं (A whole) |
| 87 | 22 | option 1 | CO3^2   ion. (a blank after the raised 2, no minus sign) | Three canonical forms can be drawn for CO_3^2 ion. — copied as printed; corrected above | CO3^2   आयन: the same blank, no minus sign |
| 89 | 22 | stem | If 0.1 mol L  ¹ of NO(g) (a blank before the raised 1; the L is also… | If 0.1 mol L^-1 of NO_(g) is taken in a closed vessel, | 0.1 mol L^−1 (minus whole) |
| 90 | 23 | figure:option 2 | [cyclohexyl]– CIIO (H crossbar gone) | structure: cyclohexyl–CHO | CHO (whole) |
| 90 | 23 | figure:option 4 | OII  OII over [cyclohexyl]–CII · · CII–[cyclohexyl] (the C–C bond bet… | structure: cyclohexyl–CH(OH)–CH(OH)–cyclohexyl | OH OH over CH–CH (bond and H's whole) |
| 90 | 23 | figure:stem | C = CII   KMnO4/H' (the + after H is only a short tick, the H has los… | reaction scheme: cyclohexyl–C(H)=CH–cyclohexyl (alkene with H on left… | KMnO4/H' (the + is broken the same way) |
| 92 | 23 | option 3 | POCl3 and II3PO4 (H crossbar mostly gone) | POCl_3 and H_3PO_4 | POCl3 एवं H3PO4 (whole) |
| 92 | 23 | stem | The products Λ and B ... 3ROH + PCl3 → 3RCl + Λ (both A's without cro… | The products A and B obtained in the following reactions, respectivel… | उत्पाद, A और B (whole) in the sentence; 3RCl + Λ in the equ… |
| 93 | 23 | option 1 | L, A, B, C, D (the first E has lost its top arm and most of its middl… | E, A, B, C, D | E, A, B, C, D (E whole) |
| 93 | 23 | stem | A. Al^3\|   B. Cu^2' (the + of Al lost its horizontal bar; the + of C… | A. Al^3+ / B. Cu^2+ | Al^3' and Cu^2\| (both pluses broken there too) |
| 94 | 23 | stem | Statement II: ... but [Co(NII3)4Cl2]^+ (H crossbar gone) | Statement II : Complex [Co(NH_3)_6]^3+ has only one kind of ligands b… | [Co(NH3)4Cl2]^' (NH3 whole there, but the + is broken) |
| 95 | 24 | stem | (Given atomic masses of Λ = 64; B = 40; C = 32 u) | (Given atomic masses of A = 64; B = 40; C = 32 u) | आण्विक द्रव्यमान Λ = 64 (the same gap; the Hindi options pr… |
| 96 | 24 | stem | slope 25.73 L bar mol  ¹ (a blank before the raised 1) | slope 25.73 L bar mol^-1 | 25.73 L bar mol  ¹: the same blank |
| 98 | 24 | stem | I'e^2+ ion? (the F has lost most of its top arm) | which of the following acid is added to prevent hydrolysis of Fe^2+ i… | Fe^2+ आयन (whole) |
| 100 | 24 | stem | 9.6487 Λ current (the ampere symbol without crossbar) | Mass in grams of copper deposited by passing 9.6487 A current through… | 9.6487 A विद्युत-धारा (A whole) |
| 105 | 25 | stem | d.\' / dı = r.\' [ (K − .\') / K ] (each italic N has lost strokes; t… | The equation of Verhulst-Pearl logistic growth is dN/dt = rN[(K - N)/… | the same broken N's and dı |
| 106 | 25 | option 1 | 3 molecules of AIP and 2 molecules of NADPH (T top bar mostly gone) | 3 molecules of ATP and 2 molecules of NADPH | ATP के तीन अणु और NADPII के 2 अणु (ATP whole; the H of NADP… |
| 106 | 25 | option 2 | 2 molecules of ATP and 3 molecules of NΛDPH | 2 molecules of ATP and 3 molecules of NADPH | NADPH (whole); ATP prints as AIP |
| 106 | 25 | option 3 | 2 molecules of ΛTP and 2 molecules of NADPII (A crossbar and H crossb… | 2 molecules of ATP and 2 molecules of NADPH | ATP के दो अणु और NADPH के 2 अणु (whole) |
| 106 | 25 | option 4 | 3 molecules of AIP and 3 molecules of NADPH (T top bar mostly gone) | 3 molecules of ATP and 3 molecules of NADPH | ΛTP के तीन अणु और NΛDPH के 3 अणु (the A's lose their crossb… |
| 106 | 25 | stem | molecules of ΛTP and NΛDPH (A crossbars gone, T top bar thin) | How many molecules of ATP and NADPH are required for every molecule o… | ATP और NADPH (whole) |
| 107 | 26 | stem | Λ. Clostridium butylicum ... IV. Cyclosporin-Λ (both A's without cros… | \| A. Clostridium butylicum \| I. Ethanol \| / \| D. Streptococcus sp… | A. क्लोस्ट्रीडियम ... IV. साइक्लोस्पोरिन-A (both A's whole) |
| 108 | 26 | option 1 | Λ, B and D only | A, B and D only | केवल A, B और D (whole) |
| 114 | 27 | option 3 | (a) Hypogynous; (b) l'pigynous (the E of Epigynous has lost most of i… | (a) Hypogynous; (b) Epigynous | (a) जायांगाधर (b) जायांगोपरिक (hypogynous; epigynous) |
| 115 | 27 | stem | Λ. Rhizopus | \| A. Rhizopus \| I. Mushroom \| | A. राइजोपस (A whole) |
| 120 | 28 | option 3 | Λ, C, D and E only (the E is partly broken too) | A, C, D and E only | केवल A, C, D और E (whole) |
| 121 | 29 | stem | C. It may multiply and be inherited along with the host DNΛ. | C. It may multiply and be inherited along with the host DNA. | परपोषी (होस्ट) डीएनए: spelled in Devanagari, no Latin lette… |
| 123 | 29 | option 1 | Λ-IV, B-III, C-II, D-I | A-IV, B-III, C-II, D-I | Λ-IV, B-III, C-II, D-I (the same gap) |
| 123 | 29 | stem | C. Cross of I'1 (the F has lost most of its top arm) | \| C. Cross of F_1 progeny with any of the parents \| III. Allele \| | C. F1 संतति का (F whole) |
| 127 | 30 | stem | cry IΛc (the A has no crossbar) | coded by a gene cry IAc. | इनको जीन cry IAc: the A is printed in full |
| 130 | 31 | stem | L. (the label E has a broken top arm and no middle arm) | E. Tropical environments are constant and predictable. | E. printed in full |
| 130 | 31 | stem | 'I'ropical (in items A and E the crossbar of T is broken) | A. Tropical latitudes ... / E. Tropical environments ... | Devanagari (उष्णकटिबंधीय); no Latin letter at this spot |
| 133 | 31 | stem | Λ transcription unit in DNΛ is defined primarily (both A's lack the c… | A transcription unit in DNA is defined primarily by the three regions… | डीएनए (DNA) में अनुलेखन: the Latin DNA has a full A (the Hi… |
| 135 | 31 | figure:stem | Λ (the top-right pointer label has no crossbar) | pointers labelled A (epidermal cell wall at right) | The label A in the Hindi copy of the figure is full. Hindi… |
| 135 | 31 | figure:stem | I3 / ( / I) (in labels B, C and D the bowl joins and bars are broken) | B (subsidiary/epidermal cell region around guard cells), C and D | Labels B, C and D are broken the same way in the Hindi figu… |
| 136 | 32 | option 3 | Λ-IV, B-I, C-II, D-III | A-IV, B-I, C-II, D-III | Λ-IV, B-I, C-II, D-III: the same gap |
| 136 | 32 | stem | Λ. Monoadelphous | \| A. Monoadelphous \| I. Citrus \| | Λ. एकलसंघी: the same gap |
| 138 | 32 | option 4 | Λ, C, D and E only | A, C, D and E only | केवल A, C, D और E: the A is full |
| 138 | 32 | stem | Λ. Asexual reproduction (the label has no crossbar; the A of 'Asexual… | A. Asexual reproduction occurs usually by biflagellate zoospores. | A. printed in full |
| 138 | 32 | stem | 'I'he major pigments (the crossbar of T is broken) | D. The major pigments found are chlorophyll a, c and carotenoids and… | Devanagari (पाए जाने वाले मुख्य वर्णक); no Latin letter |
| 139 | 32 | option 1 | Λ-IV, B-III, C-II, D-I | A-IV, B-III, C-II, D-I | A-IV, B-III, C-II, D-I: the A is full. In this question the… |
| 141 | 33 | option 1 | 5' →3˙ (the second prime is reduced to a dot with a faint tick) | 5' -> 3' | 5' →3' with the same thin, broken primes; a mark is left af… |
| 141 | 33 | option 2 | 3` →5' (the first prime is a faint grave-like tick) | 3' -> 5' | 3` →5': the same faint first prime |
| 141 | 33 | option 3 | 'I'he DNA dependent RNA polymerase (the crossbar of T is broken) | The DNA dependent RNA polymerase catalyses polymerization in one dire… | Devanagari (डीएनए निर्भर आरएनए (RNΛ)); no T at this spot |
| 141 | 33 | option 4 | The DNΛ dependent DNΛ polymerase (both A's lack the crossbar) | The DNA dependent DNA polymerase catalyses polymerization in 5' -> 3'… | डीएनए निर्भर डीएनए पोलीमरेस: Devanagari, no Latin letters a… |
| 141 | 33 | option 4 | 5' →3' as well as 3' →5˙ (the last prime is reduced to a dot) | 5' -> 3' as well as 3' -> 5' | 5' →3' के साथ-साथ 3' →5': thin primes, each still marked |
| 143 | 34 | option 4 | Λ-II, B-III, C-IV, D-I | A-II, B-III, C-IV, D-I | A-II, B-III, C-IV, D-I: the A is full |
| 143 | 34 | stem | I'rederick (the arms of F are broken) | \| A. Frederick Griffith \| I. Genetic code \| | फ्रेडरिक ग्रिफिथ (Devanagari) |
| 143 | 34 | stem | 'I'ransformation (the crossbar of T is broken) | III. Transformation | रूपांतरण (Devanagari) |
| 149 | 35 | option 1 | Λ-III, B-IV, C-I, D-II | A-III, B-IV, C-I, D-II | Λ-III, B-IV, C-I, D-II: the same gap |
| 149 | 35 | option 4 | Λ-II, B-III, C-IV, D-I | A-II, B-III, C-IV, D-I | A-II, B-III, C-IV, D-I: the A is full |
| 149 | 35 | stem | I Iormone (H without its crossbar) | \| A. GLUT-4 \| I. Hormone \| | हार्मोन (Devanagari) |
| 153 | 36 | stem | I'allopian tube (the arms of F are broken) | Which of the following is not a component of Fallopian tube? | फेलोपियन नली (Devanagari) |
| 154 | 36 | stem | I'ollowing are the stages (the arms of F are broken) | Following are the stages of pathway for conduction of an action poten… | Devanagari (हृदय में क्रिया विभव ...); no Latin letter |
| 155 | 36 | stem | Statement II : 'l'he hymen (the crossbar of T is broken) | Statement II : The hymen is torn during the first coitus only. | Devanagari; no Latin letter |
| 157 | 37 | option 2 | explanation of Λ. | Both A and R are true and R is the correct explanation of A. | R, A का सही स्पष्टीकरण है: the A is full |
| 157 | 37 | option 4 | Λ is true but R is false | A is true but R is false | A सत्य है लेकिन R असत्य है: the A is full |
| 158 | 37 | stem | Λ. Cocaine | \| A. Cocaine \| I. Effective sedative in surgery \| | A. कोकेन: the A is full |
| 159 | 37 | option 2 | Λ-IV, B-II, C-III, D-I | A-IV, B-II, C-III, D-I | Λ-IV, B-II, C-III, D-I: the same gap (Hindi option (1) also… |
| 160 | 38 | option 1 | (c) Cardiac - [Icart. (H with a broken left stem and crossbar) | (c) Cardiac - Heart. | (c) हृद – हृदय (Devanagari) |
| 161 | 38 | option 2 | Λ-II, B-I, C-III, D-IV | A-II, B-I, C-III, D-IV | A-II, B-I, C-III, D-IV: the A is full |
| 162 | 38 | option 3 | Λ-I, B-III, C-II, D-IV | A-I, B-III, C-II, D-IV | A-I, B-III, C-II, D-IV: the A is full |
| 162 | 38 | stem | A. I'ibrous joints (the arms of F are broken) | \| A. Fibrous joints \| I. Adjacent vertebrae, limited movement \| | रेशीय संधियाँ (Devanagari) |
| 163 | 39 | option 3 | Ŀ-B-D-A-C (E lacks its top arm, reads like L) | E-B-D-A-C | E-B-D-A-C (full E) |
| 163 | 39 | option 4 | B-D-Ŀ-A-C (E lacks its top arm) | B-D-E-A-C | B-D-E-A-C (full E) |
| 163 | 39 | stem | Λ. Gap 2 phase | A. Gap 2 phase | A. गैप 2 प्रावस्था (full A) |
| 164 | 39 | option 1 | Iemperature independent plasmid (left half of the T crossbar gone) | Temperature independent plasmid | तापक्रम स्वतंत्र प्लाज्मिड (Devanagari, no Latin T) |
| 165 | 39 | option 1 | Λ-II, B-I, C-III, D-IV | A-II, B-I, C-III, D-IV | A-II, B-I, C-III, D-IV (full A) |
| 166 | 39 | option 2 | Λ-IV, B-III, C-II, D-I | A-IV, B-III, C-II, D-I | A-IV, B-III, C-II, D-I (full A) |
| 167 | 39 | option 3 | Λ only | A only | केवल Λ (the same gap) |
| 170 | 40 | stem | B. I laemozoin (H crossbar gone, reads I l) | B. Haemozoin | B. हीमोजोइन (Devanagari) |
| 171 | 40 | stem | Λ. Expiratory capacity | A. Expiratory capacity | Λ. निःश्वसन क्षमता (the same gap) |
| 173 | 41 | option 2 | Both Λ and R are correct and R is the correct explanation of A. | Both A and R are correct and R is the correct explanation of A. | दोनों A और R सही हैं और R, A का सही स्पष्टीकरण है। (full A) |
| 173 | 41 | option 3 | Both Λ and R are correct but R is NOT the correct explanation of A. | Both A and R are correct but R is NOT the correct explanation of A. | दोनों A और R सही हैं लेकिन R, A का सही स्पष्टीकरण नहीं है।… |
| 173 | 41 | option 4 | Λ is correct but R is not correct. | A is correct but R is not correct. | A सही है लेकिन R गलत है। (full A) |
| 175 | 41 | option 1 | Λ-II, B-IV, C-III, D-I | A-II, B-IV, C-III, D-I | Λ-II, B-IV, C-III, D-I (the same gap) |
| 175 | 41 | stem | D. I'ilariasis (top arm of F detached) | D. Filariasis | D. फाइलेरिएसिस (Devanagari) |
| 176 | 42 | stem | Λrrange them in correct sequence. | Arrange them in correct sequence. | व्यवस्थित करो (Devanagari) |
| 176 | 42 | stem | B. IIomo sapiens (italic H crossbar gone) | B. Homo sapiens | B. होमो सैपियंस (Devanagari) |
| 177 | 42 | figure:stem | BamII I (H crossbar gone); Pst I with the P bowl faint (I'st I) | BamH I … Pst I | BamH I and Pst I (full H and P) |
| 177 | 42 | option 3 | 'I'he gene (T crossbar broken) | The gene | जीन 'X' (Devanagari, no Latin T) |
| 178 | 42 | option 2 | Iligh pO2 and Iligh pCO2 (H crossbars gone) | High pO_2 and High pCO_2 | उच्च pO2 एवं उच्च pCO2 (Devanagari for High) |
| 178 | 42 | option 4 | High H¹ concentration (horizontal stroke of + gone, a vertical tick r… | High H^+ concentration | उच्च H⁺ सांद्रता (full +; Hindi option 3 has the H¹ gap ins… |
| 180 | 43 | option 3 | Λ-II, B-III, C-IV, D-I | A-II, B-III, C-IV, D-I | A-II, B-III, C-IV, D-I (full A) |
| 180 | 43 | stem | α-'I'halassemia (left half of the T crossbar gone) | alpha-Thalassemia | α-थैलेसीमिया (Devanagari) |
| 182 | 43 | option 3 | A, B & Ŀ only (E top arm faint) | A, B & E only | केवल Λ, B और E (E printed; the A here lost its crossbar) |
| 182 | 43 | option 4 | B, C & Ŀ only (E top arm faint) | B, C & E only | केवल B, C और E (E printed) |
| 185 | 43 | option 3 | 5'ΛUGUΛΛΛGUUUΛUΛGGUΛΛGU3' | 5'AUGUAAAGUUUAUAGGUAAGU3' | 5'AUGUAAAGUUUAUAGGUAAGU3' (full A) |
| 185 | 43 | stem | 3'TACΛTGGCΛΛATΛTCCΛTTCΛ5' (six A crossbars gone) | 3'TACATGGCAAATATCCATTCA5' | 3'TACATGGCAAATATCCATTCA5' (full A throughout) |
| 187 | 44 | stem | Statement II : Λccording to Gause's principle | Statement II : According to Gause's principle | गॉसे के सिद्धांत के अनुसार (Devanagari) |
| 188 | 44 | figure:stem | GnRII, LII, (Λ), I'actors (H crossbars and A crossbar gone; F top arm… | GnRH … LH … (A) … Factors | GnRII (the same gap), एलएच, (A) full, कारक |
| 188 | 44 | option 2 | I'SH, Leydig cells… (F top arm detached) | FSH, Leydig cells, Sertoli cells, spermiogenesis | एफएसएच (Devanagari FSH) |
| 188 | 44 | option 3 | ICSII, Interstitial cells… (H crossbar gone) | ICSH, Interstitial cells, Leydig cells, spermiogenesis. | आईसीएसएच (Devanagari ICSH) |
| 189 | 45 | stem | D. IIeart is dorsal if present. | D. Heart is dorsal if present. | D. हृदय यदि उपस्थित होता है तो पृष्ठीय होता है (Devanagari) |
| 191 | 45 | stem | Λ. P wave | A. P wave | A. P तरंग (full A) |
| 191 | 45 | stem | C. 'I' wave (left half of the T crossbar gone) | C. T wave | C. T तरंग (the same partial T) |
| 192 | 45 | option 2 | Λ-I, B-III, C-II, D-IV | A-I, B-III, C-II, D-IV | A-I, B-III, C-II, D-IV (full A) |
| 192 | 45 | option 3 | Λ-IV, B-II, C-I, D-III | A-IV, B-II, C-I, D-III | Λ-IV, B-II, C-I, D-III (the same gap) |
| 193 | 46 | stem | Λ. Mesozoic Era … II. Fish & Λmphibia | A. Mesozoic Era … II. Fish & Amphibia | A. मीजोजोइक महाकल्प (full A); मत्स्य व एंफीबिया |
| 196 | 47 | option 1 | Λ-IV, B-III, C-I, D-II | A-IV, B-III, C-I, D-II | A-IV, B-III, C-I, D-II (full A) |
| 196 | 47 | option 4 | Λ-III, B-IV, C-I, D-II | A-III, B-IV, C-I, D-II | A-III, B-IV, C-I, D-II (full A) |
| 196 | 47 | stem | Λ. RNΛ polymerase III | A. RNA polymerase III | Λ. आरएनए पॉलीमरेज III (label has the same gap; RNA spelled… |
| 200 | 47 | stem | Λs per ΛBO blood grouping system | As per ABO blood grouping system | ABO रुधिर वर्ग प्रणाली (full A) |
| 200 | 47 | stem | child is O¹ (horizontal stroke of + gone) | child is O^+ | बच्चे का O⁺ (full +) |
| 200 | 47 | stem | I^Λ superscripts in A–E (A crossbars gone), e.g. I^Bi / I^Λi / ii | I^Bi / I^Ai / ii (and I^A throughout A–E) | I^Λ (the same gap) throughout A–E |
| 200 | 47 | stem | several capital I of I^A/I^B print as [ or ] (serif and stem strokes… | I^BI^B / I^AI^A / ii (I throughout) | the same [ / ] shapes for I |

## Close calls left as transcribed

- **Q84 "S_N1"**: the page raises the 1 in both columns; the corpus spells S_N1 (13 times, never S_N^1).
- **Q34's and Q43's "10^11", Q28's "^290_82 X"**: the corpus writes a positive power and a nuclide so (10^11 forms in
  81 rows, never bracketed; ^235_92U), and where the corpus writes one form the review keeps it (DECISIONS
  2026-10-22).
- **Q11 "the given logic gate"** for a circuit of three gates: loose wording (the Hindi says "logic circuit"), not a
  misprint, so not recorded.
- **Q2 "0 < chi < epsilon"**: the page's ε, a small positive number, by name.
- **Q171's "Expiratory capacity" and Q187's Statement I** are the questions' own items to be judged, not misprints.
- **Q116**: the stem describes ex-situ conservation, and none of the printed options says it; option 2 prints
  "in-situ" in both columns. A matter for D20's solve-vs-key check, not the transcription.

## Answers

From NTA's final key revised on 26.07.2024 (`2024/neet-2024-final-key-nta-revised-2024-07-26-all-codes.pdf`, sha256
825de227…), never the earlier final key (README §3): page 18 of the 52-page all-codes file, found by its header "Test
Booklet Code : S6" and carrying "FINAL ANSWER KEY (REVISED ON 26.07.2024)", the Supreme Court order of 23.07.2024 and
"EXAM HELD ON 05.05.2024". Read from the key's own text layer by a script — no model — and checked for 200 answers,
1 to 200 once each, every answer 1–4; none is multiple or dropped, as README §3 has S6 ("none"). Keys 1, 2, 3 and 4
answer 44, 56, 55 and 45 questions. Solved independently as a spot check, each matching the key: Q1 (the Balmer
lines, A-III, B-IV, C-II, D-I → 3), Q2 (A-II, B-III, C-IV, D-I → 2), Q3 (m = 4π²I/(T²B) = 1280π² × 10⁻⁵ A m² → 1,
which needs the 10⁻⁶ whose minus the scan lost), Q14 (no potential difference inside a charged shell → 1, zero) and
Q28 (286, 81 → 1). D20's full solve-against-key comparison is the rest of README §2's tier-3 check.

## Result

200 questions, 41 corrections (4 of meaning), 15 misprints kept as printed with their notes (1 `show`, 14
`record_only`), 6 places cause unsettled and read as meant — Q24's for the founder — answers 200 single; 195 lost
strokes, 190 read as meant by the extraction and 5 corrected. Next: the founder's ruling on Q24 and his approval,
which places the reviewed file at `pipeline/inputs/papers/neet-2024-s6.json`; then `pyq tag` and `pyq load`.

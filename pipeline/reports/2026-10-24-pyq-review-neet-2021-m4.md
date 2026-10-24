# pyq review — neet-2021-m4 (NEET (UG) 2021, booklet code M4)

This is the review README §7 asks for before the founder's approval: every flag of the extract run, and all 200 questions read against the booklet's page images, with the answers filled from NTA's final key.

- **Draft:** `pipeline/data/pyq/neet-2021-m4.draft.json`, from `pyq extract`. Its report, `2026-10-24-pyq-extract.md`, covers pages 2–25 (24 in all) at 150 dpi: 200 questions, every section at its declared count, 48 figure descriptions in 20 questions, ₹129.42.
- **Reviewed file:** `pipeline/data/pyq/neet-2021-m4.reviewed.json` (sha256 84f361af…). It enters `pipeline/inputs/papers/neet-2021-m4.json` only on the founder's approval.
- **Layout:** the reviewed file is written in the draft's own layout. PaperFileJson reads it and writes it back byte for byte (190,304 characters), and its checks against the register entry find no problem. So `diff` of draft against reviewed shows exactly the corrections, the 33 as-printed entries and the answers below.
- **The founder ruled on 2026-10-24 (DECISIONS):**
  - Q138: as-printed notes may sit on a figure, and Q138 carries two `show` notes, A_2+4 for the printed A_2-4 and G_(2) with NCERT's fusion brackets. The code change comes first, with its own audit, before his commit;
  - Q142's "β-galactoside": kept as printed with no note if it reads "β-galactoside permease", and with a `show` note giving NCERT's β-galactosidase if the word stands alone for the z-gene enzyme. Claude's check: both places print the word alone, in both booklets ("a gene for β-galactoside production", "produce β-galactoside."), and neither booklet prints "permease", so both carry the note;
  - Q142's "E.coli" is kept as printed with no note, as in S6: an abbreviation's full stop with no space after it is not words run together. The check is not widened;
  - approved: dimension powers unbracketed, one answer-grid form, straight quotes, units exactly as printed (Q71's "ms^-1"), Q66's "upto" shown as "up to" with a hidden note, and G6's two Δ brought into line before D31.

## Put to the founder: three points (ruled 2026-10-24, above)

**1. Q138: two wrong formulae in a figure, which the code cannot note as SPEC §6.2 asks.** Both official booklets (English, and Hindi-English) print:
- (d) as ⊕ ⚥ K_2+2 C_4 A_2−4 G_(2): the stamens' subscript has a minus where NCERT's Brassicaceae formula has a plus (2 + 4). The draft silently wrote A_2+4;
- (b) as ⊕ ⚥ K_(5) C_(5) A_5 G_2: the carpels have no fusion brackets where NCERT's Solanaceae formula (bio11 §5.9) has G_(2). The draft copied it.

Both are a wrong formula, the class SPEC §6.2 gives a line under the question (`show`), with its Hindi line due at the crop work before D31. **Here code and SPEC conflict:** an as-printed entry may sit only on the stem or an option (`QuestionAsPrinted.at`, checked by PaperFileJson), and both formulae sit in the stem's figure description. So no entry can carry them today. The reviewed file describes both as drawn, and (d)'s description adds "the stamens' subscript is printed 2 - 4, with a minus sign", so that "2-4" does not read as "2 to 4" for the solver and the alt text.
- **Recommended: let an as-printed entry sit on a figure** (`at: figure:<at>`, its `printed` span a span of that description). The change reaches `QuestionAsPrinted` and PaperFileJson's check, with tests, TECH_PLAN §2.3's line on the column and a DECISIONS row. It needs no migration, since the entry lives inside the JSONB column. V10's committed comment, which lists `at` as the stem or an option, cannot be edited, so TECH_PLAN records that it no longer holds, as it did when `display` was added. All of it is committed before your approval commit. Q138 then gets two `show` entries, A_2-4 for A_2+4 and G_2 for G_(2), and every later paper can note a figure's misprint the same way.
- **Or** keep the descriptions as they are, with no line for students: an exception to SPEC §6.2 for misprints inside figures.
- **Or** read them as meant (A_2+4, G_(2)) with no record. That departs from SPEC §6.2's "the paper's own words".
- **The key does not turn on either:** K_2+2 C_4 alone marks Brassicaceae, and K_(5) C_(5) A_5 under the arc marks Solanaceae, so option 1 stands.
- **Ruled: the recommendation.** The entries' place is `figure:stem`, and their spans are "A_2-4" and "G_2" in the stem figure's description. Where two figures share a place, the entry is held to the one that prints its span, and a figure's entry never records words run together, since a crop cannot be shown spaced (both Claude's, for the founder to overrule).

**2. Q142: "β-galactoside", printed twice (stem and option 2).** It is the setter's word, not a typesetting slip: both booklets print it, and the Hindi column prints बीटा-गैलेक्टोसाइड. A gene is inserted "for β-galactoside production", but a galactoside is a sugar, not a gene product. NCERT writes β-galactosidase, the enzyme (4 rows).
- **Recommended: keep it as printed, with a `show` note** on each of the two places: "Printed "beta-galactoside"; the enzyme beta-galactosidase is meant." The class rule gives `show` to a misspelling that forms a different real word, as G6's "Bracket series".
- **Or** keep it as printed with no note, as the setter's own wording.
- **The key does not turn on it:** option 1 (the insert inactivates amp^R) is right either way.
- **Ruled:** "β-galactoside permease", the full name of the lac y-gene product, would stand with no note; the word standing alone for the z-gene enzyme takes a `show` note giving NCERT's β-galactosidase. Claude's check: both places print the word alone, in both booklets, so both carry the note.

**3. Q142: "E.coli", with no space after the point.** SPEC §6.2 shows words the paper runs together with normal spacing and keeps the printed form on record. **Here code and SPEC conflict too:** PaperFileJson accepts a run-together entry only where the missing space sat between two letters (Claude's letters-only check, DECISIONS 2026-10-24), so "E. coli" cannot be shown spaced. NCERT prints "E. coli" in 13 rows and "E.coli" in 1.
- **Recommended: keep "E.coli" as printed, with no note,** as approved 2024 S6 keeps it twice. A point closing an abbreviation is typography, not a word run into the next.
- **Or** widen the check to a missing space after an abbreviation's point, show "E. coli" with a `record_only` note here, and bring S6's two places into line when its file is next amended.
- **Ruled: kept as printed, with no note,** as in S6. "An abbreviation's full stop with no following space isn't a run-together word under ruling 3"; the reader rules record the line, and the check is not widened.

**Readings put to the founder** (each is in the sections below; none changes a key). Approved by name on 2026-10-24:
- dimension powers stay unbracketed, as the corpus writes dimensional formulae (Q32);
- one form, "(a)-(iv), …", for every printed answer grid (Q116, Q127, Q182, Q184);
- straight quotes, as every approved paper file;
- a unit is written exactly as printed, so Q71's "ms^-1" is restored and kept with no note, as code 45's Q3 keeps "3×10^8 ms^-1";
- Q66's "upto" (four options) is shown "up to" with `record_only` notes, as words run together, since NCERT prints it both ways;
- 2023 G6's Q88 options and Q99 stem, which keep Δ where the stems and options of every other approved file write Delta, are brought into line before D31; they are on TRACKER D31's list of paper-file amendments, beside code 45's Q85.

Still Claude's, for the founder to overrule: American spellings, missing hyphens on this born-digital page, and compounds NCERT does not write are left as printed with no note.

## How it was read

- **On the saved page images, which are the bytes the calls read.** `pyq extract` kept each page's image with its call. Each local copy (`pipeline/data/pyq/pages/neet-2021-m4/`) was checked against the sha256 table of `2026-10-24-pyq-extract.md`, and 24 of 24 match. No reader opened the PDF.
- **The booklet is born digital.** Its text is vector type, not a scan, so there is no lost ink and no glyph substitution. A wrong character on the page is printed that way, and the scan rules (the gap rule, cause unsettled) do not arise.
  - **The text layer was the second witness.** The extraction fed it as the character authority (legibility 0.319). It is exact for letters and digits, but it maps symbol glyphs wrongly: '°' as '8', no '√', Greek from symbol fonts lost or garbled, subscripts run into the line. So it confirmed letters and digits, and the image settled everything else.
  - **The two official booklets agree.** The register holds a second booklet of the same code, Hindi left and English right. Its English text layer was compared with this booklet's, question by question: the words are the same in all 200 questions (a page footer aside). So every misprint below is printed in both. The Hindi column was read at Q142.
- **Every question, character by character.** Five readers split the 24 pages (2–6, 7–11, 12–16, 17–21, 22–25), each on enlarged crops of both columns with the text layer of each question. They checked every figure description against its drawing and for an answer hint, and filed 80 findings.
- **Claude checked every finding on the page image before accepting it.** Nothing was changed on a reader's word alone. Enlarged crops settled Q4's wire, Q14's v, Q17's arrows, Q19's nuclide, Q28's root, Q38's waveforms, Q79's and Q91's drawings, Q138's signs, Q140's subscript and Q197's line break.
  - **Findings not taken:** Q9's dimension powers (left unbracketed, below); Q28's root bracket and Q138's subscript brackets (close calls, below); the curly quotes printed at Q196, Q198 and Q199, which a reader wanted copied (the draft's straight quotes stand, and its 10 curly ones become straight); Q71's unit spacing (restored as printed, below); Q142's "E.coli" (point 3); Q83's "colorless", Q154's "hind gut" and Q192's "LH like" (left as printed, below).
  - **Two places no reader filed**, found by a scan of every word against the corpus after the spec audit's first pass: Q66's "upto" (four options) and Q54's "2-Bromo pentane". Both are checked on the page image and entered below.
- **Every correction is held to the notation `pyq_extract.v1` writes and the NCERT corpus spells** (DECISIONS 2026-10-22). Where the two differ, the corpus's form is used where it writes one, and the prompt's where it writes both.
- **NCERT's own spellings are not misprints, and a word NCERT spells both ways is held to the right one** (DECISIONS 2026-10-24). Q152's "auto immune" is NCERT's own: bio11 §17.5 prints "Auto immune disorder" in the same sentence on myasthenia gravis, which writes "neuromuscular" closed. "upto" (19 rows against 28), "Inspite" (4 against 5), "is referred as" (5 against 85), "complimentary" (1 against 15) and "round worm" (1 against 2) are held to the right form.

## Corrections: 71 in 33 questions

There are:
- 15 to figure descriptions, in 9 questions, and 1 printed sign the draft had silently corrected (Q138 (d), point 1 above);
- 48 of notation;
- 5 of spacing: Q102's "Inspite" shown as "In spite" and Q66's four "upto" as "up to", under the founder's run-together rule (2026-10-24; SPEC §6.2);
- 2 errors: Q71's unit, which the draft spaced and the page prints "ms^-1", and Q197's line-end hyphen in "anthropo-genic".

None is of meaning. No option or keyed answer changes.

| Q | page | field | kind | the draft had | the reviewed file reads (the page, in the corpus notation) | why |
|---|---|---|---|---|---|---|
| 4 | 2 | figure, stem | figure | circuit: two terminals; between them a rectangular network with capacitor C on top branch and capacitor C on bottom branch, and a vertical capacitor C connected between top and bottom wires in the middle | circuit: a rectangle of wires with a terminal wire leaving the middle of its left side and one leaving the middle of its right side; a capacitor C on the top side and a capacitor C on the bottom side, both left of the centre; a vertical wire at the centre carrying a capacitor C joins the top and bottom sides; the rectangle's right side is a plain wire joining the top and bottom sides | the description left out the plain wire that closes the rectangle's right side beside the centre capacitor, on which the answer turns, and where the two side capacitors sit |
| 6 | 2 | figure, stem | figure | a dipole on the central line with +q on left and -q on right | a dipole (two dots joined by a short line) drawn just above the central line, parallel to it, with +q printed under the left dot and -q under the right | the dipole's place as drawn; the charges' order and the field lines were right |
| 14 | 3 | stem | notation | (Q) ne upsilon_d | (Q) ne v_d | the page sets the drift velocity's v in the booklet's round italic v, the glyph of Q7's and Q17's speeds, which the draft writes v; NCERT's υ is written v where it is a speed (DECISIONS 2026-09-17), and the corpus writes v_d |
| 17 | 4 | figure, stem | figure | Horizontal conductor P to Q carrying 5 A (arrow towards Q); electron moving parallel above it with arrow, labelled 'Electron v = 10^5 m/s'; vertical separation marked 20 cm. | Horizontal conductor from P (left) to Q (right), its arrow pointing towards Q with 5 A printed under it; above it, parallel, the electron's line with its arrowhead at the right end, pointing the same way as the current's, labelled 'Electron v = 10^5 m/s'; a vertical line joins the left end of the electron's line to the conductor; a double-headed vertical arrow between the two lines is marked 20 cm. | the description gave no direction for the electron's arrow; every label and value it gave was right |
| 19 | 4 | stem | notation | nucleus _Z^A X undergoes | nucleus ^A_Z X undergoes | a nuclide is written mass number first, as the corpus writes ^235_92U and 2024 S6's Q28 ^290_82 X (DECISIONS 2026-10-22) |
| 19 | 4 | stem | notation | _Z^A X -> _(Z-1)B | ^A_Z X -> _(Z-1)B | as above |
| 32 | 6 | option 1 | notation | [L^(-1)] | [L^-1] | a dimension's power is written as the corpus writes dimensional formulae ([M^0 L T^-1], [M L T^-2]; T^-1 in 8 rows and T^-2 in 15, T^(-2) in 1), as this paper's Q9 already is (approved by the founder, 2026-10-24) |
| 32 | 6 | option 2 | notation | [L^(-1)] [T^(-1)] | [L^-1] [T^-1] | a dimension's power is written as the corpus writes dimensional formulae ([M^0 L T^-1], [M L T^-2]; T^-1 in 8 rows and T^-2 in 15, T^(-2) in 1), as this paper's Q9 already is (approved by the founder, 2026-10-24) |
| 32 | 6 | option 4 | notation | [L^(-2)] [T^(-1)] | [L^-2] [T^-1] | a dimension's power is written as the corpus writes dimensional formulae ([M^0 L T^-1], [M L T^-2]; T^-1 in 8 rows and T^-2 in 15, T^(-2) in 1), as this paper's Q9 already is (approved by the founder, 2026-10-24) |
| 38 | 7 | figure, stem | figure | against time marks t1 to t6. A is high t1–t2, t3–t4, t5–t6. B is high t2–t4. C is high before t2 and after t4, low t2–t4. Below it, a logic circuit: A and B go into an AND gate; B and C go into a NAND gate; both gate outputs go into an OR gate whose output is y. | against time marks t_1 to t_6, each with a dashed vertical line through the three traces. A is high t_1–t_2, t_3–t_4, t_5–t_6. B is high t_2–t_4. C is high before t_2 and after t_4, low t_2–t_4. Below it, a logic circuit: A and B go into a D-shaped gate; B (branched) and C go into a D-shaped gate with an output bubble; both outputs go into a gate with a curved input side and a pointed output, whose output is y. Inputs A, B, C and output y are drawn as small open circles. | the gates' names say what the question asks the student to work out from their symbols, so what is drawn replaces them, as 2024 S6's Q11 and 2023 G6's Q39 (DECISIONS 2026-10-21); subscripts take the underscore |
| 38 | 7 | figure, option 1 | notation | Waveform of y with marks t1 to t6: a flat line at 0 V, with short vertical tick marks at t1–t6. | Waveform of y with marks t_1 to t_6: a flat line at 0 V, with short vertical tick marks at t_1 to t_6. | subscripts take the underscore, as the paper's other descriptions |
| 38 | 7 | figure, option 2 | figure | Waveform of y: at 0 V, rising to 5 V for a middle stretch, back to 0 V, then rising to 5 V again at the end; levels labelled 5 V and 0 V. | Waveform of y: at 0 V, rising to 5 V under option 1's t_1 mark, falling to 0 V under t_3, rising to 5 V again under t_6 and staying there to the end; levels labelled 5 V and 0 V; no t marks are printed in options 2 to 4. | 'a middle stretch' misplaced the first high stretch, which runs t_1 to t_3, the waveform's left half |
| 38 | 7 | figure, option 4 | figure | Waveform of y: at 5 V, dropping to 0 V, rising to 5 V, then dropping to 0 V at the end; levels labelled 5 V and 0 V. | Waveform of y: at 5 V, dropping to 0 V under option 1's t_1 mark, rising to 5 V under t_3, then dropping to 0 V under t_6 to the end; levels labelled 5 V and 0 V. | the steps placed against option 1's marks, as option 2 |
| 39 | 7 | stem | notation | 80 µF | 80 μF | a unit's mu is the Greek character μ (U+03BC), as 2023 G6 and 2024 S6 write it; the draft took the text layer's micro sign (U+00B5) |
| 41 | 7 | figure, stem | figure | The distance from the object to the lens is marked 60 cm; | No object is drawn; a double-headed arrow from the left end of the axis to the lens is marked 60 cm; | described as drawn: the figure marks no object point |
| 60 | 10 | figure, option 1 | figure | B ends higher than A (endothermic profile). | B ends higher than A. | 'endothermic' names what the student must work out from the profile; a figure is described as drawn |
| 60 | 10 | figure, option 2 | figure | B ends lower than A (exothermic profile). | B ends lower than A. | as option 1 |
| 62 | 10 | stem | notation | + HBr -> ? ((C_6H_5CO)_2O_2) | + HBr -> ((C_6H_5CO)_2O_2) ? | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28 and 2023 G6's Q56 |
| 66 | 11 | option 1 | spacing | upto | up to | a single missing space between two words, shown spaced with the printed form kept in a record_only note (the founder, 2026-10-24; SPEC §6.2); NCERT spells it both ways (upto 19 rows, up to 28), so it is held to the right form |
| 66 | 11 | option 2 | spacing | upto | up to | a single missing space between two words, shown spaced with the printed form kept in a record_only note (the founder, 2026-10-24; SPEC §6.2); NCERT spells it both ways (upto 19 rows, up to 28), so it is held to the right form |
| 66 | 11 | option 3 | spacing | upto | up to | a single missing space between two words, shown spaced with the printed form kept in a record_only note (the founder, 2026-10-24; SPEC §6.2); NCERT spells it both ways (upto 19 rows, up to 28), so it is held to the right form |
| 66 | 11 | option 4 | spacing | upto | up to | a single missing space between two words, shown spaced with the printed form kept in a record_only note (the founder, 2026-10-24; SPEC §6.2); NCERT spells it both ways (upto 19 rows, up to 28), so it is held to the right form |
| 71 | 11 | stem | error | m s^-1] | ms^-1] | the draft spaced a unit the page prints run together; a unit is written as printed (the prompt's "Units as printed"), as code 45's Q3 keeps "3×10^8 ms^-1" with no note, and SPEC §6.2's run-together rule speaks of words (approved by the founder, 2026-10-24) |
| 79 | 12 | figure, option 1 | figure | skeletal structure: CH3–CH2–NO2 with lone pair dots on N | group labels CH_3, CH_2 and NO_2 joined by angled bonds, two dots above the N of NO_2 | not a skeletal (line-angle) formula: every group is lettered on angled bonds; subscripts take the underscore |
| 79 | 12 | figure, option 2 | figure | skeletal structure: CH3–CH2–NH–CH3 with lone pair dots on N | group labels CH_3, CH_2, NH and CH_3 joined by angled bonds, two dots above the N of NH | as option 1 |
| 79 | 12 | figure, option 3 | figure | skeletal structure: CH3–CH2–NH2 with lone pair dots on N | group labels CH_3, CH_2 and NH_2 joined by angled bonds, two dots above the N of NH_2 | as option 1 |
| 79 | 12 | figure, option 4 | figure | skeletal structure: CH3–CH2–N(CH3)–CH2–CH3, N with lone pair dots and CH3 below N | group labels CH_3, CH_2, N, CH_2 and CH_3 joined by angled bonds, two dots above the N, and a vertical bond down from the N to CH_3 | as option 1 |
| 80 | 12 | stem | notation | Acetone -> Product ((i) C_2H_5MgBr, dry Ether; (ii) H_2O, H^+) | Acetone -> ((i) C_2H_5MgBr, dry Ether; (ii) H_2O, H^+) Product | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28 and 2023 G6's Q56 |
| 81 | 12 | option 1 | notation | 2KClO_3 -> 2KCl + 3O_2 (Δ) | 2KClO_3 -> (Delta) 2KCl + 3O_2 | the condition printed over the arrow goes straight after its own arrow (DECISIONS 2026-10-22), as 2024 S6's Q28 and 2023 G6's Q56; Greek letters by name, as the prompt writes them and the corpus does (Delta in 284 rows, Δ in 53), as code 45's Q65 and Q88 and G6's Q76 |
| 81 | 12 | option 2 | notation | Cr_2O_3 + 2Al -> Al_2O_3 + 2Cr (Δ) | Cr_2O_3 + 2Al -> (Delta) Al_2O_3 + 2Cr | as option 1 |
| 86 | 13 | stem | notation | HOCl(g) -> (hν) | HOCl(g) -> (h nu) | Greek letters by name, as the prompt writes them and the corpus does: the corpus writes h nu (31 rows, never hν), as 2024 S6's Q24 |
| 86 | 13 | stem | notation | NO_2(g) -> (hν) | NO_2(g) -> (h nu) | as row (b) |
| 91 | 14 | figure, stem | figure | Reaction sequence: 2,4,6-tribromoaniline (NH_2, Br at both ortho and para) -> (NaNO_2, HCl, 0 - 5°C) -> 2,4,6-tribromobenzenediazonium chloride (N_2^+ Cl^-) -> (R) -> 1,3,5-tribromobenzene | Reaction sequence of three benzene rings: a ring with NH_2 at the top and Br on the two carbons beside it and on the carbon opposite -> (NaNO_2, HCl, 0 - 5°C) a ring with N_2^+ Cl^- at the top and Br in the same three places -> (R) a ring with Br at the upper left, the upper right and the bottom only | each condition was written as an arrow of its own ('-> (cond) ->'), which reads as four arrows where two are drawn; the condition printed over the arrow goes straight after its own arrow; the rings described as drawn rather than named |
| 96 | 15 | stem | notation | [Λ°_(H^+) | [Lambda°_(H^+) | Greek letters by name, as the prompt writes them and the corpus does, as code 45's Lambda°_+ |
| 96 | 15 | stem | notation | Λ°_(CH_3COO^-) | Lambda°_(CH_3COO^-) | as above |
| 100 | 15 | option 1 | notation | ΔU = 0, ΔS_total = 0 | Delta U = 0, Delta S_total = 0 | Greek letters by name, as the prompt writes them and the corpus does (Delta in 284 rows, Δ in 53), as code 45's Q65 and Q88 and G6's Q76 |
| 100 | 15 | option 2 | notation | ΔU ≠ 0, ΔS_total ≠ 0 | Delta U ≠ 0, Delta S_total ≠ 0 | Greek letters by name, as the prompt writes them and the corpus does (Delta in 284 rows, Δ in 53), as code 45's Q65 and Q88 and G6's Q76 |
| 100 | 15 | option 3 | notation | ΔU = 0, ΔS_total ≠ 0 | Delta U = 0, Delta S_total ≠ 0 | Greek letters by name, as the prompt writes them and the corpus does (Delta in 284 rows, Δ in 53), as code 45's Q65 and Q88 and G6's Q76 |
| 100 | 15 | option 4 | notation | ΔU ≠ 0, ΔS_total = 0 | Delta U ≠ 0, Delta S_total = 0 | Greek letters by name, as the prompt writes them and the corpus does (Delta in 284 rows, Δ in 53), as code 45's Q65 and Q88 and G6's Q76 |
| 102 | 15 | stem | spacing | Inspite of | In spite of | a single missing space between two words, shown spaced with the printed form kept in a record_only note (the founder, 2026-10-24; SPEC §6.2); NCERT spells it both ways (inspite 4 rows, in spite 5), so it is held to the right form |
| 116 | 17 | option 1 | notation | (a) (iv), (b) (i), (c) (iii), (d) (ii) | (a)-(iv), (b)-(i), (c)-(iii), (d)-(ii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 116 | 17 | option 2 | notation | (a) (iii), (b) (i), (c) (iv), (d) (ii) | (a)-(iii), (b)-(i), (c)-(iv), (d)-(ii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 116 | 17 | option 3 | notation | (a) (ii), (b) (iii), (c) (iv), (d) (i) | (a)-(ii), (b)-(iii), (c)-(iv), (d)-(i) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 116 | 17 | option 4 | notation | (a) (iv), (b) (ii), (c) (i), (d) (iii) | (a)-(iv), (b)-(ii), (c)-(i), (d)-(iii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 127 | 17 | option 1 | notation | (a) (ii), (b) (iv), (c) (i), (d) (iii) | (a)-(ii), (b)-(iv), (c)-(i), (d)-(iii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 127 | 17 | option 2 | notation | (a) (iv), (b) (iii), (c) (ii), (d) (i) | (a)-(iv), (b)-(iii), (c)-(ii), (d)-(i) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 127 | 17 | option 3 | notation | (a) (iii), (b) (i), (c) (iv), (d) (ii) | (a)-(iii), (b)-(i), (c)-(iv), (d)-(ii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 127 | 17 | option 4 | notation | (a) (ii), (b) (i), (c) (iv), (d) (iii) | (a)-(ii), (b)-(i), (c)-(iv), (d)-(iii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 131 | 18 | stem | notation | individual’s | individual's | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 138 | 18 | figure, stem | figure | G_2 with arc over C and A (epipetalous), G underlined | G_2, an arc drawn over C_(5) and A_5, G underlined | 'epipetalous' names what the arc means, which the page does not print; the arc is described, as (c)'s |
| 138 | 18 | figure, stem | **figure, a printed sign** | A_2+4 G_(2), G underlined | A_2-4 G_(2), G underlined; the stamens' subscript is printed 2 - 4, with a minus sign | the draft silently corrected a printed sign in a formula; the drawing prints a minus (a bare bar, against the clear plus of K_2+2), in both booklets; kept as drawn, with a show entry on the figure (the founder, 2026-10-24) |
| 142 | 19 | stem | notation | β-galactoside production | beta-galactoside production | Greek letters by name, as the prompt writes them and the corpus does, as 2023 G6's beta-galactosidase |
| 142 | 19 | option 2 | notation | β-galactoside. | beta-galactoside. | as the stem |
| 169 | 22 | stem | notation | “Only enzyme” | "Only enzyme" | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 169 | 22 | stem | notation | “Capability” | "Capability" | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 177 | 22 | stem | notation | ‘AB’ | 'AB' | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 177 | 22 | stem | notation | “Universal recipients” | "Universal recipients" | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 182 | 23 | option 1 | notation | (a) (iii), (b) (i), (c) (iv), (d) (ii) | (a)-(iii), (b)-(i), (c)-(iv), (d)-(ii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 182 | 23 | option 2 | notation | (a) (i), (b) (ii), (c) (iii), (d) (iv) | (a)-(i), (b)-(ii), (c)-(iii), (d)-(iv) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 182 | 23 | option 3 | notation | (a) (ii), (b) (iii), (c) (i), (d) (iv) | (a)-(ii), (b)-(iii), (c)-(i), (d)-(iv) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 182 | 23 | option 4 | notation | (a) (iv), (b) (ii), (c) (i), (d) (iii) | (a)-(iv), (b)-(ii), (c)-(i), (d)-(iii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 184 | 23 | option 1 | notation | (a) (iv), (b) (ii), (c) (i), (d) (iii) | (a)-(iv), (b)-(ii), (c)-(i), (d)-(iii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 184 | 23 | option 2 | notation | (a) (i), (b) (iii), (c) (ii), (d) (iv) | (a)-(i), (b)-(iii), (c)-(ii), (d)-(iv) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 184 | 23 | option 3 | notation | (a) (ii), (b) (iv), (c) (iii), (d) (i) | (a)-(ii), (b)-(iv), (c)-(iii), (d)-(i) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 184 | 23 | option 4 | notation | (a) (iii), (b) (i), (c) (iv), (d) (ii) | (a)-(iii), (b)-(i), (c)-(iv), (d)-(ii) | one form for the paper's printed answer grids: the draft writes 68 grid options '(a)-(iv), (b)-(i), …', as Q77 prints its pairs inline, and these 16 '(a) (iv), …' (approved by the founder, 2026-10-24) |
| 193 | 24 | stem | notation | ‘AUG’ | 'AUG' | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 193 | 24 | stem | notation | ‘AAA’ | 'AAA' | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 193 | 24 | stem | notation | ‘AAG’ | 'AAG' | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 195 | 24 | option 2 | notation | Parkinson’s | Parkinson's | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 195 | 24 | option 4 | notation | Addison’s | Addison's | straight quotes, as every approved paper file (2023 G6, 2024 S6, 2025 code 45 hold no curly quote) |
| 197 | 25 | stem | error | anthropo-genic | anthropogenic | the hyphen is a line-end break inside a narrow table cell ('anthropo-' / 'genic action'), not part of the word; NCERT writes anthropogenic |

## Checked against the saved page images

- **The figure descriptions: 48 in 20 questions.** 32 stand as the extraction wrote them. 16 are corrected above, in Q4, Q6, Q17, Q38, Q41, Q60, Q79, Q91 and Q138:
  - **Q4:** the description left out the plain wire on the rectangle's right side, which shorts the centre capacitor. The answer, 2C, turns on that wire.
  - **Q17:** the description gave no direction for the electron's arrow.
  - **Q38:** the gates were named (AND, NAND, OR), which is what the student works out from their symbols; option 2's first high stretch was misplaced.
  - **Q91:** each condition was written as an arrow of its own, which reads as four arrows where two are drawn.
  - **Q138 (d):** the draft wrote a plus where a minus is printed (point 1).
  - Q6, Q41, Q60, Q79 and Q138 (b): placement, an object the figure does not draw, profile names, a structure called skeletal that is lettered, and a word the page does not print.
- **Names in the descriptions,** by the 2026-10-21 rule (DECISIONS; Claude's, for the founder to overrule).
  - **Replaced by what is drawn:** Q38's gate names; Q60's "endothermic" and "exothermic", which is what the question asks; Q91's compound names; Q138 (b)'s "epipetalous".
  - **Kept, as descriptions that name no answer:** each was checked against its drawing.
    - Q82's "isopropyl-type" branches, the shape the name 2,6-dimethyldec-4-ene gives every option;
    - Q88's cyclohexanone ring, the reactant as drawn;
    - Q99's toluene and benzaldehyde, the reactant and the product as drawn (the question asks for X between them).
  - **Rechecked before D31:** every description is checked again for an answer hint before it serves as alt text at D31. So are the extraction's flags kept with each question in `questions.extraction`, which keep the draft's words as provenance (Q38's gate names, Q60's profile names).
- **The text-layer flags.** Q14's "upsilon" is corrected (v_d). Q62's, Q86's, Q98's, Q104's, Q138's, Q160's and Q171's are counts of words the layer prints differently (subscripts run into a formula, a plural, the word "figure" in a placeholder), not differences in the text. Q96's two degree signs are printed: Λ° is limiting molar conductivity.
- **The four section-heading flags** (pages 15, 18, 20, 23) are the booklet's own headings, printed where the register puts each section.

## The booklet's own errors: 33 as-printed entries

The transcription copies a printed misprint, except words printed run together, which are shown spaced (the founder, 2026-10-24). So 28 of the 33 entries are kept as printed in their text, Q138's two in its figure's description. Each one is recorded as an `as_printed` entry of its question. The entry holds the span as printed, the intended reading, a one-line note that quotes no more than those, and its display class (the founder, 2026-10-21):
- `show`, for a misprint that could change how a student reads the question;
- `record_only`, for a spelling or grammar slip a student reads correctly.

**Run together (Q66, Q102).** The text shows them spaced, and each `record_only` entry keeps the printed form. Q102's "Inspite" and Q66's "upto", in all four options, are two words with the space between them missing; NCERT prints each both ways, so each is held to the right form (for "upto", Claude's application of the rule, approved by the founder 2026-10-24).

**Born digital, so printed.** On this booklet a missing, extra or wrong character is what the page prints, and both booklets print it. There is no scan to have lost or substituted a glyph.

**Eight are `show`:**
- Q138's "A_2-4" and "G_2", wrong formulae drawn in the stem's figure, noted on that figure (point 1);
- Q140's "N_o", the letter o where NCERT writes N_0 (zero), a wrong symbol, as G6's Q144 "O(B)";
- Q141's "5s rRNA", which garbles the Svedberg unit, as G6's "5 srRNA";
- Q142's "beta-galactoside", twice (point 2 above);
- Q144's "motile on non-motile", "on" for "or";
- Q148's "hybridise its complimentary DNA", a different real word and a dropped "to", where NCERT writes "hybridise to its complementary DNA".

Their Hindi lines are written at the crop work before D31 (DECISIONS 2026-10-21).

**Not in this table:** Q142's "E.coli", kept as printed with no note (point 3).

| Q | at | printed | intended | the note | display |
|---|---|---|---|---|---|
| 54 | stem | 2-Bromo pentane | 2-Bromopentane | Printed "2-Bromo pentane"; 2-bromopentane is meant. | record_only |
| 66 | option 1 | upto | up to | Printed "upto"; "up to" is meant. | record_only |
| 66 | option 2 | upto | up to | Printed "upto"; "up to" is meant. | record_only |
| 66 | option 3 | upto | up to | Printed "upto"; "up to" is meant. | record_only |
| 66 | option 4 | upto | up to | Printed "upto"; "up to" is meant. | record_only |
| 80 | option 1 | 2-methyl propan-2-ol | 2-methylpropan-2-ol | Printed "2-methyl propan-2-ol"; 2-methylpropan-2-ol is meant. | record_only |
| 80 | option 4 | 2-methyl butan-2-ol | 2-methylbutan-2-ol | Printed "2-methyl butan-2-ol"; 2-methylbutan-2-ol is meant. | record_only |
| 82 | stem | 2,6-Dimethyl-dec-4-ene | 2,6-dimethyldec-4-ene | Printed "2,6-Dimethyl-dec-4-ene"; 2,6-dimethyldec-4-ene is meant. | record_only |
| 90 | stem | iso-electronic | isoelectronic | Printed "iso-electronic"; isoelectronic is meant. | record_only |
| 97 | stem | of the reaction is. Choose | of the reaction is : Choose | Printed with a full stop after "is", leaving the sentence unfinished; "is :" is meant. | record_only |
| 102 | stem | Inspite | In spite | Printed "Inspite"; "In spite" is meant. | record_only |
| 119 | stem | is referred as | is referred to as | Printed "is referred as"; "is referred to as" is meant. | record_only |
| 131 | stem | targetting | targeting | Printed "targetting"; targeting is meant. | record_only |
| 134 | stem | is referred as | is referred to as | Printed "is referred as"; "is referred to as" is meant. | record_only |
| 138 | figure, stem | G_2 | G_(2) | Printed "G_2" without brackets; G_(2), with NCERT's fusion brackets, is meant. | **show** |
| 138 | figure, stem | A_2-4 | A_2+4 | Printed "A_2-4", with a minus sign; A_2+4 is meant. | **show** |
| 140 | stem | N_o | N_0 | Printed with the letter o; N_0 (zero) is meant. | **show** |
| 141 | option 2 | 5s rRNA | 5S rRNA | Printed "5s rRNA"; "5S rRNA" is meant. | **show** |
| 142 | stem | beta-galactoside | beta-galactosidase | Printed "beta-galactoside"; the enzyme beta-galactosidase is meant. | **show** |
| 142 | option 2 | beta-galactoside | beta-galactosidase | Printed "beta-galactoside"; the enzyme beta-galactosidase is meant. | **show** |
| 144 | option 2 | motile on non-motile | motile or non-motile | Printed "motile on non-motile"; "motile or non-motile" is meant. | **show** |
| 148 | stem | Now a days | Nowadays | Printed "Now a days"; nowadays is meant. | record_only |
| 148 | stem | hybridise its complimentary DNA | hybridise to its complementary DNA | Printed "hybridise its complimentary DNA"; "hybridise to its complementary DNA" is meant. | **show** |
| 148 | option 3 | complimentarity | complementarity | Printed "complimentarity"; complementarity is meant. | record_only |
| 148 | option 4 | complimentarity | complementarity | Printed "complimentarity"; complementarity is meant. | record_only |
| 152 | stem | neuro muscular | neuromuscular | Printed "neuro muscular"; neuromuscular is meant. | record_only |
| 156 | stem | Sphincter of oddi | Sphincter of Oddi | Printed "oddi" in lower case; the sphincter of Oddi is meant. | record_only |
| 162 | option 1 | These muscle have | These muscles have | Printed "These muscle"; "These muscles" is meant. | record_only |
| 166 | stem | Veneral | Venereal | Printed "Veneral"; venereal is meant. | record_only |
| 173 | stem | Round worms | Roundworms | Printed "Round worms"; roundworms is meant. | record_only |
| 178 | option 4 | House fly | Housefly | Printed "House fly"; housefly is meant. | record_only |
| 197 | stem | Darwin Finches | Darwin's Finches | Printed "Darwin Finches"; Darwin's finches is meant. | record_only |
| 199 | stem | Myosine | Myosin | Printed "Myosine"; myosin is meant. | record_only |

## Close calls left as transcribed

Approved by the founder on 2026-10-24: dimension powers, answer grids, quotes, units as printed and G6's Δ, the first, second, third, fourth and last items below. The rest are Claude's readings, for the founder to overrule, and so is the units item's line on Q97's exponent space.

- **Dimension powers (Q9, Q32).** The corpus writes dimensional formulae unbracketed ([M^0 L T^-1], [M L T^-2]: T^-1 in 8 rows and T^-2 in 15, against T^(-2) in 1). So Q32's options lose their brackets to match Q9's.
- **Answer grids.** 17 match questions print their options as a grid under (a) (b) (c) (d); the rest print the pairs inline, as Q77 does, "(a)-(iv), …". The draft wrote 68 options in that inline form and 16 as "(a) (iv), …" (Q116, Q127, Q182 and Q184, all grids). The 16 take the paper's one form.
- **Quotes.** The draft wrote 10 curly quotes or apostrophes, the rest straight. All are straight, as in 2023 G6, 2024 S6 and code 45.
- **Units as printed (Q71, Q97).** A unit is written as the page sets it: Q71's "ms^-1" is restored from the draft's "m s^-1" and kept with no note, as code 45's Q3 keeps "3×10^8 ms^-1"; Q97's "JK^-1 mol^-1" keeps J and K run together as printed, with the space after an exponent that ends the superscript. SPEC §6.2's run-together rule speaks of words. NCERT writes "m s^-1" in 70 rows and "ms^-1" in 3; the rule that holds a word NCERT spells two ways to the right one is about spellings, and a unit's spacing is notation, which the prompt's units line governs. If the founder reads Q71's "ms^-1" as a misprint instead, it is a wrong unit, so its note would be `show`.
- **Q28 option 3.** The page prints √((R_1/R_2)), a bracketed fraction under the root. `sqrt(R_1/R_2)` stands: the function's bracket is the printed one.
- **Q138's subscripts.** The printed brackets in a floral formula mean fusion, and the corpus writes them as the subscript's own ("⊕ K_(5) C_(5) A_5 G_(2)"). So no grouping brackets are added to C_1+2+(2), A_(9)+1, A_3+3 or K_2+2, which would make K_(5) ambiguous between fusion and grouping.
- **American spellings** carry no note: Q83's "colorless", Q173's "organization", Q199's "hydrolyzes". 2024 S6 and code 45 keep "color" and "organization" with none.
- **Missing hyphens** carry no note: Q114's "Virus free plants" and Q192's "LH like activity". On a born-digital page no stroke is lost, a missing compound hyphen is not among the misprint kinds, and both read the same.
- **Compounds NCERT does not write** stay as printed, as G6's "sub stage": Q154's "hind gut" (the current books cut the cockroach; the line prints "midgut" closed) and Q178's "Fire fly". Q178's "House fly" is a misprint, since NCERT writes "housefly".
- **Q143's "G2 phase", "G1 phase".** The digit is printed small on the baseline. It is read as the subscript NCERT writes (G_2, G_1).
- **Q86's radicals.** The page prints a dot over the O of OH and over the C of Cl; `•OH + •Cl` stands.
- **2023 G6's Δ.** G6's Q88 options and Q99 stem keep Δ, where the stems and options of code 45, S6 and G6's own Q76 write Delta (figure descriptions in all three files keep Δ in places). They are on TRACKER D31's list of paper-file amendments, made at the crop work before D31, beside code 45's Q85, which waits there.

## Answers

- **The key file.** From NTA's final answer key for all codes (`2021/neet-2021-final-key-nta-all-codes.pdf`, sha256 838e0ba0…). It is tier 3: NTA's final key (result 01.11.2021), mirrored (MANIFEST.csv; README §3).
- **The page.** Page 13 of the 24-page file, found by its header "BOOK : M4". It carries "EXAM DATE: 12.09.2021" and "Result Declared on 01.11.2021".
- **How it was read.** From the key's own text layer by a script, with no model. Checked for 200 answers, 1 to 200 once each, every one 1–4.
- **Special entries.** None, as README §3 has it for M4. All 200 answers are single; keys 1, 2, 3 and 4 answer 51, 50, 54 and 45 questions.
- **Spot check.** Solved independently, each matching the key:

  | Q | working | key |
  |---|---|---|
  | Q4 | the right-hand wire shorts the centre C, leaving the two side capacitors in parallel: 2C; this needs the wire the description now gives | 2 |
  | Q5 | normal entry, 30° at the face it meets next, sin e = √3 × 1/2: 60° | 1 |
  | Q12 | Mg less the buoyancy Mg/2 | 1 |
  | Q14 | v_d = (eE/m) tau, rho = E/J, tau = m/(ne^2 rho), J = ne v_d | 1 |
  | Q15 | k = 200 N m^-1, T = 2π √(2/200) = 0.628 s | 4 |
  | Q17 | B = 5 × 10⁻⁶ T at 20 cm, F = evB = 8 × 10⁻²⁰ N | 4 |
  | Q19 | Z to Z−1 is β⁺, Z−1 to Z−3 is α, Z−3 to Z−2 is β⁻ | 3 |
  | Q20 | the voltages as rms: V = √(40² + 30²) = 50 V, I = 10√2/√2 = 10 A, Z = 5 Ω | 4 |
  | Q28 | σ ∝ 1/R: R_2/R_1 | 2 |
  | Q32 | [ML²T⁻²]/[M⁻¹L³T⁻²] = [M² L⁻¹ T⁰] | 1 |
  | Q38 | B and C are never high together, so the bubbled gate's output is always high and y is 5 V throughout; this needs the gates the description now draws | 3 |
  | Q39 | ω_0 = 50 rad/s, half-width R/2L = 4: 46 and 54 | 3 |
  | Q60 | ΔH negative (B below A), Ea 9.6 kJ above A | 2 |
  | Q62 | peroxide effect: 1-bromo-3-methylbutane | 1 |
  | Q71 | 3 × 10⁸ / 1.368 × 10⁶ = 219.3 m | 1 |
  | Q80 | acetone and C_2H_5MgBr give 2-methylbutan-2-ol | 4 |
  | Q81 | aluminium displaces chromium | 2 |
  | Q91 | ethanol replaces N_2^+ by H | 2 |
  | Q96 | α = 20/400, Ka ≈ cα² = 1.75 × 10⁻⁵ | 3 |
  | Q97 | Ea = 5 × 10³ × 8.314 = 41.6 kJ mol⁻¹ | 1 |
  | Q100 | ΔU = 0 at constant temperature, ΔS_total > 0 for an irreversible change | 3 |
  | Q138 | Fabaceae, Solanaceae, Liliaceae, Brassicaceae | 1 |
  | Q140 | the base of natural logarithms | 3 |
  | Q142 | an insert within amp^R inactivates it | 1 |

- **Still to come.** D20's full solve-against-key comparison is the rest of README §2's tier-3 check.

## Result

- **Questions:** 200.
- **Corrections:** 71 in 33 questions, none of meaning; 16 figure descriptions corrected.
- **As-printed entries:** 33 with their notes in 24 questions, 8 `show` and 25 `record_only`. 28 are kept as printed in their text, Q138's two in its figure's description; Q66's and Q102's run-together forms are shown spaced.
- **Ruled by the founder (2026-10-24):** Q138's two formulae noted on the figure, after a code change that lets a note sit there; Q142's "β-galactoside" `show`; Q142's "E.coli" as printed, with no note.
- **Answers:** 200 single.

Next: the founder's commit, which places the reviewed file at `pipeline/inputs/papers/neet-2021-m4.json` with the extract report and this report; then `pyq tag` and `pyq load`.

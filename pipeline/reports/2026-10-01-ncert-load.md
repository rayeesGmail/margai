# margai-pipeline ncert load

- run: 2026-10-01 07:01 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 3 chapters of chem11-part2 (en)
- result: ok
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 835e72960fc6fe72861d6774fc13dc092e47d286319e9876d72f2e4b0918fb1b

## page-break repairs to the model's continuation flags (deterministic, each one named)

none

## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)

- ch 7 page 2 §7.1: "(i) H_2S (g) + Cl_2 (g) -> 2 HCl (g) + S (s) (ii)" joined onto the paragraph before it (the three reactions (i)-(iii) are the items of Problem 7.1's statement ("In the reactions given below, identify … :"); a fragment item completes the sentence that introduces it (DECISIONS 2026-09-29; chem11-part1 Problem 2.5/2.18))
- ch 7 page 3 §7.2: "Reactions 7.12 to 7.14 suggest that half" joined onto the paragraph before it (set flush (x≈307) straight after the display "2 Na(s) + Cl2 (g) → 2 Na+ Cl– (s) or 2 NaCl (s)", where this book indents a new paragraph (cf. "Each of the above steps…" indented after its display): v5, a flush line after a display continues the paragraph (chem11-part1 precedent))
- ch 7 page 3 §7.2: "Oxidation : Loss of electron(s) by any species." joined onto the paragraph before it (the definition "Oxidation : …" is a fragment completing "To summarise, we may mention that" (no colon, no full stop); a fragment list item joins the sentence that introduces it (DECISIONS 2026-09-29))
- ch 7 page 3 §7.2: "Reduction : Gain of electron(s) by any species." joined onto the paragraph before it (the definition "Reduction : …" is a fragment completing "To summarise, we may mention that" (no colon, no full stop); a fragment list item joins the sentence that introduces it (DECISIONS 2026-09-29))
- ch 7 page 3 §7.2: "Oxidising agent : Acceptor of electron(s)." joined onto the paragraph before it (the definition "Oxidising agent : …" is a fragment completing "To summarise, we may mention that" (no colon, no full stop); a fragment list item joins the sentence that introduces it (DECISIONS 2026-09-29))
- ch 7 page 3 §7.2: "Reducing agent : Donor of electron(s)." joined onto the paragraph before it (the definition "Reducing agent : …" is a fragment completing "To summarise, we may mention that" (no colon, no full stop); a fragment list item joins the sentence that introduces it (DECISIONS 2026-09-29))
- ch 7 page 4 §7.2: "This splitting of the reaction under examination" joined onto the paragraph before it (inside Problem 7.2's tinted box, set flush straight after the display "H2 (g) + 2e– → 2 H–(g)"; the Solution is one worked argument set flush (DECISIONS 2026-09-29, box Solution))
- ch 7 page 4 §7.2.1: "Here, Cu(s) is oxidised to Cu^2+(aq) and Ag^+(aq)" joined onto the paragraph before it (set flush straight after the display (7.16) where this book indents a new paragraph ("By way of contrast…" below is indented): v5, a flush line after a display continues the paragraph)
- ch 7 page 5 §7.2.1: "At equilibrium, chemical tests reveal that both" continues the previous page's paragraph (p5 opens flush (x61) under Fig. 7.2 straight after p4's closing display (7.17); this book indents a new paragraph at a page or column top, so the print continues p4's "By way of contrast…" paragraph (the verify join flag))
- ch 7 page 5 §7.3: "However, as we shall see later, the charge" joined onto the paragraph before it (the right column opens flush (x307; this book indents a new paragraph at a column top, e.g. p3 R "For convenience…", p4 R "At this stage…") after the left column's last line "consequently H2 is oxidised and O2 is reduced." at x289 of 292: the paragraph runs on)
- ch 7 page 7 §7.3: "The oxidation number/state of a metal in a" continues the previous page's paragraph (p6 ends at the full measure (R y707 x305-536 of 537) and p7's text under the group table opens flush where this book indents a new paragraph at a page top: the print continues p6's paragraph (the verify join flag))
- ch 7 page 7 §7.3: "Oxidation: An increase in the oxidation number of" joined onto the paragraph before it (the definition "Oxidation: …" is one of the five term/definition items completing "To summarise, we may say that:"; each opens with a fragment, so the list joins the sentence that introduces it (DECISIONS 2026-09-29), as p3's four definitions do)
- ch 7 page 7 §7.3: "Reduction : A decrease in the oxidation number of" joined onto the paragraph before it (the definition "Reduction: …" is one of the five term/definition items completing "To summarise, we may say that:"; each opens with a fragment, so the list joins the sentence that introduces it (DECISIONS 2026-09-29), as p3's four definitions do)
- ch 7 page 7 §7.3: "Oxidising agent: A reagent which can increase the" joined onto the paragraph before it (the definition "Oxidising agent: …" is one of the five term/definition items completing "To summarise, we may say that:"; each opens with a fragment, so the list joins the sentence that introduces it (DECISIONS 2026-09-29), as p3's four definitions do)
- ch 7 page 7 §7.3: "Reducing agent: A reagent which lowers the" joined onto the paragraph before it (the definition "Reducing agent: …" is one of the five term/definition items completing "To summarise, we may say that:"; each opens with a fragment, so the list joins the sentence that introduces it (DECISIONS 2026-09-29), as p3's four definitions do)
- ch 7 page 7 §7.3: "Redox reactions: Reactions which involve change" joined onto the paragraph before it (the definition "Redox reactions: …" is one of the five term/definition items completing "To summarise, we may say that:"; each opens with a fragment, so the list joins the sentence that introduces it (DECISIONS 2026-09-29), as p3's four definitions do)
- ch 7 page 8 §7.3: "Further, Cu_2O helps sulphur in Cu_2S to increase" continues the previous page's paragraph (p8 opens inside Problem 7.4's tinted box, continuing p7's Solution; the box sets every line flush, so the print makes no paragraph break (DECISIONS 2026-09-29, box Solution; the verify join flag))
- ch 7 page 8 §7.3.1: "Either A and B or both A and B must be in the" joined onto the paragraph before it (set flush (x61) straight after the display "A + B → C", where this book indents a new paragraph: v5, a flush line after a display continues the paragraph)
- ch 7 page 8 §7.3.1: "It may carefully be noted that there is no change" joined onto the paragraph before it (set flush (x61) straight after display (7.28), where this book indents a new paragraph: v5, the paragraph continues (it resumes the decomposition examples))
- ch 7 page 8 §7.3.1: "Displacement reactions fit into two categories:" joined onto the paragraph before it (set flush (x307) straight after the display "X + YZ → XZ + Y", where this book indents a new paragraph: v5, the paragraph continues)
- ch 7 page 10 §7.3.1: "(It is to be noted with care that fluorine in" joined onto the paragraph before it (set flush (x307) straight after display (7.49), where this book indents a new paragraph ("It is of interest…" above is indented): v5, a flush line after a display continues the paragraph)
- ch 7 page 10 §7.3.1: "Disproportionation reactions are a special type of redox reactions." → "4. Disproportionation reactions Disproportionation reactions are a special type of redox reactions." (the page prints the numbered side heading "4. Disproportionation reactions" (bold italic) above it; the transcriber dropped it, while its siblings "1. Combination reactions", "2. Decomposition reactions" and "3. Displacement reactions" lead their rows (DECISIONS 2026-09-27, a side heading leads its paragraph))
- ch 7 page 11 §7.3.1: "Sometimes, we come across with certain compounds" → "The Paradox of Fractional Oxidation Number Sometimes, we come across with certain compounds" (the page prints the box title "The Paradox of Fractional Oxidation Number" over the tinted box this row opens; the transcriber dropped it (DECISIONS 2026-09-28, a boxed feature's title leads the box's first paragraph))
- ch 7 page 15 §7.3.2: "Now to equalise the number of electrons, we" joined onto the paragraph before it (inside Problem 7.10's tinted box, set flush straight after Step 5's display "MnO4–(aq) + 2H2O(l) + 3e– → MnO2(s) + 4OH–(aq)"; it finishes Step 5 (DECISIONS 2026-09-29, box Solution))
- ch 7 page 16 §7.4: "Fig.7.3" removed from the paragraph holding "This is represented by separating the oxidised" (the row carries both "Fig. 7.3" and "Fig.7.3", two spellings the paragraph prints for one figure (the Daniell cell); one link is kept)
- ch 8 page 2 §8.2.1: "(a) HC≡CCH=CHCH_3 (b) CH_2=C=CHCH_3" joined onto the paragraph before it (the items of Problem 8.1's statement, set on their own line in the box, complete its sentence (list items by corpus practice, DECISIONS 2026-09-29; chem12-part2 Example 6.7))
- ch 8 page 2 §8.2.1: "(a) sigma_(C – C): 4;" joined onto the paragraph before it (an answer line of Problem 8.1's Solution, set flush in the tinted box: a Solution set flush is one paragraph (DECISIONS 2026-09-29), led by its printed label "Solution")
- ch 8 page 2 §8.2.1: "(b) sigma_(C – C): 3;" joined onto the paragraph before it (an answer line of Problem 8.1's Solution, set flush in the tinted box: a Solution set flush is one paragraph (DECISIONS 2026-09-29), led by its printed label "Solution")
- ch 8 page 2 §8.2.1: "(a) CH_3Cl, (b) (CH_3)_2CO" joined onto the paragraph before it (the items of Problem 8.2's statement, set on their own line in the box, complete its sentence (list items by corpus practice, DECISIONS 2026-09-29; chem12-part2 Example 6.7))
- ch 8 page 2 §8.2.1: "(a) sp^3, (b) sp^3, sp^2" joined onto the paragraph before it (an answer line of Problem 8.2's Solution, set flush in the tinted box: a Solution set flush is one paragraph (DECISIONS 2026-09-29), led by its printed label "Solution")
- ch 8 page 2 §8.2.1: "(a) H_2C=O, (b) CH_3F" joined onto the paragraph before it (the items of Problem 8.3's statement, set on their own line in the box, complete its sentence (list items by corpus practice, DECISIONS 2026-09-29; chem12-part2 Example 6.7))
- ch 8 page 2 §8.2.1: "(a) sp^2 hybridised carbon" joined onto the paragraph before it (an answer line of Problem 8.3's Solution, set flush in the tinted box: a Solution set flush is one paragraph (DECISIONS 2026-09-29), led by its printed label "Solution")
- ch 8 page 4 §8.3.1: "(a) CH_3CH_2COCH_2CH_3" joined onto the paragraph before it (the items of Problem 8.4's statement, set on their own line in the box, complete its sentence (list items by corpus practice, DECISIONS 2026-09-29; chem12-part2 Example 6.7))
- ch 8 page 4 §8.3.1: "(b) CH_3CH=CH(CH_2)_3CH_3" joined onto the paragraph before it (the items of Problem 8.4's statement, set on their own line in the box, complete its sentence (list items by corpus practice, DECISIONS 2026-09-29; chem12-part2 Example 6.7))
- ch 8 page 4 §8.3.1: "Problem 8.5 For each of the following compounds" joined onto the paragraph before it (step 1 of 3 (net: Problem 8.4's bare "Solution" joins its statement): Problem 8.5 joins the "Solution" row so that "Solution Problem 8.5" is a unique start; the split below restores it)
- ch 8 page 4 §8.3.1: "Solution Problem 8.5" joined onto the paragraph before it (step 2 of 3: Problem 8.4's "Solution" heads an entirely drawn answer, which is a figure and stays out; the bare label joins the Problem statement (labels over skipped drawn content join their introducing paragraph, DECISIONS 2026-09-28, as the ch 9 rulings apply it))
- ch 8 page 4 §8.3.1: "(a) HOCH_2CH_2CH_2CH(CH_3)CH(CH_3)CH_3" joined onto the paragraph before it (the items of Problem 8.5's statement, set on their own line in the box, complete its sentence (list items by corpus practice, DECISIONS 2026-09-29; chem12-part2 Example 6.7))
- ch 8 page 4 §8.3.1: "(a) HO(CH_2)_3CH(CH_3)CH(CH_3)_2" joined onto the paragraph before it (an answer line of Problem 8.5's Solution, set flush in the tinted box: a Solution set flush is one paragraph (DECISIONS 2026-09-29), led by its printed label "Solution")
- ch 8 page 4 §8.3.1: "(b) HOCH(CN)_2" joined onto the paragraph before it (an answer line of Problem 8.5's Solution, set flush in the tinted box: a Solution set flush is one paragraph (DECISIONS 2026-09-29), led by its printed label "Solution")
- ch 8 page 4 §8.3.1: "Bond-line formula:" joined onto the paragraph before it (the sub-label "Bond-line formula:" over the drawn answers belongs to Problem 8.5's Solution, set flush in the box (DECISIONS 2026-09-29; labels over drawn content join, 2026-09-28))
- ch 8 page 4 §8.3.1: "Solution Condensed formula:" joined onto the paragraph before it (step 1 of 3 (net: Problem 8.6's bare "Solution" joins its statement): Problem 8.5's Solution joins the row before it so that Problem 8.6's "Solution" is the page's only paragraph starting "Solution"; the split below restores it)
- ch 8 page 4 §8.3.1: "Solution" joined onto the paragraph before it (step 2 of 3: Problem 8.6's "Solution" heads an entirely drawn answer, which is a figure and stays out; the bare label joins the Problem statement (labels over skipped drawn content join their introducing paragraph, DECISIONS 2026-09-28, as the ch 9 rulings apply it))
- ch 8 page 4 §8.3.1: "(b)" joined onto the paragraph before it (the label "(b)" over Problem 8.5's drawn item (b) N≡C–CH(OH)–C≡N; a label over drawn content joins the paragraph that introduces it (DECISIONS 2026-09-28). Placed after the other p4 joins so that it is the only paragraph starting "(b)")
- ch 8 page 4 §8.3.1: a new paragraph starts at "Problem 8.5 For each of the following compounds" (step 3 of 3: Problem 8.5 is its own box statement again, as transcribed)
- ch 8 page 4 §8.3.1: a new paragraph starts at "Solution Condensed formula:" (step 3 of 3: Problem 8.5's Solution is its own paragraph again, as transcribed)
- ch 8 page 6 §8.4: "(a) Alicyclic compounds" joined onto the paragraph before it (the italic heading "II Cyclic or closed chain or ring compounds" (a row of its own) leads its first item, as "I. Acyclic or open chain compounds" leads §8.4 ¶2 (side headings and list labels lead, DECISIONS 2026-09-27))
- ch 8 page 6 §8.4: "Sometimes atoms other than carbon" joined onto the paragraph before it (set flush at the column margin straight after the display of the drawn cyclopropane, cyclohexane and cyclohexene, where this book indents a new paragraph: v5, a flush line after a display continues the paragraph (chem11-part1 precedent); item (a) runs on)
- ch 8 page 6 §8.4: "These exhibit some of the properties" joined onto the paragraph before it (set flush at the column margin straight after the display of the drawn tetrahydrofuran, where this book indents a new paragraph: v5, a flush line after a display continues the paragraph (chem11-part1 precedent); item (a) runs on)
- ch 8 page 6 §8.4: "Benzenoid aromatic compounds" joined onto the paragraph before it (the label over the drawn benzene, aniline and naphthalene; labels over drawn content join the paragraph that introduces them ("…examples of various types of aromatic compounds are:", DECISIONS 2026-09-28))
- ch 8 page 6 §8.4: "Non-benzenoid compound" joined onto the paragraph before it (the label over the drawn tropone; labels over drawn content join the paragraph that introduces them (DECISIONS 2026-09-28))
- ch 8 page 8 §8.5.2: "In order to name such compounds" joined onto the paragraph before it (opens the right column flush at x307 straight after the drawn branched chains (a) and (b) that end "Branched chain hydrocarbons…", where this book indents a new paragraph ("Abbreviations are used…" below at x319): v5, the paragraph continues)
- ch 8 page 11 §8.5.3: "The –R, C_6H_5-, halogens" joined onto the paragraph before it (set flush at the column margin straight after the display the bold order of priority "-COOH, -SO3H, … –C≡C-", where this book indents a new paragraph: v5, a flush line after a display continues the paragraph (chem11-part1 precedent))
- ch 8 page 11 §8.5.3: "CH_3–CH_2–CH–CH_2–CH_2–CH–CH_2–CH_3 with OH on C-3 and CH_3 on C-6" → "CH_3–CH_2–CH(OH)–CH_2–CH_2–CH(CH_3)–CH_2–CH_3" (the page draws OH under C-3 and CH3 under C-6; the row described them in words, which v5 forbids ("Do not describe what a figure shows"); written in the bracket notation this chapter's rows already use for drawn substituents (§8.4 ¶2 "CH_3—CH(CH_3)—CH_3", §8.6.1 ¶2 "CH_3–C(CH_3)(CH_3)–CH_3", ¶3 "CH_3–CH(OH)-CH_3"), as chem12-part2 ch 7 p26 restored a drawn CH3)
- ch 8 page 13 §8.5.3: "(i) 'hexane' indicates" joined onto the paragraph before it (the row of its own "Solution" is Problem 8.9's label; a label is the first words of its paragraph (v5) and item (i) stays with it (chem11-part1 Problem 5.10 precedent); items (ii)–(v) are complete sentences and stay rows)
- ch 8 page 15 §8.5.4: "Solution (a) (b) (c) (d)" joined onto the paragraph before it (Problem 8.10's "Solution" heads an entirely drawn answer, which is a figure and stays out; the bare label joins the Problem statement (labels over skipped drawn content join their introducing paragraph, DECISIONS 2026-09-28, as the ch 9 rulings apply it); the labels (a)–(d) stay with it)
- ch 8 page 15 §8.6.1: "CH_3–CHCH_2CH_3 with CH_3 Isopentane" → "CH_3–CH(CH_3)CH_2CH_3 Isopentane" (the page draws CH3 under the CH of CH3–CHCH2CH3 (isopentane); the row's "with CH_3" describes the drawing (v5 forbids it); written in the bracket notation of the same row's neopentane)
- ch 8 page 16 §8.7: "The general reaction is depicted as follows" joined onto the paragraph before it (flush at x61 after "…and finally product(s)" (the book prints no full stop), where this book indents a new paragraph ("Substrate is that reactant…" below at x79): the paragraph runs on into the drawn scheme)
- ch 8 page 17 §8.7.1: "The heterolytic cleavage can also give" joined onto the paragraph before it (flush at x60 under the caption of Fig. 8.3(a); this book resumes a paragraph flush after a figure (p25, p27, p33 mid-sentence) and indents a new one (p23 after Fig. 8.4(b), p31 after Fig. 8.14): the paragraph runs on)
- ch 8 page 17 §8.7.1: "Carbanions are also unstable" joined onto the paragraph before it (flush at x60 under the caption of Fig. 8.3(b); a new paragraph after a figure is indented in this book (p23, p31): the paragraph runs on)
- ch 8 page 17 §8.7.2: "(i) CH_2 = CH_2 + Br_2" joined onto the paragraph before it (the displayed example (i) completes "…depends on molecule under observation. Example:"; an equation on its own line belongs to the paragraph it follows (v5; chem12-part2 precedent))
- ch 8 page 17 §8.7.2: "(ii) (Substrate) + CH_3Cl" joined onto the paragraph before it (the displayed example (ii), after (i), under the same "Example:"; a display belongs to the paragraph it follows (v5))
- ch 8 page 17 §8.7.2: "Reagents attack the reactive site" joined onto the paragraph before it (the bold italic side heading "Nucleophiles and Electrophiles" (a row of its own) leads the paragraph under it (DECISIONS 2026-09-27))
- ch 8 page 18 §8.7.2: "(a) CH_3–SCH_3, (b) CH_3–CN" joined onto the paragraph before it (the items of Problem 8.11's statement, set on their own line in the box, complete its sentence (list items by corpus practice, DECISIONS 2026-09-29; chem12-part2 Example 6.7))
- ch 8 page 18 §8.7.2: "Electrophiles: BF_3" joined onto the paragraph before it (Problem 8.12's Solution runs flush in the box from "Nucleophiles: …" into "Electrophiles: …": a Solution set flush is one paragraph (DECISIONS 2026-09-29))
- ch 8 page 19 §8.7.3: "(i) from pi bond to adjacent bond position" joined onto the paragraph before it (labels (i)–(iii) over the drawn curved-arrow shifts, each a fragment, complete "Presentation of shifting of electron pair is given below :" (labels over drawn content join, DECISIONS 2026-09-28; fragments join, 2026-09-29))
- ch 8 page 19 §8.7.3: "(ii) from pi bond to adjacent atom" joined onto the paragraph before it (as (i))
- ch 8 page 19 §8.7.3: "(iii) from atom to adjacent bond position" joined onto the paragraph before it (as (i))
- ch 8 page 19 §8.7.4: "cause permanent polarisation" → "cause permanent polarlisation" (the page prints "polarlisation" (p19 L y593, image and decoded layer); the transcriber silently corrected it; the book's own errors are restored as printed (DECISIONS 2026-09-29) and go to the errata list)
- ch 8 page 21 §8.7.6: "[I: Most stable" joined onto the paragraph before it (Problem 8.17's Solution continues flush in the box after "Stability: I > II > III": a Solution set flush is one paragraph (DECISIONS 2026-09-29))
- ch 8 page 21 §8.7.7: "+R effect: – halogen" joined onto the paragraph before it (the list lines "+R effect: …" and "– R effect: …" are fragments completing "…effects are as follows:" (list items by corpus practice, DECISIONS 2026-09-29))
- ch 8 page 21 §8.7.7: "– R effect: – COOH" joined onto the paragraph before it (as "+R effect")
- ch 8 page 22 §8.7.7: "The presence of alternate single and double bonds" starts a new paragraph, not the previous page's (p22 opens indented at x79 (margin 61): the print starts a new paragraph at the top of the page; the row ran p21's "– R effect" list onto it)
- ch 8 page 23 §8.7.10: "You will be studying these reactions" joined onto the paragraph before it (flush at x305 straight after the indented list (i)–(iv), where this book indents a new paragraph: the paragraph runs on (chem11-part1 ch 1 precedent, a flush resumption after a list))
- ch 8 page 27 §8.8.5: "(a) Adsorption chromatography, and" joined onto the paragraph before it (items (a), (b) are fragments completing "…Two of these are:" (fragments join, DECISIONS 2026-09-29))
- ch 8 page 27 §8.8.5: "(b) Partition chromatography." joined onto the paragraph before it (as (a))
- ch 8 page 27 §8.8.5: "(a) Column chromatography, and" joined onto the paragraph before it (items (a), (b) are fragments completing "Following are two main types of chromatographic techniques based on the principle of differential adsorption." (fragments join, DECISIONS 2026-09-29))
- ch 8 page 27 §8.8.5: "(b) Thin layer chromatography." joined onto the paragraph before it (as (a))
- ch 8 page 28 §8.8.5: "The glass plate is then placed" joined onto the paragraph before it (opens the right column flush at x306 after the left column's TLC paragraph, where this book indents a new paragraph ("The spots of coloured compounds…" below is indented): the paragraph runs on)
- ch 8 page 30 §8.9.2: "(a) The sodium fusion extract is acidified with acetic acid" joined onto the paragraph before it (the bold italic heading "(B) Test for Sulphur" (a row of its own) leads its first item, as "(A) Test for Nitrogen" leads §8.9.2 ¶4 (DECISIONS 2026-09-27))
- ch 8 page 30 §8.9.2: "The sodium fusion extract is acidified with nitric acid" joined onto the paragraph before it (the heading "(C) Test for Halogens" (a row of its own) leads the paragraph under it (DECISIONS 2026-09-27))
- ch 8 page 30 §8.9.2: "If nitrogen or sulphur is also present" joined onto the paragraph before it (set flush at the column margin straight after the display "X– + Ag+ → AgX" and its note "X represents a halogen – Cl, Br or I.", where this book indents a new paragraph: v5, a flush line after a display continues the paragraph (chem11-part1 precedent))
- ch 8 page 30 §8.9.2: "The compound is heated with an oxidising agent" joined onto the paragraph before it (the heading "(D) Test for Phosphorus" (a row of its own) leads the paragraph under it (DECISIONS 2026-09-27))
- ch 8 page 31 §8.10.2: "Let the mass of organic compound = m g" joined onto the paragraph before it (the derivation block ("Let the mass …" and its equations) is set flush straight after the paragraph that introduces the method, where this book indents a new paragraph: v5, an equation on its own line belongs to the paragraph it follows)
- ch 8 page 31 §8.10.2: "Where p_1 and V_1 are the pressure" joined onto the paragraph before it (set flush at the column margin straight after the display "(Let it be V mL)", where this book indents a new paragraph: v5, a flush line after a display continues the paragraph (chem11-part1 precedent))
- ch 8 page 31 §8.10.1: "The mass of water produced is determined" starts a new paragraph, not the previous page's (p31's text opens indented at x78 (margin 60) under Fig. 8.14: the print starts a new paragraph; the row ran p30's paragraph and its equation onto it)
- ch 8 page 32 §8.10.2: "V mL N_2 at STP weighs" continues the previous page's paragraph (p32's text opens flush at x61 under the Dumas figure with the rest of p31's derivation ("22400 mL N2 at STP weighs 28 g." → "V mL N2 at STP weighs = …"): the paragraph runs on across the page)
- ch 8 page 34 §8.10.3: "Let the mass of organic compound taken = m g Mass of AgX" joined onto the paragraph before it (the derivation block ("Let the mass …" and its equations) is set flush straight after the paragraph that introduces the method, where this book indents a new paragraph: v5, an equation on its own line belongs to the paragraph it follows)
- ch 8 page 34 §8.10.4: "Let the mass of organic compound taken = m g and the mass of barium" joined onto the paragraph before it (the derivation block ("Let the mass …" and its equations) is set flush straight after the paragraph that introduces the method, where this book indents a new paragraph: v5, an equation on its own line belongs to the paragraph it follows)
- ch 8 page 35 §8.10.5: "Let the mass of organic compound taken = m g and mass of ammonium" joined onto the paragraph before it (the derivation block ("Let the mass …" and its equations) is set flush straight after the paragraph that introduces the method, where this book indents a new paragraph: v5, an equation on its own line belongs to the paragraph it follows)
- ch 8 page 35 §8.10.5: "If phosphorus is estimated as Mg_2P_2O_7" joined onto the paragraph before it (set flush at the column margin straight after the display "Percentage of phosphorus = 31 × m1 × 100 / (1877 × m) %", where this book indents a new paragraph: v5, a flush line after a display continues the paragraph (chem11-part1 precedent))
- ch 8 page 35 §8.10.5: "where, 222 u is the molar mass" joined onto the paragraph before it (a lower-case row after the display that ends ¶3 completes its sentence (Chemistry ruling 2026-09-27); the display itself is cut off in the book ("= 62 × m1 × 100 / 222 ×"), kept as printed — errata)
- ch 8 page 35 §8.10.6: "Thus 88 g carbon dioxide is obtained" joined onto the paragraph before it (flush at x305 after "…two moles of carbondioxide.", where this book indents a new paragraph ("The percentage of oxygen can be derived…" below is indented): the paragraph runs on (chem11-part1 ch 1 precedent))
- ch 8 page 35 §8.10.6: "Let the mass of organic compound taken be m g" joined onto the paragraph before it (the derivation block ("Let the mass …" and its equations) is set flush straight after the paragraph that introduces the method, where this book indents a new paragraph: v5, an equation on its own line belongs to the paragraph it follows)
- ch 9 page 3 §9.2.1: "CH_3 – C – CH_2 – CH_3 with CH_3 groups above and below" → "CH_3 – C(CH_3)(CH_3) – CH_2 – CH_3" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 4 §9.2.1: "Solution" joined onto the paragraph before it (Problem 9.2's "Solution" heads the box's solution table (structures of –C5H11, corresponding alcohols, names), a table the transcriber rightly skipped; the bare label joins the Problem statement that introduces it (label ruling 2026-09-28, by analogy))
- ch 9 page 6 §9.2.1: "(ii) 7 6 5 4 3 2 1" joined onto the paragraph before it (Problem 9.5's Solution is set flush in its tinted box, (i) and (ii) under one "Solution"; a flush box Solution is one paragraph (2026-09-29), as ¶5, ¶12 and ¶19 carry theirs)
- ch 9 page 6 §9.2.1: "C^1 – ^2C – ^3C – ^4C – ^5C (with CH_3 on C2, and CH_3, C_2H_5 substituents)" → "C^1 – ^2C(CH_3)(CH_3) – ^3C(C_2H_5) – ^4C – ^5C" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 6 §9.2.1: "CH_3 – C – CH – CH_2 – CH_3 (with CH_3, CH_3 and C_2H_5 substituents)" → "CH_3 – C(CH_3)(CH_3) – CH(C_2H_5) – CH_2 – CH_3" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 6 §9.2.1: "CH_3 – CH_2 – CH – C – CH – CH_2 – CH_3 (with CH_3, CH_3, CH_3 substituents)" → "CH_3 – CH_2 – CH(CH_3) – C(CH_3)(CH_3) – CH(CH_3) – CH_2 – CH_3" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 6 §9.2.1: "CH_3 – CH – CH_2 – CH_2 – CH – CH_3 (with CH_3 and CH_3 substituents)" → "CH_3 – CH(CH_3) – CH_2 – CH_2 – CH(CH_3) – CH_3" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 6 §9.2.1: "CH_3 – CH – CH_2 – CH_2 – CH_3 (with C_2H_5 substituent)" → "CH_3 – CH(C_2H_5) – CH_2 – CH_2 – CH_3" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 6 §9.2.1: "CH_3–CH_2–CH–CH_2–CH–CH_2–CH_3 (with CH_3 and C_2H_5 substituents)" → "CH_3–CH_2–CH(CH_3)–CH_2–CH(C_2H_5)–CH_2–CH_3" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 6 §9.2.1: "iii) Attach ethyl group at carbon 3 and" starts a new paragraph, not the previous page's (p6 opens with step "iii)" of the four numbered steps, each a complete sentence set with a hanging indent; i), ii) and iv) are rows and the continuation flag glued iii) onto ii) (list-item ruling 2026-09-29: complete-sentence items stay rows))
- ch 9 page 7 §9.2.2: "i) Sodium salts of carboxylic acids o" joined onto the paragraph before it ("3. From carboxylic acids" is the numbered side heading (Bookman-DemiItalic, blue) over this item; it leads it, as "2. From alkyl halides i) Alkyl halides…" leads ¶3 (side-heading ruling 2026-09-27))
- ch 9 page 7 §9.2.3: "Alkanes are almost non-polar molecules" joined onto the paragraph before it ("Physical properties" is the unnumbered side heading (blue italic) over this paragraph; it leads it (side-heading ruling 2026-09-27), as "Chemical properties" leads ¶5)
- ch 9 page 9 §9.2.3: "(i) Initiation : The reaction is initiat" joined onto the paragraph before it ("Mechanism" is the side heading (blue italic) over the three steps; it leads the first (side-heading ruling 2026-09-27; chem12-part2's box title "Mechanism" led its box, 2026-09-28))
- ch 9 page 9 §9.2.3: "Alkanes on heating in the presence of ai" joined onto the paragraph before it ("2. Combustion" is the numbered side heading over this paragraph; it leads it, as "1. Substitution reactions" leads ¶6 (side-heading ruling 2026-09-27))
- ch 9 page 9 §9.2.3: "CH_3–CH_3 + Cl_2 --(hv)-> CH_3–CH_2Cl + HCl Chloroethane (9.14)" → "CH_3–CH_3 + C1_2 --(hv)-> CH_3–CH_2C1 + HC1 Chloroethane (9.14)" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "Cl–Cl --(hv/homolysis)-> + 2 Cl_dot" → "C1–C1 --(hv/homolysis)-> + 2 C1_dot" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "(a) CH_4 + Cl_dot --(hv)-> C_dot H_3 + H–Cl" → "(a) CH_4 + C1_dot --(hv)-> C_dot H_3 + H–C1" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "(b) C_dot H_3 + Cl–Cl --(hv)-> CH_3 – Cl + Cl_dot" → "(b) C_dot H_3 + C1–C1 --(hv)-> CH_3 – C1 + C1_dot" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "CH_3Cl + Cl_dot -> C_dot H_2Cl + HCl C_dot H_2Cl + Cl– Cl -> CH_2Cl_2 + Cl_dot" → "CH_3C1 + C1_dot -> C_dot H_2C1 + HC1 C_dot H_2C1 + C1– C1 -> CH_2C1_2 + C1_dot" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "(a) Cl_dot + Cl_dot -> Cl–Cl (b) H_3C_dot + C_dot H_3 -> H_3C– CH_3 (c) C_dot H_3 + Cl_dot -> H_3C–Cl" → "(a) C1_dot + C1_dot -> C1–C1 (b) H_3C_dot + C_dot H_3 -> H_3C– CH_3 (c) C_dot H_3 + C1_dot -> H_3C–C1" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "Delta_c H^è – 890" → "Ä_c H^è – 890" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "Delta_c H^è = −2875.84" → "Ä_c H^è = −2875.84" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 9 §9.2.3: "Though in (c), CH_3 – Cl the one" → "Though in (c), CH_3 – Cl, the one" (the page prints a comma after "CH3 – Cl" ("Though in (c), CH3 – Cl,  the one of the"); the row dropped it)
- ch 9 page 10 §9.2.3: "Alkanes on heating with a regulated supply" → "3. Controlled oxidation Alkanes on heating with a regulated supply" (the page prints the numbered side heading "3. Controlled oxidation" (Bookman-DemiItalic, blue) above this paragraph; the transcriber dropped it — it leads the paragraph, as "1. Substitution reactions" and "2. Combustion" do (side-heading ruling 2026-09-27))
- ch 9 page 10 §9.2.3: "n-Alkanes on heating in the presence of anhydrous" → "4. Isomerisation n-Alkanes on heating in the presence of anhydrous" (the page prints the numbered side heading "4. Isomerisation" (Bookman-DemiItalic, blue) above this paragraph; the transcriber dropped it — it leads the paragraph, as "1. Substitution reactions" and "2. Combustion" do (side-heading ruling 2026-09-27))
- ch 9 page 10 §9.2.3: "n-Alkanes having six or more carbon atoms" → "5. Aromatization n-Alkanes having six or more carbon atoms" (the page prints the numbered side heading "5. Aromatization" (Bookman-DemiItalic, blue) above this paragraph; the transcriber dropped it — it leads the paragraph, as "1. Substitution reactions" and "2. Combustion" do (side-heading ruling 2026-09-27))
- ch 9 page 10 §9.2.3: "Methane reacts with steam at 1273 K" → "6. Reaction with steam Methane reacts with steam at 1273 K" (the page prints the numbered side heading "6. Reaction with steam" (Bookman-DemiItalic, blue) above this paragraph; the transcriber dropped it — it leads the paragraph, as "1. Substitution reactions" and "2. Combustion" do (side-heading ruling 2026-09-27))
- ch 9 page 10 §9.2.3: "Higher alkanes on heating to higher temperature" → "7. Pyrolysis Higher alkanes on heating to higher temperature" (the page prints the numbered side heading "7. Pyrolysis" (Bookman-DemiItalic, blue) above this paragraph; the transcriber dropped it — it leads the paragraph, as "1. Substitution reactions" and "2. Combustion" do (side-heading ruling 2026-09-27))
- ch 9 page 10 §9.2.3: "[Anhy. AlCl_3/HCl]" → "[Anhy, AICI_3/ HCI]" (the page prints this formula with digit 1 (capital I on p10, Ä for Δ on p9) where the row normalised it; a garble the book prints is restored as printed (DECISIONS 2026-09-29) — errata list)
- ch 9 page 10 §9.2.3: "CH_3CH–(CH_2)_2–CH_3 + CH_3CH_2–CH–CH_2–CH_3 with CH_3 and CH_3" → "CH_3CH(CH_3)–(CH_2)_2–CH_3 + CH_3CH_2–CH(CH_3)–CH_2–CH_3" (the drawing sets these substituents on vertical bonds; the row described them in words (v5: do not describe what a figure shows) — rewritten as bracket notation of exactly what is drawn (precedent ch 8 §8.6.1 ¶2, chem12-part2 ch 7 p26))
- ch 9 page 15 §9.3.3: "Problem 9.11 Which of the following compounds" joined onto the paragraph before it (step 1 of 3 (net: Problem 9.10's bare "Solution" joins its statement): ¶13 joins the "Solution" row so that "Solution Problem 9.11" is a unique start; the split below restores ¶13)
- ch 9 page 15 §9.3.3: "Solution Problem 9.11" joined onto the paragraph before it (step 2 of 3: Problem 9.10's "Solution" heads the drawn cis/trans structures and their names, which are figures and stay out; the bare label joins the Problem statement (label ruling 2026-09-28, by analogy))
- ch 9 page 15 §9.3.3: a new paragraph starts at "Problem 9.11 Which of the following compounds" (step 3 of 3: Problem 9.11 is its own box statement again, as transcribed)
- ch 9 page 16 §9.3.5: "Alkenes as a class resemble alkanes in physical properties" joined onto the paragraph before it (the side heading "Physical properties" (¶1, a row of its own) leads this paragraph; the verifier could not find ¶1 alone on the page)
- ch 9 page 16 §9.3.5: "Alkenes are the rich source of loosely held" joined onto the paragraph before it (the side heading "Chemical properties" (¶3, a row of its own) leads this paragraph; the verifier could not find ¶3 alone on the page)
- ch 9 page 17 §9.3.5: "Addition reactions of HBr to symmetrical alkenes (similar groups" joined onto the paragraph before it (the side heading "Addition reaction of HBr to symmetrical alkenes" (¶8, a row of its own) leads this paragraph)
- ch 9 page 17 §9.3.5: "How will H – Br add to propene ?" joined onto the paragraph before it (the side heading "Addition reaction of HBr to unsymmetrical alkenes (Markovnikov Rule)" (¶10, a row of its own) leads this paragraph)
- ch 9 page 17 §9.3.5: "Hydrogen bromide provides an electrophile" joined onto the paragraph before it (the side heading "Mechanism" (¶13, a row of its own) leads this paragraph, as chem12-part2's "Mechanism" rows were joined)
- ch 9 page 17 §9.3.5: "attacked by Br– ion" → "attacked by Br^- ion" (the page prints the bromide ion with a superscript minus (Br⁻); the prompt writes a charge with a caret, as the book's other six such rows do (chem12-part1 ch 3 precedent "I^-"))
- ch 9 page 17 §9.3.5: "CH_2 – CH_2 with Br, Br" → "CH_2(Br) – CH_2(Br)" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 17 §9.3.5: "CH_3 – CH – CH_2 with Cl, Cl" → "CH_3 – CH(Cl) – CH_2(Cl)" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 17 §9.3.5: "CH_3–CH_2–CHCH_3 with Br" → "CH_3–CH_2–CH(Br)CH_3" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 17 §9.3.5: "I CH_3–CH–CH_3 with Br," → "I CH_3–CH(Br)–CH_3," (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 18 §9.3.5: "(i) in the absence of peroxide and" joined onto the paragraph before it (item (i) is a fragment completing Problem 9.12's question; a fragment joins the sentence that introduces it (founder, DECISIONS 2026-09-29, list items by corpus practice))
- ch 9 page 18 §9.3.5: "(ii) in the presence of peroxide." joined onto the paragraph before it (item (ii) is the rest of the same fragment list)
- ch 9 page 18 §9.3.5: "Solution" joined onto the paragraph before it (the label "Solution" heads a solution drawn entirely as two reaction schemes, which v5 skips (reactions wait on D17); a label over skipped drawn reactions joins the paragraph that introduces them (founder, DECISIONS 2026-09-28))
- ch 9 page 18 §9.3.5: "In the presence of peroxide, addition of HBr to unsymmetrical" → "Anti Markovnikov addition or peroxide effect or Kharash effect In the presence of peroxide, addition of HBr to unsymmetrical" (the page prints the italic side heading "Anti Markovnikov addition or peroxide effect or Kharash effect" above the paragraph; the transcriber dropped it (restored as the 2026-09-27 precedents restored "Discovery of Neutron"))
- ch 9 page 20 §9.3.5: "—(CH–CH_2)—_n | CH_3" → "—(CH(CH_3)–CH_2)—_n" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 20 §9.4.1: "H_3C–CH–C≡CH | CH_3" → "H_3C–CH(CH_3)–C≡CH" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 21 §9.4.1: "Position and chain isomerism shown by different pairs." joined onto the paragraph before it (Problem 9.13's Solution is set flush in its tinted box, so a capitalised row after a display inside it is the same paragraph (founder, DECISIONS 2026-09-29))
- ch 9 page 21 §9.4.1: "HC≡C–CH–CH_2–CH_3 | CH_3" → "HC≡C–CH(CH_3)–CH_2–CH_3" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 21 §9.4.1: "HC≡C–CH_2–CH–CH_3 | CH_3" → "HC≡C–CH_2–CH(CH_3)–CH_3" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 21 §9.4.1: "CH_3–C≡C–CH–CH_3 | CH_3" → "CH_3–C≡C–CH(CH_3)–CH_3" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 21 §9.4.1: "HC≡C–C–CH_3 with CH_3 above and CH_3 below" → "HC≡C–C(CH_3)(CH_3)–CH_3" (a substituent drawn on a vertical bond is written in the chapter's bracket notation of exactly what the drawing shows, not described in words (cross-fork ruling; precedent chem12-part2 ch 7 p26))
- ch 9 page 22 §9.4.4: "Physical properties of alkynes follow the same" joined onto the paragraph before it (the side heading "Physical properties" (¶1, a row of its own) leads this paragraph)
- ch 9 page 22 §9.4.4: "Alkynes show acidic nature, addition reactions" joined onto the paragraph before it (the side heading "Chemical properties" (¶3, a row of its own) leads this paragraph)
- ch 9 page 22 §9.4.4: "i) CH ≡ CH > H_2C –CH_2" joined onto the paragraph before it (item i) is a formula fragment completing ¶6's "…follow the following trend in their acidic behaviour :"; a fragment joins the sentence that introduces it (DECISIONS 2026-09-29))
- ch 9 page 22 §9.4.4: "ii) HC ≡ CH > CH_3 –C≡ CH" joined onto the paragraph before it (item ii) is the rest of the same fragment list)
- ch 9 page 23 §9.4.4: "CH_3–C≡CH + H–Br -> [CH_3–C(Br) = CH_2]" joined onto the paragraph before it (the second displayed equation of item (iii), set at the head of the right column after (9.65); an equation printed on its own line belongs to the paragraph it follows (v5))
- ch 9 page 23 §9.4.4: "(a) Linear polymerisation:" joined onto the paragraph before it (the standalone label "(v) Polymerisation" (¶17, a row of its own) leads its "(a)" item (ruling 2026-09-27, by analogy: a standalone numbered label leads its item))
- ch 9 page 24 §9.4.4: "Solution" joined onto the paragraph before it (the label "Solution" heads a solution drawn entirely as a reaction scheme (skipped, D17); a label over skipped drawn reactions joins the paragraph that introduces them (DECISIONS 2026-09-28))
- ch 9 page 26 §9.5.2: "According to Valence Bond Theory, the concept" → "Resonance and stability of benzene According to Valence Bond Theory, the concept" (the page prints the italic side heading "Resonance and stability of benzene" above the paragraph; the transcriber dropped it)
- ch 9 page 27 §9.5.2: "Fig. 9.6 (a)" removed from the paragraph holding "The six pi electrons are thus delocalised" (the page prints "as shown in Fig. 9.6 (a) or (b)", a stale number: Fig. 9.6 is p21's ethyne orbital picture, and the localised-pi structures the sentence means are Fig. 9.7(a)/(b) on p26 (precedent: chem12-part1 ch 2 p18, the stale "Fig. 3.7"))
- ch 9 page 27 §9.5.2: "Fig. 9.6 (b)" removed from the paragraph holding "The six pi electrons are thus delocalised" (the page prints "as shown in Fig. 9.6 (a) or (b)", a stale number: Fig. 9.6 is p21's ethyne orbital picture, and the localised-pi structures the sentence means are Fig. 9.7(a)/(b) on p26 (precedent: chem12-part1 ch 2 p18, the stale "Fig. 3.7"))
- ch 9 page 27 §9.5.2: "Fig. 9.7(a)" added to the paragraph holding "The six pi electrons are thus delocalised" (links the figures the stale "Fig. 9.6 (a) or (b)" means (Kekulé's localised pi bonds, p26), in the spelling ¶8 carries)
- ch 9 page 27 §9.5.2: "Fig. 9.7(b)" added to the paragraph holding "The six pi electrons are thus delocalised" (links the figures the stale "Fig. 9.6 (a) or (b)" means (Kekulé's localised pi bonds, p26), in the spelling ¶8 carries)
- ch 9 page 28 §9.5.5: "alkylbenene is formed. (9.75) (9.76)" → "alkylbenene is formed. (9.75) (9.76) Why do we get isopropyl benzene on treating benzene with 1-chloropropane instead of n-propyl benzene?" (the page prints the in-text question "Why do we get isopropyl benzene on treating benzene with 1-chloropropane instead of n-propyl benzene?" as an indented paragraph after (9.76); the transcriber skipped it (it kept p15's "Will propene thus obtained show geometrical isomerism?"); restored here and split off below)
- ch 9 page 28 §9.5.5: a new paragraph starts at "Why do we get isopropyl benzene on treating benzene" (the restored question is its own indented paragraph in the print)
- ch 9 page 29 §9.5.5: "(a) Generation of the eletrophile" joined onto the paragraph before it (the three steps (a)–(c) are fragments completing ¶10's "…supposed to proceed via the following three steps:"; a fragment joins the sentence that introduces it (DECISIONS 2026-09-29))
rulings on verifier flags that change no text: 44

## zero exponents the book set as a degree sign (deterministic, each one named)

none

## figure_refs that are not figure or table labels — dropped

none

## ncert_paragraphs

| inserted | updated | unchanged |
|---|---|---|
| 1 | 263 | 270 |

## pages per chapter

| chapter | pages extracted | pages with text | paragraphs | text yield |
|---|---|---|---|---|
| 7 | 21 | 17 | 127 | 81.0% |
| 8 | 39 | 34 | 215 | 87.2% |
| 9 | 33 | 31 | 192 | 93.9% |

## paragraphs that begin in the middle of the previous one's sentence

a band boundary is not a paragraph boundary; these are where it was read as one
none

## coverage for the book

| pages rendered | pages extracted | pages with text | paragraphs | coverage |
|---|---|---|---|---|
| 93 | 93 | 82 | 534 | 100.0% |

## addresses in the database this extraction no longer carried — deleted

- ch 7 §7.1 ¶14
- ch 7 §7.2 ¶10
- ch 7 §7.2 ¶11
- ch 7 §7.2 ¶6
- ch 7 §7.2 ¶7
- ch 7 §7.2 ¶8
- ch 7 §7.2 ¶9
- ch 7 §7.2.1 ¶10
- ch 7 §7.2.1 ¶9
- ch 7 §7.3 ¶20
- ch 7 §7.3 ¶21
- ch 7 §7.3 ¶22
- ch 7 §7.3 ¶23
- ch 7 §7.3 ¶24
- ch 7 §7.3 ¶25
- ch 7 §7.3 ¶26
- ch 7 §7.3 ¶27
- ch 7 §7.3.1 ¶29
- ch 7 §7.3.1 ¶30
- ch 7 §7.3.1 ¶31
- ch 7 §7.3.1 ¶32
- ch 7 §7.3.2 ¶41
- ch 8 §8.10.2 ¶10
- ch 8 §8.10.2 ¶11
- ch 8 §8.10.2 ¶12
- ch 8 §8.10.3 ¶4
- ch 8 §8.10.4 ¶4
- ch 8 §8.10.5 ¶2
- ch 8 §8.10.5 ¶3
- ch 8 §8.10.5 ¶4
- ch 8 §8.10.6 ¶6
- ch 8 §8.10.6 ¶7
- ch 8 §8.2.1 ¶10
- ch 8 §8.2.1 ¶11
- ch 8 §8.2.1 ¶12
- ch 8 §8.2.1 ¶13
- ch 8 §8.2.1 ¶14
- ch 8 §8.2.1 ¶15
- ch 8 §8.2.1 ¶9
- ch 8 §8.3.1 ¶11
- ch 8 §8.3.1 ¶12
- ch 8 §8.3.1 ¶13
- ch 8 §8.3.1 ¶14
- ch 8 §8.3.1 ¶15
- ch 8 §8.3.1 ¶16
- ch 8 §8.3.1 ¶17
- ch 8 §8.3.1 ¶18
- ch 8 §8.3.1 ¶19
- ch 8 §8.4 ¶10
- ch 8 §8.4 ¶6
- ch 8 §8.4 ¶7
- ch 8 §8.4 ¶8
- ch 8 §8.4 ¶9
- ch 8 §8.5.2 ¶17
- ch 8 §8.5.3 ¶23
- ch 8 §8.5.3 ¶24
- ch 8 §8.5.4 ¶8
- ch 8 §8.7 ¶5
- ch 8 §8.7.1 ¶8
- ch 8 §8.7.1 ¶9
- ch 8 §8.7.10 ¶2
- ch 8 §8.7.2 ¶11
- ch 8 §8.7.2 ¶12
- ch 8 §8.7.2 ¶13
- ch 8 §8.7.2 ¶14
- ch 8 §8.7.2 ¶15
- ch 8 §8.7.3 ¶4
- ch 8 §8.7.3 ¶5
- ch 8 §8.7.3 ¶6
- ch 8 §8.7.6 ¶14
- ch 8 §8.7.7 ¶6
- ch 8 §8.8.5 ¶10
- ch 8 §8.8.5 ¶11
- ch 8 §8.8.5 ¶12
- ch 8 §8.8.5 ¶13
- ch 8 §8.8.5 ¶9
- ch 8 §8.9.2 ¶11
- ch 8 §8.9.2 ¶12
- ch 8 §8.9.2 ¶13
- ch 8 §8.9.2 ¶14
- ch 9 §9.2.1 ¶22
- ch 9 §9.2.2 ¶12
- ch 9 §9.2.3 ¶28
- ch 9 §9.2.3 ¶29
- ch 9 §9.2.3 ¶30
- ch 9 §9.3.3 ¶14
- ch 9 §9.3.5 ¶23
- ch 9 §9.3.5 ¶24
- ch 9 §9.3.5 ¶25
- ch 9 §9.3.5 ¶26
- ch 9 §9.3.5 ¶27
- ch 9 §9.3.5 ¶28
- ch 9 §9.3.5 ¶29
- ch 9 §9.3.5 ¶30
- ch 9 §9.4.1 ¶6
- ch 9 §9.4.4 ¶15
- ch 9 §9.4.4 ¶16
- ch 9 §9.4.4 ¶17
- ch 9 §9.4.4 ¶18
- ch 9 §9.4.4 ¶19
- ch 9 §9.4.4 ¶20
- ch 9 §9.4.4 ¶21

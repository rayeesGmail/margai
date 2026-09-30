# margai-pipeline ncert verify

- run: 2026-09-30 07:46 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 3 chapters of chem11-part2 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 86317aebc0cf768c0e82b77156895243a6e338d2b8099f382578719690b99dcc

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 7 | 17 | 13 | 3 | 16 | 5 | 0 | 0 | 0 |
| 8 | withheld: illegible text layer | — | — | — | — | — | — | — |
| 9 | 31 | 21 | 9 | 30 | 6 | 2 | 2 | 1 |

## where rows start against where the print starts paragraphs

- ch 7 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §7 ¶1 "Chemistry deals with varieties of matter and"
- ch 7 p2: 11 rows start here, the print starts 11 paragraphs — rows the print does not start: §7.1 ¶9 "Problem 7.1 In the reactions given below," · §7.1 ¶10 "(i) H_2S (g) + Cl_2 (g) ->" · §7.1 ¶11 "Solution (i) H_2S is oxidised because a" · printed starts no row begins with: "(ii) 3Fe O (s) + 8" · "(iii) 2 Na (s) + H" · "(i) H S is oxidised because"
- ch 7 p3: 12 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.1 ¶14 "Reaction (iii) chosen here prompts us to" · §7.2 ¶4 "Reactions 7.12 to 7.14 suggest that half" · §7.2 ¶5 "Oxidation : Loss of electron(s) by any" · §7.2 ¶6 "Reduction : Gain of electron(s) by any" · §7.2 ¶7 "Oxidising agent : Acceptor of electron(s)." · §7.2 ¶8 "Reducing agent : Donor of electron(s)." · §7.2 ¶9 "Problem 7.2 Justify that the reaction: 2" · §7.2 ¶10 "Solution Since in the above reaction the"
- ch 7 p4: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §7.2 ¶11 "This splitting of the reaction under examination" · §7.2.1 ¶7 "Here, Cu(s) is oxidised to Cu^2+(aq) and" · printed starts no row begins with: "and the other half reaction is:"
- ch 7 p5: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.2.1 ¶9 "At equilibrium, chemical tests reveal that both" · §7.3 ¶3 "However, as we shall see later, the"
- ch 7 p6: 9 rows start here, the print starts 14 paragraphs — printed starts no row begins with: "oxidation number zero." · "would now be a positive figure" · "and CaH , its oxidation nXPEer" · "5. In all its compounds, fluorine" · "positive oxidation numbers."
- ch 7 p7: 11 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.3 ¶16 "The oxidation number/state of a metal in" · §7.3 ¶17 "Problem 7.3 Using Stock notation, represent the" · §7.3 ¶18 "Solution By applying various rules of calculating" · §7.3 ¶20 "Oxidation: An increase in the oxidation number" · §7.3 ¶21 "Reduction : A decrease in the oxidation" · §7.3 ¶22 "Oxidising agent: A reagent which can increase" · §7.3 ¶23 "Reducing agent: A reagent which lowers the" · §7.3 ¶24 "Redox reactions: Reactions which involve change in" · §7.3 ¶25 "Problem 7.4 Justify that the reaction: 2Cu_2O(s)" · §7.3 ¶26 "Solution Let us assign oxidation number to" · printed starts no row begins with: "CuO → Cu has 2" · "Therefore, these compounds may be"
- ch 7 p8: 10 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.3 ¶27 "Further, Cu_2O helps sulphur in Cu_2S to" · §7.3.1 ¶1 "1. Combination reactions A combination reaction may" · §7.3.1 ¶2 "Either A and B or both A" · §7.3.1 ¶3 "2. Decomposition reactions Decomposition reactions are the" · §7.3.1 ¶4 "It may carefully be noted that there" · §7.3.1 ¶5 "3. Displacement reactions In a displacement reaction," · §7.3.1 ¶6 "Displacement reactions fit into two categories: metal" · printed starts no row begins with: "A combination reaction may be denoted"
- ch 7 p10: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.3.1 ¶18 "Disproportionation reactions are a special type of" · §7.3.1 ¶22 "(It is to be noted with care" · §7.3.1 ¶23 "Problem 7.5 Which of the following species," · §7.3.1 ¶24 "Solution Among the oxoanions of chlorine listed" · §7.3.1 ¶25 "Problem 7.6 Suggest a scheme of classification" · printed starts no row begins with: "(d) 2NO (g) + 2OH (aq)"
- ch 7 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.3.1 ¶26 "Solution In reaction (a), the compound nitric" · §7.3.1 ¶27 "Sometimes, we come across with certain compounds"
- ch 7 p12: 11 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.3.1 ¶31 "Problem 7.7 Why do the following reactions" · §7.3.1 ¶32 "Solution Pb_3O_4 is actually a stoichiometric mixture" · §7.3.2 ¶3 "Step 1: Write the correct formula for" · §7.3.2 ¶4 "Step 2: Identify atoms which undergo change" · §7.3.2 ¶5 "Step 3: Calculate the increase or decrease" · §7.3.2 ¶6 "Step 4: Ascertain the involvement of ions" · §7.3.2 ¶7 "Step 5 : Make the numbers of" · §7.3.2 ¶9 "Problem 7.8 Write the net ionic equation"
- ch 7 p13: 14 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.3.2 ¶10 "Solution Step 1: The skeletal ionic equation" · §7.3.2 ¶11 "Step 2: Assign oxidation numbers for Cr" · §7.3.2 ¶12 "Step 3: Calculate the increase and decrease" · §7.3.2 ¶13 "Step 4: As the reaction occurs in" · §7.3.2 ¶14 "Step 5: Finally, count the hydrogen atoms," · §7.3.2 ¶15 "Problem 7.9 Permanganate ion reacts with bromide" · §7.3.2 ¶16 "Solution Step 1: The skeletal ionic equation" · §7.3.2 ¶17 "Step 2: Assign oxidation numbers for Mn" · §7.3.2 ¶18 "Step 3: Calculate the increase and decrease" · §7.3.2 ¶19 "Step 4: As the reaction occurs in" · §7.3.2 ¶20 "Step 5: Finally, count the hydrogen atoms" · §7.3.2 ¶21 "(b) Half Reaction Method: In this method," · §7.3.2 ¶23 "Step 1: Produce unbalanced equation for the" · printed starts no row begins with: "2Cr (aq) + 3SO (aq) +4H"
- ch 7 p14: 14 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.3.2 ¶24 "Step 2: Separate the equation into half-reactions:" · §7.3.2 ¶25 "Step 3: Balance the atoms other than" · §7.3.2 ¶26 "Step 4: For reactions occurring in acidic" · §7.3.2 ¶27 "Step 5: Add electrons to one side" · §7.3.2 ¶30 "Step 6: We add the two half" · §7.3.2 ¶31 "Step 7: Verify that the equation contains" · §7.3.2 ¶33 "Problem 7.10 Permanganate(VII) ion, MnO_4^- in basic" · §7.3.2 ¶34 "Solution Step 1: First we write the" · §7.3.2 ¶35 "Step 2: The two half-reactions are: Oxidation" · §7.3.2 ¶36 "Step 3: To balance the I atoms" · §7.3.2 ¶37 "Step 4: To balance the O atoms"
- ch 7 p15: 13 rows start here, the print starts 9 paragraphs — rows the print does not start: §7.3.2 ¶38 "Step 5 : In this step we" · §7.3.2 ¶39 "Now to equalise the number of electrons," · §7.3.2 ¶40 "Step 6: Add two half-reactions to obtain" · §7.3.2 ¶41 "Step 7: A final verification shows that"
- ch 7 p16: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "which indicates the flow of current."
- ch 7 p17: 0 rows start here, the print starts 3 paragraphs — printed starts no row begins with: "Table 7.1 The Standard Electrode Potentials" · "1. A negative E means that" · "2. A positive E means that"
- ch 9 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §9 ¶1 "The term ‘hydrocarbon’ is self-explanatory which means"
- ch 9 p2: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "(ii) unsaturated and (iii) aromatic"
- ch 9 p3: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.2.1 ¶4 "Problem 9.1 Write structures of different chain" · §9.2.1 ¶5 "Solution (i) CH_3 – CH_2 – CH_2"
- ch 9 p4: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.2.1 ¶9 "Problem 9.2 Write structures of different isomeric" · §9.2.1 ¶10 "Solution"
- ch 9 p5: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §9.2.1 ¶11 "Problem 9.3 Write IUPAC names of the" · §9.2.1 ¶12 "Solution (i) 2, 2, 4, 4-Tetramethylpentane (ii)" · §9.2.1 ¶14 "i) Draw the chain of five carbon" · §9.2.1 ¶15 "ii) Give number to carbon atoms: C^1–"
- ch 9 p6: 10 rows start here, the print starts 8 paragraphs — rows the print does not start: §9.2.1 ¶16 "iv) Satisfy the valence of each carbon" · §9.2.1 ¶18 "Problem 9.4 Write structural formulas of the" · §9.2.1 ¶19 "Solution (i) CH_3 – CH_2 – CH" · §9.2.1 ¶20 "Problem 9.5 Write structures for each of" · §9.2.1 ¶21 "Solution (i) CH_3 – CH – CH_2" · §9.2.1 ¶22 "(ii) 7 6 5 4 3 2" · printed starts no row begins with: "methyl groups at carbon 2" · "by putting requisite number of hydrogen" · "(ii) 5-Ethyl – 3-methylheptane" · "with zinc and dilute hydrochloric acid"
- ch 9 p7: 11 rows start here, the print starts 4 paragraphs — rows the print does not start: §9.2.2 ¶4 "ii) Alkyl halides on treatment with sodium" · §9.2.2 ¶5 "What will happen if two different alkyl" · §9.2.2 ¶7 "i) Sodium salts of carboxylic acids on" · §9.2.2 ¶8 "Problem 9.6 Sodium salt of which acid" · §9.2.2 ¶9 "Solution Butanoic acid, CH_3CH_2CH_2COO^-Na^+ + NaOH --CaO-->" · §9.2.2 ¶10 "ii) Kolbe's electrolytic method: An aqueous solution" · §9.2.3 ¶1 "Physical properties"
- ch 9 p8: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.2.3 ¶5 "Chemical properties As already mentioned, alkanes are" · §9.2.3 ¶6 "1. Substitution reactions One or more hydrogen" · §9.2.3 ¶7 "Halogenation CH_4 + Cl_2 -> (hv) CH_3C1"
- ch 9 p9: 15 rows start here, the print starts 12 paragraphs — rows the print does not start: §9.2.3 ¶10 "Mechanism" · §9.2.3 ¶18 "2. Combustion" · §9.2.3 ¶19 "Alkanes on heating in the presence of" · §9.2.3 ¶21 "Due to the evolution of large amount" · printed starts no row begins with: "Due to the eYolution of large"
- ch 9 p10: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §9.2.3 ¶23 "Alkanes on heating with a regulated supply" · §9.2.3 ¶25 "n-Alkanes on heating in the presence of" · §9.2.3 ¶26 "n-Alkanes having six or more carbon atoms" · §9.2.3 ¶27 "Toluene (C_7H_8) is methyl derivative of benzene." · §9.2.3 ¶28 "Methane reacts with steam at 1273 K" · §9.2.3 ¶29 "Higher alkanes on heating to higher temperature" · printed starts no row begins with: "Toluene (C H ) is methyl" · "6. Reaction with steam"
- ch 9 p11: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.2.4 ¶3 "1. Sawhorse projections In this projection, the" · §9.2.4 ¶4 "2. Newman projections In this projection, the"
- ch 9 p12: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.2.4 ¶5 "Relative stability of conformations: As mentioned earlier,"
- ch 9 p13: 7 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.3.2 ¶2 "Structure — IUPAC name; CH_3 – CH" · §9.3.2 ¶3 "Problem 9.7 Write IUPAC names of the" · §9.3.2 ¶4 "Solution (i) 2,8-Dimethyl-3, 6-decadiene; (ii) 1,3,5,7 Octatetraene;" · §9.3.2 ¶5 "Problem 9.8 Calculate number of sigma (sigma)" · §9.3.2 ¶6 "Solution sigma bonds : 33, π bonds" · §9.3.3 ¶2 "Structural isomerism : As in alkanes, ethene" · printed starts no row begins with: "Write IUPAC names of the following"
- ch 9 p14: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.3.3 ¶4 "Problem 9.9 Write structures and IUPAC names" · §9.3.3 ¶5 "Solution (a) CH_2 = CH – CH_2" · §9.3.3 ¶6 "Geometrical isomerism: Doubly bonded carbon atoms have"
- ch 9 p15: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §9.3.3 ¶11 "Problem 9.10 Draw cis and trans isomers" · §9.3.3 ¶12 "Solution" · §9.3.3 ¶13 "Problem 9.11 Which of the following compounds" · §9.3.3 ¶14 "Solution (iii) and (iv). In structures (i)" · §9.3.4 ¶2 "Will propene thus obtained show geometrical isomerism?" · printed starts no row begins with: "Which of the following compounds will" · "(iii) and (iv). In structures (i)" · "reason in support of your answer."
- ch 9 p16: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §9.3.4 ¶4 "Nature of halogen atom and the alkyl" · §9.3.5 ¶1 "Physical properties" · §9.3.5 ¶3 "Chemical properties" · §9.3.5 ¶4 "Alkenes are the rich source of loosely" · printed starts no row begins with: "it is : tertiary > secondary" · "takes out one hydrogen atom from"
- ch 9 p17: 10 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.3.5 ¶8 "Addition reaction of HBr to symmetrical alkenes" · §9.3.5 ¶9 "Addition reactions of HBr to symmetrical alkenes" · §9.3.5 ¶10 "Addition reaction of HBr to unsymmetrical alkenes" · §9.3.5 ¶11 "How will H – Br add to" · §9.3.5 ¶12 "Markovnikov, a Russian chemist made a generalisation" · §9.3.5 ¶13 "Mechanism" · §9.3.5 ¶14 "Hydrogen bromide provides an electrophile, H^+, which" · printed starts no row begins with: "because it is formed at a" · "to form the product as follows"
- ch 9 p18: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §9.3.5 ¶17 "In the presence of peroxide, addition of" · §9.3.5 ¶18 "Mechanism : Peroxide effect proceeds via free" · §9.3.5 ¶20 "Problem 9.12 Write IUPAC names of the" · §9.3.5 ¶23 "Solution"
- ch 9 p19: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.3.5 ¶27 "b) Acidic potassium permanganate or acidic potassium" · printed starts no row begins with: "potassium dichromate oxidises alkenes to"
- ch 9 p20: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §9.3.5 ¶30 "Polymers are used for the manufacture of" · §9.4.1 ¶4 "Problem 9.13 Write structures of different isomers" · §9.4.1 ¶5 "Solution 5^th member of alkyne has the" · printed starts no row begins with: "monomers. Other alkenes also undergo" · "(i) but-1-yne and (ii) but-2-yne. Since"
- ch 9 p22: 10 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.4.4 ¶2 "Physical properties of alkynes follow the same" · §9.4.4 ¶3 "Chemical properties" · §9.4.4 ¶4 "Alkynes show acidic nature, addition reactions and" · §9.4.4 ¶5 "A. Acidic character of alkyne: Sodium metal" · §9.4.4 ¶7 "i) CH ≡ CH > H_2C –CH_2" · §9.4.4 ¶8 "ii) HC ≡ CH > CH_3 –C≡" · §9.4.4 ¶9 "B. Addition reactions: Alkynes contain a triple"
- ch 9 p23: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §9.4.4 ¶15 "CH_3–C≡CH + H–Br -> [CH_3–C(Br) = CH_2]" · §9.4.4 ¶17 "(v) Polymerisation"
- ch 9 p24: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.4.4 ¶20 "Problem 9.14 How will you convert ethanoic" · §9.4.4 ¶21 "Solution"
- ch 9 p25: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §9.5.2 ¶1 "Benzene was isolated by Michael Faraday in" · §9.5.2 ¶2 "The Kekulé structure indicates the possibility of"
- ch 9 p26: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.5.2 ¶5 "According to Valence Bond Theory, the concept"
- ch 9 p27: 8 rows start here, the print starts 10 paragraphs — rows the print does not start: §9.5.3 ¶3 "Some examples of aromatic compounds are given" · printed starts no row begins with: "(ii) Complete delocalisation of the π" · "in the ring" · "(iii) Presence of (4n + 2)"
- ch 9 p28: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §9.5.5 ¶1 "Physical properties Aromatic hydrocarbons are non- polar" · §9.5.5 ¶2 "Chemical properties Arenes are characterised by electrophilic" · §9.5.5 ¶3 "Electrophilic substitution reactions The common electrophilic substitution" · printed starts no row begins with: "Aromatic hydrocarbons are non- polar" · "Why do we get isopropyl benzene"
- ch 9 p29: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §9.5.5 ¶10 "Mechanism of electrophilic substitution reactions: According to" · §9.5.5 ¶16 "The arenium ion gets stabilised by resonance:" · printed starts no row begins with: "(c) Removal of proton from the" · "(arenium ion): Attack of electrophile"
- ch 9 p30: 9 rows start here, the print starts 4 paragraphs — rows the print does not start: §9.5.5 ¶19 "Addition reactions Under vigorous conditions, i.e., at" · §9.5.5 ¶21 "Combustion: When heated in air, benzene burns" · §9.5.5 ¶22 "General combustion reaction for any hydrocarbon may" · §9.5.6 ¶1 "When monosubstituted benzene is subjected to further" · §9.5.6 ¶2 "Ortho and para directing groups: The groups" · §9.5.6 ¶3 "It is clear from the above resonating" · printed starts no row begins with: "group in monosubstituted benzene"
- ch 9 p31: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.5.6 ¶4 "In the case of aryl halides, halogens" · §9.5.6 ¶5 "Meta directing group: The groups which direct" · §9.5.6 ¶7 "In this case, the overall electron density"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 9 p27: the print starts "The six π electrons are thus" where §9.5.2 ¶9 starts "The six pi electrons are thus delocalised" — the layer dropped the line's math

## joins across page breaks against the print

- ch 7 §7.2 ¶10 runs from p3 onto p4, but p4 opens a new paragraph: "and the other half reaction is:"
- ch 7 §7.2.1 ¶9 starts a paragraph at the top of p5, but the print continues p4's: "At equilibrium, chemical tests reveal that"
- ch 7 §7.3 ¶16 starts a paragraph at the top of p7, but the print continues p6's: "The oxidation number/state of a metal"
- ch 7 §7.3 ¶27 starts a paragraph at the top of p8, but the print continues p7's: "Further, Cu O helps sulphur in"
- ch 7 §7.3.2 ¶24 starts a paragraph at the top of p14, but the print continues p13's: "Step 2: Separate the equation into"
- ch 9 §9.1 ¶1 runs from p1 onto p2, but p2 opens a new paragraph: "(ii) unsaturated and (iii) aromatic"
- ch 9 §9.2.1 ¶11 starts a paragraph at the top of p5, but the print continues p4's: "important to write the correct structure"
- ch 9 §9.2.4 ¶5 starts a paragraph at the top of p12, but the print continues p11's: "Relative stability of conformations: As"
- ch 9 §9.3.5 ¶17 starts a paragraph at the top of p18, but the print continues p17's: "Anti Markovnikov addition or peroxide"
- ch 9 §9.3.5 ¶29 runs from p19 onto p20, but p20 opens a new paragraph: "monomers. Other alkenes also undergo"
- ch 9 §9.5.2 ¶1 starts a paragraph at the top of p25, but the print continues p24's: "Friedrich August Kekulé,a German chemist was"

## figure_refs against the paragraph and the chapter's captions

- ch 9 §9.5.2 ¶8: figure_refs carries "Fig. 9.7(b)", which the paragraph never mentions
- ch 9 §9.5.2 ¶9: figure_refs carries "Fig. 9.6 (b)", which the paragraph never mentions

## numbered equations the print carries that the rows do not

- ch 9 p29: the print numbers (9.77) once, the rows carry it not at all — a displayed equation dropped, its number altered, or a reference to it lost
- ch 9 p29: the print numbers (9.78) once, the rows carry it not at all — a displayed equation dropped, its number altered, or a reference to it lost

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
none
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (635 rows)
request id: pipeline-ncert-verify-6fc42c05-6352-4fa9-94b5-924322285b19

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 82 | 82 | 0 |
artefact: verify/chem11-part2/en.jsonl (82 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 82 | 351635 | 26757 | 587331 | 7251 | ₹99.93 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 7 p5 §7.3 ¶4: printed "electronegative atom" · transcribed "electonegative atom"
- ch 7 p8 §7.3.1 ¶2: printed "CH_4(g) + 2O_2(g) -> (Delta) CO_2(g) + 2H_2O (l) (7.27)" · transcribed "CH_4(g) + 2O_2(g) -> (Delta) CO_2(g) + 2H_2O (l)"
- ch 7 p9 §7.3.1 ¶15: printed "(7.42)" · transcribed "(7.42b)"
- ch 7 p10 §7.3.1 ¶24: printed "6 ClO_2^- -(hv)-> 4ClO_3^-" · transcribed "6 ClO_2^- -> (hv) 4ClO_3^-"
- ch 7 p13 §7.3.2 ¶11: printed "Cr^3+(aq)+SO_4^2-(aq)" · transcribed "Cr(aq)+SO_4^2-(aq)"
- ch 7 p16 §7.4 ¶5: printed "(E⊖)" · transcribed "(E^0)"
- ch 8 p6 §8.4 ¶2: printed "CH3—C—H with double bond O above C" · transcribed "CH_3—CO—H Acetaldehyde"
- ch 8 p6 §8.4 ¶2: printed "CH3—C—OH with double bond O above C" · transcribed "CH_3—CO—OH Acetic acid"
- ch 8 p9 §8.5.2 ¶12: printed "attaches to the root alkane" · transcribed "attaches to the root alkane is numbered 1"
- ch 8 p15 §8.6.1 ¶2: printed "CH_3–CHCH_2CH_3 with CH_3 below as substituent" · transcribed "CH_3–CHCH_2CH_3 with CH_3"
- ch 8 p15 §8.6.1 ¶2: printed "CH_3—C(CH_3)—CH_3 with CH_3 above" · transcribed "CH_3–C(CH_3)(CH_3)–CH_3"
- ch 8 p17 §8.7.1 ¶8: printed ".CH_3 < .CH_2CH_3 < .CH(CH_3)_2 < .C(CH_3)_3" · transcribed ".CH_3 < .CH_2CH_3 < .CH(CH_3)_2 < .C(CH_3)_3, Methyl free radical Ethyl free radical Isopropyl free radical Tert-butyl free radical"
- ch 8 p19 §8.7.3 ¶6: printed "HO: + CH_3-Br: -> CH_3OH + :Br:^-" · transcribed "HO:^- + CH_3-Br: -> CH_3OH + :Br:^-"
- ch 8 p19 §8.7.4 ¶1: printed "cause permanent polarlisation" · transcribed "cause permanent polarisation"
- ch 8 p21 §8.7.6 ¶11: printed "<-> :CH_2-CH=C(-O:^+)-H" · transcribed "<-> ^-:CH_2–CH=C(–O^+)–H"
- ch 8 p22 §8.7.9 ¶2: printed "CH_3 CH_2^+" · transcribed "CH_3 C^+H_2"
- ch 8 p24 §8.8.3 ¶3: printed "higher boiling point condense before the vapours of the liquid with lower boiling point" · transcribed "higher boiling point condense before the vapours of the liquid with lower boiling"
- ch 8 p33 §8.10.2 ¶9: printed "V_1/2) mL of NH_3 solution of molarity M." · transcribed "V - V_1/2) mL of NH_3 solution of molarity M."
- ch 8 p33 §8.10.2 ¶9: printed "1.4 × M × 2(V − V_1/2) / m" · transcribed "1.4 × M × 2(V − V/2) / m"
- ch 8 p35 §8.10.5 ¶2: printed "phospho molybdate = m_1 g" · transcribed "phospho molydate = m_1 g"
- ch 9 p6 §9.2.1 ¶18: printed "2,5-Dimethylhexane" · transcribed "2,5-Dimethyhexane"
- ch 9 p6 §9.2.2 ¶2: printed "CH_3–CH=CH_2 + H_2 -> CH_3−CH_2CH_3 (Pt/Pd/Ni)
Propene                Propane" · transcribed "CH_3–CH=CH_2 + H_2 -> CH_3−CH_2CH_3 (Pt/Pd/Ni)
Propane                Propane"
- ch 9 p6 §9.2.2 ¶3: printed "CH_3 – Cl + H_2 -> CH_4 + HCl (Zn,H^+) (9.4)" · transcribed "CH_3 – C1 + H_2 -> CH_4 + HC1 (Zn,H^+) (9.4)"
- ch 9 p7 §9.2.2 ¶11: printed "2CH_3–C(=O)–O^- <-> 2CH_3 – C(=O) – O^- +2Na^+" · transcribed "2CH_3COO^-Na^+ <-> 2CH_3 – C(=O) – O^- +2Na^+"
- ch 9 p7 §9.2.2 ¶11: printed "H_2O + e^- -> OH+H·" · transcribed "H_2O + e^- -> –OH+H·"
- ch 9 p9 §9.2.3 ¶11: printed "Cl–Cl --(hv/homolysis)-> 2 Cl_dot" · transcribed "Cl–Cl --(hv/homolysis)-> + 2 Cl_dot"
- ch 9 p10 §9.2.3 ¶24: printed "2-Methylpropane-2-ol" · transcribed "2-Methylpropane-2-01"
- ch 9 p10 §9.2.3 ¶25: printed "CH_3(CH_2)_4CH_3 -> [Anhy. AlCl_3/HCl]" · transcribed "CH_3(CH)_2)_4CH_3 -> [Anhy. AlCl_3/HCl]"
- ch 9 p10 §9.2.3 ¶26: printed "presence of oxides of vanadium, molybdenum or chromium supported over alumina get dehydrogenated and cyclised to benzene and its homologues. This reaction is known as aromatization or reforming." · transcribed "presence of oxides of vanadium, molybdenum or chromium supported over alumina get dehydrogenated and cyclised to benzene and its homologues. This reaction is known as aromatization or reforming. (9.26)"
- ch 9 p10 §9.2.3 ¶29: printed "pyrolysis or cracking." · transcribed "pyrolysis or cracking. (9.28)"
- ch 9 p17 §9.3.5 ¶16: printed "attacked by Br^- ion" · transcribed "attacked by Br– ion"
- ch 9 p19 §9.3.5 ¶26: printed "273 K" · transcribed "273 K) CH_2(OH) – CH_2(OH)"
- ch 9 p19 §9.3.5 ¶26: printed "dil. KMnO_4 / 273 K" · transcribed "(dil. KMnO_4 / 273 K)"
- ch 9 p21 §9.4.3 ¶1: printed "CaCO_3 -> CaO + CaC_2 ... (Delta) (9.55)" · transcribed "CaCO_3 -> CaO + O_2 (Delta) (9.55)"
- ch 9 p22 §9.4.4 ¶2: printed "Ethyne has characteristic" · transcribed "Ethyene has characteristic"
- ch 9 p27 §9.5.3 ¶1: printed "ring, possessing following characteristics." · transcribed "ring, possessing following characteristics. (i) Planarity (ii) Complete delocalisation of the pi electrons in the ring (iii) Presence of (4n + 2) pi electrons in the ring where n is an integer (n = 0, 1, 2, . . .)."

## rows the verifier could not find on their page

- ch 9 p16 §9.3.5 ¶1 "Physical properties"
- ch 9 p16 §9.3.5 ¶3 "Chemical properties"

## running text the page prints that no row carries

- ch 7 p1: "Where there is oxidation, there is always reduction"
- ch 7 p3: "loss of 2e- / gain of 2e- (diagram equations for reactions 7.12 to 7.14)"
- ch 7 p8: "heading 7.3.1 Types of Redox Reactions"
- ch 7 p17: "A negative E means that the redox couple is a stronger reducing agent"
- ch 8 p4: "Problem 8.4 Solution (a) and (b) full structural formulas"
- ch 8 p9: "Structural diagrams and compound names (2,4-Dimethylpentane, 2,2,4-Trimethylpentane, 3-Ethyl-4,4-dimethylheptane examples)"
- ch 8 p9: "1,3-Dimethylbutyl- example diagram"
- ch 8 p10: "Structural diagrams and their names such as 5-(2-Ethylbutyl)-3,3-dimethyldecane"
- ch 8 p10: "5-sec-Butyl-4-isopropyldecane"
- ch 8 p10: "5-(2,2-Dimethylpropyl)nonane"
- ch 8 p14: "1-Chloro-2,4-dinitrobenzene (not 4-chloro,1,3-dinitrobenzene)"
- ch 8 p14: "2-Chloro-1-methyl-4-nitrobenzene (not 4-methyl-5-chloro-nitrobenzene)"
- ch 8 p14: "2-Chloro-4-methylanisole"
- ch 8 p14: "4-Ethyl-2-methylaniline"
- ch 8 p14: "3,4-Dimethylphenol"
- ch 8 p15: "Isomerism [flow chart]"
- ch 8 p19: "CH_3-Cl -> CH_3· + Cl· (this is item 6's continuation, already included)"
- ch 8 p21: "CH_2=CH-C(=O)-H <-> :CH_2-CH=C(-O:^+)-H III diagram structures"
- ch 8 p21: "CH_3-C(+)-O(..)-CH_3 <-> CH_3-C(=O)-CH_3(+) I II diagrams for problem 8.18"
- ch 8 p34: "1 mol of BaSO_4 = 233 g BaSO_4 = 32 g sulphur"
- ch 9 p1: "Hydrocarbons are the important sources of energy."
- ch 9 p2: "Butane (n- butane), (b.p. 273 K)"
- ch 9 p4: "Structures of - C_5H_11 group table with structures (i) through (viii) and corresponding alcohols and names"
- ch 9 p9: "Chlorine free radicals (label under first equation in item 5)"
- ch 9 p10: "3. Controlled oxidation"
- ch 9 p10: "4. Isomerisation"
- ch 9 p10: "5. Aromatization"
- ch 9 p10: "6. Reaction with steam"
- ch 9 p10: "7. Pyrolysis"
- ch 9 p14: "cis-But-2-ene (b.p. 277 K)"
- ch 9 p14: "trans-But-2-ene (b.p. 274 K)"
- ch 9 p17: "(a) less stable primary carbocation (b) more stable secondary carbocation"
- ch 9 p18: "(i) CH_2=CH-CH_2-CH_2-CH_2-CH_3 + H-Br"
- ch 9 p18: "(ii) CH_2=CH-CH_2-CH_2-CH_2-CH_3 + H-Br"
- ch 9 p22: "equation diagram: H_2C – C – H + KOH (alcohol/-KBr/-H_2O)"
- ch 9 p22: "Na^+NH_2^- (-NaBr/-NH_3)"
- ch 9 p22: "CH ≡ CH structural equation product"
- ch 9 p23: "Vinylic cation mechanism equation (-C≡C-+H-Z...)"
- ch 9 p25: "1,3 Dimethylbenzene (m-Xylene)"
- ch 9 p25: "1,4-Dimethylbenzene ( p-Xylene)"
- ch 9 p26: "Resonance and stability of benzene"
- ch 9 p27: "(i) Planarity"
- ch 9 p27: "(ii) Complete delocalisation of the pi electrons"
- ch 9 p27: "(iii) Presence of (4n + 2) pi electrons"
- ch 9 p28: "Why do we get isopropyl benzene on treating benzene with 1-chloropropane instead of n-propyl benzene?"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 7 p2 §7.1 ¶12: printed "Ferrous ferric oxide" · transcribed "Ferrous ferric oxide"
- ch 7 p3 §7.2 ¶1: printed "Na^+Cl^- (s), (Na^+)_2O^2-(s), and (Na^+)_2S^2-(s)" · transcribed "Na^+Cl^- (s), (Na^+)_2O^2-(s), and (Na^+)_2S^2-(s)"
- ch 7 p4 §7.2 ¶10: printed "2 H^-(g)" · transcribed "2 H^-(g)"
- ch 7 p5 §7.3 ¶3: printed "H_2(s) + Cl_2(g) -> 2HCl(g) (7.19)" · transcribed "H_2(s) + Cl_2(g) -> 2HCl(g) (7.19)"
- ch 7 p6 §7.3 ¶14: printed "nonmetallic elements have positive or negative" · transcribed "nonmetallic elements have positive or negative"
- ch 7 p10 §7.3.1 ¶19: printed "S_8(s) + 12 OH^- (aq) -> 4S^2-(aq) + 2S_2O_3^2-(aq)" · transcribed "S_8(s) + 12 OH^- (aq) -> 4S^2- (aq) + 2S_2O_3^2-(aq)"
- ch 7 p11 §7.3.1 ¶29: printed "This reveals that in C_3O_2" · transcribed "This reveals that in C_3O_2"
- ch 7 p11 §7.3.1 ¶30: printed "O_2^+ and O_2^-" · transcribed "O_2^+ and O_2^-"
- ch 7 p12 §7.3.2 ¶6: printed "If the reaction is carried out in acidic solution" · transcribed "If the reaction is carried out in acidic solution"
- ch 8 p1 §8.1 ¶2: printed "Berzilius" · transcribed "Berzilius"
- ch 8 p2 §8.2.1 ¶9: printed "(e) CH_3CH=CHCN" · transcribed "(e) CH_3CH=CHCN"
- ch 8 p4 §8.3.1 ¶12: printed "HOCH_2CH_2CH_2CH(CH_3)CH(CH_3)CH_3" · transcribed "HOCH_2CH_2CH_2CH(CH_3)CH(CH_3)CH_3"
- ch 8 p5 §8.3.2 ¶1: printed "a bond projecting out of the plane of paper, towards" · transcribed "a bond projecting out of the plane of paper, towards"
- ch 8 p6 §8.4 ¶7: printed "aromatic comounds" · transcribed "aromatic comounds"
- ch 8 p6 §8.4 ¶7: printed "hetrocyclic aromatic compounds" · transcribed "hetrocyclic aromatic compounds"
- ch 8 p8 §8.5.2 ¶4: printed "CH_3-CH-CH-" · transcribed "CH_3-CH-CH-"
- ch 8 p8 §8.5.2 ¶4: printed "CH_3 > CH-CH_2-" · transcribed "CH_3 > CH-CH_2-"
- ch 8 p8 §8.5.2 ¶5: printed "the propyl groups can either be n-propyl group or isopropyl" · transcribed "the propyl groups can either be n-propyl group or isopropyl"
- ch 8 p16 §8.7.1 ¶3: printed "give C^+H_3 and" · transcribed "give C^+H_3 and"
- ch 8 p16 §8.7.1 ¶3: printed "H_3C — Br -> H_3C^+ + Br^-" · transcribed "H_3C — Br -> H_3C^+ + Br^-"
- ch 8 p16 §8.7.1 ¶4: printed "(CH_3)_3C^+ hybridised" · transcribed "(CH_3)_3C^+ hybridised"
- ch 8 p17 §8.7.1 ¶7: printed "'half-headed' (fish hook:) curved arrow" · transcribed "'half-headed' (fish hook: ) curved arrow"
- ch 8 p17 §8.7.1 ¶7: printed "Alkyl
free radical" · transcribed "Alkyl free radical"
- ch 8 p18 §8.7.2 ¶9: printed "CH_3–SCH_3, (b) CH_3–CN, (c) CH_3–Cu" · transcribed "CH_3–SCH_3, (b) CH_3–CN, (c) CH_3–Cu"
- ch 8 p18 §8.7.2 ¶10: printed "(c) CH_3 — Cu -> ^-CH_3 + C^+u" · transcribed "(c) CH_3 — Cu -> ^-CH_3 + C^+u"
- ch 8 p18 §8.7.2 ¶13: printed "BF_3, C^+H_3–C^+=O, N^+O_2" · transcribed "BF_3, C^+H_3–C^+=O, N^+O_2"
- ch 8 p19 §8.7.3 ¶6: printed "CH_3-Cl -> CH_3· + Cl·" · transcribed "CH_3-Cl -> CH_3· + Cl·"
- ch 8 p19 §8.7.5 ¶2: printed "Let us consider cholorethane" · transcribed "Let us consider cholorethane"
- ch 8 p19 §8.7.5 ¶2: printed "deltadelta^+ CH_3" · transcribed "deltadelta^+ CH_3"
- ch 8 p19 §8.7.5 ¶2: printed "-> delta^+ CH_2 -> delta^- Cl (2, 1)" · transcribed "-> delta^+ CH_2 -> delta^- Cl (2, 1)"
- ch 8 p22 §8.7.8 ¶2: printed ">C^+ -C< with
H" · transcribed ">C^+ - C< with H"
- ch 8 p22 §8.7.8 ¶3: printed ">C^- -C< with
CN" · transcribed ">C^- - C< with CN"
- ch 8 p23 §8.7.9 ¶10: printed "Hyperconjugation interaction in (CH_3)_3C^+ is greater than" · transcribed "Hyperconjugation interaction in (CH_3)_3C^+ is greater than"
- ch 8 p30 §8.9.2 ¶11: printed "nitric acid and then treated with silver nitrate. A white precipitate, soluble in ammonium
hydroxide shows the presence of chlorine,
a yellowish precipitate, sparingly soluble in
ammonium hydroxide shows the presence of
bromine and a yellow precipitate, insoluble
in ammonium hydroxide shows the presence
of iodine." · transcribed "nitric acid and then treated with silver nitrate. A white precipitate, soluble in ammonium hydroxide shows the presence of chlorine, a yellowish precipitate, sparingly soluble in ammonium hydroxide shows the presence of bromine and a yellow precipitate, insoluble in ammonium hydroxide shows the presence of iodine."
- ch 8 p31 §8.10.2 ¶4: printed "P_1V_1 × 273" · transcribed "P_1V_1 × 273"
- ch 8 p31 §8.10.2 ¶4: printed "= m g" · transcribed "= m g"
- ch 8 p31 §8.10.2 ¶5: printed "p_1 and V_1 are" · transcribed "p_1 and V_1 are"
- ch 8 p33 §8.10.2 ¶9: printed "Percentage of N = [14 × M × 2(V − V_1/2) / 1000] × 100/m" · transcribed "Percentage of N = [14 × M × 2(V − V_1/2) / 1000] × 100/m"
- ch 8 p34 §8.10.3 ¶2: printed "1 mol of AgX contains 1 mol of X" · transcribed "1 mol of AgX contains 1 mol of X"
- ch 9 p3 §9.2.1 ¶5: printed "CH_3 – C – CH_2 – CH_3 with CH_3 groups above and below" · transcribed "CH_3 – C – CH_2 – CH_3 with CH_3 groups above and below"
- ch 9 p5 §9.2.1 ¶13: printed "3-ethyl-2, 2–dimethylpentane" · transcribed "3-ethyl-2, 2–dimethylpentane"
- ch 9 p7 §9.2.2 ¶10: printed "2NaOH (9.9)" · transcribed "2NaOH (9.9)"
- ch 9 p7 §9.2.3 ¶1: printed "Physical properties" · transcribed "Physical properties"
- ch 9 p9 §9.2.3 ¶19: printed "Delta_c H^è – 890 kJ mol^-1" · transcribed "Delta_c H^è – 890 kJ mol^-1"
- ch 9 p9 §9.2.3 ¶19: printed "H_2O(1)" · transcribed "H_2O(1)"
- ch 9 p10 §9.2.3 ¶23: printed "CH_4 + O_2 -> [Mo_2O_3, Delta] HCHO" · transcribed "CH_4 + O_2 -> [Mo_2O_3, Delta] HCHO"
- ch 9 p12 §9.3 ¶1: printed "C_nH_2n" · transcribed "C_nH_2n"
- ch 9 p15 §9.3.3 ¶11: printed "CHCl = CHCl" · transcribed "CHCl = CHCl"
- ch 9 p15 §9.3.3 ¶11: printed "C_2H_5CCH_3 = CCH_3C_2H_5" · transcribed "C_2H_5CCH_3 = CCH_3C_2H_5"
- ch 9 p15 §9.3.3 ¶13: printed "(iv) CH_3CH = CCl CH_3" · transcribed "(iv) CH_3CH = CCl CH_3"
- ch 9 p15 §9.3.4 ¶1: printed "RC ≡ CR^1+H_2" · transcribed "RC ≡ CR^1+H_2"
- ch 9 p15 §9.3.4 ¶1: printed "CH≡ CH+H_2 -> (Pd/C) CH_2 =CH_2" · transcribed "CH≡ CH+H_2 -> (Pd/C) CH_2 =CH_2"
- ch 9 p16 §9.3.4 ¶3: printed "beta carbon atom (carbon" · transcribed "beta carbon atom (carbon"
- ch 9 p16 §9.3.4 ¶3: printed "(X = Cl, Br, I) (9.34)" · transcribed "(X = Cl, Br, I) (9.34)"
- ch 9 p19 §9.3.5 ¶28: printed "(CH_3)_2C = CH_2 + O_3 -> Ozonide -> (Zn + H_2O)" · transcribed "(CH_3)_2C = CH_2 + O_3 -> Ozonide -> (Zn + H_2O)"
- ch 9 p22 §9.4.4 ¶5: printed "HC ≡ C^- Na + Na -> Na^+ C^- ≡ C^-Na^+ + 1/2 H_2" · transcribed "HC ≡ C^- Na + Na -> Na^+ C^- ≡ C^- Na^+ + 1/2 H_2"
- ch 9 p22 §9.4.4 ¶7: printed "H_2C –CH_2" · transcribed "H_2C –CH_2"
- ch 9 p22 §9.4.4 ¶8: printed "CH_3 –C≡ CH >> CH_3 –C≡C–CH_3" · transcribed "CH_3 –C≡ CH >> CH_3 –C≡C–CH_3"
- ch 9 p23 §9.4.4 ¶12: printed "CH_3–C(Br)(Br)–CH(Br)(Br)" · transcribed "CH_3–C(Br)(Br)–CH(Br)(Br)"
- ch 9 p23 §9.4.4 ¶14: printed "CHBr_2–CH_3" · transcribed "CHBr_2–CH_3"
- ch 9 p27 §9.5.2 ¶10: printed "C— C single bond (154 pm)" · transcribed "C— C single bond (154 pm)"
- ch 9 p28 §9.5.5 ¶5: printed "(ii) Halogenation" · transcribed "(ii) Halogenation"
- ch 9 p28 §9.5.5 ¶5: printed "to yield haloarenes. Chlorobenzene (9.73)" · transcribed "to yield haloarenes. Chlorobenzene (9.73)"
- ch 9 p28 §9.5.5 ¶7: printed "alkylbenene is formed" · transcribed "alkylbenene is formed"
- ch 9 p30 §9.5.5 ¶19: printed "(9.80)" · transcribed "(9.80)"
- ch 9 p31 §9.5.6 ¶6: printed "strong–I effect" · transcribed "strong–I effect"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 7 p7 §7.3 ¶26: transcribed "6Cu(s) + SO_2(g)" (printed "6Cu(s) + SO_2")

## set aside by code: a heading or a caption listed as omitted text

- ch 7 p5: "7.3 OXIDATION NUMBER"
- ch 7 p12: "7.3.2 Balancing of Redox Reactions"
- ch 7 p17: "Table 7.1 The Standard Electrode Potentials at 298 K"
- ch 8 p2: "8.2 TETRAVALENCE OF CARBON: SHAPES OF ORGANIC COMPOUNDS"
- ch 8 p2: "8.2.1 The Shapes of Carbon Compounds"
- ch 8 p2: "8.2.2 Some Characteristic Features of π Bonds"
- ch 8 p8: "Table 8.2 IUPAC Names of Some Unbranched Saturated Hydrocarbons"
- ch 8 p13: "8.5.4 Nomenclature of Substituted Benzene Compounds"
- ch 8 p15: "8.6.1 Structural Isomerism"
- ch 8 p29: "8.9.1 Detection of Carbon and Hydrogen"
- ch 8 p29: "8.9.2 Detection of Other Elements"
- ch 8 p32: "Fig. 8.15 Dumas method"
- ch 8 p35: "8.10.6 Oxygen"
- ch 9 p5: "Table 9.1 Nomenclature of a Few Organic Compounds"
- ch 9 p7: "9.2.3 Properties"
- ch 9 p8: "Table 9.2 Variation of Melting Point and Boiling Point in Alkanes"
- ch 9 p12: "9.3.1 Structure of Double Bond"
- ch 9 p12: "9.3 ALKENES"
- ch 9 p12: "9.3.2 Nomenclature"
- ch 9 p12: "Fig. 9.4 Orbital picture of ethene depicting σ bonds only"
- ch 9 p13: "9.3.3 Isomerism"
- ch 9 p20: "9.4.1 Nomenclature and Isomerism"
- ch 9 p20: "Table 9.2 Common and IUPAC Names of Alkynes"
- ch 9 p21: "9.4.2 Structure of Triple Bond"
- ch 9 p24: "9.5 AROMATIC HYDROCARBON"
- ch 9 p25: "9.5.2 Structure of Benzene"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 635

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 7 | 149 | 149 | 142 | 6 | 0 | 1 | 5 | 91.9% |
| 8 | 272 | 272 | 261 | 11 | 0 | 0 | 0 | 96.0% |
| 9 | 214 | 214 | 198 | 14 | 2 | 0 | 8 | 88.8% |
clean for the book (PLAN D15 ✅): 588 of 635 paragraphs, 92.6%
not in the clean share, adjudicate before recording it: 46 page-level start flags, 2 numbered equations the print carries that the rows do not, 45 passages no row carries

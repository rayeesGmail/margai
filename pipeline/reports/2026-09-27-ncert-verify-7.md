# margai-pipeline ncert verify

- run: 2026-09-27 17:58 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 5 chapters of chem12-part1 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 821bc975e70c4586dbb547bb06bec0e4df41b237cf61b953ac5c2f17a88a343a

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 26 | 20 | 5 | 22 | 2 | 0 | 0 | 1 |
| 2 | 27 | 22 | 4 | 26 | 4 | 4 | 0 | 1 |
| 3 | 23 | 16 | 6 | 21 | 4 | 0 | 0 | 1 |
| 4 | 26 | 22 | 3 | 24 | 2 | 1 | 0 | 0 |
| 5 | 20 | 10 | 9 | 19 | 1 | 0 | 0 | 2 |

## where rows start against where the print starts paragraphs

- ch 1 p1: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §1 ¶1 "Almost all processes in body occur in" · §1 ¶2 "In normal life we rarely come across" · §1.1 ¶1 "Solutions are homogeneous mixtures of two or"
- ch 1 p2: 4 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.2 ¶1 "Composition of a solution can be described" · printed starts no row begins with: "Table 1.1: Types of Solutions" · "Mass of the component in the" · "percentage of sodium hypochlorite in water."
- ch 1 p3: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §1.2 ¶7 "As in the case of percentage, concentration" · §1.2 ¶9 "For example, in a binary mixture, if" · §1.2 ¶10 "It can be shown that in a" · §1.2 ¶11 "Mole fraction unit is very useful in" · printed starts no row begins with: "100 mL of the solution." · "in terms of mg mL or" · "Number of moles of the component" · "Total number of moles of all"
- ch 1 p4: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.2 ¶13 "Solution Assume that we have 100 g" · §1.2 ¶16 "Solution Moles of NaOH = 5 g" · printed starts no row begins with: "in one litre (or one cubic" · "For example, 0.25 mol L (or"
- ch 1 p5: 5 rows start here, the print starts 7 paragraphs — rows the print does not start: §1.2 ¶18 "Each method of expressing concentration of the" · §1.2 ¶19 "Example 1.3 Calculate molality of 2.5 g" · §1.2 ¶20 "Solution Molar mass of C_2H_4O_2: 12 ×" · §1.3 ¶1 "Solubility of a substance is its maximum" · printed starts no row begins with: "per kilogram (kg) of the solvent" · "tetrachloride (CCl ) if 22 g" · "by mass in carbon tetrachloride." · "Co(NO ) . 6H O in" · "0.25 molal aqueous solution." · "of 20% (mass/mass) aqueous KI is"
- ch 1 p6: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.3.1 ¶1 "Every solid does not dissolve in a" · §1.3.1 ¶5 "Effect of temperature" · §1.3.1 ¶6 "The solubility of a solid in a" · §1.3.1 ¶7 "Effect of pressure" · §1.3.1 ¶8 "Pressure does not have any significant effect" · §1.3.2 ¶1 "Many gases dissolve in water. Oxygen dissolves" · printed starts no row begins with: "a Solid in a and sugar" · "Solute + Solvent ⇌ Solution" · "a Gas in a in water."
- ch 1 p8: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.3.2 ¶7 "Solution The solubility of gas is related" · §1.3.2 ¶9 "• To increase the solubility of CO_2" · §1.3.2 ¶10 "• Scuba divers must cope with high" · printed starts no row begins with: "bottle is sealed under high pressure."
- ch 1 p9: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.3.2 ¶11 "· At high altitudes the partial pressure" · §1.3.2 ¶12 "Solubility of gases in liquids decreases with" · §1.4 ¶1 "Liquid solutions are formed when solvent is" · §1.4.1 ¶1 "Let us consider a binary solution of" · printed starts no row begins with: "the solubility of H S in" · "the quantity of CO in 500" · "Pressure of two components as 1"
- ch 1 p10: 7 rows start here, the print starts 9 paragraphs — rows the print does not start: §1.4.1 ¶4 "Similarly, for component 2 p_2 = p_2^0" · §1.4.1 ¶6 "Substituting the values of p_1 and p_2," · §1.4.1 ¶7 "Following conclusions can be drawn from equation" · printed starts no row begins with: "(i) Total vapour pressure over the" · "fraction of any one component." · "(ii) Total vapour pressure over the" · "mole fraction of component 2." · "(iii) Depending on the vapour pressures"
- ch 1 p11: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.4.1 ¶10 "Example 1.5 Vapour pressure of chloroform (CHCl_3)" · §1.4.1 ¶11 "Solution (i) Molar mass of CH_2Cl_2 =" · §1.4.1 ¶13 "Note: Since, CH_2Cl_2 is a more volatile" · printed starts no row begins with: "(i) Molar mass of CH Cl" · "Total number of moles = 0.47" · "fraction of the components in gas"
- ch 1 p12: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §1.4.2 ¶1 "According to Raoult's law, the vapour pressure" · printed starts no row begins with: "Law as a in a given" · "2. When the solute is non-volatile,"
- ch 1 p13: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.5 ¶1 "Liquid-liquid solutions can be classified into ideal" · §1.5.2 ¶1 "When a solution does not obey Raoult's" · printed starts no row begins with: "Solutions concentration, then it is called"
- ch 1 p15: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §1.5.2 ¶6 "For example, ethanol-water mixture (obtained by fermentation" · §1.6 ¶1 "We have learnt in Section 1.4.3 that" · §1.6.1 ¶1 "We have learnt in Section 1.4.3 that" · printed starts no row begins with: "respectively, at 350 K . Find" · "Lowering of solution is less than" · "Knowing that x = 1 –"
- ch 1 p16: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.6.1 ¶10 "Solution The various quantities known to us" · §1.6.2 ¶1 "The vapour pressure of a liquid increases" · printed starts no row begins with: "Boiling Point temperature. It boils at"
- ch 1 p17: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.6.2 ¶7 "Example 1.7 18 g of glucose, C_6H_12O_6," · §1.6.2 ¶8 "Solution Moles of glucose = 18 g/"
- ch 1 p18: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.6.2 ¶10 "Solution The elevation (Delta T_b) in the" · §1.6.3 ¶1 "The lowering of vapour pressure of a" · printed starts no row begins with: "of Freezing freezing point compared to" · "DT = T - T is"
- ch 1 p20: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.6.3 ¶10 "Example 1.9 45 g of ethylene glycol" · §1.6.3 ¶11 "Solution Depression in freezing point is related" · §1.6.3 ¶13 "Solution Substituting the values of various terms" · §1.6.4 ¶1 "There are many phenomena which we observe" · printed starts no row begins with: "(a) the freezing point depression and" · "and Osmotic For example, raw mangoes"
- ch 1 p22: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.6.4 ¶11 "Solution The various quantities known to us"
- ch 1 p23: 3 rows start here, the print starts 7 paragraphs — rows the print does not start: §1.6.5 ¶1 "The direction of osmosis can be reversed" · §1.7 ¶1 "We know that ionic compounds when dissolved" · printed starts no row begins with: "Osmosis and osmotic pressure is applied" · "(NH CONH ) is dissolved in" · "of water for this solution and" · "be added to 500 g of" · "75 g of acetic acid to" · "by dissolving 1.0 g of polymer"
- ch 1 p24: 6 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "molecules is depicted as follows:" · "Normal molar mass" · "Calculated colligative property" · "Total number of moles of particles" · "Number of moles of particles before"
- ch 1 p25: 9 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.7 ¶9 "Example 1.12 2 g of benzoic acid" · §1.7 ¶10 "Solution The given quantities are: w_2 =" · §1.7 ¶11 "Substituting these values in equation (1.36) we" · §1.7 ¶12 "Thus, experimental molar mass of benzoic acid" · §1.7 ¶13 "Now consider the following equilibrium for the" · §1.7 ¶14 "If x represents the degree of association" · §1.7 ¶15 "Thus, total number of moles of particles" · §1.7 ¶16 "But i = Normal molar mass /"
- ch 1 p26: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.7 ¶18 "Solution Number of moles of acetic acid" · §1.7 ¶19 "Acetic acid is a weak electrolyte and"
- ch 2 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §2 ¶1 "Chemical reactions can be used to produce" · §2 ¶2 "Electrochemistry is the study of production of" · printed starts no row begins with: "for theoretical and practical considerations. A"
- ch 2 p2: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.1 ¶1 "We had studied the construction and functioning" · §2.1 ¶3 "*Strictly speaking activity should be used instead" · printed starts no row begins with: "to electrical energy and has an"
- ch 2 p3: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.2 ¶1 "As mentioned earlier a galvanic cell is"
- ch 2 p4: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §2.2 ¶9 "Cell reaction: Cu(s) + 2Ag^+(aq) -> Cu^2+(aq)" · §2.2 ¶10 "Half-cell reactions: Cathode (reduction): 2Ag^+(aq) + 2e^-" · §2.2.1 ¶1 "The potential of individual half-cell cannot be" · printed starts no row begins with: "Cu(s) + 2Ag (aq) ¾® Cu" · "Anode (oxidation): Cu(s) ® Cu (aq)" · "and we have E"
- ch 2 p5: 5 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "As E for standard hydrogen electrode" · "Pt(s) ç H (g, 1 bar)" · "is 0.34 V and it is" · "Pt(s) ç H (g, 1 bar)" · "Right electrode: Cu (aq, 1 M)"
- ch 2 p6: 5 rows start here, the print starts 6 paragraphs — rows the print does not start: §2.3 ¶1 "We have assumed in the previous section" · §2.3 ¶4 "E^o_(M^n+ / M) has already been defined," · printed starts no row begins with: "With half-cell reaction: ½ Br (aq)" · "substances that can oxidise ferrous ions" · "n+ has already been defined, R"
- ch 2 p8: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "Ni(s)ú Ni (aq) úú Ag (aq)ú"
- ch 2 p9: 4 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.3 ¶9 "Example 2.1 Represent the cell in which" · §2.3 ¶10 "Solution The cell can be written as" · §2.3.1 ¶1 "If the circuit in Daniell cell (Fig." · printed starts no row begins with: "Zn(s) + Cu (aq) ® Zn" · "takes place and as time passes," · "But at equilibrium," · "2+ = K for the reaction"
- ch 2 p10: 8 rows start here, the print starts 9 paragraphs — rows the print does not start: §2.3.1 ¶4 "Solution E^o_(cell) = (0.059 V / 2)" · §2.3.2 ¶1 "Electrical work done in one second is" · §2.3.2 ¶6 "Solution Delta_r G^o = - nF E^o_(cell)" · printed starts no row begins with: "chemical multiplied by total charge passed." · "Zn(s) + Cu (aq) ¾® Zn" · "2 Zn (s) + 2 Cu" · "Zn(s) + Cu (aq) ¾® Zn"
- ch 2 p11: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.4 ¶1 "It is necessary to define a few" · printed starts no row begins with: "2Fe (aq) + 2I (aq) →"
- ch 2 p12: 3 rows start here, the print starts 6 paragraphs — rows the print does not start: §2.4 ¶7 "* Electronically conducting polymers – In 1977" · printed starts no row begins with: "Table 2.2: The values of Conductivity" · "(i) the nature and structure of" · "(ii) the number of valence electrons" · "(iii) temperature (it decreases with increase"
- ch 2 p13: 5 rows start here, the print starts 10 paragraphs — rows the print does not start: §2.4.1 ¶1 "We know that accurate measurement of an" · printed starts no row begins with: "(i) the nature of the electrolyte" · "(ii) size of the ions produced" · "(iii) the nature of the solvent" · "(iv) concentration of the electrolyte" · "(v) temperature (it increases with the" · "performed on a Wheatstone bridge. However,"
- ch 2 p14: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.4.1 ¶4 "Once the cell constant is determined, we" · §2.4.1 ¶5 "It consists of two resistances R_3 and" · §2.4.1 ¶6 "These days, inexpensive conductivity meters are available"
- ch 2 p15: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.4.1 ¶11 "Example 2.4 Resistance of a conductivity cell" · §2.4.1 ¶12 "Solution The cell constant is given by" · printed starts no row begins with: "1 mol m = 1000(L/m )" · "1 S m mol = 10"
- ch 2 p16: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.4.1 ¶14 "Solution A = pi r^2 = 3.14" · §2.4.2 ¶1 "Both conductivity and molar conductivity change with" · printed starts no row begins with: "Conductivity concentration of the electrolyte. Conductivity"
- ch 2 p17: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.4.2 ¶4 "Strong Electrolytes" · §2.4.2 ¶5 "For strong electrolytes, Lambda_m increases slowly with" · printed starts no row begins with: "G = = k (both A" · "Since l = 1 and A"
- ch 2 p18: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §2.4.2 ¶8 "Solution Taking the square root of concentration"
- ch 2 p19: 5 rows start here, the print starts 6 paragraphs — rows the print does not start: §2.4.2 ¶12 "Here, λ°_+ and λ°_- are the limiting" · §2.4.2 ¶13 "Weak Electrolytes Weak electrolytes like acetic acid" · printed starts no row begins with: "and similarly it was found that" · "Here, l and l are the" · "(Example 2.8). At any concentration c,"
- ch 2 p20: 9 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.4.2 ¶15 "Applications of Kohlrausch law" · §2.4.2 ¶16 "Using Kohlrausch law of independent migration of" · §2.4.2 ¶20 "Solution Lambda^o_(m(HAc)) = lambda^o_(H^+) + lambda^o_(Ac^-) =" · §2.4.2 ¶22 "Solution Lambda_m = kappa / c ="
- ch 2 p21: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §2.5 ¶1 "In an electrolytic cell external source of" · §2.5 ¶5 "Michael Faraday was the first scientist who" · §2.5 ¶6 "After his extensive investigations on electrolysis of" · printed starts no row begins with: "Calculate its degree of dissociation and" · "Cu (aq) + 2e ® Cu"
- ch 2 p22: 12 rows start here, the print starts 6 paragraphs — rows the print does not start: §2.5 ¶12 "We know that charge on one electron" · §2.5 ¶13 "Therefore, the charge on one mole of" · §2.5 ¶15 "For approximate calculations we use 1F ≃" · §2.5 ¶16 "For the electrode reactions: Mg^2+(l) + 2e^-" · §2.5 ¶18 "Example 2.10 A solution of CuSO_4 is" · §2.5 ¶19 "Solution t = 600 s charge =" · §2.5.1 ¶1 "Products of electrolysis depend on the nature" · printed starts no row begins with: "Electrolysis electrolysed and the type of"
- ch 2 p23: 8 rows start here, the print starts 11 paragraphs — rows the print does not start: §2.5.1 ¶5 "but H^+ (aq) is produced by the" · printed starts no row begins with: "Na (aq) + e ® Na" · "H O (l ) ® H" · "Cathode: H O(l ) + e" · "NaCl(aq) + H O(l) ® Na"
- ch 2 p24: 4 rows start here, the print starts 8 paragraphs — rows the print does not start: §2.6 ¶1 "Any battery (actually it may have one" · §2.6.1 ¶1 "In the primary batteries, the reaction occurs" · printed starts no row begins with: "2SO (aq) ® S O (aq)" · "For dilute sulphuric acid, reaction (2.38)" · "then how many electrons would flow" · "What is the quantity of electricity" · "Batteries over a period of time" · "Cathode: HgO + H O +"
- ch 2 p25: 4 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.6.2 ¶1 "A secondary cell after use can be" · §2.6.2 ¶2 "The cell reactions when the battery is" · printed starts no row begins with: "Batteries through it in the opposite" · "Pb(s) + PbO (s) + 2H" · "The overall reaction is represented by"
- ch 2 p26: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.7 ¶1 "Production of electricity by thermal plants is"
- ch 2 p27: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.8 ¶1 "Corrosion slowly coats the surfaces of metallic"
- ch 3 p1: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §3 ¶1 "Chemistry, by its very nature, is concerned" · §3 ¶3 "(b) extent to which a reaction will" · printed starts no row begins with: "determined from chemical equilibrium;"
- ch 3 p2: 7 rows start here, the print starts 8 paragraphs — rows the print does not start: §3.1 ¶1 "Some reactions such as ionic reactions occur" · §3.1 ¶6 "Rate of disappearance of R = Decrease" · printed starts no row begins with: "(i) the rate of decrease in" · "(ii) the rate of increase in" · "Decrease in concentration of R ∆"
- ch 3 p3: 6 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.1 ¶8 "Equations (3.1) and (3.2) given above represent" · §3.1 ¶10 "Units of rate of a reaction From" · §3.1 ¶11 "Example 3.1 From the concentrations of C_4H_9Cl" · §3.1 ¶12 "Solution We can determine the difference in"
- ch 3 p4: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §3.1 ¶13 "It can be seen (Table 3.1) that"
- ch 3 p5: 7 rows start here, the print starts 9 paragraphs — rows the print does not start: §3.1 ¶19 "Similarly, for the reaction 5 Br^- (aq)" · printed starts no row begins with: "It can be determined graphically by" · "Rate of reaction = – =" · "Rate of reaction = −"
- ch 3 p6: 5 rows start here, the print starts 7 paragraphs — rows the print does not start: §3.1 ¶22 "Solution Average Rate = (1/2){- Delta[N_2O_5] /" · §3.2 ¶1 "Rate of reaction depends upon the experimental" · §3.2.1 ¶1 "The rate of a chemical reaction at" · printed starts no row begins with: "to 0.02M in 25 minutes. Calculate" · "of time both in minutes and" · "mol L to 0.4 mol L" · "temperature and catalyst." · "of Rate on the concentration of"
- ch 3 p8: 13 rows start here, the print starts 11 paragraphs — rows the print does not start: §3.2.2 ¶11 "1. CHCl_3 + Cl_2 -> CCl_4 +" · §3.2.2 ¶12 "2. CH_3COOC_2H_5 + H_2O -> CH_3COOH +" · §3.2.3 ¶1 "In the rate equation (3.4) Rate =" · §3.2.3 ¶4 "Example 3.3 Calculate the overall order of" · §3.2.3 ¶5 "Solution (a) Rate = k [A]^x [B]^y" · printed starts no row begins with: "2. CH COOC H + H" · "x and y indicate how sensitive" · "(b) order = 3/2 + (–1)"
- ch 3 p9: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §3.2.3 ¶7 "These may be consecutive reactions (e.g., oxidation" · §3.2.3 ¶8 "Units of rate constant For a general" · §3.2.3 ¶10 "Example 3.4 Identify the reaction order from" · §3.2.3 ¶11 "Solution (i) The unit of second order" · §3.2.4 ¶1 "Another property of a reaction called molecularity" · printed starts no row begins with: "Where x + y = n" · "(i) The unit of second order" · "k = 2.3 × 10 L" · "(ii) The unit of a first" · "k = 3 × 10 s" · "understanding its mechanism. The number of"
- ch 3 p10: 11 rows start here, the print starts 12 paragraphs — rows the print does not start: §3.2.4 ¶10 "Thus, from the discussion, till now, we" · printed starts no row begins with: "KClO + 6FeSO + 3H SO" · "even a fraction but molecularity cannot"
- ch 3 p11: 6 rows start here, the print starts 11 paragraphs — rows the print does not start: §3.3 ¶1 "We have already noted that the concentration" · §3.3.1 ¶1 "Zero order reaction means that the rate" · printed starts no row begins with: "molecularity of the slowest step is" · "What is the order of the" · "concentration of X is increased to" · "Reactions to zero power of the" · "As any quantity raised to power" · "Integrating both sides" · "Substituting the value of I in"
- ch 3 p12: 10 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.3.1 ¶4 "Comparing (3.6) with equation of a straight" · §3.3.1 ¶5 "Further simplifying equation (3.6), we get the" · §3.3.1 ¶6 "Zero order reactions are relatively uncommon but" · §3.3.2 ¶1 "In this class of reactions, the rate" · §3.3.2 ¶2 "Integrating this equation, we get ln [R]" · §3.3.2 ¶3 "Again, I is the constant of integration" · §3.3.2 ¶4 "When t = 0, R = [R]_0," · §3.3.2 ¶5 "Therefore, equation (3.8) can be written as" · §3.3.2 ¶6 "Substituting the value of I in equation" · printed starts no row begins with: "Reactions first power of the concentration"
- ch 3 p13: 13 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.3.2 ¶7 "Rearranging this equation ln [R]/[R]_0 = -kt" · §3.3.2 ¶8 "At time t_1 from equation (3.8) *ln[R]_1" · §3.3.2 ¶9 "At time t_2 ln[R]_2 = - kt_2" · §3.3.2 ¶10 "where, [R]_1 and [R]_2 are the concentrations" · §3.3.2 ¶11 "Subtracting (3.12) from (3.11) ln[R]_1 - ln[R]_2" · §3.3.2 ¶12 "Equation (3.9) can also be written as" · §3.3.2 ¶13 "Taking antilog of both sides [R] =" · §3.3.2 ¶15 "The first order rate equation (3.10) can" · §3.3.2 ¶16 "If we plot a graph between log" · §3.3.2 ¶17 "Hydrogenation of ethene is an example of" · §3.3.2 ¶19 "* Refer to Appendix-IV for ln and"
- ch 3 p14: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.3.2 ¶20 "Decomposition of N_2O_5 and N_2O are some" · §3.3.2 ¶22 "Solution For a first order reaction log" · §3.3.2 ¶23 "Let us consider a typical first order" · printed starts no row begins with: "(60 min- 0 min) 0.20 ´10" · "Total pressure p = p +"
- ch 3 p15: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §3.3.2 ¶26 "where, p_i is the initial pressure at" · §3.3.2 ¶27 "Example 3.6 The following data were obtained" · §3.3.2 ¶28 "Solution Let the pressure of N_2O_5(g) decrease"
- ch 3 p16: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.3.3 ¶1 "The half-life of a reaction is the" · §3.3.3 ¶2 "For a zero order reaction, rate constant" · printed starts no row begins with: "a Reaction reactant is reduced to"
- ch 3 p17: 7 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.3.3 ¶7 "Example 3.7 A first order reaction is" · §3.3.3 ¶8 "Solution Half-life for a first order reaction" · §3.3.3 ¶9 "Example 3.8 Show that in a first" · §3.3.3 ¶10 "Solution When reaction is completed 99.9%, [R]_n" · §3.3.3 ¶11 "Table 3.4 summarises the mathematical features of" · printed starts no row begins with: "Table 3.4: Integrated Rate Laws for"
- ch 3 p18: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §3.3.3 ¶12 "The order of a reaction is sometimes" · §3.4 ¶1 "Most of the chemical reactions are accelerated" · printed starts no row begins with: "of this reactant take to reduce" · "minutes. If the decomposition is a"
- ch 3 p20: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.4 ¶14 "In Fig. 3.10, slope = – E_a/R" · §3.4 ¶15 "At temperature T_2, equation (3.19) is ln" · §3.4 ¶16 "k_1 and k_2 are the values of"
- ch 3 p21: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §3.4 ¶17 "Subtracting equation (3.20) from (3.21), we obtain" · §3.4 ¶18 "Example 3.9 The rate constants of a" · §3.4 ¶19 "Solution log (k_2 / k_1) = (E_a" · §3.4 ¶21 "Solution We know that log k_2 –"
- ch 3 p22: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §3.4.1 ¶1 "A catalyst is a substance which increases" · §3.5 ¶1 "Though Arrhenius equation is applicable under a" · printed starts no row begins with: "Catalyst itself undergoing any permanent chemical"
- ch 3 p23: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §3.5 ¶7 "* Threshold energy = Activation Energy +" · printed starts no row begins with: "rate of reaction can be expressed" · "where Z represents the collision frequency"
- ch 4 p1: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §4 ¶1 "The d-block of the periodic table contains" · §4 ¶2 "There are mainly four series of the"
- ch 4 p2: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.1 ¶1 "The d–block occupies the large middle section" · §4.2 ¶1 "In general the electronic configuration of outer"
- ch 4 p3: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.2 ¶6 "Example 4.1 On what ground can you" · §4.2 ¶7 "Solution On the basis of incompletely filled"
- ch 4 p4: 3 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.3.1 ¶1 "Nearly all the transition elements display typical" · printed starts no row begins with: "How can you say that it" · "Properties of Nearly all the transition"
- ch 4 p5: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.3.2 ¶1 "In general, ions of the same charge" · printed starts no row begins with: "Atomic and decrease in radius with"
- ch 4 p7: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.3.2 ¶4 "Example 4.2 Why do the transition elements" · §4.3.2 ¶5 "Solution Because of large number of unpaired" · §4.3.3 ¶1 "There is an increase in ionisation enthalpy" · printed starts no row begins with: "of zinc is the lowest, i.e.," · "Enthalpies transition elements from left to"
- ch 4 p8: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.3.4 ¶1 "One of the notable features of a" · printed starts no row begins with: "of oxidation states these may show"
- ch 4 p9: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.3.4 ¶6 "Example 4.3 Name a transition element which" · §4.3.4 ¶7 "Solution Scandium (Z = 21) does not" · printed starts no row begins with: "largest number of oxidation states and"
- ch 4 p10: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.3.5 ¶1 "Table 4.4 contains the thermochemical parameters related" · §4.3.5 ¶3 "Example 4.4 Why is Cr^2+ reducing and" · §4.3.5 ¶4 "Solution Cr^2+ is reducing as its configuration" · printed starts no row begins with: "transformation of the solid metal atoms" · "reason for this? (Hint: consider its"
- ch 4 p11: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.3.5 ¶5 "The stability of the half-filled d sub-shell" · §4.3.6 ¶1 "An examination of the E^o(M^3+/M^2+) values (Table" · §4.3.7 ¶1 "Table 4.5 shows the stable halides of" · printed starts no row begins with: "the M /M trends. The low" · "Stability of The highest oxidation numbers"
- ch 4 p12: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.3.7 ¶6 "Example 4.5 How would you account for" · §4.3.7 ¶7 "Solution This is due to the increasing" · printed starts no row begins with: "2Cu ® Cu + Cu" · "enthalpies (first and second) in the"
- ch 4 p13: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §4.3.8 ¶1 "Transition metals vary widely in their chemical" · §4.3.8 ¶5 "Solution The E^o (M^2+/M) values are not" · §4.3.8 ¶7 "Solution Much larger third ionisation energy of" · §4.3.9 ¶1 "When a magnetic field is applied to" · printed starts no row begins with: "Reactivity them are sufficiently electropositive to" · "2 Cr (aq) + 2 H" · "Properties magnetic behaviour are observed: diamagnetism"
- ch 4 p14: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.3.9 ¶4 "Example 4.8 Calculate the magnetic moment of" · §4.3.9 ¶5 "Solution With atomic number 25, the divalent"
- ch 4 p15: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.3.10 ¶1 "When an electron from a lower energy" · §4.3.11 ¶1 "Complex compounds are those in which the" · printed starts no row begins with: "of Coloured energy d orbital, the" · "of Complex of anions or neutral"
- ch 4 p16: 4 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.3.12 ¶1 "The transition metals and their compounds are" · §4.3.13 ¶1 "Interstitial compounds are those which are formed" · §4.3.14 ¶1 "An alloy is a blend of metals" · printed starts no row begins with: "Properties activity. This activity is ascribed" · "2 Fe + S O ®" · "like H, C or N are" · "(i) They have high melting points," · "(ii) They are very hard, some" · "(iii) They retain metallic conductivity." · "(iv) They are chemically inert." · "Formation Alloys may be homogeneous solid"
- ch 4 p17: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §4.3.14 ¶3 "Solution When a particular oxidation state becomes" · §4.4.1 ¶3 "Thus, Mn_2O_7 gives HMnO_4 and CrO_3 gives" · §4.4.1 ¶4 "Potassium dichromate K_2Cr_2O_7" · §4.4.1 ¶5 "Potassium dichromate is a very important chemical" · printed starts no row begins with: "Thus, Mn O gives HMnO and" · "4 FeCr O + 8 Na" · "2Na CrO + 2 H ®"
- ch 4 p18: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §4.4.1 ¶13 "Potassium permanganate KMnO_4" · §4.4.1 ¶14 "Potassium permanganate is prepared by fusion of" · printed starts no row begins with: "3MnO + 4H ® 2MnO +"
- ch 4 p19: 10 rows start here, the print starts 12 paragraphs — printed starts no row begins with: "2KMnO ® K MnO + MnO" · "5Fe + MnO + 8H ®"
- ch 4 p20: 11 rows start here, the print starts 12 paragraphs — rows the print does not start: §4.4.1 ¶33 "Note: Permanganate titrations in presence of hydrochloric" · §4.4.1 ¶34 "Uses: Besides its use in analytical chemistry," · §4.4.1 ¶35 "The f-block consists of the two series," · §4.5 ¶1 "The names, symbols, electronic configurations of atomic" · printed starts no row begins with: "(c) Oxalate ion or oxalic acid" · "5SO + 2MnO + 6H ——>" · "2MnO + H O + I" · "8MnO + 3S O + H" · "or zinc oxide catalyses the oxidation:"
- ch 4 p21: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.5.1 ¶1 "It may be noted that atoms of" · §4.5.2 ¶1 "The overall decrease in atomic and ionic" · §4.5.3 ¶1 "In the lanthanoids, La(II) and Ln(III) compounds" · printed starts no row begins with: "Configurations configuration with 6s common but" · "Ionic Sizes lutetium (the lanthanoid contraction)" · "species. However, occasionally +2 and +4"
- ch 4 p22: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.5.4 ¶1 "All the lanthanoids are silvery white soft" · printed starts no row begins with: "Characteristics The hardness increases with increasing"
- ch 4 p23: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.6 ¶1 "The actinoids include the fourteen elements from" · printed starts no row begins with: "are in the range of –2.2"
- ch 4 p24: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §4.6.1 ¶1 "All the actinoids are believed to have" · §4.6.2 ¶1 "The general trend in lanthanoids is observable" · §4.6.3 ¶1 "There is a greater range of oxidation" · printed starts no row begins with: "Configurations and variable occupancy of the" · "There is a gradual decrease in" · "the fact that the 5f, 6d"
- ch 4 p25: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.6.4 ¶6 "Example 4.10 Name a member of the" · §4.6.4 ¶7 "Solution Cerium (Z = 58)" · §4.7 ¶1 "Iron and steels are the most important" · printed starts no row begins with: "lanthanoid contraction. Why?"
- ch 5 p1: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §5 ¶1 "In the previous Unit we learnt that" · §5.1 ¶1 "Alfred Werner (1866-1919), a Swiss chemist was"
- ch 5 p2: 9 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.1 ¶2 "1 mol CoCl_3.6NH_3 (Yellow) gave 3 mol" · §5.1 ¶4 "Note that the last two compounds in" · printed starts no row begins with: "1 mol CoCl .4NH (Violet) gave" · "the coordination number and is fixed" · "characteristic spatial arrangements corresponding to different"
- ch 5 p3: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.1 ¶11 "Example 5.1 On the basis of the" · §5.1 ¶12 "Solution (i) Secondary 4 (ii) Secondary 6" · §5.1 ¶13 "Difference between a double salt and a" · §5.1 ¶14 "Both double salts as well as complexes" · printed starts no row begins with: "He, at the age of 29"
- ch 5 p4: 7 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.2 ¶1 "(a) Coordination entity A coordination entity constitutes" · §5.2 ¶2 "(b) Central atom/ion In a coordination entity," · §5.2 ¶3 "(c) Ligands The ions or molecules bound" · §5.2 ¶6 "Similarly, SCN^- ion can coordinate through the" · §5.2 ¶7 "(d) Coordination number The coordination number (CN)" · printed starts no row begins with: "When a ligand can bind through"
- ch 5 p5: 6 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.2 ¶9 "(e) Coordination sphere The central atom/ion and" · §5.2 ¶10 "(f) Coordination polyhedron The spatial arrangement of" · §5.2 ¶11 "(g) Oxidation number of central atom The" · §5.2 ¶12 "(h) Homoleptic and heteroleptic complexes Complexes in" · §5.3 ¶1 "Nomenclature is important in Coordination Chemistry because"
- ch 5 p6: 11 rows start here, the print starts 23 paragraphs — rows the print does not start: §5.3.1 ¶1 "The formula of a compound is a" · §5.3.2 ¶1 "The names of coordination compounds are derived" · printed starts no row begins with: "Mononuclear information about the constitution of" · "(i) The central atom is listed" · "(ii) The ligands are then listed" · "a ligand in the list does" · "determine the position of the ligand" · "are also enclosed in parentheses." · "within a coordination sphere." · "sign. For example, [Co(CN) ] ," · "Mononuclear principles of additive nomenclature. Thus," · "(i) The cation is named first" · "(ii) The ligands are named in" · "central atom/ion. (This procedure is reversed" · "entity, these are enclosed in brackets" · "entity is indicated by Roman numeral"
- ch 5 p7: 11 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.3.2 ¶7 "The following examples illustrate the nomenclature for" · §5.3.2 ¶8 "1. [Cr(NH_3)_3(H_2O)_3]Cl_3 is named as: triamminetriaquachromium(III) chloride" · §5.3.2 ¶9 "Explanation: The complex ion is inside the" · §5.3.2 ¶10 "2. [Co(H_2NCH_2CH_2NH_2)_3]_2(SO_4)_3 is named as: tris(ethane-1,2–diamine)cobalt(III) sulphate" · §5.3.2 ¶11 "Explanation: The sulphate is the counter anion" · §5.3.2 ¶12 "3. [Ag(NH_3)_2][Ag(CN)_2] is named as: diamminesilver(I)dicyanidoargentate(I)" · §5.3.2 ¶14 "Solution (a) [Co(NH_3)_4(H_2O)Cl]Cl_2 (b) K_2[Zn(OH)_4] (c) K_3[Al(C_2O_4)_3]" · §5.3.2 ¶16 "Solution (a) diamminechloridonitrito-N-platinum(II) (b) potassium trioxalatochromate(III) (c)" · printed starts no row begins with: "the same as the charge of"
- ch 5 p8: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §5.4 ¶1 "Isomers are two or more compounds that" · §5.4 ¶2 "(a) Stereoisomerism (i) Geometrical isomerism (ii) Optical" · §5.4 ¶3 "(b) Structural isomerism (i) Linkage isomerism (ii)" · printed starts no row begins with: "5.4 Isomerism in Isomers are two" · "(i) Geometrical isomerism (ii) Optical isomerism" · "(iii) Ionisation isomerism (iv) Solvate isomerism" · "(ii) Coordination isomerism"
- ch 5 p9: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.4.1 ¶5 "Example 5.4 Why is geometrical isomerism not" · §5.4.1 ¶6 "Solution Tetrahedral complexes do not show geometrical" · §5.4.2 ¶1 "Optical isomers are mirror images that cannot" · §5.4.2 ¶2 "In a coordination entity of the type" · printed starts no row begins with: "are present in complexes of formula"
- ch 5 p10: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.4.2 ¶4 "Example 5.6 Out of the following two" · §5.4.2 ¶5 "Solution The two entities are represented as" · §5.4.2 ¶6 "Out of the two, (a) cis -" · §5.4.3 ¶1 "Linkage isomerism arises in a coordination compound" · §5.4.4 ¶1 "This type of isomerism arises from the" · §5.4.5 ¶1 "This form of isomerism arises when the" · printed starts no row begins with: "Isomerism ambidentate ligand. A simple example" · "Isomerism cationic and anionic entities of" · "Isomerism is itself a potential ligand"
- ch 5 p11: 7 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.4.6 ¶1 "This form of isomerism is known as" · §5.5 ¶1 "Werner was the first to describe the" · §5.5.1 ¶1 "According to this theory, the metal atom" · printed starts no row begins with: "Isomerism water is involved as a" · "draw the structures for these isomers:" · "(iii) [Co(NH ) (NO )](NO )" · "5.5 Bonding in Werner was the" · "forming coordination compounds?" · "ligands can use its (n-1)d, ns,"
- ch 5 p13: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §5.5.2 ¶1 "The magnetic moment of coordination compounds can" · §5.5.2 ¶2 "A critical study of the magnetic data" · printed starts no row begins with: "Properties by the magnetic susceptibility experiments."
- ch 5 p14: 6 rows start here, the print starts 11 paragraphs — rows the print does not start: §5.5.2 ¶5 "Solution Since the coordination number of Mn^2+" · §5.5.3 ¶1 "While the VB theory, to a larger" · §5.5.4 ¶2 "(a) Crystal field splitting in octahedral coordination" · §5.5.4 ¶3 "In an octahedral coordination entity with six" · printed starts no row begins with: "of Valence and magnetic behaviour of" · "(i) It involves a number of" · "(ii) It does not give quantitative" · "(iii) It does not explain the" · "(iv) It does not give a" · "or kinetic stabilities of coordination compounds." · "(v) It does not make exact" · "square planar structures of 4-coordinate complexes." · "(vi) It does not distinguish between"
- ch 5 p15: 5 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "field ligands and form high spin"
- ch 5 p16: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.5.4 ¶9 "(b) Crystal field splitting in tetrahedral coordination" · §5.5.5 ¶1 "In the previous Unit, we learnt that" · printed starts no row begins with: "Coordination properties of transition metal complexes"
- ch 5 p17: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §5.5.5 ¶3 "Colour of Some Gem Stones The colours"
- ch 5 p18: 4 rows start here, the print starts 6 paragraphs — rows the print does not start: §5.5.5 ¶4 "In emerald [Fig.5.12(b)], Cr^3+ ions occupy octahedral" · §5.5.6 ¶1 "The crystal field model is successful in" · §5.6 ¶1 "The homoleptic carbonyls (compounds containing carbonyl ligands" · printed starts no row begins with: "of Crystal structures, colour and magnetic" · "planar structure is diamagnetic and the" · "geometry is paramagnetic." · "outer orbital complex." · "hexacyanoion contains only one unpaired electron."
- ch 5 p19: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §5.7 ¶1 "The coordination compounds are of great importance." · §5.7 ¶2 "• Coordination compounds find use in many" · §5.7 ¶3 "• Hardness of water is estimated by" · §5.7 ¶4 "• Some important extraction processes of metals," · §5.7 ¶5 "• Similarly, purification of metals can be" · printed starts no row begins with: "(dimethylglyoxime), a–nitroso–b–naphthol, cupron, etc." · "the stability constants of calcium and" · "form from this solution by the" · "and subsequent decomposition of their coordination"
- ch 5 p20: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §5.7 ¶6 "• Coordination compounds are of great importance" · §5.7 ¶7 "• Coordination compounds are used as catalysts" · §5.7 ¶8 "• Articles can be electroplated with silver" · §5.7 ¶9 "• In black and white photography, the" · §5.7 ¶10 "• There is growing interest in the" · printed starts no row begins with: "decomposed to yield pure nickel." · "and carbonic anhydrase (catalysts of biological" · "Wilkinson catalyst, is used for the" · "and [Au(CN) ] than from a" · "AgBr to form a complex ion,"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 1 p21: the print starts "Here P is the osmotic pressure" where §1.6.4 ¶5 starts "Here Pi is the osmotic pressure and" — the layer dropped the line's math
- ch 2 p20: the print starts "Example 2.8 Lm for NaCl, HCl" where §2.4.2 ¶19 starts "Example 2.8 Lambda^o_m for NaCl, HCl and" — the layer dropped the line's math
- ch 3 p3: the print starts "Since, D[R] is a negative quantity" where §3.1 ¶7 starts "Since, Delta[R] is a negative quantity (as" — the layer dropped the line's math
- ch 5 p15: the print starts "(i) If D < P, the" where §5.5.4 ¶6 starts "(i) If Delta_o < P, the fourth" — the layer dropped the line's math
- ch 5 p15: the print starts "(ii) If D > P, it" where §5.5.4 ¶7 starts "(ii) If Delta_o > P, it becomes" — the layer dropped the line's math

## joins across page breaks against the print

- ch 1 §1.2 ¶4 runs from p2 onto p3, but p3 opens a new paragraph: "(iii) Mass by volume percentage (w/V):"
- ch 1 §1.5.2 ¶6 starts a paragraph at the top of p15, but the print continues p14's: "For example, ethanol-water mixture (obtained by"
- ch 2 §2.1 ¶1 starts a paragraph at the top of p2, but the print continues p1's: "We had studied the construction and"
- ch 2 §2.2 ¶1 starts a paragraph at the top of p3, but the print continues p2's: "As mentioned earlier a galvanic cell"
- ch 2 §2.2.1 ¶7 runs from p5 onto p6, but p6 opens a new paragraph: "With half-cell reaction: ½ Br (aq)"
- ch 2 §2.5.1 ¶9 runs from p23 onto p24, but p24 opens a new paragraph: "2SO (aq) ® S O (aq)"
- ch 3 §3.1 ¶13 runs from p4 onto p5, but p5 opens a new paragraph: "It can be determined graphically by"
- ch 3 §3.2.3 ¶7 starts a paragraph at the top of p9, but the print continues p8's: "These may be consecutive reactions (e.g.,"
- ch 3 §3.3.2 ¶7 starts a paragraph at the top of p13, but the print continues p12's: "Rearranging this equation"
- ch 3 §3.4 ¶17 starts a paragraph at the top of p21, but the print continues p20's: "Subtracting equation (3.20) from (3.21), we"
- ch 4 §4.3.2 ¶4 starts a paragraph at the top of p7, but the print continues p6's: "Why do the transition elements exhibit"
- ch 4 §4.4.1 ¶25 runs from p19 onto p20, but p20 opens a new paragraph: "(c) Oxalate ion or oxalic acid"
- ch 5 §5.1 ¶2 starts a paragraph at the top of p2, but the print continues p1's: "1 mol CoCl .6NH (Yellow) gave"

## figure_refs against the paragraph and the chapter's captions

- ch 2 §2.2.1 ¶1: figure_refs carries "Fig.3.3", which no caption in chapter 2 prints
- ch 2 §2.4.2 ¶8: figure_refs carries "Fig. 3.7", which no caption in chapter 2 prints
- ch 2 §2.4.2 ¶8: figure_refs carries "Fig. 2.7", which the paragraph never mentions
- ch 2 §2.4.2 ¶17: figure_refs carries "Table 3.4", which no caption in chapter 2 prints
- ch 4 §4.3.10 ¶1: figure_refs carries "Fig. 4.5", which no caption in chapter 4 prints

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
none
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (676 rows)
request id: pipeline-ncert-verify-d316a3f3-24c6-4d4a-b6cf-94b8432d19d2

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 122 | 122 | 0 |
artefact: verify/chem12-part1/en.jsonl (122 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 122 | 486476 | 28182 | 884622 | 7251 | ₹131.15 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 1 p4 §1.2 ¶16: printed "Using equation (1.8)" · transcribed "Using equation (2.8)"
- ch 1 p9 §1.4 ¶1: printed "solutions of (i) liquids in liquids and (ii) solids in liquids" · transcribed "the solutions of (i) liquids in liquids and (ii) solids in liquids"
- ch 1 p16 §1.6.1 ¶10: printed "in equation (1.28)" · transcribed "in equation (2.28)"
- ch 1 p18 §1.6.3 ¶2: printed "point." · transcribed "point. Delta T_f = T_f^0 – T_f is known as depression in freezing point."
- ch 1 p21 §1.6.4 ¶4: printed "Thus: Π = C R T" · transcribed "Thus: Pi = C R T"
- ch 1 p21 §1.6.4 ¶5: printed "Here Π is the osmotic pressure" · transcribed "Here Pi is the osmotic pressure"
- ch 1 p21 §1.6.4 ¶5: printed "Π = (n_2 /V) R T" · transcribed "Pi = (n_2 /V) R T"
- ch 1 p21 §1.6.4 ¶6: printed "Π V = w_2 R T / M_2" · transcribed "Pi V = w_2 R T / M_2"
- ch 1 p21 §1.6.4 ¶6: printed "M_2 = w_2 R T / (Π V)" · transcribed "M_2 = w_2 R T / (Pi V)"
- ch 1 p21 §1.6.4 ¶7: printed "w_2, T, Π and V" · transcribed "w_2, T, Pi and V"
- ch 1 p22 §1.6.4 ¶11: printed "1.26 g × 0.083 L bar K^-1 mol^-1 × 300 K / (2.57×10^-3 bar × 0.200 L)" · transcribed "1.26 g × 0.083 L bar K^-1 mol^-1 × 300 K / (2.57×10^-3 bar × 0.200 L) = 61,022 g mol^-1"
- ch 1 p26 §1.7 ¶19: printed "CH_3COOH <=> H^+ + CH_3COO^-" · transcribed "CH_3COOH <-> H^+ + CH_3COO^-"
- ch 2 p8 §2.3 ¶8: printed "a A + bB --(ne-)--> cC + dD" · transcribed "a A + bB --(ne^-)--> cC + dD"
- ch 2 p15 §2.4.1 ¶12: printed "kappa = 1.29 cm^-1 / 520 Ω" · transcribed "kappa = 1.29 cm^-1 / 520 Ω = 0.248 × 10^-2 S cm^-1"
- ch 2 p19 §2.4.2 ¶13: printed "acetic acid have lower degree" · transcribed "acetic acid have lower degree of dissociation at higher concentrations"
- ch 2 p20 §2.4.2 ¶19: printed "Calculate Lambda^o_(m) for HAc" · transcribed "Calculate Lambda^o for HAc"
- ch 2 p20 §2.4.2 ¶22: printed "48.15 S cm^2 mol^-1" · transcribed "48.15 S cm^3 mol^-1"
- ch 2 p21 §2.5 ¶2: printed "Cu(s) → Cu^2+(s) + 2e^-" · transcribed "Cu(s) -> Cu^2+(s) + 2e^-"
- ch 3 p4 §3.1 ¶13: printed "1.90 × 10^-4 mol L^-1s^-1" · transcribed "1.90 × 0^-4 mol L^-1s^-1"
- ch 3 p9 §3.2.3 ¶8: printed "aA + bB → cC + dD" · transcribed "aA + bB -> cC + dD"
- ch 3 p16 §3.3.3 ¶2: printed "[R] = [R]_0 / 2" · transcribed "[R] = 1/2 [R]_0"
- ch 3 p21 §3.4 ¶19: printed "Ae^(-Ea/RT)" · transcribed "Ae^(-E_a/RT)"
- ch 3 p21 §3.4 ¶19: printed "0.02 = Ae^-18230.8/8.314 × 500" · transcribed "0.02 = Ae^(-18230.8/8.314 × 500)"
- ch 4 p9 §4.3.4 ¶2: printed "Cr^V1O_4^2–" · transcribed "Cr^VI O_4^2–"
- ch 4 p12 §4.3.7 ¶2: printed "oxidises I⁻ to I_2" · transcribed "oxidises I^- to I_2"
- ch 4 p12 §4.3.7 ¶5: printed "stabilise V^V as VO_2^+" · transcribed "stabilise V^v as VO_2^+"
- ch 4 p13 §4.3.8 ¶2: printed "related to their E° values" · transcribed "related to their E^e values"
- ch 4 p17 §4.3.14 ¶3: printed "Mn^VIO_4 2- + 4 H^+" · transcribed "Mn^VI O_4^2- + 4 H^+"
- ch 4 p17 §4.4.1 ¶3: printed "VO_4 3-" · transcribed "VO_4^3-"
- ch 4 p21 §4.5.3 ¶1: printed "La(III) and Ln(III)" · transcribed "La(II) and Ln(III)"
- ch 4 p24 §4.6.1 ¶1: printed "5f^7 7s^2" · transcribed "5f^7 7s^2 and [Rn] 5f^7 6d^1 7s^2"
- ch 4 p26 §4.7 ¶1: printed "TiCl_4 with Al(CH_3)_3" · transcribed "TiCl_4 with A1(CH_3)_3"
- ch 5 p2 §5.1 ¶10: printed "[CoCl(NH_3)_5]^2+ and [CoCl_2(NH_3)_4]^+ are octahedral" · transcribed "[CoCl(NH_3)_5]^2+ and [CoCl_2(NH_3)_4]^+ are octahedral entities"
- ch 5 p11 §5.5.1 ¶1: printed "tetrahedral,
tetrahedral, square planar" · transcribed "octahedral, tetrahedral, square planar"
- ch 5 p13 §5.5.2 ¶3: printed "[CoF_6]^3–" · transcribed "[CoF_6.]^3–"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 1 p9: "1.6 H_2S, a toxic gas with rotten egg like smell"
- ch 1 p12: "p = K_H x."
- ch 1 p18: "Let T_f^0 be the freezing point of pure solvent"
- ch 2 p21: "Quantitative Aspects of Electrolysis"
- ch 2 p21: "Faraday's Laws of Electrolysis"
- ch 3 p1: "Chemical Kinetics helps us to understand how chemical reactions occur."
- ch 3 p3: "t/s 0 50 100 150 200 300 400 700 800"
- ch 3 p18: "3.5 A first order reaction has a rate constant"
- ch 3 p23: "The proper orientation of reactant molecules lead to bond formation whereas improper orientation makes them simply bounce back and no products are formed."
- ch 4 p9: "Intext Question 4.3 Which of the 3d series of the transition metals exhibits the largest number of oxidation states and why?"
- ch 4 p25: "Intext Question 4.10 Actinoid contraction is greater from element to element than lanthanoid contraction. Why?"
- ch 4 p26: "Summary heading and content are not applicable as they belong to summary section"
- ch 4 p26: "The d-block consisting of Groups 3-12 occupies the large middle section..."
- ch 5 p1: "Objectives"
- ch 5 p1: "Coordination Compounds are the backbone of modern inorganic"
- ch 5 p3: "Werner was born on December 12, 1866, in Mülhouse, a small community in the French province of Alsace."
- ch 5 p4: "diagram labels: nitrito-N, nitrito-O, thiocyanato-S, thiocyanato-N"
- ch 5 p7: "Notice how the name of the metal differs in cation and anion even though they contain the same metal ions."
- ch 5 p11: "Indicate the types of isomerism exhibited by the following complexes"
- ch 5 p11: "Give evidence that [Co(NH_3)_5Cl]SO_4"
- ch 5 p18: "5.6 [NiCl_4]^2- is paramagnetic"
- ch 5 p18: "5.7 [Fe(H_2O)_6]^3+ is strongly paramagnetic"
- ch 5 p20: "The chemistry of coordination compounds is an important and challenging area of modern inorganic chemistry."

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 1 p3 §1.2 ¶7: printed "mu g mL^-1" · transcribed "mu g mL^-1"
- ch 1 p6 §1.3.1 ¶2: printed "Solution <-> Solution" · transcribed "Solution <-> Solution"
- ch 1 p8 §1.3.2 ¶6: printed "76.48 kbar" · transcribed "76.48 kbar"
- ch 1 p16 §1.6.1 ¶10: printed "p_1^0 = 0.850 bar; p = 0.845 bar" · transcribed "p_1^0 = 0.850 bar; p = 0.845 bar"
- ch 2 p8 §2.3 ¶8: printed "RT/nF ln Q" · transcribed "RT/nF lnQ"
- ch 2 p9 §2.3.1 ¶1: printed "[Zn^2+] / [Cu^2+] = K_c" · transcribed "[Zn^2+] / [Cu^2+] = K_c"
- ch 2 p11 §2.4 ¶3: printed "1 / R = A / (rho l) = kappa A / l" · transcribed "1 / R = A / (rho l) = kappa A / l"
- ch 2 p18 §2.4.2 ¶7: printed "0.000989, 147.09" · transcribed "0.000989, 147.09"
- ch 2 p19 §2.4.2 ¶11: printed "nu_+ cations and nu_-
anions then" · transcribed "nu_+ cations and nu_- anions then"
- ch 2 p23 §2.5.1 ¶8: printed "NaCl (aq) -> (H_2O)" · transcribed "NaCl (aq) -> (H_2O)"
- ch 3 p5 §3.1 ¶16: printed "– Delta[Hg] / Delta t" · transcribed "– Delta[Hg] / Delta t"
- ch 3 p7 §3.2.2 ¶4: printed "-d[R]/dt" · transcribed "− d[R] / dt"
- ch 3 p10 §3.2.4 ¶2: printed "dissociation of hydrogen iodide" · transcribed "dissociation of hydrogen iodide"
- ch 3 p11 §3.3.1 ¶1: printed "d[R] = − k dt" · transcribed "d[R] = − k dt"
- ch 3 p11 §3.3.1 ¶1: printed "[R] = − k t + I" · transcribed "[R] = − k t + I"
- ch 3 p15 §3.3.2 ¶26: printed "x = (p_t - p_i)" · transcribed "x = (p_t - p_i)"
- ch 3 p15 §3.3.2 ¶26: printed "= 2p_i - p_t" · transcribed "= 2p_i – p_t"
- ch 3 p21 §3.4 ¶19: printed "Ae^(-18230.8/8.314 × 500)" · transcribed "Ae^(-18230.8/8.314 × 500)"
- ch 3 p23 §3.5 ¶3: printed "formation of methanol from bromoethane" · transcribed "formation of methanol from bromoethane"
- ch 3 p23 §3.5 ¶4: printed "Rate = P Z_AB e^(-E_a / RT)" · transcribed "Rate = P Z_AB e^(-E_a / RT)"
- ch 4 p9 §4.3.4 ¶5: printed "pi-acceptor character in addition to the sigma-bonding" · transcribed "pi-acceptor character in addition to the sigma-bonding"
- ch 4 p11 §4.3.7 ¶1: printed "beyond Mn no metal has a trihalide except FeX_3" · transcribed "beyond Mn no metal has a trihalide except FeX_3"
- ch 4 p12 §4.3.7 ¶5: printed "VO_2^+" · transcribed "VO_2^+"
- ch 4 p17 §4.4.1 ¶3: printed "to give VO_4^3- and VO_4^+" · transcribed "to give VO_4^3- and VO_4^+"
- ch 4 p18 §4.4.1 ¶9: printed "two tetrahedra sharing one corner" · transcribed "two tetrahedra sharing one corner"
- ch 4 p18 §4.4.1 ¶11: printed "6 I^- -> 3I_2 + 6 e^-" · transcribed "6 I^- -> 3I_2 + 6 e^-"
- ch 4 p18 §4.4.1 ¶15: printed "manganate, permanganate ion" · transcribed "manganate, permanganate ion"
- ch 4 p20 §4.4.1 ¶28: printed "2Mn^2+ + 5NO_3^- + 3H_2O" · transcribed "2Mn^2+ + 5NO_3^- + 3H_2O"
- ch 4 p21 §4.5.3 ¶1: printed "formation of Ce^IV" · transcribed "formation of Ce^IV"
- ch 4 p25 §4.6.4 ¶5: printed "the chemistry of elements succeeding the actinoids are much less known at the present time." · transcribed "the chemistry of elements succeeding the actinoids are much less known at the present time."
- ch 5 p10 §5.4.3 ¶1: printed "bound through oxygen (–ONO), and" · transcribed "bound through oxygen (–ONO), and"
- ch 5 p10 §5.4.3 ¶1: printed "through sulphur to give" · transcribed "through sulphur to give"
- ch 5 p10 §5.4.3 ¶1: printed "nitrite ligand is bound through nitrogen (–NO_2)" · transcribed "nitrite ligand is bound through nitrogen (–NO_2)"
- ch 5 p18 §5.5.5 ¶4: printed "Be_3Al_2Si_6O_18" · transcribed "Be_3Al_2Si_6O_18"
- ch 5 p19 §5.7 ¶4: printed "[Au(CN)_2]^-" · transcribed "[Au(CN)_2]^–"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 1 p1: "1.1 Types of Solutions"
- ch 1 p5: "1.1 Calculate the mass percentage of benzene (C_6H_6) and carbon tetrachloride (CCl_4) if 22 g of benzene is dissolved in 122 g of carbon tetrachloride."
- ch 1 p5: "1.2 Calculate the mole fraction of benzene in solution containing 30% by mass in carbon tetrachloride."
- ch 1 p5: "1.3 Calculate the molarity of each of the following solutions: (a) 30 g of Co(NO_3)_2. 6H_2O in 4.3 L of solution (b) 30 mL of 0.5 M H_2SO_4 diluted to 500 mL."
- ch 1 p5: "1.4 Calculate the mass of urea (NH_2CONH_2) required in making 2.5 kg of 0.25 molal aqueous solution."
- ch 1 p5: "1.5 Calculate (a) molality (b) molarity and (c) mole fraction of KI if the density of 20% (mass/mass) aqueous KI is 1.202 g mL^-1."
- ch 1 p8: "Table 1.2: Values of Henry's Law Constant for Some Selected Gases in Water"
- ch 1 p9: "1.7 Henry's law constant for CO_2 in water"
- ch 1 p10: "Fig. 1.3: The plot of vapour pressure and mole fraction of an ideal solution at constant temperature"
- ch 1 p15: "1.8 The vapour pressure of pure liquids A and B are 450 and 700 mm Hg respectively, at 350 K . Find out the composition of the liquid mixture if total vapour pressure is 600 mm Hg. Also find the composition of the vapour phase."
- ch 1 p23: "1.9 Vapour pressure of pure water at 298 K"
- ch 1 p23: "1.10 Boiling point of water at 750 mm Hg"
- ch 1 p23: "1.11 Calculate the mass of ascorbic acid"
- ch 1 p23: "1.12 Calculate the osmotic pressure in pascals"
- ch 2 p4: "2.2.1 Measurement of Electrode Potential"
- ch 2 p6: "2.1 How would you determine the standard electrode potential"
- ch 2 p6: "2.2 Can you store copper sulphate solutions in a zinc pot?"
- ch 2 p6: "2.3 Consult the table of standard electrode potentials"
- ch 2 p12: "Table 2.2: The values of Conductivity of some Selected Materials at 298.15 K"
- ch 2 p14: "Table 2.3: Conductivity and Molar conductivity of KCl solutions"
- ch 2 p19: "Table 2.4: Limiting Molar Conductivity for some Ions in Water at 298 K"
- ch 2 p24: "2.10 If a current of 0.5 ampere flows"
- ch 2 p24: "2.11 Suggest a list of metals"
- ch 2 p24: "2.12 Consider the reaction"
- ch 3 p4: "Fig 3.2 Instantaneous rate of hydrolysis"
- ch 3 p6: "3.1 For the reaction R -> P, the concentration of a reactant changes from 0.03M"
- ch 3 p6: "3.2 In a reaction, 2A -> Products, the concentration of A decreases from 0.5"
- ch 3 p7: "Table 3.2: Initial rate of formation of NO_2"
- ch 3 p18: "3.6 Time required to decompose"
- ch 4 p10: "Fig. 4.4: Observed and calculated values for the standard electrode potentials"
- ch 4 p12: "4.5 How would you account for the irregular variation of ionisation enthalpies (first and second) in the first series of the transition elements?"
- ch 4 p13: "4.6 Why is the highest oxidation state of a metal exhibited in its oxide or fluoride only?"
- ch 4 p13: "4.7 Which is a stronger reducing agent Cr^2+ or Fe^2+ and why?"
- ch 4 p14: "Table 4.7: Calculated and Observed Magnetic Moments (BM)"
- ch 4 p15: "Table 4.8: Colours of Some of the First Row (aquated) Transition Metal Ions"
- ch 4 p17: "4.9 Explain why Cu^+ ion is not stable in aqueous solutions?"
- ch 5 p2: "Table 5.1: Formulation of Cobalt(III) Chloride-Ammonia Complexes"
- ch 5 p8: "5.4.1 Geometric Isomerism"
- ch 5 p8: "Fig. 5.2: Geometrical isomers (cis and trans) of Pt"
- ch 5 p8: "Fig. 5.3: Geometrical isomers (cis and trans) of [Co(NH3)4Cl2]+"
- ch 5 p16: "Table 5.3: Relationship between the Wavelength of Light absorbed and the Colour observed"
- ch 5 p17: "Fig.5.10: Transition of an electron in"
- ch 5 p18: "5.5 Explain on the basis of valence bond theory"
- ch 5 p18: "5.8 Explain [Co(NH_3)_6]^3+ is an inner orbital complex"
- ch 5 p18: "5.9 Predict the number of unpaired electrons"
- ch 5 p18: "5.10 The hexaquo manganese(II) ion contains five unpaired electrons"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 676

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 143 | 143 | 133 | 10 | 0 | 0 | 2 | 91.6% |
| 2 | 136 | 136 | 130 | 6 | 0 | 0 | 7 | 90.4% |
| 3 | 152 | 152 | 148 | 4 | 0 | 0 | 4 | 95.4% |
| 4 | 128 | 128 | 119 | 9 | 0 | 0 | 3 | 90.6% |
| 5 | 117 | 117 | 114 | 3 | 0 | 0 | 1 | 96.6% |
clean for the book (PLAN D15 ✅): 628 of 676 paragraphs, 92.9%
not in the clean share, adjudicate before recording it: 112 page-level start flags, 0 numbered equations the print carries that the rows do not, 23 passages no row carries

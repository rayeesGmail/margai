# margai-pipeline ncert verify

- run: 2026-09-29 17:02 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 6 chapters of chem11-part1 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 769827e32d30d3e52f81efb16f7af0e2b9dea2cb584e7750d737c05133f60423

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 24 | 15 | 8 | 20 | 1 | 1 | 0 | 0 |
| 2 | 37 | 24 | 12 | 32 | 1 | 0 | 1 | 0 |
| 3 | 19 | 16 | 2 | 15 | 2 | 1 | 0 | 0 |
| 4 | 30 | 26 | 3 | 29 | 1 | 1 | 0 | 0 |
| 5 | 28 | 18 | 9 | 26 | 3 | 1 | 0 | 3 |
| 6 | 39 | 28 | 10 | 36 | 2 | 3 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 1 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §1 ¶1 "Science can be viewed as a continuing" · §1 ¶2 "Chemistry, as we understand it today, is" · §1 ¶3 "People in ancient India, already had the" · printed starts no row begins with: "all baser metals e.g., iron and" · "ii. ‘Elixir of life’ which would"
- ch 1 p4: 9 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "(i) Solids have definite volume and"
- ch 1 p5: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §1.2.2 ¶1 "In Class IX (Chapter 2), you have"
- ch 1 p6: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "their Measu rement"
- ch 1 p7: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.3.3 ¶3 "Maintaining the National Standards of Measurement The"
- ch 1 p8: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §1.3.3 ¶4 "The definitions of the SI base units" · §1.3.3 ¶5 "The SI system allows the use of" · §1.3.3 ¶6 "These prefixes are listed in Table 1.3." · §1.3.3 ¶7 "Let us now quickly go through some"
- ch 1 p10: 9 rows start here, the print starts 9 paragraphs — rows the print does not start: §1.3.6 ¶2 "Density of a substance is its amount" · printed starts no row begins with: "SI unit of density ="
- ch 1 p11: 6 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.4.1 ¶1 "As chemistry is the study of atoms" · §1.4.1 ¶2 "It may look funny for a moment" · §1.4.1 ¶3 "This problem is solved by using scientific" · §1.4.1 ¶4 "Thus, we can write 232.508 as 2.32508" · §1.4.1 ¶5 "Similarly, 0.00016 can be written as 1.6" · §1.4.1 ¶6 "While performing mathematical operations on numbers expressed" · printed starts no row begins with: "Scientists are in search of a" · "The metre was originally defined as"
- ch 1 p12: 12 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.4.1 ¶7 "Multiplication and Division These two operations follow" · §1.4.1 ¶8 "Addition and Subtraction For these two operations," · §1.4.1 ¶9 "Thus, for adding 6.65×10^4 and 8.95×10^3, exponent" · §1.4.1 ¶10 "Then, these numbers can be added as" · §1.4.1 ¶11 "Similarly, the subtraction of two numbers can" · §1.4.2 ¶4 "(1) All non-zero digits are significant. For" · §1.4.2 ¶5 "(2) Zeros preceding to first non-zero digit" · §1.4.2 ¶6 "(3) Zeros between two non-zero digits are" · §1.4.2 ¶7 "(4) Zeros at the end or right" · printed starts no row begins with: "are two significant figures." · "0.0052 has two significant figures." · "are significant. Thus, 2.005 has four"
- ch 1 p13: 14 rows start here, the print starts 9 paragraphs — rows the print does not start: §1.4.2 ¶8 "(5) Counting the numbers of object, for" · §1.4.2 ¶12 "The result cannot have more digits to" · §1.4.2 ¶13 "Here, 18.0 has only one digit after" · §1.4.2 ¶14 "In these operations, the result must be" · §1.4.3 ¶2 "Example A piece of metal is 3"
- ch 1 p14: 14 rows start here, the print starts 9 paragraphs — rows the print does not start: §1.4.3 ¶3 "Solution We know that 1 in =" · §1.4.3 ¶7 "Example A jug contains 2 L of" · §1.4.3 ¶8 "Solution Since 1 L = 1000 cm^3" · §1.4.3 ¶10 "Now 2 L = 2 × 1000" · §1.4.3 ¶12 "Example How many seconds are there in" · §1.4.3 ¶13 "Solution Here, we know 1 day =" · printed starts no row begins with: "and 1m = 100 cm, which"
- ch 1 p15: 11 rows start here, the print starts 14 paragraphs — printed starts no row begins with: "Hydrogen + Oxygen → Water" · "Hydrogen + Oxygen → Hydrogen Peroxide" · "Hydrogen + Oxygen → Water"
- ch 1 p16: 10 rows start here, the print starts 12 paragraphs — printed starts no row begins with: "of different elements differ in mass." · "different elements combine in a fixed"
- ch 1 p18: 12 rows start here, the print starts 9 paragraphs — rows the print does not start: §1.7.4 ¶4 "Thus, the formula mass of sodium chloride" · §1.7.4 ¶5 "Problem 1.1 Calculate the molecular mass of" · §1.7.4 ¶6 "Solution Molecular mass of glucose (C_6H_12O_6) =" · §1.8 ¶6 "Hence, so many entities (atoms, molecules or" · §1.8 ¶7 "We can, therefore, say that 1 mol" · printed starts no row begins with: "Calculate the molecular mass of glucose" · "Molecular mass of glucose (C H"
- ch 1 p19: 11 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.9.1 ¶1 "An empirical formula represents the simplest whole" · §1.9.1 ¶2 "If the mass per cent of various" · §1.9.1 ¶3 "Problem 1.2 A compound contains 4.07% hydrogen," · §1.9.1 ¶4 "Solution" · §1.9.1 ¶5 "Step 1. Conversion of mass per cent" · §1.9.1 ¶6 "Since we are having mass per cent," · §1.9.1 ¶7 "Step 2. Convert into number moles of" · §1.9.1 ¶8 "Divide the masses obtained above by respective"
- ch 1 p20: 15 rows start here, the print starts 11 paragraphs — rows the print does not start: §1.9.1 ¶9 "Step 3. Divide each of the mole" · §1.9.1 ¶10 "Since 2.021 is smallest value, division by" · §1.9.1 ¶11 "In case the ratios are not whole" · §1.9.1 ¶12 "Step 4. Write down the empirical formula" · §1.9.1 ¶13 "CH_2Cl is, thus, the empirical formula of" · §1.9.1 ¶14 "Step 5. Writing molecular formula" · §1.9.1 ¶18 "Empirical formula = CH_2Cl, n = 2." · §1.10 ¶4 "From these relationships, the given data can" · printed starts no row begins with: "two moles of H O(g)" · "of CO (g) and 2 molecules" · "(g) to give 22.7 L of" · "(g) to give 44 g of"
- ch 1 p21: 12 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.10.1 ¶2 "In performing stoichiometric calculations, this aspect is" · §1.10.2 ¶1 "A majority of reactions in the laboratories" · §1.10.2 ¶2 "Let us now study each one of" · §1.10.2 ¶4 "Equations (a) and (b) are balanced, since" · §1.10.2 ¶5 "Now, let us take combustion of propane," · §1.10.2 ¶6 "Step 1 Write down the correct formulas" · §1.10.2 ¶7 "Step 2 Balance the number of C" · §1.10.2 ¶8 "Step 3 Balance the number of H" · §1.10.2 ¶9 "Step 4 Balance the number of O" · §1.10.2 ¶10 "Step 5 Verify that the number of" · §1.10.2 ¶11 "All equations that have correct formulas for" · printed starts no row begins with: "C H (g) + O (g)"
- ch 1 p22: 12 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.10.2 ¶12 "Problem 1.3 Calculate the amount of water" · §1.10.2 ¶13 "Solution The balanced equation for the combustion" · §1.10.2 ¶16 "Problem 1.4 How many moles of methane" · §1.10.2 ¶17 "Solution According to the chemical equation, CH_4" · §1.10.2 ¶18 "Problem 1.5 50.0 kg of N_2 (g)" · §1.10.2 ¶19 "Solution A balanced equation for the above" · §1.10.2 ¶20 "According to the above equation, 1 mol" · §1.10.2 ¶21 "But we have only 4.96×10^3 mol H_2." · §1.10.2 ¶22 "Since 3 mol H_2(g) gives 2 mol" · §1.10.2 ¶23 "3.30 × 10^3 mol NH_3 (g) is"
- ch 1 p23: 14 rows start here, the print starts 8 paragraphs — rows the print does not start: §1.10.2 ¶24 "1. Mass per cent It is obtained" · §1.10.2 ¶25 "Problem 1.6 A solution is prepared by" · §1.10.2 ¶26 "Solution Mass per cent of A =" · §1.10.2 ¶27 "2. Mole Fraction It is the ratio" · §1.10.2 ¶28 "3. Molarity It is the most widely" · §1.10.2 ¶37 "Note that the number of moles of"
- ch 1 p24: 7 rows start here, the print starts 0 paragraphs — rows the print does not start: §1.10.2 ¶38 "Problem 1.7 Calculate the molarity of NaOH" · §1.10.2 ¶39 "Solution Since molarity (M) = No. of" · §1.10.2 ¶40 "Note that molarity of a solution depends" · §1.10.2 ¶41 "4. Molality It is defined as the" · §1.10.2 ¶42 "Problem 1.8 The density of 3 M" · §1.10.2 ¶43 "Solution M = 3 mol L^-1 Mass" · §1.10.2 ¶44 "Often in a chemistry laboratory, a solution"
- ch 2 p1: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §2 ¶1 "The existence of atoms has been proposed" · §2 ¶3 "In this unit we start with the"
- ch 2 p2: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.1.1 ¶3 "The results of these experiments are summarised" · §2.1.1 ¶4 "(ii) These rays themselves are not visible" · §2.1.1 ¶6 "(iv) In the presence of electrical or" · §2.1.1 ¶7 "(v) The characteristics of cathode rays (electrons)" · printed starts no row begins with: "field, these rays travel in straight"
- ch 2 p3: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §2.1.1 ¶8 "Thus, we can conclude that electrons are" · §2.1.2 ¶6 "Where m_e is the mass of the" · printed starts no row begins with: "field and thus greater is the" · "particle, greater the deflection."
- ch 2 p4: 9 rows start here, the print starts 12 paragraphs — rows the print does not start: §2.2 ¶2 "• to account for the stability of" · printed starts no row begins with: "charged gaseous ions." · "depends on the gas from which" · "of electrical charge." · "in terms of both physical and"
- ch 2 p5: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.1.4 ¶8 "Different atomic models were proposed to explain" · §2.2.1 ¶1 "J. J. Thomson, in 1898, proposed that" · §2.2.1 ¶2 "In the later half of the nineteenth" · printed starts no row begins with: "materials placed outside the cathode ray"
- ch 2 p6: 6 rows start here, the print starts 14 paragraphs — printed starts no row begins with: "(i) most of the α–particles passed" · "the gold foil undeflected." · "(ii) a small fraction of the" · "deflected by small angles." · "(iii) a very few α–particles (∼1" · "bounced back, that is, were deflected" · "the foil undeflected." · "the positively charged α–particles."
- ch 2 p7: 9 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "electrons that of revolving planets." · "together by electrostatic forces of"
- ch 2 p8: 12 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.2.4 ¶4 "Problem 2.1 Calculate the number of protons," · §2.2.4 ¶5 "Solution In this case, ^80_35Br , Z" · §2.2.4 ¶6 "Number of protons = number of electrons" · §2.2.4 ¶7 "Number of neutrons = 80 – 35" · §2.2.4 ¶8 "Problem 2.2 The number of electrons, protons" · §2.2.4 ¶9 "Solution The atomic number is equal to" · §2.2.4 ¶10 "Atomic mass number = number of protons" · §2.2.4 ¶11 "Species is not neutral as the number" · §2.2.4 ¶12 "Note : Before using the notation ^A_Z" · §2.2.5 ¶3 "* Classical mechanics is a theoretical science"
- ch 2 p9: 10 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "fields produced by oscillating charged"
- ch 2 p10: 7 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "wave is shown in Fig. 2.6." · "represent electromagnetic radiation."
- ch 2 p11: 14 rows start here, the print starts 11 paragraphs — rows the print does not start: §2.3.1 ¶14 "Problem 2.3 The Vividh Bharati station of" · §2.3.1 ¶15 "Solution The wavelength, lambda, is equal to" · §2.3.1 ¶16 "Problem 2.4 The wavelength range of the" · §2.3.1 ¶17 "Solution Using equation 2.5, frequency of violet" · §2.3.1 ¶19 "Problem 2.5 Calculate (a) wavenumber and (b)" · §2.3.1 ¶20 "Solution (a) Calculation of wavenumber (v_bar) lambda" · §2.3.2 ¶1 "Some of the experimental phenomenon such as" · §2.3.2 ¶5 "* Diffraction is the bending of wave" · §2.3.2 ¶6 "** Interference is the combination of two" · printed starts no row begins with: "(a) Calculation of wavenumber ( )" · "(b) Calculation of the frequency (ν" · "adiation: Planck’s Quantum" · "hot bodies (black-body radiation)" · "when radiation strikes it (photoelectric" · "function of temperature"
- ch 2 p13: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §2.3.2 ¶15 "In 1887, H. Hertz performed a very" · printed starts no row begins with: "proportional to the intensity or brightness"
- ch 2 p14: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.3.2 ¶22 "The particle nature of light posed a"
- ch 2 p15: 8 rows start here, the print starts 0 paragraphs — rows the print does not start: §2.3.2 ¶23 "Problem 2.6 Calculate energy of one mole" · §2.3.2 ¶24 "Solution Energy (E) of one photon is" · §2.3.2 ¶25 "Problem 2.7 A 100 watt bulb emits" · §2.3.2 ¶26 "Solution Power of the bulb = 100" · §2.3.2 ¶27 "Problem 2.8 When electromagnetic radiation of wavelength" · §2.3.2 ¶28 "Solution The energy (E) of a 300" · §2.3.2 ¶29 "Problem 2.9 The threshold frequency nu_0 for" · §2.3.2 ¶30 "Solution According to Einstein's equation Kinetic energy"
- ch 2 p16: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.3.3 ¶1 "The speed of light depends upon the" · §2.3.3 ¶2 "The spectrum of radiation emitted by a" · §2.3.3 ¶7 "When an electric discharge is passed through" · §2.3.3 ¶8 "* The restriction of any property to" · printed starts no row begins with: "E lectronic Energy Levels: Atomic"
- ch 2 p18: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.4 ¶2 "i) The electron in the hydrogen atom" · §2.4 ¶3 "ii) The energy of an electron in" · §2.4 ¶4 "Angular Momentum Just as linear momentum is" · §2.4 ¶5 "iii) The frequency of radiation absorbed or" · §2.4 ¶6 "iv) The angular momentum of an electron" · printed starts no row begins with: "(i) line spectrum of element is" · "(ii) there is regularity in the"
- ch 2 p19: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.4 ¶9 "a) The stationary states for electron are" · §2.4 ¶10 "b) The radii of the stationary states" · §2.4 ¶11 "c) The most important property associated with" · §2.4 ¶12 "Fig. 2.11 depicts the energies of different" · printed starts no row begins with: "Where m is the mass of" · "Principal quantum numbers."
- ch 2 p20: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §2.4 ¶14 "d) Bohr's theory can also be applied" · §2.4 ¶15 "e) It is also possible to calculate" · printed starts no row begins with: "and radii by the expression" · "and in terms of wavenumbers ("
- ch 2 p21: 12 rows start here, the print starts 7 paragraphs — rows the print does not start: §2.4.1 ¶6 "Problem 2.10 What are the frequency and" · §2.4.1 ¶7 "Solution Since n_i = 5 and n_f" · §2.4.1 ¶8 "Problem 2.11 Calculate the energy associated with" · §2.4.1 ¶9 "Solution E_n = - (2.18 × 10^(-18)" · §2.4.2 ¶2 "i) It fails to account for the" · §2.4.2 ¶3 "ii) It could not explain the ability" · printed starts no row begins with: "electric field (Stark effect)."
- ch 2 p22: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §2.5.1 ¶3 "Problem 2.12 What will be the wavelength" · §2.5.1 ¶4 "Solution According to de Brogile equation (2.22)" · §2.5.1 ¶5 "Problem 2.13 The mass of an electron" · §2.5.1 ¶6 "Solution Since K.E. = ½ mv^2 v" · §2.5.1 ¶7 "Problem 2.14 Calculate the mass of a" · §2.5.1 ¶8 "Solution lambda = 3.6 Å = 3.6" · printed starts no row begins with: "According to de Brogile equation (2.22)" · "Calculate the mass of a photon" · "Velocity of photon = velocity of"
- ch 2 p23: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.5.2 ¶4 "One of the important implications of the"
- ch 2 p24: 8 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.5.2 ¶10 "Problem 2.15 A microscope using suitable photons" · §2.5.2 ¶11 "Solution ∆x∆p = h / 4pi or" · §2.5.2 ¶12 "Problem 2.16 A golf ball has a" · §2.5.2 ¶13 "Solution The uncertainty in the speed is" · §2.5.2 ¶14 "One can now understand the reasons for"
- ch 2 p25: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.5.2 ¶15 "In view of these inherent weaknesses in" · §2.6 ¶5 "When Schrödinger equation is solved for hydrogen"
- ch 2 p26: 8 rows start here, the print starts 1 paragraphs — rows the print does not start: §2.6 ¶6 "Application of Schrödinger equation to multi-electron atoms" · §2.6 ¶7 "Quantum mechanical model of atom is the" · §2.6 ¶9 "2. The existence of quantised electronic energy" · §2.6 ¶10 "3. Both the exact position and exact" · §2.6 ¶11 "4. An atomic orbital is the wave" · §2.6 ¶12 "5. The probability of finding an electron" · §2.6.1 ¶1 "A large number of orbitals are possible"
- ch 2 p27: 9 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "0. For n = 2, the" · "Table 2.4 Subshell Notations"
- ch 2 p28: 8 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.6.1 ¶12 "Electron spin 's' : The three quantum" · §2.6.1 ¶15 "i) n defines the shell, determines the" · §2.6.1 ¶16 "ii) There are n subshells in the" · §2.6.1 ¶17 "iii) m_l designates the orientation of the" · §2.6.1 ¶18 "Orbit, orbital and its importance Orbit and"
- ch 2 p29: 12 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.6.1 ¶19 "iv) m_s refers to orientation of the" · §2.6.1 ¶20 "Problem 2.17 What is the total number" · §2.6.1 ¶21 "Solution For n = 3, the possible" · §2.6.1 ¶22 "Therefore, the total number of orbitals is" · §2.6.1 ¶23 "The same value can also be obtained" · §2.6.1 ¶24 "Problem 2.18 Using s, p, d, f" · §2.6.1 ¶25 "(a) n = 2, l = 1," · §2.6.1 ¶26 "Solution n l orbital a) 2 1"
- ch 2 p30: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.6.2 ¶8 "* If probability density |psi|^2 is constant"
- ch 2 p33: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.6.4 ¶2 "Aufbau Principle" · §2.6.4 ¶3 "The word 'aufbau' in German means 'building"
- ch 2 p34: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.6.4 ¶6 "The number of electrons to be filled" · §2.6.4 ¶7 "This rule deals with the filling of"
- ch 2 p35: 10 rows start here, the print starts 9 paragraphs — rows the print does not start: §2.6.5 ¶3 "(i) s^a p^b d^c ...... notation"
- ch 2 p37: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.6.6 ¶2 "The completely filled and completely half-filled subshells" · §2.6.6 ¶5 "You may note that the exchange energy"
- ch 3 p1: 2 rows start here, the print starts 5 paragraphs — rows the print does not start: §3 ¶1 "In this Unit, we will study the" · §3.1 ¶1 "We know by now that the elements" · printed starts no row begins with: "f blocks and learn their main" · "physical and chemical properties" · "• compare the reactivity of elements" · "and correlate it with their" · "ionization enthalpy and metallic"
- ch 3 p3: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.2 ¶4 "The properties of the elements are a" · §3.2 ¶5 "Mendeleev arranged elements in horizontal rows and"
- ch 3 p5: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §3.3 ¶2 "The physical and chemical properties of the" · §3.4 ¶2 "* Glenn T. Seaborg's work in the"
- ch 3 p8: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.4 ¶4 "Problem 3.1 What would be the IUPAC" · §3.4 ¶5 "Solution From Table 3.4, the roots for"
- ch 3 p9: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.5 ¶3 "Problem 3.2 How would you justify the" · §3.5 ¶4 "Solution When n = 5, l ="
- ch 3 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.6.4 ¶2 "Problem 3.3 The elements Z = 117" · §3.6.4 ¶3 "Solution We see from Fig. 3.2, that"
- ch 3 p12: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.6.5 ¶2 "Problem 3.4 Considering the atomic number and" · §3.6.5 ¶3 "Solution Metallic character increases down a group" · §3.7.1 ¶2 "(a) Atomic Radius You can very well"
- ch 3 p14: 7 rows start here, the print starts 1 paragraphs — rows the print does not start: §3.7.1 ¶5 "The removal of an electron from an" · §3.7.1 ¶7 "Problem 3.5 Which of the following species" · §3.7.1 ¶8 "Solution Atomic radii decrease across a period." · §3.7.1 ¶9 "Hence the largest species is Mg; the" · §3.7.1 ¶10 "A quantitative measure of the tendency of" · §3.7.1 ¶11 "* Two or more species with same"
- ch 3 p15: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.7.1 ¶12 "In other words, the first ionization enthalpy"
- ch 3 p16: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.7.1 ¶18 "Problem 3.6 The first ionization enthalpy (Delta_i" · §3.7.1 ¶19 "Solution It will be more close to"
- ch 3 p17: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.7.1 ¶23 "Problem 3.7 Which of the following will" · §3.7.1 ¶24 "Solution Electron gain enthalpy generally becomes more" · §3.7.1 ¶25 "(e) Electronegativity A qualitative measure of the" · §3.7.1 ¶26 "* In many books, the negative of" · printed starts no row begins with: "Table 3.7 Electron Gain Enthalpies* /"
- ch 3 p18: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.7.1 ¶28 "Electronegativity generally increases across a period from" · printed starts no row begins with: "Table 3.8(a) Electronegativity Values (on Pauling"
- ch 3 p19: 8 rows start here, the print starts 9 paragraphs — rows the print does not start: §3.7.2 ¶2 "The valence is the most characteristic property" · §3.7.2 ¶4 "Problem 3.8 Using the Periodic Table, predict" · §3.7.2 ¶5 "Solution (a) Silicon is group 14 element" · printed starts no row begins with: "(a) Periodicity of Valence or Oxidation" · "(b) aluminium and sulphur." · "(a) Silicon is group 14 element" · "compound formed would be SiBr ."
- ch 3 p20: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.7.2 ¶8 "The first element of each of the" · printed starts no row begins with: "(b) Anomalous Properties of Second"
- ch 3 p21: 8 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.7.2 ¶10 "Problem 3.9 Are the oxidation state and" · §3.7.2 ¶11 "Solution No. The oxidation state of Al" · §3.7.3 ¶3 "Problem 3.10 Show by a chemical reaction" · §3.7.3 ¶4 "Solution Na_2O with water forms a strong" · §3.7.3 ¶5 "Their basic or acidic nature can be" · §3.7.3 ¶6 "Among transition metals (3d series), the change"
- ch 4 p1: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §4 ¶1 "Matter is made up of one or"
- ch 4 p2: 12 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.1 ¶1 "In order to explain the formation of" · §4.1 ¶2 "Lewis pictured the atom in terms of" · §4.1 ¶3 "Lewis Symbols: In the formation of a" · §4.1 ¶4 "Significance of Lewis Symbols : The number" · §4.1 ¶5 "Kössel, in relation to chemical bonding, drew" · §4.1 ¶6 "• In the periodic table, the highly" · §4.1 ¶7 "• The formation of a negative ion" · §4.1 ¶8 "• The negative and positive ions thus" · §4.1 ¶9 "• The negative and positive ions are" · §4.1 ¶10 "For example, the formation of NaCl from" · §4.1 ¶11 "Similarly the formation of CaF_2 may be" · §4.1 ¶12 "The bond formed, as a result of" · printed starts no row begins with: "In order to e[Slain the formation" · "/ewis SiFtured the atom in terms" · ")or e[amSle the formation of 1a&l"
- ch 4 p3: 6 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.1 ¶13 "Kössel's postulations provide the basis for the" · §4.1.1 ¶1 "Kössel and Lewis in 1916 developed an" · §4.1.2 ¶3 "The Lewis dot structures can be written" · printed starts no row begins with: ".|ssel·s Sostulations SroYide the basis for" · ".|ssel and /ewis in  " · "7he /ewis dot struFtures Fan be" · "of an electron pair between the" · "one electron to the shared pair." · "of the sharing of electrons."
- ch 4 p4: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.1.3 ¶2 "• The total number of electrons required" · §4.1.3 ¶3 "• For anions, each negative charge would" · §4.1.3 ¶4 "• Knowing the chemical symbols of the" · §4.1.3 ¶5 "• In general the least electronegative atom" · §4.1.3 ¶6 "• After accounting for the shared pairs" · §4.1.3 ¶7 "Lewis representations of a few molecules/ions are" · printed starts no row begins with: " from the four hydrogen atoms" · "mean addition of one electron. For" · "subtraction of one electron from the"
- ch 4 p5: 14 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.1.3 ¶8 "Problem 4.1 Write the Lewis dot structure" · §4.1.3 ¶9 "Solution" · §4.1.3 ¶10 "Step 1. Count the total number of" · §4.1.3 ¶11 "Step 2. The skeletal structure of CO" · §4.1.3 ¶12 "Step 3. Draw a single bond (one" · §4.1.3 ¶13 "This does not complete the octet on" · §4.1.3 ¶14 "Problem 4.2 Write the Lewis structure of" · §4.1.3 ¶15 "Solution" · §4.1.3 ¶16 "Step 1. Count the total number of" · §4.1.3 ¶17 "Step 2. The skeletal structure of NO_2^-" · §4.1.3 ¶18 "Step 3. Draw a single bond (one" · §4.1.3 ¶19 "Hence we have to resort to multiple" · §4.1.4 ¶1 "Lewis dot structures, in general, do not" · §4.1.4 ¶2 "Formal charge (F.C.) on an atom in" · printed starts no row begins with: "/ewis dot struFtures in general do"
- ch 4 p6: 11 rows start here, the print starts 7 paragraphs — rows the print does not start: §4.1.4 ¶3 "The counting is based on the assumption" · §4.1.4 ¶4 "Let us consider the ozone molecule (O_3)." · §4.1.4 ¶5 "The atoms have been numbered as 1," · §4.1.4 ¶6 "Hence, we represent O_3 along with the" · §4.1.5 ¶1 "The octet rule, though useful, is not" · §4.1.5 ¶2 "The incomplete octet of the central atom" · §4.1.5 ¶3 "Li, Be and B have 1, 2" · §4.1.5 ¶4 "Odd-electron molecules In molecules with an odd" · §4.1.5 ¶5 "The expanded octet Elements in and beyond" · §4.1.5 ¶6 "Some of the examples of such compounds" · printed starts no row begins with: "7he Founting is based on the" · "/et us Fonsider the o]one moleFule" · "7he atoms haYe been numbered as" · "+enFe we reSresent 2 along with" · "7he oFtet rule though useful is" · "6ome of the e[amSles of suFh"
- ch 4 p7: 14 rows start here, the print starts 10 paragraphs — rows the print does not start: §4.1.5 ¶8 "Other drawbacks of the octet theory" · §4.1.5 ¶9 "• It is clear that octet rule" · §4.1.5 ¶10 "• This theory does not account for" · §4.1.5 ¶11 "• It does not explain the relative" · §4.2 ¶1 "From the Kössel and Lewis treatment of" · §4.2 ¶2 "• The ease of formation of the" · §4.2 ¶3 "• The arrangement of the positive and" · §4.2 ¶4 "The formation of a positive ion involves" · §4.2 ¶5 "The electron gain enthalpy, Delta_egH, is the" · §4.2 ¶7 "Most ionic compounds have cations derived from" · printed starts no row begins with: "compounds like XeF  .r) ," · ")rom the .|ssel and /ewis treatment" · "negatiYe ions from the resSeFtiYe neutral" · "7he formation of a SositiYe ion" · "7he electron gain enthalpy, ∆ H," · "0ost ioniF FomSounds haYe Fations"
- ch 4 p8: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.2 ¶10 "Since lattice enthalpy plays a key role" · §4.2.1 ¶2 "This process involves both the attractive forces" · printed starts no row begins with: "6inFe lattiFe enthalSy Slays a Ney" · "7his SroFess inYolYes both the attraFtiYe"
- ch 4 p10: 7 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.3.3 ¶5 "The difference in the Delta_aH^⊖ value shows" · §4.3.5 ¶2 "In both structures we have a O–O" · §4.3.5 ¶3 "The concept of resonance was introduced to" · printed starts no row begins with: "7he differenFe in the ∆ H" · "$Yerage bond enthalSy" · "is  N- mol ; being" · "In both struFtures we haYe a" · "7he FonFeSt of resonanFe was introduFed"
- ch 4 p11: 9 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.3.5 ¶4 "Some of the other examples of resonance" · §4.3.5 ¶5 "Problem 4.3 Explain the structure of CO_3^2-" · §4.3.5 ¶6 "Solution The single Lewis structure based on" · §4.3.5 ¶7 "Problem 4.4 Explain the structure of CO_2" · §4.3.5 ¶8 "Solution The experimentally determined carbon to oxygen" · §4.3.5 ¶9 "In general, it may be stated that" · §4.3.5 ¶10 "Many misconceptions are associated with resonance and" · §4.3.6 ¶1 "The existence of a hundred percent ionic" · §4.3.6 ¶2 "When covalent bond is formed between two" · printed starts no row begins with: "6ome of the other e[amSles of" · "7he FannoniFal forms haYe no real" · "7he e[istenFe of a hundred SerFent" · ":hen FoYalent bond is formed between"
- ch 4 p12: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §4.3.6 ¶4 "Dipole moment is usually expressed in Debye" · §4.3.6 ¶5 "Further dipole moment is a vector quantity" · §4.3.6 ¶6 "This arrow symbolises the direction of the" · §4.3.6 ¶8 "The dipole moment in case of BeF_2" · §4.3.6 ¶10 "Let us study an interesting case of" · printed starts no row begins with: "'iSole moment is usually e[Sressed in" · ")urther diSole moment is a YeFtor" · "7his arrow symbolises the direFtion of" · "7he diSole moment in Fase of" · "/et us study an interesting Fase"
- ch 4 p13: 7 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.3.6 ¶13 "• The smaller the size of the" · §4.3.6 ¶14 "• The greater the charge on the" · §4.3.6 ¶15 "• For cations of the same size" · §4.3.6 ¶16 "The cation polarises the anion, pulling the" · §4.4 ¶1 "As already explained, Lewis concept is unable" · printed starts no row begins with: "the shaSes of FoYalent moleFules 6idgwiFN"
- ch 4 p14: 14 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.4 ¶2 "The main postulates of VSEPR theory are" · §4.4 ¶3 "• The shape of a molecule depends" · §4.4 ¶4 "• Pairs of electrons in the valence" · §4.4 ¶5 "• These pairs of electrons tend to" · §4.4 ¶6 "• The valence shell is taken as" · §4.4 ¶7 "• A multiple bond is treated as" · §4.4 ¶8 "• Where two or more resonance structures" · §4.4 ¶9 "The repulsive interaction of electron pairs decrease" · §4.4 ¶10 "Lone pair (lp) – Lone pair (lp)" · §4.4 ¶11 "Nyholm and Gillespie (1957) refined the VSEPR" · §4.4 ¶13 "Table 4.6 (page114) shows the arrangement of" · §4.4 ¶14 "As depicted in Table 4.6, in the" · §4.4 ¶15 "The VSEPR Theory is able to predict" · printed starts no row begins with: "one another since their electron clouds" · "from one another." · "single super pair." · "model is applicable to any such" · "1yholm and *illesSie   refined" · "(ii) molecules in which the central" · "$s deSiFted in 7able  in" · "7he 96(35 7heory is able to"
- ch 4 p18: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.5 ¶1 "As we know that Lewis approach helps" · §4.5 ¶2 "Similarly the VSEPR theory gives the geometry" · §4.5 ¶4 "Consider two hydrogen atoms A and B" · §4.5 ¶5 "Attractive forces arise between:" · §4.5 ¶6 "(i) nucleus of one atom and its" · printed starts no row begins with: "$s we Nnow that /ewis aSSroaFh" · "6imilarly the 96(35 theory giYes the" · "&onsider two hydrogen atoms $ and" · "that is 1 – e and"
- ch 4 p19: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.5 ¶7 "Similarly repulsive forces arise between" · §4.5 ¶8 "(i) electrons of two atoms like e_A" · §4.5 ¶9 "(ii) nuclei of two atoms N_A –" · §4.5 ¶10 "Attractive forces tend to bring the two" · §4.5 ¶11 "Experimentally it has been found that the" · §4.5 ¶12 "Since the energy gets released when the" · printed starts no row begins with: "$ttraFtiYe forFes tend to bring the" · "([Serimentally it has been found that" · "6inFe the energy gets released when"
- ch 4 p20: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §4.5.2 ¶1 "As we have already seen, the covalent" · §4.5.2 ¶2 "In case of polyatomic molecules like CH_4," · §4.5.2 ¶3 "The valence bond theory explains the shape," · §4.5.3 ¶2 "The criterion of overlap, as the main" · §4.5.3 ¶3 "Let us first consider the CH_4 (methane)" · printed starts no row begins with: "$s we haYe already seen the" · "In Fase of SolyatomiF moleFules liNe" · "7he YalenFe bond theory e[Slains the" · "7he Friterion of oYerlaS as the" · "/et us first Fonsider the &+"
- ch 4 p21: 12 rows start here, the print starts 8 paragraphs — rows the print does not start: §4.5.4 ¶1 "The covalent bond may be classified into" · §4.5.4 ¶2 "(i) Sigma(sigma) bond, and (ii) pi(pi) bond" · §4.5.4 ¶3 "(i) Sigma(sigma) bond : This type of" · §4.5.4 ¶4 "• s-s overlapping : In this case," · §4.5.4 ¶5 "• s-p overlapping: This type of overlap" · §4.5.4 ¶6 "• p–p overlapping : This type of" · §4.5.4 ¶7 "(ii) pi(pi) bond : In the formation" · §4.5.5 ¶1 "Basically the strength of a bond depends" · §4.6 ¶1 "In order to explain the characteristic geometrical" · §4.6 ¶2 "Salient features of hybridisation: The main features" · §4.6 ¶3 "1. The number of hybrid orbitals is" · §4.6 ¶4 "2. The hybridised orbitals are always equivalent" · printed starts no row begins with: "7he FoYalent bond may be Flassified" · "(i) Sigma(σ) bond : 7his tySe" · "of combinations of atomic orbitals." · "aboYe and below the Slane of" · "%asiFally the strength of a bond" · "In order to e[Slain the FharaFteristiF" · "the number of the atomic orbitals" · "equiYalent in energy and shaSe"
- ch 4 p22: 13 rows start here, the print starts 7 paragraphs — rows the print does not start: §4.6 ¶5 "3. The hybrid orbitals are more effective" · §4.6 ¶6 "4. These hybrid orbitals are directed in" · §4.6 ¶7 "Important conditions for hybridisation" · §4.6 ¶8 "(i) The orbitals present in the valence" · §4.6 ¶9 "(ii) The orbitals undergoing hybridisation should have" · §4.6 ¶10 "(iii) Promotion of electron is not essential" · §4.6 ¶11 "(iv) It is not necessary that only" · §4.6.1 ¶1 "There are various types of hybridisation involving" · §4.6.1 ¶2 "(I) sp hybridisation: This type of hybridisation" · §4.6.1 ¶3 "The two sp hybrids point in the" · §4.6.1 ¶4 "Example of molecule having sp hybridisation" · §4.6.1 ¶5 "BeCl_2: The ground state electronic configuration of" · §4.6.1 ¶6 "(II) sp^2 hybridisation : In this hybridisation" · printed starts no row begins with: "forming stable bonds than the pure" · "indicates the geometry of the molecules." · "of the atom are hybridised." · "should haYe almost equal energy" · "condition prior to hybridisation." · "7here are Yarious tySes of hybridisation" · "7he two sp hybrids point in"
- ch 4 p23: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.6.1 ¶7 "(III) sp^3 hybridisation: This type of hybridisation" · §4.6.1 ¶8 "The structure of NH_3 and H_2O molecules" · §4.6.1 ¶9 "In case of H_2O molecule, the four" · printed starts no row begins with: "7he struFture of 1+ and +" · "In Fase of + 2 moleFule"
- ch 4 p24: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.6.2 ¶2 "sp^2 Hybridisation in C_2H_4: In the formation" · §4.6.2 ¶3 "Thus, in ethene molecule, the carbon-carbon bond" · printed starts no row begins with: "7hus in ethene moleFule the Farbon"
- ch 4 p25: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.6.2 ¶4 "sp Hybridisation in C_2H_2 : In the" · §4.6.3 ¶1 "The elements present in the third period" · §4.6.3 ¶2 "The important hybridisation schemes involving s, p" · §4.6.3 ¶4 "sp^3d hybrid orbitals filled by electron pairs" · printed starts no row begins with: "7he elements Sresent in the third" · "7he imSortant hybridisation sFhemes"
- ch 4 p26: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.6.3 ¶5 "Now the five orbitals (i.e., one s," · §4.7 ¶2 "(i) The electrons in a molecule are" · §4.7 ¶3 "(ii) The atomic orbitals of comparable energies" · §4.7 ¶4 "(iii) While an electron in an atomic" · printed starts no row begins with: "1ow the fiYe orbitals i.e., one"
- ch 4 p27: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §4.7 ¶5 "(iv) The number of molecular orbital formed" · §4.7 ¶6 "(v) The bonding molecular orbital has lower" · §4.7 ¶7 "(vi) Just as the electron probability distribution" · §4.7 ¶8 "(vii) The molecular orbitals like atomic orbitals" · §4.7.1 ¶1 "According to wave mechanics, the atomic orbitals" · §4.7.1 ¶2 "Let us apply this method to the" · §4.7.1 ¶3 "Therefore, the two molecular orbitals sigma and" · §4.7.1 ¶4 "The molecular orbital sigma formed by the" · §4.7.1 ¶5 "Qualitatively, the formation of molecular orbitals can" · printed starts no row begins with: "a molecular orbital is polycentric." · "called antibonding molecular orbital." · "by a molecular orbital." · "$FFording to waYe meFhaniFs the atomiF" · "/et us aSSly this method to" · "7herefore the two moleFular orbitals" · "7he moleFular orbital σ formed by" · "4ualitatiYely the formation of moleFular"
- ch 4 p28: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §4.7.2 ¶1 "The linear combination of atomic orbitals to" · §4.7.2 ¶4 "3. The combining atomic orbitals must overlap" · §4.7.4 ¶1 "We have seen that 1s atomic orbitals" · printed starts no row begins with: "7he linear Fombination of atomiF orbitals" · ":e haYe seen that s atomic"
- ch 4 p29: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.7.4 ¶2 "The energy levels of these molecular orbitals"
- ch 4 p30: 16 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.7.4 ¶3 "However, this sequence of energy levels of" · §4.7.4 ¶4 "The important characteristic feature of this order" · §4.7.5 ¶1 "The distribution of electrons among various molecular" · §4.7.5 ¶2 "Stability of Molecules: If N_b is the" · §4.7.5 ¶3 "(i) the molecule is stable if N_b" · §4.7.5 ¶4 "(ii) the molecule is unstable if N_b" · §4.7.5 ¶6 "Bond order (b.o.) is defined as one" · §4.7.5 ¶7 "The rules discussed above regarding the stability" · §4.7.5 ¶8 "Integral bond order values of 1, 2" · §4.7.5 ¶9 "The bond order between two atoms in" · §4.7.5 ¶10 "If all the molecular orbitals in a" · §4.8 ¶3 "The bond order of H_2 molecule can" · §4.8 ¶4 "This means that the two hydrogen atoms" · printed starts no row begins with: "+oweYer this sequenFe of energy leYels" · "7he imSortant FharaFteristiF feature" · "7he distribution of eleFtrons among Yarious" · "7he rules disFussed aboYe regarding the" · "7he bond order of + molecule" · "7his means that the two hydrogen"
- ch 4 p31: 12 rows start here, the print starts 13 paragraphs — rows the print does not start: §4.8 ¶6 "He_2 molecule is therefore unstable and does" · §4.8 ¶9 "The above configuration is also written as" · §4.8 ¶10 "From the electronic configuration of Li_2 molecule" · §4.8 ¶12 "The bond order of C_2 is ½" · §4.8 ¶14 "From the electronic configuration of O_2 molecule" · §4.8 ¶15 "So in oxygen molecule, atoms are held" · §4.8 ¶16 "Similarly, the electronic configurations of other homonuclear" · printed starts no row begins with: "%ond order of +e is ô" · "+e molecule is therefore unstable and" · "7he aboYe Fonfiguration is also written" · ")rom the eleFtroniF Fonfiguration of /i" · "7he bond order of & is" · ")rom the eleFtroniF Fonfiguration of 2" · "6o in o[ygen moleFule atoms are" · "6imilarly the eleFtroniF Fonfigurations"
- ch 4 p32: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.9 ¶1 "Nitrogen, oxygen and fluorine are the highly" · §4.9 ¶2 "Here, hydrogen bond acts as a bridge" · printed starts no row begins with: "1itrogen o[ygen and Áuorine are the"
- ch 4 p33: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.9.1 ¶2 "The magnitude of H-bonding depends on the" · §4.9.2 ¶1 "There are two types of H-bonds (i)" · §4.9.2 ¶2 "(1) Intermolecular hydrogen bond : It is" · §4.9.2 ¶3 "(2) Intramolecular hydrogen bond : It is" · printed starts no row begins with: "7he magnitude of +bonding deSends" · "7here are two tySes of +bonds"
- ch 5 p1: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §5 ¶1 "Chemical energy stored by molecules can be" · §5 ¶2 "How do we determine the energy changes" · §5 ¶3 "What drives a chemical reaction/process?" · §5 ¶4 "To what extent do the chemical reactions" · printed starts no row begins with: "and express it mathematically; also be" · "and apply it for spontaneity; In"
- ch 5 p2: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §5.1.2 ¶2 "1. Open System In an open system," · §5.1.2 ¶3 "2. Closed System In a closed system," · §5.1.2 ¶4 "* We could have chosen only the"
- ch 5 p3: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §5.1.2 ¶5 "3. Isolated System In an isolated system," · §5.1.4 ¶3 "(a) Work Let us first examine a"
- ch 5 p4: 11 rows start here, the print starts 7 paragraphs — rows the print does not start: §5.1.4 ¶5 "One way: We do some mechanical work," · §5.1.4 ¶6 "Second way: We now do an equal" · §5.1.4 ¶12 "(b) Heat We can also change the" · §5.1.4 ¶15 "* Earlier negative sign was assigned when"
- ch 5 p5: 12 rows start here, the print starts 13 paragraphs — rows the print does not start: §5.1.4 ¶18 "It is commonly stated as the law" · §5.1.4 ¶19 "Note: There is considerable difference between the" · §5.1.4 ¶20 "Problem 5.1 Express the change in internal" · §5.1.4 ¶24 "Solution (i) ∆ U = w_ad, wall" · §5.2.1 ¶2 "For understanding pressure-volume work, let us consider" · printed starts no row begins with: "The energy of an isolated system" · "type of wall does the system" · "does the system have?" · "(i) ∆ U = w ," · "(ii) ∆ U = – q," · "(iii) ∆ U = q –"
- ch 5 p7: 17 rows start here, the print starts 7 paragraphs — rows the print does not start: §5.2.1 ¶7 "Processes other than reversible processes are known" · §5.2.1 ¶11 "Free expansion: Expansion of a gas in" · §5.2.1 ¶14 "Isothermal and free expansion of an ideal" · §5.2.1 ¶15 "Equation 5.1, ΔU = q + w" · §5.2.1 ¶18 "Problem 5.2 Two litres of an ideal" · §5.2.1 ¶19 "Solution We have q = - w" · §5.2.1 ¶20 "Problem 5.3 Consider the same expansion, but" · §5.2.1 ¶21 "Solution We have q = - w" · §5.2.1 ¶22 "Problem 5.4 Consider the expansion given in" · §5.2.1 ¶23 "Solution We have q = - w"
- ch 5 p8: 17 rows start here, the print starts 17 paragraphs — rows the print does not start: §5.2.2 ¶2 "We know that the heat absorbed at" · §5.2.2 ¶14 "Here, ∆n_g refers to the number of" · §5.2.2 ¶17 "Problem 5.5 If water vapour is assumed" · printed starts no row begins with: "On rearranging, we get" · "so, equation (5.6) becomes" · "Since p is constant, we can"
- ch 5 p9: 9 rows start here, the print starts 9 paragraphs — rows the print does not start: §5.2.2 ¶18 "Solution (i) The change H_2O (l) ->" · §5.2.2 ¶19 "In thermodynamics, a distinction is made between" · §5.2.2 ¶21 "In this sub-section, let us see how" · printed starts no row begins with: "1 mol of water is vapourised" · "(b) Extensive and Intensive Properties" · "The magnitude of the coefficient depends"
- ch 5 p10: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §5.2.2 ¶27 "At constant volume, the heat capacity, C" · §5.2.2 ¶28 "We can write equation for heat, q" · §5.3 ¶2 "i) at constant volume, q_V ii) at" · §5.3 ¶3 "For chemical reactions, heat absorbed at constant" · printed starts no row begins with: "(d) The Relationship between C and" · "for an Ideal Gas"
- ch 5 p11: 10 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.3 ¶4 "(B) Delta H Measurements Measurement of heat" · §5.3 ¶6 "Problem 5.6 1g of graphite is burnt" · §5.3 ¶7 "Solution Suppose q is the quantity of" · §5.3 ¶8 "Quantity of heat from the reaction will" · §5.3 ¶9 "(Here, negative sign indicates the exothermic nature" · §5.3 ¶10 "Thus, Delta U for the combustion of" · §5.3 ¶11 "For combustion of 1 mol of graphite," · §5.4 ¶1 "In a chemical reaction, reactants are converted" · printed starts no row begins with: "reaction – Reaction Enthalpy"
- ch 5 p12: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §5.4 ¶4 "Enthalpy of a reaction depends on the" · §5.4 ¶7 "Phase transformations also involve energy changes. Ice," · printed starts no row begins with: "(a) Standard Enthalpy of Reactions" · "(b) Enthalpy Changes during Phase"
- ch 5 p13: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.4 ¶14 "Problem 5.7 A swimmer coming out from" · §5.4 ¶15 "Solution We can represent the process of" · §5.4 ¶16 "Problem 5.8 Assuming the water vapour to" · §5.4 ¶17 "The change take place as follows: Step"
- ch 5 p14: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.4 ¶18 "There is negligible change in the volume" · §5.4 ¶19 "Therefore, pDelta v = Delta n_g RT" · §5.4 ¶20 "The standard enthalpy change for the formation" · printed starts no row begins with: "(c) Standard Enthalpy of Formation"
- ch 5 p15: 12 rows start here, the print starts 11 paragraphs — rows the print does not start: §5.4 ¶26 "Here, we can make use of standard" · §5.4 ¶28 "A balanced chemical equation together with the" · printed starts no row begins with: "products involved in the reaction."
- ch 5 p16: 10 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.4 ¶34 "Note that the coefficients used in these" · §5.4 ¶36 "We know that enthalpy is a state" · §5.4 ¶40 "Although CO(g) is the major product, some" · printed starts no row begins with: "∆ H (H , g) =" · "the value of ∆ H is" · "(e) Hess’s Law of Constant Heat"
- ch 5 p17: 11 rows start here, the print starts 8 paragraphs — rows the print does not start: §5.5 ¶3 "Combustion reactions are exothermic in nature. These" · §5.5 ¶7 "Problem 5.9 The combustion of one mole" · §5.5 ¶8 "Solution The formation reaction of benezene is"
- ch 5 p18: 10 rows start here, the print starts 12 paragraphs — rows the print does not start: §5.5 ¶9 "Consider the following example of atomization of" · §5.5 ¶11 "In this case, the enthalpy of atomization" · §5.5 ¶12 "Chemical reactions involve the breaking and making" · §5.5 ¶14 "Diatomic Molecules: Consider the following process in" · §5.5 ¶17 "Polyatomic Molecules: Let us now consider a" · printed starts no row begins with: "Summing up the above two equations" · "Reversing equation (ii);" · "Adding equations (v) and (vi), we" · "(b) Enthalpy of Atomization" · "(c) Bond Enthalpy (symbol: ∆ H" · "(i) Bond dissociation enthalpy" · "(ii) Mean bond enthalpy"
- ch 5 p19: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.5 ¶19 "For example in CH_4, Delta_(C–H)H^0 is calculated" · §5.5 ¶22 "* Note that symbol used for bond" · §5.5 ¶23 "** If we use enthalpy of bond"
- ch 5 p20: 9 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.5 ¶24 "(d) Lattice Enthalpy The lattice enthalpy of" · §5.5 ¶27 "1. Na(s) -> Na(g), sublimation of sodium" · §5.5 ¶29 "3. 1/2 Cl_2(g) -> Cl(g), the dissociation" · §5.5 ¶30 "4. Cl(g) + e^-1(g) -> Cl^-(g) electron" · §5.5 ¶31 "You have learnt about ionization enthalpy and" · §5.5 ¶32 "5. Na^+(g) + Cl^-(g) -> Na^+Cl^-(s) The" · printed starts no row begins with: "sodium atoms, ionization enthalpy"
- ch 5 p22: 10 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.6 ¶1 "The first law of thermodynamics tells us" · §5.6 ¶4 "A spontaneous process is an irreversible process" · printed starts no row begins with: "HCl.25 aq. + 15 aq. →" · "reversed by some external agency."
- ch 5 p23: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §5.6 ¶11 "Then, what drives the spontaneous process in" · printed starts no row begins with: "(b) Entropy and Spontaneity"
- ch 5 p24: 14 rows start here, the print starts 12 paragraphs — rows the print does not start: §5.6 ¶23 "Problem 5.10 Predict in which of the" · §5.6 ¶26 "(iii) 2NaHCO_3 (s) -> Na_2CO_3 (s) +" · §5.6 ¶27 "(iv) H_2 (g) -> 2H (g)" · §5.6 ¶28 "Solution" · printed starts no row begins with: "is raised from 0 K to" · "an ordered state and therefore,"
- ch 5 p25: 11 rows start here, the print starts 13 paragraphs — rows the print does not start: §5.6 ¶33 "Problem 5.11 For oxidation of iron, 4Fe(s)" · §5.6 ¶34 "Solution One decides the spontaneity of a" · printed starts no row begins with: "condition of higher entropy." · "total ( sys surr ). For" · "This shows that the above reaction" · "At constant temperature,"
- ch 5 p26: 12 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.6 ¶42 "We know, Delta S_total = Delta S_sys" · §5.6 ¶46 "Using equation 5.21, the above equation can" · §5.6 ¶47 "Delta H_sys is the enthalpy change of" · §5.6 ¶48 "Delta G gives a criteria for spontaneity" · §5.6 ¶51 "Note : If a reaction has a" · §5.6 ¶52 "We know that for an isolated system" · §5.6 ¶53 "Molecules of a substance may move in" · printed starts no row begins with: "∆H is the enthalpy change of" · "∆G gives a criteria for spontaneity" · "(a) The positive entropy change of" · "(d) Entropy and Second Law of" · "(e) Absolute Entropy and Third Law"
- ch 5 p27: 10 rows start here, the print starts 9 paragraphs — rows the print does not start: §5.7 ¶10 "Using equation (5.24),"
- ch 5 p28: 12 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.7 ¶12 "Using equation (5.24)," · §5.7 ¶13 "Problem 5.12 Calculate Delta_r G^0 for conversion" · §5.7 ¶14 "Solution We know Delta_r G^0 = –" · §5.7 ¶15 "Therefore, Delta_r G^0 = – 2.303 (8.314" · §5.7 ¶16 "Problem 5.13 Find out the value of" · §5.7 ¶17 "Solution We know, log K = (–Delta_r" · §5.7 ¶18 "Problem 5.14 At 60°C, dinitrogen tetroxide is" · §5.7 ¶19 "Solution N_2O_4(g) <-> 2NO_2(g)" · §5.7 ¶20 "If N_2O_4 is 50% dissociated, the mole" · §5.7 ¶21 "The equilibrium constant K_p is given by" · §5.7 ¶22 "Since Delta_r G^0 = –RT ln K_p" · printed starts no row begins with: "(i) It is possible to obtain" · "for economic yields of the products."
- ch 6 p1: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §6 ¶1 "Chemical equilibria are important in numerous biological" · §6 ¶2 "When a liquid evaporates in a closed" · printed starts no row begins with: "hydrogen ion concentration; fast or slow"
- ch 6 p2: 10 rows start here, the print starts 13 paragraphs — printed starts no row begins with: "possible to detect these experimentally." · "at equilibrium stage." · "rate so that the amount of"
- ch 6 p3: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §6.1.2 ¶2 "However, the rate of increase in pressure" · printed starts no row begins with: "Camphor (solid) Camphor (vapour)"
- ch 6 p4: 10 rows start here, the print starts 9 paragraphs — rows the print does not start: §6.1.4 ¶1 "Solids in liquids" · §6.1.4 ¶2 "We know from our experience that we" · §6.1.4 ¶4 "Gases in liquids" · §6.1.4 ¶5 "When a soda water bottle is opened," · printed starts no row begins with: "of Solid or Gases in Liquids" · "vapour pressure is constant at a" · "the solubility is constant at a"
- ch 6 p5: 12 rows start here, the print starts 13 paragraphs — rows the print does not start: §6.2 ¶3 "With passage of time, there is accumulation" · printed starts no row begins with: "system at a given temperature." · "but stable condition."
- ch 6 p6: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.2 ¶7 "Equilibrium whether in a physical or in"
- ch 6 p8: 7 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "1 mol 1 mol 2 mol"
- ch 6 p9: 10 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "is expressed as,"
- ch 6 p10: 9 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.3 ¶20 "Problem 6.1 The following concentrations were obtained" · §6.3 ¶21 "Solution The equilibrium constant for the reaction," · §6.3 ¶22 "Problem 6.2 At equilibrium, the concentrations of" · §6.3 ¶23 "Solution For the reaction equilibrium constant, K_c" · §6.4.1 ¶3 "Here, p is the pressure in Pa,"
- ch 6 p11: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.4.1 ¶8 "Similarly, for a general reaction a A" · §6.4.1 ¶9 "It is necessary that while calculating the" · §6.4.1 ¶11 "Problem 6.3 PCl_5, PCl_3 and Cl_2 are"
- ch 6 p12: 13 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.4.1 ¶12 "Solution The equilibrium constant K_c for the" · §6.4.1 ¶13 "Problem 6.4 The value of K_c =" · §6.4.1 ¶14 "Solution For the reaction, CO (g) +" · §6.4.1 ¶15 "Let x mole per litre of each" · §6.4.1 ¶16 "At equilibrium: (0.1-x) M (0.1-x) M x" · §6.4.1 ¶17 "Hence, equilibrium constant can be written as," · §6.4.1 ¶18 "the value 0.194 should be neglected because" · §6.4.1 ¶19 "Hence the equilibrium concentrations are, [CO_2] =" · §6.4.1 ¶20 "Problem 6.5 For the equilibrium, 2NOCl(g) <->" · §6.4.1 ¶21 "Solution We know that, K_p = K_c(RT)^(Delta"
- ch 6 p13: 11 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.5 ¶6 "Units of Equilibrium Constant The value of" · §6.5 ¶7 "For the reactions, H_2(g) + I_2(g) <->" · §6.5 ¶8 "N_2O_4(g) <-> 2NO_2 (g), K_c has unit" · §6.5 ¶9 "Equilibrium constants can also be expressed as" · §6.5 ¶13 "Problem 6.6 The value of K_p for" · §6.5 ¶14 "Solution For the reaction, let ‘x’ be"
- ch 6 p14: 14 rows start here, the print starts 15 paragraphs — rows the print does not start: §6.6 ¶7 "Let us consider applications of equilibrium constant" · §6.6.1 ¶3 "• If K_c > 10^3, products predominate" · §6.6.1 ¶5 "(b) H_2(g) + Cl_2(g) <-> 2HCl(g) at" · §6.6.1 ¶6 "(c) H_2(g) + Br_2(g) <-> 2HBr (g)" · §6.6.1 ¶7 "• If K_c < 10^-3, reactants predominate" · printed starts no row begins with: "constant value at equilibrium state." · "the reactants and products." · "balanced equation at a given temperature." · "basis of its magnitude," · "Consider the following examples:" · "has a very large equilibrium constant,"
- ch 6 p15: 20 rows start here, the print starts 15 paragraphs — rows the print does not start: §6.6.1 ¶9 "(b) N_2(g) + O_2(g) <-> 2NO(g), at" · §6.6.1 ¶10 "• If K_c is in the range" · §6.6.1 ¶13 "These generarlisations are illustrated in Fig. 6.6" · §6.6.2 ¶11 "• If Q_c < K_c, net reaction" · §6.6.2 ¶12 "• If Q_c > K_c, net reaction" · §6.6.2 ¶13 "• If Q_c = K_c, no net" · §6.6.2 ¶14 "Problem 6.7 The value of K_c for" · printed starts no row begins with: "O at 500 K has a" · "Consider the following examples:"
- ch 6 p16: 12 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.6.2 ¶15 "Solution For the reaction the reaction quotient" · §6.6.3 ¶2 "Step 1. Write the balanced equation for" · §6.6.3 ¶3 "Step 2. Under the balanced equation, make" · §6.6.3 ¶5 "Step 3. Substitute the equilibrium concentrations into" · §6.6.3 ¶6 "Step 4. Calculate the equilibrium concentrations from" · §6.6.3 ¶7 "Step 5. Check your results by substituting" · §6.6.3 ¶8 "Problem 6.8 13.8g of N_2O_4 was placed" · §6.6.3 ¶9 "Solution We know pV = nRT Total" · §6.6.3 ¶10 "Problem 6.9 3.00 mol of PCl_5 kept" · §6.6.3 ¶11 "Solution PCl_5 <-> PCl_3 + Cl_2 Initial" · printed starts no row begins with: "(a) the initial concentration," · "(b) the change in concentration on" · "(c) the equilibrium concentration."
- ch 6 p17: 15 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.7 ¶1 "The value of K_c for a reaction" · §6.7 ¶2 "• ΔG is negative, then the reaction" · §6.7 ¶3 "• ΔG is positive, then reaction is" · §6.7 ¶4 "• ΔG is 0, reaction has achieved" · §6.7 ¶7 "Taking antilog of both sides, we get," · §6.7 ¶9 "• If ΔG^⊖ < 0, then –ΔG^⊖/RT" · §6.7 ¶10 "• If ΔG^⊖ > 0, then –ΔG^⊖/RT" · §6.7 ¶11 "Problem 6.10 The value of ΔG^⊖ for" · §6.7 ¶12 "Solution ΔG^⊖ = 13.8 kJ/mol = 13.8" · §6.7 ¶13 "Problem 6.11 Hydrolysis of sucrose gives, Sucrose" · §6.7 ¶14 "Solution ΔG^⊖ = – RT lnK_c ΔG^⊖" · printed starts no row begins with: "Reac tion Quo tient Q and" · "spontaneous and proceeds in the forward" · "converted to the reactants."
- ch 6 p18: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.8.1 ¶2 "• The concentration stress of an added" · §6.8.1 ¶3 "• The concentration stress of a removed" · §6.8.1 ¶4 ""When the concentration of any of the" · printed starts no row begins with: "the added substance."
- ch 6 p19: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.8.1 ¶9 "This can be demonstrated by the following" · §6.8.2 ¶3 "Here, 4 mol of gaseous reactants (CO"
- ch 6 p20: 11 rows start here, the print starts 9 paragraphs — rows the print does not start: §6.8.4 ¶3 "• The equilibrium constant for an exothermic" · §6.8.4 ¶4 "• The equilibrium constant for an endothermic" · §6.8.4 ¶7 "Effect of Temperature – An experiment Effect" · printed starts no row begins with: "reaction (negative ∆H) decreases as the"
- ch 6 p21: 10 rows start here, the print starts 9 paragraphs — rows the print does not start: §6.8.5 ¶6 "Note: If a reaction has an exceedingly"
- ch 6 p23: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.10.1 ¶4 "Hydronium and Hydroxyl Ions Hydrogen ion by" · printed starts no row begins with: "MOH(aq) → M (aq) + OH"
- ch 6 p24: 9 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.10.2 ¶7 "Problem 6.12 What will be the conjugate" · §6.10.2 ¶8 "Solution The conjugate bases should have one" · §6.10.2 ¶9 "Problem 6.13 Write the conjugate acids for" · §6.10.2 ¶10 "Solution The conjugate acid should have one" · §6.10.2 ¶11 "Problem 6.14 The species: H_2O, HCO_3^–, HSO_4^–" · §6.10.2 ¶12 "Solution The answer is given in the"
- ch 6 p25: 12 rows start here, the print starts 11 paragraphs — rows the print does not start: §6.10.3 ¶4 "Problem 6.15 Classify the following species into" · §6.10.3 ¶5 "(a) HO^– (b) F^– (c) H^+ (d)" · §6.10.3 ¶6 "Solution" · printed starts no row begins with: "donate an electron lone pair (:OH" · "electron lone pairs."
- ch 6 p26: 11 rows start here, the print starts 10 paragraphs — rows the print does not start: §6.11.1 ¶1 "Some substances like water are unique in" · §6.11.1 ¶2 "The dissociation constant is represented by, K" · printed starts no row begins with: "and its Ionic Product"
- ch 6 p27: 11 rows start here, the print starts 10 paragraphs — rows the print does not start: §6.11.2 ¶10 "Problem 6.16 The concentration of hydrogen ion" · §6.11.2 ¶11 "Solution pH = – log[3.8 × 10^-3]" · §6.11.2 ¶12 "Problem 6.17 Calculate pH of a 1.0" · printed starts no row begins with: "–log K = – log {[H" · "Note that although K may change"
- ch 6 p28: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §6.11.2 ¶13 "Solution 2H_2O (l) <-> H_3O^+ (aq) +" · printed starts no row begins with: "Let α be the extent of"
- ch 6 p29: 21 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.11.3 ¶8 "Step 1. The species present before dissociation" · §6.11.3 ¶9 "Step 2. Balanced equations for all possible" · §6.11.3 ¶10 "Step 3. The reaction with the higher" · §6.11.3 ¶11 "Step 4. Enlist in a tabular form" · §6.11.3 ¶12 "(a) Initial concentration, c." · §6.11.3 ¶14 "(c) Equilibrium concentration." · §6.11.3 ¶15 "Step 5. Substitute equilibrium concentrations into equilibrium" · §6.11.3 ¶16 "Step 6. Calculate the concentration of species" · §6.11.3 ¶17 "Step 7. Calculate pH = – log[H_3O^+]" · §6.11.3 ¶19 "Problem 6.18 The ionization constant of HF" · §6.11.3 ¶20 "Solution The following proton transfer reactions are" · §6.11.3 ¶21 "Substituting equilibrium concentrations in the equilibrium reaction" · §6.11.3 ¶22 "We obtain the following quadratic equation: alpha^2" · §6.11.3 ¶23 "The quadratic equation in alpha can be" · §6.11.3 ¶24 "The negative root is not acceptable and" · §6.11.3 ¶25 "This means that the degree of ionization," · §6.11.3 ¶26 "Problem 6.19 The pH of 0.1M monobasic" · printed starts no row begins with: "to equilibrium in terms of α,"
- ch 6 p30: 9 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.11.3 ¶27 "Solution pH = – log [H^+] Therefore," · §6.11.3 ¶29 "Problem 6.20 Calculate the pH of 0.08M" · §6.11.3 ¶30 "Solution HOCl(aq) + H_2O (l) <-> H_3O^+(aq)" · §6.11.4 ¶3 "Alternatively, if c = initial concentration of"
- ch 6 p31: 12 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.11.4 ¶7 "Problem 6.21 The pH of 0.004M hydrazine" · §6.11.4 ¶8 "Solution NH_2NH_2 + H_2O <-> NH_2NH_3^+ +" · §6.11.4 ¶9 "From the pH we can calculate the" · §6.11.4 ¶10 "The concentration of the corresponding hydrazinium ion" · §6.11.4 ¶11 "Thus, K_b = [NH_2NH_3^+][OH^-] / [NH_2NH_2] =" · §6.11.4 ¶12 "Problem 6.22 Calculate the pH of the" · §6.11.4 ¶13 "Solution NH_3 + H_2O <-> NH_4^+ +" · §6.11.4 ¶14 "As K_b is small, we can neglect" · printed starts no row begins with: "NH (aq) + H O(l) H" · "NH (aq) + H O(l) NH" · "Net: 2 H O(l) H O"
- ch 6 p32: 11 rows start here, the print starts 9 paragraphs — rows the print does not start: §6.11.5 ¶10 "Problem 6.23 Determine the degree of ionization" · §6.11.5 ¶11 "Solution The ionization of NH_3 in water" · §6.11.6 ¶1 "Some of the acids like oxalic acid," · printed starts no row begins with: "B(aq) + H O(l) BH (aq)"
- ch 6 p33: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §6.11.8 ¶1 "Consider an example of acetic acid dissociation" · printed starts no row begins with: "Ionization of Acids and Bases"
- ch 6 p34: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.11.8 ¶5 "Problem 6.24 Calculate the pH of a" · §6.11.8 ¶6 "Solution NH_3 + H_2O -> NH_4^+ +" · §6.11.8 ¶7 "On addition of 25 mL of 0.1M" · §6.11.8 ¶8 "The resulting 75 mL of solution contains" · §6.11.8 ¶9 "The final 75 mL solution after neutralisation" · printed starts no row begins with: "HAc(aq) H (aq) + Ac (aq)" · "Change in concentration (M)"
- ch 6 p35: 17 rows start here, the print starts 14 paragraphs — rows the print does not start: §6.11.9 ¶8 "Acetic acid being a weak acid (K_a" · §6.11.9 ¶9 "Similarly, NH_4Cl formed from weak base, NH_4OH" · §6.11.9 ¶13 "CH_3COOH and NH_4OH, also remain into partially" · §6.11.9 ¶16 "Problem 6.25 The pK_a of acetic acid" · §6.11.9 ¶17 "Solution pH = 7 + ½ [pK_a" · printed starts no row begins with: "Similarly, NH Cl formed from weak" · "CH COOH and NH OH, also"
- ch 6 p36: 8 rows start here, the print starts 10 paragraphs — rows the print does not start: §6.12.1 ¶2 "Preparation of Acidic Buffer To prepare a" · printed starts no row begins with: "For which we can write the" · "Rearranging the expression we have," · "Taking logarithm on both the sides"
- ch 6 p38: 7 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.13.1 ¶7 "A solid salt of the general formula" · §6.13.1 ¶10 "Problem 6.26 Calculate the solubility of A_2X_3" · §6.13.1 ¶11 "Solution A_2X_3 -> 2A^3+ + 3X^2- K_sp" · §6.13.1 ¶12 "Problem 6.27 The values of K_sp of" · §6.13.1 ¶13 "Solution AgCN <-> Ag^+ + CN^-"
- ch 6 p39: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.13.2 ¶1 "It is expected from Le Chatelier's principle" · §6.13.2 ¶2 "Problem 6.28 Calculate the molar solubility of" · §6.13.2 ¶3 "Solution Let the solubility of Ni(OH)_2 be" · §6.13.2 ¶4 "The solubility of salts of weak acids" · printed starts no row begins with: "of Ionic Salts"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 5 p21: the print starts "The values of ∆H show general" where §5.5 ¶41 starts "The values of Delta H show general" — the layer dropped the line's math
- ch 5 p26: the print starts "(i) If ∆G is negative (<" where §5.6 ¶49 starts "(i) If Delta G is negative (<" — the layer dropped the line's math
- ch 5 p26: the print starts "(ii) If ∆G is positive (>" where §5.6 ¶50 starts "(ii) If Delta G is positive (>" — the layer dropped the line's math

## joins across page breaks against the print

- ch 1 §1.4.2 ¶8 starts a paragraph at the top of p13, but the print continues p12's: "(5) Counting the numbers of object,"
- ch 2 §2.4 ¶6 runs from p18 onto p19, but p19 opens a new paragraph: "Where m is the mass of"
- ch 3 §3.7.1 ¶12 starts a paragraph at the top of p15, but the print continues p14's: "In other words, the first ionization"
- ch 3 §3.7.1 ¶21 runs from p16 onto p17, but p17 opens a new paragraph: "Table 3.7 Electron Gain Enthalpies* /"
- ch 4 §4.6 ¶5 starts a paragraph at the top of p22, but the print continues p21's: " 7he hybrid orbitals are more"
- ch 5 §5.2.1 ¶7 starts a paragraph at the top of p7, but the print continues p6's: "Processes other than reversible processes Isothermal"
- ch 5 §5.2.2 ¶17 runs from p8 onto p9, but p9 opens a new paragraph: "1 mol of water is vapourised"
- ch 5 §5.7 ¶10 runs from p27 onto p28, but p28 opens a new paragraph: "(i) It is possible to obtain"
- ch 6 §6.1.2 ¶2 starts a paragraph at the top of p3, but the print continues p2's: "However, the rate of increase in"
- ch 6 §6.13.1 ¶7 starts a paragraph at the top of p38, but the print continues p37's: "with molar solubility S in equilibrium"

## figure_refs against the paragraph and the chapter's captions

- ch 1 §1.4.2 ¶14: the paragraph mentions fig 2.5, which figure_refs does not carry
- ch 3 §3.2 ¶1: the paragraph mentions table 3.2, which figure_refs does not carry
- ch 4 §4.3.1 ¶3: figure_refs carries "Table 4.2", which no caption in chapter 4 prints
- ch 5 §5.6 ¶5: figure_refs carries "Fig. 5.10(a)", which the paragraph never mentions
- ch 6 §6.11.6 ¶3: the paragraph mentions table 6.8, which figure_refs does not carry
- ch 6 §6.11.6 ¶4: figure_refs carries "Table 6.8", which the paragraph never mentions
- ch 6 §6.13.1 ¶9: figure_refs carries "Table 6.9", which no caption in chapter 6 prints

## numbered equations the print carries that the rows do not

- ch 2 p19: the print numbers (2.13) twice, the rows carry it once — a displayed equation dropped, its number altered, or a reference to it lost

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
none
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (1454 rows)
request id: pipeline-ncert-verify-0ce2fc66-4dbb-40f1-818f-4327f895b87d

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 177 | 56 | 121 |
artefact: verify/chem11-part1/en.jsonl (177 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 56 | 255984 | 20040 | 398805 | 7251 | ₹73.17 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 1 p3 §1 ¶15: printed "chalk, red lead and minium" · transcribed "chalk, red lead and minimum"
- ch 1 p3 §1 ¶17: printed "named 'Paramānu' (comparable" · transcribed "named 'Paramãnu' (comparable"
- ch 1 p3 §1 ¶17: printed "atoms (Paramānu), which are eternal" · transcribed "atoms (Paramãnu), which are eternal"
- ch 1 p3 §1 ¶17: printed "these (Paramānu) could form pairs" · transcribed "these (Paramãnu) could form pairs"
- ch 1 p3 §1 ¶20: printed "structure, properties and interaction of matter" · transcribed "structure, properties and interection of matter"
- ch 1 p4 §1.1 ¶4: printed "like acids, alkalies, dyes, polymers metals, etc." · transcribed "like acids, alkalies, dyes, polymesr metals, etc."
- ch 1 p7 §1.3.2 ¶1: printed "volume, etc., are quantitative" · transcribed "volume, etc., are quantitative in nature"
- ch 1 p16 §1.7.1 ¶1: printed "could determine the mass of one atom relative to another" · transcribed "scientists could determine the mass of one atom relative to another"
- ch 1 p17 §1.7.2 ¶2: printed "(2 ×10^-10) (14.00317 u)" · transcribed "(2 × 10^-12) (14.00317 u)"
- ch 1 p17 §1.7.4 ¶2: printed "six Cl- ion" · transcribed "six Cl^- ion"
- ch 1 p18 §1.8 ¶4: printed "1.992648 × 10^-23 g / /^12 C atom" · transcribed "1.992648 × 10^-23 g / ^12C atom"
- ch 1 p20 §1.10 ¶4: printed "mass ⇄ moles ⇄ no. of molecules" · transcribed "mass <-> moles <-> no. of molecules"
- ch 1 p21 §1.10.2 ¶9: printed "needed to supply the required 10 oxygen atoms." · transcribed "needed to supply the required 10 CO_2 and 4×1= 4 in water). Therefore, five O_2 molecules are needed to supply the required 10 oxygen atoms."
- ch 1 p24 §1.10.2 ¶43: printed "1250 –175.5" · transcribed "1250 –75.5"
- ch 2 p4 §2.1.4 ¶6: printed "a mass slightly greater" · transcribed "mass slightly greater"
- ch 2 p6 §2.2.2 ¶4: printed "Most of the space" · transcribed "Most of the space in the atom is empty as"
- ch 2 p11 §2.3.1 ¶20: printed "v_bar = c / lambda" · transcribed "v_bar = c / lambda = 3 × 10^8 m s^-1"
- ch 2 p14 §2.3.2 ¶19: printed "red light [v = (4.3 to 4.6) × 10^14 Hz]" · transcribed "red light [nu = (4.3 to 4.6) × 10^14 Hz]"
- ch 2 p14 §2.3.2 ¶19: printed "yellow light (v = 5.1–5.2 × 10^14 Hz)" · transcribed "yellow light (nu = 5.1–5.2 × 10^14 Hz)"
- ch 2 p14 §2.3.2 ¶19: printed "frequency (v_0) for potassium" · transcribed "frequency (nu_0) for potassium"
- ch 2 p14 §2.3.2 ¶21: printed "energy equal to h v" · transcribed "energy equal to h nu"
- ch 2 p14 §2.3.2 ¶21: printed "electron is h v_0" · transcribed "electron is h nu_0"
- ch 2 p14 §2.3.2 ¶21: printed "(h v – h v_0)" · transcribed "(h nu – h nu_0)"
- ch 2 p14 §2.3.2 ¶21: printed "h v = h v_0 + (1/2) m_e v^2" · transcribed "h nu = h nu_0 + (1/2) m_e v^2"
- ch 2 p17 §2.3.3 ¶10: printed "Brackett" · transcribed "Bracket"
- ch 2 p18 §2.4 ¶5: printed "n = ΔE / h = (E_2 − E_1) / h" · transcribed "nu = ΔE / h = (E_2 − E_1) / h"
- ch 2 p20 §2.4.1 ¶3: printed "3 × 10^8 m s^-1" · transcribed "3 × 10^8 m s^-s"
- ch 2 p24 §2.5.2 ¶11: printed "∆x∆p = h / 4pi or ∆x m∆v = h/4pi" · transcribed "∆x∆p = h / 4pi or ∆x m∆v = h / 4pi ∆v = h / (4 pi ∆x m)"
- ch 2 p29 §2.6.1 ¶21: printed "–2, –1, 0, +1, +2" · transcribed "–2, –1, 0, +1+, +2"
- ch 2 p34 §2.6.4 ¶6: printed "n, l and m_l," · transcribed "n, l and m_l"
- ch 3 p8 §3.5 ¶2: printed "hydrogen (1s^1)" · transcribed "hydrogen (ls^1)"
- ch 3 p8 §3.5 ¶2: printed "helium (1s^2)" · transcribed "helium (ls^2)"
- ch 3 p16 §3.7.1 ¶20: printed "(Delta_(eg)H)" · transcribed "(Delta_egH)"
- ch 4 p9 §4.3.3 ¶1: printed "Delta_a H^⊖" · transcribed "Delta_a H^V"
- ch 4 p9 §4.3.3 ¶2: printed "Delta_a H^⊖ = 498" · transcribed "Delta_a H^V = 498"
- ch 4 p9 §4.3.3 ¶2: printed "Delta_a H^⊖ = 946.0" · transcribed "Delta_a H^V = 946.0"
- ch 4 p9 §4.3.3 ¶3: printed "Delta_a H^⊖ = 431.0" · transcribed "Delta_a H^V = 431.0"
- ch 4 p29 §4.7.4 ¶1: printed "pi*2p_x pi2p_y" · transcribed "pi*2p_x pi*2p_y"
- ch 4 p31 §4.8 ¶10: printed "molecule it is clear that there are four electrons" · transcribed "molecule it is clear that there are four electrons present in bonding"
- ch 4 p31 §4.8 ¶11: printed "(sigma*1s)^2 (sigma * 2s)^2 (sigma*2s)^2 (pi2p_x^2 = pi2p_y^2)" · transcribed "(sigma*1s)^2 (sigma * 2s)^2 (pi2p_x^2 = pi2p_y^2)"
- ch 4 p31 §4.8 ¶13: printed "(pi*2p_x^1 ≡ pi *2p_y^1)" · transcribed "(pi*2p_x^1 ≡ pi *2_y^1)"
- ch 5 p7 §5.2.1 ¶23: printed "log V_f / V_i" · transcribed "log V_f / V_s"
- ch 5 p8 §5.2.2 ¶8: printed "equation (5.7)" · transcribed "equation 5.7"
- ch 5 p8 §5.2.2 ¶12: printed "equation (5.8)" · transcribed "equation 5.8"
- ch 5 p8 §5.2.2 ¶15: printed "equation (5.9)" · transcribed "equation 5.9"
- ch 5 p8 §5.2.2 ¶15: printed "equation (5.8)" · transcribed "equation 5.8"
- ch 5 p8 §5.2.2 ¶16: printed "equation (5.10)" · transcribed "equation 5.10"
- ch 5 p12 §5.4 ¶2: printed "H_m^i (CO_2, g) + 2H_m^i (H_2O, l)" · transcribed "H_m (CO_2, g) + 2H_m (H_2O, l)"
- ch 5 p13 §5.4 ¶15: printed "H_2O(l) --vaporisation--> H_2O(g)" · transcribed "H_2O(1) --vaporisation--> H_2O(g)"
- ch 5 p13 §5.4 ¶15: printed "No. of moles in 18 g H_2O(l) is" · transcribed "No. of moles in 18 g H_2O(l) is ="
- ch 5 p15 §5.4 ¶21: printed "CaO(s) + CO_2(g) -> CaCO_3(s)" · transcribed "CaO(s) + CO_2(g) -> CaCo_3(s)"
- ch 5 p15 §5.4 ¶26: printed "Delta_r H^0 = Delta_f H^0 [CaO(s)]+ Delta_f H^0 [CO_2(g)] – Delta_f H^0 [CaCO_3(s)]" · transcribed "Delta_r H^0 = Delta_f H^0 = [CaO(s)]+ Delta_f H^0 [CO_2(g)] – Delta_f H^0 = [CaCO_3(s)]"
- ch 5 p17 §5.5 ¶8: printed "benzene is given by" · transcribed "benezene is given by"
- ch 5 p18 §5.5 ¶8: printed "6CO_2(g) + 3H_2O(l) -> C_6H_6(l) + 15/2 O_2(g)" · transcribed "6CO_2(g) + 3H_2O(l) -> C_6H_6(l) + 15/2 O_2"
- ch 5 p22 §5.6 ¶5: printed "1/2 N_2(g) + 1/2 H_2(g) = NH_3(g)" · transcribed "1/2 N_2(g) + 3/2 H_2(g) = NH_3(g)"
- ch 5 p28 §5.7 ¶21: printed "K_p = (p_(NO_2))^2 / p_(N_2O_4) = (1.5) / ((1.5)^2 (0.5))" · transcribed "K_p = (p_(NO_2))^2 / p_(N_2O_4) = 1.5 / ((1.5)^2 (0.5))"
- ch 6 p7 §6.2 ¶12: printed "N_2(g) + 3H_2(g) <-> 2NH_3(g)" · transcribed "N_2(g) + 3H_2(g) <-> 2NH_3(g) 2NH_3(g) <-> N_2(g) + 3H_2(g)"
- ch 6 p8 §6.3 ¶2: printed "(6.1)" · transcribed "(6.1) (6.1)"
- ch 6 p9 §6.3 ¶16: printed "K_c = [H_2] [I_2]" · transcribed "K'_c = [H_2] [I_2]"
- ch 6 p9 §6.3 ¶18: printed "K_c = [HI]" · transcribed "K''_c = [HI]"
- ch 6 p11 §6.4.1 ¶7: printed "[H_2(g)]^3 (RT)^3" · transcribed "[H_2(g)]^3 (RT)^3)"
- ch 6 p16 §6.6.3 ¶8: printed "equilbrium" · transcribed "equilibrium"
- ch 6 p17 §6.7 ¶10: printed "K < 1, that is" · transcribed "< 1, that is"
- ch 6 p20 §6.8.4 ¶8: printed "freezing mixture" · transcribed "freesing mixture"
- ch 6 p24 §6.10.2 ¶3: printed "NH_4^+ acts as a Bronsted acid while OH– acted as a Brönsted base" · transcribed "NH_4^+ acts as a Bronsted acid while OH^– acted as a Brönsted base"
- ch 6 p31 §6.11.4 ¶13: printed "K_b = 10^-4.75 = 1.77 × 10^-5 M" · transcribed "K_b = antilog (-pK_b) i.e. K_b = 10^-4.75 = 1.77 × 10^-5 M"
- ch 6 p33 §6.11.6 ¶3: printed "[HX-]" · transcribed "[HX^-]"
- ch 6 p36 §6.12.1 ¶8: printed "pH of the buffer solution will be same as pK_a for the" · transcribed "pH of the buffer solution will be same as pK_a for the base."
- ch 6 p36 §6.12.1 ¶8: printed "pK_a value for ammonia is 9.25" · transcribed "pK_a value for ammonia is s9.25"
- ch 6 p38 §6.13.1 ¶8: printed "S^(x + y) = K_sp / x^x . y^y" · transcribed "S^(x + y) S^(x + y) = K_sp / x^x . y^y"
- ch 6 p38 §6.13.1 ¶11: printed "A_2X_3 <-> 2A^3+ + 3X^2-" · transcribed "A_2X_3 -> 2A^3+ + 3X^2-"
- ch 6 p39 §6.13.2 ¶5: printed "{[HX] + [H^+]}" · transcribed "{[HX] + [H^-]}"

## rows the verifier could not find on their page

- ch 6 p16 §6.6.3 ¶1 "In case of a problem in which"
- ch 6 p34 §6.11.9 ¶1 "Salts formed by the reactions between acids"

## running text the page prints that no row carries

- ch 1 p15: "% of copper % of carbon % of oxygen"
- ch 1 p15: "Natural Sample 51.35 9.74 38.91"
- ch 1 p15: "Synthetic Sample 51.35 9.74 38.91"
- ch 1 p17: "Isotope Relative Abundance Atomic Mass table"
- ch 1 p24: "SUMMARY"
- ch 1 p24: "Chemistry, as we understand it today is not a very old discipline"
- ch 2 p1: "The rich diversity of chemical behaviour of different elements can be traced to the differences in the internal structure of atoms of these elements."
- ch 2 p5: "Positive sphere"
- ch 2 p5: "Electron"
- ch 2 p13: "Light Detector Metal surface"
- ch 2 p19: "Niels Bohr (1885-1962) biography box"
- ch 2 p19: "What does the negative electronic energy (E_n) for hydrogen atom mean?"
- ch 2 p24: "Reasons for the Failure of the Bohr Model"
- ch 2 p25: "Hydrogen Atom and the Schrödinger Equation"
- ch 2 p26: "Important Features of the Quantum Mechanical Model of Atom (heading is not omitted, but confirm box text is covered by items)"
- ch 2 p28: "Value of l 0 1 2 3 4 5"
- ch 3 p9: "Atomic number Symbol Electronic configuration table"
- ch 3 p19: "Group 1 2 13 14 15 16 17 18 (table of valence electrons)"
- ch 3 p20: "Metallic radius M/pm"
- ch 3 p20: "Ionic radius M+/pm"
- ch 3 p22: "In this Unit, you have studied the development of the Periodic Law"
- ch 3 p22: "Periodic trends are observed in atomic sizes, ionization enthalpies, electron gain enthalpies"
- ch 4 p1: "Scientists are constantly discovering new compounds"
- ch 4 p2: "Lewis symbols diagram: Li Be B C N O F Ne"
- ch 4 p3: "H atoms attain a duplet of electrons and O, the octet"
- ch 4 p3: "Each of the four Cl atoms along with the C atom attains octet of electrons"
- ch 4 p3: "or Cl – Cl"
- ch 4 p3: "Covalent bond between two Cl atoms"
- ch 4 p3: "Double bonds in CO_2 molecule"
- ch 4 p4: "* Each H atom attains the configuration of helium (a duplet of electrons)"
- ch 4 p5: "Formal charge (F.C.) on an atom in a Lewis structure ="
- ch 4 p10: "The concept of resonance was introduced to deal with the type of difficulty"
- ch 4 p12: "H2O molecule (a) Bond dipole (b) Resultant dipole moment"
- ch 4 p12: "BeF2 bond dipoles diagram"
- ch 4 p12: "BF3 molecule; representation of (a) bond dipoles and (b) total dipole moment"
- ch 4 p30: "Nature of the bond"
- ch 4 p30: "Bond-length"
- ch 4 p30: "Magnetic nature"
- ch 4 p30: "Bond order"
- ch 5 p10: "(a) ΔU Measurements"
- ch 5 p14: "(c) Standard Enthalpy of Formation"
- ch 5 p17: "A --Delta_r H--> B / Delta H_1 down / C --Delta_r H_2--> D / up Delta_r H_3 diagram"
- ch 5 p18: "(c) Bond Enthalpy (symbol: Delta_bond H^0)"
- ch 5 p20: "Ionization Energy and Electron Affinity"
- ch 6 p16: "The total pressure at equilbrium was found to be 9.15 bar. Calculate K_c, K_p and partial pressure at equilibrium."
- ch 6 p19: "Effect of Concentration – An experiment (heading is not running text, skip)"
- ch 6 p24: "Table with Species, Conjugate acid, Conjugate base rows"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 1 p3 §1 ¶15: printed "17th century as account" · transcribed "17th century as account"
- ch 1 p3 §1 ¶17: printed "Vaiseshika Sutras" · transcribed "Vaiseshika Sutras"
- ch 1 p11 §1.4.1 ¶1: printed "0.00000000000000000000000166 g mass" · transcribed "0.00000000000000000000000166 g mass"
- ch 1 p14 §1.4.3 ¶9: printed "first unit factor is taken and it is cubed." · transcribed "first unit factor is taken and it is cubed."
- ch 1 p14 §1.4.3 ¶9: printed "(1 m / 100 cm)^3 => 1 m^3 / 10^6 cm^3 = (1)^3 = 1" · transcribed "(1 m / 100 cm)^3 => 1 m^3 / 10^6 cm^3 = (1)^3 = 1"
- ch 1 p16 §1.7.1 ¶1: printed "represented as ^12C" · transcribed "represented as ^12C"
- ch 1 p18 §1.8 ¶4: printed "12g / mol ^12C" · transcribed "12 g / mol ^12C"
- ch 1 p19 §1.9.1 ¶8: printed "Moles of hydrogen = 4.07 g / 1.008 g = 4.04" · transcribed "Moles of hydrogen = 4.07 g / 1.008 g = 4.04"
- ch 1 p19 §1.9.1 ¶8: printed "Moles of chlorine = 71.65 g / 35.453 g = 2.021" · transcribed "Moles of chlorine = 71.65 g / 35.453 g = 2.021"
- ch 1 p22 §1.10.2 ¶19: printed "N_2 (g) + 3H_2 (g) <-> 2 NH_3 (g)" · transcribed "N_2 (g) + 3H_2 (g) <-> 2 NH_3 (g)"
- ch 2 p4 §2.1.4 ¶6: printed "by alpha-particles" · transcribed "by alpha-particles"
- ch 2 p5 §2.2.1 ¶2: printed "Wilhalm Röentgen" · transcribed "Wilhalm Röentgen"
- ch 2 p5 §2.2.1 ¶2: printed "electro-magnetic" · transcribed "electro-magnetic"
- ch 2 p5 §2.2.1 ¶3: printed "radioactivity" · transcribed "radioactivity"
- ch 2 p5 §2.2.1 ¶3: printed "alpha-rays consists" · transcribed "alpha-rays consists"
- ch 2 p7 §2.2.4 ¶2: printed "(^2_1D, 0.015%)" · transcribed "(^2_1D, 0.015%)"
- ch 2 p7 §2.2.4 ¶2: printed "tritium (^3_1T)" · transcribed "tritium (^3_1T )"
- ch 2 p8 §2.2.5 ¶2: printed "undergoes acceleration" · transcribed "undergoes acceleration"
- ch 2 p10 §2.3.1 ¶7: printed "around 10^16 Hz a component" · transcribed "around 10^16Hz a component"
- ch 2 p11 §2.3.1 ¶20: printed "(nu) v_bar = c" · transcribed "(nu) v_bar = c"
- ch 2 p11 §2.3.2 ¶1: printed "electromagentic theory" · transcribed "electromagentic theory"
- ch 2 p12 §2.3.2 ¶10: printed "wavelengths emitted by hot body depend" · transcribed "wavelengths emitted by hot body depend"
- ch 2 p13 §2.3.2 ¶11: printed "oscilators of" · transcribed "oscilators of"
- ch 2 p13 §2.3.2 ¶18: printed "nu_0, the ejected" · transcribed "nu_0, the ejected"
- ch 2 p15 §2.3.2 ¶30: printed "½ m_e v^2 = h(nu − nu_0)" · transcribed "½ m_e v^2 = h(nu - nu_0)"
- ch 2 p18 §2.4 ¶5: printed "iii) The frequency" · transcribed "iii) The frequency"
- ch 2 p18 §2.4 ¶5: printed "nu = ΔE / h" · transcribed "nu = ΔE / h"
- ch 2 p19 §2.4 ¶11: printed "E_2 = -2.18×10^-18 J (1/2^2) = -0.545×10^-18 J" · transcribed "E_2 = −2.18×10^-18 J (1/2^2) = −0.545×10^-18 J"
- ch 2 p20 §2.4 ¶14: printed "He^+ Li^2+, Be^3+" · transcribed "He^+ Li^2+, Be^3+"
- ch 2 p21 §2.4.1 ¶7: printed "6.91×10^(14) Hz" · transcribed "6.91×10^14 Hz"
- ch 2 p22 §2.5.1 ¶4: printed "de Brogile equation" · transcribed "de Brogile equation"
- ch 2 p23 §2.5.2 ¶3: printed "we must use a meterstick" · transcribed "we must use a meterstick"
- ch 2 p23 §2.5.2 ¶6: printed "Delta v.Delta x" · transcribed "Delta v.Delta x"
- ch 2 p25 §2.6 ¶4: printed "H_hat Psi = E Psi where H_hat" · transcribed "H_hat Psi = E Psi where H_hat"
- ch 2 p25 §2.6 ¶4: printed "gives E and psi" · transcribed "gives E and psi"
- ch 2 p27 §2.6.1 ¶10: printed "that the value of l are derived from n" · transcribed "that the value of l are derived from n"
- ch 2 p31 §2.6.2 ¶10: printed "d_xy, d_yz, d_xz, d_(x^2-y^2) and d_(z^2)" · transcribed "d_xy, d_yz, d_xz, d_(x^2–y^2) and d_(z^2)"
- ch 3 p1 §3.1 ¶1: printed "114 elements" · transcribed "114 elements"
- ch 3 p1 §3.1 ¶1: printed "rationalize known" · transcribed "rationalize known"
- ch 3 p1 §3.1 ¶1: printed "At present 114 elements" · transcribed "At present 114 elements"
- ch 3 p11 §3.6.3 ¶1: printed "(n-1)d^(1-10)ns^(0-2)" · transcribed "(n-1)d^(1-10)ns^(0-2)"
- ch 3 p11 §3.6.3 ¶1: printed "4d^10 5s^0." · transcribed "4d^10 5s^0"
- ch 3 p16 §3.7.1 ¶18: printed "(Delta_i H)" · transcribed "(Delta_i H)"
- ch 3 p17 §3.7.1 ¶26: printed "A_e" · transcribed "A_e"
- ch 3 p17 §3.7.1 ¶26: printed "Delta_eg H = -A_e - 5/2 RT" · transcribed "Delta_egH = –A_e – 5/2 RT"
- ch 3 p20 §3.7.2 ¶9: printed "has only four valence orbitals" · transcribed "has only four valence orbitals"
- ch 3 p20 §3.7.2 ¶9: printed "first member of p-block elements displays" · transcribed "first member of p-block elements displays"
- ch 3 p20 §3.7.2 ¶9: printed "p_pi – p_pi multiple bonds" · transcribed "p_pi – p_pi multiple bonds"
- ch 3 p20 §3.7.2 ¶9: printed "boron can only form [BF_4]^-" · transcribed "boron can only form [BF_4]^-"
- ch 3 p20 §3.7.2 ¶9: printed "aluminium [AlF_6]^3- forms" · transcribed "aluminium [AlF_6]^3- forms"
- ch 4 p5 §4.1.4 ¶2: printed "= [total number of valence electrons in the free atom] — [total number of non bonding (lone pair) electrons] — (1/2) [total number of bonding (shared) electrons]" · transcribed "= [total number of valence electrons in the free atom] — [total number of non bonding (lone pair) electrons] — (1/2) [total number of bonding (shared) electrons]"
- ch 4 p7 §4.2 ¶5: printed "Electron affinity, is the negative" · transcribed "Electron affinity, is the negative"
- ch 4 p10 §4.3.5 ¶2: printed "are same (128 pm)" · transcribed "are same (128 pm)"
- ch 4 p10 §4.3.5 ¶3: printed "The concept of resonance was introduced" · transcribed "The concept of resonance was introduced"
- ch 4 p11 §4.3.5 ¶9: printed "cannonical froms I and II" · transcribed "cannonical froms I and II"
- ch 4 p13 §4.3.6 ¶15: printed "ns^o" · transcribed "ns^o"
- ch 4 p23 §4.6.1 ¶8: printed "2S^2 2p_x^1 2p_y^1 2p_z^1" · transcribed "2S^2 2p_x^1 2p_y^1 2p_z^1"
- ch 4 p31 §4.8 ¶13: printed "(sigma2p_s)^2" · transcribed "(sigma2p_s)^2"
- ch 5 p4 §5.1.4 ¶10: printed "expresses that" · transcribed "expresses that"
- ch 5 p9 §5.2.2 ¶18: printed "Delta H = Delta U + Delta ngRT" · transcribed "Delta H = Delta U + Delta ngRT"
- ch 5 p12 §5.4 ¶2: printed "a_i H_(Products) − sum(i) b_i H_(reactants)" · transcribed "a_i H_(Products) − sum(i) b_i H_(reactants)"
- ch 5 p13 §5.4 ¶15: printed "Delta_vap H^V - Delta n_g RT = 44.01 kJ" · transcribed "Delta_vap H^V - Delta n_g RT = 44.01 kJ"
- ch 5 p13 §5.4 ¶17: printed "1 mol H_2O (l, 100°C) -> 1 mol (l, 0°C)" · transcribed "1 mol H_2O (l, 100°C) -> 1 mol (l, 0°C)"
- ch 5 p13 §5.4 ¶17: printed "1 mol H_2O( S, 0°C)" · transcribed "1 mol H_2O( S, 0°C)"
- ch 5 p14 §5.4 ¶19: printed "Delta n_g" · transcribed "Delta n_g"
- ch 5 p15 §5.4 ¶21: printed "H_2(g) + Br_2(l) -> 2HBr(g)" · transcribed "H_2(g) + Br_2(l) -> 2HBr(g)"
- ch 5 p15 §5.4 ¶28: printed "C_2H_5OH(l) + 3O_2(g) -> 2CO_2(g) + 3H_2O(l)" · transcribed "C_2H_5OH(l) + 3O_2(g) -> 2CO_2(g) + 3H_2O(l)"
- ch 5 p15 §5.4 ¶28: printed "Delta_r H^0 = – 1367 kJ mol^-1" · transcribed "Delta_r H^0 = – 1367 kJ mol^-1"
- ch 5 p16 §5.4 ¶33: printed "Delta_f H_1^0 = 3(–285.83 kJ mol^-1) – 1(– 824.2 kJ mol^-1)" · transcribed "Delta_f H_1^0 = 3(–285.83 kJ mol^-1) – 1(– 824.2 kJ mol^-1)"
- ch 5 p17 §5.5 ¶5: printed "C_6H_12O_6(g)" · transcribed "C_6H_12O_6(g)"
- ch 5 p19 §5.5 ¶23: printed "Delta_f H^0 = sum Delta_f H^0_(bonds of products) – sum Delta_f H^0_(bonds of reactants)" · transcribed "Delta_f H^0 = sum Delta_f H^0_(bonds of products) – sum Delta_f H^0_(bonds of reactants)"
- ch 5 p20 §5.5 ¶30: printed "Cl^-(g) electron gained" · transcribed "Cl^-(g) electron gained"
- ch 5 p20 §5.5 ¶30: printed "e^-1(g) -> Cl^-(g)" · transcribed "e^-1(g) -> Cl^-(g)"
- ch 5 p21 §5.5 ¶37: printed "selective values" · transcribed "selective values"
- ch 5 p22 §5.5 ¶42: printed "HCl.25 aq. + 15 aq. -> HCl.40 aq." · transcribed "HCl.25 aq. + 15 aq. -> HCl.40 aq."
- ch 5 p24 §5.6 ¶24: printed "crystallizes into a solid" · transcribed "crystallizes into a solid."
- ch 5 p24 §5.6 ¶30: printed "the contituent particles" · transcribed "the contituent particles"
- ch 5 p25 §5.6 ¶33: printed "–1648 × 10^3 J mol^-1)" · transcribed "–1648 × 10^3 J mol^-1)"
- ch 5 p27 §5.7 ¶6: printed "A + B <-> C + D" · transcribed "A + B <-> C + D"
- ch 6 p10 §6.3 ¶22: printed "N_2=3.0 × 10^-3 M" · transcribed "N_2 = 3.0 × 10^-3 M"
- ch 6 p14 §6.5 ¶14: printed "p_CO_2 = 0.48 – x = 0.48 – 0.33 = 0.15 bar" · transcribed "p_CO_2 = 0.48 – x = 0.48 – 0.33 = 0.15 bar"
- ch 6 p15 §6.6.1 ¶13: printed "generarlisations" · transcribed "generarlisations"
- ch 6 p19 §6.8.1 ¶9: printed "[Fe(SCN)]^2+(aq)" · transcribed "[Fe(SCN)]^2+(aq)"
- ch 6 p21 §6.8.4 ¶10: printed "freesing" · transcribed "freesing"
- ch 6 p21 §6.9 ¶1: printed "[Fe(SCN)]^2+(aq)" · transcribed "[Fe(SCN)]^2+(aq)"
- ch 6 p23 §6.10.2 ¶2: printed "NH_3(aq) + H_2O(l) <-> NH_4^+(aq) + OH^-(aq)" · transcribed "NH_3(aq) + H_2O(l) <-> NH_4^+(aq) + OH^-(aq)"
- ch 6 p25 §6.10.3 ¶3: printed "Co^3+" · transcribed "Co^3+"
- ch 6 p25 §6.10.3 ¶9: printed "fluoride ion." · transcribed "fluoride ion."
- ch 6 p27 §6.11.2 ¶12: printed "1.0 × 10^-8 M solution" · transcribed "1.0 × 10^-8 M solution"
- ch 6 p29 §6.11.3 ¶20: printed "is the principle reaction" · transcribed "is the principle reaction"
- ch 6 p30 §6.11.3 ¶30: printed "equilibrium concentartion (M)" · transcribed "equilibrium concentartion (M)"
- ch 6 p30 §6.11.3 ¶30: printed "0.08 –x ≃ 0.08" · transcribed "0.08 –x ≅ 0.08"
- ch 6 p32 §6.11.5 ¶8: printed "={[ OH^-][H^+]}{[BH^+] / [B][H^+]} = K_w / K_a" · transcribed "={[ OH^-][H^+]}{[BH^+] / [B][H^+]} = K_w / K_a"
- ch 6 p33 §6.11.6 ¶5: printed "H_2A being a strong acid" · transcribed "H_2A being a strong acid"
- ch 6 p34 §6.11.8 ¶9: printed "0.56 × 10^-9 Hence, pH = 9.24" · transcribed "0.56 × 10^-9 Hence, pH = 9.24"
- ch 6 p35 §6.11.9 ¶10: printed "H_2O (1)" · transcribed "H_2O (1)"
- ch 6 p35 §6.11.9 ¶11: printed "This results in increased of H^+" · transcribed "This results in increased of H^+"
- ch 6 p38 §6.13.1 ¶12: printed "6 × 0^-17" · transcribed "6 × 0^(-17)"
- ch 6 p39 §6.13.2 ¶3: printed "but the total" · transcribed "but the total"
- ch 6 p39 §6.13.2 ¶4: printed "K_a = [H^+(aq)][X^-(aq)] / [HX(aq)]" · transcribed "K_a = [H^+(aq)][X^-(aq)] / [HX(aq)]"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 1 p19 §1.9 ¶3: transcribed "46.068 g) g = 46.068 g" (printed "46.068 g")
- ch 1 p23 §1.10.2 ¶36: transcribed "V_2 = 200 L" (printed "V_2 = 200 mL")
- ch 5 p8 §5.2.2 ¶13: transcribed "equation 5.9" (printed "equation (5.9)")
- ch 6 p12 §6.4.1 ¶19: transcribed "[H_2.] = x" (printed "[H_2] = x")
- ch 6 p24 §6.10.2 ¶3: transcribed "acts as a Bronsted acid while OH– acted as a Brönsted base" (printed "acts as a Bronsted acid")
- ch 6 p34 §6.11.8 ¶6: transcribed "7.51 × 10^-12) = 11.12" (printed "7.51 × 10^-12) = 9.24")
- ch 6 p39 §6.13.2 ¶3: transcribed "increase in turn" (printed "increase")

## set aside by code: a heading or a caption listed as omitted text

- ch 1 p4: "1.2.1 States of Matter"
- ch 1 p8: "Table 1.2 Definitions of SI Base Units"
- ch 1 p12: "1.4.2 Significant Figures"
- ch 1 p13: "Table 1.4 Data to Illustrate Precision and Accuracy"
- ch 1 p14: "1.5.1 Law of Conservation of Mass"
- ch 1 p18: "1.9 PERCENTAGE COMPOSITION"
- ch 1 p18: "Fig. 1.11 One mole of various substances"
- ch 1 p20: "1.10 STOICHIOMETRY AND STOICHIOMETRIC CALCULATIONS"
- ch 1 p20: "1.10.1 Limiting Reagent"
- ch 2 p3: "2.1.2 Charge to Mass Ratio of Electron"
- ch 2 p3: "2.1.3 Charge on the Electron"
- ch 2 p5: "Fig.2.4 Thomson model of atom"
- ch 2 p7: "2.2.4 Isobars and Isotopes"
- ch 2 p11: "2.3.2 Particle Nature of Electromagnetic Radiation: Planck's Quantum Theory"
- ch 2 p13: "Fig.2.9 Equipment for studying the photoelectric effect"
- ch 2 p14: "Table 2.2 Values of Work Function"
- ch 2 p20: "2.4.1 Explanation of Line Spectrum of Hydrogen"
- ch 2 p22: "2.5.2 Heisenberg's Uncertainty Principle"
- ch 2 p25: "2.6 QUANTUM MECHANICAL MODEL OF ATOM"
- ch 2 p27: "Table 2.4 Subshell Notations"
- ch 2 p29: "2.6.2 Shapes of Atomic Orbitals"
- ch 2 p31: "2.6.3 Energies of Orbitals"
- ch 2 p32: "Fig. 2.16 Energy level diagrams for the few electronic shells"
- ch 2 p33: "2.6.4 Filling of Orbitals in Atom"
- ch 2 p35: "2.6.5 Electronic Configuration of Atoms"
- ch 2 p36: "2.6.6 Stability of Completely Filled and Half Filled Subshells"
- ch 3 p3: "Table 3.3 Mendeleev's Predictions for the Elements Eka-aluminium"
- ch 3 p9: "3.6 ELECTRONIC CONFIGURATIONS AND TYPES OF ELEMENTS: S-, P-, D-, F- BLOCKS"
- ch 3 p12: "3.7.1 Trends in Physical Properties"
- ch 3 p20: "Table 3.9 Periodic Trends in Valence of Elements as shown by the Formulas of Their Compounds"
- ch 4 p4: "Table 4.1 The Lewis Representation of Some Molecules"
- ch 4 p7: "4.2 IONIC OR ELECTROVALENT BOND"
- ch 4 p14: "Fig. 4.6 The shapes of molecules in which central atom has no lone pair"
- ch 4 p21: "4.5.4 Types of Overlapping and Nature of Covalent Bonds"
- ch 4 p25: "4.6.3 Hybridisation of Elements involving d Orbitals"
- ch 4 p27: "Fig.4.19 Formation of bonding"
- ch 4 p28: "4.7.2 Conditions for the Combination of Atomic Orbitals"
- ch 4 p28: "4.7.3 Types of Molecular Orbitals"
- ch 4 p28: "4.7.4 Energy Level Diagram for Molecular Orbitals"
- ch 4 p30: "4.7.5 Electronic Configuration and Molecular Behaviour"
- ch 4 p30: "4.8 BONDING IN SOME HOMONUCLEAR DIATOMIC MOLECULES"
- ch 5 p4: "Fig. 5.4 A system which allows heat transfer through its boundary."
- ch 5 p5: "5.2.1 Work"
- ch 5 p5: "5.2 APPLICATIONS"
- ch 5 p8: "5.2.2 Enthalpy, H"
- ch 5 p11: "5.4 ENTHALPY CHANGE, Delta_r H OF A REACTION – REACTION ENTHALPY"
- ch 5 p12: "Table 5.1 Standard Enthalpy Changes of Fusion and Vaporisation"
- ch 6 p4: "Table 6.1 Some Features of Physical Equilibria"
- ch 6 p7: "Fig. 6.4 Depiction of equilibrium for the reaction"
- ch 6 p7: "Fig. 6.5 Chemical equilibrium in the reaction"
- ch 6 p10: "6.4.1 Equilibrium Constant in Gaseous Systems"
- ch 6 p11: "Table 6.5 Equilibrium Constants, Kp for a Few Selected Reactions"
- ch 6 p15: "6.6.2 Predicting the Direction of the Reaction"
- ch 6 p18: "6.8.1 Effect of Concentration Change"
- ch 6 p25: "6.11 IONIZATION OF ACIDS AND BASES"
- ch 6 p28: "Table 6.6 The Ionization Constants of Some Selected Weak Acids"
- ch 6 p30: "Table 6.7 The Values of the Ionization Constant of Some Weak Bases at 298 K"
- ch 6 p31: "6.11.5 Relation between K_a and K_b"
- ch 6 p33: "6.11.7 Factors Affecting Acid Strength"
- ch 6 p33: "6.11.8 Common Ion Effect in the Ionization of Acids and Bases"
- ch 6 p34: "6.11.9 Hydrolysis of Salts and the pH of their Solutions"
- ch 6 p36: "6.12.1 Designing Buffer Solution"
- ch 6 p37: "6.13.1 Solubility Product Constant"
- ch 6 p39: "6.13.2 Common Ion Effect on Solubility of Ionic Salts"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 1454

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 224 | 224 | 210 | 12 | 0 | 2 | 2 | 92.9% |
| 2 | 256 | 256 | 245 | 11 | 0 | 0 | 1 | 95.3% |
| 3 | 84 | 84 | 82 | 2 | 0 | 0 | 3 | 94.0% |
| 4 | 240 | 240 | 233 | 7 | 0 | 0 | 2 | 96.3% |
| 5 | 269 | 269 | 256 | 12 | 0 | 1 | 4 | 94.1% |
| 6 | 381 | 381 | 361 | 15 | 2 | 3 | 5 | 93.7% |
clean for the book (PLAN D15 ✅): 1372 of 1454 paragraphs, 94.4%
not in the clean share, adjudicate before recording it: 158 page-level start flags, 1 numbered equations the print carries that the rows do not, 47 passages no row carries

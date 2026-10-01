# margai-pipeline ncert verify

- run: 2026-09-27 00:54 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 8 chapters of phy12-part1 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 da48dec6cfb42ab4ba47b18e88d42bc0cdd77d8ee0eb5bd9eeb49c5a5ef6d1b1

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 37 | 15 | 21 | 30 | 0 | 0 | 0 | 3 |
| 2 | 32 | 11 | 20 | 26 | 0 | 0 | 2 | 2 |
| 3 | 22 | 4 | 17 | 15 | 0 | 0 | 2 | 0 |
| 4 | 26 | 8 | 17 | 20 | 0 | 0 | 0 | 1 |
| 5 | 14 | 5 | 8 | 12 | 0 | 0 | 0 | 0 |
| 6 | 19 | 5 | 13 | 16 | 0 | 0 | 0 | 0 |
| 7 | 20 | 8 | 11 | 18 | 0 | 0 | 0 | 2 |
| 8 | 11 | 5 | 5 | 5 | 0 | 0 | 0 | 1 |

## where rows start against where the print starts paragraphs

- ch 1 p3: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.3 ¶4 "* There is a third category called"
- ch 1 p4: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.4 ¶2 "If the sizes of charged bodies are"
- ch 1 p6: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.4.3 ¶8 "At the macroscopic level, one deals with" · §1.4.3 ¶11 "It is, however, also important to know" · §1.4.3 ¶14 "Each molecule of water contains two hydrogen" · §1.5 ¶1 "Coulomb's law is a quantitative statement about"
- ch 1 p7: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.5 ¶6 "* A torsion balance is a sensitive" · §1.5 ¶7 "* Implicit in this is the assumption"
- ch 1 p8: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.5 ¶9 "epsilon_0 is called the permittivity of free" · §1.5 ¶11 "In the same way, the vector leading" · §1.5 ¶14 "Some remarks on Eq. (1.3) are relevant:" · §1.5 ¶15 "• Equation (1.3) is valid for any"
- ch 1 p9: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.5 ¶16 "· The force F_12 on charge q_1" · §1.5 ¶17 "· Coulomb's law [Eq. (1.3)] gives the" · §1.5 ¶19 "Solution (a) (i) The electric force between" · printed starts no row begins with: "(a) (i) The electric force between"
- ch 1 p10: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.5 ¶21 "The value for acceleration of the proton" · printed starts no row begins with: "(b) The electric force F exerted"
- ch 1 p11: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.6 ¶1 "The mutual electric force between two charges" · §1.6 ¶2 "Experimentally, it is verified that force on" · §1.6 ¶3 "To better understand the concept, consider a"
- ch 1 p12: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.6 ¶8 "Example 1.5 Consider three charges q_1, q_2," · §1.6 ¶9 "Solution In the given equilateral triangle ABC" · printed starts no row begins with: "In the same way, the force"
- ch 1 p13: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.6 ¶10 "It is clear also by symmetry that" · §1.6 ¶13 "The force of attraction or repulsion for" · §1.6 ¶14 "The total force F_2 on charge q"
- ch 1 p14: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.6 ¶15 "It is interesting to see that the" · §1.6 ¶16 "The result is not at all surprising." · §1.7 ¶3 "Equation (1.8) defines the SI unit of" · §1.7 ¶6 "* An alternate unit V/m will be"
- ch 1 p15: 5 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "every point in three-dimensional space." · "negative, the electric field vector, at"
- ch 1 p16: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.7.1 ¶2 "Electric field E_1 at r due to" · §1.7.1 ¶3 "In the same manner, electric field E_2" · §1.7.1 ¶4 "By the superposition principle, the electric field"
- ch 1 p17: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §1.7.2 ¶4 "Example 1.7 An electron falls through a" · §1.7.2 ¶5 "Solution In Fig. 1.10(a) the field is" · §1.7.2 ¶6 "Starting from rest, the time required by"
- ch 1 p20: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.8 ¶8 "* Solid angle is a measure of"
- ch 1 p21: 6 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "(i) Field lines start from positive" · "start or end at infinity." · "to be continuous curves without any" · "unique direction, which is absurd.)" · "This follows from the conservative nature"
- ch 1 p22: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.9 ¶7 "* It will not be proper to"
- ch 1 p23: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.10.1 ¶3 "where p_hat is the unit vector along"
- ch 1 p24: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.10.1 ¶4 "The total field at P is E" · §1.10.1 ¶6 "The directions of E_+q and E_-q are"
- ch 1 p25: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.10.2 ¶2 "Example 1.9 Two charges ±10 μC are" · §1.10.2 ¶3 "* Centre of a collection of positive"
- ch 1 p26: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.10.2 ¶6 "Clearly, the components of these two forces"
- ch 1 p27: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §1.11 ¶4 "Its direction is normal to the plane"
- ch 1 p28: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.12 ¶3 "Similar considerations apply for a line charge" · §1.12 ¶5 "* At the microscopic level, charge distribution"
- ch 1 p30: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §1.13 ¶5 "We state Gauss's law without proof: Electric" · printed starts no row begins with: "anywhere inside the surface."
- ch 1 p31: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.13 ¶15 "Example 1.10 The electric field components in" · §1.13 ¶16 "Solution (a) Since the electric field has" · printed starts no row begins with: "continuous charge distribution." · "facilitated by the choice of a"
- ch 1 p32: 7 rows start here, the print starts 11 paragraphs — rows the print does not start: §1.13 ¶19 "Solution" · printed starts no row begins with: "(c) What is the net outward" · "parallel. Therefore, the outward flux is" · "On the right face, E and" · "cylinder is zero." · "law which gives"
- ch 1 p34: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §1.14.1 ¶4 "Flux through the Gaussian surface = flux" · printed starts no row begins with: "The surface includes charge equal to" · "Vectorially, E at any point is"
- ch 1 p35: 6 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "shell. The Gaussian surface is again"
- ch 1 p36: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.14.3 ¶8 "* Compare this with a uniform mass" · printed starts no row begins with: "Textbook of Physics."
- ch 1 p37: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.14.3 ¶9 "The charge q enclosed by the Gaussian" · §1.14.3 ¶10 "Substituting for the charge density rho obtained"
- ch 2 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.1 ¶1 "In Chapters 5 and 7 (Class XI),"
- ch 2 p2: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §2.1 ¶4 "Two remarks may be made here. First," · §2.1 ¶8 "Thus, potential energy difference Delta U ="
- ch 2 p3: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §2.2 ¶3 "where V_P and V_R are the electrostatic"
- ch 2 p5: 7 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.3 ¶7 "Example 2.1 (a) Calculate the potential at" · §2.3 ¶8 "(b) Hence obtain the work done in" · §2.3 ¶9 "Solution (a) V = (1 / 4" · §2.3 ¶10 "No, work done will be path independent."
- ch 2 p6: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.4 ¶3 "Now, by geometry, r_1^2 = r^2 +" · §2.4 ¶5 "Similarly, r_2^2 ≅ r^2 (1 + 2a" · §2.4 ¶8 "Now, p cos theta = p.r_hat"
- ch 2 p7: 11 rows start here, the print starts 10 paragraphs — rows the print does not start: §2.5 ¶2 "where r_1P is the distance between q_1" · §2.5 ¶4 "where r_2P and r_3P are the distances" · printed starts no row begins with: "to P on the cone so"
- ch 2 p8: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.5 ¶7 "Example 2.2 Two charges 3 × 10^-8" · §2.5 ¶8 "Solution Let us take the origin O" · §2.5 ¶9 "Let P be the required point on"
- ch 2 p9: 12 rows start here, the print starts 20 paragraphs — rows the print does not start: §2.5 ¶16 "Solution" · §2.5 ¶17 "(a) As V ∝ 1/r , V_P" · printed starts no row begins with: "charge between the points Q and" · "positive charge from Q to P." · "a small negative charge from B" · "decrease in going from B to" · "(a) As , V > V" · "than V . Thus, V >" · "differences is positive." · "work done by the field is" · "done by the external agency. It" · "and hence the kinetic energy decreases"
- ch 2 p11: 8 rows start here, the print starts 9 paragraphs — rows the print does not start: §2.6.1 ¶3 "Thus, |E| delta l = V –" · printed starts no row begins with: "per unit displacement normal to the" · "work done on q ="
- ch 2 p13: 6 rows start here, the print starts 10 paragraphs — rows the print does not start: §2.7 ¶10 "Solution (a) Since the work done depends" · printed starts no row begins with: "(a) Since the work done depends" · "elsewhere: this is zero." · "(charge at B) × (electrostatic potential" · "B. This is given by (charge" · "This is given by (charge at"
- ch 2 p14: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.7 ¶15 "The work done depends only on the" · §2.7 ¶16 "(Students may try calculating same work/energy by" · §2.7 ¶17 "(b) The extra work necessary to bring"
- ch 2 p15: 4 rows start here, the print starts 7 paragraphs — rows the print does not start: §2.8.2 ¶2 "Example 2.5 (a) Determine the electrostatic potential" · §2.8.2 ¶3 "(b) How much work is required to" · printed starts no row begins with: "Potential energy of q at r" · "Work done on q against the" · "Work done on q against the" · "Work done in bringing q to" · "Potential energy of the system"
- ch 2 p16: 7 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.8.2 ¶4 "(c) Suppose that the same system of" · §2.8.2 ¶5 "Solution (a) U = (1 / 4" · §2.8.2 ¶6 "(b) W = U_2 − U_1 =" · §2.8.2 ¶7 "(c) The mutual interaction energy of the" · §2.8.3 ¶2 "As seen in the last chapter, in"
- ch 2 p17: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.8.3 ¶6 "We note that U'(theta) differs from U(theta)" · §2.8.3 ¶8 "Example 2.6 A molecule of a substance" · §2.8.3 ¶9 "Solution Here, dipole moment of each molecules" · printed starts no row begins with: "We note that U¢(q) differs from"
- ch 2 p18: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §2.9 ¶2 "1. Inside a conductor, electrostatic field is" · §2.9 ¶3 "2. At the surface of a charged" · §2.9 ¶4 "3. The interior of a conductor can" · §2.9 ¶5 "4. Electrostatic potential is constant throughout the"
- ch 2 p19: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.9 ¶7 "5. Electric field at the surface of" · §2.9 ¶10 "Including the fact that electric field is" · §2.9 ¶11 "6. Electrostatic shielding Consider a conductor with"
- ch 2 p20: 6 rows start here, the print starts 10 paragraphs — rows the print does not start: §2.9 ¶12 "The proofs of the results noted in" · §2.9 ¶13 "Example 2.7 (a) A comb run through" · §2.9 ¶17 "Solution (a) This is because the comb" · printed starts no row begins with: "cavity of any conductor is zero." · "charges placed in the cavity.)" · "(a) A comb run through one’s" · "a paper does not conduct electricity.)" · "aircraft are made slightly conducting. Why" · "ropes touching the ground during motion." · "(a) This is because the comb"
- ch 2 p21: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.9 ¶18 "(b) To enable them to conduct charge" · §2.9 ¶19 "(c) Reason similar to (b)." · §2.9 ¶20 "(d) Current passes only when there is"
- ch 2 p25: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §2.12 ¶5 "The direction of electric field is from"
- ch 2 p26: 6 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "The capacitance C, with dielectric between"
- ch 2 p27: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.13 ¶8 "Example 2.8 A slab of material of" · §2.13 ¶9 "Solution Let E_0 = V_0/d be the"
- ch 2 p28: 8 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.14.1 ¶4 "We compare Eq. (2.57) with Eq. (2.56)," · §2.14.1 ¶6 "Following the same steps as for the" · §2.14.2 ¶2 "The equivalent capacitor is one with charge" · §2.14.2 ¶3 "The effective capacitance C is, from Eq." · §2.14.2 ¶4 "The general formula for effective capacitance C"
- ch 2 p29: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.14.2 ¶6 "Solution (a) In the given network, C_1," · §2.15 ¶1 "A capacitor, as we have seen above," · printed starts no row begins with: "(a) In the given network, C"
- ch 2 p30: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "We can write the final result,"
- ch 2 p31: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.15 ¶6 "Note that Ad is the volume of" · §2.15 ¶7 "Though we derived Eq. (2.73) for the" · §2.15 ¶9 "Solution" · printed starts no row begins with: "(b) The capacitor is disconnected from"
- ch 2 p32: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §2.15 ¶12 "Thus in going from (a) to (b)," · §2.15 ¶13 "There is a transient period before the"
- ch 3 p4: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.4 ¶3 "Next, imagine dividing the slab into two" · §3.4 ¶4 "For a given voltage V across the"
- ch 3 p6: 7 rows start here, the print starts 0 paragraphs — rows the print does not start: §3.5 ¶3 "This last result is surprising. It tells" · §3.5 ¶4 "Because of the drift, there will be" · §3.5 ¶5 "Substituting the value of |v_d| from Eq." · §3.5 ¶6 "By definition I is related to the" · §3.5 ¶7 "Hence, from Eqs.(3.19) and (3.20), |j| =" · §3.5 ¶8 "The vector j is parallel to E" · §3.5 ¶9 "Comparison with Eq. (3.13) shows that Eq."
- ch 3 p7: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §3.5 ¶11 "Solution" · §3.5 ¶15 "* See Eq. (12.23) of Chapter 12" · printed starts no row begins with: "mass M is obtained from [<(1/2)"
- ch 3 p8: 15 rows start here, the print starts 17 paragraphs — rows the print does not start: §3.5 ¶16 "Example 3.2" · §3.5 ¶22 "Solution" · §3.5.1 ¶1 "As we have seen, conductivity arises from" · §3.5.1 ¶2 "An important quantity is the mobility mu" · §3.5.1 ¶3 "The SI unit of mobility is m^2/Vs" · printed starts no row begins with: "is current established almost the instant" · "small, how can we still obtain" · "in the same direction?" · "it does take a little while" · "therefore, electrons acquire only a drift" · "random velocities of electrons." · "presence of electric field, the paths"
- ch 3 p11: 8 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.8 ¶5 "Unlike metals, the resistivities of semiconductors decrease" · §3.8 ¶6 "We can qualitatively understand the temperature dependence" · §3.8 ¶7 "In a metal, n is not dependent" · §3.8 ¶8 "For insulators and semiconductors, however, n increases" · §3.8 ¶11 "Using the relation R_2 = R_1 [1" · §3.8 ¶12 "Thus, the steady temperature of the heating"
- ch 3 p12: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §3.8 ¶13 "Example 3.4 The resistance of the platinum" · §3.8 ¶14 "Solution R_0 = 5 Ω, R_100 =" · printed starts no row begins with: "DU = Final potential energy –"
- ch 3 p13: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §3.9 ¶6 "Using Ohm's law V = IR, we"
- ch 3 p14: 6 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "V = Potential difference between P" · "negative electrodes in an open circuit,"
- ch 3 p15: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §3.11 ¶1 "Like resistors, cells can be combined together"
- ch 3 p16: 12 rows start here, the print starts 8 paragraphs — rows the print does not start: §3.11 ¶7 "The rule for series combination clearly can" · §3.11 ¶14 "Combining the last three equations I =" · §3.11 ¶15 "Hence, V is given by, V =" · §3.11 ¶17 "The last two equations should be the" · §3.11 ¶18 "We can put these equations in a" · printed starts no row begins with: "their individual emf’s, and"
- ch 3 p17: 6 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "the junction is equal to the"
- ch 3 p18: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §3.12 ¶5 "This applies equally well if instead of" · §3.12 ¶6 "The proof of this rule follows from" · §3.12 ¶7 "(b) Loop rule: The algebraic sum of" · §3.12 ¶8 "This rule is also obvious, since electric" · §3.12 ¶9 "Example 3.5 A battery of 10 V"
- ch 3 p19: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.12 ¶11 "The paths AA', AD and AB are" · §3.12 ¶12 "Next take a closed loop, say, ABCC'EA," · §3.12 ¶13 "It should be noted that because of" · printed starts no row begins with: "–IR – (1/2)IR – IR +"
- ch 3 p20: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.13 ¶1 "As an application of Kirchhoff's rules consider" · §3.13 ¶2 "For simplicity, we assume that the cell" · printed starts no row begins with: "AB : A, CA : A,"
- ch 3 p21: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.13 ¶6 "Example 3.7 The four arms of a"
- ch 4 p2: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.1 ¶3 "In this chapter, we will see how" · §4.1 ¶4 "In this and subsequent Chapter on magnetism," · §4.2.1 ¶2 "* A dot appears like the tip"
- ch 4 p3: 7 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "magnetic field). Force on a negative" · "and magnetic field. The vector product"
- ch 4 p4: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.2.2 ¶7 "Dimensionally, we have [B] = [F/qv] and"
- ch 4 p6: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.3 ¶2 "We shall consider motion of a charged"
- ch 4 p7: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.3 ¶7 "Example 4.3 What is the radius of" · §4.3 ¶8 "Solution Using Eq. (4.5) we find r" · §4.4 ¶2 "* The sense of dl × r"
- ch 4 p8: 10 rows start here, the print starts 10 paragraphs — rows the print does not start: §4.4 ¶3 "The magnitude of this field is, |dB|" · §4.4 ¶4 "We call mu_0 the permeability of free" · §4.4 ¶12 "Example 4.4 An element Delta l =" · printed starts no row begins with: "field is linear in its source:" · "charge. The magnetic field is produced" · "plane containing the displacement vector rand"
- ch 4 p9: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.4 ¶13 "Solution |dB| = (mu_0 / 4 pi)"
- ch 4 p11: 7 rows start here, the print starts 8 paragraphs — rows the print does not start: §4.5 ¶11 "Solution (a) dl and r for each" · §4.6 ¶1 "There is an alternative and appealing way" · printed starts no row begins with: "(a) dl and r for each" · "Therefore, dl × r = 0." · "paper going into it."
- ch 4 p12: 5 rows start here, the print starts 6 paragraphs — rows the print does not start: §4.6 ¶6 "(i) It implies that the field at" · printed starts no row begins with: "(i) B is tangential to the" · "(ii) B is normal to the"
- ch 4 p13: 6 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.6 ¶11 "Example 4.7 Figure 4.13 shows a long" · §4.6 ¶12 "* Note that there are two distinct" · printed starts no row begins with: "Whenever there is symmetry, the solutions" · "justification to Oersted’s experiments." · "proportional to the distance from the" · "field due to a long wire." · "Grasp the wire in your right"
- ch 4 p14: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.6 ¶13 "Solution (a) Consider the case r >" · §4.6 ¶14 "Now the current enclosed I_e is not" · §4.6 ¶15 "Figure (4.14) shows a plot of the" · §4.6 ¶16 "This example possesses the required symmetry so"
- ch 4 p16: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.7 ¶6 "Example 4.8 A solenoid of length 0.5" · §4.7 ¶7 "Solution The number of turns per unit"
- ch 4 p17: 10 rows start here, the print starts 8 paragraphs — rows the print does not start: §4.8 ¶7 "Parallel currents attract, and antiparallel currents repel." · §4.8 ¶11 "* It turns out that when we"
- ch 4 p18: 11 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.8 ¶13 "This definition of the ampere was adopted" · §4.8 ¶16 "Example 4.9 The horizontal component of the" · §4.8 ¶17 "Solution F = Il × B F" · §4.8 ¶18 "(a) When the current is flowing from" · §4.8 ¶19 "This is larger than the value 2×10^-7" · §4.8 ¶20 "The direction of the force is downwards." · §4.8 ¶21 "(b) When the current is flowing from"
- ch 4 p20: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.9.1 ¶13 "Example 4.10 A 100 turn closely wound" · §4.9.1 ¶14 "The coil is placed in a vertical"
- ch 4 p21: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §4.9.1 ¶15 "Solution (a) From Eq. (4.12) B =" · §4.9.1 ¶17 "(c) tau = |m × B| [from" · §4.9.1 ¶19 "Example 4.11 (a) A current-carrying circular loop" · printed starts no row begins with: "The direction is given by the" · "Thus, final torque τ = m" · "(a) A current-carrying circular loop lies" · "the loop turns around itself (i.e.,"
- ch 4 p22: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.9.1 ¶21 "(c) A loop of irregular shape carrying" · §4.9.1 ¶22 "Solution (a) No, because that would require" · §4.9.1 ¶23 "(b) Orientation of stable equilibrium is one" · §4.9.1 ¶24 "(c) It assumes circular shape with its"
- ch 4 p23: 6 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "(ii) is subject to torque like"
- ch 4 p25: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.10 ¶12 "Example 4.12 In the circuit (Fig. 4.23)"
- ch 4 p26: 3 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.10 ¶13 "Solution (a) Total resistance in the circuit" · printed starts no row begins with: "(a) Total resistance in the circuit" · "Total resistance in the circuit is,"
- ch 5 p1: 5 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "(i) The earth behaves as a" · "approximately from the geographic south to"
- ch 5 p2: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §5.2 ¶1 "We begin our study by examining iron" · §5.2.1 ¶1 "The pattern of iron filings permits us" · §5.2.1 ¶3 "* In some textbooks the magnetic field" · printed starts no row begins with: "north and south poles known as"
- ch 5 p3: 5 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "magnetic field B at that point." · "region ii than in region i"
- ch 5 p4: 11 rows start here, the print starts 8 paragraphs — rows the print does not start: §5.2.3 ¶4 "The magnetic potential energy U_m is given" · §5.2.3 ¶6 "Example 5.1 (a) What happens if a" · §5.2.3 ¶7 "(b) A magnetised needle in a uniform"
- ch 5 p5: 7 rows start here, the print starts 9 paragraphs — rows the print does not start: §5.2.3 ¶9 "Solution (a) In either case, one gets" · §5.2.4 ¶1 "Comparison of Eqs. (5.1), (5.2) and (5.3)" · §5.2.4 ¶2 "Likewise, the axial field (B_A) of a" · printed starts no row begins with: "(c) Must every magnetic configuration have" · "pole? What about the field due" · "(a) In either case, one gets" · "induced north pole." · "a straight infinite conductor."
- ch 5 p6: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §5.2.4 ¶3 "Equation (5.5) is just Eq. (5.1) in" · §5.2.4 ¶8 "Solution Potential energy of the configuration arises" · §5.2.4 ¶9 "Equilibrium is stable when m_Q is parallel" · printed starts no row begins with: "among all the configurations shown?"
- ch 5 p7: 6 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.2.4 ¶10 "(a) PQ_1 and PQ_2" · §5.2.4 ¶11 "(b) (i) PQ_3, PQ_6 (stable); (ii) PQ_5," · §5.2.4 ¶12 "(c) PQ_6" · §5.3 ¶2 "The situation is radically different for magnetic"
- ch 5 p8: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.3 ¶5 "Thus, Gauss's law for magnetism is:" · §5.3 ¶7 "Example 5.3 Many of the diagrams given"
- ch 5 p9: 12 rows start here, the print starts 17 paragraphs — rows the print does not start: §5.3 ¶8 "Solution" · §5.3 ¶14 "(f ) Wrong. These field lines cannot" · §5.3 ¶16 "Example 5.4" · printed starts no row begins with: "enter the surface as the number" · "conductor, as described in Chapter 4." · "nor when the loop encloses charges." · "contains magnetic field." · "eventually to form closed loops." · "the N-pole, and the S-pole, the" · "particle at every point?" · "magnetism be modified?"
- ch 5 p10: 9 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.3 ¶21 "Solution (a) No. The magnetic force is" · §5.4 ¶1 "The earth abounds with a bewildering variety" · §5.4 ¶2 "We have seen that a circulating electron" · §5.4 ¶3 "Consider a long solenoid of n turns" · §5.4 ¶4 "If the interior of the solenoid is" · printed starts no row begins with: "have magnetic moments even though its" · "(a) No. The magnetic force is" · "force = qv × B). It" · "closed surface is always zero B.∆s" · "(monopole) magnetic charge enclosed by S.]" · "wire, this force is zero.)"
- ch 5 p11: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.4 ¶8 "Example 5.5 A solenoid has a core"
- ch 5 p12: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.4 ¶9 "Solution (a) The field H is dependent" · §5.4 ¶10 "(b) The magnetic field B is given" · §5.4 ¶11 "(c) Magnetisation is given by M =" · §5.4 ¶12 "(d) The magnetising current I_M is the"
- ch 6 p2: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.2 ¶2 "Experiment 6.1 Figure 6.1 shows a coil" · §6.2 ¶3 "Experiment 6.2 In Fig. 6.2 the bar" · §6.2 ¶4 "* Wherever the term 'coil' or 'loop'"
- ch 6 p3: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.2 ¶5 "Experiment 6.3 The above two experiments involved"
- ch 6 p4: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.4 ¶3 "* Note that sensitive electrical instruments in" · printed starts no row begins with: "Φ = B .dA + B"
- ch 6 p5: 11 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.4 ¶4 "Mathematically, the induced emf is given by" · §6.4 ¶5 "The negative sign indicates the direction of" · §6.4 ¶6 "In the case of a closely wound" · §6.4 ¶7 "The induced emf can be increased by" · §6.4 ¶8 "From Eqs. (6.1) and (6.2), we see" · §6.4 ¶10 "Solution" · §6.4 ¶13 "In experimental physics one must learn to" · printed starts no row begins with: "towards the test coil C ."
- ch 6 p6: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.4 ¶16 "Example 6.3 A circular coil of radius" · §6.4 ¶17 "Solution Initial flux through the coil, Phi_B"
- ch 6 p8: 7 rows start here, the print starts 11 paragraphs — rows the print does not start: §6.5 ¶7 "Example 6.4 Figure 6.7 shows planar loops" · §6.5 ¶8 "Solution (i) The magnetic flux through the" · §6.5 ¶11 "Example 6.5 (a) A closed loop is" · printed starts no row begins with: "(i) The magnetic flux through the" · "the increasing flux." · "bacb, so as to oppose the" · "(a) A closed loop is held" · "(i) when it is wholly inside" · "(ii) when it is partially outside" · "electric field is normal to the"
- ch 6 p9: 6 rows start here, the print starts 9 paragraphs — rows the print does not start: §6.5 ¶14 "Solution (a) No. However strong the magnet" · §6.6 ¶1 "Let us consider a straight conductor moving" · §6.6 ¶2 "Since x is changing with time, the" · printed starts no row begins with: "(d) Predict the polarity of the" · "(a) No. However strong the magnet" · "only by changing the magnetic flux" · "by changing the electric flux." · "constant, hence induced emf will vary" · "FIGURE 6.10 The arm PQ is"
- ch 6 p10: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.6 ¶6 "Example 6.6 A metallic rod of 1"
- ch 6 p11: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §6.6 ¶7 "Solution Method I As the rod is" · §6.6 ¶8 "Method II To calculate the emf, we" · §6.6 ¶9 "This expression is identical to the expression"
- ch 6 p12: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §6.6 ¶10 "Example 6.7 A wheel with 10 metallic" · §6.6 ¶11 "Solution Induced emf = (1/2) omega B" · §6.6 ¶12 "The number of spokes is immaterial because"
- ch 6 p13: 9 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.7.1 ¶2 "When a current I_2 is set up" · §6.7.1 ¶4 "For these simple co-axial solenoids it is" · §6.7.1 ¶5 "Note that we neglected the edge effects" · §6.7.1 ¶7 "M_21 is called the mutual inductance of" · §6.7.1 ¶9 "Using Eq. (6.9) and Eq. (6.10), we"
- ch 6 p14: 6 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.7.1 ¶11 "We explained the above example with air" · §6.7.1 ¶12 "It is also important to know that" · §6.7.1 ¶15 "Now, let us recollect Experiment 6.3 in" · §6.7.1 ¶16 "Then, from Eq. (6.7), we have N_1"
- ch 6 p16: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.7.2 ¶10 "Example 6.9 (a) Obtain the expression for" · §6.7.2 ¶11 "Solution" · §6.7.2 ¶12 "(a) From Eq. (6.17), the magnetic energy"
- ch 6 p17: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.7.2 ¶13 "(b) The magnetic energy per unit volume" · §6.7.2 ¶14 "We have already obtained the relation for" · §6.7.2 ¶15 "In both the cases energy is proportional"
- ch 6 p18: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §6.8 ¶4 "From Faraday's law, the induced emf for"
- ch 6 p19: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.8 ¶11 "We urge you to explore such alternative"
- ch 7 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §7.1 ¶2 "* The phrases ac voltage and ac"
- ch 7 p2: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.2 ¶2 "To find the value of current through"
- ch 7 p3: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §7.2 ¶5 "The instantaneous power dissipated in the resistor" · §7.2 ¶7 "* The average value of a function" · §7.2 ¶8 "** < cos 2 omega t >"
- ch 7 p4: 8 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.2 ¶10 "It is customary to measure and specify" · §7.2 ¶12 "Example 7.1 A light bulb is rated" · §7.2 ¶13 "Solution" · §7.2 ¶14 "(a) We are given P = 100" · §7.2 ¶15 "(b) The peak voltage of the source" · §7.2 ¶16 "(c) Since, P = I V I"
- ch 7 p5: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.3 ¶2 "From Fig. 7.4(a) we see that phasors" · §7.4 ¶2 "* Though voltage and current in ac"
- ch 7 p6: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.4 ¶5 "Using − cos(omega t) = sin(omega t"
- ch 7 p7: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.4 ¶11 "Example 7.2 A pure inductor of 25.0" · §7.4 ¶12 "Solution The inductive reactance, X_L = 2"
- ch 7 p8: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.5 ¶4 "To find the current, we use the" · §7.5 ¶5 "Using the relation, cos(omega t) = sin(omega"
- ch 7 p9: 9 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.5 ¶6 "The dimension of capacitive reactance is the" · §7.5 ¶8 "Figure 7.8(a) shows the phasor diagram at" · §7.5 ¶10 "Thus, we see that in the case" · §7.5 ¶11 "Example 7.3 A lamp is connected in" · §7.5 ¶12 "Solution When a dc source is connected" · §7.5 ¶13 "Example 7.4 A 15.0 µF capacitor is" · §7.5 ¶14 "Solution The capacitive reactance is X_C ="
- ch 7 p10: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.5 ¶15 "If the frequency is doubled, the capacitive" · §7.5 ¶17 "The switch is closed and after sometime," · §7.6 ¶2 "If q is the charge on the" · §7.6 ¶3 "We want to determine the instantaneous current" · printed starts no row begins with: "connected to an ac source."
- ch 7 p11: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.6.1 ¶7 "Substituting the values of v_Rm, v_Cm, and" · §7.6.1 ¶8 "By analogy to the resistance in a"
- ch 7 p12: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.6.1 ¶9 "Since phasor I is always parallel to" · §7.6.1 ¶11 "If X_C > X_L, phi is positive" · printed starts no row begins with: "Z as its hypotenuse." · "If X > X , f"
- ch 7 p14: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.6.2 ¶8 "Solution Given R = 200 Ω, C" · §7.6.2 ¶11 "Thus, if the phase difference between two" · §7.7 ¶1 "We have seen that a voltage v" · §7.7 ¶2 "Therefore, the instantaneous power p supplied by" · printed starts no row begins with: "the circuit. It is"
- ch 7 p15: 10 rows start here, the print starts 1 paragraphs — rows the print does not start: §7.7 ¶4 "Case (i) Resistive circuit: If the circuit" · §7.7 ¶5 "Case (ii) Purely inductive or capacitive circuit:" · §7.7 ¶6 "Case (iii) LCR series circuit: In an" · §7.7 ¶7 "Case (iv) Power dissipated at resonance in" · §7.7 ¶8 "Example 7.7 (a) For circuits used for" · §7.7 ¶9 "(b) Power factor can often be improved" · §7.7 ¶10 "Solution (a) We know that P =" · §7.7 ¶11 "(b) Suppose in a circuit, current I" · §7.7 ¶12 "We can improve the power factor (tending"
- ch 7 p16: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.7 ¶13 "It's clear from this analysis that if" · §7.7 ¶15 "Solution (a) To find the impedance of" · printed starts no row begins with: "(a) To find the impedance of" · "(b) Phase difference, f = tan"
- ch 7 p17: 11 rows start here, the print starts 10 paragraphs — rows the print does not start: §7.7 ¶24 "You can see that in the present" · §7.7 ¶26 "Solution The metal detector works on the" · printed starts no row begins with: "across the source."
- ch 7 p19: 10 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "the flux due to primary passes"
- ch 7 p20: 4 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "winding the primary and secondary coils" · "using thick wire." · "in the iron core and causes"
- ch 8 p3: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.2 ¶4 "Since the contradiction arises from our use" · §8.2 ¶6 "Now if the charge Q on the" · §8.2 ¶7 "This implies that for consistency, epsilon_0 (dPhi_E"
- ch 8 p4: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §8.2 ¶14 "* They are still not perfectly symmetrical;" · printed starts no row begins with: "region, we expect a magnetic field,"
- ch 8 p6: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §8.3.2 ¶1 "It can be shown from Maxwell's equations" · §8.3.2 ¶2 "In Fig. 8.3, we show a typical"
- ch 8 p7: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §8.3.2 ¶7 "Thus, the velocity of light depends on"
- ch 8 p8: 11 rows start here, the print starts 5 paragraphs — rows the print does not start: §8.3.2 ¶10 "Example 8.1 A plane electromagnetic wave of" · §8.3.2 ¶12 "To find the direction, we note that" · §8.3.2 ¶16 "Solution" · §8.3.2 ¶18 "(b) E_0 = B_0 c = 2×10^-7" · §8.4 ¶1 "At the time Maxwell predicted the existence" · §8.4 ¶2 "We briefly describe these different types of"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 1 p29: the print starts "Note that r, r¢, rˆ′ all" where §1.12 ¶8 starts "Note that rho, r', r_hat' all can" — the layer dropped the line's math
- ch 1 p34: the print starts "Let s be the uniform surface" where §1.14.2 ¶1 starts "Let sigma be the uniform surface charge" — the layer dropped the line's math
- ch 1 p35: the print starts "Let s be the uniform surface" where §1.14.3 ¶1 starts "Let sigma be the uniform surface charge" — the layer dropped the line's math
- ch 2 p11: the print starts "Since dV is negative, dV =" where §2.6.1 ¶4 starts "Since delta V is negative, delta V" — the layer dropped the line's math
- ch 2 p26: the print starts "The product e K is called" where §2.13 ¶5 starts "The product epsilon_0 K is called the" — the layer dropped the line's math
- ch 4 p20: the print starts "As θ à 0, the perpendicular" where §4.9.1 ¶9 starts "As theta -> 0, the perpendicular distance" — the layer dropped the line's math
- ch 7 p10: the print starts "FIGURE 7.10 A series LCR circuit" where §7.6 ¶1 starts "Figure 7.10 shows a series LCR circuit" — the layer dropped the line's math
- ch 7 p18: the print starts "But e = v . If" where §7.8 ¶6 starts "But epsilon_p = v_p. If this were" — the layer dropped the line's math
- ch 8 p7: the print starts "The relation ω = ck is" where §8.3.2 ¶3 starts "The relation omega = ck is the" — the layer dropped the line's math

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

- ch 2 p12: the print numbers (2.26) 3 times, the rows carry it twice — a displayed equation dropped, its number altered, or a reference to it lost
- ch 2 p19: the print numbers (2.35) 3 times, the rows carry it twice — a displayed equation dropped, its number altered, or a reference to it lost
- ch 3 p16: the print numbers (3.54) twice, the rows carry it once — a displayed equation dropped, its number altered, or a reference to it lost
- ch 3 p16: the print numbers (3.55) twice, the rows carry it once — a displayed equation dropped, its number altered, or a reference to it lost

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
- ch 1 §1.7.2 ¶8 runs from p18 onto p19, but p19 opens a new paragraph: "1.8 ELECTRIC FIELD LINES" — ruled noise: Example 1.8's solution continues at the top of p19 (x 78, its block's indent) and the heading 1.8 comes after it
- ch 1 §1.10.2 ¶6 runs from p26 onto p27, but p27 opens a new paragraph: "1.11 DIPOLE IN A UNIFORM EXTERNAL" — ruled noise: Example 1.9's solution continues at the top of p27 and the heading 1.11 comes after it
- ch 1 §1.8 ¶1: figure_refs carries "Figure 1.12", which no caption in chapter 1 prints — ruled noise: p19 prints "FIGURE 1.12 Field of a point charge."; the caption collector missed it
- ch 2 §2.8.1 ¶4: the paragraph mentions table 6.1, which figure_refs does not carry — ruled noise: "Physics Part I, Table 6.1" is the Class XI book's table, not this book's
- ch 3 §3.11 ¶2: figure_refs carries "Fig. 3.13", which no caption in chapter 3 prints — ruled noise: p15 prints "FIGURE 3.13 Two cells of emf's"; the caption collector missed it
- ch 3 §3.11 ¶3: figure_refs carries "Fig. 3.13", which no caption in chapter 3 prints — ruled noise: p15 prints "FIGURE 3.13 Two cells of emf's"; the caption collector missed it
- ch 3 §3.11 ¶6: figure_refs carries "Fig.3.13", which no caption in chapter 3 prints — ruled noise: p15 prints "FIGURE 3.13 Two cells of emf's"; the caption collector missed it
- ch 5 §5.2 ¶1: figure_refs carries "Fig. 5.1", which no caption in chapter 5 prints — ruled noise: p2 prints "FIGURE 5.1" under the photograph; the caption collector missed it
- ch 5 §5.2.1 ¶1: figure_refs carries "Figure 1.14(d)", which no caption in chapter 5 prints — ruled noise: "refer to the Chapter 1, Figure 1.14(d)" names chapter 1's figure; the label carries its chapter
- ch 6 §6.3 ¶2: figure_refs carries "Fig. 6.5", which no caption in chapter 6 prints — ruled noise: p4 prints "FIGURE 6.5 Magnetic field B_i"; the caption collector missed it
- ch 7 §7.5 ¶6 starts a paragraph at the top of p9, but the print continues p8's: "The dimension of capacitive reactance is" — ruled noise: p8 ends in the display (7.18) and p9 opens flush: a row starting flush after a display stays as transcribed (DECISIONS 2026-09-26)
- ch 7 §7.3 ¶1: figure_refs carries "Fig. 7.4", which no caption in chapter 7 prints — ruled noise: p5 prints "FIGURE 7.4 (a) A phasor diagram"; the caption collector missed it
- ch 7 §7.3 ¶1: figure_refs carries "Fig. 7.4(b)", which no caption in chapter 7 prints — ruled noise: p5 prints "FIGURE 7.4 (a) A phasor diagram"; the caption collector missed it
- ch 7 §7.3 ¶2: figure_refs carries "Fig. 7.4(a)", which no caption in chapter 7 prints — ruled noise: p5 prints "FIGURE 7.4 (a) A phasor diagram"; the caption collector missed it
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (1042 rows)
request id: pipeline-ncert-verify-80db28ac-62d5-48a3-bbd6-51a29d62f5a6

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 181 | 8 | 173 |
artefact: verify/phy12-part1/en.jsonl (181 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 8 | 33501 | 2670 | 50757 | 7251 | ₹11.01 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 1 p23 §1.10.1 ¶2: printed "p (not p_hat) in E_(-q) equation" · transcribed "p_hat [1.13(a)]"
- ch 1 p23 §1.10.1 ¶3: printed "p [1.13(b)]" · transcribed "p_hat [1.13(b)]"
- ch 1 p24 §1.10.1 ¶4: printed "(r^2 - a^2)^2] p_hat = q / (4 pi epsilon_o) · 4 a r / (r^2 - a^2)^2" · transcribed "(r - a)^2 - 1 / (r + a)^2] p_hat = q / (4 pi epsilon_o) · 4 a r / (r^2 - a^2)^2"
- ch 1 p24 §1.10.1 ¶6: printed "opposite to p_hat (bold p with hat)" · transcribed "opposite to p_hat"
- ch 1 p24 §1.10.1 ¶6: printed "= − 2 q a / (4 pi epsilon_o (r^2 + a^2)^(3/2)) p (bold p, no hat)" · transcribed "= − 2 q a / (4 pi epsilon_o (r^2 + a^2)^(3/2)) p_hat"
- ch 4 p4 §4.2.2 ¶6: printed "sin theta n" · transcribed "sin theta n_hat"
- ch 4 p4 §4.2.3 ¶1: printed "F = (nlA)q v_d × B" · transcribed "F = (nlA)q v_d × B where"
- ch 4 p4 §4.2.3 ¶1: printed "= [ jAl ] × B = Il × B" · transcribed "= [ jAl ] × B = Il × B (4.4)"
- ch 4 p4 §4.2.3 ¶2: printed "dl_j × B" · transcribed "Idl_j × B"
- ch 4 p9 §4.5 ¶4: printed "in the x-y plane" · transcribed "is in the x-y plane"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 1 p13: "q1 = q, q2 = q, q3 = -q"
- ch 1 p13: "|F1| = |F2| = F"
- ch 1 p13: "|F3| = sqrt(3) F"
- ch 2 p16: "The mutual interaction energy of the two charges remains unchanged (torque diagram Fig. 2.16 caption)"
- ch 3 p14: "V = Potential difference between P and A + Potential difference between A and B + Potential difference between B and N"
- ch 3 p22: "SUMMARY"
- ch 3 p22: "1. Current through a given area"
- ch 3 p22: "2. To maintain a steady current"
- ch 3 p22: "3. Ohm's law"
- ch 4 p2: "Hans Christian Oersted (1777-1851) Danish physicist and chemist"
- ch 4 p13: "We also note that Ampere's circuital law holds"
- ch 4 p13: "enclosed current"
- ch 4 p26: "SUMMARY"
- ch 4 p26: "1. The total force on a charge q moving with velocity v"
- ch 8 p5: "Maxwell's equations in Vacuum box equations"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 1 p5 §1.4.3 ¶4: printed "6 × 10^18 electrons in a charge of –1C" · transcribed "6 × 10^18 electrons in a charge of –1C"
- ch 1 p5 §1.4.3 ¶5: printed "n_2 × e + n_1 × (–e) = (n_2 – n_1) e" · transcribed "n_2 × e + n_1 × (–e) = (n_2 – n_1) e"
- ch 1 p8 §1.5 ¶12: printed "r_hat_21 = r_21 / r_21" · transcribed "r_hat_21 = r_21 / r_21"
- ch 1 p11 §1.6 ¶3: printed "F_12 = (1 / 4 pi epsilon_0) q_1 q_2 / r_12^2 r_hat_12" · transcribed "F_12 = (1 / 4 pi epsilon_0) q_1 q_2 / r_12^2 r_hat_12"
- ch 1 p12 §1.6 ¶9: printed "By symmatry AO" · transcribed "By symmatry AO"
- ch 1 p12 §1.6 ¶9: printed "AD = AC cos 30º" · transcribed "AD = AC cos 30º"
- ch 1 p12 §1.6 ¶9: printed "(2/3) AD = (1/sqrt(3)) l" · transcribed "(2/3) AD = (1/sqrt(3)) l"
- ch 1 p21 §1.9 ¶1: printed "perpendicular to v is delta dS cos theta" · transcribed "perpendicular to v is delta dS cos theta"
- ch 1 p21 §1.9 ¶1: printed "dS is v. n_hat dS" · transcribed "dS is v. n_hat dS"
- ch 1 p22 §1.9 ¶5: printed "n_hat where" · transcribed "n_hat where"
- ch 1 p22 §1.9 ¶6: printed "and the outward normal to the area element" · transcribed "and the outward normal to the area element"
- ch 1 p23 §1.10 ¶2: printed "faster than like 1/r^2" · transcribed "faster than like 1/r^2"
- ch 1 p23 §1.10.1 ¶2: printed "p_hat [1.13(a)]" · transcribed "p_hat [1.13(a)]"
- ch 1 p24 §1.10.1 ¶4: printed "E = 4 q a / (4 pi epsilon_0 r^3) p_hat" · transcribed "E = 4 q a / (4 pi epsilon_0 r^3) p_hat"
- ch 1 p24 §1.10.1 ¶7: printed "E = − 2 q a / (4 pi epsilon_o r^3) p_hat" · transcribed "E = − 2 q a / (4 pi epsilon_o r^3) p_hat"
- ch 1 p24 §1.10.1 ¶8: printed "p = q × 2a p_hat" · transcribed "p = q × 2a p_hat"
- ch 1 p24 §1.10.1 ¶8: printed "E = 2 p / (4 pi epsilon_o r^3)" · transcribed "E = 2 p / (4 pi epsilon_o r^3)"
- ch 1 p31 §1.13 ¶16: printed "±pi/2" · transcribed "± pi/2"
- ch 1 p35 §1.14.3 ¶2: printed "where q = 4 pi R^2 sigma" · transcribed "where q = 4 pi R^2 sigma"
- ch 2 p4 §2.3 ¶2: printed "r'^2 r_hat' (2.5)" · transcribed "r'^2 r_hat' (2.5)"
- ch 2 p4 §2.3 ¶2: printed "r' to r' + Delta r'" · transcribed "r' to r' + Delta r'"
- ch 2 p5 §2.4 ¶1: printed "points in the direction from –q to q" · transcribed "points in the direction from –q to q"
- ch 2 p6 §2.4 ¶4: printed "a^2 / r^2)" · transcribed "a^2 / r^2)"
- ch 2 p11 §2.7 ¶1: printed "r_1P where" · transcribed "r_1P where"
- ch 2 p16 §2.8.3 ¶2: printed "tau_ext(theta) d theta" · transcribed "tau_ext(theta) d theta"
- ch 2 p28 §2.14.2 ¶4: printed "C_1V + C_2V + ... C_nV(2.66)" · transcribed "C_1V + C_2V + ... C_nV (2.66)"
- ch 3 p6 §3.5 ¶4: printed "I Delta t = + n e A |v_d| Delta t" · transcribed "I Delta t = + n e A |v_d| Delta t"
- ch 3 p8 §3.5 ¶21: printed "Are the paths of electrons straight lines" · transcribed "Are the paths of electrons straight lines"
- ch 3 p8 §3.5 ¶21: printed "in the (i) absence of electric field, (ii) presence" · transcribed "in the (i) absence of electric field, (ii) presence"
- ch 3 p12 §3.9 ¶2: printed "Delta Q[(V (B) – V (A)]" · transcribed "Delta Q[(V (B) – V (A)]"
- ch 3 p16 §3.11 ¶15: printed "– I (r_1 r_2) / (r_1 + r_2)" · transcribed "– I (r_1 r_2) / (r_1 + r_2)"
- ch 3 p20 §3.12 ¶15: printed "5 V + (5/8 × 4) V – (15/8 × 4) V" · transcribed "5 V + (5/8 × 4) V – (15/8 × 4) V"
- ch 3 p21 §3.13 ¶4: printed "R_4 = R_3 R_2 / R_1" · transcribed "R_4 = R_3 R_2 / R_1"
- ch 4 p3 §4.2.1 ¶5: printed "principle of superposition: the magnetic field of several" · transcribed "principle of superposition: the magnetic field of several"
- ch 4 p5 §4.2.3 ¶5: printed "B = m g / I l" · transcribed "B = m g / I l"
- ch 4 p8 §4.4 ¶12: printed "Delta l = Delta x i_hat" · transcribed "Delta l = Delta x i_hat"
- ch 4 p10 §4.5 ¶7: printed "B_0 = mu_0 I / (2R) i_hat" · transcribed "B_0 = mu_0 I / (2R) i_hat"
- ch 4 p12 §4.6 ¶4: printed "B. 2πr" · transcribed "B. 2πr"
- ch 4 p13 §4.6 ¶10: printed "meant by the term
enclosed current." · transcribed "meant by the term enclosed current."
- ch 4 p16 §4.7 ¶7: printed "Eq. (4.20)" · transcribed "Eq. (4.20)"
- ch 4 p17 §4.8 ¶4: printed "equal in magnitude to F_ba" · transcribed "equal in magnitude to F_ba"
- ch 4 p18 §4.8 ¶18: printed "f = I B = 1 × 3 × 10^-5" · transcribed "f = I B = 1 × 3 × 10^-5"
- ch 4 p21 §4.9.1 ¶18: printed "ω_f = 20 s^-1" · transcribed "ω_f = 20 s^-1."
- ch 4 p23 §4.10 ¶1: printed "Chapters 3" · transcribed "Chapters 3"
- ch 4 p23 §4.10 ¶1: printed "1.2 V" · transcribed "1.2 V"
- ch 4 p24 §4.10 ¶5: printed "resistance r_s, called shunt" · transcribed "resistance r_s, called shunt"
- ch 4 p24 §4.10 ¶5: printed "R_G r_s / (R_G + r_s) ≃ r_s if R_G >> r_s" · transcribed "R_G r_s / (R_G + r_s) ≃ r_s if R_G >> r_s"
- ch 5 p4 §5.2.3 ¶4: printed "= -m.B (5.3)" · transcribed "= −m.B (5.3)"
- ch 5 p6 §5.2.4 ¶8: printed "B_P = − (mu_0 / 4 pi) (m_P / r^3)" · transcribed "B_P = − (mu_0 / 4 pi) (m_P / r^3)"
- ch 5 p6 §5.2.4 ¶8: printed "B_P = (mu_0 2 / 4 pi) (m_P / r^3)" · transcribed "B_P = (mu_0 2 / 4 pi) (m_P / r^3)"
- ch 5 p7 §5.3 ¶3: printed "(5.6) where 'all' stands" · transcribed "(5.6) where 'all' stands"
- ch 5 p7 §5.3 ¶3: printed "phi_B = sum(all) Δphi_B" · transcribed "phi_B = sum(all) Δphi_B"
- ch 5 p7 §5.3 ¶3: printed "= q / epsilon_0" · transcribed "= q / epsilon_0"
- ch 5 p7 §5.3 ¶3: printed "the field  at ΔS" · transcribed "the field at ΔS"
- ch 5 p10 §5.3 ¶22: printed "[Analogous to Gauss's law of electrostatics, integral(S) B.Delta s = mu_0 q_m where" · transcribed "[Analogous to Gauss's law of electrostatics, integral(S) B.Delta s = mu_0 q_m where"
- ch 5 p11 §5.4 ¶5: printed "H = B / mu_0 – M" · transcribed "H = B / mu_0 – M"
- ch 5 p12 §5.4 ¶11: printed "≅ 8 × 10^5 A/m" · transcribed "≅ 8 × 10^5 A/m"
- ch 5 p14 §5.5.3 ¶3: printed "magnetisation persists." · transcribed "magnetisation persists"
- ch 6 p5 §6.4 ¶11: printed "coil C_2" · transcribed "coil C_2"
- ch 6 p5 §6.4 ¶11: printed "test coil C_1" · transcribed "test coil C_1"
- ch 6 p6 §6.4 ¶17: printed "Phi_B (initial) = BA cos theta" · transcribed "Phi_B (initial) = BA cos theta"
- ch 6 p6 §6.4 ¶17: printed "3.8 × 10^-3 V" · transcribed "3.8 × 10^-3 V"
- ch 6 p8 §6.5 ¶12: printed "wholly inside" · transcribed "wholly inside"
- ch 6 p9 §6.6 ¶2: printed "= -Bl dx/dt = Blv" · transcribed "= -Bl dx/dt = Blv"
- ch 6 p10 §6.6 ¶3: printed "epsilon = W / q = Blv" · transcribed "epsilon = W / q = Blv"
- ch 6 p12 §6.7 ¶5: printed "[M L^2 T^-2 A^-2]" · transcribed "[M L^2 T^-2 A^-2]"
- ch 6 p13 §6.7.1 ¶8: printed "(n_2 l) (pi r_1^2) (mu_0 n_1 I_1)" · transcribed "(n_2 l) (pi r_1^2) (mu_0 n_1 I_1)"
- ch 6 p14 §6.7.1 ¶15: printed "(say of N_1 turns)" · transcribed "(say of N_1 turns)"
- ch 6 p16 §6.7.2 ¶8: printed "M_11 represents inductance due to the same coil" · transcribed "M_11 represents inductance due to the same coil"
- ch 7 p5 §7.4 ¶1: printed "v - L di/dt = 0" · transcribed "v − L di/dt = 0"
- ch 7 p11 §7.6.1 ¶2: printed "let V_L, V_R, V_C, and V represent" · transcribed "let V_L, V_R, V_C, and V represent"
- ch 7 p11 §7.6.1 ¶2: printed "V_C is pi/2 behind I" · transcribed "V_C is pi/2 behind I"
- ch 7 p12 §7.6.1 ¶13: printed "the phasor diagram say nothing" · transcribed "the phasor diagram say nothing"
- ch 7 p14 §7.6.2 ¶9: printed "X_C^2) = sqrt(R^2 + (2 pi nu C)^(-2))" · transcribed "X_C^2) = sqrt(R^2 + (2 pi nu C)^(-2))"
- ch 7 p16 §7.7 ¶15: printed "phi = tan^-1 (X_C - X_L) / R = tan^-1 ((4 - 8) / 3)" · transcribed "phi = tan^-1 (X_C - X_L) / R = tan^-1 ((4 - 8) / 3)"
- ch 7 p17 §7.7 ¶20: printed "= 222.1 rad/s" · transcribed "= 222.1 rad/s"
- ch 7 p17 §7.7 ¶20: printed "omega_0 / 2 pi = 221.1 / (2 × 3.14)" · transcribed "omega_0 / 2 pi = 221.1 / (2 × 3.14)"
- ch 7 p19 §7.8 ¶6: printed "dphi / dt" · transcribed "dphi / dt"
- ch 7 p19 §7.8 ¶6: printed "v_s = −N_s dphi / dt" · transcribed "v_s = −N_s dphi / dt"
- ch 8 p2 §8.1 ¶4: printed "gamma rays (wavelength ~10^(-12) m)" · transcribed "gamma rays (wavelength ~10^(-12) m)"
- ch 8 p3 §8.2 ¶9: printed "is not just the conduction" · transcribed "is not just the conduction"
- ch 8 p6 §8.3.2 ¶2: printed "[8.7(a)]" · transcribed "[8.7(a)]"
- ch 8 p6 §8.3.2 ¶2: printed "E_x = E_0 sin (kz–omega t)" · transcribed "E_x = E_0 sin (kz–omega t)"
- ch 8 p8 §8.3.2 ¶12: printed "(+ j_hat) × (+ k_hat) = i_hat" · transcribed "(+ j_hat) × (+ k_hat) = i_hat"
- ch 8 p10 §8.4.5 ¶1: printed "4 × 10^(-7) m (400 nm) down to
6 × 10^(-10) m" · transcribed "4 × 10^(-7) m (400 nm) down to 6 × 10^(-10) m"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 1 p15: "1.7.1 Electric field due to a system of charges"
- ch 1 p16: "1.7.2 Physical significance of electric field"
- ch 1 p21: "1.9 ELECTRIC FLUX"
- ch 1 p23: "1.10.1 The field of an electric dipole"
- ch 1 p25: "1.10.2 Physical significance of dipoles"
- ch 1 p34: "1.14.2 Field due to a uniformly charged infinite plane sheet"
- ch 1 p35: "1.14.3 Field due to a uniformly charged thin spherical shell"
- ch 2 p7: "2.5 POTENTIAL DUE TO A SYSTEM OF CHARGES"
- ch 2 p15: "2.8.2 Potential energy of a system of two charges in an external field"
- ch 2 p21: "2.10 DIELECTRICS AND POLARISATION"
- ch 2 p24: "2.12 The Parallel Plate Capacitor"
- ch 2 p27: "2.14.1 Capacitors in series"
- ch 2 p28: "2.14.2 Capacitors in parallel"
- ch 3 p5: "3.5 DRIFT OF ELECTRONS AND THE ORIGIN OF RESISTIVITY"
- ch 3 p8: "3.5.1 Mobility"
- ch 3 p15: "3.11 CELLS IN SERIES AND IN PARALLEL"
- ch 3 p15: "FIGURE 3.13 Two cells of emf's"
- ch 3 p16: "FIGURE 3.14 caption"
- ch 4 p2: "4.2 Magnetic Force"
- ch 4 p2: "4.2.1 Sources and fields"
- ch 4 p4: "4.2.3 Magnetic force on a current-carrying conductor"
- ch 4 p7: "Figure 4.7 illustration caption text (not applicable, it's a caption)"
- ch 4 p9: "FIGURE 4.9 Magnetic field on the axis of a current carrying circular loop of radius R."
- ch 4 p22: "4.9.2 Circular current loop as a magnetic dipole"
- ch 5 p2: "FIGURE 5.1 The arrangement of iron filings surrounding a bar magnet"
- ch 5 p4: "5.2.3 The dipole in a uniform magnetic field"
- ch 5 p5: "5.2.4 The electrostatic analog"
- ch 5 p12: "Table 5.2"
- ch 6 p4: "6.4 FARADAY'S LAW OF INDUCTION"
- ch 6 p12: "6.7.1 Mutual inductance"
- ch 6 p19: "FIGURE 6.14 An alternating emf is generated by a loop of wire rotating in a magnetic field."
- ch 7 p3: "FIGURE 7.3 The rms current I is related to the peak current"
- ch 8 p6: "8.3.2 Nature of electromagnetic waves"
flags set aside by the founder's rulings in ncert-corrections.yaml: 22
verdicts recorded on rows: 1042

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 195 | 195 | 191 | 4 | 0 | 0 | 0 | 97.9% |
| 2 | 192 | 192 | 192 | 0 | 0 | 0 | 0 | 100.0% |
| 3 | 127 | 127 | 127 | 0 | 0 | 0 | 0 | 100.0% |
| 4 | 152 | 152 | 148 | 4 | 0 | 0 | 0 | 97.4% |
| 5 | 97 | 97 | 97 | 0 | 0 | 0 | 0 | 100.0% |
| 6 | 104 | 104 | 104 | 0 | 0 | 0 | 0 | 100.0% |
| 7 | 123 | 123 | 123 | 0 | 0 | 0 | 0 | 100.0% |
| 8 | 52 | 52 | 52 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): 1034 of 1042 paragraphs, 99.2%
not in the clean share, adjudicate before recording it: 142 page-level start flags, 4 numbered equations the print carries that the rows do not, 15 passages no row carries

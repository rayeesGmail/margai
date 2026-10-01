# margai-pipeline ncert verify

- run: 2026-09-26 20:12 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part2 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 c31c191074514e534cbb6d88daaeeb110e28db482a034f1dbff6481073f9cb69

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 8 | 10 | 7 | 2 | 6 | 0 | 0 | 0 | 0 |
| 9 | 18 | 14 | 3 | 9 | 0 | 1 | 0 | 1 |
| 10 | 20 | 13 | 6 | 15 | 0 | 0 | 0 | 1 |
| 11 | 14 | 13 | 0 | 10 | 0 | 0 | 0 | 1 |
| 12 | 12 | 11 | 0 | 9 | 0 | 0 | 0 | 3 |
| 13 | 13 | 8 | 4 | 10 | 0 | 0 | 2 | 2 |
| 14 | 17 | 11 | 5 | 15 | 0 | 0 | 1 | 0 |

## where rows start against where the print starts paragraphs

- ch 8 p1: 3 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "distributed within the body. We restricted" · "situations of rigid bodies. A rigid" · "hard solid object having a definite" · "reality, bodies can be stretched, compressed" · "sufficiently large external force is applied" · "that solid bodies are not perfectly"
- ch 8 p2: 10 rows start here, the print starts 6 paragraphs — rows the print does not start: §8.2 ¶2 "The SI unit of stress is N" · §8.2 ¶4 "In both the cases, there is a" · §8.2 ¶7 "where theta is the angular displacement of" · §8.2 ¶9 "Thus, shearing strain = tan theta ≈"
- ch 8 p5: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.5.1 ¶6 "Example 8.2 A copper wire of length" · §8.5.1 ¶7 "Answer The copper and steel wires are" · printed starts no row begins with: "u Example 8.2 A copper wire"
- ch 8 p6: 15 rows start here, the print starts 10 paragraphs — rows the print does not start: §8.5.1 ¶10 "Mass of the performer = 60 kg" · §8.5.1 ¶14 "From Table 9.1, the Young's modulus for" · §8.5.1 ¶15 "Length of each thighbone L = 0.5" · §8.5.1 ¶16 "the radius of thighbone = 2.0 cm" · §8.5.1 ¶17 "Thus the cross-sectional area of the thighbone" · §8.5.2 ¶3 "Example 8.4 A square lead slab of" · §8.5.2 ¶5 "We know that shearing strain = (Δx/L)=" · printed starts no row begins with: "u Example 8.4 A square lead" · "Therefore, the stress applied is"
- ch 8 p8: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §8.5.3 ¶5 "Thus, solids are the least compressible, whereas," · §8.5.5 ¶1 "When a wire is put under a" · printed starts no row begins with: "Fractional compression ∆V/V, is" · "in a Stretched Wire"
- ch 8 p10: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §8.6 ¶7 "At the bottom of a mountain of"
- ch 9 p1: 5 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "and are therefore, called fluids. It" · "distinguishes liquids and gases from solids" · "air and two-thirds of its surface" · "is not only necessary for our" · "body constitutes mostly of water. All" · "in living beings including plants are"
- ch 9 p2: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §9.2 ¶4 "If F is the magnitude of this" · §9.2 ¶9 "The density of water at 4°C (277" · §9.2 ¶10 "* STP means standard temperature (0°C) and"
- ch 9 p4: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §9.2.2 ¶3 "Pressure difference depends on the vertical distance" · §9.2.2 ¶4 "Thus, the pressure P, at depth below" · §9.2.2 ¶7 "Answer Here h = 10 m and"
- ch 9 p5: 8 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "(b) What is the gauge pressure?"
- ch 9 p6: 9 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "larger piston move out?"
- ch 9 p9: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §9.4 ¶6 "We can employ the work – energy" · §9.4 ¶7 "We now divide each term by ∆V" · §9.4 ¶8 "We can rearrange the above terms to"
- ch 9 p10: 9 rows start here, the print starts 9 paragraphs — rows the print does not start: §9.4.2 ¶5 "Aerofoil or lift on aircraft wing: Figure" · printed starts no row begins with: "(c) shows an aerofoil, which is"
- ch 9 p11: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.4.2 ¶9 "Taking the average speed v_av = (v_2"
- ch 9 p15: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.6.2 ¶3 "This quantity S is the magnitude of" · §9.6.2 ¶5 "We make the following observations from above:"
- ch 10 p1: 3 rows start here, the print starts 12 paragraphs — printed starts no row begins with: "with boiling water is hotter than" · "physics, we need to define the" · "etc., more carefully. In this chapter," · "which heat flows from one body" · "fitting on the rim of a" · "goes down. You will also learn" · "or freezes, and its temperature does" · "processes even though a great deal" · "out of it."
- ch 10 p4: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.5 ¶5 "Here alpha_V is also a characteristic of"
- ch 10 p5: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.5 ¶7 "Water exhibits an anomalous behaviour; it contracts" · §10.5 ¶10 "At 0 °C, alpha_v = 3.7 ×" · printed starts no row begins with: "At constant pressure"
- ch 10 p6: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §10.5 ¶15 "Answer Consider a rectangular sheet of the" · §10.5 ¶18 "Answer Given, T_1 = 27 °C L_T1" · printed starts no row begins with: "Consider a rectangular sheet of the"
- ch 10 p7: 11 rows start here, the print starts 8 paragraphs — rows the print does not start: §10.6 ¶3 "In the third step, in place of" · §10.6 ¶5 "where ∆Q is the amount of heat" · §10.6 ¶10 "where C is known as molar specific"
- ch 10 p8: 4 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "Substance Specific heat capacity" · "Gas C (J mol K )"
- ch 10 p10: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.8 ¶8 "Triple Point The temperature of a substance"
- ch 10 p11: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.8 ¶10 "Let us now remove the burner. Allow"
- ch 10 p12: 4 rows start here, the print starts 6 paragraphs — rows the print does not start: §10.8.1 ¶5 "Answer Heat lost by water = m" · printed starts no row begins with: "Heat lost by water = ms" · "Heat required to raise temperature of" · "Heat lost = heat gained"
- ch 10 p13: 6 rows start here, the print starts 13 paragraphs — rows the print does not start: §10.8.1 ¶6 "Example 10.5 Calculate the heat required to" · printed starts no row begins with: "specific heat capacity of water, s" · "latent heat of fusion of ice," · "latent heat of steam, L" · "ice at –12 °C to steam" · "–12 °C to ice at 0" · "0 °C to water at 0" · "at 0 °C to water at" · "at 100 °C to steam at"
- ch 10 p15: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.9.1 ¶9 "Answer" · §10.9.1 ¶10 "Given, L_1 = L_2 = L =" · §10.9.1 ¶12 "So, H = H_1 = H_2 =" · §10.9.1 ¶13 "(i) T_0 = (K_1 T_1 + K_2" · §10.9.1 ¶14 "(ii) K' = 2K_1 K_2 / (K_1" · printed starts no row begins with: "(ii) the equivalent thermal conductivity of"
- ch 10 p16: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.9.2 ¶2 "In forced convection, material is forced to"
- ch 10 p17: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.9.3 ¶2 "When this thermal radiation falls on other" · §10.9.3 ¶3 "We find that black bodies absorb and" · §10.9.4 ¶2 "Notice that the wavelength lambda_m for which"
- ch 10 p19: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §10.10 ¶6 "From Eqs. (10.15) and (10.16) we have" · printed starts no row begins with: "∴ Rate of loss of heat"
- ch 10 p20: 5 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "Change in temperature"
- ch 11 p1: 3 rows start here, the print starts 13 paragraphs — printed starts no row begins with: "converted into heat and vice versa." · "our palms together, we feel warmer;" · "produces the ‘heat’. Conversely, in a" · "which in turn rotate the wheels" · "temperature, work, etc. more carefully. Historically," · "modern picture, heat was regarded as" · "variables and equation of body and" · "up to different heights. The flow" · "water in the two tanks are" · "favour of the modern concept of"
- ch 11 p2: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §11.2 ¶3 "* Thermodynamics may also involve other variables"
- ch 11 p3: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §11.3 ¶4 "* Both the variables need not change."
- ch 11 p6: 10 rows start here, the print starts 9 paragraphs — rows the print does not start: §11.5 ¶6 "As an application of Eq. (11.3), consider" · §11.5 ¶7 "Therefore, Delta W = P(V_g – V_l)" · printed starts no row begins with: "C is known as molar specific"
- ch 11 p7: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §11.6 ¶7 "The old unit of heat was calorie." · §11.6 ¶11 "Equations (11.9) to (11.11) give the desired" · §11.7 ¶1 "Every equilibrium state of a thermodynamic system" · printed starts no row begins with: "AND EQUATION OF STATE"
- ch 11 p8: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §11.8.1 ¶3 "* As emphasised earlier, Q is not"
- ch 11 p9: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §11.8.1 ¶6 "We now consider these processes in some"
- ch 11 p10: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §11.8.3 ¶3 "Figure11.8 shows the P-V curves of an"
- ch 11 p11: 9 rows start here, the print starts 5 paragraphs — rows the print does not start: §11.9 ¶3 "Kelvin-Planck statement" · §11.9 ¶4 "No process is possible whose sole result" · §11.9 ¶5 "Clausius statement" · §11.9 ¶6 "No process is possible whose sole result"
- ch 11 p13: 15 rows start here, the print starts 10 paragraphs — rows the print does not start: §11.11 ¶10 "Work done on the gas, [using Eq.(11.16)," · §11.11 ¶12 "The efficiency eta of the Carnot engine" · §11.11 ¶13 "Now since step 2 -> 3 is" · §11.11 ¶14 "Similarly, since step 4 -> 1 is" · §11.11 ¶15 "From Eqs. (11.24) and (11.25), V_3 /" · §11.11 ¶16 "Using Eq. (11.26) in Eq. (11.23), we" · printed starts no row begins with: "(a) working between two given temperatures"
- ch 12 p1: 3 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "gases by considering that gases are" · "particles. The actual atomic theory got" · "150 years later. Kinetic theory explains" · "which are short range forces that" · "and liquids, can be neglected for" · "was developed in the nineteenth century"
- ch 12 p3: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §12.3 ¶4 "where M is the mass of the"
- ch 12 p4: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §12.3 ¶7 "At low pressures or high temperatures the" · §12.3 ¶9 "Finally, consider a mixture of non-interacting ideal"
- ch 12 p5: 11 rows start here, the print starts 12 paragraphs — printed starts no row begins with: "Volume of a water molecule"
- ch 12 p6: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §12.4.1 ¶6 "where v is the speed and v^2_bar"
- ch 12 p7: 13 rows start here, the print starts 12 paragraphs — rows the print does not start: §12.4.2 ¶3 "We are now ready for a kinetic" · §12.4.2 ¶7 "The square root of v^2_bar is known" · §12.4.2 ¶13 "* E denotes the translational part of" · printed starts no row begins with: "( We can also write v" · "molecule = (3/2) ) k T"
- ch 12 p8: 10 rows start here, the print starts 11 paragraphs — rows the print does not start: §12.4.2 ¶14 "You should note that the composition of" · printed starts no row begins with: "of a molecule of the gas." · "speeds at any temperature."
- ch 12 p9: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §12.5 ¶5 "where omega_1 and omega_2 are the angular" · §12.5 ¶6 "We have assumed above that the O_2" · §12.5 ¶8 "* Rotation along the line joining the"
- ch 12 p12: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §12.7 ¶5 "Let us estimate l and tau for"
- ch 13 p1: 3 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "non-repetitive. We have also learnt about" · "motion and orbital motion of planets" · "time, that is, it is periodic." · "enjoyed rocking in a cradle or" · "periodic motion of a planet. Here," · "a similar motion. Examples of such" · "piston in a steam engine going" · "study this motion."
- ch 13 p3: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §13.2.1 ¶5 "Example 13.1 On an average, a human" · §13.2.1 ¶6 "Answer The beat frequency of heart =" · §13.2.2 ¶3 "If the argument of this function, omega" · printed starts no row begins with: "u Example 13.1 On an average,"
- ch 13 p4: 9 rows start here, the print starts 12 paragraphs — rows the print does not start: §13.2.2 ¶4 "The same result is obviously correct if" · §13.2.2 ¶6 "Example 13.2 Which of the following functions" · §13.2.2 ¶7 "Answer (i) sin omega t + cos" · §13.2.2 ¶8 "(ii) This is an example of a" · §13.3 ¶1 "Consider a particle oscillating back and forth" · printed starts no row begins with: "A = D cos φ and" · "u Example 13.2 Which of the" · "(b) non-periodic motion? Give the period" · "(ii) sin ωt + cos 2" · "(i) sin ωt + cos ωt" · "The periodic time of the function" · "periodic function with a period 2π/ω." · "never repeats its value."
- ch 13 p6: 9 rows start here, the print starts 5 paragraphs — rows the print does not start: §13.3 ¶7 "omega is called the angular frequency of" · §13.3 ¶8 "Example 13.3 Which of the following functions" · §13.3 ¶9 "Answer (a) sin omega t – cos" · §13.3 ¶10 "This function represents a simple harmonic motion" · §13.3 ¶11 "(b) sin^2 omega t = ½ –" · §13.3 ¶12 "The function is periodic having a period" · printed starts no row begins with: "u Example 13.3 Which of the" · "occurring at ½ instead of zero."
- ch 13 p7: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §13.4 ¶5 "Example 13.4 The figure given below depicts" · §13.4 ¶6 "Answer" · §13.4 ¶8 "The projection of OP on the x-axis" · §13.4 ¶9 "* The natural unit of angle is" · printed starts no row begins with: "u Example 13.4 The figure given" · "with the (positive direction of) x-axis."
- ch 13 p8: 5 rows start here, the print starts 9 paragraphs — rows the print does not start: §13.5 ¶3 "Eq. (13.11) gives the acceleration of a" · printed starts no row begins with: "90 = with the x-axis. After" · "covers an angle of in the" · "sense and makes an angle of" · "Writing this as x (t) =" · "and an initial phase of −"
- ch 13 p9: 10 rows start here, the print starts 10 paragraphs — rows the print does not start: §13.5 ¶5 "For simplicity, let us put phi =" · §13.5 ¶6 "Example 13.5 A body oscillates with SHM" · §13.5 ¶8 "(a) displacement = (5.0 m) cos [(2" · §13.6 ¶4 "Example 13.6 Two identical springs of spring" · printed starts no row begins with: "u Example 13.5 A body oscillates" · "according to the equation (in SI" · "(b) speed and (c) acceleration of" · "u Example 13.6 Two identical springs"
- ch 13 p11: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §13.7 ¶10 "Example 13.7 A block whose mass is" · printed starts no row begins with: "u Example 13.7 A block whose"
- ch 13 p12: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §13.7 ¶17 "The total energy of the block at"
- ch 13 p13: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §13.8 ¶5 "This is the restoring torque that tends" · §13.8 ¶7 "Now if theta is small, sin theta" · §13.8 ¶8 "In Table 13.1, we have listed the" · §13.8 ¶9 "Equation (13.24) is mathematically, identical to Eq." · §13.8 ¶11 "Example 13.8 What is the length of" · printed starts no row begins with: "u Example 13.8 What is the" · "simple pendulum, which ticks seconds ?" · "From this relation one gets,"
- ch 14 p1: 2 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "such an example. Here, elastic forces" · "to each other and, therefore, the" · "the water surface gets disturbed. The" · "a circle. If you continue dropping" · "water surface is disturbed. It gives" · "some cork pieces on the disturbed" · "the cork pieces move up and"
- ch 14 p3: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §14.2 ¶3 "We can look at a wave in"
- ch 14 p4: 12 rows start here, the print starts 15 paragraphs — rows the print does not start: §14.2 ¶8 "Example 14.1 Given below are some examples" · §14.2 ¶13 "Answer (a) Transverse and longitudinal (b) Longitudinal" · §14.3 ¶2 "The term phi in the argument of" · printed starts no row begins with: "u Example 14.1 Given below are" · "produced by displacing one end of" · "back and forth." · "vibrating quartz crystal." · "(a) Transverse and longitudinal" · "(c) Transverse and longitudinal"
- ch 14 p5: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §14.3 ¶4 "Fig. 14.6 shows the plots of Eq." · §14.3.1 ¶2 "The quantity (kx – omega t +"
- ch 14 p6: 12 rows start here, the print starts 3 paragraphs — rows the print does not start: §14.3.2 ¶2 "Since the sine function repeats its value" · §14.3.2 ¶3 "That is the displacements at points x" · §14.3.2 ¶4 "k is the angular wave number or" · §14.3.3 ¶2 "Now, the period of oscillation of the" · §14.3.3 ¶3 "Since sine function repeats after every 2" · §14.3.3 ¶4 "omega is called the angular frequency of" · §14.3.3 ¶5 "nu is usually measured in hertz." · §14.3.3 ¶7 "where s(x, t) is the displacement of" · §14.3.3 ¶8 "* Here again, ‘radian’ could be dropped"
- ch 14 p7: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §14.3.3 ¶9 "Example 14.2 A wave travelling along a" · §14.3.3 ¶13 "We, then, relate the wavelength lambda to" · §14.3.3 ¶15 "The displacement y at x = 30.0" · printed starts no row begins with: "u Example 14.2 A wave travelling" · "(b) the wavelength, and (c) the"
- ch 14 p8: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §14.4.1 ¶3 "Note the important point that the speed" · §14.4.1 ¶4 "Example 14.3 A steel wire 0.72 m" · §14.4.2 ¶1 "In a longitudinal wave, the constituents of" · printed starts no row begins with: "u Example 14.3 A steel wire" · "(Speed of Sound)"
- ch 14 p9: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §14.4.2 ¶3 "Liquids and solids generally have higher speed" · §14.4.2 ¶6 "Example 14.4 Estimate the speed of sound" · printed starts no row begins with: "u Example 14.4 Estimate the speed" · "mole of air at STP)"
- ch 14 p10: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §14.4.2 ¶8 "According to Newton's formula for the speed"
- ch 14 p11: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §14.5 ¶6 "The net displacement is then, by the"
- ch 14 p12: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §14.6.1 ¶2 "The resultant wave on the string is,"
- ch 14 p14: 3 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "with corresponding frequencies"
- ch 14 p15: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §14.6.1 ¶9 "The possible wavelengths are then restricted by" · §14.6.1 ¶10 "The normal modes – the natural frequencies" · §14.6.1 ¶13 "Example 14.5 A pipe, 30.0 cm long," · printed starts no row begins with: "u Example 14.5 A pipe, 30.0" · "ν = , for n ="
- ch 14 p16: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §14.6.1 ¶16 "For L = 30 cm and v" · §14.7 ¶4 "If |omega_1 - omega_2| << omega_1, omega_2," · §14.7 ¶5 "Now if we assume |omega_1 - omega_2|" · printed starts no row begins with: "n = = (pipe closed at" · "n = , n = ,"
- ch 14 p17: 3 rows start here, the print starts 6 paragraphs — rows the print does not start: §14.7 ¶7 "Example 14.6 Two sitar strings A and" · printed starts no row begins with: "Musical pillars are categorised into three" · "Archaeologists date the Nelliappar" · "The musical pillars of Nelliappar and" · "u Example 14.6 Two sitar strings"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 9 p3: the print starts "Now, if ρ is the mass" where §9.2.2 ¶2 starts "Now, if rho is the mass density" — the layer dropped the line's math
- ch 10 p6: the print starts "Since α ≃ 10 K ," where §10.5 ¶16 starts "Since alpha_l ≃ 10^-5 K^-1, from Table" — the layer dropped the line's math
- ch 11 p6: the print starts "We expect ∆Q and, therefore, heat" where §11.6 ¶2 starts "We expect Delta Q and, therefore, heat" — the layer dropped the line's math
- ch 12 p3: the print starts "where µ is the number of" where §12.3 ¶3 starts "where mu is the number of moles" — the layer dropped the line's math
- ch 12 p4: the print starts "If we fix µ and T" where §12.3 ¶8 starts "If we fix mu and T in" — the layer dropped the line's math
- ch 12 p6: the print starts "where is the average of v" where §12.4.1 ¶5 starts "where v_x^2_bar is the average of v_x^2" — the layer dropped the line's math
- ch 13 p3: the print starts "The unit of ν is thus" where §13.2.1 ¶3 starts "The unit of nu is thus s^-1." — the layer dropped the line's math
- ch 13 p12: the print starts "Let θ be the angle made" where §13.8 ¶3 starts "Let theta be the angle made by" — the layer dropped the line's math

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

- ch 9 §9.3 ¶3: the paragraph mentions fig 9.7, which figure_refs does not carry

## numbered equations the print carries that the rows do not

- ch 13 p5: the print numbers (13.4) 3 times, the rows carry it once — a displayed equation dropped, its number altered, or a reference to it lost
- ch 13 p6: the print numbers (13.4) once, the rows carry it not at all — a displayed equation dropped, its number altered, or a reference to it lost
- ch 14 p5: the print numbers (14.2) 9 times, the rows carry it 8 times — a displayed equation dropped, its number altered, or a reference to it lost

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
- ch 8 §8.5.1 ¶7: figure_refs carries "Table 8.1", which the paragraph never mentions — ruled noise: the text keeps the book's stale "Table 9.1"; figure_refs carry the Table 8.1 it means
- ch 8 §8.5.1 ¶7: the paragraph mentions table 9.1, which figure_refs does not carry — ruled noise: the text keeps the book's stale "Table 9.1"; figure_refs carry the Table 8.1 it means
- ch 8 §8.5.1 ¶14: figure_refs carries "Table 8.1", which the paragraph never mentions — ruled noise: the text keeps the book's stale "Table 9.1"; figure_refs carry the Table 8.1 it means
- ch 8 §8.5.1 ¶14: the paragraph mentions table 9.1, which figure_refs does not carry — ruled noise: the text keeps the book's stale "Table 9.1"; figure_refs carry the Table 8.1 it means
- ch 8 §8.5.2 ¶2: figure_refs carries "Table 8.2", which the paragraph never mentions — ruled noise: the text keeps the book's stale Tables 9.1 and 9.2; figure_refs carry the 8.1 and 8.2 they mean
- ch 8 §8.5.2 ¶2: figure_refs carries "Table 8.1", which the paragraph never mentions — ruled noise: the text keeps the book's stale Tables 9.1 and 9.2; figure_refs carry the 8.1 and 8.2 they mean
- ch 8 §8.5.2 ¶2: the paragraph mentions table 9.2, which figure_refs does not carry — ruled noise: the text keeps the book's stale Tables 9.1 and 9.2; figure_refs carry the 8.1 and 8.2 they mean
- ch 8 §8.5.2 ¶2: the paragraph mentions table 9.1, which figure_refs does not carry — ruled noise: the text keeps the book's stale Tables 9.1 and 9.2; figure_refs carry the 8.1 and 8.2 they mean
- ch 10 §10.6 ¶11 runs from p7 onto p8, but p8 opens a new paragraph: "Substance Specific heat capacity" — ruled noise: p7 ends at the measure and p8 opens with Table 10.3 across the top; the paragraph resumes under it
- ch 12 §12.4.2 ¶12 runs from p7 onto p8, but p8 opens a new paragraph: "of a molecule of the gas." — ruled noise: p8 opens "of a molecule of the gas. Therefore,", the rest of p7's "where m is the mass"
- ch 14 §14.4.2 ¶8 starts a paragraph at the top of p10, but the print continues p9's: "According to Newton’s formula for the" — ruled noise: p9 ends in Example 14.4's display (= 1.29 kg m^-3) and p10 opens indented (x 58.4 against 46.4)
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (682 rows)
request id: pipeline-ncert-verify-26409bc3-8178-4804-a791-6f64d28c5be6

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 104 | 7 | 97 |
artefact: verify/phy11-part2/en.jsonl (104 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 7 | 37646 | 2268 | 58008 | 0 | ₹9.89 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 9 p13 §9.5 ¶6: printed "eta = stress / strain rate" · transcribed "eta = stress / strain rate s^-1"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 9 p8: "but their directions are parallel. Figure 9.8 (b) gives a sketch of turbulent flow."
- ch 10 p10: "Figure : Pressure-temperature phase diagrams for (a) water and (b) CO_2 (not to the scale)."
- ch 12 p2: "Atomic Hypothesis in Ancient India and Greece"
- ch 12 p11: "Heat required = no. of moles × molar specific heat × rise in temperature"
- ch 13 p2: "h = ut + 1/2 gt^2 for downward motion, and"
- ch 13 p2: "h = ut − 1/2 gt^2 for upward motion,"
- ch 14 p17: "Musical Pillars"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 8 p2 §8.2 ¶4: printed "change in the length ∆L to the original" · transcribed "change in the length ∆L to the original"
- ch 8 p5 §8.5.1 ¶7: printed "From Table 9.1" · transcribed "From Table 9.1"
- ch 8 p6 §8.5.2 ¶1: printed "G = shearing stress (sigma_s)/shearing strain" · transcribed "G = shearing stress (sigma_s)/shearing strain"
- ch 8 p6 §8.5.2 ¶1: printed "G = (F/A)/theta = F/(A × theta) (8.11)" · transcribed "G = (F/A)/theta = F/(A × theta) (8.11)"
- ch 9 p4 §9.2.3 ¶1: printed "P_a = rho g h (9.8)" · transcribed "P_a = rho g h (9.8)"
- ch 9 p7 §9.2.4 ¶9: printed "P = F_1 / A_1 = 1.5 × 10^3 N / pi(5 × 10^-2)^2 m = 1.9 × 10^5 Pa" · transcribed "P = F_1 / A_1 = 1.5 × 10^3 N / pi(5 × 10^-2)^2 m = 1.9 × 10^5 Pa"
- ch 9 p8 §9.3 ¶5: printed "may have different magnitudes but their directions are parallel" · transcribed "may have different magnitudes but their directions are parallel."
- ch 9 p9 §9.4.1 ¶1: printed "y_1" · transcribed "y_1"
- ch 9 p10 §9.4.2 ¶4: printed "due to spining is called" · transcribed "due to spining is called"
- ch 9 p18 §9.6.5 ¶3: printed "It is larger, for a smaller a" · transcribed "It is larger, for a smaller a"
- ch 10 p5 §10.5 ¶10: printed "about 3300 × 10^-6 K^-1" · transcribed "about 3300 × 10^-6 K^-1"
- ch 10 p10 §10.8 ¶8: printed "and pressure 6.11×10^-3 Pa" · transcribed "and pressure 6.11×10^(-3) Pa"
- ch 10 p15 §10.9.1 ¶8: printed "K_2 = 109 W m^-1 K^-1)" · transcribed "K_2 = 109 W m^-1K^-1)"
- ch 10 p16 §10.9.2 ¶3: printed "reveresed" · transcribed "reveresed"
- ch 10 p17 §10.9.4 ¶2: printed "lambda_m" · transcribed "lambda_m"
- ch 10 p18 §10.9.4 ¶5: printed "0.3 × 10^-4 × 0.4 × 5.67 × 10^-8 × (3000)^4" · transcribed "0.3 × 10^-4 × 0.4 × 5.67 × 10^-8 × (3000)^4"
- ch 10 p19 §10.10 ¶6: printed "From Eqs. (10.15) and (10.16)" · transcribed "From Eqs. (10.15) and (10.16)"
- ch 11 p2 §11.2 ¶2: printed "disorderness" · transcribed "disorderness"
- ch 11 p7 §11.6 ¶7: printed "by 1 °C." · transcribed "by 1 °C."
- ch 11 p7 §11.6 ¶7: printed "raise the temperature of 1g of water by 1 °C." · transcribed "raise the temperature of 1 g of water by 1 °C."
- ch 11 p9 §11.8.2 ¶3: printed "mu RT In (V_2 / V_1)" · transcribed "mu RT In (V_2 / V_1)"
- ch 11 p9 §11.8.2 ¶3: printed "mu RT integral(V_1 to V_2) dV / V" · transcribed "mu RT integral(V_1 to V_2) dV / V"
- ch 12 p3 §12.3 ¶5: printed "P = rho R T / M_0" · transcribed "P = rho RT / M_0"
- ch 12 p7 §12.4.1 ¶7: printed "finding v_x^2_bar. Thus" · transcribed "finding v_x^2_bar. Thus"
- ch 12 p7 §12.4.1 ¶7: printed "collisions) will not affect" · transcribed "collisions) will not affect"
- ch 12 p8 §12.4.2 ¶16: printed "v_349 / v_352 = (352/ 349)^(1/2) = 1.0044" · transcribed "v_349 / v_352 = (352/349)^(1/2) = 1.0044"
- ch 12 p8 §12.4.2 ¶16: printed "Hence difference Delta V / V = 0.44 %." · transcribed "Hence difference Delta V / V = 0.44 %."
- ch 12 p8 §12.4.2 ¶17: printed "[235U is the isotope needed" · transcribed "[235U is the isotope needed"
- ch 12 p10 §12.6.1 ¶2: printed "U = (3/2) k_B T × N_A = (3/2) RT (12.27)" · transcribed "U = (3/2) k_B T × N_A = (3/2) RT (12.27)"
- ch 12 p11 §12.6.3 ¶5: printed "= 22.4 litres" · transcribed "= 22.4 litres"
- ch 12 p12 §12.7 ¶4: printed "<v_r>" · transcribed "<v_r>"
- ch 13 p3 §13.2.2 ¶1: printed "rectilinear motion of a steel ball" · transcribed "rectilinear motion of a steel ball"
- ch 13 p6 §13.3 ¶12: printed "instead of zero" · transcribed "instead of zero."
- ch 14 p8 §14.4.1 ¶2: printed "[MLT^-2] / [ML] = [L^2 T^-2]" · transcribed "[MLT^(-2)] / [ML] = [L^2 T^(-2)]"
- ch 14 p13 §14.6.1 ¶2: printed "kx - omega t" · transcribed "kx - omega t"
- ch 14 p15 §14.6.1 ¶14: printed "resonate at v_2, i.e." · transcribed "resonate at v_2, i.e."
- ch 14 p16 §14.7 ¶4: printed "omega_1, omega_2, omega_a >> omega_b, th" · transcribed "omega_1, omega_2, omega_a >> omega_b, th"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 12 p10 §12.6.1 ¶1: transcribed "C_v (monatomic gas) = dU/dT = (3/2) RT" (printed "C_v (monatomic gas) = dU/dT = (3/2) R")

## set aside by code: a heading or a caption listed as omitted text

- ch 9 p3: "Fig. 9.2 Proof of Pascal's law. ABC-DEF is an element of the interior of a fluid at rest."
- ch 9 p6: "9.2.4 Hydraulic Machines"
- ch 9 p7: "9.3 STREAMLINE FLOW"
- ch 9 p9: "Fig. 9.9 The flow of an ideal fluid in a pipe of varying cross section."
- ch 9 p12: "Fig 9.12 (a) A layer of liquid sandwiched between two parallel glass plates"
- ch 9 p13: "9.6 SURFACE TENSION"
- ch 9 p16: "9.6.3 Angle of Contact"
- ch 10 p3: "10.5 THERMAL EXPANSION"
- ch 10 p11: "Fig. 10.11 Boiling process."
- ch 10 p13: "10.9 HEAT TRANSFER"
- ch 10 p17: "Fig. 10.18: Energy emitted versus wavelength"
- ch 11 p2: "11.2 THERMAL EQUILIBRIUM"
- ch 11 p5: "11.5 FIRST LAW OF THERMODYNAMICS"
- ch 11 p8: "11.8 THERMODYNAMIC PROCESSES"
- ch 11 p8: "11.8.1 Quasi-static process"
- ch 11 p14: "Fig. 11.10 An irreversible engine (I) coupled to a reversible refrigerator (R)."
- ch 12 p3: "Fig.12.1 Real gases approach ideal gas behaviour at low pressures and high temperatures."
- ch 12 p6: "12.4.1 Pressure of an Ideal Gas"
- ch 12 p9: "12.5 LAW OF EQUIPARTITION OF ENERGY"
- ch 13 p2: "13.2.1 Period and frequency"
- ch 13 p3: "13.2.2 Displacement"
- ch 13 p6: "Fig. 13.8 Plots of Eq. (13.4) for phi = 0 for two different periods."
- ch 13 p9: "Fig. 13.13 Displacement, velocity and acceleration"
- ch 13 p12: "13.8 The Simple Pendulum"
- ch 14 p2: "Fig. 14.1 A collection of springs connected to each other."
- ch 14 p5: "Fig. 14.5 The meaning of standard symbols in Eq. (14.2)"
- ch 14 p7: "14.4 THE SPEED OF A TRAVELLING WAVE"
- ch 14 p8: "14.4.1 Speed of a Transverse Wave on Stretched String"
- ch 14 p8: "14.4.2 Speed of a Longitudinal Wave (Speed of Sound)"
- ch 14 p9: "Table 14.1 Speed of Sound in some Media"
- ch 14 p17: "Fig. 14.16 Superposition of two harmonic waves, one of frequency 11 Hz (a), and the other of frequency 9Hz (b), giving rise to beats of frequency 2 Hz, as shown in (c)."
flags set aside by the founder's rulings in ncert-corrections.yaml: 27
verdicts recorded on rows: 682

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 8 | 64 | 64 | 64 | 0 | 0 | 0 | 0 | 100.0% |
| 9 | 127 | 127 | 126 | 1 | 0 | 0 | 1 | 98.4% |
| 10 | 107 | 107 | 107 | 0 | 0 | 0 | 0 | 100.0% |
| 11 | 92 | 92 | 92 | 0 | 0 | 0 | 0 | 100.0% |
| 12 | 95 | 95 | 94 | 0 | 0 | 1 | 0 | 98.9% |
| 13 | 94 | 94 | 94 | 0 | 0 | 0 | 0 | 100.0% |
| 14 | 103 | 103 | 103 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): 679 of 682 paragraphs, 99.6%
not in the clean share, adjudicate before recording it: 74 page-level start flags, 3 numbered equations the print carries that the rows do not, 7 passages no row carries

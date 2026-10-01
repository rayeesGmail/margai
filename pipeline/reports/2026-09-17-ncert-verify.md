# margai-pipeline ncert verify

- run: 2026-09-17 22:58 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 9 | 6 | 2 | 7 | 2 | 0 | 0 | 0 |
| 2 | 8 | 4 | 3 | 7 | 2 | 1 | 0 | 0 |
| 3 | 16 | 10 | 5 | 14 | 2 | 2 | 0 | 1 |
| 4 | 17 | 15 | 1 | 14 | 2 | 0 | 0 | 0 |
| 5 | 15 | 12 | 2 | 15 | 3 | 0 | 0 | 0 |
| 6 | 31 | 21 | 9 | 23 | 1 | 2 | 0 | 6 |
| 7 | 12 | 6 | 5 | 5 | 0 | 0 | 0 | 1 |

## where rows start against where the print starts paragraphs

- ch 1 p2: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §1.2 ¶4 "In SI, there are seven base units" · §1.2 ¶5 "* The values mentioned here need not"
- ch 1 p3: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.3 ¶3 "(1) For example, the length 2.308 cm"
- ch 1 p4: 17 rows start here, the print starts 8 paragraphs — rows the print does not start: §1.3 ¶5 "This shows that the location of decimal" · §1.3 ¶6 "The example gives the following rules :" · §1.3 ¶7 "• All the non-zero digits are significant." · §1.3 ¶8 "• All the zeros between two non-zero" · §1.3 ¶9 "• If the number is less than" · §1.3 ¶10 "• The terminal or trailing zero(s) in" · §1.3 ¶11 "[Thus 123 m = 12300 cm =" · §1.3 ¶12 "• The trailing zero(s) in a number" · §1.3 ¶13 "(2) There can be some confusion regarding" · §1.3 ¶14 "(3) To remove such ambiguities in determining" · §1.3 ¶17 "(4) The scientific notation is ideal for" · §1.3 ¶18 "• For a number greater than 1," · §1.3 ¶19 "• For a number with a decimal," · §1.3 ¶20 "(5) The digit 0 conventionally put on" · §1.3 ¶21 "(6) The multiplying or dividing factors which" · printed starts no row begins with: "decimal point is, if at all." · "zeroes are not significant]." · "number without a decimal point are" · "see the next observation." · "significant figures each.]" · "decimal, the trailing zero(s) are not"
- ch 1 p5: 10 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.3.1 ¶1 "The result of a calculation involving approximate" · §1.3.1 ¶2 "(1) In multiplication or division, the final" · §1.3.1 ¶5 "(2) In addition or subtraction, the final" · §1.3.1 ¶8 "Note that we should not use the" · §1.3.2 ¶1 "The result of computation with approximate numbers,"
- ch 1 p6: 13 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.3.2 ¶5 "Example 1.2 5.74 g of a substance" · §1.3.3 ¶1 "The rules for determining the uncertainty or" · §1.3.3 ¶2 "(1) If the length and breadth of" · §1.3.3 ¶4 "(2) If a set of experimental data" · §1.3.3 ¶5 "However, if data are subtracted, the number" · §1.3.3 ¶6 "For example, 12.9 g – 7.06 g," · §1.3.3 ¶7 "(3) The relative error of a value" · §1.3.3 ¶8 "For example, the accuracy in measurement of" · §1.3.3 ¶9 "Finally, remember that intermediate results in a" · printed starts no row begins with: "keeping the significant figures in view." · "in the Results of Arithmetic"
- ch 1 p8: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.6.1 ¶1 "The magnitudes of physical quantities may be"
- ch 1 p9: 14 rows start here, the print starts 6 paragraphs — rows the print does not start: §1.6.1 ¶8 "The dimensions of RHS are [M][L T^-2]" · §1.6.1 ¶9 "The dimensions of LHS and RHS are" · §1.6.2 ¶1 "The method of dimensions can sometimes be" · §1.6.2 ¶5 "By considering dimensions on both sides, we" · §1.6.2 ¶6 "On equating the dimensions on both sides," · §1.6.2 ¶7 "Note that value of constant k can" · §1.6.2 ¶8 "Actually, k = 2pi so that T" · §1.6.2 ¶9 "Dimensional analysis is very useful in deducing"
- ch 2 p2: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.2 ¶4 "Now, we decrease the value of Delta"
- ch 2 p3: 9 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "measured in seconds. What is its"
- ch 2 p4: 5 rows start here, the print starts 12 paragraphs — rows the print does not start: §2.3 ¶4 "Instantaneous acceleration is defined in the same" · printed starts no row begins with: "(a) An object is moving in" · "with a positive acceleration." · "(b) An object is moving in" · "with a negative acceleration." · "(c) An object is moving in" · "with a negative acceleration." · "(d) An object is moving in" · "same negative acceleration."
- ch 2 p5: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.3 ¶10 "Note that the x-t, v-t, and a-t" · §2.4 ¶2 "As explained in the previous section, the" · §2.4 ¶3 "Equations (2.7a) and (2.7b) mean that the"
- ch 2 p6: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §2.4 ¶4 "This equation can also be obtained by"
- ch 2 p7: 11 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.4 ¶13 "FIRST METHOD : In the first method," · §2.4 ¶14 "This is the time in going from" · §2.4 ¶15 "Solving, we get t_2 = 3 s" · §2.4 ¶16 "Therefore, the total time taken by the" · §2.4 ¶17 "SECOND METHOD : The total time taken" · §2.4 ¶18 "Solving this quadratic equation for t, we" · §2.4 ¶19 "Note that the second method is better" · §2.4 ¶22 "We assume that the motion is in" · §2.4 ¶23 "These equations give the velocity and the"
- ch 2 p8: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.4 ¶26 "Using this equation, we can calculate the" · printed starts no row begins with: "in terms of v anda."
- ch 3 p2: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.2.1 ¶1 "To describe the position of an object" · §3.2.2 ¶1 "Two vectors A and B are said" · §3.2.2 ¶3 "* Addition and subtraction of scalars make" · §3.2.2 ¶4 "** In our study, vectors do not"
- ch 3 p3: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.3 ¶2 "For example, if A is multiplied by" · §3.3 ¶4 "Multiplying a given vector A by negative"
- ch 3 p4: 6 rows start here, the print starts 1 paragraphs — rows the print does not start: §3.4 ¶2 "The addition of vectors also obeys the" · §3.4 ¶3 "What is the result of adding two" · §3.4 ¶5 "What is the physical meaning of a" · §3.4 ¶6 "Subtraction of vectors can be defined in" · §3.4 ¶7 "It is shown in Fig 3.5. The"
- ch 3 p6: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §3.5 ¶3 "Since these are unit vectors, we have" · §3.5 ¶5 "We can now resolve a vector A" · §3.5 ¶6 "This is represented in Fig. 3.9(c). The" · §3.5 ¶10 "If A and theta are given, A_x" · printed starts no row begins with: "with the x-axis; or"
- ch 3 p7: 9 rows start here, the print starts 5 paragraphs — rows the print does not start: §3.5 ¶12 "In general, we have A = A_x" · §3.6 ¶2 "Since vectors obey the commutative and associative" · §3.6 ¶4 "In three dimensions, we have A =" · §3.6 ¶7 "* Note that angles alpha, beta, and"
- ch 3 p8: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.6 ¶11 "We can obtain the magnitude of R"
- ch 3 p9: 6 rows start here, the print starts 0 paragraphs — rows the print does not start: §3.7.1 ¶1 "The position vector r of a particle" · §3.7.1 ¶2 "Suppose a particle moves along the curve" · §3.7.1 ¶3 "We can write Eq. (3.25) in a" · §3.7.1 ¶4 "The average velocity (v_bar) of an object" · §3.7.1 ¶5 "Since v_bar = Delta r / Delta" · §3.7.1 ¶6 "The meaning of the limiting process can"
- ch 3 p10: 7 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.7.1 ¶8 "So, if the expressions for the coordinates" · §3.7.1 ¶10 "The average acceleration a_bar of an object" · §3.7.1 ¶11 "The acceleration (instantaneous acceleration) is the limiting" · §3.7.1 ¶12 "As in the case of velocity, we" · §3.7.1 ¶13 "* In terms of x and y,"
- ch 3 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.7.1 ¶14 "Note that in one dimension, the velocity" · §3.7.1 ¶16 "Answer v(t) = dr/dt = d/dt (3.0" · §3.8 ¶2 "Let us now find how the position" · printed starts no row begins with: "where t is in seconds and"
- ch 3 p12: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §3.8 ¶3 "One immediate interpretation of Eq.(3.34b) is that" · §3.9 ¶3 "After the object has been projected, the" · §3.9 ¶4 "The components of initial velocity v_o are"
- ch 3 p13: 6 rows start here, the print starts 0 paragraphs — rows the print does not start: §3.9 ¶5 "If we take the initial position to" · §3.9 ¶6 "What is the shape of the path" · §3.9 ¶7 "Now, since g, theta_o and v_o are" · §3.9 ¶8 "How much time does the projectile take" · §3.9 ¶9 "The maximum height h_m reached by the" · §3.9 ¶10 "The horizontal distance travelled by a projectile"
- ch 3 p14: 10 rows start here, the print starts 9 paragraphs — rows the print does not start: §3.9 ¶11 "Equation (3.42a) shows that for a given"
- ch 3 p15: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.10 ¶4 "* In the limit Delta t ->" · printed starts no row begins with: "Let the angle between position vectors"
- ch 3 p16: 7 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.10 ¶6 "Now, if the distance travelled by the" · §3.10 ¶7 "We can express centripetal acceleration a_c in" · §3.10 ¶8 "The time taken by an object to" · §3.10 ¶11 "The direction of velocity v is along"
- ch 4 p2: 8 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "(iii) Motion on a horizontal plane"
- ch 4 p3: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.4 ¶3 "The state of rest or uniform linear" · §4.4 ¶4 "Two kinds of situations are encountered in"
- ch 4 p5: 9 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.5 ¶2 "Momentum of a body is defined to" · §4.5 ¶3 "Momentum is clearly a vector quantity. The" · §4.5 ¶4 "• Suppose a light-weight vehicle (say a" · §4.5 ¶5 "• If two stones, one light and" · §4.5 ¶6 "• Speed is another important parameter to" · §4.5 ¶7 "• A seasoned cricketer catches a cricket" · §4.5 ¶8 "• Observations confirm that the product of" · §4.5 ¶9 "• In the preceding observations, the vector" · printed starts no row begins with: "if they are moving with the" · "on its motion." · "force that needs to be applied." · "second law of motion."
- ch 4 p6: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.5 ¶11 "The rate of change of momentum of" · §4.5 ¶12 "Thus, if under the action of a" · §4.5 ¶13 "The unit of force has not been" · printed starts no row begins with: "law is obviously consistent with the"
- ch 4 p7: 10 rows start here, the print starts 10 paragraphs — rows the print does not start: §4.5 ¶21 "The retarding force, by the second law" · §4.5 ¶22 "The actual resistive force, and therefore, retardation" · §4.5 ¶26 "Impulse We sometimes encounter examples where a" · printed starts no row begins with: "of velocity remains unchanged (Fig. 4.5)." · "mass m is described by y" · "the force acting on the particle."
- ch 4 p8: 13 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.5 ¶27 "A large force acting for a short" · §4.6 ¶2 "Thus, according to Newtonian mechanics, force never" · §4.6 ¶3 "To every action, there is always an" · §4.6 ¶4 "Newton’s wording of the third law is" · §4.6 ¶6 "Forces always occur in pairs. Force on" · printed starts no row begins with: "force on the body B by"
- ch 4 p10: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.7 ¶3 "The total momentum of an isolated system" · §4.7 ¶4 "An important example of the application of" · §4.8 ¶3 "In other words, the resultant of any" · §4.8 ¶6 "* Equilibrium of a body requires not" · printed starts no row begins with: "the mass of the rope."
- ch 4 p11: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §4.8 ¶10 "Note the answer does not depend on" · §4.9 ¶5 "* We are not considering, for simplicity,"
- ch 4 p12: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.9.1 ¶1 "Let us return to the example of"
- ch 4 p13: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §4.9.1 ¶9 "Therefore, tan theta_max = mu_s or theta_max" · §4.9.1 ¶10 "For theta_max = 15°, mu_s = tan"
- ch 4 p14: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.9.1 ¶13 "A body like a ring or a" · §4.9.1 ¶16 "In many practical situations, however, friction is"
- ch 4 p15: 15 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.10 ¶2 "where m is the mass of the" · §4.10 ¶4 "Motion of a car on a level" · §4.10 ¶8 "As there is no acceleration in the" · §4.10 ¶9 "The centripetal force required for circular motion" · §4.10 ¶10 "which is independent of the mass of" · §4.10 ¶11 "Motion of a car on a banked" · §4.10 ¶12 "The centripetal force is provided by the" · §4.10 ¶13 "But f ≤ mu_s N" · §4.10 ¶14 "Thus to obtain v_max we put f" · §4.10 ¶15 "Then Eqs. (4.19a) and (4.19b) become N"
- ch 4 p16: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §4.10 ¶16 "For mu_s = 0 in Eq. (4.21" · printed starts no row begins with: "(b) maximum permissible speed to avoid"
- ch 4 p17: 14 rows start here, the print starts 14 paragraphs — rows the print does not start: §4.11 ¶7 "The following example illustrates the above procedure" · §4.11 ¶9 "Answer" · §4.11 ¶12 "By the third law, the action of" · §4.11 ¶13 "Action-reaction pairs" · §4.11 ¶14 "For (a): (i) the force of gravity" · §4.11 ¶15 "For (b): (i) the force of gravity" · printed starts no row begins with: "links, supports, etc." · "as one system." · "under consideration is without a net" · "laws of motion." · "be shown as –F." · "(ii) the force on the floor"
- ch 5 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.1.1 ¶1 "We have learnt about vectors and their"
- ch 5 p2: 8 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.1.1 ¶2 "From Eq. (5.1a), we have A.B =" · §5.1.1 ¶4 "The proofs of the above equations are" · §5.1.1 ¶5 "For unit vectors i_hat, j_hat, k_hat we" · §5.1.1 ¶6 "( i ) A.A = A_x A_x" · §5.1.1 ¶7 "(ii) A.B = 0, if A and" · §5.1.1 ¶8 "Example 5.1 Find the angle between force" · §5.1.1 ¶9 "Answer F.d = F_x d_x + F_y" · printed starts no row begins with: "u Example 5.1 Find the angle" · "projection of F on d."
- ch 5 p3: 9 rows start here, the print starts 9 paragraphs — rows the print does not start: §5.2 ¶2 "Once again multiplying both sides by m/2" · printed starts no row begins with: "by the unknown resistive force?"
- ch 5 p4: 15 rows start here, the print starts 16 paragraphs — rows the print does not start: §5.3 ¶2 "The work done by the force is" · §5.3 ¶3 "We see that if there is no" · §5.3 ¶4 "No work is done if :" · §5.4 ¶2 "Kinetic energy is a scalar quantity. The" · printed starts no row begins with: "does no work on the load" · "may undergo a large displacement." · "perpendicular. This is so since, for" · "(b) How much work does the" · "to a halt in accordance with"
- ch 5 p5: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.4 ¶5 "The speed is reduced by approximately 68%" · §5.5 ¶3 "This is illustrated in Fig. 5.3(a). Adding" · §5.5 ¶4 "If the displacements are allowed to approach"
- ch 5 p6: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.5 ¶6 "Answer The plot of the applied force" · §5.5 ¶7 "The work done by the woman is" · §5.5 ¶8 "The work done by the frictional force" · printed starts no row begins with: "W → area of the rectangle"
- ch 5 p7: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §5.6 ¶3 "Example 5.6 A block of mass m" · §5.6 ¶5 "Here, note that ln is a symbol" · §5.7 ¶4 "* The variation of g with height" · printed starts no row begins with: "moving on a horizontal surface with"
- ch 5 p8: 10 rows start here, the print starts 7 paragraphs — rows the print does not start: §5.7 ¶5 "The work done by a conservative force" · §5.8 ¶3 "• A force F(x) is conservative if" · §5.8 ¶4 "• The work done by the conservative" · §5.8 ¶5 "• A third definition states that the" · printed starts no row begins with: "which depends on the end points."
- ch 5 p9: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §5.8 ¶9 "The constant force is a special case" · §5.8 ¶13 "Thus, at C E = (1/2) mv_c^2" · printed starts no row begins with: "mg = [Newton’s Second Law] (5.14)"
- ch 5 p10: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.8 ¶16 "At point C, the string becomes slack" · §5.9 ¶2 "Suppose that we pull the block outwards"
- ch 5 p11: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.9 ¶4 "Thus the work done by the spring" · §5.9 ¶6 "Example 5.8 To simulate car accidents, auto" · printed starts no row begins with: "maximum compression of the spring ?"
- ch 5 p12: 13 rows start here, the print starts 9 paragraphs — rows the print does not start: §5.9 ¶9 "We note that we have idealised the" · §5.9 ¶10 "We conclude this section by making a" · §5.9 ¶18 "Now mu mg = 0.5 × 10^3" · §5.9 ¶19 "where we take the positive square root" · §5.9 ¶20 "which, as expected, is less than the" · printed starts no row begins with: "It is set according to convenience."
- ch 5 p13: 13 rows start here, the print starts 8 paragraphs — rows the print does not start: §5.10 ¶3 "The instantaneous power is defined as the" · §5.10 ¶4 "The work dW done by a force" · §5.10 ¶8 "Our electricity bills carry the energy consumption" · §5.11 ¶2 "Consider two masses m_1 and m_2. The" · §5.11 ¶3 "The masses m_1 and m_2 fly-off in"
- ch 5 p14: 10 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.11.1 ¶1 "In all collisions the total linear momentum" · §5.11.1 ¶2 "The above conclusion is true even though" · §5.11.2 ¶1 "Consider first a completely inelastic collision in" · §5.11.2 ¶2 "The loss in kinetic energy on collision" · §5.11.2 ¶4 "Substituting this in Eq. (5.23), we obtain" · §5.11.2 ¶5 "Thus, the 'unknowns' {v_1f, v_2f} are obtained" · §5.11.2 ¶6 "Case I : If the two masses" · §5.11.2 ¶7 "Case II : If one mass dominates,"
- ch 5 p15: 11 rows start here, the print starts 6 paragraphs — rows the print does not start: §5.11.2 ¶10 "The fractional kinetic energy lost is f_1" · §5.11.2 ¶11 "One can also verify this result by" · §5.11.2 ¶12 "For deuterium m_2 = 2m_1 and we" · §5.11.3 ¶1 "Fig. 5.10 also depicts the collision of" · §5.11.3 ¶2 "If, further the collision is elastic, (1/2)" · §5.11.3 ¶3 "We obtain an additional equation. That still" · printed starts no row begins with: "motion are not important. Obtain θ"
- ch 6 p1: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.1 ¶2 "Any real body which we encounter in" · §6.1.1 ¶1 "Let us try to explore this question"
- ch 6 p5: 13 rows start here, the print starts 12 paragraphs — rows the print does not start: §6.2 ¶5 "If we have n particles of masses"
- ch 6 p6: 10 rows start here, the print starts 8 paragraphs — rows the print does not start: §6.2 ¶20 "Often we have to calculate the centre" · §6.2 ¶25 "Answer"
- ch 6 p7: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.2 ¶27 "Example 6.2 Find the centre of mass" · §6.2 ¶30 "Example 6.3 Find the centre of mass" · §6.2 ¶32 "Hence X = [1(1/2) + 1(3/2) +" · printed starts no row begins with: "u Example 6.2 Find the centre" · "u Example 6.3 Find the centre"
- ch 6 p9: 8 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "Comparing this with Eq. (6.8)" · "Comparing Eq.(6.16) and Eq. (6.11)," · "This is the statement of Newton’s"
- ch 6 p11: 18 rows start here, the print starts 8 paragraphs — rows the print does not start: §6.5 ¶3 "A vector product of two vectors a" · §6.5 ¶8 "A simpler version of the right hand" · §6.5 ¶10 "Because of the cross (×) used to" · §6.5 ¶11 "• Note that scalar product of two" · §6.5 ¶12 "The vector product, however, is not commutative," · §6.5 ¶13 "The magnitude of both a × b" · §6.5 ¶14 "• Another interesting property of a vector" · §6.5 ¶15 "Thus, a × b does not change" · §6.5 ¶16 "• Both scalar and vector products are" · §6.5 ¶17 "• We may write c = a" · §6.5 ¶18 "(i) a × a = 0 (0" · printed starts no row begins with: "angle between the two vectors."
- ch 6 p12: 10 rows start here, the print starts 8 paragraphs — rows the print does not start: §6.5 ¶24 "Now, a × b = (a_x i_hat" · §6.5 ¶26 "Example 6.4 Find the scalar and vector" · §6.5 ¶27 "Answer a·b = (3 i_hat − 4" · printed starts no row begins with: "u Example 6.4 Find the scalar"
- ch 6 p14: 10 rows start here, the print starts 9 paragraphs — rows the print does not start: §6.6 ¶12 "Now omega × r = omega ×" · §6.6.1 ¶1 "You may have noticed that we are" · §6.7.1 ¶1 "We have learnt that the motion of" · printed starts no row begins with: "But ω × OC = 0" · "(v) in translational motion, we have"
- ch 6 p15: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.7.2 ¶1 "Just as the moment of a force"
- ch 6 p16: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.7.2 ¶8 "Because of this dr/dt × p =" · §6.7.2 ¶10 "To get the total angular momentum of"
- ch 6 p17: 11 rows start here, the print starts 10 paragraphs — rows the print does not start: §6.7.2 ¶19 "If tau_ext = 0, Eq. (6.28b) reduces" · §6.7.2 ¶23 "Answer Here r = i_hat – j_hat" · printed starts no row begins with: "remains constant throughout the motion."
- ch 6 p18: 14 rows start here, the print starts 15 paragraphs — rows the print does not start: §6.8 ¶4 "(1) the total force, i.e. the vector" · §6.8 ¶5 "If the total force on the body" · §6.8 ¶6 "(2) The total torque, i.e. the vector" · printed starts no row begins with: "forces, on the rigid body is" · "of the body." · "torques on the rigid body is" · "∑ ix , ∑ iy and"
- ch 6 p20: 12 rows start here, the print starts 12 paragraphs — rows the print does not start: §6.8 ¶19 "Answer Consider a couple as shown in" · §6.8.1 ¶1 "An ideal lever is essentially a light" · printed starts no row begins with: "Consider a couple as shown in" · "The moment of the couple, therefore,"
- ch 6 p21: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §6.8.2 ¶1 "Many of you may have the experience" · printed starts no row begins with: "m r = 0. Remember that"
- ch 6 p22: 12 rows start here, the print starts 13 paragraphs — rows the print does not start: §6.8.2 ¶9 "Answer Figure 6.26 shows the rod AB," · §6.8.2 ¶17 "Answer" · printed starts no row begins with: "Figure 6.26 shows the rod AB," · "Note W and W act vertically" · "From (iii) and (iv), R ="
- ch 6 p23: 11 rows start here, the print starts 14 paragraphs — printed starts no row begins with: "The ladder AB is 3 m" · "where m is the mass of" · "With this definition,"
- ch 6 p24: 7 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "Thus, for the pair of masses,"
- ch 6 p25: 1 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "radius R disc at centre" · "(6) Hollow cylinder, Axis of cylinder" · "(7) Solid cylinder, Axis of cylinder"
- ch 6 p26: 12 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.10 ¶5 "where x_0 = initial displacement and v_0" · §6.10 ¶7 "where theta_0 = initial angular displacement of" · §6.10 ¶8 "Example 6.10 Obtain Eq. (6.36) from first" · §6.10 ¶9 "Answer The angular acceleration is uniform, hence" · §6.10 ¶10 "With the definition of omega = d" · §6.10 ¶11 "Example 6.11 The angular speed of a" · §6.10 ¶12 "Answer" · §6.10 ¶13 "(i) We shall use omega = omega_0" · printed starts no row begins with: "u Example 6.10 Obtain Eq. (6.36)" · "u Example 6.11 The angular speed" · "ω = initial angular speed in"
- ch 6 p27: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §6.11 ¶3 "(1) We need to consider only those" · §6.11 ¶4 "(2) We need to consider only those" · printed starts no row begins with: "Similarly ω = final angular speed" · "not be taken into account." · "not be taken into account."
- ch 6 p29: 15 rows start here, the print starts 13 paragraphs — rows the print does not start: §6.11 ¶15 "Since alpha = d omega / dt," · §6.11 ¶24 "Answer" · §6.11 ¶25 "(a) We use I alpha = tau" · §6.11 ¶28 "(d) The answers are the same, i.e." · printed starts no row begins with: "2m of the cord is unwound." · "wheel starts from rest."
- ch 6 p30: 15 rows start here, the print starts 13 paragraphs — rows the print does not start: §6.12 ¶5 "The magnitude of the linear velocity v" · §6.12 ¶9 "We denote by L_perp and L_z the"
- ch 6 p31: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.12 ¶17 "Thus, for rotation about a fixed axis," · §6.12 ¶18 "If the moment of inertia I does" · §6.12.1 ¶1 "We are now in a position to"
- ch 7 p3: 8 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "equal times to traverse BAC and"
- ch 7 p4: 11 rows start here, the print starts 9 paragraphs — rows the print does not start: §7.3 ¶3 "Every body in the universe attracts every" · §7.3 ¶8 "The total force on m_1 is F_1"
- ch 7 p5: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §7.3 ¶16 "(1) The force of attraction between a" · §7.3 ¶17 "(2) The force of attraction due to" · §7.4 ¶1 "The value of the gravitational constant G" · printed starts no row begins with: "this force works out to be" · "the Universal law of gravitation can"
- ch 7 p11: 11 rows start here, the print starts 10 paragraphs — rows the print does not start: §7.9 ¶7 "Which is approximately 85 minutes."
- ch 7 p12: 14 rows start here, the print starts 13 paragraphs — rows the print does not start: §7.9 ¶17 "Answer Given k = 10^-13 s^2 m^-3"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 3 p3: the print starts "The factor λ by which a" where §3.3 ¶5 starts "The factor lambda by which a vector" — the layer dropped the line's math
- ch 6 p5: the print starts "Here M = m is the" where §6.2 ¶11 starts "Here M = sum m_i is the" — the layer dropped the line's math
- ch 6 p14: the print starts "The vector ω CP is perpendicular" where §6.6 ¶13 starts "The vector omega × CP is perpendicular" — the layer dropped the line's math
- ch 6 p14: the print starts "Thus, ω × r is a" where §6.6 ¶14 starts "Thus, omega × r is a vector" — the layer dropped the line's math
- ch 6 p17: the print starts "Since τ = τ , it" where §6.7.2 ¶16 starts "Since tau = sum tau_i, it follows" — the layer dropped the line's math
- ch 6 p28: the print starts "In time ∆t, the point moves" where §6.11 ¶6 starts "In time Delta t, the point moves" — the layer dropped the line's math
- ch 6 p29: the print starts "(c) Let ω be the final" where §6.11 ¶27 starts "(c) Let omega be the final angular" — the layer dropped the line's math
- ch 7 p7: the print starts "For , using binomial expression," where §7.6 ¶4 starts "For h/R_E << 1, using binomial expression," — the layer dropped the line's math

## joins across page breaks against the print

- ch 1 §1.3 ¶5 starts a paragraph at the top of p4, but the print continues p3's: "This shows that the location of"
- ch 1 §1.6.1 ¶8 starts a paragraph at the top of p9, but the print continues p8's: "The dimensions of RHS are"
- ch 2 §2.3 ¶4 starts a paragraph at the top of p4, but the print continues p3's: "Instantaneous acceleration is defined in the"
- ch 2 §2.4 ¶4 starts a paragraph at the top of p6, but the print continues p5's: "This equation can also be obtained"
- ch 3 §3.7.1 ¶1 starts a paragraph at the top of p9, but the print continues p8's: "3.7.1 Position Vector and Displacement"
- ch 3 §3.7.1 ¶14 starts a paragraph at the top of p11, but the print continues p10's: "Note that in one dimension, the"
- ch 4 §4.5 ¶27 starts a paragraph at the top of p8, but the print continues p7's: "A large force acting for a"
- ch 4 §4.7 ¶3 starts a paragraph at the top of p10, but the print continues p9's: "The total momentum of an isolated"
- ch 5 §5.3 ¶2 starts a paragraph at the top of p4, but the print continues p3's: "The work done by the force"
- ch 5 §5.8 ¶14 runs from p9 onto p10, but p10 opens a new paragraph: "(iii) The ratio of the kinetic"
- ch 5 §5.11.1 ¶1 starts a paragraph at the top of p14, but the print continues p13's: "5.11.1 Elastic and Inelastic Collisions"
- ch 6 §6.8.2 ¶17 runs from p22 onto p23, but p23 opens a new paragraph: "The ladder AB is 3 m"

## figure_refs against the paragraph and the chapter's captions

- ch 2 §2.3 ¶5: the paragraph mentions fig 2.4, which figure_refs does not carry
- ch 3 §3.2.2 ¶2: the paragraph mentions fig 3.2, which figure_refs does not carry
- ch 3 §3.9 ¶5: the paragraph mentions fig 3.17, which figure_refs does not carry
- ch 6 §6.9 ¶9: the paragraph mentions table 6.1, which figure_refs does not carry
- ch 6 §6.11 ¶5: the paragraph mentions fig 6.30, which figure_refs does not carry

## numbered equations the print carries that the rows do not

none
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 ff6e0b61a174bf0b7e2965d13f7779ce5031a1f057158c3ec969570b7a11d49d
no model was called: add --read-pages for the second read

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 7 p3 §7.2 ¶7: printed "Delta A / Delta t =½ (r × p)/m" · transcribed "Delta A / Delta t = ½ (r × p)/m"
- ch 7 p3 §7.2 ¶9: printed "traverse BAC and CPB" · transcribed "traverse BAC and CPB"
- ch 7 p3 §7.3 ¶1: printed "R_m was already known then to be about 3.84 × 10^8 m" · transcribed "R_m was already known then to be about 3.84 × 10^8 m"
- ch 7 p5 §7.3 ¶12: printed "F_GB = Gm(2m) / 1 (-i_hat cos 30° - j_hat sin 30°)" · transcribed "F_GB = Gm(2m) / 1 (-i_hat cos 30° - j_hat sin 30°)"
- ch 7 p6 §7.5 ¶3: printed "radius r for which" · transcribed "radius r for which"
- ch 7 p6 §7.5 ¶3: printed "M_r is concentrated" · transcribed "M_r is concentrated"
- ch 7 p6 §7.5 ¶3: printed "F = Gm(M_r) / r^2" · transcribed "F = Gm (M_r) / r^2"
- ch 7 p9 §7.7 ¶12: printed "− 4√2 G m / l" · transcribed "− 4 sqrt(2) G m / l"
- ch 7 p9 §7.8 ¶2: printed "E (infinity) = W_1 + mV_f^2 / 2" · transcribed "E (infinity) = W_1 + mV_f^2 / 2"
- ch 7 p10 §7.8 ¶13: printed "E_i = (1/2) m v^2 – G M m / R – 4 G M m / 5 R" · transcribed "E_i = (1/2) m v^2 – G M m / R – 4 G M m / 5 R ."
- ch 7 p10 §7.8 ¶14: printed "E_N = – G M m / 2 R – 4 G M m / 4 R" · transcribed "E_N = – G M m / 2 R – 4 G M m / 4 R ."
- ch 7 p10 §7.8 ¶15: printed "(1/2) v^2 – GM / R – 4GM / 5R = – GM / 2R – GM / R" · transcribed "(1/2) v^2 – GM / R – 4GM / 5R = – GM / 2R – GM / R"
- ch 7 p11 §7.9 ¶9: printed "6.67 × 10^-11 × (459 × 60)^2" · transcribed "6.67 × 10^(-11) × (459 × 60)^2"
- ch 7 p12 §7.10 ¶1: printed "K.E" · transcribed "K.E"
- ch 7 p12 §7.10 ¶1: printed "Gm M_E / (2(R_E + h))" · transcribed "Gm M_E / (2(R_E + h))"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 4
verdicts recorded on rows: 106

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 84 | 0 | 0 | 0 | 0 | 0 | 2 | — (84 rows without a verdict) |
| 2 | 53 | 0 | 0 | 0 | 0 | 0 | 3 | — (53 rows without a verdict) |
| 3 | 101 | 0 | 0 | 0 | 0 | 0 | 4 | — (101 rows without a verdict) |
| 4 | 139 | 0 | 0 | 0 | 0 | 0 | 2 | — (139 rows without a verdict) |
| 5 | 128 | 0 | 0 | 0 | 0 | 0 | 3 | — (128 rows without a verdict) |
| 6 | 281 | 0 | 0 | 0 | 0 | 0 | 3 | — (281 rows without a verdict) |
| 7 | 106 | 106 | 106 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): not computed — some rows have no verdict yet
not in the clean share, adjudicate before recording it: 85 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

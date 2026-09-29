# margai-pipeline ncert verify

- run: 2026-09-29 07:38 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 5 chapters of chem12-part2 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 0a48576040e00e8dbee64bb03d7e1a6c1c67ac5f4514849f506ed438acdcc0f6

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 6 | 30 | 23 | 6 | 28 | 0 | 0 | 0 | 0 |
| 7 | 28 | 21 | 6 | 28 | 0 | 0 | 0 | 0 |
| 8 | 28 | 21 | 6 | 27 | 0 | 0 | 0 | 0 |
| 9 | 19 | 14 | 4 | 17 | 1 | 0 | 0 | 0 |
| 10 | 21 | 16 | 4 | 20 | 0 | 0 | 0 | 2 |

## where rows start against where the print starts paragraphs

- ch 6 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §6 ¶1 "The replacement of hydrogen atom(s) in an"
- ch 6 p2: 7 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.1 ¶1 "Haloalkanes and haloarenes may be classified as" · §6.1.1 ¶1 "These may be classified as mono, di," · §6.1.2 ¶1 "This class includes" · §6.1.2 ¶3 "(b) Allylic halides These are the compounds" · §6.1.2 ¶4 "(c) Benzylic halides These are the compounds" · printed starts no row begins with: "Basis of compounds depending on whether"
- ch 6 p3: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.1.3 ¶1 "This class includes:" · §6.1.3 ¶2 "(a) Vinylic halides These are the compounds" · §6.1.3 ¶3 "(b) Aryl halides These are the compounds" · §6.2 ¶1 "Having learnt the classification of halogenated compounds," · printed starts no row begins with: "These are the compounds in which" · "a sp -hybridised carbon atom of"
- ch 6 p4: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.2 ¶3 "Some common examples of halocompounds are mentioned" · §6.2 ¶5 "Solution CH_3CH_2CH_2CH_2CH_2Br 1-Bromopentane (1°) CH_3CH_2CH_2CH(Br)CH_3 2-Bromopentane (2°)" · printed starts no row begins with: "Table 6.1: Common and IUPAC Names"
- ch 6 p5: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.2 ¶6 "Example 6.2 Write IUPAC names of the" · §6.2 ¶7 "Solution (i) 4-Bromopent-2-ene (ii) 3-Bromo-2-methylbut-1-ene (iii) 4-Bromo-3-methylpent-2-ene" · §6.3 ¶3 "Alkyl halides are best prepared from alcohols,"
- ch 6 p6: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.4.1 ¶1 "The hydroxyl group of an alcohol is" · §6.4.2 ¶1 "(I) From alkanes by free radical halogenation" · printed starts no row begins with: "secondary alcohols with HCl require the" · "Free radical chlorination or bromination of"
- ch 6 p7: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.4.2 ¶2 "(II) From alkenes (i) Addition of hydrogen" · §6.4.2 ¶3 "Propene yields two products, however only one" · §6.4.2 ¶5 "Example 6.3 Identify all the possible monochloro" · §6.4.2 ¶6 "Solution In the given molecule, there are" · printed starts no row begins with: "(i) Addition of hydrogen halides: An"
- ch 6 p8: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.5 ¶2 "The ortho and para isomers can be" · printed starts no row begins with: "are not prepared by this method"
- ch 6 p9: 2 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.5 ¶5 "Example 6.4 Write the products of the" · printed starts no row begins with: "on photochemical chlorination yields" · "(ii) Three isomeric monochlorides." · "(iii) Four isomeric monochlorides."
- ch 6 p10: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.6 ¶2 "Melting and boiling points Methyl chloride, methyl"
- ch 6 p11: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.6 ¶6 "Density Bromo, iodo and polychloro derivatives of" · §6.6 ¶7 "Solubility The haloalkanes are very slightly soluble" · §6.7.1 ¶1 "The reactions of haloalkanes may be divided" · §6.7.1 ¶2 "(1)Nucleophilic substitution reactions You have learnt in" · printed starts no row begins with: "Table 6.3: Density of Some Haloalkanes" · "(i) Bromomethane, Bromoform, Chloromethane, Dibromomethane." · "(ii) 1-Chloropropane, Isopropyl chloride, 1-Chlorobutane." · "3. Reaction with metals."
- ch 6 p13: 7 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.7.1 ¶5 "Example 6.5 Haloalkanes react with KCN to" · §6.7.1 ¶6 "Solution KCN is predominantly ionic and provides" · §6.7.1 ¶7 "Mechanism: This reaction has been found to" · §6.7.1 ¶9 "The solid wedge represents the bond coming" · §6.7.1 ¶10 "The above reaction can be represented diagrammatically" · §6.7.1 ¶11 "It depicts a bimolecular nucleophilic substitution (S_N2)"
- ch 6 p14: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §6.7.1 ¶12 "Configuration Spacial arrangement of functional groups around" · §6.7.1 ¶13 "These are the two structures of the" · §6.7.1 ¶14 "Since this reaction requires the approach of"
- ch 6 p15: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.7.1 ¶16 "It occurs in two steps. In step"
- ch 6 p16: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.7.1 ¶20 "Example 6.6 In the following pairs of" · §6.7.1 ¶21 "Solution It is primary halide and therefore" · §6.7.1 ¶22 "As iodine is a better leaving group" · §6.7.1 ¶23 "Example 6.7 Predict the order of reactivity" · printed starts no row begins with: "In the following pairs of halogen" · "(i) The four isomeric bromobutanes"
- ch 6 p17: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.7.1 ¶24 "Solution (i) CH_3CH_2CH_2CH_2Br < (CH_3)_2CHCH_2Br < CH_3CH_2CH(Br)CH_3" · §6.7.1 ¶25 "Of the two primary bromides, the carbocation" · §6.7.1 ¶26 "(ii) C_6H_5C(CH_3)(C_6H_5)Br > C_6H_5CH(C_6H_5)Br > C_6H_5CH(CH_3)Br >"
- ch 6 p18: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §6.7.1 ¶30 "The symmetry and asymmetry are also observed" · printed starts no row begins with: "the optical activity in such organic"
- ch 6 p19: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.7.1 ¶34 "The stereoisomers related to each other as" · §6.7.1 ¶36 "However, the sign of optical rotation is"
- ch 6 p20: 6 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.7.1 ¶40 "In general, if during a reaction, no" · §6.7.1 ¶41 "It is important to note that configuration" · §6.7.1 ¶43 "If (A) is the only compound obtained," · §6.7.1 ¶44 "If (B) is the only compound obtained,"
- ch 6 p21: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.7.1 ¶45 "If a 50:50 mixture of A and" · §6.7.1 ¶50 "2. Elimination reactions When a haloalkane with"
- ch 6 p22: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.7.1 ¶51 "As a result, an alkene is formed" · §6.7.1 ¶53 "Elimination versus substitution A chemical reaction is"
- ch 6 p23: 7 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.7.1 ¶55 "In the Grignard reagent, the carbon-magnesium bond" · §6.7.1 ¶56 "Grignard reagents are highly reactive and react" · §6.7.1 ¶57 "It is therefore necessary to avoid even" · §6.7.1 ¶58 "Wurtz reaction Alkyl halides react with sodium" · §6.7.2 ¶1 "1. Nucleophilic substitution Aryl halides are extremely" · §6.7.2 ¶3 "C—Cl bond acquires a partial double bond" · printed starts no row begins with: "Aryl halides are extremely less reactive"
- ch 6 p24: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §6.7.2 ¶5 "The sp^2 hybridised carbon with a greater" · §6.7.2 ¶8 "Replacement by hydroxyl group Chlorobenzene can be" · printed starts no row begins with: "by resonance and therefore, S 1"
- ch 6 p25: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.7.2 ¶10 "The effect is pronounced when (-NO_2) group" · printed starts no row begins with: "Can you think why does NO"
- ch 6 p27: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.7.2 ¶15 "Solution Chlorine withdraws electrons through inductive effect" · §6.7.2 ¶16 "Through resonance, halogen tends to stabilise the"
- ch 6 p28: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §6.7.2 ¶18 "Fittig reaction Aryl halides also give analogous" · printed starts no row begins with: "rapidly by an S 2 mechanism?"
- ch 6 p29: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §6.8 ¶1 "Carbon compounds containing more than one halogen" · §6.8.1 ¶1 "Dichloromethane is widely used as a solvent" · §6.8.2 ¶1 "Chemically, chloroform is employed as a solvent" · §6.8.3 ¶1 "It was used earlier as an antiseptic" · §6.8.4 ¶1 "It is produced in large quantities for" · printed starts no row begins with: "methane propellant in aerosols, and as" · "iodine and other substances. The major" · "methane due to the liberation of" · "refrigerants and propellants for aerosol cans."
- ch 6 p30: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.8.6 ¶1 "DDT, the first chlorinated organic insecticides, was"
- ch 7 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §7 ¶1 "You have learnt that substitution of one"
- ch 7 p2: 8 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.1 ¶1 "The classification of compounds makes their study" · §7.1.1 ¶1 "Alcohols and phenols may be classified as" · §7.1.1 ¶2 "Monohydric alcohols may be further classified according" · §7.1.1 ¶4 "Primary, secondary and tertiary alcohols: In these" · §7.1.1 ¶5 "Allylic alcohols: In these alcohols, the —OH" · §7.1.1 ¶6 "Benzylic alcohols: In these alcohols, the —OH" · printed starts no row begins with: "Mono, Di, polyhydric compounds depending on"
- ch 7 p3: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.1.1 ¶7 "Allylic and benzylic alcohols may be primary," · §7.2 ¶1 "(a) Alcohols: The common name of an" · printed starts no row begins with: "groups attached to the oxygen atom" · "7.2 Nomenclature (a) Alcohols: The common"
- ch 7 p4: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.2 ¶2 "According to IUPAC system, the name of" · printed starts no row begins with: "Table 7.1: Common and IUPAC Names"
- ch 7 p5: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §7.2 ¶5 "Dihydroxy derivatives of benzene are known as" · §7.2 ¶6 "(c) Ethers: Common names of ethers are"
- ch 7 p6: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.2 ¶7 "If both the alkyl groups are the" · §7.2 ¶8 "According to IUPAC system of nomenclature, ethers" · §7.2 ¶10 "Solution (i) 4-Chloro-2,3-dimethylpentan-1-ol (ii) 2-Ethoxypropane (iii) 2,6-Dimethylphenol" · §7.3 ¶1 "In alcohols, the oxygen of the –OH" · printed starts no row begins with: "7.3 Structures of In alcohols, the"
- ch 7 p7: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §7.4.1 ¶1 "Alcohols are prepared by the following methods:" · §7.4.1 ¶2 "1. From alkenes (i) By acid catalysed" · §7.4.1 ¶3 "Mechanism The mechanism of the reaction involves" · §7.4.1 ¶4 "Step 1: Protonation of alkene to form" · printed starts no row begins with: "(i) By acid catalysed hydration: Alkenes"
- ch 7 p8: 5 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.4.1 ¶10 "(ii) By reduction of carboxylic acids and" · printed starts no row begins with: "alcohol is obtained in excellent yield." · "(i) By reduction of aldehydes and"
- ch 7 p9: 10 rows start here, the print starts 8 paragraphs — rows the print does not start: §7.4.1 ¶16 "Example 7.2 Give the structures and IUPAC" · §7.4.1 ¶20 "Solution (a) CH_3CH_2-CH_2-CH_2-OH Butan-1-ol (b) CH_3-CH-CH_3 with" · §7.4.2 ¶1 "Phenol, also known as carbolic acid, was" · printed starts no row begins with: "of Phenols nineteenth century from coal"
- ch 7 p10: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.4.2 ¶2 "1. From haloarenes Chlorobenzene is fused with" · §7.4.2 ¶5 "4. From cumene Phenol is manufactured from"
- ch 7 p11: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.4.3 ¶1 "Alcohols and phenols consist of two parts," · §7.4.3 ¶2 "Boiling Points The boiling points of alcohols" · printed starts no row begins with: "Grignard reagent on methanal ?" · "Properties hydroxyl group. The properties of"
- ch 7 p12: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §7.4.3 ¶6 "Solubility Solubility of alcohols and phenols in" · §7.4.3 ¶8 "Solution (a) Methanol, ethanol, propan-1-ol, butan-2-ol, butan-1-ol," · §7.4.4 ¶1 "Alcohols are versatile compounds. They react both" · §7.4.4 ¶2 "Alcohols as nucleophiles (i) R–O–H + +C–" · §7.4.4 ¶4 "Protonated alcohols as electrophiles R–CH_2–OH + H^+" · printed starts no row begins with: "(b) n-Butane, ethoxyethane, pentanal and pentan-1-ol." · "Reactions electrophiles. The bond between O–H" · "electrophiles. Protonated alcohols react in this"
- ch 7 p13: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "(i) Reaction with metals: Alcohols and"
- ch 7 p14: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §7.4.4 ¶15 "The ionisation of an alcohol and a" · printed starts no row begins with: "them proton acceptors."
- ch 7 p15: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.4.4 ¶19 "Example 7.4 Arrange the following compounds in" · §7.4.4 ¶20 "Solution Propan-1-ol, 4-methylphenol, phenol, 3-nitrophenol, 3,5-dinitrophenol, 2,4," · §7.4.4 ¶21 "2. Esterification Alcohols and phenols react with"
- ch 7 p16: 5 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "The reaction with carboxylic acid and" · "alcohols. Phenols show this type of" · "halides to form alkyl halides (Refer" · "The difference in reactivity of three" · "alcohols do not produce turbidity at" · "Unit 6, Class XII)."
- ch 7 p17: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §7.4.4 ¶30 "Mechanism Step 1: Formation of protonated alcohol." · §7.4.4 ¶31 "Step 2: Formation of carbocation: It is" · §7.4.4 ¶32 "Step 3: Formation of ethene by elimination" · printed starts no row begins with: "oxygen double bond with cleavage of"
- ch 7 p18: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §7.4.4 ¶41 "Biological oxidation of methanol and ethanol in" · printed starts no row begins with: "Following reactions are shown by phenols"
- ch 7 p19: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.4.4 ¶44 "Common electrophilic aromatic substitution reactions taking place" · printed starts no row begins with: "phenol yields a mixture of ortho"
- ch 7 p20: 7 rows start here, the print starts 9 paragraphs — rows the print does not start: §7.4.4 ¶51 "The usual halogenation of benzene takes place" · §7.4.4 ¶54 "Solution The combined influence of –OH and" · §7.4.4 ¶55 "2. Kolbe’s reaction Phenoxide ion generated by" · printed starts no row begins with: "effect of –OH group attached to" · "2,4,6-tribromophenol is formed as white precipitate." · "(a) Mononitration of 3-methylphenol" · "(b) Dinitration of 3-methylphenol" · "(c) Mononitration of phenyl methanoate."
- ch 7 p21: 4 rows start here, the print starts 7 paragraphs — rows the print does not start: §7.4.4 ¶56 "3. Reimer-Tiemann reaction On treating phenol with" · §7.4.4 ¶59 "5. Oxidation Oxidation of phenol with chromic" · printed starts no row begins with: "Phenol is converted to benzene on" · "following alcohol reacts with (a) HCl" · "(i) 1-methylcyclohexanol and (ii) butan-1-ol" · "resonance structures of the corresponding phenoxide" · "(i) Reimer - Tiemann reaction (ii)"
- ch 7 p22: 8 rows start here, the print starts 1 paragraphs — rows the print does not start: §7.5 ¶1 "Methanol and ethanol are among the two" · §7.5 ¶2 "1. Methanol Methanol, CH_3OH, also known as" · §7.5 ¶3 "Methanol is a colourless liquid and boils" · §7.5 ¶4 "2. Ethanol Ethanol, C_2H_5OH, is obtained commercially" · §7.5 ¶5 "In wine making, grapes are the source" · §7.5 ¶6 "The action of zymase is inhibited once" · §7.5 ¶8 "Nowadays, large quantities of ethanol are obtained"
- ch 7 p23: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.6.1 ¶1 "1. By dehydration of alcohols Alcohols undergo" · §7.6.1 ¶2 "The formation of ether is a nucleophilic" · §7.6.1 ¶5 "2. Williamson synthesis It is an important" · §7.6.1 ¶6 "Ethers containing substituted alkyl groups (secondary or" · printed starts no row begins with: "Alcohols undergo dehydration in the presence" · "Can you explain why is bimolecular"
- ch 7 p24: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §7.6.1 ¶8 "It is because alkoxides are not only" · §7.6.1 ¶10 "Solution (i) The major product of the" · printed starts no row begins with: "(i) What would be the major" · "(ii) Write a suitable reaction for"
- ch 7 p25: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §7.6.2 ¶1 "The C-O bonds in ethers are polar" · §7.6.3 ¶2 "Alkyl aryl ethers are cleaved at the" · §7.6.3 ¶4 "The order of reactivity of hydrogen halides" · printed starts no row begins with: "Properties moment. The weak polarity of" · "Reactions Ethers are the least reactive"
- ch 7 p26: 8 rows start here, the print starts 0 paragraphs — rows the print does not start: §7.6.3 ¶5 "Mechanism The reaction of an ether with" · §7.6.3 ¶6 "The reaction takes place with HBr or" · §7.6.3 ¶7 "Step 2: Iodide is a good nucleophile." · §7.6.3 ¶8 "Thus, in the cleavage of mixed ethers" · §7.6.3 ¶9 "When HI is in excess and the" · §7.6.3 ¶10 "However, when one of the alkyl group" · §7.6.3 ¶11 "It is because in step 2 of" · §7.6.3 ¶12 "In case of anisole, methylphenyl oxonium ion,"
- ch 7 p27: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §7.6.3 ¶13 "Example 7.7 Give the major products that" · §7.6.3 ¶14 "Solution (i) CH_3–CH_2–CH(CH_3)–CH_2OH + CH_3CH_2I (ii) CH_3CH_2CH_2OH" · §7.6.3 ¶15 "2. Electrophilic substitution The alkoxy group (-OR)"
- ch 7 p28: 2 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "and nitric acids to yield a" · "starting from ethanol and 3-methylpentan-2-ol." · "preparation of 1-methoxy-4-nitrobenzene and why?"
- ch 8 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §8 ¶1 "In the previous Unit, you have studied"
- ch 8 p2: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.1.1 ¶1 "I. Aldehydes and ketones Aldehydes and ketones" · §8.1.1 ¶2 "There are two systems of nomenclature of" · §8.1.1 ¶3 "(a) Common names Aldehydes and ketones are" · printed starts no row begins with: "Aldehydes and ketones are the simplest"
- ch 8 p3: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §8.1.1 ¶4 "(b) IUPAC names The IUPAC names of"
- ch 8 p4: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §8.1.1 ¶5 "The common and IUPAC names of some"
- ch 8 p5: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §8.1.2 ¶1 "The carbonyl carbon atom is sp^2-hybridised and" · §8.2 ¶1 "Some important methods for the preparation of" · §8.2.1 ¶1 "1. By oxidation of alcohols Aldehydes and" · §8.2.1 ¶3 "3. From hydrocarbons (i) By ozonolysis of" · printed starts no row begins with: "bonds. The fourth valence electron of" · "(iii) 2-Hydroxycyclopentane carbaldehyde (iv) 4-Oxopentanal" · "Aldehydes and ketones are generally prepared" · "(i) By ozonolysis of alkenes: As" · "followed by reaction with zinc dust"
- ch 8 p6: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §8.2.2 ¶1 "1. From acyl chloride (acid chloride) Acyl" · §8.2.2 ¶3 "Alternatively, nitriles are selectively reduced by diisobutylaluminium" · §8.2.2 ¶4 "Similarly, esters are also reduced to aldehydes" · §8.2.2 ¶5 "3. From hydrocarbons Aromatic aldehydes (benzaldehyde and" · printed starts no row begins with: "pattern of the alkene (Unit 9," · "Acyl chloride (acid chloride) is hydrogenated"
- ch 8 p7: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.2.3 ¶1 "1. From acyl chlorides Treatment of acyl" · printed starts no row begins with: "Treatment of acyl chlorides with dialkylcadmium,"
- ch 8 p8: 4 rows start here, the print starts 9 paragraphs — rows the print does not start: §8.2.3 ¶2 "2. From nitriles Treating a nitrile with" · §8.2.3 ¶5 "Solution (i) C_5H_5NH^+CrO_3Cl^- (PCC) (ii) Anhydrous CrO_3" · printed starts no row begins with: "(v) Allyl alcohol to propenal" · "(iii) CrO in the presence" · "of acetic anhydride/" · "(ii) Cyclohexanol to cyclohexanone" · "(iv) Ethanenitrile to ethanal" · "(vi) But-2-ene to ethanal" · "(ii) Anhydrous CrO"
- ch 8 p9: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.3 ¶1 "The physical properties of aldehydes and ketones" · §8.3 ¶2 "Methanal is a gas at room temperature." · §8.3 ¶5 "Example 8.2 Arrange the following compounds in" · §8.3 ¶6 "Solution The molecular masses of these compounds" · printed starts no row begins with: "74. Since only butan-1-ol molecules are"
- ch 8 p10: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.4 ¶1 "Since aldehydes and ketones both possess the" · §8.4 ¶4 "(ii) Reactivity Aldehydes are generally more reactive" · §8.4 ¶6 "Solution The carbon atom of the carbonyl" · printed starts no row begins with: "their boiling points."
- ch 8 p11: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §8.4 ¶10 "The position of the equilibrium lies largely" · printed starts no row begins with: "purification of aldehydes."
- ch 8 p12: 4 rows start here, the print starts 5 paragraphs — rows the print does not start: §8.4 ¶16 "2. Reduction (i) Reduction to alcohols: Aldehydes" · §8.4 ¶18 "* 2,4-DNP-derivatives are yellow, orange or red" · printed starts no row begins with: "corresponding aldehydes and ketones respectively." · "(i) Reduction to alcohols: Aldehydes and" · "well as by catalytic hydrogenation (Unit"
- ch 8 p13: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.4 ¶19 "3. Oxidation Aldehydes differ from ketones in" · §8.4 ¶21 "The mild oxidising agents given below are"
- ch 8 p14: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.4 ¶27 "Solution (A) forms 2,4-DNP derivative. Therefore, it" · §8.4 ¶28 "Compound (B), being an oxidation product of"
- ch 8 p16: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §8.4 ¶34 "Ketones can also be used as one" · §8.4 ¶35 "5. Other reactions (i) Cannizzaro reaction: Aldehydes" · printed starts no row begins with: "(i) Cannizzaro reaction: Aldehydes which do"
- ch 8 p17: 2 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "nucleophilic addition reactions." · "(i) Ethanal, Propanal, Propanone, Butanone." · "(ii) Benzaldehyde, p-Tolualdehyde, p-Nitrobenzaldehyde, Acetophenone." · "Hint: Consider steric effect and electronic"
- ch 8 p18: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §8.5 ¶2 "Carboxylic Acids Carbon compounds containing a carboxyl" · §8.6.1 ¶1 "Since carboxylic acids are amongst the earliest"
- ch 8 p19: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §8.6.2 ¶1 "In carboxylic acids, the bonds to the" · §8.7 ¶1 "Some important methods of preparation of carboxylic" · §8.7 ¶2 "1. From primary alcohols and aldehydes Primary" · printed starts no row begins with: "of Carboxyl and are separated by" · "8.7 Methods of Some important methods"
- ch 8 p20: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.7 ¶4 "2. From alkylbenzenes Aromatic carboxylic acids can"
- ch 8 p21: 3 rows start here, the print starts 8 paragraphs — rows the print does not start: §8.7 ¶9 "6. From esters Acidic hydrolysis of esters" · §8.7 ¶10 "Example 8.5 Write chemical reactions to affect" · printed starts no row begins with: "alkyl halides (ascending the series)." · "(i) Butan-1-ol to butanoic acid" · "(ii) Benzyl alcohol to phenylethanoic acid" · "(iii) 3-Nitrobromobenzene to 3-nitrobenzoic acid" · "(iv) 4-Methylacetophenone to benzene-1,4-dicarboxylic acid" · "(v) Cyclohexene to hexane-1,6-dioic acid" · "(vi) Butanal to butanoic acid."
- ch 8 p22: 1 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.7 ¶11 "Solution (i) CH_3CH_2CH_2CH_2OH -> [CrO_3-H_2SO_4 / Jones" · printed starts no row begins with: "converted to benzoic acid." · "(i) Ethylbenzene (ii) Acetophenone" · "(iii) Bromobenzene (iv) Phenylethene (Styrene)"
- ch 8 p23: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.8 ¶1 "Aliphatic carboxylic acids upto nine carbon atoms" · §8.9 ¶1 "The reaction of carboxylic acids are classified" · §8.9.1 ¶1 "Acidity Reactions with metals and alkalies The" · printed starts no row begins with: "The carboxylic acids like alcohols evolve"
- ch 8 p24: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §8.9.1 ¶3 "For the above reaction: K_eq = [H_3O^+]" · printed starts no row begins with: "where K , is equilibrium constant"
- ch 8 p25: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §8.9.2 ¶1 "1. Formation of anhydride Carboxylic acids on" · §8.9.2 ¶2 "2. Esterification Carboxylic acids are esterified with" · printed starts no row begins with: "Carboxylic acids on heating with mineral"
- ch 8 p26: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §8.9.2 ¶3 "Mechanism of esterification of carboxylic acids: The" · §8.9.2 ¶4 "3. Reactions with PCl_5, PCl_3 and SOCl_2" · §8.9.2 ¶5 "4. Reaction with ammonia Carboxylic acids react"
- ch 8 p27: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.9.3 ¶1 "1. Reduction Carboxylic acids are reduced to" · §8.9.3 ¶2 "2. Decarboxylation Carboxylic acids lose carbon dioxide" · §8.9.4 ¶1 "1. Halogenation Carboxylic acids having an alpha-hydrogen" · printed starts no row begins with: "Carboxylic acids are reduced to primary" · "Carboxylic acids having an α-hydrogen are"
- ch 8 p28: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §8.9.4 ¶2 "2. Ring substitution Aromatic carboxylic acids undergo" · §8.10 ¶1 "Methanoic acid is used in rubber, textile," · printed starts no row begins with: "(i) CH CO H or CH"
- ch 9 p1: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §9 ¶1 "Amines constitute an important class of organic" · §9 ¶2 "I. AMINES Amines can be considered as" · §9 ¶3 "For example: CH_3–NH_2 , C_6H_5–NH_2, CH_3–NH–CH_3, CH_3–N(CH_3)(CH_3)" · §9.1 ¶1 "Like ammonia, nitrogen atom of amines is"
- ch 9 p2: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §9.2 ¶1 "Amines are classified as primary (1°), secondary" · §9.3 ¶1 "In common system, an aliphatic amine is"
- ch 9 p4: 3 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.4 ¶1 "Amines are prepared by the following methods:" · §9.4 ¶2 "1. Reduction of nitro compounds Nitro compounds" · printed starts no row begins with: "9.2 (i) Write structures of different" · "(ii) Write IUPAC names of all" · "(iii) What type of isomerism is" · "to initiate the reaction."
- ch 9 p5: 7 rows start here, the print starts 9 paragraphs — rows the print does not start: §9.4 ¶7 "Example 9.1 Write chemical equations for the" · §9.4 ¶8 "Solution (i) C_2H_5-Cl -> (NH_3) C_2H_5-NH_2 ->" · printed starts no row begins with: "(i) Reaction of ethanolic NH with" · "(ii) Ammonolysis of benzyl chloride and" · "with two moles of CH Cl." · "The amides on reduction with lithium"
- ch 9 p6: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.4 ¶14 "Solution (i) CH_3–CH_2–Cl -> (Ethanolic NaCN) CH_3–CH_2–C≡N"
- ch 9 p7: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §9.4 ¶15 "Example 9.3 Write structures and IUPAC names" · §9.4 ¶16 "Solution (i) Propanamine contains three carbons. Hence," · §9.5 ¶1 "The lower aliphatic amines are gases with" · printed starts no row begins with: "(i) the amide which gives propanamine" · "(ii) the amine produced by the" · "(i) Propanamine contains three carbons. Hence," · "(i) Benzene into aniline (ii) Benzene"
- ch 9 p8: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.5 ¶4 "Intermolecular hydrogen bonding in primary amines is" · §9.6 ¶1 "Difference in electronegativity between nitrogen and hydrogen"
- ch 9 p10: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.6 ¶9 "Structure-basicity relationship of amines Basicity of amines" · §9.6 ¶11 "Due to the electron releasing nature of" · §9.6 ¶12 "Decreasing order of extent of H-bonding in"
- ch 9 p11: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.6 ¶15 "On the other hand, anilinium ion obtained"
- ch 9 p12: 6 rows start here, the print starts 1 paragraphs — rows the print does not start: §9.6 ¶17 "Example 9.4 Arrange the following in decreasing" · §9.6 ¶18 "Solution The decreasing order of basic strength" · §9.6 ¶19 "2. Alkylation Amines undergo alkylation on reaction" · §9.6 ¶20 "3. Acylation Aliphatic and aromatic primary and" · §9.6 ¶22 "What do you think is the product"
- ch 9 p13: 9 rows start here, the print starts 9 paragraphs — rows the print does not start: §9.6 ¶23 "4. Carbylamine reaction Aliphatic and aromatic primary" · printed starts no row begins with: "benzenesulphonamide is formed."
- ch 9 p14: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §9.6 ¶34 "7. Electrophilic substitution You have read earlier" · §9.6 ¶36 "The main problem encountered during electrophilic substitution" · §9.6 ¶37 "The lone pair of electrons on nitrogen" · printed starts no row begins with: "temperature to give a white precipitate"
- ch 9 p15: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.6 ¶39 "However, by protecting the –NH_2 group by" · §9.6 ¶41 "Aniline does not undergo Friedel-Crafts reaction (alkylation" · printed starts no row begins with: "effect of –NHCOCH group is less"
- ch 9 p16: 3 rows start here, the print starts 4 paragraphs — rows the print does not start: §9.6 ¶42 "II. DIAZONIUM SALTS The diazonium salts have" · §9.7 ¶1 "Benzenediazonium chloride is prepared by the reaction" · printed starts no row begins with: "iodide in the presence of sodium" · "the product obtained." · "C H N. Write IUPAC names"
- ch 9 p17: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §9.8 ¶1 "Benzenediazonium chloride is a colourless crystalline solid." · §9.9 ¶1 "The reactions of diazonium salts can be" · §9.9 ¶2 "A. Reactions involving displacement of nitrogen Diazonium"
- ch 9 p18: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.9 ¶11 "B. Reactions involving retention of diazo group" · §9.10 ¶1 "From the above reactions, it is clear" · §9.10 ¶2 "Aryl fluorides and iodides cannot be prepared"
- ch 9 p19: 1 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.10 ¶4 "Example 9.5 How will you convert 4-nitrotoluene" · printed starts no row begins with: "(i) 3-Methylaniline into 3-nitrotoluene." · "(ii) Aniline into 1,3,5 - tribromobenzene."
- ch 10 p1: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §10 ¶1 "A living system grows, sustains and reproduces" · §10.1 ¶1 "Carbohydrates are primarily produced by plants and"
- ch 10 p2: 8 rows start here, the print starts 10 paragraphs — rows the print does not start: §10.1.1 ¶1 "Carbohydrates are classified on the basis of" · §10.1.2.1 ¶1 "Glucose occurs freely in nature as well" · §10.1.2.1 ¶2 "1. From sucrose (Cane sugar): If sucrose" · printed starts no row begins with: "to give simpler unit of polyhydroxy" · "nature. Some common examples are glucose," · "whereas maltose gives two molecules of" · "Table 10.1: Different Types of Monosaccharides" · "present in sweet fruits and honey."
- ch 10 p3: 7 rows start here, the print starts 8 paragraphs — rows the print does not start: §10.1.2.1 ¶4 "Glucose is an aldohexose and is also" · printed starts no row begins with: "starch by boiling it with dilute" · "the six carbon atoms are linked"
- ch 10 p5: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.1.2.1 ¶15 "The structure (I) of glucose explained most" · §10.1.2.1 ¶16 "1. Despite having the aldehyde group, glucose" · §10.1.2.1 ¶20 "The two cyclic hemiacetal forms of glucose" · printed starts no row begins with: "indicating the absence of free —CHO"
- ch 10 p6: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.1.2.2 ¶1 "Fructose is an important ketohexose. It is" · §10.1.2.2 ¶2 "Fructose also has the molecular formula C_6H_12O_6" · printed starts no row begins with: "by the hydrolysis of disaccharide, sucrose."
- ch 10 p7: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §10.1.3 ¶1 "You have already read that disaccharides on" · printed starts no row begins with: "hydrolysis gives equimolar mixture of D-(+)-glucose" · "(+) to laevo (–) and the"
- ch 10 p8: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.1.4 ¶1 "Polysaccharides contain a large number of monosaccharide"
- ch 10 p9: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.1.5 ¶1 "Carbohydrates are essential for life in both" · printed starts no row begins with: "C4 of the next glucose unit."
- ch 10 p10: 4 rows start here, the print starts 6 paragraphs — rows the print does not start: §10.2.1 ¶1 "Amino acids contain amino (–NH_2) and carboxyl" · printed starts no row begins with: "benzene (simple six membered ring compounds)" · "groups. Depending upon the relative position" · "Table 10.2: Natural Amino Acids"
- ch 10 p11: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §10.2.2 ¶1 "Amino acids are classified as acidic, basic"
- ch 10 p12: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §10.2.3 ¶1 "You have already read that proteins are" · §10.2.3 ¶4 "(a) Fibrous proteins When the polypeptide chains" · printed starts no row begins with: "of Proteins and they are connected"
- ch 10 p13: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §10.2.3 ¶5 "(b) Globular proteins This structure results when" · §10.2.3 ¶9 "alpha-Helix is one of the most common" · §10.2.3 ¶12 "(iv) Quaternary structure of proteins: Some of" · printed starts no row begins with: "a-Helix is one of the most"
- ch 10 p14: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §10.2.4 ¶1 "Protein found in a biological system with"
- ch 10 p15: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.3 ¶1 "Life is possible due to the coordination" · §10.4 ¶1 "It has been observed that certain organic" · printed starts no row begins with: "higher than that of the corresponding" · "10.3 Enzymes Life is possible due"
- ch 10 p16: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §10.4.1 ¶1 "Vitamins are classified into two groups depending" · printed starts no row begins with: "D, E and K. They are"
- ch 10 p17: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §10.5 ¶1 "Every generation of each and every species" · §10.5.1 ¶1 "Complete hydrolysis of DNA (or RNA) yields"
- ch 10 p18: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.5.2 ¶1 "A unit formed by the attachment of" · printed starts no row begins with: "of Nucleic known as nucleoside. In"
- ch 10 p19: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.5.2 ¶3 "A simplified version of nucleic acid chain" · printed starts no row begins with: "Sugar Phosphate Sugar Phosphate Sugar"
- ch 10 p20: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.5.3 ¶1 "DNA is the chemical basis of heredity" · §10.6 ¶1 "Hormones are molecules that act as intercellular" · printed starts no row begins with: "Functions of genetic information. DNA is"
- ch 10 p21: 1 rows start here, the print starts 3 paragraphs — printed starts no row begins with: "thymine is hydrolysed?" · "bases obtained. What does this fact"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 10 p10: the print starts "All a-amino acids have trivial names," where §10.2.1 ¶2 starts "All alpha-amino acids have trivial names, which" — the layer dropped the line's math
- ch 10 p13: the print starts "In b-pleated sheet structure all peptide" where §10.2.3 ¶10 starts "In beta-pleated sheet structure all peptide chains" — the layer dropped the line's math

## joins across page breaks against the print

- ch 9 §9.4 ¶3 runs from p4 onto p5, but p5 opens a new paragraph: "The free amine can be obtained"

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
- ch 6 §6.4.1 ¶1 starts a paragraph at the top of p6, but the print continues p5's: "Table 6.2: Carbon-Halogen (C—X) Bond Lengths," — ruled noise: §6.4.1 opens p6 under its own margin heading; p5 ends in Table 6.2 and §6.4's one-line opening
- ch 6 §6.6 ¶2 starts a paragraph at the top of p10, but the print continues p9's: "Melting and boiling points" — ruled noise: p10 opens with the side heading the text correction restores; p9 ends in §6.6's complete first paragraph
- ch 6 §6.7.1 ¶5 starts a paragraph at the top of p13, but the print continues p12's: "Haloalkanes react with KCN to form" — ruled noise: p13 opens with Example 6.5's box; the sentence it interrupts is mended by the text correction on p12
- ch 6 §6.8 ¶1 starts a paragraph at the top of p29, but the print continues p28's: "Carbon compounds containing more than one" — ruled noise: §6.8 opens p29 under its own margin heading; p28 ends in Intext Questions
- ch 7 §7.1.1 ¶7 starts a paragraph at the top of p3, but the print continues p2's: "Allylic and benzylic alcohols may be" — ruled noise: p3 opens with the display of primary, secondary and tertiary benzylic alcohols; the capitalised sentence after it stays as transcribed
- ch 7 §7.2 ¶7 starts a paragraph at the top of p6, but the print continues p5's: "If both the alkyl groups are" — ruled noise: p5 ends in the ether examples; p6 sets its lines flush at x 220 whether they continue or not ("According to IUPAC…" below is flush too), so the flush top proves nothing
- ch 8 §8.8 ¶1 starts a paragraph at the top of p23, but the print continues p22's: "Aliphatic carboxylic acids upto nine carbon" — ruled noise: §8.8 opens p23 under its own margin heading; p22 ends in the Intext Question box
- ch 8 §8.4 ¶18: figure_refs carries "Table 8.2", which the paragraph never mentions — ruled noise: the row is Table 8.2's footnote, set under the table; the link names the table it annotates
- ch 10 §10.1.3 ¶1 starts a paragraph at the top of p7, but the print continues p6's: "You have already read that disaccharides" — ruled noise: §10.1.3 opens p7 under its own margin heading; p6 ends in the Haworth structures of fructose
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (582 rows)
request id: pipeline-ncert-verify-33fd49a6-61ed-49b3-8029-d28afeba2047

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 126 | 52 | 74 |
artefact: verify/chem12-part2/en.jsonl (126 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 52 | 196863 | 13118 | 369801 | 7251 | ₹55.80 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 6 p7 §6.4.2 ¶5: printed "Example 6.3 Identify all the possible" · transcribed "Identify all the possible"
- ch 6 p12 §6.7.1 ¶4: printed "nitrite ion also represents an ambident nucleophile with two different points of linkage [O—N=O]" · transcribed "nitrite ion also represents an ambident nucleophile with two different points of linkage [^-O— N =O]"
- ch 6 p21 §6.7.1 ¶50: printed "from β-carbon" · transcribed "from beta-carbon"
- ch 6 p22 §6.7.1 ¶54: printed "CH_3CH_2Br + Mg --(dry ether)--> CH_3CH_2MgBr" · transcribed "CH_3CH_2Br + Mg -> CH_3CH_2MgBr (dry ether)"
- ch 7 p24 §7.6.1 ¶9: printed "CH_3-C(CH_3)_2-Cl" · transcribed "CH_3-C(CH_3)_2-Cl -> CH_3-C(CH_3)_2-OC_2H_5"
- ch 8 p7 §8.2.3 ¶1: printed "R' — C — Cl with double bond O below C" · transcribed "R' — C(=O) — Cl"
- ch 8 p7 §8.2.3 ¶1: printed "2 R' — C — R with double bond O below C" · transcribed "2 R' — C(=O) — R"
- ch 8 p11 §8.4 ¶12: printed "give a gem-dialkoxy compound" · transcribed "give a gem-dialkoxy compound known as acetal as shown in the reaction"
- ch 8 p13 §8.4 ¶22: printed "RCOO‾" · transcribed "RCOO^-"
- ch 8 p13 §8.4 ¶23: printed "RCOO‾" · transcribed "RCOO^-"
- ch 8 p23 §8.9.1 ¶1: printed "2R-COO^-Na^+ + H_2" · transcribed "2R-COO^-Na^+ + H_2 (Sodium carboxylate)"
- ch 9 p8 §9.6 ¶2: printed "R—NH_2 + H X <-> R—NH_3^+ X^-" · transcribed "R—NH_2 + H X <-> R—NH_3^+ X^- (Salt)"
- ch 10 p12 §10.2.2 ¶4: printed "all other naturally occurring α-amino acids" · transcribed "all other naturally occurring alpha-amino acids"
- ch 10 p12 §10.2.2 ¶4: printed "since the α-carbon atom" · transcribed "since the alpha-carbon atom"
- ch 10 p12 §10.2.3 ¶1: printed "polymers of α-amino acids" · transcribed "polymers of alpha-amino acids"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 6 p3: "CH3CH2CH2Br naming/structure examples"
- ch 6 p3: "H3C-CH-CH3 Cl examples"
- ch 6 p3: "Isobutyl chloride examples"
- ch 6 p3: "Bromobenzene, m-Dibromobenzene, sym-Tribromobenzene examples"
- ch 6 p3: "1-Chloro-2,2-dimethylpropane and 2-Bromopropane examples"
- ch 6 p5: "Intext Question 6.1 Write structures of the following compounds"
- ch 6 p5: "(image structures i-vi with labeled compounds under Example 6.2"
- ch 6 p8: "+ X_2 -> Fe/dark -> o-Halotoluene"
- ch 6 p8: "NaNO_2 + HX / 273-278 K -> Benzene diazonium halide"
- ch 6 p8: "Cu_2X_2 -> Aryl halide + N_2"
- ch 6 p8: "KI -> + N_2"
- ch 6 p9: "Solution"
- ch 6 p10: "CH3CH2CH2CH2Br CH3CH2CHCH3 H3C-C-CH3 b.p./K 375 364 346"
- ch 6 p13: "carbon atom resulting in alkyl cyanides and through nitrogen atom leading to isocyanides. Similarly nitrite ion also represents an ambident nucleophile with two different points of linkage"
- ch 6 p13: "In the year 1937, Edward Davies Hughes and Sir Christopher Ingold proposed a mechanism for an S_N2 reaction."
- ch 6 p15: "(CH_3)_3CBr step I / step II reaction scheme with carbocation intermediate"
- ch 6 p16: "For S_N2 reaction ... Tertiary halide; Secondary halide; Primary halide; CH_3X ... For S_N1 reaction (diagram arrows text)"
- ch 6 p16: "H_2C=C(H)-CH_2^+ resonance structures diagram"
- ch 6 p16: "hexyl-CH_2Cl and hexyl-Cl ; ... I and ... Cl (example 6.6 structures)"
- ch 6 p19: "Identify chiral and achiral molecules (parts (i), (ii), (iii) with structures/formulae) - not fully carried by item 6"
- ch 6 p20: "CH3CHCH2CH3 Br"
- ch 6 p20: "heat"
- ch 6 p20: "H CH3 CH2 OH + H-Cl -> H CH3 CH2 Cl + H-OH"
- ch 6 p20: "(–)–2-Methylbutan-1-ol"
- ch 6 p20: "(+)-1-Chloro-2-methylbutane"
- ch 6 p20: "C2H5 H X CH3 -> Y (diagram of A and B)"
- ch 6 p21: "Location of α and β carbon in a molecule Carbon on which halogen atom is directly attached is called α-carbon and the carbon atom adjacent to this carbon is called β-carbon."
- ch 6 p22: "H_3C-CH_2-CH=CH-CH_3 <- OH ... Pent-2-ene (81%) 2-Bromopentane Pent-1-ene (19%)"
- ch 6 p25: "Can you think why does NO_2 group show its effect only at ortho- and para- positions and not at meta- position?"
- ch 6 p27: "Inductive effect destabilises the intermediate carbocation"
- ch 6 p27: "Resonance effect stabilises the intermediate carbocation"
- ch 6 p28: "Diphenyl"
- ch 6 p29: "Phosgene"
- ch 7 p2: "CH2OH structures for Monohydric, Dihydric, Trihydric alcohols"
- ch 7 p2: "CH2-OH, CH-OH, C-OH structures for Primary, Secondary, Tertiary alcohols"
- ch 7 p2: "CH2=CH-CH2-OH structures for Allylic alcohols"
- ch 7 p3: "Intext Questions 7.1 Classify the following"
- ch 7 p6: "Intext Question 7.3 Name the following compounds according to IUPAC system."
- ch 7 p9: "The reaction of Grignard reagents with methanal produces a primary alcohol, with other aldehydes, secondary alcohols and with ketones, tertiary alcohols."
- ch 7 p10: "Most of the worldwide production of phenol is from cumene."
- ch 7 p13: "Phenol reaction with sodium diagram (phenol + 2Na -> sodium phenoxide + H2)"
- ch 7 p13: "B: + H-O-R -> B-H + O-R conjugate acid/base equation"
- ch 7 p13: "R-CH2OH > R-CHOH >> R-C-OH primary secondary tertiary diagram"
- ch 7 p14: "R–Ö: + H–Ö–H (equation with base, acid, conjugate acid, conjugate base labels)"
- ch 7 p14: "resonance structures diagram of phenoxide ion (five structures)"
- ch 7 p14: "OH ... O⁻ + H⁺ ionisation of phenol structural diagram"
- ch 7 p16: "Salicylic acid + (CH_3CO)_2O -> Acetylsalicylic acid (Aspirin) + CH_3COOH structure diagram"
- ch 7 p16: "–C–C– / H OH -> (H^+, Heat) C=C + H_2O"
- ch 7 p17: "Bond breaking"
- ch 7 p18: "R–CH_2OH → Oxidation → R–C=O(H) → R–C=O(OH) diagram equations"
- ch 7 p18: "R–CH–R' --CrO_3--> R–C–R' equation (secondary alcohol to ketone)"
- ch 7 p18: "R–CH–R' --Cu, 573K--> R–C–R' equation"
- ch 7 p18: "CH_3–C(CH_3)–OH --Cu, 573K--> CH_3–C=CH_2 equation"
- ch 7 p19: "Dilute HNO3 reaction scheme (Nitration equation)"
- ch 7 p19: "Conc. HNO3 reaction scheme (2,4,6-Trinitrophenol equation)"
- ch 7 p19: "2, 4, 6 - Trinitrophenol is a strong acid due to the presence of three electron withdrawing –NO2 groups which facilitate the release of hydrogen ion."
- ch 7 p20: "reactions:"
- ch 7 p20: "(a) Mononitration of 3-methylphenol"
- ch 7 p20: "2,4,6-Tribromophenol"
- ch 7 p21: "Intext Questions 7.6"
- ch 7 p21: "7.7"
- ch 7 p21: "7.8"
- ch 7 p21: "7.9"
- ch 7 p22: "Ingestion of ethanol acts on the central nervous system."
- ch 7 p23: "Can you explain why is bimolecular dehydration not appropriate for the preparation of ethyl methyl ether?"
- ch 7 p24: "CH_3-C-Br + Na-O-CH_3 -> CH_3-C=CH_2+NaBr + CH_3OH"
- ch 7 p24: ":OH + NaOH -> :O Na -> :O-R"
- ch 7 p27: "mechanism resonance structures I-V for electrophilic substitution"
- ch 7 p27: "bromination equation with anisole, p-Bromoanisole and o-Bromoanisole"
- ch 8 p2: "Ester"
- ch 8 p2: "Acid anhydride"
- ch 8 p2: "Vanillin"
- ch 8 p2: "Salicylaldehyde"
- ch 8 p2: "Cinnamaldehyde"
- ch 8 p2: "Acetaldehyde"
- ch 8 p2: "Benzaldehyde"
- ch 8 p2: "β-Bromobutyraldehyde"
- ch 8 p3: "Acetone, Acetophenone, Propiophenone, Benzophenone diagrams"
- ch 8 p3: "Ethanal, 4-Bromo-3-methylheptanal, 3-Methylcyclopentanone, Cyclohexanecarbaldehyde, Pent-2-enal, 1-Phenylpropan-1-one diagrams"
- ch 8 p9: "b.p.(K) Molecular Mass table"
- ch 8 p9: "The diagram/figure with R, C=O hydrogen bonding"
- ch 8 p10: "Intext Question 8.3 Arrange the following compounds"
- ch 8 p13: "Bernhard Tollens (1841-1918) was a Professor of Chemistry at the University of Gottingen, Germany."
- ch 8 p14: "R—C—CH_3 NaOX reaction scheme with CHX_3 (X=Cl, Br, I)"
- ch 8 p14: "H CH_3 / C=C / H_3C ... NaOCl reaction scheme with CHCl_3"
- ch 8 p14: "Reactions are as follows: (A) + H_2NHN-... reaction scheme showing formation of 2,4-DNP derivative"
- ch 8 p15: "2 CH_3-CHO (dil. NaOH equation with Ethanal, 3-Hydroxybutanal, But-2-enal)"
- ch 8 p15: "2CH_3-CO-CH_3 (Ba(OH)_2 equation with Propanone, Ketol, 4-Methylpent-3-en-2-one)"
- ch 8 p16: "CH_3CHO + CH_3CH_2CHO 1. NaOH 2. Delta reaction scheme with But-2-enal and 2-Methylpent-2-enal"
- ch 8 p16: "CH_3-CH=C-CHO CH_3 2-Methylbut-2-enal + CH_3CH_2-CH=CHCHO Pent-2-enal"
- ch 8 p16: "phenyl-CHO + phenyl-C(=O)-CH_3 OH/293K -> 1,3-Diphenylprop-2-en-1-one (Benzalacetophenone) (Major product)"
- ch 8 p16: "H_2C=O + H_2C=O + Conc. KOH -> Delta H-CH_2-OH + H-C(=O)OK (Formaldehyde, Methanol, Potassium formate)"
- ch 8 p16: "2 phenyl-CHO + Conc. NaOH -> Delta phenyl-CH_2OH + phenyl-COONa (Benzaldehyde, Benzyl alcohol, Sodium benzoate)"
- ch 8 p17: "Benzaldehyde reaction scheme with HNO3/H2SO4"
- ch 8 p17: "Intext Questions 8.4 and 8.5"
- ch 8 p20: "CH_3CONH_2 -> ... CH_3COOH + NH_3"
- ch 8 p20: "Benzamide -> Benzoic acid + NH_3"
- ch 8 p20: "R-Mg-X + O=C=O -> ... RCOOH"
- ch 8 p21: "Example 8.5 as displayed reactions (RCOCl, anhydride equations, ester equations)"
- ch 8 p25: "This is because of greater electronegativity... (structures) 4-Methoxy benzoic acid (pK_a = 4.46), Benzoic acid (pK_a = 4.19), 4-Nitrobenzoic acid (pK_a = 3.41)"
- ch 8 p25: "Ethanoic acid, Ethanoic anhydride (equation labels)"
- ch 8 p26: "CH_3COOH + NH_3 <-> CH_3COONH_4 -> CH_3CONH_2"
- ch 8 p26: "COOH + NH_3 <-> COONH_4 -> CONH_2 (Ammonium benzoate to Benzamide)"
- ch 8 p27: "Ammonium phthalate"
- ch 8 p27: "Phthalamide"
- ch 8 p27: "Phthalimide"
- ch 9 p2: "NH_3 -> RNH_2 -> ... Primary(1°) Secondary(2°) Tertiary(3°) diagram"
- ch 9 p4: "(i) diagram/reaction scheme with H_2/Pd/Ethanol"
- ch 9 p4: "(ii) diagram/reaction scheme with Sn+HCl or Fe+HCl"
- ch 9 p4: "NH_3 + R-X reaction scheme with Nucleophile and Substituted ammonium salt labels"
- ch 9 p8: "Aniline + HCl <-> Anilinium chloride"
- ch 9 p11: "I II III IV V structure labels"
- ch 9 p11: "I II structure labels"
- ch 9 p12: "C_2H_5-N: + CH_3-C-Cl -> C_2H_5-N-C-CH_3 + H-Cl (N-Ethylethanamine to N,N-Diethylethanamide reaction)"
- ch 9 p12: "C_6H_5-N-H + CH_3-C-O-C-CH_3 -> C_6H_5-N-C-CH_3 + CH_3COOH (Benzenamine and Ethanoic anhydride reaction)"
- ch 9 p13: "Aniline"
- ch 9 p13: "Benzenediazonium chloride"
- ch 9 p14: "Bromination reaction scheme with structures (Aniline + 3Br2 -> 2,4,6-Tribromoaniline + 3HBr)"
- ch 9 p14: "Aniline to N-Phenylethanamide (Acetanilide) to 4-Bromoaniline reaction scheme with (CH3CO)2O/Pyridine, Br2/CH3COOH, OH- or H+ steps"
- ch 9 p16: "resonance structures diagram showing N≡N: etc"
- ch 9 p18: "ArN_2Cl + H_3PO_2 + H_2O"
- ch 9 p18: "ArN_2Cl + CH_3CH_2OH"
- ch 9 p18: "ArN_2Cl + H_2O"
- ch 9 p18: "Fluoroboric acid diagram equation"
- ch 9 p18: "p-Hydroxyazobenzene (orange dye)"
- ch 9 p18: "p-Aminoazobenzene (yellow dye)"
- ch 9 p19: "Solution (scheme of reactions in Example 9.5)"
- ch 10 p3: "CHO (CHOH)_4 CH_2OH Glucose structure diagram"
- ch 10 p3: "n-hexane structural formula CH_3-CH_2-CH_2-CH_2-CH_2-CH_3"
- ch 10 p4: "CHO (CHOH)_4 CH_2OH Oxidation ... structural equations for saccharic/gluconic acid"
- ch 10 p4: "CHO H OH HO H H OH H OH CH_2OH structures I, II, III"
- ch 10 p4: "(+) – Glyceraldehyde / (–) – Glyceraldehyde structures"
- ch 10 p10: "Intext Questions 10.1 Glucose or sucrose are soluble in water"
- ch 10 p10: "R-CH-COOH / NH_2 alpha-amino acid (R = side chain)"
- ch 10 p15: "All the vitamins are generally available in our diet."
- ch 10 p17: "James Dewey Watson"
- ch 10 p17: "Born in Chicago, Illinois, in 1928, Dr Watson received his Ph.D."

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 6 p6 §6.4.1 ¶1: printed "R–OH -> R–X (red P/X_2, X_2=Br_2,I_2)" · transcribed "R–OH -> R–X (red P/X_2, X_2 = Br_2, I_2)"
- ch 6 p10 §6.6 ¶3: printed "van der Waal forces" · transcribed "van der Waal forces"
- ch 6 p13 §6.7.1 ¶8: printed "Substitution nucleophilic bimolecular" · transcribed "Substitution nucleophilic bimolecular"
- ch 6 p19 §6.7.1 ¶34: printed "(Fig. 6.7). A and B in Fig. 6.5" · transcribed "(Fig. 6.7). A and B in Fig. 6.5"
- ch 6 p24 §6.7.2 ¶5: printed "less s-chararcter" · transcribed "less s-chararcter"
- ch 7 p6 §7.2 ¶9: printed "H_3C-(C_6H_3)(OH)-CH_3" · transcribed "H_3C–(C_6H_3)(OH)–CH_3"
- ch 7 p7 §7.4.1 ¶2: printed "Protonation of alkene" · transcribed "Protonation of alkene"
- ch 7 p7 §7.4.1 ¶4: printed "Protonation of alkene to form carbocation" · transcribed "Protonation of alkene to form carbocation"
- ch 7 p8 §7.4.1 ¶7: printed "H_2O, 3H_2O_2, OH^-" · transcribed "H_2O, 3H_2O_2, OH^-"
- ch 7 p9 §7.4.1 ¶13: printed "[>C - O^- Mg^+ - X | R]" · transcribed "[>C - O^- Mg^+ - X | R]"
- ch 7 p9 §7.4.1 ¶13: printed "--H_2O-->" · transcribed "--H_2O-->"
- ch 7 p12 §7.4.4 ¶2: printed "R–O–H + +C– -> R–O^+H–C– -> R–O–C– + H^+" · transcribed "R–O–H + +C– -> R–O^+H–C– -> R–O–C– + H^+"
- ch 7 p13 §7.4.4 ¶6: printed "Aluminium tert- butoxide" · transcribed "Aluminium tert- butoxide"
- ch 7 p20 §7.4.4 ¶53: printed "phenyl methanoate." · transcribed "phenyl methanoate."
- ch 7 p20 §7.4.4 ¶54: printed "groups determine the" · transcribed "groups determine the"
- ch 7 p25 §7.6.3 ¶3: printed "R–O–R'+ HX" · transcribed "R–O–R' + HX"
- ch 7 p26 §7.6.3 ¶10: printed "CH_3-C(CH_3)(CH_3)-O-CH_3" · transcribed "CH_3-C(CH_3)(CH_3)-O-CH_3"
- ch 7 p27 §7.6.3 ¶13: printed "CH_3–CH_2–CH_2–O–C(CH_3)_2–CH_2CH_3" · transcribed "CH_3–CH_2–CH_2–O–C(CH_3)_2–CH_2CH_3"
- ch 8 p3 §8.1.1 ¶4: printed "named as substituted benzaldehydes" · transcribed "named as substituted benzaldehydes."
- ch 8 p5 §8.1.2 ¶2: printed "and are polar than ethers" · transcribed "and are polar than ethers"
- ch 8 p6 §8.2.2 ¶2: printed "RCH = NH -(H_3O^+)-> RCHO" · transcribed "RCH = NH -(H_3O^+)-> RCHO"
- ch 8 p6 §8.2.2 ¶3: printed "CH_3 — CH=CH-CH_2CH_2-CN -(1. AlH(i-Bu)_2, 2. H_2O)-> CH_3 — CH=CH-CH_2CH_2-CHO" · transcribed "CH_3 — CH=CH-CH_2CH_2-CN -(1. AlH(i-Bu)_2, 2. H_2O)-> CH_3 — CH=CH-CH_2CH_2-CHO"
- ch 8 p7 §8.2.3 ¶1: printed "2 R' — C(=O) — Cl" · transcribed "2 R' — C(=O) — Cl"
- ch 8 p8 §8.2.3 ¶5: printed "C_5H_5NH^+CrO_3Cl^-(PCC)" · transcribed "C_5H_5NH^+CrO_3Cl^- (PCC)"
- ch 8 p8 §8.2.3 ¶5: printed "acetic anhydride/ 1. CrO_2Cl_2 2. HOH" · transcribed "acetic anhydride/ 1. CrO_2Cl_2 2. HOH"
- ch 8 p13 §8.4 ¶20: printed "R—CH_2—C(=O)—CH_2-R'" · transcribed "R—CH_2—C(=O)—CH_2-R'"
- ch 8 p13 §8.4 ¶22: printed "3 ‾OH" · transcribed "3 ‾OH"
- ch 8 p13 §8.4 ¶23: printed "5‾OH" · transcribed "5‾OH"
- ch 8 p22 §8.7 ¶11: printed "Phenylethanoic acid" · transcribed "Phenylethanoic acid"
- ch 8 p23 §8.9.1 ¶1: printed "R-COOH + NaOH -> R-COO^-Na^+ + H_2O" · transcribed "R-COOH + NaOH -> R-COO^-Na^+ + H_2O"
- ch 8 p24 §8.9.1 ¶5: printed "the strongest carboxylic acid" · transcribed "the strongest carboxylic acid"
- ch 8 p24 §8.9.1 ¶7: printed "pK_a is ~16 for ethanol" · transcribed "pK_a is ~16 for ethanol"
- ch 8 p26 §8.9.2 ¶5: printed "heating at high temperature give amides" · transcribed "heating at high temperature give amides"
- ch 8 p27 §8.9.3 ¶1: printed "R-CH(X)-COOH" · transcribed "R-CH(X)-COOH"
- ch 9 p1 §9 ¶3: printed "CH_3–N(CH_3)(CH_3)" · transcribed "CH_3–N(CH_3)(CH_3)"
- ch 9 p7 §9.4 ¶16: printed "CH_2–C(=O)–NH_2" · transcribed "CH_2–C(=O)–NH_2"
- ch 9 p7 §9.5 ¶3: printed "engaged in intermolecular" · transcribed "engaged in intermolecular"
- ch 9 p13 §9.6 ¶29: printed "N-ethylbenzenesulphonyl amide" · transcribed "N-ethylbenzenesulphonyl amide"
- ch 9 p17 §9.9 ¶3: printed "ArN_2^+X^- -> (Cu_2Cl_2/HCl) ArCl + N_2" · transcribed "ArN_2^+X^- -> (Cu_2Cl_2/HCl) ArCl + N_2"
- ch 9 p17 §9.9 ¶5: printed "Gattermann reaction" · transcribed "Gattermann reaction."
- ch 9 p17 §9.9 ¶7: printed "Ar-N_2^+BF_4^-" · transcribed "Ar-N_2^+BF_4^-"
- ch 10 p10 §10.2.1 ¶2: printed "in Greek glykos means sweet" · transcribed "in Greek glykos means sweet"
- ch 10 p10 §10.2.1 ¶2: printed "tyros means cheese" · transcribed "tyros means cheese"
- ch 10 p13 §10.2.3 ¶9: printed "alpha-Helix is one" · transcribed "alpha-Helix is one"
- ch 10 p13 §10.2.3 ¶9: printed "the >C=O of an adjacent" · transcribed "the >C=O of an adjacent"
- ch 10 p15 §10.3.1 ¶1: printed "mol^-1, while" · transcribed "mol^-1, while"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 9 p8 §9.6 ¶1: transcribed "R X" (printed "R <-> H <-> X")

## set aside by code: a heading or a caption listed as omitted text

- ch 6 p4: "Table 6.1: Common and IUPAC Names of some Halides"
- ch 6 p9: "6.2 Why is sulphuric acid not used during the reaction of alcohols with KI?"
- ch 6 p9: "6.3 Write structures of different dihalogen derivatives of propane."
- ch 6 p9: "6.4 Among the isomeric alkanes of molecular formula C_5H_12, identify the one that on photochemical chlorination yields"
- ch 6 p9: "6.5 Draw the structures of major monohalo products in each of the following reactions:"
- ch 6 p10: "Fig. 6.1: Comparison of boiling points of some alkyl halides"
- ch 6 p11: "Table 6.3: Density of Some Haloalkanes"
- ch 6 p11: "6.6 Arrange each set of compounds in order of increasing boiling points."
- ch 6 p12: "Table 6.4: Nucleophilic Substitution of Alkyl Halides"
- ch 6 p18: "Fig 6.4: Some common examples of chiral and achiral objects"
- ch 6 p18: "Fig 6.5: B is mirror image of A; B is rotated by 180° and C is obtained; C is superimposable on A."
- ch 7 p3: "7.2 Identify allylic alcohols in the above examples."
- ch 7 p4: "Table 7.1: Common and IUPAC Names of Some Alcohols"
- ch 7 p6: "7.3 Structures of functional Groups"
- ch 7 p15: "Table 7.3: pKa Values of some Phenols and Ethanol"
- ch 8 p5: "8.1 Write the structures of the following compounds."
- ch 8 p7: "8.2.3 Preparation of Ketones"
- ch 8 p8: "8.2 Write the structures of products of the following reactions"
- ch 8 p12: "Table 8.2: Some N-Substituted Derivatives of Aldehydes and Ketones"
- ch 8 p18: "Table 8.3 Names and Structures of Some Carboxylic Acids"
- ch 8 p19: "8.6 Give the IUPAC names of the following compounds"
- ch 8 p22: "8.7 Show how each of the following compounds can be converted to benzoic acid."
- ch 9 p1: "9.1 Structure of Amines"
- ch 9 p7: "9.3 How will you convert (i) Benzene into aniline"
- ch 10 p2: "Table 10.1: Different Types of Monosaccharides"
- ch 10 p10: "10.2 What are the expected products of hydrolysis of lactose?"
- ch 10 p10: "10.3 How do you explain the absence of aldehyde group in the pentaacetate of D-glucose?"
- ch 10 p12: "10.2.3 Structure of Proteins"
flags set aside by the founder's rulings in ncert-corrections.yaml: 25
verdicts recorded on rows: 582

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 6 | 129 | 129 | 125 | 4 | 0 | 0 | 0 | 96.9% |
| 7 | 159 | 159 | 158 | 1 | 0 | 0 | 0 | 99.4% |
| 8 | 115 | 115 | 110 | 5 | 0 | 0 | 0 | 95.7% |
| 9 | 90 | 90 | 88 | 1 | 0 | 1 | 1 | 96.7% |
| 10 | 89 | 89 | 87 | 2 | 0 | 0 | 0 | 97.8% |
clean for the book (PLAN D15 ✅): 567 of 582 paragraphs, 97.4%
not in the clean share, adjudicate before recording it: 120 page-level start flags, 0 numbered equations the print carries that the rows do not, 137 passages no row carries

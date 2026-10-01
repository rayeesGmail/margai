# margai-pipeline ncert verify

- run: 2026-09-24 07:44 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 f53aaee88c2d182ff459406562a4d68b2404613336c536661d1ae6584c98fb11

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 6 | 2 | 3 | 4 | 0 | 0 | 0 | 0 |
| 2 | 11 | 5 | 5 | 3 | 0 | 0 | 0 | 0 |
| 3 | 9 | 3 | 5 | 2 | 0 | 0 | 0 | 0 |
| 4 | 15 | 5 | 9 | 14 | 0 | 0 | 0 | 0 |
| 5 | 12 | 4 | 7 | 6 | 0 | 0 | 0 | 0 |
| 6 | 6 | 2 | 3 | 2 | 0 | 0 | 0 | 0 |
| 7 | 5 | 2 | 2 | 2 | 0 | 0 | 0 | 0 |
| 8 | 14 | 7 | 6 | 3 | 0 | 0 | 0 | 0 |
| 9 | 13 | 6 | 6 | 5 | 0 | 0 | 0 | 0 |
| 10 | 8 | 4 | 3 | 7 | 0 | 0 | 0 | 0 |
| 11 | 17 | 8 | 8 | 5 | 0 | 0 | 0 | 0 |
| 12 | 11 | 5 | 5 | 5 | 0 | 0 | 0 | 0 |
| 13 | 12 | 6 | 5 | 5 | 0 | 0 | 0 | 0 |
| 14 | 8 | 3 | 4 | 5 | 0 | 0 | 0 | 0 |
| 15 | 10 | 4 | 5 | 4 | 0 | 0 | 0 | 0 |
| 16 | 9 | 4 | 4 | 2 | 0 | 0 | 0 | 0 |
| 17 | 10 | 4 | 5 | 3 | 0 | 0 | 0 | 0 |
| 18 | 7 | 2 | 4 | 5 | 0 | 0 | 0 | 0 |
| 19 | 10 | 4 | 5 | 2 | 0 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 1 p3: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §1 ¶1 "How wonderful is the living world! The"
- ch 1 p4: 7 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "They are Latinised or derived from" · "the second component denotes the specific" · "separately underlined, or printed in italics"
- ch 1 p5: 7 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "illustrated with the example of Mangifera"
- ch 1 p8: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.2.7 ¶4 "Table 1.1 indicates the taxonomic categories to"
- ch 2 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §2 ¶1 "Since the dawn of civilisation, there have"
- ch 2 p6: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.2.5 ¶2 "Amoeboid protozoans: These organisms live in fresh"
- ch 2 p7: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §2.2.5 ¶3 "Flagellated protozoans: The members of this group" · §2.2.5 ¶4 "Ciliated protozoans: These are aquatic, actively moving" · §2.2.5 ¶5 "Sporozoans: This includes diverse organisms that have"
- ch 3 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §3 ¶1 "In the previous chapter, we looked at"
- ch 3 p6: 1 rows start here, the print starts 1 paragraphs — rows the print does not start: §3.2 ¶1 "Bryophytes include the various mosses and liverworts" · printed starts no row begins with: "Figure 3.2 Bryophytes: A liverwort –"
- ch 4 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §4 ¶1 "When you look around, you will observe"
- ch 4 p2: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "(a) Diploblastic (b) Triploblastic ectoderm and"
- ch 4 p4: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.2 ¶2 "The important characteristic features of the different"
- ch 4 p5: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.2.1 ¶2 "Examples: Sycon (Scypha), Spongilla (Fresh water sponge)" · §4.2.2 ¶2 "Examples: Physalia (Portuguese man-of-war), Adamsia (Sea anemone),"
- ch 4 p6: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.2.3 ¶2 "Examples: Pleurobrachia and Ctenoplana." · §4.2.4 ¶2 "Examples: Taenia (Tapeworm), Fasciola (Liver fluke)."
- ch 4 p7: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.2.5 ¶2 "Examples : Ascaris (Roundworm), Wuchereria (Filaria worm)," · §4.2.6 ¶2 "Examples : Nereis, Pheretima (Earthworm) and Hirudinaria"
- ch 4 p8: 6 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.2.7 ¶2 "Examples: Economically important insects – Apis (Honey" · §4.2.7 ¶3 "Vectors – Anopheles, Culex and Aedes (Mosquitoes)" · §4.2.7 ¶4 "Gregarious pest – Locusta (Locust)" · §4.2.7 ¶5 "Living fossil – Limulus (King crab)."
- ch 4 p9: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.2.8 ¶2 "Examples: Pila (Apple snail), Pinctada (Pearl oyster)," · §4.2.9 ¶2 "Examples: Asterias (Star fish), Echinus (Sea urchin)," · §4.2.10 ¶3 "Examples: Balanoglossus and Saccoglossus."
- ch 4 p10: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11 ¶2 "Table 4.1 presents a comparison of salient" · §4.2.11 ¶3 "Phylum Chordata is divided into three subphyla:" · §4.2.11 ¶4 "Subphyla Urochordata and Cephalochordata are often referred" · §4.2.11 ¶5 "Examples: Urochordata – Ascidia, Salpa, Doliolum; Cephalochordata" · §4.2.11 ¶6 "The members of subphylum Vertebrata possess notochord"
- ch 4 p11: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11 ¶7 "The subphylum Vertebrata is further divided as" · §4.2.11.1 ¶1 "All living members of the class Cyclostomata" · §4.2.11.1 ¶2 "Examples: Petromyzon (Lamprey) and Myxine (Hagfish)." · §4.2.11.2 ¶1 "They are marine animals with streamlined body"
- ch 4 p12: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.2.11.2 ¶2 "Examples: Scoliodon (Dog fish), Pristis (Saw fish)," · §4.2.11.3 ¶1 "It includes both marine and fresh water" · §4.2.11.3 ¶2 "Examples: Marine – Exocoetus (Flying fish), Hippocampus" · §4.2.11.4 ¶1 "As the name indicates (Gr., Amphi :" · §4.2.11.4 ¶2 "Examples: Bufo (Toad), Rana (Frog), Hyla (Tree" · printed starts no row begins with: "(a) Hippocampus (b) Catla is usually"
- ch 4 p13: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11.5 ¶1 "The class name refers to their creeping" · §4.2.11.5 ¶2 "Examples: Chelone (Turtle), Testudo (Tortoise), Chameleon (Tree" · §4.2.11.6 ¶1 "The characteristic features of Aves (birds) are"
- ch 4 p14: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11.6 ¶2 "Examples : Corvus (Crow), Columba (Pigeon), Psittacula" · §4.2.11.7 ¶1 "They are found in a variety of"
- ch 4 p15: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11.7 ¶2 "Examples: Oviparous-Ornithorhynchus (Platypus); Viviparous - Macropus (Kangaroo)," · §4.2.11.7 ¶3 "The salient distinguishing features of all phyla"
- ch 5 p3: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §5 ¶1 "The wide range in the structure of"
- ch 5 p9: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.5.1.1 ¶1 "The calyx is the outermost whorl of" · §5.5.1.2 ¶1 "Corolla is composed of petals. Petals are" · §5.5.1.2 ¶2 "Aestivation: The mode of arrangement of sepals"
- ch 5 p10: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.5.1.3 ¶1 "Androecium is composed of stamens. Each stamen"
- ch 5 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.5.1.4 ¶1 "Gynoecium is the female reproductive part of" · §5.5.1.4 ¶2 "Placentation: The arrangement of ovules within the"
- ch 5 p13: 1 rows start here, the print starts 2 paragraphs — printed starts no row begins with: "for female, for bisexual plants, ⊕"
- ch 5 p14: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.9 ¶2 "Vegetative Characters" · §5.9 ¶3 "Plants mostly herbs, shrubs and rarely small" · §5.9 ¶4 "Stem: herbaceous rarely woody, aerial; erect, cylindrical," · §5.9 ¶5 "Leaves: alternate, simple, rarely pinnately compound, exstipulate;"
- ch 6 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §6 ¶1 "You can very easily see the structural"
- ch 6 p4: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.2.2 ¶1 "The anatomy of the monocot root is"
- ch 7 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §7 ¶1 "In the preceding chapters you came across"
- ch 7 p5: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.2.2 ¶7 "Frog has different types of sense organs,"
- ch 8 p3: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §8 ¶1 "When you look around, you see both" · printed starts no row begins with: "(ii) performing the essential functions of"
- ch 8 p4: 6 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "(i) all living organisms are composed" · "(ii) all cells arise from pre-existing"
- ch 8 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.5.3.1 ¶1 "Electron microscopic studies of eukaryotic cells reveal" · §8.5.3.2 ¶1 "Camillo Golgi (1898) first observed densely stained"
- ch 9 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §9 ¶1 "There is a wide diversity in living"
- ch 9 p11: 3 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "In the absence of any enzyme"
- ch 9 p12: 8 rows start here, the print starts 9 paragraphs — rows the print does not start: §9.8.3 ¶3 "The catalytic cycle of an enzyme action" · printed starts no row begins with: "into the active site." · "fitting more tightly around the substrate."
- ch 9 p13: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.8.4 ¶2 "Temperature and pH Enzymes generally function in" · §9.8.4 ¶3 "Concentration of Substrate With the increase in"
- ch 9 p14: 9 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.8.5 ¶2 "Oxidoreductases/dehydrogenases: Enzymes which catalyse oxidoreduction between two" · §9.8.5 ¶3 "Transferases: Enzymes catalysing a transfer of a" · §9.8.5 ¶4 "Hydrolases: Enzymes catalysing hydrolysis of ester, ether," · §9.8.5 ¶5 "Lyases: Enzymes that catalyse removal of groups" · §9.8.5 ¶6 "Isomerases: Includes all enzymes catalysing inter-conversion of" · §9.8.5 ¶7 "Ligases: Enzymes catalysing the linking together of"
- ch 10 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §10 ¶1 "Are you aware that all organisms, even"
- ch 10 p3: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §10.1.1 ¶8 "In animals, mitotic cell division is only" · §10.2.1 ¶2 "Chromosomal material condenses to form compact mitotic" · §10.2.1 ¶3 "Centrosome which had undergone duplication during interphase," · printed starts no row begins with: "do so depending on the requirement" · "chromatids attached together at the centromere."
- ch 10 p4: 3 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "l Spindle fibres attach to kinetochores"
- ch 10 p5: 2 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "identity is lost as discrete elements." · "clusters at each pole forming two"
- ch 10 p6: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §10.4 ¶2 "Meiosis involves two sequential cycles of nuclear" · §10.4 ¶3 "Meiosis I is initiated after the parental" · §10.4 ¶4 "Meiosis involves pairing of homologous chromosomes and" · §10.4 ¶5 "Four haploid cells are formed at the" · printed starts no row begins with: "meiosis I and meiosis II but" · "to produce identical sister chromatids at" · "recombination between non-sister chromatids of homologous"
- ch 10 p7: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.4.1 ¶1 "Prophase I: Prophase of the first meiotic" · §10.4.1 ¶5 "Metaphase I: The bivalent chromosomes align on"
- ch 10 p8: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §10.4.1 ¶6 "Anaphase I: The homologous chromosomes separate, while" · §10.4.1 ¶7 "Telophase I: The nuclear membrane and nucleolus" · §10.4.2 ¶2 "Metaphase II: At this stage, the chromosomes" · §10.4.2 ¶3 "Anaphase II: It begins with the simultaneous"
- ch 11 p3: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §11 ¶1 "All animals including human beings depend on" · §11.1 ¶1 "Let us try to find out what" · printed starts no row begins with: "Transport that transform light energy into" · "ATP and NADPH"
- ch 11 p8: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §11.5 ¶1 "Light reactions or the ‘Photochemical’ phase include" · printed starts no row begins with: "different wavelengths of light. The single"
- ch 11 p12: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "Can we, hence, say that calling"
- ch 11 p14: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §11.7.2 ¶4 "2. Reduction – These are a series" · §11.7.2 ¶5 "3. Regeneration – Regeneration of the CO_2"
- ch 11 p17: 6 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "Based on the above discussion can"
- ch 12 p1: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §12 ¶1 "All of us breathe to live, but" · §12 ¶3 "You may wonder at the several questions"
- ch 12 p4: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §12.2 ¶2 "ATP is utilised at two steps: first" · §12.2 ¶3 "The fructose 1, 6-bisphosphate is split into" · §12.2 ¶4 "Pyruvic acid is then the key product"
- ch 12 p6: 6 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "the hydrogen atoms, leaving three molecules" · "atoms to molecular O with simultaneous"
- ch 12 p9: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §12.5 ¶2 "• There is a sequential, orderly pathway" · §12.5 ¶3 "• The NADH synthesised in glycolysis is" · §12.5 ¶4 "• None of the intermediates in the" · §12.5 ¶5 "• Only glucose is being respired –" · printed starts no row begins with: "ETS pathway following one after another." · "mitochondria and undergoes oxidative phosphorylation." · "any other compound." · "are entering in the pathway at"
- ch 12 p10: 3 rows start here, the print starts 4 paragraphs — rows the print does not start: §12.5 ¶7 "Now let us compare fermentation and aerobic" · printed starts no row begins with: "whereas in aerobic respiration it is" · "the reaction is very vigorous in"
- ch 13 p1: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §13 ¶1 "You have already studied the organisation of" · printed starts no row begins with: "5. Have you ever thought about"
- ch 13 p5: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §13.1.4 ¶5 "The exponential growth can be expressed as" · §13.1.4 ¶6 "Here, r is the relative growth rate" · printed starts no row begins with: "division, only one daughter cell continues"
- ch 13 p9: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §13.4.2 ¶1 "Interestingly, the discovery of each of the"
- ch 13 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §13.4.3.2 ¶1 "Gibberellins are another kind of promotory PGR." · §13.4.3.3 ¶1 "Cytokinins have specific effects on cytokinesis, and"
- ch 13 p12: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.4.3.4 ¶1 "Ethylene is a simple gaseous PGR. It" · §13.4.3.5 ¶1 "As mentioned earlier, abscisic acid (ABA) was"
- ch 14 p3: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §14 ¶1 "As you have read earlier, oxygen (O_2)"
- ch 14 p5: 3 rows start here, the print starts 9 paragraphs — rows the print does not start: §14.1.1 ¶3 "Respiration involves the following steps: (i) Breathing" · printed starts no row begins with: "(i) Breathing or pulmonary ventilation by" · "is drawn in and CO rich" · "(ii) Diffusion of gases (O and" · "(iii) Transport of gases by the" · "(iv) Diffusion of O and CO" · "(v) Utilisation of O by the" · "release of CO (cellular respiration as"
- ch 14 p6: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §14.2.1 ¶1 "Tidal Volume (TV): Volume of air inspired" · §14.2.1 ¶2 "Inspiratory Reserve Volume (IRV): A person can" · §14.2.1 ¶3 "Expiratory Reserve Volume (ERV): A person can"
- ch 14 p7: 9 rows start here, the print starts 3 paragraphs — rows the print does not start: §14.2.1 ¶4 "Residual Volume (RV): Volume of air remaining" · §14.2.1 ¶6 "Inspiratory Capacity (IC): Total volume of air" · §14.2.1 ¶7 "Expiratory Capacity (EC): Total volume of air" · §14.2.1 ¶8 "Functional Residual Capacity (FRC): Volume of air" · §14.2.1 ¶9 "Vital Capacity (VC): The maximum volume of" · §14.2.1 ¶10 "Total Lung Capacity (TLC): Total volume of"
- ch 14 p10: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §14.6 ¶2 "Emphysema is a chronic disorder in which"
- ch 15 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §15 ¶1 "You have learnt that all living cells"
- ch 15 p2: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §15.1.1 ¶2 "Fibrinogens are needed for clotting or coagulation"
- ch 15 p3: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §15.1.3.1 ¶1 "ABO grouping is based on the presence" · printed starts no row begins with: "Blood Group Antigens on Antibodies Donor’s"
- ch 15 p4: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §15.1.3.2 ¶1 "Another antigen, the Rh antigen similar to"
- ch 16 p1: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §16 ¶1 "Animals accumulate ammonia, urea, uric acid, carbon" · §16 ¶2 "The process of excreting ammonia is Ammonotelism."
- ch 16 p2: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §16.1 ¶1 "In humans, the excretory system consists of"
- ch 17 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §17 ¶1 "Movement is one of the significant features"
- ch 17 p6: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §17.2.2 ¶3 "This pulls the attached actin filaments towards"
- ch 17 p10: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §17.3 ¶6 "Pectoral and Pelvic girdle bones help in" · §17.3 ¶7 "Pelvic girdle consists of two coxal bones"
- ch 18 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §18 ¶1 "As you know, the functions of the"
- ch 18 p2: 8 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "(i) the central neural system (CNS)" · "(ii) the peripheral neural system (PNS)"
- ch 18 p3: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §18.3.1 ¶1 "Neurons are excitable cells because their membranes"
- ch 18 p6: 2 rows start here, the print starts 3 paragraphs — printed starts no row begins with: "(iii) hindbrain (Figure 18.4)."
- ch 18 p7: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §18.4.3 ¶2 "Three major regions make up the brain"
- ch 19 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §19 ¶1 "You have already learnt that the neural"
- ch 19 p10: 1 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "(i) peptide, polypeptide, protein hormones (e.g.," · "pituitary hormones, hypothalamic hormones, etc.)" · "(ii) steroids (e.g., cortisol, testosterone, estradiol" · "(iii) iodothyronines (thyroid hormones)" · "(iv) amino-acid derivatives (e.g., epinephrine)."

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
- ch 2 §2 ¶3: figure_refs carries "Table 2.1", which no caption in chapter 2 prints — ruled noise: Table 2.1 is printed; the caption is not collected
- ch 2 §2.3.1 ¶1: figure_refs carries "Figure 2.5a", which no caption in chapter 2 prints — ruled noise: Figure 2.5a: the caption prints the bare Figure 2.5
- ch 2 §2.3.2 ¶1: figure_refs carries "Figure 2.5b", which no caption in chapter 2 prints — ruled noise: Figure 2.5b: the caption prints the bare Figure 2.5
- ch 2 §2.3.3 ¶1: figure_refs carries "Figure 2.5c", which no caption in chapter 2 prints — ruled noise: Figure 2.5c: the caption prints the bare Figure 2.5
- ch 4 §4.2.2 ¶1: figure_refs carries "Figure 4.7", which no caption in chapter 4 prints — ruled noise: Figure 4.7's caption is printed on the row's own page
- ch 4 §4.2.6 ¶1: figure_refs carries "Figure 4.11", which no caption in chapter 4 prints — ruled noise: Figure 4.11's caption is printed on the row's own page
- ch 4 §4.2.10 ¶2: figure_refs carries "Figure 4.15", which no caption in chapter 4 prints — ruled noise: Figure 4.15's caption is printed on the row's own page
- ch 7 §7.2.2 ¶8: figure_refs carries "Figure 7.3", which no caption in chapter 7 prints — ruled noise: Figure 7.3's caption is printed on the row's own page
- ch 9 §9 ¶1: figure_refs carries "Table 9.1", which no caption in chapter 9 prints — ruled noise: Table 9.1 is printed; the caption is not collected
- ch 10 §10.1.1 ¶1: figure_refs carries "Figure 10.1", which no caption in chapter 10 prints — ruled noise: Figure 10.1's caption is printed on the row's own page
- ch 14 §14.2 ¶1: figure_refs carries "Figure 14.2a", which no caption in chapter 14 prints — ruled noise: Figure 14.2a and 14.2b: the page prints both in the running text, the caption the bare label
- ch 14 §14.2 ¶1: figure_refs carries "Figure 14.2b", which no caption in chapter 14 prints — ruled noise: Figure 14.2a and 14.2b: the page prints both in the running text, the caption the bare label
- ch 14 §14.4.1 ¶1: figure_refs carries "Figure 14.5", which no caption in chapter 14 prints — ruled noise: Figure 14.5's caption is printed on the row's own page
- ch 16 §16.1 ¶1: figure_refs carries "Figure 16.2", which no caption in chapter 16 prints — ruled noise: Figure 16.2's caption is printed on the row's own page
- ch 17 §17.2.2 ¶2: figure_refs carries "Figure 17.4", which no caption in chapter 17 prints — ruled noise: Figure 17.4's caption is set in plain Bookman and is not collected
- ch 17 §17.2.2 ¶3: figure_refs carries "Figure 17.4", which no caption in chapter 17 prints — ruled noise: Figure 17.4's caption is set in plain Bookman and is not collected
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (694 rows)
request id: pipeline-ncert-verify-a3d7641b-5612-40e7-b403-379cce30b48b

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 193 | 193 | 0 |
artefact: verify/bio11/en.jsonl (193 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 193 | 740693 | 30316 | 1392192 | 7251 | ₹188.32 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 3 p5 §3.1.3 ¶1: printed "Majority of the red algae" · transcribed "Majority of the red algae are marine"
- ch 4 p13 §4.2.11.6 ¶1: printed "The characteristic features of Aves" · transcribed "The characteristic features of Aves (birds) are the presence of feathers"
- ch 5 p13 §5.8 ¶1: printed "♂ for bisexual plants" · transcribed "⚥ for bisexual plants"
- ch 9 p9 §9.7 ¶2: printed "subunits of α type" · transcribed "subunits of alpha type"
- ch 9 p9 §9.7 ¶2: printed "subunits of β type" · transcribed "subunits of beta type"
- ch 9 p12 §9.8.3 ¶2: printed "E + S <-> ES -> EP -> E + P" · transcribed "E + S ES -> EP -> E + P"
- ch 14 p10 §14.4.2 ¶1: printed "CO_2 + H_2O <-> H_2CO_3 <-> HCO_3^- + H^+" · transcribed "CO_2 + H_2O <-> H_2CO_3 (Carbonic anhydrase) <-> HCO_3^- + H^+ (Carbonic anhydrase)"
- ch 18 p7 §18.4.2 ¶1: printed "passes through" · transcribed "passess through"
- ch 19 p5 §19.2.4 ¶2: printed "Exopthalmic goitre" · transcribed "Exopthalmic goitre is"

## rows the verifier could not find on their page

- ch 11 p9 §11.6.2 ¶1 "Living organisms have the capability of extracting"

## running text the page prints that no row carries

- ch 9 p3: "H_3N^+-CH-COOH <-> H_3N^+-CH-COO^- <-> H_2N-CH-COO^- (A) (B) (C)"
- ch 9 p14: "X Y | | C-C -> X - Y + C = C"
- ch 11 p12: "Can we, hence, say that calling the biosynthetic phase as the dark reaction is a misnomer?"
- ch 11 p12: "How many carbon atoms does it have?"
- ch 11 p15: "In Out Six CO_2 One glucose 18 ATP 18 ADP 12 NADPH 12 NADP"
- ch 11 p17: "Based on the above discussion can you compare plants showing the C_3 and the C_4 pathway?"
- ch 12 p11: "the equation itself following item 3 (glucose oxidation reaction)"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 1 p6 §1.2 ¶3: printed "species as the lowest category" · transcribed "species as the lowest category"
- ch 2 p3 §2 ¶5: printed "green plants had a cellulosic cell wall" · transcribed "green plants had a cellulosic cell wall"
- ch 2 p10 §2.5 ¶1: printed "adults that have a definite shape" · transcribed "adults that have a definite shape"
- ch 3 p7 §3.2 ¶3: printed "provide peat that" · transcribed "provide peat that"
- ch 7 p3 §7.2.1 ¶2: printed "first digit of the fore limbs" · transcribed "first digit of the fore limbs"
- ch 8 p11 §8.5.3.2 ¶1: printed "disc-shaped sacs" · transcribed "disc-shaped sacs"
- ch 8 p15 §8.5.8 ¶2: printed "The axoneme usually has nine doublets" · transcribed "The axoneme usually has nine doublets"
- ch 8 p16 §8.5.10 ¶2: printed "nucleoplasm contains nucleolus and" · transcribed "nucleoplasm contains nucleolus and"
- ch 9 p11 §9.8.2 ¶2: printed "substrate ‘S’ has to bind the enzyme at" · transcribed "substrate ‘S’ has to bind the enzyme at"
- ch 9 p14 §9.8.5 ¶3: printed "S - G + S' -> S + S' - G" · transcribed "S - G + S' -> S + S' - G"
- ch 10 p2 §10.1.1 ¶5: printed "cell is metabolically active and continuously grows but does not replicate" · transcribed "cell is metabolically active and continuously grows but does not replicate"
- ch 11 p8 §11.4 ¶5: printed "photosynthesis takes place in" · transcribed "photosynthesis takes place in"
- ch 11 p8 §11.4 ¶5: printed "for photosyntesis but" · transcribed "for photosyntesis but"
- ch 11 p13 §11.7.2 ¶3: printed "most crucial step of the Calvin cycle" · transcribed "most crucial step of the Calvin cycle"
- ch 11 p17 §11.9 ¶3: printed "binds with O_2 to form one molecule of phosphoglycerate" · transcribed "binds with O_2 to form one molecule of phosphoglycerate"
- ch 11 p17 §11.9 ¶3: printed "pathway, there is neither synthesis of sugars, nor of ATP. Rather it results in the release of CO_2 with the utilisation of ATP. In the photorespiratory pathway there is no synthesis of ATP or NADPH." · transcribed "pathway, there is neither synthesis of sugars, nor of ATP. Rather it results in the release of CO_2 with the utilisation of ATP. In the photorespiratory pathway there is no synthesis of ATP or NADPH."
- ch 13 p5 §13.1.4 ¶5: printed "W_1 = W_0 e^(rt)" · transcribed "W_1 = W_0 e^rt"
- ch 15 p2 §15.1.2 ¶3: printed "primarly" · transcribed "primarly"
- ch 17 p4 §17.2 ¶5: printed "called the 'H' zone" · transcribed "called the ‘H’ zone"
- ch 19 p7 §19.2.8 ¶1: printed "normal human pancreas representing" · transcribed "normal human pancreas representing"
- ch 19 p7 §19.2.8 ¶1: printed "called alpha-cells and beta-cells" · transcribed "called alpha-cells and beta-cells"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 5 p10: "5.5.1.3 Androecium"
- ch 5 p13: "5.8 Semi-Technical Description of a Typical Flowering Plant"
- ch 5 p14: "5.9 SOLANACEAE"
- ch 6 p2: "Figure 6.1 Diagrammatic representation: (a) stomata with bean-shaped guard cells (b) stomata with dumb-bell shaped guard cell"
- ch 8 p16: "Figure 8.11 Structure of nucleus"
- ch 9 p11: "9.8.2 How do Enzymes bring about such High Rates of Chemical Conversions?"
- ch 10 p8: "10.4.2 Meiosis II"
- ch 11 p14: "Figure 11.8 The Calvin cycle proceeds in three stages"
- ch 12 p2: "12.1 Do Plants Breathe?"
- ch 12 p4: "Figure 12.1 Steps of glycolysis"
- ch 12 p6: "12.4 AEROBIC RESPIRATION"
- ch 12 p6: "12.4.1 Tricarboxylic Acid Cycle"
- ch 12 p7: "12.4.2 Electron Transport System (ETS) and Oxidative Phosphorylation"
- ch 13 p9: "13.4 Plant Growth Regulators"
- ch 13 p9: "13.4.1 Characteristics"
- ch 13 p9: "13.4.2 The Discovery of Plant Growth Regulators"
- ch 14 p7: "14.3 Exchange of Gases"
- ch 15 p7: "15.3.2 Cardiac Cycle"
- ch 18 p3: "18.3.1 Generation and Conduction of Nerve Impulse"
- ch 19 p6: "Figure 19.4 Diagrammatic representation of : (a) Adrenal gland above kidney (b) Section showing two parts of adrenal gland"
- ch 19 p7: "19.2.8 Pancreas"
flags set aside by the founder's rulings in ncert-corrections.yaml: 0
verdicts recorded on rows: 694

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 29 | 29 | 29 | 0 | 0 | 0 | 0 | 100.0% |
| 2 | 45 | 45 | 45 | 0 | 0 | 0 | 0 | 100.0% |
| 3 | 28 | 28 | 27 | 1 | 0 | 0 | 0 | 96.4% |
| 4 | 58 | 58 | 57 | 1 | 0 | 0 | 0 | 98.3% |
| 5 | 40 | 40 | 39 | 1 | 0 | 0 | 0 | 97.5% |
| 6 | 13 | 13 | 13 | 0 | 0 | 0 | 0 | 100.0% |
| 7 | 15 | 15 | 15 | 0 | 0 | 0 | 0 | 100.0% |
| 8 | 60 | 60 | 60 | 0 | 0 | 0 | 0 | 100.0% |
| 9 | 50 | 50 | 48 | 2 | 0 | 0 | 0 | 96.0% |
| 10 | 37 | 37 | 37 | 0 | 0 | 0 | 0 | 100.0% |
| 11 | 77 | 77 | 76 | 0 | 1 | 0 | 0 | 98.7% |
| 12 | 38 | 38 | 38 | 0 | 0 | 0 | 0 | 100.0% |
| 13 | 42 | 42 | 42 | 0 | 0 | 0 | 0 | 100.0% |
| 14 | 25 | 25 | 24 | 1 | 0 | 0 | 0 | 96.0% |
| 15 | 32 | 32 | 32 | 0 | 0 | 0 | 0 | 100.0% |
| 16 | 28 | 28 | 28 | 0 | 0 | 0 | 0 | 100.0% |
| 17 | 24 | 24 | 24 | 0 | 0 | 0 | 0 | 100.0% |
| 18 | 20 | 20 | 19 | 1 | 0 | 0 | 0 | 95.0% |
| 19 | 33 | 33 | 32 | 1 | 0 | 0 | 0 | 97.0% |
clean for the book (PLAN D15 ✅): 685 of 694 paragraphs, 98.7%
not in the clean share, adjudicate before recording it: 84 page-level start flags, 0 numbered equations the print carries that the rows do not, 7 passages no row carries

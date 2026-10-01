# margai-pipeline ncert verify

- run: 2026-09-23 08:02 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 3a1d3f141561d000fa3663408064bfc5596efdbdea33f676bcbfcd6e292ddee0

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 6 | 2 | 3 | 6 | 0 | 0 | 0 | 0 |
| 2 | 11 | 4 | 6 | 9 | 1 | 4 | 0 | 0 |
| 3 | 9 | 3 | 5 | 8 | 1 | 0 | 0 | 0 |
| 4 | 15 | 4 | 10 | 15 | 3 | 3 | 0 | 0 |
| 5 | 12 | 3 | 8 | 11 | 0 | 0 | 0 | 0 |
| 6 | 6 | 1 | 4 | 5 | 0 | 0 | 0 | 0 |
| 7 | 5 | 2 | 2 | 4 | 0 | 1 | 0 | 0 |
| 8 | 14 | 6 | 7 | 13 | 0 | 0 | 0 | 0 |
| 9 | 13 | 6 | 6 | 11 | 0 | 1 | 0 | 0 |
| 10 | 8 | 2 | 5 | 8 | 1 | 1 | 0 | 0 |
| 11 | 17 | 8 | 8 | 14 | 2 | 0 | 0 | 0 |
| 12 | 11 | 5 | 5 | 10 | 2 | 0 | 0 | 0 |
| 13 | 12 | 6 | 5 | 11 | 1 | 0 | 0 | 0 |
| 14 | 8 | 3 | 4 | 7 | 2 | 3 | 0 | 0 |
| 15 | 10 | 4 | 5 | 10 | 1 | 0 | 0 | 0 |
| 16 | 9 | 4 | 4 | 7 | 0 | 1 | 0 | 0 |
| 17 | 10 | 3 | 6 | 6 | 0 | 2 | 0 | 0 |
| 18 | 7 | 2 | 4 | 6 | 1 | 0 | 0 | 0 |
| 19 | 10 | 3 | 6 | 10 | 0 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 1 p3: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §1 ¶1 "How wonderful is the living world! The" · §1.1 ¶1 "If you look around you will see"
- ch 1 p4: 7 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "They are Latinised or derived from" · "the second component denotes the specific" · "separately underlined, or printed in italics"
- ch 1 p5: 7 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "illustrated with the example of Mangifera"
- ch 1 p6: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.2 ¶1 "Classification is not a single step process" · §1.2.1 ¶1 "Taxonomic studies consider a group of individual"
- ch 1 p7: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §1.2.2 ¶1 "Genus comprises a group of related species" · §1.2.3 ¶1 "The next category, Family, has a group" · §1.2.4 ¶1 "You have seen earlier that categories like" · §1.2.5 ¶1 "This category includes related orders. For example," · §1.2.6 ¶1 "Classes comprising animals like fishes, amphibians, reptiles,"
- ch 1 p8: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §1.2.7 ¶1 "All animals belonging to various phyla are" · §1.2.7 ¶4 "Table 1.1 indicates the taxonomic categories to"
- ch 2 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §2 ¶1 "Since the dawn of civilisation, there have"
- ch 2 p3: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.1 ¶1 "Bacteria are the sole members of the"
- ch 2 p4: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.1.1 ¶1 "These bacteria are special since they live" · §2.1.2 ¶1 "There are thousands of different eubacteria or"
- ch 2 p5: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.2 ¶1 "All single-celled eukaryotes are placed under Protista," · §2.2.1 ¶1 "This group includes diatoms and golden algae"
- ch 2 p6: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §2.2.2 ¶1 "These organisms are mostly marine and photosynthetic." · §2.2.3 ¶1 "Majority of them are fresh water organisms" · §2.2.4 ¶1 "Slime moulds are saprophytic protists. The body" · §2.2.5 ¶1 "All protozoans are heterotrophs and live as" · §2.2.5 ¶2 "Amoeboid protozoans: These organisms live in fresh"
- ch 2 p7: 7 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.2.5 ¶3 "Flagellated protozoans: The members of this group" · §2.2.5 ¶4 "Ciliated protozoans: These are aquatic, actively moving" · §2.2.5 ¶5 "Sporozoans: This includes diverse organisms that have" · §2.3 ¶1 "The fungi constitute a unique kingdom of"
- ch 2 p8: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §2.3.1 ¶1 "Members of phycomycetes are found in aquatic" · §2.3.2 ¶1 "Commonly known as sac-fungi, the ascomycetes are"
- ch 2 p9: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §2.3.3 ¶1 "Commonly known forms of basidiomycetes are mushrooms," · §2.3.4 ¶1 "Commonly known as imperfect fungi because only"
- ch 2 p10: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §2.4 ¶1 "Kingdom Plantae includes all eukaryotic chlorophyll-containing organisms" · §2.5 ¶1 "This kingdom is characterised by heterotrophic eukaryotic" · §2.6 ¶1 "In the five kingdom classification of Whittaker"
- ch 3 p1: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §3 ¶1 "In the previous chapter, we looked at"
- ch 3 p2: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.1 ¶1 "Algae are chlorophyll-bearing, simple, thalloid, autotrophic and"
- ch 3 p4: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.1.1 ¶1 "The members of chlorophyceae are commonly called" · §3.1.2 ¶1 "The members of phaeophyceae or brown algae"
- ch 3 p5: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.1.3 ¶1 "The members of rhodophyceae are commonly called"
- ch 3 p6: 1 rows start here, the print starts 1 paragraphs — rows the print does not start: §3.2 ¶1 "Bryophytes include the various mosses and liverworts" · printed starts no row begins with: "Figure 3.2 Bryophytes: A liverwort –"
- ch 3 p7: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §3.2 ¶2 "Bryophytes are also called amphibians of the" · §3.2.1 ¶1 "The liverworts grow usually in moist, shady"
- ch 3 p8: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.2.2 ¶1 "The predominant stage of the life cycle" · §3.3 ¶1 "The Pteridophytes include horsetails and ferns. Pteridophytes"
- ch 3 p10: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §3.4 ¶1 "The gymnosperms (gymnos : naked, sperma :"
- ch 4 p1: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §4 ¶1 "When you look around, you will observe" · §4.1 ¶1 "Inspite of differences in structure and form" · §4.1.1 ¶1 "Though all members of Animalia are multicellular,"
- ch 4 p2: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.1.2 ¶1 "Animals can be categorised on the basis" · §4.1.3 ¶1 "Animals in which the cells are arranged" · printed starts no row begins with: "(a) Diploblastic (b) Triploblastic ectoderm and"
- ch 4 p3: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.1.3 ¶2 "Those animals in which the developing embryo" · §4.1.4 ¶1 "Presence or absence of a cavity between" · §4.1.5 ¶1 "In some animals, the body is externally" · §4.1.6 ¶1 "Notochord is a mesodermally derived rod-like structure" · §4.2 ¶1 "The broad classification of Animalia, based on"
- ch 4 p4: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2 ¶2 "The important characteristic features of the different" · §4.2.1 ¶1 "Members of this phylum are commonly known"
- ch 4 p5: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.1 ¶2 "Examples: Sycon (Scypha), Spongilla (Fresh water sponge)" · §4.2.2 ¶1 "They are aquatic, mostly marine, sessile or" · §4.2.2 ¶2 "Examples: Physalia (Portuguese man-of-war), Adamsia (Sea anemone),"
- ch 4 p6: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.3 ¶1 "Ctenophores, commonly known as sea walnuts or" · §4.2.3 ¶2 "Examples: Pleurobrachia and Ctenoplana." · §4.2.4 ¶1 "They have dorso-ventrally flattened body, hence are" · §4.2.4 ¶2 "Examples: Taenia (Tapeworm), Fasciola (Liver fluke)."
- ch 4 p7: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.5 ¶1 "The body of the aschelminthes is circular" · §4.2.5 ¶2 "Examples : Ascaris (Roundworm), Wuchereria (Filaria worm)," · §4.2.6 ¶1 "They may be aquatic (marine and fresh" · §4.2.6 ¶2 "Examples : Nereis, Pheretima (Earthworm) and Hirudinaria"
- ch 4 p8: 6 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.7 ¶1 "This is the largest phylum of Animalia" · §4.2.7 ¶2 "Examples: Economically important insects – Apis (Honey" · §4.2.7 ¶3 "Vectors – Anopheles, Culex and Aedes (Mosquitoes)" · §4.2.7 ¶4 "Gregarious pest – Locusta (Locust)" · §4.2.7 ¶5 "Living fossil – Limulus (King crab)." · §4.2.8 ¶1 "This is the second largest animal phylum"
- ch 4 p9: 7 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.2.8 ¶2 "Examples: Pila (Apple snail), Pinctada (Pearl oyster)," · §4.2.9 ¶1 "These animals have an endoskeleton of calcareous" · §4.2.9 ¶2 "Examples: Asterias (Star fish), Echinus (Sea urchin)," · §4.2.10 ¶1 "Hemichordata was earlier considered as a sub-phylum" · §4.2.10 ¶3 "Examples: Balanoglossus and Saccoglossus." · §4.2.11 ¶1 "Animals belonging to phylum Chordata are fundamentally"
- ch 4 p10: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11 ¶2 "Table 4.1 presents a comparison of salient" · §4.2.11 ¶3 "Phylum Chordata is divided into three subphyla:" · §4.2.11 ¶4 "Subphyla Urochordata and Cephalochordata are often referred" · §4.2.11 ¶5 "Examples: Urochordata – Ascidia, Salpa, Doliolum; Cephalochordata" · §4.2.11 ¶6 "The members of subphylum Vertebrata possess notochord"
- ch 4 p11: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11 ¶7 "The subphylum Vertebrata is further divided as" · §4.2.11.1 ¶1 "All living members of the class Cyclostomata" · §4.2.11.1 ¶2 "Examples: Petromyzon (Lamprey) and Myxine (Hagfish)." · §4.2.11.2 ¶1 "They are marine animals with streamlined body"
- ch 4 p12: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.2.11.2 ¶2 "Examples: Scoliodon (Dog fish), Pristis (Saw fish)," · §4.2.11.3 ¶1 "It includes both marine and fresh water" · §4.2.11.3 ¶2 "Examples: Marine – Exocoetus (Flying fish), Hippocampus" · §4.2.11.4 ¶1 "As the name indicates (Gr., Amphi :" · §4.2.11.4 ¶2 "Examples: Bufo (Toad), Rana (Frog), Hyla (Tree" · printed starts no row begins with: "(a) Hippocampus (b) Catla is usually"
- ch 4 p13: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11.5 ¶1 "The class name refers to their creeping" · §4.2.11.5 ¶2 "Examples: Chelone (Turtle), Testudo (Tortoise), Chameleon (Tree" · §4.2.11.6 ¶1 "The characteristic features of Aves (birds) are"
- ch 4 p14: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11.6 ¶2 "Examples : Corvus (Crow), Columba (Pigeon), Psittacula" · §4.2.11.7 ¶1 "They are found in a variety of"
- ch 4 p15: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.2.11.7 ¶2 "Examples: Oviparous-Ornithorhynchus (Platypus); Viviparous - Macropus (Kangaroo)," · §4.2.11.7 ¶3 "The salient distinguishing features of all phyla"
- ch 5 p3: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §5 ¶1 "The wide range in the structure of" · §5.1 ¶1 "In majority of the dicotyledonous plants, the"
- ch 5 p5: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.1.1 ¶1 "The root is covered at the apex" · §5.2 ¶1 "What are the features that distinguish a" · §5.3 ¶1 "The leaf is a lateral, generally flattened"
- ch 5 p6: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.3.1 ¶1 "The arrangement of veins and the veinlets" · §5.3.2 ¶1 "A leaf is said to be simple,"
- ch 5 p7: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.3.3 ¶1 "Phyllotaxy is the pattern of arrangement of" · §5.4 ¶1 "A flower is a modified shoot wherein"
- ch 5 p8: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.5 ¶1 "The flower is the reproductive unit in"
- ch 5 p9: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.5.1 ¶1 "Each flower normally has four floral whorls," · §5.5.1.1 ¶1 "The calyx is the outermost whorl of" · §5.5.1.2 ¶1 "Corolla is composed of petals. Petals are" · §5.5.1.2 ¶2 "Aestivation: The mode of arrangement of sepals"
- ch 5 p10: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.5.1.3 ¶1 "Androecium is composed of stamens. Each stamen"
- ch 5 p11: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.5.1.4 ¶1 "Gynoecium is the female reproductive part of" · §5.5.1.4 ¶2 "Placentation: The arrangement of ovules within the" · §5.6 ¶1 "The fruit is a characteristic feature of"
- ch 5 p12: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §5.7 ¶1 "The ovules after fertilisation, develop into seeds." · §5.7.1 ¶1 "The outermost covering of a seed is" · §5.7.2 ¶1 "Generally, monocotyledonous seeds are endospermic but some"
- ch 5 p13: 1 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.8 ¶1 "Various morphological features are used to describe" · printed starts no row begins with: "for female, for bisexual plants, ⊕"
- ch 5 p14: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §5.9 ¶1 "It is a large family, commonly called" · §5.9 ¶2 "Vegetative Characters" · §5.9 ¶3 "Plants mostly herbs, shrubs and rarely small" · §5.9 ¶4 "Stem: herbaceous rarely woody, aerial; erect, cylindrical," · §5.9 ¶5 "Leaves: alternate, simple, rarely pinnately compound, exstipulate;"
- ch 6 p1: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §6 ¶1 "You can very easily see the structural" · §6.1 ¶1 "We were discussing types of tissues based" · §6.1.1 ¶1 "The epidermal tissue system forms the outer-most"
- ch 6 p2: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.1.2 ¶1 "All tissues except epidermis and vascular bundles"
- ch 6 p3: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §6.1.3 ¶1 "The vascular system consists of complex tissues," · §6.2 ¶1 "For a better understanding of tissue organisation" · §6.2.1 ¶1 "Look at Figure 6.3 (a), it shows"
- ch 6 p4: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §6.2.2 ¶1 "The anatomy of the monocot root is" · §6.2.3 ¶1 "The transverse section of a typical young"
- ch 6 p6: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §6.2.4 ¶1 "The monocot stem has a sclerenchymatous hypodermis," · §6.2.5 ¶1 "The vertical section of a dorsiventral leaf"
- ch 7 p1: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §7 ¶1 "In the preceding chapters you came across" · §7.1 ¶1 "The basic tissues as you have learnt"
- ch 7 p2: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.2 ¶1 "Frogs can live both on land and" · §7.2.1 ¶1 "Have you ever touched the skin of"
- ch 7 p3: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §7.2.2 ¶1 "The body cavity of frogs accommodate different"
- ch 7 p5: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.2.2 ¶7 "Frog has different types of sense organs,"
- ch 8 p3: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8 ¶1 "When you look around, you see both" · §8.1 ¶1 "Unicellular organisms are capable of (i) independent" · §8.2 ¶1 "In 1838, Matthias Schleiden, a German botanist," · printed starts no row begins with: "(ii) performing the essential functions of"
- ch 8 p4: 6 rows start here, the print starts 7 paragraphs — rows the print does not start: §8.3 ¶1 "You have earlier observed cells in an" · printed starts no row begins with: "(i) all living organisms are composed" · "(ii) all cells arise from pre-existing"
- ch 8 p5: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §8.4 ¶1 "The prokaryotic cells are represented by bacteria,"
- ch 8 p6: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.4.1 ¶1 "Most prokaryotic cells, particularly the bacterial cells,"
- ch 8 p7: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.4.2 ¶1 "In prokaryotes, ribosomes are associated with the" · §8.5 ¶1 "The eukaryotes include all the protists, plants,"
- ch 8 p9: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.5.1 ¶1 "The detailed structure of the membrane was"
- ch 8 p10: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §8.5.2 ¶1 "As you may recall, a non-living rigid" · §8.5.3 ¶1 "While each of the membranous organelles is"
- ch 8 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.5.3.1 ¶1 "Electron microscopic studies of eukaryotic cells reveal" · §8.5.3.2 ¶1 "Camillo Golgi (1898) first observed densely stained"
- ch 8 p12: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.5.3.3 ¶1 "These are membrane bound vesicular structures formed" · §8.5.3.4 ¶1 "The vacuole is the membrane-bound space found" · §8.5.4 ¶1 "Mitochondria (sing.: mitochondrion), unless specifically stained, are"
- ch 8 p13: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §8.5.5 ¶1 "Plastids are found in all plant cells"
- ch 8 p14: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §8.5.6 ¶1 "Ribosomes are the granular structures first observed" · §8.5.7 ¶1 "An elaborate network of filamentous proteinaceous structures"
- ch 8 p15: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §8.5.8 ¶1 "Cilia (sing.: cilium) and flagella (sing.: flagellum)" · §8.5.9 ¶1 "Centrosome is an organelle usually containing two"
- ch 8 p16: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §8.5.10 ¶1 "Nucleus as a cell organelle was first"
- ch 9 p1: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §9 ¶1 "There is a wide diversity in living" · §9.1 ¶1 "We can continue asking in the same"
- ch 9 p5: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.2 ¶1 "The most exciting aspect of chemistry deals" · §9.3 ¶1 "There is one feature common to all"
- ch 9 p6: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.4 ¶1 "Proteins are polypeptides. They are linear chains"
- ch 9 p7: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §9.5 ¶1 "The acid insoluble pellet also has polysaccharides"
- ch 9 p8: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.6 ¶1 "The other type of macromolecule that one" · §9.7 ¶1 "Proteins, as mentioned earlier, are heteropolymers containing"
- ch 9 p9: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §9.8 ¶1 "Almost all enzymes are proteins. There are"
- ch 9 p10: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §9.8.1 ¶1 "How do we understand these enzymes? Let"
- ch 9 p11: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §9.8.2 ¶1 "To understand this we should study enzymes" · printed starts no row begins with: "In the absence of any enzyme"
- ch 9 p12: 8 rows start here, the print starts 8 paragraphs — rows the print does not start: §9.8.3 ¶1 "Each enzyme (E) has a substrate (S)" · §9.8.3 ¶3 "The catalytic cycle of an enzyme action" · printed starts no row begins with: "into the active site." · "fitting more tightly around the substrate."
- ch 9 p13: 5 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.8.4 ¶2 "Temperature and pH Enzymes generally function in" · §9.8.4 ¶3 "Concentration of Substrate With the increase in" · §9.8.4 ¶4 "The activity of an enzyme is also"
- ch 9 p14: 9 rows start here, the print starts 1 paragraphs — rows the print does not start: §9.8.5 ¶1 "Thousands of enzymes have been discovered, isolated" · §9.8.5 ¶2 "Oxidoreductases/dehydrogenases: Enzymes which catalyse oxidoreduction between two" · §9.8.5 ¶3 "Transferases: Enzymes catalysing a transfer of a" · §9.8.5 ¶4 "Hydrolases: Enzymes catalysing hydrolysis of ester, ether," · §9.8.5 ¶5 "Lyases: Enzymes that catalyse removal of groups" · §9.8.5 ¶6 "Isomerases: Includes all enzymes catalysing inter-conversion of" · §9.8.5 ¶7 "Ligases: Enzymes catalysing the linking together of" · §9.8.6 ¶1 "Enzymes are composed of one or several"
- ch 10 p1: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §10 ¶1 "Are you aware that all organisms, even" · §10.1 ¶1 "Cell division is a very important process"
- ch 10 p2: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §10.1.1 ¶1 "A typical eukaryotic cell cycle is illustrated"
- ch 10 p3: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.1.1 ¶8 "In animals, mitotic cell division is only" · §10.2.1 ¶1 "Prophase which is the first stage of" · §10.2.1 ¶2 "Chromosomal material condenses to form compact mitotic" · §10.2.1 ¶3 "Centrosome which had undergone duplication during interphase," · printed starts no row begins with: "do so depending on the requirement" · "chromatids attached together at the centromere."
- ch 10 p4: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §10.2.2 ¶1 "The complete disintegration of the nuclear envelope" · §10.2.3 ¶1 "At the onset of anaphase, each chromosome" · printed starts no row begins with: "l Spindle fibres attach to kinetochores"
- ch 10 p5: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §10.2.4 ¶1 "At the beginning of the final stage" · §10.2.5 ¶1 "Mitosis accomplishes not only the segregation of" · printed starts no row begins with: "identity is lost as discrete elements." · "clusters at each pole forming two"
- ch 10 p6: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §10.3 ¶1 "Mitosis or the equational division is usually" · §10.4 ¶1 "The production of offspring by sexual reproduction" · §10.4 ¶2 "Meiosis involves two sequential cycles of nuclear" · §10.4 ¶3 "Meiosis I is initiated after the parental" · §10.4 ¶4 "Meiosis involves pairing of homologous chromosomes and" · §10.4 ¶5 "Four haploid cells are formed at the" · printed starts no row begins with: "meiosis I and meiosis II but" · "to produce identical sister chromatids at" · "recombination between non-sister chromatids of homologous"
- ch 10 p7: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.4.1 ¶1 "Prophase I: Prophase of the first meiotic" · §10.4.1 ¶5 "Metaphase I: The bivalent chromosomes align on"
- ch 10 p8: 5 rows start here, the print starts 0 paragraphs — rows the print does not start: §10.4.1 ¶6 "Anaphase I: The homologous chromosomes separate, while" · §10.4.1 ¶7 "Telophase I: The nuclear membrane and nucleolus" · §10.4.2 ¶1 "Prophase II: Meiosis II is initiated immediately" · §10.4.2 ¶2 "Metaphase II: At this stage, the chromosomes" · §10.4.2 ¶3 "Anaphase II: It begins with the simultaneous"
- ch 11 p3: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §11 ¶1 "All animals including human beings depend on" · §11.1 ¶1 "Let us try to find out what" · printed starts no row begins with: "Transport that transform light energy into" · "ATP and NADPH"
- ch 11 p4: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §11.2 ¶1 "It is interesting to learn about those"
- ch 11 p6: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §11.3 ¶1 "You would of course answer: in 'the"
- ch 11 p7: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §11.4 ¶1 "Looking at plants have you ever wondered"
- ch 11 p8: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §11.5 ¶1 "Light reactions or the ‘Photochemical’ phase include" · §11.6 ¶1 "In photosystem II the reaction centre chlorophyll"
- ch 11 p9: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §11.6.1 ¶1 "You would then ask, How does PS" · §11.6.2 ¶1 "Living organisms have the capability of extracting"
- ch 11 p10: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §11.6.3 ¶1 "Let us now try and understand how"
- ch 11 p12: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §11.7 ¶1 "We learnt that the products of light" · printed starts no row begins with: "Can we, hence, say that calling"
- ch 11 p13: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §11.7.1 ¶1 "Let us now ask ourselves a question" · §11.7.2 ¶1 "Calvin and his co-workers then worked out"
- ch 11 p14: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §11.7.2 ¶4 "2. Reduction – These are a series" · §11.7.2 ¶5 "3. Regeneration – Regeneration of the CO_2"
- ch 11 p15: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §11.8 ¶1 "Plants that are adapted to dry tropical"
- ch 11 p17: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §11.9 ¶1 "Let us try and understand one more" · printed starts no row begins with: "Based on the above discussion can"
- ch 11 p19: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §11.10 ¶1 "An understanding of the factors that affect" · §11.10.1 ¶1 "We need to distinguish between light quality,"
- ch 11 p20: 6 rows start here, the print starts 3 paragraphs — rows the print does not start: §11.10.2 ¶1 "Carbon dioxide is the major limiting factor" · §11.10.3 ¶1 "The dark reactions being enzymatic are temperature" · §11.10.4 ¶1 "Even though water is one of the"
- ch 12 p1: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §12 ¶1 "All of us breathe to live, but" · §12 ¶3 "You may wonder at the several questions"
- ch 12 p2: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §12.1 ¶1 "Well, the answer to this question is"
- ch 12 p3: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §12.2 ¶1 "The term glycolysis has originated from the"
- ch 12 p4: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §12.2 ¶2 "ATP is utilised at two steps: first" · §12.2 ¶3 "The fructose 1, 6-bisphosphate is split into" · §12.2 ¶4 "Pyruvic acid is then the key product"
- ch 12 p5: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §12.2 ¶5 "There are three major ways in which" · §12.3 ¶1 "In fermentation, say by yeast, the incomplete"
- ch 12 p6: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §12.4 ¶1 "For aerobic respiration to take place within" · §12.4.1 ¶1 "The TCA cycle starts with the condensation" · printed starts no row begins with: "the hydrogen atoms, leaving three molecules" · "atoms to molecular O with simultaneous"
- ch 12 p7: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §12.4.2 ¶1 "The following steps in the respiratory process"
- ch 12 p9: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §12.5 ¶1 "It is possible to make calculations of" · §12.5 ¶2 "• There is a sequential, orderly pathway" · §12.5 ¶3 "• The NADH synthesised in glycolysis is" · §12.5 ¶4 "• None of the intermediates in the" · §12.5 ¶5 "• Only glucose is being respired –" · printed starts no row begins with: "ETS pathway following one after another." · "mitochondria and undergoes oxidative phosphorylation." · "any other compound." · "are entering in the pathway at"
- ch 12 p10: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §12.5 ¶7 "Now let us compare fermentation and aerobic" · §12.6 ¶1 "Glucose is the favoured substrate for respiration." · printed starts no row begins with: "whereas in aerobic respiration it is" · "the reaction is very vigorous in"
- ch 12 p11: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §12.7 ¶1 "Let us now look at another aspect"
- ch 13 p1: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §13 ¶1 "You have already studied the organisation of" · printed starts no row begins with: "5. Have you ever thought about"
- ch 13 p2: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §13.1 ¶1 "Growth is regarded as one of the" · §13.1.1 ¶1 "Plant growth is unique because plants retain"
- ch 13 p3: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §13.1.2 ¶1 "Growth, at a cellular level, is principally" · §13.1.3 ¶1 "The period of growth is generally divided"
- ch 13 p4: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §13.1.4 ¶1 "The increased growth per unit time is"
- ch 13 p5: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §13.1.4 ¶5 "The exponential growth can be expressed as" · §13.1.4 ¶6 "Here, r is the relative growth rate" · printed starts no row begins with: "division, only one daughter cell continues"
- ch 13 p6: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.1.5 ¶1 "Why do you not try to write"
- ch 13 p7: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.2 ¶1 "The cells derived from root apical and" · §13.3 ¶1 "Development is a term that includes all"
- ch 13 p9: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.4.1 ¶1 "The plant growth regulators (PGRs) are small," · §13.4.2 ¶1 "Interestingly, the discovery of each of the"
- ch 13 p10: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §13.4.3.1 ¶1 "Auxins (from Greek ‘auxein’ : to grow)"
- ch 13 p11: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §13.4.3.2 ¶1 "Gibberellins are another kind of promotory PGR." · §13.4.3.3 ¶1 "Cytokinins have specific effects on cytokinesis, and"
- ch 13 p12: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.4.3.4 ¶1 "Ethylene is a simple gaseous PGR. It" · §13.4.3.5 ¶1 "As mentioned earlier, abscisic acid (ABA) was"
- ch 14 p3: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §14 ¶1 "As you have read earlier, oxygen (O_2)" · §14.1 ¶1 "Mechanisms of breathing vary among different groups"
- ch 14 p4: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §14.1.1 ¶1 "We have a pair of external nostrils"
- ch 14 p5: 3 rows start here, the print starts 8 paragraphs — rows the print does not start: §14.1.1 ¶3 "Respiration involves the following steps: (i) Breathing" · §14.2 ¶1 "Breathing involves two stages : inspiration during" · printed starts no row begins with: "(i) Breathing or pulmonary ventilation by" · "is drawn in and CO rich" · "(ii) Diffusion of gases (O and" · "(iii) Transport of gases by the" · "(iv) Diffusion of O and CO" · "(v) Utilisation of O by the" · "release of CO (cellular respiration as"
- ch 14 p6: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §14.2.1 ¶1 "Tidal Volume (TV): Volume of air inspired" · §14.2.1 ¶2 "Inspiratory Reserve Volume (IRV): A person can" · §14.2.1 ¶3 "Expiratory Reserve Volume (ERV): A person can"
- ch 14 p7: 9 rows start here, the print starts 2 paragraphs — rows the print does not start: §14.2.1 ¶4 "Residual Volume (RV): Volume of air remaining" · §14.2.1 ¶6 "Inspiratory Capacity (IC): Total volume of air" · §14.2.1 ¶7 "Expiratory Capacity (EC): Total volume of air" · §14.2.1 ¶8 "Functional Residual Capacity (FRC): Volume of air" · §14.2.1 ¶9 "Vital Capacity (VC): The maximum volume of" · §14.2.1 ¶10 "Total Lung Capacity (TLC): Total volume of" · §14.3 ¶1 "Alveoli are the primary sites of exchange"
- ch 14 p9: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §14.4 ¶1 "Blood is the medium of transport for" · §14.4.1 ¶1 "Haemoglobin is a red coloured iron containing" · §14.4.2 ¶1 "CO_2 is carried by haemoglobin as carbamino-haemoglobin"
- ch 14 p10: 4 rows start here, the print starts 1 paragraphs — rows the print does not start: §14.5 ¶1 "Human beings have a significant ability to" · §14.6 ¶1 "Asthma is a difficulty in breathing causing" · §14.6 ¶2 "Emphysema is a chronic disorder in which"
- ch 15 p1: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §15 ¶1 "You have learnt that all living cells" · §15.1.1 ¶1 "Plasma is a straw coloured, viscous fluid"
- ch 15 p2: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §15.1.1 ¶2 "Fibrinogens are needed for clotting or coagulation" · §15.1.2 ¶2 "Erythrocytes or red blood cells (RBC) are"
- ch 15 p3: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §15.1.3 ¶1 "As you know, blood of human beings" · §15.1.3.1 ¶1 "ABO grouping is based on the presence" · printed starts no row begins with: "Blood Group Antigens on Antibodies Donor’s"
- ch 15 p4: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §15.1.3.2 ¶1 "Another antigen, the Rh antigen similar to" · §15.1.4 ¶1 "You know that when you cut your"
- ch 15 p5: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §15.2 ¶1 "As the blood passes through the capillaries" · §15.3 ¶1 "The circulatory patterns are of two types"
- ch 15 p6: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §15.3.1 ¶1 "Human circulatory system, also called the blood"
- ch 15 p7: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §15.3.2 ¶1 "How does the heart function? Let us"
- ch 15 p8: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §15.3.3 ¶1 "You are probably familiar with this scene"
- ch 15 p9: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §15.4 ¶1 "The blood flows strictly by a fixed"
- ch 15 p10: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §15.5 ¶1 "Normal activities of the heart are regulated" · §15.6 ¶1 "High Blood Pressure (Hypertension): Hypertension is the"
- ch 16 p1: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §16 ¶1 "Animals accumulate ammonia, urea, uric acid, carbon" · §16 ¶2 "The process of excreting ammonia is Ammonotelism."
- ch 16 p2: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §16.1 ¶1 "In humans, the excretory system consists of"
- ch 16 p4: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §16.2 ¶1 "Urine formation involves three main processes namely,"
- ch 16 p5: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §16.3 ¶1 "Proximal Convoluted Tubule (PCT): PCT is lined"
- ch 16 p6: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §16.4 ¶1 "Mammals have the ability to produce a"
- ch 16 p8: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §16.5 ¶1 "The functioning of the kidneys is efficiently" · §16.6 ¶1 "Urine formed by the nephrons is ultimately"
- ch 16 p9: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §16.7 ¶1 "Other than the kidneys, lungs, liver and" · §16.8 ¶1 "Malfunctioning of kidneys can lead to accumulation"
- ch 17 p1: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §17 ¶1 "Movement is one of the significant features" · §17.1 ¶1 "Cells of the human body exhibit three"
- ch 17 p2: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §17.2 ¶1 "You have studied in Chapter 8 that"
- ch 17 p5: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §17.2.1 ¶1 "Each actin (thin) filament is made of" · §17.2.2 ¶1 "Mechanism of muscle contraction is best explained"
- ch 17 p6: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §17.2.2 ¶3 "This pulls the attached actin filaments towards"
- ch 17 p8: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §17.3 ¶1 "Skeletal system consists of a framework of"
- ch 17 p10: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §17.3 ¶6 "Pectoral and Pelvic girdle bones help in" · §17.3 ¶7 "Pelvic girdle consists of two coxal bones" · §17.4 ¶1 "Joints are essential for all types of"
- ch 18 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §18 ¶1 "As you know, the functions of the"
- ch 18 p2: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §18.1 ¶1 "The neural system of all animals is" · §18.2 ¶1 "The human neural system is divided into" · §18.3 ¶1 "A neuron is a microscopic structure composed" · printed starts no row begins with: "(i) the central neural system (CNS)" · "(ii) the peripheral neural system (PNS)"
- ch 18 p3: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §18.3.1 ¶1 "Neurons are excitable cells because their membranes"
- ch 18 p5: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §18.3.2 ¶1 "A nerve impulse is transmitted from one"
- ch 18 p6: 2 rows start here, the print starts 2 paragraphs — rows the print does not start: §18.4 ¶1 "The brain is the central information processing" · printed starts no row begins with: "(iii) hindbrain (Figure 18.4)."
- ch 18 p7: 4 rows start here, the print starts 0 paragraphs — rows the print does not start: §18.4.1 ¶1 "The forebrain consists of cerebrum, thalamus and" · §18.4.2 ¶1 "The midbrain is located between the thalamus/hypothalamus" · §18.4.3 ¶1 "The hindbrain comprises pons, cerebellum and medulla" · §18.4.3 ¶2 "Three major regions make up the brain"
- ch 19 p1: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §19 ¶1 "You have already learnt that the neural" · §19.1 ¶1 "Endocrine glands lack ducts and are hence,"
- ch 19 p2: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §19.2 ¶1 "The endocrine glands and hormone producing diffused" · §19.2.1 ¶1 "As you know, the hypothalamus is the"
- ch 19 p3: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §19.2.2 ¶1 "The pituitary gland is located in a"
- ch 19 p4: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §19.2.3 ¶1 "The pineal gland is located on the" · §19.2.4 ¶1 "The thyroid gland is composed of two"
- ch 19 p5: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §19.2.5 ¶1 "In humans, four parathyroid glands are present" · §19.2.6 ¶1 "The thymus gland is a lobular structure"
- ch 19 p6: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §19.2.7 ¶1 "Our body has one pair of adrenal"
- ch 19 p7: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §19.2.8 ¶1 "Pancreas is a composite gland (Figure 19.1)"
- ch 19 p8: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §19.2.9 ¶1 "A pair of testis is present in" · §19.2.10 ¶1 "Females have a pair of ovaries located"
- ch 19 p9: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §19.3 ¶1 "Now you know about the endocrine glands" · §19.4 ¶1 "Hormones produce their effects on target tissues"
- ch 19 p10: 1 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "(i) peptide, polypeptide, protein hormones (e.g.," · "pituitary hormones, hypothalamic hormones, etc.)" · "(ii) steroids (e.g., cortisol, testosterone, estradiol" · "(iii) iodothyronines (thyroid hormones)" · "(iv) amino-acid derivatives (e.g., epinephrine)."

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

- ch 2 §2.4 ¶1 starts a paragraph at the top of p10, but the print continues p9's: "Kingdom Plantae includes all eukaryotic chlorophyll-containing"
- ch 3 §3.2 ¶2 starts a paragraph at the top of p7, but the print continues p6's: "Bryophytes are also called amphibians of"
- ch 4 §4.1.3 ¶2 starts a paragraph at the top of p3, but the print continues p2's: "Those animals in which the developing"
- ch 4 §4.2.1 ¶2 starts a paragraph at the top of p5, but the print continues p4's: "Examples: Sycon (Scypha), Spongilla (Fresh water"
- ch 4 §4.2.11 ¶7 starts a paragraph at the top of p11, but the print continues p10's: "The subphylum Vertebrata is further divided"
- ch 10 §10.4.1 ¶6 starts a paragraph at the top of p8, but the print continues p7's: "Anaphase I: The homologous chromosomes separate,"
- ch 11 §11.4 ¶1 starts a paragraph at the top of p7, but the print continues p6's: "Looking at plants have you ever"
- ch 11 §11.10 ¶1 starts a paragraph at the top of p19, but the print continues p17's: "An understanding of the factors that"
- ch 12 §12.2 ¶5 starts a paragraph at the top of p5, but the print continues p4's: "There are three major ways in"
- ch 12 §12.7 ¶1 starts a paragraph at the top of p11, but the print continues p10's: "Let us now look at another"
- ch 13 §13.1 ¶1 starts a paragraph at the top of p2, but the print continues p1's: "Growth is regarded as one of"
- ch 14 §14.2.1 ¶4 starts a paragraph at the top of p7, but the print continues p6's: "Residual Volume (RV): Volume of air"
- ch 14 §14.4 ¶1 starts a paragraph at the top of p9, but the print continues p8's: "Blood is the medium of transport"
- ch 15 §15.2 ¶1 starts a paragraph at the top of p5, but the print continues p4's: "As the blood passes through the"
- ch 18 §18.1 ¶1 starts a paragraph at the top of p2, but the print continues p1's: "The neural system of all animals"

## figure_refs against the paragraph and the chapter's captions

- ch 2 §2 ¶3: figure_refs carries "Table 2.1", which no caption in chapter 2 prints
- ch 2 §2.3.1 ¶1: figure_refs carries "Figure 2.5a", which no caption in chapter 2 prints
- ch 2 §2.3.2 ¶1: figure_refs carries "Figure 2.5b", which no caption in chapter 2 prints
- ch 2 §2.3.3 ¶1: figure_refs carries "Figure 2.5c", which no caption in chapter 2 prints
- ch 4 §4.2.2 ¶1: figure_refs carries "Figure 4.7", which no caption in chapter 4 prints
- ch 4 §4.2.6 ¶1: figure_refs carries "Figure 4.11", which no caption in chapter 4 prints
- ch 4 §4.2.10 ¶2: figure_refs carries "Figure 4.15", which no caption in chapter 4 prints
- ch 7 §7.2.2 ¶8: figure_refs carries "Figure 7.3", which no caption in chapter 7 prints
- ch 9 §9 ¶1: figure_refs carries "Table 9.1", which no caption in chapter 9 prints
- ch 10 §10.1.1 ¶1: figure_refs carries "Figure 10.1", which no caption in chapter 10 prints
- ch 14 §14.2 ¶1: figure_refs carries "Figure 14.2a", which no caption in chapter 14 prints
- ch 14 §14.2 ¶1: figure_refs carries "Figure 14.2b", which no caption in chapter 14 prints
- ch 14 §14.4.1 ¶1: figure_refs carries "Figure 14.5", which no caption in chapter 14 prints
- ch 16 §16.1 ¶1: figure_refs carries "Figure 16.2", which no caption in chapter 16 prints
- ch 17 §17.2.2 ¶2: figure_refs carries "Figure 17.4", which no caption in chapter 17 prints
- ch 17 §17.2.2 ¶3: figure_refs carries "Figure 17.4", which no caption in chapter 17 prints

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
none
no model was called: add --read-pages for the second read
no second read in the artefact yet: verify/bio11/en.jsonl does not exist

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 29 | 0 | 0 | 0 | 0 | 0 | 0 | — (29 rows without a verdict) |
| 2 | 45 | 0 | 0 | 0 | 0 | 0 | 5 | — (45 rows without a verdict) |
| 3 | 29 | 0 | 0 | 0 | 0 | 0 | 1 | — (29 rows without a verdict) |
| 4 | 59 | 0 | 0 | 0 | 0 | 0 | 6 | — (59 rows without a verdict) |
| 5 | 40 | 0 | 0 | 0 | 0 | 0 | 0 | — (40 rows without a verdict) |
| 6 | 13 | 0 | 0 | 0 | 0 | 0 | 0 | — (13 rows without a verdict) |
| 7 | 15 | 0 | 0 | 0 | 0 | 0 | 1 | — (15 rows without a verdict) |
| 8 | 60 | 0 | 0 | 0 | 0 | 0 | 0 | — (60 rows without a verdict) |
| 9 | 50 | 0 | 0 | 0 | 0 | 0 | 1 | — (50 rows without a verdict) |
| 10 | 37 | 0 | 0 | 0 | 0 | 0 | 2 | — (37 rows without a verdict) |
| 11 | 77 | 0 | 0 | 0 | 0 | 0 | 2 | — (77 rows without a verdict) |
| 12 | 39 | 0 | 0 | 0 | 0 | 0 | 2 | — (39 rows without a verdict) |
| 13 | 42 | 0 | 0 | 0 | 0 | 0 | 1 | — (42 rows without a verdict) |
| 14 | 25 | 0 | 0 | 0 | 0 | 0 | 4 | — (25 rows without a verdict) |
| 15 | 32 | 0 | 0 | 0 | 0 | 0 | 1 | — (32 rows without a verdict) |
| 16 | 28 | 0 | 0 | 0 | 0 | 0 | 1 | — (28 rows without a verdict) |
| 17 | 24 | 0 | 0 | 0 | 0 | 0 | 2 | — (24 rows without a verdict) |
| 18 | 20 | 0 | 0 | 0 | 0 | 0 | 1 | — (20 rows without a verdict) |
| 19 | 33 | 0 | 0 | 0 | 0 | 0 | 0 | — (33 rows without a verdict) |
clean for the book (PLAN D15 ✅): not computed — some rows have no verdict yet
not in the clean share, adjudicate before recording it: 171 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries

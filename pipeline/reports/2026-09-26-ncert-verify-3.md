# margai-pipeline ncert verify

- run: 2026-09-26 11:27 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 13 chapters of bio12 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 5af92cd4c7707f328d80748ba56788dc706f24414eceeeb006220dd8b54a1b02

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 1 | 21 | 18 | 2 | 9 | 0 | 0 | 0 | 0 |
| 2 | 13 | 10 | 2 | 3 | 0 | 0 | 0 | 0 |
| 3 | 8 | 7 | 0 | 3 | 0 | 0 | 0 | 0 |
| 4 | 23 | 17 | 5 | 10 | 0 | 0 | 0 | 0 |
| 5 | 29 | 27 | 1 | 19 | 0 | 0 | 0 | 0 |
| 6 | 16 | 14 | 1 | 1 | 0 | 0 | 0 | 0 |
| 7 | 19 | 17 | 1 | 8 | 0 | 0 | 0 | 0 |
| 8 | 9 | 5 | 3 | 1 | 0 | 0 | 0 | 0 |
| 9 | 13 | 12 | 0 | 6 | 0 | 0 | 0 | 0 |
| 10 | 9 | 8 | 0 | 7 | 1 | 0 | 0 | 0 |
| 11 | 13 | 11 | 1 | 7 | 0 | 0 | 0 | 0 |
| 12 | 10 | 9 | 0 | 3 | 0 | 0 | 0 | 0 |
| 13 | 10 | 9 | 0 | 8 | 0 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 1 p3: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §1 ¶1 "Are we not lucky that plants reproduce"
- ch 1 p5: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §1.2.1 ¶4 "Structure of microsporangium: In a transverse section," · §1.2.1 ¶6 "Microsporogenesis : As the anther develops, the"
- ch 1 p6: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.2.1 ¶8 "Pollen grain: The pollen grains represent the"
- ch 1 p9: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.2.2 ¶2 "The Megasporangium (Ovule) : Let us familiarise" · §1.2.2 ¶4 "Megasporogenesis : The process of formation of"
- ch 1 p10: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §1.2.2 ¶5 "Female gametophyte : In a majority of"
- ch 1 p11: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §1.2.3 ¶3 "Kinds of Pollination : Depending on the"
- ch 1 p12: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.2.3 ¶5 "(ii) Geitonogamy – Transfer of pollen grains" · §1.2.3 ¶6 "(iii) Xenogamy – Transfer of pollen grains" · §1.2.3 ¶7 "Agents of Pollination : Plants use two" · printed starts no row begins with: "(b) Cross pollinated flowers; enormous amount"
- ch 1 p14: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §1.2.3 ¶13 "Majority of insect-pollinated flowers are large, colourful,"
- ch 1 p15: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §1.2.3 ¶16 "Outbreeding Devices : Majority of flowering plants" · §1.2.3 ¶17 "Pollen-pistil Interaction : Pollination does not guarantee"
- ch 2 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §2 ¶1 "As you are aware, humans are sexually"
- ch 2 p2: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.1 ¶2 "The testes are situated outside the abdominal"
- ch 2 p12: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §2.6 ¶3 "Immediately after implantation, the inner cell mass"
- ch 3 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §3 ¶1 "You have learnt about human reproductive system"
- ch 3 p4: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §3.2 ¶5 "In barrier methods, ovum and sperms are"
- ch 3 p7: 3 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "(i) Avoid sex with unknown partners/multiple" · "(ii) Always try to use condoms" · "(iii) In case of doubt, one" · "detection and get complete treatment if"
- ch 4 p3: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §4 ¶1 "Have you ever wondered why an elephant"
- ch 4 p8: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §4.2 ¶11 "The 1/4 : 1/2 : 1/4 ratio" · §4.2 ¶14 "Using Punnett square, try to find out" · §4.2 ¶15 "What ratio did you get?" · §4.2 ¶16 "Using the genotypes of this cross, can" · printed starts no row begins with: "The 1/4 : 1/2 : 1/4"
- ch 4 p9: 4 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "(ii) Factors occur in pairs." · "(iii) In a dissimilar pair of" · "(dominant) the other (recessive)."
- ch 4 p10: 3 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.2.2.1 ¶1 "When experiments on peas were repeated using" · §4.2.2.1 ¶2 "Explanation of the concept of dominance: What" · §4.2.2.1 ¶3 "Let’s take an example of a gene" · printed starts no row begins with: "(i) the normal/less efficient enzyme, or" · "(ii) a non-functional enzyme, or" · "(iii) no enzyme at all"
- ch 4 p11: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.2.2.2 ¶1 "Till now we were discussing crosses where"
- ch 4 p22: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.7 ¶3 "In addition to the above, mutation also" · §4.7 ¶4 "The mechanism of mutation is beyond the"
- ch 4 p23: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §4.8.2 ¶2 "Colour Blindness : It is a sex-linked"
- ch 4 p24: 2 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.8.2 ¶3 "Haemophilia : This sex linked recessive disease," · §4.8.2 ¶4 "Sickle-cell anaemia : This is an autosome"
- ch 4 p25: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.8.2 ¶5 "Phenylketonuria : This inborn error of metabolism" · §4.8.2 ¶6 "Thalassemia : This is also an autosome-linked"
- ch 4 p26: 3 rows start here, the print starts 0 paragraphs — rows the print does not start: §4.8.3 ¶4 "Down's Syndrome : The cause of this" · §4.8.3 ¶5 "Klinefelter's Syndrome : This genetic disorder is" · §4.8.3 ¶6 "Turner's Syndrome : Such a disorder is"
- ch 5 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §5 ¶1 "In the previous chapter, you have learnt"
- ch 5 p3: 7 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.1.1 ¶5 "The salient features of the Double-helix structure" · printed starts no row begins with: "(i) It is made of two" · "constituted by sugar-phosphate, and the bases" · "chain has the polarity 5 à3" · "strands of the helix (Figure 5.2)."
- ch 5 p6: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §5.2 ¶2 "In 1928, Frederick Griffith, in a series"
- ch 5 p7: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §5.2 ¶6 "Prior to the work of Oswald Avery,"
- ch 5 p9: 9 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.2.2 ¶2 "Can you recall the two chemical differences" · §5.2.2 ¶3 "A molecule that can act as a" · printed starts no row begins with: "(i) It should be able to" · "(ii) It should be stable chemically" · "are required for evolution."
- ch 5 p10: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.4 ¶2 "“It has not escaped our notice that" · §5.4 ¶3 "The scheme suggested that the two strands"
- ch 5 p11: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.4.1 ¶4 "Can you recall what centrifugal force is," · §5.4.1 ¶5 "The results are shown in Figure 5.7." · printed starts no row begins with: "separated from N only based on"
- ch 5 p12: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §5.4.1 ¶7 "If E. coli was allowed to grow"
- ch 5 p13: 4 rows start here, the print starts 5 paragraphs — printed starts no row begins with: "(ii) The Structural gene"
- ch 5 p14: 4 rows start here, the print starts 5 paragraphs — rows the print does not start: §5.5.1 ¶3 "Can you now write the sequence of" · printed starts no row begins with: "3 -ATGCATGCATGCATGCATGCATGC-5 Template Strand" · "5 -TACGTACGTACGTACGTACGTACG-3 Coding Strand"
- ch 5 p17: 5 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "heterogeneous nuclear RNA (hnRNA)."
- ch 5 p18: 9 rows start here, the print starts 11 paragraphs — rows the print does not start: §5.6 ¶4 "The salient features of genetic code are" · printed starts no row begins with: "not code for any amino acids," · "the code is degenerate." · "also act as initiator codon."
- ch 5 p19: 18 rows start here, the print starts 12 paragraphs — rows the print does not start: §5.6 ¶14 "Met-Phe-Phe-Phe-Phe-Phe-Phe" · §5.6.1 ¶8 "Now we insert three letters together, say" · §5.6.1 ¶9 "RAM HAS BIG RED CAP" · §5.6.1 ¶11 "RAM HAS EDC AP" · §5.6.1 ¶12 "RAM HAS DCA P" · §5.6.1 ¶13 "RAM HAS CAP"
- ch 5 p20: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §5.6.2 ¶1 "From the very beginning of the proposition"
- ch 5 p21: 4 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "(i) transcriptional level (formation of primary" · "(ii) processing level (regulation of splicing)," · "(iii) transport of mRNA from nucleus" · "(iv) translational level."
- ch 5 p24: 5 rows start here, the print starts 10 paragraphs — rows the print does not start: §5.9 ¶4 "Some of the important goals of HGP" · §5.9 ¶5 "The Human Genome Project was a 13-year" · printed starts no row begins with: "(i) Identify all the approximately 20,000-25,000" · "(ii) Determine the sequences of the" · "make up human DNA;" · "(iv) Improve tools for data analysis;" · "(v) Transfer related technologies to other" · "(vi) Address the ethical, legal, and" · "from the project."
- ch 5 p25: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §5.9 ¶6 "Methodologies : The methods involved two major"
- ch 5 p26: 10 rows start here, the print starts 14 paragraphs — printed starts no row begins with: "(i) The human genome contains 3164.7" · "the largest known human gene being" · "(99.9 per cent) nucleotide bases are" · "on chromosome structure, dynamics and evolution."
- ch 5 p28: 2 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "(i) isolation of DNA," · "(ii) digestion of DNA by restriction" · "(iii) separation of DNA fragments by" · "(iv) transferring (blotting) of separated DNA" · "membranes, such as nitrocellulose or nylon," · "(v) hybridisation using labelled VNTR probe," · "(vi) detection of hybridised DNA fragments" · "representation of DNA fingerprinting is shown"
- ch 6 p1: 3 rows start here, the print starts 1 paragraphs — rows the print does not start: §6 ¶1 "Evolutionary Biology is the study of history" · §6.1 ¶2 "The origin of life is considered a"
- ch 7 p3: 1 rows start here, the print starts 5 paragraphs — rows the print does not start: §7 ¶1 "Health, for a long time, was considered" · printed starts no row begins with: "(i) genetic disorders – deficiencies with" · "from parents from birth;" · "(ii) infections and" · "(iii) life style including food and" · "exercise we give to our bodies,"
- ch 7 p8: 5 rows start here, the print starts 11 paragraphs — printed starts no row begins with: "(i) Physical barriers : Skin on" · "prevents entry of the micro-organisms. Mucus" · "tracts also help in trapping microbes" · "(ii) Physiological barriers : Acid in" · "tears from eyes–all prevent microbial growth." · "like polymorpho-nuclear leukocytes (PMNL-neutrophils) and"
- ch 7 p9: 4 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.2.2 ¶3 "The B-lymphocytes produce an army of proteins" · printed starts no row begins with: "interferons which protect non-infected cells from"
- ch 7 p11: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §7.2.7 ¶2 "Lymphoid organs: These are the organs where"
- ch 7 p14: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.3 ¶4 "Prevention of AIDS : As AIDS has"
- ch 7 p15: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §7.4 ¶3 "Causes of cancer : Transformation of normal" · §7.4 ¶4 "Cancer detection and diagnosis : Early detection"
- ch 7 p16: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §7.4 ¶6 "Treatment of cancer : The common approaches"
- ch 7 p21: 4 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "yoga and other extracurricular activities." · "this would help young to vent" · "in initiating proper remedial steps or"
- ch 8 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §8 ¶1 "Besides macroscopic plants and animals, microbes are"
- ch 9 p3: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §9 ¶1 "Biotechnology deals with techniques of using live" · §9.1 ¶2 "(i) Genetic engineering : Techniques to alter"
- ch 9 p4: 5 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "phenotype of the host organism." · "like antibiotics, vaccines, enzymes, etc."
- ch 9 p5: 5 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "(i) identification of DNA with desirable" · "(ii) introduction of the identified DNA" · "(iii) maintenance of introduced DNA in" · "to its progeny."
- ch 9 p8: 4 rows start here, the print starts 2 paragraphs — rows the print does not start: §9.2.1 ¶11 "Separation and isolation of DNA fragments :" · §9.2.1 ¶12 "The separated DNA fragments can be visualised"
- ch 9 p9: 4 rows start here, the print starts 5 paragraphs — rows the print does not start: §9.2.2 ¶2 "The following are the features that are" · printed starts no row begins with: "cloned in a vector whose origin" · "against any of these antibiotics."
- ch 9 p10: 3 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "these are identified as recombinant colonies."
- ch 10 p1: 3 rows start here, the print starts 7 paragraphs — rows the print does not start: §10 ¶1 "Biotechnology, as you would have learnt from" · printed starts no row begins with: "(i) Providing the best catalyst in" · "organism usually a microbe or pure" · "(ii) Creating optimal conditions through engineering" · "a catalyst to act, and" · "(iii) Downstream processing technologies to purify"
- ch 10 p2: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §10.1 ¶4 "As traditional breeding techniques failed to keep" · printed starts no row begins with: "(ii) organic agriculture; and"
- ch 10 p3: 5 rows start here, the print starts 10 paragraphs — rows the print does not start: §10.1 ¶11 "Bt Cotton: Some strains of Bacillus thuringiensis" · printed starts no row begins with: "(i) made crops more tolerant to" · "(ii) reduced reliance on chemical pesticides" · "(iii) helped to reduce post harvest" · "(iv) increased efficiency of mineral usage" · "exhaustion of fertility of soil)." · "(v) enhanced nutritional value of food,"
- ch 10 p4: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §10.1 ¶13 "Pest Resistant Plants: Several nematodes parasitise a"
- ch 10 p6: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.2.1 ¶4 "In mammals, including humans, insulin is synthesised"
- ch 10 p7: 6 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "biological role of the factor in"
- ch 10 p8: 8 rows start here, the print starts 10 paragraphs — printed starts no row begins with: "more balanced product for human babies" · "monkeys to test the safety of"
- ch 11 p3: 1 rows start here, the print starts 0 paragraphs — rows the print does not start: §11 ¶1 "Our living world is fascinatingly diverse and"
- ch 11 p6: 6 rows start here, the print starts 8 paragraphs — rows the print does not start: §11.1.2 ¶6 "So, if N is the population density" · printed starts no row begins with: "population that are added to the" · "have come into the habitat from" · "left the habitat and gone elsewhere"
- ch 11 p7: 5 rows start here, the print starts 1 paragraphs — rows the print does not start: §11.1.2 ¶8 "Growth Models : Does the growth of" · §11.1.2 ¶10 "The r in this equation is called" · §11.1.2 ¶11 "To give you some idea about the" · §11.1.2 ¶12 "The above equation describes the exponential or"
- ch 11 p8: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §11.1.2 ¶13 "Darwin showed how even a slow growing" · §11.1.2 ¶14 "The king and the minister sat for" · printed starts no row begins with: "fast a huge population could build"
- ch 11 p11: 3 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "by them actually as defences against"
- ch 11 p12: 2 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "behavioural differences in their foraging activities." · "free lodging and meals, it is"
- ch 11 p14: 2 rows start here, the print starts 3 paragraphs — printed starts no row begins with: "and watch brood parasitism in action."
- ch 12 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §12 ¶1 "An ecosystem can be visualised as a"
- ch 12 p2: 5 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "(iii) Energy flow; and" · "(iv) Nutrient cycling."
- ch 12 p5: 7 rows start here, the print starts 6 paragraphs — rows the print does not start: §12.4 ¶7 "Grass -> Goat -> Man (Producer) (Primary"
- ch 13 p1: 2 rows start here, the print starts 1 paragraphs — rows the print does not start: §13 ¶1 "If an alien from a distant galaxy"
- ch 13 p2: 5 rows start here, the print starts 8 paragraphs — printed starts no row begins with: "of rice, and 1,000 varieties of" · "the Eastern Ghats." · "than a Scandinavian country like Norway."
- ch 13 p4: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.1.2 ¶2 "What is so special about tropics that"
- ch 13 p5: 3 rows start here, the print starts 4 paragraphs — printed starts no row begins with: "log S = log C +"
- ch 13 p7: 5 rows start here, the print starts 5 paragraphs — rows the print does not start: §13.1.4 ¶4 "Causes of biodiversity losses: The accelerated rates" · printed starts no row begins with: "food and shelter, but when ‘need’"
- ch 13 p8: 5 rows start here, the print starts 7 paragraphs — printed starts no row begins with: "continued existence of some commercially important" · "in our rivers."
- ch 13 p9: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.2.2 ¶2 "In situ conservation– Faced with the conflict"
- ch 13 p10: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §13.2.2 ¶4 "Ex situ Conservation– In this approach, threatened"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

- ch 10 §10.1 ¶1 runs from p1 onto p2, but p2 opens a new paragraph: "(ii) organic agriculture; and"

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
- ch 1 §1.4.2 ¶2: figure_refs carries "Figure 1.14a", which no caption in chapter 1 prints — ruled noise: Figure 1.14a: the caption prints Figure 1.14 (a)
- ch 1 §1.4.2 ¶3: figure_refs carries "Figure 1.14 b", which no caption in chapter 1 prints — ruled noise: Figure 1.14 b: the caption prints Figure 1.14 (b)
- ch 2 §2.6 ¶1: figure_refs carries "Figure 2.12", which no caption in chapter 2 prints — ruled noise: Figure 2.12's caption is printed on the row's own page
- ch 4 §4.3.2 ¶5: figure_refs carries "Figure 4.10", which no caption in chapter 4 prints — ruled noise: Figure 4.10's caption is printed on the row's own page
- ch 5 §5.9 ¶7: figure_refs carries "Figure 5.15", which no caption in chapter 5 prints — ruled noise: Figure 5.15's caption is printed on the row's own page
- ch 7 §7.5 ¶5: figure_refs carries "Figure 7.11", which no caption in chapter 7 prints — ruled noise: Figure 7.11's caption is printed on the row's own page
- ch 10 §10.2.1 ¶4 starts a paragraph at the top of p6, but the print continues p5's: "In mammals, including humans, insulin is" — ruled noise: p5's last line ends 109 pt short: that paragraph closed on p5
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (673 rows)
request id: pipeline-ncert-verify-a48797de-2fac-42c0-8e39-c4f6e6bb8a31

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 193 | 3 | 190 |
artefact: verify/bio12/en.jsonl (193 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 3 | 11532 | 461 | 14502 | 7251 | ₹4.40 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
none

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 5 p6: "S strain -> Inject into mice -> Mice die"
- ch 5 p6: "R strain -> Inject into mice -> Mice live"
- ch 5 p6: "S strain (heat-killed) -> Inject into mice -> Mice live"
- ch 5 p6: "S strain (heat-killed) + R strain (live) -> Inject into mice -> Mice die"
- ch 5 p28: "(iv) transferring (blotting) of separated DNA fragments to synthetic membranes"
- ch 5 p28: "(v) hybridisation using labelled VNTR probe, and"
- ch 5 p28: "(vi) detection of hybridised DNA fragments by autoradiography"
- ch 7 p21: "Health is not just the absence of disease. It is a state of complete physical, mental, social and psychological well-being."
- ch 8 p10: "SUMMARY Microbes are a very important component of life on earth."
- ch 9 p13: "Can you think of any reason why there is a need for large-scale production?"

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 1 p19 §1.4.2 ¶1: printed "embryo development (embryogeny)" · transcribed "embryo development (embryogeny)"
- ch 2 p1 §2 ¶1: printed "male and female reproductive systems in human" · transcribed "male and female reproductive systems in human"
- ch 2 p5 §2.2 ¶4: printed "membranous perimetrium," · transcribed "membranous perimetrium,"
- ch 2 p5 §2.2 ¶4: printed "membranous perimetrium,  middle" · transcribed "membranous perimetrium, middle"
- ch 2 p12 §2.5 ¶4: printed "trophoblast called the inner cell mass" · transcribed "trophoblast called the inner cell mass"
- ch 2 p12 §2.6 ¶2: printed "relaxin is also secreted by the ovary" · transcribed "relaxin is also secreted by the ovary"
- ch 2 p13 §2.7 ¶2: printed "develop resistance for the new-born babies" · transcribed "develop resistance for the new-born babies"
- ch 3 p2 §3.1 ¶3: printed "In aminocentesis some" · transcribed "In aminocentesis some"
- ch 3 p2 §3.1 ¶3: printed "Statutory ban on amniocentesis for" · transcribed "Statutory ban on amniocentesis for"
- ch 3 p8 §3.5 ¶2: printed "(ZIFT–zygote intra fallopian transfer)" · transcribed "(ZIFT–zygote intra fallopian transfer)"
- ch 3 p8 §3.5 ¶2: printed "Embryos formed by in-vivo fertilisation" · transcribed "Embryos formed by in-vivo fertilisation"
- ch 4 p12 §4.3 ¶3: printed "F_1 hybrid RrYy" · transcribed "F_1 hybrid RrYy"
- ch 4 p14 §4.3.1 ¶5: printed "rY and ry each" · transcribed "rY and ry each"
- ch 4 p14 §4.3.1 ¶5: printed "1/4^th" · transcribed "1/4^th"
- ch 5 p1 §5 ¶2: printed "The determination" · transcribed "The determination"
- ch 5 p3 §5.1.1 ¶6: printed "5'->3'" · transcribed "5'->3'"
- ch 5 p9 §5.2.2 ¶3: printed "criteria:" · transcribed "criteria:"
- ch 5 p12 §5.4.1 ¶8: printed "Vicia faba" · transcribed "Vicia faba"
- ch 5 p15 §5.5.3 ¶1: printed "transcription (Initiation). It uses" · transcribed "transcription (Initiation). It uses"
- ch 5 p17 §5.5.3 ¶7: printed "dominance of RNA-world" · transcribed "dominance of RNA-world"
- ch 5 p19 §5.6.1 ¶7: printed "BIR EDC AP" · transcribed "BIR EDC AP"
- ch 5 p22 §5.8.1 ¶1: printed "elucidate a transcriptionally regulated system" · transcribed "elucidate a transcriptionally regulated system"
- ch 5 p26 §5.9.1 ¶7: printed "has most genes (2968)" · transcribed "has most genes (2968)"
- ch 5 p27 §5.10 ¶1: printed "compare two sets of 3 × 10^6 base pairs" · transcribed "compare two sets of 3 × 10^6 base pairs"
- ch 6 p2 §6.1 ¶4: printed "water vapour at 800°C" · transcribed "water vapour at 800°C"
- ch 6 p9 §6.5 ¶2: printed "take million of years" · transcribed "take million of years"
- ch 7 p7 §7.1 ¶9: printed "W. bancrofti" · transcribed "W. bancrofti"
- ch 7 p20 §7.5.3 ¶4: printed "liver (cirrhosis)" · transcribed "liver (cirrhosis)."
- ch 8 p5 §8.2.3 ¶3: printed "Trichoderma polysporum" · transcribed "Trichoderma polysporum"
- ch 9 p5 §9.2.1 ¶3: printed "Escherichia coli RY 13" · transcribed "Escherichia coli RY 13"
- ch 9 p6 §9.2.1 ¶4: printed "Exonucleases remove nucleotides from the ends of the DNA whereas, endonucleases make cuts" · transcribed "Exonucleases remove nucleotides from the ends of the DNA whereas, endonucleases make cuts"
- ch 9 p6 §9.2.1 ¶5: printed "a specific palindromic nucleotide sequences in the DNA" · transcribed "a specific palindromic nucleotide sequences in the DNA"
- ch 9 p7 §9.2.1 ¶6: printed "sequences reads the same" · transcribed "sequences reads the same"
- ch 9 p13 §9.3.5 ¶2: printed "Can you think of any reason why there is a need for large-scale production?" · transcribed "Can you think of any reason why there is a need for large-scale production?"
- ch 10 p2 §10.1 ¶3: printed "the use of improved crop varieties" · transcribed "the use of improved crop varieties"
- ch 10 p3 §10.1 ¶11: printed "once an insect ingest the" · transcribed "once an insect ingest the"
- ch 10 p3 §10.1 ¶11: printed "exist as inactive protoxins" · transcribed "exist as inactive protoxins"
- ch 10 p4 §10.1 ¶12: printed "a gene cryIAc named cry" · transcribed "a gene cryIAc named cry"
- ch 12 p6 §12.4 ¶9: printed "interconnection of food chains make it a" · transcribed "interconnection of food chains make it a"
- ch 12 p6 §12.4 ¶9: printed "These natural interconnection of food chains" · transcribed "These natural interconnection of food chains"
- ch 12 p7 §12.4 ¶12: printed "called as the standing crop" · transcribed "called as the standing crop."
- ch 13 p2 §13.1 ¶2: printed "Rauwolfia vomitoria growing" · transcribed "Rauwolfia vomitoria growing"
- ch 13 p4 §13.1.2 ¶1: printed "378 of reptiles" · transcribed "378 of reptiles"
- ch 13 p6 §13.1.4 ¶1: printed "784 species (including 338 vertebrates" · transcribed "784 species (including 338 vertebrates"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

- ch 1 p6: "Figure 1.3 (a) Transverse section of a young anther"
- ch 1 p10: "Figure 1.8 (a) Parts of the ovule showing a large megaspore mother cell"
- ch 1 p20: "1.4.3 Seed"
- ch 1 p22: "1.5 APOMIXIS AND POLYEMBRYONY"
- ch 4 p6: "Figure 4.3 Diagrammatic representation"
- ch 4 p16: "Table 4.3: A Comparison between the Behaviour of Chromosomes and Genes"
- ch 5 p2: "5.1 THE DNA"
- ch 5 p2: "5.1.1 Structure of Polynucleotide Chain"
- ch 5 p13: "5.5.1 Transcription Unit"
- ch 5 p14: "Figure 5.9 Schematic structure of a transcription unit"
- ch 6 p15: "6.9 Origin and Evolution of Man"
- ch 9 p5: "9.2.1 Restriction Enzymes"
- ch 10 p5: "10.2.1 Genetically Engineered Insulin"
- ch 11 p7: "Figure 11.3 Population growth curve"
- ch 11 p10: "Table 11.1 : Population Interactions"
- ch 11 p14: "Figure 11.4 Mutual relationship between fig tree and wasp"
- ch 13 p3: "Figure 13.1 Representing global biodiversity"
- ch 13 p5: "Figure 13.2 Showing species area relationship."
- ch 13 p6: "13.1.4 Loss of Biodiversity"
- ch 13 p8: "13.2.1 Why Should We Conserve Biodiversity?"
flags set aside by the founder's rulings in ncert-corrections.yaml: 17
verdicts recorded on rows: 673

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 1 | 64 | 64 | 64 | 0 | 0 | 0 | 0 | 100.0% |
| 2 | 30 | 30 | 30 | 0 | 0 | 0 | 0 | 100.0% |
| 3 | 24 | 24 | 24 | 0 | 0 | 0 | 0 | 100.0% |
| 4 | 77 | 77 | 77 | 0 | 0 | 0 | 0 | 100.0% |
| 5 | 145 | 145 | 145 | 0 | 0 | 0 | 0 | 100.0% |
| 6 | 32 | 32 | 32 | 0 | 0 | 0 | 0 | 100.0% |
| 7 | 69 | 69 | 69 | 0 | 0 | 0 | 0 | 100.0% |
| 8 | 31 | 31 | 31 | 0 | 0 | 0 | 0 | 100.0% |
| 9 | 47 | 47 | 47 | 0 | 0 | 0 | 0 | 100.0% |
| 10 | 43 | 43 | 43 | 0 | 0 | 0 | 1 | 97.7% |
| 11 | 44 | 44 | 44 | 0 | 0 | 0 | 0 | 100.0% |
| 12 | 33 | 33 | 33 | 0 | 0 | 0 | 0 | 100.0% |
| 13 | 34 | 34 | 34 | 0 | 0 | 0 | 0 | 100.0% |
clean for the book (PLAN D15 ✅): 672 of 673 paragraphs, 99.9%
not in the clean share, adjudicate before recording it: 85 page-level start flags, 0 numbered equations the print carries that the rows do not, 10 passages no row carries

# pipeline/inputs — founder-owned data files

The four files here are the inputs of the D13 pipeline commands (TECH_PLAN §6.2, §6.3): the pipeline
reads them and never edits them. They were **drafted by Claude on 2026-09-10 from the official NEET (UG)
2026 syllabus and the NCERT 2022-edition contents pages, for the founder's review** (PLAN D13:
"founder-reviewed"). Every judgement call is listed under *Review checklist* below; DECISIONS.md rows
dated 2026-09-10 record the spec-silent choices.

| file | loads into | command (§6.3) | rows |
|---|---|---|---|
| `taxonomy.csv` | `syllabus_nodes` | `taxonomy load` | 516 nodes: 4 subjects, 55 units, 83 chapters, 374 topics |
| `prerequisites.csv` | `syllabus_prerequisites` | `taxonomy prerequisites` | 104 chapter-to-chapter edges, acyclic |
| `archetypes.yaml` | `archetype_tracks`, `archetype_track_steps` | `backbone load` | 4 tracks, 744 steps |
| `cutoffs.csv` | `cutoffs` | `cutoffs load` | 40 qualifying rows, 2019–2026 |

Sources: `syllabus/manifest.md` (the 2025 and 2026 syllabus PDFs: identical content, 50 NTA units),
`ncert/2022-ed/en/manifest.md` (the 79 NCERT chapters; titles verified from each book's contents page
and chapter headings), NTA result notices for the cut-offs.

## taxonomy.csv

Columns: `code, subject, class_level, parent_code, kind, name_en, name_hi, sort_order,
default_learn_minutes, neet_relevant` (TECH_PLAN §6.2). Values follow the V3 constraints: `subject` in
physics | chemistry | botany | zoology, `kind` in subject | unit | chapter | topic, `class_level` 11 | 12
or empty.

**Tree shape** (the DECISIONS rows of 2026-09-10 D13 on the tree shape, the biology split and the
synthetic chapters):

| level | what it is | code | class_level |
|---|---|---|---|
| subject | the four schema subjects | `PHY`, `CHE`, `BOT`, `ZOO` | empty |
| unit | the NTA syllabus unit, numbered as in the syllabus | `PHY.U06` | empty (NTA units are class-agnostic) |
| chapter | the NCERT 2022-edition chapter | `PHY.11.GRAV` | 11 or 12 |
| topic | grouped syllabus content under its chapter, 2–10 per chapter | `PHY.11.GRAV.KEPLER` | the chapter's |

Special cases, each worth a look:

- **Biology is split.** The schema has no "biology" subject, so the five NTA Biology units that span
  both halves exist twice, once per subject, with the NTA number kept in both codes: Diversity
  (`BOT.U01` plants and microbes / `ZOO.U01` animals), Structural Organisation (`BOT.U02` / `ZOO.U02`),
  Reproduction (`BOT.U06` flowering plants / `ZOO.U06` human), Genetics and Evolution (`BOT.U07`
  heredity and molecular basis / `ZOO.U07` evolution), Biology and Human Welfare (`BOT.U08` microbes /
  `ZOO.U08` health and disease). Cell Structure and Function (with Biomolecules) and Biotechnology sit
  under botany; those two are the conventional swing chapters.
- **Three syllabus-only chapters** have no NCERT 2022-edition chapter: `PHY.00.EXPSKILL` (Unit 20
  Experimental Skills), `CHE.00.PBLOCK` (Unit 10 p-Block Elements, dropped from NCERT in the 2022
  rationalisation) and `CHE.00.PRACTICAL` (Unit 20 Principles Related to Practical Chemistry). They carry
  an empty `class_level` and a `00` code segment.
- **NCERT Chemistry Unit 8 is two chapter nodes**: `CHE.11.GOC` (sections 8.1–8.7, NTA Unit 14) and
  `CHE.11.GOCTECH` (sections 8.8–8.10: purification, qualitative and quantitative analysis, NTA Unit 13),
  because NTA splits it and a chapter has one parent unit. The section numbers were read from the chapter
  PDF; the node names carry none, since section addressing belongs to the NCERT anchor (D23).
- **NTA Chemistry Unit 7** (Redox Reactions and Electrochemistry) holds a class-11 and a class-12 chapter.
- Every other NTA unit maps to one to six NCERT chapters; every one of the 79 NCERT chapters appears once.

Conventions in this draft:

- `name_hi` is filled for subjects, units and chapters and **empty for topics**. Provenance: the chapter
  titles are the NCERT Hindi edition titles as Claude recalls them, not extracted from the Hindi PDFs
  (their Chanakya encoding yields no Unicode, see `ncert/2022-ed/hi/manifest.md`); the unit names are
  translations of the NTA unit names. Both need a native-reader pass. Topic translation plus its own
  native-reader check is PARKED for before the D26 syllabus grid.
- `default_learn_minutes` is a flat **45 per topic**, the chapter is the sum of its topics, units and
  subjects are empty. SPEC §9.1: these recalibrate from student data.
- `neet_relevant` is `true` on every row: the tree is built outward from the syllabus, so no
  NCERT-only chapter exists to flag.
- `sort_order` is the position among siblings: subjects in the exam's order, units by NTA number,
  chapters in NCERT order within their unit, topics in syllabus order.

## prerequisites.csv

`from_code` is learned before `to_code` (DECISIONS 2026-09-06 D4). Chapter level only, 104 edges.
Physics and Chemistry edges stay within the subject; Biology edges cross between botany and zoology
where the discipline does (Cell Cycle before Human Reproduction, Genetics before Evolution, and, added
at the founder's review on 2026-09-11, Biological Classification before Animal Kingdom, the mirror of
Classification before Plant Kingdom). The graph is acyclic (Kahn's algorithm, the check the D13 loader
repeats). Soft dependencies were left out on purpose: an edge here constrains every track's ordering.

## archetypes.yaml

Four tracks per the `archetype_tracks` CHECK: `fresher_2yr` (96 weeks), `fresher_1yr` (44),
`dropper` (40), `repeater` (40). Steps reference `syllabus_nodes.code`:

- **learn** steps name chapters. Each track learns all 83 chapters in four parallel subject streams.
  Freshers go class 11 then class 12 in NCERT order; the dropper and repeater go weightage-first, using
  a draft priority list that D22's computed `weightage_marks_avg` should replace. Within every stream
  the order respects `prerequisites.csv` (a priority topological sort in which a prerequisite inherits
  the priority of the chapters that need it, so Classification is not left until week 14 because Plant
  Kingdom is wanted early); a chapter whose prerequisite lives in another stream waits for that
  prerequisite's week and the rest of its stream slides with it; inside a week the chapters are ordered
  topologically over every edge. The generator checked every edge across streams at both week and
  `sequence` granularity, so no `sequence` places a chapter ahead of its prerequisite.
- **revision** steps name NTA units, spread over the revision window.
- **mock** steps name the subject node, four per mock week, since a step needs a node.
- `target_week` is the week of the track by which the step should be reached; `sequence` is the upsert key
  with the track code (§6.3). Track names reuse the interview labels of SPEC §5.1. These step conventions
  are recorded in DECISIONS (2026-09-10 D13, the archetype conventions row).

Windows: fresher_2yr learns class 11 in weeks 1–38, revises it in 39–42 with mocks at 40, 42 and 44,
learns class 12 in 45–80, revises everything in 81–94 with a mock every other week to 96. fresher_1yr
learns in 1–34, revises in 35–42, mocks from week 30. dropper learns in 1–28, mocks every four weeks from
16 and every two from 28, revises in 29–38. repeater learns in 1–24, mocks every four weeks from 8 and
every two from 24, revises in 25–38. This is the draft the F3 educator review is for.

## cutoffs.csv

Only `seat_type = qualifying` rows: the NTA qualifying cut-off per category for 2019 to 2026, taken as
the lower bound of the published score range, with EWS equal to general as NTA publishes it. The 2024
row is the revised result of 26 July 2024 (the 4 June notice read 164 and 129), and its `source` says so.
`quota_scope` is `AIQ` (the DECISIONS 2026-09-10 D13 row on qualifying cut-offs). The 2026 rows come from the
NTA result notice of 16 July 2026 for the Re-NEET of 21 June (the May exam was cancelled), supplied by
the founder at the review; **2026 is a historical outlier** (general 144 → 213) and the D58 trajectory
feature must anchor to a smoothed reference, not the latest year alone (DECISIONS 2026-09-11 D13).
**Still founder-sourced, later:** every `govt_mbbs`, `private_mbbs` and `bds` closing-marks row by year,
category and quota scope from MCC and state counselling data, not needed before the trajectory work
around D58.

## Review checklist (founder, before the D13 tick)

1. ~~Botany/zoology assignment of the split units, and of Cell (with Biomolecules) and Biotechnology.~~
   **Closed 2026-09-11, approved as drafted**: the prevalent coaching convention; Ecology under botany
   confirmed (DECISIONS row).
2. ~~The three syllabus-only chapters and the Unit 8 split: keep, or fold their topics elsewhere.~~
   **Closed 2026-09-11, keep all**: EXPSKILL's ten experiments cover the one or two experimental-skills
   questions NTA reliably asks, PRACTICAL's seven topics stand, PBLOCK's two match the slimmed p-block
   content of the current syllabus, the GOC → GOCTECH edge is wired.
3. ~~Topic granularity per chapter: 374 topics, 2 to 10 per chapter, median 4.~~ **Closed 2026-09-11,
   no merges**: every two-topic chapter (ELAST, ATOMS, STRUCTANI, ORGPOP, ANAT) matches the 2022
   edition's slimmed content, the high end (EXPSKILL 10, INHERIT 8) fits those chapters, and the
   chapters deleted by rationalisation (Solid State, Polymers, Transport in Plants, Digestion) are
   absent throughout.
4. ~~Hindi names of units and chapters (native-reader pass); topic Hindi is deliberately empty.~~
   **Closed 2026-09-11, approved for D13**: the sampled units and chapters read correctly; a final
   native-reader skim of the full `name_hi` column is PARKED for before D26, with the topic translations.
5. ~~The prerequisite edges: anything missing that should constrain the plan, anything too strict.~~
   **Closed 2026-09-11, approved with one addition**: every edge passes the "cannot learn B without A"
   test, nothing removed; Biological Classification before Animal Kingdom added (104 edges).
6. ~~Track weeks and windows, and the weightage-first list, ahead of the F3 educator review.~~
   **Closed 2026-09-11, sniff test passed**: shapes, mock cadence and revision windows are sane and the
   weightage-first list is marked as the D22 placeholder; the real verdict is F3's, so the open step is
   booking the educator review (TRACKER F3).
7. ~~Cut-off values against the NTA notices; add the 2026 and seat-type rows.~~ **Closed 2026-09-11**:
   the seven drafted years verified against the official notices; the five 2026 rows added from the
   16 July 2026 notice; seat-type rows stay founder-sourced for around D58.

## How the draft was produced

A scratchpad generator (not committed) turned a hand-authored unit → chapter → topic mapping into the
CSV and YAML and ran the loader-style checks: unique codes under 32 characters, every parent present,
kind and subject consistent along each path, class-level rules, sort orders unique among siblings,
prerequisite endpoints present with no self-loops, duplicates or cycles, every chapter learned once
per track, sequences unique, target weeks inside the track, prerequisite order across streams at week and
sequence granularity, cut-off natural keys unique with enum values valid, and the chapter set per NCERT
book matching the manifests.
The D13 loader re-implements these checks in Java; the CSVs are the artefact, edit them directly.

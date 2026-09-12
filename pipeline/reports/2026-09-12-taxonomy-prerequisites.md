# margai-pipeline taxonomy prerequisites

- run: 2026-09-12 09:20 IST
- input: ../pipeline/inputs/prerequisites.csv
- sha256: dc17b8da5b6253bff718f201972c9d9979ade48209b86c40320cceeb9eb0333c
- read: 104 edges
- result: ok

## syllabus_prerequisites

| inserted | already present | edges in the database | nodes with edges | cycle check |
|---|---|---|---|---|
| 104 | 0 | 104 | 83 | passed (Kahn's remainder empty; a remainder fails the run) |

## orphan edges (in the database, not in the file)

none

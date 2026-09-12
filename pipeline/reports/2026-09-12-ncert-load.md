# margai-pipeline ncert load

- run: 2026-09-12 22:38 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: FAILED: <lang>.jsonl: ch 7 §7.2 ¶1 is claimed by page 2 and page 16, which cannot be one paragraph continuing across a page break: page 3 between them carries text — re-extract those pages (`ncert extract --redo --chapters 7 --pages 2,16`)
content store: s3://margai-beta-content

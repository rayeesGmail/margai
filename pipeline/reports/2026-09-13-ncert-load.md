# margai-pipeline ncert load

- run: 2026-09-13 20:04 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: FAILED: en.jsonl: ch 1 §1.3 ¶3 is claimed by page 3 and page 4, which cannot be one paragraph continuing across a page break: the first half is a finished sentence — re-extract those pages (`ncert extract --redo --chapters 1 --pages 3,4`)
content store: s3://margai-beta-content

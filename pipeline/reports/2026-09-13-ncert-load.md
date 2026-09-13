# margai-pipeline ncert load

- run: 2026-09-13 20:21 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: FAILED: en.jsonl: 3 address(es) cannot be one paragraph continuing across a page break:
  ch 1 §1.3 ¶3 is claimed by page 3 and page 4: the first half is a finished sentence
  ch 4 §4.9.1 ¶13 is claimed by page 13 and page 14: the first half is a finished sentence
  ch 6 §6.7.4 ¶3 is claimed by page 17 and page 18: the first half is a finished sentence
Re-extract every affected page:
  ncert extract --redo --chapters 1 --pages 3,4
  ncert extract --redo --chapters 4 --pages 13,14
  ncert extract --redo --chapters 6 --pages 17,18
content store: s3://margai-beta-content

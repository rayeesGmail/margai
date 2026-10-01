# margai-pipeline ncert load

- run: 2026-09-27 00:39 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 8 chapters of phy12-part1 (en)
- result: FAILED: ncert-corrections.yaml: figure_ref correction on ch 3 page 16: the paragraph holding "We can put these equations in a simpler way" carries no "Fig. (3.14)" — fix the entry against the page, or remove it
content store: s3://margai-beta-content
corrections: ../pipeline/inputs/ncert-corrections.yaml sha256 d95f98a1d07ab1f6017db9ccfd1c459e90916f22efb211e638a481ea840110d4

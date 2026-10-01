# margai-pipeline ncert verify

- run: 2026-09-28 15:37 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 5 chapters of chem12-part2 (en)
- result: FAILED: StorageException: could not read s3://margai-beta-content/source/ncert/2022-ed/en/chem12-part2/lech201.pdf: no usable AWS credentials. An SSO session expires while a long run is going — run `aws sso login --profile margai` and start the command again; it resumes where it stopped.
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 f97ae5097718350ac7d354ae9a534744062f79a9e188c618b684640c2fd1ec8d

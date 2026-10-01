# margai-pipeline ncert load

- run: 2026-09-16 22:45 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: FAILED: StorageException: could not stat s3://margai-beta-content/extract/phy11-part1/en.jsonl: no usable AWS credentials. An SSO session expires while a long run is going — run `aws sso login --profile margai` and start the command again; it resumes where it stopped.
content store: s3://margai-beta-content

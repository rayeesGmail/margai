# margai-pipeline ncert extract

- run: 2026-09-14 16:05 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: FAILED: StorageException: could not stat s3://margai-beta-content/extract/phy11-part1/en.jsonl: no usable AWS credentials. An SSO session expires while a long run is going — run `aws sso login --profile margai` and start the command again; it resumes where it stopped.
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-17f8d18a-8e4b-43b3-ab34-0d8dec4de5dd

# margai-pipeline ncert extract

- run: 2026-10-17 07:14 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 chapters of bio11 (hi)
- result: FAILED: StorageException: could not stat s3://margai-beta-content/extract/bio11/hi.jsonl: no usable AWS credentials. An SSO session expires while a long run is going — run `aws sso login --profile margai` and start the command again; it resumes where it stopped.
content store: s3://margai-beta-content
artefact: extract/bio11/hi.jsonl
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-fbc81f40-9216-4160-9f5d-c37e3ad02eab

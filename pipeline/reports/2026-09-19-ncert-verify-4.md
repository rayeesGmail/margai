# margai-pipeline ncert verify

- run: 2026-09-19 07:50 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 7 chapters of phy11-part1 (en)
- result: FAILED: StorageException: could not read s3://margai-beta-content/source/ncert/2022-ed/en/phy11-part1/keph101.pdf: no usable AWS credentials. An SSO session expires while a long run is going — run `aws sso login --profile margai` and start the command again; it resumes where it stopped.
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 2fba95d0e02ba94d65e8741c805fd6d12eb6e9b3e9436a4e046af78c4f552dd4

package com.margai.curriculum.api;

import java.util.List;

/**
 * One booklet file a paper's questions were read from ({@code pyq_papers.source}, migration V9):
 * a paper may come from two files of one booklet code, an English one whose text layer is clean
 * and a bilingual one that carries NTA's own Hindi.
 *
 * @param file      the content-bucket key
 * @param sha256    as the founder's MANIFEST.csv records it; the extraction refuses a file that differs
 * @param languages "en" or "hi-en"
 * @param pages     the 1-based pages read, in order
 */
public record PaperSourceFile(String file, String sha256, String languages, List<Integer> pages) {

    public PaperSourceFile {
        pages = pages == null ? List.of() : List.copyOf(pages);
    }
}

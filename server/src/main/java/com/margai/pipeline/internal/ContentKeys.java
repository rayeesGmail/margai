package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * The content bucket's key scheme (DECISIONS 2026-09-12 F8, D14): prefixes sit at the bucket's
 * root, and a page image carries the chapter it came from, because NCERT's sources are
 * chapter-wise PDFs and page numbers run per chapter.
 *
 * <pre>
 * source/ncert/2022-ed/{lang}/{book}/{file}.pdf   the founder's upload, mirroring the local tree
 * pages/{book}/{lang}/{chapter}/{page}.png        `ncert render`
 * extract/{book}/{lang}[.{tag}].jsonl             `ncert extract` (a tag: a scratch run's own)
 * verify/{book}/{lang}[.{tag}].jsonl              `ncert verify --read-pages`
 * </pre>
 */
final class ContentKeys {

    /** Page numbers are zero-padded so a plain key listing sorts into reading order. */
    private static final String PAGE_FORMAT = "%03d.png";

    /** An artefact tag becomes part of an object key. */
    private static final Pattern TAG = Pattern.compile("[a-z0-9][a-z0-9-]{0,31}");

    private ContentKeys() {
    }

    static String pagePrefix(String book, BookLanguage language, short chapter) {
        return "pages/" + book + "/" + language + "/" + chapter + "/";
    }

    static String page(String book, BookLanguage language, short chapter, int page) {
        return pagePrefix(book, language, chapter) + PAGE_FORMAT.formatted(page);
    }

    static String extract(String book, BookLanguage language) {
        return extract(book, language, null);
    }

    /**
     * The same, under a tag for a scratch run — a model trial against the frozen corpus (D16, the 5.5
     * validation) must never write into, or resume from, the canonical artefact:
     * {@code extract/{book}/{lang}.{tag}.jsonl}.
     */
    static String extract(String book, BookLanguage language, String tag) {
        return "extract/" + book + "/" + language + suffix(tag) + ".jsonl";
    }

    /** {@code ncert verify --read-pages}' artefact: one line per page read (D15). */
    static String verify(String book, BookLanguage language) {
        return verify(book, language, null);
    }

    /**
     * The same, under a tag for a scratch run — the seeded recall run reads a copy of the database and
     * must never write into the real artefact (DECISIONS 2026-09-15): {@code verify/{book}/{lang}.{tag}.jsonl}.
     */
    static String verify(String book, BookLanguage language, String tag) {
        return "verify/" + book + "/" + language + suffix(tag) + ".jsonl";
    }

    /**
     * An artefact tag as {@code --artefact-tag} gives it, refused unless it can be one key segment:
     * lowercase letters, digits and hyphens, at most 32. Null — no tag — is the canonical artefact.
     */
    static String tag(String tag) {
        if (tag != null && !TAG.matcher(tag).matches()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "--artefact-tag must be lowercase letters, digits and hyphens, at most 32 — it becomes part of an object key");
        }
        return tag;
    }

    private static String suffix(String tag) {
        return tag == null ? "" : "." + tag(tag);
    }

    /**
     * The page number a page key carries. Read back from the listing rather than assumed from the
     * count, so a chapter rendered in part — the state an interrupted render leaves — is extracted
     * for the pages that exist instead of for 1..n.
     */
    static int pageNumber(String pageKey) {
        String name = pageKey.substring(pageKey.lastIndexOf('/') + 1);
        int dot = name.indexOf('.');
        return Integer.parseInt(dot < 0 ? name : name.substring(0, dot));
    }
}

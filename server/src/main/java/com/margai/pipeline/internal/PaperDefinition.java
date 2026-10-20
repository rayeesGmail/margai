package com.margai.pipeline.internal;

import com.margai.curriculum.api.PaperSourceFile;
import com.margai.curriculum.api.PyqPaperRow;
import java.util.List;

/**
 * One paper of {@code pyq-papers.yaml} as the pipeline needs it: the database columns in
 * {@link PyqPaperRow}, plus the special entries of the paper's code in NTA's final key — the
 * founder's own table (README §3), which {@code pyq load} holds the approved paper file's answers
 * to, so a key typed into the file can never disagree with the key the founder checked.
 */
record PaperDefinition(PyqPaperRow row, List<KeySpecial> specials) {

    PaperDefinition {
        specials = List.copyOf(specials);
    }

    String code() {
        return row.code();
    }

    /**
     * The booklet the English is transcribed from: an English-only file of the code where the
     * register lists one — its text layer is the clean one (README §4, 2020 E1) — else the
     * bilingual booklet, whose English column is read.
     */
    PaperSourceFile transcribed() {
        return row.source().stream().filter(file -> file.languages().equals(PyqPapersYamlReader.ENGLISH))
                .findFirst().orElse(row.source().getFirst());
    }

    /**
     * A question NTA's final key does not answer with one option: dropped, or several accepted.
     *
     * @param accepted the accepted keys, ascending; empty when dropped
     */
    record KeySpecial(short question, boolean dropped, List<String> accepted) {

        KeySpecial {
            accepted = List.copyOf(accepted);
        }
    }
}

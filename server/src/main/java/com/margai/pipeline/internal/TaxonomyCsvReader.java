package com.margai.pipeline.internal;

import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code taxonomy.csv} (TECH_PLAN §6.2) into {@link SyllabusNodeRow}s: the ten columns in their
 * contract order, codes unique within the file, a subject has no parent and no class level,
 * every other kind has a parent, a class level is 11 or 12 or empty.
 */
final class TaxonomyCsvReader {

    static final String[] HEADER = {"code", "subject", "class_level", "parent_code", "kind", "name_en", "name_hi",
            "sort_order", "default_learn_minutes", "neet_relevant"};
    static final int NAME_MAX_LENGTH = 160;

    private TaxonomyCsvReader() {
    }

    static List<SyllabusNodeRow> read(Path file) {
        List<SyllabusNodeRow> rows = new ArrayList<>();
        Set<String> codes = new HashSet<>();
        for (CsvInput.Row row : CsvInput.read(file, HEADER)) {
            String code = row.code("code");
            if (!codes.add(code)) {
                throw row.error("code '" + code + "' appears twice");
            }
            NodeKind kind = row.requiredEnum("kind", NodeKind.class);
            String parentCode = row.optionalCode("parent_code");
            Short classLevel = row.optionalShort("class_level", 11, 12);
            if (kind == NodeKind.subject && (parentCode != null || classLevel != null)) {
                throw row.error("a subject has no parent_code and no class_level");
            }
            if (kind != NodeKind.subject && parentCode == null) {
                throw row.error("a " + kind + " needs a parent_code");
            }
            rows.add(new SyllabusNodeRow(
                    code,
                    row.requiredEnum("subject", Subject.class),
                    classLevel,
                    parentCode,
                    kind,
                    row.required("name_en", NAME_MAX_LENGTH),
                    row.optional("name_hi", NAME_MAX_LENGTH),
                    row.requiredInt("sort_order", 1),
                    row.optionalInt("default_learn_minutes", 0),
                    row.requiredBoolean("neet_relevant")));
        }
        return rows;
    }
}

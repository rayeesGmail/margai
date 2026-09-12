package com.margai.curriculum.internal;

import com.margai.curriculum.api.CurriculumImportException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Codes to nodes for the importers, or a {@link CurriculumImportException} naming what names nothing. */
final class NodeLookup {

    private NodeLookup() {
    }

    static Map<String, SyllabusNode> resolve(SyllabusNodeRepository nodes, Set<String> codes, String what) {
        Map<String, SyllabusNode> byCode = nodes.findByCodeIn(codes).stream()
                .collect(Collectors.toMap(SyllabusNode::getCode, Function.identity()));
        List<String> missing = codes.stream().filter(code -> !byCode.containsKey(code)).toList();
        if (!missing.isEmpty()) {
            throw new CurriculumImportException(what + " not in the taxonomy: " + missing);
        }
        return byCode;
    }
}

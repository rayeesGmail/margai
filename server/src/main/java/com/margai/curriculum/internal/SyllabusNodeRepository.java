package com.margai.curriculum.internal;

import com.margai.curriculum.api.NodeKind;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyllabusNodeRepository extends JpaRepository<SyllabusNode, UUID> {

    Optional<SyllabusNode> findByCode(String code);

    List<SyllabusNode> findByCodeIn(Collection<String> codes);

    List<SyllabusNode> findByKindOrderBySortOrder(NodeKind kind);
}

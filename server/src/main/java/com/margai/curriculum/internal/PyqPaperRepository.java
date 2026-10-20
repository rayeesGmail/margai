package com.margai.curriculum.internal;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface PyqPaperRepository extends JpaRepository<PyqPaper, UUID> {

    Optional<PyqPaper> findByCode(String code);

    Optional<PyqPaper> findByExamAndYearAndPaperCode(String exam, short year, String paperCode);
}

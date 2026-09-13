package com.margai.curriculum.internal;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface NcertParagraphRepository extends JpaRepository<NcertParagraph, UUID> {

    List<NcertParagraph> findByBookId(UUID bookId);

    List<NcertParagraph> findByBookIdAndChapterNo(UUID bookId, short chapterNo);

    long countByBookId(UUID bookId);
}

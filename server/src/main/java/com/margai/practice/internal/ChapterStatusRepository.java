package com.margai.practice.internal;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterStatusRepository extends JpaRepository<ChapterStatus, UUID> {
}

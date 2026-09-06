package com.margai.curriculum.internal;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchetypeTrackStepRepository extends JpaRepository<ArchetypeTrackStep, UUID> {

    List<ArchetypeTrackStep> findByTrackIdOrderBySequence(UUID trackId);
}

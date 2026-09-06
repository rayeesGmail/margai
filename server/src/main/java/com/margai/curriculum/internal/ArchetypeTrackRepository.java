package com.margai.curriculum.internal;

import com.margai.common.api.AttemptType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchetypeTrackRepository extends JpaRepository<ArchetypeTrack, UUID> {

    Optional<ArchetypeTrack> findByCode(AttemptType code);
}

package com.margai.curriculum.internal;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CutoffRepository extends JpaRepository<Cutoff, UUID> {
}

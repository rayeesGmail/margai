package com.margai.curriculum.internal;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface NcertBookRepository extends JpaRepository<NcertBook, UUID> {

    Optional<NcertBook> findByCode(String code);
}

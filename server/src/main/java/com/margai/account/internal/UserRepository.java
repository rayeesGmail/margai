package com.margai.account.internal;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneAndStatus(String phone, UserStatus status);

    Optional<User> findByEmailAndStatus(String email, UserStatus status);

    /**
     * Serialises find-or-create on one identifier (PLAN D9 "duplicate accounts"; DECISIONS
     * 2026-09-09): a transaction-scoped advisory lock keyed by the normalised identifier, so two
     * first logins for the same new email or phone queue behind each other instead of racing the
     * partial unique index. Released with the transaction; no row, no schema, no retry loop.
     */
    @Query(value = "select 1 from pg_advisory_xact_lock(hashtextextended(:key, 0))", nativeQuery = true)
    Integer lockIdentifier(String key);
}

package com.margai.account.internal;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneAndStatus(String phone, UserStatus status);

    Optional<User> findByEmailAndStatus(String email, UserStatus status);
}

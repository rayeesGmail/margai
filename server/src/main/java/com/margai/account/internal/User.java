package com.margai.account.internal;

import com.margai.common.api.Language;
import com.margai.common.api.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * {@code users} (TECH_PLAN §2.2, migrations V2 and V6). A student signs in with one verified
 * identifier: {@code phone} (E.164) or, since the D7 founder ruling (DECISIONS 2026-09-08),
 * {@code email} (stored lowercased). Each is unique while present and both are nulled on deletion
 * (§2.10); the database enforces {@code status = 'deleted' OR phone IS NOT NULL OR email IS NOT NULL}.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 16)
    private String phone;

    private Instant phoneVerifiedAt;

    @Column(length = 254)
    private String email;

    @Column(length = 80)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private UserStatus status;

    private Instant deletedAt;

    private LocalDate purgeAfter;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected User() {
        // JPA
    }

    /** A phone-identified account; the phone is verified by the OTP that created it. */
    public User(String phone, Language language) {
        this(phone, null, language);
    }

    private User(String phone, String email, Language language) {
        this.phone = phone;
        this.email = email;
        this.language = language;
        this.role = UserRole.student;
        this.status = UserStatus.active;
    }

    /** An email-identified account (D7 ruling): {@code email} is already lowercased by the caller. */
    public static User withEmail(String email, Language language) {
        return new User(null, email, language);
    }

    public UUID getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public Instant getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public String getEmail() {
        return email;
    }

    /** Stamped on every phone-OTP login (TECH_PLAN §2.2 {@code phone_verified_at}). */
    public void markPhoneVerified(Instant at) {
        this.phoneVerifiedAt = at;
    }

    public String getDisplayName() {
        return displayName;
    }

    /** {@code PATCH /me display_name} (D10); already trimmed and bounded by the web layer. */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Language getLanguage() {
        return language;
    }

    /** {@code PATCH /me language} (SPEC §6.11, D10): new content from now on; the JWT follows on the next refresh (§3.8). */
    public void setLanguage(Language language) {
        this.language = language;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public LocalDate getPurgeAfter() {
        return purgeAfter;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

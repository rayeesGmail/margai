package com.margai.account.internal;

import com.margai.account.api.CoachingMode;
import com.margai.account.api.CoachingProvider;
import com.margai.account.api.Goal;
import com.margai.common.api.AttemptType;
import com.margai.common.api.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

/**
 * {@code student_profiles}, 1:1 with {@code users} (TECH_PLAN §2.2, migration V2). SPEC §5.1
 * Q1–Q7 map one-to-one onto the columns; {@code scorecard} and {@code board_marks} hold
 * confirmed fields only, never an image reference (SPEC §5.2). Columns that later days fill
 * ({@code scorecard}, {@code board_marks}, {@code exam_date}) are declared now so the row shape
 * is stable; the JSON ones are plain strings until D28 gives them a record shape. The setters are
 * the {@code PATCH /me} fields of §3.7 (D10); the onboarding interview (D25) fills the rest.
 */
@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private AttemptType attemptType;

    private Short targetYear;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private CoachingMode coachingMode;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private CoachingProvider coachingProvider;

    @Column(precision = 3, scale = 1)
    private BigDecimal hoursWeekday;

    @Column(precision = 3, scale = 1)
    private BigDecimal hoursWeekend;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private Goal goal;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 2)
    private String stateCode;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Category category;

    private LocalDate dob;

    @Column(nullable = false)
    private boolean isMinor;

    private Short lastNeetYear;

    private Short lastNeetScore;

    private Integer lastNeetRank;

    @JdbcTypeCode(SqlTypes.JSON)
    private String scorecard;

    @JdbcTypeCode(SqlTypes.JSON)
    private String boardMarks;

    @Column(nullable = false, length = 24)
    private String onboardingStep;

    private Instant onboardingCompletedAt;

    private LocalDate examDate;

    @Column(nullable = false)
    private LocalTime morningNotificationTime;

    @Column(nullable = false)
    private int currentStreak;

    @Column(nullable = false)
    private int longestStreak;

    private LocalDate lastActiveIstDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected StudentProfile() {
        // JPA
    }

    public StudentProfile(UUID userId) {
        this.userId = userId;
        this.onboardingStep = "intro";
        this.morningNotificationTime = LocalTime.of(7, 0);
        this.currentStreak = 0;
        this.longestStreak = 0;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public AttemptType getAttemptType() {
        return attemptType;
    }

    public Short getTargetYear() {
        return targetYear;
    }

    public CoachingMode getCoachingMode() {
        return coachingMode;
    }

    public CoachingProvider getCoachingProvider() {
        return coachingProvider;
    }

    public BigDecimal getHoursWeekday() {
        return hoursWeekday;
    }

    public void setHoursWeekday(BigDecimal hoursWeekday) {
        this.hoursWeekday = hoursWeekday;
    }

    public BigDecimal getHoursWeekend() {
        return hoursWeekend;
    }

    public void setHoursWeekend(BigDecimal hoursWeekend) {
        this.hoursWeekend = hoursWeekend;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDob() {
        return dob;
    }

    public boolean isMinor() {
        return isMinor;
    }

    public Short getLastNeetYear() {
        return lastNeetYear;
    }

    public Short getLastNeetScore() {
        return lastNeetScore;
    }

    public Integer getLastNeetRank() {
        return lastNeetRank;
    }

    public String getScorecard() {
        return scorecard;
    }

    public String getBoardMarks() {
        return boardMarks;
    }

    public String getOnboardingStep() {
        return onboardingStep;
    }

    public Instant getOnboardingCompletedAt() {
        return onboardingCompletedAt;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public LocalTime getMorningNotificationTime() {
        return morningNotificationTime;
    }

    /** {@code PATCH /me morning_notification_time} (D10); SPEC §6.10's "See you at 7 AM?" default until then. */
    public void setMorningNotificationTime(LocalTime morningNotificationTime) {
        this.morningNotificationTime = morningNotificationTime;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public LocalDate getLastActiveIstDate() {
        return lastActiveIstDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

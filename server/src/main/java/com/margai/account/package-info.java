/**
 * account module (TECH_PLAN §1.3): users, student profile, language, settings, export job and
 * deletion. Owns {@code users}, {@code student_profiles}, {@code user_devices},
 * {@code data_export_jobs}. Allowed dependencies per §1.4: {@code common :: api}, and
 * {@code storage} once it exists (export files).
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "account",
        allowedDependencies = {"common :: api"})
package com.margai.account;

/**
 * account module (TECH_PLAN §1.3): users, student profile, language, settings, export job and
 * deletion. Owns {@code users}, {@code student_profiles}, {@code user_devices},
 * {@code data_export_jobs}. Depends on {@code common} and {@code storage} once they exist (§1.4);
 * nothing declared until then because Modulith rejects unknown module names.
 */
@org.springframework.modulith.ApplicationModule(displayName = "account")
package com.margai.account;

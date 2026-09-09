import '../../core/auth/user_summary.dart';

/// The `profile` half of `/me` (TECH_PLAN §3.7, server `ProfileSummary`; D10): the scalar
/// columns of `student_profiles` (§2.2). Read leniently — every field but the onboarding step is
/// optional, and keys this build does not know are ignored, since later days add fields (§3.1).
/// The onboarding interview (D25) gives the answers their own types; here they travel as the
/// wire strings and numbers.
class Profile {
  const Profile({
    required this.onboardingStep,
    this.attemptType,
    this.targetYear,
    this.coachingMode,
    this.coachingProvider,
    this.hoursWeekday,
    this.hoursWeekend,
    this.goal,
    this.stateCode,
    this.category,
    this.dob,
    this.isMinor = false,
    this.onboardingCompletedAt,
    this.examDate,
    this.morningNotificationTime,
    this.currentStreak = 0,
    this.longestStreak = 0,
  });

  factory Profile.fromJson(Map<String, Object?> json) => Profile(
    onboardingStep: json['onboarding_step'] as String? ?? 'intro',
    attemptType: json['attempt_type'] as String?,
    targetYear: (json['target_year'] as num?)?.toInt(),
    coachingMode: json['coaching_mode'] as String?,
    coachingProvider: json['coaching_provider'] as String?,
    hoursWeekday: (json['hours_weekday'] as num?)?.toDouble(),
    hoursWeekend: (json['hours_weekend'] as num?)?.toDouble(),
    goal: json['goal'] as String?,
    stateCode: json['state_code'] as String?,
    category: json['category'] as String?,
    dob: json['dob'] as String?,
    isMinor: json['is_minor'] as bool? ?? false,
    onboardingCompletedAt: json['onboarding_completed_at'] as String?,
    examDate: json['exam_date'] as String?,
    morningNotificationTime: json['morning_notification_time'] as String?,
    currentStreak: (json['current_streak'] as num?)?.toInt() ?? 0,
    longestStreak: (json['longest_streak'] as num?)?.toInt() ?? 0,
  );

  /// `intro` until the interview starts (D25); the router's onboarding guard reads it then.
  final String onboardingStep;
  final String? attemptType;
  final int? targetYear;
  final String? coachingMode;
  final String? coachingProvider;
  final double? hoursWeekday;
  final double? hoursWeekend;
  final String? goal;
  final String? stateCode;
  final String? category;

  /// `YYYY-MM-DD`, an IST calendar date (§11.1).
  final String? dob;
  final bool isMinor;

  /// ISO-8601 UTC.
  final String? onboardingCompletedAt;
  final String? examDate;

  /// `HH:mm[:ss]`; the SPEC §6.10 "See you at 7 AM?" default is `07:00`.
  final String? morningNotificationTime;
  final int currentStreak;
  final int longestStreak;
}

/// `GET /me` and the answer of `PATCH /me` (TECH_PLAN §3.7): the account and its profile, one
/// call on app start. `subscription`, `limits` and `consent_state` join on their days (D61, D37,
/// D27) and are ignored until then.
class Me {
  const Me({required this.user, required this.profile});

  factory Me.fromJson(Map<String, Object?> json) => Me(
    user: UserSummary.fromJson(_object(json['user'])),
    profile: Profile.fromJson(_object(json['profile'])),
  );

  final UserSummary user;
  final Profile profile;

  static Map<String, Object?> _object(Object? value) => (value! as Map).map(
    (key, entry) => MapEntry(key.toString(), entry),
  );
}

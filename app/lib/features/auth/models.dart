import '../../core/auth/token_store.dart';
import '../../core/auth/user_summary.dart';

/// `POST /auth/otp/request` → `{challenge_id, resend_after_s, channel}` (TECH_PLAN §3.7).
class OtpChallenge {
  const OtpChallenge({
    required this.challengeId,
    required this.resendAfter,
    required this.channel,
  });

  factory OtpChallenge.fromJson(Map<String, Object?> json) => OtpChallenge(
    challengeId: json['challenge_id'] as String,
    resendAfter: Duration(seconds: (json['resend_after_s'] as num).toInt()),
    channel: json['channel'] as String,
  );

  final String challengeId;

  /// The 30-second resend cooldown the server announces (§3.4).
  final Duration resendAfter;

  /// `email` while the SMS channel waits for the DLT template (D7 ruling); `sms` after F1.
  final String channel;
}

/// `POST /auth/otp/verify` → `{access_token, refresh_token, expires_in, is_new_user, user}`.
class SignedInResult {
  const SignedInResult({
    required this.accessToken,
    required this.refreshToken,
    required this.expiresIn,
    required this.isNewUser,
    required this.user,
  });

  factory SignedInResult.fromJson(Map<String, Object?> json) => SignedInResult(
    accessToken: json['access_token'] as String,
    refreshToken: json['refresh_token'] as String,
    expiresIn: Duration(seconds: (json['expires_in'] as num).toInt()),
    isNewUser: json['is_new_user'] as bool,
    user: UserSummary.fromJson(_object(json['user'])),
  );

  final String accessToken;
  final String refreshToken;
  final Duration expiresIn;

  /// True on the first login: the onboarding interview (D25) starts here.
  final bool isNewUser;
  final UserSummary user;

  StoredSession toSession() => StoredSession(
    accessToken: accessToken,
    refreshToken: refreshToken,
    user: user,
  );

  static Map<String, Object?> _object(Object? value) => (value! as Map).map(
    (key, entry) => MapEntry(key.toString(), entry),
  );
}

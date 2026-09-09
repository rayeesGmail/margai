import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/api/api_client.dart';
import '../../core/api/api_failure.dart';
import '../../core/api/api_providers.dart';
import 'models.dart';

/// The two public auth calls of TECH_PLAN §3.7 as the login flow needs them. Every failure
/// reaches the caller as an [ApiFailure]; a 200 that is not the documented shape is
/// [ApiFailure.malformed], never a crash.
class AuthRepository {
  const AuthRepository(this._api);

  final ApiClient _api;

  /// `{email}` → the challenge. The `{phone}` form joins when `margai.auth.otp.channels`
  /// includes `sms` (TRACKER F1).
  Future<OtpChallenge> requestOtp({required String email}) async {
    final body = await _api.post('/auth/otp/request', {'email': email});
    return _parse(body, OtpChallenge.fromJson);
  }

  /// `{challenge_id, code}` → tokens, the user and `is_new_user`.
  Future<SignedInResult> verifyOtp({
    required String challengeId,
    required String code,
  }) async {
    final body = await _api.post('/auth/otp/verify', {
      'challenge_id': challengeId,
      'code': code,
    });
    return _parse(body, SignedInResult.fromJson);
  }

  static T _parse<T>(
    Map<String, Object?> body,
    T Function(Map<String, Object?>) fromJson,
  ) {
    try {
      return fromJson(body);
    } on TypeError {
      throw const ApiFailure.malformed(200);
    }
  }
}

final authRepositoryProvider = Provider<AuthRepository>(
  (ref) => AuthRepository(ref.watch(apiClientProvider)),
);

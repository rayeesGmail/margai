import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/api/api_client.dart';
import '../../core/api/api_failure.dart';
import '../../core/api/api_providers.dart';
import 'models.dart';

/// The auth calls of TECH_PLAN §3.7 as the login flow and the session need them: the two public
/// OTP calls, the public refresh, and the authenticated logout (D10). Every failure reaches the
/// caller as an [ApiFailure]; a 200 that is not the documented shape is [ApiFailure.malformed],
/// never a crash.
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

  /// `{refresh_token}` → the rotated pair (§3.2). A spent or revoked token is `AUTH_INVALID`.
  Future<TokensResult> refresh(String refreshToken) async {
    final body = await _api.post('/auth/refresh', {'refresh_token': refreshToken});
    return _parse(body, TokensResult.fromJson);
  }

  /// `{refresh_token}` → 204: this device's family is revoked (§3.7). Authenticated.
  Future<void> logout(String refreshToken) async {
    await _api.post('/auth/logout', {'refresh_token': refreshToken});
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

final Provider<AuthRepository> authRepositoryProvider = Provider<AuthRepository>(
  (ref) => AuthRepository(ref.watch(apiClientProvider)),
);

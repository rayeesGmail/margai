import 'dart:collection';

import 'package:margai/core/auth/user_summary.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/features/auth/models.dart';
import 'package:margai/features/auth/repository.dart';

/// A scripted [AuthRepository]: each call takes the next scripted outcome for its method
/// (a model to return or an error to throw) and records what it was asked.
class FakeAuthRepository implements AuthRepository {
  final Queue<Object> _requests = Queue<Object>();
  final Queue<Object> _verifies = Queue<Object>();
  final Queue<Object> _refreshes = Queue<Object>();
  final Queue<Object> _logouts = Queue<Object>();

  final List<String> requestedEmails = <String>[];
  final List<({String challengeId, String code})> verified =
      <({String challengeId, String code})>[];
  final List<String> refreshed = <String>[];
  final List<String> loggedOut = <String>[];

  void onRequest(Object outcome) => _requests.add(outcome);

  void onVerify(Object outcome) => _verifies.add(outcome);

  void onRefresh(Object outcome) => _refreshes.add(outcome);

  /// Script a logout outcome; `null` completes normally (the 204), an error throws.
  void onLogout(Object? outcome) => _logouts.add(outcome ?? _ok);

  @override
  Future<TokensResult> refresh(String refreshToken) async {
    refreshed.add(refreshToken);
    return _next(_refreshes, 'refresh') as TokensResult;
  }

  @override
  Future<void> logout(String refreshToken) async {
    loggedOut.add(refreshToken);
    _next(_logouts, 'logout');
  }

  static const Object _ok = Object();

  @override
  Future<OtpChallenge> requestOtp({required String email}) async {
    requestedEmails.add(email);
    return _next(_requests, 'requestOtp') as OtpChallenge;
  }

  @override
  Future<SignedInResult> verifyOtp({
    required String challengeId,
    required String code,
  }) async {
    verified.add((challengeId: challengeId, code: code));
    return _next(_verifies, 'verifyOtp') as SignedInResult;
  }

  static Object _next(Queue<Object> script, String method) {
    if (script.isEmpty) {
      throw StateError('FakeAuthRepository: nothing scripted for $method');
    }
    final outcome = script.removeFirst();
    if (outcome is Exception || outcome is Error) {
      throw outcome;
    }
    return outcome;
  }

  static const OtpChallenge challenge = OtpChallenge(
    challengeId: 'c-1',
    resendAfter: Duration(seconds: 30),
    channel: 'email',
  );

  static const UserSummary user = UserSummary(
    id: 'u-1',
    email: 'founder@example.com',
    language: AppLanguage.en,
    role: 'student',
  );

  static const SignedInResult signedIn = SignedInResult(
    accessToken: 'access-1',
    refreshToken: 'refresh-1',
    expiresIn: Duration(minutes: 15),
    isNewUser: true,
    user: user,
  );

  static const TokensResult rotated = TokensResult(
    accessToken: 'access-2',
    refreshToken: 'refresh-2',
    expiresIn: Duration(minutes: 15),
  );
}

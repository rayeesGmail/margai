import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/api/api_client.dart';
import '../../core/api/api_failure.dart';
import '../../core/api/api_providers.dart';
import '../../core/l10n/language_mapper.dart';
import 'models.dart';

/// The `/me` calls of TECH_PLAN §3.7 (D10). Every failure reaches the caller as an
/// [ApiFailure]; a 200 that is not the documented shape is [ApiFailure.malformed], never a crash.
class AccountRepository {
  const AccountRepository(this._api);

  final ApiClient _api;

  /// `GET /me` → the account and its profile: one call on app start.
  Future<Me> getMe() async => _parse(await _api.get('/me'));

  /// `PATCH /me` with only the fields given (absent = unchanged, §3.7) → the new `me`. The
  /// other fields of the contract — display name, hours, goal, state, category, morning time —
  /// join this signature with the screens that edit them (D25, D64).
  Future<Me> update({AppLanguage? language}) async {
    final body = <String, Object?>{
      if (language != null) 'language': language.wireValue,
    };
    return _parse(await _api.patch('/me', body));
  }

  static Me _parse(Map<String, Object?> body) {
    try {
      return Me.fromJson(body);
    } on TypeError {
      throw const ApiFailure.malformed(200);
    }
  }
}

final Provider<AccountRepository> accountRepositoryProvider = Provider<AccountRepository>(
  (ref) => AccountRepository(ref.watch(apiClientProvider)),
);

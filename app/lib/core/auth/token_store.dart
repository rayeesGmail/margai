import 'dart:convert';

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

import 'user_summary.dart';

/// What stays on the device after a sign-in (TECH_PLAN §3.2, §5.7 "tokens at rest", §9.1): the
/// 15-minute access JWT, the opaque refresh token of this device's family, and the user summary,
/// so a cold start knows who is signed in before any call. Stored as one blob so the three can
/// never disagree.
class StoredSession {
  const StoredSession({
    required this.accessToken,
    required this.refreshToken,
    required this.user,
  });

  final String accessToken;
  final String refreshToken;
  final UserSummary user;

  static StoredSession? tryParse(String? blob) {
    if (blob == null || blob.isEmpty) {
      return null;
    }
    try {
      final json = jsonDecode(blob);
      if (json is! Map) {
        return null;
      }
      final access = json['access'];
      final refresh = json['refresh'];
      final user = json['user'];
      if (access is! String || refresh is! String || user is! Map) {
        return null;
      }
      return StoredSession(
        accessToken: access,
        refreshToken: refresh,
        user: UserSummary.fromJson(
          user.map((key, value) => MapEntry(key.toString(), value)),
        ),
      );
    } on FormatException {
      return null;
    } on TypeError {
      return null;
    }
  }

  String serialise() => jsonEncode(<String, Object?>{
    'access': accessToken,
    'refresh': refreshToken,
    'user': user.toJson(),
  });
}

abstract interface class TokenStore {
  Future<StoredSession?> read();

  Future<void> write(StoredSession session);

  Future<void> clear();
}

/// Android Keystore-backed storage (`flutter_secure_storage`, its default strong options).
class SecureTokenStore implements TokenStore {
  SecureTokenStore([FlutterSecureStorage? storage])
    : _storage = storage ?? const FlutterSecureStorage();

  static const String slot = 'margai.session';

  final FlutterSecureStorage _storage;

  @override
  Future<StoredSession?> read() async =>
      StoredSession.tryParse(await _storage.read(key: slot));

  @override
  Future<void> write(StoredSession session) =>
      _storage.write(key: slot, value: session.serialise());

  @override
  Future<void> clear() => _storage.delete(key: slot);
}

/// For tests and the analyzer: the same contract, nothing persisted past the process.
class InMemoryTokenStore implements TokenStore {
  InMemoryTokenStore([this._blob]);

  String? _blob;

  int writes = 0;

  @override
  Future<StoredSession?> read() async => StoredSession.tryParse(_blob);

  @override
  Future<void> write(StoredSession session) async {
    writes++;
    _blob = session.serialise();
  }

  @override
  Future<void> clear() async => _blob = null;
}

final tokenStoreProvider = Provider<TokenStore>((ref) => SecureTokenStore());

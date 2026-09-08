import 'package:flutter_riverpod/flutter_riverpod.dart';

/// Build-time configuration from `--dart-define` (TECH_PLAN §5.9). Debug builds point at the
/// Android emulator's host alias so a local server on port 8081 answers (§5.4); the D74 build
/// passes the beta ALB. Keys, secrets and model ids never live here (CLAUDE.md).
class AppConfig {
  const AppConfig({required this.apiBaseUrl, required this.env});

  static const String defaultApiBaseUrl = 'http://10.0.2.2:8081';

  /// Origin of the API, without the `/api/v1` base path (the client adds it).
  final String apiBaseUrl;

  /// `local` on a developer machine, `beta` on the internal track.
  final String env;

  static const AppConfig fromEnvironment = AppConfig(
    apiBaseUrl: String.fromEnvironment(
      'API_BASE_URL',
      defaultValue: defaultApiBaseUrl,
    ),
    env: String.fromEnvironment('ENV', defaultValue: 'local'),
  );
}

final appConfigProvider = Provider<AppConfig>(
  (ref) => AppConfig.fromEnvironment,
);

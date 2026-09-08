import 'package:flutter_riverpod/flutter_riverpod.dart';

/// The `X-App-Version` header (TECH_PLAN §3.1): `margai/<version>+<build> android`. The server
/// stores it as the token family's `device_label` (D7), so a student's sessions read as builds.
/// `main.dart` resolves the real value with `package_info_plus` and overrides the provider; tests
/// and the analyzer see the placeholder.
abstract final class AppVersion {
  static const String placeholder = 'margai/unknown android';

  static String header({required String version, required String build}) {
    final number = build.isEmpty ? version : '$version+$build';
    return 'margai/$number android';
  }
}

final appVersionProvider = Provider<String>((ref) => AppVersion.placeholder);

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:package_info_plus/package_info_plus.dart';

import 'app.dart';
import 'core/config/app_version.dart';

/// Bootstrap (TECH_PLAN §5.1): the build's version for the `X-App-Version` header, then the
/// provider scope. Config comes from `--dart-define` at compile time and the stored session is
/// read lazily by `authStateProvider`, so nothing else waits here.
Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  final info = await PackageInfo.fromPlatform();
  runApp(
    ProviderScope(
      overrides: [
        appVersionProvider.overrideWithValue(
          AppVersion.header(version: info.version, build: info.buildNumber),
        ),
      ],
      child: const MargaiApp(),
    ),
  );
}

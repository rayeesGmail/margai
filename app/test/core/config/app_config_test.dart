import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/config/app_config.dart';

void main() {
  test('without --dart-define the config points at the emulator host on 8081', () {
    // TECH_PLAN §5.4: local emulator base URL http://10.0.2.2:8081; §5.9 ENV defaults to local.
    const config = AppConfig.fromEnvironment;
    expect(config.apiBaseUrl, 'http://10.0.2.2:8081');
    expect(config.env, 'local');
  });

  test('the base URL is an origin, not a path', () {
    expect(Uri.parse(AppConfig.defaultApiBaseUrl).path, isEmpty);
  });
}

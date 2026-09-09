import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/config/app_version.dart';

void main() {
  test('the X-App-Version header names product, version, build and platform', () {
    expect(
      AppVersion.header(version: '0.1.0', build: '1'),
      'margai/0.1.0+1 android',
    );
  });

  test('a missing build number leaves the version alone', () {
    expect(AppVersion.header(version: '0.1.0', build: ''), 'margai/0.1.0 android');
  });

  test('the placeholder fits the server\'s 80-character device label', () {
    expect(AppVersion.placeholder.length, lessThanOrEqualTo(80));
  });
}

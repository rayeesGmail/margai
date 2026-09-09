import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/router/app_router.dart';
import 'package:margai/features/auth/providers.dart';

import '../../support/fake_auth_repository.dart';

void main() {
  const loading = AsyncLoading<AuthState>();
  const signedOut = AsyncData<AuthState>(SignedOut());
  const signedIn = AsyncData<AuthState>(SignedIn(FakeAuthRepository.user));

  String? go(AsyncValue<AuthState> auth, LoginStep step, String location) =>
      guard(auth: auth, step: step, location: location);

  test('unknown auth stays on the splash and pulls everything else there', () {
    expect(go(loading, LoginStep.entry, AppRoutes.splash), isNull);
    expect(go(loading, LoginStep.entry, AppRoutes.today), AppRoutes.splash);
    expect(go(loading, LoginStep.code, AppRoutes.otp), AppRoutes.splash);
  });

  test('signed out goes to the login step the flow is on (TECH_PLAN §5.3)', () {
    expect(go(signedOut, LoginStep.entry, AppRoutes.splash), AppRoutes.login);
    expect(go(signedOut, LoginStep.entry, AppRoutes.login), isNull);
    expect(go(signedOut, LoginStep.entry, AppRoutes.today), AppRoutes.login);
    expect(go(signedOut, LoginStep.entry, AppRoutes.otp), AppRoutes.login);
    expect(go(signedOut, LoginStep.code, AppRoutes.login), AppRoutes.otp);
    expect(go(signedOut, LoginStep.code, AppRoutes.otp), isNull);
  });

  test('signed in never sees a login route', () {
    expect(go(signedIn, LoginStep.entry, AppRoutes.splash), AppRoutes.today);
    expect(go(signedIn, LoginStep.entry, AppRoutes.login), AppRoutes.today);
    expect(go(signedIn, LoginStep.code, AppRoutes.otp), AppRoutes.today);
    expect(go(signedIn, LoginStep.entry, AppRoutes.today), isNull);
  });
}

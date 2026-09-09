import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../features/auth/providers.dart';
import '../../features/auth/screens/login_screen.dart';
import '../../features/auth/screens/otp_screen.dart';
import '../../features/auth/screens/splash_screen.dart';
import '../../features/planner/screens/today_placeholder_screen.dart';
import '../auth/auth_state.dart';

/// Route names (TECH_PLAN §5.8). The bottom-bar shell and the deep links of §5.3 arrive with
/// their screens (D29 onwards); today the tree is the splash, the two login routes and the
/// signed-in landing.
abstract final class AppRoutes {
  static const String splash = '/';
  static const String login = '/login';
  static const String otp = '/login/otp';
  static const String today = '/today';
}

/// The redirect guard of TECH_PLAN §5.3 as a pure function, so it is testable without a widget
/// tree: unknown auth → splash; signed out → the login step the flow is on; signed in → never a
/// login route.
String? guard({
  required AsyncValue<AuthState> auth,
  required LoginStep step,
  required String location,
}) {
  if (auth.isLoading) {
    return location == AppRoutes.splash ? null : AppRoutes.splash;
  }
  final signedIn = auth.value is SignedIn;
  if (signedIn) {
    final onLogin =
        location == AppRoutes.splash || location.startsWith(AppRoutes.login);
    return onLogin ? AppRoutes.today : null;
  }
  final wanted = step == LoginStep.code ? AppRoutes.otp : AppRoutes.login;
  return location == wanted ? null : wanted;
}

final routerProvider = Provider<GoRouter>((ref) {
  final refresh = _RouterRefresh();
  ref.listen<AsyncValue<AuthState>>(authStateProvider, (_, _) => refresh.ping());
  ref.listen<LoginStep>(
    loginProvider.select((state) => state.step),
    (_, _) => refresh.ping(),
  );
  ref.onDispose(refresh.dispose);

  return GoRouter(
    initialLocation: AppRoutes.splash,
    refreshListenable: refresh,
    redirect: (context, state) => guard(
      auth: ref.read(authStateProvider),
      step: ref.read(loginProvider).step,
      location: state.matchedLocation,
    ),
    routes: [
      GoRoute(
        path: AppRoutes.splash,
        builder: (context, state) => const SplashScreen(),
      ),
      GoRoute(
        path: AppRoutes.login,
        builder: (context, state) => const LoginScreen(),
        routes: [
          GoRoute(
            path: 'otp',
            builder: (context, state) => const OtpScreen(),
          ),
        ],
      ),
      GoRoute(
        path: AppRoutes.today,
        builder: (context, state) => const TodayPlaceholderScreen(),
      ),
    ],
  );
});

class _RouterRefresh extends ChangeNotifier {
  void ping() => notifyListeners();
}

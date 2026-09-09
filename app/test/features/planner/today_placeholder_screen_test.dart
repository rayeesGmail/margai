import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/features/auth/screens/splash_screen.dart';
import 'package:margai/features/planner/screens/today_placeholder_screen.dart';

import '../../support/fake_auth_repository.dart';
import '../../support/pump.dart';

void main() {
  for (final locale in allLocales) {
    final l10n = copyFor(locale);

    testWidgets('Today placeholder in ${locale.toLanguageTag()} names the signed-in identity', (
      tester,
    ) async {
      final store = InMemoryTokenStore();
      await store.write(FakeAuthRepository.signedIn.toSession());

      await pumpScreen(
        tester,
        const TodayPlaceholderScreen(),
        locale: locale,
        store: store,
      );

      expect(find.text(l10n.todayPlaceholderTitle), findsOneWidget);
      expect(
        find.text(l10n.todayPlaceholderBody('founder@example.com')),
        findsOneWidget,
      );
      expect(find.text(l10n.todayPlaceholderNote), findsOneWidget);
    });

    testWidgets('Splash in ${locale.toLanguageTag()} shows the brand', (tester) async {
      await pumpScreen(tester, const SplashScreen(), locale: locale);
      expect(find.text(l10n.appTitle), findsOneWidget);
    });
  }
}

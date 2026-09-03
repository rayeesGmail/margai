import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/app.dart';

void main() {
  testWidgets('app shell boots and shows the localised app title', (
    tester,
  ) async {
    await tester.pumpWidget(const ProviderScope(child: MargaiApp()));
    await tester.pumpAndSettle();

    expect(find.text('MARG AI'), findsOneWidget);
  });
}

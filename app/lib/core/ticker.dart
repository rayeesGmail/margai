import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'clock.dart';

/// The current time, once a second, for screens that show a countdown (the resend cooldown).
/// State stays in the notifiers; the widget only re-reads it on each tick. Tests override this
/// with a single fixed value so no timer is left pending.
final tickerProvider = StreamProvider<DateTime>((ref) {
  final clock = ref.watch(clockProvider);
  return Stream<DateTime>.periodic(const Duration(seconds: 1), (_) => clock());
});

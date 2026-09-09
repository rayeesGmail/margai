import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'clock.dart';

/// The current time, once a second, for a notifier that owns a countdown (the resend cooldown).
/// Auto-disposed: the timer exists only while someone listens, so an idle app never ticks.
/// Tests override this with a single fixed value so no timer is left pending.
final tickerProvider = StreamProvider.autoDispose<DateTime>((ref) {
  final clock = ref.watch(clockProvider);
  return Stream<DateTime>.periodic(const Duration(seconds: 1), (_) => clock());
});

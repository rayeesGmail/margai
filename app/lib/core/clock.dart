import 'package:flutter_riverpod/flutter_riverpod.dart';

/// Wall time as a provider so notifiers with cooldowns (resend, rate limits) are testable with a
/// fixed clock, the same discipline the server keeps with its `IstClock` (TECH_PLAN §11.1).
final clockProvider = Provider<DateTime Function()>((ref) => DateTime.now);

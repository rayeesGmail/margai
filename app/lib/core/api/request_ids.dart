import 'dart:math';

/// Mints the `X-Request-Id` every call carries (TECH_PLAN §3.1, §5.4): a random version-4 UUID.
/// The server echoes it on the response and logs it, so a support conversation can find the
/// call. Twelve lines beat a dependency (DECISIONS, D8).
class RequestIds {
  RequestIds([Random? random]) : _random = random ?? Random.secure();

  final Random _random;

  String next() {
    final bytes = List<int>.generate(16, (_) => _random.nextInt(256));
    bytes[6] = (bytes[6] & 0x0f) | 0x40; // version 4
    bytes[8] = (bytes[8] & 0x3f) | 0x80; // RFC 4122 variant
    final hex = bytes.map((b) => b.toRadixString(16).padLeft(2, '0')).join();
    return '${hex.substring(0, 8)}-${hex.substring(8, 12)}-'
        '${hex.substring(12, 16)}-${hex.substring(16, 20)}-${hex.substring(20)}';
  }
}

import 'dart:math';

import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/request_ids.dart';

void main() {
  final v4 = RegExp(
    r'^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$',
  );

  test('mints RFC 4122 version-4 ids', () {
    final ids = RequestIds();
    for (var i = 0; i < 50; i++) {
      expect(ids.next(), matches(v4));
    }
  });

  test('ids are distinct and follow the random source', () {
    final seeded = RequestIds(Random(7));
    final again = RequestIds(Random(7));
    final a = seeded.next();
    expect(a, again.next());
    expect(seeded.next(), isNot(a));
  });
}

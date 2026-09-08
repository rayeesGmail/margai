import 'package:flutter/material.dart';

/// Material 3 theme and the layout constants every screen shares (TECH_PLAN §5.1 `core/theme`).
/// Built for a mid-range Android phone used one-handed (SPEC §1 principle 5): the primary action
/// sits at the bottom of the screen, tap targets are tall, content stays narrow enough for a thumb.
abstract final class AppTheme {
  static const Color seed = Color(0xFF1B5E9E);

  static ThemeData light() => _theme(Brightness.light);

  static ThemeData dark() => _theme(Brightness.dark);

  static ThemeData _theme(Brightness brightness) {
    final scheme = ColorScheme.fromSeed(seedColor: seed, brightness: brightness);
    return ThemeData(
      colorScheme: scheme,
      useMaterial3: true,
      inputDecorationTheme: const InputDecorationTheme(
        border: OutlineInputBorder(),
        contentPadding: EdgeInsets.symmetric(
          horizontal: AppSpacing.md,
          vertical: AppSpacing.md,
        ),
      ),
      filledButtonTheme: FilledButtonThemeData(
        style: FilledButton.styleFrom(
          minimumSize: const Size.fromHeight(AppLayout.primaryActionHeight),
          textStyle: const TextStyle(fontSize: 16, fontWeight: FontWeight.w600),
        ),
      ),
      textButtonTheme: TextButtonThemeData(
        style: TextButton.styleFrom(
          minimumSize: const Size(AppLayout.minTapTarget, AppLayout.minTapTarget),
        ),
      ),
    );
  }
}

abstract final class AppSpacing {
  static const double xs = 4;
  static const double sm = 8;
  static const double md = 16;
  static const double lg = 24;
  static const double xl = 32;
}

abstract final class AppLayout {
  /// Height of the bottom-anchored primary button: reachable and hard to miss.
  static const double primaryActionHeight = 52;

  /// Material's minimum; nothing tappable is smaller.
  static const double minTapTarget = 48;

  /// Content column width on wide screens, so a tablet still reads like a phone.
  static const double maxContentWidth = 480;
}

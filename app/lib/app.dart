import 'package:flutter/material.dart';

import 'l10n/app_localizations.dart';

/// Root widget. Screens follow the SPEC §8 catalog and arrive with their days;
/// this shell only proves the toolchain (PLAN D2) and carries the localisation wiring.
class MargaiApp extends StatelessWidget {
  const MargaiApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      onGenerateTitle: (context) => AppLocalizations.of(context).appTitle,
      localizationsDelegates: AppLocalizations.localizationsDelegates,
      supportedLocales: AppLocalizations.supportedLocales,
      home: const ShellScreen(),
    );
  }
}

/// Placeholder until the Splash/Login screen lands (PLAN D8, SPEC §8).
class ShellScreen extends StatelessWidget {
  const ShellScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context);
    return Scaffold(
      body: Center(
        child: Text(
          l10n.appTitle,
          style: Theme.of(context).textTheme.headlineMedium,
        ),
      ),
    );
  }
}

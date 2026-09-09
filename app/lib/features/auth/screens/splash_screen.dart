import 'package:flutter/material.dart';

import '../../../l10n/app_localizations.dart';

/// SPEC §8 screen 1, the splash half: shown only while the stored session is being read
/// (TECH_PLAN §5.3 "unknown"); the router moves on the moment auth state resolves.
class SplashScreen extends StatelessWidget {
  const SplashScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text(
          AppLocalizations.of(context).appTitle,
          style: Theme.of(context).textTheme.headlineMedium,
        ),
      ),
    );
  }
}

import 'dart:collection';

import 'package:margai/core/auth/user_summary.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/features/account/models.dart';
import 'package:margai/features/account/repository.dart';

import 'fake_auth_repository.dart';

/// A scripted [AccountRepository]: each call takes the next scripted outcome for its method
/// (a model to return or an error to throw) and records what it was asked.
class FakeAccountRepository implements AccountRepository {
  final Queue<Object> _gets = Queue<Object>();
  final Queue<Object> _updates = Queue<Object>();

  int gets = 0;
  final List<AppLanguage?> updatedLanguages = <AppLanguage?>[];

  void onGetMe(Object outcome) => _gets.add(outcome);

  void onUpdate(Object outcome) => _updates.add(outcome);

  @override
  Future<Me> getMe() async {
    gets++;
    return _next(_gets, 'getMe') as Me;
  }

  @override
  Future<Me> update({AppLanguage? language}) async {
    updatedLanguages.add(language);
    return _next(_updates, 'update') as Me;
  }

  static Object _next(Queue<Object> script, String method) {
    if (script.isEmpty) {
      throw StateError('FakeAccountRepository: nothing scripted for $method');
    }
    final outcome = script.removeFirst();
    if (outcome is Exception || outcome is Error) {
      throw outcome;
    }
    return outcome;
  }

  static const Profile emptyProfile = Profile(onboardingStep: 'intro');

  static const Me me = Me(user: FakeAuthRepository.user, profile: emptyProfile);

  static const UserSummary hindiUser = UserSummary(
    id: 'u-1',
    email: 'founder@example.com',
    language: AppLanguage.hi,
    role: 'student',
  );

  static const Me meInHindi = Me(user: hindiUser, profile: emptyProfile);

  static const UserSummary hinglishUser = UserSummary(
    id: 'u-1',
    email: 'founder@example.com',
    language: AppLanguage.hinglish,
    role: 'student',
  );

  static const Me meInHinglish = Me(user: hinglishUser, profile: emptyProfile);
}

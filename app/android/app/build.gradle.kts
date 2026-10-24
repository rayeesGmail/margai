plugins {
    id("com.android.application")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.hauslalearning.app"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        // Permanent once the first build reaches Play (the internal-testing upload on the Play-account day, TRACKER
        // F14). Tied to the domain we own, not to an app name; F21 covers only the display name (DECISIONS 2026-10-24).
        applicationId = "com.hauslalearning.app"
        // minSdk/targetSdk follow the Flutter SDK defaults (https://flutter.dev/to/review-gradle-config).
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        // Version code/name come from pubspec.yaml. With split APKs Flutter adds 1000 * ABI_VERSION.
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    buildTypes {
        release {
            // Release signing arrives on the Play-account day (TRACKER F14, F20): an upload key read from the
            // untracked key.properties (DECISIONS 2026-10-24); debug keys keep `flutter run --release` working until then.
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

flutter {
    source = "../.."
}

plugins {
    id("com.android.application")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.margai.app"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        // Permanent once the first build reaches Play (D74); revisit with the final name (TRACKER F6/F7).
        applicationId = "com.margai.app"
        // minSdk/targetSdk follow the Flutter SDK defaults (https://flutter.dev/to/review-gradle-config).
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        // Version code/name come from pubspec.yaml. With split APKs Flutter adds 1000 * ABI_VERSION.
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    buildTypes {
        release {
            // Release signing arrives with the Play internal track (D74); debug keys keep
            // `flutter run --release` working until then.
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

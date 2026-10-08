import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.retroshooter"

    // compileSdk / targetSdk are kept one step behind the very newest Android
    // release on purpose. minSdk 24 = Android 7.0, so old phones still work.
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.retroshooter"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    // The game has no third-party libraries on purpose: everything is plain
    // Kotlin plus the Android framework, so students can read all of it.
    testImplementation(libs.junit)
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "com.example"
version = "1.0-SNAPSHOT"

kotlin {
    jvm()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
    macosArm64()
    macosX64()
    mingwX64()
    sourceSets {
        val commonMain by getting {}
    }
}

android {
    namespace = "com.subscribe.nativebridge.annotation"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization") version("1.8.10")
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
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:atomicfu:0.21.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.5.0")
            }
        }
    }
}

android {
    namespace = "com.subscribe.nativebridge.annotation"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}



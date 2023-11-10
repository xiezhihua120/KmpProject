plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    // android平台
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    // ios平台
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "publish"
        }
    }

    // native平台
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("nativePc") {
            binaries.staticLib("macosArm64") { baseName = "kmpLib" }
        }
        hostOs == "Mac OS X" && !isArm64 -> macosX64("nativePc")  {
            binaries.staticLib("macosX64") { baseName = "kmpLib" }
        }
        isMingwX64 -> mingwX64("nativePc") {
            binaries.sharedLib("winX64") { baseName = "kmpLib" }
        }
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        // 共享代码
        val commonMain by getting {
            dependencies {
                api(project(":welcome"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        // android代码
        val androidMain by getting {
            dependsOn(commonMain)
        }

        // ios代码
        val iosMain by getting {
            dependsOn(commonMain)
        }

        // native代码
        val nativePcMain by getting {
            dependsOn(commonMain)
        }
    }
}

android {
    namespace = "com.subscribe.kmpproject"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}

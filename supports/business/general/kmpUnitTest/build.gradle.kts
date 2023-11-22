plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "me.allin327"
version = "1.0-SNAPSHOT"

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
    )

    // native平台
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("nativeType") == "macArm"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("nativePc")
        hostOs == "Mac OS X" && !isArm64 -> macosX64("nativePc")
        isMingwX64 -> mingwX64("nativePc")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        // 共享代码
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
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


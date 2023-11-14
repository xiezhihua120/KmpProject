plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    id("com.subscribe.gradlex").version("1.0.2")
}

subprojects {
    apply(from = "${rootProject.rootDir}/gradle/scripts/github.gradle.kts")
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KmpProject"
include(":androidApp")
project(":androidApp").projectDir = file("androidApp")
include(":publish")
project(":publish").projectDir = file("publish")
include(":welcome")
project(":welcome").projectDir = file("supports/business/feature/welcome")
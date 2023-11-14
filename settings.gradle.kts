enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        maven {
            url = uri("${rootProject.projectDir}/supports/foundation/plugins/local-plugin-repository")
        }
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

include("nativeBridge-annotation")
project(":nativeBridge-annotation").projectDir = file("supports/foundation/nativeBridge/nativeBridge-annotation")
include("nativeBridge-processor")
project(":nativeBridge-processor").projectDir = file("supports/foundation/nativeBridge/nativeBridge-processor")
include(":gradlex")

include(":gradlex")
project(":gradlex").projectDir = file("supports/foundation/plugins/gradlex")


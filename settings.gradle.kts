enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        maven {
            url = uri(providers.gradleProperty("subscribe.repo.maven.remote").get())
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        maven {
            url = uri(providers.gradleProperty("subscribe.repo.maven.remote").get())
        }
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

gradle.addBuildListener(object : BuildListener {
    override fun settingsEvaluated(settings: Settings) {
        // empty impl
    }

    override fun projectsLoaded(gradle: Gradle) {
        println("[Gradle初始化完成...]")
    }

    override fun projectsEvaluated(gradle: Gradle) {
        println("[Gradle配置完成...]")
    }

    override fun buildFinished(result: BuildResult) {
        println("[Gradle执行完成...]")
    }
})


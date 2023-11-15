plugins {
    id("groovy")
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}

version = "1.0.3"

gradlePlugin {
    plugins {
        create("simplePlugin") {
            id = "com.subscribe.gradlex"
            group = "com.subscribe.gradlex"
            implementationClass = "com.subscribe.gradlex.DependenciesPlugin"
        }
        create("kotlinPlugin") {
            id = "com.subscribe.gradlek"
            group = "com.subscribe.gradlek"
            implementationClass = "com.subscribe.gradlek.NativeBridgePlugin"
        }
    }
}

val repoPath: String = providers.gradleProperty("subscribe.repo.maven.local").get()
val repoDir: String =
    if (repoPath.startsWith(".")) rootProject.file(repoPath).absolutePath else repoPath

publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri(repoDir)
        }
    }
}
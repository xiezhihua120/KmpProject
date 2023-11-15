val kspVersion: String = "1.8.0-1.0.8"

plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "com.subscribe.nativebridge.processor"
version = "1.0.3"

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.squareup:kotlinpoet:1.10.2")
                implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
                implementation("com.subscribe.nativebridge.annotation:nativeBridge-annotation:1.0.3")
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
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
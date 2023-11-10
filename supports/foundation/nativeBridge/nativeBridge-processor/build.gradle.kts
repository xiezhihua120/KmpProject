val kspVersion: String = "1.8.0-1.0.8"

plugins {
    kotlin("multiplatform")
}

group = "com.example"
version = "1.0-SNAPSHOT"

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.squareup:kotlinpoet:1.10.2")
                implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
                implementation(project(":nativeBridge-annotation"))
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}

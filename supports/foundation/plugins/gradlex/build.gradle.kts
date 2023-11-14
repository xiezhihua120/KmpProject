plugins {
    id("groovy")
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}

gradlePlugin {
    plugins {
        create("simplePlugin") {
            id = "com.subscribe.gradlex"
            group = "com.subscribe.gradlex"
            version = "1.0.2"
            implementationClass = "com.subscribe.gradlex.DependenciesPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}
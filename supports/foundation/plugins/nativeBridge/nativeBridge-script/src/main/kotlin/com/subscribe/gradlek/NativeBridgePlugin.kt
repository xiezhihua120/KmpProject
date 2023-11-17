package com.subscribe.gradlek

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetContainer
import java.io.File

/**
 * Created on 2023/11/14
 * @author：xiezh
 * @function：
 * dependencies {
 *     add("kspCommonMainMetadata", project(":nativeBridge-processor"))
 *     add("kspNativePc", project(":nativeBridge-processor"))
 * }
 * kotlin.sourceSets {
 *     named("nativePcMain") {
 *         dependencies { implementation(project(":nativeBridge-annotation")) }
 *         kotlin.srcDir("build${File.separator}generated${File.separator}ksp${File.separator}nativePc${File.separator}nativePcMain${File.separator}kotlin")
 *     }
 * }
 */


class NativeBridgePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println(">>>>>>>>  " + this::class.qualifiedName)
        val extension = target.extensions.create("NativeBridge", MainExtension::class.java)
        target.afterEvaluate {
            configNativeBridge(extension, target)
        }
    }

    private fun configNativeBridge(
        extension: MainExtension, target: Project
    ) {
        println("这是插件 ${this::class.qualifiedName}")
        println("namedSourceSets = ${extension.namedSourceSets}")
        println("version = ${extension.info.version}")
        target.tasks.find { it.group == "build" && it.name.startsWith("nativePcBinaries") }?.let {
            it.doLast {
                println("拷贝文件到指定目录")
            }
        }

        target.dependencies.add(
            "kspCommonMainMetadata", target.rootProject.findProject(":nativeBridge-processor")
        )
        target.dependencies.add(
            "kspNativePc", target.rootProject.findProject(":nativeBridge-processor")
        )

        val isMultiplatform = target.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")
        val sourceSetName = if (isMultiplatform) "nativePcMain" else "main"
        val containerClass = KotlinSourceSetContainer::class.java

        // Add the runtime dependency.
        val allSourceSet = target.extensions.getByType(containerClass).sourceSets
        val namedSourceSet = allSourceSet.getByName(sourceSetName)
        val namedSourceSetApi = target.configurations.getByName(namedSourceSet.apiConfigurationName)
        namedSourceSetApi.dependencies.addAll(buildList {
            add(target.dependencies.create(target.rootProject.findProject(":nativeBridge-annotation")))
        })
        namedSourceSet.kotlin.srcDir("build${File.separator}generated${File.separator}ksp${File.separator}nativePc${File.separator}nativePcMain${File.separator}kotlin")
    }
}

open class MainExtension(private val project: Project) {
    var namedSourceSets: String = ""
    var info: SubExtension = SubExtension(project)

    fun printVersion() {
        println("MainExtension: $namedSourceSets")
    }

    init {
        info = project.extensions.create("info", SubExtension::class.java, project)
    }
}
open class SubExtension(private val project: Project) {
    var version: String = "version_${project.name}_0"
}
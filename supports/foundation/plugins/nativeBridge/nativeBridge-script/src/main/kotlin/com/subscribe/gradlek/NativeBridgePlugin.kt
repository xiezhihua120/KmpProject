package com.subscribe.gradlek

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import java.io.File


/**
 * Created on 2023/11/14
 * @author：xiezh
 * @function：
 */
class NativeBridgePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println(">>>>>>>>  " + this::class.qualifiedName)
        val extension = project.extensions.create("NativeBridge", MainExtension::class.java)
        project.afterEvaluate {
            println("这是插件 ${this::class.qualifiedName}")
            println("namedSourceSets = ${extension.namedSourceSets}")
            println("version = ${extension.info.version}")
            project.tasks.find { it.group == "build" && it.name.startsWith("nativePcBinaries") }?.let {
                it.doLast {
                    println("拷贝文件到指定目录")
                }
            }

            project.dependencies.add("kspCommonMainMetadata", project.rootProject.findProject(":nativeBridge-processor"))
            project.dependencies.add("kspNativePc", project.rootProject.findProject(":nativeBridge-processor"))

        }

//        val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
//        val main = sourceSets.getByName("nativePcMain")
//        main.java.srcDir("build${File.separator}generated${File.separator}ksp${File.separator}nativePc${File.separator}nativePcMain${File.separator}kotlin"))
//
//        project.kotlin.sourceSets {
//            named("nativePcMain") {
//                dependencies { implementation(project(":nativeBridge-annotation")) }
//                kotlin.srcDir("build${File.separator}generated${File.separator}ksp${File.separator}nativePc${File.separator}nativePcMain${File.separator}kotlin")
//            }
//        }

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
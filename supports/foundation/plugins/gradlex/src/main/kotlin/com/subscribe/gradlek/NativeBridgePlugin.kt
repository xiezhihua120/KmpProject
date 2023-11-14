package com.subscribe.gradlek

import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.random.Random

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
            println("title = ${extension.title}")
            println("version = ${extension.version}")
            println("author = ${extension.sub.author}")
        }
    }
}

open class MainExtension(private val project: Project) {
    var title: String = ""
    val version: String get() {
        return "version_${project.name}_${Random.nextInt()}"
    }
    var sub: SubExtension = SubExtension()

    fun printVersion() {
        println("MainExtension: $version")
    }

    init {
        sub = project.extensions.create("sub", SubExtension::class.java)
    }
}
open class SubExtension {
    var author: String = ""
}
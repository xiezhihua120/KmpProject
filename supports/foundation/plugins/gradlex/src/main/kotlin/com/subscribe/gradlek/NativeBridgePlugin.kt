package com.subscribe.gradlek

import org.gradle.api.Plugin
import org.gradle.api.Project

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
            println("chapter = ${extension.chapter}")
            println("author = ${extension.sub.author}")
        }
    }
}

open class MainExtension(project: Project) {
    var title: String = ""
    var chapter: Int = 0
    var sub: SubExtension = SubExtension()

    init {
        sub = project.extensions.create("sub", SubExtension::class.java)
    }
}
open class SubExtension {
    var author: String = ""
}
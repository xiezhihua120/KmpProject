public open class JacocoPublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.afterEvaluate {
            project.plugins.apply("jacoco")

            val jacocoExtension: JacocoPluginExtension = project.extensions.getByType(JacocoPluginExtension::class.java)
            jacocoExtension.toolVersion = "0.8.7"

            project.tasks.register<JacocoReport>("jacocoTestReport") {
                group = "verification"
                dependsOn("testDebugUnitTest")

                sourceDirectories.setFrom(files(project.fileTree("${projectDir}/src/commonMain/kotlin")))
                classDirectories.setFrom(project.fileTree("${buildDir}/tmp/kotlin-classes/debug"))
                executionData.setFrom(fileTree("$buildDir") {
                    setIncludes(
                        listOf(
                            "jacoco/testDebugUnitTest.exec",
                            "outputs/code-coverage/connected/*coverage.ec"
                        )
                    )
                })

                reports {
                    csv.required.set(false)
                    xml.required.set(true)
                    html.required.set(true)
                    html.outputLocation.set(file("${buildDir}/jacoco"))
                }
            }
        }
    }
}

project.apply<JacocoPublishPlugin>()


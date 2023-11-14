public open class GithubPublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("xiezh: project ${project.name} apply plugin!")
    }
}

project.apply<GithubPublishPlugin>()
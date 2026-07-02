
plugins {
    id("java-library")
    // see https://fabricmc.net/develop/ for new versions
    id("fabric-loom") version "1.17-SNAPSHOT" apply false
    // see https://projects.neoforged.net/neoforged/moddevgradle for new versions
    id("net.neoforged.moddev") version "2.0.140" apply false
    id("me.modmuss50.mod-publish-plugin") version "2.0.1"
}


val repo: String by project
val branch: String by project
val minecraft_version: String by project
val mod_version: String by project

version = "${mod_version}+${project.name}-${minecraft_version}"

publishMods {
    github("githubParent") {
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))
        repository.set(repo)
        commitish.set(branch)

        allowEmptyFiles.set(true)
        changelog.set(providers.fileContents(rootProject.layout.projectDirectory.file("changelog.md")).asText)

        type.set(STABLE)

        this.version = project.version.toString()
        this.displayName =
            (project.version.toString()).replace("+", " ").replace("-", " ").replace("fabric", "Fabric")
    }
}

tasks.register("uploadMod") {
    description = "Uploads the mod to various platforms."
    group = "mod"

    var theTasks: MutableList<Any> = mutableListOf()

    var fabric : Project = project(":fabric")
    var neoforge : Project = project(":neoforge")

    if (project.providers.environmentVariable("PUBLISH_GH").getOrElse("False") == "True") {
        theTasks.add(rootProject.tasks["publishGHParent"])
        theTasks.add(fabric.tasks["publishGHFabric"])
        theTasks.add(neoforge.tasks["publishGHNeoforge"])
    }

    if (project.providers.environmentVariable("PUBLISH_CF").getOrElse("False") == "True") {
        theTasks.add(fabric.tasks["publishCurseforgeFabric"])
        theTasks.add(neoforge.tasks["publishCurseforgeNeoforge"])
    }

    if (project.providers.environmentVariable("PUBLISH_MR").getOrElse("False") == "True") {
        theTasks.add(fabric.tasks["publishModrinthFabric"])
        theTasks.add(neoforge.tasks["publishModrinthNeoforge"])
    }

    finalizedBy(theTasks)
}

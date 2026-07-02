import me.modmuss50.mpp.PublishModTask

plugins {
    id("java-library")
    // see https://fabricmc.net/develop/ for new versions
    id("net.fabricmc.fabric-loom") version "1.15-SNAPSHOT" apply false
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
    if (project.providers.environmentVariable("publishGH").getOrElse("False").equals("True")) {
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
}

tasks.register("uploadMod") {
    description = "Uploads the mod."
    group = "mod"

    finalizedBy(tasks["publishMods"], project(":fabric").tasks["publishMods"], project(":neoforge").tasks["publishMods"])
}

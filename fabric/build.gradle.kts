import me.modmuss50.mpp.PublishOptions
import java.util.Locale

plugins {
    id("gremdle-loader")
    id("net.fabricmc.fabric-loom")
    id("me.modmuss50.mod-publish-plugin") version "2.0.1"
}

val minecraft_version : String by project

val mod_id: String by project
val mod_name: String by project

val fabric_loader_version : String by project
val fabric_api_version : String by project

val curseforge_id: String by project
val modrinth_id: String by project
val repo: String by project
val branch: String by project

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    implementation ("net.fabricmc:fabric-loader:${fabric_loader_version}")
    implementation ("net.fabricmc.fabric-api:fabric-api:${fabric_api_version}+${minecraft_version}")
}

loom {
    var ct = project(":common").file("src/main/resources/${mod_id}.classtweaker")

    if (ct.exists()) {
        accessWidenerPath.set(ct)
    }

    runs {
        this.getByName("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run/client")
        }

        this.getByName("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run/server")
        }
    }
}

fabricApi {
    configureDataGeneration {
        client = true
        outputDirectory = project(":common").file("src/main/generated")
    }
}

publishMods {
    plugins.apply("java-library")

    val publishes: PublishOptions = publishOptions {
        changelog.set(providers.fileContents(rootProject.layout.projectDirectory.file("changelog.md")).asText)

        type.set(STABLE)

        this.version = project.version.toString()
        this.displayName = (project.version.toString()).replace("+", " ").replace("-", " ").replace("fabric", "Fabric")
        file = (project.tasks.named<Jar>("jar").get().archiveFile)

        modLoaders = listOf("fabric", "quilt")
    }.get()

    if (project.providers.environmentVariable("publishCF").getOrElse("False").equals("True")) {
        curseforge("curseforgeFabric") {
            from(publishes)

            accessToken.set(
                providers.environmentVariable("CURSEFORGE_TOKEN").orNull ?: project.findProperty("curseforgeToken")
                    ?.toString()
            )
            projectId.set(curseforge_id)
            minecraftVersions.add(minecraft_version)

            changelogType.set("markdown")

            javaVersions.add(JavaVersion.VERSION_25)

            client.set(true)
            server.set(true)

            requires("fabric-api")
        }
    }

    if (project.providers.environmentVariable("publishMR").getOrElse("False").equals("True")) {
        modrinth("modrinthFabric") {
            from(publishes)

            accessToken.set(
                providers.environmentVariable("MODRINTH_PAT").orNull ?: project.findProperty("modrinthPAT")?.toString()
            )
            projectId.set(modrinth_id)
            minecraftVersions.add(minecraft_version)

            projectDescription.set(providers.fileContents(rootProject.layout.projectDirectory.file("readme.md")).asText)

            requires("fabric-api")
        }
    }

    if (project.providers.environmentVariable("publishGH").getOrElse("False").equals("True")) {
        github("ghFabric") {
            accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))

            file = (project.tasks.named<Jar>("jar").get().archiveFile)
            this.parent(project(":").tasks.named("publishGithubParent"))
        }
    }
}

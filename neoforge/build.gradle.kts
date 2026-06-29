import me.modmuss50.mpp.PublishOptions
import java.util.Locale
import kotlin.text.replace

plugins {
    id("gremdle-loader")
    id("net.neoforged.moddev")
    id("me.modmuss50.mod-publish-plugin") version "2.0.1"
}

val minecraft_version : String by project

val mod_id: String by project
val mod_name: String by project

val neoforge_version : String by project

val curseforge_id: String by project
val modrinth_id: String by project
val repo: String by project
val branch: String by project

neoForge {
    version = neoforge_version
    // Automatically enable neoforge AccessTransformers if the file exists
    var at = project(":common").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
        accessTransformers {
            from(at.absolutePath)
            publish(at)
        }
    }

    val intInject = project(":common").file("interfaces.json")
    if (intInject.exists()) {
        interfaceInjectionData {
            from(intInject.absolutePath)
            publish(intInject)
        }
    }

    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
            ideName = "NeoForge ${this.name.capitalize()} (${project.path})" // Unify the run config names with fabric
        }
        register("client") {
            client()
            gameDirectory = project.file("run/client")
        }
        register("data") {
            clientData()
            gameDirectory = project.file("run/client")
            // DataGen can be run by - "./gradlew :neoforge:runData" in Terminal.
            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            programArguments.addAll( "--mod", mod_id, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
        }
        register("server") {
            server()
            gameDirectory = project.file("run/server")
        }
    }
    mods {
        this.register(mod_id) {
            sourceSet (sourceSets.main.get())
        }
    }
}

sourceSets.main.get().resources {
    srcDir (project(":common").file("src/main/generated"))
}

publishMods {
    plugins.apply("java-library")

    val publishes: PublishOptions = publishOptions {
        changelog.set(providers.fileContents(rootProject.layout.projectDirectory.file("changelog.md")).asText)

        type.set(STABLE)

        this.version = project.version.toString()
        this.displayName = (project.version.toString()).replace("+", " ").replace("-", " ").replace("neoforge", "Neoforge")
        file = (project.tasks.named<Jar>("jar").get().archiveFile)

        modLoaders.add("neoforge")
    }.get()

    curseforge("curseforgeNeo") {
        from(publishes)

        accessToken.set(
            providers.environmentVariable("CURSEFORGE_TOKEN").orNull ?: project.findProperty("curseforgeToken")?.toString()
        )
        projectId.set(curseforge_id)
        minecraftVersions.add(minecraft_version)

        changelogType.set("markdown")

        javaVersions.add(JavaVersion.VERSION_25)

        client.set(true)
        server.set(true)

    }

    modrinth("modrinthNeo") {
        from(publishes)

        accessToken.set(
            providers.environmentVariable("MODRINTH_TOKEN").orNull ?: project.findProperty("modrinthPAT")?.toString()
        )
        projectId.set(modrinth_id)
        minecraftVersions.add(minecraft_version)
    }


    github("ghNeo") {
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))

        file = (project.tasks.named<Jar>("jar").get().archiveFile)
        this.parent(project(":").tasks.named("publishGithubParent"))
    }
}

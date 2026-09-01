import me.modmuss50.mpp.PublishOptions
import io.gremstudio.gremdle.util.Loaders

plugins {
    id("gremdle-loader")
    id("io.gremstudio.gremdle")
    id("net.fabricmc.fabric-loom")
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

val modId = providers.gradleProperty("mod_id").get()
val modName = providers.gradleProperty("mod_name").get()

val fabricLoaderVersion = providers.gradleProperty("fabric_loader_version").get()
val fabricApiVersion = providers.gradleProperty("fabric_api_version").get()

val curseforgeId = providers.gradleProperty("curseforge_id").get()
val modrinthId = providers.gradleProperty("modrinth_id").get()
val repo = providers.gradleProperty("repo").get()
val branch = providers.gradleProperty("branch").get()

dependencies {
    //minecraft("com.mojang:minecraft:${minecraftVersion}")
    //implementation ("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
    implementation ("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}+${minecraftVersion}")
}

loom {
    var ct = project(":common").file("src/main/resources/${modId}.classtweaker")

    if (ct.exists()) {
        accessWidenerPath.set(ct)
    }

    runs {
        this.getByName("client") {
            client()
            displayName = "Fabric Client"
            generateRunConfig.set(true)
            runDirectory.set(project.file("run/client"))
        }

        this.getByName("server") {
            server()
            displayName = "Fabric Server"
            generateRunConfig.set(true)
            runDirectory.set(project.file("run/server"))
        }
    }
}

fabricApi {
    configureDataGeneration {
        client = true
        outputDirectory = project(":common").file("src/main/generated")
    }
}

sourceSets {
    create("testmod") {
        compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.main.get().output + project(":common").sourceSets["testmod"].output
        runtimeClasspath += sourceSets.main.get().runtimeClasspath + sourceSets.main.get().output + project(":common").sourceSets["testmod"].output

        resources {
            srcDir (project(":common").file("src/testmod/generated"))
        }
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

    curseforge("curseforgeFabric") {
        from(publishes)

        accessToken.set(
            providers.environmentVariable("CURSEFORGE_TOKEN").orNull ?: project.findProperty("curseforgeToken")
                ?.toString()
        )
        projectId.set(curseforgeId)
        minecraftVersions.add(minecraftVersion)

        changelogType.set("markdown")

        javaVersions.add(JavaVersion.VERSION_25)

        client.set(true)
        server.set(true)

        requires("fabric-api")
    }

    modrinth("modrinthFabric") {
        from(publishes)

        accessToken.set(
            providers.environmentVariable("MODRINTH_PAT").orNull ?: project.findProperty("modrinthPAT")?.toString()
        )
        projectId.set(modrinthId)
        minecraftVersions.add(minecraftVersion)

        projectDescription.set(providers.fileContents(rootProject.layout.projectDirectory.file("readme.md")).asText)

        requires("fabric-api")
    }

    github("ghFabric") {
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))

        file = (project.tasks.named<Jar>("jar").get().archiveFile)
        this.parent(project(":").tasks.named("publishGithubParent"))
    }

}

gremdle {
    //loader.name = "fabric"
    loader {
        loader = Loaders.FABRIC
        setMixin("gremlib.fabric.mixins.json")
        loaderVersion = fabricLoaderVersion
        //setClassTweaker("gremlib.classtweaker")
        logger.lifecycle("HEY I WAS CALLED HERE!!! OVER HERE!!!")
    }
}

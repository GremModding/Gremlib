plugins {
    id("java-library")
    id("maven-publish")
}

val javaVersion: String = providers.gradleProperty("java_version").get()
val minecraftVersion: String = providers.gradleProperty("minecraft_version").get()

val modName: String = providers.gradleProperty("mod_name").get()
val modId: String = providers.gradleProperty("mod_id").get()
val modVersion: String = providers.gradleProperty("mod_version").get()
val modAuthor: String = providers.gradleProperty("mod_author").get()

var release: Boolean = providers.environmentVariable("RELEASE_MODE").getOrElse("False") == "True"

base {
    version = "${modVersion}+${project.name}-${minecraftVersion}" + if (release) "" else "-SNAPSHOT"
    archivesName = modId
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenLocal()
    mavenCentral()
    // https://docs.gradle.org/current/userguide/declaring_repositories.html#declaring_content_exclusively_found_in_one_repository

    exclusiveContent {
        forRepository {
            maven {
                name = "FabricMC"
                url = uri("https://maven.fabricmc.net")
            }
        }
        filter { includeGroupAndSubgroups("net.fabricmc.sponge-mixin") }
    }

    maven {
        name = "BlameJared"
        url = uri("https://maven.blamejared.com")
    }

    maven {
        name = "DevOS Snapshots"
        url = uri("https://mvn.devos.one/snapshots/")
    }
}

dependencies {
    /*if (!(project.hasProperty("gremdle.include-gremlib") && project.property("gremdle.include-gremlib")?.equals("false") == true)) {
        var gremlib_version : String = project.property("gremlib_version") as String
        implementation("io.gremstudio:gremlib:${gremlib_version}+${project.name}")
    }*/
}

// Declare capabilities on the outgoing configurations.
// Read more about capabilities here: https://docs.gradle.org/current/userguide/component_capabilities.html#sec:declaring-additional-capabilities-for-a-local-component
arrayOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations[variant].outgoing {
        capability("${group}:${modId}:${modVersion}")
        capability("${group}:${modId}:${modVersion}+${project.name}")
        capability("${group}:${modId}:${modVersion}+${project.name}-${minecraftVersion}")
    }

    publishing.publications.configureEach {
        if (this is MavenPublication)
            suppressPomMetadataWarningsFor(variant)
    }
}

tasks {
    getByName<Jar>("sourcesJar") {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${modName}" }
        }
    }


    getByName<Jar>("jar") {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${modName}" }
        }

        val archiveVersion = this.archiveVersion
        manifest {
            attributes += mapOf(
                "Specification-Title" to modName,
                "Specification-Vendor" to modAuthor,
                "Specification-Version" to archiveVersion,
                "Implementation-Title" to modName,
                "Implementation-Version" to archiveVersion,
                "Implementation-Vendor" to modAuthor,
                "Built-On-Minecraft" to minecraftVersion
            )
        }
    }

    getByName<Javadoc>("javadoc") {
        if (options is CoreJavadocOptions) {
            (options as CoreJavadocOptions).also {
                it.addStringOption("Xdoclint:-missing", "-quiet")
            }
        }
    }


   getByName<ProcessResources>("processResources") {
        var expandProps = mutableMapOf(
            "version" to modVersion,
            //"group" to project.group, //Else we target the task's group.
            "minecraft_version" to minecraftVersion
        )

        var jsonExpandProps = mutableMapOf<String, Any>()

       expandProps.forEach {
                entry -> jsonExpandProps += mapOf(entry.key to
                    (entry.value.replace("\n", "\\\\n")) as Any
                )
        }

        filesMatching(listOf("META-INF/neoforge.mods.toml")) {
            expand(expandProps)
        }

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json")) {
            expand(jsonExpandProps)
        }

        inputs.properties(expandProps)
    }
}

publishing {
    var rel = "Snapshots"
    if (release) rel = "Release"

    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = modId
            from(components.getByName("java"))
        }
    }

    repositories {
        maven("https://mvn.devos.one/${rel.lowercase()}") {
            name = "devOS"
            credentials {
                username = providers.environmentVariable("DEVOS_USERNAME").orNull ?: project.findProperty("devOSUsername")?.toString()
                password = providers.environmentVariable("DEVOS_PASSWORD").orNull ?: project.findProperty("devOSPassword")?.toString()
            }
        }
	}
}

plugins {
    id("java-library")
    id("maven-publish")
}

val java_version: String by project
val minecraft_version: String by project
val mod_id: String by project
val mod_version: String by project
val mod_name: String by project
val mod_author: String by project
var release: Boolean = providers.environmentVariable("RELEASE_MODE").getOrElse("False") == "True"

base {
    version = "${mod_version}+${project.name}-${minecraft_version}" + if (release) "" else "-SNAPSHOT"
    archivesName = mod_id
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(java_version)
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    mavenLocal()
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
        capability("${group}:${mod_id}:${mod_version}")
        capability("${group}:${mod_id}:${mod_version}+${project.name}")
        capability("${group}:${mod_id}:${mod_version}+${project.name}-${minecraft_version}")
    }

    publishing.publications.configureEach {
        if (this is MavenPublication)
            suppressPomMetadataWarningsFor(variant)
    }
}

tasks {
    getByName<Jar>("sourcesJar") {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${mod_name}" }
        }
    }


    getByName<Jar>("jar") {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${mod_name}" }
        }

        val archiveVersion = this.archiveVersion
        manifest {
            attributes += mapOf(
                "Specification-Title" to mod_name,
                "Specification-Vendor" to mod_author,
                "Specification-Version" to archiveVersion,
                "Implementation-Title" to mod_name,
                "Implementation-Version" to archiveVersion,
                "Implementation-Vendor" to mod_author,
                "Built-On-Minecraft" to minecraft_version
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
            "version" to mod_version,
            //"group" to project.group, //Else we target the task's group.
            "minecraft_version" to minecraft_version
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
            artifactId = mod_id
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

plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
}

group = "io.gremstudio"
version = "1.0"
var release: Boolean = providers.environmentVariable("RELEASE_MODE").getOrElse("False") == "True"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net")
    }
}

dependencies {
    implementation("net.fabricmc:fabric-loom:1.17-SNAPSHOT")
    implementation("net.neoforged:moddev-gradle:2.0.144")
    implementation("me.modmuss50:mod-publish-plugin:2.2.0")
}

gradlePlugin {
    plugins {
        create("gremdle") {
            id = "io.gremstudio.gremdle"
            implementationClass = "io.gremstudio.gremdle.GremdlePlugin"

        }
    }
}

publishing {
    var rel = "Snapshots"
    if (release) rel = "Release"

    publications {
        gradlePlugin.plugins.forEach { plugin ->
            register<MavenPublication>("mavenJava" + plugin.name) {
                artifactId = plugin.name
                from(components.getByName("java"))
            }
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


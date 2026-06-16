plugins {
    id("gremdle-loader")
    id("net.fabricmc.fabric-loom")
}

val minecraft_version : String by project

val mod_id: String by project
val mod_name: String by project

val fabric_loader_version : String by project
val fabric_api_version : String by project

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

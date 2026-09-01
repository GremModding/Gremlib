plugins {
    id("java-library")
    id("gremdle-common")
}

val mod_id: String = providers.gradleProperty("mod_id").get()

val commonJava = configurations.create("commonJava") {
    isCanBeResolved = true
}

val commonResources = configurations.create("commonResources") {
    isCanBeResolved = true
}

dependencies {
    compileOnly(project(":common")) {
        capabilities { requireCapability("$group:$mod_id") }
        val loaderAttribute: Attribute<String> = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
        attributes {
            attribute(loaderAttribute, "common")
        }
    }

    commonJava(project(":common", "commonJava"))
    commonResources(project(":common", "commonResources"))
}

tasks {
    getByName<ProcessResources>("processResources") {
        dependsOn(commonResources)
        from(commonResources)
    }

    getByName<JavaCompile>("compileJava") {
        dependsOn(commonJava)
        source(commonJava)
    }

    getByName<Javadoc>("javadoc") {
        dependsOn(commonJava)
        source(commonJava)
    }

    getByName<Jar>("sourcesJar") {
        dependsOn(commonJava)
        from(commonJava)
        dependsOn(commonResources)
        from(commonResources)
    }
}

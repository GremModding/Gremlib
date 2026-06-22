plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
}

group = "io.gremstudio"
version = "1.0"

repositories {
    mavenCentral()
}


publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            groupId = group as String?

        }
    }
}

/*gradlePlugin {
    plugins {
        create("gremdle") {
            id = "io.gremstudio.gremdle"
            implementationClass = "io.gremstudio.gremdle.GremdlePlugin"
        }
    }
}*/

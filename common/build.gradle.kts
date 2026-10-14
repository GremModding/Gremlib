import io.gremstudio.gauntlet.util.Loaders

plugins {
    id("java-library")
    id("net.neoforged.moddev")
    id("io.gremstudio.gauntlet")
}

val neoformVersion = providers.gradleProperty("neoform_version").get()

sourceSets {
    create("testmod") {
        compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().runtimeClasspath + sourceSets.main.get().output
    }
}


gauntlet {
    loader {
        loader = Loaders.COMMON
        setMixin("gremlib.mixins.json")
        setClassTweaker("gremlib.classtweaker")
    }
}

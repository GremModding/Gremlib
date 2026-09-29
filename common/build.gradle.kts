import io.gremstudio.gauntlet.util.Loaders

plugins {
    id("java-library")
    //id("gauntlet-common")
    id("net.neoforged.moddev")
    id("io.gremstudio.gauntlet")
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()
val neoformVersion = providers.gradleProperty("neoform_version").get()
val mixinVersion = providers.gradleProperty("mixin_version").get()
val fabricMixinVersion = providers.gradleProperty("fabric_mixin_version").get()
val mixinExtrasVersion = providers.gradleProperty("mixin_extras_version").get()


/*neoForge {
    neoFormVersion = neoformVersion
    // Automatically enable AccessTransformers if the file exists
    val at = file("src/main/resources/META-INF/common.accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
        accessTransformers {
            from(at.absolutePath)
            publish(at)
        }
    }

    val intInject = file("interfaces.json")
    if (intInject.exists()) {
        interfaceInjectionData {
            from(intInject.absolutePath)
            publish(intInject)
        }
    }
}

dependencies {
    compileOnly("net.fabricmc:sponge-mixin:${fabricMixinVersion}+mixin.${mixinVersion}")

    // fabric and neoforge both bundle mixinextras, so it is safe to use it in common
    compileOnly("io.github.llamalad7:mixinextras-common:${mixinExtrasVersion}")
    annotationProcessor("io.github.llamalad7:mixinextras-common:${mixinExtrasVersion}")
}


val commonJava by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}

val commonResources by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}*/

sourceSets {
    create("testmod") {
        compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().runtimeClasspath + sourceSets.main.get().output
    }
}

/*artifacts {
    add(commonJava.name, (sourceSets.main.get().java.sourceDirectories.singleFile))
    add(commonResources.name, (sourceSets.main.get().resources.sourceDirectories.singleFile))
}*/

gauntlet {
    loader {
        loader = Loaders.COMMON
        loaderVersion = neoformVersion // Temporary workaround until I can make this more proper.
        setMixin("gremlib.mixins.json")
        setClassTweaker("gremlib.classtweaker")
    }
}

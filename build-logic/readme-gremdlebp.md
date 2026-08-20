# Gremdle (Binary Plugin)
Gremdle is a Gradle plugin that wraps around various other Minecraft Modding related Gradle plugins (Loom, ModDevGradle, mod-publish-plugin) in order to provide a single unified source for multiloader support that isn't completely custom

Please not that Gremdle is first and foremost designed for Grem Studio's + Siuolplex's mods. It will be mainly modified to fit the needs of said groups.

(I can't guarantee that it will actually be really supported either!!! If I were you and I was interested in this and knew what I was doing, I would fork it.)

## How to use
This guide assumes you use Kotlin for your build scripts.

`build.gradle.kts`
```
plugins {
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT" apply false // See https://fabricmc.net/develop/ for new versions
    id("net.neoforged.moddev") version "2.0.144" apply false // See https://projects.neoforged.net/neoforged/moddevgradle for new versions
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
    id("io.gremstudio.gremdle")
}
```

`settings.gradle.kts`
```
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net")
        }
        maven {
            name = "DevOS Snapshots"
            url = uri("https://mvn.devos.one/snapshots/")
        }
    }
}
```

## Questions
### Why not use [Jared's Multiloader Template](https://github.com/jaredlll08/MultiLoader-Template)?
While Multiloader Template is a very useful tool that will suffice for most, it requires a lot more indepth maintaining to keep it up to date, especially amongst multiple mods.

It is quite excellent though! The previous version of Gremdle was a fork of Multiloader Template to be written in Kotlin.

### Why not use [Architectury Loom](https://github.com/architectury/architectury-loom)?
Arch Loom exists mainly to fit (Neo)Forge into a Loom sized box. While it works alright, it is a lot more tied up with Loom than would be preferred.


## Other things
- If you are interested in other alternatives to the traditional multiloader plugin system, I would recommend checking out [Cloche](https://github.com/terrarium-earth/cloche) by Terrarium. I haven't tried it out too much yet, but it seems very intriguing. It's metadata DSL was the main inspiration for Gremdle's.


## Things I need to do:
- Add plugin for classtweaker conversion.
- Actually get it working
- Separate this from Gremlib.
- Make an example mod.

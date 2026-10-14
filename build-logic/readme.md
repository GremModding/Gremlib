# Gauntlet 
Gauntlet is a Gradle plugin that wraps around various other Minecraft Modding related Gradle plugins (Loom, ModDevGradle, 
mod-publish-plugin) in order to provide a single unified source for multiloader support that isn't completely custom.

It is technically a fork of [Jared's Multiloader Template](https://github.com/jaredlll08/MultiLoader-Template), although 
it has had major changes made.

Please not that Gauntlet is first and foremost designed for Grem Studio's + Siuolplex's mods. It will be mainly modified 
to fit the needs of said groups.

(I can't guarantee that it will actually be really supported either!!! If I were you and I was interested in this and knew 
what I was doing, I would fork it.)

## How to use
This guide assumes you use Kotlin for your build scripts.

`build.gradle.kts`
```kts
plugins {
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT" apply false // See https://fabricmc.net/develop/ for new versions
    id("net.neoforged.moddev") version "2.0.144" apply false // See https://projects.neoforged.net/neoforged/moddevgradle for new versions
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
    id("io.gremstudio.gauntlet")
}
```

`common/build.gradle.kts/`
```kts
plugins {
    id("java-library")
    id("net.neoforged.moddev")
    id("io.gremstudio.gauntlet")
}

gauntlet {
    loader {
        loader = Loaders.COMMON
        setMixin("gremlib.mixins.json")
        setClassTweaker("gremlib.classtweaker")
    }
}

```

`settings.gradle.kts`
```kts
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
While Multiloader Template is certainly well made and very nice to use, when wanting to maintain a plugin across multiple 
mods I feel it is a lot easier to do a binary plugin. Furthermore, I feel that Gauntlet has an opportunity to grow well 
past what Multiloader Template has to offer.

I would still recommend it for most people, if you are willing to maintain it across your mods it will work great.

### Why not use [Architectury Loom](https://github.com/architectury/architectury-loom)?
Arch Loom exists mainly to fit (Neo)Forge into a Loom sized box. While it works alright, it is a lot more tied up with 
Loom than would be preferred.

### Why the name Gauntlet?
Well two reasons:
1) Gauntlet are a type of glove, gloves wrap around your hand, and this plugin is a wrapper for various plugins.
2) Gigi Murin wears really big gauntlets.

## Other things
- If you are interested in other alternatives to the traditional multiloader plugin system, I would recommend checking out 
[Cloche](https://github.com/terrarium-earth/cloche) by Terrarium. I haven't tried it out too much yet, but it seems very intriguing. Gauntlet uses the @Expect/@Actual 
that Cloche uses, and the Metadata block in Gauntlet was based off of that.


## Things I need to do:
- Add plugin for classtweaker conversion.
- Separate this from Gremlib.
- Make an example mod.

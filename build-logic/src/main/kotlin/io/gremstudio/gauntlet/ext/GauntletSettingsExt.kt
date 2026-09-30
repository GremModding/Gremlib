package io.gremstudio.gauntlet.ext

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

// Acts as settings for the plugin.
abstract class GauntletSettingsExt @Inject constructor(factory: ObjectFactory) {
    abstract val javaVersion: Property<String>
    abstract val exportJavadocJar: Property<String>
    abstract val quietJavadocExport: Property<String>

    //Todo: Move these over to the CommonLoaderExt when thats done.
    abstract val neoformVersion: Property<String>
    abstract val mixinVersion: Property<String>
    abstract val fabricMixinVersion: Property<String>
    abstract val mixinExtrasVersion: Property<String>

    init {
        javaVersion.convention("25")
        exportJavadocJar.convention("false");
        quietJavadocExport.convention("true");

        mixinVersion.convention("0.8.7")
        fabricMixinVersion.convention("0.17.2")
        mixinExtrasVersion.convention("0.5.4")
    }
}

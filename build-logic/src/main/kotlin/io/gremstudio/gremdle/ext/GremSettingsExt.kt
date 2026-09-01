package io.gremstudio.gremdle.ext

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

// Acts as settings for the plugin.
abstract class GremSettingsExt @Inject constructor(factory: ObjectFactory) {
    abstract val javaVersion: Property<String>
    abstract val exportJavadocJar: Property<String>
    abstract val quietJavadocExport: Property<String>
}

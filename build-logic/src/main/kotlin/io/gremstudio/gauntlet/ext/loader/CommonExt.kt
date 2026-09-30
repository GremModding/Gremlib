package io.gremstudio.gauntlet.ext.loader

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class CommonExt @Inject constructor(factory: ObjectFactory) {
    abstract val neoformVersion: Property<String>
    abstract val mixinVersion: Property<String>
    abstract val fabricMixinVersion: Property<String>
    abstract val mixinExtrasVersion: Property<String>

    abstract val accessTransformers: ListProperty<String>
    abstract val interfaceInjections: ListProperty<String>
    abstract val mixins: ListProperty<String>

    init {
        mixinVersion.convention("0.8.7")
        fabricMixinVersion.convention("0.17.2")
        mixinExtrasVersion.convention("0.5.4")
    }
}

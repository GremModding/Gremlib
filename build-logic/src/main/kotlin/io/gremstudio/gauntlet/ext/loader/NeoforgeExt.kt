package io.gremstudio.gauntlet.ext.loader

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import javax.inject.Inject

abstract class NeoforgeExt @Inject constructor(factory: ObjectFactory) {
    abstract val accessTransformers: ListProperty<String>
    abstract val interfaceInjections: ListProperty<String>
    abstract val mixins: ListProperty<String>
}

package io.gremstudio.gauntlet.ext.loader

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import javax.inject.Inject

abstract class FabricExt @Inject constructor(factory: ObjectFactory) {
    abstract val classTweakers: ListProperty<String>
    abstract val mixins: ListProperty<String>
}

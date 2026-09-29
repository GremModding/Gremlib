package io.gremstudio.gauntlet.ext

import io.gremstudio.gauntlet.util.Loaders
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class LoaderExt @Inject constructor(factory: ObjectFactory) {
    abstract val loader: Property<Loaders> // This is whats checked for when seeing if it exists or not.
    abstract val classTweakers: ListProperty<String>
    abstract val mixins: ListProperty<String>
    abstract val loaderVersion: Property<String>

    fun setClassTweaker(ctStr : String) {
        classTweakers.set(arrayListOf(ctStr))
    }

    fun setMixin(mixStr : String) {
        mixins.set(arrayListOf(mixStr))
    }
}

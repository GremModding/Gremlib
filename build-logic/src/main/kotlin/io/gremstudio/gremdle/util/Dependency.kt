package io.gremstudio.gremdle.util

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class Dependency @Inject constructor(val name: String) {
    abstract val version: Property<String>
    abstract val dependingType: Property<DependencyTypes>
    abstract val onLoaders: ListProperty<String>

    fun onLoader(loader: String) {
        onLoaders.set(listOf(loader))
    }
}

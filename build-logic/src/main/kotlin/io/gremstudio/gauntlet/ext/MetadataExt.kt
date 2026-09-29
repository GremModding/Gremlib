package io.gremstudio.gauntlet.ext

import io.gremstudio.gauntlet.metadata.Person
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

abstract class MetadataExt {
        abstract val modName: Property<String>
        abstract val description: Property<String>
        abstract val license: Property<String>
        abstract val contacts: MapProperty<String, String>

        val authors: MutableList<Person> = ArrayList()
        val contributors: MutableList<Person> = ArrayList()
        abstract val icon: Property<String>
}

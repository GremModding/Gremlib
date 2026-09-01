package io.gremstudio.gremdle.ext

import io.gremstudio.gremdle.util.Dependency
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class ModDetailsExt @Inject constructor(factory: ObjectFactory) {
    abstract val loaders: ListProperty<String>
    abstract val minecraftVersion: Property<String>
    abstract val modID: Property<String>
    abstract val modVersion: Property<String>
    val dependency = factory.domainObjectContainer(Dependency::class.java) { name ->
        factory.newInstance(
            Dependency::class.java,
            name
        )
    }
    val metadata: MetadataExt = factory.newInstance(MetadataExt::class.java)
    val upload: UploadExt = factory.newInstance(UploadExt::class.java)

    fun metadata(action: Action<MetadataExt>) {
        action.execute(metadata)
    }

    fun upload(action: Action<UploadExt>) {
        action.execute(upload)
    }

    fun modDependencies(action: Action<NamedDomainObjectContainer<Dependency>>) {
        action.execute(dependency)
    }
}

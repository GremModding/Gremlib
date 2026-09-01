package io.gremstudio.gremdle

import io.gremstudio.gremdle.ext.GremSettingsExt
import io.gremstudio.gremdle.ext.LoaderExt
import io.gremstudio.gremdle.ext.ModDetailsExt
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class GremdleExt @Inject constructor(factory: ObjectFactory) {
    val modDetails: ModDetailsExt = factory.newInstance(ModDetailsExt::class.java)
    val loader: LoaderExt = factory.newInstance(LoaderExt::class.java)
    val gremSettings: GremSettingsExt = factory.newInstance(GremSettingsExt::class.java)


    /*
        modName = "Gremlib"
            version = 0.1.0 // The loader and mc versions would be appended on.
            description = "The library used for various mods."
            license = "MIT"
            author = person.name("Siuol")
            contributor.add(person.name("Siuol"))
     */
    fun modDetails(action: Action<ModDetailsExt>) {
        action.execute(modDetails)
    }

    fun loader(action: Action<LoaderExt>) {
        action.execute(loader)
    }

    fun gremSettings(action: Action<GremSettingsExt>) {
        action.execute(gremSettings)
    }
}

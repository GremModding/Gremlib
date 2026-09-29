package io.gremstudio.gauntlet

import io.gremstudio.gauntlet.ext.GauntletSettingsExt
import io.gremstudio.gauntlet.ext.LoaderExt
import io.gremstudio.gauntlet.ext.ModDetailsExt
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class GauntletExt @Inject constructor(factory: ObjectFactory) {
    val modDetails: ModDetailsExt = factory.newInstance(ModDetailsExt::class.java)
    val loader: LoaderExt = factory.newInstance(LoaderExt::class.java)
    val gremSettings: GauntletSettingsExt = factory.newInstance(GauntletSettingsExt::class.java)


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

    fun gremSettings(action: Action<GauntletSettingsExt>) {
        action.execute(gremSettings)
    }
}

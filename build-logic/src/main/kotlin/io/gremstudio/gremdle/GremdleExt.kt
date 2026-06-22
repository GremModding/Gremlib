package io.gremstudio.gremdle

import org.gradle.api.provider.Property

abstract class GremdleExt {
    abstract val modDetails: Property<ModDetailsExt>
    abstract val common: Property<CommonExt>
    abstract val fabric: Property<FabricExt>
    abstract val neoforge: Property<NeoforgeExt>


    abstract class ModDetailsExt {
        abstract val loaders: Property<Array<String>>
        abstract val minecraftVersion: Property<String>
        abstract val modID: Property<String>
        abstract val metadata: Property<MetadataExt>
        abstract val upload: Property<UploadExt>


        abstract class MetadataExt {
            abstract val modName: Property<String>
            abstract val description: Property<String>
            abstract val license: Property<String>
            abstract val urlIssues: Property<String>

            val authors: MutableList<Person> = ArrayList()
            val contributors: MutableList<Person> = ArrayList()
        }

        abstract class UploadExt {
            abstract val modrinthToken: Property<String>


            abstract val curseforgeToken: Property<String>

            abstract val githubToken: Property<String>
        }
    }

    /*
        modName = "Gremlib"
            version = 0.1.0 // The loader and mc versions would be appended on.
            description = "The library used for various mods."
            license = "MIT"
            author = person.name("Siuol")
            contributor.add(person.name("Siuol"))
     */


    abstract class CommonExt : LoaderExt() {
        abstract val neoformVersion: Property<String>
    }

    abstract class FabricExt : LoaderExt() {
        abstract val loaderVersion: Property<String>
        abstract val apiVersion: Property<String> // Maybe null if no api installed?
    }

    abstract class NeoforgeExt : LoaderExt() {
        abstract val loaderVersion: Property<String>
    }

    abstract class LoaderExt {
        abstract val classTweaker: Property<String>
        abstract val mixin: Property<String>
    }


}

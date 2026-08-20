package io.gremstudio.gremdle

import io.gremstudio.gremdle.ext.LoaderExt
import io.gremstudio.gremdle.ext.ModDetailsExt
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class GremdleTestTask : DefaultTask()  {
    @get:Input
    abstract val testModDetails: Property<ModDetailsExt>

    @get:Input
    abstract val testLoaderExt: Property<LoaderExt>

    @TaskAction
    fun run() {
        logger.lifecycle(
            "TEST" +
            "\n\tModID: " + testModDetails.get().modID.get() +
            "\n\tMod Name: " + testModDetails.get().metadata.modName.get()
        )

        if (testLoaderExt.isPresent) {
            logger.lifecycle(
                "\n\tLoader Detected: " + testLoaderExt.get()
            )
        }
    }
}

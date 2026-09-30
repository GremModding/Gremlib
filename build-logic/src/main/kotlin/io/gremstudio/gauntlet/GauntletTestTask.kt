package io.gremstudio.gauntlet

import io.gremstudio.gauntlet.ext.LoaderExt
import io.gremstudio.gauntlet.ext.ModDetailsExt
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class GauntletTestTask : DefaultTask()  {


    @TaskAction
    fun run() {
        logger.lifecycle(
            "TEST"
        )
    }
}

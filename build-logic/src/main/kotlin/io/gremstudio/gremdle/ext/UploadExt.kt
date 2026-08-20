package io.gremstudio.gremdle.ext

import org.gradle.api.provider.Property

abstract class UploadExt {
    // Its gonna require a lot more than this. I was thinking that it should be a bit more simplified compared to MPP, just enough where you can configure it once in the root and be done.
    abstract val modrinthToken: Property<String>
    abstract val curseforgeToken: Property<String>
    abstract val githubToken: Property<String>
}

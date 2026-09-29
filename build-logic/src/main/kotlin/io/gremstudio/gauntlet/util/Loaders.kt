package io.gremstudio.gauntlet.util

enum class Loaders constructor(name: String) {
    COMMON("common"), FABRIC("fabric"), NEOFORGE("neoforge");

    fun getName() : String {
        return name
    }
}

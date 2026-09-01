package io.gremstudio.gremdle.util

enum class Loaders constructor(name: String) {
    COMMON("common"), FABRIC("fabric"), NEOFORGE("neoforge");

    fun getName() : String {
        return name
    }
}

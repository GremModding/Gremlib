package io.gremstudio.gremdle.metadata

class Person(val name: String) {
    var role: String = ""
    var contact: List<String> = listOf()

    fun setRole(role: String) : Person {
        this.role = role
        return this
    }
}

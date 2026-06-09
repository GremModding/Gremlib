package io.gremstudio.gremdle

// Intended to be expanded on, you know that FMJ supports more data for people than just their name!
// Quilt my beloved had even more too! You could define the role the person had which was pretty nice.
class Person(val name: String) {
    var role: String = ""

    fun setRole(role: String) : Person {
        this.role = role
        return this
    }
}

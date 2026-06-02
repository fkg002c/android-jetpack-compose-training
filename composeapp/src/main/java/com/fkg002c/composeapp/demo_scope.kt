package com.fkg002c.composeapp

class Person(
    var name: String = "",
    var age: Int = 0,
)

abstract class MyRowScope() {
    abstract fun addtext()
    abstract fun addButton()
}

fun main() {
    val person = Person().apply {
        name = "Alice"
        age = 20
    }

    val text: String? = "Hello"

    text?.let {
        println(it.length)
    }

    with(person) {

    }

    /*
        val rowScope = MyRowScope()
        rowScope.apply {
            addtext("Hello")
            addButton() {
                addText("Push me!")
            }
        }
    */
}

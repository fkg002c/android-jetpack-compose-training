package com.fkg002c.composeapp

fun main() {
    fun sayHello(name: String, message: () -> String) {
        println("Hello, $name! ${message()}")
    }

    fun sayBye(name: String, message: () -> String) {
        println("Bye, $name! ${message()}")
    }
    // Вызов без вынесения лямбды
    sayHello("Alice", { "How are you today?" })
    // Вызов с вынесением лямбды
    sayBye("John") {
        sayHello("Alice") {
            "How are you today?"
        }
        "See you later!"
    }
}
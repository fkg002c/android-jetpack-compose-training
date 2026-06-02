package com.fkg002c.composeapp

class GreetingDSL(val mood: String) {
    fun sayHello(name: String, message: String) {
        println("[$mood] Hello, $name! $message")
    }

    fun sayBye(name: String, message: String) {
        println("[$mood] Bye, $name! $message")
    }
}

fun main() {
    // Функция для создания DSL
    fun greeting(
        mood: String,
        block: GreetingDSL.() -> Unit
    ) {
        val dsl = GreetingDSL(mood) // Передаём настроение в DSL
        dsl.block() // Выполняем переданный блок в контексте об
    }

    // !!! Объект класса GreetingDSL НЕ создается явно
    greeting("Happy") {
        sayHello("Alice", "How are you today?")
    }

    greeting("Serious") {
        sayBye("Mark", "Let's talk tomorrow.")
    }
}
package com.intellijentes.sys.colasAlegres

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class ColasAlegresApplication

fun main(args: Array<String>) {
    dotenv().entries().forEach { System.setProperty(it.key, it.value) }
    runApplication<ColasAlegresApplication>(*args)
}

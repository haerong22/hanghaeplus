package io.hhplus.cleanarchitecture.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = [
    "io.hhplus.cleanarchitecture.domain",
    "io.hhplus.cleanarchitecture.jpa",
])
@SpringBootApplication
class DbTestApplication

fun main(args: Array<String>) {
    runApplication<DbTestApplication>(*args)
}
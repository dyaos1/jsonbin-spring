package be.dev.jsonbin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JsonbinApplication

fun main(args: Array<String>) {
    runApplication<JsonbinApplication>(*args)
}

package be.dev.jsonbin.controller

import be.dev.jsonbin.kafka.KafkaProducer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("kafka")
class KafkaController(
    private val kafkaProducer: KafkaProducer,
) {
    @GetMapping("/test/{message}")
    fun kafka(@PathVariable("message") message: String): Unit {
        kafkaProducer.sendMessage("myTopic", message)
    }
}
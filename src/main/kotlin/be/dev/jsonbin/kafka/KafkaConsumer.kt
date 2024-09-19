package be.dev.jsonbin.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {

    @KafkaListener(topics = ["myTopic"], groupId = "json-bin")
    fun listen(message: String) {
        println("Kafka Consumer Received Message Successfully!! : $message")
    }
}
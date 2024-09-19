package be.dev.jsonbin

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class JsonBinApplication
//{
//    @Bean
//    fun topic() = NewTopic("myTopic", 10, 1)  // topic 생성
//
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            runApplication<JsonBinApplication>(*args)
//        }
//    }
//}

fun main(args: Array<String>) {
    runApplication<JsonBinApplication>(*args)
}

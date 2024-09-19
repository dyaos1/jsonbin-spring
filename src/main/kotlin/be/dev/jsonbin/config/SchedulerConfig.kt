package be.dev.jsonbin.config

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class SchedulerConfig {
    @Scheduled(fixedDelay = 60000)
    fun run() {
        println("Running in ${LocalDate.now()} seconds")
    }
}
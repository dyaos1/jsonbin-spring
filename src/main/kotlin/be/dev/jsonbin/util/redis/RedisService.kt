package be.dev.jsonbin.util.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String?>,
) {
    fun set(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }

    fun delByExpire(key: String) {
        redisTemplate.opsForValue().getAndExpire(key, 1, TimeUnit.SECONDS)
    }
}
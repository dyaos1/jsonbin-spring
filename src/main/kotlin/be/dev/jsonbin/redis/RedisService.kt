package be.dev.jsonbin.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException
import kotlin.reflect.full.primaryConstructor

@Service
class RedisService {
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    fun saveData(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun getData(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }

    fun <T: Any> caching(key: String, classType: Class<T>): T? {
        val constructor = classType.kotlin.primaryConstructor ?: return null
        val cached = getData(key) ?: return null
        val params = constructor.parameters.associateWith { cached }
        return constructor.callBy(params)
    }
}
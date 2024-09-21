package be.dev.jsonbin

import be.dev.jsonbin.dto.GetResponseDto
import be.dev.jsonbin.dto.PostRequestDto
import be.dev.jsonbin.dto.PostResponseDto
import be.dev.jsonbin.dto.PutRequestDto
import be.dev.jsonbin.redis.RedisService
import be.dev.jsonbin.util.ObjectUtil
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class Service(
    private val repository: Repository,
    private val redisService: RedisService,
) {
    fun getJson(uuid: UUID, type: String): GetResponseDto {
        val cached = redisService.getData(uuid.toString())
        checkNotNull(cached) {
            println("no caching")
            val item = repository.findByUuid(uuid)
            checkNotNull(item) { "No json exists for id $uuid" }

            return when (type) {
                "DETAIL" -> Items.getDetail(item)
                "SIMPLE" -> Items.getSimple(item)
                else -> throw IllegalArgumentException("Unknown type $type")
            }
        }
        return Items.getSimple(Items(
            payload = cached
        ))
    }

    fun postJson(postRequestDto: PostRequestDto): PostResponseDto {
        val uuid = repository.save(Items(
            payload = postRequestDto.payload,
        )).uuid

        redisService.saveData(uuid.toString(), postRequestDto.payload)

        return PostResponseDto(
            uuid = uuid
        )
    }

    fun updateJson(uuid: UUID, putRequestDto: PutRequestDto): Items {
        val found = repository.findByUuid(uuid) ?: throw IllegalArgumentException("No json exists for id $uuid")

        val updated = found.copy(
            id = found.id,
            uuid = found.uuid,
            payload = putRequestDto.payload,
            createdAt = found.createdAt,
            updatedAt = LocalDateTime.now(),
            version = found.version,
        )

        redisService.saveData(found.uuid.toString(), putRequestDto.payload)

        return repository.save(updated)
    }

    fun deleteJson(uuid: UUID) {
        return repository.deleteByUuid(uuid)
    }
}
package be.dev.jsonbin.service

import be.dev.jsonbin.dto.GetResponseDto
import be.dev.jsonbin.dto.PostRequestDto
import be.dev.jsonbin.dto.PostResponseDto
import be.dev.jsonbin.dto.PutRequestDto
import be.dev.jsonbin.dto.PutResponseDto
import be.dev.jsonbin.model.Items
import be.dev.jsonbin.model.Repository
import be.dev.jsonbin.util.redis.RedisService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class Service(
    private val repository: Repository,
    private val redisService: RedisService,
) {
    fun getJsonSimple(uuid: UUID): GetResponseDto {
        val cached = redisService.get(uuid.toString())
        checkNotNull(cached) {
            val item = repository.findByUuid(uuid)
            checkNotNull(item) { "No json exists for id $uuid" }
            return Items.getSimple(item)
        }
        return Items.getSimple(
            Items(
            payload = cached
        )
        )
    }


    fun getJsonDetail(uuid: UUID): GetResponseDto {
        val item = repository.findByUuid(uuid)
        checkNotNull(item) { "No json exists for id $uuid" }
        return Items.getDetail(item)
    }


    fun postJson(postRequestDto: PostRequestDto): PostResponseDto {
        val uuid = repository.save(
            Items(
            payload = postRequestDto.payload,
        )
        ).uuid

        redisService.set(uuid.toString(), postRequestDto.payload)

        return PostResponseDto(
            uuid = uuid
        )
    }


    fun updateJson(uuid: UUID, putRequestDto: PutRequestDto): PutResponseDto {
        val item = repository.findByUuid(uuid)
        checkNotNull(item) { "No json exists for id $uuid" }
        val updatedItem = item.copy(
            payload = putRequestDto.payload,
            updatedAt = LocalDateTime.now(),
        )
        redisService.set(uuid.toString(), putRequestDto.payload)
        return repository.save(updatedItem).toPutResponseDto()
    }

    fun deleteJson(uuid: UUID) {
        val item = repository.findByUuid(uuid) ?: throw RuntimeException("No item exists for id $uuid")
        redisService.delByExpire(uuid.toString())
        repository.delete(item)
    }
}
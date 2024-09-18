package be.dev.jsonbin

import be.dev.jsonbin.dto.GetResponseDto
import be.dev.jsonbin.dto.PostRequestDto
import be.dev.jsonbin.dto.PostResponseDto
import be.dev.jsonbin.dto.PutRequestDto
import be.dev.jsonbin.util.ObjectUtil
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class Service(
    private val repository: Repository
) {
    fun getJson(uuid: UUID, type: String): GetResponseDto {
        val item = repository.findByUuid(uuid)
        checkNotNull(item) { "No json exists for id $uuid" }

        return if (type == "DETAIL") {
            Items.getDetail(item)
        } else if (type == "SIMPLE") {
            Items.getSimple(item)
        } else {
            throw IllegalArgumentException("Unknown type $type")
        }

    }

    fun postJson(postRequestDto: PostRequestDto): PostResponseDto {
        val uuid = repository.save(Items(
            payload = postRequestDto.payload,
        )).uuid

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
        return repository.save(updated)
    }

    fun deleteJson(uuid: UUID) {
        return repository.deleteByUuid(uuid)
    }
}
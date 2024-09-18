package be.dev.jsonbin

import be.dev.jsonbin.dto.GetResponseDto
import be.dev.jsonbin.dto.PostRequestDto
import be.dev.jsonbin.dto.PostResponseDto
import be.dev.jsonbin.dto.PutRequestDto
import be.dev.jsonbin.dto.payLoadMapper
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class Service(
    private val repository: Repository
) {
    fun getJson(uuid: UUID): GetResponseDto {
        val item = repository.findByUuid(uuid)
            .orElseThrow{IllegalArgumentException("No json exists for id $uuid")}

        return payLoadMapper(item.payload, item)
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
        val found = repository.findByUuid(uuid).orElseThrow {IllegalArgumentException("No json exists for id $uuid")}
        found.payload = putRequestDto.payload
        found.updated_at = LocalDateTime.now()
        return repository.save(found)
    }

    fun deleteJson(uuid: UUID) {
        return repository.deleteByUuid(uuid)
    }
}
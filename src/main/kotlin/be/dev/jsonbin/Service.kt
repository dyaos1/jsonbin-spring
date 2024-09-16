package be.dev.jsonbin

import be.dev.jsonbin.dto.PostRequestDto
import be.dev.jsonbin.dto.PostResponseDto
import be.dev.jsonbin.dto.PutRequestDto
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class Service(
    private val repository: Repository
) {
    fun getJson(id: String): Entity {
        return repository.findById(UUID.fromString(id))
            .orElseThrow{IllegalArgumentException("No json exists for id $id")}
    }

    fun postJson(postRequestDto: PostRequestDto): PostResponseDto {
        val id = repository.save(Entity(
            name = postRequestDto.name,
        )).id ?: throw IllegalStateException("id was not created properly")

        return PostResponseDto(
            id = id
        )
    }

    fun updateJson(id: UUID, putRequestDto: PutRequestDto): Entity {
        val found = repository.findById(id).orElseThrow {IllegalArgumentException("No json exists for id $id")}
        found.name = putRequestDto.name
        return repository.save(found)
    }

    fun deleteJson(id: UUID) {
        return repository.deleteById(id)
    }
}
package be.dev.jsonbin.dto

import be.dev.jsonbin.Items
import be.dev.jsonbin.util.ObjectUtil
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.util.*

sealed class GetResponseDto {
    data class Detail(
        val id: Long? = null,
        var uuid: UUID = UUID.randomUUID(),
        val payload: Map<String, Any> = emptyMap(),
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val version: Int = 0,
    ) : GetResponseDto()

    data class Simple(
        val payload: Map<String, Any> = emptyMap(),
    ) : GetResponseDto()
}



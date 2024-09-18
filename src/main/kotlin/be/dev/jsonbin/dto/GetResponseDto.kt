package be.dev.jsonbin.dto

import be.dev.jsonbin.Items
import be.dev.jsonbin.util.ObjectUtil
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.util.*

data class GetResponseDto(
    val id: Long? = null,
    var uuid: UUID = UUID.randomUUID(),
    val data: Map<String, Any>? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val version: Int = 0,
)

fun payLoadMapper(payload: String?, item: Items): GetResponseDto {
    val getResponse = GetResponseDto(
        id = item.id,
        uuid = item.uuid,
        data = payload?.let {ObjectUtil.jsonToMap(payload)},
        createdAt = item.createdAt,
        updatedAt = item.updatedAt,
        version = item.version,
    )
    return getResponse
}
package be.dev.jsonbin.dto

import be.dev.jsonbin.Items
import java.time.LocalDateTime
import java.util.*

data class GetResponseDto(
    var id: Long? = null,
    var uuid: UUID = UUID.randomUUID(),
    val data: MutableMap<String, Any> = mutableMapOf(),
    var created_at: LocalDateTime,
    var updated_at: LocalDateTime,
    var version: Int = 0,
)

fun payLoadMapper(payload: String?, item: Items): GetResponseDto {
    val getResponse = GetResponseDto(
        id = item.id,
        uuid = item.uuid,
        created_at = item.created_at,
        updated_at = item.updated_at,
        version = item.version,
    )
    if (payload == null) {
       return getResponse
    }

    val rawPayload = payload.replace("{", "").replace("}", "").replace("\"", "")
    val payloadList = rawPayload.split(",")

    for (payloadString in payloadList) {
        val payloadStringPair = payloadString.split(":")
        getResponse.data[payloadStringPair[0]] = payloadStringPair[1]
    }
    return getResponse
}
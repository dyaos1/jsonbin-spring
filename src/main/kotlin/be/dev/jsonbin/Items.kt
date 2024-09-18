package be.dev.jsonbin

import be.dev.jsonbin.dto.GetResponseDto
import be.dev.jsonbin.util.ObjectUtil
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "items")
data class Items(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val uuid: UUID = UUID.randomUUID(),
    val payload: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val version: Int = 0,
) {
    companion object {
        fun getDetail(item: Items): GetResponseDto {
            return GetResponseDto.Detail(
                id = item.id,
                uuid = item.uuid,
                payload = item.payload.let { ObjectUtil.jsonToMap(item.payload!!) },
                createdAt = item.createdAt,
                updatedAt = item.updatedAt,
                version = item.version,
            )
        }

        fun getSimple(item: Items): GetResponseDto {
            return GetResponseDto.Simple(
                payload = item.payload.let { ObjectUtil.jsonToMap(item.payload!!) },
            )
        }
    }
}
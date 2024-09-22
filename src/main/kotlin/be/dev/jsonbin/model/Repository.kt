package be.dev.jsonbin.model

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface Repository : CrudRepository<Items, UUID> {
    fun findByUuid(uuid: UUID): Items?

    fun deleteByUuid(uuid: UUID)

    @Query("update Items i set i.payload=:payload where i.uuid=:uuid")
    @Modifying
    @Transactional
    fun updateJson(@Param("uuid") uuid: UUID, @Param("payload") payload: String): UUID
}
package be.dev.jsonbin

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface Repository : CrudRepository<Items, UUID> {
    fun findByUuid(uuid: UUID): Optional<Items>

    fun deleteByUuid(uuid: UUID)
}
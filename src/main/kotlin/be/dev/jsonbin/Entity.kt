package be.dev.jsonbin

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class Entity(
    @Id @GeneratedValue(generator = "uuid2")
    var id: UUID? = null,
    var name: String? = null,
)
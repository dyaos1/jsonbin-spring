package be.dev.jsonbin

import be.dev.jsonbin.dto.GetResponseDto
import be.dev.jsonbin.dto.PostRequestDto
import be.dev.jsonbin.dto.PostResponseDto
import be.dev.jsonbin.dto.PutRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(path = ["/api/v1/json"])
class Controller(
    private val service: Service
) {
    @GetMapping("/{uuid}")
    fun getJson(@PathVariable("uuid") uuid: UUID): GetResponseDto {
        return service.getJson(uuid)
    }

    @PostMapping(path = ["/"])
    fun postJson(@RequestBody postRequestDto: PostRequestDto): PostResponseDto {
        return service.postJson(postRequestDto)
    }

    @PutMapping("/{id}")
    fun updateJson(
        @PathVariable("id") id: UUID,
        @RequestBody putRequestDto: PutRequestDto
    ): ResponseEntity<Void> {
        try {
            service.updateJson(id, putRequestDto)
            return ResponseEntity.noContent().build()
        } catch(e: Exception) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteJson(@PathVariable("id") id: UUID) : ResponseEntity<Void> {
        try {
            service.deleteJson(id)
            return ResponseEntity.ok().build()
        } catch(e: Exception) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
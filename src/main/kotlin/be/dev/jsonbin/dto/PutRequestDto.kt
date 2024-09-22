package be.dev.jsonbin.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
data class PutRequestDto(
    val payload: String,
)

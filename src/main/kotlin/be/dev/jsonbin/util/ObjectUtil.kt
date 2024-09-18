package be.dev.jsonbin.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.readValue

object ObjectUtil {
    val mapper = jacksonMapperBuilder()
        .addModules(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .build()

    // POJO -> Json String
    fun <T> pojoToJson(pojo: T): String {
        return mapper.writeValueAsString(pojo)
    }

    // Json String -> POJO
    inline fun <reified T> jsonToPojo(jsonString: String): T {
        return mapper.readValue(jsonString)
    }

    // POJO -> Map<String, Any>
    fun <T> pojoToMap(pojo: T): Map<String, Any> {
        return mapper.convertValue(pojo, Map::class.java) as Map<String, Any>
    }

    // Map<String, Any> -> POJO
    inline fun <reified T> mapToPojo(map: Map<String, Any>): T {
        return mapper.convertValue(map, T::class.java)
    }

    // Json String -> Map<String, Any>
    fun jsonToMap(jsonString: String): Map<String, Any> {
        return mapper.readValue(jsonString)
    }

    // Map<String, Any> -> Json String
    fun mapToJson(map: Map<String, Any>): String {
        return mapper.writeValueAsString(map)
    }
}
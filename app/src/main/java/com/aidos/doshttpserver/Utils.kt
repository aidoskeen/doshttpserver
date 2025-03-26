package com.aidos.doshttpserver

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonArray

fun <T> List<T>.toJsonArray(transform: (T) -> JsonElement): JsonArray {
    return buildJsonArray {
        forEach { add(transform(it)) }
    }
}
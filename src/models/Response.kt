package com.pezholio.models

data class Response(val originalURL: String, private val id: String) {
    val shortURL: String = "http://localhost:8080/$id"
}

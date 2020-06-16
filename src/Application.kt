package com.pezholio

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.jackson.*

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import io.ktor.features.ContentNegotiation

import com.pezholio.models.Request
import com.pezholio.models.Response
import com.pezholio.extensions.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
  install(ContentNegotiation) {
    jackson {
      enable(SerializationFeature.INDENT_OUTPUT)
      propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    }
  }

  // Hash Table Response object by ID
  val responseByID = mutableMapOf<String, Response>()

  routing {
    post("/api/v1/encode") {
      log.debug("Hi!")
      // Deserialize JSON body to Request object
      val request = call.receive<Request>()

      // find the Response object if it already exists
      val retrievedResponse = responseByID[request.url.encodeToID()]
      if (retrievedResponse != null) {
          // cache hit
          log.debug("cache hit $retrievedResponse")
          return@post call.respond(retrievedResponse)
      }

      // cache miss
      val response = request.toResponse()
      responseByID[request.url.encodeToID()] = response
      log.debug("cache miss $response")

      // Serialize Response object to JSON body
      call.respond(response)
    }
  }
}


package com.pezholio.models

import com.pezholio.models.Response
import com.pezholio.extensions.*

data class Request(val url: String) {
    fun toResponse(): Response = Response(url, url.encodeToID())
}

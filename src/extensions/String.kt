package com.pezholio.extensions

import java.security.MessageDigest
import java.math.BigInteger

// String extension
fun String.encodeToID(): String {
    // hash String with MD5
    val hashBytes = MessageDigest.getInstance("MD5").digest(this.toByteArray(Charsets.UTF_8))
    // transform to human readable MD5 String
    val hashString = String.format("%032x", BigInteger(1, hashBytes))
    // truncate MD5 String
    val truncatedHashString = hashString.take(6)
    // return id
    return truncatedHashString
}

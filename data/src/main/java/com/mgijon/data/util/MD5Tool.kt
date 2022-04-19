package com.mgijon.data.util

import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date

class MD5Tool(private val publicKey: String, private val privateKey: String) {

    private var ts: Long = 1

    fun getHash(): String {
        val stringHash = "${ts}$privateKey$publicKey"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(stringHash.toByteArray())).toString(16).padStart(32, '0')
    }

    fun getPublicKey(): String = publicKey

    fun getTs(): Long {
        ts = Date().time
        return ts
    }
}
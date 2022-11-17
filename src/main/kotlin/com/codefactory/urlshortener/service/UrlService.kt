package com.codefactory.urlshortener.service

import com.codefactory.urlshortener.exception.HashNotFound
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.MessageDigest

@Service
class UrlService(private val redis: RedisTemplate<String, String>) {

    private val digest = MessageDigest.getInstance("SHA3-512")

    fun createShortURL(url: String): String {
        val hash = createHash(url)
        redis.opsForValue().set(hash, url)

        return hash
    }

    private fun createHash(url: String, lenght: Int = 8): String {
        val bytes = digest.digest(url.toByteArray())
        val hash = String.format("%32x", BigInteger(1, bytes))

        return hash.take(lenght)
    }

    fun resolveShortURL(hash: String): String {
        return redis.opsForValue().get(hash) ?: throw HashNotFound(hash)
    }
}

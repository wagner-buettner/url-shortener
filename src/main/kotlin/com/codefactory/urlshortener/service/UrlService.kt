package com.codefactory.urlshortener.service

import com.codefactory.urlshortener.entity.HashURL
import com.codefactory.urlshortener.exception.HashNotFound
import com.codefactory.urlshortener.repository.HashURLRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.MessageDigest

@Service
class UrlService(
  private val redis: RedisTemplate<String, String>,
  private val repository: HashURLRepository
) {

  private val digest = MessageDigest.getInstance("SHA3-512")

  fun createShortURL(url: String): String {
    val hash = createHash(url)
    redis.opsForValue().set(hash, url)
    val hashURL = HashURL(null, hash, url)
    repository.save(hashURL)

    return hash
  }

  private fun createHash(url: String, lenght: Int = 8): String {
    val bytes = digest.digest(url.toByteArray(Charsets.UTF_8))
    val hash = String.format("%32x", BigInteger(1, bytes))

    return hash.take(lenght)
  }

  fun resolveShortURL(hash: String): String {
    return redis.opsForValue().get(hash) ?: throw HashNotFound(hash)
  }
}

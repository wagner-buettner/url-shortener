package com.codefactory.urlshortener.service

import com.codefactory.urlshortener.entity.HashURL
import com.codefactory.urlshortener.exception.HashNotFound
import com.codefactory.urlshortener.repository.HashURLRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
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
    val hashURL = HashURL(null, hash, url)
    repository.save(hashURL)
    redis.opsForValue().set(hash, url)

    return hash
  }

  private fun createHash(url: String, length: Int = 8): String {
    val bytes = digest.digest(url.toByteArray(Charsets.UTF_8))
    val hash = String.format("%32x", BigInteger(1, bytes))

    return hash.take(length)
  }

  fun resolveShortURL(hash: String): String {
    val hashToReturn = redis.opsForValue().get(hash)

    if (hashToReturn.isNullOrEmpty()) { // cache miss
      val hashStored = repository.findByHash(hash)
      if (hashStored.isPresent) {
        redis.opsForValue().set(hash, hashStored.get().url)
        return hashStored.get().url
      } else {
        throw HashNotFound("Hash not found: $hash")
      }
    } else {
      return hashToReturn;
    }
  }
}

package com.codefactory.urlshortener.service

import com.codefactory.urlshortener.entity.HashURL
import com.codefactory.urlshortener.repository.HashURLRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.time.LocalDateTime
import java.util.*


@ExtendWith(MockitoExtension::class)
internal class URLServiceImplTest {

  @Mock
  private lateinit var redis: RedisTemplate<String, String>

  @Mock
  private lateinit var repository: HashURLRepository

  @Mock
  private lateinit var valueOperations: ValueOperations<String, String>

  lateinit var urlService: URLService

  @BeforeEach
  fun setup() {
    MockitoAnnotations.openMocks(this)
    urlService = URLServiceImpl(redis, repository)
  }

  @Test
  fun whenFirstURLIsBeingHashed() {
    Mockito.`when`(repository.findByHash(anyString())).thenReturn(Optional.empty())
    Mockito.`when`(redis.opsForValue()).thenReturn(valueOperations)
    Mockito.doNothing().`when`(valueOperations).set(anyString(), anyString())

    val url = urlService.createShortURL("https://www.google.com")
    assertEquals("2315c569", url)
  }

  @Test
  fun whenSameURLIsBeingRequested() {
    val hash = "2315c569"
    Mockito.`when`(repository.findByHash(hash)).thenReturn(Optional.of(HashURL(1, hash,"https://www.google.com", LocalDateTime.now())))
    val url = urlService.createShortURL("https://www.google.com")
    assertEquals(hash, url)
  }
}

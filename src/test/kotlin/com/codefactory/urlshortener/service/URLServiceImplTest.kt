package com.codefactory.urlshortener.service

import com.codefactory.urlshortener.entity.HashURL
import com.codefactory.urlshortener.repository.HashURLRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.time.LocalDateTime
import java.util.*


@SpringBootTest
internal class URLServiceImplTest {

  @Autowired
  lateinit var urlService : URLService

  @MockBean
  private lateinit var redis: RedisTemplate<String, String>
  @MockBean
  private lateinit var repository: HashURLRepository
  @MockBean
  private lateinit var valueOperations: ValueOperations<String, String>

  @BeforeEach
  fun setup() {
    MockitoAnnotations.openMocks(this);
    Mockito.`when`(redis.opsForValue()).thenReturn(valueOperations)
    Mockito.doNothing().`when`(valueOperations).set(anyString(), anyString())
  }

  @Test
  fun whenFirstURLIsBeingHashed() {
    Mockito.`when`(repository.findByHash(anyString())).thenReturn(Optional.empty())
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

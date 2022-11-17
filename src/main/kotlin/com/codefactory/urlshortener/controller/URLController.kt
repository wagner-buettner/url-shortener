package com.codefactory.urlshortener.controller

import com.codefactory.urlshortener.dto.URLRequestDTO
import com.codefactory.urlshortener.dto.URLResponseDTO
import com.codefactory.urlshortener.service.URLService
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class URLController(private val service: URLService) {

  companion object : KLogging();

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createShortURL(@RequestBody request: URLRequestDTO): URLResponseDTO {
    val hash = service.createShortURL(request.url)

    logger.info { "url requested: ${request.url} and hashed to: $hash" }
    return URLResponseDTO(hash)
  }

  @GetMapping("/{hash}")
  fun resolveShortURL(@PathVariable hash: String): ResponseEntity<HttpStatus> {
    val target = service.resolveShortURL(hash)

    logger.info { "hash $hash resolved into $target" }
    return ResponseEntity
      .status(HttpStatus.MOVED_PERMANENTLY)
      .location(URI.create(target))
      .header(HttpHeaders.CONNECTION, "close")
      .build()
  }
}

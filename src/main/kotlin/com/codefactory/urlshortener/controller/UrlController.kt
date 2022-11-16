package com.codefactory.urlshortener.controller

import com.codefactory.urlshortener.entity.URLRequest
import com.codefactory.urlshortener.entity.URLResponse
import com.codefactory.urlshortener.service.UrlService
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class UrlController(private val service: UrlService) {

  companion object : KLogging();

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createShortURL(@RequestBody request: URLRequest): URLResponse {
    val hash = service.createShortURL(request.url)

    logger.info { "url requested: ${request.url} and hashed to: $hash" }
    return URLResponse(hash)
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

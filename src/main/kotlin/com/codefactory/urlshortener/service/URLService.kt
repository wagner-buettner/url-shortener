package com.codefactory.urlshortener.service

interface URLService {
  fun createShortURL(url: String): String
  fun resolveShortURL(hash: String): String
}

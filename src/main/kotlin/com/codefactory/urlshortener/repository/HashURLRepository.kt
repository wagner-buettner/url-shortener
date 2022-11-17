package com.codefactory.urlshortener.repository

import com.codefactory.urlshortener.entity.HashURL
import org.springframework.data.repository.CrudRepository
import java.util.*

interface HashURLRepository: CrudRepository<HashURL, String> {
  fun findByHash(hash: String): Optional<HashURL>
}

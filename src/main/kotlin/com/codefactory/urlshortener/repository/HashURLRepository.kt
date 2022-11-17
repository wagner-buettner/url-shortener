package com.codefactory.urlshortener.repository

import com.codefactory.urlshortener.entity.HashURL
import org.springframework.data.repository.CrudRepository

interface HashURLRepository: CrudRepository<HashURL, String> {
}

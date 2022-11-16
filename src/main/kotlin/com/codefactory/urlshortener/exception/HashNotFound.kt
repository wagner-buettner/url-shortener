package com.codefactory.urlshortener.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class HashNotFound(hash: String) : RuntimeException("short URL $hash not found")

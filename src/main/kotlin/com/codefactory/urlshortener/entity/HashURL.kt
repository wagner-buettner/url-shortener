package com.codefactory.urlshortener.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "hashurl")
data class HashURL (
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id : Int?,
  val hash: String,
  val url: String,
  val createdAt: LocalDateTime = LocalDateTime.now()
)

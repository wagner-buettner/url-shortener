package com.codefactory.urlshortener.entity

import javax.persistence.*

@Entity
@Table(name = "hashurl")
data class HashURL (
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id : Int?,
  val hash: String,
  val url: String
)

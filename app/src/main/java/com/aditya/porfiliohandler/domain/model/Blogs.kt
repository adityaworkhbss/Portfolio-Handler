package com.aditya.porfiliohandler.domain.model

import com.google.firebase.Timestamp

data class Blogs(
    val id: String = "",
    val title: String = "",
    val slug: String = "",
    val content: String = "",
    val excerpt: String = "",
    val coverImage: String = "",
    val readingTime: Int = 0,
    val views: Int = 0,
    val published: Boolean = false,
    val tags: List<String> = emptyList(),
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)
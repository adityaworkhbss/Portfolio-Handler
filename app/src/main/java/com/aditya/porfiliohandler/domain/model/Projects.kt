package com.aditya.porfiliohandler.domain.model

import com.google.firebase.Timestamp

data class Projects(
    val title: String = "",
    val description: String = "",
    val longDescription: String = "",
    val coverImage: String = "",
    val githubUrl: String = "",
    val liveUrl: String = "",
    val featured: Boolean = false,
    val order: Int = 0,
    val techStack: List<String> = emptyList(),
    val images: List<String> = emptyList(),
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)
package com.aditya.porfiliohandler.domain.model

data class Experience(
    val id: String = "",
    val role: String = "",
    val company: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val current: Boolean = false,
    val order: Int = 0,
    val techStack: List<String> = emptyList()
)
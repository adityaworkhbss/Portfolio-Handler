package com.aditya.porfiliohandler.domain.model

data class Dashboard(
    val about: About?,
    val projects: List<Projects>,
    val messages: List<Messages>,
    val experience: List<Experience>,
    val blogs: List<Blogs>
)
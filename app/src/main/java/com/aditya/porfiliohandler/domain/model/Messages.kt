package com.aditya.porfiliohandler.domain.model

import com.google.firebase.Timestamp

data class Messages(
    val name: String = "",
    val email: String = "",
    val message: String = "",
    val read: Boolean = false,
    val createdAt: Timestamp? = null
)

package com.aditya.porfiliohandler.domain.repository

import com.aditya.porfiliohandler.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
}
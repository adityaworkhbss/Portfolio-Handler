package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.model.User
import com.aditya.porfiliohandler.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.login(email, password)
    }
}

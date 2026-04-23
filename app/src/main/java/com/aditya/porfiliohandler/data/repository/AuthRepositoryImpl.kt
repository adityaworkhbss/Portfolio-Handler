package com.aditya.porfiliohandler.data.repository

import com.aditya.porfiliohandler.data.datasource.AuthDataSource
import com.aditya.porfiliohandler.domain.model.User
import com.aditya.porfiliohandler.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {

            val user = dataSource.login(email, password)

            Result.success(User(
                id = user.uid,
                email = user.email ?: ""
            ))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.repository.UserRepository

class MessageUseCase(
    private val repository: UserRepository
) {
    suspend fun delete(messages: Messages) = repository.deleteMessage(messages)
}
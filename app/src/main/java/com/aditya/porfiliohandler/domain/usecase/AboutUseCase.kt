package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.model.Stat
import com.aditya.porfiliohandler.domain.repository.UserRepository

class AboutUseCase(
    private val repository: UserRepository
) {
    suspend fun updateStats(stat: Stat) = repository.updateStat(stat)
}

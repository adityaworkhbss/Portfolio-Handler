package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.repository.UserRepository

class DashboardUseCase(
    private val repo : UserRepository
) {
    suspend operator fun invoke() = repo.getDashboard()
}
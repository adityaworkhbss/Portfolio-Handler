package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.model.Experience
import com.aditya.porfiliohandler.domain.repository.UserRepository

class ExperienceUseCase(
    private val repo: UserRepository
) {
    suspend fun updateExperience(experience: Experience) = repo.updateExperience(experience)
    suspend fun addExperience(experience: Experience) = repo.addExperience(experience)
    suspend fun deleteExperience(experience: Experience) = repo.deleteExperience(experience)
}
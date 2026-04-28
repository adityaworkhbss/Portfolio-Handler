package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.model.Projects
import com.aditya.porfiliohandler.domain.repository.UserRepository

class ProjectUseCase(
    val repo : UserRepository
) {
    suspend fun updateProject(project: Projects) = repo.updateProject(project)
    suspend fun addProject(project: Projects) = repo.addProject(project)
    suspend fun deleteProject(project: Projects) = repo.deleteProject(project)
}
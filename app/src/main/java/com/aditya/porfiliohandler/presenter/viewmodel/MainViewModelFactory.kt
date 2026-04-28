package com.aditya.porfiliohandler.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aditya.porfiliohandler.domain.repository.UserRepository
import com.aditya.porfiliohandler.domain.usecase.*

class MainViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                dashboardUseCase = DashboardUseCase(userRepository),
                messageUseCase = MessageUseCase(userRepository),
                aboutUseCase = AboutUseCase(userRepository),
                projectUseCase = ProjectUseCase(userRepository),
                experienceUseCase = ExperienceUseCase(userRepository),
                blogUseCase = BlogUseCase(userRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

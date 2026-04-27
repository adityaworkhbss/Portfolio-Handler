package com.aditya.porfiliohandler.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.porfiliohandler.domain.model.*
import com.aditya.porfiliohandler.domain.usecase.*
import com.aditya.porfiliohandler.presenter.responseHandler.UIEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val dashboardUseCase: DashboardUseCase,
    private val messageUseCase: MessageUseCase,
    private val aboutUseCase: AboutUseCase,
    private val projectUseCase: ProjectUseCase,
    private val experienceUseCase: ExperienceUseCase
) : ViewModel() {

    private val _dashboard = MutableLiveData<Dashboard>()
    val dashboard: LiveData<Dashboard> = _dashboard

    private val _uiEvent = MutableLiveData<UIEvent>()
    val uiEvent : LiveData<UIEvent> = _uiEvent

    fun getDashboardData() {
        viewModelScope.launch {
            _dashboard.value = dashboardUseCase()
        }
    }

    fun deleteMessage(message: Messages) {
        viewModelScope.launch {

            _uiEvent.value = UIEvent.loading

            val res = messageUseCase.delete(message)
            if (res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the message")
            }
        }
    }

    fun readMessage(messages: Messages){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = messageUseCase.read(messages)
            if (res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the message")
            }

        }
    }

    fun updateStat(stat: Stat){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.updateStats(stat)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the stat")
            }
        }
    }

    fun updateSocialLink(oldSocialLink: SocialLink, newSocialLink: SocialLink){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.updateSocialLink(oldSocialLink, newSocialLink)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the social link")
            }
        }
    }

    fun addSocialLink(socialLink: SocialLink){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.addSocialLink(socialLink)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while adding the social link")
            }
        }
    }

    fun updateSkillCategory(oldSkillCategory: SkillCategory, newSkillCategory: SkillCategory){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.updateSkillCategory(oldSkillCategory, newSkillCategory)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the skill category")
            }
        }
    }

    fun addSkillCategory(skillCategory: SkillCategory){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.addSkillCategory(skillCategory)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while adding the skill category")
            }
        }
    }

    fun addProject(project: Projects){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = projectUseCase.addProject(project)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while adding the project")
            }
        }
    }

    fun updateProject(project: Projects){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = projectUseCase.updateProject(project)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the project")
            }
        }
    }

    fun addExperience(experience: Experience){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = experienceUseCase.addExperience(experience)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while adding the experience")
            }
        }
    }

    fun updateExperience(experience: Experience){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = experienceUseCase.updateExperience(experience)
            if(res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the experience")
            }
        }
    }

}


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
    private val experienceUseCase: ExperienceUseCase,
    private val blogUseCase: BlogUseCase
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
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedMessages = currentDashboard.messages.filter { it.id != message.id }
                    _dashboard.value = currentDashboard.copy(messages = updatedMessages)
                }
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
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedMessages = currentDashboard.messages.map {
                        if(it.id == messages.id) it.copy(read = true) else it
                    }
                    _dashboard.value = currentDashboard.copy(messages = updatedMessages)
                }
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
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedStats = currentAbout.stats.map {
                        if(it.label == stat.label) stat else it
                    }
                    val updatedAbout = currentAbout.copy(stats = updatedStats)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
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
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedSocialLinks = currentAbout.socialLinks.map {
                        if(it.platform == oldSocialLink.platform) newSocialLink else it
                    }
                    val updatedAbout = currentAbout.copy(socialLinks = updatedSocialLinks)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
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
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedSocialLinks = currentAbout.socialLinks + socialLink
                    val updatedAbout = currentAbout.copy(socialLinks = updatedSocialLinks)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
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
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedSkills = currentAbout.skills.map {
                        if(it.category == oldSkillCategory.category) newSkillCategory else it
                    }
                    val updatedAbout = currentAbout.copy(skills = updatedSkills)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
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
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedSkills = currentAbout.skills + skillCategory
                    val updatedAbout = currentAbout.copy(skills = updatedSkills)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
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
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedProjects = currentDashboard.projects + project
                    _dashboard.value = currentDashboard.copy(projects = updatedProjects)
                }
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
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedProjects = currentDashboard.projects.map {
                        if(it.id == project.id) project else it
                    }
                    _dashboard.value = currentDashboard.copy(projects = updatedProjects)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the project")
            }
        }
    }

    fun deleteProject(projects: Projects){
        viewModelScope.launch {

            _uiEvent.value = UIEvent.loading

            val res = projectUseCase.deleteProject(projects)
            if (res.isSuccess){
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedProjects = currentDashboard.projects.filter { it.id != projects.id }
                    _dashboard.value = currentDashboard.copy(projects = updatedProjects)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the projects")
            }
        }
    }

    fun addExperience(experience: Experience){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = experienceUseCase.addExperience(experience)
            if(res.isSuccess){
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedExperience = currentDashboard.experience + experience
                    _dashboard.value = currentDashboard.copy(experience = updatedExperience)
                }
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
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedExperience = currentDashboard.experience.map {
                        if(it.id == experience.id) experience else it
                    }
                    _dashboard.value = currentDashboard.copy(experience = updatedExperience)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the experience")
            }
        }
    }

    fun deleteExperience(experience: Experience){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = experienceUseCase.deleteExperience(experience)
            if(res.isSuccess){
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedExperience = currentDashboard.experience.filter { it.id != experience.id }
                    _dashboard.value = currentDashboard.copy(experience = updatedExperience)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the experience")
            }
        }
    }

    fun deleteStat(stat: Stat){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.deleteStat(stat)
            if(res.isSuccess){
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedStats = currentAbout.stats.filter { it.label != stat.label }
                    val updatedAbout = currentAbout.copy(stats = updatedStats)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the stat")
            }
        }
    }

    fun deleteSocialLink(socialLink: SocialLink){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.deleteSocialLink(socialLink)
            if(res.isSuccess){
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedSocialLinks = currentAbout.socialLinks.filter { it.platform != socialLink.platform || it.url != socialLink.url }
                    val updatedAbout = currentAbout.copy(socialLinks = updatedSocialLinks)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the social link")
            }
        }
    }

    fun deleteSkillCategory(skillCategory: SkillCategory){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = aboutUseCase.deleteSkillCategory(skillCategory)
            if(res.isSuccess){
                val currentDashboard = _dashboard.value
                val currentAbout = currentDashboard?.about
                if(currentDashboard != null && currentAbout != null){
                    val updatedSkills = currentAbout.skills.filter { it.category != skillCategory.category }
                    val updatedAbout = currentAbout.copy(skills = updatedSkills)
                    _dashboard.value = currentDashboard.copy(about = updatedAbout)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the skill category")
            }
        }
    }

    fun deleteBlog(blogs: Blogs){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = blogUseCase.deleteBlog(blogs)
            if(res.isSuccess){
                val currentDashboard = _dashboard.value
                if(currentDashboard != null){
                    val updatedBlogs = currentDashboard.blogs.filter { it.id != blogs.id }

                    _dashboard.value = currentDashboard.copy(blogs = updatedBlogs)
                }
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the blog")
            }
        }
    }

    fun publishBlog(
        blogs: Blogs,
        isPublished : Boolean
    ){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = blogUseCase.publishBlog(blogs, isPublished)
            if (res.isSuccess) {

                val currentDashboard = _dashboard.value
                if (currentDashboard != null) {
                    val updatedBlogs = currentDashboard.blogs.map { blog ->
                        if (blog.id == blogs.id) {
                            blog.copy(published = !isPublished)
                        } else {
                            blog
                        }
                    }
                    _dashboard.value = currentDashboard.copy(blogs = updatedBlogs)
                }

                _uiEvent.value = UIEvent.success
            } else {
                if(!isPublished){
                    _uiEvent.value = UIEvent.ShowError("Error while unpublishing the blog")
                } else {
                    _uiEvent.value = UIEvent.ShowError("Error while publishing the blog")
                }
            }
        }
    }
}


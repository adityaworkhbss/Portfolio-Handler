package com.aditya.porfiliohandler.data.repository

import com.aditya.porfiliohandler.data.datasource.UserDataSource
import com.aditya.porfiliohandler.domain.model.Blogs
import com.aditya.porfiliohandler.domain.model.Dashboard
import com.aditya.porfiliohandler.domain.model.Experience
import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.model.Projects
import com.aditya.porfiliohandler.domain.model.SkillCategory
import com.aditya.porfiliohandler.domain.model.SocialLink
import com.aditya.porfiliohandler.domain.model.Stat
import com.aditya.porfiliohandler.domain.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class UserRepositoryImpl(
    private val dataSource: UserDataSource
) : UserRepository {

    override suspend fun getDashboard(): Dashboard = coroutineScope {
        val about = async { dataSource.getAbout() }
        val projects = async { dataSource.getProjects() }
        val messages = async { dataSource.getMessages() }
        val experience = async { dataSource.getExperiences() }
        val blogs = async { dataSource.getBlogs() }

        Dashboard(
            about = about.await(),
            projects = projects.await(),
            messages = messages.await(),
            experience = experience.await(),
            blogs = blogs.await()
        )
    }

    override suspend fun deleteMessage(messages: Messages): Result<Unit> {
        return dataSource.deleteMessage(messages)
    }

    override suspend fun readMessage(messages: Messages): Result<Unit> {
        return dataSource.readMessage(messages)
    }

    override suspend fun updateStat(stat: Stat): Result<Unit> {
        return dataSource.updateStat(stat)
    }

    override suspend fun updateSocialLink(oldSocialLink: SocialLink, newSocialLink: SocialLink): Result<Unit> {
        return dataSource.updateSocialLink(oldSocialLink, newSocialLink)
    }

    override suspend fun addSocialLink(socialLink: SocialLink): Result<Unit> {
        return dataSource.addSocialLink(socialLink)
    }

    override suspend fun updateSkillCategory(oldSkillCategory: SkillCategory, newSkillCategory: SkillCategory): Result<Unit> {
        return dataSource.updateSkillCategory(oldSkillCategory, newSkillCategory)
    }

    override suspend fun addSkillCategory(skillCategory: SkillCategory): Result<Unit> {
        return dataSource.addSkillCategory(skillCategory)
    }

    override suspend fun updateProject(project: Projects): Result<Unit> {
        return dataSource.updateProject(project)
    }

    override suspend fun addProject(project: Projects): Result<Unit> {
        return dataSource.addProject(project)
    }

    override suspend fun updateExperience(experience: Experience): Result<Unit> {
        return dataSource.updateExperience(experience)
    }

    override suspend fun addExperience(experience: Experience): Result<Unit> {
        return dataSource.addExperience(experience)
    }
}
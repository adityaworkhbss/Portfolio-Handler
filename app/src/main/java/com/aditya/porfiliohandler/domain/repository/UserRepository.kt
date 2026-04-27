package com.aditya.porfiliohandler.domain.repository

import com.aditya.porfiliohandler.domain.model.About
import com.aditya.porfiliohandler.domain.model.Blogs
import com.aditya.porfiliohandler.domain.model.Dashboard
import com.aditya.porfiliohandler.domain.model.Experience
import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.model.Projects
import com.aditya.porfiliohandler.domain.model.SkillCategory
import com.aditya.porfiliohandler.domain.model.SocialLink
import com.aditya.porfiliohandler.domain.model.Stat

interface UserRepository {
    suspend fun getDashboard() : Dashboard
    suspend fun deleteMessage(messages: Messages) : Result<Unit>
    suspend fun readMessage(messages: Messages) : Result<Unit>

    suspend fun updateStat(stat: Stat) : Result<Unit>

    suspend fun updateSocialLink(oldSocialLink: SocialLink, newSocialLink: SocialLink) : Result<Unit>
    suspend fun addSocialLink(socialLink: SocialLink) : Result<Unit>

    suspend fun updateSkillCategory(oldSkillCategory: SkillCategory, newSkillCategory: SkillCategory) : Result<Unit>
    suspend fun addSkillCategory(skillCategory: SkillCategory) : Result<Unit>

    suspend fun updateProject(projects: Projects) : Result<Unit>
    suspend fun addProject(projects: Projects) : Result<Unit>

    suspend fun updateExperience(experience: Experience) : Result<Unit>
    suspend fun addExperience(experience: Experience) : Result<Unit>
}
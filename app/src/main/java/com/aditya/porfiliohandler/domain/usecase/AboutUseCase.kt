package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.model.SkillCategory
import com.aditya.porfiliohandler.domain.model.SocialLink
import com.aditya.porfiliohandler.domain.model.Stat
import com.aditya.porfiliohandler.domain.repository.UserRepository

class AboutUseCase(
    private val repository: UserRepository
) {
    suspend fun updateStats(stat: Stat) = repository.updateStat(stat)

    suspend fun updateSocialLink(oldSocialLink: SocialLink, newSocialLink: SocialLink) =
        repository.updateSocialLink(oldSocialLink, newSocialLink)

    suspend fun addSocialLink(socialLink: SocialLink) =
        repository.addSocialLink(socialLink)

    suspend fun updateSkillCategory(oldSkillCategory: SkillCategory, newSkillCategory: SkillCategory) =
        repository.updateSkillCategory(oldSkillCategory, newSkillCategory)

    suspend fun addSkillCategory(skillCategory: SkillCategory) =
        repository.addSkillCategory(skillCategory)

    suspend fun deleteStat(stat: Stat) =
        repository.deleteStat(stat)

    suspend fun deleteSocialLink(socialLink: SocialLink) =
        repository.deleteSocialLink(socialLink)

    suspend fun deleteSkillCategory(skillCategory: SkillCategory) =
        repository.deleteSkillCategory(skillCategory)
}


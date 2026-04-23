package com.aditya.porfiliohandler.domain.model

data class About(
    val availabilityStatus: String = "",
    val availabilityText: String = "",
    val avatarUrl: String = "",
    val bio: String = "",
    val email: String = "",
    val location: String = "",
    val name: String = "",
    val resumeUrl: String = "",
    val role: String = "",
    val tagline: String = "",
    val skills: List<SkillCategory> = emptyList(),
    val socialLinks: List<SocialLink> = emptyList(),
    val stats: List<Stat> = emptyList()
)

data class SkillCategory(
    val category: String = "",
    val items: List<String> = emptyList()
)

data class SocialLink(
    val platform: String = "",
    val url: String = ""
)

data class Stat(
    val label: String = "",
    val value: String = ""
)

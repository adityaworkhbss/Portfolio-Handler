package com.aditya.porfiliohandler.data.repository

import com.aditya.porfiliohandler.data.datasource.UserDataSource
import com.aditya.porfiliohandler.domain.model.About
import com.aditya.porfiliohandler.domain.model.Blogs
import com.aditya.porfiliohandler.domain.model.Dashboard
import com.aditya.porfiliohandler.domain.model.Experience
import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.model.Projects
import com.aditya.porfiliohandler.domain.model.User
import com.aditya.porfiliohandler.domain.repository.UserRepository
import kotlin.math.exp

class UserRepositoryImpl(
    private val dataSource: UserDataSource
) : UserRepository {

    override suspend fun getDashboard(): Dashboard {
        val about = dataSource.getAbout()
        val projects = dataSource.getProjects()
        val messages = dataSource.getMessages()
        val experience = dataSource.getExperiences()
        val blogs = dataSource.getBlogs()

        return Dashboard(
            about = about,
            projects = projects,
            messages = messages,
            experience = experience,
            blogs = blogs
        )
    }
}
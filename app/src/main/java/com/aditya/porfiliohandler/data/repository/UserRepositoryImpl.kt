package com.aditya.porfiliohandler.data.repository

import com.aditya.porfiliohandler.data.datasource.UserDataSource
import com.aditya.porfiliohandler.domain.model.Dashboard
import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.repository.UserRepository

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

    override suspend fun deleteMessage(messages: Messages): Result<Unit> {
        return dataSource.deleteMessage(messages)
    }
}
package com.aditya.porfiliohandler.data.repository

import com.aditya.porfiliohandler.data.datasource.UserDataSource
import com.aditya.porfiliohandler.domain.model.Blogs
import com.aditya.porfiliohandler.domain.model.Dashboard
import com.aditya.porfiliohandler.domain.model.Experience
import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.model.Projects
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
}
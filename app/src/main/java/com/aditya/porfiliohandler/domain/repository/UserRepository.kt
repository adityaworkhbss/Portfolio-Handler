package com.aditya.porfiliohandler.domain.repository

import com.aditya.porfiliohandler.domain.model.About
import com.aditya.porfiliohandler.domain.model.Blogs
import com.aditya.porfiliohandler.domain.model.Dashboard
import com.aditya.porfiliohandler.domain.model.Experience
import com.aditya.porfiliohandler.domain.model.Messages
import com.aditya.porfiliohandler.domain.model.Projects
import com.aditya.porfiliohandler.domain.model.Stat

interface UserRepository {
    suspend fun getDashboard() : Dashboard
    suspend fun deleteMessage(messages: Messages) : Result<Unit>
    suspend fun readMessage(messages: Messages) : Result<Unit>

    suspend fun updateStat(stat: Stat) : Result<Unit>
}
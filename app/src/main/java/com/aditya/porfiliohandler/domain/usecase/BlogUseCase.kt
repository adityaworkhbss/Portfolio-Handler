package com.aditya.porfiliohandler.domain.usecase

import com.aditya.porfiliohandler.domain.model.Blogs
import com.aditya.porfiliohandler.domain.repository.UserRepository

class BlogUseCase(
    private val repository: UserRepository
){
    suspend fun deleteBlog(blogs: Blogs) = repository.deleteBlog(blogs)
    suspend fun publishBlog(blogs: Blogs, isPublished : Boolean) = repository.publishBlog(blogs, isPublished)
}
package com.aditya.porfiliohandler.data.datasource

import com.aditya.porfiliohandler.domain.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserDataSource(
    private val db: FirebaseFirestore
) {

    suspend fun getAbout(): About? {
        return try {
            db.collection("about")
                .get()
                .await()
                .toObjects(About::class.java)
                .firstOrNull()
        } catch (e: Exception) {
            null
        }
    }


    suspend fun getProjects(): List<Projects> {
        return try {
            db.collection("projects")
                .get()
                .await()
                .toObjects(Projects::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getMessages(): List<Messages> {
        return try {
            db.collection("messages")
                .get()
                .await()
                .toObjects(Messages::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getBlogs(): List<Blogs> {
        return try {
            db.collection("blogs")
                .get()
                .await()
                .toObjects(Blogs::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getExperiences(): List<Experience> {
        return try {
            db.collection("experiences")
                .get()
                .await()
                .toObjects(Experience::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}

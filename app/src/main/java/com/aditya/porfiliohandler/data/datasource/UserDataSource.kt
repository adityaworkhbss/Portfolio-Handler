package com.aditya.porfiliohandler.data.datasource

import android.util.Log
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
            val snapshot = db.collection("projects")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Projects::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getMessages(): List<Messages> {
        return try {
            val snapshot = db.collection("messages")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Messages::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getBlogs(): List<Blogs> {
        return try {
            val snapshot = db.collection("blogs")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Blogs::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getExperiences(): List<Experience> {
        return try {
            val snapshot = db.collection("experiences")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Experience::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun deleteMessage(messages: Messages) : Result<Unit> {
        return try {
             db.collection("messages")
                .document(messages.id)
                .delete()
                .await()
             Result.success(Unit)
        } catch (e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun readMessage(messages: Messages) : Result<Unit> {
        return try {
            db.collection("messages")
                .document(messages.id)
                .update("read", true)
                .await()
            Result.success(Unit)
        } catch (e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun updateStat(stat: Stat): Result<Unit> {
        return try {
            val snapshot = db.collection("about").get().await()
            val doc = snapshot.documents.firstOrNull()
            if (doc != null) {
                val about = doc.toObject(About::class.java)
                val updatedStats = about?.stats?.map {
                    if (it.label == stat.label) stat else it
                }
                db.collection("about").document(doc.id).update("stats", updatedStats).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("About document not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

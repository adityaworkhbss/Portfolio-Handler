package com.aditya.porfiliohandler.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthDataSource(
    private val auth : FirebaseAuth
) {

    suspend fun login(email: String, password : String) : FirebaseUser =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { res ->
                    cont.resume(res.user!!)
                }
                .addOnFailureListener { e ->
                    cont.resumeWithException(e)
                }
        }
}

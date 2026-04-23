package com.aditya.porfiliohandler.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    companion object {
        private const val PREF_NAME = "user_session"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUserSession(id: String, email: String) {
        val editor = prefs.edit()
        editor.putString(KEY_USER_ID, id)
        editor.putString(KEY_USER_EMAIL, email)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserId(): String? = prefs.getString(KEY_USER_ID, null)

    fun getUserEmail(): String? = prefs.getString(KEY_USER_EMAIL, null)

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}

package com.example.discovermovie.shared

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPreferencesStore @Inject constructor(@ApplicationContext context: Context) {


    val sharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)

   fun saveRequestToken(requestToken: String) {
        val sharedEditor = sharedPreferences.edit()
        sharedEditor.putString(TOKEN_KEY, requestToken)
        sharedEditor.commit()
    }

    fun getRequestToken() = sharedPreferences.getString(TOKEN_KEY, null)


    fun saveSessionId(sessionId: String) {
        val sharedEditor = sharedPreferences.edit()
        sharedEditor.putString(SESSION_ID, sessionId)
        sharedEditor.commit()
    }

    fun getSessionId() = sharedPreferences.getString(SESSION_ID, null)

    companion object {
        const val SHARED_PREF_KEY = "SharedKey"
        const val TOKEN_KEY = "RequestToken"
        const val SESSION_ID = "SessionId"

    }


}
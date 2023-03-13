package com.example.discovermovie

import android.content.SharedPreferences
import com.example.discovermovie.shared.SharedPreferencesStore.Companion.TOKEN_KEY
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ErrorsHandlingInterceptor (private val sharedPreferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        if (requiresAuth(request)) {
            val requestWithToken = request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            return chain.proceed(requestWithToken)
        }

        return chain.proceed(request)
    }

    fun requiresAuth(request: Request): Boolean {
        val url = request.url.toString()
        return url.contains("3/authentication/session/new")
    }


}
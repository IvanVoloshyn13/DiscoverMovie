package com.example.discovermovie.repository

import com.example.discovermovie.api.AuthenticationServices
import com.example.discovermovie.data.authentication.*
import com.example.discovermovie.shared.SharedPreferencesStore
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authService: AuthenticationServices,
    private val sharedPreferencesStore: SharedPreferencesStore
) {
    suspend fun createRequestToken(): Response<TokenResponse> {
        return authService.createToken()
    }

    suspend fun createSessionWithLogin(body: AuthenticationRequest): Response<TokenResponse> {
        return authService.authenticateAccount(body)
    }

    suspend fun createSessionId(requestToken: RequestToken): Response<SessionIdResponse> {
        return authService.createSessionId(requestToken)
    }

    suspend fun getAccDetails(sessionId: String): Response<UserResponse> {
        return authService.getAccountDetail(sessionId)
    }

    fun saveRequestToken(requestToken: String) {
        sharedPreferencesStore.saveRequestToken(requestToken)
    }

    fun getRequestToken() = sharedPreferencesStore.getRequestToken()


}
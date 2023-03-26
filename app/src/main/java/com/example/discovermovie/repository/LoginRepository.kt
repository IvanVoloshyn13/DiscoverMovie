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
    suspend fun createRequestToken() = authService.createToken()


    suspend fun authenticateAccount(body: AuthenticationRequest): Response<TokenResponse> {
        return authService.authenticateAccount(body)
    }

    suspend fun createSessionId(requestToken: BodyTokenRequest): Response<SessionIdResponse> {
        return authService.createSessionId(request_token = requestToken)
    }

    suspend fun createSessionIdV4(accessToken: String): Response<SessionIdResponse> {
        return authService.createSessionIdV4(accessToken)
    }

    suspend fun getAccDetails(sessionId: String): Response<UserResponse> {
        return authService.getAccountDetail(sessionId)
    }

    fun saveRequestToken(requestToken: String) {
        sharedPreferencesStore.saveRequestToken(requestToken)
    }

    fun getRequestToken() = sharedPreferencesStore.getRequestToken()

    fun saveSessionId(sessionId: String) {
        sharedPreferencesStore.saveSessionId(sessionId)
    }

    fun getSessionId() = sharedPreferencesStore.getSessionId()


}
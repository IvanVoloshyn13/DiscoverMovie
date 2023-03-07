package com.example.discovermovie.repository

import com.example.discovermovie.api.AuthenticationServices
import com.example.discovermovie.data.authentication.*
import com.example.discovermovie.data.movieModels.authentication.*
import com.example.discovermovie.util.API_KEY
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(val authService:AuthenticationServices) {
    suspend fun createRequestToken(): Response<TokenResponse> {
        return authService.createToken(API_KEY)
    }

    suspend fun createSessionWithLogin(body: AuthenticationRequest): Response<TokenResponse> {
        return authService.authenticateAccount(API_KEY, body)
    }

    suspend fun createSessionId(requestToken: RequestToken): Response<SessionIdResponse> {
        return authService.createSessionId(API_KEY, requestToken)
    }

    suspend fun getAccDetails(sessionId: String): Response<UserResponse> {
        return authService.getAccountDetail(API_KEY, sessionId)
    }
}
package com.example.discovermovie.data.repository

import com.example.discovermovie.authentication.*
import com.example.discovermovie.util.API_KEY
import retrofit2.Response

class LoginRepository(val movieRemoteRepository: MovieRemoteRepository) {
    suspend fun createRequestToken(): Response<TokenResponse> {
        return movieRemoteRepository.authenticationService().createToken(API_KEY)
    }

    suspend fun createSessionWithLogin(body: AuthenticationRequest): Response<TokenResponse> {
        return movieRemoteRepository.authenticationService().authenticateAccount(API_KEY, body)
    }

    suspend fun createSessionId(requestToken: RequestToken): Response<SessionIdResponse> {
        return movieRemoteRepository.authenticationService().createSessionId(API_KEY, requestToken)
    }

    suspend fun getAccDetails(sessionId: String): Response<UserResponse> {
        return movieRemoteRepository.authenticationService().getAccountDetail(API_KEY, sessionId)
    }
}
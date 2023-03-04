package com.example.discovermovie.api

import com.example.discovermovie.authentication.*
import com.example.discovermovie.util.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticationServices {
    @GET("3/authentication/token/new")
    suspend fun createToken(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<TokenResponse>

    @POST("3/authentication/token/validate_with_login")
    suspend fun authenticateAccount(
        @Query("api_key") apiKey: String,
        @Body request: AuthenticationRequest
    ): Response<TokenResponse>

    @POST("3/authentication/session/new")
    suspend fun createSessionId(
        @Query("api_key") apiKey: String,
        @Body request_token: RequestToken
    ): Response<SessionIdResponse>

    @GET("3/account")
    suspend fun getAccountDetail(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String
    ): Response<UserResponse>
}
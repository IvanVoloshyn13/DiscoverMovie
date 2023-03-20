package com.example.discovermovie.api

import com.example.discovermovie.data.authentication.AuthenticationRequest
import com.example.discovermovie.data.authentication.SessionIdResponse
import com.example.discovermovie.data.authentication.TokenResponse
import com.example.discovermovie.data.authentication.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticationServices {
    @GET("3/authentication/token/new")
    suspend fun createToken(
    ): Response<TokenResponse>


    @POST("3/authentication/token/validate_with_login")
    suspend fun authenticateAccount(
        @Body request: AuthenticationRequest
    ): Response<TokenResponse>


    @POST("3/authentication/session/new")
    suspend fun createSessionId(
        @Body request_token: String
    ): Response<SessionIdResponse>

    @POST("3/authentication/session/convert/4")
    suspend fun createSessionIdV4(
        @Body access_token: String
    ): Response<SessionIdResponse>

    @GET("3/account")
    suspend fun getAccountDetail(
        @Query("session_id") sessionId: String
    ): Response<UserResponse>
}
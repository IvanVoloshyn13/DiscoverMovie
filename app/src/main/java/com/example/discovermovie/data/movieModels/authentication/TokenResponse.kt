package com.example.discovermovie.data.movieModels.authentication

data class TokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)
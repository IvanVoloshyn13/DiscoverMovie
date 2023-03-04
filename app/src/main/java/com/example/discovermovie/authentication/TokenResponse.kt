package com.example.discovermovie.authentication

data class TokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)
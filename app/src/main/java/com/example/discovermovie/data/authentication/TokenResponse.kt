package com.example.discovermovie.data.authentication

data class TokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean,
    val status_message: String?=null,
    val status_code: String?=null
)
package com.example.discovermovie.data.authentication

data class AuthenticationRequest(
    val username: String,
    val password: String,
    val request_token: String,
    val status_message: String,
    val status_code: Int
)
package com.example.discovermovie.data.authentication

data class AuthenticationRequest(
    val username: String,
    val password: String,
    val request_token: String
)
package com.example.discovermovie.authentication

import com.example.discovermovie.util.API_KEY

data class AuthenticationRequest(
    val username: String,
    val password: String,
    val request_token: String
)
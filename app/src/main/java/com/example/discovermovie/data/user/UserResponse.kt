package com.example.discovermovie.data.user

data class UserResponse(
    val id: Int,
    val include_adult: Boolean,
    val name: String,
    val username: String,
    val status_message: String,
    val status_code: String
)

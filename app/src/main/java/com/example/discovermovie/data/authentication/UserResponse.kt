package com.example.discovermovie.data.authentication

data class UserResponse(
    val id: Int,
    val include_adult: Boolean,
    val name: String,
    val username: String
)

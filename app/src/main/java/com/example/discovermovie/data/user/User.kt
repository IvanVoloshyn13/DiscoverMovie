package com.example.discovermovie.data.user

data class User(
    val id: Int,
    val include_adult: Boolean,
    val name: String,
    val username: String
)

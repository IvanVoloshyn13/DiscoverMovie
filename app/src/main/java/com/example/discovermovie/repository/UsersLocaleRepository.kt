package com.example.discovermovie.repository

import com.example.discovermovie.data.user.UserEntity
import com.example.discovermovie.database.MovieDatabase
import javax.inject.Inject

class UsersLocaleRepository @Inject constructor(
    val db: MovieDatabase
) {
    suspend fun insertUser(user: UserEntity) = db.getUserDao().insertUser(user)
    suspend fun getUserById(userId: Int): UserEntity = db.getUserDao().getUserById(userId)
}
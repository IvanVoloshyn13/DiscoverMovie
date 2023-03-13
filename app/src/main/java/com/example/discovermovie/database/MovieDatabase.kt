package com.example.discovermovie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.discovermovie.data.localeDataBase.MovieEntity
import com.example.discovermovie.data.localeDataBase.UserEntity

@Database(entities = [MovieEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
    abstract fun getUserDao(): UserDao

}
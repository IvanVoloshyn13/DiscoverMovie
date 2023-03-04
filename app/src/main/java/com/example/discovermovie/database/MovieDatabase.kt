package com.example.discovermovie.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.discovermovie.data.movieModels.DatabaseMovieModel

@Database(entities = [DatabaseMovieModel::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao

}
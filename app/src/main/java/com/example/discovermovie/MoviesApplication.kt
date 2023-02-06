package com.example.discovermovie

import android.app.Application
import android.util.Log
import com.example.discovermovie.database.MovieDatabase

class MoviesApplication : Application() {
    companion object {
        lateinit var movieDatabase: MovieDatabase
    }

    override fun onCreate() {
        super.onCreate()
        movieDatabase = let { MovieDatabase.getInstance(this.applicationContext)!! }

    }
}
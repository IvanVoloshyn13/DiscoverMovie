package com.example.discovermovie

import android.app.Application
import android.util.Log
import com.example.discovermovie.database.MovieDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
@Singleton
class MoviesApplication : Application() {

}
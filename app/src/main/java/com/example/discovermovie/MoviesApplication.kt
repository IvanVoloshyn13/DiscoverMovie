package com.example.discovermovie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
@Singleton
class MoviesApplication : Application() {

}
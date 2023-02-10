package com.example.discovermovie.data.repository

import com.example.discovermovie.api.MovieServices
import com.example.discovermovie.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepository() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun movieService(): MovieServices = retrofit.create(MovieServices::class.java)


}
package com.example.discovermovie.api

import com.example.discovermovie.database.MovieDatabase
import com.example.discovermovie.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIRepository() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val api = retrofit.create(ApiCall::class.java)


}
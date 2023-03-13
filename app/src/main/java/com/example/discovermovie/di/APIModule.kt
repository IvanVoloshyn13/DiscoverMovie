package com.example.discovermovie.di

import android.content.SharedPreferences
import com.example.discovermovie.ErrorsHandlingInterceptor
import com.example.discovermovie.api.MovieServices
import com.example.discovermovie.util.API_KEY
import com.example.discovermovie.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {


    @Provides
    @Singleton
    fun provideApiInterceptor() = Interceptor() { chain ->
        val originalRequest = chain.request()
        val newHttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        chain.proceed(newRequest)

    }



    @Provides
    @Singleton
    fun provideHttpClient(
        apiInterceptor: Interceptor,
      //  tokenInterceptor: ErrorsHandlingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
           // .addInterceptor(tokenInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    @Provides
    @Singleton
    fun provideMovieServices(retrofit: Retrofit): MovieServices =
        retrofit.create(MovieServices::class.java)



}
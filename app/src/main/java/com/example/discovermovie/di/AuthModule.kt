package com.example.discovermovie.di

import com.example.discovermovie.api.AuthenticationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun authenticationService(retrofit: Retrofit): AuthenticationServices =
        retrofit.create(AuthenticationServices::class.java)
}
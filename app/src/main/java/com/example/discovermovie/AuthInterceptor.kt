package com.example.discovermovie

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $API_ACCESS_TOKEN")
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val API_ACCESS_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMmMyNDI1ODkyMGJhMDYwOTZlZDIyM2EyNTkzMTU4YiIsInN1YiI6IjYzNjdhZGQyNjUxZmNmMDA3YjBkZjA4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1de3bptAPDtptMPQokIYO5IfGiBne6oCzePytKMBuFQ"
    }
}
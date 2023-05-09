package com.example.discovermovie.util

import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(api: suspend () -> Response<T>): Resource<T> {
        try {
            val response = api()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return Resource.Success(body)
                } ?: return errorMessage("Body is empty")
            } else {
                return errorMessage(
                    "${response.code()} ${response.body().toString()} "
                )
            }
        } catch (e: Exception) {
            return errorMessage(e.message.toString())
        }
    }

    private fun <T> errorMessage(e: String): Resource.Error<T> {
        return Resource.Error(data = null, message = "Api call failed : $e")
    }
}


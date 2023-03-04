package com.example.discovermovie.api

import androidx.core.view.accessibility.AccessibilityEventCompat.ContentChangeType
import com.example.discovermovie.data.movieModels.details.MovieDetailsModel
import com.example.discovermovie.data.movieModels.images.ImagesResponse
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.data.movieModels.videoModel.VideoResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices {

    @GET("3/discover/movie")
    suspend fun discoverMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Response<MovieModelResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieModelResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieModelResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<MovieDetailsModel>


    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieModelResponse>

    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<VideoResponse>

    @GET("3/movie/{movie_id}/images")
    suspend fun getImages(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<ImagesResponse>

    @Headers("Content-Type:application/json;charset=utf-8")
    @POST("3/movie/{movie_id}/rating")
    suspend fun setRating(
        @Path("movie_id") movieId: Int,
        @Body value: Double,
        @Query("api_key") apiKey: String
    ): Response<ResponseBody>


}
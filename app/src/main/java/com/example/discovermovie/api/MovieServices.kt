package com.example.discovermovie.api

import com.example.discovermovie.data.movieModels.details.MovieDetailsModel
import com.example.discovermovie.data.movieModels.images.ImagesResponse
import com.example.discovermovie.data.movieModels.search.SearchResponse
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.data.movieModels.videoModel.VideoResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MovieServices {

    @GET("3/discover/movie")
    suspend fun discoverMovie(
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
    ): Response<MovieModelResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieModelResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieModelResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): Response<MovieDetailsModel>


    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieModelResponse>

    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): Response<VideoResponse>

    @GET("3/movie/{movie_id}/images")
    suspend fun getImages(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): Response<ImagesResponse>

    @Headers("Content-Type:application/json;charset=utf-8")
    @POST("3/movie/{movie_id}/rating")
    suspend fun setRating(
        @Path("movie_id") movieId: Int,
        @Body value: Double,
    ): Response<ResponseBody>

    @GET("3/search/multi")
    suspend fun multiSearch(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<SearchResponse>

}
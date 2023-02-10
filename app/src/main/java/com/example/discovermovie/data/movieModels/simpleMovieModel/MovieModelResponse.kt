package com.example.discovermovie.data.movieModels.simpleMovieModel

import com.google.gson.annotations.SerializedName

data class MovieModelResponse(
    val dates: Dates,
    val page: Int,
    @SerializedName("results")
    val moviesList: MutableList<MovieItemModel>,
    val total_pages: Int,
    val total_results: Int
)
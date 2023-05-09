package com.example.discovermovie.data.movieModels.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    val searchList: MutableList<SearchResponseItem>
)
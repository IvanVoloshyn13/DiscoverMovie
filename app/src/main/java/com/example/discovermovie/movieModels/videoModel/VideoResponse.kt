package com.example.discovermovie.movieModels.videoModel

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val id: Int,
    @SerializedName("results")
    val videoList: List<MovieVideoModel>
)
package com.example.discovermovie.data.movieModels.images

data class ImagesResponse(
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<Poster>
)
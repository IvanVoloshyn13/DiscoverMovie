package com.example.discovermovie.repository

import com.example.discovermovie.api.MovieServices
import com.example.discovermovie.data.movieModels.search.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(private val movieServices: MovieServices) {

    suspend fun multiSearch(query: String, page: Int): Response<SearchResponse> =
        movieServices.multiSearch(query, page)
}
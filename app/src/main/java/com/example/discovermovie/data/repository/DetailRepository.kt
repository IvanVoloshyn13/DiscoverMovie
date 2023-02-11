package com.example.discovermovie.data.repository

import android.util.Log
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import com.example.discovermovie.data.movieModels.details.MovieDetailsModel
import com.example.discovermovie.data.movieModels.images.ImagesResponse
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.util.API_KEY
import retrofit2.Response
import java.util.Locale

class DetailRepository(
    private val localeRepository: LocaleRepository,
    val remoteRepository: RemoteRepository
) {
    private val language = Locale.getDefault().language
    private val languageEn = "en"


    suspend fun addMovieToLocaleRepository(movie: DatabaseMovieModel) {
        localeRepository.addMovie(movie)
    }

    suspend fun deleteMovieFromLocaleRepository(movieId: Int) {
        localeRepository.deleteMovieById(movieId)
    }

    fun getFavouriteMovies() = localeRepository.getAllMovies()

    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsModel> {
        return remoteRepository.movieService()
            .getMovieDetails(movieId, API_KEY, language)
    }

    suspend fun getSimilarMovies(movieId: Int): Response<MovieModelResponse> {
        return remoteRepository.movieService()
            .getSimilarMovies(movieId, API_KEY, language, 1)
    }

    suspend fun getImages(movieId: Int): Response<ImagesResponse> {
        return remoteRepository.movieService().getImages(movieId, API_KEY, languageEn)
    }

}
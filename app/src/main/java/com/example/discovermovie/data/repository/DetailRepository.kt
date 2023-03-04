package com.example.discovermovie.data.repository

import android.media.Rating
import com.example.discovermovie.api.MovieServices
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import com.example.discovermovie.data.movieModels.details.MovieDetailsModel
import com.example.discovermovie.data.movieModels.images.ImagesResponse
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.util.API_KEY
import retrofit2.Response
import java.util.Locale
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val localeRepository: LocaleRepository,
    private val movieService: MovieServices
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
        return movieService.getMovieDetails(movieId, API_KEY, language)
    }

    suspend fun getSimilarMovies(movieId: Int): Response<MovieModelResponse> {
        return movieService
            .getSimilarMovies(movieId, API_KEY, language, 1)
    }

    suspend fun getImages(movieId: Int): Response<ImagesResponse> {
        return movieService.getImages(movieId, API_KEY, languageEn)
    }

    suspend fun getSingleMovieById(movieId: Int) = localeRepository.getSingleMovieById(movieId)

    suspend fun setRating(movieId: Int, rating: Double) {
        movieService.setRating(movieId, rating, API_KEY)
    }

}
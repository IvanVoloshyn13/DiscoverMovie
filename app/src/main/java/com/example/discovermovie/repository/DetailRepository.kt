package com.example.discovermovie.repository

import com.example.discovermovie.api.MovieServices
import com.example.discovermovie.data.localeDataBase.MovieEntity
import com.example.discovermovie.data.movieModels.details.MovieDetailsModel
import com.example.discovermovie.data.movieModels.images.ImagesResponse
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val favouriteMoviesLocaleRepository: FavouriteMoviesLocaleRepository,
    private val movieService: MovieServices,
) {
    private val language = Locale.getDefault().language
    private val languageEn = "en"


    suspend fun addMovieToLocaleRepository(movie: MovieEntity) {
        favouriteMoviesLocaleRepository.addMovie(movie)
    }

    suspend fun deleteMovieFromLocaleRepository(movieId: Int) {
        favouriteMoviesLocaleRepository.deleteMovieById(movieId)
    }

    fun getFavouriteMovies() = favouriteMoviesLocaleRepository.getAllMovies()

    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsModel> {
        return movieService.getMovieDetails(movieId, language)
    }

    suspend fun getSimilarMovies(movieId: Int): Response<MovieModelResponse> {
        return movieService
            .getSimilarMovies(movieId, language, 1)
    }

    suspend fun getImages(movieId: Int): Response<ImagesResponse> {
        return movieService.getImages(movieId, languageEn)
    }

    suspend fun getSingleMovieById(movieId: Int) =
        favouriteMoviesLocaleRepository.getSingleMovieById(movieId)

    suspend fun setRating(movieId: Int, rating: Double) {
        movieService.setRating(movieId, rating)
    }


}
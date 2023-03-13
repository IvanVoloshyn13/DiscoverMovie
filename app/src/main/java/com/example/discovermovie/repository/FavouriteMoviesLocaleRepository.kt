package com.example.discovermovie.repository

import androidx.lifecycle.LiveData
import com.example.discovermovie.database.MovieDatabase
import com.example.discovermovie.data.localeDataBase.MovieEntity
import javax.inject.Inject

class FavouriteMoviesLocaleRepository @Inject constructor(
    private val db: MovieDatabase
) {

    suspend fun addMovie(movie: MovieEntity) = db.getMovieDao().addMovie(movie)
    suspend fun deleteMovie(movie: MovieEntity) = db.getMovieDao().deleteMovie(movie)
    suspend fun deleteMovieById(movieId: Int) = db.getMovieDao().deleteMovieById(movieId)
    suspend fun getSingleMovieById(movieId: Int): MovieEntity =
        db.getMovieDao().getSingleMovie(movieId)

    fun getAllMovies(): LiveData<List<MovieEntity>> = db.getMovieDao().getAllMovies()
}
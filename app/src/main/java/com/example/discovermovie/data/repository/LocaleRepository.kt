package com.example.discovermovie.data.repository

import androidx.lifecycle.LiveData
import com.example.discovermovie.database.MovieDatabase
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import javax.inject.Inject

class LocaleRepository @Inject constructor(
    private val db: MovieDatabase
) {

    suspend fun addMovie(movie: DatabaseMovieModel) = db.getMovieDao().addMovie(movie)
    suspend fun deleteMovie(movie: DatabaseMovieModel) = db.getMovieDao().deleteMovie(movie)
    suspend fun deleteMovieById(movieId: Int) = db.getMovieDao().deleteMovieById(movieId)
    suspend fun getSingleMovieById(movieId: Int): DatabaseMovieModel =
        db.getMovieDao().getSingleMovie(movieId)

    fun getAllMovies(): LiveData<List<DatabaseMovieModel>> = db.getMovieDao().getAllMovies()
}
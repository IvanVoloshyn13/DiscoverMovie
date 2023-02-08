package com.example.discovermovie.repository

import androidx.lifecycle.LiveData
import com.example.discovermovie.database.MovieDatabase
import com.example.discovermovie.movieModels.DatabaseMovieModel

class DatabaseMovieRepository(
    private val db: MovieDatabase
) {

    suspend fun addMovie(movie: DatabaseMovieModel) = db.getMovieDao().addMovie(movie)
    suspend fun deleteMovie(movie: DatabaseMovieModel) = db.getMovieDao().deleteMovie(movie)
    suspend fun deleteMovieById(movieId: Int) = db.getMovieDao().deleteMovieById(movieId)

    fun getAllMovies(): LiveData<List<DatabaseMovieModel>> = db.getMovieDao().getAllMovies()
}
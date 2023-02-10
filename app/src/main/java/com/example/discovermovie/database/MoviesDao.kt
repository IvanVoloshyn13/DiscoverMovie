package com.example.discovermovie.database


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.discovermovie.data.movieModels.DatabaseMovieModel

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: DatabaseMovieModel)

    @Delete
    suspend fun deleteMovie(movie: DatabaseMovieModel)

    @Query("DELETE FROM movies WHERE movieId=:movieId ")
    suspend fun deleteMovieById(movieId: Int)

    @Query("SELECT * FROM movies ")
    fun getAllMovies(): LiveData<List<DatabaseMovieModel>>
}
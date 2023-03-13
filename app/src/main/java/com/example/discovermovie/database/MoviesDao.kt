package com.example.discovermovie.database


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.discovermovie.data.localeDataBase.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE movieId=:movieId ")
    suspend fun deleteMovieById(movieId: Int)

    @Query("SELECT * FROM movies ")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE movieId=:movieId")
    suspend fun getSingleMovie(movieId: Int): MovieEntity
}
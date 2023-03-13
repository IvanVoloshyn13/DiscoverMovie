package com.example.discovermovie.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.localeDataBase.MovieEntity
import com.example.discovermovie.repository.FavouriteMoviesLocaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val dbRepository: FavouriteMoviesLocaleRepository
) : ViewModel() {


    fun getFavouriteMovies() =
        dbRepository.getAllMovies()

    fun addToFavourite(movie: MovieEntity) {
        viewModelScope.launch {
            dbRepository.addMovie(movie)
        }
    }

    fun deleteFromFavouriteByMovieID(movieId: Int) {
        viewModelScope.launch {
            dbRepository.deleteMovieById(movieId)
        }
    }


}
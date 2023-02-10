package com.example.discovermovie.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import com.example.discovermovie.data.repository.LocaleRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val dbRepository: LocaleRepository
) : ViewModel() {


    fun getFavouriteMovies() =
        dbRepository.getAllMovies()

    fun addToFavourite(movie: DatabaseMovieModel) {
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
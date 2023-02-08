package com.example.discovermovie.screens.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.movieModels.DatabaseMovieModel
import com.example.discovermovie.repository.DatabaseMovieRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val dbRepository: DatabaseMovieRepository
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
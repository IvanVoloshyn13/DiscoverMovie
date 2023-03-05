package com.example.discovermovie.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import com.example.discovermovie.repository.LocaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
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
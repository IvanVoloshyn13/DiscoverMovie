package com.example.discovermovie.screens.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.discovermovie.movieModels.DatabaseMovieModel
import com.example.discovermovie.repository.DatabaseMovieRepository

class FavouriteViewModel(
    private val dbRepository: DatabaseMovieRepository
) : ViewModel() {


    fun getFavouriteMovies() =
        dbRepository.getAllMovies()


}
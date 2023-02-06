package com.example.discovermovie.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.discovermovie.repository.DatabaseMovieRepository

class MovieDetailsViewModelProviderFactory(
    private val dbMovieRepository: DatabaseMovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        //dbMovieRepository
        return DetailViewModel() as T
    }
}
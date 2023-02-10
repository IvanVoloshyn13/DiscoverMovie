package com.example.discovermovie.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.discovermovie.data.repository.LocaleRepository

class FavouriteViewModelProviderFactory(private val dbRepository: LocaleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteViewModel(dbRepository) as T
    }
}
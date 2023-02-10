package com.example.discovermovie.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.discovermovie.data.repository.DetailRepository
import com.example.discovermovie.data.repository.LocaleRepository

class MovieDetailsViewModelProviderFactory(
    private val detailRepository: DetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        return DetailViewModel(detailRepository) as T
    }
}
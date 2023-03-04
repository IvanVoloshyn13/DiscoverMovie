package com.example.discovermovie.screens.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.discovermovie.data.repository.LoginRepository

class LoginViewModelProviderFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        return LoginViewModel(loginRepository) as T
    }
}

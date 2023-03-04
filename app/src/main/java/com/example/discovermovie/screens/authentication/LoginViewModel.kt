package com.example.discovermovie.screens.authentication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.authentication.*
import com.example.discovermovie.data.repository.LoginRepository
import com.example.discovermovie.util.API_KEY
import kotlinx.coroutines.launch

class LoginViewModel(val loginRepository: LoginRepository) : ViewModel() {
    val tokenLiveData = MutableLiveData<TokenResponse>()
    val loginLiveData = MutableLiveData<TokenResponse>()
    val sessionIdLiveData = MutableLiveData<SessionIdResponse>()
    val accountDetailsLiveData = MutableLiveData<UserResponse>()

    fun createToken() {
        viewModelScope.launch {
            val response =
                loginRepository.createRequestToken()
            tokenLiveData.postValue(response.body())
        }
    }

    fun login(userName: String, password: String, token: String) {
        Log.d("Login", "$userName,$password,$token")
        viewModelScope.launch {
            val response =
                loginRepository.createSessionWithLogin(
                    AuthenticationRequest(
                        userName, password, token
                    )
                )
            Log.d("LOGIN", response.isSuccessful.toString())
            loginLiveData.postValue(response.body())
        }

    }

    fun createSessionId(requestToken: RequestToken) {
        viewModelScope.launch {
            val response = loginRepository.createSessionId(requestToken)
            sessionIdLiveData.postValue(response.body())
        }
    }

    fun getAccDetails(sessionId: String) {
        viewModelScope.launch {
            val response = loginRepository.getAccDetails(sessionId)
            accountDetailsLiveData.postValue(response.body())
        }
    }


}
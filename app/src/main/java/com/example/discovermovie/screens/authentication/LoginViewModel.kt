package com.example.discovermovie.screens.authentication

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.authentication.AuthenticationRequest
import com.example.discovermovie.data.authentication.RequestToken
import com.example.discovermovie.data.authentication.UserResponse
import com.example.discovermovie.data.localeDataBase.UserEntity
import com.example.discovermovie.repository.LoginRepository
import com.example.discovermovie.repository.UsersLocaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val usersLocaleRepository: UsersLocaleRepository,
) :
    ViewModel() {

    val userLiveData = MutableLiveData<UserResponse>()
    var requestToken: String? = null
    private lateinit var sessionItRequestToken: RequestToken


    fun userAuth(login: String, password: String) {
        viewModelScope.launch {
            getAccDetails(createSessionId(login(login, password, createToken())))
        }
    }


    private suspend fun createToken(): String {
        val response =
            loginRepository.createRequestToken()
        return if (response.isSuccessful) {
            requestToken = response.body()?.request_token
            requestToken!!
        } else response.message()
    }

    private suspend fun login(
        userName: String,
        password: String,
        token: String,
    ): RequestToken {
        val response =
            loginRepository.createSessionWithLogin(
                AuthenticationRequest(
                    userName, password, token
                )
            )
        return if (response.isSuccessful) {
            sessionItRequestToken = RequestToken(requestToken!!)
            loginRepository.saveRequestToken(sessionItRequestToken.request_token)
            sessionItRequestToken
        } else null as RequestToken

    }

    suspend fun createSessionId(requestToken: RequestToken): String {
        val response = loginRepository.createSessionId(requestToken)
        return if (response.isSuccessful) {
            response.body()?.session_id!!
        } else response.message()

    }

    private suspend fun getAccDetails(sessionId: String) {
        val response = loginRepository.getAccDetails(sessionId)
        userLiveData.postValue(response.body())
    }

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            usersLocaleRepository.insertUser(user)
        }

    }


    fun getRequestTokenFromShared(): String? = loginRepository.getRequestToken()


}

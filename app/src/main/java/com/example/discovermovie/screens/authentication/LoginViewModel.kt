package com.example.discovermovie.screens.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.authentication.AuthenticationRequest
import com.example.discovermovie.data.authentication.BodyTokenRequest
import com.example.discovermovie.data.authentication.UserResponse
import com.example.discovermovie.data.localeDataBase.UserEntity
import com.example.discovermovie.repository.LoginRepository
import com.example.discovermovie.repository.UsersLocaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val usersLocaleRepository: UsersLocaleRepository,
) :
    ViewModel() {

    val userLiveData = MutableLiveData<UserResponse>()
    lateinit var authToken: String


    fun userAuth(login: String, password: String) {
        viewModelScope.launch {
            val reqToken = createToken()
            val token = authAccount(login, password, reqToken)
            val sessionId = createSessionId(BodyTokenRequest(token))
            getAccDetails(sessionId)
            //   getAccDetails(createSessionId(authAccount(login, password, createToken())))
        }
    }


    private suspend fun createToken(): String {
        val response =
            loginRepository.createRequestToken()
        return if (response.isSuccessful) {
            response.body()?.request_token!!
        } else {
            response.message()
        }
    }

    private suspend fun authAccount(
        userName: String,
        password: String,
        token: String,
    ): String {
        val authenticationRequest = AuthenticationRequest(userName, password, token)
        val response = loginRepository.authenticateAccount(authenticationRequest)
        return if (response.code() == 200) {
            authToken = response.body()!!.request_token
            // loginRepository.saveRequestToken(authToken)
            authToken
        } else response.message()

    }

    suspend fun createSessionId(requestToken: BodyTokenRequest): String {
        val response = loginRepository.createSessionId(requestToken)
        return if (response.isSuccessful) {
            response.body()?.session_id!!
            //  loginRepository.saveSessionId(sessionId!!)
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


}

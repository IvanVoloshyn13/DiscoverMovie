package com.example.discovermovie.screens.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.authentication.AuthenticationRequest
import com.example.discovermovie.authentication.RequestToken
import com.example.discovermovie.authentication.UserResponse
import com.example.discovermovie.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    val userLiveData = MutableLiveData<UserResponse>()
    var requestToken: String? = null
    private lateinit var sessionItRequestToken: RequestToken

    private fun createToken(callback: (String) -> Unit) {
        viewModelScope.launch {
            val response =
                loginRepository.createRequestToken()
            if (response.isSuccessful) {
                requestToken = response.body()?.request_token
                callback(requestToken!!)
            } else {
                callback(response.message())
            }
        }
    }

    private fun login(userName: String, password: String, token: String, callback: (RequestToken) -> Unit) {
        viewModelScope.launch {
            val response =
                loginRepository.createSessionWithLogin(
                    AuthenticationRequest(
                        userName, password, token
                    )
                )
            if (response.isSuccessful) {
                sessionItRequestToken = RequestToken(requestToken!!)
                callback(sessionItRequestToken)
            } else {
            }

        }

    }

    private fun createSessionId(requestToken: RequestToken, callback: (String) -> Unit) {
        viewModelScope.launch {
            val response = loginRepository.createSessionId(requestToken)
            if (response.isSuccessful) {
                callback(response.body()!!.session_id)
            }
        }
    }

    private fun getAccDetails(sessionId: String) {
        viewModelScope.launch {
            val response = loginRepository.getAccDetails(sessionId)
            userLiveData.postValue(response.body())
        }
    }

    fun userAuth(login: String, password: String) {
        createToken {
            login(login, password, it) { requestToken ->
                createSessionId(requestToken) { sessionId ->
                    getAccDetails(sessionId)
                }
            }

        }
    }


}
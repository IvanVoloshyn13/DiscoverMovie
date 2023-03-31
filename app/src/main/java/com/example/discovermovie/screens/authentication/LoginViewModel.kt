package com.example.discovermovie.screens.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.authentication.AuthenticationRequest
import com.example.discovermovie.data.authentication.PostTokenBody
import com.example.discovermovie.data.user.UserEntity
import com.example.discovermovie.data.user.UserResponse
import com.example.discovermovie.repository.LoginRepository
import com.example.discovermovie.repository.UsersLocaleRepository
import com.example.discovermovie.util.Resource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val usersLocaleRepository: UsersLocaleRepository,
) :
    ViewModel() {

    val accountAuthLiveData = MutableLiveData<Resource<UserResponse>>()

    fun userAuth(login: String, password: String) {
        viewModelScope.launch {

            val requestToken = createToken()
            requestToken?.let {
                val accessToken = authAccount(login, password, it)
                accessToken?.let { token ->
                    val sessionId = createSessionId(PostTokenBody(token))
                    sessionId?.let { id ->
                        val user = getAccDetails(sessionId)
                        user?.let { _user ->
                            accountAuthLiveData.postValue(Resource.Success(_user))
                            insertUser(_user)
                        }
                    }
                }
            }
        }
    }

    private suspend fun createToken(): String? {
        val response =
            loginRepository.createRequestToken()
        if (response.isSuccessful) {
            return response.body()?.request_token!!
        }
        accountAuthLiveData.postValue(Resource.Error(response.errorBody().toString()))
        return null
    }

    private suspend fun authAccount(
        userName: String,
        password: String,
        token: String,
    ): String? {
        val authenticationRequest = AuthenticationRequest(userName, password, token,"",0)
        val response = loginRepository.authenticateAccount(authenticationRequest)
        if (response.data != null) {
            return response.data.request_token
        }
        accountAuthLiveData.postValue(Resource.Error(response.message))

        return null
    }

        private suspend fun createSessionId(
            requestToken: PostTokenBody
        ): String? {
            val response = loginRepository.createSessionId(requestToken)
            if (response.isSuccessful) {
                return response.body()?.session_id
            }
            accountAuthLiveData.postValue(Resource.Error(response.errorBody().toString()))
            return null

        }

        private suspend fun getAccDetails(sessionId: String): UserResponse? {
            val response = loginRepository.getAccDetails(sessionId)
            if (response.isSuccessful) {
                return response.body()
            }
            accountAuthLiveData.postValue(Resource.Error(response.errorBody().toString()))
            return null
        }

        private fun insertUser(user: UserResponse) {
            viewModelScope.launch {
                val userEntity = UserEntity(
                    id = null,
                    userId = user.id,
                    include_adult = user.include_adult,
                    name = user.name,
                    username = user.username
                )
                usersLocaleRepository.insertUser(userEntity)
            }
        }

    }



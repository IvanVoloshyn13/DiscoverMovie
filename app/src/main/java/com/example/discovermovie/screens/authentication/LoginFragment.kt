package com.example.discovermovie.screens.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.discovermovie.authentication.RequestToken
import com.example.discovermovie.data.repository.LoginRepository
import com.example.discovermovie.data.repository.MovieRemoteRepository
import com.example.discovermovie.databinding.FragmentLoginScreenBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding
    private lateinit var loginRepository: LoginRepository
    private lateinit var movieRemoteRepository: MovieRemoteRepository
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginScreenBinding.inflate(inflater)
        movieRemoteRepository = MovieRemoteRepository()
        loginRepository = LoginRepository(movieRemoteRepository)
        loginViewModel = viewModels<LoginViewModel> {
            LoginViewModelProviderFactory(loginRepository)
        }.value
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bttLogin.setOnClickListener {

            //Rewrite it to one fun in viewModel
            loginViewModel.createToken()
            loginViewModel.tokenLiveData.observe(viewLifecycleOwner) {
                if (it.success) {
                    val username = binding.tvEmail.text.toString()
                    val password = binding.tvPassword.text.toString()
                    loginViewModel.login(username, password, it.request_token)
                    loginViewModel.loginLiveData.observe(viewLifecycleOwner) { login ->
                        if (login.success) {
                            val requestToken = RequestToken(login.request_token)
                            loginViewModel.createSessionId(requestToken)
                            loginViewModel.sessionIdLiveData.observe(viewLifecycleOwner) { session ->
                                if (session.success) {
                                    val sessionId = session.session_id
                                    loginViewModel.getAccDetails(sessionId)
                                    loginViewModel.accountDetailsLiveData.observe(viewLifecycleOwner) { user ->
                                        Log.d("LOGIN", user.username)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
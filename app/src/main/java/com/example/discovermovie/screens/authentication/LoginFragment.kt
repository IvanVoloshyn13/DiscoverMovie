package com.example.discovermovie.screens.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.discovermovie.R
import com.example.discovermovie.data.localeDataBase.UserEntity
import com.example.discovermovie.databinding.FragmentLoginScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginScreenBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bttLogin.setOnClickListener {
            val login = binding.tvEmail.text.toString()
            val password = binding.tvPassword.text.toString()
            loginViewModel.userAuth(login, password)
            loginViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    user.apply {
                        val user = UserEntity(
                            id = null,
                            userId = id,
                            include_adult = include_adult,
                            name = name,
                            username = username
                        )
                        loginViewModel.insertUser(user)
                    }
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    Toast.makeText(
                        this@LoginFragment.requireContext(),
                        user.username,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
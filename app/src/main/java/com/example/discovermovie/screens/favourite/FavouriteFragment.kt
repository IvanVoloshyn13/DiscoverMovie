package com.example.discovermovie.screens.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.discovermovie.MoviesApplication
import com.example.discovermovie.R
import com.example.discovermovie.databinding.FragmentFavouriteBinding
import com.example.discovermovie.repository.DatabaseMovieRepository


class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: FavouriteAdapter
    private lateinit var dbRepository: DatabaseMovieRepository
    private lateinit var favouriteViewModel: FavouriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater)
        val movieDb = MoviesApplication.movieDatabase
        dbRepository = DatabaseMovieRepository(movieDb)
        favouriteViewModel = viewModels<FavouriteViewModel> {
            FavouriteViewModelProviderFactory(dbRepository = dbRepository)
        }.value
        adapter = FavouriteAdapter()
        binding.rvFavouriteMovies.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteViewModel.getFavouriteMovies().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }
}
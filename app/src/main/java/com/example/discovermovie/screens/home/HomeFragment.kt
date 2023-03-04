package com.example.discovermovie.screens.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.discovermovie.R
import com.example.discovermovie.databinding.FragmentHomeBinding
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieItemModel
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_BIG
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_ORIGINAL
import com.example.discovermovie.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterUpcomingMovies: HomeAdapter
    private lateinit var adapterNowPlayingMovies: HomeAdapter
    private  val homeViewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUpcomingMovies()
        observeNowPlayingMovies()
        observeDiscoverMovies()


        binding.apply {
            bttToUpcomingMovies.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("RVName", getString(R.string.upcomingMovie))
                view.findNavController().navigate(R.id.action_homeFragment_to_moviesHome, bundle)
            }
            bttToNowPlayingMovies.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("RVName", getString(R.string.nowPlayingMovie))
                view.findNavController().navigate(R.id.action_homeFragment_to_moviesHome, bundle)
            }
        }


    }

    private fun observeUpcomingMovies() {
        adapterUpcomingMovies = HomeAdapter(this)
        binding.apply {
            rvMovieUpcoming.adapter = adapterUpcomingMovies
            homeViewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            adapterUpcomingMovies.submitList(it)
                            tvUpcomingMovie.text = getText(R.string.upcomingMovie)
                        }
                    }
                    else -> {}

                }


            })
        }
    }

    private fun observeNowPlayingMovies() {
        adapterNowPlayingMovies = HomeAdapter(this)
        binding.apply {
            rvMovieNowPlaying.adapter = adapterNowPlayingMovies
            homeViewModel.nowPlayingMovieLiveData.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            adapterNowPlayingMovies.submitList(it)
                            tvNowPlayingMovie.text = getText(R.string.nowPlayingMovie)
                            progressBar.visibility = View.INVISIBLE
                        }

                    }

                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    else -> {}
                }


            })
        }
    }

    private fun observeDiscoverMovies() {
        binding.apply {
            homeViewModel.discoverMovieLiveData.observe(viewLifecycleOwner, Observer {
                Glide.with(this@HomeFragment)
                    .load("${BASE_IMAGE_URL + IMAGE_POSTER_SIZE_ORIGINAL + it.poster_path}")
                    .centerCrop()
                    .into(ivMoviePosterBig)
                tvExploreMovieDateRelease.text = it.release_date
                tvExploreMovieRuntime.text = it.title
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Destroy")
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    override fun onItemClick(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt("MovieId", movieId)
        requireView().findNavController()
            .navigate(R.id.action_homeFragment_to_detailsFragment, bundle)

    }


}
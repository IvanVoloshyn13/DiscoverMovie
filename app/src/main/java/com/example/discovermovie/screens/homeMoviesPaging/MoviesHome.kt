package com.example.discovermovie.screens.homeMoviesPaging

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.discovermovie.R
import com.example.discovermovie.R.string.nowPlayingMovie
import com.example.discovermovie.databinding.FragmentNoviesHomeBinding
import com.example.discovermovie.screens.home.HomeAdapter
import com.example.discovermovie.screens.home.HomeViewModel
import com.example.discovermovie.util.Resource

const val QUERY_PAGE_SIZE = 20


class MoviesHome : Fragment(), HomeAdapter.OnItemClickListener {

    private lateinit var binding: FragmentNoviesHomeBinding
    private lateinit var adapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoviesHomeBinding.inflate(layoutInflater)
        adapter = HomeAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.adapter = adapter
        binding.rvMovies.addOnScrollListener(onScrollListener)
        getArgs()
    }

    fun getArgs(): String {
        val args = arguments.let {
            it?.getString("RVName")
        } as String
        binding.tvMovieRvName.text = args
        if (args == getString(nowPlayingMovie)) {
            observeNowPlayingMovies()
        } else {
            observeUpcomingMovies()
        }
        return args
    }

    private fun observeUpcomingMovies() {
        homeViewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    progressBarIsNotLoading()
                    it.data.let { list ->
                        isLoading = false
                        adapter.submitList(list!!.toList())
                    }
                }
                is Resource.Loading -> progressBarIsLoading()
                else -> {}
            }
        }
    }

    private fun observeNowPlayingMovies() {
        homeViewModel.nowPlayingMovieLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    progressBarIsNotLoading()
                    it.data.let { list ->
                        isLoading = false
                        adapter.submitList(list!!.toList())


                    }
                }
                is Resource.Loading -> progressBarIsLoading()
                else -> {}
            }
        }

    }

    fun progressBarIsLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun progressBarIsNotLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndIsNotLastPage = !isLoading && !isLastPage
            val isLastItem = firstVisiblePosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisiblePosition > 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndIsNotLastPage && isLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                isLoading = true
                if (getArgs() == getString(nowPlayingMovie)) {
                    homeViewModel.getNowPlayingMovies()
                } else {
                    homeViewModel.getUpcomingMovies()
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = MoviesHome()
    }

    override fun onItemClick(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt("MovieId", movieId)
        requireView().findNavController()
            .navigate(R.id.action_moviesHome_to_detailsFragment, bundle)
    }
}
package com.example.discovermovie.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.api.MovieServices
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieItemModel
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.util.API_KEY
import com.example.discovermovie.util.Resource
import com.example.discovermovie.util.SORT_BY_RELEASE_DATE_DESC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieService: MovieServices
) : ViewModel() {


    val discoverMovieLiveData = MutableLiveData<MovieItemModel>()

    val upcomingMoviesLiveData = MutableLiveData<Resource<List<MovieItemModel>>>()
    var upcomingMoviePage = 1
    var upcomingMovieResponse: MovieModelResponse? = null

    val nowPlayingMovieLiveData = MutableLiveData<Resource<List<MovieItemModel>>>()
    var nowPlayingMoviePage = 1
    var nowPlayingMovieResponse: MovieModelResponse? = null

    init {
        getUpcomingMovies()
        getNowPlayingMovies()
        getDiscoverMovies()
    }

    fun getDiscoverMovies() {
        viewModelScope.launch {
            val response = movieService.discoverMovie(
                Locale.getDefault().language,
                SORT_BY_RELEASE_DATE_DESC
            )
            discoverMovieLiveData.postValue(response.body().let {
                it!!.moviesList[0]
            })
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            upcomingMoviesLiveData.postValue(Resource.Loading())
            val response = movieService.getUpcomingMovies(
                Locale.getDefault().language,
                upcomingMoviePage
            )
            upcomingMoviesLiveData
                .postValue(handleUpcomingMovies(response))

        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            val response = movieService.getNowPlayingMovies(
                Locale.getDefault().language,
                nowPlayingMoviePage
            )
            nowPlayingMovieLiveData.postValue(handleNowPlayingMovies(response))
        }
    }

    private fun handleUpcomingMovies(
        response: Response<MovieModelResponse>
    ): Resource<List<MovieItemModel>> {
        var oldMovies: MutableList<MovieItemModel>? = null
        if (response.isSuccessful) {
            upcomingMoviePage++
            response.body().let {
                if (upcomingMovieResponse == null) {
                    upcomingMovieResponse = it
                } else {
                    oldMovies = upcomingMovieResponse!!.moviesList
                    val newMovies = it?.moviesList
                    oldMovies?.addAll(newMovies!!)

                }
                return Resource.Success(upcomingMovieResponse?.moviesList ?: oldMovies)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleNowPlayingMovies(
        response: Response<MovieModelResponse>
    ): Resource<List<MovieItemModel>> {
        var oldMovies: MutableList<MovieItemModel>? = null
        if (response.isSuccessful) {
            nowPlayingMoviePage++
            response.body().let {
                if (nowPlayingMovieResponse == null) {
                    nowPlayingMovieResponse = it
                } else {
                    oldMovies = nowPlayingMovieResponse!!.moviesList
                    val newMovies = it?.moviesList
                    oldMovies?.addAll(newMovies!!)
                }
                return Resource.Success(nowPlayingMovieResponse?.moviesList ?: oldMovies)
            }
        }
        return Resource.Error(response.message())
    }

}


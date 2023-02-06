package com.example.discovermovie.screens.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.api.APIRepository
import com.example.discovermovie.movieModels.details.MovieDetailsModel
import com.example.discovermovie.movieModels.images.Backdrop
import com.example.discovermovie.movieModels.simpleMovieModel.MovieItemModel
import com.example.discovermovie.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.movieModels.videoModel.MovieVideoModel
import com.example.discovermovie.repository.DatabaseMovieRepository
import com.example.discovermovie.util.API_KEY
import com.example.discovermovie.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

//val dbMovieRepository: DatabaseMovieRepository
class DetailViewModel() : ViewModel() {

    private val APIRepository = APIRepository()
    val movieDetailLiveData = MutableLiveData<Resource<MovieDetailsModel>>()
    val similarMoviesLiveData = MutableLiveData<Resource<List<MovieItemModel>>>()
    val movieVideosLiveData = MutableLiveData<List<MovieVideoModel>>()
    val imagesLiveData = MutableLiveData<List<Backdrop>>()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieDetailLiveData.postValue(Resource.Loading())
            val response =
                APIRepository.api.getMovieDetails(movieId, API_KEY, Locale.getDefault().language)
            movieDetailLiveData.postValue(handleMovieDetail(response))
        }
    }

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            similarMoviesLiveData.postValue(Resource.Loading())
            val response =
                APIRepository.api.getSimilarMovies(
                    movieId,
                    API_KEY,
                    Locale.getDefault().language,
                    1
                )
            similarMoviesLiveData.postValue(handleSimilarMovies(response))
        }
    }

    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            val response =
                APIRepository.api.getMovieVideos(movieId, API_KEY, "en")
            movieVideosLiveData.postValue(response.body()!!.videoList)
        }
    }

    fun getImages(movieId: Int) {
        viewModelScope.launch {
            val response =
                APIRepository.api.getImages(movieId, API_KEY, "en")
            imagesLiveData.postValue(response.body()!!.backdrops)
        }
    }


    private fun handleMovieDetail(response: Response<MovieDetailsModel>): Resource<MovieDetailsModel> {
        if (response.isSuccessful) {
            response.body().let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSimilarMovies(response: Response<MovieModelResponse>): Resource<List<MovieItemModel>> {
        if (response.isSuccessful) {
            response.body().let {
                return Resource.Success(it!!.moviesList)
            }
        }
        return Resource.Error(response.message())
    }
}
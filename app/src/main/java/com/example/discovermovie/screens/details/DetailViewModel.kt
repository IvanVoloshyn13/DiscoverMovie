package com.example.discovermovie.screens.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import com.example.discovermovie.data.movieModels.details.MovieDetailsModel
import com.example.discovermovie.data.movieModels.images.Backdrop
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieItemModel
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.data.repository.DetailRepository
import com.example.discovermovie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    val movieDetailLiveData = MutableLiveData<Resource<MovieDetailsModel>>()
    val similarMoviesLiveData = MutableLiveData<Resource<List<MovieItemModel>>>()
    val imagesLiveData = MutableLiveData<List<Backdrop>>()
    val ratingLiveData = MutableLiveData<String>()
    var movieIsInFavourite = false

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieDetailLiveData.postValue(Resource.Loading())
            val response = detailRepository.getMovieDetails(movieId)
            movieDetailLiveData.postValue(handleMovieDetail(response))
        }
    }

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            similarMoviesLiveData.postValue(Resource.Loading())
            val response = detailRepository.getSimilarMovies(movieId)
            similarMoviesLiveData.postValue(handleSimilarMovies(response))
        }
    }


    fun getImages(movieId: Int) {
        viewModelScope.launch {
            val response = detailRepository.getImages(movieId)
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

    private fun handleSimilarMovies(
        response: Response<MovieModelResponse>
    ): Resource<List<MovieItemModel>> {
        if (response.isSuccessful) {
            response.body().let {
                return Resource.Success(it!!.moviesList)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToFavouriteMovies(movie: DatabaseMovieModel) {
        viewModelScope.launch {
            detailRepository.addMovieToLocaleRepository(movie)
        }
    }

    fun deleteFromFavouriteMovies(movieId: Int) {
        viewModelScope.launch {
            detailRepository.deleteMovieFromLocaleRepository(movieId)
        }
    }


    fun isMovieInFavourite(movieId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val value = detailRepository.getSingleMovieById(movieId)
            if (value != null) {
                movieIsInFavourite = movieId == value.movieId
                callback(movieIsInFavourite)
            }
        }
    }

    fun postRating(movieId: Int, rating: Double) {
        viewModelScope.launch {
            val response = detailRepository.setRating(movieId, rating)
            ratingLiveData.postValue(response.toString())

        }
    }


}
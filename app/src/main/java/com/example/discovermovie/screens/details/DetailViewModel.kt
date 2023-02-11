package com.example.discovermovie.screens.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import com.example.discovermovie.data.repository.RemoteRepository
import com.example.discovermovie.data.movieModels.details.MovieDetailsModel
import com.example.discovermovie.data.movieModels.images.Backdrop
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieItemModel
import com.example.discovermovie.data.movieModels.simpleMovieModel.MovieModelResponse
import com.example.discovermovie.data.movieModels.videoModel.MovieVideoModel
import com.example.discovermovie.data.repository.DetailRepository
import com.example.discovermovie.data.repository.LocaleRepository
import com.example.discovermovie.util.API_KEY
import com.example.discovermovie.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

//val dbMovieRepository: DatabaseMovieRepository
class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {

    val movieDetailLiveData = MutableLiveData<Resource<MovieDetailsModel>>()
    val similarMoviesLiveData = MutableLiveData<Resource<List<MovieItemModel>>>()
    val imagesLiveData = MutableLiveData<List<Backdrop>>()
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

    fun getFavouriteMovies() = detailRepository.getFavouriteMovies()


    fun isFavourite(
        movieId: Int, favouriteMoviesList: List<DatabaseMovieModel>, callback: (Boolean) -> Unit
    ): Boolean {
        if (favouriteMoviesList != null) {
            for (elements in favouriteMoviesList!!) {
                if (elements.movieId == movieId) {
                    movieIsInFavourite = true
                }
            }
            callback(movieIsInFavourite)
        }
        return movieIsInFavourite
    }


}
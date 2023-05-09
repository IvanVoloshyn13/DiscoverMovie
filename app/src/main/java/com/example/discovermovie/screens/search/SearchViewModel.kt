package com.example.discovermovie.screens.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discovermovie.data.movieModels.search.SearchResponse
import com.example.discovermovie.data.movieModels.search.SearchResponseItem
import com.example.discovermovie.repository.SearchRepository
import com.example.discovermovie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val searchRepository: SearchRepository) : ViewModel() {

    var multiSearchLiveData = MutableLiveData<Resource<List<SearchResponseItem>>>()
    var pageSearch = 1

    fun multiSearch(query: String) {
        viewModelScope.launch {
            multiSearchLiveData.postValue(Resource.Loading())
            val response = searchRepository.multiSearch(query, pageSearch)
            if (response.isSuccessful) {
                multiSearchLiveData.postValue(handleMultiSearch(response))
            } else {

            }
        }
    }

    fun handleMultiSearch(response: Response<SearchResponse>): Resource<List<SearchResponseItem>> {
        if (response.isSuccessful) {
            response.body().let { searchResponse ->
                return Resource.Success(searchResponse!!.searchList)
            }
        } else {
            return Resource.Error(response.message())
        }
    }
}
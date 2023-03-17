package com.example.discovermovie.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.discovermovie.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter
    private var searchJob: Job? = null
    private val searchDelay: Long = 500

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        adapter = SearchAdapter()
        binding.rvSearch.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! >= 1) {
                    //  var encodedQuery = URLEncoder.encode(query, "UTF-8")
                    searchViewModel.multiSearch(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    newText?.let { query ->
                        if (query.length >= 2) {
                            delay(searchDelay)
                            searchViewModel.multiSearch(query)
                        }
                    }
                }
                return true
            }

        })

        searchViewModel.multiSearchLiveData.observe(viewLifecycleOwner) { searchResponse ->
            searchResponse.data?.let { searchResponse ->
                adapter.submitSearchList(searchResponse)
            }

        }
    }


}
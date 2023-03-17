package com.example.discovermovie.screens.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.discovermovie.R
import com.example.discovermovie.data.movieModels.search.SearchResponseItem
import com.example.discovermovie.databinding.LayoutSearchItemBinding
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_SMALL

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private val searchItemList = ArrayList<SearchResponseItem>()

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LayoutSearchItemBinding.bind(itemView)

        fun bind(data: SearchResponseItem) {
            binding.apply {
                tvMovieTitle.text = data.original_title
                Glide.with(itemView)
                    .load(BASE_IMAGE_URL + IMAGE_POSTER_SIZE_SMALL + data.poster_path)
                    .into(ivMoviePosterSmall)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_search_item, parent, false)
        return SearchViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchItemList[position])
    }

    override fun getItemCount(): Int {
        return searchItemList.size
    }

    fun submitSearchList(list: List<SearchResponseItem>) {
        searchItemList.clear()
        searchItemList.addAll(list)
        notifyDataSetChanged()
    }

}